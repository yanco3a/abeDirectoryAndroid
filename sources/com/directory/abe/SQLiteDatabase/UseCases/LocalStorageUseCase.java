package com.directory.abe.SQLiteDatabase.UseCases;

import com.directory.abe.GSONModels.EntryModel;
import com.directory.abe.SQLiteDatabase.Services.APILoadDataService;
import java.util.ArrayList;

public class LocalStorageUseCase implements ILocalStorageUseCase {
    private APILoadDataService apiLoadDataService = new APILoadDataService(this);
    private ILocalStoragePresenter presenter;

    public LocalStorageUseCase(ILocalStoragePresenter presenter) {
        this.presenter = presenter;
    }

    public void getServerData() {
        this.apiLoadDataService.loadAll();
    }

    public void onLoadAllSuccess(ArrayList<EntryModel> body) {
        this.presenter.onLoadAllSuccess(body);
    }

    public void sendErrorFromAPI(String string) {
        this.presenter.onLoadAllFailure();
    }
}
