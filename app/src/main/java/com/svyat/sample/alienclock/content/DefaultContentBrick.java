package com.svyat.sample.alienclock.content;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

/**
 * Created by shromyak on 18.07.2016.
 *
 * Default implementation of content brick (depends on persistence realization)
 *
 * Please refer to component brick interface description
 * for explanation
 *
 * @link AlienComponentBrick
 * and
 * @link DefaultContentScheme
 */
public class DefaultContentBrick implements AlienContentBrick {

    protected static Gson gson = new GsonBuilder().create();

    public static DefaultContentBrick fromJson(String gsonString) {
        return gson.fromJson(gsonString, DefaultContentBrick.class);
    }

    @Expose
    private String[] childrenTags;

    @Expose
    private String containerViewIdName;

    @Expose
    private String controllerClassName;

    @Expose
    private String hostComponentClassName;

    @Expose
    private String layoutIdName;

    @Expose
    private String tag;

    @Override
    public String[] getChildrenTags() {
        return childrenTags;
    }

    @Override
    public String getContainerViewIdName() {
        return containerViewIdName;
    }

    @Override
    public String getControllerClassName() {
        return controllerClassName;
    }

    public String getHostComponentClassName() {
        return hostComponentClassName;
    }

    @Override
    public String getLayoutIdName() {
        return layoutIdName;
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public String toJson() {
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return toJson();
    }

    protected DefaultContentBrick(Builder builder) {
        childrenTags = builder.childrenTags;
        containerViewIdName = builder.containerViewIdName;
        controllerClassName = builder.controllerClassName;
        hostComponentClassName = builder.hostComponentClassName;
        layoutIdName = builder.layoutIdName;
        tag = builder.tag;
    }

    public static class Builder {

        private String[] childrenTags;
        private String containerViewIdName;
        private String controllerClassName;
        private String hostComponentClassName;
        private String layoutIdName;
        private String tag;

        public Builder setChildrenTags(String[] childrenTags) {
            this.childrenTags = childrenTags;
            return this;
        }

        public Builder setContainerViewIdName(String containerViewIdName) {
            this.containerViewIdName = containerViewIdName;
            return this;
        }

        public Builder setControllerClassName(String controllerClassName) {
            this.controllerClassName = controllerClassName;
            return this;
        }

        public Builder setHostComponentClassName(String hostControllerClassName) {
            this.hostComponentClassName = hostControllerClassName;
            return this;
        }

        public Builder setLayoutIdName(String layoutIdName) {
            this.layoutIdName = layoutIdName;
            return this;
        }

        public Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public DefaultContentBrick build() {
            return new DefaultContentBrick(this);
        }
    }
}
