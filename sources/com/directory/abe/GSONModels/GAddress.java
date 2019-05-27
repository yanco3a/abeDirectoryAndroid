package com.directory.abe.GSONModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class GAddress {
    @SerializedName("results")
    @Expose
    private List<Result> results = new ArrayList();
    @SerializedName("status")
    @Expose
    private String status;

    public List<Result> getResults() {
        return this.results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
