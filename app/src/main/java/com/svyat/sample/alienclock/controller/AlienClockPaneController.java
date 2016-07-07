package com.svyat.sample.alienclock.controller;

import android.content.Context;
import android.view.View;

import com.svyat.sample.alienclock.R;
import com.svyat.sample.alienclock.model.AlienViewSettings;
import com.svyat.sample.alienclock.widget.AlienViewGroup;

/**
 * Created by shromyak on 07.07.2016.
 */
public class AlienClockPaneController extends AlienViewController {

    public AlienClockPaneController(AlienViewGroup view) {
        super(view);
    }

    @Override
    public View[] initChildViews(AlienViewSettings settings) {

        Context context = getView().getContext();

        if (isQuadra(settings)) {
            return new View[] {
                    getAlienClockViewContainer(context, settings)
            };
        }

        return new View[] {
                getAlienClockViewContainer(context, settings),
                getAlienExpandView(context, settings)
        };
    }

    private View getAlienExpandView(Context context, AlienViewSettings settings) {

        View alienExpandView = new View(context);
        alienExpandView.setId(R.id.alien_expand_container);

        if (isPortrait(settings)) {

            alienExpandView.setMinimumWidth(settings.getMinDim());
            alienExpandView.setMinimumHeight(settings.getMaxDim() - settings.getMinDim() - 1);
            alienExpandView.setLeft(0);
            alienExpandView.setTop(settings.getMinDim() + 1);

        } else {

            alienExpandView.setMinimumHeight(settings.getMinDim());
            alienExpandView.setMinimumWidth(settings.getMaxDim() - settings.getMinDim() - 1);
            alienExpandView.setTop(0);
            alienExpandView.setLeft(settings.getMinDim() + 1);
        }

        return alienExpandView;
    }

    private View getAlienClockViewContainer(Context context, AlienViewSettings settings) {

        View alienClockView = new View(context);

        alienClockView.setId(R.id.alien_clock_container);
        alienClockView.setMinimumWidth(settings.getMinDim());
        alienClockView.setMinimumHeight(settings.getMinDim());
        alienClockView.setLeft(0);
        alienClockView.setTop(0);

        return alienClockView;
    }
}