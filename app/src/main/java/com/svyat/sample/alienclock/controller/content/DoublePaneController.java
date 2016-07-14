package com.svyat.sample.alienclock.controller.content;

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
public class DoublePaneController extends BaseController {

    public DoublePaneController(DaemonParams daemonParams, AlienContentBrick brick) {
        super(daemonParams, brick);
    }
}