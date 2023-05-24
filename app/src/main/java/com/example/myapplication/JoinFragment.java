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

import java.util.ArrayList;

public class JoinFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<String> name, time;
    DBHandler dbHandler;
    MyAdapterGame adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_join, container, false);

        // Instantiate our DBHandler
        dbHandler = new DBHandler(getActivity());

        // Instantiate our arraylists
        name = new ArrayList<>();
        time = new ArrayList<>();

        // Set up our recycleview
        recyclerView = view.findViewById(R.id.recyclerviewgame);
        adapter = new MyAdapterGame(getActivity(), name, time);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));

        // Finally, call the function to display our data
        displayData();

        return view;
    }

    private void displayData() {
        Cursor cursor = dbHandler.getGameData();
        if (cursor.getCount() == 0) {
            return;
        } else {
            while (cursor.moveToNext()) {
                name.add(cursor.getString(1));
                time.add(cursor.getString(2));
            }
        }
    }
}