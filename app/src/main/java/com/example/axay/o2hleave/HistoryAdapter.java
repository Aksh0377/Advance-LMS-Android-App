package com.example.axay.o2hleave;

import android.app.LauncherActivity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.common.io.LittleEndianDataOutputStream;

import java.util.List;

/**
 * Created by axay on 04/07/17.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    List<hisoryItem> listitems;
    Context context;

    public HistoryAdapter(List<hisoryItem> listitems, Context context) {
        this.listitems = listitems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_model,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        hisoryItem listItem =listitems.get(position);
        holder.Head.setText(listItem.getHeading());
        holder.Desc.setText(listItem.getDesc());



    }

    @Override
    public int getItemCount()
    {

        return listitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView Head;
        public TextView Desc;

        public ViewHolder(View itemView) {
            super(itemView);

            Head=(TextView)itemView.findViewById(R.id.heading);
            Desc=(TextView)itemView.findViewById(R.id.descripation);

        }
    }
}
