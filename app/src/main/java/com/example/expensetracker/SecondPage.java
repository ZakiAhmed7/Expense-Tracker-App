package com.example.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SecondPage extends AppCompatActivity {
    private TextView tvName1, tvName2, tvName3, tvAmount1, tvAmount2, tvAmount3;
    private ListView listView;
    ArrayList<String> namesArray = new ArrayList<String>();
    String name1, name2, name3;
    int number;
    // for card view
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private TextView tvTitle;
    private EditText edTAmountNumber, edTNote;
    private Button btnUpdate;

    private AlertDialog.Builder exitCardDialogBuilder;
    private AlertDialog exitCardDialog;
    private TextView tvTotal, tvECP1, tvECP2, tvECP3;
    private Button btnCancel, btnEndTrip;
    // for listview
    ArrayList<DetailList> detailsArrayList = new ArrayList<DetailList>();
    String nameSelected;
    // for storing the input data and calculating it.
    ArrayList<Integer> name1Cal = new ArrayList<Integer>();
    ArrayList<Integer> name2Cal = new ArrayList<Integer>();
    ArrayList<Integer> name3Cal = new ArrayList<Integer>();
    int amu1, amu2, amu3 = 0;

    DetailList list;

    SharedPreferences getPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);
        initViews();

        getPrefs = getSharedPreferences("NameList", MODE_PRIVATE);
        name1 = getPrefs.getString("name1", " Error");
        name2 = getPrefs.getString("name2", "Error");
        name3 = getPrefs.getString("name3", "Error");
        number = getPrefs.getInt("option", 1);

        namesArray.add(name1);
        namesArray.add(name2);
        namesArray.add(name3);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Expenses");
        actionBar.setSubtitle("Details");
        // adding icon into the left side of the action bar
        actionBar.setIcon(R.drawable.ic_note);
        // methods to display the icon in the ActionBar
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        // number = getIntent().getExtras().getInt("Number");
        switch(number) {
            case 2: {
                tvName1.setText(name1);
                tvName2.setText(name2);
                tvName3.setVisibility(View.GONE);
                tvAmount3.setVisibility(View.GONE);
                break;
            }
            case 3: {
                tvName1.setText(name1);
                tvName2.setText(name2);
                tvName3.setText(name3);
                break;
            }
            default: {
                tvName1.setText(name1);
                tvName2.setVisibility(View.GONE);
                tvName3.setVisibility(View.GONE);
                tvAmount2.setVisibility(View.GONE);
                tvAmount3.setVisibility(View.GONE);
                break;
            }
        }

    }


    private void exitCardView() {
        exitCardDialogBuilder = new AlertDialog.Builder(this);
        final View exitCardView = getLayoutInflater().inflate(R.layout.exit_card, null);

        tvTotal = exitCardView.findViewById(R.id.tvTotal);
        tvECP1 = exitCardView.findViewById(R.id.tvECP1);
        tvECP2 = exitCardView.findViewById(R.id.tvECP2);
        tvECP3 = exitCardView.findViewById(R.id.tvECP3);
        btnCancel = exitCardView.findViewById(R.id.btnCancel);
        btnEndTrip = exitCardView.findViewById(R.id.btnEndTrip);

        int totalAmu = amu1 + amu2 + amu3;

        switch (number) {
            case 1: {
                tvTotal.setText("Total: "+totalAmu);
                tvECP1.setText(name1+" : "+amu1);
                tvECP2.setVisibility(View.GONE);
                tvECP3.setVisibility(View.GONE);
                break;
            }
            case 2: {
                tvTotal.setText("Total: "+totalAmu);
                tvECP1.setText(name1+" : "+amu1);
                tvECP2.setText(name2+" : "+amu2);
                tvECP3.setVisibility(View.GONE);
                break;
            }
            default: {
                tvTotal.setText("Total: "+totalAmu);
                tvECP1.setText(name1 + " : " + amu1);
                tvECP2.setText(name2 + " : " + amu2);
                tvECP3.setText(name3 + " : " + amu3);
                break;
            }
        }

        exitCardDialogBuilder.setView(exitCardView);
        exitCardDialog = exitCardDialogBuilder.create();
        exitCardDialog.show();

        btnEndTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SecondPage.this, FirstPage.class);
                startActivity(i);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitCardDialog.dismiss();
            }
        });

    }

    // card view for getting details of expenses
    private void cardView(int i) {
        // converting an activity to a card view by
        dialogBuilder = new AlertDialog.Builder(this);
        final View cView = getLayoutInflater().inflate(R.layout.cardview, null);
        // initialization of views in the card view
        tvTitle = cView.findViewById(R.id.tvTitle);
        edTAmountNumber = cView.findViewById(R.id.edTAmountNumber);
        edTNote = cView.findViewById(R.id.edTNote);
        Spinner nameSpinner = cView.findViewById(R.id.nameSpinner);
        btnUpdate = cView.findViewById(R.id.btnUpdate);

        //Adding a spinner and using an adapter to show spinner. Spinner Code
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, namesArray);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nameSpinner.setAdapter(ad);
        //selecting item from drop down menu and Adding Functionality to spinner
        nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(SecondPage.this, namesArray.get(position), Toast.LENGTH_SHORT).show();
                nameSelected = namesArray.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(i == 2) {
            tvTitle.setText("Enter the Amount that you need to take");
        }
        //creating a cardView to display it.
        dialogBuilder.setView(cView);
        dialog = dialogBuilder.create();
        dialog.show();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount = Integer.parseInt(edTAmountNumber.getText().toString());
                String note = edTNote.getText().toString();
                if (note.isEmpty()) {
                    note = "No Data Entered";
                }
                DetailList details = new DetailList(nameSelected, amount, note);
                detailsArrayList.add(details);
                saveData();
                if (i == 1) {
                    // wrong calculation logic please check the condition.
                    //please check the Java code of this program to get an idea of how things work.
                    if (nameSelected.equals(name1)) {
                        name1Cal.add(amount);
                        amu1 = amu1 + amount;
                        tvAmount1.setText(String.valueOf(amu1));

                    } else if (nameSelected.equals(name2)) {
                        name2Cal.add(amount);
                        amu2 = amu2 + amount;
                        tvAmount2.setText(String.valueOf(amu2));
                    } else if (nameSelected.equals(name3)) {
                        name3Cal.add(amount);
                        amu3 = amu3 + amount;
                        tvAmount3.setText(String.valueOf(amu3));
                    }
                } else if (i == 2) {
                    if (nameSelected.equals(name1)) {
                        name1Cal.add(amount);
                        amu1 = amu1 - amount;
                        tvAmount1.setText(String.valueOf(amu1));
                    }else if (nameSelected.equals(name2)) {
                        name2Cal.add(amount);
                        amu2 = amu2 - amount;
                        tvAmount2.setText(String.valueOf(amu2));
                    } else if (nameSelected.equals(name3)) {
                        name3Cal.add(amount);
                        amu3 = amu3 - amount;
                        tvAmount3.setText(String.valueOf(amu3));
                    }
                }
                dialog.dismiss();
            }
        });

        //deceleration of a custom Adapter
        ArrayListCustomAdapter listCustomAdapter = new ArrayListCustomAdapter(this, detailsArrayList);
        listView.setAdapter(listCustomAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(SecondPage.this, detailsArrayList.get(position).getDetailMessage()+ " Hello", Toast.LENGTH_SHORT).show();
                Snackbar.make(SecondPage.this, view, detailsArrayList.get(position).getDetailMessage(), Snackbar.LENGTH_LONG)
                        .setAction("More Details", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(SecondPage.this, "Showing More Details", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();

            }
        });

    }
    // for ArrayList
    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(detailsArrayList);
        editor.putString("task list", json);
        editor.apply();

    }

    //Initialization of views
    private void initViews() {
        tvName1 = findViewById(R.id.tvName1);
        tvName2 = findViewById(R.id.tvName2);
        tvName3 = findViewById(R.id.tvName3);
        tvAmount1 = findViewById(R.id.tvAmount1);
        tvAmount2 = findViewById(R.id.tvAmount2);
        tvAmount3 = findViewById(R.id.tvAmount3);
        listView = findViewById(R.id.listView);
    }
    //Second page menu inflater method
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.second_page_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    // on menu options selected method
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.plus:
                int i = 1;
                cardView(i);
                break;
            case R.id.delete:
                int j = 2;
                cardView(j);
                Toast.makeText(this, "Remove amount from an account", Toast.LENGTH_SHORT).show();
                break;
            case R.id.endTrip:
                exitCardView();
                Toast.makeText(this, "Calculating", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this, "Opening new page", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}