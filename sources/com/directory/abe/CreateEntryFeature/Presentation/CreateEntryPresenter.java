package com.directory.abe.CreateEntryFeature.Presentation;

import android.app.Activity;
import com.directory.abe.AnalyticsApp;
import com.directory.abe.CreateEntryFeature.UseCases.CreateEntryUseCase;
import com.directory.abe.CreateEntryFeature.UseCases.ICreateEntryPresenter;
import com.directory.abe.GSONModels.EntryModel;
import com.directory.abe.IAnalyse;
import com.directory.abe.Models.Admin;
import com.directory.abe.Models.CompanyHouse;
import com.directory.abe.Models.Listing;
import com.directory.abe.Models.Rating;
import com.directory.abe.Models.User;
import com.directory.abe.Models.Vendor;
import com.google.android.gms.analytics.HitBuilders.EventBuilder;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.gms.analytics.Tracker;

public class CreateEntryPresenter implements ICreateEntryPresenter, IAnalyse {
    private static final String TAG = CreateEntryPresenter.class.getSimpleName();
    /* renamed from: a */
    private Admin f8a = new Admin();
    private Activity activity;
    private int adminId;
    private String adminUsername;
    private Tracker appTracker;
    /* renamed from: c */
    private CompanyHouse f9c = new CompanyHouse();
    private String companyHouseNo;
    private CreateEntryUseCase createEntryUseCase = new CreateEntryUseCase(this);
    private ICreateEntryView iCreateEntryView;
    /* renamed from: l */
    private Listing f10l = new Listing();
    private String listingTradeCategory;
    private String listingTradeDate;
    private String listingTradeReferralCode;
    private String listingTradeSummary;
    private EntryModel model1;
    /* renamed from: r */
    private Rating f11r = new Rating();
    /* renamed from: u */
    private User f12u = new User();
    /* renamed from: v */
    private Vendor f13v = new Vendor();
    private String vendorAddress1;
    private String vendorAddress2;
    private String vendorAddress3;
    private String vendorEmail;
    private String vendorName;
    private String vendorPostcode;
    private String vendorTelephone;
    private String vendorType;
    private String vendorWebsite;

    public CreateEntryPresenter(ICreateEntryView iCreateEntryView) {
        this.iCreateEntryView = iCreateEntryView;
    }

    public void storeSharedPrefs() {
        this.iCreateEntryView.storeSharedPrefs();
    }

