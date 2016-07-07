package com.svyat.sample.alienclock.appwidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.svyat.sample.alienclock.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link AlienAppWidgetConfigureActivity AlienAppWidgetConfigureActivity}
 */
public class AlienAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = AlienAppWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        Calendar cal = GregorianCalendar.getInstance();
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.alien_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText
                + ":"
                + cal.get(Calendar.HOUR_OF_DAY)
                + ":"
                + cal.get(Calendar.MINUTE)
        );

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            AlienAppWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

