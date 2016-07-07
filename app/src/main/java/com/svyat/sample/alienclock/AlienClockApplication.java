package com.svyat.sample.alienclock;

import android.content.Intent;
import android.support.multidex.MultiDexApplication;

import com.svyat.sample.alienclock.skin.AlienSkinManager;
import com.svyat.sample.alienclock.state.StateMachine;
import static com.svyat.sample.alienclock.common.Constants.ACTION_APP_START;
import static com.svyat.sample.alienclock.common.Constants.ACTION_APP_TERMINATE;

/**
 * Created by shromyak on 07.07.2016.
 */
public class AlienClockApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    public void onTerminate() {
        deinit();
        super.onTerminate();
    }

    private void init() {
        AlienSkinManager.get(this);
        startStateMachine();
    }

    private void deinit() {
        AlienSkinManager.clear();
        stopStateMachine();
    }

    private void startStateMachine() {
        Intent intent = new Intent(this, StateMachine.class);
        intent.setAction(ACTION_APP_START);
        startService(intent);
    }

    private void stopStateMachine() {
        Intent intent = new Intent(this, StateMachine.class);
        intent.setAction(ACTION_APP_TERMINATE);
        startService(intent);
    }
}
