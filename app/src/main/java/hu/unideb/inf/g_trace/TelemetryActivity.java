package hu.unideb.inf.g_trace;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.widget.Toast;

public class TelemetryActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private TextView gForceValue, maxGLabel;
    private float maxG = 0;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telemetry);
        gForceValue = findViewById(R.id.gForceValue);
        maxGLabel = findViewById(R.id.maxGLabel);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        dbHelper = new DatabaseHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            double acceleration = Math.sqrt(x*x + y*y + z*z);
            float gForce = (float) (acceleration / 9.81);
            gForceValue.setText(String.format("%.2f G", gForce));

            if (gForce > maxG) {
                maxG = gForce;
                maxGLabel.setText(String.format("SESSION MAX: %.2f G", maxG));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void stopTelemetry(View view) {
        SharedPreferences prefs = getSharedPreferences("GTracePrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        float currentMax = prefs.getFloat("fastest_g", 0);

        if (maxG > currentMax) {
            editor.putFloat("fastest_g", maxG);
            editor.apply();
        }

        if (maxG > 0.1) {
            dbHelper.addResult(maxG);
            Toast.makeText(this, "Session saved to Paddock History", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}
