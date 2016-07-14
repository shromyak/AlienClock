package com.svyat.sample.alienclock.timer;

import android.support.annotation.NonNull;

import com.svyat.sample.alienclock.model.TimerSettings;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by shromyak on 09.07.16.
 *
 * interface to control predefined timer
 * Main idea was to publish trigger events
 * into application bus, but actually is
 * used in clock widget controller only
 * as "part of"
 */
public interface AlienTimer {

    void start();
    void stop();
    void reinit(TimerSettings settings);
    boolean isRunning();

    enum TimeEventType {

        SECOND,
        FIVESECOND,
        MINUTE,
        HOUR;

        public static @NonNull
        TimeEventType getCurrent() {

            Calendar cal = new GregorianCalendar();
            int sec = cal.get(Calendar.SECOND);
            int min = cal.get(Calendar.MINUTE);

            int fivesec = sec % 5;

            if ( sec == 0 && min == 0) {

                return HOUR;
            } else if (sec == 0) {

                return MINUTE;
            } else if (fivesec == 0) {

                return FIVESECOND;
            }

            return SECOND;
        }
    }
}
