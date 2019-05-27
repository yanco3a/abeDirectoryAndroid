package com.directory.abe.Models;

import java.io.Serializable;

public class Vendor implements Serializable {
    private String[] vendorAddress;
    private String vendorAddress1;
    private String vendorAddress2;
    private String vendorAddress3;
    private String vendorEmail;
    private int vendorId;
    private double vendorLat;
    private double vendorLng;
    private String vendorName;
    private String vendorPostcode;
    private String vendorTelephone;
    private String vendorType;
    private String vendorWebsite;

    public Vendor() {
        this.vendorAddress = new String[4];
    }

    public Vendor(int vendorId, String vendorName, String vendorAddress1, String vendorAddress2, String vendorAddress3, String vendorPostcode, String vendorTelephone, String vendorEmail, String vendorWebsite, String vendorType, double vendorLat, double vendorLng) {
        this.vendorAddress = new String[4];
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
    }

    public Vendor(String vendorName, String[] vendorAddress, String vendorType) {
        this.vendorAddress = new String[4];
        this.vendorName = vendorName;
        this.vendorAddress = vendorAddress;
        this.vendorType = vendorType;
    }

    public Vendor(String vendorName, String vendorType) {
        this.vendorAddress = new String[4];
        this.vendorName = vendorName;
        this.vendorType = vendorType;
    }

    public int getVendorId() {
        return this.vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return this.vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorAddress1() {
        return this.vendorAddress1;
    }

    public void setVendorAddress1(String vendorAddress1) {
        this.vendorAddress1 = vendorAddress1;
    }

    public String getVendorAddress2() {
        return this.vendorAddress2;
    }

    public void setVendorAddress2(String vendorAddress2) {
        this.vendorAddress2 = vendorAddress2;
    }

    public String getVendorAddress3() {
        return this.vendorAddress3;
    }

    public void setVendorAddress3(String vendorAddress3) {
        this.vendorAddress3 = vendorAddress3;
    }

    public String getVendorPostcode() {
        return this.vendorPostcode;
    }

    public void setVendorPostcode(String vendorPostcode) {
        this.vendorPostcode = vendorPostcode;
    }

    public String getVendorTelephone() {
        return this.vendorTelephone;
    }

    public void setVendorTelephone(String vendorTelephone) {
        this.vendorTelephone = vendorTelephone;
    }

    public String getVendorEmail() {
        return this.vendorEmail;
    }

    public void setVendorEmail(String vendorEmail) {
        this.vendorEmail = vendorEmail;
    }

    public String getVendorWebsite() {
        return this.vendorWebsite;
    }

    public void setVendorWebsite(String vendorWebsite) {
        this.vendorWebsite = vendorWebsite;
    }

    public String getVendorType() {
        return this.vendorType;
    }

    public void setVendorType(String vendorType) {
        this.vendorType = vendorType;
    }

    public String toString() {
        return this.vendorName;
    }

    public void createVendor() {
    }

    public String checkValue(String attributeName, Object attributeValue) {
        return "error or something";
    }

    public double getVendorLat() {
        return this.vendorLat;
    }

    public double getVendorLng() {
        return this.vendorLng;
    }
}
