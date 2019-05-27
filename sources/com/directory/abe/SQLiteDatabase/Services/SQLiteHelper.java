package com.directory.abe.SQLiteDatabase.Services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.directory.abe.Models.Admin;
import com.directory.abe.Models.Listing;
import com.directory.abe.Models.Rating;
import com.directory.abe.Models.User;
import com.directory.abe.Models.Vendor;
import com.directory.abe.SQLiteDatabase.SQLiteModel.FRContract.FeedAdmin;
import com.directory.abe.SQLiteDatabase.SQLiteModel.FRContract.FeedListing;
import com.directory.abe.SQLiteDatabase.SQLiteModel.FRContract.FeedRating;
import com.directory.abe.SQLiteDatabase.SQLiteModel.FRContract.FeedUser;
import com.directory.abe.SQLiteDatabase.SQLiteModel.FRContract.FeedVendor;
import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String COMMA_SEP = ", ";
    private static final String CREATE_TBL_ADMIN = "CREATE TABLE Admin (_id INTEGER PRIMARY KEY AUTOINCREMENT, adminUsername TEXT, adminPassword TEXT) ";
    private static final String CREATE_TBL_LISTING = "CREATE TABLE Listing (_id INTEGER PRIMARY KEY AUTOINCREMENT, listingVendorId INTEGER, listingAdminId INTEGER, listingDate TEXT, listingPoints INTEGER, listingIsFeatured TEXT, listingReferralCode TEXT, listingTradeCategory TEXT, listingTradeVerified TEXT, listingTradeSummary TEXT, listingTradeDate TEXT) ";
    private static final String CREATE_TBL_RATING = "CREATE TABLE Rating (_id INTEGER PRIMARY KEY, ratingUserId TEXT, ratingListingId TEXT, ratingReview TEXT, ratingValue TEXT) ";
    private static final String CREATE_TBL_USER = "CREATE TABLE User (_id INTEGER PRIMARY KEY AUTOINCREMENT, userSessionId TEXT, userEmail TEXT, username TEXT, userPassword TEXT, userLng TEXT, userLat TEXT) ";
    private static final String CREATE_TBL_VENDOR = "CREATE TABLE Vendor (_id INTEGER PRIMARY KEY AUTOINCREMENT, vendorName TEXT, vendorAddress1 TEXT, vendorAddress2 TEXT, vendorAddress3 TEXT, vendorPostcode TEXT, vendorTelephone TEXT, vendorEmail TEXT, vendorWebsite TEXT, vendorType TEXT, vendorLat TEXT, vendorLng TEXT) ";
    private static final String CURL_BRACKET_CLOSE = ") ";
    private static final String CURL_BRACKET_OPEN = " (";
    private static final String DATABASE_NAME = "abpn994.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";
    private static final String INT_TYPE = " INTEGER";
    private static final String TAG = SQLiteHelper.class.getSimpleName();
    private static final String TEXT_TYPE = " TEXT";
    private SQLiteHelper dbHelper;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TBL_VENDOR);
        db.execSQL(CREATE_TBL_LISTING);
        db.execSQL(CREATE_TBL_ADMIN);
        db.execSQL(CREATE_TBL_USER);
        db.execSQL(CREATE_TBL_RATING);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Vendor");
        db.execSQL("DROP TABLE IF EXISTS Admin");
        db.execSQL("DROP TABLE IF EXISTS Listing");
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Rating");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean recreateTbls() {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL("DROP TABLE IF EXISTS Vendor");
            db.execSQL("DROP TABLE IF EXISTS Admin");
            db.execSQL("DROP TABLE IF EXISTS Listing");
            db.execSQL("DROP TABLE IF EXISTS User");
            db.execSQL("DROP TABLE IF EXISTS Rating");
            db.execSQL(CREATE_TBL_VENDOR);
            db.execSQL(CREATE_TBL_ADMIN);
            db.execSQL(CREATE_TBL_LISTING);
            db.execSQL(CREATE_TBL_USER);
            db.execSQL(CREATE_TBL_RATING);
            return true;
        } catch (SQLException e) {
            e.getMessage();
            return false;
        } catch (Throwable th) {
            return false;
        }
    }

    public boolean onInsertVendor(String name, String a1, String a2, String a3, String p, String t, String e, String w, String ty, double lat, double lng) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FeedVendor.COLUMN_NAME_VENDOR_NAME, name);
        contentValues.put(FeedVendor.COLUMN_NAME_VENDOR_ADDRESS1, a1);
        contentValues.put(FeedVendor.COLUMN_NAME_VENDOR_ADDRESS2, a2);
        contentValues.put(FeedVendor.COLUMN_NAME_VENDOR_ADDRESS3, a3);
        contentValues.put(FeedVendor.COLUMN_NAME_VENDOR_POSTCODE, p);
        contentValues.put(FeedVendor.COLUMN_NAME_VENDOR_TELEPHONE, t);
        contentValues.put(FeedVendor.COLUMN_NAME_VENDOR_EMAIL, e);
        contentValues.put(FeedVendor.COLUMN_NAME_VENDOR_WEBSITE, w);
        contentValues.put(FeedVendor.COLUMN_NAME_VENDOR_TYPE, ty);
        contentValues.put(FeedVendor.COLUMN_NAME_VENDOR_LAT, Double.valueOf(lat));
        contentValues.put(FeedVendor.COLUMN_NAME_VENDOR_LNG, Double.valueOf(lng));
        if (db.insert(FeedVendor.TABLE_NAME, null, contentValues) == -1) {
            return false;
        }
        return true;
    }

    public boolean onInsertAdmin(int avId, String u, String p) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FeedAdmin.COLUMN_NAME_ADMIN_VENDOR_ID, Integer.valueOf(avId));
        contentValues.put(FeedAdmin.COLUMN_NAME_ADMIN_USERNAME, u);
        contentValues.put(FeedAdmin.COLUMN_NAME_ADMIN_PASSWORD, p);
        if (db.insert(FeedAdmin.TABLE_NAME, null, contentValues) == -1) {
            return false;
        }
        return true;
    }

    public boolean onInsertListing(int lvId, int laId, String ldate, int lpts, String lif, String ltrc, String ltcat, String ltver, String ltsum, String ltdate) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FeedListing.COLUMN_NAME_LISTING_VENDOR_ID, Integer.valueOf(lvId));
        contentValues.put(FeedListing.COLUMN_NAME_LISTING_ADMIN_ID, Integer.valueOf(laId));
        contentValues.put(FeedListing.COLUMN_NAME_LISTING_DATE, ldate);
        contentValues.put(FeedListing.COLUMN_NAME_LISTING_POINTS, Integer.valueOf(lpts));
        contentValues.put(FeedListing.COLUMN_NAME_LISTING_IS_FEATURED, lif);
        contentValues.put(FeedListing.COLUMN_NAME_LISTING_TRADE_REFERRAL_CODE, ltrc);
        contentValues.put(FeedListing.COLUMN_NAME_LISTING_TRADE_CATEGORY, ltcat);
        contentValues.put(FeedListing.COLUMN_NAME_LISTING_TRADE_VERIFIED, ltver);
        contentValues.put(FeedListing.COLUMN_NAME_LISTING_TRADE_SUMMARY, ltsum);
        contentValues.put(FeedListing.COLUMN_NAME_LISTING_TRADE_DATE, ltdate);
        if (db.insert(FeedListing.TABLE_NAME, null, contentValues) == -1) {
            return false;
        }
        return true;
    }

    public boolean onInsertUser(int usId, String ue, String u, String p, String ulng, String ulat) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FeedUser.COLUMN_NAME_USER_SESSION_ID, Integer.valueOf(usId));
        contentValues.put(FeedUser.COLUMN_NAME_USER_EMAIL, ue);
        contentValues.put(FeedUser.COLUMN_NAME_USER_USERNAME, u);
        contentValues.put(FeedUser.COLUMN_NAME_USER_PASSWORD, p);
        contentValues.put(FeedUser.COLUMN_NAME_USER_LAT, ulat);
        contentValues.put(FeedUser.COLUMN_NAME_USER_LNG, ulng);
        if (db.insert(FeedUser.TABLE_NAME, null, contentValues) == -1) {
            return false;
        }
        return true;
    }

    public boolean onInsertRating(int rlId, int ruId, int v, String r) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FeedRating.COLUMN_NAME_RATING_LISTING_ID, Integer.valueOf(rlId));
        contentValues.put(FeedRating.COLUMN_NAME_RATING_USER_ID, Integer.valueOf(ruId));
        contentValues.put(FeedRating.COLUMN_NAME_RATING_VALUE, Integer.valueOf(v));
        contentValues.put(FeedRating.COLUMN_NAME_RATING_REVIEW, r);
        if (db.insert(FeedRating.TABLE_NAME, null, contentValues) == -1) {
            return false;
        }
        return true;
    }

    public Cursor onSelectAllVendors() {
        return getWritableDatabase().rawQuery("SELECT * FROM Vendor", null);
    }

    public boolean insertAll(ArrayList<Vendor> vendors, ArrayList<Admin> admins, ArrayList<Listing> listings, ArrayList<User> users, ArrayList<Rating> ratings) {
        int i;
        boolean isComplete;
        SQLiteDatabase db = getWritableDatabase();
        String vQuery = "INSERT INTO Vendor (_id, vendorName, vendorAddress1, vendorAddress2, vendorAddress3, vendorPostcode, vendorTelephone, vendorEmail, vendorWebsite, vendorType, vendorLat, vendorLng) SELECT " + ((Vendor) vendors.get(0)).getVendorId() + " AS _id, " + "'" + ((Vendor) vendors.get(0)).getVendorName() + "' AS vendorName, " + "'" + ((Vendor) vendors.get(0)).getVendorAddress1() + "' AS vendorAddress1, " + "'" + ((Vendor) vendors.get(0)).getVendorAddress2() + "' AS vendorAddress2, " + "'" + ((Vendor) vendors.get(0)).getVendorAddress3() + "' AS vendorAddress3, " + "'" + ((Vendor) vendors.get(0)).getVendorPostcode() + "' AS vendorPostcode, " + "'" + ((Vendor) vendors.get(0)).getVendorTelephone() + "' AS vendorTelephone, " + "'" + ((Vendor) vendors.get(0)).getVendorEmail() + "' AS vendorEmail, " + "'" + ((Vendor) vendors.get(0)).getVendorWebsite() + "' AS vendorWebsite, " + "'" + ((Vendor) vendors.get(0)).getVendorType() + "' AS vendorType, " + "" + ((Vendor) vendors.get(0)).getVendorLat() + " AS vendorLat, " + "" + ((Vendor) vendors.get(0)).getVendorLng() + " AS vendorLng";
        for (i = 1; i < vendors.size(); i++) {
            vQuery = vQuery + " UNION SELECT " + ((Vendor) vendors.get(i)).getVendorId() + COMMA_SEP + "'" + ((Vendor) vendors.get(i)).getVendorName() + "', " + "'" + ((Vendor) vendors.get(i)).getVendorAddress1() + "', " + "'" + ((Vendor) vendors.get(i)).getVendorAddress2() + "', " + "'" + ((Vendor) vendors.get(i)).getVendorAddress3() + "', " + "'" + ((Vendor) vendors.get(i)).getVendorPostcode() + "', " + "'" + ((Vendor) vendors.get(i)).getVendorTelephone() + "', " + "'" + ((Vendor) vendors.get(i)).getVendorEmail() + "', " + "'" + ((Vendor) vendors.get(i)).getVendorWebsite() + "', " + "'" + ((Vendor) vendors.get(i)).getVendorType() + "', " + "" + ((Vendor) vendors.get(i)).getVendorLat() + COMMA_SEP + "" + ((Vendor) vendors.get(i)).getVendorLng() + "";
        }
        String aQuery = "INSERT INTO Admin (_id, adminUsername, adminPassword) SELECT " + ((Admin) admins.get(0)).getAdminId() + " AS _id, " + "'" + ((Admin) admins.get(0)).getAdminUsername() + "' AS adminUsername, " + "'" + ((Admin) admins.get(0)).getAdminPassword() + "' AS adminPassword";
        for (i = 1; i < admins.size(); i++) {
            aQuery = aQuery + " UNION SELECT " + ((Admin) admins.get(i)).getAdminId() + COMMA_SEP + "'" + ((Admin) admins.get(i)).getAdminUsername() + "', " + "'" + ((Admin) admins.get(i)).getAdminPassword() + "' ";
        }
        String lQuery = "INSERT INTO Listing (_id, listingAdminId, listingVendorId, listingDate, listingPoints, listingIsFeatured, listingReferralCode, listingTradeCategory, listingTradeVerified, listingTradeSummary, listingTradeDate) SELECT " + ((Listing) listings.get(0)).getListingId() + " AS _id, " + "" + ((Listing) listings.get(0)).getListingAdminId() + " AS listingAdminId, " + "" + ((Listing) listings.get(0)).getListingVendorId() + " AS listingVendorId, " + "'" + ((Listing) listings.get(0)).getListingDate() + "' AS listingDate, " + "" + ((Listing) listings.get(0)).getListingPoints() + " AS listingPoints, " + "'" + ((Listing) listings.get(0)).getListingIsFeatured() + "' AS listingIsFeatured, " + "'" + ((Listing) listings.get(0)).getListingReferralCode() + "' AS listingReferralCode, " + "'" + ((Listing) listings.get(0)).getListingTradeCategory() + "' AS listingTradeCategory, " + "'" + ((Listing) listings.get(0)).getListingTradeVerified() + "' AS listingTradeVerified, " + "'" + ((Listing) listings.get(0)).getListingTradeSummary() + "' AS listingTradeSummary, " + "'" + ((Listing) listings.get(0)).getListingTradeDate() + "' AS listingTradeDate";
        for (i = 1; i < listings.size(); i++) {
            lQuery = lQuery + " UNION SELECT " + ((Listing) listings.get(i)).getListingId() + COMMA_SEP + "" + ((Listing) listings.get(i)).getListingAdminId() + COMMA_SEP + "" + ((Listing) listings.get(i)).getListingVendorId() + COMMA_SEP + "'" + ((Listing) listings.get(i)).getListingDate() + "', " + "" + ((Listing) listings.get(i)).getListingPoints() + COMMA_SEP + "'" + ((Listing) listings.get(i)).getListingIsFeatured() + "', " + "'" + ((Listing) listings.get(i)).getListingReferralCode() + "', " + "'" + ((Listing) listings.get(i)).getListingTradeCategory() + "', " + "'" + ((Listing) listings.get(i)).getListingTradeVerified() + "', " + "'" + ((Listing) listings.get(i)).getListingTradeSummary() + "', " + "'" + ((Listing) listings.get(i)).getListingTradeDate() + "'";
        }
        String uQuery = "INSERT INTO User (_id, userSessionId, username) SELECT " + ((User) users.get(0)).getUserId() + " AS _id, " + "'" + ((User) users.get(0)).getUserSessionId() + "' AS userSessionId, " + "'" + ((User) users.get(0)).getUsername() + "' AS username";
        for (i = 1; i < listings.size(); i++) {
            uQuery = uQuery + " UNION SELECT " + ((User) users.get(i)).getUserId() + COMMA_SEP + "'" + ((User) users.get(i)).getUserSessionId() + "', " + "'" + ((User) users.get(i)).getUsername() + "'";
        }
        String rQuery = "INSERT INTO Rating (_id, ratingListingId, ratingUserId, ratingValue, ratingReview) SELECT " + ((Rating) ratings.get(0)).getRatingId() + " AS _id, " + "" + ((Rating) ratings.get(0)).getRatingListingId() + " AS ratingListingId, " + "" + ((Rating) ratings.get(0)).getRatingUserId() + " AS ratingUserId, " + "" + ((Rating) ratings.get(0)).getRatingValue() + " AS ratingValue, " + "'" + ((Rating) ratings.get(0)).getRatingReview() + "' AS ratingReview";
        for (i = 1; i < listings.size(); i++) {
            rQuery = rQuery + " UNION SELECT " + ((Rating) ratings.get(i)).getRatingId() + COMMA_SEP + "" + ((Rating) ratings.get(i)).getRatingListingId() + COMMA_SEP + "" + ((Rating) ratings.get(i)).getRatingUserId() + COMMA_SEP + "" + ((Rating) ratings.get(i)).getRatingValue() + COMMA_SEP + "'" + ((Rating) ratings.get(i)).getRatingReview() + "'";
        }
        try {
            db.beginTransaction();
            db.execSQL(vQuery);
            db.execSQL(aQuery);
            db.execSQL(lQuery);
            db.execSQL(uQuery);
            db.execSQL(rQuery);
            isComplete = true;
        } catch (SQLException sqlEx) {
            isComplete = false;
            sqlEx.printStackTrace();
            sqlEx.getMessage();
        } catch (IllegalStateException ie) {
            isComplete = false;
            ie.printStackTrace();
            ie.getMessage();
        } catch (Exception e) {
            isComplete = false;
            e.printStackTrace();
            e.getMessage();
        } finally {
            db.endTransaction();
            db.close();
        }
        return isComplete;
    }

    public long getListingCount() {
        long result = 0;
        SQLiteDatabase db = getReadableDatabase();
        try {
            result = db.compileStatement("SELECT COUNT(*) FROM Rating").simpleQueryForLong();
            db.close();
            return result;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            sqlEx.getMessage();
            db.close();
            return 0;
        } catch (IllegalStateException ie) {
            ie.printStackTrace();
            ie.getMessage();
            db.close();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            db.close();
            return 0;
        } catch (Throwable th) {
            db.close();
            return result;
        }
    }

    public void selectCatByRating(String category, String filterType, String viewType, float rating) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT vendorName, vendorAddress1, vendorAddress2, vendorAddress3, vendorPostcode, vendorTelephone, vendorEmail, vendorWebsite, vendorType, listingDate, listingTradeCategory, listingTradeVerified, listingTradeSummary, listingTradeDate, userSessionId, username, ratingListingId, ratingUserId, ratingValue, ratingReview FROM listing LEFT JOIN vendor ON listing.listingVendorId = vendor.vendorId LEFT JOIN featured ON listing.listingId = featured.featuredListingId  LEFT JOIN rating ON listing.listingId = rating.ratingListingId WHERE listing.listingTradeCategory = " + category + " " + " AND rating.ratingValue = " + rating + " ", null);
        StringBuffer buffer = new StringBuffer();
        while (c.moveToNext()) {
            String vendorName = c.getString(0);
            String vendorAddress1 = c.getString(0);
            String vendorAddress2 = c.getString(0);
            String vendorAddress3 = c.getString(0);
            String vendorPostcode = c.getString(0);
            String vendorTelephone = c.getString(0);
            String vendorEmail = c.getString(0);
            String vendorWebsite = c.getString(0);
            String listingDate = c.getString(0);
            String listingTradeCategory = c.getString(0);
            String listingTradeVerified = c.getString(0);
            String listingTradeSummary = c.getString(0);
            String listingTradeDate = c.getString(0);
            String userSessionId = c.getString(0);
            String username = c.getString(0);
            int ratingListingId = c.getInt(0);
            int ratingUserId = c.getInt(0);
            float ratingValue = c.getFloat(0);
            buffer.append(vendorName + " " + vendorAddress1 + " " + vendorAddress2 + " " + vendorAddress3 + " " + vendorPostcode + " " + vendorTelephone + " " + vendorEmail + " " + vendorWebsite + " " + listingDate + " " + listingTradeCategory + " " + listingTradeVerified + " " + listingTradeSummary + " " + listingTradeDate + " " + userSessionId + " " + username + " " + ratingListingId + " " + ratingUserId + " " + ratingValue + " " + c.getString(0) + "\n");
        }
        c.close();
        db.close();
    }
}
