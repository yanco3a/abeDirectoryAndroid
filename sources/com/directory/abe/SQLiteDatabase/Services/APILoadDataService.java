package com.directory.abe.SQLiteDatabase.Services;

import com.directory.abe.APIServiceGeneratorMainServer;
import com.directory.abe.GSONModels.EntryModel;
import com.directory.abe.SQLiteDatabase.UseCases.ILocalStorageUseCase;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.HttpStatus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

public class APILoadDataService implements Callback<ArrayList<EntryModel>> {
    private static final String TAG = APILoadDataService.class.getSimpleName();
    private ILocalStorageUseCase idbUseCase;

    public interface GetAllFromMainServer {
        @GET("api/select/all")
        Call<ArrayList<EntryModel>> getAll();
    }

    public APILoadDataService(ILocalStorageUseCase idbUseCase) {
        this.idbUseCase = idbUseCase;
    }

    public void loadAll() {
        try {
            Call<ArrayList<EntryModel>> clonedCall = ((GetAllFromMainServer) APIServiceGeneratorMainServer.createService(GetAllFromMainServer.class)).getAll().clone();
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
                    this.idbUseCase.sendErrorFromAPI("");
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                    e.getMessage();
                    this.idbUseCase.sendErrorFromAPI("");
                    return;
                } catch (NullPointerException ne) {
                    ne.printStackTrace();
                    ne.getMessage();
                    this.idbUseCase.sendErrorFromAPI("");
                    return;
                }
            }
            this.idbUseCase.onLoadAllSuccess((ArrayList) response.body());
            return;
        }
        try {
            this.idbUseCase.sendErrorFromAPI("");
        } catch (Exception e2) {
            e2.printStackTrace();
            e2.getMessage();
            this.idbUseCase.sendErrorFromAPI("");
        }
    }

    public void onFailure(Throwable t) {
        try {
            t.toString();
            this.idbUseCase.sendErrorFromAPI("");
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            this.idbUseCase.sendErrorFromAPI("");
        }
    }
}
