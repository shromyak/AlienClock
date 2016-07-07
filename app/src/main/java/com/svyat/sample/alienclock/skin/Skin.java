package com.svyat.sample.alienclock.skin;

import com.svyat.sample.alienclock.appearance.AppearanceDecorator;
import com.svyat.sample.alienclock.media.AudioDecorator;

/**
 * Created by shromyak on 07.07.2016.
 */
public interface Skin {
    AppearanceDecorator getAppearance();
    AudioDecorator getAudio();
}
