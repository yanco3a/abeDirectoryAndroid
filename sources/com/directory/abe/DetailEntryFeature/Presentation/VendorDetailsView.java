package com.directory.abe.DetailEntryFeature.Presentation;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.directory.abe.AnalyticsApp;
import com.directory.abe.BuildConfig;
import com.directory.abe.C0246R;
import com.directory.abe.Developer;
import com.directory.abe.FeedbackFeature.Presentation.FeedbackView;
import com.directory.abe.GSONModels.EntryModel;
import com.directory.abe.LoginFeature.Presentation.LoginView;
import com.directory.abe.SQLiteDatabase.SQLiteModel.FRContract.FeedUser;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import org.apache.http.protocol.HTTP;

public class VendorDetailsView extends AppCompatActivity implements OnMapReadyCallback, AppCompatCallback, IVendorDetailsView, OnClickListener {
    private static final String TAG = VendorDetailsView.class.getSimpleName();
    private static final float ZOOM_ME = 15.0f;
    private Tracker appTracker;
    private LinearLayout bodyLayout;
    private Button btn_submit_user_review;
    private double d_lat;
    private double d_lng;
    private EditText et_user_err;
    private EditText et_user_review;
    private Bundle extras;
    /* renamed from: m */
    private EntryModel f14m;
    private ArrayList<EntryModel> objs;
    private SharedPreferences prefs;
    private VendorDetailsPresenter presenter;
    private RatingBar rating_bar;
    private RatingBar rb_user_rating_value;
    private String st_vendorame;
    private String st_website;
    private TextView tv_addy1;
    private TextView tv_addy2;
    private TextView tv_addy3;
    private TextView tv_addy3_a;
    private TextView tv_category;
    private TextView tv_date;
    private TextView tv_directions_tip;
    private TextView tv_email;
    private TextView tv_enter_user_review;
    private TextView tv_enter_user_review2;
    private TextView tv_isVerified;
    private TextView tv_login_to_review;
    private TextView tv_postcode;
    private TextView tv_postcode_a;
    private TextView tv_summary;
    private TextView tv_tele;
    private TextView tv_type;
    private TextView tv_user_review;
    private TextView tv_vendorId;
    private TextView tv_vendorName;
    private TextView tv_vendor_name_a;
    private TextView tv_website;
    private int uId;
    private String userEmail;
    private String username;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView((int) C0246R.layout.act_vendor_details);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            this.bodyLayout = (LinearLayout) findViewById(C0246R.id.ll_for_map_frag);
            this.tv_directions_tip = (TextView) findViewById(C0246R.id.tv_directions_tip);
            this.tv_directions_tip = (TextView) findViewById(C0246R.id.tv_directions_tip);
            this.tv_vendorName = (TextView) findViewById(C0246R.id.tv_vendor_name_list_item);
            this.tv_vendorId = (TextView) findViewById(C0246R.id.tv_vendor_id_list_item);
            this.tv_addy3 = (TextView) findViewById(C0246R.id.tv_vendor_address3_list_item);
            this.tv_postcode = (TextView) findViewById(C0246R.id.tv_vendor_postcode_list_item);
            this.tv_vendor_name_a = (TextView) findViewById(C0246R.id.tv_vendor_name_a);
            this.tv_addy1 = (TextView) findViewById(C0246R.id.tv_vendor_address1_list_item);
            this.tv_addy2 = (TextView) findViewById(C0246R.id.tv_vendor_address2_list_item);
            this.tv_addy3_a = (TextView) findViewById(C0246R.id.tv_vendor_address3_list_item_a);
            this.tv_postcode_a = (TextView) findViewById(C0246R.id.tv_vendor_postcode_list_item_a);
            this.tv_tele = (TextView) findViewById(C0246R.id.tv_vendor_telephone_list_item);
            this.tv_email = (TextView) findViewById(C0246R.id.tv_vendor_email_list_item);
            this.tv_website = (TextView) findViewById(C0246R.id.tv_vendor_website_list_item);
            this.tv_type = (TextView) findViewById(C0246R.id.tv_vendor_type_list_item);
            this.tv_isVerified = (TextView) findViewById(C0246R.id.tv_trade_isVerified_list_item);
            this.tv_summary = (TextView) findViewById(C0246R.id.tv_trade_summary_list_item);
            this.tv_date = (TextView) findViewById(C0246R.id.tv_trade_date_list_item);
            this.tv_category = (TextView) findViewById(C0246R.id.tv_trade_category_list_item);
            this.rating_bar = (RatingBar) findViewById(C0246R.id.featuredRatingBar);
            this.et_user_err = (EditText) findViewById(C0246R.id.et_user_err);
            this.presenter = new VendorDetailsPresenter(this);
            this.presenter.storeSharedPrefs();
            this.presenter.setUpAnalytics();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUpAnalytics() {
        this.appTracker = ((AnalyticsApp) getApplication()).getDefaultTracker();
    }

