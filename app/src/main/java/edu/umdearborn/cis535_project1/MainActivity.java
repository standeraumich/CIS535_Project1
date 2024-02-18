package edu.umdearborn.cis535_project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button buttonAccelerometer;
    Button buttonTemperature;
    private static final String TAG = "MyFirstApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAccelerometer = findViewById(R.id.ButtonAccelerometer);
        buttonTemperature = findViewById(R.id.ButtonTemperature);

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