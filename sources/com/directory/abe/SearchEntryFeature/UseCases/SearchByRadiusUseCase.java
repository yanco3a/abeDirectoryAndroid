package com.directory.abe.SearchEntryFeature.UseCases;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import com.directory.abe.GSONModels.EntryModel;
import com.directory.abe.SearchEntryFeature.Presentation.SearchEntryView;
import com.directory.abe.SearchEntryFeature.Services.APIGoogleServices;
import com.directory.abe.SearchEntryFeature.Services.APISearchServices;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;
import java.util.ArrayList;

public class SearchByRadiusUseCase implements ISearchUseCase, IFilter, IGoogleApiCientTools {
    private static final String TAG = SearchByRadiusUseCase.class.getSimpleName();
    private final SearchEntryView activity;
    private ArrayList<LatLng> addressesWLatLngs;
    private ArrayList<LatLng> allLatLngs;
    private APIGoogleServices apiGoogleServices = new APIGoogleServices(this);
    private APISearchServices apiSearchServices = new APISearchServices(this);
    private ArrayList<LatLng> bestLatLngs;
    private String category;
    private final Context context;
    private String filterType;
    private ArrayList<LatLng> filteredAddressesWithLatLngs;
    private boolean isGoogleServiceCreated;
    private boolean isLocationAvailable;
    private ISearchEntryPresenter presenter;
    private double radius;
    private double rating;
    private LatLng userLatLng;
    private String viewType;

    public SearchByRadiusUseCase(ISearchEntryPresenter presenter, Context context, Activity activity) {
        this.presenter = presenter;
        this.context = context;
        this.activity = (SearchEntryView) activity;
    }

    public void getFilteredByRadius(String cat, String ft, String vT, double r) {
        setValues(cat, ft, vT, r);
        if (isGoogleLocationAPIAvail()) {
            sendMainServerRequest(cat, ft, vT, r);
        } else {
            this.presenter.sendMessagesToUser("Abe can't seem to obtain your location");
        }
    }

    public void sendMainServerRequest(String cat, String ft, String vT, double r) {
        this.apiSearchServices.getVendorCategoryListing(cat, ft, vT, r);
    }

    public boolean isGoogleLocationAPIAvail() {
        this.isGoogleServiceCreated = this.apiGoogleServices.isGoogleAPIClientConnected();
        if (this.isGoogleServiceCreated) {
            return true;
        }
        return false;
    }

    public void setUserLatLng(LatLng latLng) {
        this.userLatLng = latLng;
    }

    public LatLng getUserLatLng() {
        return this.userLatLng;
    }

    public void onCreateGoogleAPI() {
        this.isGoogleServiceCreated = this.apiGoogleServices.initiateGoogleLocationAndUpdates(this.context, this.activity);
    }

    public boolean handleNewLocation(Location userLocation) {
        if (userLocation != null) {
            try {
                setUserLatLng(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()));
                this.isLocationAvailable = true;
            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
                return false;
            }
        }
        this.isLocationAvailable = false;
        return this.isLocationAvailable;
    }

    public void onGoogleLocationResponse(Location lastLocation) {
        if (!handleNewLocation(lastLocation)) {
        }
    }

    public void onSearchByCatSuccess(ArrayList<EntryModel> entryModels, String cat, String fType, String vType, double radius) {
        filterReturnedCategory(entryModels, cat, fType, vType, radius);
    }

    public void filterReturnedCategory(ArrayList<EntryModel> entryModels, String cat, String fType, String vType, double radius) {
        this.bestLatLngs = new ArrayList();
        if (this.activity == null) {
            return;
        }
        if (entryModels == null) {
            this.presenter.onSearchByCatFailure("Abe requires that you enable location settings & check that you're online");
        } else if (fType.equals("Radius")) {
            this.presenter.onSearchByCatSuccess(filterByRadius(radius, entryModels), vType, fType);
        }
    }

    public ArrayList<EntryModel> filterByRating(double rating, ArrayList<EntryModel> arrayList) {
        return null;
    }

    public ArrayList<EntryModel> filterByRadius(double radius, ArrayList<EntryModel> vendors) {
        ArrayList<EntryModel> filteredObjs = new ArrayList();
        if (vendors != null) {
            int i = 0;
            while (i < vendors.size()) {
                if (!(((EntryModel) vendors.get(i)).getVendorType() == null || ((EntryModel) vendors.get(i)).getVendorPostcode() == null)) {
                    if (((EntryModel) vendors.get(i)).getVendorType().equals("Online")) {
                        filteredObjs.add(vendors.get(i));
                    } else if (((EntryModel) vendors.get(i)).getVendorPostcode().equals("") || ((EntryModel) vendors.get(i)).getVendorPostcode().equals("NULL")) {
                        filteredObjs.add(vendors.get(i));
                    } else {
                        LatLng vendorLatLng = new LatLng(((EntryModel) vendors.get(i)).getVendorLat(), ((EntryModel) vendors.get(i)).getVendorLng());
                        if (this.userLatLng != null) {
                            double distance = compareUserAndVendorLatLng(this.userLatLng, vendorLatLng);
                            if (distance != -1.0d && distance <= radius) {
                                filteredObjs.add(vendors.get(i));
                            }
                        }
                    }
                }
                i++;
            }
        }
        return filteredObjs;
    }

    public void setAddressesWithLatLngs(ArrayList<LatLng> a) {
        this.addressesWLatLngs = new ArrayList();
        this.addressesWLatLngs = a;
    }

    public void setAllLatLngs(ArrayList<LatLng> l) {
        this.allLatLngs = new ArrayList();
        this.allLatLngs = l;
    }

    public void setFilteredAddressesWithLatLngs(ArrayList<LatLng> a) {
        this.filteredAddressesWithLatLngs = a;
    }

    public ArrayList<LatLng> getAddressesWithLatLngs() {
        return this.addressesWLatLngs;
    }

    public double compareUserAndVendorLatLng(LatLng userLatLng, LatLng vendorLatLng) {
        try {
            return ((double) Math.round(SphericalUtil.computeDistanceBetween(userLatLng, vendorLatLng))) * 6.21371192E-4d;
        } catch (NullPointerException ne) {
            ne.printStackTrace();
            ne.getMessage();
            return -1.0d;
        }
    }

    public void sendErrorFromApi(int erc, int rec) {
        GoogleApiAvailability.getInstance().getErrorDialog(this.activity, erc, rec).show();
    }

    public void sendErrorFromAPI(String errBody) {
        this.presenter.onSearchByCatFailure("Abe isn't feeling so good right now");
    }

    public void setValues(String category, String ft, String vT, double r) {
        this.category = category;
        this.filterType = ft;
        this.viewType = vT;
        this.radius = r;
    }

    public Context getContext() {
        return this.context;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public boolean isGoogleAPIClientConnected() {
        return this.apiGoogleServices.isGoogleAPIClientConnected();
    }

    public void disconnectGoogleApiClient() {
        this.apiGoogleServices.disconnectGoogleApiClient();
    }

    public void connectGoogleApi() {
        this.apiGoogleServices.connectGoogleApi();
    }

    public boolean isLocationEnabled() {
        if (getUserLatLng() != null) {
            this.isLocationAvailable = true;
        }
        return this.isLocationAvailable;
    }

    public boolean isRequestingLocationUpdates() {
        return this.apiGoogleServices.isRequestingLocationUpdates();
    }
}
