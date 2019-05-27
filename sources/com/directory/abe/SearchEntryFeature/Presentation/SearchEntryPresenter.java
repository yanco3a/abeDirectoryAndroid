package com.directory.abe.SearchEntryFeature.Presentation;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import com.directory.abe.AnalyticsApp;
import com.directory.abe.C0246R;
import com.directory.abe.GSONModels.EntryModel;
import com.directory.abe.IAnalyse;
import com.directory.abe.ISQLite;
import com.directory.abe.SQLiteDatabase.SQLiteModel.FRContract.FeedRating;
import com.directory.abe.SQLiteDatabase.Services.SQLiteHelper;
import com.directory.abe.SearchEntryFeature.Services.ICheckConnections;
import com.directory.abe.SearchEntryFeature.UseCases.IGoogleApiCientTools;
import com.directory.abe.SearchEntryFeature.UseCases.ISearchEntryPresenter;
import com.directory.abe.SearchEntryFeature.UseCases.SearchByKeywordUseCase;
import com.directory.abe.SearchEntryFeature.UseCases.SearchByRadiusUseCase;
import com.directory.abe.SearchEntryFeature.UseCases.SearchByRatingUseCase;
import com.google.android.gms.analytics.HitBuilders.EventBuilder;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;

public class SearchEntryPresenter implements ISearchEntryPresenter, OnMapReadyCallback, OnInfoWindowClickListener, ICheckConnections, ISQLite, IGoogleApiCientTools, IAnalyse {
    private static final String CATEGORY_DEFAULT = "Categories";
    private static final double DEFAULT_LAT = 51.512909d;
    private static final double DEFAULT_LNG = -0.091324d;
    private static final String KEYWORD_DEFAULT = "";
    private static final String KEYWORD_TAB = "byKeyword";
    private static final String RADIUS_TAB = "byRadius";
    private static final float RATING_DEFAULT = 0.0f;
    private static final String RATING_TAB = "byRating";
    private static final int REQUEST_CODE = 999;
    private static final String TAG = SearchEntryPresenter.class.getSimpleName();
    private SearchEntryView activity;
    private Tracker appTracker;
    String category = CATEGORY_DEFAULT;
    private Context context;
    private ArrayList<EntryModel> detailViewObjs;
    private ArrayList<LatLng> fVendorLatLngs;
    private String filterType;
    private GoogleMap googleMap;
    private String keyword;
    private String[] keywordArray;
    private SearchEntryKeywordLFrag keywordFragment;
    private boolean keywordValid;
    private FragmentManager manager;
    private SupportMapFragment mapFragment;
    private ArrayList<EntryModel> modelObjs;
    private ArrayList<EntryModel> models;
    private ArrayList<LatLng> noOnlineLatLngs;
    private ArrayList<EntryModel> noOnlineVendors;
    double radius = 20.0d;
    private SearchEntryLFrag radiusFragment;
    private float rating = 5.0f;
    private SearchEntryRatingLFrag ratingFragment;
    private SearchByKeywordUseCase searchByKeywordUseCase;
    private SearchByRadiusUseCase searchByRadiusUseCase;
    private SearchByRatingUseCase searchByRatingUseCase;
    private ISearchEntryView searchEntryView;
    private SQLiteHelper sqLiteHelper;
    private FragmentTransaction transaction;
    private String userCategory = "";
    private Location userLocation;
    String viewType;

    public SearchEntryPresenter(ISearchEntryView searchEntryView, SearchEntryLFrag radiusFragment, SearchEntryRatingLFrag ratingFragment, SearchEntryKeywordLFrag keywordFragment, Context context, Activity activity) {
        this.searchEntryView = searchEntryView;
        this.radiusFragment = radiusFragment;
        this.ratingFragment = ratingFragment;
        this.keywordFragment = keywordFragment;
        this.context = context;
        this.activity = (SearchEntryView) activity;
        this.searchByRadiusUseCase = new SearchByRadiusUseCase(this, context, activity);
        this.searchByRatingUseCase = new SearchByRatingUseCase(this, context, activity);
        this.searchByKeywordUseCase = new SearchByKeywordUseCase(this, context, activity);
        this.filterType = "Radius";
        this.viewType = "List";
        this.appTracker = ((AnalyticsApp) activity.getApplication()).getDefaultTracker();
    }

