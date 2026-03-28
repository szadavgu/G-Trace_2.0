package hu.unideb.inf.g_trace;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startLive(View view) {
        Intent intent = new Intent(this, TelemetryActivity.class);
        startActivity(intent);
    }

    public void showFastest(View view) {
        SharedPreferences prefs = getSharedPreferences("GTracePrefs", MODE_PRIVATE);
        float fastest = prefs.getFloat("fastest_g", 0.0f);

        if (fastest == 0) {
            Toast.makeText(this, "No record yet. Push the limits!", Toast.LENGTH_SHORT).show();
        } else {
            String recordMessage = String.format("🏆 FASTEST OF TODAY: %.2f G", fastest);
            Toast.makeText(this, recordMessage, Toast.LENGTH_LONG).show();
        }
    }

    public void showHistory(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }
}