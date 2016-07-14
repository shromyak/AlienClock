package com.svyat.sample.alienclock.appearance;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.svyat.sample.alienclock.content.AlienContentBrick;
import com.svyat.sample.alienclock.content.AlienContentManager;
import com.svyat.sample.alienclock.content.DefaultContentManager;
import com.svyat.sample.alienclock.model.DaemonParams;
import com.svyat.sample.alienclock.model.DecoratorParams;
import com.svyat.sample.alienclock.model.ShapeParams;
import com.svyat.sample.alienclock.skin.AppearanceSkin;
import com.svyat.sample.alienclock.alien.AlienViewGroup;

/**
 * Created by shromyak on 07.07.2016.
 *
 * Default implementation of decorator please refer
 * interface description for explanation
 * @link AppearanceDecorator
 */
public class DefaultAppearanceDecorator implements AppearanceDecorator {

    private static final String TAG = DefaultAppearanceDecorator.class.getSimpleName();

    private final Context context;
    private final AppearanceSkin appearanceTheme;

    public DefaultAppearanceDecorator(Context context, AppearanceSkin appearanceTheme) {
        this.context = context;
        this.appearanceTheme = appearanceTheme;
    }

    @Override
    public void drawShape(Canvas canvas, ShapeParams settings) {

        final ShapeParams.ShapeType shapeType = settings.getShapeType();
        final RectF frame = settings.getFrame();

        if (shapeType == null || frame == null) {
            return;
        }

        int val = settings.getIntValue();
        float x = frame.centerX();
        float y = frame.top;

        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(x, y);
        Paint paintLine;

        switch (shapeType) {

            case ARC1: {

                Paint paintText = appearanceTheme.getPaint(AppearanceSkin.PaintType.TEXT);
                canvas.drawText("Hello", 0, paintText.getTextSize(), paintText);

                paintLine = appearanceTheme.getPaint(AppearanceSkin.PaintType.LINE1);
                path.arcTo(frame, -90f, (val/60f)*360f);
                break;
            }

            case ARC2: {

                paintLine = appearanceTheme.getPaint(AppearanceSkin.PaintType.LINE2);
                path.arcTo(frame, -90f, (val/60f)*360f);
                break;
            }

            case ARC3: {

                paintLine = appearanceTheme.getPaint(AppearanceSkin.PaintType.LINE3);
                path.arcTo(frame, -90f, (val/12f)*360f);
                break;
            }

            default:
                paintLine = appearanceTheme.getPaint(AppearanceSkin.PaintType.LINE);
        }

        path.moveTo(x, y);
        path.close();

        canvas.drawPath(path, paintLine);
    }

    @Override
    public void attachChildren(@Nullable View view, @NonNull DecoratorParams params) {

        if (view == null) {
            return;
        }

        AlienContentBrick brick = params.getContentBrick();

        if (brick == null) {
            return;
        }

        ViewGroup viewGroup;

        int lay = params.getDerivedLayoutId();

        int target = params.getDerivedContainerViewId();

        int idv = view.getId();

        if (target > 0 && lay > 0 && idv == target && view instanceof ViewGroup) {

            viewGroup = (ViewGroup) view;

        } else {

            return;
        }

        attachChildren(viewGroup, lay, params);
    }

    private void attachChildren(ViewGroup viewGroup, int lay, DecoratorParams params) {

        View generated = params.getLayoutInflater().inflate(lay, viewGroup);
        DaemonParams daemonParams = params.getDaemonParams();
        if (generated != null && daemonParams != null) {

            AlienContentManager manager = DefaultContentManager.get(daemonParams.getContext());
            ClassLoader classLoader = daemonParams.getClassLoader();
            FragmentManager fragmentManager = daemonParams.getFragmentManager();
            AlienContentBrick brick = params.getContentBrick();
            Context context = daemonParams.getContext();

            String[] tags = brick.getChildrenTags();
            for (String tag: tags) {

                AlienContentBrick childBrick = manager.getBrickWithTag(tag);
                String className = childBrick.getHostComponentClassName();
                String name = childBrick.getContainerViewIdName();
                int childId = context.getResources().getIdentifier(name, "id", context.getPackageName());
                View childView = null;
                if(childId > 0) {
                    childView = generated.findViewById(childId);
                }

                if (className != null && childView != null) {
                    try {

                        Class clazz = classLoader.loadClass(className);
                        if (clazz == null) {
                            continue;
                        }

                        if (Fragment.class.isAssignableFrom(clazz)) {

                            Fragment fragment = fragmentManager.findFragmentByTag(brick.getTag());
                            if (fragment == null) {

                                fragment = (Fragment) clazz.newInstance();
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.add(childId, fragment, brick.getTag());
                                transaction.commit();

                            }
                        } else if (Activity.class.isAssignableFrom(clazz)) {

                            Log.d(TAG, "Activity not supported yet");

                        } else if (AlienViewGroup.class.isAssignableFrom(clazz)) {

                            Log.d(TAG, "AlienViewGroup not supported yet");
                        }

                    } catch (ClassNotFoundException e) {
                        Log.e(TAG, "Can't load host", e);
                    } catch (InstantiationException e) {
                        Log.e(TAG, "Can't instantiate fragment 1", e);
                    } catch (IllegalAccessException e) {
                        Log.e(TAG, "Can't instantiate fragment 2", e);
                    }
                }
            }
        }
    }
}