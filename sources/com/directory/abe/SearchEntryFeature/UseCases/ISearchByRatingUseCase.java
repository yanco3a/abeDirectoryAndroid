package com.directory.abe.SearchEntryFeature.UseCases;

import com.directory.abe.GSONModels.EntryModel;
import java.util.ArrayList;

public interface ISearchByRatingUseCase {
    void onFilterByRatingSQLiteFailure();

    void onFilterByRatingSQLiteSuccess();

    void onFilterByRatingSuccess(ArrayList<EntryModel> arrayList, String str, String str2, String str3, float f);

    void onGetDataForSQLiteFromServerSuccess(ArrayList<EntryModel> arrayList);

    void sendErrorFromAPI(String str);
}
