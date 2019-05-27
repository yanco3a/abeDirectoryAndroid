package com.directory.abe.Models;

public class Feedback {
    private String basedIn;
    private int feedbackId;
    private String howEasyLogReg;
    private String howEasySearch;
    private String howImpressed;
    private String howOften;
    private String isBusinessUser;
    private String shopsMostIn;
    private String successfulLogReg;
    private String suggestions;
    private String testerEmail;
    private String whichFeatures;

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
}
