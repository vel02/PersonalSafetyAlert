/*
 *
 *   Created by Austria, Ariel Namias on 2/13/21 12:24 PM
 *   Copyright Ⓒ 2021. All rights reserved Ⓒ 2021 https://github.com/vel02
 *   Last modified: 2/13/21 7:04 AM
 *
 *   Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 *   except in compliance with the License. You may obtain a copy of the License at
 *   http://www.apache.org/licenses/LICENS... Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 *    either express or implied. See the License for the specific language governing permissions and
 *    limitations under the License.
 * /
 */

package com.sti.research.personalsafetyalert.util.api;

import android.media.MediaRecorder;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Parcelable;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

public class AudioRecordManager extends CountDownTimer {

    public static final int AUDIO_INTERVAL = 1_000;
    public static final int AUDIO_MAX_DURATION = 20_000;
    public static final int AUDIO_MIN_DURATION = 10_000;
    public static final int AUDIO_TEST_DURATION = 5_000;

    @Override
    public void onTick(long millisUntilFinished) {
        Log.d(TAG, "onTick: " + millisUntilFinished / 1000);
        if (recorder == null) startRecord();
    }

    @Override
    public void onFinish() {
        if (recorder != null) stopRecord();
        listener.audioPath(path, filename);
    }

    private static final String TAG = "KizAudioRecorder";

    private MediaRecorder recorder;
    private final OnAudioRecordListener listener;

    private final String folder;
    private String path;
    private String filename;

    private static AudioRecordManager instance;

    public static AudioRecordManager getInstance(long millisInFuture, long countDownInterval, OnAudioRecordListener listener, String folder) {
        if (instance == null) {
            instance = new AudioRecordManager(millisInFuture, countDownInterval, listener, folder);
        }
        return instance;
    }

    public AudioRecordManager(long millisInFuture, long countDownInterval, OnAudioRecordListener listener, String folder) {
        super(millisInFuture, countDownInterval);
        this.listener = listener;
        this.folder = folder;
    }

    private void conversion() {
        setAudioChannel();
        setAudioSource();

        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        recorder.setAudioSamplingRate(44100);
        recorder.setAudioEncodingBitRate(128000);
        recorder.setOutputFile(getLocationToSave());
    }

    private void setAudioSource() {
        try {
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        } catch (Exception e) {
            recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        }
    }

    private void setAudioChannel() {
        try {
            recorder.setAudioChannels(2);
        } catch (Exception e) {
            recorder.setAudioChannels(1);
        }
    }

    public void startRecord() {
        recorder = new MediaRecorder();
        conversion();

        try {
            recorder.prepare();
            recorder.start();

        } catch (IOException e) {
            Log.e(TAG, "RECORDING STATUS: FAILED TO PREPARE THE AUDIO RECORDING! ");
            e.printStackTrace();
        }

    }

    public void stopRecord() {
        if (recorder == null) {
            Log.e(TAG, "RECORDING STATUS: FAILED TO STOP THE RECORD, MediaRecorder NOT FOUND!");
            return;
        }

        recorder.stop();
        recorder.reset();
        recorder.release();
        recorder = null;
    }

    private String getLocationToSave() {

        final String SEPARATOR = File.separator;
        final String FOLDER_NAME = this.folder;

        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + SEPARATOR + FOLDER_NAME);

        if (!folder.exists()) {
            if (folder.mkdir()) Log.d(TAG, "STATUS SUCCESS: FOLDER CREATED SUCCESSFULLY!");
        } else Log.w(TAG, "STATUS FAILED: FOLDER EXISTS IN PHONE DIRECTORY.");

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        String timeFormat = reformatCalendar(calendar, Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND);
        String dateFormat = reformatCalendar(calendar, Calendar.DAY_OF_MONTH, Calendar.MONTH, Calendar.YEAR);

        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + SEPARATOR + FOLDER_NAME
                + SEPARATOR + "audio_record_" + dateFormat + "_" + timeFormat + ".m4a";

        filename = "audio_record_" + dateFormat + "_" + timeFormat + ".m4a";

        return path;
    }

    private String reformatCalendar(Calendar calendar, int a, int b, int c) {
        int minute_or_month;
        if (b == Calendar.MONTH)
            minute_or_month = calendar.get(b) + 1;
        else minute_or_month = calendar.get(b);
        return "" + calendar.get(a) + minute_or_month + calendar.get(c);
    }

    public interface OnAudioRecordListener {

        void audioPath(String path, String filename);

    }

}

/*
QUICK FIXED REFERENCES SAVING AUDIO IN ANDROID VERSION 30!

https://stackoverflow.com/questions/58379543/cant-create-directory-in-android-10
https://stackoverflow.com/questions/64250814/how-to-obtain-manage-external-storage-permission/66968986#66968986
https://stackoverflow.com/questions/65743960/android-r-create-a-text-file-in-download-folder-using-scoped-storage-mediasto/65744517#65744517
 */