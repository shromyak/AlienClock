package com.svyat.sample.alienclock.net;

/**
 * Created by MAC on 09.07.16.
 */
public interface AlienDownloadManager {
    void startWithIntervalSec(int sec);
    boolean isInAction();
    void stop();
}
