package com.example.axay.o2hleave;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    public String login_url ="http://192.168.1.104/leave/public/api/login";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "user_password";
    Button signin;
    Button gotoregister;
    Button forgot_password;
    EditText email, password;
    RequestQueue requestQueue_login;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            // Activity was brought to front and not created,
            // Thus finishing this will get us to the last viewed activity
            finish();
            return;
        }

        signin = (Button) findViewById(R.id.sign_in);
        gotoregister = (Button) findViewById(R.id.GotoRegisterActivity);
        forgot_password = (Button) findViewById(R.id.ForgotPassword);
        email = (EditText) findViewById(R.id.etemail);
        password = (EditText) findViewById(R.id.etpassword);
        requestQueue_login=  Volley.newRequestQueue(this);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Onclicksignin();
            }
        });
        gotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnclickgotoRegister();
            }
        });
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Onclickforgotpassword();
            }
        });

        //shared prefrence
        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("UserData",MODE_PRIVATE);
        String Email= sharedPreferences.getString("e_mail","null");
        String pass= sharedPreferences.getString("password","null");
        if(Email.equals("null") ||pass.equals("null") ) {
            Toast.makeText(getApplicationContext(),"notfoumd",Toast.LENGTH_LONG).show();
        }
        else  {
            email.setText(Email);
            password.setText(pass);
        }

        if(! Email.equals("null")  ) {

            SharedPreferences sp_get = getApplicationContext().getSharedPreferences("credentils", MODE_PRIVATE); //Storing recenlty entered values
            String input_email = sp_get.getString("input_email", "null");
            String input_password = sp_get.getString("input_password", "null");


            email.setText(input_email);
            password.setText(input_password);

        }




    }

    public void Onclicksignin() {
        Map<String, String> params = new HashMap<String, String>();
        params.put(KEY_EMAIL, email.getText().toString());
        params.put(KEY_PASSWORD, password.getText().toString());
        JSONObject json = new JSONObject(params);
        System.out.println(json);
        Log.d("error", json.toString());
        final ProgressDialog progressDialog=new ProgressDialog(this); //progressbar
        progressDialog.setMessage("Authenticating");
        progressDialog.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, login_url
                , json, new Response.Listener<JSONObject>() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    JSONObject json2 = response.getJSONObject("data");
                    id = json2.getString("identity");
                    String name = json2.getString("identity");
                    Log.d("ds", response.toString());
                    Log.d("ds", name);
                    System.out.print(name);
                   // Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(LoginActivity.this, name, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                Intent intent=new Intent(LoginActivity.this,Main3Activity.class);
                intent.putExtra("User_id",id);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

                SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences("userID",MODE_PRIVATE);
                SharedPreferences.Editor editor =sharedPreferences.edit();
                editor.putString("id",id);
                editor.commit();
                Log.d("error", "error");

            }
        }, new Response.ErrorListener() {
            @Override


            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.d("error", "error from server response");
            }
        }
        );
        requestQueue_login.add(jsonObjectRequest);



        SharedPreferences sp =getApplicationContext().getSharedPreferences("credentils",MODE_PRIVATE);
        SharedPreferences.Editor sp_editor = sp.edit();
        sp_editor.putString("input_email",email.getText().toString());
        sp_editor.putString("input_password",password.getText().toString());
        sp_editor.commit();





    }

    public void OnclickgotoRegister() {
        Intent intent = new Intent(LoginActivity.this, SelfieActivity.class);
        startActivity(intent);

    }

    public void Onclickforgotpassword() {
        Intent intent = new Intent(LoginActivity.this, forgot_password.class);
        startActivity(intent);
    }

}