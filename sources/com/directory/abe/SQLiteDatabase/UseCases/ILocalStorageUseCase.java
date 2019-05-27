package com.directory.abe.SQLiteDatabase.UseCases;

import com.directory.abe.GSONModels.EntryModel;
import java.util.ArrayList;

public interface ILocalStorageUseCase {
    void onLoadAllSuccess(ArrayList<EntryModel> arrayList);

    void sendErrorFromAPI(String str);
}
