package com.directory.abe.SearchEntryFeature.Services;

import com.directory.abe.APIServiceGeneratorMainServer;
import com.directory.abe.GSONModels.EntryModel;
import com.directory.abe.SearchEntryFeature.UseCases.ISearchByKeywordUseCase;
import java.io.IOError;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class APISearchKeywordService implements Callback<ArrayList<EntryModel>> {
    private static final String TAG = APISearchKeywordService.class.getSimpleName();
    private String cat;
    private ArrayList<EntryModel> detailedView;
    private String fType;
    private ISearchByKeywordUseCase iSearchByKeywordUseCase;
    private String keyword;
    private String vType;

    public interface GetFilteredByKeywordFromMainServer {
        @GET("api/select/bykeyword/{keyword}")
        Call<ArrayList<EntryModel>> getFilteredByKeyword(@Path("keyword") String str);
    }

    public APISearchKeywordService(ISearchByKeywordUseCase iSearchByKeywordUseCase) {
        this.iSearchByKeywordUseCase = iSearchByKeywordUseCase;
    }

    public void filterCategoryByKeyword(String c, String ft, String vt, String k) {
        this.cat = c;
        this.fType = ft;
        this.vType = vt;
        this.keyword = k;
        try {
            Call<ArrayList<EntryModel>> clonedCall = ((GetFilteredByKeywordFromMainServer) APIServiceGeneratorMainServer.createService(GetFilteredByKeywordFromMainServer.class)).getFilteredByKeyword(k).clone();
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
            String statusMsg = "";
            String statusError = "";
            if (response.isSuccess()) {
                int statusCode = response.code();
                if (((ArrayList) response.body()).size() >= 0) {
                    this.iSearchByKeywordUseCase.onFilterByKeywordSuccess((ArrayList) response.body(), this.cat, this.vType, this.fType, this.keyword);
                    return;
                } else {
                    this.iSearchByKeywordUseCase.sendErrorFromAPI("abe isn't feeling so good right now");
                    return;
                }
            }
            this.iSearchByKeywordUseCase.sendErrorFromAPI("abe isn't feeling so good right now");
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            this.iSearchByKeywordUseCase.sendErrorFromAPI("abe isn't feeling so good right now");
        }
    }

    public void onFailure(Throwable t) {
        try {
            t.toString();
            this.iSearchByKeywordUseCase.sendErrorFromAPI("abe isn't feeling so good right now");
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            this.iSearchByKeywordUseCase.sendErrorFromAPI("abe isn't feeling so good right now");
        }
    }
}
