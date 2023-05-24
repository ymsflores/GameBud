package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextPassword;
    private Button buttonRegister;
    DBHandler mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Instantiate our db handler
        mydb = new DBHandler(this);

        // Get our GUI elements
        editTextUsername = findViewById(R.id.editTextSearch);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.button);

        // When user clicks Register button
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input from text fields
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                // Insert values into our account table
                // Return boolean value to check if successful
                boolean b = mydb.insertAcc(username, password);
                int accID = mydb.getAccID(username);


                // If registration was successful, proceed to PROFILE CREATION
                // If unsuccessful, inform user
                if (b){
                    // Move to CreateProfile class, pass accID as it is necessary for user table entry creation
                    if(username.equalsIgnoreCase("") || password.equalsIgnoreCase("")) {
                        editTextUsername.setError("Please enter all required fields.");
                        editTextPassword.setError("Please enter all required fields");
                    } else {
                        Intent intent = new Intent(RegisterActivity.this, CreateProfileActivity.class);
                        intent.putExtra("accID", accID);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this,"Failed To insert Data",Toast.LENGTH_SHORT).show();
                }
            }
        });
     }
    }
