package com.example.axay.o2hleave.mrecycleView;

/**
 * Created by axay on 29/06/17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SimpleAdapter;

import com.example.axay.o2hleave.R;


/**
 * Created by Oclemmy on 4/23/2016 for ProgrammingWizards Channel.
 */
public class MyAdapter extends RecyclerView.Adapter<MyHolder> implements Filterable{

    Context c;
    String[] tvshows;

    public MyAdapter(Context c, String[] tvshows) {
        this.c = c;
        this.tvshows = tvshows;
    }

    //INITIALIE VH
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.model,parent,false);
        MyHolder holder=new MyHolder(v);
        return holder;
    }



    //BIND DATA
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.nameTxt.setText(tvshows[position]);
    }

    @Override
    public int getItemCount() {
        return tvshows.length;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}