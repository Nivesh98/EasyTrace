package com.nivacreation.login;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nivacreation.login.model.AppNotificationChaneel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

public class InboxFragment_Driver extends Fragment implements OnMapReadyCallback {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    List<String> list = new ArrayList<>();

    boolean[] booleans = new boolean[36];

    private String mParam1;
    private String mParam2;

    //chanell
    public  String c1 = "channel1";
    int chStart = 1;
    NotificationManagerCompat notificationManagerCompat;

    String selectedDestination = "";

    boolean isActive = false;

    Spinner spinner_start;

    //firebase
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    String vui;
    StorageReference storageReference;

    final Handler mHandler = new Handler();
    private Thread mUiThread;
    private GoogleMap mMap;

    //map change live location
    private ScheduledExecutorService scheduler;
    private boolean isrun = false;


    private static final int DEFAULT_ZOOM = 18;

    MarkerOptions marker;
    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    LatLng centerlocation;
    private Location lastKnownLocation;
    private CameraPosition cameraPosition;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    Vector<MarkerOptions> markerOptions;

    private boolean locationPermissionGranted;
    private FusedLocationProviderClient fusedLocationProviderClient;


    public InboxFragment_Driver() {
    }

    public static InboxFragment_Driver newInstance(String param1, String param2) {
        InboxFragment_Driver fragment = new InboxFragment_Driver();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox__driver, container, false);

         //Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        getFragmentManager().beginTransaction().add(R.id.framePasssenger,new InboxFragment_Driver()).commit();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapDriver);
        mapFragment.getMapAsync(this);

        //firebase
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        //chanell
        notificationManagerCompat = NotificationManagerCompat.from(getActivity());

        spinner_start = view.findViewById(R.id.spinner_start);

        Button checkBtn = (Button) view.findViewById(R.id.busStatusBtn);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                centerlocation = new LatLng(7.0,80);
                if (savedInstanceState != null) {
                    lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
                    cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
                }
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
                Vector<MarkerOptions> markerOptions = new Vector<>();

                markerOptions.add(new MarkerOptions().title("Kirindiwela")
                        .position(new LatLng(7.042869765280313, 80.12968951041988))
                        .snippet("Kirindiwela Bus Stand").icon(BitmapDescriptorFactory.fromResource(R.drawable.pngwing))
                );
                markerOptions.add(new MarkerOptions().title("Radawana")
                        .position(new LatLng(7.0334659995603435, 80.09290565519682))
                        .snippet("Radawana Bus Stop").icon(BitmapDescriptorFactory.fromResource(R.drawable.pngwing))
                );
                markerOptions.add(new MarkerOptions().title("Henegama")
                        .position(new LatLng(7.022026231737852, 80.05525135700815))
                        .snippet("Henegama Bus Stop").icon(BitmapDescriptorFactory.fromResource(R.drawable.pngwing))
                );
                markerOptions.add(new MarkerOptions().title("Waliweriya")
                        .position(new LatLng(7.032076348406431, 80.02829747222384))
                        .snippet("Waliweriya Bus Stop").icon(BitmapDescriptorFactory.fromResource(R.drawable.pngwing))
                );
                markerOptions.add(new MarkerOptions().title("Nadungamuwa")
                        .position(new LatLng(7.053384345245499, 80.01657207619385))
                        .snippet("Nadungamuwa Bus Stop").icon(BitmapDescriptorFactory.fromResource(R.drawable.pngwing))
                );
                markerOptions.add(new MarkerOptions().title("Mudungoda")
                        .position(new LatLng(7.066219118010275, 80.01296881316584))
                        .snippet("Mudungoda Bus Stop").icon(BitmapDescriptorFactory.fromResource(R.drawable.pngwing))
                );
                markerOptions.add(new MarkerOptions().title("Miriswaththa")
                        .position(new LatLng(7.073011397441073, 80.01599033016919))
                        .snippet("Miriswaththa Bus Stop").icon(BitmapDescriptorFactory.fromResource(R.drawable.pngwing))
                );
                markerOptions.add(new MarkerOptions().title("Gampaha")
                        .position(new LatLng(7.091658180200923, 79.99269939532508))
                        .snippet("Gampaha Bus Stop").icon(BitmapDescriptorFactory.fromResource(R.drawable.pngwing))
                );
                markerOptions.add(new MarkerOptions().title("Ranavirugama 2")
                        .position(new LatLng(7.009945,80.074702))
                        .snippet("Radawana Bus Stand").icon(BitmapDescriptorFactory.fromResource(R.drawable.pngwing))
                );
                markerOptions.add(new MarkerOptions().title("Ranavirugama 1")
                        .position(new LatLng(7.011150,80.075165))
                        .snippet("Radawana Bus Stand").icon(BitmapDescriptorFactory.fromResource(R.drawable.pngwing))
                );

