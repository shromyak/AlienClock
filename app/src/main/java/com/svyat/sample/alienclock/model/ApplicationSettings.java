package com.svyat.sample.alienclock.model;

import android.content.SharedPreferences;

import com.svyat.sample.alienclock.net.AlienDownloader;
import com.svyat.sample.alienclock.timer.AlienTimer;
import com.svyat.sample.alienclock.utils.StringUtils;

/**
 * Created by shromyak on 08.07.2016.
 */
public class ApplicationSettings {

    //Shared prefs keys
    private static final String KEY_TIMEEVENT_TYPE = "com.svyat.sample.alienclock.key.TIMEEVENT_TYPE";
    private static final String KEY_DOWNLOAD_TASKS = "com.svyat.sample.alienclock.key.DOWNLOAD_TASKS";
    private static final String KEY_ISDOWNLOAD_ACTIVE = "com.svyat.sample.alienclock.key.ISDOWNLOAD_ACTIVE";

    private String downloadTasksCsv;
    private AlienTimer.TimeEventType timerEventType;
    private boolean isDownloadActive;

    private ApplicationSettings (Builder builder) {

        downloadTasksCsv = builder.downloadTasksCsv;
        isDownloadActive = builder.isDownloadActive;
        timerEventType = builder.timerEventType;

    }

    public boolean isDownloadActive() {

        return isDownloadActive;
    }

    public String getDownloadTasksCsv() {

        return downloadTasksCsv;
    }

    public AlienTimer.TimeEventType getTimerEventType() {

        return timerEventType;
    }

    public void storeToPreferences(SharedPreferences prefs) {

        prefs.edit()
                .putString(KEY_DOWNLOAD_TASKS, downloadTasksCsv)
                .putBoolean(KEY_ISDOWNLOAD_ACTIVE, isDownloadActive)
                .putString(KEY_TIMEEVENT_TYPE, timerEventType.name())
                .apply();

    }

    @Override
    public boolean equals(Object set) {

        if (set != null && set instanceof ApplicationSettings) {

            ApplicationSettings settings = (ApplicationSettings) set;

            if (settings.timerEventType.equals(timerEventType)) {

                return true;
            }
        }

        return false;
    }

    public static class Builder {

        private String downloadTasksCsv = StringUtils.enumNamesToCsv(AlienDownloader.DownloaderType.class);
        private AlienTimer.TimeEventType timerEventType = AlienTimer.TimeEventType.SECOND;
        private boolean isDownloadActive = true;

        public Builder setDownloadTasksCsv(String downloadTasksCsv) {
            this.downloadTasksCsv = downloadTasksCsv;
            return this;
        }

        public Builder setIsDownloadActive(boolean downloadActive) {
            isDownloadActive = downloadActive;
            return this;
        }

        public Builder setTimerEventType(AlienTimer.TimeEventType timerEventType) {
            this.timerEventType = timerEventType;
            return this;
        }

        public Builder readFromPreferences(SharedPreferences prefs) {

            downloadTasksCsv = prefs.getString(KEY_DOWNLOAD_TASKS, downloadTasksCsv);
            isDownloadActive = prefs.getBoolean(KEY_ISDOWNLOAD_ACTIVE, isDownloadActive);
            timerEventType = AlienTimer.TimeEventType.valueOf(prefs.getString(KEY_TIMEEVENT_TYPE, timerEventType.name()));

            return this;
        }

        public ApplicationSettings build() {

            return new ApplicationSettings(this);
        }
    }
}