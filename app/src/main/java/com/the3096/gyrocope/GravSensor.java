package com.the3096.gyrocope;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.PowerManager;

/**
 * Created by Frank on 7/29/2015. Yeah
 */
public class GravSensor implements SensorEventListener {
    Context mContext;
    PowerManager.WakeLock mWakeLock;
    SensorManager mSensorManager;
    Sensor mSensor;
    Ringtone alarm;
    float x, y, z;
    boolean notStarted;

    GravSensor(Activity c) {
        mContext = c;

        PowerManager powerManager = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        mWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "MyWakelockTag");

        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

        alarm = RingtoneManager.getRingtone(mContext,
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));

        notStarted = true;
    }


    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    void getStarted() {
        notStarted = false;
        mWakeLock.acquire();
    }

    public void onSensorChanged(SensorEvent event) {
        if (notStarted) {
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
            return;
        }

        if (Math.abs(event.values[0] - x) < 1 &&
                Math.abs(event.values[1] - y) < 1 &&
                Math.abs(event.values[2] - z) < 1) {
        } else {
            alarm.play();
        }
    }
}
