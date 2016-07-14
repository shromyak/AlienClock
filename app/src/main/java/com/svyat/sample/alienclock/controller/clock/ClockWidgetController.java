package com.svyat.sample.alienclock.controller.clock;

import android.os.Handler;
import android.util.Log;

import com.svyat.sample.alienclock.content.AlienContentBrick;
import com.svyat.sample.alienclock.controller.BaseController;
import com.svyat.sample.alienclock.model.DaemonParams;
import com.svyat.sample.alienclock.model.TimerSettings;
import com.svyat.sample.alienclock.settings.AlienSettingsManager;
import com.svyat.sample.alienclock.timer.AlienTimer;
import com.svyat.sample.alienclock.timer.DefaultTimer;
import com.svyat.sample.alienclock.timer.TimerListener;

/**
 * Created by shromyak on 20.07.2016.
 *
 * This is a stub that created
 * for custom integration with decorators (based on timer engine)
 *
 * Main integration is in parent
 * @link BaseController
 */
public class ClockWidgetController extends BaseController {

    private final String LOG_TAG = ClockWidgetController.class.getSimpleName();

    private AlienTimer alienTimer;

    private TimerListener timerListener;

    public ClockWidgetController(DaemonParams daemonParams, AlienContentBrick alienContentBrick) {
        super(daemonParams, alienContentBrick);
        Log.d(LOG_TAG, "Instantiated");
    }

    private void init() {
        initTimerMachine();
        startTimerMachine();
    }

    private void deinit() {
        stopTimerMachine();
    }

    //Internal timer listener
    private void initTimerListener() {
        timerListener = new TimerListener() {
            @Override
            public void onTick() {
                processTimerEvent();
            }
        };
    }

    //External timer listener
    public void setTimerListener(TimerListener timerListener) {
        this.timerListener = timerListener;
    }

    public TimerListener getTimerListener() {
        if (timerListener == null) {
            initTimerListener();
        }
        return timerListener;
    }

    private void initTimerMachine() {

        if (getContext() == null) return;

        AlienTimer.TimeEventType eventType = AlienSettingsManager.get(getContext()).getSettings().getTimerEventType();

        TimerSettings settings = new TimerSettings.Builder()
                .setHandler(new Handler())
                .setListener(getTimerListener())
                .setEventType(eventType)
                .build();

        alienTimer = new DefaultTimer(getContext(), settings);

        startTimerMachine();
    }

    private void startTimerMachine() {

        if (alienTimer != null) {

            alienTimer.start();
        }
    }

    private void stopTimerMachine() {

        if (alienTimer != null) {
            alienTimer.stop();
            alienTimer = null;
        }
    }

    private void processTimerEvent() {
    }

//    @Override
//    public void decorate(Canvas canvas) {
//        super.decorate(canvas);
//
//        ShapeSettings settings = new ShapeSettings.Builder()
//                .setShapeType(ShapeSettings.ShapeType.ARC1)
//                .setFrame(getFrame())
//                .build();
//
//        appearanceDecorator.drawShape(canvas, settings);
//
//        Log.d(getLogTag(), "Painted");
//    }
}
