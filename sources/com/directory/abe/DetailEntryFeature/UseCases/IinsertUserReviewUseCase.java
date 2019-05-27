package com.directory.abe.DetailEntryFeature.UseCases;

import com.directory.abe.GSONModels.EntryModel;

public interface IinsertUserReviewUseCase {
    void onInsertUserReviewSuccess(EntryModel entryModel, int i, int i2, float f, String str);

    void sendErrorFromAPI(String str);
}
