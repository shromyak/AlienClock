package com.svyat.sample.alienclock.model;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.svyat.sample.alienclock.net.AlienDownloader;
import com.svyat.sample.alienclock.timer.AlienTimer;
import com.svyat.sample.alienclock.utils.StringUtils;

/**
 * Created by shromyak on 08.07.2016.
 *
 * Main application settings, controlled by drawer menu
 */
public class ApplicationSettings {

    //Shared prefs keys
    private static final String KEY_CONTENT_ROOT_TAG = "com.svyat.sample.alienclock.key.CONTENT_ROOT_TAG";
    private static final String KEY_TIME_EVENT_TYPE = "com.svyat.sample.alienclock.key.TIME_EVENT_TYPE";
    private static final String KEY_DOWNLOAD_TASKS = "com.svyat.sample.alienclock.key.DOWNLOAD_TASKS";
    private static final String KEY_IS_DOWNLOAD_ACTIVE = "com.svyat.sample.alienclock.key.IS_DOWNLOAD_ACTIVE";
    private static final String KEY_DOWNLOAD_INTERVAL_SECONDS = "com.svyat.sample.alienclock.key.DOWNLOAD_INTERVAL_SECONDS";

    private final static int DEFAULT_DOWNLOAD_TASK_INTERVAL = 20;

    private String contentRootTag;
    private int downloadIntervalSeconds;
    private String downloadTasksCsv;
    private AlienTimer.TimeEventType timerEventType;
    private boolean isDownloadActive;

    private ApplicationSettings (Builder builder) {

        contentRootTag = builder.contentRootTag;
        downloadIntervalSeconds = builder.downloadIntervalSeconds;
        downloadTasksCsv = builder.downloadTasksCsv;
        isDownloadActive = builder.isDownloadActive;
        timerEventType = builder.timerEventType;
    }

    public String getContentRootTag() {
        return contentRootTag;
    }

    public ApplicationSettings setContentRootTag(String contentRootTag) {
        return new Builder(this).setContentRootTag(contentRootTag).build();
    }

    public boolean isDownloadActive() {
        return isDownloadActive;
    }

    public int getDownloadIntervalSeconds() {
        return downloadIntervalSeconds;
    }

    public String getDownloadTasksCsv() {
        return downloadTasksCsv;
    }

    public AlienTimer.TimeEventType getTimerEventType() {
        return timerEventType;
    }

    public Builder builder() {
        return new Builder(this);
    }

    public void storeToPreferences(SharedPreferences prefs) {

        prefs.edit()
                .putString(KEY_CONTENT_ROOT_TAG, contentRootTag)
                .putString(KEY_DOWNLOAD_TASKS, downloadTasksCsv)
                .putBoolean(KEY_IS_DOWNLOAD_ACTIVE, isDownloadActive)
                .putString(KEY_TIME_EVENT_TYPE, timerEventType == null ? null : timerEventType.name())
                .apply();
    }

    @Override
    public boolean equals(Object set) {

        if (set != null && set instanceof ApplicationSettings) {

            ApplicationSettings settings = (ApplicationSettings) set;

            if (TextUtils.equals(settings.contentRootTag, contentRootTag)
                    && TextUtils.equals(settings.downloadTasksCsv, downloadTasksCsv)
                    && settings.downloadIntervalSeconds == downloadIntervalSeconds
                    && settings.timerEventType == timerEventType
                    && settings.isDownloadActive == isDownloadActive) {

                return true;
            }
        }
        return false;
    }

    public static class Builder {

        private String contentRootTag;
        private int downloadIntervalSeconds = DEFAULT_DOWNLOAD_TASK_INTERVAL;
        private String downloadTasksCsv = StringUtils.enumNamesToCsv(AlienDownloader.DownloaderType.class);
        private AlienTimer.TimeEventType timerEventType = AlienTimer.TimeEventType.SECOND;
        private boolean isDownloadActive = true;

        public Builder setContentRootTag(String contentRootTag) {
            this.contentRootTag = contentRootTag;
            return this;
        }

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

        public Builder() {
        }

        private Builder (ApplicationSettings settings) {
            contentRootTag = settings.contentRootTag;
            downloadIntervalSeconds = settings.downloadIntervalSeconds;
            downloadTasksCsv = settings.downloadTasksCsv;
            isDownloadActive = settings.isDownloadActive;
            timerEventType = settings.timerEventType;
        }

        public Builder readFromPreferences(SharedPreferences prefs) {

            contentRootTag = prefs.getString(KEY_CONTENT_ROOT_TAG, contentRootTag);
            isDownloadActive = prefs.getBoolean(KEY_IS_DOWNLOAD_ACTIVE, isDownloadActive);
            downloadIntervalSeconds = prefs.getInt(KEY_DOWNLOAD_INTERVAL_SECONDS
                    , isDownloadActive ? downloadIntervalSeconds : -1);
            downloadTasksCsv = prefs.getString(KEY_DOWNLOAD_TASKS, downloadTasksCsv);
            timerEventType = AlienTimer.TimeEventType.valueOf(prefs.getString(KEY_TIME_EVENT_TYPE, timerEventType.name()));

            return this;
        }

        public ApplicationSettings build() {
            return new ApplicationSettings(this);
        }
    }
}