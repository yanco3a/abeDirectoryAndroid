package com.directory.abe.CreateEntryFeature.Presentation;

import android.app.Activity;
import android.widget.EditText;
import com.directory.abe.GSONModels.EntryModel;

public interface ICreateEntryView {
    void checkTypeSelected(String str);

    void clearErrorText();

    void displayHelp();

    Activity getActivity();

    void onCreateFailure(String str);

    void onCreateSuccess(EntryModel entryModel);

    void progessDialogMsg(String str);

    void setAdminId(int i);

    void setAdminUsername(String str);

    void setAllData();

    void setEtCompanyHouseNo(EditText editText);

    void setEtListingTradeDate(EditText editText);

    void setEtListingTradeReferralCode(EditText editText);

    void setEtListingTradeSummary(EditText editText);

    void setEtVendorAddress1(EditText editText);

    void setEtVendorAddress2(EditText editText);

    void setEtVendorAddress3(EditText editText);

    void setEtVendorEmail(EditText editText);

    void setEtVendorName(EditText editText);

    void setEtVendorPostcode(EditText editText);

    void setEtVendorTelephone(EditText editText);

    void setEtVendorWebsite(EditText editText);

    void setTradeCategory(String str);

    void setVendorType(String str);

    void storeSharedPrefs();

    void toastLMsg(String str);

    void toastSMsg(String str);

    boolean validateAllUserData(String str);
}
