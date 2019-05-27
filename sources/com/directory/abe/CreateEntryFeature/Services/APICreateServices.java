package com.directory.abe.CreateEntryFeature.Services;

import com.directory.abe.APIServiceGeneratorMainServer;
import com.directory.abe.CreateEntryFeature.UseCases.ICreateEntryUseCase;
import com.directory.abe.GSONModels.CompanyHouseData;
import com.directory.abe.GSONModels.EntryModel;
import org.apache.http.HttpStatus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class APICreateServices {
    private static final String COMPANY_HOUSE_PSWD = "";
    private static final String COMPANY_HOUSE_USERNAME = "xIszMCLdGRsOeZDBqusdH9qzNJHCfjSXCloJ953r";
    private static final String TAG = APICreateServices.class.getSimpleName();
    private ICreateEntryUseCase iCreateEntryUseCase;

    /* renamed from: com.directory.abe.CreateEntryFeature.Services.APICreateServices$1 */
    class C02401 implements Callback<EntryModel> {
        C02401() {
        }

        public void onResponse(Response<EntryModel> response) {
            String statusMsg = "";
            String statusError = "";
            try {
                if (response.isSuccess()) {
                    int statusCode = response.code();
                    if (statusCode < HttpStatus.SC_OK || statusCode > HttpStatus.SC_MULTIPLE_CHOICES) {
                        APICreateServices.this.iCreateEntryUseCase.onCreateFailure("Abe isn't feeling so good right now");
                        return;
                    } else if (((EntryModel) response.body()).getVendorName() == null || ((EntryModel) response.body()).getVendorName().equals("null")) {
                        APICreateServices.this.iCreateEntryUseCase.onCreateFailure("Abe couldn't handle your request");
                        return;
                    } else {
                        APICreateServices.this.iCreateEntryUseCase.onCreateSuccess((EntryModel) response.body());
                        return;
                    }
                }
                APICreateServices.this.iCreateEntryUseCase.onCreateFailure("Abe isn't feeling so good right now");
            } catch (Exception ne) {
                ne.printStackTrace();
                ne.getMessage();
                APICreateServices.this.iCreateEntryUseCase.onCreateFailure("Abe isn't feeling so good right now");
            }
        }

        public void onFailure(Throwable t) {
            t.toString();
            APICreateServices.this.iCreateEntryUseCase.onCreateFailure("Abe isn't feeling so good right now");
        }
    }

    /* renamed from: com.directory.abe.CreateEntryFeature.Services.APICreateServices$2 */
    class C02412 implements Callback<EntryModel> {
        C02412() {
        }

        public void onResponse(Response<EntryModel> response) {
            String statusMsg = "";
            String statusError = "";
            try {
                if (response.isSuccess()) {
                    int statusCode = response.code();
                    if (statusCode < HttpStatus.SC_OK || statusCode > HttpStatus.SC_MULTIPLE_CHOICES) {
                        APICreateServices.this.iCreateEntryUseCase.onCreateFailure("");
                        return;
                    } else {
                        APICreateServices.this.iCreateEntryUseCase.onCreateSuccess((EntryModel) response.body());
                        return;
                    }
                }
                APICreateServices.this.iCreateEntryUseCase.onCreateFailure("");
            } catch (NullPointerException ne) {
                ne.printStackTrace();
                ne.getMessage();
            }
        }

        public void onFailure(Throwable t) {
            t.toString();
            APICreateServices.this.iCreateEntryUseCase.onCreateFailure("");
        }
    }

    public interface CompanyHouseServiceInterface {
        @GET("search/companies")
        Call<CompanyHouseData> searchCompanyHouseResult(@Query("q") String str);
    }

    public interface PostEntryAPIServiceInterface {
        @FormUrlEncoded
        @POST("api/insert/l/")
        Call<EntryModel> postEntry(@Field("adminId") int i, @Field("vendorName") String str, @Field("vendorAddress1") String str2, @Field("vendorAddress2") String str3, @Field("vendorAddress3") String str4, @Field("vendorPostcode") String str5, @Field("vendorTelephone") String str6, @Field("vendorEmail") String str7, @Field("vendorWebsite") String str8, @Field("vendorType") String str9, @Field("vendorLat") double d, @Field("vendorLng") double d2, @Field("listingTradeVerified") String str10, @Field("listingReferralCode") String str11, @Field("listingTradeCategory") String str12, @Field("listingTradeSummary") String str13, @Field("listingTradeDate") String str14);
    }

    public interface ReferralEntryAPIServiceInterface {
        @FormUrlEncoded
        @POST("api/insert/f/")
        Call<EntryModel> postReferralEntry(@Field("adminId") int i, @Field("vendorName") String str, @Field("vendorAddress1") String str2, @Field("vendorAddress2") String str3, @Field("vendorAddress3") String str4, @Field("vendorPostcode") String str5, @Field("vendorTelephone") String str6, @Field("vendorEmail") String str7, @Field("vendorWebsite") String str8, @Field("vendorType") String str9, @Field("vendorLat") double d, @Field("vendorLng") double d2, @Field("listingTradeVerified") String str10, @Field("listingReferralCode") String str11, @Field("listingTradeCategory") String str12, @Field("listingTradeSummary") String str13, @Field("listingTradeDate") String str14);
    }

    public APICreateServices(ICreateEntryUseCase iCreateEntryUseCase) {
        this.iCreateEntryUseCase = iCreateEntryUseCase;
    }

    public CompanyHouseData getCompanyHouseResult(String companyHouseNo) {
        CompanyHouseData companyHouseResponse = null;
        try {
            Call<CompanyHouseData> clonedCall = ((CompanyHouseServiceInterface) APIServiceGeneratorCompanyHouse.createService(CompanyHouseServiceInterface.class, COMPANY_HOUSE_USERNAME, "")).searchCompanyHouseResult(companyHouseNo).clone();
            if (clonedCall.isExecuted()) {
                clonedCall.cancel();
            } else {
                companyHouseResponse = (CompanyHouseData) clonedCall.execute().body();
            }
            return companyHouseResponse;
        } catch (IllegalStateException is) {
            is.printStackTrace();
            is.getMessage();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            return null;
        } catch (Throwable th) {
            return null;
        }
    }

    public void postEntry(EntryModel model) {
        ((PostEntryAPIServiceInterface) APIServiceGeneratorMainServer.createService(PostEntryAPIServiceInterface.class)).postEntry(model.getAdminId(), model.getVendorName(), model.getVendorAddress1(), model.getVendorAddress2(), model.getVendorAddress3(), model.getVendorPostcode(), model.getVendorTelephone(), model.getVendorEmail(), model.getVendorWebsite(), model.getVendorType(), model.getVendorLat(), model.getVendorLng(), model.getListingTradeVerified(), model.getListingReferralCode(), model.getListingTradeCategory(), model.getListingTradeSummary(), model.getListingTradeDate()).enqueue(new C02401());
    }

    public void postReferralEntry(EntryModel fModel) {
        ((ReferralEntryAPIServiceInterface) APIServiceGeneratorMainServer.createService(ReferralEntryAPIServiceInterface.class)).postReferralEntry(fModel.getAdminId(), fModel.getVendorName(), fModel.getVendorAddress1(), fModel.getVendorAddress2(), fModel.getVendorAddress3(), fModel.getVendorPostcode(), fModel.getVendorTelephone(), fModel.getVendorEmail(), fModel.getVendorWebsite(), fModel.getVendorType(), fModel.getVendorLat(), fModel.getVendorLng(), fModel.getListingTradeVerified(), fModel.getListingReferralCode(), fModel.getListingTradeCategory(), fModel.getListingTradeSummary(), fModel.getListingTradeDate()).enqueue(new C02412());
    }
}
