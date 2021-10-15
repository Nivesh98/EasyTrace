package com.nivacreation.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nivacreation.login.adapter.BusAdapter;
import com.nivacreation.login.model.Bus;

import java.util.ArrayList;
import java.util.List;

public class AboutBusActivity extends AppCompatActivity implements BusAdapter.ItemClickListener {
    Button busA, busB, busC, busD, busE, busF, busG, busH, busI, busJ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_bus);


        List<Bus> busList = new ArrayList<>();
        for(int i=0; i<10;i++){
            Bus newBus = new Bus();
            newBus.setBusId("Bus "+(i+1));
            busList.add(newBus);
        }

        RecyclerView budRecyclerView = findViewById(R.id.busRecyclerView);
        budRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        BusAdapter busAdapter = new BusAdapter(this, this, busList);
        budRecyclerView.setAdapter(busAdapter);


        busA = findViewById(R.id.bus_A);
        busB = findViewById(R.id.bus_B);
        busC = findViewById(R.id.bus_C);
        busD = findViewById(R.id.bus_D);
        busE = findViewById(R.id.bus_E);
        busF = findViewById(R.id.bus_F);
        busG = findViewById(R.id.bus_G);
        busH = findViewById(R.id.bus_H);
        busI = findViewById(R.id.bus_I);
        busJ = findViewById(R.id.bus_J);

        busA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(AboutBusActivity.this,BusInsideDetails.class);
                startActivity(i);
            }
        });

        busB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(AboutBusActivity.this,BusInsideDetails.class);
                startActivity(i);
            }
        });

        busC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(AboutBusActivity.this,BusInsideDetails.class);
                startActivity(i);
            }
        });

        busD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(AboutBusActivity.this,BusInsideDetails.class);
                startActivity(i);
            }
        });

        busE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(AboutBusActivity.this,BusInsideDetails.class);
                startActivity(i);
            }
        });

        busF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(AboutBusActivity.this,BusInsideDetails.class);
                startActivity(i);
            }
        });

        busG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(AboutBusActivity.this,BusInsideDetails.class);
                startActivity(i);
            }
        });

        busH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(AboutBusActivity.this,BusInsideDetails.class);
                startActivity(i);
            }
        });

        busI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(AboutBusActivity.this,BusInsideDetails.class);
                startActivity(i);
            }
        });

        busJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(AboutBusActivity.this,BusInsideDetails.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBusItem(String name) {
        // when bus clicked load this screen
        Intent i =  new Intent(AboutBusActivity.this,BusInsideDetails.class);
        i.putExtra("bus_name", name);
        startActivity(i);

    }
}