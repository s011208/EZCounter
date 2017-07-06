package bj4.yhh.ezcounter.appwidget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import bj4.yhh.ezcounter.database.WidgetInfoDatabase;

/**
 * Created by s011208 on 2017/7/6.
 */

public class SimpleWidgetConfigureActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent == null) {
            setResult(RESULT_CANCELED);
            finish();
            return;
        }
        final int appWidgetId = intent.getIntExtra("appWidgetId", AppWidgetManager.INVALID_APPWIDGET_ID);

        WidgetInfoDatabase widgetInfoDatabase = WidgetInfoDatabase.getInstance(this);
        widgetInfoDatabase.initWidget(appWidgetId);
        setResult(RESULT_OK);
        finish();
    }
}
