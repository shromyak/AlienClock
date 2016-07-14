package com.svyat.sample.alienclock.appearance;

import android.graphics.Canvas;
import android.view.View;

import com.svyat.sample.alienclock.model.DecoratorParams;
import com.svyat.sample.alienclock.model.ShapeParams;

/**
 * Created by shromyak on 07.07.2016.
 *
 * Is responsible for decoration of controlled components
 * Main idea: there is some common theme (appearance skin)
 * We use decorator to allow custom components "to be on the same page"
 */
public interface AppearanceDecorator {

    void drawShape(Canvas canvas, ShapeParams settings);
    void attachChildren(View view, DecoratorParams params);
}
