package com.svyat.sample.alienclock.model;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;

import com.svyat.sample.alienclock.content.AlienContentBrick;
import com.svyat.sample.alienclock.content.AlienContentManager;

/**
 * Created by shromyak on 18.07.2016.
 *
 * First stage for decorator instrumentation palette
 */
public class DaemonParams {

    private ClassLoader classLoader;
    private Context context;
    private FragmentManager fragmentManager;

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public Context getContext() {
        return context;
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public Builder builder() {
        return new Builder(this);
    }

    private DaemonParams(Builder builder) {
        classLoader = builder.classLoader;
        context = builder.context;
        fragmentManager = builder.fragmentManager;
    }

    public static class Builder {

        private ClassLoader classLoader;
        private Context context;
        private FragmentManager fragmentManager;

        public Builder(){}

        private Builder (DaemonParams settings) {
            classLoader = settings.classLoader;
            context = settings.context;
            fragmentManager = settings.fragmentManager;
        }

        public Builder setClassLoader(ClassLoader classLoader) {
            this.classLoader = classLoader;
            return this;
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setFragmentManager(FragmentManager fragmentManager) {
            this.fragmentManager = fragmentManager;
            return this;
        }

        public DaemonParams build() {
            return new DaemonParams(this);
        }
    }
}
