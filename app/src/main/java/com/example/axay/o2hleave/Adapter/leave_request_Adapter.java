package com.example.axay.o2hleave.Adapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.axay.o2hleave.Main2Activity;
import com.example.axay.o2hleave.R;
import com.example.axay.o2hleave.model.leave_request_pojo;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by axay on 31/08/17.
 */

public class leave_request_Adapter extends RecyclerView.Adapter<leave_request_Adapter.ViewHolder> {

    List<leave_request_pojo.DataBeanXX.LeaveBean.DataBeanX> leave_request;
    Context context;
    String accept="Approve";
    String reject="reject";
    String s,sr;
    String ID;
    Main2Activity main =new Main2Activity();
    RequestQueue requestQueue_accept;
    String reject_reason;




    public leave_request_Adapter(List<leave_request_pojo.DataBeanXX.LeaveBean.DataBeanX> leave_request, Context context) {
        this.leave_request = leave_request;
        this.context = context;
    }

    @Override
    public leave_request_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaverequests_recyclerview_row_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(leave_request_Adapter.ViewHolder holder, final int position) {
        final leave_request_pojo.DataBeanXX.LeaveBean.DataBeanX datas =leave_request.get(position);
        Picasso.with(context).load(datas.getUsers()).into(holder.applier_profile_pic);
         holder.requested_leave_applier.setText(datas.getFirst_name());
        holder.requested_leave_type.setText(datas.getType());
        holder.requested_leave_reason.setText(datas.getLeave_reason());
        holder.requested_leave_fromDate.setText(datas.getFrom());
        holder.requested_leave_toDate.setText("- "+datas.getTo());
        holder.req_leave_id.setText(datas.getIdentity());
        requestQueue_accept=Volley.newRequestQueue(context);

        SharedPreferences sharedPreferences=context.getSharedPreferences("UserData",MODE_PRIVATE);
        ID= sharedPreferences.getString("id","null");

        Toast.makeText(context,"your dat"+ID,Toast.LENGTH_SHORT).show();

        s= datas.getIdentity().toString();
//        String logged_user= main2Activity.getbs();
//        Toast.makeText(context,"your id"+logged_user,Toast.LENGTH_SHORT).show();

        holder.reject_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,"Leave reject",Toast.LENGTH_SHORT).show();
                /* Alert Dialog Code Start*/
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Reason to Reject leave"); //Set Alert dialog title here
                alert.setMessage("Reason"); //Message here

                // Set an EditText view to get user input
                final EditText input = new EditText(context);
                alert.setView(input);

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //You will get as string input data in this variable.
                        // here we convert the input to a string and show in a toast.
                        reject_reason = input.getEditableText().toString();
                        Toast.makeText(context,reject_reason,Toast.LENGTH_LONG).show();
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("users_id", ID);
                        params.put("reject_reason", reject_reason);
                        params.put("status", reject);

                        JSONObject json = new JSONObject(params);
                        System.out.println(json);
                        Log.d("error", json.toString());


                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, "http://192.168.1.104/leave/public/api/leaveusers/"+datas.getIdentity()
                                , json, new Response.Listener<JSONObject>() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    // JSONArray jsonArray=new JSONArray(response);
                                    Log.d("ds", response.toString());
                                    Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
                                    Toast.makeText(context,datas.getIdentity(),Toast.LENGTH_SHORT).show();

                                    // JSONObject jsonObject=jsonArray.getJSONObject(0);
                                    //String Code= jsonObject.getString("code");
                                    //String message= jsonObject.getString("message");
                                    //Toast.makeText(Register.this,Code,Toast.LENGTH_LONG).show();


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
                                Toast.makeText(context, "http://192.168.1.104/leave/public/api/leaveusers/"+datas.getIdentity(), Toast.LENGTH_LONG).show();


                                Log.d("error", "error");

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                                Log.d("error", "error from server response");
                            }
                        });


                        requestQueue_accept.add(jsonObjectRequest);

                        removeItem(position);
                    } // End of onClick(DialogInterface dialog, int whichButton)
                }); //End of alert.setPositiveButton
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                        dialog.cancel();
                    }
                }); //End of alert.setNegativeButton
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
       /* Alert Dialog Code End*/

       //Json request to reject leave



            }
        });
        holder.accept_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Toast.makeText(context,"weldone",Toast.LENGTH_SHORT).show();


                Map<String, String> params = new HashMap<String, String>();
                params.put("users_id", ID);
                params.put("reject_reason", "");
                params.put("status", accept);

                JSONObject json = new JSONObject(params);
                System.out.println(json);
                Log.d("error", json.toString());


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, "http://192.168.1.104/leave/public/api/leaveusers/"+s
                        , json, new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            // JSONArray jsonArray=new JSONArray(response);
                            Log.d("ds", response.toString());
                            Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
                            // JSONObject jsonObject=jsonArray.getJSONObject(0);
                            //String Code= jsonObject.getString("code");
                            //String message= jsonObject.getString("message");
                            //Toast.makeText(Register.this,Code,Toast.LENGTH_LONG).show();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();

                        Log.d("error", "error");

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                        Log.d("error", "error from server response");
                    }
                });


                requestQueue_accept.add(jsonObjectRequest);

                removeItem(position);

            }
        });


    }

    @Override
    public int getItemCount() {
        return leave_request.size();
    }

    public void addItem(String country) {
       // leave_request.add(country);
        notifyItemInserted(leave_request.size());
    }

    public void removeItem(int position) {
        leave_request.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, leave_request.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView requested_leave_applier;
        private TextView requested_leave_type;
        public TextView requested_leave_reason;
        public TextView requested_leave_fromDate;
        public TextView requested_leave_toDate;
        public TextView req_leave_id;
        public TextView logged_user_id;

        public ImageView applier_profile_pic;
        public Button accept_leave,reject_leave;
        RequestQueue requestQueue_accept_leave;

        Main2Activity main1 =new Main2Activity();



        String user_id;
        Bundle bundle_user_id;




        public ViewHolder(View itemView) {
            super(itemView);
            applier_profile_pic=(ImageView)itemView.findViewById(R.id.applier_pic);
            requested_leave_applier=(TextView)itemView.findViewById(R.id.xml_requested_leave_applier);
            requested_leave_type=(TextView)itemView.findViewById(R.id.xml_requested_leave_type);
            requested_leave_reason=(TextView)itemView.findViewById(R.id.xml_requested_leave_reason);
            requested_leave_fromDate=(TextView)itemView.findViewById(R.id.xml_requested_leave_from_date);
            requested_leave_toDate=(TextView)itemView.findViewById(R.id.xml_requested_leave_to_date);
            req_leave_id=(TextView)itemView.findViewById(R.id.requested_leave_id);
            logged_user_id=(TextView)itemView.findViewById(R.id.logged_in_user_id);

            accept_leave=(Button)itemView.findViewById(R.id.xml_accept_leave);
            reject_leave=(Button)itemView.findViewById(R.id.xml_reject_leave);
            requestQueue_accept_leave= Volley.newRequestQueue(context);




        }





    }

}
