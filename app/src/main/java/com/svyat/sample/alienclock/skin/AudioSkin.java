package com.svyat.sample.alienclock.skin;

import com.svyat.sample.alienclock.media.AudioDecorator;

/**
 * Created by shromyak on 07.07.2016.
 *
 * interface to communicate with audio skin
 */
public interface AudioSkin {
    int getSound(final AudioDecorator.SoundType type);
}
