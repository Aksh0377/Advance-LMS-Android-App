package com.example.axay.o2hleave;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.TimeZone;

public class demoData extends AppCompatActivity {
    DatePicker fromDate, toDate;
    TextView days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_data);
        fromDate = (DatePicker) findViewById(R.id.date1);
        toDate = (DatePicker) findViewById(R.id.date2);
        days = (TextView)findViewById(R.id.day);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        fromDate.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                UpdateText();
            }
        });
        toDate.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                UpdateText();
            }
        });
    }

    private void UpdateText() {
        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());

        localCalendar.set(fromDate.getYear(),  fromDate.getMonth(), fromDate.getDayOfMonth());
        int day1 = localCalendar.get(Calendar.DAY_OF_YEAR);

        localCalendar.set(toDate.getYear(), toDate.getMonth(), toDate.getDayOfMonth());
        int day2 = localCalendar.get(Calendar.DAY_OF_YEAR);

        int day = day2-day1;
        days.setText(""+day);
    }
}
