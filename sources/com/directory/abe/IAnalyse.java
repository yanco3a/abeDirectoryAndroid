package com.directory.abe;

import android.app.Activity;

public interface IAnalyse {
    void analyseScreen(String str, String str2, String str3);

    void resumeTracker(String str);

    void setUpAnalytics(Activity activity);
}
