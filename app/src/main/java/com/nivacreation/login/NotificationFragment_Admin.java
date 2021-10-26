package com.nivacreation.login;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.remote.WatchChange;
import com.nivacreation.login.adapter.UserAdapter;
import com.nivacreation.login.model.User;

import java.util.ArrayList;

public class NotificationFragment_Admin extends Fragment {

    RecyclerView recyclerView;
    ArrayList<User> userArrayList;
    UserAdapter userAdapter;
    FirebaseFirestore fStore;

    private RadioGroup radioGroupUsers;
    private RadioButton radioButtonUsers;



    //ProgressDialog progressDialog;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public NotificationFragment_Admin() {

    }

    public static NotificationFragment_Admin newInstance(String param1, String param2) {
        NotificationFragment_Admin fragment = new NotificationFragment_Admin();
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
        View view = inflater.inflate(R.layout.fragment_notification__admin, container, false);

        recyclerView = view.findViewById(R.id.userRecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        fStore = FirebaseFirestore.getInstance();

        radioGroupUsers = view.findViewById(R.id.rgUsers);

        userArrayList = new ArrayList<User>();

        userAdapter = new UserAdapter(getActivity(),userArrayList);

//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setCancelable(false);
//        //progressDialog.setMessage("Fetching Data...");
//        progressDialog.show();
        recyclerView.setAdapter(userAdapter);
       radioButtonUsers = view.findViewById(R.id.rdPassenger);

        radioGroupUsers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButtonUsers = view.findViewById(checkedId);
                userArrayList.clear();
                switch (checkedId)
                {
                    case  R.id.rdPassenger :
                        break;
                    case R.id.rdDriver:
                        break;
                }
                EventChangeListner();
            }

        });

        return view;
    }

    private void EventChangeListner() {
        fStore = FirebaseFirestore.getInstance();

        fStore.collection("Users").orderBy("firstName", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {

                        if(error != null){

//                            if (progressDialog.isShowing())
//                                progressDialog.dismiss();

                            Log.e("12345",error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()){

                            if (dc.getType() == DocumentChange.Type.ADDED){

                                String userType = dc.getDocument().get("userType").toString();
                                Log.e("12345",userType);
                                Log.e("12345",radioButtonUsers.getText().toString());
                                if (userType.equals(radioButtonUsers.getText().toString())){


                                    userArrayList.add(dc.getDocument().toObject(User.class));
                                }

                            }

                            userAdapter.notifyDataSetChanged();
//                            if (progressDialog.isShowing())
//                                progressDialog.dismiss();
                        }
                    }
                });
    }
//    private void findRadioChecked(int checkIdRadio) {
//        switch (checkIdRadio)
//        {
//            case  R.id.rdPassenger :
//
//                UserNameRadio = "Passenger";
//                break;
////            case R.id.rdAdmin:
////                radioButtonUsers = findViewById(R.id.rdAdmin);
////                UserNameRadio = radioButtonUsers.getText().toString();
////                break;
//            case R.id.rdDriver:
//
//                UserNameRadio = "Driver";
//                break;
//        }
//
//
//    }
}