package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {
    ArrayList<String> namesArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences prefs = getSharedPreferences("name list", MODE_PRIVATE);
                String name = prefs.getString("name1", null);

                Intent intent;
                if(name == null) {
                    intent = new Intent(SplashActivity.this, FirstPage.class);
                    startActivity(intent);
                    Toast.makeText(SplashActivity.this, name, Toast.LENGTH_SHORT).show();
                } else {
                    intent = new Intent(SplashActivity.this, SecondPage.class);
                    startActivity(intent);
                }

            }
        }, 3000);
    }

}