package com.svyat.sample.alienclock.widget.clock;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.svyat.sample.alienclock.appearance.AppearanceDecorator;
import com.svyat.sample.alienclock.controller.AlienController;
import com.svyat.sample.alienclock.model.ShapeParams;
import com.svyat.sample.alienclock.skin.AlienSkinManager;
import com.svyat.sample.alienclock.alien.AlienViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by shromyak on 06.07.16.
 *
 * AlienControlled widget without main component
 */
public class ClockWidget extends ViewGroup implements AlienViewGroup {

    private final static String LOG_TAG = ClockWidget.class.getSimpleName();

    private final int SECOND_OFFSET  = 50;
    private final int MINUTES_OFFSET  = 125;
    private final int HOURS_OFFSET  = 200;

    private final GregorianCalendar calendar = new GregorianCalendar();

    private AppearanceDecorator appearanceDecorator;

    private AlienController controller;

    private final List<ShapeParams> settings = new ArrayList<>();

    public ClockWidget(Context context) {
        super(context);
        init();
    }

    public ClockWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClockWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public ClockWidget(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        handler.postDelayed(runnable, 1000L);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacks(runnable);
    }

    private final Handler handler = new Handler();
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            ClockWidget.this.invalidate();
            handler.postDelayed(runnable, 1000L);
        }
    };

    private void init() {
        setWillNotDraw(false);
        initAlien();
    }

    protected void initAlien() {
        appearanceDecorator = AlienSkinManager.get(getContext()).getSkin().getAppearance();
        //alien controller should be injected by parent in this case
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        int min = Math.min(width, height);
        setMeasuredDimension(min, min);
        initShapes();
    }

    boolean isAdded = false;
    private void initShapes() {

        if (isAdded) {
            return;
        }
        isAdded = true;

        RectF rectF;
        Rect rect0 = new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight());
        Rect rect = new Rect(rect0);

        rect.inset(SECOND_OFFSET, SECOND_OFFSET);
        rectF = new RectF(rect.left, rect.top, rect.right, rect.bottom);

        ShapeParams sett = new ShapeParams.Builder()
                .setShapeType(ShapeParams.ShapeType.ARC1)
                .setFrame(rectF)
                .build();
        settings.add(sett);

        rect = new Rect(rect0);
        rect.inset(MINUTES_OFFSET, MINUTES_OFFSET);
        rectF = new RectF(rect.left, rect.top, rect.right, rect.bottom);

        sett = new ShapeParams.Builder()
                .setShapeType(ShapeParams.ShapeType.ARC2)
                .setFrame(rectF)
                .build();
        settings.add(sett);

        rect = new Rect(rect0);
        rect.inset(HOURS_OFFSET, HOURS_OFFSET);
        rectF = new RectF(rect.left, rect.top, rect.right, rect.bottom);

        sett = new ShapeParams.Builder()
                .setShapeType(ShapeParams.ShapeType.ARC3)
                .setFrame(rectF)
                .build();
        settings.add(sett);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        calendar.setTimeInMillis(System.currentTimeMillis());
        for (ShapeParams sett: settings) {
            resetValue(sett);
            appearanceDecorator.drawShape(canvas, sett);
        }
    }

    private void resetValue(ShapeParams shapeSettings) {

        switch (shapeSettings.getShapeType()) {
            case ARC1:
                shapeSettings.setIntValue(calendar.get(Calendar.SECOND));
                break;
            case ARC2:
                shapeSettings.setIntValue(calendar.get(Calendar.MINUTE));
                break;
            case ARC3:
                shapeSettings.setIntValue(calendar.get(Calendar.HOUR));
                break;
        }
    }

    @Override
    public void plantAlien(AlienController controller) {
        this.controller = controller;
    }

    @Override
    public boolean hasAlienInside() {
        return controller != null;
    }
}
