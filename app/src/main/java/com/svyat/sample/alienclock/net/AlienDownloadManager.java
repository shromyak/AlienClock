package com.svyat.sample.alienclock.net;

/**
 * Created by shromyak on 09.07.16.
 *
 * Interface describing downloader manager base functionality
 * Main idea that Downloader manager should start downloading operations
 * when suitable downloading window appears (for example: unmetered network + in time)
 *
 * Downloader manager performs bulk downloading (queued as usual)
 * There is no dedicated scheduling for certain task (downloader)
 */
public interface AlienDownloadManager {
    void startWithIntervalSec(int sec);
    boolean isInAction();
    void stop();
}
