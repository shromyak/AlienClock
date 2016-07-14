package com.svyat.sample.alienclock.alien;

/**
 * Created by shromyak on 18.07.2016.
 *
 * Child of AlienControlled is the only descendant that uses
 * daemons of parent component (Fragment or Activity)
 * That means that it's initialized "from outside" so needs
 * indicator to prevent over-initialization
 */
public interface AlienViewGroup extends AlienControlled {

    boolean hasAlienInside();

}
