package com.svyat.sample.alienclock.appearance;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.svyat.sample.alienclock.model.ShapeSettings;
import com.svyat.sample.alienclock.skin.AppearanceSkin;

/**
 * Created by shromyak on 07.07.2016.
 */
public class DefaultAppearanceDecorator implements AppearanceDecorator {

    private final Context context;
    private final AppearanceSkin appearanceTheme;

    public DefaultAppearanceDecorator(Context context, AppearanceSkin appearanceTheme) {
        this.context = context;
        this.appearanceTheme = appearanceTheme;
    }

    @Override
    public void drawShape(Canvas canvas, ShapeSettings settings) {

        final ShapeSettings.ShapeType shapeType = settings.getShapeType();

        switch (shapeType) {

            case SECONDS:

                Paint paintLine = appearanceTheme.getPaint(AppearanceSkin.PaintType.LINE);
                Paint paintText = appearanceTheme.getPaint(AppearanceSkin.PaintType.TEXT);

                canvas.drawArc(settings.getFrame(), 0, 360, false, paintLine);

                canvas.drawText("Hello", 0, paintText.getTextSize(), paintText);
                break;
            default:
        }

    }
}
