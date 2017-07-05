package bj4.yhh.ezcounter.appwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import bj4.yhh.ezcounter.R;

/**
 * Created by s011208 on 2017/7/5.
 */

public class SimpleWidget extends AppWidgetProvider {
    private static final String TAG = "SimpleWidget";
    private static final boolean DEBUG = true;

    private static final String ADD_ONE_ACTION = "add_one";
    private static final String RESET_ACTION = "reset";

    private static final String PREFERENCE_KEY = "p_key";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        if (DEBUG) {
            Log.v(TAG, "onUpdate");
        }
        SharedPreferences pref = context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
        for (int appWidgetId : appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.simple_widget_layout);
            remoteViews.setOnClickPendingIntent(R.id.add_one, getPendingIntent(context, ADD_ONE_ACTION, appWidgetId));
            remoteViews.setOnClickPendingIntent(R.id.reset, getPendingIntent(context, RESET_ACTION, appWidgetId));
            final String counter = pref.getString(String.valueOf(appWidgetId), "0");
            remoteViews.setTextViewText(R.id.counter, counter);
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
        SharedPreferences pref = context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.simple_widget_layout);
        String counter = pref.getString(String.valueOf(appWidgetId), "0");
        if (ADD_ONE_ACTION.equals(action)) {
            counter = String.valueOf(Integer.valueOf(counter) + 1);
        } else if (RESET_ACTION.endsWith(action)) {
            counter = "0";
        }
        remoteViews.setTextViewText(R.id.counter, counter);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        pref.edit().putString(String.valueOf(appWidgetId), counter).commit();
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        if (DEBUG) {
            Log.v(TAG, "onDeleted");
        }
        SharedPreferences pref = context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
        for (int appWidgetId : appWidgetIds) {
            Log.v(TAG, "appWidgetId: " + appWidgetId);
            pref.edit().remove(String.valueOf(appWidgetId)).commit();
        }
    }

    private PendingIntent getPendingIntent(Context context, String action, int appWidgetId) {
        Intent intent = new Intent(context, getClass());
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, appWidgetId, intent, 0);
    }
}
