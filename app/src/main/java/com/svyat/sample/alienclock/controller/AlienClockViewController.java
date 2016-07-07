package com.svyat.sample.alienclock.controller;

import android.os.Handler;
import android.view.View;

import com.svyat.sample.alienclock.model.AlienViewSettings;
import com.svyat.sample.alienclock.model.TimerSettings;
import com.svyat.sample.alienclock.settings.AlienSettingsManager;
import com.svyat.sample.alienclock.timer.AlienTimer;
import com.svyat.sample.alienclock.timer.DefaultTimer;
import com.svyat.sample.alienclock.timer.TimerListener;
import com.svyat.sample.alienclock.widget.AlienViewGroup;

/**
 * Created by shromyak on 07.07.2016.
 */
public class AlienClockViewController extends AlienViewController {

    private AlienTimer alienTimer;

    private TimerListener timerListener;

    public AlienClockViewController(AlienViewGroup view) {
        super(view);
    }

    @Override
    public View[] initChildViews(AlienViewSettings settings) {
        return new View[0];
    }

    private void init() {
        initTimerMachine();
        startTimerMachine();
    }

    private void deinit() {
        stopTimerMachine();
    }

    private void initTimerListener() {

        timerListener = new TimerListener() {
            @Override
            public void onTick(boolean late) {

                processTimerEvent(late);
            }
        };
    }

    public TimerListener getTimerListener() {

        if (timerListener == null) {

            initTimerListener();
        }

        return timerListener;
    }

    private void initTimerMachine() {

        if (getView() == null) return;

        AlienTimer.TimeEventType eventType = AlienSettingsManager.get(getView().getContext()).getSettings().getTimerEventType();

        TimerSettings settings = new TimerSettings.Builder()
                .setHandler(new Handler())
                .setListener(getTimerListener())
                .setEventType(eventType)
                .build();

        alienTimer = new DefaultTimer(getView().getContext(), settings);

        startTimerMachine();
    }

    private void startTimerMachine() {

        if (alienTimer == null) {

            alienTimer.start();
        }
    }

    private void stopTimerMachine() {

        if (alienTimer == null) {
            alienTimer.stop();
            alienTimer = null;
        }
    }

    private void processTimerEvent(boolean late) {

    }

}
