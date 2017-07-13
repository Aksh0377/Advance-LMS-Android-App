package com.example.axay.o2hleave;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LeaveHistory extends AppCompatActivity {
     RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<hisoryItem> listitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_history);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        listitems= new ArrayList<>();
        for(int i=0;i<=10;i++)
        {
            hisoryItem listitem = new hisoryItem("heading"+i+1,"lorem ispum tem text");
            listitems.add(listitem);


        }

        adapter =new HistoryAdapter(listitems,this);
        recyclerView.setAdapter(adapter);
    }
}
