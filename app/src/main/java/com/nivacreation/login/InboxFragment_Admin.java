package com.nivacreation.login;

import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class InboxFragment_Admin extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int DEFAULT_ZOOM = 12;

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
                        .snippet("Open during MCO : 8am - 6pm").icon(BitmapDescriptorFactory.fromResource(R.drawable.pngwing))
                );
                markerOptions.add(new MarkerOptions().title("Radawana")
                        .position(new LatLng(7.0312, 80.1045))
                        .snippet("Open during MCO : 8am - 6pm").icon(BitmapDescriptorFactory.fromResource(R.drawable.pngwing))
                );
                markerOptions.add(new MarkerOptions().title("Henegama")
                        .position(new LatLng(7.02166,80.05446))
                        .snippet("Open during MCO : 8am - 6pm").icon(BitmapDescriptorFactory.fromResource(R.drawable.pngwing))
                );
                markerOptions.add(new MarkerOptions().title("Waliweriya")
                        .position(new LatLng(7.03150,80.02781))
                        .snippet("Open during MCO : 8am - 6pm").icon(BitmapDescriptorFactory.fromResource(R.drawable.pngwing))
                );
                markerOptions.add(new MarkerOptions().title("Nadungamuwa")
                        .position(new LatLng(7.05312,80.01614))
                        .snippet("Open during MCO : 8am - 6pm").icon(BitmapDescriptorFactory.fromResource(R.drawable.pngwing))
                );
                markerOptions.add(new MarkerOptions().title("Mudungoda")
                        .position(new LatLng(7.06615,80.01228))
                        .snippet("Open during MCO : 8am - 6pm").icon(BitmapDescriptorFactory.fromResource(R.drawable.pngwing))
                );
                markerOptions.add(new MarkerOptions().title("Miriswaththa")
                        .position(new LatLng(7.07339,80.01610))
                        .snippet("Open during MCO : 8am - 6pm").icon(BitmapDescriptorFactory.fromResource(R.drawable.pngwing))
                );
                markerOptions.add(new MarkerOptions().title("Gampaha")
                        .position(new LatLng(7.09199,79.99321))
                        .snippet("Open during MCO : 8am - 6pm").icon(BitmapDescriptorFactory.fromResource(R.drawable.pngwing))
                );
                markerOptions.add(new MarkerOptions().title("Ranavirugama 2")
                        .position(new LatLng(7.009945,80.074702))
                        .snippet("Open during MCO : 8am - 6pm").icon(BitmapDescriptorFactory.fromResource(R.drawable.pngwing))
                );
                markerOptions.add(new MarkerOptions().title("Ranavirugama 1")
                        .position(new LatLng(7.011150,80.075165))
                        .snippet("Open during MCO : 8am - 6pm").icon(BitmapDescriptorFactory.fromResource(R.drawable.pngwing))
                );

                mMap = googleMap;

                for (MarkerOptions mark : markerOptions){
                    mMap.addMarker(mark);
                }

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerlocation,11));

            }
        });
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}