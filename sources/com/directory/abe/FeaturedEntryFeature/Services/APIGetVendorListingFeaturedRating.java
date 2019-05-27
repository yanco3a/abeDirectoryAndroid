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

public class APIGetVendorListingFeaturedRating implements Callback<ArrayList<EntryModel>> {
    private static final String TAG = APIFeaturedServices.class.getSimpleName();
    private IMainUseCase iInteract;

    public interface GetVendorListingFeaturedRatingResultInterface {
        @GET("api/getall")
        Call<ArrayList<EntryModel>> getAll();
    }

    public APIGetVendorListingFeaturedRating(IMainUseCase iInteract) {
        this.iInteract = iInteract;
    }

    public void getVendorListingFeaturedRatingResultInterface() {
        try {
            Call<ArrayList<EntryModel>> clonedCall = ((GetVendorListingFeaturedRatingResultInterface) APIServiceGeneratorMainServer.createService(GetVendorListingFeaturedRatingResultInterface.class)).getAll().clone();
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

    public void onResponse(Response<ArrayList<EntryModel>> response) {
        String statusMsg = "";
        String statusError = "";
        try {
            if (response.isSuccess()) {
                int statusCode = response.code();
                if (statusCode < HttpStatus.SC_OK || statusCode > HttpStatus.SC_MULTIPLE_CHOICES) {
                    this.iInteract.onFeaturedFailureMsg("");
                    return;
                }
                this.iInteract.sendAllResponseFromMainServer(String.valueOf(statusCode));
                this.iInteract.onAllSuccess((ArrayList) response.body());
                return;
            }
            this.iInteract.onFeaturedFailureMsg("");
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void onFailure(Throwable t) {
        t.toString();
        this.iInteract.onFeaturedFailureMsg(t.getMessage());
    }
}
