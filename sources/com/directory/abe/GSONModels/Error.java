package com.directory.abe.GSONModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Error {
    @SerializedName("e")
    @Expose
    /* renamed from: e */
    public String f16e;
    public String message;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getE() {
        return this.f16e;
    }

    public void setE(String e) {
        this.f16e = e;
    }
}
