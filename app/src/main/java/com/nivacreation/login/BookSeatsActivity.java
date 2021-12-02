package com.nivacreation.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class BookSeatsActivity extends AppCompatActivity implements View.OnClickListener {

    Button[] buttons = new Button[36];
    String[] booleans = new String[36];

    boolean checkOne = false;

    Button positiveButton;

    String isAct22;
    String isAct2;
    String isAct1;
    String isActTR;
    String isActStLocation;
    String isActEnLocation;
    String isActStInt;
    String isActEnInt;

    double k,pay,fPay,oBook,rem;

    Calendar calender;
    SimpleDateFormat simpleDateFormat;
    String time;

    String title, travelRoute,stLocation, edLocation1,titleBus;
    int stInt, enInt;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    DocumentReference document1,document2;

    //String userID = "87HATPpL1MQ0hhunLRzQkzXQoDt2";
    String userID ;

    AlertDialog dialog1;

    //    private boolean isValue0 = true, isValue1 = true;
    int i;
    int g =0;
    int c;
    int y =0, x=0;

    AlertDialog.Builder builder;
    TextView busID, route,seatsDialog,payment,timeDia,remain;
    Button okDial;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_seats);

        titleBus = getIntent().getStringExtra("title");
        travelRoute = getIntent().getStringExtra("trRoute");
        stLocation = getIntent().getStringExtra("stLocation");
        edLocation1 = getIntent().getStringExtra("endLocation");
        stInt =getIntent().getIntExtra("stInt",stInt);
        enInt =getIntent().getIntExtra("enInt",enInt);

        builder = new AlertDialog.Builder(BookSeatsActivity.this);
        builder.setTitle("Payment Details");

        view = getLayoutInflater().inflate(R.layout.payment_popup,null);

        busID = view.findViewById(R.id.busIDTextDialog);
        route = view.findViewById(R.id.busRouteTextDialog);
        seatsDialog = view.findViewById(R.id.busSeatTextDialog);
        payment = view.findViewById(R.id.busPaymentTextDialog);
        timeDia = view.findViewById(R.id.busTimeTextDialog);
        remain = view.findViewById(R.id.busRemainTextDialog);

        okDial = view.findViewById(R.id.okDialogBtn);

        //titleQr = getIntent().getStringExtra("titleQr");

        Log.d("1111","stInt"+stInt);
        Log.d("1111","enInt"+enInt);
        Log.d("1111","enInt"+titleBus);
        isAct2 = PreferenceManager
                .getDefaultSharedPreferences(this).getString("isAct2", "");

        isAct1 = PreferenceManager
                .getDefaultSharedPreferences(this).getString("isAct1", "");

        isAct22 = PreferenceManager
                .getDefaultSharedPreferences(this).getString("isAct22", "");

        isActTR = PreferenceManager
                .getDefaultSharedPreferences(this).getString("isActTR", "");

        isActStLocation = PreferenceManager
                .getDefaultSharedPreferences(this).getString("isActStLocation", "");

        isActEnLocation = PreferenceManager
                .getDefaultSharedPreferences(this).getString("isActEnLocation", "");

        int StInt = PreferenceManager
                .getDefaultSharedPreferences(this).getInt("isAct", 0);

        int EnInt = PreferenceManager
                .getDefaultSharedPreferences(this).getInt("isActo", 0);

        Log.d("1111","stIntsa "+StInt);
        Log.d("1111","enIntsa "+EnInt);
        title = titleBus;


        Log.d("1111","title1 "+title);
        if (!isActTR.equals("")){
            travelRoute = isActTR;
        }

        if (!isActStLocation.equals("")){
            stLocation = isActStLocation;
        }

        if (!isActEnLocation.equals("")){
            edLocation1 = isActEnLocation;
        }
