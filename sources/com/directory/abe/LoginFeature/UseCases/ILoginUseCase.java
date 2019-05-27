package com.directory.abe.LoginFeature.UseCases;

import com.directory.abe.GSONModels.EntryModel;

public interface ILoginUseCase {
    void onLoginFailure(String str);

    void onLoginSuccess(EntryModel entryModel);
}
