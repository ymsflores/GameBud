package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class GameDetailsFragment extends Fragment {
    int accID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_game_details, container, false);

        // Retrieve necessary GUI elements
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        TextView txtDetails = view.findViewById(R.id.txtDetails);
        TextView txtUsername = view.findViewById(R.id.txtUsername);
        TextView txtCode = view.findViewById(R.id.txtCode);
        TextView txtUserRank = view.findViewById(R.id.txtUserRank);

        // Get the accID from the selected card in recyclerview
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            accID = (int) bundle.get("accID");
            Log.d("Successful!", String.valueOf(accID));
        }

        // Instantiate our DBHandler
        // Get user data based on username
        DBHandler dbHandler = new DBHandler(getActivity());
        Cursor cursor = dbHandler.getGameData(accID);

        // Check if user data exists
        if (cursor.getCount() == 0) {
            Toast.makeText(getActivity(), "No game data exists", Toast.LENGTH_SHORT).show();
        } else {
            txtTitle.setText(cursor.getString(1));
            txtDetails.setText(cursor.getString(4));
            txtCode.setText(cursor.getString(3));
        }

        cursor = dbHandler.getUserData(accID);
        // Check if user data exists
        if (cursor.getCount() == 0) {
            Toast.makeText(getActivity(), "No user data exists", Toast.LENGTH_SHORT).show();
        } else {
            txtUsername.setText(cursor.getString(1));
            txtUserRank.setText(cursor.getString(6 ));
        }


        return view;
    }
}