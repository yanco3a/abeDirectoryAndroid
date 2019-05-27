package com.directory.abe.RegisterFeature.UseCases;

import com.directory.abe.Developer;
import com.directory.abe.Emailer.GMailSender;
import com.directory.abe.GSONModels.EntryModel;
import com.directory.abe.RegisterFeature.Services.APIRegisterService;
import java.util.NoSuchElementException;

public class RegisterUseCase implements IRegisterUseCase {
    private static final String TAG = RegisterUseCase.class.getSimpleName();
    private APIRegisterService apiRegisterService = new APIRegisterService(this);
    private IRegisterPresenter iRegisterPresenter;

    public RegisterUseCase(IRegisterPresenter iRegisterPresenter) {
        this.iRegisterPresenter = iRegisterPresenter;
    }

    public boolean validator(String u, String p) {
        if (u == null || p == null) {
            this.iRegisterPresenter.sendLongMsgToUser("registration email address & password required");
            this.iRegisterPresenter.setErrorText("registration email address & password required");
            return false;
        } else if (u.length() <= 6) {
            this.iRegisterPresenter.sendLongMsgToUser("valid email address required");
            this.iRegisterPresenter.setErrorText("valid email address required");
            return false;
        } else if (p.length() < 6) {
            this.iRegisterPresenter.sendLongMsgToUser("password must be 6 characters or more");
            this.iRegisterPresenter.setErrorText("password must be 6 characters or more");
            return false;
        } else if (u.contains("@")) {
            return true;
        } else {
            this.iRegisterPresenter.sendLongMsgToUser("valid email address required");
            this.iRegisterPresenter.setErrorText("valid email address required");
            return false;
        }
    }

    public boolean attemptRegister(String u, String e, String p, String type) {
        if (!validator(e, p)) {
            return false;
        }
        if (type.equals("user")) {
            checkUserExists(u, e, p);
        } else {
            checkAdminExists(e, p);
        }
        return true;
    }

    public void checkAdminExists(final String e, final String p) {
        new Thread(new Runnable(null) {
            public void run() {
                try {
                    if (RegisterUseCase.this.apiRegisterService.doesAdminExist(e).booleanValue()) {
                        RegisterUseCase.this.iRegisterPresenter.onRegisterFailure("this email is already registered, did you want to log in?");
                    } else if (RegisterUseCase.this.registrationEmail(null, e, p)) {
                        RegisterUseCase.this.apiRegisterService.attemptAdminRegisterResult(e, p);
                    } else {
                        RegisterUseCase.this.iRegisterPresenter.onRegisterFailure("abe could not verify " + e);
                    }
                } catch (NoSuchElementException no) {
                    RegisterUseCase.this.iRegisterPresenter.onRegisterFailure("abe isn't feeling so good right now");
                    no.printStackTrace();
                    no.getMessage();
                } catch (NullPointerException ne) {
                    RegisterUseCase.this.iRegisterPresenter.onRegisterFailure("abe isn't feeling so good right now");
                    ne.printStackTrace();
                    ne.getMessage();
                } catch (Exception e) {
                    RegisterUseCase.this.iRegisterPresenter.onRegisterFailure("abe isn't feeling so good right now");
                    e.printStackTrace();
                    e.getMessage();
                }
            }
        }).start();
    }

    public void checkUserExists(final String u, final String e, final String p) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    if (RegisterUseCase.this.apiRegisterService.doesUserExist(e).booleanValue()) {
                        RegisterUseCase.this.iRegisterPresenter.onRegisterFailure("this email is already registered, did you want to log in?");
                    } else if (RegisterUseCase.this.registrationEmail(u, e, p)) {
                        RegisterUseCase.this.apiRegisterService.attemptUserRegisterResult(u, e, p);
                    } else {
                        RegisterUseCase.this.iRegisterPresenter.onRegisterFailure("abe could not verify this email address " + e);
                    }
                } catch (NoSuchElementException no) {
                    RegisterUseCase.this.iRegisterPresenter.onRegisterFailure("abe isn't feeling so good right now");
                    no.printStackTrace();
                    no.getMessage();
                } catch (NullPointerException ne) {
                    RegisterUseCase.this.iRegisterPresenter.onRegisterFailure("abe isn't feeling so good right now");
                    ne.printStackTrace();
                    ne.getMessage();
                } catch (Exception e) {
                    RegisterUseCase.this.iRegisterPresenter.onRegisterFailure("abe isn't feeling so good right now");
                    e.printStackTrace();
                    e.getMessage();
                }
            }
        }).start();
    }

    private boolean registrationEmail(String username, String email, String passwd) {
        try {
            String msg;
            GMailSender sender = new GMailSender(Developer.ADMIN_EMAIL, Developer.ADMIN_PASSWORD);
            if (username == null) {
                msg = "Hello From Abe, \n\nWelcome to Abe Directory. Your registration is now complete.\nPlease log in to use Abe \n\nemail: " + email + "\n" + "password: " + passwd + "\n" + "\n" + "Once logged in you can create a listing for your business(es). If you have any questions you can email us and we will be happy to help\n" + "\n" + "\n" + "See you on Abe,\n\n" + "The Abe Directory Team\n";
            } else {
                msg = "Hello From Abe, \n\nWelcome to Abe Directory. Your registration is now complete.\nPlease log in to use additional features on Abe \n\nusername: " + username + "\n" + "email: " + email + "\n" + "password: " + passwd + "\n" + "\n" + "Once logged in you will able to rate & review listings. If you have any questions you can email us and we will be happy to help\n" + "\n" + "\n" + "See you on Abe,\n\n" + "The Abe Directory Team\n";
            }
            if (sender.sendMail("Abe directory: Registration", msg, Developer.ADMIN_EMAIL, email)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void onRegisterSuccess(EntryModel body) {
        this.iRegisterPresenter.onRegisterSuccess(body);
    }

    public void onRegisterFailure(String msg) {
        this.iRegisterPresenter.onRegisterFailure(msg);
    }
}
