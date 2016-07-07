package com.svyat.sample.alienclock.skin;

import android.graphics.Paint;

/**
 * Created by shromyak on 07.07.2016.
 */
public interface AppearanceSkin {

    Paint getPaint(PaintType paintType);

    enum PaintType {
        DEFAULT,
        LINE,
        TEXT,
        SHADOW,
        CLOCK
    }
}
