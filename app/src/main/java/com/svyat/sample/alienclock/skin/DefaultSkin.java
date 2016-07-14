package com.svyat.sample.alienclock.skin;

import android.content.Context;

import com.svyat.sample.alienclock.appearance.AppearanceDecorator;
import com.svyat.sample.alienclock.appearance.DefaultAppearanceDecorator;
import com.svyat.sample.alienclock.media.AudioDecorator;
import com.svyat.sample.alienclock.media.DefaultAudioDecorator;

/**
 * Created by shromyak on 07.07.2016.
 *
 * Default preset of common skin
 */
public class DefaultSkin implements Skin {

    private final AudioDecorator audioDecorator;
    private final AppearanceDecorator appearanceDecorator;

    public DefaultSkin(Context context) {

        audioDecorator = new DefaultAudioDecorator(context, new DefaultAudioSkin());
        appearanceDecorator = new DefaultAppearanceDecorator(context, new DefaultAppearanceSkin(context));
    }

    @Override
    public AppearanceDecorator getAppearance() {
        return appearanceDecorator;
    }

    @Override
    public AudioDecorator getAudio() {
        return audioDecorator;
    }
}
