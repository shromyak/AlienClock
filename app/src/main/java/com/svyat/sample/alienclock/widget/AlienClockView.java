package com.svyat.sample.alienclock.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

import com.svyat.sample.alienclock.appearance.AlienAppearanceManager;
import com.svyat.sample.alienclock.appearance.AppearanceDecorator;
import com.svyat.sample.alienclock.controller.AlienClockViewController;
import com.svyat.sample.alienclock.model.ShapeSettings;
import com.svyat.sample.alienclock.skin.AlienSkinManager;

/**
 * Created by MAC on 06.07.16.
 */
public class AlienClockView extends AlienViewGroup {

    private final String LOG_TAG = AlienClockView.class.getSimpleName();

    private AppearanceDecorator appearanceDecorator;

    private RectF frame;

    public AlienClockView(Context context) {
        super(context);
    }

    public AlienClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlienClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public AlienClockView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void init() {
        setWillNotDraw(false);
        appearanceDecorator = AlienSkinManager.get(getContext()).getSkin().getAppearance();
    }

    @Override
    protected void initViewType() {
        setViewType(AlienAppearanceManager.ViewType.CLOCK);
    }

    @Override
    protected void initViewController() {
        AlienClockViewController controller = new AlienClockViewController(this);
        setViewController(controller);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        ShapeSettings settings = new ShapeSettings.Builder()
                .setShapeType(ShapeSettings.ShapeType.SECONDS).setFrame(frame).build();

        appearanceDecorator.drawShape(canvas, settings);

        Log.d(LOG_TAG, "Painted");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        frame = new RectF(0, 0, realWidth, realHeiht);
    }

    @Override
    protected String getLogTag() {
        return LOG_TAG;
    }
}
