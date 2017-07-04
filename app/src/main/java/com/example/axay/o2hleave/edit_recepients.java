package com.example.axay.o2hleave;

/**
 * Created by axay on 29/06/17.
 */

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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

import com.example.axay.o2hleave.mrecycleView.MyAdapter;


/**
 * Created by Oclemmy on 4/23/2016 for ProgrammingWizards Channel.
 */
public class edit_recepients extends DialogFragment {

    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    Button Apply;

    String[] tvshows = {"Crisis", "Blindspot", "BlackList", "Game of Thrones", "Gotham", "Banshee", "fnkfe", "dkjkfnw", "jfhdjhbcf", "dkckljw"
            , "fjhwckfjqw", "efcjl;fce", "eckfhnw"};
    //RecyclerView rv;
   // MyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fraglayout, container);

        listView=(ListView)rootView.findViewById(R.id.listview);
       // rv = (RecyclerView) rootView.findViewById(R.id.mRecyerID);
        SearchView searchView=(SearchView)rootView.findViewById(R.id.search_view);
      //  rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        //ADAPTER
       // adapter = new MyAdapter(this.getActivity(), tvshows);
        arrayAdapter= new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_multiple_choice,tvshows);

        listView.setAdapter(arrayAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

       // rv.setAdapter(adapter);

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


        return rootView;
    }
}

