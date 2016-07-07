package com.svyat.sample.alienclock.net;

/**
 * Created by MAC on 09.07.16.
 */
public interface AlienDownloader {

    void start();
    void forceStop();
    boolean isActive();
    boolean test();

    enum DownloaderType {
        BLOOMCHAN
    }
}
