package com.svyat.sample.alienclock.controller;

import android.support.annotation.Nullable;
import android.view.View;

import com.svyat.sample.alienclock.content.AlienContentBrick;

/**
 * Created by shromyak on 14.07.2016.
 *
 * As for now is used to add children but will be extended soon
 * Main idea is that Alien Controller is bridge between Android Components
 * and Alien subsystem such as decorators
 */
public interface AlienController {

    void attachChildren(View container);

    @Nullable
    AlienContentBrick getContentBrick();
}
