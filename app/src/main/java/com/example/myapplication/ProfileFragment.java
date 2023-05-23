package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileFragment extends Fragment {
    String txtFb = "";
    String txtDc = "";
    String txtTwt = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Retrieve necessary GUI elements
        Button btnSignOut = view.findViewById(R.id.btnSignOut);
        Button btnFb = view.findViewById(R.id.btnFb);
        Button btnDc = view.findViewById(R.id.btnDc);
        Button btnTwt = view.findViewById(R.id.btnTwt);
        TextView txtName = view.findViewById(R.id.txtName);
        TextView txtDesc = view.findViewById(R.id.txtDesc);
        TextView txtRegion = view.findViewById(R.id.txtRegion);
        TextView txtServer = view.findViewById(R.id.txtServer);
        TextView txtPeak = view.findViewById(R.id.txtPeak);
        TextView txtCurr = view.findViewById(R.id.txtCurr);


        // Get the username from the login page
        int accID = getActivity().getIntent().getExtras().getInt("accID");

        // Instantiate our DBHandler
        // Get user data based on username
        DBHandler dbHandler = new DBHandler(getActivity());
        Cursor cursor = dbHandler.getUserData(accID);

        // Check if user data exists
        if (cursor.getCount() == 0) {
            Toast.makeText(getActivity(), "No user data exists", Toast.LENGTH_SHORT).show();
        } else {
            txtName.setText(cursor.getString(1));
            txtDesc.setText(cursor.getString(2));
            txtRegion.setText(cursor.getString(3));
            txtServer.setText(cursor.getString(4));
            txtPeak.setText(cursor.getString(5));
            txtCurr.setText(cursor.getString(6));
            txtTwt = cursor.getString(7);
            txtFb = cursor.getString(8);
            txtDc = cursor.getString(9);
        }

        // When user clicks SIGN OUT
        // Proceed to LOGIN activity
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://www.facebook.com/" + txtFb);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        btnTwt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://www.twitter.com/" + txtTwt);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        btnDc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Discord ID: " + txtDc, Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

}