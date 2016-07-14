package com.svyat.sample.alienclock;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

/**
 * Created by shromyak on 09.07.16.
 *
 * Is activity able to create
 */
public class DrawerActivityTest extends ActivityInstrumentationTestCase2<DrawerActivity> {

    public DrawerActivityTest() {

        super(DrawerActivity.class);
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
    public void testDrawerActivity_1_Create() {
        assert getActivity() != null;
    }
}
