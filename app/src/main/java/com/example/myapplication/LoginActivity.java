package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin, buttonRegister;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        Boolean u = false,p = false;

        // Initialize the views
        editTextUsername = findViewById(R.id.editTextSearch);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);

        // Instantiate our db handler
        dbHandler = new DBHandler(this);
        //dbHandler.insertGame("chill games only!!!", "1h ago");

        // When user clicks Login
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                Cursor cursor = dbHandler.getAccData();

                if (cursor.getCount() == 0) {
                    Toast.makeText(LoginActivity.this, "No entries exist", Toast.LENGTH_SHORT).show();
                }
                if (loginCheck(cursor, username, password)) {
                    // Retrieve accID from the logged in account so we can pass it
                    int accID = dbHandler.getAccID(username);

                    // Clear all input fields
                    editTextUsername.setText("");
                    editTextPassword.setText("");

                    // Move to the next activity, finish current
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("accID", accID);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Incorrect username / password. Please try again!", Toast.LENGTH_SHORT).show();
                }
                dbHandler.close();
            }
        });

        // When user clicks Register
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform registration logic here
                Toast.makeText(LoginActivity.this, "Register button clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean loginCheck(Cursor cursor, String username, String password) {
        while (cursor.moveToNext()){
            //Log.d("LOGIN CHECK!!!!!!!!!!!", cursor.getString(0) + " " + cursor.getString(1) );
            if (cursor.getString(1).equals(username)) {
                if (cursor.getString(2).equals(password)) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
