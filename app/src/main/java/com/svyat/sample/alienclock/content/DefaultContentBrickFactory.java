package com.svyat.sample.alienclock.content;

import com.svyat.sample.alienclock.BloomchanFragment;
import com.svyat.sample.alienclock.ClockFragment;
import com.svyat.sample.alienclock.DrawerActivity;
import com.svyat.sample.alienclock.controller.bloomchan.BloomchanController;
import com.svyat.sample.alienclock.controller.clock.ClockController;
import com.svyat.sample.alienclock.controller.clock.ClockWidgetController;
import com.svyat.sample.alienclock.controller.content.DoublePaneController;
import com.svyat.sample.alienclock.widget.clock.ClockWidget;

/**
 * Created by shromyak on 20.07.2016.
 *
 * Helper factory that used to build content bricks
 *
 * Factory creational pattern is used at the moment
 * For better flexibility need to be refactored into
 * AbstractFactory, that will allow us to make correction
 * to predefined bricks
 */
public final class DefaultContentBrickFactory {

    private DefaultContentBrickFactory() {}

    private static final String bloomchanTag = BloomchanFragment.class.getName();

    public static DefaultContentBrick getBloomchanBrick() {

        return new DefaultContentBrick.Builder()
                .setChildrenTags(new String[0])
                .setContainerViewIdName("double_expand_container")
                .setControllerClassName(BloomchanController.class.getName())
                .setHostComponentClassName(bloomchanTag)
                .setLayoutIdName("android.R.layout.list_content")
                .setTag(bloomchanTag)
                .build();
    }

    private static final String clockTag = ClockFragment.class.getName();

    public static DefaultContentBrick getClockBrick() {

        return new DefaultContentBrick.Builder()
                .setChildrenTags(new String[0])
                .setContainerViewIdName("double_main_container")
                .setControllerClassName(ClockController.class.getName())
                .setHostComponentClassName(clockTag)
                .setLayoutIdName("")
                .setTag(clockTag)
                .build();
    }
    //It's orphan brick -- initialized by layout directly
    private static final String clockWidgetTag = ClockWidget.class.getName();

    public static DefaultContentBrick getClockWidgetBrick() {

        return new DefaultContentBrick.Builder()
                .setChildrenTags(new String[0])
                .setContainerViewIdName("clock")
                .setControllerClassName(ClockWidgetController.class.getName())
                .setHostComponentClassName(clockWidgetTag)
                .setLayoutIdName("clock")
                .setTag(clockWidgetTag)
                .build();
    }

    private static final String doublePaneTag = DrawerActivity.class.getName();

    public static DefaultContentBrick getDoublePaneBrick() {

        return new DefaultContentBrick.Builder()
                .setChildrenTags(new String[]{clockTag, bloomchanTag})
                .setContainerViewIdName("content_pane")
                .setControllerClassName(DoublePaneController.class.getName())
                .setHostComponentClassName(doublePaneTag)
                .setLayoutIdName("double_pane")
                .setTag(doublePaneTag)
                .build();
    }
}
