package com.svyat.sample.alienclock.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;

import com.svyat.sample.alienclock.appearance.AlienAppearanceManager;
import com.svyat.sample.alienclock.controller.AlienClockPaneController;

/**
 * Created by shromyak on 07.07.2016.
 */
public class AlienClockPane extends AlienViewGroup {

    private final static String LOG_TAG = AlienClockPane.class.getSimpleName();

    public AlienClockPane(Context context) {
        super(context);
    }

    public AlienClockPane(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlienClockPane(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public AlienClockPane(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init() {
        setWillNotDraw(true);
    }

    @Override
    protected void initViewType() {
        setViewType(AlienAppearanceManager.ViewType.UNMANAGED);
    }

    @Override
    protected void initViewController() {
        AlienClockPaneController controller = new AlienClockPaneController(this);
        setViewController(controller);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected String getLogTag() {
        return LOG_TAG;
    }
}
