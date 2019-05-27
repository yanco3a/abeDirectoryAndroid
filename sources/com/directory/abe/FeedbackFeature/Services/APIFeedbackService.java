package com.directory.abe.FeedbackFeature.Services;

import com.directory.abe.APIServiceGeneratorMainServer;
import com.directory.abe.FeedbackFeature.UseCases.IFeedbackUseCase;
import com.directory.abe.GSONModels.EntryModel;
import java.io.IOError;
import java.io.IOException;
import org.apache.http.HttpStatus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class APIFeedbackService implements Callback<EntryModel> {
    private static final String TAG = APIFeedbackService.class.getSimpleName();
    private IFeedbackUseCase ifeedbackUseCase;

    public interface IFeedbackAPIService {
        @FormUrlEncoded
        @POST("api/insert/feedback/")
        Call<EntryModel> postFeedbackResult(@Field("q1") String str, @Field("q2") String str2, @Field("q3") String str3, @Field("q4") String str4, @Field("q4a") String str5, @Field("q5") String str6, @Field("q6") String str7, @Field("q7") String str8, @Field("q8") String str9, @Field("q9") String str10, @Field("email") String str11);
    }

    public APIFeedbackService(IFeedbackUseCase ifeedbackUseCase) {
        this.ifeedbackUseCase = ifeedbackUseCase;
    }

    public void attemptFeedbackResult(String q1, String q2, String q3, String q4, String q4a, String q5, String q6, String q7, String q8, String q9, String email) {
        try {
            Call<EntryModel> clonedCall = ((IFeedbackAPIService) APIServiceGeneratorMainServer.createService(IFeedbackAPIService.class)).postFeedbackResult(q1, q2, q3, q4, q4a, q5, q6, q7, q8, q9, email).clone();
            if (clonedCall.isExecuted()) {
                clonedCall.cancel();
            } else {
                clonedCall.enqueue(this);
            }
        } catch (IOError is) {
            is.printStackTrace();
            is.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void onResponse(Response<EntryModel> response) {
        String statusMsg = "";
        String statusError = "";
        if (response.isSuccess()) {
            int statusCode = response.code();
            if (statusCode < HttpStatus.SC_OK || statusCode > HttpStatus.SC_MULTIPLE_CHOICES) {
                try {
                    statusMsg = response.message();
                    statusError = response.errorBody().string();
                    this.ifeedbackUseCase.onFeedbackFailure("");
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                    e.getMessage();
                    this.ifeedbackUseCase.onFeedbackFailure("");
                    return;
                } catch (NullPointerException ne) {
                    ne.printStackTrace();
                    ne.getMessage();
                    return;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    e2.getMessage();
                    return;
                }
            }
            String booleanStr = ((EntryModel) response.body()).getMessage();
            if (booleanStr == null) {
                this.ifeedbackUseCase.onFeedbackFailure("error");
                return;
            } else if (booleanStr.equals("true") || booleanStr.equals("True")) {
                this.ifeedbackUseCase.onFeedbackSuccess(booleanStr);
                return;
            } else {
                this.ifeedbackUseCase.onFeedbackFailure("error");
                return;
            }
        }
        try {
            this.ifeedbackUseCase.onFeedbackFailure(response.errorBody().string());
        } catch (IOException e3) {
            e3.printStackTrace();
            e3.getMessage();
        } catch (Exception e22) {
            e22.printStackTrace();
            e22.getMessage();
        }
    }

    public void onFailure(Throwable t) {
        t.toString();
        this.ifeedbackUseCase.onFeedbackFailure("abe isn't feeling so good right now");
    }
}
