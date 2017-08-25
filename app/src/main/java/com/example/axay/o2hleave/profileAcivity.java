package com.example.axay.o2hleave;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
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
import android.widget.ImageButton;
import android.widget.ImageView;
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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class profileAcivity extends AppCompatActivity  implements  Spinner.OnItemSelectedListener {


    public String Department_url = "http://192.168.1.104/leave/public/api/department/";
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
    String selected_dept_id = null;
    String selected_team_id;
    Bitmap bitmap;
    Bitmap bitmap1;


    //profile data
    String profile_id = null;
    String profile_name = null;
    String profile_email = null;
    String profile_designation = null;
    String profile_avtar = null;
    String profile_employee_code = null;


    //Volley Request
    RequestQueue requestQueue_update, requestQueue_get_dept, requestQueue_get_team, requestQueue_getprofile;

    ImageView profile_pic;
    ImageButton selectImage;

    private static EditText name, email, employee_code, designation, password;
    Spinner spinner_department, spinner_team;
    Button update_profile;
    private int Request_code = 1;

    ArrayList<department_pojo> department_pojo_spinner_data;
    ArrayList<team_pojo> team_pojo_spinner_data;
    ArrayList<String> deaprtment_list;
    ArrayList<String> team_list;
    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> arrayAdapter_team;
    profile_pojo.DataBean p_pojo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_acivity);

        //progress dialoge
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Departments");
        progressDialog.show();

        final ProgressDialog progressDialog1 = new ProgressDialog(this);
        progressDialog1.setMessage("Loading data");
        progressDialog1.show();

        selectImage = (ImageButton) findViewById(R.id.select_profile);
        Bitmap bitmap = (Bitmap) this.getIntent().getParcelableExtra("photo");
        profile_pic = (ImageView) findViewById(R.id.profile_picture);
        profile_pic.setImageBitmap(bitmap1);


          p_pojo = new profile_pojo.DataBean();


        department_pojo_spinner_data = new ArrayList<>();
        team_pojo_spinner_data = new ArrayList<>();
        deaprtment_list = new ArrayList<>();
        team_list = new ArrayList<>();
        name = (EditText) findViewById(R.id.edit_ful_name);
        email = (EditText) findViewById(R.id.edit_email);
        employee_code = (EditText) findViewById(R.id.edit_employee_code);
        designation = (EditText) findViewById(R.id.edit_designation);
        password = (EditText) findViewById(R.id.edit_password);
        spinner_department = (Spinner) findViewById(R.id.edit_spinner_dept);
        spinner_team = (Spinner) findViewById(R.id.edit_spinner_team);
        update_profile = (Button) findViewById(R.id.update);
        requestQueue_update = Volley.newRequestQueue(this);
        requestQueue_get_dept = Volley.newRequestQueue(this);
        requestQueue_get_team = Volley.newRequestQueue(this);
        requestQueue_getprofile = Volley.newRequestQueue(this);


        spinner_department.setOnItemSelectedListener(this);
        spinner_team.setOnItemSelectedListener(this);


        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Update_profile();

            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.o2htextOne));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = getResources().getDrawable(R.drawable.back_arrow);
        upArrow.setColorFilter(getResources().getColor(R.color.o2htextOne), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }


        //selecting image from the gallery

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "select_picture"), Request_code);
            }
        });





        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, "http://192.168.1.104/leave/public/api/users/1"
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog1.dismiss();


                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                        JSONObject jsonObject = jsonArray.getJSONObject(0);


                        profile_id = jsonObject.getString("identity");
                        profile_name = jsonObject.getString("first_name").toString();
                        name.setText(profile_name);
                        profile_email = jsonObject.getString("email").toString();
                        email.setText(profile_email);
                        profile_designation = jsonObject.getString("user_designation").toString();
                        designation.setText(profile_designation);
                        String base64Content = jsonObject.getString("avatar");
                        byte[] bytes = Base64.decode(base64Content, Base64.DEFAULT);
                        bitmap1 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                       profile_pic.setImageBitmap(bitmap1);
                        profile_employee_code = jsonObject.getString("employee_code").toString();
                       employee_code.setText(profile_employee_code);

                        Toast.makeText(getApplicationContext(), profile_name, Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("error", "error from server response");
            }
        }

        );


        requestQueue_getprofile.add(jsonObjectRequest1);

        //get json data into spinner
        arrayAdapter = new ArrayAdapter<String>(profileAcivity.this,
                R.layout.spinner_dept_layout,
                deaprtment_list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner_department.setAdapter(arrayAdapter);
        int default_sel = 0;
        //department_spinner.setSelection(Integer.parseInt(selected_dept_id));





        //Volley request to get departments
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


                        //textView.append( id+" "+ first_name + " " + last_name + "\n");

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
        }

        );


        requestQueue_get_dept.add(jsonObjectRequest);




    //Volley get request to get data of logged in user


    //set the fetched data into views

}








    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Request_code && resultCode == RESULT_OK && data != null  && data.getData() != null) {
            Uri uri = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver() ,uri);
                profile_pic.setImageBitmap(bitmap);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    // this method will call when user click update profile button.
    public void Update_profile()
    {
        final String first_name = name.getText().toString().trim();
        final String user_password = password.getText().toString().trim();
        final String e_mail = email.getText().toString().trim();
        final String user_designation = designation.getText().toString().trim();


        String avatar=null;

        if(avatar!=null)
        {
            avatar = getStringImage(bitmap);

        }
        else
        {

            avatar="";
        }

        final String department = selected_dept_id;
        final String team = selected_team_id;

        final String emp_code =employee_code.getText().toString().trim();



        Map<String, String> params = new HashMap<String, String>();
        params.put(KEY_USERNAME, first_name);
        params.put(KEY_PASSWORD, user_password);
        params.put(KEY_EMAIL, e_mail);
        params.put(KEY_DESIGNATION, user_designation);
        params.put(AVTAR, avatar);
        params.put(KEY_TEAM, team);
        params.put(KEY_DEPARTMENT, department);
        params.put(EMPLOYEE_CODE, emp_code);
        JSONObject json = new JSONObject(params);
        System.out.println(json);
        Log.d("error", json.toString());


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, "http://192.168.1.104/leave/public/api/users/1"
                , json, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject response) {

                try {
                    // JSONArray jsonArray=new JSONArray(response);
                    Log.d("ds", response.toString());
                    Toast.makeText(profileAcivity.this, response.toString(), Toast.LENGTH_LONG).show();
                    // JSONObject jsonObject=jsonArray.getJSONObject(0);
                    //String Code= jsonObject.getString("code");
                    //String message= jsonObject.getString("message");
                    //Toast.makeText(Register.this,Code,Toast.LENGTH_LONG).show();


                } catch (Exception e) {
                    e.printStackTrace();
                }

                Toast.makeText(profileAcivity.this, response.toString(), Toast.LENGTH_LONG).show();
                Intent intent=new Intent(profileAcivity.this,Main3Activity.class);
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


        requestQueue_update.add(jsonObjectRequest);


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        final ProgressDialog progressDialog1= new ProgressDialog(this);
        //progressDialog1.setMessage("Loading Team");
        // progressDialog1.show();



        switch(parent.getId()){
            case R.id.edit_spinner_dept :


                selected_dept_id =department_pojo_spinner_data.get(position).getId();
                arrayAdapter_team = new ArrayAdapter<String>(profileAcivity.this,
                        R.layout.spinner_team_layout,
                        team_list);
                arrayAdapter_team.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spinner_team.setAdapter(arrayAdapter_team);

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



                                //textView.append( id+" "+ first_name + " " + last_name + "\n");

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
                }

                );


                requestQueue_get_team.add(jsonObjectRequest1);






                Toast.makeText(getApplicationContext(),"method called  " +selected_dept_id,Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"method called  " +Department_url+selected_dept_id,Toast.LENGTH_LONG).show();

                break;
            case R.id.edit_spinner_team :


                selected_team_id= team_pojo_spinner_data.get(position).getId();
                Toast.makeText(getApplicationContext(),"team called  " +selected_team_id,Toast.LENGTH_LONG).show();


                break;
        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //convert the image into string Base64
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

}

