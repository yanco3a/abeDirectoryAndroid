package com.directory.abe.SearchEntryFeature.Services;

import com.directory.abe.APIServiceGeneratorMainServer;
import com.directory.abe.GSONModels.EntryModel;
import com.directory.abe.SearchEntryFeature.UseCases.ISearchByRatingUseCase;
import java.io.IOError;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class APISearchRatingService implements Callback<ArrayList<EntryModel>> {
    private static final String TAG = APISearchRatingService.class.getSimpleName();
    private String cat;
    private String fType;
    private ISearchByRatingUseCase iSearchByRatingUseCase;
    private float rating;
    private String vType;

    public interface GetFilteredByRatingFromMainServer {
        @GET("api/select/byrating/{rating}/bycat/{cat}")
        Call<ArrayList<EntryModel>> getFilteredByRating(@Path("rating") float f, @Path("cat") String str);
    }

    public APISearchRatingService(ISearchByRatingUseCase iSearchByRatingUseCase) {
        this.iSearchByRatingUseCase = iSearchByRatingUseCase;
    }

    public void filterCategoryByRating(String c, float r, String vt, String ft) {
        this.cat = c;
        this.fType = ft;
        this.vType = vt;
        this.rating = r;
        try {
            Call<ArrayList<EntryModel>> clonedCall = ((GetFilteredByRatingFromMainServer) APIServiceGeneratorMainServer.createService(GetFilteredByRatingFromMainServer.class)).getFilteredByRating(r, c).clone();
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
        try {
            if (response.isSuccess()) {
                int statusCode = response.code();
                if (((ArrayList) response.body()).size() >= 0) {
                    this.iSearchByRatingUseCase.onFilterByRatingSuccess((ArrayList) response.body(), this.cat, this.vType, this.fType, this.rating);
                    return;
                } else {
                    this.iSearchByRatingUseCase.sendErrorFromAPI("abe isn't feeling so good right now");
                    return;
                }
            }
            this.iSearchByRatingUseCase.sendErrorFromAPI("abe isn't feeling so good right now");
        } catch (Exception e) {
            this.iSearchByRatingUseCase.sendErrorFromAPI("abe isn't feeling so good right now");
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void onFailure(Throwable t) {
        try {
            t.toString();
            this.iSearchByRatingUseCase.sendErrorFromAPI("abe isn't feeling so good right now");
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            this.iSearchByRatingUseCase.sendErrorFromAPI("abe isn't feeling so good right now");
        }
    }
}
