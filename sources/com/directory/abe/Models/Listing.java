package com.directory.abe.Models;

import java.io.Serializable;

public class Listing implements Serializable {
    private int listingAdminId;
    private String listingDate;
    private int listingId;
    private String listingIsFeatured;
    private int listingPoints;
    private String listingReferralCode;
    private String listingTradeCategory;
    private String listingTradeDate;
    private String listingTradeSummary;
    private String listingTradeVerified;
    private int listingVendorId;

    public Listing(int listingId, int listingAdminId, int listingVendorId, String listingDate, int listingPoints, String listingIsFeatured, String listingReferralCode, String listingTradeCategory, String listingTradeVerified, String listingTradeSummary, String listingTradeDate) {
        this.listingId = listingId;
        this.listingAdminId = listingAdminId;
        this.listingVendorId = listingVendorId;
        this.listingDate = listingDate;
        this.listingPoints = listingPoints;
        this.listingIsFeatured = listingIsFeatured;
        this.listingTradeCategory = listingTradeCategory;
        this.listingReferralCode = listingReferralCode;
        this.listingTradeVerified = listingTradeVerified;
        this.listingTradeSummary = listingTradeSummary;
        this.listingTradeDate = listingTradeDate;
    }

    public String isRegistered(String isVerified, String inCompanyHouse, String isRegisteredByEmail) {
        if (Boolean.getBoolean(inCompanyHouse) && Boolean.getBoolean(isRegisteredByEmail)) {
            this.listingTradeVerified = "true";
        }
        return isVerified;
    }

    public int getListingId() {
        return this.listingId;
    }

    public void setListingId(int listingId) {
        this.listingId = listingId;
    }

    public String getListingDate() {
        return this.listingDate;
    }

    public void setListingDate(String listingDate) {
        this.listingDate = listingDate;
    }

    public int getListingPoints() {
        return this.listingPoints;
    }

    public void setListingPoints(int listingPoints) {
        this.listingPoints = listingPoints;
    }

    public String getListingIsFeatured() {
        return this.listingIsFeatured;
    }

    public void setListingIsFeatured(String listingIsFeatured) {
        this.listingIsFeatured = listingIsFeatured;
    }

    public String getListingTradeCategory() {
        return this.listingTradeCategory;
    }

    public void setListingTradeCategory(String listingTradeCategory) {
        this.listingTradeCategory = listingTradeCategory;
    }

    public String getListingTradeVerified() {
        return this.listingTradeVerified;
    }

    public void setListingTradeVerified(String listingTradeVerified) {
        this.listingTradeVerified = listingTradeVerified;
    }

    public String getListingTradeSummary() {
        return this.listingTradeSummary;
    }

    public void setListingTradeSummary(String listingTradeSummary) {
        this.listingTradeSummary = listingTradeSummary;
    }

    public String getListingTradeDate() {
        return this.listingTradeDate;
    }

    public void setListingTradeDate(String listingTradeDate) {
        this.listingTradeDate = listingTradeDate;
    }

    public int getListingAdminId() {
        return this.listingAdminId;
    }

    public void setListingAdminId(int listingAdminId) {
        this.listingAdminId = listingAdminId;
    }

    public int getListingVendorId() {
        return this.listingVendorId;
    }

    public void setListingVendorId(int listingVendorId) {
        this.listingVendorId = listingVendorId;
    }

    public void setListingReferralCode(String listingReferralCode) {
        this.listingReferralCode = listingReferralCode;
    }

    public String getListingReferralCode() {
        return this.listingReferralCode;
    }
}
