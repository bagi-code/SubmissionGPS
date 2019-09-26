package com.bagicode.submissiongps.tutorial1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bagicode.submissiongps.MainActivity;
import com.bagicode.submissiongps.R;
import com.bagicode.submissiongps.services.PushGpsJobService;

public class Tutorial1MainActivity extends AppCompatActivity {

    Button btnShowLocation;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // GPSTracker class
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial1_main);

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
                // execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnShowLocation = (Button) findViewById(R.id.button);

        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // create class object
                gps = new GPSTracker(Tutorial1MainActivity.this);

                // check if GPS enabled
                if(gps.canGetLocation()){

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                            + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

                    Log.d("tamvanbangetcui", "Running");

                    //==============================
                    PersistableBundle bundle = new PersistableBundle();
                    bundle.putString("fld_btid", "1");
                    bundle.putString("fld_driverid", "1");
                    bundle.putDouble("lat", gps.getLatitude());
                    bundle.putDouble("lon", gps.getLongitude());

                    int jobId = 10;

                    ComponentName mServiceComponent = new ComponentName(Tutorial1MainActivity.this, PushGpsJobService.class);
                    JobInfo.Builder builder = new JobInfo.Builder(jobId, mServiceComponent);
                    builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
                    builder.setRequiresDeviceIdle(false);
                    builder.setRequiresCharging(false);
                    builder.setExtras(bundle);

                    // 1000 ms = 1 detik
                    builder.setPeriodic(3000);
                    JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
                    jobScheduler.schedule(builder.build());
                    //===============================
                } else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

            }
        });

    }
}
