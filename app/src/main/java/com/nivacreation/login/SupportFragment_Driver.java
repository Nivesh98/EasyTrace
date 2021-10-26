package com.nivacreation.login;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.nivacreation.login.adapter.UserAdapter;
import com.nivacreation.login.adapter.UserSupportAdapter;
import com.nivacreation.login.model.User;
import com.nivacreation.login.model.UserSupport;

import java.util.ArrayList;

public class SupportFragment_Driver extends Fragment {

    RecyclerView recyclerView;
    ArrayList<UserSupport> userSupportArrayList;
    UserSupportAdapter userSupportAdapter;
    FirebaseFirestore fStore;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SupportFragment_Driver() {

    }

    public static SupportFragment_Driver newInstance(String param1, String param2) {
        SupportFragment_Driver fragment = new SupportFragment_Driver();
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

        View view = inflater.inflate(R.layout.fragment_support__driver, container, false);

        recyclerView = view.findViewById(R.id.userRecycleViewAdminSupport);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        fStore = FirebaseFirestore.getInstance();

        userSupportArrayList = new ArrayList<UserSupport>();

        userSupportAdapter = new UserSupportAdapter(getActivity(),userSupportArrayList);
        recyclerView.setAdapter(userSupportAdapter);

        EventChangeListner();

        return  view;
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
                                }

                            }

                            userSupportAdapter.notifyDataSetChanged();

                        }
                    }
                });
    }
}