package com.svyat.sample.alienclock.model;

import android.content.Context;
import android.view.LayoutInflater;

import com.svyat.sample.alienclock.content.AlienContentBrick;

/**
 * Created by shromyak on 20.07.2016.
 *
 * Stage 2 of instrumentation params marshalled to decorator
 */
public class DecoratorParams {

    private AlienContentBrick contentBrick;
    private DaemonParams daemonParams;
    private LayoutInflater layoutInflater;
    private int derivedLayoutId;
    private int derivedContainerViewId;

    public AlienContentBrick getContentBrick() {
        return contentBrick;
    }

    public DaemonParams getDaemonParams() {
        return daemonParams;
    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }

    public int getDerivedContainerViewId() {
        return derivedContainerViewId;
    }

    public int getDerivedLayoutId() {
        return derivedLayoutId;
    }

    private DecoratorParams(Builder builder) {
        contentBrick = builder.contentBrick;
        daemonParams = builder.daemonParams;
        layoutInflater = builder.layoutInflater;
        derivedContainerViewId = builder.derivedContainerViewId;
        derivedLayoutId = builder.derivedLayoutId;
    }

    public static class Builder {

        private AlienContentBrick contentBrick;
        private DaemonParams daemonParams;
        private LayoutInflater layoutInflater;
        private int derivedLayoutId;
        private int derivedContainerViewId;

        public Builder(){}

        public Builder (DecoratorParams params) {
            contentBrick = params.contentBrick;
            daemonParams = params.daemonParams;
            layoutInflater = params.layoutInflater;
        }

        public Builder setContentBrick(AlienContentBrick contentBrick) {
            this.contentBrick = contentBrick;
            return this;
        }

        public Builder setDaemonParams(DaemonParams daemonParams) {
            this.daemonParams = daemonParams;
            return this;
        }

        public Builder setLayoutInflater(LayoutInflater layoutInflater) {
            this.layoutInflater = layoutInflater;
            return this;
        }

        private void tryNormalize() {

            if (contentBrick != null && daemonParams != null && daemonParams.getContext() != null) {

                Context context = daemonParams.getContext();

                String name =  contentBrick.getLayoutIdName();
                derivedLayoutId = context.getResources().getIdentifier(name, "layout", context.getPackageName());

                name = contentBrick.getContainerViewIdName();
                derivedContainerViewId = context.getResources().getIdentifier(name, "id", context.getPackageName());
            }
        }

        public DecoratorParams build() {
            tryNormalize();
            return new DecoratorParams(this);
        }
    }
}
