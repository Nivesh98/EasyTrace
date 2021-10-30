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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class BookSeatsActivity extends AppCompatActivity implements View.OnClickListener {

    Button[] buttons = new Button[36];
    boolean[] booleans = new boolean[36];

    String title, travelRoute,stLocation, edLocation1;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    //    private boolean isValue0 = true, isValue1 = true;
    int i;
    int k;
    int g =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_seats);

        title = getIntent().getStringExtra("title");
        travelRoute = getIntent().getStringExtra("trRoute");
        stLocation = getIntent().getStringExtra("stLocation");
        edLocation1 = getIntent().getStringExtra("endLocation");

        fStore = FirebaseFirestore.getInstance();

        Button count = findViewById(R.id.count);

        for (i = 0; i < buttons.length; i++) {
            String buttonID = "seat" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            booleans[i] = true;
            buttons[i] = findViewById(resID);
            buttons[i].setOnClickListener(this);
        }

        //Retriew current seats data from firebasefirestore
       firebaseAboutSeats(title);


        buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[0]) {
                    buttons[0].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[0] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[0] = true;

                }
            }
        });
        buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[1]) {
                    buttons[1].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[1] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[1] = true;

                }
            }
        });

        buttons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[2]) {
                    buttons[2].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[2] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[2] = true;

                }
            }
        });

        buttons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[3]) {
                    buttons[3].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[3] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[3] = true;

                }
            }
        });

        buttons[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[4]) {
                    buttons[4].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[4] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[4] = true;

                }
            }
        });
        buttons[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[5]) {
                    buttons[5].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[5] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[5] = true;

                }
            }
        });

        buttons[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[6]) {
                    buttons[6].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[6] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[6] = true;

                }
            }
        });
        buttons[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[7]) {
                    buttons[7].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[7] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[7] = true;

                }
            }
        });
        buttons[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[8]) {
                    buttons[8].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[8] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[8] = true;

                }
            }
        });
        buttons[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[9]) {
                    buttons[9].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[9] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[9] = true;

                }
            }
        });
        buttons[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[10]) {
                    buttons[10].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[10] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[10] = true;

                }
            }
        });
        buttons[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[11]) {
                    buttons[11].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[11] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[11] = true;

                }
            }
        });
        buttons[12].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[12]) {
                    buttons[12].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[12] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[12] = true;

                }
            }
        });
        buttons[13].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[13]) {
                    buttons[13].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[13] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[13] = true;

                }
            }
        });
        buttons[14].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[14]) {
                    buttons[14].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[14] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[14] = true;

                }
            }
        });
        buttons[15].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[15]) {
                    buttons[15].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[15] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[15] = true;

                }
            }
        });
        buttons[16].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[16]) {
                    buttons[16].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[16] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[16] = true;

                }
            }
        });
        buttons[17].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[17]) {
                    buttons[17].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[17] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[17] = true;

                }
            }
        });
        buttons[18].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[18]) {
                    buttons[18].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[18] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[18] = true;

                }
            }
        });
        buttons[19].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[19]) {
                    buttons[19].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[19] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[19] = true;

                }
            }
        });
        buttons[20].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[20]) {
                    buttons[20].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[20] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[20] = true;

                }
            }
        });
        buttons[21].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[21]) {
                    buttons[21].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[21] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[21] = true;

                }
            }
        });
        buttons[22].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[22]) {
                    buttons[22].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[22] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[22] = true;

                }
            }
        });
        buttons[23].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[23]) {
                    buttons[23].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[23] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[23] = true;

                }
            }
        });
        buttons[24].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[24]) {
                    buttons[24].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[24] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[24] = true;

                }
            }
        });
        buttons[25].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[25]) {
                    buttons[25].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[25] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[25] = true;

                }
            }
        });
        buttons[26].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[26]) {
                    buttons[26].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[26] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[26] = true;

                }
            }
        });
        buttons[27].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[27]) {
                    buttons[27].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[27] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[27] = true;

                }
            }
        });
        buttons[28].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[28]) {
                    buttons[28].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[28] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[28] = true;

                }
            }
        });
        buttons[29].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[29]) {
                    buttons[29].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[29] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[29] = true;

                }
            }
        });
        buttons[30].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[30]) {
                    buttons[30].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[30] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[30] = true;

                }
            }
        });
        buttons[31].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[31]) {
                    buttons[31].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[31] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[31] = true;

                }
            }
        });
        buttons[32].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[32]) {
                    buttons[32].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[32] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[32] = true;

                }
            }
        });
        buttons[33].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[33]) {
                    buttons[33].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[33] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[33] = true;

                }
            }
        });
        buttons[34].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[34]) {
                    buttons[34].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[34] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[34] = true;

                }
            }
        });
        buttons[35].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleans[35]) {
                    buttons[35].setBackgroundResource(android.R.color.holo_red_dark);
                    booleans[35] = false;
                    return;
                } else {
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
                        }
                    });
                    booleans[35] = true;

                }
            }
        });
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> seats = new HashMap<>();
                g = 0;
                String userID = "87HATPpL1MQ0hhunLRzQkzXQoDt2";
                //userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                firebaseAboutSeats(title);

                for (int j = 0; j < buttons.length; j++) {

                    if (booleans[j] == false) {
                        String seat = "seat"+j;
                        seats.put(seat,userID);
                        g++;
                    } else {
                        String seat = "seat"+j;
                        seats.put(seat,"");

                    }

                }
                FirebaseFirestore.getInstance().collection("BusSeats").document(title).update(seats);
                Toast.makeText(BookSeatsActivity.this, "Booked seats are " + g, Toast.LENGTH_SHORT).show();
            }
        });
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
                            booleans[l]=false;
                            buttons[l].setBackgroundResource(android.R.color.holo_red_dark);

                        }else{
                            booleans[l]=true;
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