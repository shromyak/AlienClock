package com.svyat.sample.alienclock.timer;

import android.content.Context;
import android.os.Handler;

import com.svyat.sample.alienclock.model.TimerSettings;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by shromyak on 07.07.16.
 *
 * Default implementation of AlienTimer
 * @link AlienTimer
 *
 * This implemetation uses delegate pattern
 * @link TimerListener
 */
public class DefaultTimer implements AlienTimer {

    private static String LOG_TAG = DefaultTimer.class.getSimpleName();

    private final Context context;

    private final AtomicBoolean stop;

    private Handler handler;

    private TimerListener listener;

    private TimeEventType eventType;

    private int normalizedInterval = 1;

    public DefaultTimer(Context context, TimerSettings settings) {
        this.context = context.getApplicationContext();
        stop = new AtomicBoolean(false);
        init(settings);
    }

    public void reinit(TimerSettings settings) {
        stop();
        init(settings);
    }

    private void init(TimerSettings settings) {

        handler = settings.getHandler();
        listener = settings.getListener();
        eventType = settings.getEventType();

        assert  handler != null;
        assert  listener != null;
        assert  eventType != null;

        normalizeInterval();
    }

    public boolean isRunning() {

        return !stop.get();
    }

    private long getNext() {

        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.MILLISECOND, 0);

        int sec = normalizedInterval;

        calendar.add(Calendar.SECOND, sec);

        return calendar.getTimeInMillis();
    }

    private void scheduleNextFire() {

        long next = getNext();

        handler.postAtTime(new Runnable() {

            @Override
            public void run() {
                if (!stop.get()) {
                    processFireEvent();
                    scheduleNextFire();
                }
            }

        }, this, next);
    }

    private void processFireEvent() {

        listener.onTick();
    }

    public void start() {

        handler.post(new Runnable() {

            @Override
            public void run() {

                scheduleNextFire();
            }
        });
    }

    private void normalizeInterval() {

        int interval = 1;

        switch (eventType) {

            case FIVESECOND:
                interval = 5;
                break;

            case MINUTE:
                interval = 60;
                break;

            case HOUR:
                interval = 3600;
                break;
        }

        normalizedInterval = interval;
    }

    public void stop() {

        handler.removeCallbacksAndMessages(this);
        stop.set(true);
    }
}
