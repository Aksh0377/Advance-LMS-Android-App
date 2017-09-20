package com.example.axay.o2hleave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class forgot_password extends AppCompatActivity {
     TextView name;
     EditText email;
     Button send_req,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        name=(TextView)findViewById(R.id.forgotActivity_name);
        email=(EditText)findViewById(R.id.forgotActivity_email);
        send_req=(Button)findViewById(R.id.request_forgot_password);
        back=(Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Onclickback();
            }
        });
    }

      public void  Onclickback()
    {
        Intent intent=new Intent(forgot_password.this,LoginActivity.class);
        startActivity(intent);
    }
}
