package com.svyat.sample.alienclock.alien;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.svyat.sample.alienclock.model.DaemonParams;

/**
 * Created by shromyak on 20.07.2016.
 *
 * Is used to achieve visitor pattern
 *
 * We have different components (for example inherited from different kinds of Fragment),
 * but the same operations performed on each descendant
 */
public interface AlienIncubator {
    //applied to component from component tree
    void visit(@NonNull AlienControlled visitor, @Nullable DaemonParams settings);
}
