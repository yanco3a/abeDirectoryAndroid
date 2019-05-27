package com.directory.abe.FeaturedEntryFeature.UseCases;

import com.directory.abe.GSONModels.EntryModel;
import java.util.ArrayList;

public interface IMainUseCase {
    void onAllSuccess(ArrayList<EntryModel> arrayList);

    void onFeaturedFailureMsg(String str);

    void onFeaturedSuccess(ArrayList<EntryModel> arrayList);

    void onFeaturedSuccessMsg(String str);

    void sendAllResponseFromMainServer(String str);
}
