package com.svyat.sample.alienclock.appearance;

import android.content.Context;
import android.support.annotation.NonNull;

import com.svyat.sample.alienclock.widget.AlienViewGroup;

import java.util.HashSet;

/**
 * Created by shromyak on 07.07.2016.
 *
 * Responsibility of this class is to listen to events
 * to manage AlienViewGroup instances appearance
 */
public class AlienAppearanceManager {

    private static AlienAppearanceManager instance;

    public synchronized static AlienAppearanceManager get(Context context) {

        if (instance == null) {
            instance = new AlienAppearanceManager(context);
        }

        return instance;
    }

    public synchronized static void clear() {

        if (instance != null) {

            instance.deinit();
            instance = null;
        }
    }

    private final HashSet<AlienViewGroup> managedViews = new HashSet<>();

    private final Context context;

    private boolean registered;

    private AlienAppearanceManager(Context context) {

        assert context != null;

        this.context = context.getApplicationContext();
        init();
    }

    private void init() {
        registerListeners();
    }

    private void deinit() {
        unregisterListeners();
        unregisterManagedViews();
    }

    private void registerListeners() {

    }

    private void unregisterListeners() {

    }

    private void unregisterManagedViews() {

        synchronized (managedViews) {

            managedViews.clear();
        }
    }

    public void registerAlienView(@NonNull AlienViewGroup view) {

        if (view.getViewType() != ViewType.UNMANAGED) {

            synchronized (managedViews) {

                managedViews.add(view);
            }
        }
    }

    public void unregisterAlienView(@NonNull AlienViewGroup view) {

        synchronized (managedViews) {

            managedViews.remove(view);
        }
    }

    public enum ViewType {
        UNMANAGED,
        CLOCK,
        LIST
    }
}
