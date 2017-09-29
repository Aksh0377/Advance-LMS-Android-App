package com.example.axay.o2hleave.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.Filterable;

import com.example.axay.o2hleave.R;
import com.example.axay.o2hleave.model.GetEmails_pojo;

import java.util.ArrayList;

/**
 * Created by axay on 31/07/17.
 */

public class GetEmail_Adapter  extends ArrayAdapter<GetEmails_pojo> implements Filterable {

    Context context;
    int layoutResourceId;
    ArrayList<GetEmails_pojo> email_pojo = null;

    public GetEmail_Adapter(Context context, int layoutResourceId, ArrayList<GetEmails_pojo> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.email_pojo = data;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        UserHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new UserHolder();
            holder.checkedTextView=(CheckedTextView)row.findViewById(R.id.checked_textview);

            row.setTag(holder);
        }
        else
        {
            holder = (UserHolder) row.getTag();
        }


        GetEmails_pojo get_mails = email_pojo.get(position);
        //Weather weather = data[position];
        holder.checkedTextView.setText(get_mails.getEmail().toString());
        // holder.imgIcon.setImageResource(weather.icon);

        return row;
    }




    static class UserHolder
    {
        CheckedTextView checkedTextView;
    }



    @Override
    public int getCount() {
        return email_pojo.size();
    }

    public void setFilter(ArrayList<GetEmails_pojo> newlist)
    {
        email_pojo=new ArrayList<>();
        email_pojo.addAll(newlist);
        notifyDataSetChanged();
    }

}