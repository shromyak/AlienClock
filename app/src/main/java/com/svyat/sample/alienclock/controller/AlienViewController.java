package com.svyat.sample.alienclock.controller;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.svyat.sample.alienclock.model.AlienViewSettings;
import com.svyat.sample.alienclock.widget.AlienViewGroup;

import java.lang.ref.WeakReference;

/**
 * Created by shromyak on 07.07.2016.
 */
public abstract class AlienViewController {

    private final WeakReference<AlienViewGroup> viewRef;

    public AlienViewController(@NonNull AlienViewGroup view) {

        viewRef = new WeakReference<>(view);
    }

    public abstract @NonNull View[] initChildViews(@NonNull AlienViewSettings settings);

    protected @Nullable AlienViewGroup getView() {

        return viewRef.get();
    }

    protected boolean isQuadra(@NonNull AlienViewSettings settings) {

        return settings.getMinDim() == settings.getMaxDim();
    }

    protected boolean isPortrait(@NonNull AlienViewSettings settings) {

        return settings.getMinDim() == settings.getWidth();
    }
}