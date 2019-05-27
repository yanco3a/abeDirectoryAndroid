package com.directory.abe.FeaturedEntryFeature.Presentation;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.directory.abe.AnalyticsApp;
import com.directory.abe.FeaturedEntryFeature.UseCases.IMainPresenter;
import com.directory.abe.FeaturedEntryFeature.UseCases.MainUseCase;
import com.directory.abe.GSONModels.EntryModel;
import com.directory.abe.IAnalyse;
import com.directory.abe.SearchEntryFeature.Services.ICheckConnections;
import com.google.android.gms.analytics.HitBuilders.EventBuilder;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.gms.analytics.Tracker;
import java.util.ArrayList;

public class MainViewPresenter implements IMainPresenter, ICheckConnections, IAnalyse {
    private static final String TAG = MainViewPresenter.class.getSimpleName();
    private Tracker appTracker;
    private EntryModel entryModel;
    private final IMainView iMainView;
    private String keyword;
    private final MainUseCase mainUseCase = new MainUseCase(this);

    public MainViewPresenter(IMainView iMainView) {
        this.iMainView = iMainView;
    }

    public void getFeaturedListing() {
        if (this.mainUseCase != null) {
            this.mainUseCase.getFeaturedListing();
        }
    }

    public void onFeaturedListingLoadedSuccess(ArrayList<EntryModel> featuredEntryModel) {
        if (this.iMainView == null) {
            return;
        }
        if (featuredEntryModel == null || featuredEntryModel.size() == 0) {
            setMainViewTitle("Featured Business");
            this.iMainView.loadDefaultEntry();
            return;
        }
        EntryModel featuredEntry = (EntryModel) featuredEntryModel.get(0);
        if (featuredEntry != null) {
            setMainViewTitle("This Week's Featured Business");
            featuredEntry.setRatingValue(getRatingValue(featuredEntryModel));
            this.iMainView.loadFeaturedEntry(featuredEntry);
            return;
        }
        setMainViewTitle("Featured Business");
        this.iMainView.loadDefaultEntry();
    }

    public float getRatingValue(ArrayList<EntryModel> featuredEntryModel) {
        float sum = 0.0f;
        int ratingCount = 0;
        try {
            float value;
            if (featuredEntryModel.get(0) == null) {
                value = 0.0f;
            } else if (((EntryModel) featuredEntryModel.get(0)).getListingId() != 0) {
                int id = ((EntryModel) featuredEntryModel.get(0)).getListingId();
                for (int i = 0; i < featuredEntryModel.size(); i++) {
                    if (id == ((EntryModel) featuredEntryModel.get(i)).getListingId()) {
                        sum += ((EntryModel) featuredEntryModel.get(i)).getRatingValue();
                        ratingCount++;
                    }
                }
                if (sum == 0.0f || ratingCount == 0) {
                    value = 0.0f;
                } else {
                    value = sum / ((float) ratingCount);
                }
            } else {
                value = 0.0f;
            }
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0f;
        } catch (Throwable th) {
            return 0.0f;
        }
    }

    public void setMainViewTitle(String title) {
        this.iMainView.setMainViewTitle(title);
    }

    public void onFeaturedListingLoadedFailure() {
        if (this.iMainView != null) {
            this.iMainView.loadDefaultEntry();
        }
    }

    public void searchByKeywordMode(String userSearch) {
        if (isKeywordValid(userSearch)) {
            this.iMainView.goToSearch(userSearch);
        } else {
            sendMessagesToUser("abe doesn't understand '" + userSearch + "'");
        }
    }

    public void sendMessagesToUser(String s) {
        this.iMainView.showLUIMessages(s);
    }

    private boolean isKeywordValid(String userSearch) {
        if (userSearch == null) {
            return false;
        }
        try {
            if (userSearch.length() <= 2 || !userSearch.matches("^[a-zA-Z0-9\\s .,]*$")) {
                return false;
            }
            this.keyword = TextUtils.join("|", userSearch.trim().split(" "));
            return true;
        } catch (Exception ne) {
            ne.printStackTrace();
            ne.getMessage();
            return false;
        }
    }

    public Boolean isOnline(Context context) {
        if (context != null) {
            try {
                NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
                    if (networkInfo != null) {
                        return Boolean.valueOf(false);
                    }
                    return Boolean.valueOf(false);
                } else if (networkInfo.isConnected()) {
                    return Boolean.valueOf(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
                return Boolean.valueOf(false);
            }
        }
        return Boolean.valueOf(false);
    }

    public boolean getPermissions(Context context, Activity activity) {
        return true;
    }

    public void analyseScreen(String action, String label, String category) {
        try {
            this.appTracker.send(new EventBuilder().setAction(action).setLabel(label).setCategory(category).setValue(1).build());
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void resumeTracker(String tag) {
        this.appTracker.setScreenName("Screen~" + tag);
        this.appTracker.send(new ScreenViewBuilder().build());
    }

    public void setUpAnalytics(Activity activity) {
        try {
            this.appTracker = ((AnalyticsApp) activity.getApplication()).getDefaultTracker();
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void loadDefaultEntry() {
        this.iMainView.loadDefaultEntry();
    }
}
