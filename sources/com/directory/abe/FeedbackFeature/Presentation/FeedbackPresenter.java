package com.directory.abe.FeedbackFeature.Presentation;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Patterns;
import com.directory.abe.AnalyticsApp;
import com.directory.abe.FeedbackFeature.UseCases.FeedbackUseCase;
import com.directory.abe.FeedbackFeature.UseCases.IFeedbackPresenter;
import com.directory.abe.IAnalyse;
import com.directory.abe.SearchEntryFeature.Services.ICheckConnections;
import com.google.android.gms.analytics.HitBuilders.EventBuilder;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.gms.analytics.Tracker;

public class FeedbackPresenter implements IFeedbackPresenter, ICheckConnections, IAnalyse {
    private static final String TAG = FeedbackView.class.getSimpleName();
    private Tracker appTracker;
    private FeedbackUseCase feedbackUseCase = new FeedbackUseCase(this);
    private IFeedbackView iFeedbackView;

    public FeedbackPresenter(IFeedbackView iFeedbackView) {
        this.iFeedbackView = iFeedbackView;
    }

    public void attemptFeedback(String s, String f) {
        try {
            if (this.iFeedbackView == null) {
                return;
            }
            if (validateForm(s, f)) {
                String q1 = this.iFeedbackView.getQ1();
                String q2 = this.iFeedbackView.getQ2();
                String q3 = this.iFeedbackView.getQ3();
                String q4 = this.iFeedbackView.getQ4();
                String q4a = this.iFeedbackView.getQ4a();
                String q5 = this.iFeedbackView.getQ5();
                String q6 = this.iFeedbackView.getQ6();
                String q7 = this.iFeedbackView.getQ7();
                String q8 = this.iFeedbackView.getQ8();
                String q9 = this.iFeedbackView.getQ9();
                String email = this.iFeedbackView.getEmail();
                this.iFeedbackView.progessDialogMsg("submitting feedback...");
                this.feedbackUseCase.attemptFeedback(q1, q2, q3, q4, q4a, q5, q6, q7, q8, q9, email);
                return;
            }
            sendLongMsgToUser("please complete the required fields");
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            try {
                this.iFeedbackView.onFeedbackFailure("abe isn't feeling so good right now");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public void onFeedbackSuccess(String s) {
        if (this.iFeedbackView == null) {
            return;
        }
        if (s != null) {
            this.iFeedbackView.onFeedbackSuccess(s);
        } else {
            this.iFeedbackView.onFeedbackFailure("abe isn't feeling so good right now");
        }
    }

    public void onFeedbackFailure(String msg) {
        this.iFeedbackView.onFeedbackFailure(msg);
    }

    public boolean validateForm(String e, String f) {
        if (this.iFeedbackView == null) {
            return false;
        }
        validateTesterEmail(e);
        checkFeedback(f);
        return validateQuestions();
    }

    public boolean validateQuestions() {
        try {
            if (this.iFeedbackView.getQ1() == null || this.iFeedbackView.getQ4() == null || this.iFeedbackView.getQ5().equals("") || this.iFeedbackView.getQ6() == null || this.iFeedbackView.getQ7() == null || this.iFeedbackView.getQ8() == null || this.iFeedbackView.getQ9() == null || this.iFeedbackView.getEmail() == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void checkFeedback(String f) {
        try {
            if (this.iFeedbackView == null) {
                return;
            }
            if (f == null) {
                this.iFeedbackView.setQ9(null);
            } else if (isFeedbackValid(f)) {
                this.iFeedbackView.setQ9(f.trim());
            } else {
                this.iFeedbackView.setQ9(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public boolean isFeedbackValid(String feedback) {
        if (feedback != null) {
            try {
                if (feedback.length() > 1 && feedback.matches("^[a-zA-Z0-9\\n \\u2026/.,:;&()-/'+!?]+$") && feedback.length() <= 1000) {
                    return true;
                }
                this.iFeedbackView.setQ9(null);
                this.iFeedbackView.setErrorText("Note: only the following symbols allowed. (.,:;&()-/'+!? ...)");
                return false;
            } catch (NullPointerException ne) {
                ne.printStackTrace();
                ne.getMessage();
                this.iFeedbackView.setQ9(null);
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
                this.iFeedbackView.setQ9(null);
                return false;
            }
        }
        this.iFeedbackView.setQ9(null);
        return false;
    }

    public void validateTesterEmail(String e) {
        if (e == null) {
            this.iFeedbackView.setErrorText("please provide a valid email for us to contact you");
            this.iFeedbackView.setTesterEmail(null);
        } else if (TextUtils.isEmpty(e) || !Patterns.EMAIL_ADDRESS.matcher(e).matches()) {
            this.iFeedbackView.setErrorText("please provide a valid email for us to contact you");
            this.iFeedbackView.setTesterEmail(null);
        } else {
            this.iFeedbackView.setTesterEmail(e);
        }
    }

    public void sendLongMsgToUser(String msg) {
        if (this.iFeedbackView != null) {
            this.iFeedbackView.sendLongMsgToUser(msg);
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
