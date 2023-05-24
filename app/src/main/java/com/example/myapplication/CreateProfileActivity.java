package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateProfileActivity extends AppCompatActivity {
    private EditText editTextDisplayName, editTextRegion, editTextServer, editTextPeak, editTextCurrent, editTextTwt, editTextFb, editTextDc;
    private Button btnFinish;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        //Retrieve necessary GUI elements
        btnFinish = findViewById(R.id.btnFinish);
        editTextDisplayName = findViewById(R.id.editTextDisplayName);
        editTextRegion = findViewById(R.id.editTextRegion);
        editTextServer = findViewById(R.id.editTextServer);
        editTextPeak = findViewById(R.id.editTextPeak);
        editTextCurrent = findViewById(R.id.editTextCurrent);
        editTextTwt = findViewById(R.id.editTextTwt);
        editTextFb = findViewById(R.id.editTextFb);
        editTextDc = findViewById(R.id.editTextDc);

        // Retrieve accID from reg page
        int accID = getIntent().getExtras().getInt("accID");

        // Instantiate our db handler
        dbHandler = new DBHandler(this);

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input from text fields
                String displayName = editTextDisplayName.getText().toString();
                String region = editTextRegion.getText().toString();
                String server = editTextServer.getText().toString();
                String peak = editTextPeak.getText().toString();
                String current = editTextCurrent.getText().toString();
                String twt = editTextTwt.getText().toString();
                String fb = editTextFb.getText().toString();
                String dc = editTextDc.getText().toString();

                // Insert values into our account table
                // Return boolean value to check if successful
                boolean b = dbHandler.insertUser(displayName, "Hello world!", region, server, peak, current, twt, fb, dc, accID);

                // If profile creation was successful, proceed to LOGIN
                // If unsuccessful, inform user
                if (b){
                    if(displayName.equalsIgnoreCase("") || region.equalsIgnoreCase("") || server.equalsIgnoreCase("") || peak.equalsIgnoreCase("") || current.equalsIgnoreCase("")) {
                        Toast.makeText(CreateProfileActivity.this,"Please enter all required fields.",Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(CreateProfileActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    // Move to CreateProfile activity
                    // Pass accID as we need the value for database entry creation
                } else {
                    // Inform user there has been an error: Add POP-UP overlay if possible
                    Toast.makeText(CreateProfileActivity.this,"Failed to create new PROFILE",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}