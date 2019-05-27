package com.directory.abe.RegisterFeature.UseCases;

import com.directory.abe.GSONModels.EntryModel;

public interface IRegisterUseCase {
    void onRegisterFailure(String str);

    void onRegisterSuccess(EntryModel entryModel);
}
