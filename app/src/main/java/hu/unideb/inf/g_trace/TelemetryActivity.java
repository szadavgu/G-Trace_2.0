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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TelemetryActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private TextView txtCurrentG, txtMaxG;
    private float maxG = 0;
    private DatabaseHelper dbHelper;
    private GForceView gForceVisual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telemetry);

        txtCurrentG = findViewById(R.id.txtCurrentG);
        txtMaxG = findViewById(R.id.txtMaxG);
        gForceVisual = findViewById(R.id.gForceVisual);

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

        SharedPreferences prefs = getSharedPreferences("GTracePrefs", MODE_PRIVATE);
        String lastReset = prefs.getString("last_reset_date", "");
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        if (!today.equals(lastReset)) {
            dbHelper.clearAllData();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putFloat("fastest_g", 0);
            editor.putString("last_reset_date", today);
            editor.apply();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        double gResult = Math.sqrt(x*x + y*y + z*z) / 9.81;

        txtCurrentG.setText(String.format("%.2f G", gResult));

        if (gForceVisual != null) {
            gForceVisual.updatePosition(x, y, z);
        }

        if (gResult > maxG) {
            maxG = (float) gResult;
            txtMaxG.setText(String.format("MAX: %.2f G", maxG));
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