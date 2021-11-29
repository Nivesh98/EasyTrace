package com.nivacreation.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class BusDetailsQRActivity extends AppCompatActivity {

    Spinner spinner_start, spinner_end;
    Button submit;

    String selectedDestinationEnd = "";
    String selectedDestinationStart = "";

    int startPoint = 0, endPoint =0;

    //..........Start Location...........

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_details_qr);

        spinner_start = findViewById(R.id.spinner_start);
        spinner_end = findViewById(R.id.spinner_end);

        submit = findViewById(R.id.btnSubmit);

        String passengerId = getIntent().getStringExtra("passengerUserId");
        String driverId = getIntent().getStringExtra("driverUserId");

        ArrayList<String> townList_start = new ArrayList<>();

        townList_start.add("Kirindiwela");
        townList_start.add("Kirindiwela");
        townList_start.add("Radawana");
        townList_start.add("Henegama");
        townList_start.add("Waliweriya");
        townList_start.add("Nadungamuwa");
        townList_start.add("Miriswaththa");
        townList_start.add("Mudungoda");
        townList_start.add("Gampaha");

        spinner_start.setAdapter(new ArrayAdapter<>(BusDetailsQRActivity.this, android.R.layout.simple_spinner_dropdown_item,townList_start));

        spinner_start.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    startPoint = position;
                    selectedDestinationStart = parent.getItemAtPosition(position).toString();

//                if(position == 0){
//                    selectedDestinationStart ="";
//                }else {
//                    startPoint = position;
//                    selectedDestinationStart = parent.getItemAtPosition(position).toString();
//                }
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


        spinner_end.setAdapter(new ArrayAdapter<>(BusDetailsQRActivity.this, android.R.layout.simple_spinner_dropdown_item,townList_end));

        spinner_end.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0){
                    selectedDestinationEnd = "";
                }else {
                    endPoint =position;
                    selectedDestinationEnd = parent.getItemAtPosition(position).toString();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedDestinationEnd.equals("")){
                    Toast.makeText(BusDetailsQRActivity.this,"Please Select EndPoint!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(selectedDestinationStart.equals("")){
                    Toast.makeText(BusDetailsQRActivity.this,"Please Select StartPoint!",Toast.LENGTH_SHORT).show();
                    return;
                }
                String parengerDirection = startPoint>endPoint? "Kirindiwela" : "Gampaha";

                Intent i = new Intent(BusDetailsQRActivity.this, BusInsideDetailsQRActivity.class);
                i.putExtra("passengerID",passengerId);
                i.putExtra("driverID",driverId);
                i.putExtra("stLocation", selectedDestinationStart);
                i.putExtra("enLocation",selectedDestinationEnd);
                i.putExtra("trRoute",parengerDirection);
                i.putExtra("stInt",startPoint);
                i.putExtra("enInt",endPoint);
                startActivity(i);
            }
        });
    }
}