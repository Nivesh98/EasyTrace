package com.nivacreation.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class SplashScreenActivity extends AppCompatActivity {

    private static  int SPLASH_SCREEN = 4000;
    Animation topAnim, bottomAnim;
    ImageView logoImg, textImg;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    String userId;
    public String verifyUserType;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_splash_screen);

        topAnim = AnimationUtils.loadAnimation(this,R.animator.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.animator.bottom_animation);

        logoImg = findViewById(R.id.imageLogo);
        textImg = findViewById(R.id.imageText);

        logoImg.setAnimation(topAnim);
        textImg.setAnimation(bottomAnim);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        String isShow = PreferenceManager
                .getDefaultSharedPreferences(this).getString("isShow", "Empty");

       // Toast.makeText(this, "iswelcome load "+isShow, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isShow.equals("1")){
                    if (mAuth.getCurrentUser() != null){
                        findUserType();
                    }else{
                        Intent goSplash = new Intent(SplashScreenActivity.this,SignInActivity.class);
                        startActivity(goSplash);
                        finish();
                    }

                }else{
                    Intent goSplash = new Intent(SplashScreenActivity.this, WelcomeActivity.class);
                    startActivity(goSplash);
                    finish();
                }
            }
        },SPLASH_SCREEN);
    }

    private void findUserType() {

        userId = mAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("Users").document(userId);
        documentReference.addSnapshotListener(SplashScreenActivity.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {

                if (value != null && value.exists()) {
                    verifyUserType = value.getString("userType");
                    switch (verifyUserType) {
                        case "Passenger":
                            //Toast.makeText(SignInActivity.this, "Login Successfully !!", Toast.LENGTH_SHORT).show();
                            // startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                            Intent goPassengerActivity = new Intent(SplashScreenActivity.this, PassengerNavigationActivity.class);
                            goPassengerActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            goPassengerActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            startActivity(goPassengerActivity);
                            finish();
                            break;
                        case "Driver":
                            //Toast.makeText(SignInActivity.this, "Login Successfully !!", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(SignInActivity.this, Driver.class));
                            Intent goPassengerActivity2 = new Intent(SplashScreenActivity.this, DriverNavigationActivity.class);
                            goPassengerActivity2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            goPassengerActivity2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            startActivity(goPassengerActivity2);
                            finish();
                            break;
                        case "Admin":
                            //Toast.makeText(SignInActivity.this, "Login Successfully !!", Toast.LENGTH_SHORT).show();
                            Intent goPassengerActivity3 = new Intent(SplashScreenActivity.this, AdminNavigationActivity.class);
                            goPassengerActivity3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            goPassengerActivity3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            startActivity(goPassengerActivity3);
                            finish();
                            break;

                    }
                }
            }
        });


    }
}