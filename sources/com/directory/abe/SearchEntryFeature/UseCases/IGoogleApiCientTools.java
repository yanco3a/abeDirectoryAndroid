package com.directory.abe.SearchEntryFeature.UseCases;

import android.app.Activity;
import android.content.Context;

public interface IGoogleApiCientTools {
    void connectGoogleApi();

    void disconnectGoogleApiClient();

    Activity getActivity();

    Context getContext();

    boolean isGoogleAPIClientConnected();

    boolean isLocationEnabled();
}
