package com.svyat.sample.alienclock;

import android.content.Intent;
import android.test.ServiceTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.svyat.sample.alienclock.state.StateMachine;

import static com.svyat.sample.alienclock.common.Constants.ACTION_APP_START;

/**
 * Created by shromyak on 09.07.16.
 *
 * Is state machine is able to start
 */
public class StateMachineTest extends ServiceTestCase<StateMachine> {

    public StateMachineTest() {
        super(StateMachine.class);
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
    public void testStateMachine_1_Create() {
        Intent intent = new Intent(getContext(), StateMachine.class);
        intent.setAction(ACTION_APP_START);
        startService(intent);
    }
}
