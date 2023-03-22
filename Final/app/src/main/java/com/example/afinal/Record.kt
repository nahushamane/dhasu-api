package com.example.afinal

import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.IOException

class Record : Fragment() {
    private var ButtonRecord: Button? = null
    private var TVRecord: TextView? = null
    private var recorder: MediaRecorder? = null
    private var fileName: String? = null
    private var ClickCount = 0
    private var Storage: StorageReference? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_record, container, false)
        ButtonRecord = v.findViewById(R.id.button_record_record)
        TVRecord = v.findViewById(R.id.tv_record)
        Storage = FirebaseStorage.getInstance().reference
        fileName = Environment.getExternalStorageDirectory().absolutePath + "/heart_sound.3gp"
        ButtonRecord.setOnClickListener(View.OnClickListener {
            ClickCount++
            if (ClickCount % 2 == 1) {
                startRecording()
                TVRecord.setText("Press button to stop recording")
                ButtonRecord.setActivated(true)
            } else if (ClickCount % 2 == 0) {
                stopRecording()
                ButtonRecord.setActivated(false)
                ButtonRecord.setEnabled(false)
            }
        })
        return v
    }

    private fun startRecording() {
        recorder = MediaRecorder()
        recorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        recorder!!.setOutputFile(fileName)
        recorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        try {
            recorder!!.prepare()
        } catch (e: IOException) {
            Log.e(LOG_TAG, "prepare() failed")
        }
        recorder!!.start()
    }

    private fun stopRecording() {
        recorder!!.stop()
        recorder!!.release()
        recorder = null
        uploadAudio()
        TVRecord!!.text = "Uploading Audio..."
    }

    private fun uploadAudio() {
        val filepath = Storage!!.child("Audio").child("heart_sound.3gp")
        val uri = Uri.fromFile(File(fileName))
        filepath.putFile(uri).addOnSuccessListener {
            TVRecord!!.text = "Upload Complete"
            ButtonRecord!!.isEnabled = true
        }
    }

    companion object {
        private const val LOG_TAG = "Record_Log"
    }
}