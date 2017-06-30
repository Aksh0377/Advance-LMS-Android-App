package com.example.axay.o2hleave.mrecycleView;

/**
 * Created by axay on 29/06/17.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.axay.o2hleave.R;


/**
 * Created by Oclemmy on 4/23/2016 for ProgrammingWizards Channel.
 */
public class MyHolder extends RecyclerView.ViewHolder {

    TextView nameTxt;

    public MyHolder(View itemView) {
        super(itemView);
        nameTxt= (TextView) itemView.findViewById(R.id.nameTxt);
    }
}