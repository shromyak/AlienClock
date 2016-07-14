package com.svyat.sample.alienclock.skin;

import com.svyat.sample.alienclock.appearance.AppearanceDecorator;
import com.svyat.sample.alienclock.media.AudioDecorator;

/**
 * Created by shromyak on 07.07.2016.
 *
 * Skin is a theme, named as skin to prevent confusing with android themes
 * Actually contains two decorators charged with suitable skin (audio and appearance)
 * during instantiation
 * So we have 4 parameters: audio: 1)skin + 2)decorator; appearance: 3) skin + 4) decorator
 * Combination of this four elements gives us common look and feel
 */
public interface Skin {
    AppearanceDecorator getAppearance();
    AudioDecorator getAudio();
}
