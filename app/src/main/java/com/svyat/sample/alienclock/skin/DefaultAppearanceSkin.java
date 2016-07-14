package com.svyat.sample.alienclock.skin;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;

import com.svyat.sample.alienclock.R;

/**
 * Created by shromyak on 07.07.2016.
 *
 * Default preset of appearance skin
 */
public class DefaultAppearanceSkin implements AppearanceSkin {

    private final Context context;

    public DefaultAppearanceSkin(Context context) {

        this.context = context;
    }

    @Override
    public Paint getPaint(PaintType paintType) {

        Paint paint = new Paint(0);
        switch (paintType) {

            case LINE1:

                initAsLineWithColor(paint, android.R.color.holo_blue_bright);
                return paint;

            case LINE2:

                initAsLineWithColor(paint, android.R.color.holo_blue_dark);
                return paint;

            case LINE3:

                initAsLineWithColor(paint, android.R.color.holo_green_dark);
                return paint;

            case LINE:

                initAsLineWithColor(paint, R.color.colorPrimary);
                return paint;

            case TEXT: {

                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                setColor(paint, R.color.colorAccent);
                paint.setStrokeWidth(2);
                paint.setTypeface(Typeface.MONOSPACE);
                return paint;
            }

            case SHADOW:

            case DEFAULT:
                return new Paint();

            default:
                return new Paint();

        }
    }

    private void initAsLineWithColor(Paint paint, int color) {

        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        setColor(paint, color);

        paint.setStrokeWidth(4);
    }

    private void setColor(Paint paint, int color) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            paint.setColor(context.getColor(color));

        } else {

            //noinspection deprecation
            paint.setColor(context.getResources().getColor(color));
        }
    }
}
