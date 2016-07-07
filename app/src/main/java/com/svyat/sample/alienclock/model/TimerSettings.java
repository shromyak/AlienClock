package com.svyat.sample.alienclock.model;

import android.os.Handler;

import com.svyat.sample.alienclock.timer.DefaultTimer;
import com.svyat.sample.alienclock.timer.TimerListener;

/**
 * Created by shromyak on 08.07.2016.
 */
public class TimerSettings {

    private final DefaultTimer.TimeEventType eventType;
    private final Handler handler;
    private final TimerListener listener;

    private TimerSettings (Builder builder) {

        handler = builder.handler;
        eventType = builder.eventType;
        listener = builder.listener;
    }

    public DefaultTimer.TimeEventType getEventType() {
        return eventType;
    }

    public Handler getHandler() {
        return handler;
    }

    public TimerListener getListener() {
        return listener;
    }

    @Override
    public boolean equals(Object o) {

        if (o != null && o instanceof TimerSettings) {

            TimerSettings set1 = (TimerSettings) o;

            if (o == this
                    || (set1.listener == listener
                        && set1.handler == handler
                        && set1.eventType == eventType)) {

                return true;
            }
        }

        return false;
    }

    public static class Builder {

        private DefaultTimer.TimeEventType eventType;
        private Handler handler;
        private TimerListener listener;

        public Builder setEventType(DefaultTimer.TimeEventType eventType) {

            this.eventType = eventType;
            return this;
        }

        public Builder setListener(TimerListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder setHandler(Handler handler) {
            this.handler = handler;
            return this;
        }

        public TimerSettings build() {

            return new TimerSettings(this);
        }
    }
}
