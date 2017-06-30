package com.example.axay.o2hleave;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;


/*
  Created by Axay Soni
  1.for applying leave
  2.select leave type:- spinner\
  3.from date and to date :- date picker
  4.save and send button

 */

//second commit
public class ApplyLeave extends AppCompatActivity {
    private static final String tag="ApplyLeave";
    private TextView fomDate;
    private TextView ToDate;
    private TextView leave_type;
    private DatePickerDialog.OnDateSetListener fromdatelistener;
    private DatePickerDialog.OnDateSetListener todatelistener;

  Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_leave);
        fomDate=(TextView)findViewById(R.id.from_date);
        ToDate=(TextView)findViewById(R.id.to_date);
        leave_type=(TextView)findViewById(R.id.leave_type);

        //spinner for leave
        spinner=(Spinner)findViewById(R.id.leaves);
        adapter=ArrayAdapter.createFromResource(this,R.array.leave_type,R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //get data from dashboard activity and set into spnner
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(getIntent().getExtras()!=null) {
                    int select = getIntent().getExtras().getInt("leave");
                    spinner.setSelection(select);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //



       fomDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar calendar= Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog=new DatePickerDialog(ApplyLeave.this,
                        R.style.DatePickerDialogTheme,
                        fromdatelistener,year,month,day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
            }
        });

        fromdatelistener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                                 Log.d("tag","setDate: "+year+ "/"+month+"/"+dayOfMonth+"");

                String date =+year+ "/"+month+"/"+dayOfMonth+"";
                fomDate.setText(date);
            }
        };

        ToDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar calendar= Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog=new DatePickerDialog(ApplyLeave.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        todatelistener,year,month,day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        todatelistener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                Log.d("tag","setDate: "+year+ "/"+month+"/"+dayOfMonth+"");

                String date =+year+ "/"+month+"/"+dayOfMonth+"";
                ToDate.setText(date);
            }
        };
    }
}
