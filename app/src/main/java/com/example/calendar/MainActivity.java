package com.example.calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private MySQL dbHandler;
    private Button save;
    private Button cancel;
    private EditText editText;
    private CalendarView calendarView;
    private String selectedDate;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        save = findViewById(R.id.save);

        editText = findViewById(R.id.editText);

        calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = Integer.toString(year)+Integer.toString(month)+Integer.toString(dayOfMonth);
                 ReadData(view);
            }
        });

        try{
            dbHandler = new MySQL(this,"CalendarDatabase",null,1);
            sqLiteDatabase = dbHandler.getWritableDatabase();
            sqLiteDatabase.execSQL("CREATE TABLE EventCalendar(Date TEXT,Event TEXT)");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void InsertDatabase(View view){
        ContentValues contentValues = new ContentValues();
        contentValues.put("Date",selectedDate);
        contentValues.put("Event",editText.getText().toString());

        sqLiteDatabase.insert("EventCalendar",null,contentValues);
    }

    public void ReadData(View view){
        String query = "Select Event from EventCalendar where date ="+ selectedDate;
        try{
            Cursor cursor = sqLiteDatabase.rawQuery(query,null);
            cursor.moveToFirst();
            editText.setText(cursor.getString(0));
        }catch (Exception e){
            e.printStackTrace();
            editText.setText("");
        }

    }
    public void ReturnEvent(String selectedDate){

    }
}
