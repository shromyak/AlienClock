package com.svyat.sample.alienclock.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.svyat.sample.alienclock.model.ApplicationSettings;

/**
 * Created by shromyak on 08.07.2016.
 *
 * Responsibility of this class is to manage Application settings
 */
public class AlienSettingsManager {

    private final static String sharedPrefsFile = "alien_settings";

    private static AlienSettingsManager instance;

    public synchronized static AlienSettingsManager get(Context context) {

        if (instance == null) {
            instance = new AlienSettingsManager(context);
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

    private ApplicationSettings settings;

    private AlienSettingsManager(Context context) {

        assert context != null;

        this.context = context.getApplicationContext();
        init();
    }

    private void init() {
    }

    public void deinit() {
    }

    public boolean isEmpty() {
        return settings == null;
    }

    public @NonNull ApplicationSettings getSettings() {

        if (settings == null) {
            settings = readOrInitSettings();
        }

        return settings;
    }

    /**
     * This is used to set end write new settings
     * @param settings -- new settings
     *                 null forces clear persistence please use it carefully
     */
    public void setSettings(@Nullable ApplicationSettings settings) {

        if (settings == null) {
            this.settings = settings;
            clearPersistence();
            return;
        }

        if (!settings.equals(this.settings)) {
            this.settings = settings;
            storeToPersistence();
        }
    }

    private @NonNull ApplicationSettings readOrInitSettings() {

        return readFromPersistence();
    }

    //Persistence

    private void clearPersistence() {

        context.getSharedPreferences(sharedPrefsFile, Context.MODE_PRIVATE).edit().clear().apply();
    }

    private void storeToPersistence() {

        SharedPreferences prefs = context.getSharedPreferences(sharedPrefsFile, Context.MODE_PRIVATE);
        getSettings().storeToPreferences(prefs);

    }

    private @NonNull ApplicationSettings readFromPersistence() {

        return new ApplicationSettings.Builder()
                .readFromPreferences(context.getSharedPreferences(sharedPrefsFile, Context.MODE_PRIVATE))
                .build();
    }
}