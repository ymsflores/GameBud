package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

        // Retrieve out floating action button
        // Open the Add Game screen on user click
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddGameFragment fragment = new AddGameFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).addToBackStack(null).commit();
            }
        });

        // Return inflated layout
        return view;
    }

    private void displayData() {
        String timeTemp = "";
        String timeFin = "";
        Cursor cursor = dbHandler.getGameData();
        if (cursor.getCount() == 0) {
            return;
        } else {
            while (cursor.moveToNext()) {
                // Calculate difference between current time and game creation time
                // First, retrieve our saved time and convert it back to Date
                try {
                    timeTemp = cursor.getString(2);
                    timeFin = "";
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    String currDate = getCurrentDate();

                    int diff = (int) TimeUnit.HOURS.convert(format.parse(currDate).getTime() - format.parse(timeTemp).getTime(), TimeUnit.MILLISECONDS);
                    if (diff < 1) {
                        timeFin = String.valueOf(TimeUnit.MINUTES.convert(format.parse(currDate).getTime() - format.parse(timeTemp).getTime(), TimeUnit.MILLISECONDS)) + (" mins ago");
                    } else {
                        timeFin += (diff + (" hrs ago"));
                        Log.d("Help", "");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Add our values to our arraylists
                name.add(cursor.getString(1));
                time.add(timeFin);

            }
        }
    }

    private String getCurrentDate() {
        // Get current date so we can insert it in case user wants to make a new game
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();
        String datetime = dateFormat.format(date); // this date & time you can store in database as a string

        return datetime;
    }


}