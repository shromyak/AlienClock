package com.svyat.sample.alienclock.skin;

import android.content.Context;
import android.content.Intent;

import com.svyat.sample.alienclock.media.AlienAudioManager;

import static com.svyat.sample.alienclock.common.Constants.ACTION_EVENT_THEME_CHANGED;
import static com.svyat.sample.alienclock.common.Constants.KEY_THEME_TYPE;

/**
 * Created by shromyak on 07.07.2016.
 */
public class AlienSkinManager {

    private static AlienSkinManager instance;

    public synchronized static AlienSkinManager get(Context context) {

        assert context != null;

        if (instance == null) {
            instance = new AlienSkinManager(context);
        }

        return instance;
    }

    public synchronized static void clear() {

        if (instance != null) {

            instance.deinit();
            instance = null;
        }
    }

    private final Context context;

    private Skin skin;

    private AlienAudioManager alienAudioManager;

    private AlienSkinManager(Context context) {
        this.context = context.getApplicationContext();
        init();
    }

    private void deinit() {

    }

    private void init() {

        final ThemeType themeType = getPreferableTheme();

        switch (themeType) {
            case DEFAULT:
            default:
                this.skin = new DefaultSkin(context);
        }
    }

    public Skin getSkin() {
        return skin;
    }

    private void onThemeChanged(ThemeType type) {
        Intent intent = new Intent(ACTION_EVENT_THEME_CHANGED);
        intent.setPackage(context.getPackageName());
        intent.putExtra(KEY_THEME_TYPE, type.name());
    }

    //TODO: Get from shared preferences
    private ThemeType getPreferableTheme() {
        return ThemeType.DEFAULT;
    }

    enum ThemeType {
        DEFAULT
    }
}
