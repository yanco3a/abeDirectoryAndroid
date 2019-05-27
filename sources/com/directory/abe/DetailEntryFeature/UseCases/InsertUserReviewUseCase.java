package com.directory.abe.DetailEntryFeature.UseCases;

import com.directory.abe.DetailEntryFeature.Services.APIInsertUserReviewService;
import com.directory.abe.GSONModels.EntryModel;

public class InsertUserReviewUseCase implements IinsertUserReviewUseCase {
    private static final String TAG = InsertUserReviewUseCase.class.getSimpleName();
    private APIInsertUserReviewService apiInsertUserReviewService = new APIInsertUserReviewService(this);
    private IVendorDetailsPresenter presenter;

    public InsertUserReviewUseCase(IVendorDetailsPresenter presenter) {
        this.presenter = presenter;
    }

    public void insertUserReview(int userId, int listingId, float rating, String userReview) {
        this.apiInsertUserReviewService.insertUserReview(userId, listingId, rating, userReview);
    }

    public void onInsertUserReviewSuccess(EntryModel body, int userId, int listingId, float rating, String userReview) {
        this.presenter.onInsertUserReviewSuccess(body, userId, listingId, rating, userReview);
    }

    public void sendErrorFromAPI(String string) {
        this.presenter.onInsertUserReviewFailure();
    }
}
