package com.svyat.sample.alienclock.content;

/**
 * Created by shromyak on 18.07.2016.
 *
 * Interface for Component Bricks Persistence
 * Please refer to component brick interface description
 * for explanation
 *
 * @link AlienComponentBrick
 * and
 * @link DefaultContentScheme
 */
public interface AlienContentManager {

    void reinitScheme();

    void storeBrick(AlienContentBrick brick);

    AlienContentBrick getBrickWithTag(String tag);

    String getRootTag();

    //Set root tag without clearance
    void setRootTag(String tag);

    //Set root tag and clear store if exist
    void reinitWithRootTag(String tag);
}
