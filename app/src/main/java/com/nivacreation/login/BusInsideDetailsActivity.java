package com.nivacreation.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.nivacreation.login.model.AppNotificationChaneel;

public class BusInsideDetailsActivity extends AppCompatActivity {

    Button bookSeats, getHoltBtn;
    TextView busId, startLocation, endLocation, trRoute, driverName, availableSeats;

    int stInt,enInt;

    String travelRoute,stLocation,edLocation1;

    //chaennel
    NotificationManagerCompat notificationManagerCompat;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_inside_details);

        bookSeats = findViewById(R.id.book_seat);
        getHoltBtn = findViewById(R.id.busHoltBtn);

        busId = findViewById(R.id.bus_id);
        startLocation = findViewById(R.id.stLocation);
        endLocation = findViewById(R.id.endLocation);
        trRoute = findViewById(R.id.route);
        driverName = findViewById(R.id.driver_name);
        availableSeats = findViewById(R.id.available_seats);

        //chanell
        notificationManagerCompat = NotificationManagerCompat.from(BusInsideDetailsActivity.this);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        title = getIntent().getStringExtra("title");
        busId.setText("Bus_"+title.substring(0,6));

        travelRoute = getIntent().getStringExtra("trRoute");
        trRoute.setText(travelRoute);
        stLocation = getIntent().getStringExtra("stLocation");
        startLocation.setText(stLocation);
        edLocation1 = getIntent().getStringExtra("endLocation");
        endLocation.setText(edLocation1);

        stInt = getIntent().getIntExtra("stInt",stInt);
        enInt = getIntent().getIntExtra("enInt",enInt);

        PreferenceManager
                .getDefaultSharedPreferences(this).edit().putString("isAct2", "#").apply();

        Log.d("1111","start "+stInt);
        Log.d("1111","end "+enInt);

        userDetails();

        bookSeats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BusInsideDetailsActivity.this, BookSeatsActivity.class);
                i.putExtra("title",title);
                i.putExtra("stLocation",stLocation);
                i.putExtra("endLocation",edLocation1);
                i.putExtra("stInt",stInt);
                i.putExtra("enInt",enInt);
                i.putExtra("trRoute",travelRoute);
                PreferenceManager
                        .getDefaultSharedPreferences(BusInsideDetailsActivity.this).edit().putString("isAct2", "#").apply();
                startActivity(i);
            }
        });

        getHoltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog dialog = new AlertDialog.Builder(BusInsideDetailsActivity.this)
                        .setTitle("Notification")
                        .setMessage("Do you want to get notifications regarding bus holts?")
                        .setPositiveButton("Yes",null)
                        .setNegativeButton("No",null)
                        .show();
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firebaseGetNotification(title);
                        dialog.dismiss();
                    }
                });

            }
        });


    }

    private void firebaseGetNotification(String title) {
        Log.d("12345", "goes Inside firebaseGetNotification "+title);

            DocumentReference documentReference = fStore.collection("PassedLocation").document(title);
            documentReference.addSnapshotListener( BusInsideDetailsActivity.this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {

                        Log.d("12345", "goes Inside firebaseGetNotification "+value.getString("LocationDescription"));
                        String getHolt = value.getString("LocationDescription");

                            notificationLocation(getHolt);


                    Log.d("12345", "goes Inside firebaseGetNotification "+value.getString("LocationDescription"));

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

        DocumentReference documentReference1 = fStore.collection("Bus").document(title);
        documentReference1.addSnapshotListener( BusInsideDetailsActivity.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {

                if (value != null && value.exists()) {


                    double ff = value.getDouble("available seat");
                    int kk = ((int) ff);
                    availableSeats.setText(String.valueOf(kk));


                    Log.d("12345", "Get driver Name " + value.getString("firstName") + " " + value.getString("lastName"));
                }

            }
        });
    }

    private void notificationLocation(String getHolt) {
        Log.d("12345", "goes Inside notification ");
        if (getHolt != ""){
            //
            AppNotificationChaneel.c1 = "c"+ String.valueOf(AppNotificationChaneel.chStart);
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(BusInsideDetailsActivity.this);
            Notification notification = new NotificationCompat.Builder(BusInsideDetailsActivity.this,AppNotificationChaneel.c1)
                    .setSmallIcon(R.drawable.bus_icon)
                    .setContentTitle("Bus Current Location")
                    .setContentText(getHolt)
                    .setSound(uri)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE )
                    .build();
            managerCompat.notify(AppNotificationChaneel.chStart,notification);
            //  }
            Log.d("12345", "Get Holt " + getHolt);
            AppNotificationChaneel.chStart++;
        }else{
            Log.d("12345", "Get Holt " + getHolt);
        }

    }

}