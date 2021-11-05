package com.nivacreation.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class BusInsideDetailsQRActivity extends AppCompatActivity {

    Button selectSeat;

    TextView busId, passengerId, driverName, route;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String dId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_inside_details_qr);

        busId = findViewById(R.id.bus_id);
        passengerId = findViewById(R.id.passengerId);
        driverName = findViewById(R.id.driver_name);
        route = findViewById(R.id.route);

        selectSeat = findViewById(R.id.select_seat);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        String pId = getIntent().getStringExtra("passengerID");
        dId = getIntent().getStringExtra("driverID");
        busId.setText(dId);
        passengerId.setText(pId);

        PreferenceManager
                .getDefaultSharedPreferences(this).edit().putString("isAct1", "1").apply();
        PreferenceManager
                .getDefaultSharedPreferences(this).edit().putString("isAct22", dId).apply();

        selectSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BusInsideDetailsQRActivity.this, BookSeatsActivity.class);
                //i.putExtra("titleQr",dId);
                startActivity(i);
            }
        });

        userDetails();
    }

    public void userDetails(){

        FirebaseUser user = fAuth.getCurrentUser();
        if (fAuth.getCurrentUser().getUid() != null){

            DocumentReference documentReference = fStore.collection("Users").document(dId.trim());
            documentReference.addSnapshotListener( BusInsideDetailsQRActivity.this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {

                    if (value != null && value.exists()) {

                        driverName.setText(value.getString("firstName") + " " + value.getString("lastName"));

                    }

                }
            });
        }

    }
}