    public void storeSharedPrefs() {
        try {
            this.prefs = getSharedPreferences(BuildConfig.APPLICATION_ID, 0);
            this.uId = this.prefs.getInt("uId", 0);
            this.username = this.prefs.getString(FeedUser.COLUMN_NAME_USER_USERNAME, null);
            this.userEmail = this.prefs.getString(FeedUser.COLUMN_NAME_USER_EMAIL, null);
            this.extras = getIntent().getExtras();
            if (this.extras != null) {
                this.f14m = (EntryModel) this.extras.getSerializable("vendorListing");
                this.objs = (ArrayList) this.extras.getSerializable("ratings");
                if (this.uId == 0 || this.uId == -1) {
                    this.presenter.setUpData(this.extras, this.uId, this.f14m, this.objs, false);
                } else {
                    this.presenter.setUpData(this.extras, this.uId, this.f14m, this.objs, true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public float getRatingValue() {
        float f = -1.0f;
        try {
            if (this.rb_user_rating_value != null && this.rb_user_rating_value.getRating() > 0.0f) {
                f = this.rb_user_rating_value.getRating();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    public void goToLogin(int listingId, float rating, String userReview) {
        Intent intent = new Intent(this, LoginView.class);
        Bundle extras = new Bundle();
        extras.putInt("listingId", listingId);
        extras.putFloat("rating", rating);
        extras.putString(userReview, "userReview");
        intent.putExtra("insertUserReview", extras);
        startActivity(intent);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void onMapReady(GoogleMap googleMap) {
        try {
            LatLng latLng = new LatLng(this.d_lat, this.d_lng);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM_ME));
            googleMap.addMarker(new MarkerOptions().position(latLng).title(this.st_vendorame));
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public ActionMode onWindowStartingSupportActionMode(Callback callback) {
        return null;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0246R.menu.menu_vendor_details, menu);
        return true;
    }

    protected void onResume() {
        super.onResume();
        try {
            this.appTracker.setScreenName("Screen~" + TAG);
            this.appTracker.send(new ScreenViewBuilder().build());
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
    }

    protected void onRestart() {
        super.onRestart();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case C0246R.id.tv_vendor_website_list_item:
                    this.presenter.launchWebsite();
                    return;
                case C0246R.id.btn_submit_user_review:
                    if (this.et_user_review != null) {
                        this.presenter.onClickHandler(this.et_user_review.getText().toString(), this.extras, this.uId);
                        return;
                    }
                    return;
                default:
                    return;
            }
        } catch (ActivityNotFoundException anf) {
            anf.printStackTrace();
            anf.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void launchWebsite() {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(this.st_website)));
        } catch (Exception e) {
            sendMessagesToUser("website unavailable");
        }
    }

    public void setErrorText(String e) {
        this.et_user_err.setError(e);
    }

    public void setVendorName(String s) {
        this.st_vendorame = s;
        this.tv_vendorName.setText(s);
        showOrHideData(this.tv_vendorName, s);
    }

    public void setVendorId(int i) {
        this.tv_vendorId.setText(String.valueOf(i));
    }

    public void setAddy3(String s) {
        this.tv_addy3.setText(s);
        showOrHideData(this.tv_addy3, s);
        this.tv_addy3_a.setText(s);
        showOrHideData(this.tv_addy3_a, s);
    }

    public void setTel(String s) {
        this.tv_tele.setText(s);
        showOrHideData(this.tv_tele, s);
    }

    public void setEmail(String s) {
        this.tv_email.setText(s);
        showOrHideData(this.tv_email, s);
    }

    public void setWebsite(String w, String n) {
        this.st_website = w;
        this.tv_website.setText(Html.fromHtml("Website <br>" + ("<a href=\"" + w + "\">" + n + "</a>")));
        this.tv_website.setMovementMethod(LinkMovementMethod.getInstance());
        this.tv_website.setOnClickListener(this);
        showOrHideData(this.tv_website, w);
    }

    public void setType(String s) {
        this.tv_type.setText(s);
        showOrHideData(this.tv_type, s);
    }

    public void setVerified(String s) {
        this.tv_isVerified.setText(s);
        showOrHideData(this.tv_isVerified, s);
    }

    public void setSummary(String s) {
        this.tv_summary.setText(s);
        showOrHideData(this.tv_summary, s);
    }

    public void setDate(String s) {
        this.tv_date.setText(s);
        showOrHideData(this.tv_date, s);
    }

    public void setRating(float f) {
        this.rating_bar.setNumStars(5);
        this.rating_bar.setRating(f);
    }

    public void disableErrorText() {
        this.et_user_err.setKeyListener(null);
    }

    public void setPostcode(String s) {
        this.tv_postcode.setText(s);
        showOrHideData(this.tv_postcode, s);
        this.tv_postcode_a.setText(s);
        showOrHideData(this.tv_postcode_a, s);
    }

    public void setVendorame(String s) {
        this.tv_vendor_name_a.setText(s);
        showOrHideData(this.tv_vendor_name_a, s);
    }

    public void setAddy1(String s) {
        this.tv_addy1.setText(s);
        showOrHideData(this.tv_addy1, s);
    }

    public void setAddy2(String s) {
        this.tv_addy2.setText(s);
        showOrHideData(this.tv_addy2, s);
    }

    public void setCategory(String s) {
        this.tv_category.setText(s);
        showOrHideData(this.tv_category, s);
    }

    public void setLat(double lat) {
        this.d_lat = lat;
    }

    public void setLng(double lng) {
        this.d_lng = lng;
    }

    public void endDetailedSearch() {
        try {
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUserScreen(String s) {
        this.rb_user_rating_value = (RatingBar) findViewById(C0246R.id.rb_user_rating_value);
        this.tv_user_review = (TextView) findViewById(C0246R.id.tv_user_review);
        this.tv_user_review.setText(s);
        this.et_user_review = (EditText) findViewById(C0246R.id.et_user_review);
        this.btn_submit_user_review = (Button) findViewById(C0246R.id.btn_submit_user_review);
        this.btn_submit_user_review.setOnClickListener(this);
        this.tv_login_to_review = (TextView) findViewById(C0246R.id.tv_login_to_review);
        this.tv_login_to_review.setVisibility(8);
    }

    public void setAdminScreen(String s) {
        this.tv_user_review = (TextView) findViewById(C0246R.id.tv_user_review);
        this.tv_user_review.setText(s);
        this.rb_user_rating_value = (RatingBar) findViewById(C0246R.id.rb_user_rating_value);
        this.rb_user_rating_value.setVisibility(8);
        this.tv_enter_user_review = (TextView) findViewById(C0246R.id.tv_enter_user_review);
        this.tv_enter_user_review.setVisibility(8);
        this.tv_enter_user_review2 = (TextView) findViewById(C0246R.id.tv_enter_user_review2);
        this.tv_enter_user_review2.setVisibility(8);
        this.et_user_review = (EditText) findViewById(C0246R.id.et_user_review);
        this.et_user_review.setVisibility(8);
        this.btn_submit_user_review = (Button) findViewById(C0246R.id.btn_submit_user_review);
        this.btn_submit_user_review.setVisibility(8);
    }

    public void showAdditionalData() {
        this.bodyLayout.setVisibility(0);
        this.tv_directions_tip.setVisibility(0);
    }

    public void hideAdditionalData() {
        this.bodyLayout.setVisibility(8);
        this.tv_directions_tip.setVisibility(8);
    }

    public void initMap() {
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(C0246R.id.map)).getMapAsync(this);
    }

    public void disableMap() {
        getSupportFragmentManager().beginTransaction().hide((SupportMapFragment) getSupportFragmentManager().findFragmentById(C0246R.id.map)).commit();
    }

    public void sendMessagesToUser(String msg) {
        Toast.makeText(this, msg, 1).show();
    }

    public void onInsertUserReviewSuccess(String s) {
        sendMessagesToUser(s);
        storeRefreshSetting(true);
        navToPreviousScreen();
    }

    public void storeRefreshSetting(boolean b) {
        this.prefs = getSharedPreferences(BuildConfig.APPLICATION_ID, 0);
        Editor editedPrefs = this.prefs.edit();
        editedPrefs.putBoolean("refresh", b);
        editedPrefs.apply();
    }

    public void onInsertUserReviewFailure(String s) {
        sendMessagesToUser(s);
        storeRefreshSetting(false);
    }

    public void showOrHideData(TextView tv, String str) {
        if (str != null) {
            try {
                if (str.equals("") || str.equals(" ") || str.length() <= 1) {
                    tv.setVisibility(8);
                    return;
                } else {
                    tv.setVisibility(0);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        tv.setVisibility(8);
    }

    public void navToPreviousScreen() {
        try {
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case C0246R.id.action_tester_feedback:
                startActivity(new Intent(this, FeedbackView.class));
                finish();
                break;
            case C0246R.id.action_email_us:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.EMAIL", new String[]{Developer.ADMIN_EMAIL});
                intent.putExtra("android.intent.extra.SUBJECT", "Abe directory query");
                intent.setType(HTTP.PLAIN_TEXT_TYPE);
                startActivity(intent);
                break;
            case C0246R.id.action_login:
                startActivity(new Intent(this, LoginView.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
