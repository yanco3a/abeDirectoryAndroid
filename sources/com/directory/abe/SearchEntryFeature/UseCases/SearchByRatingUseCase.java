package com.directory.abe.SearchEntryFeature.UseCases;

import android.app.Activity;
import android.content.Context;
import com.directory.abe.GSONModels.EntryModel;
import com.directory.abe.SQLiteDatabase.Services.SQLiteHelper;
import com.directory.abe.SearchEntryFeature.Services.APIGetAllForSQLiteService;
import com.directory.abe.SearchEntryFeature.Services.APISearchRatingService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class SearchByRatingUseCase implements ISearchByRatingUseCase {
    private static final String TAG = SearchByRatingUseCase.class.getSimpleName();
    private final APIGetAllForSQLiteService apiGetDataForSQLiteFromServer = new APIGetAllForSQLiteService(this);
    private final APISearchRatingService apiSearchRatingService = new APISearchRatingService(this);
    private final ISearchEntryPresenter presenter;
    private float userRating;

    public SearchByRatingUseCase(ISearchEntryPresenter presenter, Context context, Activity activity) {
        this.presenter = presenter;
    }

    public void getVendorByCategorySQLite(SQLiteHelper sqLiteHelper, String category, String filterType, String viewType, float rating) {
        final SQLiteHelper sQLiteHelper = sqLiteHelper;
        final String str = category;
        final String str2 = filterType;
        final String str3 = viewType;
        final float f = rating;
        new Thread(new Runnable() {
            public void run() {
                sQLiteHelper.selectCatByRating(str, str2, str3, f);
            }
        }).start();
    }

    public void onFilterByRatingSQLiteSuccess() {
    }

    public void onFilterByRatingSQLiteFailure() {
    }

    public void onGetDataForSQLiteFromServerSuccess(ArrayList<EntryModel> arrayList) {
    }

    public void getDataForSQLiteFromServer() {
        this.apiGetDataForSQLiteFromServer.getDataForSQLiteFromServer();
    }

    public void onFilterByRatingSuccess(ArrayList<EntryModel> model, String cat, String vType, String fType, float rating) {
        this.userRating = rating;
        ArrayList<EntryModel> models = new ArrayList();
        HashMap<Integer, List<EntryModel>> hashMap = new HashMap();
        List<EntryModel> ratings = new ArrayList();
        int id = 0;
        if (model != null) {
            for (int i = 0; i < model.size(); i++) {
                if (((EntryModel) model.get(i)).getRatingListingId() != id) {
                    id = ((EntryModel) model.get(i)).getRatingListingId();
                    ratings = new ArrayList();
                    ratings.add(new EntryModel(((EntryModel) model.get(i)).getVendorId(), ((EntryModel) model.get(i)).getVendorName(), ((EntryModel) model.get(i)).getVendorAddress1(), ((EntryModel) model.get(i)).getVendorAddress2(), ((EntryModel) model.get(i)).getVendorAddress3(), ((EntryModel) model.get(i)).getVendorPostcode(), ((EntryModel) model.get(i)).getVendorTelephone(), ((EntryModel) model.get(i)).getVendorEmail(), ((EntryModel) model.get(i)).getVendorWebsite(), ((EntryModel) model.get(i)).getVendorType(), ((EntryModel) model.get(i)).getVendorLat(), ((EntryModel) model.get(i)).getVendorLng(), ((EntryModel) model.get(i)).getListingId(), ((EntryModel) model.get(i)).getListingDate(), ((EntryModel) model.get(i)).getListingPoints(), ((EntryModel) model.get(i)).getListingIsFeatured(), ((EntryModel) model.get(i)).getListingReferralCode(), ((EntryModel) model.get(i)).getListingTradeCategory(), ((EntryModel) model.get(i)).getListingTradeVerified(), ((EntryModel) model.get(i)).getListingTradeSummary(), ((EntryModel) model.get(i)).getListingTradeDate(), ((EntryModel) model.get(i)).getRatingId(), ((EntryModel) model.get(i)).getRatingListingId(), ((EntryModel) model.get(i)).getRatingUserId(), ((EntryModel) model.get(i)).getUsername(), ((EntryModel) model.get(i)).getRatingValue(), ((EntryModel) model.get(i)).getRatingReview()));
                    hashMap.put(Integer.valueOf(((EntryModel) model.get(i)).getRatingListingId()), ratings);
                } else {
                    ratings.add(new EntryModel(((EntryModel) model.get(i)).getVendorId(), ((EntryModel) model.get(i)).getVendorName(), ((EntryModel) model.get(i)).getVendorAddress1(), ((EntryModel) model.get(i)).getVendorAddress2(), ((EntryModel) model.get(i)).getVendorAddress3(), ((EntryModel) model.get(i)).getVendorPostcode(), ((EntryModel) model.get(i)).getVendorTelephone(), ((EntryModel) model.get(i)).getVendorEmail(), ((EntryModel) model.get(i)).getVendorWebsite(), ((EntryModel) model.get(i)).getVendorType(), ((EntryModel) model.get(i)).getVendorLat(), ((EntryModel) model.get(i)).getVendorLng(), ((EntryModel) model.get(i)).getListingId(), ((EntryModel) model.get(i)).getListingDate(), ((EntryModel) model.get(i)).getListingPoints(), ((EntryModel) model.get(i)).getListingIsFeatured(), ((EntryModel) model.get(i)).getListingReferralCode(), ((EntryModel) model.get(i)).getListingTradeCategory(), ((EntryModel) model.get(i)).getListingTradeVerified(), ((EntryModel) model.get(i)).getListingTradeSummary(), ((EntryModel) model.get(i)).getListingTradeDate(), ((EntryModel) model.get(i)).getRatingId(), ((EntryModel) model.get(i)).getRatingListingId(), ((EntryModel) model.get(i)).getRatingUserId(), ((EntryModel) model.get(i)).getUsername(), ((EntryModel) model.get(i)).getRatingValue(), ((EntryModel) model.get(i)).getRatingReview()));
                    hashMap.put(Integer.valueOf(((EntryModel) model.get(i)).getRatingListingId()), ratings);
                }
            }
            printMap(hashMap, vType, fType);
        }
    }

    public void printMap(HashMap<Integer, List<EntryModel>> mp, String vType, String fType) {
        try {
            ArrayList<EntryModel> listViewObjs = new ArrayList();
            ArrayList<EntryModel> detailViewObjs = new ArrayList();
            ArrayList<EntryModel> filteredDetailViewObjs = new ArrayList();
            Iterator it = mp.entrySet().iterator();
            while (it.hasNext()) {
                float ratingValue = 0.0f;
                Entry pair = (Entry) it.next();
                ArrayList arrayList = new ArrayList();
                List<EntryModel> model = (List) pair.getValue();
                for (int j = 0; j < model.size(); j++) {
                    ratingValue += ((EntryModel) model.get(j)).getRatingValue();
                    detailViewObjs.add(new EntryModel(((EntryModel) model.get(0)).getVendorId(), ((EntryModel) model.get(j)).getVendorName(), ((EntryModel) model.get(j)).getVendorAddress1(), ((EntryModel) model.get(j)).getVendorAddress2(), ((EntryModel) model.get(j)).getVendorAddress3(), ((EntryModel) model.get(j)).getVendorPostcode(), ((EntryModel) model.get(j)).getVendorTelephone(), ((EntryModel) model.get(j)).getVendorEmail(), ((EntryModel) model.get(j)).getVendorWebsite(), ((EntryModel) model.get(j)).getVendorType(), ((EntryModel) model.get(j)).getVendorLat(), ((EntryModel) model.get(j)).getVendorLng(), ((EntryModel) model.get(j)).getListingId(), ((EntryModel) model.get(j)).getListingDate(), ((EntryModel) model.get(j)).getListingPoints(), ((EntryModel) model.get(j)).getListingIsFeatured(), ((EntryModel) model.get(j)).getListingReferralCode(), ((EntryModel) model.get(j)).getListingTradeCategory(), ((EntryModel) model.get(j)).getListingTradeVerified(), ((EntryModel) model.get(j)).getListingTradeSummary(), ((EntryModel) model.get(j)).getListingTradeDate(), ((EntryModel) model.get(j)).getRatingId(), ((EntryModel) model.get(j)).getRatingListingId(), ((EntryModel) model.get(j)).getRatingUserId(), ((EntryModel) model.get(j)).getUsername(), ((EntryModel) model.get(j)).getRatingValue(), ((EntryModel) model.get(j)).getRatingReview()));
                }
                int count1 = model.size();
                if (ratingValue > 0.0f) {
                    if (ratingValue / ((float) count1) >= this.userRating) {
                        filteredDetailViewObjs.addAll(detailViewObjs);
                        detailViewObjs.clear();
                    } else if (detailViewObjs.size() > 0) {
                        detailViewObjs.clear();
                    }
                } else if (detailViewObjs.size() > 0) {
                    detailViewObjs.clear();
                }
                int count = model.size();
                if (ratingValue > 0.0f && ratingValue / ((float) count) >= this.userRating) {
                    ArrayList<EntryModel> arrayList2 = listViewObjs;
                    arrayList2.add(new EntryModel(((EntryModel) model.get(0)).getVendorId(), ((EntryModel) model.get(0)).getVendorName(), ((EntryModel) model.get(0)).getVendorAddress1(), ((EntryModel) model.get(0)).getVendorAddress2(), ((EntryModel) model.get(0)).getVendorAddress3(), ((EntryModel) model.get(0)).getVendorPostcode(), ((EntryModel) model.get(0)).getVendorTelephone(), ((EntryModel) model.get(0)).getVendorEmail(), ((EntryModel) model.get(0)).getVendorWebsite(), ((EntryModel) model.get(0)).getVendorType(), ((EntryModel) model.get(0)).getVendorLat(), ((EntryModel) model.get(0)).getVendorLng(), ((EntryModel) model.get(0)).getListingId(), ((EntryModel) model.get(0)).getListingDate(), ((EntryModel) model.get(0)).getListingPoints(), ((EntryModel) model.get(0)).getListingIsFeatured(), ((EntryModel) model.get(0)).getListingReferralCode(), ((EntryModel) model.get(0)).getListingTradeCategory(), ((EntryModel) model.get(0)).getListingTradeVerified(), ((EntryModel) model.get(0)).getListingTradeSummary(), ((EntryModel) model.get(0)).getListingTradeDate(), ((EntryModel) model.get(0)).getRatingId(), ((EntryModel) model.get(0)).getRatingListingId(), ((EntryModel) model.get(0)).getRatingUserId(), ((EntryModel) model.get(0)).getUsername(), ratingValue / ((float) count), ((EntryModel) model.get(0)).getRatingReview()));
                }
                it.remove();
            }
            this.presenter.storeDetailedViewObs(filteredDetailViewObjs);
            this.presenter.onSearchByCatSuccess(listViewObjs, vType, fType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendErrorFromAPI(String string) {
        this.presenter.onSearchByCatFailure("Abe isn't feeling so good right now");
    }

    public void getFilteredByRating(String category, float rating, String viewType, String filterType) {
        this.apiSearchRatingService.filterCategoryByRating(category, rating, viewType, filterType);
    }
}