Log.d("1111","isActStInt "+isActStInt);
        if (StInt!=0){
            stInt = Integer.valueOf(StInt);
        }


        if (EnInt!=0){
            enInt = Integer.valueOf(EnInt);
        }

        Log.i("12345","Qr title out side = " + isAct22);
        if (!isAct22.equals("")){
            Log.i("12345","Qr title = " + isAct22);
            title = isAct22;
        }
        Log.i("12345","title out side = " + title);
        Log.d("1111","start "+stInt);
        Log.d("1111","end "+enInt);

        calender = Calendar.getInstance();

        simpleDateFormat = new SimpleDateFormat("dd-MMM HH:mm:ss");
        time =simpleDateFormat.format(calender.getTime());

        fStore = FirebaseFirestore.getInstance();

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();


        Log.i("12345","Qr Activity Value = " + isAct1);
        Log.i("12345","Bus Activity Value = " + isAct2);

        for (i = 0; i < buttons.length; i++) {
            String buttonID = "seat" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            booleans[i] = "";
            buttons[i] = findViewById(resID);
            buttons[i].setOnClickListener(this);
        }

        //Retriew current seats data from firebasefirestore
       firebaseAboutSeats(title);


        buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[0].getBackground();
                int color = buttonColor.getColor();
                if (booleans[0].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[0].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[0] = userID+"#";
                    return;

                } else if (booleans[0].equals("")&& isAct1.equals("*")) { //Now Empty
                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[0].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[0] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }
                } else {
                    if(!booleans[0].equals(userID+"*") && isAct1.equals("*")){
                        
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[0].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[0].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[0] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColorDrawable buttonColor = (ColorDrawable) buttons[1].getBackground();
                int color = buttonColor.getColor();

                if (booleans[1].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[1].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[1] = userID+"#";
                    return;

                } else if (booleans[1].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[1].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[1] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }


                } else {
                    if(!booleans[1].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[1].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[1].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[1] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });

        buttons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[2].getBackground();
                int color = buttonColor.getColor();
                if (booleans[2].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[2].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[2] = userID+"#";
                    return;

                } else if (booleans[2].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[2].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[2] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[2].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[2].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[2].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[2] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });

        buttons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[3].getBackground();
                int color = buttonColor.getColor();
                if (booleans[3].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[3].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[3] = userID+"#";
                    return;

                } else if (booleans[3].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[3].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[3] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[3].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[3].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[3].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[3] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });

        buttons[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[4].getBackground();
                int color = buttonColor.getColor();
                if (booleans[4].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[4].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[4] = userID+"#";
                    return;

                } else if (booleans[4].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[4].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[4] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[4].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[4].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[4].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[4] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[5].getBackground();
                int color = buttonColor.getColor();
                if (booleans[5].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[5].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[5] = userID+"#";
                    return;

                } else if (booleans[5].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[5].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[5] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[5].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[5].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[5].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[5] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[6].getBackground();
                int color = buttonColor.getColor();
                if (booleans[6].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[6].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[6] = userID+"#";
                    return;

                } else if (booleans[6].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[6].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[6] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[6].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[6].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[6].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[6] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[7].getBackground();
                int color = buttonColor.getColor();
                if (booleans[7].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[7].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[7] = userID+"#";
                    return;

                } else if (booleans[7].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[7].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[7] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[7].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[7].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[7].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[7] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[8].getBackground();
                int color = buttonColor.getColor();
                if (booleans[8].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[8].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[8] = userID+"#";
                    return;

                } else if (booleans[8].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[8].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[8] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[8].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[8].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[8].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[8] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[9].getBackground();
                int color = buttonColor.getColor();
                if (booleans[9].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[9].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[9] = userID+"#";
                    return;

                } else if (booleans[9].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[9].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[9] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[9].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[9].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[9].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[9] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[10].getBackground();
                int color = buttonColor.getColor();
                if (booleans[10].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[10].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[10] = userID+"#";
                    return;

                } else if (booleans[10].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[10].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[10] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[10].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[10].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[10].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[10] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[11].getBackground();
                int color = buttonColor.getColor();
                if (booleans[11].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[11].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[11] = userID+"#";
                    return;

                } else if (booleans[11].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[11].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[11] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[11].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[11].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[11].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[11] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[12].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[12].getBackground();
                int color = buttonColor.getColor();
                if (booleans[12].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[12].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[12] = userID+"#";
                    return;

                } else if (booleans[12].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[12].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[12] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[12].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[12].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[12].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[12] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[13].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[13].getBackground();
                int color = buttonColor.getColor();
                if (booleans[13].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[13].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[13] = userID+"#";
                    return;

                } else if (booleans[13].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[13].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[13] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[13].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[13].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[13].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[13] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[14].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[14].getBackground();
                int color = buttonColor.getColor();
                if (booleans[14].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[14].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[14] = userID+"#";
                    return;

                } else if (booleans[14].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[14].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[14] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[14].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[14].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[14].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[14] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[15].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[15].getBackground();
                int color = buttonColor.getColor();
                if (booleans[15].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[15].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[15] = userID+"#";
                    return;

                } else if (booleans[15].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[15].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[15] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[15].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[15].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[15].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[15] = "";
                                checkOne =false;
                            }
                        });

                    }



                }
            }
        });
        buttons[16].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[16].getBackground();
                int color = buttonColor.getColor();
                if (booleans[16].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[16].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[16] = userID+"#";
                    return;

                } else if (booleans[16].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[16].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[16] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[16].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[16].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[16].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[16] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[17].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[17].getBackground();
                int color = buttonColor.getColor();
                if (booleans[17].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[17].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[17] = userID+"#";
                    return;

                } else if (booleans[17].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[17].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[17] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[17].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[17].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[17].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[17] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[18].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[18].getBackground();
                int color = buttonColor.getColor();
                if (booleans[18].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[18].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[18] = userID+"#";
                    return;

                } else if (booleans[18].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[18].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[18] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[18].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[18].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[18].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[18] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[19].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[19].getBackground();
                int color = buttonColor.getColor();
                if (booleans[19].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[19].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[19] = userID+"#";
                    return;

                } else if (booleans[19].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[19].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[19] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[19].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[19].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[19].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[19] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[20].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[20].getBackground();
                int color = buttonColor.getColor();
                if (booleans[20].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[20].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[20] = userID+"#";
                    return;

                } else if (booleans[20].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[20].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[20] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[20].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[20].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[20].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[20] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[21].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[21].getBackground();
                int color = buttonColor.getColor();
                if (booleans[21].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[21].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[21] = userID+"#";
                    return;

                } else if (booleans[21].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[21].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[21] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[21].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[21].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[21].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[21] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[22].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[22].getBackground();
                int color = buttonColor.getColor();
                if (booleans[22].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[22].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[22] = userID+"#";
                    return;

                } else if (booleans[22].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[22].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[22] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[22].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[22].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[22].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[22] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[23].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[23].getBackground();
                int color = buttonColor.getColor();
                if (booleans[23].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[23].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[23] = userID+"#";
                    return;

                } else if (booleans[23].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[23].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[23] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[23].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[23].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[23].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[23] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[24].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[24].getBackground();
                int color = buttonColor.getColor();
                if (booleans[24].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[24].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[24] = userID+"#";
                    return;

                } else if (booleans[24].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[24].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[24] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[24].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[24].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[24].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[24] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[25].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[25].getBackground();
                int color = buttonColor.getColor();
                if (booleans[25].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[25].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[25] = userID+"#";
                    return;

                } else if (booleans[25].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[25].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[25] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[25].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[25].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[25].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[25] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[26].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[26].getBackground();
                int color = buttonColor.getColor();
                if (booleans[26].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[26].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[26] = userID+"#";
                    return;

                } else if (booleans[26].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[26].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[26] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[26].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[26].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[26].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[26] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[27].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[27].getBackground();
                int color = buttonColor.getColor();
                if (booleans[27].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[27].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[27] = userID+"#";
                    return;

                } else if (booleans[27].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[27].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[27] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[27].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[27].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[27].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[27] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[28].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[28].getBackground();
                int color = buttonColor.getColor();
                if (booleans[28].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[28].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[28] = userID+"#";
                    return;

                } else if (booleans[28].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[28].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[28] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[28].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[28].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[28].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[28] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[29].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[29].getBackground();
                int color = buttonColor.getColor();
                if (booleans[29].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[29].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[29] = userID+"#";
                    return;

                } else if (booleans[29].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[29].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[29] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[29].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[29].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[29].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[29] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[30].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[30].getBackground();
                int color = buttonColor.getColor();
                if (booleans[30].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[30].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[30] = userID+"#";
                    return;

                } else if (booleans[30].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[30].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[30] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[30].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[30].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[30].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[30] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[31].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[31].getBackground();
                int color = buttonColor.getColor();
                if (booleans[31].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[31].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[31] = userID+"#";
                    return;

                } else if (booleans[31].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[31].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[31] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[31].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[31].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[31].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[31] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[32].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[32].getBackground();
                int color = buttonColor.getColor();
                if (booleans[32].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[32].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[32] = userID+"#";
                    return;

                } else if (booleans[32].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[32].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[32] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[32].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[32].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[32].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[32] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[33].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[33].getBackground();
                int color = buttonColor.getColor();
                if (booleans[33].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[33].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[33] = userID+"#";
                    return;

                } else if (booleans[33].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[33].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[33] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[33].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[33].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[33].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[33] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[34].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[34].getBackground();
                int color = buttonColor.getColor();
                if (booleans[34].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[34].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[34] = userID+"#";
                    return;

                } else if (booleans[34].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[34].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[34] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[34].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[34].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[34].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[34] = "";
                                checkOne=false;
                            }
                        });

                    }



                }
            }
        });
        buttons[35].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorDrawable buttonColor = (ColorDrawable) buttons[35].getBackground();
                int color = buttonColor.getColor();
                if (booleans[35].equals("")&& isAct2.equals("#")) { //Now Empty

                    buttons[35].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[35] = userID+"#";
                    return;

                } else if (booleans[35].equals("")&& isAct1.equals("*")) { //Now Empty

                    if (color == getResources().getColor(R.color.teal_200)){
                        if (checkOne==false){
                            buttons[35].setBackgroundResource(android.R.color.holo_orange_light);
                            booleans[35] = userID+"*";
                            checkOne=true;
                            return;
                        }
                    }

                } else {
                    if(!booleans[35].equals(userID+"*") && isAct1.equals("*")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else if(!booleans[35].equals(userID+"#")&& isAct2.equals("#")){

                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();

                    } else{
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("Are you sure want to cancel the seat?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buttons[35].setBackgroundResource(R.color.teal_200);
                                dialog.dismiss();
                                booleans[35] = "";
                                checkOne =false;
                            }
                        });

                    }



                }
            }
        });

        PreferenceManager
                .getDefaultSharedPreferences(BookSeatsActivity.this).edit().putString("isAct22", "").apply();

        PreferenceManager
                .getDefaultSharedPreferences(BookSeatsActivity.this).edit().putString("isAct2", "").apply();

        PreferenceManager
                .getDefaultSharedPreferences(BookSeatsActivity.this).edit().putString("isAct1", "").apply();

        PreferenceManager
                .getDefaultSharedPreferences(BookSeatsActivity.this).edit().putString("isActTR", "").apply();

        PreferenceManager
                .getDefaultSharedPreferences(BookSeatsActivity.this).edit().putString("isActStLocation", "").apply();

        PreferenceManager
                .getDefaultSharedPreferences(BookSeatsActivity.this).edit().putString("isActEnLocation", "").apply();

        PreferenceManager
                .getDefaultSharedPreferences(BookSeatsActivity.this).edit().putInt("isAct",0).apply();

        PreferenceManager
                .getDefaultSharedPreferences(BookSeatsActivity.this).edit().putInt("isActo",0).apply();
    }

    private void firebasePassengerAboutBooking(int c, String userID, String title) {


        Log.i("12345", "firebasePassengerAboutBooking Outside");

        if (isAct2.equals("#")){
            fStore.collection("User Booking History").document(userID+"#").collection(userID).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    k--;
                    y = queryDocumentSnapshots.size();
                    Log.i("12345", "firebasePassengerAboutBooking Inside ="+y);
                    document1 = fStore.collection("User Booking History").document(userID+"#").collection(userID).document(String.valueOf(y+1));
                    Log.i("12345", "firebasePassengerAboutBooking Inside 2 ="+y+1);
                    DocumentReference documentReference = fStore.collection("User Booking").document(userID+"#");

                    int cc = c;
                    cc = cc-1;
                    Map<String,Object> book = new HashMap<>();
                    book.put("busID",title);
                    book.put("enLocation",edLocation1);
                    book.put("stLocation",stLocation);
                    book.put("seats",cc);
                    String bb = userID;
                    book.put("userId",bb.substring(0,6)+"#");

                    int diff =stInt-enInt;
                    Log.d("1111","differ "+diff);
                    if ((1==diff) || (-1==diff)){
                        k = Double.valueOf(cc);
                        pay = (50*k*0.2);
                        fPay = (50*k);
                        oBook = (50*0.2);
                        book.put("onePayment",oBook);
                        rem = (50*0.8*k);
                        book.put("remain",rem);
                        book.put("forOne",50);
                        book.put("fullPayment",fPay);
                        book.put("paid",pay);
                        book.put("time",time);
                        String paydia = String.valueOf(pay);
                        String remDia = String.valueOf(rem);
                        payment.setText(paydia);
                        remain.setText(remDia);
                        String d = title.substring(0,6);
                        busID.setText("Bus_"+d);
                        route.setText(stLocation+" - "+edLocation1);

                        String cDia = String.valueOf(cc);
                        seatsDialog.setText(cDia);

                        timeDia.setText(time);
                    }else if ((2==diff) || (-2==diff)){
                        k = Double.valueOf(cc);
                        pay = (100*k*0.2);
                        fPay = (100*k);
                        oBook = (100*0.2);
                        book.put("onePayment",oBook);
                        rem = (100*0.8*k);
                        book.put("remain",rem);
                        book.put("forOne",100);
                        book.put("fullPayment",fPay);
                        book.put("paid",pay);
                        book.put("time",time);
                        String paydia = String.valueOf(pay);
                        String remDia = String.valueOf(rem);
                        payment.setText(paydia);
                        remain.setText(remDia);
                        String d = title.substring(0,6);
                        busID.setText("Bus_"+d);
                        route.setText(stLocation+" - "+edLocation1);

                        String cDia = String.valueOf(cc);
                        seatsDialog.setText(cDia);

                        timeDia.setText(time);
                    }else if ((3==diff) || (-3==diff)){
                        k = Double.valueOf(cc);
                        pay = (180*k*0.2);
                        fPay = (180*k);
                        oBook = (180*0.2);
                        book.put("onePayment",oBook);
                        rem = (180*0.8*k);
                        book.put("remain",rem);
                        book.put("forOne",180);
                        book.put("fullPayment",fPay);
                        book.put("paid",pay);
                        book.put("time",time);
                        String paydia = String.valueOf(pay);
                        String remDia = String.valueOf(rem);
                        payment.setText(paydia);
                        remain.setText(remDia);
                        String d = title.substring(0,6);
                        busID.setText("Bus_"+d);
                        route.setText(stLocation+" - "+edLocation1);

                        String cDia = String.valueOf(cc);
                        seatsDialog.setText(cDia);

                        timeDia.setText(time);
                    }else if ((4==diff) || (-4==diff)){
                        k = Double.valueOf(cc);
                        pay = (250*k*0.2);
                        fPay = (250*k);
                        oBook = (250*0.2);
                        book.put("onePayment",oBook);
                        rem = (250*0.8*k);
                        book.put("remain",rem);
                        book.put("forOne",250);
                        book.put("fullPayment",fPay);
                        book.put("paid",pay);
                        book.put("time",time);
                        String paydia = String.valueOf(pay);
                        String remDia = String.valueOf(rem);
                        payment.setText(paydia);
                        remain.setText(remDia);
                        String d = title.substring(0,6);
                        busID.setText("Bus_"+d);
                        route.setText(stLocation+" - "+edLocation1);

                        String cDia = String.valueOf(cc);
                        seatsDialog.setText(cDia);

                        timeDia.setText(time);
                    }else if ((5==diff) || (-5==diff)){
                        k = Double.valueOf(cc);
                        pay = (400*k*0.2);
                        fPay = (400*k);
                        oBook = (400*0.2);
                        book.put("onePayment",oBook);
                        rem = (400*0.8*k);
                        book.put("remain",rem);
                        book.put("forOne",400);
                        book.put("fullPayment",fPay);
                        book.put("paid",pay);
                        book.put("time",time);
                        String paydia = String.valueOf(pay);
                        String remDia = String.valueOf(rem);
                        payment.setText(paydia);
                        remain.setText(remDia);
                        String d = title.substring(0,6);
                        busID.setText("Bus_"+d);
                        route.setText(stLocation+" - "+edLocation1);

                        String cDia = String.valueOf(cc);
                        seatsDialog.setText(cDia);

                        timeDia.setText(time);
                    }else if ((6==diff) || (-6==diff)){
                        k = Double.valueOf(cc);
                        pay = (550*k*0.2);
                        fPay = (550*k);
                        oBook = (550*0.2);
                        book.put("onePayment",oBook);
                        rem = (550*0.8*k);
                        book.put("remain",rem);
                        book.put("forOne",550);
                        book.put("fullPayment",fPay);
                        book.put("paid",pay);
                        book.put("time",time);
                        String paydia = String.valueOf(pay);
                        String remDia = String.valueOf(rem);
                        payment.setText(paydia);
                        remain.setText(remDia);
                        String d = title.substring(0,6);
                        busID.setText("Bus_"+d);
                        route.setText(stLocation+" - "+edLocation1);

                        String cDia = String.valueOf(cc);
                        seatsDialog.setText(cDia);

                        timeDia.setText(time);
                    }else if ((7==diff) || (-7==diff)){
                        k = Double.valueOf(cc);
                        pay = (900*k*0.2);
                        fPay = (900*k);
                        oBook = (900*0.2);
                        book.put("onePayment",oBook);
                        rem = (900*0.8*k);
                        book.put("remain",rem);
                        book.put("forOne",900);
                        book.put("fullPayment",fPay);
                        book.put("paid",pay);
                        book.put("time",time);
                        String paydia = String.valueOf(pay);
                        String remDia = String.valueOf(rem);
                        payment.setText(paydia);
                        remain.setText(remDia);
                        String d = title.substring(0,6);
                        busID.setText("Bus_"+d);
                        route.setText(stLocation+" - "+edLocation1);

                        String cDia = String.valueOf(cc);
                        seatsDialog.setText(cDia);

                        timeDia.setText(time);
                    }


                    document1.set(book);

                    documentReference.set(book);

                }
            });
        }else if (isAct1.equals("*")){
            fStore.collection("User Booking Live History").document(userID+"*").collection(userID).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    x = queryDocumentSnapshots.size();
                    document2 = fStore.collection("User Booking Live History").document(userID+"*").collection(userID).document(String.valueOf(x+1));

                    DocumentReference documentReference1 = fStore.collection("User Booking Live").document(userID+"*");

                    Map<String,Object> book1 = new HashMap<>();
                    book1.put("busID",title);
                    book1.put("enLocation",edLocation1);
                    book1.put("stLocation",stLocation);
                    book1.put("seats",c);
                    String bb = userID;
                    book1.put("userId",bb.substring(0,6)+"*");

                    int diff1 =stInt-enInt;
                    Log.d("1111","differ "+diff1);
                    if ((2==diff1) || (-2==diff1)){
                        double k = Double.valueOf(c);
                        book1.put("paid",50);
                        book1.put("time",time);
                        String paydia = String.valueOf(50*k);
                        String remDia = String.valueOf(rem);
                        payment.setText(paydia);
                        remain.setText(remDia);
                        String d = title.substring(0,6);
                        busID.setText("Bus_"+d);
                        route.setText(stLocation+" - "+edLocation1);
                        String cDia = String.valueOf(c);
                        seatsDialog.setText(cDia);

                        timeDia.setText(time);
                    }else if ((3==diff1) || (-3==diff1)){
                        double k = Double.valueOf(c);
                        book1.put("paid",100);
                        book1.put("time",time);
                        String paydia = String.valueOf(100*k);
                        String remDia = String.valueOf(rem);
                        payment.setText(paydia);
                        remain.setText(remDia);
                        String d = title.substring(0,6);
                        busID.setText("Bus_"+d);
                        route.setText(stLocation+" - "+edLocation1);
                        String cDia = String.valueOf(c);
                        seatsDialog.setText(cDia);

                        timeDia.setText(time);
                    }else if ((4==diff1) || (-4==diff1)){
                        double k = Double.valueOf(c);
                        book1.put("paid",180);
                        book1.put("time",time);
                        String paydia = String.valueOf(180*k);
                        String remDia = String.valueOf(rem);
                        payment.setText(paydia);
                        remain.setText(remDia);
                        String d = title.substring(0,6);
                        busID.setText("Bus_"+d);
                        route.setText(stLocation+" - "+edLocation1);
                        String cDia = String.valueOf(c);
                        seatsDialog.setText(cDia);

                        timeDia.setText(time);
                    }else if ((5==diff1) || (-5==diff1)){
                        double k = Double.valueOf(c);
                        book1.put("paid",250);
                        book1.put("time",time);
                        String paydia = String.valueOf(250*k);
                        String remDia = String.valueOf(rem);
                        payment.setText(paydia);
                        remain.setText(remDia);
                        String d = title.substring(0,6);
                        busID.setText("Bus_"+d);
                        route.setText(stLocation+" - "+edLocation1);
                        String cDia = String.valueOf(c);
                        seatsDialog.setText(cDia);

                        timeDia.setText(time);
                    }else if ((6==diff1) || (-6==diff1)){
                        double k = Double.valueOf(c);
                        book1.put("paid",400);
                        book1.put("time",time);
                        String paydia = String.valueOf(400*k);
                        String remDia = String.valueOf(rem);
                        payment.setText(paydia);
                        remain.setText(remDia);
                        String d = title.substring(0,6);
                        busID.setText("Bus_"+d);
                        route.setText(stLocation+" - "+edLocation1);
                        String cDia = String.valueOf(c);
                        seatsDialog.setText(cDia);

                        timeDia.setText(time);
                    }else if ((7==diff1) || (-7==diff1)){
                        double k = Double.valueOf(c);
                        book1.put("paid",550);
                        book1.put("time",time);
                        String paydia = String.valueOf(550*k);
                        String remDia = String.valueOf(rem);
                        payment.setText(paydia);
                        remain.setText(remDia);
                        String d = title.substring(0,6);
                        busID.setText("Bus_"+d);
                        route.setText(stLocation+" - "+edLocation1);
                        String cDia = String.valueOf(c);
                        seatsDialog.setText(cDia);

                        timeDia.setText(time);
                    }else if ((8==diff1) || (-8==diff1)){
                        double k = Double.valueOf(c);
                        book1.put("paid",900);
                        book1.put("time",time);
                        String paydia = String.valueOf(900*k);
                        String remDia = String.valueOf(rem);
                        payment.setText(paydia);
                        remain.setText(remDia);
                        String d = title.substring(0,6);
                        busID.setText("Bus_"+d);
                        route.setText(stLocation+" - "+edLocation1);
                        String cDia = String.valueOf(c);
                        seatsDialog.setText(cDia);

                        timeDia.setText(time);
                    }
                    document2.set(book1);
                    documentReference1.set(book1);


                }
            });

        }
Log.d("1111","pay "+pay);
        Log.d("1111","remain "+rem);


    }



    private void firebaseUpdateDriverDetails(int g) {

        int avail = 36-g;
        //DocumentReference documentReference = fStore.collection("Bus").document(userID);
        Map<String,Object> available = new HashMap<>();

        available.put("available seat",avail);
        available.put("seat",36);
        available.put("id",title);

        if (travelRoute.equals("Kirindiwela")){
            available.put("from",travelRoute);
            available.put("to","Gampaha");
        }else{
            available.put("from","Kirindiwela");
            available.put("to",travelRoute);
        }


        FirebaseFirestore.getInstance().collection("Bus").document(title).update(available);
    }

    private void firebaseAboutSeats(String title) {

        Log.d("12345","Inside firebaseAboutSeats"+ title);

        DocumentReference documentReference = fStore.collection("BusSeats").document(title);
        documentReference.addSnapshotListener(BookSeatsActivity.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {

                Log.d("1111","Inside Document Reference");

                if (value != null && value.exists()) {

                    Log.d("1111","Value is not null");

                    for(int l=0; l<booleans.length; l++){

                        Log.d("1111","Inside for loop");
                        String val = "seat"+l;

                        String getValue = value.get(val).toString();

                        if(!getValue.equals("")){

                            String n = getValue;

                            if ((n.substring(n.length()-1)).equals("#")){
                                booleans[l]=getValue;
                                buttons[l].setBackgroundResource(android.R.color.holo_red_dark);
                                 }else if ((n.substring(n.length()-1)).equals("*")){
                                booleans[l]=getValue;
                                buttons[l].setBackgroundResource(android.R.color.holo_orange_light);
                            }


                        }else{
                            booleans[l]="";
                            buttons[l].setBackgroundResource(R.color.teal_200);
                        }


                    }


                }else{
                    Log.d("1111","Value is null");
                }

            }
        });


    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {

        builder.setView(view);
        dialog1 = builder.create();

        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                .setTitle("Confirm Seats")
                .setMessage("Do you confirm your reservation details?")
                .setPositiveButton("Yes", null)
                .setNegativeButton("No", null)
                .show();
        dialog1.dismiss();
        positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String,Object> seats = new HashMap<>();
                g = 0;
                c = 0;
                Log.d("1111","title2 "+title);
                firebaseAboutSeats(title);

                for (int j = 0; j < buttons.length; j++) {

                    if(!booleans[j].equals("")) {

                        if(booleans[j].equals(userID+"#")){
                            String seat = "seat"+j;
                            seats.put(seat,userID+"#");
                            g++;
                            c++;
                        }else if(booleans[j].equals(userID+"*")){
                            String seat = "seat"+j;
                            seats.put(seat,userID+"*");
                            g++;
                            c++;
                        }else{
                            String seat = "seat"+j;
                            seats.put(seat,booleans[j]);
                            g++;
                        }

                    } else {
                        String seat = "seat"+j;
                        seats.put(seat,"");

                    }

                }
                FirebaseFirestore.getInstance().collection("BusSeats").document(title).update(seats);
                Toast.makeText(BookSeatsActivity.this, "Booked seats are " + g, Toast.LENGTH_SHORT).show();

                firebaseUpdateDriverDetails(g);
                firebasePassengerAboutBooking(c,userID,title);


                dialog1.show();
                dialog.dismiss();


            }
        });

        okDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog1.dismiss();
                BookSeatsActivity.super.onBackPressed();
            }
        });

        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookSeatsActivity.super.onBackPressed();
                dialog.dismiss();
            }
        });
    }
}