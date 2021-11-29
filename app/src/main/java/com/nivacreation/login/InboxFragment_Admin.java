package com.nivacreation.login;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nivacreation.login.model.BusInsideDetailsForAdminActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class InboxFragment_Admin extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int DEFAULT_ZOOM = 12;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    Double lat;
    Double log;

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

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public InboxFragment_Admin() {

    }

    public static InboxFragment_Admin newInstance(String param1, String param2) {
        InboxFragment_Admin fragment = new InboxFragment_Admin();
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
        View view = inflater.inflate(R.layout.fragment_inbox__admin, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapAdmin);
        mapFragment.getMapAsync(this);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

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
                        .position(new LatLng(7.0427287,80.1300031))
                        .snippet("Open during MCO : 8am - 6pm").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_stop))
                );
                markerOptions.add(new MarkerOptions().title("Radawana")
                        .position(new LatLng(7.0334659995603435, 80.09290565519682))
                        .snippet("Open during MCO : 8am - 6pm").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_stop))
                );
                markerOptions.add(new MarkerOptions().title("Henegama")
                        .position(new LatLng(7.022026231737852, 80.05525135700815))
                        .snippet("Open during MCO : 8am - 6pm").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_stop))
                );
                markerOptions.add(new MarkerOptions().title("Waliweriya")
                        .position(new LatLng(7.032076348406431, 80.02829747222384))
                        .snippet("Open during MCO : 8am - 6pm").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_stop))
                );
                markerOptions.add(new MarkerOptions().title("Nadungamuwa")
                        .position(new LatLng(7.05312,80.01614))
                        .snippet("Open during MCO : 8am - 6pm").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_stop))
                );
                markerOptions.add(new MarkerOptions().title("Mudungoda")
                        .position(new LatLng(7.06615,80.01228))
                        .snippet("Open during MCO : 8am - 6pm").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_stop))
                );
                markerOptions.add(new MarkerOptions().title("Miriswaththa")
                        .position(new LatLng(7.07339,80.01610))
                        .snippet("Open during MCO : 8am - 6pm").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_stop))
                );
                markerOptions.add(new MarkerOptions().title("Gampaha")
                        .position(new LatLng(7.09199,79.99321))
                        .snippet("Open during MCO : 8am - 6pm").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_stop))
                );
                mMap = googleMap;

                for (MarkerOptions mark : markerOptions){
                    mMap.addMarker(mark);
                }

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerlocation,12));

            }
        });
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        busLocation();
    }

    private void busLocation() {

        mMap.clear();
        getDriverLocation();

    }
    private void getDriverLocation() {
        Log.i("12345", "inside getDevice location");

        if (fAuth.getCurrentUser().getUid() != null){
            Log.i("12345", "user not null");
            userId = fAuth.getCurrentUser().getUid();

            fStore.collection("BusLocations").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    Log.i("12345", "is Success"+ task.isSuccessful());
                    if (task.isSuccessful()) {
                        List<String> list = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            if (document.get("isStarted") == null) {
                                Log.i("12345", "ssss "+document.getId());
                                return;
                            }

                            String isStarted = document.get("isStarted").toString();

                            if (isStarted.equals("True")){

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

                                            if (markertitle.contains("Kirindiwela")||markertitle.contains("Radawana")||markertitle.contains("Henegama")||markertitle.contains("Waliweriya")
                                                    ||markertitle.contains("Nadungamuwa")||markertitle.contains("Mudungoda")||markertitle.contains("Miriswaththa")||markertitle.contains("Gampaha")){

                                            }else{

                                                Intent i = new Intent(getActivity(), BusInsideDetailsForAdminActivity.class);
                                                startActivity(i);
//                                            if (!townList_end.contains(markertitle)){
//                                                Intent i = new Intent(PassengerFindMap.this,BusInsideDetailsActivity.class);
//                                                i.putExtra("title",markertitle);
//                                                i.putExtra("stLocation",stlo);
//                                                i.putExtra("stInt",startPoint);
//                                                i.putExtra("enInt",endPoint);
//                                                i.putExtra("endLocation",enlo);
//                                                i.putExtra("trRoute",trRoute);
//                                                startActivity(i);
//                                            }
                                            }

                                            return false;
                                        }
                                    });


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