package edu.umdearborn.cis535_project1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

public class AccelerometerActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    private double accelerationCurrentValue;
    private int pointsPlotted = 0;
    private int sampleCount = 0;
    private int graphIntervalCounter = 0;
    private Viewport viewport;
    private TextView textViewAccelerometerData;
    private List<Double> acceleration100msSample = new ArrayList<Double>();
    private Animation animation;

    LineGraphSeries<DataPoint> seriesCurrentValue = new LineGraphSeries<DataPoint>(new DataPoint[] {});
    LineGraphSeries<DataPoint> seriesAvg = new LineGraphSeries<DataPoint>(new DataPoint[] {});
    LineGraphSeries<DataPoint> seriesSTD = new LineGraphSeries<DataPoint>(new DataPoint[] {});
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            accelerationCurrentValue = Math.sqrt((x * x + y * y + z * z));
            acceleration100msSample.add(accelerationCurrentValue);
            if (accelerationCurrentValue > 10){
                textViewAccelerometerData.startAnimation(animation);
            }
            // Update graph
            sampleCount++;
            if (sampleCount == 10){
                sampleCount = 0;
                double sum = 0;
                for (double d : acceleration100msSample){
                    sum += d;
                }
                double accelerationAverage = sum / acceleration100msSample.size();
                double variance = 0.0;
                for (double num : acceleration100msSample){
                    variance += Math.pow(num - accelerationAverage, 2);
                }
                double standardDev = Math.sqrt(variance / acceleration100msSample.size());
                seriesCurrentValue.appendData(new DataPoint(pointsPlotted, accelerationCurrentValue), true, 100);
                seriesAvg.appendData(new DataPoint(pointsPlotted, accelerationAverage), true, 100);
                seriesSTD.appendData(new DataPoint(pointsPlotted, standardDev), true, 100);
                pointsPlotted++;
            }

            viewport.setMaxX(pointsPlotted);
            viewport.setMinX(pointsPlotted - 15);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        // Initialize sensor objects
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Initialize textview
        textViewAccelerometerData = findViewById(R.id.textViewAccelerometerData);

        // Initialize animation
        animation = AnimationUtils.loadAnimation(this,R.anim.bounce);

        //Graph sensor values
        GraphView graph = (GraphView) findViewById(R.id.graph);
        viewport = graph.getViewport();
        viewport.setScalable(true);
        viewport.setXAxisBoundsManual(true);
        seriesCurrentValue.setTitle("Current Value");
        seriesCurrentValue.setColor(Color.GREEN);
        seriesCurrentValue.setDrawDataPoints(true);
        seriesCurrentValue.setDataPointsRadius(10);
        seriesCurrentValue.setThickness(8);

        seriesAvg.setTitle("Mean");
        seriesAvg.setColor(Color.BLUE);
        seriesAvg.setDrawDataPoints(true);
        seriesAvg.setDataPointsRadius(10);
        seriesAvg.setThickness(8);

        seriesSTD.setTitle("std Dev");
        seriesSTD.setColor(Color.YELLOW);
        seriesSTD.setDrawDataPoints(true);
        seriesSTD.setDataPointsRadius(10);
        seriesSTD.setThickness(8);

        graph.addSeries(seriesCurrentValue);
        graph.addSeries(seriesAvg);
        graph.addSeries(seriesSTD);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

    }

    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(sensorEventListener, sensorAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }


}