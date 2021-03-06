package com.svyat.sample.alienclock.common;

/**
 * Created by shromyak on 07.07.2016.
 *
 * Common public constants
 */
public interface Constants {

    String ACTION_APP_START = "com.svyat.sample.alienclock.action.APP_START";
    String ACTION_APP_TERMINATE = "com.svyat.sample.alienclock.action.APP_TERMINATE";

    String ACTION_EVENT_DATA_CHANGED = "com.svyat.sample.alienclock.action.event.DATA_CHANGED";
    String ACTION_EVENT_SETTINGS_CHANGED = "com.svyat.sample.alienclock.action.event.SETTINGS_CHANGED";
    String ACTION_EVENT_THEME_CHANGED = "com.svyat.sample.alienclock.action.event.THEME_CHANGED";

    String KEY_THEME_TYPE = "com.svyat.sample.alienclock.key.THEME_TYPE";

    String GCM_DOWNLOAD_TASK_TAG = "com.svyat.sample.alienclock.net.DOWNLOAD_TASK";

    String KEY_DEBUG_MESSENGER = "com.svyat.sample.alienclock.key.DEBUG_MESSENGER";
    String KEY_DEBUG_COMMAND = "com.svyat.sample.alienclock.key.DEBUG_COMMAND";
    String DEBUG_COMMAND_DRY_RUN = "com.svyat.sample.alienclock.net.cmd.DRY_RUN";
    String DEBUG_COMMAND_GET_TEST = "com.svyat.sample.alienclock.net.cmd.GET_TEST";

    int DEBUG_WHAT_DRY_RUN = 1;
    int DEBUG_WHAT_GET_TEST = 1;
}
