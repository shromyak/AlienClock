package com.svyat.sample.alienclock.net.gcmbased;

import android.content.Intent;
import android.os.ConditionVariable;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.svyat.sample.alienclock.common.Constants;
import com.svyat.sample.alienclock.model.ApplicationSettings;
import com.svyat.sample.alienclock.net.AlienDownloader;
import com.svyat.sample.alienclock.net.source.bloomchan.BloomchanDownloader;
import com.svyat.sample.alienclock.settings.AlienSettingsManager;
import com.svyat.sample.alienclock.utils.StringUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by shromyak on 07.07.2016.
 *
 * GCM Task scheduler will hold a PowerManager.WakeLock for this service, however after three minutes of execution
 * if onRunTask has not returned result it will be considered to have timed out, and the wakelock will be released.
 * Rescheduling task with RESULT_RESCHEDULE at this point will have no effect.
 */
public class GcmbasedDownloadTaskService extends GcmTaskService {

    private final static String LOG_TAG = GcmbasedDownloadTaskService.class.getSimpleName();

    private final static String MESSAGE_TAG = GcmbasedDownloadTaskService.class.getSimpleName();

    private final HashMap<AlienDownloader.DownloaderType, AlienDownloader> downloaders = new HashMap<>();

    @Override
    public int onStartCommand(Intent intent, int i, int i1) {
        initDebug(intent);
        return super.onStartCommand(intent, i, i1);
    }

    /**
     * This method will run at priority Process.THREAD_PRIORITY_BACKGROUND. If this is not appropriate,
     * you should start your own service with suitably conditioned threads.
     *
     * @param taskParams -- parameter of created in @link DefaultDownloadManager periodical task
     * @return One of GcmNetworkManager.RESULT_SUCCESS, GcmNetworkManager.RESULT_RESCHEDULE,
     * or GcmNetworkManager.RESULT_FAILURE
     */
    @Override
    public int onRunTask(TaskParams taskParams) {

        echoDryRunIfRequested();

        startDownload();

        return GcmNetworkManager.RESULT_SUCCESS;
    }

    private void startDownload() {

        ApplicationSettings settings = AlienSettingsManager.get(this).getSettings();

        if (!settings.isDownloadActive()) return;

        String taskCsv = settings.getDownloadTasksCsv();

        if (TextUtils.isEmpty(taskCsv)) return;

        List<String> tasks = StringUtils.splitCsv(taskCsv);

        startDownloadNamedTasks(tasks);
    }

    private void startDownloadNamedTasks(List<String> tasks) {

        if (StringUtils.contains(tasks, AlienDownloader.DownloaderType.BLOOMCHAN.name())) {

            AlienDownloader downloader = getStoppedDownloader(AlienDownloader.DownloaderType.BLOOMCHAN);
            if (isGetTestRequested()) {

                echoGetTest(downloader.test());
            } else {

                downloader.start();
            }
        }
    }

    private AlienDownloader getStoppedDownloader(AlienDownloader.DownloaderType target) {

        AlienDownloader downloader = downloaders.get(target);

        if (downloader == null) {

            try {

                downloader = new BloomchanDownloader(this);
                downloaders.put(target, downloader);

            } catch (Exception e) {

                Log.e(LOG_TAG, e.getMessage(), e);
                return null;
            }
        }

        if (downloader.isActive()) {

            downloader.forceStop();
        }

        return downloader;
    }

    /**
     * *********************************************************************
     *          This part is for ANDROID TEST CASE
     *
     * Please refer:
     * @link GcmbasedDownloadTaskServiceTest#testGcmBasedDownload_2_DryRun
     * *********************************************************************
     */
    private Messenger debugMessenger;
    private String debugCommand;

    private void initDebug(Intent intent) {

        if (intent != null
                && intent.getExtras() != null
                && intent.getExtras().containsKey(Constants.KEY_DEBUG_MESSENGER)
                && intent.getExtras().containsKey(Constants.KEY_DEBUG_COMMAND)) {

            debugMessenger = (Messenger) intent.getExtras().get(Constants.KEY_DEBUG_MESSENGER);
            debugCommand = intent.getExtras().getString(Constants.KEY_DEBUG_COMMAND);
        }
    }

    private boolean isDryRunRequested() {

        return debugMessenger != null
                && Constants.DEBUG_COMMAND_DRY_RUN.equals(debugCommand);
    }

    private void echoDryRunIfRequested() {

        if (!isDryRunRequested()) return;

        sendEchoMessage(Constants.DEBUG_WHAT_DRY_RUN, 1);
    }

    private boolean isGetTestRequested() {

        return debugMessenger != null
                && Constants.DEBUG_COMMAND_GET_TEST.equals(debugCommand);

    }

    /**
    * Please refer:
    * @link GcmbasedDownloadTaskServiceTest#testGcmBasedDownload_2_DryRun
     */
    private void echoGetTest(boolean result) {

        sendEchoMessage(Constants.DEBUG_WHAT_DRY_RUN, result ? 1 : 0);
    }

    private void sendEchoMessage(int what, int passed) {

        Message msg = Message.obtain();
        msg.what = what;
        msg.arg1 = passed;

        try {

            debugMessenger.send(msg);

            //Prevent original gcm services callback to run till test finished
            final ConditionVariable variable = new ConditionVariable();
            variable.block(200L);

        } catch (RemoteException ignorable) {
        }
    }
}
