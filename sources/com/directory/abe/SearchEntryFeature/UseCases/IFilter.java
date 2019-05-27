package com.directory.abe.SearchEntryFeature.UseCases;

import com.directory.abe.GSONModels.EntryModel;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;

public interface IFilter {
    double compareUserAndVendorLatLng(LatLng latLng, LatLng latLng2);

    ArrayList<EntryModel> filterByRadius(double d, ArrayList<EntryModel> arrayList);

    ArrayList<EntryModel> filterByRating(double d, ArrayList<EntryModel> arrayList);

    ArrayList<LatLng> getAddressesWithLatLngs();

    void setAddressesWithLatLngs(ArrayList<LatLng> arrayList);

    void setFilteredAddressesWithLatLngs(ArrayList<LatLng> arrayList);
}
