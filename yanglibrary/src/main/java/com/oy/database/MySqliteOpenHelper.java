package com.oy.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public class MySqliteOpenHelper extends SQLiteOpenHelper {
    private String tableName;
    private String sql;

    public MySqliteOpenHelper(Context context, String name, String tableName,String sql) {
        super(context, name, null, 1);
        this.tableName = tableName;
        this.sql = sql;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
