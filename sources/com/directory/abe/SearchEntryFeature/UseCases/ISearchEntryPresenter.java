package com.directory.abe.SearchEntryFeature.UseCases;

import com.directory.abe.GSONModels.EntryModel;
import java.util.ArrayList;

public interface ISearchEntryPresenter {
    void initListAndMap();

    void insertAllToSQLite(ArrayList<EntryModel> arrayList);

    void onFilterByKeywordSuccess(ArrayList<EntryModel> arrayList, String str, String str2, String str3);

    void onFilterByRatingSuccess(ArrayList<EntryModel> arrayList, ArrayList<EntryModel> arrayList2, String str, String str2, float f);

    void onSearchByCatFailure(String str);

    void onSearchByCatSuccess(ArrayList<EntryModel> arrayList, String str, String str2);

    void searchByRadius(boolean z);

    void searchByRating(boolean z);

    void sendMessagesToUser(String str);

    void setViewType();

    void storeDetailedViewObs(ArrayList<EntryModel> arrayList);
}
