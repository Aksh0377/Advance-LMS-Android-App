package com.example.axay.o2hleave;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.desai.vatsal.mydynamiccalendar.MyDynamicCalendar;
import com.desai.vatsal.mydynamiccalendar.OnDateClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;


/*
  Created by Axay Soni
  1.for applying leave
  2.select leave type:- spinner\
  3.from date and to date :- date picker
  4.save and send button
 */
/*
* TODO : DISABLE HOLIDAYS AND ALTERNATIVE SATURDAY & SUNDAY DISABLE FROM DATE-TIME PICKER
* */
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
    public float days,half_day,quarter_day;
    String date1,date2;
    String from,to;
    Date first_ex,second_ex;
    int day1_ex,day2_ex;
    Spinner spinner,mspin;
    String[] idArr;
    String selected_leaveType=null;
    String total_days= null;
    ArrayAdapter<CharSequence> adapter;
    ListView lv;
    RequestQueue requestQueue;
    String message;
    String U_id;
    String text;
    int selectedPosition;
    int selected_dayposition;
    FragmentManager fm;
    edit_recepients  er;
    SharedPreferences sharedPreferences, sharedpref;


    private MyDynamicCalendar myCalendar, mycalender_todate;
    long f_date;
    long l_date;
    Date first, second;
    Date first_loop,second_loop;
    Date date_sunday;
    int demo1,demo2;
    String demo1_str;
    String demo2_str;
    private static final String[] bankHolidays = {"2-11-2017","13-11-2017","8-10-2017","10-12-2017"};
    private static Set<Date> holidayDates;
    List<Date> daylist;
    SimpleDateFormat sm;
    List<Date>  Array3;
    List<Date>  Array3_sun;
    List<Date> newList_final;
    List<Date> array4;
    int day1;
    int day2;
    String Year_start;
    String Year_end;
    Date Year_start_date;
    Date Year_end_date;
    Date date_sat;
    Date date_sun;
    Bundle USer_id;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_leave);


        myCalendar = (MyDynamicCalendar) findViewById(R.id.myCalendar);
        mycalender_todate = (MyDynamicCalendar) findViewById(R.id.mycalender_todate);
        sm=new SimpleDateFormat("dd-MM-yyyy");
        Array3=new ArrayList<Date>();
        Array3_sun=new ArrayList<Date>();

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
        adapter=ArrayAdapter.createFromResource(this,R.array.leave_type,R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        fm =getFragmentManager();
        er=new edit_recepients();


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

        SharedPreferences sharedPreferences1=getApplicationContext().getSharedPreferences("userID",MODE_PRIVATE);
        U_id= sharedPreferences1.getString("id","null"); //get id of logged in user

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


//        Ranom <code></code>



        /*
        ===================================
        Save  Leave Application
        ====================================
        * */
        save_btn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Toast.makeText(getApplicationContext(),"Application saved",Toast.LENGTH_SHORT).show();}
          });

        /*
        ===================================
        Apply Leave Application
        ====================================
        * */
        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject jsonObjSend = new JSONObject();
                try {
                    jsonObjSend.put("type", spinner.getSelectedItem().toString());
                    jsonObjSend.put("leave_reason", reason.getText().toString().trim());
                    jsonObjSend.put("users",U_id.toString());
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

        //get data from dashboard activity and set into spineer
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                 selectedPosition=spinner.getSelectedItemPosition();
                spinner.setSelection(selectedPosition);

                Bundle extras = getIntent().getExtras();
                String className = getIntent().getStringExtra("Class");
                if(getIntent().getExtras()!=null && className.equals("B"))
                {

                    Intent mIntent = getIntent();
                    int intValue = mIntent.getIntExtra("leave", 0);
                    spinner.setSelection(intValue);
                    Toast.makeText(getApplicationContext(),selected_leaveType,Toast.LENGTH_LONG).show();
                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        //listview to display selected persons on Apply leave Activity from the onclick event on setttings activiy.
        String className = getIntent().getStringExtra("Class");
        if(getIntent().getExtras()!=null &&  className.equals("A")) {
            Bundle b = getIntent().getExtras();
            String[] resultArr = b.getStringArray("selectedItems");
            idArr = b.getStringArray("Selected_id");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, resultArr);
            lv.setAdapter(adapter);
        }
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
        }

         /*
        ==============================================
        Select from & to date and calculate difference
        ==============================================
         */

        if (holidayDates == null) {
            holidayDates = new HashSet<Date>();
            for (int i = 0; i < bankHolidays.length; i++) {
                Calendar c = Calendar.getInstance();
                Date date = null;
                try {
                    date = sm.parse(bankHolidays[i]);
                    Toast.makeText(getApplicationContext(),"List of Holidays"+date,Toast.LENGTH_LONG).show();
                    holidayDates.add(date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                c.setTime(date);
            }
            System.out.print("Holidays"+holidayDates);
        }


        fomDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendar.setVisibility(View.VISIBLE);
                myCalendar.showMonthView();
            }
        });


         /*
        * add holiday Starts here  !
        *
        * */


        int year = 2017;
        int month = Calendar.JANUARY;
        Calendar cal = new GregorianCalendar(year, month, 1);


        do {
            int week2 =cal.get(Calendar.WEEK_OF_MONTH);
            // get the day of the week for the current day
            int day = cal.get(Calendar.DAY_OF_WEEK);

            if ((week2 == 1 || week2 == 3) && day==Calendar.SATURDAY  ) {
                // print the day - but you could add them to a list or whatever
                int cal_month=cal.get(Calendar.MONTH);
                int Cal_act_month =cal_month+1;
                String sat =cal.get(Calendar.DATE)+"-"+Cal_act_month+"-"+cal.get(Calendar.YEAR)+"-"+cal.get(Calendar.WEEK_OF_MONTH);
                // System.out.print(cal.get(Calendar.DATE)+"-"+Cal_act_month+"-"+cal.get(Calendar.YEAR)+"-"+cal.get(Calendar.WEEK_OF_MONTH));
                try {
                    date_sat =sm.parse(sat);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Array3.add(date_sat);
                // System.out.println("sat"+Array3);
            }
            // advance to the next day
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }  while (cal.get(Calendar.YEAR) == year);


        Calendar cal_sun = new GregorianCalendar(year, month, 1);
        do {
            // get the day of the week for the current day
            int day = cal_sun.get(Calendar.DAY_OF_WEEK);

            // System.out.print("- week2 -"+week2);

            // check if it is a Saturday or Sunday
            if ( day==Calendar.SUNDAY  ) {
                // print the day - but you could add them to a list or whatever
                int cal_month=cal_sun.get(Calendar.MONTH);
                int Cal_act_month =cal_month+1;

                String sun =cal_sun.get(Calendar.DATE)+"-"+Cal_act_month+"-"+cal_sun.get(Calendar.YEAR);
                //System.out.print(cal.get(Calendar.DATE)+"-"+Cal_act_month+"-"+cal.get(Calendar.YEAR)+"-"+cal.get(Calendar.WEEK_OF_MONTH));

                try {
                    date_sun =sm.parse(sun);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Array3_sun.add(date_sun);
                //System.out.println("sun"+Array3_sun);
                List<Date> newList = new ArrayList<Date>(Array3);
                newList.addAll(Array3_sun);                      //combining sundays array with saturday array.
                //System.out.println("sun-sat"+newList);
                newList_final = new ArrayList<Date>(newList);
                newList_final.addAll(holidayDates);        //combining sat-sun array into other holidays
                System.out.println("All Holidays"+newList_final);
            }
            // advance to the next day
            cal_sun.add(Calendar.DAY_OF_YEAR, 1);
        }  while (cal_sun.get(Calendar.YEAR) == year);


        /*
        * add holiday ends here  !
        *
        * */



        /*
        * add colors to holidays !
        *
        * */


        myCalendar.isSaturdayOff(true, "#ffffff", "#ff0000");
        // set all sunday off(Holiday) - default value is false
        // isSundayOff(true/false, date_background_color, date_text_color);

        myCalendar.isSundayOff(true, "#ffffff", "#ff0000");
        myCalendar.setHolidayCellBackgroundColor("#ffffff");
        myCalendar.setHolidayCellTextColor("#f5f5f5");
        // set holiday date clickable true/false
        myCalendar.setHolidayCellClickable(false);
        // Add holiday  -  addHoliday(holiday_date)
        myCalendar.addHoliday("2-11-2017");
        myCalendar.addHoliday("13-11-2017");
        myCalendar.addHoliday("8-10-2017");
        myCalendar.addHoliday("10-12-2017");



        /*
        * add colors to holidays ends here  !
        *
        * */

        myCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onClick(Date date1) {
                Toast.makeText(getApplicationContext(),"date"+ String.valueOf(date1),Toast.LENGTH_LONG).show();
                myCalendar.setVisibility(View.GONE);

                day1= (int) (date1.getTime()/(1000 * 60 * 60 * 24));
                demo1= date1.getDate();
                demo1_str=date1.getDate()+"-"+(date1.getMonth()+1)+"-"+(date1.getYear()+1900);
                fomDate.setText(demo1_str);
                int year = date1.getMonth();

                f_date =  date1.getTime();
                Toast.makeText(getApplicationContext(),"year"+demo1_str,Toast.LENGTH_LONG).show();
                try {
                    first_loop = sm.parse(demo1_str);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),"yeardv"+first_loop,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onLongClick(Date date) {
                Log.e("date", String.valueOf(date));
            }
        });


        ToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mycalender_todate.setVisibility(View.VISIBLE);
                mycalender_todate.showMonthView();

            }
        });
        mycalender_todate.isSaturdayOff(true, "#ffffff", "#ff0000");
        // set all sunday off(Holiday) - default value is false
        // isSundayOff(true/false, date_background_color, date_text_color);
        mycalender_todate.isSundayOff(true, "#ffffff", "#ff0000");
        mycalender_todate.setHolidayCellBackgroundColor("#ffffff");
        mycalender_todate.setHolidayCellTextColor("#f5f5f5");
        // set holiday date clickable true/false
        mycalender_todate.setHolidayCellClickable(false);
        // Add holiday  -  addHoliday(holiday_date)
        mycalender_todate.addHoliday("2-11-2017");
        mycalender_todate.addHoliday("13-11-2017");
        mycalender_todate.addHoliday("8-10-2017");
        mycalender_todate.addHoliday("10-12-2017");

        mycalender_todate.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onClick(Date date2) {
                Toast.makeText(getApplicationContext(),"date"+ String.valueOf(date2),Toast.LENGTH_LONG).show();
                mycalender_todate.setVisibility(View.GONE);
                demo2= date2.getDate();
                l_date =date2.getTime();

                day2= (int) (date2.getTime()/ (1000 * 60 * 60 * 24));
                demo2_str=date2.getDate()+"-"+(date2.getMonth()+1)+"-"+(date2.getYear()+1900);
                ToDate.setText(demo2_str);
                try {
                    second_loop = sm.parse(demo2_str);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),"yeardv"+second_loop,Toast.LENGTH_LONG).show();

                daylist =  new LinkedList<>();
                Calendar start = Calendar.getInstance();
                start.setTime(first_loop);
                Calendar end = Calendar.getInstance();
                end.setTime(second_loop);

                for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
                    // Do your job here with `date`.
                    System.out.println(date);
                    daylist.add(date);
                    Toast.makeText(getApplicationContext(),"List of Dates"+date,Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getApplicationContext(),"List of Dates"+daylist,Toast.LENGTH_LONG).show();
                 array4 = new ArrayList<Date>(daylist);//Create copy of array 1
                array4.removeAll(newList_final);//Remove common elements
                Toast.makeText(getApplicationContext(),"Days of leave"+array4,Toast.LENGTH_LONG).show();
                int total_leave =array4.size();
                Leave_days.setText(Integer.toString(array4.size()+1));

                if(l_date<f_date)
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(ApplyLeave.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("End date must be greater than Start date");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }

                update();



            }
            @Override
            public void onLongClick(Date date) {
                Log.e("date", String.valueOf(date));
            }
        });


    }

   @RequiresApi(api = Build.VERSION_CODES.N)
    public void update()
    {
        //Difference in milliseconds:
         days = (array4.size()+1);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return true;
    }
}
