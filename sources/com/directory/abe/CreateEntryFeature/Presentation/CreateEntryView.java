package com.directory.abe.CreateEntryFeature.Presentation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputFilter.AllCaps;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.directory.abe.BuildConfig;
import com.directory.abe.C0246R;
import com.directory.abe.DateValidator;
import com.directory.abe.DetailEntryFeature.Presentation.VendorDetailsView;
import com.directory.abe.Developer;
import com.directory.abe.FeaturedEntryFeature.Presentation.MainView;
import com.directory.abe.FeedbackFeature.Presentation.FeedbackView;
import com.directory.abe.GSONModels.EntryModel;
import com.directory.abe.LoginFeature.Presentation.LoginView;
import com.directory.abe.SQLiteDatabase.SQLiteModel.FRContract.FeedAdmin;
import com.directory.abe.SearchEntryFeature.Presentation.SearchEntryView;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.gms.analytics.Tracker;
import java.util.ArrayList;
import java.util.regex.Pattern;
import org.apache.http.protocol.HTTP;

public class CreateEntryView extends AppCompatActivity implements OnItemSelectedListener, ValidateData, OnClickListener, ICreateEntryView {
    private static final String TAG = CreateEntryView.class.getSimpleName();
    private EditText a1;
    private EditText a2;
    private EditText a3;
    private AlertDialog aDialog;
    private String adminPass;
    private String adminUser;
    private Tracker appTracker;
    private Button btnGetCompHouse;
    private int checkCategory = 0;
    private EditText cn;
    private String companyNo;
    private CreateEntryPresenter createListingPresenter;
    private ProgressDialog dialog;
    private EditText ema;
    private EditText et_adminUsername;
    private EditText et_error;
    private Bundle extras;
    private int int_adminId;
    private String listingTradeCategory;
    private String listingTradeDate;
    private String listingTradeReferralCode;
    private String listingTradeSummary;
    private EditText pc;
    private SharedPreferences prefs;
    public String request;
    private Spinner spinner;
    private Spinner spinner1;
    private String str_adminUsername;
    private EditText tel;
    private String tradeCategory = "";
    private EditText tradeDate;
    private EditText tradeReferralCode;
    private EditText tradeSummary;
    private TextView tradeSummary1;
    private TextView tv_address_detail_id;
    private TextView tv_help;
    private String vType = "Other";
    private String vendorAddress1;
    private String vendorAddress2;
    private String vendorAddress3;
    private String vendorEmail;
    private String vendorName;
    private String vendorPostcode;
    private String vendorTelephone;
    private String vendorType;
    private String vendorWebsite;
    private EditText vn;
    private EditText web;

    /* renamed from: com.directory.abe.CreateEntryFeature.Presentation.CreateEntryView$1 */
    class C02371 implements Runnable {
        C02371() {
        }

        public void run() {
            CreateEntryView.this.btnGetCompHouse.setOnClickListener(CreateEntryView.this);
        }
    }

    /* renamed from: com.directory.abe.CreateEntryFeature.Presentation.CreateEntryView$2 */
    class C02382 implements Runnable {
        C02382() {
        }

        public void run() {
            CreateEntryView.this.spinner.setOnItemSelectedListener(CreateEntryView.this);
        }
    }

    /* renamed from: com.directory.abe.CreateEntryFeature.Presentation.CreateEntryView$3 */
    class C02393 implements Runnable {
        C02393() {
        }

