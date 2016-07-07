package com.svyat.sample.alienclock.media;

/**
 * Created by shromyak on 07.07.2016.
 */
public interface AudioDecorator {

    void playSound(SoundType type);

    enum SoundType {
        TICK
    }
}
