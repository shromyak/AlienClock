package com.svyat.sample.alienclock.net;

/**
 * Created by shromyak on 09.07.16.
 *
 * Downloader interface used to control downloader implementations by DownloaderManager
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
