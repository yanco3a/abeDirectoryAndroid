package com.directory.abe.LoginFeature.Presentation;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.directory.abe.AnalyticsApp;
import com.directory.abe.C0246R;
import com.directory.abe.GSONModels.EntryModel;
import com.directory.abe.IAnalyse;
import com.directory.abe.LoginFeature.UseCases.ILoginPresenter;
import com.directory.abe.LoginFeature.UseCases.LoginUseCase;
import com.directory.abe.SearchEntryFeature.Services.ICheckConnections;
import com.google.android.gms.analytics.HitBuilders.EventBuilder;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.gms.analytics.Tracker;

public class LoginPresenter implements ILoginPresenter, ICheckConnections, IAnalyse {
    private static final String TAG = LoginPresenter.class.getSimpleName();
    private Tracker appTracker;
    private LoginView iLoginView;
    private String lType;
    private LoginUseCase loginUseCase = new LoginUseCase(this);

    public LoginPresenter(LoginView iLoginView) {
        this.iLoginView = iLoginView;
    }

    public void attemptLogin(String u, String p, int id) {
        try {
            if (this.loginUseCase.attemptLogin(u, p, setLoginType(id))) {
                this.iLoginView.progressDialogMsg("authenticating...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public String setLoginType(int id) {
        if (id == C0246R.id.btn_u_login) {
            this.lType = "user";
        } else {
            this.lType = "admin";
        }
        return this.lType;
    }

    public void setErrorText(String s) {
        if (this.iLoginView != null) {
            this.iLoginView.setErrorText(s);
        }
    }

    public void onLoginSuccess(EntryModel model) {
        if (model != null) {
            try {
                if (this.iLoginView != null && this.lType != null) {
                    if (this.lType.equals("user")) {
                        this.iLoginView.onLoginUsuccess("Welcome " + model.getUsername(), model.getUserId(), model.getUsername(), model.getUserEmail());
                    } else {
                        this.iLoginView.onLoginAsuccess("Logged in as " + model.getAdminUsername(), model.getAdminId(), model.getAdminUsername());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
            }
        }
    }

    public void onLoginFailure(String msg) {
        try {
            this.iLoginView.onLoginFailure("login unsuccessful, check details provided are correct");
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void sendLongMsgToUser(String msg) {
        try {
            if (this.iLoginView != null) {
                this.iLoginView.toastLMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public Boolean isOnline(Context context) {
        if (context != null) {
            NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return Boolean.valueOf(true);
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
