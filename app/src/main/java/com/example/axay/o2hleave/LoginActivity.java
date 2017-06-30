package com.example.axay.o2hleave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    Button signin;
    Button gotoregister;
    Button forgot_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signin=(Button)findViewById(R.id.sign_in);
        gotoregister=(Button)findViewById(R.id.GotoRegisterActivity);
        forgot_password=(Button)findViewById(R.id.ForgotPassword);

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

    }

    public void Onclicksignin()
    {Intent intent=new Intent(LoginActivity.this,Main2Activity.class);
        startActivity(intent);
    }
    public void OnclickgotoRegister()
    {Intent intent=new Intent(LoginActivity.this,SelfieActivity.class);
        startActivity(intent);
    }
    public void Onclickforgotpassword()
    {Intent intent=new Intent(LoginActivity.this,forgot_password.class);
        startActivity(intent);
    }
}
