package com.directory.abe.SearchEntryFeature.UseCases;

import com.directory.abe.GSONModels.EntryModel;
import java.util.ArrayList;

public interface ISearchByKeywordUseCase {
    void onFilterByKeywordSQLiteFailure();

    void onFilterByKeywordSQLiteSuccess();

    void onFilterByKeywordSuccess(ArrayList<EntryModel> arrayList, String str, String str2, String str3, String str4);

    void onGetDataForSQLiteFromServerSuccess(ArrayList<EntryModel> arrayList);

    void onSearchByCatSuccess(ArrayList<EntryModel> arrayList, String str, String str2, String str3, double d, String str4);

    void sendErrorFromAPI(String str);
}
