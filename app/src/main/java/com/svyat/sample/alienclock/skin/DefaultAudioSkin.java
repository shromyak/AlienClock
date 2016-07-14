package com.svyat.sample.alienclock.skin;

import android.media.AudioManager;

import com.svyat.sample.alienclock.media.AudioDecorator;

/**
 * Created by shromyak on 07.07.2016.
 *
 * Default preset of audio skin
 */
public class DefaultAudioSkin implements AudioSkin {

    public int getSound(final AudioDecorator.SoundType type) {
        switch (type) {
            case TICK:
            default:
                return AudioManager.FX_KEYPRESS_STANDARD;
        }
    }

}
