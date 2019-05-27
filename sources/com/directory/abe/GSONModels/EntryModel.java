package com.directory.abe.GSONModels;

import com.directory.abe.Models.Admin;
import com.directory.abe.Models.CompanyHouse;
import com.directory.abe.Models.Listing;
import com.directory.abe.Models.Rating;
import com.directory.abe.Models.User;
import com.directory.abe.Models.Vendor;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

public class EntryModel implements Serializable {
    public Admin admin;
    public int adminId;
    public String adminPassword;
    public String adminUsername;
    private String basedIn;
    private Object code;
    public CompanyHouse companyHouse;
    private String companyHouseNo;
    /* renamed from: e */
    public String f15e;
    private ArrayList<EntryModel> entryModelList;
    public ArrayList<Error> errResponse;
    @SerializedName("error")
    @Expose
    public Error error;
    private int feedbackId;
    private String howEasyLogReg;
    private String howEasySearch;
    private String howImpressed;
    private String howOften;
    private String isBusinessUser;
    public Listing listing;
    public int listingAdminId;
    public String listingDate;
    public int listingId;
    public String listingIsFeatured;
    public int listingPoints;
    public String listingReferralCode;
    public String listingTradeCategory;
    public String listingTradeDate;
    public String listingTradeSummary;
    public String listingTradeVerified;
    public int listingVendorId;
    private String locationType;
    public String message;
    private String msg;
    public Rating rating;
    public int ratingId;
    public int ratingListingId;
    public String ratingReview;
    public int ratingUserId;
    public float ratingValue;
    public float ratingcount;
    private String shopsMostIn;
    private String successfulLogReg;
    private String suggestions;
    private String testerEmail;
    public float totalRatingValue;
    public User user;
    public String userEmail;
    public int userId;
    public int userLat;
    public int userLng;
    public String userPassword;
    public String userSessionId;
    public String username;
    public Vendor vendor;
    public String vendorAddress1;
    public String vendorAddress2;
    public String vendorAddress3;
    public String vendorEmail;
    public int vendorId;
    public double vendorLat;
    public double vendorLng;
    public String vendorName;
    public String vendorPostcode;
    public String vendorTelephone;
    public String vendorType;
    public String vendorWebsite;
    private String whichFeatures;

    public EntryModel(Vendor v, Listing l, Admin a) {
    }

    public EntryModel(CompanyHouse c, Vendor v, Listing l, Admin a, User u, Rating r) {
        this.vendor = v;
        this.listing = l;
        this.admin = a;
        this.user = u;
        this.companyHouse = c;
        this.rating = r;
        this.adminId = this.admin.getAdminId();
        this.adminUsername = this.admin.getAdminUsername();
        this.adminPassword = this.admin.getAdminPassword();
        this.userId = this.user.getUserId();
        this.userSessionId = this.user.getUserSessionId();
        this.username = this.user.getUsername();
        this.userEmail = this.user.getUserEmail();
        this.userPassword = this.user.getUserPassword();
        this.userLng = this.user.getUserLng();
        this.userLat = this.user.getUserLat();
        this.vendorId = this.vendor.getVendorId();
        this.vendorName = this.vendor.getVendorName();
        this.vendorAddress1 = this.vendor.getVendorAddress1();
        this.vendorAddress2 = this.vendor.getVendorAddress2();
        this.vendorAddress3 = this.vendor.getVendorAddress3();
        this.vendorPostcode = this.vendor.getVendorPostcode();
        this.vendorTelephone = this.vendor.getVendorTelephone();
        this.vendorEmail = this.vendor.getVendorEmail();
        this.vendorWebsite = this.vendor.getVendorWebsite();
        this.vendorType = this.vendor.getVendorType();
        this.vendorLat = this.vendor.getVendorLat();
        this.vendorLng = this.vendor.getVendorLng();
        this.listingAdminId = this.listing.getListingAdminId();
        this.listingVendorId = this.listing.getListingVendorId();
        this.listingDate = this.listing.getListingDate();
        this.listingPoints = this.listing.getListingPoints();
        this.listingIsFeatured = this.listing.getListingIsFeatured();
        this.listingReferralCode = this.listing.getListingReferralCode();
        this.listingTradeCategory = this.listing.getListingTradeCategory();
        this.listingTradeVerified = this.listing.getListingTradeVerified();
        this.listingTradeSummary = this.listing.getListingTradeSummary();
        this.listingTradeDate = this.listing.getListingTradeDate();
        this.ratingId = this.rating.getRatingId();
        this.ratingListingId = this.rating.getRatingListingId();
        this.ratingUserId = this.rating.getRatingUserId();
        this.ratingReview = this.rating.getRatingReview();
        this.ratingValue = this.rating.getRatingValue();
        this.totalRatingValue = this.rating.getTotalRatingValue();
        this.companyHouseNo = this.companyHouse.getCompanyHouseNo();
    }

