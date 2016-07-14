package com.svyat.sample.alienclock.state;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.svyat.sample.alienclock.model.ApplicationSettings;
import com.svyat.sample.alienclock.net.AlienDownloadManager;
import com.svyat.sample.alienclock.net.gcmbased.GcmbasedDownloadManager;
import com.svyat.sample.alienclock.settings.AlienSettingsManager;

import static com.svyat.sample.alienclock.common.Constants.ACTION_APP_START;
import static com.svyat.sample.alienclock.common.Constants.ACTION_APP_TERMINATE;
import static com.svyat.sample.alienclock.common.Constants.ACTION_EVENT_DATA_CHANGED;
import static com.svyat.sample.alienclock.common.Constants.ACTION_EVENT_SETTINGS_CHANGED;

/**
 * Created by shromyak on 07.07.2016.
 *
 * Actually this class isn't doing anything else except starting download task jobs.
 * Future goal is to turn this class into event bus.
 */
public class StateMachine extends Service {

    private final static String TAG = StateMachine.class.getSimpleName();

    private static int startId;

    private static int oldTaskInterval = -1;

    private static long lastDownload = -1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {

            handleAction(intent, startId);
        } else {
            //Sticky restart
            StateMachine.startId = startId;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void handleAction(@NonNull Intent intent, int startId) {

        String action = intent.getAction();

        if (ACTION_APP_START.equals(action)) {

            StateMachine.startId = startId;
            init();

        } else if (ACTION_APP_TERMINATE.equals(action)) {

            deinit();
            stopSelf(StateMachine.startId);

        } else if (ACTION_EVENT_SETTINGS_CHANGED.equals(action)) {

            //reinitialization of download task
            initDownloadManager();
        } else if (ACTION_EVENT_DATA_CHANGED.equals(action)) {

            //download statistic
            lastDownload = System.currentTimeMillis();
        }
    }

    private void init() {
        Log.d(TAG, "Started");
        initDownloadManager();
    }

    private void deinit() {
        Log.d(TAG, "Stopped");
    }

    private void initDownloadManager() {

        AlienSettingsManager settingsManager = AlienSettingsManager.get(this);
        ApplicationSettings settings = settingsManager.getSettings();
        int interval = settings.getDownloadIntervalSeconds();

        if(settings.isDownloadActive()) {
            startDownloadManager(interval);
        } else {
            oldTaskInterval = interval;
            stopDownloadManager();
        }
    }

    private void startDownloadManager(int interval) {

        AlienDownloadManager downloadManager = new GcmbasedDownloadManager(this);
        if (!downloadManager.isInAction()||oldTaskInterval != interval) {
            oldTaskInterval = interval;
            downloadManager.startWithIntervalSec(interval);
        }
    }

    private void stopDownloadManager() {

        AlienDownloadManager downloadManager = new GcmbasedDownloadManager(this);
        if (downloadManager.isInAction()) {
            downloadManager.stop();
        }
    }
}
