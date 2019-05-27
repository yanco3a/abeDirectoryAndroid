package com.directory.abe.SQLiteDatabase.Presentation;

public interface ILocalStorageView {
    void gotoMain();

    void onSQLiteFailure(String str);

    void onSQLiteSuccess(String str);

    void progessDialogMsg(String str);

    void toastLMsg(String str);

    void toastSMsg(String str);
}
