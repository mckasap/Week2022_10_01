package com.example.week2022_10_01;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView tv;
    SensorManager sm;
    long lastUpdate;
    boolean color=false;
    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);

    }

    @Override
    protected void onResume() {

        sm.registerListener(this,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),sm.SENSOR_DELAY_NORMAL);


        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv= (TextView) findViewById(R.id.textView);
        sm= (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate= System.currentTimeMillis();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;

        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        double accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = event.timestamp;
        if (accelationSquareRoot >= 2) //
        {
            if (actualTime - lastUpdate < 200) {
                return;
            }
            lastUpdate = actualTime;
            Toast.makeText(this, "Device was shuffed", Toast.LENGTH_SHORT)
                    .show();
            if (color) {
                tv.setBackgroundColor(Color.GREEN);
            } else {
                tv.setBackgroundColor(Color.RED);
            }
            color = !color;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}