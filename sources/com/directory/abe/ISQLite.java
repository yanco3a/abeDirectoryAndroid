package com.directory.abe;

import android.content.Context;
import com.directory.abe.SQLiteDatabase.Services.SQLiteHelper;

public interface ISQLite {
    SQLiteHelper getSQLiteHelper();

    void setupDBHelper(Context context);
}
