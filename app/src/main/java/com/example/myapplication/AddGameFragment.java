package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class AddGameFragment extends Fragment {
    Button btnAdd;
    EditText editTxtName, editTxtCode, editTxtDetails;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_game, container, false);

        // Retrieving our buttons and editText fields
        btnAdd = view.findViewById(R.id.btnAdd);
        editTxtName = view.findViewById(R.id.editTxtName);
        editTxtCode = view.findViewById(R.id.editTxtCode);
        editTxtDetails = view.findViewById(R.id.editTxtDetails);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtName = editTxtName.getText().toString();
                String txtTime = getCurrentDate();
                String txtCode = editTxtCode.getText().toString();
                String txtDetails = editTxtDetails.getText().toString();

                // Get logged in user's accID
                int accID = getActivity().getIntent().getExtras().getInt("accID");

                // Instantiate our DBHandler
                // Get user data based on username
                DBHandler dbHandler = new DBHandler(getActivity());

                // Check if required fields are empty
                if (txtName.equalsIgnoreCase("") || txtCode.equalsIgnoreCase("")) {
                    editTxtName.setError("Please enter all required fields.");
                    editTxtCode.setError("Please enter all required fields");
                } else {
                    // Insert new game into our DB
                    dbHandler.insertGame(txtName, txtTime, txtCode, txtDetails, accID);

                    // Go back to Join screen
                    JoinFragment fragment = new JoinFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).addToBackStack(null).commit();
                }
            }
        });
        return view;
    }

    private String getCurrentDate() {
        // Get current date and format as string so we can insert it into our game table
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();
        String datetime = dateFormat.format(date); //

        return datetime;
    }
}