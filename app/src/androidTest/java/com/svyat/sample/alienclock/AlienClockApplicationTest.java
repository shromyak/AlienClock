package com.svyat.sample.alienclock;

import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class AlienClockApplicationTest extends ApplicationTestCase<AlienClockApplication> {

    public AlienClockApplicationTest() {

        super(AlienClockApplication.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @SmallTest
    public void testApplication_1_Create() {
        assert getApplication() != null;
    }

}