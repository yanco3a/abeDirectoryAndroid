package com.directory.abe.SearchEntryFeature.Presentation;

import android.widget.AdapterView;

public interface ISearchEntryView {
    void checkIfUserCanRefresh();

    void checkIfUserHasSearched();

    String getCurrentTab();

    boolean isMapOn();

    void resetCatSpinnerDefault();

    void setMapViewType(Boolean bool);

    String setUserDefinedCategory(AdapterView<?> adapterView, int i);

    void setUserDefinedRadius(AdapterView<?> adapterView, int i);

    void showLUIMessages(String str);

    void showSUIMessages(String str);
}
