package com.svyat.sample.alienclock.content;

import android.content.Context;

/**
 * Created by shromyak on 14.07.2016.
 *
 * Content scheme, as usual, is content brick tree, but ;-)...
 * There are two ways how to initialize component (described in brick)
 * and inject into alien controller:
 *
 * 1) add component as a child view(fragment) with decorator (childrenTags field)
 * 2) initialize on the fly during layout inflating (include into xml layout as custom view)
 *
 * If brick isn't in tree (orphan) -- that means:
 * it's initialized on-fly during layout inflating (second way)
 *
 * Main reason of this -- who bears responsibility for "view-add" animation:
 * decorator in first case
 * system layout inflater -- in second one
 *
 * If you are using both ways -- result will be unpredictable, because two
 * components will be added with the same android:id="@+id/..."
 *
 * To omit this -- please add verification in
 * @link DefaultAppearanceDecorator#attachChildren
 */
public class DefaultContentScheme implements AlienContentScheme {

    private final AlienContentManager alienContentManager;

    public DefaultContentScheme(Context context) {
        alienContentManager = DefaultContentManager.get(context);
    }

    @Override
    public void initDefault() {

        DefaultContentBrick root = DefaultContentBrickFactory.getDoublePaneBrick();
        alienContentManager.reinitWithRootTag(root.getTag());
        alienContentManager.storeBrick(root);

        DefaultContentBrick clock = DefaultContentBrickFactory.getClockBrick();
        alienContentManager.storeBrick(clock);

        // Please put your attention here: clockWidget is an orphan (it's not linked with
        // clock fragment via childrenTags field)
        DefaultContentBrick clockWidget = DefaultContentBrickFactory.getClockWidgetBrick();
        alienContentManager.storeBrick(clockWidget);

        DefaultContentBrick bloomchan = DefaultContentBrickFactory.getBloomchanBrick();
        alienContentManager.storeBrick(bloomchan);
    }
}
