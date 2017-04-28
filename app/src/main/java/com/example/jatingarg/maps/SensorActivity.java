package com.example.jatingarg.maps;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private TextView mLightSensorTV;
    private TextView mProximitySensorTV;
    private TextView mAccelerometerTV;

    private static final String TAG = "SensorActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        mLightSensorTV = (TextView) findViewById(R.id.textLight);
        mAccelerometerTV = (TextView) findViewById(R.id.textAccelerometer);
        mProximitySensorTV = (TextView) findViewById(R.id.textProximity);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        registerForSensors();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: inside");
        registerForSensors();
    }

    private void registerForSensors(){
        if(isAccelerometerPresent()){
            Log.d(TAG, "onCreate: found accelerometer");
            Sensor mMotionSenser = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mSensorManager.registerListener(this,mMotionSenser,SensorManager.SENSOR_DELAY_GAME);
        }
        if(isLightSensorPresent()){
            Log.d(TAG, "onCreate: found light sensor");
            Sensor mEnvironMentSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            mSensorManager.registerListener(this,mEnvironMentSensor,SensorManager.SENSOR_DELAY_GAME);
        }

        if(isProximityPresent()){
            Log.d(TAG, "onCreate: found proximity sensor");
            Sensor mPositionSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            mSensorManager.registerListener(this,mPositionSensor,SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        TextView targetTV = null;
        switch (event.sensor.getType()){
            case Sensor.TYPE_PROXIMITY:
                targetTV = mProximitySensorTV;
                break;
            case Sensor.TYPE_ACCELEROMETER:
                targetTV = mAccelerometerTV;
                break;
            case Sensor.TYPE_LIGHT:
                targetTV = mLightSensorTV;
                break;

        }

        if(targetTV != null){
            targetTV.setText("X: " + event.values[0] + " Y: " + event.values[1] + " Z: " + event.values[2]);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

   public boolean isLightSensorPresent(){
       if(mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null){
           return true;
       }
       return false;
   }

   public boolean isAccelerometerPresent(){
       if(mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
           return true;
       }
       return false;
   }

   public boolean isProximityPresent(){
       if(mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null){
           return true;
       }
       return false;
   }
}
