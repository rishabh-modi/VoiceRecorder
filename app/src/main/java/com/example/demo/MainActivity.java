package com.example.demo;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {
    private Button startbtn, stopbtn;
    private TextView statusText;

    RecordingManager recordingManager;
    int VOICE_MEMO_STATUS_RECORDING = 1;
    int VOICE_MEMO_STATUS_PAUSE = 2;
    int VOICE_MEMO_STATUS_NONE = 0;
    int VOICE_MEMO_APP_STATUS = 0;

    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startbtn = (Button)findViewById(R.id.btnRecord);
        stopbtn = (Button)findViewById(R.id.btnStop);
        statusText = (TextView) findViewById(R.id.status_text);

        stopbtn.setEnabled(false);

       recordingManager = new RecordingManager();

        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckPermissions()) {
                    stopbtn.setEnabled(true);
                    if(VOICE_MEMO_APP_STATUS != VOICE_MEMO_STATUS_RECORDING && VOICE_MEMO_APP_STATUS != VOICE_MEMO_STATUS_PAUSE)
                    {
                        setVOICE_MEMO_APP_STATUS(VOICE_MEMO_STATUS_RECORDING);
                        Log.d("vm" , "rishabh 1");
                        startbtn.setText("PAUSE Recording");
                        statusText.setText("Recording...");
                        recordingManager.startRecording();

                    }
                    else if(VOICE_MEMO_APP_STATUS == VOICE_MEMO_STATUS_RECORDING)
                    {
                        setVOICE_MEMO_APP_STATUS(VOICE_MEMO_STATUS_PAUSE);
                        Log.d("vm" , "rishabh 2");
                        startbtn.setText("start Recording");
                        statusText.setText("Paused");
                        recordingManager.pauseRecording();

                    }
                    else if (VOICE_MEMO_APP_STATUS == VOICE_MEMO_STATUS_PAUSE)
                    {
                        setVOICE_MEMO_APP_STATUS(VOICE_MEMO_STATUS_RECORDING);
                        Log.d("vm" , "rishabh 3");
                        statusText.setText("Recording...");
                        startbtn.setText("PAUSE Recording");
                        recordingManager.resumeRecording();
                    }

                }
                else
                {
                    RequestPermissions();
                }
            }
        });
        stopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopbtn.setEnabled(false);
                startbtn.setEnabled(true);
                startbtn.setText("Start Recording");
                statusText.setText("Ready");
                setVOICE_MEMO_APP_STATUS(VOICE_MEMO_STATUS_NONE);
                recordingManager.stopRecording();
                Toast.makeText(getApplicationContext(), "Recording Stopped", Toast.LENGTH_LONG).show();
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_AUDIO_PERMISSION_CODE:
                if (grantResults.length> 0) {
                    boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permissionToStore = grantResults[1] ==  PackageManager.PERMISSION_GRANTED;
                    if (permissionToRecord && permissionToStore) {
                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
    public boolean CheckPermissions() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }
    private void RequestPermissions() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }

    public int getVOICE_MEMO_APP_STATUS() {
        return VOICE_MEMO_APP_STATUS;
    }

    public void setVOICE_MEMO_APP_STATUS(int VOICE_MEMO_APP_STATUS) {
        this.VOICE_MEMO_APP_STATUS = VOICE_MEMO_APP_STATUS;
    }
}
