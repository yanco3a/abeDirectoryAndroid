package com.directory.abe.SearchEntryFeature.Services;

import com.directory.abe.APIServiceGeneratorMainServer;
import com.directory.abe.GSONModels.EntryModel;
import com.directory.abe.SearchEntryFeature.UseCases.ISearchUseCase;
import java.io.IOError;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class APISearchServices implements Callback<ArrayList<EntryModel>> {
    private static final String TAG = APISearchServices.class.getSimpleName();
    private String cat;
    private ArrayList<EntryModel> detailedView;
    private String fType;
    private ISearchUseCase iSearchUseCase;
    private double radius;
    private String vType;

    public interface SearchVendorListingResultInterface {
        @GET("api/cat/{cat}")
        Call<ArrayList<EntryModel>> searchVendorListingResult(@Path("cat") String str);
    }

    public APISearchServices(ISearchUseCase iSearchUseCase) {
        this.iSearchUseCase = iSearchUseCase;
    }

    public void getVendorCategoryListing(String category, String ft, String vT, double r) {
        this.cat = category;
        this.fType = ft;
        this.vType = vT;
        this.radius = r;
        try {
            Call<ArrayList<EntryModel>> clonedCall = ((SearchVendorListingResultInterface) APIServiceGeneratorMainServer.createService(SearchVendorListingResultInterface.class)).searchVendorListingResult(category).clone();
            if (clonedCall.isExecuted()) {
                clonedCall.cancel();
            } else {
                clonedCall.enqueue(this);
            }
        } catch (IOError is) {
            is.printStackTrace();
            is.getMessage();
            this.iSearchUseCase.sendErrorFromAPI("abe isn't feeling so good right now");
        }
    }

    public void onResponse(Response<ArrayList<EntryModel>> response) {
        try {
            String statusMsg = "";
            String statusError = "";
            if (response.isSuccess()) {
                int statusCode = response.code();
                if (response.body() == null) {
                    this.iSearchUseCase.sendErrorFromAPI("abe isn't feeling so good right now");
                    return;
                } else if (((ArrayList) response.body()).size() >= 0) {
                    this.iSearchUseCase.onSearchByCatSuccess((ArrayList) response.body(), this.cat, this.fType, this.vType, this.radius);
                    return;
                } else {
                    this.iSearchUseCase.sendErrorFromAPI("abe isn't feeling so good right now");
                    return;
                }
            }
            this.iSearchUseCase.sendErrorFromAPI("abe isn't feeling so good right now");
        } catch (Exception e) {
            this.iSearchUseCase.sendErrorFromAPI("abe isn't feeling so good right now");
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void onFailure(Throwable t) {
        try {
            t.toString();
            this.iSearchUseCase.sendErrorFromAPI("abe isn't feeling so good right now");
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            this.iSearchUseCase.sendErrorFromAPI("abe isn't feeling so good right now");
        }
    }
}
