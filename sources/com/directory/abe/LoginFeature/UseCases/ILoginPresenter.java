package com.directory.abe.LoginFeature.UseCases;

import com.directory.abe.GSONModels.EntryModel;

public interface ILoginPresenter {
    void onLoginFailure(String str);

    void onLoginSuccess(EntryModel entryModel);

    void sendLongMsgToUser(String str);

    void setErrorText(String str);

    String setLoginType(int i);
}