    public EntryModel(int ratingId, int ratingListingId, int ratingUserId, float totalRatingValue, String ratingReview) {
        this.ratingId = ratingId;
        this.ratingListingId = ratingListingId;
        this.ratingUserId = ratingUserId;
        this.ratingReview = ratingReview;
        this.totalRatingValue = totalRatingValue;
    }

    public EntryModel(int vendorId, String vendorName, String vendorAddress1, String vendorAddress2, String vendorAddress3, String vendorPostcode, String vendorTelephone, String vendorEmail, String vendorWebsite, String vendorType, double vendorLat, double vendorLng, int listingId, String listingDate, int listingPoints, String listingIsFeatured, String listingReferralCode, String listingTradeCategory, String listingTradeVerified, String listingTradeSummary, String listingTradeDate, int ratingId, int ratingListingId, int ratingUserId, String username, float ratingValue, String ratingReview) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.vendorAddress1 = vendorAddress1;
        this.vendorAddress2 = vendorAddress2;
        this.vendorAddress3 = vendorAddress3;
        this.vendorPostcode = vendorPostcode;
        this.vendorTelephone = vendorTelephone;
        this.vendorEmail = vendorEmail;
        this.vendorWebsite = vendorWebsite;
        this.vendorType = vendorType;
        this.vendorLat = vendorLat;
        this.vendorLng = vendorLng;
        this.listingId = listingId;
        this.listingDate = listingDate;
        this.listingPoints = listingPoints;
        this.listingIsFeatured = listingIsFeatured;
        this.listingReferralCode = listingReferralCode;
        this.listingTradeCategory = listingTradeCategory;
        this.listingTradeVerified = listingTradeVerified;
        this.listingTradeSummary = listingTradeSummary;
        this.listingTradeDate = listingTradeDate;
        this.ratingId = ratingId;
        this.ratingListingId = ratingListingId;
        this.ratingUserId = ratingUserId;
        this.username = username;
        this.ratingValue = ratingValue;
        this.ratingReview = ratingReview;
    }

    public EntryModel(Vendor vendor) {
        this.vendor = vendor;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getLocationType() {
        return this.locationType;
    }

    public String getCompanyHouseNo() {
        return this.companyHouseNo;
    }

    public int getAdminId() {
        return this.adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getAdminUsername() {
        return this.adminUsername;
    }

    public String getAdminPassword() {
        return this.adminPassword;
    }

    public int getVendorId() {
        return this.vendorId;
    }

    public String getVendorName() {
        return this.vendorName;
    }

    public String getVendorAddress1() {
        return this.vendorAddress1;
    }

    public String getVendorAddress2() {
        return this.vendorAddress2;
    }

    public String getVendorAddress3() {
        return this.vendorAddress3;
    }

    public String getVendorPostcode() {
        return this.vendorPostcode;
    }

    public String getVendorTelephone() {
        return this.vendorTelephone;
    }

    public String getVendorEmail() {
        return this.vendorEmail;
    }

    public String getVendorWebsite() {
        return this.vendorWebsite;
    }

    public String getVendorType() {
        return this.vendorType;
    }

    public int getListingVendorId() {
        return this.listingVendorId;
    }

    public int getListingAdminId() {
        return this.listingAdminId;
    }

    public String getListingDate() {
        return this.listingDate;
    }

    public int getListingPoints() {
        return this.listingPoints;
    }

    public String getListingIsFeatured() {
        return this.listingIsFeatured;
    }

    public String getListingReferralCode() {
        return this.listingReferralCode;
    }

    public String getListingTradeCategory() {
        return this.listingTradeCategory;
    }

    public String getListingTradeVerified() {
        return this.listingTradeVerified;
    }

    public String getListingTradeSummary() {
        return this.listingTradeSummary;
    }

    public String getListingTradeDate() {
        return this.listingTradeDate;
    }

    public void setCompanyHouseNo(String companyHouseNo) {
        this.companyHouseNo = companyHouseNo;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
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
        this.vendorWebsite = vendorWebsite;
    }

    public void setVendorType(String vendorType) {
        this.vendorType = vendorType;
    }

    public double getVendorLng() {
        return this.vendorLng;
    }

    public void setVendorLng(double vendorLng) {
        this.vendorLng = vendorLng;
    }

    public double getVendorLat() {
        return this.vendorLat;
    }

    public void setVendorLat(double vendorLat) {
        this.vendorLat = vendorLat;
    }

    public int getListingId() {
        return this.listingId;
    }

    public void setListingVendorId(int listingVendorId) {
        this.listingVendorId = listingVendorId;
    }

    public void setListingAdminId(int listingAdminId) {
        this.listingAdminId = listingAdminId;
    }

    public void setListingDate(String listingDate) {
        this.listingDate = listingDate;
    }

    public void setListingPoints(int listingPoints) {
        this.listingPoints = listingPoints;
    }

    public void setListingIsFeatured(String listingIsFeatured) {
        this.listingIsFeatured = listingIsFeatured;
    }

    public void setListingReferralCode(String listingReferralCode) {
        this.listingReferralCode = listingReferralCode;
    }

    public void setListingTradeCategory(String listingTradeCategory) {
        this.listingTradeCategory = listingTradeCategory;
    }

    public void setListingTradeVerified(String listingTradeVerified) {
        this.listingTradeVerified = listingTradeVerified;
    }

    public void setListingTradeSummary(String listingTradeSummary) {
        this.listingTradeSummary = listingTradeSummary;
    }

    public void setListingTradeDate(String listingTradeDate) {
        this.listingTradeDate = listingTradeDate;
    }

    public String getUserSessionId() {
        return this.userSessionId;
    }

    public void setUserSessionId(String userSessionId) {
        this.userSessionId = userSessionId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return this.userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getUserLng() {
        return this.userLng;
    }

    public void setUserLng(int userLng) {
        this.userLng = userLng;
    }

    public int getUserLat() {
        return this.userLat;
    }

    public void setUserLat(int userLat) {
        this.userLat = userLat;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setRatingValue(float ratingValue) {
        this.ratingValue = ratingValue;
    }

    public float getRatingValue() {
        return this.ratingValue;
    }

    public int getRatingId() {
        return this.ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public int getRatingListingId() {
        return this.ratingListingId;
    }

    public void setRatingListingId(int ratingListingId) {
        this.ratingListingId = ratingListingId;
    }

    public int getRatingUserId() {
        return this.ratingUserId;
    }

    public void setRatingUserId(int ratingUserId) {
        this.ratingUserId = ratingUserId;
    }

    public String getRatingReview() {
        return this.ratingReview;
    }

    public void setRatingReview(String ratingReview) {
        this.ratingReview = ratingReview;
    }

    public float getRatingcount() {
        return this.ratingcount;
    }

    public void setRatingcount(float ratingcount) {
        this.ratingcount = ratingcount;
    }

    public float getTotalRatingValue() {
        return this.totalRatingValue;
    }

    public void setTotalRatingValue(float totalRatingValue) {
        this.totalRatingValue = totalRatingValue;
    }

    public int getFeedbackId() {
        return this.feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getIsBusinessUser() {
        return this.isBusinessUser;
    }

    public void setIsBusinessUser(String isBusinessUser) {
        this.isBusinessUser = isBusinessUser;
    }

    public String getBasedIn() {
        return this.basedIn;
    }

    public void setBasedIn(String basedIn) {
        this.basedIn = basedIn;
    }

    public String getShopsMostIn() {
        return this.shopsMostIn;
    }

    public void setShopsMostIn(String shopsMostIn) {
        this.shopsMostIn = shopsMostIn;
    }

    public String getSuccessfulLogReg() {
        return this.successfulLogReg;
    }

    public void setSuccessfulLogReg(String successfulLogReg) {
        this.successfulLogReg = successfulLogReg;
    }

    public String getHowEasyLogReg() {
        return this.howEasyLogReg;
    }

    public void setHowEasyLogReg(String howEasyLogReg) {
        this.howEasyLogReg = howEasyLogReg;
    }

    public String getWhichFeatures() {
        return this.whichFeatures;
    }

    public void setWhichFeatures(String whichFeatures) {
        this.whichFeatures = whichFeatures;
    }

    public String getHowEasySearch() {
        return this.howEasySearch;
    }

    public void setHowEasySearch(String howEasySearch) {
        this.howEasySearch = howEasySearch;
    }

    public String getHowImpressed() {
        return this.howImpressed;
    }

    public void setHowImpressed(String howImpressed) {
        this.howImpressed = howImpressed;
    }

    public String getHowOften() {
        return this.howOften;
    }

    public void setHowOften(String howOften) {
        this.howOften = howOften;
    }

    public String getSuggestions() {
        return this.suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }

    public void setTesterEmail(String testerEmail) {
        this.testerEmail = testerEmail;
    }

    public String getTesterEmail() {
        return this.testerEmail;
    }

    public String toString() {
        return "EntryModel{userSessionId='" + this.userSessionId + '\'' + ", username='" + this.username + '\'' + ", userEmail='" + this.userEmail + '\'' + ", userPassword='" + this.userPassword + '\'' + ", userLng=" + this.userLng + ", userLat=" + this.userLat + ", userId=" + this.userId + ", adminId=" + this.adminId + ", adminUsername='" + this.adminUsername + '\'' + ", adminPassword='" + this.adminPassword + '\'' + ", vendorId=" + this.vendorId + ", vendorName='" + this.vendorName + '\'' + ", vendorAddress1='" + this.vendorAddress1 + '\'' + ", vendorAddress2='" + this.vendorAddress2 + '\'' + ", vendorAddress3='" + this.vendorAddress3 + '\'' + ", vendorPostcode='" + this.vendorPostcode + '\'' + ", vendorTelephone='" + this.vendorTelephone + '\'' + ", vendorEmail='" + this.vendorEmail + '\'' + ", vendorWebsite='" + this.vendorWebsite + '\'' + ", vendorType='" + this.vendorType + '\'' + ", vendorLat=" + this.vendorLat + ", vendorLng=" + this.vendorLng + ", listingId=" + this.listingId + ", listingVendorId=" + this.listingVendorId + ", listingAdminId=" + this.listingAdminId + ", listingDate='" + this.listingDate + '\'' + ", listingPoints=" + this.listingPoints + ", listingIsFeatured='" + this.listingIsFeatured + '\'' + ", listingReferralCode='" + this.listingReferralCode + '\'' + ", listingTradeCategory='" + this.listingTradeCategory + '\'' + ", listingTradeVerified='" + this.listingTradeVerified + '\'' + ", listingTradeSummary='" + this.listingTradeSummary + '\'' + ", listingTradeDate='" + this.listingTradeDate + '\'' + ", ratingId=" + this.ratingId + ", ratingValue=" + this.ratingValue + ", ratingListingId=" + this.ratingListingId + ", ratingUserId=" + this.ratingUserId + ", ratingReview='" + this.ratingReview + '\'' + ", totalRatingValue='" + this.totalRatingValue + '\'' + ", ratingcount='" + this.ratingcount + '\'' + ", feedbackId='" + this.feedbackId + '\'' + ", isBusinessUser='" + this.isBusinessUser + '\'' + ", basedIn='" + this.basedIn + '\'' + ", shopsMostIn='" + this.shopsMostIn + '\'' + ", successfulLogReg='" + this.successfulLogReg + '\'' + ", howEasyLogReg='" + this.howEasyLogReg + '\'' + ", whichFeatures='" + this.whichFeatures + '\'' + ", howEasySearch='" + this.howEasySearch + '\'' + ", howImpressed='" + this.howImpressed + '\'' + ", howOften='" + this.howOften + '\'' + ", suggestions='" + this.suggestions + '\'' + ", testerEmail='" + this.testerEmail + '\'' + ", e='" + this.f15e + '\'' + ", error='" + this.error + '\'' + ", message='" + this.message + '\'' + '}';
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getCode() {
        return this.code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public Vendor getVendor() {
        return this.vendor;
    }

    public Admin getAdmin() {
        return this.admin;
    }

    public User getUser() {
        return this.user;
    }

    public Listing getListing() {
        return this.listing;
    }

    public Rating getRating() {
        return this.rating;
    }

    public CompanyHouse getCompanyHouse() {
        return this.companyHouse;
    }

    public ArrayList<EntryModel> getEntryModel() {
        return this.entryModelList;
    }

    public void setEntryModel(ArrayList<EntryModel> entryModelList) {
        this.entryModelList = entryModelList;
    }

    public Error getError() {
        return this.error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public String getE() {
        return this.f15e;
    }

    public void setE(String e) {
        this.f15e = e;
    }

    public ArrayList<Error> getErrResponse() {
        return this.errResponse;
    }

    public void setErrResponse(ArrayList<Error> errResponse) {
        this.errResponse = errResponse;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }
}
