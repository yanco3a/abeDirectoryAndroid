package com.directory.abe.SearchEntryFeature.Services;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender.SendIntentException;
import android.location.Location;
import android.os.Bundle;
import com.directory.abe.SearchEntryFeature.UseCases.ISearchUseCase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import java.io.IOError;
import java.text.DateFormat;
import java.util.Date;

public class APIGoogleServices implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener, ResultCallback<LocationSettingsResult> {
    private static final int REQUEST_ERROR_RESOLUTION = 1;
    private static final String TAG = APIGoogleServices.class.getSimpleName();
    private static final long TEN_SECONDS = 10000;
    private static final long THIRTY_SECONDS = 30000;
    private Activity activity;
    private Context context;
    private int counter = 0;
    private GoogleApiClient googleApiClient;
    private ISearchUseCase iSearchUseCase;
    private boolean isLocationEnabled;
    private String lastUpdateTime;
    private Location location;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;
    private boolean requestingLocationUpdates = false;
    private boolean resolvingError = false;

    public APIGoogleServices(ISearchUseCase iSearchUseCase) {
        this.iSearchUseCase = iSearchUseCase;
        this.isLocationEnabled = false;
    }

    public boolean createGoogleLocationsAPI() {
        try {
            this.googleApiClient = new Builder(this.context).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
            return true;
        } catch (NullPointerException ne) {
            ne.printStackTrace();
            ne.getMessage();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            return false;
        }
    }

    public boolean createLocationRequest() {
        try {
            this.locationRequest = LocationRequest.create().setPriority(100).setInterval(THIRTY_SECONDS).setFastestInterval(THIRTY_SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            return false;
        }
    }

    public boolean createLocationRequestSettings() {
        try {
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
            builder.addLocationRequest(this.locationRequest);
            this.locationSettingsRequest = builder.build();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            return false;
        }
    }

    public boolean connectGoogleAPIClient() {
        try {
            if (this.googleApiClient == null || this.googleApiClient.isConnected()) {
                return false;
            }
            this.googleApiClient.connect();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            return false;
        }
    }

    public boolean waitingForGoogle() {
        try {
            return this.googleApiClient.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            return false;
        }
    }

    public boolean initiateGoogleLocationAndUpdates(Context context, Activity activity) {
        this.activity = activity;
        this.context = context;
        if (createGoogleLocationsAPI() && createLocationRequest() && createLocationRequestSettings()) {
            this.requestingLocationUpdates = true;
            if (this.googleApiClient != null) {
                checkLocationSettings();
                return true;
            }
        }
        return false;
    }

    public void onConnected(Bundle bundle) {
        try {
            if (this.context != null) {
                Location location = LocationServices.FusedLocationApi.getLastLocation(this.googleApiClient);
                if (location == null) {
                    startLocationUpdates();
                }
                this.iSearchUseCase.onGoogleLocationResponse(location);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
            e.getMessage();
            if (this.googleApiClient.isConnected()) {
                this.googleApiClient.disconnect();
            }
            this.iSearchUseCase.sendErrorFromAPI("Abe can't seem to get your location right now. Are they enabled? ");
        } catch (Exception e2) {
            e2.printStackTrace();
            e2.getMessage();
            if (this.googleApiClient.isConnected()) {
                this.googleApiClient.disconnect();
            }
            this.iSearchUseCase.sendErrorFromAPI("Abe can't seem to get your location right now. Are they enabled? ");
        }
    }

    public void onConnectionSuspended(int i) {
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (this.activity == null) {
            this.requestingLocationUpdates = false;
        } else if (!this.resolvingError) {
            if (connectionResult.hasResolution()) {
                try {
                    this.resolvingError = true;
                    connectionResult.startResolutionForResult(this.activity, 1);
                } catch (SendIntentException e) {
                    e.printStackTrace();
                    e.getMessage();
                    this.googleApiClient.connect();
                } catch (IOError io) {
                    io.printStackTrace();
                }
            } else if (connectionResult != null) {
                this.iSearchUseCase.sendErrorFromApi(connectionResult.getErrorCode(), 1);
            }
        }
    }

    public void startLocationUpdates() {
        try {
            if (this.googleApiClient != null && this.locationRequest != null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(this.googleApiClient, this.locationRequest, (LocationListener) this);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void onLocationChanged(Location locationResult) {
        this.isLocationEnabled = true;
        this.location = locationResult;
        this.iSearchUseCase.onGoogleLocationResponse(this.location);
        this.lastUpdateTime = DateFormat.getTimeInstance().format(new Date());
    }

    public void stopLocationUpdates() {
        try {
            if (this.googleApiClient != null && isRequestingLocationUpdates()) {
                LocationServices.FusedLocationApi.removeLocationUpdates(this.googleApiClient, (LocationListener) this);
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            this.requestingLocationUpdates = false;
        }
    }

    public boolean isGoogleAPIClientConnected() {
        if (this.googleApiClient != null) {
            return this.googleApiClient.isConnected();
        }
        return false;
    }

    public void disconnectGoogleApiClient() {
        try {
            stopLocationUpdates();
            if (this.googleApiClient != null) {
                this.googleApiClient.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            this.requestingLocationUpdates = false;
        }
    }

    public void checkLocationSettings() {
        try {
            LocationServices.SettingsApi.checkLocationSettings(this.googleApiClient, this.locationSettingsRequest).setResultCallback(this);
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void onResult(LocationSettingsResult locationSettingsResult) {
        Status status = locationSettingsResult.getStatus();
        LocationSettingsStates states = locationSettingsResult.getLocationSettingsStates();
        switch (status.getStatusCode()) {
            case 0:
                startLocationUpdates();
                this.isLocationEnabled = true;
                return;
            case 6:
                try {
                    status.startResolutionForResult(this.activity, 1);
                    return;
                } catch (SendIntentException e) {
                    e.printStackTrace();
                    e.getMessage();
                    this.iSearchUseCase.sendErrorFromAPI("Abe cant seem to get your location right now...");
                    return;
                }
            default:
                return;
        }
    }

    public boolean isRequestingLocationUpdates() {
        return this.requestingLocationUpdates;
    }

    public void connectGoogleApi() {
        if (this.googleApiClient != null && !this.googleApiClient.isConnected()) {
            this.googleApiClient.connect();
        }
    }
}
