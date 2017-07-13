package com.example.axay.o2hleave;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button signin;
    Button gotoregister;
    Button forgot_password;
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signin = (Button) findViewById(R.id.sign_in);
        gotoregister = (Button) findViewById(R.id.GotoRegisterActivity);
        forgot_password = (Button) findViewById(R.id.ForgotPassword);
        email = (EditText) findViewById(R.id.etemail);
        password = (EditText) findViewById(R.id.etpassword);


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
        if(Email.equals("null") ||pass.equals("null") )
        {
            Toast.makeText(getApplicationContext(),"notfoumd",Toast.LENGTH_LONG).show();
        }
        else
        {
            email.setText(Email);
            password.setText(pass);
        }

    }

    public void Onclicksignin() {
        if (email.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
            Intent intent = new Intent(LoginActivity.this, Main3Activity.class);
            startActivity(intent);
            finish();
            //correcct password
        } else {
            Toast.makeText(getApplicationContext(), "wrong email/password", Toast.LENGTH_LONG).show();
        }
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