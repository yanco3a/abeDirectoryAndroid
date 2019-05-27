package com.directory.abe.DetailEntryFeature.Presentation;

import android.os.Bundle;
import com.directory.abe.DetailEntryFeature.UseCases.IVendorDetailsPresenter;
import com.directory.abe.DetailEntryFeature.UseCases.InsertUserReviewUseCase;
import com.directory.abe.GSONModels.EntryModel;
import java.util.ArrayList;
import org.apache.http.HttpStatus;

public class VendorDetailsPresenter implements IVendorDetailsPresenter {
    private static final String TAG = VendorDetailsPresenter.class.getSimpleName();
    private InsertUserReviewUseCase insertUserReviewUseCase = new InsertUserReviewUseCase(this);
    private int listingId;
    private int uId;
    private IVendorDetailsView vendorDetailsView;

    public VendorDetailsPresenter(IVendorDetailsView vendorDetailsView) {
        this.vendorDetailsView = vendorDetailsView;
    }

    public boolean isUserReviewValid(String userReview) {
        if (userReview == null) {
            return false;
        }
        try {
            if (userReview.length() <= 1 || !userReview.matches("^[a-zA-Z0-9\\n \\u2026/.,:;&()-/'+!?]+$") || userReview.length() >= HttpStatus.SC_OK) {
                return false;
            }
            return true;
        } catch (NullPointerException ne) {
            ne.printStackTrace();
            ne.getMessage();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            return false;
        }
    }

    public void sendMessagesToUser(String msg) {
        if (this.vendorDetailsView != null) {
            this.vendorDetailsView.sendMessagesToUser(msg);
        }
    }

    public void insertUserReview(int userId, int listingId, float rating, String userReview) {
        this.insertUserReviewUseCase.insertUserReview(userId, listingId, rating, userReview);
    }

    public void onInsertUserReviewFailure() {
        if (this.vendorDetailsView != null) {
            this.vendorDetailsView.onInsertUserReviewFailure("Abe couldn't process your review");
        }
    }

    public void onInsertUserReviewSuccess(EntryModel body, int userId, int listingId, float rating, String userReview) {
        if (body == null) {
            onInsertUserReviewFailure();
        } else if (this.vendorDetailsView != null) {
            this.vendorDetailsView.onInsertUserReviewSuccess("Thank you for your review!");
        } else {
            onInsertUserReviewFailure();
        }
    }

    public String cleanSearch(String s) {
        return s.replaceAll("[^\\w\\s]", "");
    }

    public void storeSharedPrefs() {
        this.vendorDetailsView.storeSharedPrefs();
    }

    public void setUpAnalytics() {
        this.vendorDetailsView.setUpAnalytics();
    }

    public void onClickHandler(String userReview, Bundle extras, int uId) {
        if (isUserReviewValid(userReview)) {
            String tUserReview = userReview.trim();
            if (getRatingValue() != -1.0f) {
                float rating = getRatingValue();
                if (getUserLoggedIn(extras) != -1) {
                    insertUserReview(getUserLoggedIn(extras), this.listingId, rating, tUserReview);
                    return;
                }
                sendMessagesToUser("you must be logged in as a user to do that");
                this.vendorDetailsView.goToLogin(this.listingId, rating, tUserReview);
                return;
            }
            this.vendorDetailsView.setErrorText("Abe wants you to rate this business");
            return;
        }
        this.vendorDetailsView.setErrorText("A valid review is required. Note: only the following symbols allowed. (.,:;&()-/'+!? ...)");
    }

    public int getUserLoggedIn(Bundle extras) {
        if (extras == null || this.uId <= 0) {
            return -1;
        }
        return this.uId;
    }

    public float getRatingValue() {
        return this.vendorDetailsView.getRatingValue();
    }

    private void setUserId(int id) {
        this.uId = id;
    }

