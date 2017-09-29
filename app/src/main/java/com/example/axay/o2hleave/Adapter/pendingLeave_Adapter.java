package com.example.axay.o2hleave.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.axay.o2hleave.R;
import com.example.axay.o2hleave.model.pendingLeave_pojo;

import java.util.List;

/**
 * Created by axay on 25/08/17.
 */

public class pendingLeave_Adapter extends RecyclerView.Adapter<pendingLeave_Adapter.ViewHolder> {

    List<pendingLeave_pojo> data;
    Context context;

    public pendingLeave_Adapter(List<pendingLeave_pojo> data, Context context) {
        this.data = data;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pendingleave_recyclerview_row_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        pendingLeave_pojo datas =data.get(position);

        holder.pending_leave_type.setText(datas.getType());
        holder.pending_leave_reason.setText(datas.getLeave_reason());
        holder.pending_leave_fromDate.setText(datas.getFrom());
        holder.pending_leave_toDate.setText("- "+datas.getTo());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView pending_leave_type;
        public TextView pending_leave_reason;
        public TextView pending_leave_fromDate;
        public TextView pending_leave_toDate;

        public ViewHolder(View itemView) {
            super(itemView);
            pending_leave_type=(TextView)itemView.findViewById(R.id.pendingLeave_type);
            pending_leave_reason=(TextView)itemView.findViewById(R.id.pendingLave_reason);
            pending_leave_fromDate=(TextView)itemView.findViewById(R.id.pendingLave_from_date);
            pending_leave_toDate=(TextView)itemView.findViewById(R.id.pendinglave_to_date);

        }
    }
}
