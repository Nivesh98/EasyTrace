package com.nivacreation.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class BusInsideDetailsActivity extends AppCompatActivity {

    Button bookSeats;
    TextView busId, startLocation, endLocation, trRoute, driverName;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_inside_details);
        bookSeats = findViewById(R.id.book_seat);

        busId = findViewById(R.id.bus_id);
        startLocation = findViewById(R.id.stLocation);
        endLocation = findViewById(R.id.endLocation);
        trRoute = findViewById(R.id.route);
        driverName = findViewById(R.id.driver_name);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        title = getIntent().getStringExtra("title");
        busId.setText(title);

        String travelRoute = getIntent().getStringExtra("trRoute");
        trRoute.setText(travelRoute);
        String stLocation = getIntent().getStringExtra("stLocation");
        startLocation.setText(stLocation);
        String edLocation = getIntent().getStringExtra("endLocation");
        endLocation.setText(edLocation);

        userDetails();

        bookSeats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BusInsideDetailsActivity.this, BookSeatsActivity.class);
                startActivity(i);
            }
        });
    }

    public void userDetails(){

        Log.d("12345", "goes Inside userDetails ");

        if (fAuth.getCurrentUser().getUid() != null){

            DocumentReference documentReference = fStore.collection("Users").document(title.trim().toString());
            documentReference.addSnapshotListener( BusInsideDetailsActivity.this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {

                    TextView driverName = findViewById(R.id.driver_name);
                    if (value != null && value.exists()) {

                        driverName.setText(value.getString("firstName") + " " + value.getString("lastName"));
                        Log.d("12345", "Get driver Name " + value.getString("firstName") + " " + value.getString("lastName"));
                    }

                }
            });
        }
    }
}