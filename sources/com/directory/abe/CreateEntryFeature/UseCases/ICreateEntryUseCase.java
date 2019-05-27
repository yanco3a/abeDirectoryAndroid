package com.directory.abe.CreateEntryFeature.UseCases;

import com.directory.abe.GSONModels.EntryModel;

public interface ICreateEntryUseCase {
    void onCreateFailure(String str);

    void onCreateSuccess(EntryModel entryModel);

    void postlisting();

    void sendErrorFromApi(String str);
}
