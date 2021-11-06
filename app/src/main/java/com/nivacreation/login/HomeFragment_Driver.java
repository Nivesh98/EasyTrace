package com.nivacreation.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static com.nivacreation.login.MainActivity.TAG;

public class HomeFragment_Driver extends Fragment {

    private static final int RESULT_OK = -1;
    TextView userFullNameTxt, userEmailTxt, userTypeTxt, seats, availableSeats, from, to;
    Button logOutBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId, sampleQRuserID;
    Toolbar toolbar;

    private Uri imageUri;
    private Bitmap compressor;
    ImageView userImageP;
    StorageReference storageReference;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment_Driver() {
    }

    public static HomeFragment_Driver newInstance(String param1, String param2) {
        HomeFragment_Driver fragment = new HomeFragment_Driver();
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

        View view = inflater.inflate(R.layout.fragment_home__driver, container, false);

        userEmailTxt = view.findViewById(R.id.txtUserEmail);
        userFullNameTxt = view.findViewById(R.id.txtUserFullName);
        userTypeTxt = view.findViewById(R.id.txtUserType);
        toolbar = view.findViewById(R.id.toolBar_logout);
        seats =view.findViewById(R.id.seats);
        availableSeats =view.findViewById(R.id.availableSeats);
        from =view.findViewById(R.id.from);
        to =view.findViewById(R.id.to);

        logOutBtn = view.findViewById(R.id.logout);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //get details about seats
        String uId =fAuth.getCurrentUser().getUid();
        getDeatilsBusSeats(seats,availableSeats,from,to, uId);

        //QR Genareter im below

        sampleQRuserID = fAuth.getCurrentUser().getUid();
        ImageView barcode = view.findViewById(R.id.bar_code);
        String data_in_code = "Hello Bar code Data";
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        //image profile
        userImageP = view.findViewById(R.id.imageProfile_home_Driver);
        storageReference = FirebaseStorage.getInstance().getReference();

        ImageView userImageP = view.findViewById(R.id.imageProfile_home_Driver);
        String userId = fAuth.getCurrentUser().getUid();

        StorageReference profileRef = storageReference.child("user profile Driver").child(userId +".jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Picasso.get().load(uri).into(userImageP);
            }
        });


        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(sampleQRuserID, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            barcode.setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }

        //QR genarater inabove

        userDetails();



        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();
                Intent signInActivity = new Intent(getActivity(), SignInActivity.class);
                signInActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(signInActivity);
            }
        });

        return view;
    }

    private void getDeatilsBusSeats(TextView seats, TextView availableSeats, TextView from, TextView to, String uId) {

        String seat = seats.getText().toString();
        String ava = availableSeats.getText().toString();
        String fro = from.getText().toString();
        String t = to.getText().toString();

        DocumentReference documentReference = fStore.collection("Bus").document(uId);
        documentReference.addSnapshotListener( getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {

                if (value != null && value.exists()) {

                    seats.setText(value.get("seat").toString());
                    availableSeats.setText(value.get("available seat").toString());
                    from.setText(value.getString("from"));
                    to.setText(value.getString("to"));


                }

            }
        });


    }


    public void userDetails(){

        FirebaseUser user = fAuth.getCurrentUser();
        if (fAuth.getCurrentUser().getUid() != null){

            userId = fAuth.getCurrentUser().getUid();

            DocumentReference documentReference = fStore.collection("Users").document(userId);
            documentReference.addSnapshotListener( getActivity(), new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {

                    if (value != null && value.exists()) {

                        userEmailTxt.setText(value.getString("email"));
                        userFullNameTxt.setText(value.getString("firstName") + " " + value.getString("lastName"));
                        userTypeTxt.setText(value.getString("userType"));

//                        String name = value.getString("First Name") + " " + value.getString("Last Name");
//                        Intent intent = new Intent(getActivity(),layout_header.class);
//                        intent.putExtra("key",name);
//                        Toast.makeText(getActivity(),"this is complete"  + name,Toast.LENGTH_LONG).show();
//                        startActivity(intent);

                    }

                }
            });
        }
    }
}