package com.nivacreation.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.squareup.picasso.Picasso;


public class HomeFragment_Admin extends Fragment {

    private static final int RESULT_OK = -1;
    TextView userFullNameTxt, userEmailTxt, userTypeTxt;
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

    public HomeFragment_Admin() {

    }

    public static HomeFragment_Admin newInstance(String param1, String param2) {
        HomeFragment_Admin fragment = new HomeFragment_Admin();
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
        View view = inflater.inflate(R.layout.fragment_home__admin, container, false);

        userFullNameTxt = view.findViewById(R.id.txtUserFullName);
        userTypeTxt = view.findViewById(R.id.txtUserType);

        logOutBtn = view.findViewById(R.id.logout);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //image profile
        userImageP = view.findViewById(R.id.imageProfile_home_Admin);
        storageReference = FirebaseStorage.getInstance().getReference();

        ImageView userImageP = view.findViewById(R.id.imageProfile_home_Admin);
        String userId = fAuth.getCurrentUser().getUid();

        StorageReference profileRef = storageReference.child("user profile Admin").child(userId +".jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(userImageP);
            }
        });

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

    public void userDetails(){

        FirebaseUser user = fAuth.getCurrentUser();
        if (fAuth.getCurrentUser().getUid() != null){

            userId = fAuth.getCurrentUser().getUid();

            DocumentReference documentReference = fStore.collection("Users").document(userId);
            documentReference.addSnapshotListener( getActivity(), new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {

                    if (value != null && value.exists()) {

                        //userEmailTxt.setText(value.getString("email"));
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