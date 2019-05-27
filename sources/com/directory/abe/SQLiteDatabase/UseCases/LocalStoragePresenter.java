package com.directory.abe.SQLiteDatabase.UseCases;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.directory.abe.GSONModels.EntryModel;
import com.directory.abe.ISQLite;
import com.directory.abe.Models.Admin;
import com.directory.abe.Models.Listing;
import com.directory.abe.Models.Rating;
import com.directory.abe.Models.User;
import com.directory.abe.Models.Vendor;
import com.directory.abe.SQLiteDatabase.Presentation.ILocalStorageView;
import com.directory.abe.SQLiteDatabase.Presentation.LocalStorageView;
import com.directory.abe.SQLiteDatabase.Services.SQLiteHelper;
import com.directory.abe.SearchEntryFeature.Services.ICheckConnections;
import java.util.ArrayList;

public class LocalStoragePresenter implements ILocalStoragePresenter, ICheckConnections, ISQLite {
    private static final String TAG = LocalStoragePresenter.class.getSimpleName();
    private LocalStorageView activity;
    private Context context;
    private ILocalStorageView iLocalStorageView;
    private LocalStorageUseCase localStorageUseCase = new LocalStorageUseCase(this);
    private SQLiteHelper sqLiteHelper;

    /* renamed from: com.directory.abe.SQLiteDatabase.UseCases.LocalStoragePresenter$2 */
    class C02532 implements Runnable {
        C02532() {
        }

        public void run() {
            if (LocalStoragePresenter.this.iLocalStorageView != null) {
                LocalStoragePresenter.this.iLocalStorageView.gotoMain();
            }
        }
    }

    public LocalStoragePresenter(ILocalStorageView iLocalStorageView, Context context, Activity activity) {
        this.iLocalStorageView = iLocalStorageView;
        this.activity = (LocalStorageView) activity;
        this.context = context;
    }

    public void getServerData() {
        this.localStorageUseCase.getServerData();
    }

    public void onLoadAllSuccess(final ArrayList<EntryModel> body) {
        if (this.activity != null) {
            new Thread(new Runnable() {

                /* renamed from: com.directory.abe.SQLiteDatabase.UseCases.LocalStoragePresenter$1$1 */
                class C02501 implements Runnable {
                    C02501() {
                    }

                    public void run() {
                        LocalStoragePresenter.this.iLocalStorageView.onSQLiteSuccess("SQLite success");
                    }
                }

                /* renamed from: com.directory.abe.SQLiteDatabase.UseCases.LocalStoragePresenter$1$2 */
                class C02512 implements Runnable {
                    C02512() {
                    }

                    public void run() {
                        LocalStoragePresenter.this.iLocalStorageView.onSQLiteFailure("SQLite failure");
                    }
                }

                public void run() {
                    try {
                        ArrayList<Vendor> vendors = new ArrayList();
                        ArrayList<Admin> admins = new ArrayList();
                        ArrayList<Listing> listings = new ArrayList();
                        ArrayList<User> users = new ArrayList();
                        ArrayList<Rating> ratings = new ArrayList();
                        if (body == null) {
                            LocalStoragePresenter.this.gotoMainOnUIthread();
                        } else if (LocalStoragePresenter.this.sqLiteHelper == null) {
                            LocalStoragePresenter.this.gotoMainOnUIthread();
                        } else if (((long) body.size()) == LocalStoragePresenter.this.sqLiteHelper.getListingCount()) {
                            LocalStoragePresenter.this.gotoMainOnUIthread();
                        } else if (LocalStoragePresenter.this.recreateTbls()) {
                            for (int i = 0; i < body.size(); i++) {
                                vendors.add(new Vendor(((EntryModel) body.get(i)).getVendorId(), ((EntryModel) body.get(i)).getVendorName(), ((EntryModel) body.get(i)).getVendorAddress1(), ((EntryModel) body.get(i)).getVendorAddress2(), ((EntryModel) body.get(i)).getVendorAddress3(), ((EntryModel) body.get(i)).getVendorPostcode(), ((EntryModel) body.get(i)).getVendorTelephone(), ((EntryModel) body.get(i)).getVendorEmail(), ((EntryModel) body.get(i)).getVendorWebsite(), ((EntryModel) body.get(i)).getVendorType(), ((EntryModel) body.get(i)).getVendorLat(), ((EntryModel) body.get(i)).getVendorLng()));
                                admins.add(new Admin(((EntryModel) body.get(i)).getAdminId(), ((EntryModel) body.get(i)).getAdminUsername(), ((EntryModel) body.get(i)).getAdminPassword()));
                                listings.add(new Listing(((EntryModel) body.get(i)).getListingId(), ((EntryModel) body.get(i)).getListingAdminId(), ((EntryModel) body.get(i)).getListingVendorId(), ((EntryModel) body.get(i)).getListingDate(), ((EntryModel) body.get(i)).getListingPoints(), ((EntryModel) body.get(i)).getListingIsFeatured(), ((EntryModel) body.get(i)).getListingReferralCode(), ((EntryModel) body.get(i)).getListingTradeCategory(), ((EntryModel) body.get(i)).getListingTradeVerified(), ((EntryModel) body.get(i)).getListingTradeSummary(), ((EntryModel) body.get(i)).getListingTradeDate()));
                                users.add(new User(((EntryModel) body.get(i)).getUserId(), ((EntryModel) body.get(i)).getUserSessionId(), ((EntryModel) body.get(i)).getUsername()));
                                ratings.add(new Rating(((EntryModel) body.get(i)).getRatingId(), ((EntryModel) body.get(i)).getRatingListingId(), ((EntryModel) body.get(i)).getRatingUserId(), ((EntryModel) body.get(i)).getRatingValue(), ((EntryModel) body.get(i)).getRatingReview()));
                            }
                            if (vendors != null && admins != null && listings != null && ratings != null && users != null) {
                                if (LocalStoragePresenter.this.sqLiteHelper.insertAll(vendors, admins, listings, users, ratings)) {
                                    LocalStoragePresenter.this.activity.runOnUiThread(new C02501());
                                } else {
                                    LocalStoragePresenter.this.activity.runOnUiThread(new C02512());
                                }
                            }
                        }
                    } catch (Exception e) {
                        LocalStoragePresenter.this.gotoMainOnUIthread();
                    }
                }
            }).start();
        }
    }

    public boolean recreateTbls() {
        return this.sqLiteHelper.recreateTbls();
    }

    public void onLoadAllFailure() {
        this.iLocalStorageView.onSQLiteFailure("failed");
    }

    public void setupDBHelper(Context c) {
        this.sqLiteHelper = new SQLiteHelper(c);
    }

    public SQLiteHelper getSQLiteHelper() {
        return this.sqLiteHelper;
    }

    public void sendLongMsgtoUser(String s) {
        if (this.iLocalStorageView != null) {
            this.iLocalStorageView.toastLMsg(s);
        }
    }

    public void showProgessDialogMsg() {
        this.iLocalStorageView.progessDialogMsg("Loading...");
    }

    public void gotoMainOnUIthread() {
        if (this.activity != null) {
            this.activity.runOnUiThread(new C02532());
        }
    }

    public Boolean isOnline(Context context) {
        if (context != null) {
            try {
                NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
                    if (networkInfo != null) {
                        return Boolean.valueOf(false);
                    }
                    return Boolean.valueOf(false);
                } else if (networkInfo.isConnected()) {
                    return Boolean.valueOf(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
                return Boolean.valueOf(false);
            }
        }
        return Boolean.valueOf(false);
    }

    public boolean getPermissions(Context context, Activity activity) {
        return true;
    }
}
