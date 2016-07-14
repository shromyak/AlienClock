package com.svyat.sample.alienclock.alien;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.svyat.sample.alienclock.content.AlienContentBrick;
import com.svyat.sample.alienclock.content.AlienContentManager;
import com.svyat.sample.alienclock.content.DefaultContentManager;
import com.svyat.sample.alienclock.controller.AlienController;
import com.svyat.sample.alienclock.model.DaemonParams;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by shromyak on 20.07.2016.
 *
 * Implementation of visitor engine (see explanation for interface)
 * @link AlienIncubator
 *
 * Uses singleton creational pattern
 */
public class DefaultAlienIncubator implements AlienIncubator {

    public static final String TAG = DefaultAlienIncubator.class.getName();

    private  static DefaultAlienIncubator instance;

    public static synchronized DefaultAlienIncubator get(@NonNull Context context) {

        if (instance == null) {
            instance = new DefaultAlienIncubator(context.getApplicationContext());
        }

        return instance;
    }

    AlienContentManager alienContentManager;

    private final Context context;

    private DefaultAlienIncubator(Context context) {
        this.context = context;
    }

    private AlienContentManager getAlienContentManager() {
        if (alienContentManager == null) {
            alienContentManager = DefaultContentManager.get(context);
        }
        return alienContentManager;
    }

    /**
     * Main method used to initialize visitor with alien controller
     * (other words for alien injection)
     *
     * @param visitor -- target object to be charged with alien controller
     * @param daemonParams -- main daemons that are necessary to work with decorators
     *                     if omitted (null) would be created JIT (here)
     */
    @Override
    public void visit(@NonNull AlienControlled visitor, @Nullable DaemonParams daemonParams) {

        if (visitor instanceof AlienViewGroup &&((AlienViewGroup)visitor).hasAlienInside()) {
            return;
        }

        if (daemonParams == null) {
            daemonParams = getDaemonParams(visitor);
        }

        AlienController controller = getAlienController(visitor, daemonParams);
        if (controller == null) {
            return;
        }
        visitor.plantAlien(controller);

        if (visitor instanceof AlienActivity && visitor instanceof AppCompatActivity) {

            initActivitySubviews((Activity) visitor, controller, daemonParams);

        } else if (visitor instanceof AlienFragment && visitor instanceof Fragment) {

            initFragmentSubviews((Fragment) visitor, controller, daemonParams);

        }
    }

    private DaemonParams getDaemonParams(AlienControlled alienControlled) {

        if (alienControlled instanceof AppCompatActivity) {

            AppCompatActivity activity = (AppCompatActivity) alienControlled;
            return new DaemonParams.Builder()
                    .setClassLoader(activity.getClassLoader())
                    .setContext(activity)
                    .setFragmentManager(activity.getSupportFragmentManager())
                    .build();

        } else if (alienControlled instanceof Fragment) {

            Fragment fragment = (Fragment) alienControlled;
            return new DaemonParams.Builder()
                    .setClassLoader(fragment.getContext().getClassLoader())
                    .setContext(fragment.getContext())
                    .setFragmentManager(fragment.getChildFragmentManager())
                    .build();

        } else {
            throw new IllegalArgumentException("Can't plant alien without params");
        }
    }

    private void initActivitySubviews(Activity activity, AlienController controller, DaemonParams params) {

        View view = null;
        if (controller != null && controller.getContentBrick() != null) {
            String name = controller.getContentBrick().getContainerViewIdName();
            view = activity.findViewById(activity.getResources().getIdentifier(name, "id", activity.getPackageName()));
            controller.attachChildren(view);
        }

        if (view == null) {
            view = activity.findViewById(android.R.id.content);
        }

        if (view != null) {
            checkViewTree(view, params);
        }
    }

    private void initFragmentSubviews(Fragment fragment, AlienController controller, DaemonParams params) {

        if (fragment.getView() == null) {
            return;
        }

        if (controller != null && controller.getContentBrick() != null) {
            String name = controller.getContentBrick().getContainerViewIdName();
            View view = fragment.getView().findViewById(
                    fragment.getContext().getResources().getIdentifier(name, "id", fragment.getContext().getPackageName()));
            controller.attachChildren(view);
        }

        checkViewTree(fragment.getView(), params);
    }

    private AlienController getAlienController(AlienControlled visitor, DaemonParams settings) {

        AlienContentManager alienContentManager = getAlienContentManager();
        AlienContentBrick contentBrick = alienContentManager.getBrickWithTag(visitor.getClass().getName());
        String name = contentBrick.getControllerClassName();

        try {

            @SuppressWarnings("unchecked")
            Class<? extends AlienController> clazz = (Class<? extends AlienController>) context.getClassLoader().loadClass(name);
            Constructor<? extends AlienController> constructor = clazz.getConstructor(DaemonParams.class, AlienContentBrick.class);
            return constructor.newInstance(settings, contentBrick);

        } catch (ClassCastException e) {
            Log.e(TAG, "Wrong class", e);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "Can't load class", e);
        } catch (InstantiationException e) {
            Log.e(TAG, "Can't instantiate class", e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "Can't instantiate class (wrong constructor arguments)", e);
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "Can't fit constructor class", e);
        } catch (InvocationTargetException e) {
            Log.e(TAG, "Can't instantiate class (wrong invocation)", e);
        }

        return null;
    }

    private void checkViewTree(View view, DaemonParams daemonParams) {

        if (view instanceof ViewGroup) {

            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                checkViewTree(viewGroup.getChildAt(i), daemonParams);
            }
        }

        if (view instanceof AlienViewGroup) {
            visit((AlienViewGroup) view, daemonParams);
        }
    }
}
