package com.bagicode.submissiongps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import com.bagicode.submissiongps.services.PushGpsJobService;
import com.bagicode.submissiongps.services.getlocationa;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPush = findViewById(R.id.btn_push);
        btnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startService(new Intent(getBaseContext(), getlocationa.class));

                PersistableBundle bundle = new PersistableBundle();
                bundle.putString("fld_btid", "1");
                bundle.putString("fld_driverid", "1");
                bundle.putDouble("lat", 0.01);
                bundle.putDouble("lon", 0.02);

                int jobId = 10;

                ComponentName mServiceComponent = new ComponentName(MainActivity.this, PushGpsJobService.class);
                JobInfo.Builder builder = new JobInfo.Builder(jobId, mServiceComponent);
                builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
                builder.setRequiresDeviceIdle(false);
                builder.setRequiresCharging(false);
                builder.setExtras(bundle);

                // 1000 ms = 1 detik
                builder.setPeriodic(300000);
                JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
                jobScheduler.schedule(builder.build());

            }
        });
    }
}
