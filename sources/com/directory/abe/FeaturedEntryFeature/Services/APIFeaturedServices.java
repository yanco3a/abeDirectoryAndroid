package com.directory.abe.FeaturedEntryFeature.Services;

import com.directory.abe.APIServiceGeneratorMainServer;
import com.directory.abe.FeaturedEntryFeature.UseCases.IMainUseCase;
import com.directory.abe.GSONModels.EntryModel;
import java.io.IOError;
import java.util.ArrayList;
import org.apache.http.HttpStatus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

public class APIFeaturedServices implements Callback<ArrayList<EntryModel>> {
    private static final String TAG = APIFeaturedServices.class.getSimpleName();
    private IMainUseCase iMainUseCase;

    public interface FeaturedResultInterface {
        @GET("api/featured")
        Call<ArrayList<EntryModel>> featuredResult();
    }

    public APIFeaturedServices(IMainUseCase iMainUseCase) {
        this.iMainUseCase = iMainUseCase;
    }

    public void getFeaturedEntry() {
        try {
            Call<ArrayList<EntryModel>> clonedCall = ((FeaturedResultInterface) APIServiceGeneratorMainServer.createService(FeaturedResultInterface.class)).featuredResult().clone();
            if (clonedCall.isExecuted()) {
                clonedCall.cancel();
            } else {
                clonedCall.enqueue(this);
            }
        } catch (IOError is) {
            is.printStackTrace();
            is.getMessage();
            this.iMainUseCase.onFeaturedFailureMsg("");
        }
    }

    public void onResponse(Response<ArrayList<EntryModel>> response) {
        String statusMsg = "";
        String statusError = "";
        try {
            if (response.isSuccess()) {
                int statusCode = response.code();
                if (statusCode < HttpStatus.SC_OK || statusCode > HttpStatus.SC_MULTIPLE_CHOICES) {
                    this.iMainUseCase.onFeaturedFailureMsg("");
                    return;
                }
                this.iMainUseCase.onFeaturedSuccessMsg(String.valueOf(statusCode));
                this.iMainUseCase.onFeaturedSuccess((ArrayList) response.body());
                return;
            }
            this.iMainUseCase.onFeaturedFailureMsg("");
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            this.iMainUseCase.onFeaturedFailureMsg("");
        }
    }

    public void onFailure(Throwable t) {
        t.toString();
        this.iMainUseCase.onFeaturedFailureMsg("");
    }
}