        public void run() {
            CreateEntryView.this.spinner1.setOnItemSelectedListener(CreateEntryView.this);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            this.spinner.setSelection(savedInstanceState.getInt("spinner", 0));
            this.spinner1.setSelection(savedInstanceState.getInt("spinner1", 1));
        }
        setContentView((int) C0246R.layout.act_create_vendor);
        this.extras = new Bundle();
        this.extras = getIntent().getExtras();
        this.createListingPresenter = new CreateEntryPresenter(this);
        this.btnGetCompHouse = (Button) findViewById(C0246R.id.btn_create_vendor);
        this.cn = (EditText) findViewById(C0246R.id.et_companyNo);
        this.vn = (EditText) findViewById(C0246R.id.eT_vendor_name);
        this.tv_address_detail_id = (TextView) findViewById(C0246R.id.tv_address_detail_id);
        this.a1 = (EditText) findViewById(C0246R.id.eT_Address1);
        this.a2 = (EditText) findViewById(C0246R.id.eT_Address2);
        this.a3 = (EditText) findViewById(C0246R.id.eT_Address3);
        this.pc = (EditText) findViewById(C0246R.id.eT_postcode);
        this.tel = (EditText) findViewById(C0246R.id.eT_telephone);
        this.ema = (EditText) findViewById(C0246R.id.eT_email);
        this.web = (EditText) findViewById(C0246R.id.eT_website);
        this.tradeDate = (EditText) findViewById(C0246R.id.et_tradeDate);
        this.tradeSummary = (EditText) findViewById(C0246R.id.et_tradeSummary);
        this.tradeSummary1 = (TextView) findViewById(C0246R.id.tv_tradeSummary1);
        this.tradeReferralCode = (EditText) findViewById(C0246R.id.eT_referralCode);
        this.et_adminUsername = (EditText) findViewById(C0246R.id.et_adminUsername);
        this.et_adminUsername.setText(this.str_adminUsername);
        this.et_adminUsername.setKeyListener(null);
        this.et_error = (EditText) findViewById(C0246R.id.et_error);
        this.et_error.setKeyListener(null);
        this.pc.setFilters(new InputFilter[]{new AllCaps()});
        this.tradeReferralCode.setFilters(new InputFilter[]{new AllCaps()});
        this.tv_help = (TextView) findViewById(C0246R.id.tv_help);
        this.tv_help.setOnClickListener(this);
        this.spinner = (Spinner) findViewById(C0246R.id.spin_cat);
        Resources res = getResources();
        this.spinner.setAdapter(new ArrayAdapter(this, 17367050, res.getStringArray(C0246R.array.category_array)));
        this.spinner1 = (Spinner) findViewById(C0246R.id.spin_type);
        this.spinner1.setAdapter(new ArrayAdapter(this, 17367050, res.getStringArray(C0246R.array.type_array)));
        this.btnGetCompHouse.post(new C02371());
        this.spinner.post(new C02382());
        this.spinner1.post(new C02393());
        this.createListingPresenter.storeSharedPrefs();
        this.createListingPresenter.setUpAnalytics(getActivity());
    }

