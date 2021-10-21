package com.nivacreation.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class BookSeatsActivity extends AppCompatActivity {

    ImageButton seat_1,seat_2,seat_3,seat_4,seat_5,seat_6,seat_7,seat_8,seat_9,seat_10,
            seat_11,seat_12,seat_13,seat_14,seat_15,seat_16,seat_17,seat_18,seat_19,seat_20,
            seat_21,seat_22,seat_23,seat_24,seat_25,seat_26,seat_27,seat_28,seat_29,seat_30,
            seat_31,seat_32,seat_33,seat_34,seat_35,seat_36,seat_37,seat_38,seat_39,seat_40,
            seat_41,seat_42,seat_43,seat_44,seat_45,seat_46,seat_47,seat_48,seat_49,seat_50;

    boolean isValue = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_seats);

        seat_1 = findViewById(R.id.seat_1); seat_11 = findViewById(R.id.seat_11); seat_23 = findViewById(R.id.seat_23);
        seat_2 = findViewById(R.id.seat_2); seat_12 = findViewById(R.id.seat_12); seat_24 = findViewById(R.id.seat_24);
        seat_3 = findViewById(R.id.seat_3); seat_13 = findViewById(R.id.seat_13); seat_25 = findViewById(R.id.seat_25);
        seat_4 = findViewById(R.id.seat_4); seat_14 = findViewById(R.id.seat_14); seat_26 = findViewById(R.id.seat_26);
        seat_5 = findViewById(R.id.seat_5); seat_15 = findViewById(R.id.seat_15); seat_27 = findViewById(R.id.seat_27);
        seat_6 = findViewById(R.id.seat_6); seat_16 = findViewById(R.id.seat_16); seat_29 = findViewById(R.id.seat_29);
        seat_7 = findViewById(R.id.seat_7); seat_17 = findViewById(R.id.seat_17); seat_30 = findViewById(R.id.seat_30);
        seat_8 = findViewById(R.id.seat_8); seat_18 = findViewById(R.id.seat_18); seat_31 = findViewById(R.id.seat_31);
        seat_9 = findViewById(R.id.seat_9); seat_19 = findViewById(R.id.seat_19); seat_32 = findViewById(R.id.seat_32);
        seat_10 = findViewById(R.id.seat_10); seat_20 = findViewById(R.id.seat_20); seat_33 = findViewById(R.id.seat_33);
        seat_22 = findViewById(R.id.seat_22); seat_21 = findViewById(R.id.seat_21); seat_28 = findViewById(R.id.seat_28);

        seat_34 = findViewById(R.id.seat_34); seat_45 = findViewById(R.id.seat_45);
        seat_35 = findViewById(R.id.seat_35); seat_46 = findViewById(R.id.seat_46);
        seat_36 = findViewById(R.id.seat_36); seat_47 = findViewById(R.id.seat_47);
        seat_37 = findViewById(R.id.seat_37); seat_48 = findViewById(R.id.seat_48);
        seat_38 = findViewById(R.id.seat_38); seat_49 = findViewById(R.id.seat_49);
        seat_39 = findViewById(R.id.seat_39); seat_50 = findViewById(R.id.seat_50);
        seat_40 = findViewById(R.id.seat_40);
        seat_41 = findViewById(R.id.seat_41);
        seat_42 = findViewById(R.id.seat_42);
        seat_43 = findViewById(R.id.seat_43);
        seat_44 = findViewById(R.id.seat_44);

        seat_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isValue){
                    seat_1.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_1.setBackgroundResource(android.R.color.background_light);
                isValue = true;

            }
        });
        seat_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isValue){
                    seat_2.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_2.setBackgroundResource(android.R.color.background_light);
                isValue = true;

            }
        });
        seat_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_3.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_3.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_4.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_4.setBackgroundResource(android.R.color.background_light);
                isValue = true;

            }
        });
        seat_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_5.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_5.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_6.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_6.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_7.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_7.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_8.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_8.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_9.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_9.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_10.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_10.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_11.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_11.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_12.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_12.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_13.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_13.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_14.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_14.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_15.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_15.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_16.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_16.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_17.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_17.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_18.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_18.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_19.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_19.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_20.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_20.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_21.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_21.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_22.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_22.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_23.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_23.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_24.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_24.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_25.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_25.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_26.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_26.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_27.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_27.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_28.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_28.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_29.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_29.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_30.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_30.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_31.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_31.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_32.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_32.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_33.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_33.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_34.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_34.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_35.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_35.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_36.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_36.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_37.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_37.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_37.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_38.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_38.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_38.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_39.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_39.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_39.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_40.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_40.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_41.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_41.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_42.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_42.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_43.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_43.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_44.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_44.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_45.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_45.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_46.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_46.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_46.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_47.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_47.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_47.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_48.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_48.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_48.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_49.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_49.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_49.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
        seat_50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValue){
                    seat_50.setBackgroundResource(android.R.color.holo_red_dark);
                    isValue = false;
                    return;
                }
                seat_50.setBackgroundResource(android.R.color.background_light);
                isValue = true;
            }
        });
    }
}