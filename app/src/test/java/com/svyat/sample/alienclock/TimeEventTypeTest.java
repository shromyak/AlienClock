package com.svyat.sample.alienclock;

import com.svyat.sample.alienclock.timer.AlienTimer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by shromyak on 09.07.16.
 *
 * Test current timer event type instantiation
 */
public class TimeEventTypeTest {

    @Test
    public void currentTimeEventType_isCorrect() throws Exception {

        AlienTimer.TimeEventType type1 = AlienTimer.TimeEventType.getCurrent();
        AlienTimer.TimeEventType type2 = AlienTimer.TimeEventType.getCurrent();

        assertEquals(type1.ordinal(), type2.ordinal());
    }
}
