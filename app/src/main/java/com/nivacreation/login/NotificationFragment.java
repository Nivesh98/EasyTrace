package com.nivacreation.login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;
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

        List<TransactionDetails> transactionList = new ArrayList<>();

        for(int i=0; i<10; i++){
            TransactionDetails transactionDetails = new TransactionDetails();
            transactionDetails.setCost("Rs: "+i*123);
            transactionDetails.setDate("Wed 8.38 am");
            transactionDetails.setFromTo("Matara - Gampara");
            transactionList.add(transactionDetails);

        }


        RecyclerView transactionRecyclerView = view.findViewById(R.id.transactionRecyclerView);
        transactionRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        TransactionAdapter busAdapter = new TransactionAdapter(getContext(), this, transactionList);
        transactionRecyclerView.setAdapter(busAdapter);

        swipeButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.navHostFragment, new HomeFragment());
                fragmentTransaction.commit();
//                Intent signInActivity = new Intent(getActivity(), HomeFragment.class);
//                signInActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(signInActivity);
            }
        });

        return view;
    }

    @Override
    public void onTransactionItemClicked(String name) {
        // when clicked transaction this can be use
    }
}