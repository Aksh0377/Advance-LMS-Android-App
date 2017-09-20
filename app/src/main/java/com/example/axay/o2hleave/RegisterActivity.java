package com.example.axay.o2hleave;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.axay.o2hleave.model.department_pojo;
import com.example.axay.o2hleave.model.team_pojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, Spinner.OnItemSelectedListener {
    public String Department_url ="http://192.168.1.104/leave/public/api/department/";
    public static final String KEY_USERNAME = "first_name";
    public static final String KEY_PASSWORD = "user_password";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_DESIGNATION = "user_designation";
    public static final String AVTAR = "avatar";
    public static final String KEY_TEAM = "team";
    public static final String KEY_DEPARTMENT = "department";
    public static final String EMPLOYEE_CODE = "employee_code";
    String json_dept_id;
    String json_dept;
    String json_team_id;
    String json_team;
    String selected_dept_id=null;
    String selected_team_id;
    Bitmap bitmap;
    String encodedImage;
    RequestQueue requestQueue_dept,getRequestQueue_team,requestQueue_register;      //Volley RequestQue
    EditText full_name, e_mail, password,employee_code,designation;
    Button register;
    Spinner department_spinner,team_spinner;
    ArrayList<department_pojo> department_pojo_spinner_data;
    ArrayList<team_pojo> team_pojo_spinner_data;
    ArrayList<String> deaprtment_list;
    ArrayList<String> team_list;
    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> arrayAdapter_team;

    private AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final ProgressDialog progressDialog =new ProgressDialog(this);  //progress dialog
        progressDialog.setMessage("Loading Departments");
        progressDialog.show();

        department_pojo_spinner_data = new ArrayList<>();
        team_pojo_spinner_data = new ArrayList<>();
        deaprtment_list = new ArrayList<>();
        team_list=new ArrayList<>();
        full_name=(EditText)findViewById(R.id.fullname);
        e_mail=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        employee_code=(EditText)findViewById(R.id.employee_code);
        designation=(EditText)findViewById(R.id.designation);
        department_spinner=(Spinner)findViewById(R.id.xml_dept_spinner);
        team_spinner=(Spinner)findViewById(R.id.xml_team_spinner);
        requestQueue_dept= Volley.newRequestQueue(this);            //Volley RequestQueue
        getRequestQueue_team= Volley.newRequestQueue(this);         //Volley RequestQueue
        requestQueue_register= Volley.newRequestQueue(this);        //Volley RequestQueue
        department_spinner.setOnItemSelectedListener(this);
        team_spinner.setOnItemSelectedListener(this);
        register=(Button)findViewById(R.id.register);
        bitmap = (Bitmap) this.getIntent().getParcelableExtra("photo");
        ImageView profile_pic=(ImageView)findViewById(R.id.register_profile);
        profile_pic.setImageBitmap(bitmap);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.o2htextOne));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = getResources().getDrawable(R.drawable.back_arrow);
        upArrow.setColorFilter(getResources().getColor(R.color.o2htextOne), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.fullname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.email, "[a-zA-Z0-9._-]+@o2h.com", R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.password, "((?=.*[a-z]).{6,20})", R.string.passworderror);
        awesomeValidation.addValidation(this, R.id.employee_code, "((?=.*\\d).{1,4})", R.string.empoyeecodeerror);
        awesomeValidation.addValidation(this, R.id.designation, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.empoyeecodeerror);

        register.setOnClickListener( this);

        /*//============================================================
        // set list of department into the spinner using volley library
           =============================================================*/
        arrayAdapter = new ArrayAdapter<String>(RegisterActivity.this, R.layout.spinner_dept_layout, deaprtment_list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        department_spinner.setAdapter(arrayAdapter);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://192.168.1.104/leave/public/api/department"
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        json_dept_id = jsonObject.getString("identity");
                        json_dept = jsonObject.getString("department");
                        deaprtment_list.add(json_dept);
                        arrayAdapter.notifyDataSetChanged();
                        department_pojo deprt_data = new department_pojo();
                        deprt_data.setId(json_dept_id);
                        deprt_data.setDepartment(json_dept);
                        department_pojo_spinner_data.add(deprt_data);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("error", "error from server response");
            }
        });
        requestQueue_dept.add(jsonObjectRequest);
    }

    /*
  * ======================================================
  * Validate input and Register User using volley request
  * ======================================================
   */
    private void submitForm() {
        //first validate the form then move ahead
        //if this becomes true that means validation is successfull
        if (awesomeValidation.validate()) {

            final String first_name = full_name.getText().toString().trim();
            final String user_password = password.getText().toString().trim();
            final String email = e_mail.getText().toString().trim();
            final String user_designation = designation.getText().toString().trim();
            final String department = selected_dept_id;
            final String team = selected_team_id;
            final String emp_code =employee_code.getText().toString().trim();

            Map<String, String> params = new HashMap<String, String>();
            params.put(KEY_USERNAME, first_name);
            params.put(KEY_PASSWORD, user_password);
            params.put(KEY_EMAIL, email);
            params.put(KEY_DESIGNATION, user_designation);
            params.put(AVTAR, getStringImage(bitmap));
            params.put(KEY_TEAM, team);
            params.put(KEY_DEPARTMENT, department);
            params.put(EMPLOYEE_CODE, emp_code);
            JSONObject json = new JSONObject(params);
            System.out.println(json);
            Log.d("error", json.toString());

            final ProgressDialog progressDialog1=new ProgressDialog(this);
            progressDialog1.setMessage("Registering");
            progressDialog1.show();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://192.168.1.104/leave/public/api/users"
                    , json, new Response.Listener<JSONObject>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(JSONObject response) {
                      progressDialog1.dismiss();

                    try {
                        // JSONArray jsonArray=new JSONArray(response);
                        Log.d("ds", response.toString());
                        Toast.makeText(RegisterActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        // JSONObject jsonObject=jsonArray.getJSONObject(0);
                        //String Code= jsonObject.getString("code");
                        //String message= jsonObject.getString("message");
                        //Toast.makeText(Register.this,Code,Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(RegisterActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                    Log.d("error", "error");

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    Log.d("error", "error from server response");
                }
            });
            requestQueue_register.add(jsonObjectRequest);

            SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences("UserData",MODE_PRIVATE);
            SharedPreferences.Editor editor =sharedPreferences.edit();
            editor.putString("name",full_name.getText().toString());
            editor.putString("e_mail",e_mail.getText().toString());
            editor.putString("password",password.getText().toString());
            editor.putString("employee_code",employee_code.getText().toString());
            editor.putString("desig.",designation.getText().toString());
            editor.commit();
        }
    }

    //process the data further
    public void onClick(View view) {
        if (view == register) {
            submitForm();
        }
    }

    /*
 * ==============================================================================================================================
 *   when i will select any department from one spinner it will call team from api accordingly and show into another team spinner
 * ==============================================================================================================================
   */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()){
            case R.id.xml_dept_spinner :

                selected_dept_id =department_pojo_spinner_data.get(position).getId();
                arrayAdapter_team = new ArrayAdapter<String>(RegisterActivity.this, R.layout.spinner_team_layout, team_list);
                arrayAdapter_team.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                team_spinner.setAdapter(arrayAdapter_team);

                JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, Department_url+selected_dept_id
                        , null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                json_team_id = jsonObject.getString("identity");
                                json_team = jsonObject.getString("team");
                                team_list.add(json_team);
                                arrayAdapter_team.notifyDataSetChanged();

                                team_pojo teamJson = new team_pojo();
                                teamJson.setId(json_team_id);
                                teamJson.setTeam(json_team);
                                team_pojo_spinner_data.add(teamJson);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("error", "error from server response");
                    }
                });
                getRequestQueue_team.add(jsonObjectRequest1);

                Toast.makeText(getApplicationContext(),"method called  " +selected_dept_id,Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"method called  " +Department_url+selected_dept_id,Toast.LENGTH_LONG).show();

                break;
                case R.id.xml_team_spinner :

                selected_team_id= team_pojo_spinner_data.get(position).getId();
                Toast.makeText(getApplicationContext(),"team called  " +selected_team_id,Toast.LENGTH_LONG).show();

                break;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    /*
  * =============================================
  *   Converts the Image into Base64 String
  * =============================================
    */
    public String getStringImage(Bitmap bmp){
    if(bmp !=null)
       {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
       encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
        }
        else
    {
        encodedImage="";
    }
    return encodedImage;
  }

    /*
   * =============================================
   * finish Activity on back button
   * =============================================
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
            Intent intent =new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
