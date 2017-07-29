package com.example.axay.o2hleave;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class LeaveHistory extends Fragment {

    RecyclerView recyclerView;
    RequestQueue requestQueue;
    private RecyclerView.Adapter adapter;
    private List<LeaveHistory_pojo.DataBean>  data;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_leave_history, container, false);
        requestQueue= Volley.newRequestQueue(getActivity());

        recyclerView=(RecyclerView)rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        data=new ArrayList<>();

        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Data");
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://192.168.1.104/leave/public/api/leave/5"
                ,null,  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();

                try {
                    JSONArray jsonArray = response.getJSONArray("data");


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject dataa = jsonArray.getJSONObject(i);

                        LeaveHistory_pojo.DataBean bean = new LeaveHistory_pojo.DataBean(

                                dataa.getString("type"),
                                dataa.getString("leave_reason"),
                                dataa.getString("from"),
                                dataa.getString("to"));


                        data.add(bean);
                    }

                    adapter= new LeaveHistory_Adapter(data,getActivity());
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getActivity(),"Data fetched successfully",Toast.LENGTH_LONG);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"sorry no data fetched",Toast.LENGTH_LONG);

                Log.d("error", "error from server response");
            }}

        );


        requestQueue.add(jsonObjectRequest);

        return rootView;
    }

}
