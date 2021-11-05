package com.nivacreation.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

    Calendar calender;
    SimpleDateFormat simpleDateFormat;
    String time;

    String title, travelRoute,stLocation, edLocation1;
    int stInt, enInt;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    DocumentReference document1;

    //String userID = "87HATPpL1MQ0hhunLRzQkzXQoDt2";
    String userID ;

    //    private boolean isValue0 = true, isValue1 = true;
    int i;
    int k;
    int g =0;
    int c;
    int y =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_seats);

        title = getIntent().getStringExtra("title");
        travelRoute = getIntent().getStringExtra("trRoute");
        stLocation = getIntent().getStringExtra("stLocation");
        edLocation1 = getIntent().getStringExtra("endLocation");
        stInt =getIntent().getIntExtra("stInt",stInt);
        enInt =getIntent().getIntExtra("enInt",enInt);

        Log.d("1111","start "+stInt);
        Log.d("1111","end "+enInt);

        calender = Calendar.getInstance();

        simpleDateFormat = new SimpleDateFormat("dd-MMM HH:mm:ss");
        time =simpleDateFormat.format(calender.getTime());

        fStore = FirebaseFirestore.getInstance();

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Button count = findViewById(R.id.count);

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
                if (booleans[0].equals("")) { //Now Empty
                    buttons[0].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[0] = userID;
                    return;
                } else {
                    if(!booleans[0].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else {
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
                            }
                        });

                    }



                }
            }
        });
        buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[1].equals("")) {
                    buttons[1].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[1] = userID;
                    return;
                } else {
                    if(!booleans[1].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });

        buttons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[2].equals("")) {
                    buttons[2].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[2] = userID;
                    return;
                } else {
                    if(!booleans[2].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });

        buttons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[3].equals("")) {
                    buttons[3].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[3] = userID;
                    return;
                } else {
                    if(!booleans[3].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });

        buttons[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[4].equals("")) {
                    buttons[4].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[4] = userID;
                    return;
                } else {
                    if(!booleans[4].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[5].equals("")) {
                    buttons[5].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[5] = userID;
                    return;
                } else {
                    if(!booleans[5].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[6].equals("")) {
                    buttons[6].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[6] = userID;
                    return;
                } else {
                    if(!booleans[6].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[7].equals("")) {
                    buttons[7].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[7] = userID;
                    return;
                } else {
                    if(!booleans[7].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[8].equals("")) {
                    buttons[8].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[8] = userID;
                    return;
                } else {
                    if(!booleans[8].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[9].equals("")) {
                    buttons[9].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[9] = userID;
                    return;
                } else {
                    if(!booleans[9].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[10].equals("")) {
                    buttons[10].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[10] = userID;
                    return;
                } else {
                    if(!booleans[10].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[11].equals("")) {
                    buttons[11].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[11] = userID;
                    return;
                } else {
                    if(!booleans[11].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[12].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[12].equals("")) {
                    buttons[12].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[12] = userID;
                    return;
                } else {
                    if(!booleans[12].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[13].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[13].equals("")) {
                    buttons[13].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[13] = userID;
                    return;
                } else {
                    if(!booleans[13].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("Ok", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[14].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[14].equals("")) {
                    buttons[14].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[14] = userID;
                    return;
                } else {
                    if(!booleans[14].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("OK", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[15].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[15].equals("")) {
                    buttons[15].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[15] = userID;
                    return;
                } else {
                    if(!booleans[15].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("OK", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[16].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[16].equals("")) {
                    buttons[16].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[16] = userID;
                    return;
                } else {
                    if(!booleans[16].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("OK", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[17].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[17].equals("")) {
                    buttons[17].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[17] = userID;
                    return;
                } else {
                    if(!booleans[17].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("OK", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[18].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[18].equals("")) {
                    buttons[18].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[18] = userID;
                    return;
                } else {
                    if(!booleans[18].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("OK", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[19].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[19].equals("")) {
                    buttons[19].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[19] = userID;
                    return;
                } else {
                    if(!booleans[19].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("OK", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[20].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[20].equals("")) {
                    buttons[20].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[20] = userID;
                    return;
                } else {
                    if(!booleans[20].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("OK", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[21].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[21].equals("")) {
                    buttons[21].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[21] = userID;
                    return;
                } else {
                    if(!booleans[21].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("OK", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[22].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[22].equals("")) {
                    buttons[22].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[22] = userID;
                    return;
                } else {
                    if(!booleans[22].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("OK", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[23].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[23].equals("")) {
                    buttons[23].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[23] = userID;
                    return;
                } else {
                    if(!booleans[23].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("OK", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[24].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[24].equals("")) {
                    buttons[24].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[24] = userID;
                    return;
                } else {
                    if(!booleans[24].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("OK", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[25].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[25].equals("")) {
                    buttons[25].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[25] = userID;
                    return;
                } else {
                    if(!booleans[25].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("OK", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[26].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[26].equals("")) {
                    buttons[26].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[26] = userID;
                    return;
                } else {
                    if(!booleans[26].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("OK", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[27].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[27].equals("")) {
                    buttons[27].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[27] = userID;
                    return;
                } else {
                    if(!booleans[27].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("OK", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[28].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[28].equals("")) {
                    buttons[28].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[28] = userID;
                    return;
                } else {
                    if(!booleans[28].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("OK", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[29].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[29].equals("")) {
                    buttons[29].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[29] = userID;
                    return;
                } else {
                    if(!booleans[29].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("OK", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[30].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[30].equals("")) {
                    buttons[30].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[30] = userID;
                    return;
                } else {
                    if(!booleans[30].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("OK", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[31].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[31].equals("")) {
                    buttons[31].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[31] = userID;
                    return;
                } else {
                    if(!booleans[31].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("OK", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[32].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[32].equals("")) {
                    buttons[32].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[32] = userID;
                    return;
                } else {
                    if(!booleans[32].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("OK", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[33].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[33].equals("")) {
                    buttons[33].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[33] = userID;
                    return;
                } else {
                    if(!booleans[33].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("OK", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[34].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[34].equals("")) {
                    buttons[34].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[34] = userID;
                    return;
                } else {
                    if(!booleans[34].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("OK", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        buttons[35].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[35].equals("")) {
                    buttons[35].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[35] = userID;
                    return;
                } else {
                    if(!booleans[35].equals(userID)){
                        AlertDialog dialog = new AlertDialog.Builder(BookSeatsActivity.this)
                                .setTitle("Cancelling the seat")
                                .setMessage("You Can't Cancel the Seat")
                                .setPositiveButton("OK", null)
                                .show();


                    }else {
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
                            }
                        });

                    }


                }
            }
        });
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> seats = new HashMap<>();
                g = 0;
                c = 0;

                firebaseAboutSeats(title);

                for (int j = 0; j < buttons.length; j++) {

                    if(!booleans[j].equals("")) {

                        if(booleans[j].equals(userID)){
                            String seat = "seat"+j;
                            seats.put(seat,userID);
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
            }
        });
    }

    private void firebasePassengerAboutBooking(int c, String userID, String title) {

        Log.i("12345", "firebasePassengerAboutBooking Outside");

        fStore.collection("User Booking History").document(userID).collection(userID).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                y = queryDocumentSnapshots.size();
                Log.i("12345", "firebasePassengerAboutBooking Inside ="+y);
                document1 = fStore.collection("User Booking History").document(userID).collection(userID).document(String.valueOf(y+1));
                Log.i("12345", "firebasePassengerAboutBooking Inside 2 ="+y+1);
                DocumentReference documentReference = fStore.collection("User Booking").document(userID);

                Map<String,Object> book = new HashMap<>();
                book.put("busID",title);
                book.put("enLocation",edLocation1);
                book.put("stLocation",stLocation);
                book.put("seats",c);

                int diff =stInt-enInt;
                Log.d("1111","differ "+diff);
                if ((1==diff) || (-1==diff)){
                    double k = Double.valueOf(c);
                    double pay = (50*k*0.2);
                    double fPay = (50*k);
                    double oBook = (50*0.2);
                    book.put("onePayment",oBook);
                    double rem = (50*0.8*k);
                    book.put("remain",rem);
                    book.put("forOne",50);
                    book.put("fullPayment",fPay);
                    book.put("paid",pay);
                    book.put("time",time);
                }else if ((2==diff) || (-2==diff)){
                    double k = Double.valueOf(c);
                    double pay = (100*k*0.2);
                    double fPay = (100*k);
                    double oBook = (100*0.2);
                    book.put("onePayment",oBook);
                    double rem = (100*0.8*k);
                    book.put("remain",rem);
                    book.put("forOne",100);
                    book.put("fullPayment",fPay);
                    book.put("paid",pay);
                    book.put("time",time);
                }else if ((3==diff) || (-3==diff)){
                    double k = Double.valueOf(c);
                    double pay = (180*k*0.2);
                    double fPay = (180*k);
                    double oBook = (180*0.2);
                    book.put("onePayment",oBook);
                    double rem = (180*0.8*k);
                    book.put("remain",rem);
                    book.put("forOne",180);
                    book.put("fullPayment",fPay);
                    book.put("paid",pay);
                    book.put("time",time);
                }else if ((4==diff) || (-4==diff)){
                    double k = Double.valueOf(c);
                    double pay = (250*k*0.2);
                    double fPay = (250*k);
                    double oBook = (250*0.2);
                    book.put("onePayment",oBook);
                    double rem = (250*0.8*k);
                    book.put("remain",rem);
                    book.put("forOne",250);
                    book.put("fullPayment",fPay);
                    book.put("paid",pay);
                    book.put("time",time);
                }else if ((5==diff) || (-5==diff)){
                    double k = Double.valueOf(c);
                    double pay = (400*k*0.2);
                    double fPay = (400*k);
                    double oBook = (400*0.2);
                    book.put("onePayment",oBook);
                    double rem = (400*0.8*k);
                    book.put("remain",rem);
                    book.put("forOne",400);
                    book.put("fullPayment",fPay);
                    book.put("paid",pay);
                    book.put("time",time);
                }else if ((6==diff) || (-6==diff)){
                    double k = Double.valueOf(c);
                    double pay = (550*k*0.2);
                    double fPay = (550*k);
                    double oBook = (550*0.2);
                    book.put("onePayment",oBook);
                    double rem = (550*0.8*k);
                    book.put("remain",rem);
                    book.put("forOne",550);
                    book.put("fullPayment",fPay);
                    book.put("paid",pay);
                    book.put("time",time);
                }else if ((7==diff) || (-7==diff)){
                    double k = Double.valueOf(c);
                    double pay = (900*k*0.2);
                    double fPay = (900*k);
                    double oBook = (900*0.2);
                    book.put("onePayment",oBook);
                    double rem = (900*0.8*k);
                    book.put("remain",rem);
                    book.put("forOne",900);
                    book.put("fullPayment",fPay);
                    book.put("paid",pay);
                    book.put("time",time);
                }


                document1.set(book);
                documentReference.set(book);
            }
        });

//        fStore.collection("User Booking History").document(userID).collection(userID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                Log.d("12345", "is Success"+ task.isSuccessful());
//                if (task.isSuccessful()) {
//
//                    y = task.getResult().size();
//                    Log.i("12345", "firebasePassengerAboutBooking Inside For"+y);
////                    for (QueryDocumentSnapshot document : task.getResult()) {
////
////                        y++;
////                        Log.i("12345", "firebasePassengerAboutBooking Inside For"+y);
////                    }
//
//                } else {
//
//                    Log.d("12345", "Error getting documents: ", task.getException());
//
//                }
//            }
//        });

        //String num = String.valueOf(y+1);


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

        Log.d("1111","Inside firebaseAboutSeats");

        DocumentReference documentReference = fStore.collection("BusSeats").document(this.title);
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
                            booleans[l]=getValue;
                            buttons[l].setBackgroundResource(android.R.color.holo_red_dark);

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
}