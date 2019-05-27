package com.directory.abe.SearchEntryFeature.Services;

import com.directory.abe.APIServiceGeneratorMainServer;
import com.directory.abe.GSONModels.EntryModel;
import com.directory.abe.SearchEntryFeature.UseCases.ISearchByRatingUseCase;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.HttpStatus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

public class APIGetAllForSQLiteService implements Callback<ArrayList<EntryModel>> {
    private static final String TAG = APISearchRatingService.class.getSimpleName();
    private String cat;
    private String fType;
    private ISearchByRatingUseCase iSearchByRatingUseCase;
    private float rating;
    private String vType;

    public interface GetAllForSQLiteFromMainServer {
        @GET("api/select/all")
        Call<ArrayList<EntryModel>> getAllForSQLite();
    }

    public APIGetAllForSQLiteService(ISearchByRatingUseCase iSearchByRatingUseCase) {
        this.iSearchByRatingUseCase = iSearchByRatingUseCase;
    }

    public void getDataForSQLiteFromServer() {
        try {
            Call<ArrayList<EntryModel>> clonedCall = ((GetAllForSQLiteFromMainServer) APIServiceGeneratorMainServer.createService(GetAllForSQLiteFromMainServer.class)).getAllForSQLite().clone();
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
        if (response.isSuccess()) {
            int statusCode = response.code();
            if (statusCode < HttpStatus.SC_OK || statusCode > HttpStatus.SC_MULTIPLE_CHOICES) {
                try {
                    statusMsg = response.message();
                    statusError = response.errorBody().string();
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                    e.getMessage();
                    return;
                } catch (NullPointerException ne) {
                    ne.printStackTrace();
                    ne.getMessage();
                    return;
                } catch (Exception ne2) {
                    ne2.printStackTrace();
                    ne2.getMessage();
                    return;
                }
            }
            this.iSearchByRatingUseCase.onGetDataForSQLiteFromServerSuccess((ArrayList) response.body());
            return;
        }
        try {
            this.iSearchByRatingUseCase.sendErrorFromAPI(response.errorBody().string());
        } catch (IOException e2) {
            e2.printStackTrace();
            e2.getMessage();
        }
    }

    public void onFailure(Throwable t) {
        try {
            t.toString();
            this.iSearchByRatingUseCase.sendErrorFromAPI(t.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }
}
