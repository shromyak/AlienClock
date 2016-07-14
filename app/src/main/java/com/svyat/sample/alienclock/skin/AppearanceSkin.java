package com.svyat.sample.alienclock.skin;

import android.graphics.Paint;

/**
 * Created by shromyak on 07.07.2016.
 *
 * interface to communicate with appearance skin
 */
public interface AppearanceSkin {

    Paint getPaint(PaintType paintType);

    enum PaintType {
        DEFAULT,
        LINE,
        LINE1,
        LINE2,
        LINE3,
        TEXT,
        SHADOW
    }
}
