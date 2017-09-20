package com.example.axay.o2hleave;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
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
import com.example.axay.o2hleave.Adapter.LeaveHistory_Adapter;
import com.example.axay.o2hleave.model.LeaveHistory_pojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class LeaveHistory extends Fragment {

    RecyclerView recyclerView;
    RequestQueue requestQueue;
    TextView no_hostory;
    private RecyclerView.Adapter adapter;
    private List<LeaveHistory_pojo.DataBean>  data;
    Context context;
    Bundle USer_id;
    String s;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_leave_history, container, false);
        requestQueue= Volley.newRequestQueue(getActivity());
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recyclerView);
        no_hostory=(TextView)rootView.findViewById(R.id.no_leave_history);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        data=new ArrayList<>();

      /*  get USer_id from login activity
          USer_id=getActivity().getIntent().getExtras();
          s= USer_id.getString("User_id");
          Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
          get User_id ends here */



        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("userID",MODE_PRIVATE);
        s= sharedPreferences.getString("id","null");

        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Data");
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://192.168.1.104/leave/public/api/leave/"+s
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
                progressDialog.dismiss();
                no_hostory.setVisibility(View.VISIBLE);
                Log.d("error", "error from server response");
            }}
        );
        requestQueue.add(jsonObjectRequest);

        return rootView;
    }

}
