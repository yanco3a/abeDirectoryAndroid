package com.directory.abe.SQLiteDatabase.UseCases;

import com.directory.abe.GSONModels.EntryModel;
import java.util.ArrayList;

public interface ILocalStoragePresenter {
    void gotoMainOnUIthread();

    void onLoadAllFailure();

    void onLoadAllSuccess(ArrayList<EntryModel> arrayList);

    void sendLongMsgtoUser(String str);

    void showProgessDialogMsg();
}
