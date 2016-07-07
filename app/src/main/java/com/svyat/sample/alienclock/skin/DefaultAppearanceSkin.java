package com.svyat.sample.alienclock.skin;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;

import com.svyat.sample.alienclock.R;

/**
 * Created by shromyak on 07.07.2016.
 */
public class DefaultAppearanceSkin implements AppearanceSkin {

    private final Context context;

    public DefaultAppearanceSkin(Context context) {

        this.context = context;
    }

    @Override
    public Paint getPaint(PaintType paintType) {
        switch (paintType) {

            case LINE: {
                Paint paint = new Paint(0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    paint.setColor(context.getColor(R.color.colorPrimary));
                } else {
                    //noinspection deprecation
                    paint.setColor(context.getResources().getColor(R.color.colorPrimary));
                }
                paint.setStrokeWidth(2);
                return paint;
            }

            case TEXT: {
                Paint paint = new Paint(0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    paint.setColor(context.getColor(R.color.colorAccent));
                } else {
                    //noinspection deprecation
                    paint.setColor(context.getResources().getColor(R.color.colorAccent));
                }
                paint.setStrokeWidth(2);
                paint.setTypeface(Typeface.MONOSPACE);
                return paint;
            }

            case SHADOW:

            case CLOCK:

            case DEFAULT:
                return new Paint();

            default:
                return new Paint();

        }
    }
}
