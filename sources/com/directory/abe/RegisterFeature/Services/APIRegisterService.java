package com.directory.abe.RegisterFeature.Services;

import com.directory.abe.APIServiceGeneratorMainServer;
import com.directory.abe.GSONModels.EntryModel;
import com.directory.abe.RegisterFeature.UseCases.IRegisterUseCase;
import java.io.IOError;
import java.io.IOException;
import org.apache.http.HttpStatus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class APIRegisterService implements Callback<EntryModel> {
    private static final String TAG = APIRegisterService.class.getSimpleName();
    Call<EntryModel> call;
    Call<Boolean> call1;
    private IAdminCheckAPIService iAdminCheckAPIService;
    private IAdminRegisterAPIService iAdminPostService;
    private final IRegisterUseCase iRegisterUseCase;
    private IUserCheckAPIService iUserCheckAPIService;
    private IUserRegisterAPIService iUserPostService;

    public interface IAdminCheckAPIService {
        @GET("api/check/a/{a}")
        Call<EntryModel> searchAdminResult(@Path("a") String str);
    }

    public interface IAdminRegisterAPIService {
        @FormUrlEncoded
        @POST("api/insert/a/")
        Call<EntryModel> attemptARegister(@Field("adminUsername") String str, @Field("adminPassword") String str2);
    }

    public interface IUserCheckAPIService {
        @GET("api/check/u/{u}")
        Call<EntryModel> searchUserResult(@Path("u") String str);
    }

    public interface IUserRegisterAPIService {
        @FormUrlEncoded
        @POST("api/insert/u/")
        Call<EntryModel> attemptURegister(@Field("username") String str, @Field("userEmail") String str2, @Field("userPassword") String str3);
    }

    public APIRegisterService(IRegisterUseCase iRegisterUseCase) {
        this.iRegisterUseCase = iRegisterUseCase;
    }

    public Boolean doesUserExist(String e) {
        boolean doesExist = false;
        try {
            this.iUserCheckAPIService = (IUserCheckAPIService) APIServiceGeneratorMainServer.createService(IUserCheckAPIService.class);
            this.call = this.iUserCheckAPIService.searchUserResult(e);
            Call<EntryModel> clonedCall = this.call.clone();
            if (clonedCall.isExecuted()) {
                clonedCall.cancel();
            } else {
                EntryModel model = (EntryModel) clonedCall.execute().body();
                if (model == null) {
                    doesExist = false;
                } else if (model.getUserEmail().equals(e)) {
                    doesExist = true;
                } else {
                    doesExist = false;
                }
            }
            return Boolean.valueOf(doesExist);
        } catch (IllegalStateException is) {
            is.printStackTrace();
            is.getMessage();
            Boolean.valueOf(false);
            return Boolean.valueOf(false);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            ioe.getMessage();
            Boolean.valueOf(false);
            return Boolean.valueOf(false);
        } catch (Exception ne) {
            ne.printStackTrace();
            ne.getMessage();
            Boolean.valueOf(false);
            return Boolean.valueOf(false);
        } catch (Throwable th) {
            return Boolean.valueOf(false);
        }
    }

    public Boolean doesAdminExist(String a) {
        boolean doesExist = false;
        try {
            this.iAdminCheckAPIService = (IAdminCheckAPIService) APIServiceGeneratorMainServer.createService(IAdminCheckAPIService.class);
            this.call = this.iAdminCheckAPIService.searchAdminResult(a);
            Call<EntryModel> clonedCall = this.call.clone();
            if (clonedCall.isExecuted()) {
                clonedCall.cancel();
            } else {
                EntryModel model = (EntryModel) clonedCall.execute().body();
                if (model == null) {
                    doesExist = false;
                } else if (model.getAdminUsername().equals(a)) {
                    doesExist = true;
                } else {
                    doesExist = false;
                }
            }
            return Boolean.valueOf(doesExist);
        } catch (IllegalStateException is) {
            is.printStackTrace();
            is.getMessage();
            Boolean.valueOf(false);
            return Boolean.valueOf(false);
        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
            Boolean.valueOf(false);
            return Boolean.valueOf(false);
        } catch (Exception ne) {
            ne.printStackTrace();
            ne.getMessage();
            Boolean.valueOf(false);
            return Boolean.valueOf(false);
        } catch (Throwable th) {
            return Boolean.valueOf(false);
        }
    }

    public void attemptUserRegisterResult(String u, String e, String p) {
        try {
            this.iUserPostService = (IUserRegisterAPIService) APIServiceGeneratorMainServer.createService(IUserRegisterAPIService.class);
            this.call = this.iUserPostService.attemptURegister(u, e, p);
            Call<EntryModel> clonedCall = this.call.clone();
            if (clonedCall.isExecuted()) {
                clonedCall.cancel();
            } else {
                clonedCall.enqueue(this);
            }
        } catch (IOError is) {
            is.printStackTrace();
            is.getMessage();
        }
    }

    public void attemptAdminRegisterResult(String a, String p) {
        try {
            this.iAdminPostService = (IAdminRegisterAPIService) APIServiceGeneratorMainServer.createService(IAdminRegisterAPIService.class);
            this.call = this.iAdminPostService.attemptARegister(a, p);
            Call<EntryModel> clonedCall = this.call.clone();
            if (clonedCall.isExecuted()) {
                clonedCall.cancel();
            } else {
                clonedCall.enqueue(this);
            }
        } catch (IOError is) {
            is.printStackTrace();
            is.getMessage();
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
                    this.iRegisterUseCase.onRegisterFailure("abe isn't feeling so good right now");
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                    e.getMessage();
                    this.iRegisterUseCase.onRegisterFailure("abe isn't feeling so good right now");
                    return;
                } catch (NullPointerException ne) {
                    ne.printStackTrace();
                    ne.getMessage();
                    this.iRegisterUseCase.onRegisterFailure("abe isn't feeling so good right now");
                    return;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    e2.getMessage();
                    this.iRegisterUseCase.onRegisterFailure("abe isn't feeling so good right now");
                    return;
                }
            } else if (((EntryModel) response.body()).getMessage() == null) {
                this.iRegisterUseCase.onRegisterSuccess((EntryModel) response.body());
                return;
            } else {
                this.iRegisterUseCase.onRegisterFailure("abe isn't feeling so good right now ");
                return;
            }
        }
        try {
            this.iRegisterUseCase.onRegisterFailure("abe isn't feeling so good right now");
        } catch (Exception e22) {
            e22.printStackTrace();
            e22.getMessage();
            this.iRegisterUseCase.onRegisterFailure("abe isn't feeling so good right now");
        }
    }

    public void onFailure(Throwable t) {
        try {
            t.toString();
            this.iRegisterUseCase.onRegisterFailure("abe isn't feeling so good right now");
        } catch (Exception e) {
            this.iRegisterUseCase.onRegisterFailure("abe isn't feeling so good right now");
        }
    }
}