    public boolean isAllDataValid() {
        try {
            if (this.companyHouseNo.equals("-2")) {
                this.iCreateEntryView.toastLMsg("Abe doesn't recognise that Companies house no. If unsure, please leave it blank.");
                return false;
            } else if (this.listingTradeCategory == null) {
                this.iCreateEntryView.toastLMsg("Please select a Category");
                return false;
            } else if (this.listingTradeCategory.equals("") || this.listingTradeCategory.equals("Categories")) {
                this.iCreateEntryView.toastLMsg("Please select a Category");
                return false;
            } else if (this.vendorType == null) {
                this.iCreateEntryView.toastLMsg("Please select business type i.e. Store/Outlet, Online or Other");
                return false;
            } else if (!this.vendorType.equals("Other") && !this.vendorType.equals("Online") && !this.vendorType.equals("Store")) {
                this.iCreateEntryView.toastLMsg("Please select business type i.e. Store/Outlet, Online or Other");
                return false;
            } else if (this.iCreateEntryView.validateAllUserData(this.vendorType)) {
                return true;
            } else {
                this.iCreateEntryView.toastLMsg("Listing incomplete");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            return false;
        }
    }

    public void setUserDataToModel() {
        this.f9c.setCompanyHouseNo(this.companyHouseNo);
        this.f13v.setVendorName(this.vendorName);
        this.f13v.setVendorAddress1(this.vendorAddress1);
        this.f13v.setVendorAddress2(this.vendorAddress2);
        this.f13v.setVendorAddress3(this.vendorAddress3);
        this.f13v.setVendorPostcode(this.vendorPostcode);
        this.f13v.setVendorTelephone(this.vendorTelephone);
        this.f13v.setVendorEmail(this.vendorEmail);
        this.f13v.setVendorWebsite(this.vendorWebsite);
        this.f13v.setVendorType(this.vendorType);
        this.f8a.setAdminId(this.adminId);
        this.f8a.setAdminUsername(this.adminUsername);
        this.f10l.setListingTradeCategory(this.listingTradeCategory);
        this.f10l.setListingReferralCode(this.listingTradeReferralCode);
        this.f10l.setListingTradeSummary(this.listingTradeSummary);
        this.f10l.setListingTradeDate(this.listingTradeDate);
        this.model1 = new EntryModel(this.f9c, this.f13v, this.f10l, this.f8a, this.f12u, this.f11r);
    }

    public void createVendorOnClick() {
        try {
            this.iCreateEntryView.setAllData();
            if (isAllDataValid()) {
                this.iCreateEntryView.clearErrorText();
                this.iCreateEntryView.progessDialogMsg("Submitting your request...");
                setUserDataToModel();
                createVendorOnClickPrtTwo();
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            try {
                this.activity.finish();
            } catch (Exception e1) {
                e1.printStackTrace();
                e1.getMessage();
            }
        }
    }

    public void sendModelToUseCase(EntryModel m) {
        if (this.iCreateEntryView != null) {
            this.createEntryUseCase.createEntryAndAttemptAutoVerify(this.companyHouseNo, m);
        }
    }

    public void createVendorOnClickPrtTwo() {
        sendModelToUseCase(this.model1);
    }

    public void onCreateSuccess(final EntryModel model) {
        this.iCreateEntryView.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if (CreateEntryPresenter.this.iCreateEntryView != null && model != null) {
                    CreateEntryPresenter.this.iCreateEntryView.onCreateSuccess(model);
                }
            }
        });
    }

    public void onCreateFailure(String msg) {
        this.iCreateEntryView.onCreateFailure(msg);
    }

    public void sendLongMsgToUser(String msg) {
        if (this.iCreateEntryView != null) {
            this.iCreateEntryView.toastLMsg(msg);
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
        this.appTracker.setScreenName("Screen~" + tag);
        this.appTracker.send(new ScreenViewBuilder().build());
    }

    public void setUpAnalytics(Activity activity) {
        try {
            this.appTracker = ((AnalyticsApp) activity.getApplication()).getDefaultTracker();
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void setCompanyHouse(String companyHouseNo) {
        this.companyHouseNo = companyHouseNo;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public void setVendorAddress1(String vendorAddress1) {
        this.vendorAddress1 = vendorAddress1;
    }

    public void setVendorAddress2(String vendorAddress2) {
        this.vendorAddress2 = vendorAddress2;
    }

    public void setVendorAddress3(String vendorAddress3) {
        this.vendorAddress3 = vendorAddress3;
    }

    public void setVendorPostcode(String vendorPostcode) {
        this.vendorPostcode = vendorPostcode;
    }

    public void setVendorTelephone(String vendorTelephone) {
        this.vendorTelephone = vendorTelephone;
    }

    public void setVendorEmail(String vendorEmail) {
        this.vendorEmail = vendorEmail;
    }

    public void setVendorWebsite(String vendorWebsite) {
        if (vendorWebsite == null) {
            this.vendorWebsite = "";
        } else if (vendorWebsite.equals("http://") || vendorWebsite.equals("https://") || vendorWebsite.equals("")) {
            this.vendorWebsite = "";
        } else {
            this.vendorWebsite = vendorWebsite;
        }
    }

    public void setVendorType(String vendorType) {
        this.vendorType = vendorType;
    }

    public void setListingTradeCategory(String listingTradeCategory) {
        this.listingTradeCategory = listingTradeCategory;
    }

    public void setListingTradeReferralCode(String listingTradeReferralCode) {
        this.listingTradeReferralCode = listingTradeReferralCode;
    }

    public void setListingTradeSummary(String listingTradeSummary) {
        this.listingTradeSummary = listingTradeSummary;
    }

    public void setListingTradeDate(String listingTradeDate) {
        this.listingTradeDate = listingTradeDate;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public void checkTypeSelected(String vType) {
        this.iCreateEntryView.checkTypeSelected(vType);
    }
}
