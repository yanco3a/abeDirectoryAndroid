package com.directory.abe.SearchEntryFeature.UseCases;

import android.app.Activity;
import android.content.Context;
import com.directory.abe.GSONModels.EntryModel;
import com.directory.abe.SearchEntryFeature.Presentation.SearchEntryView;
import com.directory.abe.SearchEntryFeature.Services.APISearchKeywordService;
import java.util.ArrayList;

public class SearchByKeywordUseCase implements ISearchByKeywordUseCase {
    private final SearchEntryView activity;
    private APISearchKeywordService apiSearchService = new APISearchKeywordService(this);
    private final Context context;
    private final ISearchEntryPresenter presenter;

    public SearchByKeywordUseCase(ISearchEntryPresenter presenter, Context context, Activity activity) {
        this.presenter = presenter;
        this.context = context;
        this.activity = (SearchEntryView) activity;
    }

    public void getVendorByKeyword(String category, String filterType, String viewType, String keyword) {
        this.apiSearchService.filterCategoryByKeyword(category, filterType, viewType, keyword);
    }

    public void onSearchByCatSuccess(ArrayList<EntryModel> entryModels, String cat, String fType, String vType, double radius, String keyword) {
        final ArrayList<EntryModel> arrayList = entryModels;
        final String str = cat;
        final String str2 = vType;
        final String str3 = keyword;
        new Thread(new Runnable() {
            public void run() {
                SearchByKeywordUseCase.this.presenter.onFilterByKeywordSuccess(arrayList, str, str2, str3);
            }
        }).start();
    }

    public void onFilterByKeywordSuccess(ArrayList<EntryModel> model, String cat, String vType, String fType, String rating) {
        this.presenter.onSearchByCatSuccess(model, vType, fType);
    }

    public void sendErrorFromAPI(String string) {
        this.presenter.onSearchByCatFailure("Abe isn't feeling so good right now");
    }

    public void onFilterByKeywordSQLiteSuccess() {
    }

    public void onFilterByKeywordSQLiteFailure() {
    }

    public void onGetDataForSQLiteFromServerSuccess(ArrayList<EntryModel> arrayList) {
    }
}
