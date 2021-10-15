package com.nivacreation.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BusInsideDetailsActivity extends AppCompatActivity {

    Button bookSeats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_inside_details);
        bookSeats = findViewById(R.id.book_seat);

        bookSeats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BusInsideDetailsActivity.this, BookSeatsActivity.class);
                startActivity(i);
            }
        });
    }
}