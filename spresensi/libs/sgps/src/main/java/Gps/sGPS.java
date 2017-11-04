package Gps;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import static android.content.ContentValues.TAG;

/**
 * Created by mac1 on 8/28/17.
 */


            /*
            compile 'com.google.android.gms:play-services-location:11.0.4'
            compile 'com.google.android.gms:play-services-places:11.0.4'

            <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
            <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
            */


public class sGPS implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Context context;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private LocationListener locationListener;

    public sGPS(Context context) {
        this.context = context;
    }

    public void on(LocationListener locationListener) {

        this.locationListener = locationListener;

        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(3000);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }



        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, locationListener);
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    public void off() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    public void save() {

        on(new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                SfDataLokasi sfDataLokasi = new SfDataLokasi(context);
                sfDataLokasi.save(location.getLatitude(), location.getLongitude());

                off();

            }
        });
    }

    public static void reqHighGps(final Activity activity) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(activity)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(activity, 8);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

    public static double jarak (Location lokasi1 , Location lokasi2, int radius){
        double jarak = lokasi1.distanceTo(lokasi2);
        Log.d("DISTANCE",""+jarak);
        return jarak;
    }

    public static double jarak (double lat1 , double long1, double lat2, double long2, int radius){

        Location lokasi1 = new Location("");
        lokasi1.setLatitude(lat1); lokasi1.setLongitude(long1);

        Location lokasi2 = new Location("");
        lokasi2.setLatitude(lat2); lokasi2.setLongitude(long2);

        double jarak = lokasi1.distanceTo(lokasi2);
        Log.d("DISTANCE",""+jarak);
        return jarak;
    }


    public static boolean isInLocation (Location lokasi1 , Location lokasi2, int radius){
        double jarak = lokasi1.distanceTo(lokasi2);
        Log.d("DISTANCE",""+jarak);
        return jarak <= radius;
    }

    public static boolean isInLocation (double lat1 , double long1, double lat2, double long2, int radius){

        Location lokasi1 = new Location("");
        lokasi1.setLatitude(lat1); lokasi1.setLongitude(long1);

        Location lokasi2 = new Location("");
        lokasi2.setLatitude(lat2); lokasi2.setLongitude(long2);

        double jarak = lokasi1.distanceTo(lokasi2);
        Log.d("DISTANCE",""+jarak);
        return jarak <= radius;
    }




}

