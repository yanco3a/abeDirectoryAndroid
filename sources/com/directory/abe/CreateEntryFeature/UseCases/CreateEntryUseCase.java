package com.directory.abe.CreateEntryFeature.UseCases;

import com.directory.abe.CreateEntryFeature.Services.APICreateServices;
import com.directory.abe.Developer;
import com.directory.abe.Emailer.GMailSender;
import com.directory.abe.GSONModels.CompanyHouseData;
import com.directory.abe.GSONModels.CompanyHouseData.Items;
import com.directory.abe.GSONModels.EntryModel;
import java.util.List;

public class CreateEntryUseCase implements ICreateEntryUseCase {
    private static final String TAG = CreateEntryUseCase.class.getSimpleName();
    private APICreateServices apiCreateServices = new APICreateServices(this);
    private EntryModel model;
    private ICreateEntryPresenter presenter;

    public CreateEntryUseCase(ICreateEntryPresenter presenter) {
        this.presenter = presenter;
    }

    public void createEntryAndAttemptAutoVerify(final String c, final EntryModel model) {
        if (c.equals("-1")) {
            model.setListingTradeVerified("False");
            createEntry(model);
            return;
        }
        new Thread(new Runnable() {
            public void run() {
                try {
                    CompanyHouseData companyHouseDatas = CreateEntryUseCase.this.apiCreateServices.getCompanyHouseResult(c);
                    if (companyHouseDatas.items == null) {
                        model.setListingTradeVerified("False");
                        CreateEntryUseCase.this.createEntry(model);
                    } else if (companyHouseDatas.items.size() > 0) {
                        List<Items> companyHouseItems = companyHouseDatas.items;
                        if (companyHouseItems.get(0) == null) {
                            model.setListingTradeVerified("False");
                            CreateEntryUseCase.this.createEntry(model);
                        } else if (((Items) companyHouseItems.get(0)).company_status != null) {
                            model.setListingTradeVerified("True");
                            CreateEntryUseCase.this.createEntry(model);
                        } else {
                            model.setListingTradeVerified("False");
                            CreateEntryUseCase.this.createEntry(model);
                        }
                    } else {
                        model.setListingTradeVerified("False");
                        CreateEntryUseCase.this.createEntry(model);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    e.getMessage();
                    model.setListingTradeVerified("False");
                    CreateEntryUseCase.this.createEntry(model);
                }
            }
        }).start();
    }

    public void createEntry(EntryModel entryModel) {
        this.model = entryModel;
        if (usesReferralCode(this.model)) {
            this.apiCreateServices.postReferralEntry(this.model);
        } else {
            this.apiCreateServices.postEntry(this.model);
        }
    }

    public boolean usesReferralCode(EntryModel model) {
        if (model.getListingReferralCode() == null || model.getListingReferralCode().equals("")) {
            return false;
        }
        return true;
    }

    public void postlisting() {
        createEntry(this.model);
    }

    public void sendErrorFromApi(String errMsg) {
        errMsg = "Abe isn't feeling so good right now";
        if (errMsg == null) {
            onCreateFailure(errMsg);
        } else {
            onCreateFailure(errMsg);
        }
    }

    public void onCreateSuccess(final EntryModel model) {
        if (model != null) {
            try {
                new Thread(new Runnable() {
                    public void run() {
                        CreateEntryUseCase.this.emailReferralCode(model);
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
                return;
            }
        }
        this.presenter.onCreateSuccess(model);
    }

    private void emailReferralCode(EntryModel model) {
        String email = model.getAdminUsername();
        String vendorName = model.getVendorName();
        String referralCode = model.getListingReferralCode();
        try {
            GMailSender sender = new GMailSender(Developer.ADMIN_EMAIL, Developer.ADMIN_PASSWORD);
            if (email != null && vendorName != null && referralCode != null && !sender.sendMail("Abe directory: New listing active", "Thank you for registering " + vendorName + " on Abe Directory. Your listing is now active! \n" + "\n" + "Unique referral code:\n" + referralCode + "\n" + "\n" + "Tell your friends and family to include this referral code when registering their Afro-caribbean business with us. " + "Each week the listing with the most referrals will be featured in our premium advertising space " + "and potentially seen by thousands of our app users!\n " + "\n " + "Please keep this referral code safe. If you have any questions you can email us with your " + "referral code and we will be happy to help.\n" + "\n" + "\n" + "See you on Abe,\n" + "The Abe Directory Team\n\n", Developer.ADMIN_EMAIL, email)) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCreateFailure(String msg) {
        this.presenter.onCreateFailure(msg);
    }
}
