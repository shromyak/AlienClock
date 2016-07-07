package com.svyat.sample.alienclock.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;

import com.svyat.sample.alienclock.appearance.AlienAppearanceManager;
import com.svyat.sample.alienclock.controller.AlienExpandViewController;

/**
 * Created by shromyak on 07.07.2016.
 */
public class AlienExpandView extends AlienViewGroup {

    private final static String LOG_TAG = AlienExpandView.class.getSimpleName();

    public AlienExpandView(Context context) {
        super(context);
    }

    public AlienExpandView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlienExpandView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public AlienExpandView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init() {
        setWillNotDraw(true);
    }

    @Override
    protected void initViewType() {
        setViewType(AlienAppearanceManager.ViewType.LIST);
    }

    @Override
    protected void initViewController() {
        AlienExpandViewController controller = new AlienExpandViewController(this);
        setViewController(controller);
    }

    @Override
    protected String getLogTag() {
        return LOG_TAG;
    }
}