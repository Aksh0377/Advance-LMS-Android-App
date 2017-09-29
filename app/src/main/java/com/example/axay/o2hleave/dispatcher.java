package com.example.axay.o2hleave;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by axay on 18/09/17.
 */

public class dispatcher extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Class<?> activityClass;

        try {
            SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
            activityClass = Class.forName(
                    prefs.getString("lastActivity", MainActivity.class.getName()));
        } catch (ClassNotFoundException ex) {
            activityClass = MainActivity.class;
        }

        startActivity(new Intent(this, activityClass));
    }
}