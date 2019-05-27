package com.directory.abe.FeaturedEntryFeature.UseCases;

import com.directory.abe.FeaturedEntryFeature.Presentation.MainViewPresenter;
import com.directory.abe.FeaturedEntryFeature.Services.APIFeaturedServices;
import com.directory.abe.FeaturedEntryFeature.Services.APIGetVendorListingFeaturedRating;
import com.directory.abe.GSONModels.EntryModel;
import java.util.ArrayList;

public class MainUseCase implements IMainUseCase {
    private static final String TAG = MainUseCase.class.getSimpleName();
    private APIGetVendorListingFeaturedRating apiAll = new APIGetVendorListingFeaturedRating(this);
    private APIFeaturedServices apiFeaturedServices = new APIFeaturedServices(this);
    private final MainViewPresenter presenter;

    public MainUseCase(MainViewPresenter presenter) {
        this.presenter = presenter;
    }

    public void getAll() {
        if (this.apiAll != null) {
            this.apiAll.getVendorListingFeaturedRatingResultInterface();
        }
    }

    public void getFeaturedListing() {
        if (this.apiFeaturedServices != null) {
            this.apiFeaturedServices.getFeaturedEntry();
        }
    }

    public void onFeaturedSuccessMsg(String s) {
    }

    public void onFeaturedSuccess(ArrayList<EntryModel> featuredEntryModel) {
        if (this.presenter != null) {
            this.presenter.onFeaturedListingLoadedSuccess(featuredEntryModel);
        }
    }

    public void onFeaturedFailureMsg(String string) {
        if (this.presenter != null) {
            this.presenter.onFeaturedListingLoadedFailure();
        }
    }

    public void onAllSuccess(ArrayList<EntryModel> arrayList) {
    }

    public void sendAllResponseFromMainServer(String s) {
    }
}
