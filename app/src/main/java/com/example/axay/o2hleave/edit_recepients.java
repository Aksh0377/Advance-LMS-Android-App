package com.example.axay.o2hleave;

/**
 * Created by axay on 29/06/17.
 */

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import android.widget.ListView;
import android.widget.Toast;
import com.example.axay.o2hleave.mrecycleView.MyAdapter;
import java.util.ArrayList;


/**
 * created  by Axay soni
 *
 * In this we have used ListView.CHOICE_MODE_MULTIPLE in listview to select items rather than checkboxes.
 */
public class edit_recepients extends DialogFragment {

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

        listView=(ListView)rootView.findViewById(R.id.listview);
        Apply=(Button)rootView.findViewById(R.id.apply);

        Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SparseBooleanArray checked = listView.getCheckedItemPositions();
                ArrayList<String> selectedItems = new ArrayList<String>();
                for (int i = 0; i < checked.size(); i++) {
                    // Item position in adapter
                    int position = checked.keyAt(i);
                    // Add sport if it is checked i.e.) == TRUE!
                    if (checked.valueAt(i))
                        selectedItems.add(arrayAdapter.getItem(position));
                }

                String[] outputStrArr = new String[selectedItems.size()];

                for (int i = 0; i < selectedItems.size(); i++) {
                    outputStrArr[i] = selectedItems.get(i);
                }

                Intent intent = new Intent(getActivity(),
                        ApplyLeave.class);

                // Create a bundle object
                Bundle b = new Bundle();
                b.putStringArray("selectedItems", outputStrArr);

                // Add the bundle to the intent.
                intent.putExtras(b);

                // start the ResultActivity
                PendingIntent pendingIntent =
                        TaskStackBuilder.create(getActivity())
                                // add all of DetailsActivity's parents to the stack,
                                // followed by DetailsActivity itself
                                .addNextIntentWithParentStack(intent)
                                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
                builder.setContentIntent(pendingIntent);
                startActivity(intent);



            }
        });




        //setting adapter for the item in the list view
        arrayAdapter= new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_multiple_choice,recepients);
        listView.setAdapter(arrayAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        //end_listview


        //search_view used in the edit_recepients list of recepients
        SearchView searchView=(SearchView)rootView.findViewById(R.id.search_view);
        this.getDialog().setTitle("Edit Recepients");
        searchView.setQueryHint("search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
                }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
          return false;
            }
        });
        //end_search_view


        return rootView;


    }





}

