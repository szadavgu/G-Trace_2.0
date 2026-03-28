package hu.unideb.inf.g_trace;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private ListView historyListView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyListView = findViewById(R.id.historyListView);
        dbHelper = new DatabaseHelper(this);

        loadTop10();
    }

    private void loadTop10() {
        ArrayList<String> topList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT g_value, date FROM History ORDER BY g_value DESC LIMIT 10", null);

        int rank = 1;
        if (cursor.moveToFirst()) {
            do {
                float gVal = cursor.getFloat(0);
                String date = cursor.getString(1);
                topList.add(rank + ". [" + String.format("%.2f G", gVal) + "] - " + date);
                rank++;
            } while (cursor.moveToNext());
        }
        cursor.close();

        if (topList.isEmpty()) {
            topList.add("No record yet. Push the limits!");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, topList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.WHITE);
                textView.setTextSize(18);
                textView.setPadding(20, 20, 20, 20);
                return textView;
            }
        };

        historyListView.setAdapter(adapter);
    }

    public void goBack(View v) {
        finish();
    }
}