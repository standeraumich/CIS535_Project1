package edu.umdearborn.cis535_project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button buttonAccelerometer , buttonTemperature;
    private TextView textViewAccelerometerStatus, textViewAccelerometerInfo, textViewTemperatureStatus, textViewTemperatureInfo;
    private Sensor sensorAccelerometer, sensorTemperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assign views to their respective variables
        buttonAccelerometer = findViewById(R.id.ButtonAccelerometer);
        buttonTemperature = findViewById(R.id.ButtonTemperature);
        textViewAccelerometerStatus = findViewById(R.id.textViewAccelerometerStatus);
        textViewAccelerometerInfo = findViewById(R.id.textViewAccelerometerInfo);
        textViewTemperatureStatus = findViewById(R.id.textViewTemperatureStatus);
        textViewTemperatureInfo = findViewById(R.id.textViewTemperatureInfo);

        // Assign sensors to their respective variables
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        // Get sensor status and update status text views
        if(sensorAccelerometer != null){
            textViewAccelerometerStatus.setText("Status: Accelerometer Present");
        } else{
            textViewAccelerometerStatus.setText("Status: NOT PRESENT ");
        }
        if(sensorTemperature != null){
            textViewTemperatureStatus.setText("Status: Temperature Present");
        } else{
            textViewTemperatureStatus.setText("Status: NOT PRESENT");
        }

        //Get sensor info and update info text views
        float maxRangeAccelerometer = sensorAccelerometer.getMaximumRange();
        float maxRangeTemperature = sensorTemperature.getMaximumRange();
        float resolutionAccelerometer = sensorAccelerometer.getResolution();
        float resolutionTemperature = sensorTemperature.getResolution();
        int minDelayAccelerometer = sensorAccelerometer.getMinDelay();
        int minDelayTemperature = sensorTemperature.getMinDelay();

        textViewAccelerometerInfo.setText(String.format("Info: max range: %.2f, resolution: %.1e, min delay: %d",
                maxRangeAccelerometer, resolutionAccelerometer, minDelayAccelerometer));
        textViewTemperatureInfo.setText(String.format("Info: max range: %.0f, resolution: %.0f, min delay: %d",
                maxRangeTemperature, resolutionTemperature, minDelayTemperature));



        buttonAccelerometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAccelerometer = new Intent(MainActivity.this, AccelerometerActivity.class);
                startActivity(intentAccelerometer);
            }
        });

        buttonTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTemperature = new Intent(MainActivity.this, TemperatureActivity.class);
                startActivity(intentTemperature);
            }
        });
    }
}