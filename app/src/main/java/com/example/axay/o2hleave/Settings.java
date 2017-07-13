package com.example.axay.o2hleave;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Settings extends AppCompatActivity {
    TextView fullname,email;
    Button edit_profile;
    Button leave_history,edit_recepients,logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        fullname=(TextView)findViewById(R.id.settings_name);
        email=(TextView)findViewById(R.id.settings_email);
        edit_profile=(Button)findViewById(R.id.edit_profile);
        leave_history=(Button)findViewById(R.id.view_leave_history);
        edit_recepients=(Button)findViewById(R.id.edit_recepients);
        logout=(Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Onclicklogout();
            }
        });

           leave_history.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   OnclickLeaveHistory();
               }
           });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_seetings);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.o2htextOne));
        final Drawable upArrow = getResources().getDrawable(R.drawable.back_arrow);
        upArrow.setColorFilter(getResources().getColor(R.color.o2htextOne), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Onclick();
            }
        });
        Bitmap bitmap= (Bitmap)this.getIntent().getParcelableExtra("photo");
        ImageView setting=(ImageView)findViewById(R.id.settings_profile_pic);
        setting.setImageBitmap(bitmap);



        //RECYCLER VIEW ON CLICK

        final FragmentManager fm=getFragmentManager();
        final  edit_recepients er=new edit_recepients();



         edit_recepients.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 er.show(fm,"TV_tag");
             }
         });
    }

    public  void Onclick()
    {
        Intent intent=new Intent(Settings.this,profileAcivity.class);
        startActivity(intent);
    }
    public  void Onclicklogout()
    {
        Intent intent=new Intent(Settings.this,LoginActivity.class);
        startActivity(intent);
    }
    public  void OnclickLeaveHistory()
    {
        Intent intent=new Intent(Settings.this,LeaveHistory.class);
        startActivity(intent);
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
