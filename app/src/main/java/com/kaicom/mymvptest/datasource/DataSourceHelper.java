package com.kaicom.mymvptest.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.kaicom.mymvptest.datasource.gen.DaoMaster;

/**
 * Created by jc on 2017/9/1.
 */

public class DataSourceHelper extends DaoMaster.OpenHelper {

    public DataSourceHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
