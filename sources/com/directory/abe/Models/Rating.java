package com.directory.abe.Models;

import java.io.Serializable;

public class Rating implements Serializable {
    private float ratingCount;
    private int ratingId;
    private int ratingListingId;
    private String ratingReview;
    private int ratingUserId;
    private float ratingValue;
    private float totalRatingValue;

    public Rating(int ratingId, int ratingListingId, int ratingUserId, float ratingValue, String ratingReview) {
        this.ratingId = ratingId;
        this.ratingListingId = ratingListingId;
        this.ratingUserId = ratingUserId;
        this.ratingValue = ratingValue;
        this.ratingReview = ratingReview;
    }

    public Rating(int ratingId, int ratingListingId, int ratingUserId, float ratingValue, String ratingReview, float totalRatingValue) {
        this.ratingId = ratingId;
        this.ratingListingId = ratingListingId;
        this.ratingUserId = ratingUserId;
        this.ratingValue = ratingValue;
        this.ratingReview = ratingReview;
        this.totalRatingValue = totalRatingValue;
    }

    public int getRatingListingId() {
        return this.ratingListingId;
    }

    public void setRatingListingId(int ratingListingId) {
        this.ratingListingId = ratingListingId;
    }

    public int getRatingId() {
        return this.ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public int getRatingUserId() {
        return this.ratingUserId;
    }

    public void setRatingUserId(int ratingUserId) {
        this.ratingUserId = ratingUserId;
    }

    public float getRatingValue() {
        return this.ratingValue;
    }

    public void setRatingValue(float ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getRatingReview() {
        return this.ratingReview;
    }

    public void setRatingReview(String ratingReview) {
        this.ratingReview = ratingReview;
    }

    public float getRatingCount() {
        return this.ratingCount;
    }

    public void setRatingCount(float ratingCount) {
        this.ratingCount = ratingCount;
    }

    public float getTotalRatingValue() {
        return this.totalRatingValue;
    }

    public void setTotalRatingValue(float totalRatingValue) {
        this.totalRatingValue = totalRatingValue;
    }
}
