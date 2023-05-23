package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileFragment extends Fragment {
    Button btnSignOut;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Get elements from GUI
        Button btnSignOut = view.findViewById(R.id.btnSignOut);
        TextView txtName = view.findViewById(R.id.txtName);

        // Get the username from the login page
        String username = getActivity().getIntent().getExtras().getString("username");

        // Instantiate our DBHandler
        // Get user data based on username
        DBHandler dbHandler = new DBHandler(getActivity());
        Cursor cursor = dbHandler.getUserData(username);

        // Check if user data exists
        if (cursor.getCount() == 0) {
            Toast.makeText(getActivity(), "No user data exists", Toast.LENGTH_SHORT).show();
        } else {
            txtName.setText(cursor.getString(1));
        }

        // When user clicks SIGN OUT
        // Proceed to LOGIN activity
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform registration logic here
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}