package com.example.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    // log in view objects
    private EditText mUsername;
    private EditText mPassword;
    private Button mLoginButton;
    private Button mSignupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // resolve the view objects
        mUsername = findViewById(R.id.etUsername);
        mPassword = findViewById(R.id.etPassword);
        mLoginButton = findViewById(R.id.btnLogin);
        mSignupButton = findViewById(R.id.btnSignup);

        // set up on click listener for the log in button
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = mUsername.getText().toString();
                final String password = mPassword.getText().toString();
                login(username, password);
            }
        });

        // set up on click listener for the log in button
        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = mUsername.getText().toString();
                final String password = mPassword.getText().toString();
                signup(username, password);
            }
        });


        // keep user logged in on device
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            Log.d("LoginActivity", "Login successful");
            final Intent loginIntent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(loginIntent);
            finish();
        }


    }

    private void login(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    Log.d("LoginActivity", "Login successful");
                    final Intent loginIntent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(loginIntent);
                    finish();
                }
                else {
                    Log.e("LoginActivity", "Login failure");
                    e.printStackTrace();
                }
            }
        });
    }

    private void signup(String username, String password) {
        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("LoginActivity", "Sign Up successful");
                    final Intent signUpIntent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(signUpIntent);
                    finish();
                } else {
                    Log.e("LoginActivity", "Sign Up failure");
                    e.printStackTrace();
                }
            }
        });
    }
}
