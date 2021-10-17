package com.nivacreation.login;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;


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
        return view;
    }
}