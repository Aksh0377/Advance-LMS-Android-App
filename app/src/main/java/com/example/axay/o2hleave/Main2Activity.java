package com.example.axay.o2hleave;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.example.axay.o2hleave.Adapter.leave_request_Adapter;
import com.example.axay.o2hleave.Adapter.pendingLeave_Adapter;
import com.example.axay.o2hleave.model.leave_request_pojo;
import com.example.axay.o2hleave.model.pendingLeave_pojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Main2Activity extends Fragment  implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener{
    TextView cl, sl, pl;
    TextView no_pending_leaves, no_leave_req, no_history;
    RecyclerView recyclerView_pending_request, recyclerView_leave_request;
    RequestQueue requestQueue, requestQueue1, requestqueue_leave_count;
    private leave_request_Adapter adapter;
    pendingLeave_Adapter adapter1;
    private List<leave_request_pojo.DataBeanXX.LeaveBean.DataBeanX> data;
    private List<pendingLeave_pojo> pendingLeave_data;
    Context context;
    Bundle USer_id;
    String s;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_main2, container, false);


        cl = (TextView) rootView.findViewById(R.id.cl);
        sl = (TextView) rootView.findViewById(R.id.sl);
        pl = (TextView) rootView.findViewById(R.id.pl);
        no_pending_leaves = (TextView) rootView.findViewById(R.id.no_pending_leaves);
        no_leave_req = (TextView) rootView.findViewById(R.id.no_leave_request);
        requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue1 = Volley.newRequestQueue(getActivity());
        requestqueue_leave_count = Volley.newRequestQueue(getActivity());
        recyclerView_pending_request = (RecyclerView) rootView.findViewById(R.id.recyclerView_PendingLeaves);
        recyclerView_leave_request = (RecyclerView) rootView.findViewById(R.id.recyclerView_LeaveRequests);
        recyclerView_pending_request.setHasFixedSize(true);
        recyclerView_pending_request.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView_leave_request.setHasFixedSize(true);
        recyclerView_leave_request.setLayoutManager(new LinearLayoutManager(getActivity()));
        data = new ArrayList<>();
        pendingLeave_data = new ArrayList<>();




       s= getActivity().getIntent().getStringExtra("User_id");

        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("userID",MODE_PRIVATE);
        s= sharedPreferences.getString("id","null");

        //getting user_id from login Activity
//        USer_id = getActivity().getIntent().getExtras();
//        s = USer_id.getString("User_id");
        // Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
        //getting user_id from login Activity ends here





        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ApplyLeave.class);
                int leave = 0;
                intent.putExtra("leave", leave);
                intent.putExtra("Class","B");
                startActivity(intent);
            }
        });
        pl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ApplyLeave.class);
                int leave = 1;
                intent.putExtra("leave", leave);
                intent.putExtra("Class","B");
                startActivity(intent);
            }
        });
        sl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ApplyLeave.class);
                int leave = 2;
                intent.putExtra("leave", leave);
                intent.putExtra("Class","B");
                startActivity(intent);
            }
        });





        /*
        =========================================
        Volley Request -Shows pending Leaves
        =========================================
        */
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Data");
        progressDialog.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://192.168.1.104/leave/public/api/leave/pending/" + s
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject dataa = jsonArray.getJSONObject(i);
                        pendingLeave_pojo bean = new pendingLeave_pojo(
                                dataa.getString("type"),
                                dataa.getString("leave_reason"),
                                dataa.getString("from"),
                                dataa.getString("to"));
                        pendingLeave_data.add(bean);
                    }

                    adapter1 = new pendingLeave_Adapter(pendingLeave_data, getActivity());
                    recyclerView_pending_request.setAdapter(adapter1);
                    adapter1.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getActivity(), "Data fetched successfully", Toast.LENGTH_LONG);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                no_pending_leaves.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "sorry no data fetched", Toast.LENGTH_LONG);
                Log.d("error", "error from server response");
            }
        });
        requestQueue1.add(jsonObjectRequest);

         /*
        =========================================
        Volley Request -Shows Leave Requests
        =========================================
        */
        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, "http://192.168.1.104/leave/public/api/leaveusers/tag/" + s
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject leave = jsonArray.getJSONObject(i);
                        JSONObject leave1 = leave.getJSONObject("leave");
                        JSONArray data1 = leave1.getJSONArray("data");
                        JSONObject dataa = data1.getJSONObject(0);
                        JSONObject users = dataa.getJSONObject("users");
                        JSONObject dat = users.getJSONObject("data");

                        leave_request_pojo.DataBeanXX.LeaveBean.DataBeanX bean1 = new leave_request_pojo.DataBeanXX.LeaveBean.DataBeanX(
                                dataa.getJSONObject("users").getJSONObject("data").getString("first_name"),
                                dataa.getString("identity"),
                                dataa.getString("type"),
                                dataa.getString("leave_reason"),
                                dataa.getString("from"),
                                dataa.getString("to"),
                                dataa.getJSONObject("users").getJSONObject("data").getString("avatar")
                        );
                        data.add(bean1);
                    }
                    adapter = new leave_request_Adapter(data, getActivity());
                    recyclerView_leave_request.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getActivity(), "Data fetched successfully", Toast.LENGTH_LONG);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                no_leave_req.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "sorry no data fetched", Toast.LENGTH_LONG);
                Log.d("error", "error from server response");
            }
        });
        requestQueue.add(jsonObjectRequest1);

         /*
        =========================================
        Volley Request -Shows Leave Count
        =========================================
        */
        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, "http://192.168.1.104/leave/public/api/userleaves/" + s
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    JSONObject dataa = jsonArray.getJSONObject(0);

                    String casual_leave = dataa.getString("casual_leave");
                    cl.setText(casual_leave);
                    String sick_leave = dataa.getString("sick_leave");
                    String personal_leave = dataa.getString("persoal_leave");
                    cl.setText(casual_leave);
                    pl.setText(personal_leave);
                    sl.setText(sick_leave);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getActivity(), "Data fetched successfully", Toast.LENGTH_LONG);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "sorry no data fetched", Toast.LENGTH_LONG);
                Log.d("error", "error from server response");
            }
        });
        requestqueue_leave_count.add(jsonObjectRequest2);


        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putString("id", s);
        editor.commit();
        return rootView;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d("tap", "onDoubleTap: " + e.toString());
        Toast.makeText(getActivity(), "OnDoubleTap" + e.toString(), Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Toast.makeText(getActivity(), "OnDoubleTap" + e.toString(), Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    public void onClick_leave(View v) {

    }

    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences prefs = getActivity().getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
    }




}




