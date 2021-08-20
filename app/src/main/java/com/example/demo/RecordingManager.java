package com.example.demo;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class RecordingManager {

    private MediaRecorder mRecorder;
    private static final String LOG_TAG = "AudioRecording";
    private static String mFileName = null;




    public RecordingManager() {

        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/Download/VoiceMemo.3gp";


    }

    public void startRecording()
    {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mRecorder.setOutputFile(mFileName);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
        mRecorder.start();
       // Toast.makeText(getApplicationContext(), "Recording Started", Toast.LENGTH_LONG).show();

    }


    public void stopRecording()
    {

        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

    }

    public void pauseRecording()
    {
        mRecorder.pause();
    }

    public void resumeRecording()
    {
        mRecorder.resume();
    }

}
