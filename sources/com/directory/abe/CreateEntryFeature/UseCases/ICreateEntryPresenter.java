package com.directory.abe.CreateEntryFeature.UseCases;

import com.directory.abe.GSONModels.EntryModel;

public interface ICreateEntryPresenter {
    void onCreateFailure(String str);

    void onCreateSuccess(EntryModel entryModel);

    void sendLongMsgToUser(String str);
}
