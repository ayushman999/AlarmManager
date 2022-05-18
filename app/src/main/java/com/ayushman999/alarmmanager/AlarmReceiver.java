package com.ayushman999.alarmmanager;
//extend the broadcast receiver and will work when the event happens

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {

    //Whenever this particular notification is pushed onReceive method will run
    //Also add receiver in manifest
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, DestinationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0, i, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "amsandroid")
                .setSmallIcon(R.drawable.ic_launcher_background)              //icon when notification is displayed
                .setContentTitle("Ams Android Manager")                         //title
                .setContentText("Here is some more text")                       //desc
                .setAutoCancel(true)                                            //on press cancelled
                .setDefaults(NotificationCompat.DEFAULT_ALL)                    //set all the defaults
                .setPriority(NotificationCompat.PRIORITY_HIGH);             //pop up on lock screen
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123,builder.build());
    }
}
