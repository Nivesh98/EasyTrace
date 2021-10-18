package com.nivacreation.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.jetbrains.annotations.NotNull;

//import id.zelory.compressor.Compressor;

public class PassengerNavigationActivity extends AppCompatActivity {

   // private ImageView userImage;
    private Uri imageUri;
    Bitmap compressor;
    private ProgressDialog progressDialog;
    ImageView userImage;


    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    StorageReference storageReference;
    String userId;
    String vui;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_navigation);

        progressDialog = new ProgressDialog(this);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        userId = fAuth.getCurrentUser().getUid();

        final DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);

        findViewById(R.id.image_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        View headerView = navigationView.getHeaderView(0);

        ImageView userImage = (ImageView) headerView.findViewById(R.id.imageProfile);

        StorageReference profileRef = storageReference.child("user profile").child(userId +".jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(userImage);
            }
        });

        userImage();

        userDetails();



        NavController navController = Navigation.findNavController(this,R.id.navHostFragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        final TextView textTitle = findViewById(R.id.textTitle);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull @NotNull NavController controller, @NonNull @NotNull NavDestination destination, @Nullable @org.jetbrains.annotations.Nullable Bundle arguments) {
                textTitle.setText(destination.getLabel());
            }
        });
  }

    public void userDetails(){

        FirebaseUser user = fAuth.getCurrentUser();
        if (fAuth.getCurrentUser().getUid() != null){

            userId = fAuth.getCurrentUser().getUid();

            DocumentReference documentReference = fStore.collection("Users").document(userId);
            documentReference.addSnapshotListener( this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {

                    if (value != null && value.exists()) {

                        NavigationView navigationView = findViewById(R.id.navigationView);
                        navigationView.setItemIconTintList(null);

                        View headerView = navigationView.getHeaderView(0);
                        TextView navUserName = (TextView) headerView.findViewById(R.id.userName);

                       navUserName.setText(value.getString("firstName") + " " + value.getString("lastName"));

                    }

                }
            });
        }
    }

    public void userImage(){
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        View headerView = navigationView.getHeaderView(0);

        ImageView userImage = (ImageView) headerView.findViewById(R.id.imageProfile);

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if (ContextCompat.checkSelfPermission(PassengerNavigationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(PassengerNavigationActivity.this,"Permission Denied", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(PassengerNavigationActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                    }else {

                        ChoseImage();
                    }
                }else{

                    ChoseImage();
                }
            }
        });
//
//        progressDialog.setMessage("Storing data...");
//        progressDialog.show();
//
//        if (imageUri != null){
//
//            File newfile = new File(imageUri.getPath());
//            try {
//                compressor = new Compressor(Passenger_Navigation.this)
//                        .setMaxHeight(125)
//                        .setMaxWidth(125)
//                        .setQuality(50)
//                        .compressToBitmap(newfile);
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            compressor.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
//            byte[] thumb = byteArrayOutputStream.toByteArray();
//
//            UploadTask image_path = storageReference.child("user_image").child(userId +".jpg").putBytes(thumb);
//
//            image_path.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onComplete(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) {
//
//                    if (task.isSuccessful()){
//
//                        storeUserData(task);
//
//                    }else {
//                        String error = task.getException().getMessage();
//                        Toast.makeText(Passenger_Navigation.this,"(Image Error :)" +error,Toast.LENGTH_SHORT).show();
//                        progressDialog.dismiss();
//                    }
//                }
//            });
//        }else {
//            Toast.makeText(Passenger_Navigation.this, "Upload image", Toast.LENGTH_SHORT).show();
//        }
//
//
//    }
//
//    private void storeUserData(Task<UploadTask.TaskSnapshot> task) {
//
//        Uri download_uri;
//        if (task !=null){
//            download_uri = task.getResult().getUploadSessionUri();
//        }else{
//            download_uri = imageUri;
//        }
//
//        Map<String,String> userData = new HashMap<>();
//        userData.put("User Image : ",download_uri.toString());
//
//        fStore.collection("Users").document(userId).set(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull @NotNull Task<Void> task) {
//
//                if (task.isSuccessful()){
//
//                    progressDialog.dismiss();
//                    Toast.makeText(Passenger_Navigation.this,"Successfully :"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
//
//                }else {
//                    Toast.makeText(Passenger_Navigation.this,"Firebse Error :"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });
    }

    private void ChoseImage() {

        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1,1).start(PassengerNavigationActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            progressDialog.setMessage("Storing data...");

            if (resultCode==RESULT_OK){
                imageUri = result.getUri();
                //userImage.setImageURI(imageUri);
                progressDialog.show();
                uploadImageToFirebase(imageUri);
                //progressDialog.dismiss();
            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
                progressDialog.dismiss();
            }
            //progressDialog.dismiss();
        }else{
            progressDialog.dismiss();
        }
        

    }

    private void uploadImageToFirebase(Uri imageUri) {
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        View headerView = navigationView.getHeaderView(0);

        ImageView userImage = (ImageView) headerView.findViewById(R.id.imageProfile);

        StorageReference fileRef = storageReference.child("user profile").child(userId +".jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(userImage);
                    }
                });
                Toast.makeText(PassengerNavigationActivity.this,"Image Uploaded !",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(PassengerNavigationActivity.this,"Failed !",Toast.LENGTH_SHORT).show();
            }
        });
    }

}