package com.nivacreation.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.nivacreation.login.R;

public class BusInsideDetailsForAdminActivity extends AppCompatActivity {

    TextView busId, startLocation, endLocation, trRoute, driverName, availableSeat;

    int stInt,enInt;

    String travelRoute,stLocation,edLocation1;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_inside_details_for_admin);

        busId = findViewById(R.id.bus_id);
        startLocation = findViewById(R.id.stLocation);
        endLocation = findViewById(R.id.endLocation);
        trRoute = findViewById(R.id.route);
        driverName = findViewById(R.id.driver_name);
        availableSeat = findViewById(R.id.available_seats);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        title = getIntent().getStringExtra("title");
        busId.setText("Bus_"+title.substring(0,6));


        userDetails(title);
    }

    public void userDetails(String title){

        Log.d("12345", "goes Inside userDetails title "+title);

            DocumentReference documentReference = fStore.collection("Users").document(title);
            documentReference.addSnapshotListener( BusInsideDetailsForAdminActivity.this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                    Log.d("12345", "Outside userDetails name "+value.getString("firstName")+" error"+ error);
                    TextView driverName = findViewById(R.id.driver_name);
                    if (value != null && value.exists()) {
                        Log.d("12345", "Inside userDetails title "+title);
                        Log.d("12345", "Inside userDetails name "+value.getString("firstName"));
                        driverName.setText(value.getString("firstName") + " " + value.getString("lastName"));

                        Log.d("12345", "Get driver Name " + value.getString("firstName") + " " + value.getString("lastName"));
                    }

                }
            });


            DocumentReference documentReference1 = fStore.collection("Bus").document(title);
            documentReference1.addSnapshotListener( BusInsideDetailsForAdminActivity.this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {

                    if (value != null && value.exists()) {

                        startLocation.setText(value.getString("from"));
                        endLocation.setText(value.getString("to"));
                        availableSeat.setText(value.getDouble("available seat").toString());
                        trRoute.setText(value.getString("to"));

                        Log.d("12345", "Get driver Name " + value.getString("firstName") + " " + value.getString("lastName"));
                    }

                }
            });

    }
}