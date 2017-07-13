package com.example.axay.o2hleave;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


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
    private EditText reason;
    private Button save_btn,apply_btn,select_recepeints;
    private TextView leave_type;
    private DatePickerDialog.OnDateSetListener fromdatelistener;
    private DatePickerDialog.OnDateSetListener todatelistener;
    String date1,date2;
    String from,to;
    Date first,second;
    int day1,day2;
  Spinner spinner,mspin;
     public float days,half_day,quarter_day;

    ArrayAdapter<CharSequence> adapter;
    ListView lv;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_leave);
        reason=(EditText)findViewById(R.id.reason);
        fomDate=(TextView)findViewById(R.id.from_date);
        ToDate=(TextView)findViewById(R.id.to_date);
        leave_type=(TextView)findViewById(R.id.leave_type);
        save_btn=(Button)findViewById(R.id.save_button);
        apply_btn=(Button)findViewById(R.id.apply_button);
        select_recepeints=(Button)findViewById(R.id.edit_rec);
        lv = (ListView)findViewById(R.id.result);



        final FragmentManager fm=getFragmentManager();
        final  edit_recepients er=new edit_recepients();

        select_recepeints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                er.show(fm,"TV_tag");

            }
        });
            SharedPreferences sharedPreferences =getSharedPreferences("MyData",MODE_PRIVATE);
             String leave_typ= sharedPreferences.getString("leave_type","null");
        String formdate= sharedPreferences.getString("fromDate","null");
        String toDate= sharedPreferences.getString("ToDate","null");
        String reson= sharedPreferences.getString("reason","null");
        String d_count= sharedPreferences.getString("days_count","null");
    String recepienst= sharedPreferences.getString("recepients","null");
        if(leave_typ.equals("null") || formdate.equals("null") || toDate.equals("null") || reson.equals("null") || reson.equals("null"))
        {
            Toast.makeText(getApplicationContext(), "not data found",Toast.LENGTH_SHORT).show();

        }
        else {

            leave_type.setText(leave_typ);
            fomDate.setText(formdate);
            ToDate.setText(toDate);
            reason.setText(reson);



        }




          save_btn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                /*  SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("MyData",MODE_PRIVATE);
                 SharedPreferences.Editor editor=sharedPreferences.edit();
                  editor.putString("leave_type",leave_type.getText().toString());
                  editor.putString("fromDate",fomDate.getText().toString());
                  editor.putString("ToDate",ToDate.getText().toString());
                  editor.putString("d_count",days_count.getText().toString());

                  editor.putString("reason",reason.getText().toString());
                  editor.putString("recepients",lv.getAdapter().toString());

                  editor.commit();*/
                  Toast.makeText(getApplicationContext(),"Application saved",Toast.LENGTH_SHORT).show();

              }
          });

        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mspin=(Spinner) findViewById(R.id.float_days_count);

        //spinner for leave

        spinner=(Spinner)findViewById(R.id.leaves);
        adapter=ArrayAdapter.createFromResource(this,R.array.leave_type,R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //get data from dashboard activity and set into spineer
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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





            //listview to display selected persons on Apply leave Acitivity from the onclick event on setttings activiy.
        if(getIntent().getExtras()!=null) {
            Bundle b = getIntent().getExtras();
            String[] resultArr = b.getStringArray("selectedItems");

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.spinner_layout, resultArr);
            lv.setAdapter(adapter);
        }

        //

        //  Back button in toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.o2htextOne));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = getResources().getDrawable(R.drawable.back_arrow);
        upArrow.setColorFilter(getResources().getColor(R.color.o2htextOne), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Intent intent=new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        }






        fomDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar calendar= Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                day1=calendar.get(Calendar.DAY_OF_YEAR);

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
               date1 =+year+ "/"+month+"/"+dayOfMonth+"";
                fomDate.setText(date1);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                try {
                    first = sdf.parse(fomDate.getText().toString());
                    second = sdf.parse(fomDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                update(first,second);

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
                 day2=calendar.get(Calendar.DAY_OF_YEAR);

                DatePickerDialog datePickerDialog=new DatePickerDialog(ApplyLeave.this,
                      R.style.DatePickerDialogTheme,
                        todatelistener,year,month,day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();


            }
        });



        todatelistener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                Log.d("tag","setDate: "+year+ "/"+month+"/"+dayOfMonth+"");

               date2 =+year+ "/"+month+"/"+dayOfMonth+"";
                ToDate.setText(date2);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                try {
                    first = sdf.parse(fomDate.getText().toString());
                    second = sdf.parse(ToDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
              update(first,second);

            }
        };







    }


    @Override
    public void onBackPressed() {
         Intent intent = new Intent(getApplicationContext(), Main3Activity.class);
         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         startActivity(intent);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


   @RequiresApi(api = Build.VERSION_CODES.N)
    public void update(Date date1, Date date2)
    {
        //Difference in milliseconds:
        long difference = date2.getTime()-date1.getTime();
         days = (int) (difference / (1000 * 60 * 60 * 24));
         half_day= (float) (days-0.5);
         quarter_day= (float) (days-0.75);

       // days_count.setText(""+days);
        Float[] items = new Float[]{days,half_day,quarter_day};
        ArrayAdapter<Float> adapter_add = new ArrayAdapter<Float>(this,android.R.layout.simple_spinner_item, items);
        mspin.setAdapter(adapter_add);

    }


}
