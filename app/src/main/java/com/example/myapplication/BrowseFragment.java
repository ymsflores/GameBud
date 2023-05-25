package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;

public class BrowseFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<String> name, region;
    ArrayList<Integer> ids;
    DBHandler dbHandler;
    MyAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_browse, container, false);

        // Instantiate our DBHandler
        dbHandler = new DBHandler(getActivity());

        // Instantiate our arraylists
        name = new ArrayList<>();
        region = new ArrayList<>();
        ids = new ArrayList<>();

        // Set up our recycleview
        recyclerView = view.findViewById(R.id.recyclerview);
        adapter = new MyAdapter(getActivity(), name, region, ids);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Finally, call the function to display our data
        displayData();

        EditText editTextSearch =  view.findViewById(R.id.editTextSearch);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString()) ;
            }
        });
        return view;
    }

    private void displayData() {
        Cursor cursor = dbHandler.getUserData();

        if (cursor.getCount() == 0) {
            return;
        } else {
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()) {
                if (cursor.getInt(10) == getActivity().getIntent().getExtras().getInt("accID")) {
                    continue;
                }

                name.add(cursor.getString(1));
                region.add(cursor.getString(3));
                ids.add(cursor.getInt(10));
            }
        }
    }

    private void filter(String text) {
        ArrayList<String> filteredNames = new ArrayList<>();
        ArrayList<String> filteredRegions = new ArrayList<>();

        for (int i = 0; i < name.size(); i++) {
            if (name.get(i).toLowerCase().contains(text.toLowerCase())) {
                filteredNames.add(name.get(i));
                filteredRegions.add(region.get(i));
            }
        }

        adapter.filterList(filteredNames, filteredRegions);

    }



}