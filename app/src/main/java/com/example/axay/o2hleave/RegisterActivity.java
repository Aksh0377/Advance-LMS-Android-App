package com.example.axay.o2hleave;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.MenuItem;
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
}

    private void submitForm() {
        //first validate the form then move ahead
        //if this becomes true that means validation is successfull
        if (awesomeValidation.validate()) {
            Toast.makeText(this, "Registration Successfull", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(RegisterActivity.this,Main3Activity.class);
            startActivity(intent);
            finish();
            //process the data further
        }
    }




    public void onClick(View view) {
        if (view == register) {


            submitForm();

            SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences("UserData",MODE_PRIVATE);
            SharedPreferences.Editor editor =sharedPreferences.edit();
            editor.putString("name",full_name.getText().toString());
            editor.putString("e_mail",email.getText().toString());
            editor.putString("password",password.getText().toString());
            editor.putString("employee_code",employee_code.getText().toString());
            editor.putString("desig.",designation.getText().toString());
            editor.commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
