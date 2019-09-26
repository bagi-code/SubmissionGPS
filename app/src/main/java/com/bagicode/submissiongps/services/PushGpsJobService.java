package com.bagicode.submissiongps.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

//import com.dunex.tmsmobile.api.ApiService;
//import com.dunex.tmsmobile.api.ApiUtils;
import com.bagicode.submissiongps.tutorial1.GPSTracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
//import com.google.gson.Gson;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;

//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;

public class PushGpsJobService extends JobService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

//    public static final String TAG = PushGpsJobService.class.getSimpleName();
    public static final String TAG = "tamvanbangetcui";
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private Double dLat=0.01, dlong=0.01;

    GPSTracker gps;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob() Executed");

        gps = new GPSTracker(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

        mLocationRequest = LocationRequest.create();
        // Use high accuracy
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);

        pushGpsJob(params);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStopJob() Executed");
        return true;
    }

    private void pushGpsJob(final JobParameters job) {
        Log.d(TAG, "Running");

//        dLat = job.getExtras().getDouble("lat");
//        dlong = job.getExtras().getDouble("lon");

//        dLat = 0.01;
//        dlong = 0.01;

        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();

        Log.v("tamvanbangetcui","1 push gps lat "+dLat+" and long "+dlong);
        Log.v("tamvanbangetcui","2 push gps lat "+latitude+" and long "+longitude);

//        ApiService mAPIService = ApiUtils.getAPIService();
//        mAPIService.postOrderGpsSubmit(
//                job.getExtras().getString("fld_btid"),
//                job.getExtras().getString("fld_driverid"),
//                dLat,
//                dlong)
//                .enqueue(new Callback<JsonElement>() {
//                    @Override
//                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
//
//                        try {
//                            JsonObject jsonObject = new Gson().fromJson(response.body().getAsJsonObject(), JsonObject.class);
//
//                            jobFinished(job, false);
//
//
//                        } catch (Exception e) {
//                            jobFinished(job, true);
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<JsonElement> call, Throwable t) {
//                        jobFinished(job, true);
//                    }
//                });
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.v("tamvanbangetcui","on location lat "+location.getLatitude()+" and long "+location.getLongitude());

        dLat = location.getLatitude();
        dlong = location.getLongitude();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
