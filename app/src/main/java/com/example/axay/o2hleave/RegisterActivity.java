package com.example.axay.o2hleave;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText full_name, email, password,employee_code,designation;
Button register;
    private AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        full_name=(EditText)findViewById(R.id.fullname);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        employee_code=(EditText)findViewById(R.id.employee_code);
        designation=(EditText)findViewById(R.id.designation);


        register=(Button)findViewById(R.id.register);

        Bitmap bitmap= (Bitmap)this.getIntent().getParcelableExtra("photo");
        ImageView profile_pic=(ImageView)findViewById(R.id.register_profile);
        profile_pic.setImageBitmap(bitmap);


        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

     awesomeValidation.addValidation(this, R.id.fullname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS+"", R.string.emailerror);
       // awesomeValidation.addValidation(this, R.id.password, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.passworderror);
       // awesomeValidation.addValidation(this, R.id.employee_code, "^[2-9]{2}[0-9]{8}$", R.string.empoyeecodeerror);


        register.setOnClickListener( this);
}

    private void submitForm() {
        //first validate the form then move ahead
        //if this becomes true that means validation is successfull
        if (awesomeValidation.validate()) {
            Toast.makeText(this, "Registration Successfull", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(RegisterActivity.this,Main2Activity.class);
            startActivity(intent);
            //process the data further
        }
    }


    public void onClick(View view) {
        if (view == register) {
            submitForm();
        }
    }
}
