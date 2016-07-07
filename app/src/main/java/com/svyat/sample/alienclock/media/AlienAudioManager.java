package com.svyat.sample.alienclock.media;

import android.content.Context;

/**
 * Created by shromyak on 07.07.2016.
 *
 * Responsibility of this class is to listen to events
 * to decorate audio surround
 */
public class AlienAudioManager {

    private static AlienAudioManager instance;

    /**
     * Static method to get singleton object
     * @param context -- context that will be converted into application context
     * @return AlienAudioManager singleton instance
     */
    public static synchronized AlienAudioManager get(Context context) {

        if (instance == null) {

            instance = new AlienAudioManager(context);
        }

        return instance;
    }

    public static synchronized void clear() {

        if (instance != null) {

            instance.deinit();
            instance = null;
        }
    }

    private final Context context;

    private boolean registered;

    private AlienAudioManager(Context context) {

        assert context != null;

        this.context = context.getApplicationContext();
        init();
    }

    private void init() {
        registerListeners();
    }

    private void deinit() {
        unregisterListeners();
    }

    private synchronized void registerListeners() {

    }

    private synchronized void unregisterListeners() {

    }
}