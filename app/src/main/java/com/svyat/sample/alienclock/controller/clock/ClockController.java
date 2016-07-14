package com.svyat.sample.alienclock.controller.clock;

import android.util.Log;

import com.svyat.sample.alienclock.content.AlienContentBrick;
import com.svyat.sample.alienclock.controller.BaseController;
import com.svyat.sample.alienclock.model.DaemonParams;

/**
 * Created by shromyak on 07.07.2016.
 *
 * This is a stub that created
 * for custom integration with decorators
 * (promise)
 * Main integration is in parent
 * @link BaseController
 */
public class ClockController extends BaseController {

    private final String LOG_TAG = ClockController.class.getSimpleName();

    public ClockController(DaemonParams daemonParams, AlienContentBrick alienContentBrick) {
        super(daemonParams, alienContentBrick);
        Log.d(LOG_TAG, "Instantiated");
    }
}
