package com.directory.abe.FeedbackFeature.Presentation;

import android.view.View;

public interface IFeedbackView {
    String getEmail();

    String getQ1();

    String getQ2();

    String getQ3();

    String getQ4();

    String getQ4a();

    String getQ5();

    String getQ6();

    String getQ7();

    String getQ8();

    String getQ9();

    void howImpressed(View view);

    void howOften(View view);

    void howWasSearch(View view);

    void isBusiness(View view);

    void logRegEasy(View view);

    void logRegSuccess(View view);

    void onFeedbackFailure(String str);

    void onFeedbackSuccess(String str);

    void progessDialogMsg(String str);

    void sendLongMsgToUser(String str);

    void setErrorText(String str);

    void setQ9(String str);

    void setTesterEmail(String str);

    void testerIsBasedIn(View view);

    void testerShopsIn(View view);

    void whichFeatures(View view);
}
