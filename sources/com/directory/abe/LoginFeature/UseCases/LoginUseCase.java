package com.directory.abe.LoginFeature.UseCases;

import com.directory.abe.GSONModels.EntryModel;
import com.directory.abe.LoginFeature.Services.APILoginService;

public class LoginUseCase implements ILoginUseCase {
    private APILoginService apiLoginService = new APILoginService(this);
    private ILoginPresenter iLoginPresenter;

    public LoginUseCase(ILoginPresenter iLoginPresenter) {
        this.iLoginPresenter = iLoginPresenter;
    }

    public boolean validator(String u, String p) {
        if (u == null || p == null) {
            this.iLoginPresenter.sendLongMsgToUser("registration email address & password required");
            this.iLoginPresenter.setErrorText("registration email address & password required");
            return false;
        } else if (u.length() <= 6) {
            this.iLoginPresenter.sendLongMsgToUser("please enter your registration email address");
            this.iLoginPresenter.setErrorText("please enter your registration email address");
            return false;
        } else if (p.length() <= 6) {
            this.iLoginPresenter.sendLongMsgToUser("please enter valid password");
            this.iLoginPresenter.setErrorText("please enter valid password");
            return false;
        } else if (u.contains("@")) {
            return true;
        } else {
            this.iLoginPresenter.sendLongMsgToUser("please enter your registration email address");
            this.iLoginPresenter.setErrorText("please enter your registration email address");
            return false;
        }
    }

    public boolean attemptLogin(String u, String p, String type) {
        if (!validator(u, p)) {
            return false;
        }
        if (type.equals("user")) {
            this.apiLoginService.attemptUserLoginResult(u, p);
        } else {
            this.apiLoginService.attemptAdminLoginResult(u, p);
        }
        return true;
    }

    public void onLoginSuccess(EntryModel body) {
        this.iLoginPresenter.onLoginSuccess(body);
    }

    public void onLoginFailure(String msg) {
        this.iLoginPresenter.onLoginFailure(msg);
    }
}
