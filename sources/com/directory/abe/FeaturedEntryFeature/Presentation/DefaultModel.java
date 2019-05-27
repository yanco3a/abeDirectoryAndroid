package com.directory.abe.FeaturedEntryFeature.Presentation;

public class DefaultModel {
    private String headerTitle;
    private String inLineText1;
    private String inLineText2;
    private String oneLineCapitalisedText;
    private String oneLineText1;
    private String oneLineText2;
    private String paraText;

    public String getHeaderTitle() {
        return this.headerTitle;
    }

    public String getOneLineText1() {
        return this.oneLineText1;
    }

    public String getOneLineCapitalisedText() {
        return this.oneLineCapitalisedText;
    }

    public String getInLineText1() {
        return this.inLineText1;
    }

    public String getInLineText2() {
        return this.inLineText2;
    }

    public String getOneLineText2() {
        return this.oneLineText2;
    }

    public String getParaText() {
        return this.paraText;
    }

    public void setValues(String s, String s1, String s2, String s3, String s4, String s5, String s6) {
        this.headerTitle = s;
        this.oneLineText1 = s1;
        this.oneLineCapitalisedText = s2;
        this.inLineText1 = s3;
        this.inLineText2 = s4;
        this.oneLineText2 = s5;
        this.paraText = s6;
    }
}
