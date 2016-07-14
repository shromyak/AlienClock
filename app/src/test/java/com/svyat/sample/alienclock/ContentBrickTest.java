package com.svyat.sample.alienclock;

import com.svyat.sample.alienclock.content.DefaultContentBrick;
import com.svyat.sample.alienclock.content.DefaultContentBrickFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by shromyak on 18.07.2016.
 */
@RunWith(PowerMockRunner.class)
public class ContentBrickTest {

    @Before
    public void setup(){
    }

    public void check(DefaultContentBrick brick2, DefaultContentBrick brick) {
        assertArrayEquals(brick2.getChildrenTags(), brick.getChildrenTags());
        assertEquals(brick2.getContainerViewIdName(), brick.getContainerViewIdName());
        assertTrue(brick2.getHostComponentClassName().equals(brick.getHostComponentClassName()));
        assertEquals(brick2.getLayoutIdName(), brick.getLayoutIdName());
    }

    @Test
    public void gsonParseDefaultPaneBrick_isCorrect() throws Exception {

        DefaultContentBrick brick = new DefaultContentBrick.Builder()
                .setChildrenTags(new String[]{"1", "2"})
                .setContainerViewIdName("name1")
                .setHostComponentClassName("class1")
                .setLayoutIdName("name2")
                .build();

        String gsonString = brick.toJson();

        DefaultContentBrick brick2 = DefaultContentBrick.fromJson(gsonString);

        check(brick2, brick);
    }

    @Test
    public void gsonParseDoublePaneBrick_isCorrect() throws Exception {

        DefaultContentBrick brick = DefaultContentBrickFactory.getDoublePaneBrick();

        String gsonString = brick.toJson();

        DefaultContentBrick brick2 = DefaultContentBrick.fromJson(gsonString);

        check(brick2, brick);
    }

    @Test
    public void gsonParseClockContentBrick_isCorrect() throws Exception {

        DefaultContentBrick brick = DefaultContentBrickFactory.getClockBrick();

        String gsonString = brick.toJson();

        DefaultContentBrick brick2 = DefaultContentBrick.fromJson(gsonString);

        check(brick2, brick);
    }

    @Test
    public void gsonParseBloomchanContentBrick_isCorrect() throws Exception {

        DefaultContentBrick brick = DefaultContentBrickFactory.getBloomchanBrick();

        String gsonString = brick.toJson();

        DefaultContentBrick brick2 = DefaultContentBrick.fromJson(gsonString);

        check(brick2, brick);
    }
}
