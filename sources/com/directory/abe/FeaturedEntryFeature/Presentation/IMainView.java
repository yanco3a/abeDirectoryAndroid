package com.directory.abe.FeaturedEntryFeature.Presentation;

import android.widget.TextView;
import com.directory.abe.GSONModels.EntryModel;

public interface IMainView {
    void bridgeTextViewObjs();

    void displayLicenses();

    void getFeaturedText(EntryModel entryModel);

    void goToSearch(String str);

    void loadDefaultEntry();

    void loadFeaturedEntry(EntryModel entryModel);

    void setFeaturedText();

    void setMainViewTitle(String str);

    void showLUIMessages(String str);

    void showOrHideData(TextView textView, String str);
}
