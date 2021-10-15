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

    }

    @Override
    public void onBusItem(String name) {
        // when bus clicked load this screen
        Intent i =  new Intent(AboutBusActivity.this,BusInsideDetails.class);
        i.putExtra("bus_name", name);
        startActivity(i);

    }
}