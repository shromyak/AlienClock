package com.svyat.sample.alienclock.media;

import android.content.Context;
import android.media.AudioManager;

import com.svyat.sample.alienclock.skin.AudioSkin;

/**
 * Created by shromyak on 07.07.2016.
 *
 * Default Implementation of audio decorator based on AudioManager
 *
 * For additional explanation please refer
 * @link AudioDecorator
 */
public class DefaultAudioDecorator implements AudioDecorator {

    private final Context context;
    private final AudioSkin audioTheme;

    public DefaultAudioDecorator (Context context, AudioSkin audioTheme) {
        this.context = context;
        this.audioTheme = audioTheme;
    }

    @Override
    public void playSound(SoundType type) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.playSoundEffect(audioTheme.getSound(type));
    }
}