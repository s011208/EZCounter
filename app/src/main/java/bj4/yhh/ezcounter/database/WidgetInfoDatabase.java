package bj4.yhh.ezcounter.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by s011208 on 2017/7/6.
 */

public class WidgetInfoDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "widget_info.db";
    private static final int VERSION = 1;

    private static final String TABLE_WIDGET_INFO = "widget_info";
    private static final String COLUMN_WIDGET_ID = "w_id";
    private static final String COLUMN_WIDGET_COUNTER = "w_counter";
    private static final String COLUMN_WIDGET_TITLE = "w_title";
    private static final String COLUMN_BACKGROUND_COLOR = "bg_color";
    private static final String COLUMN_TEXT_COLOR = "text_Color";

    private static final String CREATE_TABLE_WIDGET_INFO = "CREATE TABLE IF NOT EXISTS " + TABLE_WIDGET_INFO + " (" +
            COLUMN_WIDGET_ID + " INTEGER  PRIMARY KEY," +
            COLUMN_BACKGROUND_COLOR + " TEXT," +
            COLUMN_TEXT_COLOR + " TEXT," +
            COLUMN_WIDGET_COUNTER + " TEXT," +
            COLUMN_WIDGET_TITLE + " TEXT" + ")";

    private static WidgetInfoDatabase sInstance;

    private WidgetInfoDatabase(Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, VERSION);
    }

    public synchronized static WidgetInfoDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new WidgetInfoDatabase(context);
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_WIDGET_INFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @NonNull
    public String getWidgetTitle(int appWidgetId) {
        final String rtn = getWidgetInfo(appWidgetId, COLUMN_WIDGET_TITLE);
        return rtn == null ? "" : rtn;
    }

    @NonNull
    public String getWidgetCounter(int appWidgetId) {
        final String rtn = getWidgetInfo(appWidgetId, COLUMN_WIDGET_COUNTER);
        return rtn == null ? "0" : rtn;
    }

    @Nullable
    private String getWidgetInfo(int appWidgetId, String column) {
        Cursor c = getWritableDatabase().query(TABLE_WIDGET_INFO, new String[]{column}, COLUMN_WIDGET_ID + "=" + appWidgetId, null, null, null, null);
        if (c == null) return null;
        try {
            while (c.moveToNext()) {
                return c.getString(0);
            }
        } finally {
            c.close();
        }
        return null;
    }

    public void initWidget(int appWidgetId) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_WIDGET_ID, appWidgetId);
        getWritableDatabase().insert(TABLE_WIDGET_INFO, null, cv);
    }

    public void updateWidgetTitle(int appWidgetId, String title) {
        updateWidgetInfo(appWidgetId, COLUMN_WIDGET_TITLE, title);
    }

    public void updateWidgetCounter(int appWidgetId, String counter) {
        updateWidgetInfo(appWidgetId, COLUMN_WIDGET_COUNTER, counter);
    }

    private void updateWidgetInfo(int appWidgetId, String column, String value) {
        ContentValues cv = new ContentValues();
        cv.put(column, value);
        getWritableDatabase().update(TABLE_WIDGET_INFO, cv, COLUMN_WIDGET_ID + "=" + appWidgetId, null);
    }

    public void removeWidgetInfo(int appWidgetId) {
        getWritableDatabase().delete(TABLE_WIDGET_INFO, COLUMN_WIDGET_ID + "=" + appWidgetId, null);
    }
}
