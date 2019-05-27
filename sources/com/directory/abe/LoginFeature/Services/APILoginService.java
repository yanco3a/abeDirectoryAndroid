package com.directory.abe.LoginFeature.Services;

import com.directory.abe.APIServiceGeneratorMainServer;
import com.directory.abe.GSONModels.EntryModel;
import com.directory.abe.LoginFeature.UseCases.ILoginUseCase;
import java.io.IOError;
import java.io.IOException;
import org.apache.http.HttpStatus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class APILoginService implements Callback<EntryModel> {
    private static final String TAG = APILoginService.class.getSimpleName();
    Call<EntryModel> call;
    private IAdminLoginAPIService iAdminService;
    private final ILoginUseCase iLoginUseCase;
    private IUserLoginAPIService iUserService;

    public interface IAdminLoginAPIService {
        @GET("api/a/{a}/p/{p}")
        Call<EntryModel> attemptALogin(@Path("a") String str, @Path("p") String str2);
    }

    public interface IUserLoginAPIService {
        @GET("api/u/{u}/p/{p}")
        Call<EntryModel> attemptULogin(@Path("u") String str, @Path("p") String str2);
    }

    public APILoginService(ILoginUseCase iLoginUseCase) {
        this.iLoginUseCase = iLoginUseCase;
    }

    public void attemptUserLoginResult(String u, String p) {
        try {
            this.iUserService = (IUserLoginAPIService) APIServiceGeneratorMainServer.createService(IUserLoginAPIService.class);
            this.call = this.iUserService.attemptULogin(u, p);
            Call<EntryModel> clonedCall = this.call.clone();
            if (clonedCall.isExecuted()) {
                clonedCall.cancel();
            } else {
                clonedCall.enqueue(this);
            }
        } catch (IOError is) {
            is.printStackTrace();
            is.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void attemptAdminLoginResult(String a, String p) {
        try {
            this.iAdminService = (IAdminLoginAPIService) APIServiceGeneratorMainServer.createService(IAdminLoginAPIService.class);
            this.call = this.iAdminService.attemptALogin(a, p);
            Call<EntryModel> clonedCall = this.call.clone();
            if (clonedCall.isExecuted()) {
                clonedCall.cancel();
            } else {
                clonedCall.enqueue(this);
            }
        } catch (IOError is) {
            is.printStackTrace();
            is.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void onResponse(Response<EntryModel> response) {
        String statusMsg = "";
        String statusError = "";
        if (response.isSuccess()) {
            int statusCode = response.code();
            if (statusCode < HttpStatus.SC_OK || statusCode > HttpStatus.SC_MULTIPLE_CHOICES) {
                try {
                    statusMsg = response.message();
                    statusError = response.errorBody().string();
                    this.iLoginUseCase.onLoginFailure("abe isn't feeling so good right now");
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                    e.getMessage();
                    return;
                } catch (NullPointerException ne) {
                    ne.printStackTrace();
                    ne.getMessage();
                    return;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    e2.getMessage();
                    return;
                }
            } else if (((EntryModel) response.body()).getMessage() != null) {
                this.iLoginUseCase.onLoginFailure("abe isn't feeling so good right now");
                return;
            } else if (((EntryModel) response.body()).getUserId() == 0 && ((EntryModel) response.body()).getAdminId() == 0) {
                this.iLoginUseCase.onLoginFailure("abe isn't feeling so good right now");
                return;
            } else {
                this.iLoginUseCase.onLoginSuccess((EntryModel) response.body());
                return;
            }
        }
        try {
            this.iLoginUseCase.onLoginFailure("abe isn't feeling so good right now");
        } catch (Exception e22) {
            e22.printStackTrace();
            e22.getMessage();
            this.iLoginUseCase.onLoginFailure("abe isn't feeling so good right now");
        }
    }

    public void onFailure(Throwable t) {
        try {
            t.toString();
            this.iLoginUseCase.onLoginFailure("abe isn't feeling so good right now");
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            this.iLoginUseCase.onLoginFailure("abe isn't feeling so good right now");
        }
    }
}
