package com.nivacreation.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nivacreation.login.databinding.ActivityPassengerFindMapBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.nivacreation.login.MainActivity.TAG;

public class PassengerFindMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityPassengerFindMapBinding binding;

    private ScheduledExecutorService scheduler;
    private boolean isrun = false;
    final Handler mHandler = new Handler();
    private Thread mUiThread;

    String selectedDestinationStart = "";
    String selectedDestinationEnd = "";
    boolean isActive = false;

    ArrayList<LatLng> destinationLatLong;
    ArrayList<String> townList_end;
    ArrayList<String> townList_start;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    Double lat;
    Double log;

    int startPoint=0, endPoint =0;

    Spinner spinner_start, spinner_end;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPassengerFindMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        scheduler = Executors.newSingleThreadScheduledExecutor();
        //selectLocation();
       // getDriverLocation();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        busLocation();
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    private void selectLocation() {
        //        editText = findViewById(R.id.Date);
//        editText1= findViewById(R.id.Time);
        spinner_start = findViewById(R.id.spinner_start);
        spinner_end = findViewById(R.id.spinner_end);
        //start = findViewById(R.id.txt_start);
        //end = findViewById(R.id.txt_end);
        submit = findViewById(R.id.btnSubmit);

        townList_start = new ArrayList<>();

        townList_start.add("Select Start Location");
        townList_start.add("Kirindiwela");
        townList_start.add("Radawana");
        townList_start.add("Henegama");
        townList_start.add("Waliweriya");
        townList_start.add("Nadungamuwa");
        townList_start.add("Miriswaththa");
        townList_start.add("Mudungoda");
        townList_start.add("Gampaha");


        spinner_start.setAdapter(new ArrayAdapter<>(PassengerFindMap.this, android.R.layout.simple_spinner_dropdown_item,townList_start));

        spinner_start.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0){
                    selectedDestinationStart ="";
                    //Toast.makeText(getApplicationContext(), "Select Start Location",Toast.LENGTH_SHORT).show();
                    //start.setText("");
                }else {
                    startPoint = position;
                    selectedDestinationStart = parent.getItemAtPosition(position).toString();
                    //start.setText(STown);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //.................End Location..................

        townList_end = new ArrayList<>();

        townList_end.add("Select End Location");
        townList_end.add("Kirindiwela");
        townList_end.add("Radawana");
        townList_end.add("Henegama");
        townList_end.add("Waliweriya");
        townList_end.add("Nadungamuwa");
        townList_end.add("Miriswaththa");
        townList_end.add("Mudungoda");
        townList_end.add("Gampaha");

        //LatLong
        //Kirindiwela
        LatLng Kirindiwela = new LatLng( 7.042869765280313, 80.12968951041988);

        //Radawana
        LatLng Radawana = new LatLng(7.0334659995603435, 80.09290565519682);

        //Henegama
        LatLng Henegama = new LatLng(7.022026231737852, 80.05525135700815);

        //Waliweriya
        LatLng Waliweriya = new LatLng(7.032076348406431, 80.02829747222384);

        //Nadungamuwa
        LatLng Nadungamuwa = new LatLng(7.053384345245499, 80.01657207619385);

        //Mudungoda
        LatLng Mudungoda = new LatLng(7.066219118010275, 80.01296881316584);

        //Miriswaththa
        LatLng Miriswaththa = new LatLng(7.073011397441073, 80.01599033016919);

        //Gampaha
        LatLng Gampaha = new LatLng(7.091658180200923, 79.99269939532508);

        destinationLatLong = new ArrayList<>();

        destinationLatLong.add(Kirindiwela);
        destinationLatLong.add(Kirindiwela);
        destinationLatLong.add(Radawana);
        destinationLatLong.add(Henegama);
        destinationLatLong.add(Waliweriya);
        destinationLatLong.add(Nadungamuwa);
        destinationLatLong.add(Mudungoda);
        destinationLatLong.add(Miriswaththa);
        destinationLatLong.add(Gampaha);


        spinner_end.setAdapter(new ArrayAdapter<>(PassengerFindMap.this, android.R.layout.simple_spinner_dropdown_item,townList_end));

        spinner_end.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0){
                    selectedDestinationEnd ="";
                    //Toast.makeText(getApplicationContext(),"Select End Location",Toast.LENGTH_SHORT).show();
                    //end.setText("");
                }else {
                    endPoint = position;
                    selectedDestinationEnd = parent.getItemAtPosition(position).toString();
                    //end.setText(ETown);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    private void busLocation() {

        selectLocation();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String stLo = spinner_start.getSelectedItem().toString();
                String enLo = spinner_end.getSelectedItem().toString();

                if(selectedDestinationStart.equals("")){
                    Toast.makeText(PassengerFindMap.this,"Please Select StartPoint!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(selectedDestinationEnd.equals("")){
                    Toast.makeText(PassengerFindMap.this,"Please Select EndPoint!",Toast.LENGTH_SHORT).show();
                    return;
                }

                mMap.clear();
                            mMap.addMarker(new MarkerOptions().position(destinationLatLong.get(startPoint)).title(stLo));
                            mMap.addMarker(new MarkerOptions().position(destinationLatLong.get(endPoint)).title(enLo));

                            int difer = startPoint-endPoint;

                            if(difer>=-4 && difer<=4){
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destinationLatLong.get(startPoint),12));
                            }else {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destinationLatLong.get(startPoint),11));
                            }


                            getDriverLocation();

            }


        });
    }

    private void getDriverLocation() {


        if (fAuth.getCurrentUser().getUid() != null){

            userId = fAuth.getCurrentUser().getUid();

            fStore.collection("BusLocations").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    Log.d("12345", "is Success"+ task.isSuccessful());
                    if (task.isSuccessful()) {
                        List<String> list = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            if (document.get("isStarted") == null) {
                                Log.i("12345", "ssss "+document.getId());
                                return;
                            }

                            String isStarted = document.get("isStarted").toString();

                            if (isStarted.equals("True")){

                                String direction = document.get("to").toString();
                                String parengerDirection = startPoint>endPoint? "Kiridiwala" : "Gampaha";

                                if (direction.equals(parengerDirection)) { //Gampaha -> kiridiwala
                                    Log.i("12345", "show buses "+document.getId());
                                    double lat = document.getDouble("lat");
                                    double log = document.getDouble("log");
                                    double location = document.getDouble("location");
                                    float f = (float) location;
                                    String userId = document.get("userID").toString();
                                    String getUID = document.getId();

                                    LatLng Miriswaththa = new LatLng(lat, log);
                                    mMap.addMarker(new MarkerOptions().position(Miriswaththa).title(userId) .snippet("Bus Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_top_icon)).rotation(f).anchor((float)0.5,(float)0.5));

                                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                        @Override
                                        public boolean onMarkerClick(Marker marker) {

                                            String markertitle = marker.getTitle();
                                            String stlo = spinner_start.getSelectedItem().toString();
                                            String enlo = spinner_end.getSelectedItem().toString();
                                            String trRoute = parengerDirection;

                                            if (!townList_end.contains(markertitle)){
                                                Intent i = new Intent(PassengerFindMap.this,BusInsideDetailsActivity.class);
                                                i.putExtra("title",markertitle);
                                                i.putExtra("stLocation",stlo);
                                                i.putExtra("endLocation",enlo);
                                                i.putExtra("trRoute",trRoute);
                                                startActivity(i);
                                            }
                                            return false;
                                        }
                                    });

                                }
                            }

                        }

                    } else {
                        Log.d("12345", "Error getting documents: ", task.getException());
                    }
                }
            });


        }
    }

}