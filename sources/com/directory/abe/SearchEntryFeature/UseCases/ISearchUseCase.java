package com.directory.abe.SearchEntryFeature.UseCases;

import android.location.Location;
import com.directory.abe.GSONModels.EntryModel;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;

public interface ISearchUseCase {
    boolean handleNewLocation(Location location);

    void onGoogleLocationResponse(Location location);

    void onSearchByCatSuccess(ArrayList<EntryModel> arrayList, String str, String str2, String str3, double d);

    void sendErrorFromAPI(String str);

    void sendErrorFromApi(int i, int i2);

    void setAllLatLngs(ArrayList<LatLng> arrayList);

    void setValues(String str, String str2, String str3, double d);
}
