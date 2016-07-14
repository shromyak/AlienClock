package com.svyat.sample.alienclock.content;

/**
 * Created by shromyak on 14.07.2016.
 *
 * Content brick that will be reflected with Android components
 * Main idea is that we have application like CMS, so we have dynamic
 * user-driven layout. These bricks allow us to save state into
 * the persistence.
 */
public interface AlienContentBrick {

    String getHostComponentClassName();

    String getControllerClassName();

    String getContainerViewIdName();

    String getLayoutIdName();

    String getTag();

    String[] getChildrenTags();

    String toJson();
}