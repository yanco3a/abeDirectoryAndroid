package com.directory.abe.RegisterFeature.Presentation;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.directory.abe.AnalyticsApp;
import com.directory.abe.C0246R;
import com.directory.abe.GSONModels.EntryModel;
import com.directory.abe.IAnalyse;
import com.directory.abe.RegisterFeature.UseCases.IRegisterPresenter;
import com.directory.abe.RegisterFeature.UseCases.RegisterUseCase;
import com.directory.abe.SearchEntryFeature.Services.ICheckConnections;
import com.google.android.gms.analytics.HitBuilders.EventBuilder;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.gms.analytics.Tracker;

public class RegisterPresenter implements IRegisterPresenter, ICheckConnections, IAnalyse {
    private static final String TAG = RegisterPresenter.class.getSimpleName();
    private Tracker appTracker;
    private IRegisterView iRegisterView;
    private String lType;
    private RegisterUseCase registerUseCase = new RegisterUseCase(this);

    public RegisterPresenter(IRegisterView iRegisterView) {
        this.iRegisterView = iRegisterView;
    }

    public void attemptRegister(String u, String e, String p, int id) {
        if (this.registerUseCase.attemptRegister(u, e, p, getRegistrationType(id))) {
            this.iRegisterView.progessDialogMsg("authenticating...");
        }
    }

    public String getRegistrationType(int id) {
        if (id == C0246R.id.btn_u_register) {
            this.lType = "user";
        } else {
            this.lType = "admin";
        }
        return this.lType;
    }

    public void setErrorText(String s) {
        if (this.iRegisterView != null) {
            this.iRegisterView.setErrorText(s);
        }
    }

    public void onRegisterSuccess(EntryModel model) {
        if (model != null && this.iRegisterView != null) {
            if (model.getMessage() != null) {
                this.iRegisterView.onRegisterFailure("abe isn't feeling so good right now, please try again later");
            } else if (this.lType == null) {
            } else {
                if (this.lType.equals("user")) {
                    this.iRegisterView.onRegisterUsuccess("Welcome " + model.getUsername(), model.getUserId(), model.getUsername(), model.getUserEmail());
                } else {
                    this.iRegisterView.onRegisterAsuccess("You are logged in as " + model.getAdminUsername(), model.getAdminId(), model.getAdminUsername());
                }
            }
        }
    }

    public void onRegisterFailure(String msg) {
        this.iRegisterView.onRegisterFailure(msg);
    }

    public void sendLongMsgToUser(String msg) {
        if (this.iRegisterView != null) {
            this.iRegisterView.toastLMsg(msg);
        }
    }

    public Boolean isOnline(Context context) {
        if (context != null) {
            try {
                NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
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
}
