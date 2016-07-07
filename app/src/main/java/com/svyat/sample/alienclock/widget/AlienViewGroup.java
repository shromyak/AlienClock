package com.svyat.sample.alienclock.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.svyat.sample.alienclock.appearance.AlienAppearanceManager;
import com.svyat.sample.alienclock.controller.AlienViewController;
import com.svyat.sample.alienclock.model.AlienViewSettings;

/**
 * Created by shromyak on 07.07.2016.
 */
public abstract class AlienViewGroup extends ViewGroup {

    private final static String LOG_TAG = AlienViewGroup.class.getSimpleName();

    private AlienViewController controller;

    private AlienAppearanceManager.ViewType viewType;

    protected int realHeiht = 100;
    protected int realWidth = 100;

    public AlienViewGroup(Context context) {
        super(context);
        init();
    }

    public AlienViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AlienViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public AlienViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        AlienViewSettings settings =
                new AlienViewSettings.Builder()
                        .setTop(top).setLeft(left).setRight(right).setBottom(bottom).buid();

        initChildren(settings);

        Log.d(LOG_TAG, "On Layout "
                + "\nchanged: " + changed
                + "\nleft: " + left
                + "\ntop: " + top
                + "\nright: " + right
                + "\nbottom: " + bottom
        );
    }

    private boolean hasChildrenInitialized = false;

    private void initChildren(AlienViewSettings settings) {

        View[] children = getController().initChildViews(settings);

        if (!hasChildrenInitialized) {

            for (View view : children) {
                addView(view);
            }

            hasChildrenInitialized = true;

        } else {

            for (View view : children) {

                View subview = findViewById(view.getId());
                if (subview == null) {

                    addView(view);
                } else {

                    if (!compareLayouts(subview, view)) {
                        removeView(subview);
                        addView(view);
                    }
                }
            }
        }
    }

    private boolean compareLayouts(View view1, View view2) {

        return view1.getTop() == view2.getTop()
                && view1.getLeft() == view2.getLeft()
                && view1.getMinimumHeight() == view2.getMinimumHeight()
                && view1.getMinimumWidth() == view2.getMinimumWidth();
    }

    protected void init() {
        initViewController();
        initViewType();
    }

    protected abstract void initViewType();

    protected void setViewType(AlienAppearanceManager.ViewType viewType) {

        this.viewType = viewType;
    }

    public AlienAppearanceManager.ViewType getViewType() {

        return viewType;
    }

    protected abstract void initViewController();

    protected void setViewController(AlienViewController controller) {

        this.controller = controller;
    }

    public AlienViewController getController() {

        return controller;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        realHeiht = getMeasuredHeight();
        realWidth = getMeasuredWidth();

        Log.d(LOG_TAG, "Width: " + realWidth + " Height:" + realHeiht);
    }

    protected String getLogTag() {
        return LOG_TAG;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        AlienAppearanceManager.get(getContext()).registerAlienView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        AlienAppearanceManager.get(getContext()).unregisterAlienView(this);
        super.onDetachedFromWindow();
    }
}