                mMap = googleMap;

                for (MarkerOptions mark : markerOptions){
                    mMap.addMarker(mark);
                }
                getLocationPermission();
                updateLocationUI();
                //TaskRunInCycle();
                selectDestination();
                checkBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(selectedDestination.equals("")){
                            Toast.makeText(getActivity(),"Please Select Destination!",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!isActive){
                            isActive = true;

                            checkBtn.setText("STOP");
                            spinner_start.setEnabled(false);
                            enableBusSeats();
                            //checkBtn.isClickable() = false;
                            Toast.makeText(getActivity(),"Bus Started!",Toast.LENGTH_SHORT).show();
                            // checkBtn.isClickable();
                            scheduler = Executors.newSingleThreadScheduledExecutor();
                            scheduler.scheduleAtFixedRate(new Runnable()
                            {
                                public void run()
                                {
                                    if(isrun){
                                        runOnUiThread(new Runnable(){
                                            @Override
                                            public void run(){
                                                getDeviceLocation();
                                                enableMyLocation();

                                                Toast.makeText(getActivity(), "change location",Toast.LENGTH_SHORT).show();
                                                mMap.clear();
                                                for (MarkerOptions mark : markerOptions){
                                                    mMap.addMarker(mark);
                                                }
                                            }
                                        });
                                    }else{
                                        isrun = true;
                                    }

                                }
                            }, 0, 5, TimeUnit.SECONDS);
                        }else {
                            spinner_start.setEnabled(true);
                            isActive = false;
                            checkBtn.setText("RUN");
                            mMap.clear();
                            for (MarkerOptions mark : markerOptions){
                                mMap.addMarker(mark);
                            }
                            Toast.makeText(getActivity(),"Bus Stopped!",Toast.LENGTH_SHORT).show();
                            // checkBtn.isClickable();

                            String userID ;
                            userID = fAuth.getCurrentUser().getUid();
                            firebaseUploaded(false, userID, null);

                            scheduler.shutdownNow();
                        }
                    }
                });
                // [END_EXCLUDE]

                // Turn on the My Location layer and the related control on the map.


                // Get the current location of the device and set the position of the map.
                //getDeviceLocation();

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerlocation,11));

            }
        });

        return view;
    }




    private void firebaseUploaded(boolean isRun, String userID, Location lastKnownLocation) {
        DocumentReference documentReference = fStore.collection("BusLocations").document(userID);
        Map<String,Object> user = new HashMap<>();
        DocumentReference passedLocation = fStore.collection("PassedLocation").document(userID);
        Map<String,Object> user1 = new HashMap<>();


        if(isRun){
            user.put("lat",lastKnownLocation.getLatitude());
            user.put("log",lastKnownLocation.getLongitude());
            user.put("to",selectedDestination);
            user.put("isStarted","True");
            user.put("userID",userID);
            user.put("location",lastKnownLocation.getBearing());


        }else{
            user.put("to",selectedDestination);
            user.put("isStarted","False");
            user.put("userID",userID);
            user1.put("LocationDescription","");
        }
        passedLocation.set(user1);
        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG,"onSuccess: Success isStarted False! "+ userID);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            String perms[] = {"android.permission.ACCESS_FINE_LOCATION"};
            // Permission to access the location is missing. Show rationale and request permission
            ActivityCompat.requestPermissions(getActivity(), perms,200);
        }
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */

        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {

                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()), DEFAULT_ZOOM));

                                LatLng currentlocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());

                                mMap.addMarker(new MarkerOptions().position(currentlocation).title("current location").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_top_icon)).rotation(lastKnownLocation.getBearing()).anchor((float)0.5,(float)0.5));

                                Toast.makeText(getActivity(), "latitude:" + lastKnownLocation.getLatitude() + " longitude:" +lastKnownLocation.getLongitude(), Toast.LENGTH_SHORT).show();

                                drawCircle(new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude()),2);
                                Toast.makeText(getActivity(), "circle is here", Toast.LENGTH_SHORT).show();

                                String userID ;
                                userID = fAuth.getCurrentUser().getUid();

                                firebaseUploaded(true,userID,lastKnownLocation);

                                getNotification(lastKnownLocation);


                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    private void getNotification(Location lastKnownLocation) {
        if((7.0104 >lastKnownLocation.getLatitude()&&lastKnownLocation.getLatitude()>=7.0100) &&(80.0749 >=lastKnownLocation.getLongitude()&&lastKnownLocation.getLongitude()>=80.0740)){

            String r1 = "Bus is Ranavirugama1 now.";
            Toast.makeText(getActivity(), r1, Toast.LENGTH_SHORT).show();

            notificationLocation(r1);

        }else if ((7.0084 >lastKnownLocation.getLatitude()&&lastKnownLocation.getLatitude()>=7.0082) &&(80.0751 >=lastKnownLocation.getLongitude()&&lastKnownLocation.getLongitude()>=80.0748)){
            String r2 ="Bus is Ranavirugama2 now.";
            Toast.makeText(getActivity(), r2, Toast.LENGTH_SHORT).show();
            notificationLocation(r2);
        }
        else if ((7.00715 >lastKnownLocation.getLatitude()&&lastKnownLocation.getLatitude()>=7.00701) &&(80.07366 >=lastKnownLocation.getLongitude()&&lastKnownLocation.getLongitude()>=80.07351)){
            String  r3 ="Bus is Ranavirugama3 now.";
            Toast.makeText(getActivity(), r3, Toast.LENGTH_SHORT).show();
            notificationLocation(r3);
        }
    }

    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }



    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    //map change live location
    private void TaskRunInCycle() {

    }
    public final void runOnUiThread(Runnable action) {
        if (Thread.currentThread() != mUiThread) {
            mHandler.post(action);
        } else {
            action.run();
        }
    }
    private void drawCircle(LatLng point, double radius_km){
        CircleOptions circleOptions = new CircleOptions();
        //Set center of circle
        circleOptions.center(point);
        //Set radius of circle
        circleOptions.radius(radius_km * 1000);
        //Set border color of circle
        circleOptions.strokeColor(Color.BLUE);
        //Set border width of circle
        circleOptions.strokeWidth(2);
        circleOptions.fillColor(Color.argb(75,94,165,197));
        //Adding circle to map
        Circle mapCircle = mMap.addCircle(circleOptions);
        //We can remove above circle with code bellow
        //mapCircle.remove();

    }

    //Select Destination
    private void selectDestination(){
        ArrayList<String> townList_start = new ArrayList<>();
        townList_start.add("Choose Destination! ");
        townList_start.add("Kirindiwela");
        townList_start.add("Gampaha");


        spinner_start.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,townList_start));

        spinner_start.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0){
                    selectedDestination = "";
                    Toast.makeText(getActivity(),
                            "Select Start Location",Toast.LENGTH_SHORT).show();
                    //start.setText("");
                }else {
                    selectedDestination =  parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void notificationLocation(String r1) {



       //
            AppNotificationChaneel.c1 = "c"+ String.valueOf(AppNotificationChaneel.chStart);
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getActivity());
            Notification notification = new NotificationCompat.Builder(getActivity(),AppNotificationChaneel.c1)
                    .setSmallIcon(R.drawable.bus_icon)
                    .setContentTitle("Bus Current Location")
                    .setContentText(r1)
                    .setSound(uri)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE )
                    .build();
            managerCompat.notify(AppNotificationChaneel.chStart,notification);
            notificationOfFirebase(r1, AppNotificationChaneel.chStart);
      //  }


        AppNotificationChaneel.chStart++;


    }

    private void notificationOfFirebase(String r1, int chStart) {

        String userID ;
        userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("PassedLocation").document(userID);
        Map<String,Object> user = new HashMap<>();

        user.put("LocationDescription",r1);

        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG,"onSuccess: Success isStarted False! "+ userID);
            }
        });
        list.add(r1);
        notificationOfInboxFirebase(userID);

    }

    private void notificationOfInboxFirebase(String userID) {

        DocumentReference documentReference = fStore.collection("BusLocationInbox").document(userID);
        Map<String,Object> user = new HashMap<>();
        for(int i =0; i<list.size(); i++){
            String index = "n"+String.valueOf(i+1);
            user.put(index, list.get(i));
        }

            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d(TAG,"onSuccess: Success isStarted False! "+ userID);
                }
            });

    }

    private void enableBusSeats() {

        Log.d("12345","inside enable bus seats ");
        String userID ;
        userID = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("BusSeats").document(userID);
        Map<String,Object> seats = new HashMap<>();
        for(int i = 0; i<booleans.length; i++){
            booleans[i] =true;
            String seat = "seat"+i;
            seats.put(seat,"");

        }
        documentReference.set(seats).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG,"onSuccess: Success isStarted False! "+ userID);
            }
        });

    }
}