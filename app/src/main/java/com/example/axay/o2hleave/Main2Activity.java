package com.example.axay.o2hleave;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity  implements Profile_fragment.OnFragmentInteractionListener,Recepients_list_fragment.OnFragmentInteractionListener {
    BottomNavigationView bottomNavigationView;
  TextView cl,sl,pl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        cl = (TextView) findViewById(R.id.cl);
        pl = (TextView) findViewById(R.id.pl);
        sl = (TextView) findViewById(R.id.sl);






        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nevigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               if (item.getItemId() == R.id.home_menu) {
                   Intent intent=new Intent(Main2Activity.this,Main2Activity.class);
                   startActivity(intent);

                } else if (item.getItemId() == R.id.edit_menu) {
                   Onclicknav();
                } else if (item.getItemId() == R.id.settings_menu) {
                   Intent intent = new Intent(Main2Activity.this, Settings.class);
                   startActivity(intent);
                }

                return false;
            }
        });


    }

    public void onClick(View v) {
        if (v.getId() == R.id.cl) {
            Intent intent = new Intent(Main2Activity.this, ApplyLeave.class);
            int leave = 0;
            intent.putExtra("leave", leave);
            startActivity(intent);



        }else if (v.getId() == R.id.pl) {
            Intent intent = new Intent(Main2Activity.this, ApplyLeave.class);
            int leave = 1;
            intent.putExtra("leave", leave);
            startActivity(intent);


        }
        else if (v.getId() == R.id.sl) {
            Intent intent = new Intent(Main2Activity.this, ApplyLeave.class);
            int leave = 2;
            intent.putExtra("leave", leave);

            startActivity(intent);
        }

            else if(v.getId()==R.id.edit_menu)
            {
                Intent intent = new Intent(Main2Activity.this, ApplyLeave.class);

                startActivity(intent);
            }
        }



    public void Onclicknav()
    {
        Intent intent = new Intent(Main2Activity.this, ApplyLeave.class);startActivity(intent);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}