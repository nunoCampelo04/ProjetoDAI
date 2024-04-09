package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

        private boolean isConnected;
        private TextView noInternetTextView;
        private ImageView noInternet;

        Button buttonLogin;
        Button buttonRegister;

        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            noInternetTextView = findViewById(R.id.no_internet_text_view);
            noInternet = findViewById(R.id.noInternet);
            buttonLogin = findViewById(R.id.buttonLogin);
            buttonRegister = findViewById(R.id.buttonRegister);

            if (!NetworkUtils.isNetworkAvailable(this)) {
                showNoInternetMessage();
            } else {
                buttonLogin.setVisibility(View.VISIBLE);
                buttonRegister.setVisibility(View.VISIBLE);
                noInternetTextView.setVisibility(View.GONE);
                noInternet.setVisibility(View.GONE);
            }

            isConnected = NetworkUtils.isNetworkAvailable(this);

            buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openLoginPage();
                }
            });

            buttonRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openRegisterPage();
                }
            });

            FirebaseApp.initializeApp(this);

            // Check internet connection every 5 seconds
            final Handler handler = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (!isConnected && NetworkUtils.isNetworkAvailable(getApplicationContext())) {
                        isConnected = true;
                        noInternetTextView.setVisibility(View.GONE);
                        noInternet.setVisibility(View.GONE);
                    } else if (isConnected && !NetworkUtils.isNetworkAvailable(getApplicationContext())) {
                        isConnected = false;
                        showNoInternetMessage();
                    }
                    handler.postDelayed(this, 5000);
                }
            };
            handler.postDelayed(runnable, 5000);
        }

        private void showNoInternetMessage() {
            buttonLogin.setVisibility(View.GONE);
            buttonRegister.setVisibility(View.GONE);
            noInternetTextView.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.VISIBLE);

        }

        private void openLoginPage() {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        private void openRegisterPage() {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    }