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

        ArrayList<String> townList_start = new ArrayList<>();

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
                //getDriverLocation();
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
                if(!stLo.isEmpty()){
                    if(!enLo.isEmpty()){
                        mMap.clear();

                        if((stLo.equals("Kirindiwela") && enLo.equals("Radawana"))||(stLo.equals("Radawana") && enLo.equals("Kirindiwela"))){
                            double kiLat = 7.0427287;
                            double kiLong = 80.1300031;
                            double raLat = 7.0312;
                            double raLong = 80.1045;

                            double comLat = (7.0427287 + 7.0312)/2;
                            double comLong = (80.1300031 + 80.1045)/2;

                            LatLng kirindiwela = new LatLng(7.0427287,80.1300031);
                            mMap.addMarker(new MarkerOptions().position(kirindiwela).title("Kirindiwela"));

                            LatLng radawana = new LatLng(7.0312,80.1045);
                            mMap.addMarker(new MarkerOptions().position(radawana).title("Radawana"));

                            LatLng sydney = new LatLng(comLat,comLong);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,13));
                            getDriverLocation();

                        }else if((stLo.equals("Kirindiwela") && enLo.equals("Henegama"))||(stLo.equals("Henegama") && enLo.equals("Kirindiwela"))){
                            double kiLat = 7.0427287;
                            double kiLong = 80.1300031;
                            double heLat = 7.02166;
                            double heLong = 80.05446;

                            double comLat = (kiLat + heLat)/2;
                            double comLong = (kiLong + heLong)/2;

                            LatLng kirindiwela = new LatLng(7.0427287,80.1300031);
                            mMap.addMarker(new MarkerOptions().position(kirindiwela).title("Kirindiwela"));

                            LatLng radawana = new LatLng(heLat,heLong);
                            mMap.addMarker(new MarkerOptions().position(radawana).title("Henegama"));

                            LatLng sydney = new LatLng(comLat,comLong);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,12));
                            getDriverLocation();
                        }else if ((stLo.equals("Kirindiwela") && enLo.equals("Waliweriya"))||(stLo.equals("Waliweriya") && enLo.equals("Kirindiwela"))){

                            double kiLat = 7.0427287;
                            double kiLong = 80.1300031;
                            double weLat = 7.03150;
                            double weLong = 80.02781;

                            double comLat = (kiLat + weLat)/2;
                            double comLong = (kiLong + weLong)/2;

                            LatLng kirindiwela = new LatLng(7.0427287,80.1300031);
                            mMap.addMarker(new MarkerOptions().position(kirindiwela).title("Kirindiwela"));

                            LatLng weliweriya = new LatLng(weLat,weLong);
                            mMap.addMarker(new MarkerOptions().position(weliweriya).title("Waliweriya"));

                            LatLng sydney = new LatLng(comLat,comLong);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,12));
                            getDriverLocation();
                        }else if ((stLo.equals("Kirindiwela") && enLo.equals("Nadungamuwa"))||(stLo.equals("Nadungamuwa") && enLo.equals("Kirindiwela"))){
                            double kiLat = 7.0427287;
                            double kiLong = 80.1300031;
                            double naLat = 7.05312;
                            double naLong = 80.01614;

                            double comLat = (kiLat + naLat)/2;
                            double comLong = (kiLong + naLong)/2;

                            LatLng kirindiwela = new LatLng(7.0427287,80.1300031);
                            mMap.addMarker(new MarkerOptions().position(kirindiwela).title("Kirindiwela"));

                            LatLng nadungamuwa = new LatLng(naLat,naLong);
                            mMap.addMarker(new MarkerOptions().position(nadungamuwa).title("Nadungamuwa"));

                            LatLng sydney = new LatLng(comLat,comLong);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,11));
                            getDriverLocation();
                        }else if ((stLo.equals("Kirindiwela") && enLo.equals("Mudungoda"))||(stLo.equals("Mudungoda") && enLo.equals("Kirindiwela"))){
                            double kiLat = 7.0427287;
                            double kiLong = 80.1300031;
                            double muLat = 7.05312;
                            double muLong = 80.01614;

                            double comLat = (kiLat + muLat)/2;
                            double comLong = (kiLong + muLong)/2;

                            LatLng kirindiwela = new LatLng(7.0427287,80.1300031);
                            mMap.addMarker(new MarkerOptions().position(kirindiwela).title("Kirindiwela"));

                            LatLng mudungoda = new LatLng(muLat,muLong);
                            mMap.addMarker(new MarkerOptions().position(mudungoda).title("Mudungoda"));

                            LatLng sydney = new LatLng(comLat,comLong);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,11));
                            getDriverLocation();
                        }else if ((stLo.equals("Kirindiwela") && enLo.equals("Miriswaththa"))||(stLo.equals("Miriswaththa") && enLo.equals("Kirindiwela"))) {
                            double kiLat = 7.0427287;
                            double kiLong = 80.1300031;
                            double miLat = 7.07339;
                            double miLong = 80.01610;

                            double comLat = (kiLat + miLat) / 2;
                            double comLong = (kiLong + miLong) / 2;

                            LatLng kirindiwela = new LatLng(7.0427287, 80.1300031);
                            mMap.addMarker(new MarkerOptions().position(kirindiwela).title("Kirindiwela"));

                            LatLng miriswaththa = new LatLng(miLat, miLong);
                            mMap.addMarker(new MarkerOptions().position(miriswaththa).title("Miriswaththa"));

                            LatLng sydney = new LatLng(comLat, comLong);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 11));
                            getDriverLocation();
                        }else if ((stLo.equals("Kirindiwela") && enLo.equals("Gampaha"))||(stLo.equals("Gampaha") && enLo.equals("Kirindiwela"))) {
                            double kiLat = 7.0427287;
                            double kiLong = 80.1300031;
                            double gaLat = 7.09199;
                            double gaLong = 79.99321;

                            double comLat = (kiLat + gaLat) / 2;
                            double comLong = (kiLong + gaLong) / 2;

                            LatLng kirindiwela = new LatLng(7.0427287, 80.1300031);
                            mMap.addMarker(new MarkerOptions().position(kirindiwela).title("Kirindiwela"));

                            LatLng gampaha = new LatLng(gaLat, gaLong);
                            mMap.addMarker(new MarkerOptions().position(gampaha).title("Gampaha"));

                            LatLng sydney = new LatLng(comLat, comLong);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 11));
                            getDriverLocation();
                        }else if ((stLo.equals("Radawana") && enLo.equals("Henegama"))||(stLo.equals("Henegama") && enLo.equals("Radawana"))) {
                            double raLat = 7.0312;
                            double raLong = 80.1045;
                            double heLat = 7.02166;
                            double heLong = 80.05446;

                            double comLat = (raLat + heLat) / 2;
                            double comLong = (raLong + heLong) / 2;

                            LatLng radawana = new LatLng(raLat, raLong);
                            mMap.addMarker(new MarkerOptions().position(radawana).title("Radawana"));

                            LatLng henegama = new LatLng(heLat, heLong);
                            mMap.addMarker(new MarkerOptions().position(henegama).title("Henegama"));

                            LatLng sydney = new LatLng(comLat, comLong);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
                            getDriverLocation();
                        }else if ((stLo.equals("Radawana") && enLo.equals("Waliweriya"))||(stLo.equals("Waliweriya") && enLo.equals("Radawana"))) {
                            double raLat = 7.0312;
                            double raLong = 80.1045;
                            double waLat = 7.03150;
                            double waLong = 80.02781;

                            double comLat = (raLat + waLat) / 2;
                            double comLong = (raLong + waLong) / 2;

                            LatLng radawana = new LatLng(raLat, raLong);
                            mMap.addMarker(new MarkerOptions().position(radawana).title("Radawana"));

                            LatLng Waliweriya = new LatLng(waLat, waLong);
                            mMap.addMarker(new MarkerOptions().position(Waliweriya).title("Waliweriya"));

                            LatLng sydney = new LatLng(comLat, comLong);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));
                            getDriverLocation();
                        }else if ((stLo.equals("Radawana") && enLo.equals("Nadungamuwa"))||(stLo.equals("Nadungamuwa") && enLo.equals("Radawana"))) {
                            double raLat = 7.0312;
                            double raLong = 80.1045;
                            double naLat = 7.05312;
                            double naLong = 80.01614;

                            double comLat = (raLat + naLat) / 2;
                            double comLong = (raLong + naLong) / 2;

                            LatLng radawana = new LatLng(raLat, raLong);
                            mMap.addMarker(new MarkerOptions().position(radawana).title("Radawana"));

                            LatLng Nadungamuwa = new LatLng(naLat, naLong);
                            mMap.addMarker(new MarkerOptions().position(Nadungamuwa).title("Nadungamuwa"));

                            LatLng sydney = new LatLng(comLat, comLong);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));
                            getDriverLocation();
                        }else if ((stLo.equals("Radawana") && enLo.equals("Mudungoda"))||(stLo.equals("Mudungoda") && enLo.equals("Radawana"))) {
                            double raLat = 7.0312;
                            double raLong = 80.1045;
                            double muLat = 7.06615;
                            double muLong = 80.01228;

                            double comLat = (raLat + muLat) / 2;
                            double comLong = (raLong + muLong) / 2;

                            LatLng radawana = new LatLng(raLat, raLong);
                            mMap.addMarker(new MarkerOptions().position(radawana).title("Radawana"));

                            LatLng Mudungoda = new LatLng(muLat, muLong);
                            mMap.addMarker(new MarkerOptions().position(Mudungoda).title("Mudungoda"));

                            LatLng sydney = new LatLng(comLat, comLong);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));
                            getDriverLocation();
                        }else if ((stLo.equals("Radawana") && enLo.equals("Miriswaththa"))||(stLo.equals("Miriswaththa") && enLo.equals("Radawana"))) {
                            double raLat = 7.0312;
                            double raLong = 80.1045;
                            double miLat = 7.07339;
                            double miLong = 80.01610;

                            double comLat = (raLat + miLat) / 2;
                            double comLong = (raLong + miLong) / 2;

                            LatLng radawana = new LatLng(raLat, raLong);
                            mMap.addMarker(new MarkerOptions().position(radawana).title("Radawana"));

                            LatLng Miriswaththa = new LatLng(miLat, miLong);
                            mMap.addMarker(new MarkerOptions().position(Miriswaththa).title("Miriswaththa"));

                            LatLng sydney = new LatLng(comLat, comLong);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 11));
                            getDriverLocation();
                        }else if ((stLo.equals("Radawana") && enLo.equals("Gampaha"))||(stLo.equals("Gampaha") && enLo.equals("Radawana"))) {
                            double raLat = 7.0312;
                            double raLong = 80.1045;
                            double gaLat = 7.09199;
                            double gaLong = 79.99321;

                            double comLat = (raLat + gaLat) / 2;
                            double comLong = (raLong + gaLong) / 2;

                            LatLng radawana = new LatLng(raLat, raLong);
                            mMap.addMarker(new MarkerOptions().position(radawana).title("Radawana"));

                            LatLng Gampaha = new LatLng(gaLat, gaLong);
                            mMap.addMarker(new MarkerOptions().position(Gampaha).title("Gampaha"));

                            LatLng sydney = new LatLng(comLat, comLong);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 11));
                            getDriverLocation();
                        }else if ((stLo.equals("Henegama") && enLo.equals("Waliweriya"))||(stLo.equals("Waliweriya") && enLo.equals("Henegama"))) {
                            double heLat = 7.02166;
                            double heLong = 80.05446;
                            double waLat = 7.03150;
                            double waLong = 80.02781;

                            double comLat = (heLat + waLat) / 2;
                            double comLong = (heLong + waLong) / 2;

                            LatLng Henegama = new LatLng(heLat, heLong);
                            mMap.addMarker(new MarkerOptions().position(Henegama).title("Henegama"));

                            LatLng Waliweriya = new LatLng(waLat, waLong);
                            mMap.addMarker(new MarkerOptions().position(Waliweriya).title("Waliweriya"));

                            LatLng sydney = new LatLng(comLat, comLong);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
                            getDriverLocation();
                        }else if ((stLo.equals("Henegama") && enLo.equals("Nadungamuwa"))||(stLo.equals("Nadungamuwa") && enLo.equals("Henegama"))) {
                            double heLat = 7.02166;
                            double heLong = 80.05446;
                            double naLat = 7.05312;
                            double naLong = 80.01614;

                            double comLat = (heLat + naLat) / 2;
                            double comLong = (heLong + naLong) / 2;

                            LatLng Henegama = new LatLng(heLat, heLong);
                            mMap.addMarker(new MarkerOptions().position(Henegama).title("Henegama"));

                            LatLng Nadungamuwa = new LatLng(naLat, naLong);
                            mMap.addMarker(new MarkerOptions().position(Nadungamuwa).title("Nadungamuwa"));

                            LatLng sydney = new LatLng(comLat, comLong);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
                            getDriverLocation();
                        }else if ((stLo.equals("Henegama") && enLo.equals("Mudungoda"))||(stLo.equals("Mudungoda") && enLo.equals("Henegama"))) {
                            double heLat = 7.02166;
                            double heLong = 80.05446;
                            double muLat = 7.06615;
                            double muLong = 80.01228;

                            double comLat = (heLat + muLat) / 2;
                            double comLong = (heLong + muLong) / 2;

                            LatLng Henegama = new LatLng(heLat, heLong);
                            mMap.addMarker(new MarkerOptions().position(Henegama).title("Henegama"));

                            LatLng Mudungoda = new LatLng(muLat, muLong);
                            mMap.addMarker(new MarkerOptions().position(Mudungoda).title("Mudungoda"));

                            LatLng sydney = new LatLng(comLat, comLong);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
                            getDriverLocation();
                        }else if ((stLo.equals("Henegama") && enLo.equals("Miriswaththa"))||(stLo.equals("Miriswaththa") && enLo.equals("Henegama"))) {
                            double heLat = 7.02166;
                            double heLong = 80.05446;
                            double miLat = 7.07339;
                            double miLong = 80.01610;

                            double comLat = (heLat + miLat) / 2;
                            double comLong = (heLong + miLong) / 2;

                            LatLng Henegama = new LatLng(heLat, heLong);
                            mMap.addMarker(new MarkerOptions().position(Henegama).title("Henegama"));

                            LatLng Miriswaththa = new LatLng(miLat, miLong);
                            mMap.addMarker(new MarkerOptions().position(Miriswaththa).title("Miriswaththa"));

                            LatLng sydney = new LatLng(comLat, comLong);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
                            getDriverLocation();
                        }else if ((stLo.equals("Henegama") && enLo.equals("Gampaha"))||(stLo.equals("Gampaha") && enLo.equals("Henegama"))) {
                            double heLat = 7.02166;
                            double heLong = 80.05446;
                            double gaLat = 7.09199;
                            double gaLong = 79.99321;

                            double comLat = (heLat + gaLat) / 2;
                            double comLong = (heLong + gaLong) / 2;

                            LatLng Henegama = new LatLng(heLat, heLong);
                            mMap.addMarker(new MarkerOptions().position(Henegama).title("Henegama"));

                            LatLng Gampaha = new LatLng(gaLat, gaLong);
                            mMap.addMarker(new MarkerOptions().position(Gampaha).title("Gampaha"));

                            LatLng sydney = new LatLng(comLat, comLong);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));
                            getDriverLocation();
                        }else if ((stLo.equals("Waliweriya") && enLo.equals("Nadungamuwa"))||(stLo.equals("Nadungamuwa") && enLo.equals("Waliweriya"))) {
                            double waLat = 7.03150;
                            double waLong = 80.02781;
                            double naLat = 7.05312;
                            double naLong = 80.01614;

                            double comLat = (waLat + naLat) / 2;
                            double comLong = (waLong + naLong) / 2;

                            LatLng Waliweriya = new LatLng(waLat, waLong);
                            mMap.addMarker(new MarkerOptions().position(Waliweriya).title("Waliweriya"));

                            LatLng Nadungamuwa = new LatLng(naLat, naLong);
                            mMap.addMarker(new MarkerOptions().position(Nadungamuwa).title("Nadungamuwa"));

                            LatLng sydney = new LatLng(comLat, comLong);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
                            getDriverLocation();
                        }else if ((stLo.equals("Waliweriya") && enLo.equals("Mudungoda"))||(stLo.equals("Mudungoda") && enLo.equals("Waliweriya"))) {
                            double waLat = 7.03150;
                            double waLong = 80.02781;
                            double muLat = 7.06615;
                            double muLong = 80.01228;

                            double comLat = (waLat + muLat) / 2;
                            double comLong = (waLong + muLong) / 2;

                            LatLng Waliweriya = new LatLng(waLat, waLong);
                            mMap.addMarker(new MarkerOptions().position(Waliweriya).title("Waliweriya"));

                            LatLng Mudungoda = new LatLng(muLat, muLong);
                            mMap.addMarker(new MarkerOptions().position(Mudungoda).title("Mudungoda"));

                            LatLng sydney = new LatLng(comLat, comLong);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
                            getDriverLocation();
                        }else if ((stLo.equals("Waliweriya") && enLo.equals("Miriswaththa"))||(stLo.equals("Miriswaththa") && enLo.equals("Waliweriya"))) {
                            double waLat = 7.03150;
                            double waLong = 80.02781;
                            double miLat = 7.07339;
                            double miLong = 80.01610;

                            double comLat = (waLat + miLat) / 2;
                            double comLong = (waLong + miLong) / 2;

                            LatLng Waliweriya = new LatLng(waLat, waLong);
                            mMap.addMarker(new MarkerOptions().position(Waliweriya).title("Waliweriya"));

                            LatLng Miriswaththa = new LatLng(miLat, miLong);
                            mMap.addMarker(new MarkerOptions().position(Miriswaththa).title("Miriswaththa"));

                            LatLng sydney = new LatLng(comLat, comLong);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));
                            getDriverLocation();
                        }else if ((stLo.equals("Waliweriya") && enLo.equals("Gampaha"))||(stLo.equals("Gampaha") && enLo.equals("Waliweriya"))) {
                            double waLat = 7.03150;
                            double waLong = 80.02781;
                            double gaLat = 7.09199;
                            double gaLong = 79.99321;

                            double comLat = (waLat + gaLat) / 2;
                            double comLong = (waLong + gaLong) / 2;

                            LatLng Waliweriya = new LatLng(waLat, waLong);
                            mMap.addMarker(new MarkerOptions().position(Waliweriya).title("Waliweriya"));

                            LatLng Gampaha = new LatLng(gaLat, gaLong);
                            mMap.addMarker(new MarkerOptions().position(Gampaha).title("Gampaha"));

                            LatLng sydney = new LatLng(comLat, comLong);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
                            getDriverLocation();
                        }else if ((stLo.equals("Nadungamuwa") && enLo.equals("Mudungoda"))||(stLo.equals("Mudungoda") && enLo.equals("Nadungamuwa"))) {
                            double naLat = 7.05312;
                            double naLong = 80.01614;
                            double muLat = 7.06615;
                            double muLong = 80.01228;

                            double comLat = (naLat + muLat) / 2;
                            double comLong = (naLong + muLong) / 2;

                            LatLng Nadungamuwa = new LatLng(naLat, naLong);
                            mMap.addMarker(new MarkerOptions().position(Nadungamuwa).title("Nadungamuwa"));

                            LatLng Mudungoda = new LatLng(muLat, muLong);
                            mMap.addMarker(new MarkerOptions().position(Mudungoda).title("Mudungoda"));

                            LatLng sydney = new LatLng(comLat, comLong);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));
                            getDriverLocation();
                        }else if ((stLo.equals("Nadungamuwa") && enLo.equals("Miriswaththa"))||(stLo.equals("Miriswaththa") && enLo.equals("Nadungamuwa"))) {
                            double naLat = 7.05312;
                            double naLong = 80.01614;
                            double miLat = 7.07339;
                            double miLong = 80.01610;

                            double comLat = (naLat + miLat) / 2;
                            double comLong = (naLong + miLong) / 2;

                            LatLng Nadungamuwa = new LatLng(naLat, naLong);
                            mMap.addMarker(new MarkerOptions().position(Nadungamuwa).title("Nadungamuwa"));

                            LatLng Miriswaththa = new LatLng(miLat, miLong);
                            mMap.addMarker(new MarkerOptions().position(Miriswaththa).title("Miriswaththa"));

                            LatLng sydney = new LatLng(comLat, comLong);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));
                            getDriverLocation();
                        }else if ((stLo.equals("Nadungamuwa") && enLo.equals("Gampaha"))||(stLo.equals("Gampaha") && enLo.equals("Nadungamuwa"))) {
                            double naLat = 7.05312;
                            double naLong = 80.01614;
                            double gaLat = 7.09199;
                            double gaLong = 79.99321;

                            double comLat = (naLat + gaLat) / 2;
                            double comLong = (naLong + gaLong) / 2;

                            LatLng Nadungamuwa = new LatLng(naLat, naLong);
                            mMap.addMarker(new MarkerOptions().position(Nadungamuwa).title("Nadungamuwa"));

                            LatLng Gampaha = new LatLng(gaLat, gaLong);
                            mMap.addMarker(new MarkerOptions().position(Gampaha).title("Gampaha"));

                            LatLng sydney = new LatLng(comLat, comLong);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14));
                            getDriverLocation();
                        }else if ((stLo.equals("Mudungoda") && enLo.equals("Miriswaththa"))||(stLo.equals("Miriswaththa") && enLo.equals("Mudungoda"))) {
                            double muLat = 7.06615;
                            double muLong = 80.01228;
                            double miLat = 7.07339;
                            double miLong = 80.01610;

                            double comLat = (muLat + miLat) / 2;
                            double comLong = (muLong + miLong) / 2;

                            LatLng Mudungoda = new LatLng(muLat, muLong);
                            mMap.addMarker(new MarkerOptions().position(Mudungoda).title("Mudungoda"));

                            LatLng Miriswaththa = new LatLng(miLat, miLong);
                            mMap.addMarker(new MarkerOptions().position(Miriswaththa).title("Miriswaththa"));

                            LatLng sydney = new LatLng(comLat, comLong);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));
                            getDriverLocation();
                        }else if ((stLo.equals("Mudungoda") && enLo.equals("Gampaha"))||(stLo.equals("Gampaha") && enLo.equals("Mudungoda"))) {
                            double muLat = 7.06615;
                            double muLong = 80.01228;
                            double gaLat = 7.09199;
                            double gaLong = 79.99321;

                            double comLat = (muLat + gaLat) / 2;
                            double comLong = (muLong + gaLong) / 2;

                            LatLng Mudungoda = new LatLng(muLat, muLong);
                            mMap.addMarker(new MarkerOptions().position(Mudungoda).title("Mudungoda"));

                            LatLng Gampaha = new LatLng(gaLat, gaLong);
                            mMap.addMarker(new MarkerOptions().position(Gampaha).title("Gampaha"));

                            LatLng sydney = new LatLng(comLat, comLong);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14));
                            getDriverLocation();
                        }else if ((stLo.equals("Miriswaththa") && enLo.equals("Gampaha"))||(stLo.equals("Gampaha") && enLo.equals("Miriswaththa"))) {
                            double miLat = 7.07339;
                            double miLong = 80.01610;
                            double gaLat = 7.09199;
                            double gaLong = 79.99321;

                            double comLat = (miLat + gaLat) / 2;
                            double comLong = (miLong + gaLong) / 2;

                            LatLng Miriswaththa = new LatLng(miLat, miLong);
                            mMap.addMarker(new MarkerOptions().position(Miriswaththa).title("Miriswaththa"));

                            LatLng Gampaha = new LatLng(gaLat, gaLong);
                            mMap.addMarker(new MarkerOptions().position(Gampaha).title("Gampaha"));

                            LatLng sydney = new LatLng(comLat, comLong);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14));
                            getDriverLocation();
                        }

                    }else{
                        Toast.makeText(PassengerFindMap.this,"Please Enter Valid Location! ",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(PassengerFindMap.this,"Please Enter Valid Location! ",Toast.LENGTH_SHORT).show();
                    //startLocation.setError("Please Select Start Location");
                }
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

                                            Intent i = new Intent(PassengerFindMap.this,BusInsideDetailsActivity.class);
                                            i.putExtra("title",markertitle);
                                            i.putExtra("stLocation",stlo);
                                            i.putExtra("endLocation",enlo);
                                            i.putExtra("trRoute",trRoute);
                                            startActivity(i);
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