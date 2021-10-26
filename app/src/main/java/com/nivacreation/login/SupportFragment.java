package com.nivacreation.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.nivacreation.login.adapter.UserSupportAdapter;
import com.nivacreation.login.model.UserSupport;

import java.util.ArrayList;


public class SupportFragment extends Fragment {

    TextView firstNum, secondNum, thirdNum, fourthNum;

    RecyclerView recyclerView, recyclerViewDriver;
    ArrayList<UserSupport> userSupportArrayList, userSupportArrayListDriver;
    UserSupportAdapter userSupportAdapter,userSupportAdapterDriver;
    FirebaseFirestore fStore;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public SupportFragment() {

    }

    public static SupportFragment newInstance(String param1, String param2) {
        SupportFragment fragment = new SupportFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_support, container, false);

        recyclerView = view.findViewById(R.id.userRecycleViewAdminSupport);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerViewDriver = view.findViewById(R.id.userRecycleViewSupportDriver);
        recyclerViewDriver.setHasFixedSize(true);
        recyclerViewDriver.setLayoutManager(new LinearLayoutManager(getActivity()));

        fStore = FirebaseFirestore.getInstance();

        userSupportArrayList = new ArrayList<UserSupport>();

        userSupportAdapter = new UserSupportAdapter(getActivity(),userSupportArrayList);
        recyclerView.setAdapter(userSupportAdapter);

        userSupportArrayListDriver = new ArrayList<UserSupport>();

        userSupportAdapterDriver = new UserSupportAdapter(getActivity(),userSupportArrayListDriver);
        recyclerViewDriver.setAdapter(userSupportAdapterDriver);

        EventChangeListner();

//        firstNum = view.findViewById(R.id.firstNumber);
//        secondNum =view.findViewById(R.id.secondNumber);
//        thirdNum =view.findViewById(R.id.thirdNumber);
//        fourthNum =view.findViewById(R.id.fourthNumber);
//
//        firstNum.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:"+firstNum.getText().toString()));
//                startActivity(intent);
//            }
//        });
//        secondNum.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:"+secondNum.getText().toString()));
//                startActivity(intent);
//            }
//        });
//        thirdNum.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:"+thirdNum.getText().toString()));
//                startActivity(intent);
//            }
//        });
//        fourthNum.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:"+fourthNum.getText().toString()));
//                startActivity(intent);
//            }
//        });

        return view;
    }

    private void EventChangeListner() {
        fStore = FirebaseFirestore.getInstance();

        fStore.collection("Users").orderBy("firstName", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {

                        if(error != null){
                            Log.e("12345",error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()){

                            if (dc.getType() == DocumentChange.Type.ADDED){

                                String userType = dc.getDocument().get("userType").toString();
                                Log.e("12345",userType);
                                if (userType.equals("Admin")){
                                    userSupportArrayList.add(dc.getDocument().toObject(UserSupport.class));
                                }else if (userType.equals("Driver")){
                                    userSupportArrayListDriver.add(dc.getDocument().toObject(UserSupport.class));
                                }

                            }

                            userSupportAdapter.notifyDataSetChanged();
                            userSupportAdapterDriver.notifyDataSetChanged();

                        }
                    }
                });
    }
}