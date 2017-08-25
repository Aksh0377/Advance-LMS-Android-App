package com.example.axay.o2hleave;

/**
 * Created by axay on 29/06/17.
 */

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.axay.o2hleave.mrecycleView.MyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;


/**
 * created  by Axay soni
 *
 * In this we have used ListView.CHOICE_MODE_MULTIPLE in listview to select items rather than checkboxes.
 */
public class edit_recepients extends DialogFragment   {

    String id,email;

ArrayList<GetEmails_pojo> items;
    ArrayList<GetEmails_pojo> tempitem;

    GetEmail_Adapter adapter;
    RequestQueue requestQueue;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    Button Apply;
    Context context;
    String[] recepients = {"nitu@o2h.com","nilesh@o2h.com", "bhargavi@o2h.com", "krishana@o2h.com", "chris@visibly.io", "akshay@o2h.com", "sujith@02h.com", "yaamin@o2h.com", "juned@o2h.com", "vipul@o2h.com", "Archa@o2h.com"
            , "prashant@o2h.com", "neel@o2h.com", "sunil@o2h.com"};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fraglayout, container);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());

        items = new ArrayList<>();
        progressDialog.setMessage("Loading emails");
        progressDialog.show();
        listView = (ListView) rootView.findViewById(R.id.listview);
        Apply = (Button) rootView.findViewById(R.id.apply);
        requestQueue = Volley.newRequestQueue(getActivity());

        Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SparseBooleanArray checked = listView.getCheckedItemPositions();
                ArrayList<GetEmails_pojo> selectedItems = new ArrayList<GetEmails_pojo>();
                for (int i = 0; i < checked.size(); i++) {
                    // Item position in adapter
                    int position = checked.keyAt(i);
                    // Add sport if it is checked i.e.) == TRUE!
                    if (checked.valueAt(i))
                        selectedItems.add(adapter.getItem(position));
                }

                String[] outputStrArr = new String[selectedItems.size()];
                String[] idarray = new String[selectedItems.size()];

                for (int i = 0; i < selectedItems.size(); i++) {
                    outputStrArr[i] = selectedItems.get(i).getEmail();
                    idarray[i] = selectedItems.get(i).getId();

                }

                Intent intent = new Intent(getActivity(),
                        ApplyLeave.class);

                // Create a bundle object
                Bundle b = new Bundle();
                Bundle c = new Bundle();
                b.putStringArray("selectedItems", outputStrArr);
                c.putStringArray("Selected_id", idarray);

                // Add the bundle to the intent.
                intent.putExtras(b);
                intent.putExtras(c);


                // start the ResultActivity

               /* PendingIntent pendingIntent =
                        TaskStackBuilder.create(getActivity())
                                // add all of DetailsActivity's parents to the stack,
                                // followed by DetailsActivity itself
                                .addNextIntentWithParentStack(intent)
                                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
                builder.setContentIntent(pendingIntent);
                //startActivity(intent);*/

                startActivity(intent);


            }
        });

        //Volley request to get data
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://192.168.1.104/leave/public/api/users/getEmailUsers\n "
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        id = jsonObject.getString("identity");
                        email = jsonObject.getString("email");


                        GetEmails_pojo get_mails = new GetEmails_pojo();

                        get_mails.setEmail(email);
                        get_mails.setId(id);
                        items.add(get_mails);
                        //textView.append( id+" "+ first_name + " " + last_name + "\n");

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("error", "error from server response");
            }
        }

        );


        requestQueue.add(jsonObjectRequest);


        //setting adapter for the item in the list view
        adapter = new GetEmail_Adapter(this.getActivity(), R.layout.row_checklist_item, items);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        //end_listview


        //search_view used in the edit_recepients list of recepients



        SearchView searchView = (SearchView) rootView.findViewById(R.id.search_view);
        this.getDialog().setTitle("Edit Recepients");
        searchView.setQueryHint("search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                newText=newText.toLowerCase();

                ArrayList<GetEmails_pojo> newlist = new ArrayList<>();
                for(GetEmails_pojo getEmails_pojo : items)
                {
                    String name= getEmails_pojo.getEmail().toLowerCase();
                    if(name.contains(newText) )
                    {
                        newlist.add(getEmails_pojo);
                    }
                }
                   adapter.setFilter(newlist);
//                adapter.getFilter().filter(newText);
//                adapter.notifyDataSetChanged();
                return true;
            }
        });

        //end_search_view


        return rootView;


    }



}

