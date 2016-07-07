package com.svyat.sample.alienclock.net.gcmbased;

import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;
import com.svyat.sample.alienclock.common.Constants;
import com.svyat.sample.alienclock.net.AlienDownloadManager;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by shromyak on 09.07.16.
 *
 * Default download manager is based on GCM Network Task engine
 * that is compat wrapper for JobService and Job
 * It's not persisted task we create, but demands Unmetered network
 * connection, so we are free from any connectivity listeners
 *
 * Please note: this approach isn't suitable for devices without GSM Services
 * installed such as Cyanogenmod etc., so you should implement your own
 * download manager in this case
 */
public class GcmbasedDownloadManager implements AlienDownloadManager {

    private final static String LOG_TAG = GcmbasedDownloadManager.class.getSimpleName();

    private final static int DEFAULT_FLEX = 5;

    private final Context context;

    private final AtomicBoolean isInAction;

    public GcmbasedDownloadManager(Context context) {

        this.context = context;
        this.isInAction = new AtomicBoolean(false);
    }

    @Override
    public void startWithIntervalSec(int periodSecs) {

        synchronized (isInAction) {

            PeriodicTask periodic = new PeriodicTask.Builder()
                    .setService(GcmbasedDownloadTaskService.class)
                    .setPeriod(periodSecs)
                    .setFlex(DEFAULT_FLEX)
                    .setTag(Constants.GCM_DOWNLOAD_TASK_TAG)
                    .setPersisted(false)
                    .setRequiredNetwork(Task.NETWORK_STATE_UNMETERED)
                    .setRequiresCharging(false)
                    .setUpdateCurrent(true)
                    .build();

            GcmNetworkManager.getInstance(context).schedule(periodic);

            isInAction.set(true);
        }
    }

    @Override
    public boolean isInAction() {

        return isInAction.get();
    }

    @Override
    public void stop() {

        synchronized (isInAction) {

            if (isInAction.get()) {

                GcmNetworkManager.getInstance(context)
                        .cancelTask(Constants.GCM_DOWNLOAD_TASK_TAG, GcmbasedDownloadTaskService.class);

                isInAction.set(false);
            }
        }
    }

}
