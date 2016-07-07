package com.svyat.sample.alienclock;

import android.content.Intent;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.test.ServiceTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.google.android.gms.gcm.PendingCallback;
import com.svyat.sample.alienclock.common.Constants;
import com.svyat.sample.alienclock.net.gcmbased.GcmbasedDownloadTaskService;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by MAC on 09.07.16.
 */
public class GcmbasedDownloadTaskServiceTest extends ServiceTestCase<GcmbasedDownloadTaskService> {

    private Intent forceTaskIntent;
    private ConditionVariable conditionVariable;
    private final AtomicBoolean passed = new AtomicBoolean(false);
    private final Messenger debugMessenger = new Messenger(new MyHandler());

    private final class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            if (what == Constants.DEBUG_WHAT_GET_TEST) {
                assert msg.arg1 == 1;
            }
            passed.set(true);
            conditionVariable.open();
        }
    }

    public GcmbasedDownloadTaskServiceTest() {
        super(GcmbasedDownloadTaskService.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        forceTaskIntent = new Intent("com.google.android.gms.gcm.ACTION_TASK_READY");
        forceTaskIntent.setClass(getContext(), GcmbasedDownloadTaskService.class);

        forceTaskIntent.putExtra(Constants.KEY_DEBUG_MESSENGER, debugMessenger);

        //This ("callback" extra) used to pass validator in original implementation
        Parcel parcel = Parcel.obtain();
        parcel.writeStrongBinder(debugMessenger.getBinder());
        PendingCallback callback = new PendingCallback(parcel);
        forceTaskIntent.putExtra("callback", callback);

        forceTaskIntent.putExtra("tag", Constants.GCM_DOWNLOAD_TASK_TAG);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @SmallTest
    public void testGcmBasedDownload_1_Create() {
        assert getService() != null;
    }

    @SmallTest
    public void testGcmBasedDownload_2_DryRun() {

        forceTaskIntent.putExtra(Constants.KEY_DEBUG_COMMAND, Constants.DEBUG_COMMAND_DRY_RUN);

        startService(forceTaskIntent);

        conditionVariable = new ConditionVariable();
        conditionVariable.block(200L);

        assert passed.get();
    }

    @SmallTest
    public void testGcmBasedDownload_3_GetTest() {

        forceTaskIntent.putExtra(Constants.KEY_DEBUG_COMMAND, Constants.DEBUG_COMMAND_GET_TEST);

        startService(forceTaskIntent);

        conditionVariable = new ConditionVariable();
        conditionVariable.block(55000L);

        assert passed.get();
    }
}
