package com.svyat.sample.alienclock.appearance;

import android.graphics.Canvas;

import com.svyat.sample.alienclock.model.ShapeSettings;

/**
 * Created by shromyak on 07.07.2016.
 */
public interface AppearanceDecorator {

    void drawShape(Canvas canvas, ShapeSettings settings);

}
