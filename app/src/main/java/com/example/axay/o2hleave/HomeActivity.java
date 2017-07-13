package com.example.axay.o2hleave;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    Button signin;
    Button signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
                 signUp=(Button)findViewById(R.id.home_register);
                signin=(Button)findViewById(R.id.home_login);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Onclicklogin();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnclickRegister();
            }
        });

    }


    public  void Onclicklogin()
    {
        Intent intent=new Intent(HomeActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
    public  void OnclickRegister()
    {
        Intent intent=new Intent(HomeActivity.this,SelfieActivity.class);
        startActivity(intent);
        finish();
    }
}
