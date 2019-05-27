package com.directory.abe.FeaturedEntryFeature.UseCases;

import com.directory.abe.GSONModels.EntryModel;
import java.util.ArrayList;

public interface IMainPresenter {
    void onFeaturedListingLoadedFailure();

    void onFeaturedListingLoadedSuccess(ArrayList<EntryModel> arrayList);

    void searchByKeywordMode(String str);

    void sendMessagesToUser(String str);

    void setMainViewTitle(String str);
}
