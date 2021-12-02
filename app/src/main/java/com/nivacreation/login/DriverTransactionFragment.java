package com.nivacreation.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nivacreation.login.adapter.TransactionAdapter;
import com.nivacreation.login.adapter.TransactionAdapterDriver;
import com.nivacreation.login.model.TransactionDetails;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DriverTransactionFragment extends Fragment {

    TextView firstNum, secondNum, thirdNum, fourthNum;
    public double sum = 0, remainD =0;
    String t;
    Calendar calender;
    SimpleDateFormat simpleDateFormat, simple;
    String time;

    RecyclerView recyclerView,recyclerView1;
    ArrayList<TransactionDetails> userSupportArrayList,userSupportArrayList1;
    TransactionAdapterDriver userSupportAdapter,userSupportAdapter1;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public DriverTransactionFragment() {

    }

    public static DriverTransactionFragment newInstance(String param1, String param2) {
        DriverTransactionFragment fragment = new DriverTransactionFragment();
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

        View view = inflater.inflate(R.layout.fragment_driver_transaction, container, false);
        recyclerView = view.findViewById(R.id.driverTransactionFragment);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        SwipeButton swipeButton = view.findViewById(R.id.swipeBtnDriverTransaction);

        userSupportArrayList = new ArrayList<TransactionDetails>();

        userSupportAdapter = new TransactionAdapterDriver(getActivity(),userSupportArrayList);
        recyclerView.setAdapter(userSupportAdapter);

        calender = Calendar.getInstance();

        simpleDateFormat = new SimpleDateFormat("dd-MMM");
        time =simpleDateFormat.format(calender.getTime());
        EventChangeListner();
        verifyBusQrCode();

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

    private void EventChangeListner() {
        String userId = fAuth.getCurrentUser().getUid();
        fStore = FirebaseFirestore.getInstance();

        fStore.collection("User Booking")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {

                        if(error != null){
                            Log.e("12345",error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()){

                            if (dc.getType() == DocumentChange.Type.ADDED){

                                String userType = dc.getDocument().toString();
                                Log.e("12345",userType);
                                userSupportArrayList.add(dc.getDocument().toObject(TransactionDetails.class));
                            }

                            userSupportAdapter.notifyDataSetChanged();

                        }
                    }
                });


        fStore.collection("User Booking Live")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {

                        if(error != null){
                            Log.e("12345",error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()){

                            if (dc.getType() == DocumentChange.Type.ADDED){

                                String userType = dc.getDocument().toString();
                                Log.e("12345",userType);
                                userSupportArrayList.add(dc.getDocument().toObject(TransactionDetails.class));
                            }
                            userSupportAdapter.notifyDataSetChanged();

                        }
                    }
                });
    }

    private void verifyBusQrCode() {



        if (fAuth.getCurrentUser().getUid() != null){

            String userId = fAuth.getCurrentUser().getUid();

            fStore.collection("User Booking").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    Log.d("12345", "is Success"+ task.isSuccessful());
                    if (task.isSuccessful()) {
                        Log.i("12345", "pay success");
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.i("12345", "inside get bus id pay  "+document.get("busID"));
                            if (document.get("busID") == null) {
                                Log.i("12345", "ssss "+document.getId());
                                return;
                            }

                            String busID = document.get("busID").toString();

                            if (busID.trim().equals(userId.trim())){
                                Log.i("12345", "time here  "+time);

                                t = document.get("time").toString().substring(0,7);
                                Log.i("12345", "time "+document.get("time")+" "+t);
                                if (time.trim().equals(t.trim())){
                                    Log.i("12345", "inside bus pay equal both ids  "+busID+" "+document.get("busID").toString());
                                    if (document.get("paid") == null) {
                                        Log.i("12345", "user Id error Driver "+document.getId());
                                        return;
                                    }
                                    Log.i("12345", "sum  "+sum);
                                    Log.i("12345", "inside pay  "+document.get("paid"));
                                    double paid = document.getDouble("paid");
                                    sum = sum + paid;
                                    double remain = document.getDouble("remain");
                                    remainD = remainD+remain;
                                    Log.i("12345", "sum  "+sum);

                                    DocumentReference document1 = fStore.collection("Driver Transaction").document(userId);
                                    DocumentReference documentReference = fStore.collection("Driver Transaction").document(userId);
                                    Map<String,Object> book = new HashMap<>();
//                                    book.put("busID",userId);
//                                    book.put("total booking",sum);
//                                    book.put("total remain",remainD);
//                                    book.put("time",time);
                                    documentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {

                                            if (value != null && value.exists()) {

                                                if (value.getDouble("total booking") == null || value.getDouble("total remain") ==null){
                                                    return;
                                                }

                                                double kk = value.getDouble("total booking");
                                                double ll = value.getDouble("total remain");

                                                sum = sum +kk;
                                                remainD = remainD+ll;
                                                book.put("busID",userId);
                                                book.put("total booking",sum);
                                                book.put("total remain",remainD);
                                                book.put("time",time);
                                            }
                                        }
                                    });



                                    document1.set(book);
                                }


                            }
                        }






                        Log.i("12345", "sum  "+sum);
                        Toast.makeText(getActivity(),"Total = "+sum, Toast.LENGTH_SHORT).show();
                    } else {

                        Log.d("12345", "Error getting documents: ", task.getException());

                    }
                }
            });

            fStore.collection("User Booking Live").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    Log.d("12345", "is Success"+ task.isSuccessful());
                    if (task.isSuccessful()) {
                        Log.i("12345", "pay success");
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.i("12345", "inside get bus id pay  "+document.get("busID"));
                            if (document.get("busID") == null) {
                                Log.i("12345", "ssss "+document.getId());
                                return;
                            }

                            String busID = document.get("busID").toString();

                            if (busID.trim().equals(userId.trim())){
                                Log.i("12345", "time here  "+time);

                                t = document.get("time").toString().substring(0,7);
                                Log.i("12345", "time "+document.get("time")+" "+t);
                                if (time.trim().equals(t.trim())){
                                    Log.i("12345", "inside bus pay equal both ids  "+busID+" "+document.get("busID").toString());
                                    if (document.get("paid") == null) {
                                        Log.i("12345", "user Id error Driver "+document.getId());
                                        return;
                                    }
                                    Log.i("12345", "sum  "+sum);
                                    Log.i("12345", "inside pay  "+document.get("paid"));
                                    double paid = document.getDouble("paid");
                                    sum = sum + paid;
                                    Log.i("12345", "sum  "+sum);


                                    DocumentReference document1 = fStore.collection("Driver Transaction").document(userId);
                                    DocumentReference documentReference = fStore.collection("Driver Transaction").document(userId);
                                    Map<String,Object> book = new HashMap<>();

//                                    book.put("busID",userId);
//                                    book.put("total booking",sum);
//                                    book.put("total remain",0);
//                                    book.put("time",time);
                                    documentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {

                                            if (value != null && value.exists()) {

                                                if (value.getDouble("total booking") == null){

                                                    return;
                                                }
                                                double kk = value.getDouble("total booking");

                                                sum = sum +kk;
                                                book.put("busID",userId);
                                                book.put("total booking",sum);
                                                book.put("total remain",0);
                                                book.put("time",time);
                                            }
                                        }
                                    });

                                    document1.set(book);
                                }


                            }
                        }





                        Log.i("12345", "sum  "+sum);
                        Toast.makeText(getActivity(),"Total = "+sum, Toast.LENGTH_SHORT).show();
                    } else {

                        Log.d("12345", "Error getting documents: ", task.getException());

                    }
                }
            });
            Toast.makeText(getActivity(),"Total = "+sum, Toast.LENGTH_LONG).show();

        }

    }
}