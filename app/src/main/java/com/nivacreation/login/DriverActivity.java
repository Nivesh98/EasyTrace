package com.nivacreation.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class DriverActivity extends AppCompatActivity {
    TextView userFullNameTxt, userEmailTxt, userTypeTxt;
    Button logOutBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId, sampleQRuserID;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        userEmailTxt = findViewById(R.id.txtUserEmail);
        userFullNameTxt = findViewById(R.id.txtUserFullName);
        userTypeTxt = findViewById(R.id.txtUserType);
        toolbar = findViewById(R.id.toolBar_logout);

        logOutBtn = findViewById(R.id.logout);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        //QR Genareter im below

        sampleQRuserID = fAuth.getCurrentUser().getUid();
        ImageView barcode = findViewById(R.id.bar_code);
        String data_in_code = "Hello Bar code Data";
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        HomeActivity homeActivity = new HomeActivity();

        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(sampleQRuserID, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            barcode.setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }

        //QR genarater inabove

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();
                //startActivity(new Intent(getApplicationContext(),SignInActivity.class));
                Intent goPassengerActivity = new Intent(DriverActivity.this,SignInActivity.class);
                goPassengerActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                goPassengerActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(goPassengerActivity);
                finish();
            }
        });
        setSupportActionBar(toolbar);

        userDetails();
    }

    public void userDetails(){
        FirebaseUser user = fAuth.getCurrentUser();
        if (fAuth.getCurrentUser().getUid() != null){

                userId = fAuth.getCurrentUser().getUid();

                DocumentReference documentReference = fStore.collection("Users").document(userId);
                documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        if (value != null && value.exists()){
                            userEmailTxt.setText(value.getString("email"));
                            userFullNameTxt.setText(value.getString("firstName")+" "+value.getString("lastName"));
                            userTypeTxt.setText(value.getString("userType"));
                        }
                    }
                });


        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return  super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this,SignInActivity.class));
                finish();
        }
        return true;
    }
}