package com.directory.abe.SearchEntryFeature.Presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;
import com.directory.abe.BuildConfig;
import com.directory.abe.C0246R;
import com.directory.abe.FeaturedEntryFeature.Presentation.MainView;
import com.directory.abe.GSONModels.EntryModel;
import com.directory.abe.LoginFeature.Presentation.LoginView;
import com.directory.abe.RegisterFeature.Presentation.RegisterView;
import com.google.android.gms.analytics.Tracker;
import java.util.ArrayList;

public class SearchEntryView extends AppCompatActivity implements OnTabChangeListener, ISearchEntryView, OnItemSelectedListener, OnCheckedChangeListener, OnClickListener {
    private static final String CATEGORY_DEFAULT = "Categories";
    private static final String KEYWORD_DEFAULT = "";
    private static final String KEYWORD_TAB = "byKeyword";
    private static final int RADIUS_DEFAULT = 20;
    private static final String RADIUS_TAB = "byRadius";
    private static final float RATING_DEFAULT = 0.0f;
    private static final String RATING_TAB = "byRating";
    private static final int REQUEST_LOCATION = 1;
    private static final String TAG = SearchEntryView.class.getSimpleName();
    private Activity activity;
    private Tracker appTracker;
    private Button btnMapView;
    private Button btn_keyword;
    private ArrayAdapter<String> categoryAdapter;
    private String categoryName;
    private String categoryNameSelected = CATEGORY_DEFAULT;
    private int check = 0;
    private CheckBox chkb_show_map;
    private Context context;
    private EditText et_keyword_search;
    private ISearchEntryView iSearchEntryView;
    private SearchEntryKeywordLFrag keywordFragment;
    private boolean mapOn;
    private ArrayList<EntryModel> oo;
    private SharedPreferences prefs;
    private Spinner radCatSpinner;
    private double radius;
    private SearchEntryLFrag radiusFragment;
    private ArrayAdapter<String> radiusListAdpater;
    private Spinner radiusSpinner;
    private Spinner ratCatspinner;
    private String rating;
    private SearchEntryRatingLFrag ratingFragment;
    private ArrayAdapter<String> ratingListAdpater;
    private Spinner ratingSpinner;
    private SearchEntryPresenter searchEntryPresenter;
    private String storedCategory;
    private double storedRadius;
    private String storedViewType;
    private TabHost tabHost;
    private int userId;
    private ArrayList<EntryModel> vendorArrayList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0246R.layout.act_search_category_layout);
        this.radius = 20.0d;
        this.mapOn = false;
        this.storedRadius = 20.0d;
        this.storedViewType = "List";
        this.rating = "5";
        this.vendorArrayList = new ArrayList();
        this.oo = new ArrayList();
        Resources res = getResources();
        this.radCatSpinner = (Spinner) findViewById(C0246R.id.spin_category_search_rad);
        this.categoryAdapter = new ArrayAdapter(this, 17367050, res.getStringArray(C0246R.array.category_array));
        this.radCatSpinner.setAdapter(this.categoryAdapter);
        this.radCatSpinner.setSelection(0, false);
        this.radCatSpinner.setOnItemSelectedListener(this);
        String[] radiusList = res.getStringArray(C0246R.array.radius);
        this.radiusSpinner = (Spinner) findViewById(C0246R.id.spin_radius);
        this.radiusListAdpater = new ArrayAdapter(this, 17367050, radiusList);
        this.radiusSpinner.setAdapter(this.radiusListAdpater);
        this.radiusSpinner.setSelection(6, false);
        this.radiusSpinner.setOnItemSelectedListener(this);
        this.et_keyword_search = (EditText) findViewById(C0246R.id.et_keyword_search);
        this.btnMapView = (Button) findViewById(C0246R.id.btn_map_view);
        this.btn_keyword = (Button) findViewById(C0246R.id.btn_keyword);
        this.btn_keyword.setOnClickListener(this);
        this.categoryName = "";
        this.chkb_show_map = (CheckBox) findViewById(C0246R.id.chkb_map_view);
        this.chkb_show_map.setOnCheckedChangeListener(this);
        this.tabHost = (TabHost) findViewById(C0246R.id.tabHost);
        this.tabHost.setup();
        TabSpec tabspec = this.tabHost.newTabSpec(KEYWORD_TAB);
        tabspec.setContent(C0246R.id.tab_by_keyword);
        tabspec.setIndicator("Keyword (any)");
        this.tabHost.addTab(tabspec);
        TabSpec tabspec2 = this.tabHost.newTabSpec(RADIUS_TAB);
        tabspec2.setContent(C0246R.id.tab_by_radius);
        tabspec2.setIndicator("Distance (miles m)");
        this.tabHost.addTab(tabspec2);
        this.tabHost.setOnTabChangedListener(this);
        this.context = getApplicationContext();
        this.activity = this;
        if (this.radiusFragment == null || this.ratingFragment == null || this.keywordFragment == null) {
            this.radiusFragment = new SearchEntryLFrag();
            this.ratingFragment = new SearchEntryRatingLFrag();
            this.keywordFragment = new SearchEntryKeywordLFrag();
        }
        this.prefs = getSharedPreferences(BuildConfig.APPLICATION_ID, 0);
        this.userId = this.prefs.getInt("uId", 0);
        Bundle newExtras = new Bundle();
        newExtras.putInt("userId", this.userId);
        this.radiusFragment.setArguments(newExtras);
        this.keywordFragment.setArguments(newExtras);
        this.searchEntryPresenter = new SearchEntryPresenter(this.iSearchEntryView, this.radiusFragment, this.ratingFragment, this.keywordFragment, this.context, this.activity);
        this.searchEntryPresenter.createGoogleAPI();
        this.searchEntryPresenter.initListAndMap();
        checkIfUserHasSearched();
        this.searchEntryPresenter.setUpAnalytics(this.activity);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onResume() {
        super.onResume();
        try {
            if (!this.searchEntryPresenter.isGoogleAPIClientConnected()) {
                this.searchEntryPresenter.connectGoogleApi();
            }
            this.searchEntryPresenter.resumeTracker(TAG);
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    protected void onPause() {
        if (this.searchEntryPresenter.isGoogleAPIClientConnected()) {
            this.searchEntryPresenter.disconnectGoogleApiClient();
        }
        super.onPause();
    }

    protected void onStop() {
        if (this.searchEntryPresenter.isGoogleAPIClientConnected()) {
            this.searchEntryPresenter.disconnectGoogleApiClient();
        }
        super.onStop();
    }

    protected void onRestart() {
        super.onRestart();
        checkIfUserCanRefresh();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0246R.menu.menu_search, menu);
        return true;
    }

    public String setUserDefinedCategory(AdapterView<?> parent, int pos) {
        if (parent == null) {
            this.categoryNameSelected = CATEGORY_DEFAULT;
        } else {
            this.categoryNameSelected = parent.getItemAtPosition(pos).toString();
        }
        this.searchEntryPresenter.setStoredCategory(this.categoryNameSelected);
        try {
            this.searchEntryPresenter.analyseScreen("category: " + this.categoryNameSelected, "category", "search pg");
            return this.categoryNameSelected;
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            return this.categoryNameSelected;
        }
    }

    public void setUserDefinedRadius(AdapterView<?> parent, int pos) {
        try {
            String radiusSelected = parent.getItemAtPosition(pos).toString();
            this.searchEntryPresenter.setStoredRadius(radiusSelected);
            this.searchEntryPresenter.analyseScreen(radiusSelected + " miles", "by radius", "search pg");
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void setMapViewType(Boolean b) {
        try {
            String whichView;
            if (b.booleanValue()) {
                this.searchEntryPresenter.setStoredViewType("Map");
                whichView = "Map view";
            } else {
                this.searchEntryPresenter.setStoredViewType("List");
                whichView = "List view";
            }
            this.searchEntryPresenter.analyseScreen(whichView, "view types", "search pg");
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void showLUIMessages(String msg) {
        try {
            Toast.makeText(getApplicationContext(), msg, 1).show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void showSUIMessages(String msg) {
        try {
            Toast.makeText(getApplicationContext(), msg, 0).show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void checkIfUserHasSearched() {
        if (getIntent() != null) {
            Intent intent = getIntent();
            if (intent.getExtras() != null) {
                Bundle bundle = new Bundle();
                if (intent.getExtras().getBundle("com.directory.abe.usersearch") != null) {
                    String userSearch = intent.getExtras().getBundle("com.directory.abe.usersearch").getString("usersearch");
                    this.et_keyword_search.setText(userSearch);
                    this.searchEntryPresenter.searchByKeywordMode(userSearch);
                }
            }
        }
    }

    public void checkIfUserCanRefresh() {
        try {
            if (checkSharedPrefs() && this.et_keyword_search.getText().toString().length() > 0) {
                onClick(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkSharedPrefs() {
        try {
            this.prefs = getSharedPreferences(BuildConfig.APPLICATION_ID, 0);
            if (this.prefs.getBoolean("refresh", false)) {
                return true;
            }
            return false;
        } catch (ClassCastException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        try {
            if (buttonView.isChecked()) {
                if (this.searchEntryPresenter.isValidCategory(this.categoryNameSelected)) {
                    this.mapOn = true;
                    buttonView.setChecked(this.mapOn);
                    setMapViewType(Boolean.valueOf(isChecked));
                    this.searchEntryPresenter.searchByRadiusMode(this.categoryNameSelected);
                    return;
                }
                this.mapOn = false;
                buttonView.setChecked(this.mapOn);
                setMapViewType(Boolean.valueOf(this.mapOn));
                this.searchEntryPresenter.sendMessagesToUser("please select a category");
            } else if (this.searchEntryPresenter.isValidCategory(this.categoryNameSelected) && !buttonView.isChecked()) {
                this.mapOn = false;
                buttonView.setChecked(this.mapOn);
                setMapViewType(Boolean.valueOf(this.mapOn));
                this.searchEntryPresenter.searchByRadiusMode(this.categoryNameSelected);
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public boolean isMapOn() {
        return this.mapOn;
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onTabChanged(java.lang.String r6) {
        /*
        r5 = this;
        r2 = 0;
        r3 = -1;
        r1 = r6.hashCode();
        switch(r1) {
            case 240019858: goto L_0x000e;
            case 1450739657: goto L_0x0018;
            default: goto L_0x0009;
        };
    L_0x0009:
        r1 = r3;
    L_0x000a:
        switch(r1) {
            case 0: goto L_0x0022;
            case 1: goto L_0x003f;
            default: goto L_0x000d;
        };
    L_0x000d:
        return;
    L_0x000e:
        r1 = "byKeyword";
        r1 = r6.equals(r1);
        if (r1 == 0) goto L_0x0009;
    L_0x0016:
        r1 = r2;
        goto L_0x000a;
    L_0x0018:
        r1 = "byRadius";
        r1 = r6.equals(r1);
        if (r1 == 0) goto L_0x0009;
    L_0x0020:
        r1 = 1;
        goto L_0x000a;
    L_0x0022:
        r1 = r5.tabHost;
        r1 = r1.getTabWidget();
        r2 = r5.tabHost;
        r2 = r2.getCurrentTab();
        r1 = r1.getChildAt(r2);
        r1 = r1.getBackground();
        r2 = android.graphics.PorterDuff.Mode.MULTIPLY;
        r1.setColorFilter(r3, r2);
        r5.setKeywordDefault();
        goto L_0x000d;
    L_0x003f:
        r1 = r5.tabHost;
        r1 = r1.getTabWidget();
        r4 = r5.tabHost;
        r4 = r4.getCurrentTab();
        r1 = r1.getChildAt(r4);
        r1 = r1.getBackground();
        r4 = android.graphics.PorterDuff.Mode.MULTIPLY;
        r1.setColorFilter(r3, r4);
        r5.setRadiusDefault();
        r1 = "input_method";
        r0 = r5.getSystemService(r1);
        r0 = (android.view.inputmethod.InputMethodManager) r0;
        r1 = r5.et_keyword_search;
        r1 = r1.getWindowToken();
        r0.hideSoftInputFromWindow(r1, r2);
        goto L_0x000d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.directory.abe.SearchEntryFeature.Presentation.SearchEntryView.onTabChanged(java.lang.String):void");
    }

    public String getCurrentTab() {
        return this.tabHost.getCurrentTabTag();
    }

    private void setRadiusDefault() {
        if (!this.searchEntryPresenter.isGoogleAPIClientConnected()) {
            this.searchEntryPresenter.connectGoogleApi();
        }
    }

    private void setKeywordDefault() {
        if (this.searchEntryPresenter.isGoogleAPIClientConnected()) {
            this.searchEntryPresenter.disconnectGoogleApiClient();
        }
    }

    public void onClick(View v) {
        try {
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(this.et_keyword_search.getWindowToken(), 0);
            String userSearch = this.et_keyword_search.getText().toString();
            this.searchEntryPresenter.searchByKeywordMode(userSearch);
            this.searchEntryPresenter.analyseScreen("search term: " + userSearch, "by keyword", "search pg");
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        try {
            Spinner spinner = (Spinner) parent;
            if (spinner.getId() == C0246R.id.spin_category_search_rad) {
                String cat = setUserDefinedCategory(parent, pos);
                this.searchEntryPresenter.searchByRadius(true);
                this.searchEntryPresenter.searchByRadiusMode(cat);
            } else if (spinner.getId() == C0246R.id.spin_radius) {
                setUserDefinedRadius(parent, pos);
                this.searchEntryPresenter.searchByRadius(true);
                this.searchEntryPresenter.searchByRadiusMode(this.categoryNameSelected);
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void resetCatSpinnerDefault() {
        this.radCatSpinner.setOnItemSelectedListener(null);
        this.radCatSpinner.setSelection(0, false);
        this.radCatSpinner.setOnItemSelectedListener(this);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case C0246R.id.action_home:
                startActivity(new Intent(this, MainView.class));
                break;
            case C0246R.id.action_login:
                startActivity(new Intent(this, LoginView.class));
                break;
            case C0246R.id.action_register:
                startActivity(new Intent(this, RegisterView.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                switch (resultCode) {
                    case 0:
                        showLUIMessages("Please enable Google's locations Services on your device's Locations Services menu");
                        return;
                    default:
                        return;
                }
            default:
                return;
        }
    }
}
