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
            String recordMessage = String.format("🏆 YOUR ALL-TIME RECORD: %.2f G", fastest);
            Toast.makeText(this, recordMessage, Toast.LENGTH_LONG).show();
        }
    }


    public void showHistory(View view) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT g_value FROM History ORDER BY g_value DESC LIMIT 10", null);

        StringBuilder historyBuilder = new StringBuilder("--- TOP 10 RESULTS ---\n");
        int rank = 1;

        while (cursor.moveToNext()) {
            float val = cursor.getFloat(0);
            historyBuilder.append(rank).append(". ").append(String.format("%.2f G", val)).append("\n");
            rank++;
        }
        cursor.close();

        if (rank == 1) {
            Toast.makeText(this, "No history yet. Go racing!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, historyBuilder.toString(), Toast.LENGTH_LONG).show();
        }
    }
}