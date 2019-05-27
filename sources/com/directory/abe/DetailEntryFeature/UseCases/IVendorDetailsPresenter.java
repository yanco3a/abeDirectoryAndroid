package com.directory.abe.DetailEntryFeature.UseCases;

import com.directory.abe.GSONModels.EntryModel;

public interface IVendorDetailsPresenter {
    String cleanSearch(String str);

    void insertUserReview(int i, int i2, float f, String str);

    void onInsertUserReviewFailure();

    void onInsertUserReviewSuccess(EntryModel entryModel, int i, int i2, float f, String str);

    void sendMessagesToUser(String str);
}
