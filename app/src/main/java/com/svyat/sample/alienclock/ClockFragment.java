package com.svyat.sample.alienclock;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.svyat.sample.alienclock.alien.AlienFragment;
import com.svyat.sample.alienclock.alien.AlienIncubator;
import com.svyat.sample.alienclock.alien.DefaultAlienIncubator;
import com.svyat.sample.alienclock.controller.AlienController;

/**
 * Created by shromyak on 07.07.2016.
 *
 * Main fragment (for double pane layout) manages main container area
 * in this certain case -- clock area
 */
public class ClockFragment extends Fragment implements AlienFragment {

    @SuppressWarnings("unused")
    private AlienController controller;
    private AlienIncubator alienIncubator;

    public ClockFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.clock, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAlien();
    }

    private AlienIncubator getAlienIncubator() {
        if (alienIncubator == null) {
            alienIncubator = DefaultAlienIncubator.get(getContext());
        }
        return alienIncubator;
    }

    private void initAlien() {
        getAlienIncubator().visit(this, null);
    }

    @Override
    public void plantAlien(AlienController controller) {
        this.controller = controller;
    }
}