    public void storeSharedPrefs() {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("alogin", 0);
            this.int_adminId = sharedPreferences.getInt("aId", 0);
            this.str_adminUsername = sharedPreferences.getString(FeedAdmin.COLUMN_NAME_ADMIN_USERNAME, null);
            this.prefs = getSharedPreferences(BuildConfig.APPLICATION_ID, 0);
            this.int_adminId = this.prefs.getInt("aId", 0);
            this.str_adminUsername = this.prefs.getString(FeedAdmin.COLUMN_NAME_ADMIN_USERNAME, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("spinner", this.spinner.getSelectedItemPosition());
        outState.putInt("spinner1", this.spinner1.getSelectedItemPosition());
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        try {
            switch (((Spinner) parent).getId()) {
                case C0246R.id.spin_cat:
                    if (parent.getSelectedItemPosition() == 0 || parent.getSelectedItemPosition() == -1) {
                        this.tradeCategory = "";
                        return;
                    } else {
                        this.tradeCategory = parent.getItemAtPosition(pos).toString();
                        return;
                    }
                case C0246R.id.spin_type:
                    this.vType = parent.getItemAtPosition(pos).toString();
                    this.createListingPresenter.checkTypeSelected(this.vType);
                    return;
                default:
                    return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
        e.printStackTrace();
        e.getMessage();
    }

    public void checkTypeSelected(String vType) {
        int i = -1;
        switch (vType.hashCode()) {
            case -1928355213:
                if (vType.equals("Online")) {
                    i = 0;
                    break;
                }
                break;
        }
        switch (i) {
            case 0:
                this.a1.setVisibility(8);
                this.a2.setVisibility(8);
                this.a3.setVisibility(8);
                this.pc.setVisibility(8);
                this.tv_address_detail_id.setVisibility(8);
                return;
            default:
                this.a1.setVisibility(0);
                this.a2.setVisibility(0);
                this.a3.setVisibility(0);
                this.pc.setVisibility(0);
                this.tv_address_detail_id.setVisibility(0);
                return;
        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public boolean isObjectNull(Object obj) {
        try {
            if (String.valueOf(obj) == "null" || String.valueOf(obj).equals("")) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            return false;
        }
    }

    public String checkString(Object o) {
        try {
            if (isObjectNull(o)) {
                return "-1";
            }
            return String.valueOf(o);
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            return "-2";
        }
    }

    public void setAllData() {
        setAdminId(this.int_adminId);
        setAdminUsername(this.str_adminUsername);
        setEtVendorName(this.vn);
        setEtListingTradeDate(this.tradeDate);
        setEtListingTradeReferralCode(this.tradeReferralCode);
        setEtCompanyHouseNo(this.cn);
        setEtVendorTelephone(this.tel);
        setEtVendorEmail(this.ema);
        setEtVendorPostcode(this.pc);
        setEtVendorWebsite(this.web);
        setEtVendorAddress1(this.a1);
        setEtVendorAddress2(this.a2);
        setEtVendorAddress3(this.a3);
        setEtVendorPostcode(this.pc);
        setEtListingTradeSummary(this.tradeSummary);
        setVendorType(this.vType);
        setTradeCategory(this.tradeCategory);
    }

    public void clearErrorText() {
        this.et_error.setKeyListener(null);
    }

    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case C0246R.id.tv_help:
                    this.createListingPresenter.analyseScreen("help selected: ", "help selected...", "Create Listing screen");
                    displayHelp();
                    return;
                case C0246R.id.btn_create_vendor:
                    this.createListingPresenter.analyseScreen("create listing: ", "create listing attempt occurring...", "Create Listing screen");
                    this.createListingPresenter.createVendorOnClick();
                    return;
                default:
                    return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
        e.printStackTrace();
        e.getMessage();
    }

    public void displayHelp() {
        try {
            View view = (WebView) LayoutInflater.from(this).inflate(C0246R.layout.dialog_create_help, null);
            view.loadUrl("file:///android_res/raw/create_help.html");
            this.aDialog = new Builder(this, C0246R.style.Theme.AppCompat.Light.Dialog.Alert).setTitle(getString(C0246R.string.action_guide)).setView(view).setPositiveButton(17039370, null).show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void onCreateSuccess(EntryModel model) {
        try {
            String successMsg = model.getVendorName() + " successfully listed!";
            if (this.dialog != null) {
                this.dialog.dismiss();
            }
            toastLMsg(successMsg);
            Intent goToDetailView = new Intent(this, VendorDetailsView.class);
            Bundle extras = new Bundle();
            extras.putSerializable("vendorListing", model);
            ArrayList<EntryModel> objs = new ArrayList();
            objs.add(model);
            extras.putSerializable("ratings", objs);
            goToDetailView.putExtras(extras);
            startActivity(goToDetailView);
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void progessDialogMsg(String dialogMsg) {
        this.dialog = ProgressDialog.show(this, dialogMsg, null);
    }

    public void onCreateFailure(String msg) {
        if (this.dialog != null) {
            this.dialog.dismiss();
        }
        toastLMsg(msg);
    }

    public Activity getActivity() {
        return this;
    }

    public void toastLMsg(String toastLMsg) {
        Toast.makeText(this, toastLMsg, 1).show();
    }

    public void toastSMsg(String toastSMsg) {
        Toast.makeText(this, toastSMsg, 0).show();
    }

    public boolean validateAllUserData(String vendorType) {
        try {
            this.vn.setError(null);
            this.tradeSummary.setError(null);
            this.tradeSummary1.setText("");
            this.tradeDate.setError(null);
            this.web.setError(null);
            this.ema.setError(null);
            this.a1.setError(null);
            this.pc.setError(null);
            this.et_error.setError(null);
            if (TextUtils.isEmpty(this.str_adminUsername)) {
                toastLMsg("Login error occurred, please try again later");
                return false;
            } else if (TextUtils.isEmpty(this.vn.getText().toString())) {
                this.vn.setError("Business name is required");
                return false;
            } else if (this.vn.getText().toString().length() > 45) {
                this.vn.setError("Business name is too long. Exceeded by: \n" + (this.vn.getText().toString().length() - 45));
                return false;
            } else if (TextUtils.isEmpty(this.tradeSummary.getText().toString())) {
                this.tradeSummary1.requestFocus();
                this.tradeSummary1.setText("Business summary missing. Tell customers a little about your business. i.e. opening hours, about your services or what you sell etc...");
                return false;
            } else if (!this.tradeSummary.getText().toString().matches("^[a-zA-Z0-9\\n \\u2026/.,:;&()-/'+!?]+$")) {
                this.tradeSummary1.requestFocus();
                this.tradeSummary1.setText("Business summary invalid. Only spaces, paragraphs and these symbols are allowed: \n.,:;&()-/'+!? ...");
                return false;
            } else if (this.tradeSummary.getText().toString().length() > 1000) {
                String errText = "Business summary invalid. Character limit exceeded by " + String.valueOf(1000 - this.tradeSummary.getText().toString().length());
                this.tradeSummary1.requestFocus();
                this.tradeSummary1.setText(errText);
                return false;
            } else {
                DateValidator dateValidator = new DateValidator();
                if (!TextUtils.isEmpty(this.tradeDate.getText().toString()) && !dateValidator.validateD(this.tradeDate.getText().toString())) {
                    this.tradeDate.setError("When did first business start trading? DD/MM/YYYY or D/D/YYYY i.e. 01/01/2016 or 1/1/2016");
                    return false;
                } else if (this.web.getText().toString().length() > 0 && !Pattern.compile("^(http|https)://").matcher(this.web.getText().toString()).find()) {
                    this.web.setError("Websites must begin: \nhttp:// or https:// i.e. http://www.abe.com");
                    return false;
                } else if (this.web.getText().toString().length() > 7 && !Patterns.WEB_URL.matcher(this.web.getText().toString()).matches()) {
                    this.web.setError("Abe doesn't think \n'" + this.web.getText().toString() + "' is a real web address.");
                    return false;
                } else if (this.ema.getText().toString().length() <= 0 || Patterns.EMAIL_ADDRESS.matcher(this.ema.getText().toString()).matches()) {
                    if (this.pc.getText().toString().length() > 0) {
                        if (!Pattern.compile("(GIR 0AA)|((([A-Z-[QVX]][0-9][0-9]?)|(([A-Z-[QVX]][A-Z-[IJZ]][0-9][0-9]?)|(([A-Z-[QVX]][0-9][A-HJKPSTUW])|([A-Z-[QVX]][A-Z-[IJZ]][0-9][ABEHMNPRVWXY]))))\\s?[0-9][A-Z-[CIKMOV]]{2})").matcher(this.pc.getText().toString().toUpperCase()).find()) {
                            this.pc.setError("Abe doesn't recognise this postcode. Please enter a full uk postcode \ne.g. NW6 5LL");
                            return false;
                        }
                    }
                    boolean z = true;
                    switch (vendorType.hashCode()) {
                        case -1928355213:
                            if (vendorType.equals("Online")) {
                                z = false;
                                break;
                            }
                            break;
                        case 76517104:
                            if (vendorType.equals("Other")) {
                                z = true;
                                break;
                            }
                            break;
                        case 80218305:
                            if (vendorType.equals("Store")) {
                                z = true;
                                break;
                            }
                            break;
                    }
                    switch (z) {
                        case false:
                            if (TextUtils.isEmpty(this.web.getText().toString())) {
                                this.web.setError("Valid website required for Online businesses \ni.e. http://www.abe.com");
                                return false;
                            } else if (!Pattern.compile("^(http|https)://").matcher(this.web.getText().toString()).find()) {
                                this.web.setError("Websites must begin: \nhttp:// or https:// i.e. http://www.abe.com");
                                return false;
                            } else if (this.web.getText().toString().length() <= 7) {
                                return true;
                            } else {
                                if (!Patterns.WEB_URL.matcher(this.web.getText().toString()).matches()) {
                                    this.web.setError("Abe doesn't think '" + this.web.getText().toString() + "' is a real web address.");
                                    return false;
                                }
                            }
                            break;
                        case true:
                            break;
                        case true:
                            if (!TextUtils.isEmpty(this.tel.getText().toString()) && this.tel.getText().toString().replaceAll("\\s", "").length() != 11) {
                                this.tel.setError("11 chars required e.g. 02089042744, 020 8904 2744, or 0208 904 2744");
                                return false;
                            } else if (!TextUtils.isEmpty(this.ema.getText().toString()) && !Patterns.EMAIL_ADDRESS.matcher(this.ema.getText().toString()).matches()) {
                                this.ema.setError("eg info@yourbusiness.co.uk");
                                return false;
                            } else if (!TextUtils.isEmpty(this.pc.getText().toString()) && TextUtils.isEmpty(this.a1.getText().toString())) {
                                this.a1.setError("Abe needs the first line of the address, so that customers can find you");
                                return false;
                            } else if (!TextUtils.isEmpty(this.a1.getText().toString()) && TextUtils.isEmpty(this.pc.getText().toString())) {
                                this.pc.setError("Abe doesn't recognise this address. please enter postcode");
                                return false;
                            } else if (!TextUtils.isEmpty(this.tel.getText().toString()) || !TextUtils.isEmpty(this.ema.getText().toString()) || !TextUtils.isEmpty(this.pc.getText().toString())) {
                                return true;
                            } else {
                                this.et_error.setError("Something went wrong. Abe needs one of the following: a valid contact number, email or postal address");
                                return false;
                            }
                        default:
                            return false;
                    }
                    if (TextUtils.isEmpty(this.a1.getText().toString())) {
                        this.a1.setError("Abe needs the first line of the address, so that customers can find you");
                        return false;
                    } else if (!TextUtils.isEmpty(this.pc.getText().toString())) {
                        return true;
                    } else {
                        this.pc.setError("Abe needs a postcode, so that customers can find you");
                        return false;
                    }
                } else {
                    this.ema.setError("Abe doesn't think \n'" + this.ema.getText().toString() + "' is a real email address.");
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            return false;
        }
    }

    public void setVendorType(String s) {
        this.vendorType = s;
        this.createListingPresenter.setVendorType(this.vendorType);
    }

    public void setTradeCategory(String s) {
        this.listingTradeCategory = s;
        this.createListingPresenter.setListingTradeCategory(this.listingTradeCategory);
    }

    public void setAdminId(int id) {
        this.createListingPresenter.setAdminId(id);
    }

    public void setAdminUsername(String adminUsername) {
        this.createListingPresenter.setAdminUsername(adminUsername);
    }

    public void setEtCompanyHouseNo(EditText e) {
        this.companyNo = checkString(e.getText().toString());
        this.createListingPresenter.setCompanyHouse(this.companyNo);
    }

    public void setEtVendorName(EditText e) {
        this.vendorName = e.getText().toString();
        this.createListingPresenter.setVendorName(this.vendorName);
    }

    public void setEtVendorAddress1(EditText e) {
        this.vendorAddress1 = e.getText().toString();
        this.createListingPresenter.setVendorAddress1(this.vendorAddress1);
    }

    public void setEtVendorAddress2(EditText e) {
        this.vendorAddress2 = e.getText().toString();
        this.createListingPresenter.setVendorAddress2(this.vendorAddress2);
    }

    public void setEtVendorAddress3(EditText e) {
        this.vendorAddress3 = e.getText().toString();
        this.createListingPresenter.setVendorAddress3(this.vendorAddress3);
    }

    public void setEtVendorPostcode(EditText e) {
        this.vendorPostcode = e.getText().toString().toUpperCase();
        this.createListingPresenter.setVendorPostcode(this.vendorPostcode);
    }

    public void setEtVendorTelephone(EditText e) {
        this.vendorTelephone = e.getText().toString();
        this.createListingPresenter.setVendorTelephone(this.vendorTelephone);
    }

    public void setEtVendorEmail(EditText e) {
        this.vendorEmail = e.getText().toString();
        this.createListingPresenter.setVendorEmail(this.vendorEmail);
    }

    public void setEtVendorWebsite(EditText e) {
        this.vendorWebsite = e.getText().toString();
        this.createListingPresenter.setVendorWebsite(this.vendorWebsite);
    }

    public void setEtListingTradeReferralCode(EditText e) {
        this.listingTradeReferralCode = e.getText().toString();
        this.createListingPresenter.setListingTradeReferralCode(this.listingTradeReferralCode);
    }

    public void setEtListingTradeSummary(EditText e) {
        this.listingTradeSummary = e.getText().toString();
        this.createListingPresenter.setListingTradeSummary(this.listingTradeSummary);
    }

    public void setEtListingTradeDate(EditText e) {
        this.listingTradeDate = e.getText().toString();
        this.createListingPresenter.setListingTradeDate(this.listingTradeDate);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0246R.menu.menu_create, menu);
        return super.onCreateOptionsMenu(menu);
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

    protected void onStart() {
        super.onStart();
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
                goFeedback();
                return true;
            case C0246R.id.action_home:
                goHome();
                return true;
            case C0246R.id.action_email_us:
                goContact();
                return true;
            case C0246R.id.action_search:
                goSearch();
                return true;
            case C0246R.id.action_login:
                goLogin();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void goHome() {
        startActivity(new Intent(this, MainView.class));
    }

    private void goLogin() {
        startActivity(new Intent(this, LoginView.class));
    }

    private void goSearch() {
        startActivity(new Intent(this, SearchEntryView.class));
    }

    private void goContact() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.EMAIL", new String[]{Developer.ADMIN_EMAIL});
        intent.putExtra("android.intent.extra.SUBJECT", "Abe directory query");
        intent.setType(HTTP.PLAIN_TEXT_TYPE);
        startActivity(intent);
    }

    private void goFeedback() {
        startActivity(new Intent(this, FeedbackView.class));
    }
}
