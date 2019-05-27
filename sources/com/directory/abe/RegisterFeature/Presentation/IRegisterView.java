package com.directory.abe.RegisterFeature.Presentation;

import android.widget.EditText;

public interface IRegisterView {
    void checkIfUserSelectedTypeFromLogin();

    void onRegisterAsuccess(String str, int i, String str2);

    void onRegisterFailure(String str);

    void onRegisterUsuccess(String str, int i, String str2, String str3);

    void progessDialogMsg(String str);

    void registerAttempt(int i);

    void setErrorText(String str);

    void switchToAdmin();

    void toastLMsg(String str);

    void toastSMsg(String str);

    boolean validate(int i, EditText editText, EditText editText2, EditText editText3);
}
