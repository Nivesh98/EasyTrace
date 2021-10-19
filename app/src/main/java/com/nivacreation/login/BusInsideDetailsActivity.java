package com.nivacreation.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BusInsideDetailsActivity extends AppCompatActivity {

    Button bookSeats;
    TextView busId, startLocation, endLocation, trRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_inside_details);
        bookSeats = findViewById(R.id.book_seat);

        busId = findViewById(R.id.bus_id);
        startLocation = findViewById(R.id.stLocation);
        endLocation = findViewById(R.id.endLocation);
        trRoute = findViewById(R.id.route);

        String title = getIntent().getStringExtra("title");
        busId.setText(title);

        String travelRoute = getIntent().getStringExtra("trRoute");
        trRoute.setText(travelRoute);
        String stLocation = getIntent().getStringExtra("stLocation");
        startLocation.setText(stLocation);
        String edLocation = getIntent().getStringExtra("endLocation");
        endLocation.setText(edLocation);

        bookSeats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BusInsideDetailsActivity.this, BookSeatsActivity.class);
                startActivity(i);
            }
        });
    }
}