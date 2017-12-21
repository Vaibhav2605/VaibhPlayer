package com.example.vaibhav.vaibhplayer.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vaibhav.vaibhplayer.R;


import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.MODIFY_AUDIO_SETTINGS;
import static android.Manifest.permission.PROCESS_OUTGOING_CALLS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;

public class splashActivity extends AppCompatActivity {
    private TextView echotext;
    private TextView musictext;
    private TextView bytext;
    Handler handler = new Handler();

//    ArrayList<String> arrprem=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE, CALL_PHONE, MODIFY_AUDIO_SETTINGS, RECORD_AUDIO, PROCESS_OUTGOING_CALLS}, 1);
//        arrprem.add(READ_EXTERNAL_STORAGE);
//        arrprem.add(android.Manifest.permission.MODIFY_AUDIO_SETTINGS);
//        arrprem.add(android.Manifest.permission.RECORD_AUDIO);
//        arrprem.add(android.Manifest.permission.PROCESS_OUTGOING_CALLS);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);

            }
        },4000);
        }




    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //If user presses allow
                    Toast.makeText(splashActivity.this, "Permission granted!", Toast.LENGTH_SHORT).show();
                    Log.d("Player", "Granted");
//
//
                } else {
                    //If user presses deny
                    Log.d("Player", "Denied");
                    Toast.makeText(splashActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }

    }






}
