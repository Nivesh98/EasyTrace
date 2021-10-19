package com.nivacreation.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BusDetailsActivity extends AppCompatActivity {


    //EditText editText,editText1;
    Spinner spinner_start, spinner_end;
    Button submit;

    int startPoint=0, endPoint =0;
    //TextView start,end;
    //DatePickerDialog.OnDateSetListener listener;
    //TimePickerDialog.OnTimeSetListener timeSetListener;

    //..........Start Location...........

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_details);

        selectLocation();

    }

    private void selectLocation() {
        //        editText = findViewById(R.id.Date);
//        editText1= findViewById(R.id.Time);
        spinner_start = findViewById(R.id.spinner_start);
        spinner_end = findViewById(R.id.spinner_end);
        //start = findViewById(R.id.txt_start);
        //end = findViewById(R.id.txt_end);
        submit = findViewById(R.id.btnSubmit);

        ArrayList<String> townList_start = new ArrayList<>();

        townList_start.add("Select Start Location");
        townList_start.add("Kirindiwela");
        townList_start.add("Radawana");
        townList_start.add("Henegama");
        townList_start.add("Waliweriya");
        townList_start.add("Nadungamuwa");
        townList_start.add("Miriswaththa");
        townList_start.add("Mudungoda");
        townList_start.add("Gampaha");


        spinner_start.setAdapter(new ArrayAdapter<>(BusDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item,townList_start));

        spinner_start.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0){
                    Toast.makeText(getApplicationContext(),
                            "Select Start Location",Toast.LENGTH_SHORT).show();
                    //start.setText("");
                }else {
                    startPoint = position;
                    String STown = parent.getItemAtPosition(position).toString();
                    //start.setText(STown);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //.................End Location..................

        ArrayList<String> townList_end = new ArrayList<>();

        townList_end.add("Select End Location");
        townList_end.add("Kirindiwela");
        townList_end.add("Radawana");
        townList_end.add("Henegama");
        townList_end.add("Waliweriya");
        townList_end.add("Nadungamuwa");
        townList_end.add("Miriswaththa");
        townList_end.add("Mudungoda");
        townList_end.add("Gampaha");


        spinner_end.setAdapter(new ArrayAdapter<>(BusDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item,townList_end));

        spinner_end.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0){
                    Toast.makeText(getApplicationContext(),
                            "Select End Location",Toast.LENGTH_SHORT).show();
                    //end.setText("");
                }else {
                    endPoint = position;
                    String ETown = parent.getItemAtPosition(position).toString();
                    //end.setText(ETown);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(BusDetailsActivity.this, AboutBusActivity.class);
                startActivity(i);
            }
        });
    }
}