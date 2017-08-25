package com.example.axay.o2hleave;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Context;
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
import android.preference.PreferenceManager;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


/*
  Created by Axay Soni
  1.for applying leave
  2.select leave type:- spinner\
  3.from date and to date :- date picker
  4.save and send button

 */
//second commit
public class ApplyLeave extends AppCompatActivity  {

    static final int PICK_CONTACT_REQUEST = 0;
    private static final String tag="ApplyLeave";
    private TextView fomDate;
    private TextView ToDate;
    private TextView Leave_days;
    private EditText reason;

    private Button save_btn,apply_btn,select_recepeints;
    private Spinner leave_type,leave_days;
    private DatePickerDialog.OnDateSetListener fromdatelistener;
    private DatePickerDialog.OnDateSetListener todatelistener;
    String date1,date2;
    String from,to;
    Date first,second;
    int day1,day2;
    Spinner spinner,mspin;
    String[] idArr;
     public float days,half_day,quarter_day;

    String selected_leaveType=null;
    String total_days= null;

    ArrayAdapter<CharSequence> adapter;
    ListView lv;
    RequestQueue requestQueue;
    String message;
    String text;
    int selectedPosition;
    int selected_dayposition;



    SharedPreferences sharedPreferences, sharedpref;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_leave);


        mspin=(Spinner) findViewById(R.id.float_days_count);
        spinner=(Spinner)findViewById(R.id.leaves);

        reason = (EditText) findViewById(R.id.reason);
            fomDate = (TextView) findViewById(R.id.from_date);
        Leave_days= (TextView) findViewById(R.id.total_leave_days);
            ToDate = (TextView) findViewById(R.id.to_date);
            save_btn = (Button) findViewById(R.id.save_button);
            apply_btn = (Button) findViewById(R.id.apply_button);
            select_recepeints = (Button) findViewById(R.id.edit_rec);
            lv = (ListView) findViewById(R.id.result);

            requestQueue = Volley.newRequestQueue(this);


        final FragmentManager fm=getFragmentManager();
        final  edit_recepients er=new edit_recepients();

        select_recepeints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                er.show(fm,"TV_tag");

                SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences("UserData",MODE_PRIVATE);
                SharedPreferences.Editor editor =sharedPreferences.edit();
                editor.putString("from_date",fomDate.getText().toString());
                editor.putString("to_date",ToDate.getText().toString());
                editor.putString("reason",reason.getText().toString());
                editor.putString("leave_days_count",total_days);
                editor.putInt("spinnerSelection", selectedPosition);
                editor.commit();


            }


          });


        adapter=ArrayAdapter.createFromResource(this,R.array.leave_type,R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        sharedPreferences=getApplicationContext().getSharedPreferences("UserData",MODE_PRIVATE);
        String from_date= sharedPreferences.getString("from_date","null");
        String too_date= sharedPreferences.getString("to_date","null");
        String re_ason= sharedPreferences.getString("reason","");
        String count= sharedPreferences.getString("leave_days_count","null");



        if(reason.equals("null") ||fomDate.equals("null")||ToDate.equals("null") )
        {
            Toast.makeText(getApplicationContext(),"notfoumd",Toast.LENGTH_LONG).show();
        }
        else
        {

            spinner.setSelection(sharedPreferences.getInt("spinnerSelection",0));
            reason.setText(re_ason);
            fomDate.setText(from_date);
            ToDate.setText(too_date);
            Leave_days.setText(count);


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

                JSONObject jsonObjSend = new JSONObject();
                try {
                    jsonObjSend.put("type", spinner.getSelectedItem().toString());
                    jsonObjSend.put("leave_reason", reason.getText().toString().trim());
                    jsonObjSend.put("users",17);
                    jsonObjSend.put("day",Leave_days.getText().toString());
                    jsonObjSend.put("from", fomDate.getText().toString());
                    jsonObjSend.put("to",ToDate.getText().toString());
                    jsonObjSend.put("taggedusers", new JSONArray(idArr ));
                    System.out.print(jsonObjSend);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://192.168.1.104/leave/public/api/leave"
                        , jsonObjSend,new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                             JSONArray jsonArray=new JSONArray(response);
                            Log.d("ds",response.toString());
                            Toast.makeText(ApplyLeave.this,response.toString(),Toast.LENGTH_LONG).show();
                            JSONObject jsonObject=jsonArray.getJSONObject(0);
                            //String Code= jsonObject.getString("code");
                             message= jsonObject.getString("message");
                            //Toast.makeText(Register.this,Code,Toast.LENGTH_LONG).show();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(ApplyLeave.this,response.toString(),Toast.LENGTH_LONG).show();

                        Log.d("error","error");

                    }}, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_LONG).show();
                        Log.d("error", "error from server response");
                    }});





                requestQueue.add(jsonObjectRequest);

            }
        });



        //spinner for leave


        //get data from dashboard activity and set into spineer
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    selected_leaveType =spinner.getSelectedItem().toString();
                    Toast.makeText(getApplicationContext(),selected_leaveType,Toast.LENGTH_LONG).show();


                  selectedPosition = spinner.getSelectedItemPosition();




            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        //listview to display selected persons on Apply leave Acitivity from the onclick event on setttings activiy.
        if(getIntent().getExtras()!=null) {

            Bundle b = getIntent().getExtras();
            String[] resultArr = b.getStringArray("selectedItems");
             idArr = b.getStringArray("Selected_id");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.spinner_layout, resultArr);
            lv.setAdapter(adapter);
            //spinner.getSelectedItem().toString();
            //mspin.getSelectedItem().toString();

        }

        //

        //  Back button in toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.o2htextOne));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = getResources().getDrawable(R.drawable.back_arrow);
        upArrow.setColorFilter(getResources().getColor(R.color.o2htextOne), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Intent intent=new Intent();



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
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

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
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

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
            Intent intent =new Intent(ApplyLeave.this,Main3Activity.class);
            startActivity(intent);
        }
        return true;
    }


   @RequiresApi(api = Build.VERSION_CODES.N)
    public void update(Date date1, Date date2)
    {
        //Difference in milliseconds:
        long difference = date2.getTime()-date1.getTime();
         days = (int) (difference / (1000 * 60 * 60 * 24)+1);
         half_day= (float) (days-0.5);
         quarter_day= (float) (days-0.75);

       // days_count.setText(""+days);

        Float[] items = new Float[]{days,half_day,quarter_day};
        ArrayAdapter<Float> adapter_add = new ArrayAdapter<Float>(this,android.R.layout.simple_spinner_item, items);
        mspin.setAdapter(adapter_add);

        mspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                total_days= mspin.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(),total_days,Toast.LENGTH_SHORT).show();
                selected_dayposition = mspin.getSelectedItemPosition();


                SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences("UserData",MODE_PRIVATE);
                SharedPreferences.Editor editor =sharedPreferences.edit();

                editor.putInt("spinner_daySelection", selected_dayposition);
                editor.commit();


                Leave_days.setVisibility(View.GONE);

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sharedpref=getApplicationContext().getSharedPreferences("UserData",MODE_PRIVATE);
            mspin.setSelection(sharedpref.getInt("spinner_daySelection",0));





    }


}
