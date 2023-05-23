package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;

public class BrowseFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<String> name, region;
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

        // Set up our recycleview
        recyclerView = view.findViewById(R.id.recyclerview);
        adapter = new MyAdapter(getActivity(), name, region);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Finally, call the function to display our data
        displayData();

        return view;
    }

    private void displayData() {
        Cursor cursor = dbHandler.getUserData();

        if (cursor.getCount() == 0) {
            Toast.makeText(getActivity(), "help!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {
                name.add(cursor.getString(1));
                region.add(cursor.getString(3));
            }
        }
    }


}