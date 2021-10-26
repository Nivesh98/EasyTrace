package com.nivacreation.login;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.nivacreation.login.MainActivity.TAG;

public class SettingsFragment extends Fragment {

    EditText firstName, lastName, contact, city, street;

    Button updateBtn;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    String vui;
    String userType;
    String email;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SettingsFragment() {

    }

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        firstName = view.findViewById(R.id.firstName);
        lastName = view.findViewById(R.id.lastName);
        contact = view.findViewById(R.id.contactNum);
        city = view.findViewById(R.id.city);
        street = view.findViewById(R.id.street);

        updateBtn = view.findViewById(R.id.settingUpdateBtn);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        contact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (validateMobile(contact.getText().toString())){

                }else{
                    contact.setError("Invalid Mobile No! First Digit 0, Include 10 Digits!");

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        userDetails();
//        Bundle bundle = this.getArguments();
//        String userType = bundle.getString("userType");
//        String email = bundle.getString("email");
//        String userId = bundle.getString("userId");
//
//        Toast.makeText(getActivity(),"userType "+userType+" email "+email+" userId "+userId,Toast.LENGTH_LONG).show();
        return view;
    }

    boolean validateMobile(String input){

        Pattern p = Pattern.compile("[0][0-9]{9}");
        Matcher m = p.matcher(input);

        return m.matches();
    }

    private void userUpdateDetails() {

        if (fAuth.getCurrentUser().getUid() != null){
            userId = fAuth.getCurrentUser().getUid();
            DocumentReference documentReference = fStore.collection("Users").document(userId);
            Map<String,Object> user = new HashMap<>();
            user.put("firstName",firstName.getText().toString());
            user.put("lastName",lastName.getText().toString());
            user.put("email",email);
            user.put("userType", userType);
            user.put("userID",userId);
            user.put("contact",contact.getText().toString());
            user.put("city",city.getText().toString());
            user.put("street",street.getText().toString());

            Toast.makeText(getActivity(), "Updated Successfully!", Toast.LENGTH_SHORT).show();
            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d(TAG,"onSuccess: User profile is created for "+ userId);
                }
            });
        }

    }

    private void userDetails(){

        FirebaseUser user = fAuth.getCurrentUser();
        if (fAuth.getCurrentUser().getUid() != null){

            userId = fAuth.getCurrentUser().getUid();

            DocumentReference documentReference = fStore.collection("Users").document(userId);
            documentReference.addSnapshotListener( getActivity(), new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {

                    if (value != null && value.exists()) {

                        email = (value.getString("email"));
                        firstName.setText(value.getString("firstName"));
                        lastName.setText(value.getString("lastName"));
                        contact.setText(value.getString("contact"));
                        city.setText(value.getString("city"));
                        street.setText(value.getString("street"));
                        //userTypeTxt.setText(value.getString("User Type"));
                        userType = value.getString("userType");


                    }

                }
            });

            updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog dialog = new AlertDialog.Builder(getActivity())
                            .setTitle("Information Updating")
                            .setMessage("Are you sure want to update these details?")
                            .setPositiveButton("Yes",null)
                            .setNegativeButton("No",null)
                            .show();
                    Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            userUpdateDetails();
                            dialog.dismiss();
                        }
                    });
                }
            });
        }

    }

}