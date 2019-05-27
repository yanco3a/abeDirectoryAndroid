package com.directory.abe.SQLiteDatabase.SQLiteModel;

import android.provider.BaseColumns;

public final class FRContract {

    public static abstract class FeedAdmin implements BaseColumns {
        public static final String COLUMN_NAME_ADMIN_ID = "admin_id";
        public static final String COLUMN_NAME_ADMIN_PASSWORD = "adminPassword";
        public static final String COLUMN_NAME_ADMIN_USERNAME = "adminUsername";
        public static final String COLUMN_NAME_ADMIN_VENDOR_ID = "adminVendorId";
        public static final String TABLE_NAME = "Admin";
    }

    public static abstract class FeedListing implements BaseColumns {
        public static final String COLUMN_NAME_LISTING_ADMIN_ID = "listingAdminId";
        public static final String COLUMN_NAME_LISTING_DATE = "listingDate";
        public static final String COLUMN_NAME_LISTING_ID = "listing_id";
        public static final String COLUMN_NAME_LISTING_IS_FEATURED = "listingIsFeatured";
        public static final String COLUMN_NAME_LISTING_POINTS = "listingPoints";
        public static final String COLUMN_NAME_LISTING_TRADE_CATEGORY = "listingTradeCategory";
        public static final String COLUMN_NAME_LISTING_TRADE_DATE = "listingTradeDate";
        public static final String COLUMN_NAME_LISTING_TRADE_REFERRAL_CODE = "listingReferralCode";
        public static final String COLUMN_NAME_LISTING_TRADE_SUMMARY = "listingTradeSummary";
        public static final String COLUMN_NAME_LISTING_TRADE_VERIFIED = "listingTradeVerified";
        public static final String COLUMN_NAME_LISTING_VENDOR_ID = "listingVendorId";
        public static final String TABLE_NAME = "Listing";
    }

    public static abstract class FeedRating implements BaseColumns {
        public static final String COLUMN_NAME_RATING_ID = "rate_id";
        public static final String COLUMN_NAME_RATING_LISTING_ID = "ratingListingId";
        public static final String COLUMN_NAME_RATING_REVIEW = "ratingReview";
        public static final String COLUMN_NAME_RATING_USER_ID = "ratingUserId";
        public static final String COLUMN_NAME_RATING_VALUE = "ratingValue";
        public static final String TABLE_NAME = "Rating";
    }

    public static abstract class FeedStat implements BaseColumns {
        public static final String COLUMN_NAME_STAT_ID = "stat_id";
        public static final String COLUMN_NAME_STAT_TIME_END = "statTimeEnd";
        public static final String COLUMN_NAME_STAT_TIME_START = "statTimeStart";
        public static final String COLUMN_NAME_STAT_TRADE_ID = "statTrade_id";
        public static final String TABLE_NAME = "Stat";
    }

    public static abstract class FeedUser implements BaseColumns {
        public static final String COLUMN_NAME_USER_EMAIL = "userEmail";
        public static final String COLUMN_NAME_USER_ID = "user_id";
        public static final String COLUMN_NAME_USER_LAT = "userLat";
        public static final String COLUMN_NAME_USER_LNG = "userLng";
        public static final String COLUMN_NAME_USER_PASSWORD = "userPassword";
        public static final String COLUMN_NAME_USER_SESSION_ID = "userSessionId";
        public static final String COLUMN_NAME_USER_USERNAME = "username";
        public static final String TABLE_NAME = "User";
    }

    public static abstract class FeedVendor implements BaseColumns {
        public static final String COLUMN_NAME_VENDOR_ADDRESS1 = "vendorAddress1";
        public static final String COLUMN_NAME_VENDOR_ADDRESS2 = "vendorAddress2";
        public static final String COLUMN_NAME_VENDOR_ADDRESS3 = "vendorAddress3";
        public static final String COLUMN_NAME_VENDOR_EMAIL = "vendorEmail";
        public static final String COLUMN_NAME_VENDOR_ID = "vendor_id";
        public static final String COLUMN_NAME_VENDOR_LAT = "vendorLat";
        public static final String COLUMN_NAME_VENDOR_LNG = "vendorLng";
        public static final String COLUMN_NAME_VENDOR_NAME = "vendorName";
        public static final String COLUMN_NAME_VENDOR_POSTCODE = "vendorPostcode";
        public static final String COLUMN_NAME_VENDOR_TELEPHONE = "vendorTelephone";
        public static final String COLUMN_NAME_VENDOR_TYPE = "vendorType";
        public static final String COLUMN_NAME_VENDOR_WEBSITE = "vendorWebsite";
        public static final String TABLE_NAME = "Vendor";
    }
}
