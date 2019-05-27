package com.directory.abe.SearchEntryFeature.Services;

import android.app.Activity;
import android.content.Context;

public interface ICheckConnections {
    boolean getPermissions(Context context, Activity activity);

    Boolean isOnline(Context context);
}
