package com.directory.abe.DetailEntryFeature.Services;

import com.directory.abe.APIServiceGeneratorMainServer;
import com.directory.abe.DetailEntryFeature.UseCases.IinsertUserReviewUseCase;
import com.directory.abe.GSONModels.EntryModel;
import java.io.IOError;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class APIInsertUserReviewService implements Callback<EntryModel> {
    private static final String TAG = APIInsertUserReviewService.class.getSimpleName();
    private IinsertUserReviewUseCase iInsertUserReviewUseCase;
    private int listingId;
    private float rating;
    private int userId;
    private String userReview;

    public interface InsertUserReviewAPIServiceInterface {
        @FormUrlEncoded
        @POST("api/insert/review")
        Call<EntryModel> insertUserReview(@Field("ratingUserId") int i, @Field("ratingListingId") int i2, @Field("ratingValue") float f, @Field("ratingReview") String str);
    }

    public APIInsertUserReviewService(IinsertUserReviewUseCase iInsertUserReviewUseCase) {
        this.iInsertUserReviewUseCase = iInsertUserReviewUseCase;
    }

    public void insertUserReview(int userId, int listingId, float rating, String userReview) {
        this.userId = userId;
        this.listingId = listingId;
        this.rating = rating;
        this.userReview = userReview;
        try {
            Call<EntryModel> clonedCall = ((InsertUserReviewAPIServiceInterface) APIServiceGeneratorMainServer.createService(InsertUserReviewAPIServiceInterface.class)).insertUserReview(userId, listingId, rating, userReview).clone();
            if (clonedCall.isExecuted()) {
                clonedCall.cancel();
            } else {
                clonedCall.enqueue(this);
            }
        } catch (IOError is) {
            is.printStackTrace();
            is.getMessage();
        }
    }

    public void onResponse(Response<EntryModel> response) {
        try {
            String statusMsg = "";
            String statusError = "";
            if (response.isSuccess()) {
                int statusCode = response.code();
                if (((EntryModel) response.body()).getMessage() != null) {
                    this.iInsertUserReviewUseCase.sendErrorFromAPI("error");
                    return;
                } else if (((EntryModel) response.body()).getListingId() != 0) {
                    this.iInsertUserReviewUseCase.onInsertUserReviewSuccess((EntryModel) response.body(), this.userId, this.listingId, this.rating, this.userReview);
                    return;
                } else {
                    this.iInsertUserReviewUseCase.sendErrorFromAPI(((EntryModel) response.body()).getMessage());
                    return;
                }
            }
            this.iInsertUserReviewUseCase.sendErrorFromAPI("error");
        } catch (Exception e) {
            this.iInsertUserReviewUseCase.sendErrorFromAPI("error");
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void onFailure(Throwable t) {
        try {
            t.toString();
            this.iInsertUserReviewUseCase.sendErrorFromAPI(t.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            this.iInsertUserReviewUseCase.sendErrorFromAPI("server failure");
        }
    }
}
