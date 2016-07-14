package com.svyat.sample.alienclock.content;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.svyat.sample.alienclock.controller.AlienController;
import com.svyat.sample.alienclock.settings.AlienSettingsManager;

import java.util.HashSet;

/**
 * Created by shromyak on 14.07.2016.
 *
 * This certain implementation of content brick persistence is based on Shared Preferences
 * You can make your own implementation based on Content Provider, for example
 *
 * The main idea is that components don't know what exact content should be shown on the screen
 * Control upon this behaviour is delegated to this controller
 *
 * Please refer to component brick interface description
 * for extra explanation
 *
 * @link AlienContentBrick
 * and
 * @link DefaultContentScheme
 */
public class DefaultContentManager implements AlienContentManager {

    private static DefaultContentManager instance;

    private final static String PREFS_FILE_PREFIX = "alien_content_scheme";

    private String sharedPrefsSchemeFile = PREFS_FILE_PREFIX;

    private boolean needClearStore = false;

    public synchronized static DefaultContentManager get(Context context) {

        if (instance == null) {
            instance = new DefaultContentManager(context);
        }

        return instance;
    }

    public synchronized static void clear() {

        if (instance != null) {

            instance.deinit();
            instance = null;
        }
    }

    private final HashSet<AlienController> managedControllers = new HashSet<>();

    private final Context context;

    private DefaultContentManager(Context context) {

        assert context != null;

        this.context = context.getApplicationContext();
        init();
    }

    private void init() {
        initStore();
    }

    private void deinit() {
        unregisterManagedControllers();
    }

    private void initStore() {

        String suffix = getRootTag();

        if (!TextUtils.isEmpty(suffix)) {

            sharedPrefsSchemeFile = PREFS_FILE_PREFIX + "_" + suffix.toLowerCase().replace('.', '_');

            if (sharedPrefsSchemeFile.length() > 127) {

                sharedPrefsSchemeFile = sharedPrefsSchemeFile.substring(0, 127);
            }
        }
    }

    private void unregisterManagedControllers() {

        synchronized (managedControllers) {

            managedControllers.clear();
        }
    }

    @Override
    public void reinitScheme() {
        DefaultContentScheme scheme = new DefaultContentScheme(context);
        boolean save = needClearStore;
        needClearStore = true;
        scheme.initDefault();
        needClearStore = save;
    }

    @Override
    public void storeBrick(AlienContentBrick brick) {
        context.getSharedPreferences(sharedPrefsSchemeFile, Context.MODE_PRIVATE)
                .edit()
                .putString(brick.getTag(), brick.toJson())
                .apply();
    }

    @Override
    public AlienContentBrick getBrickWithTag(String tag) {
        SharedPreferences prefs = context.getSharedPreferences(sharedPrefsSchemeFile, Context.MODE_PRIVATE);
        if (prefs.contains(tag)) {
            String brickAsJson = prefs.getString(tag, "");
            return DefaultContentBrick.fromJson(brickAsJson);
        }
        return null;
    }

    @Override
    public String getRootTag() {
        AlienSettingsManager manager = AlienSettingsManager.get(context);
        return manager.getSettings().getContentRootTag();
    }

    @Override
    public void setRootTag(String tag) {

        AlienSettingsManager manager = AlienSettingsManager.get(context);
        manager.setSettings(manager.getSettings().setContentRootTag(tag));

        initStore();

        if (needClearStore) {
            context.getSharedPreferences(sharedPrefsSchemeFile, Context.MODE_PRIVATE).edit().clear().apply();
        }
    }

    @Override
    public void reinitWithRootTag(String tag) {
        boolean save = needClearStore;
        needClearStore = true;
        setRootTag(tag);
        needClearStore = save;
    }
}
