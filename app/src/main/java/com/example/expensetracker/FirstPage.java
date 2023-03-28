package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class FirstPage extends AppCompatActivity {

    private TextView txtFirstSen;
    private EditText edTNumber, edTName1, edTName2, edTName3;
    Button btnSet, btnSave;
    LinearLayout namesLinearLayout;
    int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        initViews();

        namesLinearLayout.setVisibility(View.GONE);
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = Integer.parseInt(edTNumber.getText().toString());
                switch (number) {
                    case 1:
                        case1();
                        break;
                    case 2:
                        case2();
                        break;
                    case 3:
                        case3();
                        break;
                    default:
                        Toast.makeText(FirstPage.this, "Maximum 3 persons can register for now", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void case3() {
        namesLinearLayout.setVisibility(View.VISIBLE);
        edTName1.setVisibility(View.VISIBLE);
        btnSave.setVisibility(View.VISIBLE);
        edTName2.setVisibility(View.VISIBLE);
        edTName3.setVisibility(View.VISIBLE);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAndSaveData(number);
                nextActivity();
            }
        });
    }

    private void case2() {
        namesLinearLayout.setVisibility(View.VISIBLE);
        edTName1.setVisibility(View.VISIBLE);
        btnSave.setVisibility(View.VISIBLE);
        edTName2.setVisibility(View.VISIBLE);
        edTName3.setVisibility(View.GONE);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAndSaveData(number);
                nextActivity();
            }
        });
    }

    private void case1() {
        namesLinearLayout.setVisibility(View.VISIBLE);
        edTName1.setVisibility(View.VISIBLE);
        btnSave.setVisibility(View.VISIBLE);
        edTName2.setVisibility(View.GONE);
        edTName3.setVisibility(View.GONE);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAndSaveData(number);
                nextActivity();
            }
        });
    }

    private void getAndSaveData(int number) {
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("NameList", MODE_PRIVATE);
        switch(number) {
            case 2: {
                String name1 = edTName1.getText().toString();
                String name2 = edTName2.getText().toString();
                int option = Integer.parseInt(edTNumber.getText().toString());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("name1", name1);
                editor.putString("name2", name2);
                editor.putInt("option", option);
                editor.apply();
                break;
            }
            case 3: {
                String name1 = edTName1.getText().toString();
                String name2 = edTName2.getText().toString();
                String name3 = edTName3.getText().toString();
                int option = Integer.parseInt(edTNumber.getText().toString());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("name1", name1);
                editor.putString("name2", name2);
                editor.putString("name3", name3);
                editor.putInt("option", option);
                editor.apply();
                break;
            }
            default: {
                int option = Integer.parseInt(edTNumber.getText().toString());
                String name1 = edTName1.getText().toString();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("name1", name1);
                editor.putInt("option", option);
                editor.apply();
                break;
            }
        }

    }
    private void nextActivity() {
        Intent intent = new Intent(FirstPage.this, SecondPage.class);
        startActivity(intent);
    }
    private void initViews() {
        txtFirstSen = findViewById(R.id.txtFirstSen);

        edTNumber = findViewById(R.id.edTNumber);
        edTName1 = findViewById(R.id.edTName1);
        edTName2 = findViewById(R.id.edTName2);
        edTName3 = findViewById(R.id.edTName3);

        btnSet = findViewById(R.id.btnSet);
        btnSave = findViewById(R.id.btnSave);

        namesLinearLayout = findViewById(R.id.namesLinearLayout);
    }
}