package com.nivacreation.login.model;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import java.util.Collections;

public class AppNotificationChaneel extends Application {

    public static String c1 = "channel1";
    public static int chStart = 1;

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }
    public void createNotificationChannels(){

        c1 = "c"+ String.valueOf(chStart);
        Log.e("1234", c1);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(c1,"channel1", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("Bus is on the way");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannels(Collections.singletonList(channel1));


        }
        chStart++;
    }
}
