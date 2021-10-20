package com.nivacreation.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

//import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QAScannerActivity extends AppCompatActivity  {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    boolean isValue = false;

    CodeScanner codeScanner;
    CodeScannerView scannerView;
    TextView resultData;
    DatabaseReference dbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanner);
        scannerView = findViewById(R.id.scannerView);
        codeScanner = new CodeScanner(this, scannerView);
        resultData = findViewById(R.id.txtValue);
        dbRef = FirebaseDatabase.getInstance().getReference("qrData");

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        verifyBusQrCode(result.getText());
                        resultData.setText(result.getText());
                    }
                });
            }
        });
    }

    private void verifyBusQrCode(String text) {

        if (fAuth.getCurrentUser().getUid() != null){

            userId = fAuth.getCurrentUser().getUid();

            fStore.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    Log.d("12345", "is Success"+ task.isSuccessful());
                    if (task.isSuccessful()) {
                        List<String> list = new ArrayList<>();

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            if (document.get("userType") == null) {
                                Log.i("12345", "ssss "+document.getId());
                                return;
                            }

                            String userType = document.get("userType").toString();

                            if (userType.equals("Driver")){

                                if (document.get("userID") == null) {
                                    Log.i("12345", "user Id error Driver "+document.getId());
                                    return;
                                }

                                String driverQrCode = document.get("userID").toString();

                                list.add(driverQrCode);
                                Log.i("12345", "driver qr: "+driverQrCode+" text: "+text);

                                if (text.trim().toString().equals(driverQrCode.trim().toString())) {
                                    Intent i = new Intent(QAScannerActivity.this, BusDetailsQRActivity.class);
                                    startActivity(i);
                                    Log.i("12345", "show buses insides "+document.getId());
                                    return;
                                }
                            }
                        }

                    } else {
                        isValue = false;
                        Log.d("12345", "Error getting documents: ", task.getException());

                    }
                }
            });


        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        requestForCamera();
        //codeScanner.startPreview();

    }
    private void requestForCamera() {
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                codeScanner.startPreview();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(QAScannerActivity.this, "Camera Permission is Required.",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }
//        Dexter.withContext(getApplicationContext())
//                .withPermission(Manifest.permission.CAMERA)
//                .withListener(new PermissionListener() {
//                    @Override
//                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
//                        scannerView.startCamera();
//                    }
//
//                    @Override
//                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
//
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
//                    permissionToken.continuePermissionRequest();
//                    }
//                }).check();
//    }
//
//    @Override
//    public void handleResult(Result rawResult) {
//        String data  = rawResult.getText().toString();
//
//        dbRef.push().setValue(data)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull @NotNull Task<Void> task) {
//                        HomeActivity.valueTxt.setText(" Data Inserted Successfully ! ");
//                        onBackPressed();
//                    }
//                });
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        scannerView.stopCamera();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        scannerView.setResultHandler(this);
//        scannerView.startCamera();

}