package com.example.afinal;

import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Record extends Fragment {

    private Button ButtonRecord;
    private TextView TVRecord;
    private MediaRecorder recorder;
    private  String fileName = null;
    private static final String LOG_TAG = "Record_Log";
    private int ClickCount = 0;
    private StorageReference Storage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_record,container,false);

        ButtonRecord = v.findViewById(R.id.button_record_record);
        TVRecord = v.findViewById(R.id.tv_record);
        Storage = FirebaseStorage.getInstance().getReference();
        fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/heart_sound.3gp";

        ButtonRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickCount++;
                if(ClickCount%2 == 1) {
                    startRecording();
                    TVRecord.setText("Press button to stop recording");
                    ButtonRecord.setActivated(true);
                }
                else if(ClickCount%2 == 0) {
                    stopRecording();
                    ButtonRecord.setActivated(false);
                    ButtonRecord.setEnabled(false);
                }
            }
        });

        return v;
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        recorder.start();
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;

        uploadAudio();
        TVRecord.setText("Uploading Audio...");
    }

    private void uploadAudio() {

        StorageReference filepath = Storage.child("Audio").child("heart_sound.3gp");
        Uri uri = Uri.fromFile(new File(fileName));
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               TVRecord.setText("Upload Complete");
               ButtonRecord.setEnabled(true);
            }
        });
    }
}
