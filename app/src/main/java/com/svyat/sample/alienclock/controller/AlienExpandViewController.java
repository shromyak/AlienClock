package com.svyat.sample.alienclock.controller;

import android.view.View;

import com.svyat.sample.alienclock.model.AlienViewSettings;
import com.svyat.sample.alienclock.widget.AlienViewGroup;

/**
 * Created by shromyak on 07.07.2016.
 */
public class AlienExpandViewController extends AlienViewController {

    public AlienExpandViewController(AlienViewGroup view) {
        super(view);
    }

    @Override
    public View[] initChildViews(AlienViewSettings settings) {
        return new View[0];
    }
}
