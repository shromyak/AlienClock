package com.svyat.sample.alienclock.alien;

import com.svyat.sample.alienclock.controller.AlienController;

/**
 * Created by shromyak on 20.07.2016.
 *
 * It's hidden parent: direct implementation isn't allowed, please use
 * descendant interfaces: AlienFragment, AlienActivity and AlienViewGroup
 */
interface AlienControlled {
    void plantAlien(AlienController controller);
}
