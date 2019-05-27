package com.directory.abe.DetailEntryFeature.Presentation;

import android.widget.TextView;

public interface IVendorDetailsView {
    void disableErrorText();

    void disableMap();

    void endDetailedSearch();

    float getRatingValue();

    void goToLogin(int i, float f, String str);

    void hideAdditionalData();

    void initMap();

    void launchWebsite();

    void navToPreviousScreen();

    void onInsertUserReviewFailure(String str);

    void onInsertUserReviewSuccess(String str);

    void sendMessagesToUser(String str);

    void setAddy1(String str);

    void setAddy2(String str);

    void setAddy3(String str);

    void setAdminScreen(String str);

    void setCategory(String str);

    void setDate(String str);

    void setEmail(String str);

    void setErrorText(String str);

    void setLat(double d);

    void setLng(double d);

    void setPostcode(String str);

    void setRating(float f);

    void setSummary(String str);

    void setTel(String str);

    void setType(String str);

    void setUpAnalytics();

    void setUserScreen(String str);

    void setVendorId(int i);

    void setVendorName(String str);

    void setVendorame(String str);

    void setVerified(String str);

    void setWebsite(String str, String str2);

    void showAdditionalData();

    void showOrHideData(TextView textView, String str);

    void storeSharedPrefs();
}
