package com.nivacreation.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nivacreation.login.adapter.NotificationUserAdapter;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment_Driver extends Fragment {

    RecyclerView recyclerViewNotificationInbox;
    List<String> notificationArray = new ArrayList<>();
    NotificationUserAdapter notificationUserAdapter;
    FirebaseFirestore fStore;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public NotificationFragment_Driver() {
    }

    public static NotificationFragment_Driver newInstance(String param1, String param2) {
        NotificationFragment_Driver fragment = new NotificationFragment_Driver();
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
        View view = inflater.inflate(R.layout.fragment_notification__driver, container, false);

        recyclerViewNotificationInbox = view.findViewById(R.id.notificationFragmentInbox);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerViewNotificationInbox.setLayoutManager(linearLayoutManager);
        //recyclerViewNotificationInbox.setLayoutManager(new LinearLayoutManager(getActivity()));
        notificationUserAdapter = new NotificationUserAdapter(notificationArray);
        recyclerViewNotificationInbox.setAdapter(notificationUserAdapter);

        fStore = FirebaseFirestore.getInstance();

        getNotificationPassengerRecycler();
        SwipeButton swipeButton = view.findViewById(R.id.swipeBtn);

        swipeButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.navHostFragment_driver, new HomeFragment_Driver());
                fragmentTransaction.commit();
//                Intent signInActivity = new Intent(getActivity(), HomeFragment.class);
//                signInActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(signInActivity);
            }
        });
        return view;
    }

    private void getNotificationPassengerRecycler() {
        fStore.collection("BusLocationInbox").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.d("12345", "is Success"+ task.isSuccessful());
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        if (document.getId().equals("8RRcncO8IANHf7vsbVBZyi65MO92")){

                            int i =1;
                            for (int j =1; j<4; j++){

                                String user = "n"+j;
                                String userType = document.get(user).toString();
                                Log.i("12345", "user Driver"+ userType);
                                notificationArray.add(userType);
                                notificationUserAdapter.notifyItemInserted(notificationArray.size()-1);

                            }
                        }

                    }

                } else {

                    Log.d("12345", "Error getting documents: ", task.getException());

                }
            }
        });

    }
}