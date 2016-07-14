package com.svyat.sample.alienclock.media;

/**
 * Created by shromyak on 07.07.2016.
 *
 * Interface for Audio Decorator
 * Main idea is to use AudioManager not directly (in components) but with
 * help of AlienController, so theme will be more common in this case
 * Implementation is postponed at the moment
 */
public interface AudioDecorator {

    void playSound(SoundType type);

    enum SoundType {
        //very short and quiet sound (50-100ms duration)
        TICK
    }
}
