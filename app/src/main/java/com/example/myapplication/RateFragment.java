package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

public class RateFragment extends Fragment {
    int accID_rcv, accID_sub;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rate, container, false);

        // Retrieve necessary GUI elements
        Button btn = view.findViewById(R.id.btnSubmit);
        RatingBar rBar = view.findViewById(R.id.ratingBarRate);

        // Get logged in user's accID
        // Get the accID from the selected (to review) user profile
        // Instantiate our DBHandler
        DBHandler dbHandler = new DBHandler(getActivity());

        accID_sub = getActivity().getIntent().getExtras().getInt("accID");

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            accID_rcv = (int) bundle.get("accID");
            //Log.d("Successful!", String.valueOf(accID_rcv));
        }


        // On button click, add new rating to Database
        // If user already had existing review from current user, overwrite it
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Are the values correct?", String.valueOf(accID_sub) + ' ' + String.valueOf(accID_rcv));
                // Retrieve the rating and insert it into our database
                float rating = rBar.getRating();
                dbHandler.insertRating("Placeholder description", rating, accID_sub, accID_rcv);

                // Go back to selected user's profile
                Bundle bundle = new Bundle();
                bundle.putInt("accID", accID_rcv);
                OtherProfileFragment fragment = new OtherProfileFragment();
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).addToBackStack(null).commit();
            }
        });

        return view;
    }
}