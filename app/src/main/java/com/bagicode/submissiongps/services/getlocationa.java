package com.bagicode.submissiongps.services;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

//import com.dunex.tmsmobile.api.ApiService;
//import com.dunex.tmsmobile.api.UtilsApi;
//import com.dunex.tmsmobile.utils.OrderPreference;
//import com.dunex.tmsmobile.utils.UserPreference;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Timer;
import java.util.TimerTask;

public class getlocationa extends Service {

//    private UserPreference userPreference;
//    private OrderPreference orderPreference;
    private Timer timer;
//    private SharedPrefManager sharedPrefManager;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Set the schedule function

            location();

        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.v("tamvan", "ini destory");

        timer.cancel();
    }

    private void location(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
        @Override
        public void run() {
//        sharedPrefManager = new SharedPrefManager(getBaseContext());
//        final ApiService mAPIService = UtilsApi.getAPIService();
        if (ActivityCompat.checkSelfPermission(getApplication(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplication(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(getApplication());
        mFusedLocation.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
//                    mAPIService.postOrderGpsSubmit(
//                            sharedPrefManager.getSP_btid(),
//                            sharedPrefManager.getSP_fld_driverid(),
//                            location.getLongitude(),
//                            location.getLatitude())
//                            .enqueue(new Callback<ResponseBody>() {
//                                @Override
//                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                                    Log.v("tamvan", "ini sukse");
//                                }
//
//                                @Override
//                                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                                    Log.v("tamvan", "ini failure");
//                                }
//                            });
                    //=========================

//                        @Override
//                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
//                            Toast.makeText(getApplication(),"sukses",Toast.LENGTH_LONG).show();
//                            Log.v("tamvan", "ini sukse");
//                        }
//
//                        @Override
//                        public void onFailure(Call<JsonElement> call, Throwable t) {
//                            Toast.makeText(getApplication(),"sukses tidak",Toast.LENGTH_LONG).show();
//
////                        }
//                    );

                    Log.v("tamvan", "ini jalanin service berapa de");
                    Log.v("tamvanbanget", "ini sukses long : "+location.getLongitude()+" dan lat : "+location.getLatitude());

                }

                Log.v("tamvan", "ini jalan");
            }
        });
        }
        },
    0, 100000);
    }
}
