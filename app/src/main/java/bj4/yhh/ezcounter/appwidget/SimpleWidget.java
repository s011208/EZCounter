package bj4.yhh.ezcounter.appwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import bj4.yhh.ezcounter.R;
import bj4.yhh.ezcounter.database.WidgetInfoDatabase;

/**
 * Created by s011208 on 2017/7/5.
 */

public class SimpleWidget extends AppWidgetProvider {
    private static final String TAG = "SimpleWidget";
    private static final boolean DEBUG = true;

    private static final String ADD_ONE_ACTION = "bj4.yhh.ezcounter.appwidget.add_one";
    private static final String RESET_ACTION = "bj4.yhh.ezcounter.appwidget.reset";

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        if (DEBUG) {
            Log.v(TAG, "onAppWidgetOptionsChanged");
        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        if (DEBUG) {
            Log.v(TAG, "onEnabled");
        }
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        if (DEBUG) {
            Log.v(TAG, "onDisabled");
        }
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
        if (DEBUG) {
            Log.v(TAG, "onRestored");
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        if (DEBUG) {
            Log.v(TAG, "onUpdate");
        }
        WidgetInfoDatabase widgetInfoDatabase = WidgetInfoDatabase.getInstance(context);
        for (int appWidgetId : appWidgetIds) {
            if (DEBUG) {
                Log.v(TAG, "update appWidgetId: " + appWidgetId);
            }
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.simple_widget_layout);
            remoteViews.setOnClickPendingIntent(R.id.add_one, getPendingIntent(context, ADD_ONE_ACTION, appWidgetId));
            remoteViews.setOnClickPendingIntent(R.id.reset, getPendingIntent(context, RESET_ACTION, appWidgetId));
            remoteViews.setTextViewText(R.id.counter, widgetInfoDatabase.getWidgetCounter(appWidgetId));
            remoteViews.setTextViewText(R.id.title, widgetInfoDatabase.getWidgetTitle(appWidgetId));
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        final int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0);
        final String action = intent.getAction();
        if (DEBUG) {
            Log.v(TAG, "onReceive, action: " + action + ", appWidgetId: " + appWidgetId);
        }
        WidgetInfoDatabase widgetInfoDatabase = WidgetInfoDatabase.getInstance(context);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.simple_widget_layout);
        String counter = widgetInfoDatabase.getWidgetCounter(appWidgetId);
        if (ADD_ONE_ACTION.equals(action)) {
            counter = String.valueOf(Integer.valueOf(counter) + 1);
        } else if (RESET_ACTION.endsWith(action)) {
            counter = "0";
        }
        remoteViews.setTextViewText(R.id.counter, counter);
        remoteViews.setTextViewText(R.id.title, widgetInfoDatabase.getWidgetTitle(appWidgetId));
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        widgetInfoDatabase.updateWidgetCounter(appWidgetId, counter);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        if (DEBUG) {
            Log.v(TAG, "onDeleted");
        }
        WidgetInfoDatabase widgetInfoDatabase = WidgetInfoDatabase.getInstance(context);
        for (int appWidgetId : appWidgetIds) {
            Log.v(TAG, "appWidgetId: " + appWidgetId);
            widgetInfoDatabase.removeWidgetInfo(appWidgetId);
        }
    }

    private PendingIntent getPendingIntent(Context context, String action, int appWidgetId) {
        Intent intent = new Intent(context, getClass());
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