    public void initListAndMap() {
        if (this.activity != null) {
            this.mapFragment = (SupportMapFragment) this.activity.getSupportFragmentManager().findFragmentById(C0246R.id.mapRadius);
            if (this.mapFragment != null) {
                this.manager = this.activity.getSupportFragmentManager();
                this.transaction = this.manager.beginTransaction();
                this.transaction.hide(this.mapFragment);
                this.transaction.replace(C0246R.id.fragRadiusLayout, this.radiusFragment, "radiusFragment");
                this.transaction.replace(C0246R.id.fragKeywordLayout, this.keywordFragment, "keywordFragment");
                this.transaction.commit();
            }
        }
    }

    public void searchByRadiusMode(String cat) {
        try {
            this.filterType = "Radius";
            if (!isValidCategory(cat)) {
                sendMessagesToUser("Please select a category");
            } else if (!isOnline(this.context).booleanValue()) {
                resetCatSpinnerDefault();
                sendMessagesToUser("Abe doesn't have a good net connection right now");
            } else if (!getPermissions(this.context, this.activity)) {
                resetCatSpinnerDefault();
                sendMessagesToUser("Please enable location service in Settings to use this service");
            } else if (isLocationEnabled()) {
                if (isMapOn()) {
                    this.viewType = "Map";
                } else {
                    this.viewType = "List";
                }
                this.searchByRadiusUseCase.getFilteredByRadius(cat, this.filterType, this.viewType, this.radius);
                getRadiusByServer(cat, this.radius, this.viewType, this.filterType);
            } else {
                resetCatSpinnerDefault();
                sendMessagesToUser("Please enable Google's locations Services on your device's Locations Services menu");
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                resetCatSpinnerDefault();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void resetCatSpinnerDefault() {
        if (this.activity != null) {
            this.activity.resetCatSpinnerDefault();
        }
    }

    private boolean isMapOn() {
        if (this.activity != null) {
            return this.activity.isMapOn();
        }
        return false;
    }

    public void searchByRatingMode(String cat) {
        this.filterType = FeedRating.TABLE_NAME;
        try {
            if (isValidCategory(cat)) {
                setupDBHelper(this.context);
                if (getSQLiteHelper().getListingCount() != 0) {
                    getRatingBySQLite(getSQLiteHelper(), cat);
                    return;
                } else if (isOnline(this.context).booleanValue()) {
                    getAllForSQLite(cat);
                    getRatingByServer(cat, this.rating, this.viewType, this.filterType);
                    return;
                } else {
                    sendMessagesToUser("Abe doesn't have a good net connection right now");
                    return;
                }
            }
            sendMessagesToUser("Please select a category");
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            sendMessagesToUser("Abe isn't feeling so good right now :( ");
        }
    }

    public void analyseScreen(String action, String label, String category) {
        try {
            this.appTracker.send(new EventBuilder().setAction(action).setLabel(label).setCategory(category).setValue(1).build());
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void resumeTracker(String tag) {
        try {
            this.appTracker.setScreenName("Screen~" + tag);
            this.appTracker.send(new ScreenViewBuilder().build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUpAnalytics(Activity activity) {
        try {
            this.appTracker = ((AnalyticsApp) activity.getApplication()).getDefaultTracker();
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void searchByKeywordMode(String userSearch) {
        this.filterType = "Keyword";
        this.viewType = "List";
        if (!isKeywordValid(userSearch)) {
            sendMessagesToUser("Abe doesn't understand... please try a postcode, business name or a category");
        } else if (!isOnline(this.context).booleanValue()) {
            sendMessagesToUser("Abe doesn't have a good net connection right now");
        } else if (getPermissions(this.context, this.activity)) {
            this.searchByKeywordUseCase.getVendorByKeyword(this.category, this.filterType, this.viewType, this.keyword);
        } else {
            sendMessagesToUser("Please enable location service in Settings to use this service");
        }
    }

    private void getAllForSQLite(String cat) {
        if (isValidCategory(cat)) {
            this.searchByRatingUseCase.getDataForSQLiteFromServer();
        } else {
            sendMessagesToUser("Please select a category");
        }
    }

    public void getRatingByServer(String cat, float rating, String viewType, String filterType) {
        if (isValidCategory(cat)) {
            this.searchByRatingUseCase.getFilteredByRating(cat, rating, viewType, filterType);
        } else {
            sendMessagesToUser("Please select a category");
        }
    }

    public void createGoogleAPI() {
        this.searchByRadiusUseCase.onCreateGoogleAPI();
    }

    public void getRadiusByServer(String cat, double radius, String viewType, String filterType) {
        if (isValidCategory(cat)) {
            this.searchByRadiusUseCase.getFilteredByRadius(cat, filterType, viewType, radius);
        } else {
            sendMessagesToUser("Please select a category");
        }
    }

    public void getRatingBySQLite(SQLiteHelper sqLiteHelper, String cat) {
        if (isValidCategory(cat)) {
            this.searchByRatingUseCase.getVendorByCategorySQLite(sqLiteHelper, cat, this.filterType, this.viewType, this.rating);
            return;
        }
        sendMessagesToUser("Please select a category");
    }

    public boolean isValidCategory(String cat) {
        if (cat.equals(CATEGORY_DEFAULT)) {
            return false;
        }
        return true;
    }

    public boolean isKeywordValid(String userSearch) {
        if (userSearch == null) {
            return false;
        }
        try {
            if (userSearch.length() <= 2 || !userSearch.matches("^[a-zA-Z0-9\\s .,]*$")) {
                return false;
            }
            this.keyword = userSearch.trim();
            return true;
        } catch (Exception ne) {
            ne.printStackTrace();
            ne.getMessage();
            return false;
        }
    }

    public ArrayList<EntryModel> getModel() {
        return this.models;
    }

    public void setModel(ArrayList<EntryModel> models) {
        this.models = models;
    }

    public void sendMessagesToUser(String msg) {
        if (this.activity != null) {
            this.activity.showLUIMessages(msg);
        }
    }

    public boolean hasUserAlreadySelectedCategory() {
        if (this.modelObjs == null || this.userLocation == null) {
            return false;
        }
        if (((EntryModel) this.modelObjs.get(0)).listingTradeCategory.equals(this.category)) {
            return true;
        }
        return false;
    }

    public void setViewType() {
    }

    public void searchByRadius(boolean b) {
        if (b) {
            this.filterType = "Radius";
        }
    }

    public void searchByRating(boolean b) {
        if (b) {
            this.filterType = FeedRating.TABLE_NAME;
        }
    }

    public void onSearchByCatSuccess(final ArrayList<EntryModel> objs, final String viewType, final String filterType) {
        if (objs != null) {
            this.modelObjs = objs;
            this.fVendorLatLngs = getAllLatLng(objs);
            if (this.activity != null) {
                this.activity.runOnUiThread(new Runnable() {
                    public void run() {
                        String str = filterType;
                        Object obj = -1;
                        switch (str.hashCode()) {
                            case -1854711630:
                                if (str.equals("Radius")) {
                                    obj = null;
                                    break;
                                }
                                break;
                            case -1854235203:
                                if (str.equals(FeedRating.TABLE_NAME)) {
                                    obj = 1;
                                    break;
                                }
                                break;
                            case 850245065:
                                if (str.equals("Keyword")) {
                                    obj = 2;
                                    break;
                                }
                                break;
                        }
                        switch (obj) {
                            case null:
                                SearchEntryPresenter.this.updateRadiusUI(objs, viewType, SearchEntryPresenter.this.fVendorLatLngs);
                                return;
                            case 1:
                                SearchEntryPresenter.this.updateRatingUI(objs, viewType, SearchEntryPresenter.this.fVendorLatLngs);
                                return;
                            case 2:
                                SearchEntryPresenter.this.updateKeywordUI(objs, viewType, SearchEntryPresenter.this.fVendorLatLngs);
                                return;
                            default:
                                return;
                        }
                    }
                });
            }
        }
    }

    private void updateRadiusUI(ArrayList<EntryModel> objs, String viewType, ArrayList<LatLng> vendorLatLngs) {
        try {
            if (viewType.equals("List")) {
                listViewSetup(this.radiusFragment);
                this.radiusFragment.removeDuplicateListings(objs);
            } else if (viewType.equals("Map")) {
                mapViewSetup(this.radiusFragment);
                initMapIfNeeded(vendorLatLngs, objs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateRatingUI(ArrayList<EntryModel> objs, String viewType, ArrayList<LatLng> vendorLatLngs) {
        try {
            if (viewType.equals("List")) {
                listViewSetup(this.ratingFragment);
                this.ratingFragment.removeDuplicateListings(objs, getDetailViewObjs());
            } else if (viewType.equals("Map")) {
                mapViewSetup(this.ratingFragment);
                initMapIfNeeded(vendorLatLngs, objs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateKeywordUI(ArrayList<EntryModel> objs, String viewType, ArrayList<LatLng> arrayList) {
        try {
            if (viewType.equals("List")) {
                listViewSetup(this.keywordFragment);
                this.keywordFragment.removeDuplicateListings(objs);
            } else if (viewType.equals("Map")) {
                mapViewSetup(this.keywordFragment);
                initMapIfNeeded(this.fVendorLatLngs, objs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<LatLng> getAllLatLng(ArrayList<EntryModel> objs) {
        ArrayList fLatLngs = new ArrayList();
        for (int i = 0; i < objs.size(); i++) {
            fLatLngs.add(new LatLng(((EntryModel) objs.get(i)).getVendorLat(), ((EntryModel) objs.get(i)).getVendorLng()));
        }
        return fLatLngs;
    }

    public void onSearchByCatFailure(final String errBody) {
        try {
            if (this.activity != null) {
                this.activity.runOnUiThread(new Runnable() {
                    public void run() {
                        String currentTab = SearchEntryPresenter.this.activity.getCurrentTab();
                        Object obj = -1;
                        switch (currentTab.hashCode()) {
                            case 240019858:
                                if (currentTab.equals(SearchEntryPresenter.KEYWORD_TAB)) {
                                    obj = 2;
                                    break;
                                }
                                break;
                            case 1450739657:
                                if (currentTab.equals(SearchEntryPresenter.RADIUS_TAB)) {
                                    obj = null;
                                    break;
                                }
                                break;
                            case 1451216084:
                                if (currentTab.equals(SearchEntryPresenter.RATING_TAB)) {
                                    obj = 1;
                                    break;
                                }
                                break;
                        }
                        switch (obj) {
                            case null:
                                if (SearchEntryPresenter.this.radiusFragment != null) {
                                    SearchEntryPresenter.this.radiusFragment.getListView().invalidateViews();
                                    break;
                                }
                                break;
                            case 1:
                                if (SearchEntryPresenter.this.ratingFragment != null) {
                                    SearchEntryPresenter.this.ratingFragment.getListView().invalidateViews();
                                    break;
                                }
                                break;
                            case 2:
                                if (SearchEntryPresenter.this.keywordFragment != null) {
                                    SearchEntryPresenter.this.keywordFragment.getListView().invalidateViews();
                                    break;
                                }
                                break;
                            default:
                                SearchEntryPresenter.this.sendMessagesToUser("Abe isn't feeling so good right now");
                                break;
                        }
                        SearchEntryPresenter.this.sendMessagesToUser(errBody);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onFilterByKeywordSuccess(ArrayList<EntryModel> arrayList, String cat, String vType, String keyword) {
    }

    public void onFilterByRatingSuccess(final ArrayList<EntryModel> objs, ArrayList<EntryModel> arrayList, String cat, String vType, float rating) {
        if (objs != null) {
            this.modelObjs = objs;
            this.fVendorLatLngs = getAllLatLng(objs);
            if (this.activity != null) {
                this.activity.runOnUiThread(new Runnable() {
                    public void run() {
                        if (SearchEntryPresenter.this.viewType.equals("List")) {
                            SearchEntryPresenter.this.listViewSetup(SearchEntryPresenter.this.ratingFragment);
                            SearchEntryPresenter.this.radiusFragment.removeDuplicateListings(objs);
                        } else if (SearchEntryPresenter.this.viewType.equals("Map")) {
                            SearchEntryPresenter.this.mapViewSetup(SearchEntryPresenter.this.radiusFragment);
                            SearchEntryPresenter.this.initMapIfNeeded(SearchEntryPresenter.this.fVendorLatLngs, objs);
                        }
                    }
                });
            }
        }
    }

    public void storeDetailedViewObs(ArrayList<EntryModel> detailViewObjs) {
        this.detailViewObjs = detailViewObjs;
    }

    public ArrayList<EntryModel> getDetailViewObjs() {
        return this.detailViewObjs;
    }

    public void insertAllToSQLite(ArrayList<EntryModel> arrayList) {
    }

    public void mapViewSetup(ListFragment frag) {
        if (frag != null && this.activity != null && frag.getView() != null && frag.getView().getVisibility() == 0) {
            this.manager = this.activity.getSupportFragmentManager();
            this.transaction = this.manager.beginTransaction();
            this.transaction.hide(frag);
            this.transaction.show(this.mapFragment);
            this.transaction.commit();
        }
    }

    public void listViewSetup(ListFragment frag) {
        if (this.mapFragment != null && this.activity != null && this.mapFragment.getView() != null && this.filterType.equals("Radius") && this.mapFragment.getView().getVisibility() == 0) {
            this.manager = this.activity.getSupportFragmentManager();
            this.transaction = this.manager.beginTransaction();
            this.transaction.hide(this.mapFragment);
            this.transaction.show(frag);
            this.transaction.commit();
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (this.noOnlineVendors == null) {
            defaultMap(DEFAULT_LAT, DEFAULT_LNG);
        } else if (this.noOnlineVendors.size() > 0) {
            updateMap(this.noOnlineLatLngs, this.noOnlineVendors);
        } else {
            defaultMap(DEFAULT_LAT, DEFAULT_LNG);
        }
    }

    private void initMapIfNeeded(ArrayList<LatLng> fVendorLatLngs, ArrayList<EntryModel> modelObjs) {
        try {
            this.fVendorLatLngs = fVendorLatLngs;
            this.noOnlineVendors = new ArrayList();
            this.noOnlineLatLngs = new ArrayList();
            for (int i = 0; i < modelObjs.size(); i++) {
                if (!((EntryModel) modelObjs.get(i)).getVendorType().equals("Online")) {
                    this.noOnlineVendors.add(modelObjs.get(i));
                    this.noOnlineLatLngs.add(fVendorLatLngs.get(i));
                }
            }
            if (this.noOnlineVendors.size() == 0) {
            }
            if (this.googleMap != null) {
                this.googleMap.clear();
                if (this.noOnlineVendors.size() > 0) {
                    updateMap(this.noOnlineLatLngs, this.noOnlineVendors);
                    return;
                }
                this.googleMap.clear();
                defaultMap(DEFAULT_LAT, DEFAULT_LNG);
            } else if (this.noOnlineVendors.size() > 0) {
                this.mapFragment.getMapAsync(this);
            } else {
                this.mapFragment.getMapAsync(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void updateMap(ArrayList<LatLng> fVendorLatLngs, ArrayList<EntryModel> fModelObjs) {
        if (this.googleMap != null) {
            int i = 0;
            while (i < fModelObjs.size()) {
                if (((EntryModel) fModelObjs.get(i)).getVendorLat() == 0.0d || ((EntryModel) fModelObjs.get(i)).getVendorLng() == 0.0d) {
                    defaultMap(DEFAULT_LAT, DEFAULT_LNG);
                } else {
                    LatLng latLng = new LatLng(((EntryModel) fModelObjs.get(i)).getVendorLat(), ((EntryModel) fModelObjs.get(i)).getVendorLng());
                    this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom((LatLng) fVendorLatLngs.get(i), 10.0f));
                    this.googleMap.addMarker(new MarkerOptions().position(latLng).title(((EntryModel) fModelObjs.get(i)).getVendorName()));
                    this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom((LatLng) fVendorLatLngs.get(i), 10.0f));
                }
                i++;
            }
        }
    }

    public void onInfoWindowClick(Marker marker) {
        try {
            marker.getSnippet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void defaultMap(double lat, double lng) {
        if (this.googleMap != null) {
            this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 10.0f));
        }
    }

    public void setStoredRadius(String radiusString) {
        double storedRadius = (double) Integer.parseInt(radiusString.replaceAll("\\D+", ""));
        if (this.radius != storedRadius) {
            this.radius = storedRadius;
        }
    }

    public void setStoredCategory(String userCategory) {
        this.userCategory = userCategory;
        if (!userCategory.equals(this.category) && !userCategory.equals(CATEGORY_DEFAULT)) {
            this.category = userCategory;
        }
    }

    public void setStoredViewType(String storedViewType) {
        if (!storedViewType.equals(this.viewType)) {
            this.viewType = storedViewType;
        }
    }

    public void setStoredRating(String ratingString) {
        int storedRating = Integer.parseInt(ratingString.replaceAll("\\D+", ""));
        if (this.rating != ((float) storedRating)) {
            this.rating = (float) storedRating;
        }
    }

    public double getRadius() {
        return this.radius;
    }

    public ArrayList<EntryModel> getObjs() {
        return this.modelObjs;
    }

    public Boolean isOnline(Context context) {
        if (context != null) {
            try {
                NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
                    if (networkInfo != null) {
                        return Boolean.valueOf(false);
                    }
                    return Boolean.valueOf(false);
                } else if (networkInfo.isConnected()) {
                    return Boolean.valueOf(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
                return Boolean.valueOf(false);
            }
        }
        return Boolean.valueOf(false);
    }

    public boolean getPermissions(Context context, Activity activity) {
        if (context == null) {
            return false;
        }
        if (ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION") == 0 && ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            return true;
        }
        sendMessagesToUser("Please enable location services permissions in Settings");
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, "android.permission.ACCESS_FINE_LOCATION") || ActivityCompat.shouldShowRequestPermissionRationale(activity, "android.permission.ACCESS_COARSE_LOCATION")) {
            sendMessagesToUser("Please enable location services permissions in Settings");
            ActivityCompat.requestPermissions(activity, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, REQUEST_CODE);
        } else {
            sendMessagesToUser("Please enable location services permissions in Settings");
        }
        return false;
    }

    public void setupDBHelper(Context c) {
        this.sqLiteHelper = new SQLiteHelper(c);
    }

    public SQLiteHelper getSQLiteHelper() {
        return this.sqLiteHelper;
    }

    public Context getContext() {
        return this.context;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public boolean isGoogleAPIClientConnected() {
        return this.searchByRadiusUseCase.isGoogleAPIClientConnected();
    }

    public void disconnectGoogleApiClient() {
        this.searchByRadiusUseCase.disconnectGoogleApiClient();
    }

    public boolean isRequestingLocationUpdates() {
        return this.searchByRadiusUseCase.isRequestingLocationUpdates();
    }

    public void connectGoogleApi() {
        this.searchByRadiusUseCase.connectGoogleApi();
    }

    public boolean isLocationEnabled() {
        return this.searchByRadiusUseCase.isLocationEnabled();
    }
}
