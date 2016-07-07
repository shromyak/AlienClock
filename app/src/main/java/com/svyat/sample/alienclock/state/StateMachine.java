package com.svyat.sample.alienclock.state;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.svyat.sample.alienclock.common.Constants.ACTION_APP_START;
import static com.svyat.sample.alienclock.common.Constants.ACTION_APP_TERMINATE;
import static com.svyat.sample.alienclock.common.Constants.ACTION_EVENT_UI_CHANGED;

/**
 * Created by shromyak on 07.07.2016.
 */
public class StateMachine extends Service {

    private Connectivity connectivityState;
    private UserInterface userInterfaceState;

    private static int startId;

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

        if (ACTION_EVENT_UI_CHANGED.equals(action)) {

        } else if (ACTION_APP_START.equals(action)) {

            StateMachine.startId = startId;
            init();

        } else if (ACTION_APP_TERMINATE.equals(action)) {

            deinit();
            stopSelf(StateMachine.startId);
        }
    }

    private void init() {
        initDownloadManager();
        startDownloadManager();
    }

    private void deinit() {
        stopDownloadManager();
    }

    private void initDownloadManager() {

    }

    private void startDownloadManager() {

    }

    private void stopDownloadManager() {

    }

    public enum UserInterface {
        INTERFACE,
        PAUSED,
        STOPPED
    }

    public enum Connectivity {
        CONNECTED,
        DISABLED,
        LISTENING
    }
}
