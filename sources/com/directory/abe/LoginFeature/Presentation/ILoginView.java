package com.directory.abe.LoginFeature.Presentation;

import android.widget.EditText;

public interface ILoginView {
    void checkUserTypeFromRegistration();

    void goToRegistration(String str);

    void loginAttempt(int i);

    void onLoginAsuccess(String str, int i, String str2);

    void onLoginFailure(String str);

    void onLoginUsuccess(String str, int i, String str2, String str3);

    void progressDialogMsg(String str);

    void setErrorText(String str);

    void switchToAdmin();

    void toastLMsg(String str);

    void toastSMsg(String str);

    boolean validate(EditText editText, EditText editText2);
}
