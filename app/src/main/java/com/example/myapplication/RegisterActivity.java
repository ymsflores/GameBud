package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextPassword;
    private Button buttonRegister;
    DBHandler mydb;

    String conP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Instantiate db handler
        mydb = new DBHandler(this);

        // Get our GUI elements
        editTextUsername = findViewById(R.id.editTextUsername);
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
                Log.d("You are in Register Activity!", String.valueOf(accID));

                boolean b2 = mydb.insertUser("Abu Maomao", "desc", "region", "server", "peak", "current", "test", "test", "test", accID);

                // If registration was successful, proceed to PROFILE CREATION
                // If unsuccessful, inform user
                if (b){
                    Toast.makeText(RegisterActivity.this,"Data inserted",Toast.LENGTH_SHORT).show();
                    if (b2) {
                        Toast.makeText(RegisterActivity.this,"Data inserted USER",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this,"Failed to insert data USER",Toast.LENGTH_SHORT).show();
                    }
                    Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(RegisterActivity.this,"Failed To insert Data",Toast.LENGTH_SHORT).show();
                }

            }
        });
     }
    }
