package com.example.axay.o2hleave;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

public class Main2Activity extends Fragment {

  TextView cl,sl,pl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main2, container, false);
        cl=(TextView)getActivity().findViewById(R.id.cl);
        sl=(TextView)getActivity().findViewById(R.id.sl);
        pl=(TextView)getActivity().findViewById(R.id.pl);

        return rootView;

    }

    public void onClick(View v) {
        if (v.getId() == R.id.cl) {
           /* Intent intent = new Intent(getActivity(), ApplyLeave.class);
            int leave = 0;
            intent.putExtra("leave", leave);
            startActivity(intent);*/



        }else if (v.getId() == R.id.pl) {
           /* Intent intent = new Intent(getActivity(), ApplyLeave.class);
            int leave = 1;
            intent.putExtra("leave", leave);
            startActivity(intent);*/


        }
        else if (v.getId() == R.id.sl) {
           /* Intent intent = new Intent(getActivity(), ApplyLeave.class);
            int leave = 2;
            intent.putExtra("leave", leave);

            startActivity(intent);*/
        }

            else if(v.getId()==R.id.edit_menu)
            {
                Intent intent = new Intent(getActivity(), ApplyLeave.class);

                startActivity(intent);
            }
        }



    public void Onclicknav()
    {
        Intent intent = new Intent(getActivity(), ApplyLeave.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }



}