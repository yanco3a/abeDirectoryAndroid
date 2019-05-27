package com.directory.abe.FeaturedEntryFeature.Presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.directory.abe.C0246R;
import com.directory.abe.Developer;
import com.directory.abe.FeedbackFeature.Presentation.FeedbackView;
import com.directory.abe.GSONModels.EntryModel;
import com.directory.abe.LoginFeature.Presentation.LoginView;
import com.directory.abe.RegisterFeature.Presentation.RegisterView;
import com.directory.abe.SearchEntryFeature.Presentation.SearchEntryView;
import com.google.android.gms.analytics.Tracker;
import org.apache.http.protocol.HTTP;

public class MainView extends AppCompatActivity implements IMainView, OnClickListener {
    private static final String TAG = MainView.class.getSimpleName();
    private Activity activity;
    private Tracker appTracker;
    private Button btn_keyword_home;
    private Context context;
    private AlertDialog dialog;
    private EditText et_keyword_search_home;
    private float float_ratingValue;
    private ImageView img_image_vendor_data_item;
    private MainViewPresenter mainViewPresenter;
    private LinearLayout optionallyFeaturedLayout;
    private RatingBar rb_ratingValue;
    private String st_trade_category_list_item;
    private String st_trade_date_list_item;
    private String st_trade_isVerified_list_item;
    private String st_trade_summary_list_item;
    private String st_vendor_address1_list_item;
    private String st_vendor_address2_list_item;
    private String st_vendor_address3_list_item;
    private String st_vendor_email_list_item;
    private String st_vendor_name_list_item;
    private String st_vendor_postcode_list_item;
    private String st_vendor_tele_list_item;
    private String st_vendor_type_list_item;
    private String st_vendor_web_list_item;
    private TextView tv_featured_entry_title;
    private TextView tv_licenses;
    private TextView tv_trade_category_list_item;
    private TextView tv_trade_date_list_item;
    private TextView tv_trade_isVerified_list_item;
    private TextView tv_trade_summary_list_item;
    private TextView tv_vendor_address1_list_item;
    private TextView tv_vendor_address2_list_item;
    private TextView tv_vendor_address3_list_item;
    private TextView tv_vendor_address3_list_item_a;
    private TextView tv_vendor_email_list_item;
    private TextView tv_vendor_name_list_item;
    private TextView tv_vendor_name_list_item_a;
    private TextView tv_vendor_postcode_list_item;
    private TextView tv_vendor_postcode_list_item_a;
    private TextView tv_vendor_tele_list_item;
    private TextView tv_vendor_type_list_item;
    private TextView tv_vendor_web_list_item;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0246R.layout.act_main);
        this.context = getBaseContext();
        this.activity = this;
        this.tv_licenses = (TextView) findViewById(C0246R.id.tv_licenses);
        this.et_keyword_search_home = (EditText) findViewById(C0246R.id.et_keyword_search_home);
        this.btn_keyword_home = (Button) findViewById(C0246R.id.btn_keyword_home);
        this.btn_keyword_home.setOnClickListener(this);
        this.tv_licenses.setOnClickListener(this);
        this.mainViewPresenter = new MainViewPresenter(this);
        this.optionallyFeaturedLayout = (LinearLayout) findViewById(C0246R.id.inner_featured_entry_layout);
        this.mainViewPresenter.setUpAnalytics(this.activity);
    }

    public void onSaveInstanceState(Bundle outstate) {
        super.onSaveInstanceState(outstate);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0246R.menu.menu_main, menu);
        return true;
    }

    public void onBackPressed() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        startActivity(intent);
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onResume() {
        super.onResume();
        try {
            if (this.mainViewPresenter.isOnline(this.context).booleanValue()) {
                this.mainViewPresenter.getFeaturedListing();
            } else {
                this.mainViewPresenter.loadDefaultEntry();
            }
            this.mainViewPresenter.resumeTracker(TAG);
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void setMainViewTitle(String title) {
        this.tv_featured_entry_title = (TextView) findViewById(C0246R.id.tv_featured_entry_title);
        this.tv_featured_entry_title.setText(title);
    }

    public void bridgeTextViewObjs() {
        this.tv_vendor_name_list_item = (TextView) findViewById(C0246R.id.tv_vendor_name_list_item);
        this.tv_vendor_address3_list_item = (TextView) findViewById(C0246R.id.tv_vendor_address3_list_item);
        this.tv_vendor_postcode_list_item = (TextView) findViewById(C0246R.id.tv_vendor_postcode_list_item);
        this.tv_vendor_type_list_item = (TextView) findViewById(C0246R.id.tv_vendor_type_list_item);
        this.tv_trade_category_list_item = (TextView) findViewById(C0246R.id.tv_trade_category_list_item);
        this.tv_trade_isVerified_list_item = (TextView) findViewById(C0246R.id.tv_trade_isVerified_list_item);
        this.tv_trade_summary_list_item = (TextView) findViewById(C0246R.id.tv_trade_summary_list_item);
        this.rb_ratingValue = (RatingBar) findViewById(C0246R.id.featuredRatingBar);
        this.img_image_vendor_data_item = (ImageView) findViewById(C0246R.id.image_vendor_data_item);
        this.tv_vendor_name_list_item_a = (TextView) findViewById(C0246R.id.tv_vendor_name_a);
        this.tv_vendor_address1_list_item = (TextView) findViewById(C0246R.id.tv_vendor_address1_list_item);
        this.tv_vendor_address2_list_item = (TextView) findViewById(C0246R.id.tv_vendor_address2_list_item);
        this.tv_vendor_address3_list_item_a = (TextView) findViewById(C0246R.id.tv_vendor_address3_list_item_a);
        this.tv_vendor_postcode_list_item_a = (TextView) findViewById(C0246R.id.tv_vendor_postcode_list_item_a);
        this.tv_vendor_tele_list_item = (TextView) findViewById(C0246R.id.tv_vendor_telephone_list_item);
        this.tv_vendor_email_list_item = (TextView) findViewById(C0246R.id.tv_vendor_email_list_item);
        this.tv_vendor_web_list_item = (TextView) findViewById(C0246R.id.tv_vendor_website_list_item);
        this.tv_trade_date_list_item = (TextView) findViewById(C0246R.id.tv_trade_date_list_item);
    }

    public void loadDefaultEntry() {
        bridgeTextViewObjs();
        setMainViewTitle("Abe's favourite listing");
        this.st_vendor_name_list_item = "Xsandy's Hair & Cosmetics";
        this.st_vendor_type_list_item = "Hair & Beauty";
        this.st_trade_category_list_item = "Store";
        this.st_trade_isVerified_list_item = "True";
        this.st_trade_summary_list_item = "Xsandy's Hair & Cosmetics.\nAfro-Caribbean owned Hair & Beauty store in SE London \n";
        this.st_vendor_address1_list_item = "";
        this.st_vendor_address2_list_item = "Lewisham Shopping Centre";
        this.st_vendor_address3_list_item = "London";
        this.st_vendor_postcode_list_item = "SE13 7HB";
        this.st_vendor_email_list_item = "haireverlasting@yahoo.com";
        this.st_vendor_tele_list_item = "0208 318 4417";
        this.st_vendor_web_list_item = "www.haireverlasting.co.uk";
        this.rb_ratingValue.setNumStars(5);
        this.rb_ratingValue.setRating(5.0f);
        this.tv_vendor_name_list_item.setText(this.st_vendor_name_list_item);
        showOrHideData(this.tv_vendor_name_list_item, this.st_vendor_name_list_item);
        this.tv_vendor_address3_list_item.setText(this.st_vendor_address3_list_item);
        showOrHideData(this.tv_vendor_address3_list_item, this.st_vendor_address3_list_item);
        this.tv_vendor_postcode_list_item.setText(this.st_vendor_postcode_list_item);
        showOrHideData(this.tv_vendor_postcode_list_item, this.st_vendor_postcode_list_item);
        this.tv_vendor_type_list_item.setText(this.st_vendor_type_list_item);
        showOrHideData(this.tv_vendor_type_list_item, this.st_vendor_type_list_item);
        this.tv_trade_category_list_item.setText(this.st_trade_category_list_item);
        showOrHideData(this.tv_trade_category_list_item, this.st_trade_category_list_item);
        this.tv_trade_isVerified_list_item.setText(this.st_trade_isVerified_list_item);
        showOrHideData(this.tv_trade_isVerified_list_item, this.st_trade_isVerified_list_item);
        this.tv_trade_summary_list_item.setText(this.st_trade_summary_list_item);
        showOrHideData(this.tv_trade_summary_list_item, this.st_trade_summary_list_item);
        if (!(this.rb_ratingValue == null || this.img_image_vendor_data_item == null)) {
            this.rb_ratingValue.setVisibility(8);
            this.img_image_vendor_data_item.setVisibility(8);
        }
        this.tv_vendor_name_list_item_a.setText(this.st_vendor_name_list_item);
        showOrHideData(this.tv_vendor_name_list_item_a, this.st_vendor_name_list_item);
        this.tv_vendor_address1_list_item.setText(this.st_vendor_address1_list_item);
        showOrHideData(this.tv_vendor_address1_list_item, this.st_vendor_address1_list_item);
        this.tv_vendor_address2_list_item.setText(this.st_vendor_address2_list_item);
        showOrHideData(this.tv_vendor_address2_list_item, this.st_vendor_address2_list_item);
        this.tv_vendor_address3_list_item_a.setText(this.st_vendor_address3_list_item);
        showOrHideData(this.tv_vendor_address3_list_item_a, this.st_vendor_address3_list_item);
        this.tv_vendor_postcode_list_item_a.setText(this.st_vendor_postcode_list_item);
        showOrHideData(this.tv_vendor_postcode_list_item_a, this.st_vendor_postcode_list_item);
        this.tv_vendor_tele_list_item.setText(this.st_vendor_tele_list_item);
        showOrHideData(this.tv_vendor_tele_list_item, this.st_vendor_tele_list_item);
        this.tv_vendor_email_list_item.setText(this.st_vendor_email_list_item);
        showOrHideData(this.tv_vendor_email_list_item, this.st_vendor_email_list_item);
        this.tv_vendor_web_list_item.setText(this.st_vendor_web_list_item);
        showOrHideData(this.tv_vendor_address1_list_item, this.st_vendor_address1_list_item);
        this.tv_trade_date_list_item.setText(this.st_trade_date_list_item);
        showOrHideData(this.tv_trade_date_list_item, this.st_trade_date_list_item);
    }

    public void goToSearch(String userSearch) {
        Intent intent = new Intent(this, SearchEntryView.class);
        Bundle extras = new Bundle();
        extras.putString("usersearch", userSearch);
        intent.putExtra("com.directory.abe.usersearch", extras);
        startActivity(intent);
    }

    public void loadFeaturedEntry(EntryModel featuredEntry) {
        this.optionallyFeaturedLayout.setOnClickListener(this);
        bridgeTextViewObjs();
        getFeaturedText(featuredEntry);
        setFeaturedText();
    }

    public void getFeaturedText(EntryModel featuredEntry) {
        try {
            this.st_vendor_name_list_item = featuredEntry.getVendorName();
            this.st_vendor_address3_list_item = featuredEntry.getVendorAddress3();
            this.st_vendor_postcode_list_item = featuredEntry.getVendorPostcode();
            this.st_vendor_type_list_item = featuredEntry.getVendorType();
            this.st_trade_category_list_item = featuredEntry.getListingTradeCategory();
            this.st_trade_isVerified_list_item = "Verified: " + featuredEntry.getListingTradeVerified();
            this.st_trade_summary_list_item = featuredEntry.getListingTradeSummary();
            this.float_ratingValue = featuredEntry.getRatingValue();
            this.st_vendor_address1_list_item = featuredEntry.getVendorAddress1();
            this.st_vendor_address2_list_item = featuredEntry.getVendorAddress2();
            this.st_vendor_tele_list_item = featuredEntry.getVendorTelephone();
            this.st_vendor_email_list_item = featuredEntry.getVendorEmail();
            this.st_vendor_web_list_item = featuredEntry.getVendorWebsite();
            if (featuredEntry.getListingTradeDate() == null) {
                return;
            }
            if (featuredEntry.getListingTradeDate().length() < 2) {
                this.st_trade_date_list_item = null;
            } else {
                this.st_trade_date_list_item = "Trading since: " + featuredEntry.getListingTradeDate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setFeaturedText() {
        this.tv_vendor_name_list_item.setText(this.st_vendor_name_list_item);
        this.tv_vendor_address3_list_item.setText(this.st_vendor_address3_list_item);
        this.tv_vendor_postcode_list_item.setText(this.st_vendor_postcode_list_item);
        this.tv_vendor_type_list_item.setText(this.st_vendor_type_list_item);
        this.tv_trade_category_list_item.setText(this.st_trade_category_list_item);
        this.tv_trade_isVerified_list_item.setText(this.st_trade_isVerified_list_item);
        this.tv_trade_summary_list_item.setText(this.st_trade_summary_list_item);
        this.rb_ratingValue.setNumStars(5);
        this.rb_ratingValue.setRating(this.float_ratingValue);
        this.tv_vendor_name_list_item_a.setText(this.st_vendor_name_list_item);
        showOrHideData(this.tv_vendor_name_list_item_a, this.st_vendor_name_list_item);
        this.tv_vendor_address1_list_item.setText(this.st_vendor_address1_list_item);
        showOrHideData(this.tv_vendor_address1_list_item, this.st_vendor_address1_list_item);
        this.tv_vendor_address2_list_item.setText(this.st_vendor_address2_list_item);
        showOrHideData(this.tv_vendor_address2_list_item, this.st_vendor_address2_list_item);
        this.tv_vendor_address3_list_item_a.setText(this.st_vendor_address3_list_item);
        showOrHideData(this.tv_vendor_address3_list_item_a, this.st_vendor_address3_list_item);
        this.tv_vendor_postcode_list_item_a.setText(this.st_vendor_postcode_list_item);
        showOrHideData(this.tv_vendor_postcode_list_item_a, this.st_vendor_postcode_list_item);
        this.tv_vendor_tele_list_item.setText(this.st_vendor_tele_list_item);
        showOrHideData(this.tv_vendor_tele_list_item, this.st_vendor_tele_list_item);
        this.tv_vendor_email_list_item.setText(this.st_vendor_email_list_item);
        showOrHideData(this.tv_vendor_email_list_item, this.st_vendor_email_list_item);
        this.tv_vendor_web_list_item.setText(this.st_vendor_web_list_item);
        showOrHideData(this.tv_vendor_web_list_item, this.st_vendor_web_list_item);
        this.tv_trade_date_list_item.setText(this.st_trade_date_list_item);
        showOrHideData(this.tv_trade_date_list_item, this.st_trade_date_list_item);
    }

    public void showOrHideData(TextView tv, String str) {
        if (str != null) {
            try {
                if (str.length() > 1) {
                    tv.setVisibility(0);
                    return;
                } else {
                    tv.setVisibility(8);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        tv.setVisibility(8);
    }

    public void onClick(View v) {
        try {
            String userSearch = this.et_keyword_search_home.getText().toString();
            switch (v.getId()) {
                case C0246R.id.btn_keyword_home:
                    this.mainViewPresenter.searchByKeywordMode(userSearch);
                    break;
                case C0246R.id.tv_licenses:
                    displayLicenses();
                    break;
            }
            this.mainViewPresenter.analyseScreen("search term: " + userSearch, "by keyword", "main screen search");
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

    public void displayLicenses() {
        try {
            View view = (WebView) LayoutInflater.from(this).inflate(C0246R.layout.dialog_licenses, null);
            Uri path = Uri.parse("android.resource://com.directory.abe/raw/open_source_licenses.html");
            view.loadUrl("file:///android_res/raw/open_source_licenses.html");
            this.dialog = new Builder(this, C0246R.style.Theme.AppCompat.Light.Dialog.Alert).setTitle(getString(C0246R.string.action_licenses)).setView(view).setPositiveButton(17039370, null).show();
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

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case C0246R.id.action_tester_feedback:
                startActivity(new Intent(this, FeedbackView.class));
                break;
            case C0246R.id.action_email_us:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.EMAIL", new String[]{Developer.ADMIN_EMAIL});
                intent.putExtra("android.intent.extra.SUBJECT", "Abe directory query");
                intent.setType(HTTP.PLAIN_TEXT_TYPE);
                startActivity(intent);
                break;
            case C0246R.id.action_search:
                startActivity(new Intent(this, SearchEntryView.class));
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
}
