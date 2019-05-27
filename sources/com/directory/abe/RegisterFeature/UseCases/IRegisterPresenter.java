package com.directory.abe.RegisterFeature.UseCases;

import com.directory.abe.GSONModels.EntryModel;

public interface IRegisterPresenter {
    String getRegistrationType(int i);

    void onRegisterFailure(String str);

    void onRegisterSuccess(EntryModel entryModel);

    void sendLongMsgToUser(String str);

    void setErrorText(String str);
}
