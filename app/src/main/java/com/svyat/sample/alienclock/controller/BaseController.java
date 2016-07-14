package com.svyat.sample.alienclock.controller;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.svyat.sample.alienclock.appearance.AppearanceDecorator;
import com.svyat.sample.alienclock.content.AlienContentBrick;
import com.svyat.sample.alienclock.model.DaemonParams;
import com.svyat.sample.alienclock.model.DecoratorParams;
import com.svyat.sample.alienclock.skin.AlienSkinManager;

/**
 * Created by shromyak on 20.07.2016.
 *
 * Default implementation of Alien controller,
 * contains all necessary functionality to be bridge
 * between components and aliens
 */
public class BaseController implements AlienController {

    private static final String TAG = BaseController.class.getSimpleName();

    private final DaemonParams daemonParams;
    private final AlienContentBrick contentBrick;

    private AppearanceDecorator appearanceDecorator;
    private DecoratorParams decoratorParams;

    protected BaseController(DaemonParams daemonParams, AlienContentBrick contentBrick) {

        this.daemonParams = daemonParams;
        this.contentBrick = contentBrick;

        appearanceDecorator = AlienSkinManager.get(daemonParams.getContext()).getSkin().getAppearance();
        decoratorParams = instantiateDecoratorDaemonParams();
    }

    @Override
    public void attachChildren(View container) {
        appearanceDecorator.attachChildren(container,  decoratorParams);
        Log.d(TAG, "Children attached");
    }

    @Nullable
    @Override
    public AlienContentBrick getContentBrick() {
        return contentBrick;
    }

    protected DecoratorParams getDecoratorParams() {
        return decoratorParams;
    }

    private DecoratorParams instantiateDecoratorDaemonParams() {

        return new DecoratorParams.Builder()
                .setDaemonParams(daemonParams)
                .setContentBrick(contentBrick)
                .setLayoutInflater((LayoutInflater) daemonParams.getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE ))
                .build();
    }

    protected Context getContext() {
        return daemonParams.getContext();
    }
}