    public void setUpData(Bundle extras, int uId, EntryModel m, ArrayList<EntryModel> objs, boolean canReview) {
        try {
            setUserId(uId);
            if (extras != null && m != null) {
                this.listingId = m.getRatingListingId();
                String st_vendorame = m.getVendorName();
                int st_vendorId = m.getVendorId();
                String st_addy1 = m.getVendorAddress1();
                String st_addy2 = m.getVendorAddress2();
                String st_addy3 = m.getVendorAddress3();
                String st_postcode = m.getVendorPostcode();
                String st_tele = m.getVendorTelephone();
                String st_email = m.getVendorEmail();
                String st_website = m.getVendorWebsite();
                String st_type = m.getVendorType();
                String st_isVerified = "Verified: " + m.getListingTradeVerified();
                String st_summary = m.getListingTradeSummary();
                String st_date = "";
                if (m.getListingTradeDate() != null) {
                    if (m.getListingTradeDate().length() < 2) {
                        st_date = null;
                    } else {
                        st_date = "Trading since: " + m.getListingTradeDate();
                    }
                }
                String st_category = m.getListingTradeCategory();
                double d_lat = m.getVendorLat();
                double d_lng = m.getVendorLng();
                String str = "";
                float count = 0.0f;
                float currentValue = 0.0f;
                float fl_rating_bar = 0.0f;
                if (objs != null) {
                    for (int i = 0; i < objs.size(); i++) {
                        if (m.getListingId() == ((EntryModel) objs.get(i)).getRatingListingId()) {
                            ((EntryModel) objs.get(i)).getRatingReview();
                            if (((EntryModel) objs.get(i)).getRatingReview().length() > 1) {
                                str = str + ((EntryModel) objs.get(i)).getUsername() + ": \"" + ((EntryModel) objs.get(i)).getRatingReview() + "\"" + "\n" + "\n";
                                currentValue += ((EntryModel) objs.get(i)).getRatingValue();
                                count += 1.0f;
                            }
                        }
                    }
                }
                if (currentValue != 0.0f) {
                    fl_rating_bar = currentValue / count;
                } else {
                    str = "no reviews yet!";
                }
                this.vendorDetailsView.setVendorName(st_vendorame);
                this.vendorDetailsView.setVendorId(st_vendorId);
                this.vendorDetailsView.setAddy3(st_addy3);
                this.vendorDetailsView.setPostcode(st_postcode);
                this.vendorDetailsView.setVendorame(st_vendorame);
                this.vendorDetailsView.setAddy1(st_addy1);
                this.vendorDetailsView.setAddy2(st_addy2);
                this.vendorDetailsView.setAddy3(st_addy3);
                this.vendorDetailsView.setPostcode(st_postcode);
                this.vendorDetailsView.setTel(st_tele);
                this.vendorDetailsView.setEmail(st_email);
                this.vendorDetailsView.setWebsite(st_website, st_vendorame);
                this.vendorDetailsView.setType(st_type);
                this.vendorDetailsView.setVerified(st_isVerified);
                this.vendorDetailsView.setSummary(st_summary);
                this.vendorDetailsView.setDate(st_date);
                this.vendorDetailsView.setCategory(st_category);
                this.vendorDetailsView.setRating(fl_rating_bar);
                this.vendorDetailsView.setLat(d_lat);
                this.vendorDetailsView.setLng(d_lng);
                this.vendorDetailsView.disableErrorText();
                if (canReview) {
                    this.vendorDetailsView.setUserScreen(str);
                } else {
                    this.vendorDetailsView.setAdminScreen(str);
                }
                if (st_type != null && st_postcode != null) {
                    if ((st_type.equals("Store") || st_type.equals("Other")) && d_lng != 0.0d) {
                        this.vendorDetailsView.showAdditionalData();
                        this.vendorDetailsView.initMap();
                        return;
                    }
                    this.vendorDetailsView.hideAdditionalData();
                    this.vendorDetailsView.disableMap();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void launchWebsite() {
        this.vendorDetailsView.launchWebsite();
    }
}
