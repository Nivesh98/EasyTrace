package com.nivacreation.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.nivacreation.login.adapter.BusAdapter;
import com.nivacreation.login.adapter.TransactionAdapter;
import com.nivacreation.login.model.Bus;
import com.nivacreation.login.model.TransactionDetails;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment implements TransactionAdapter.ItemClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;

    public NotificationFragment() {

    }

    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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

        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        SwipeButton swipeButton = view.findViewById(R.id.swipeBtn);

        FirebaseApp.initializeApp(getActivity());
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        String userID = mAuth.getCurrentUser().getUid();

        firebaseTransaction(userID);

        List<TransactionDetails> transactionList = new ArrayList<>();
        DocumentReference documentReference = fStore.collection("User Booking").document(userID);
        documentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {

                if (value != null && value.exists()) {

                    String stLoEnLo = value.getString("stLocation");
                    String enLoca = value.getString("enLocation");
                    String time =value.getString("time");
                    double fullPayment =value.getDouble("fullPayment");


                    TransactionDetails transactionDetails = new TransactionDetails();
                    transactionDetails.setFullPayment(fullPayment);
                    transactionDetails.setTime(time);
                    transactionDetails.setStLocation(stLoEnLo);
                    transactionDetails.setEnLocation(enLoca);
                    transactionList.add(transactionDetails);


                }
            }
        });

        RecyclerView transactionRecyclerView = view.findViewById(R.id.transactionRecyclerView);
        transactionRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        TransactionAdapter busAdapter = new TransactionAdapter(getContext(),getActivity(), transactionList);
        transactionRecyclerView.setAdapter(busAdapter);

        swipeButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.navHostFragment, new HomeFragment());
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    private void firebaseTransaction(String userID) {


    }

    @Override
    public void onTransactionItemClicked(String name) {
        // when clicked transaction this can be use
    }
}