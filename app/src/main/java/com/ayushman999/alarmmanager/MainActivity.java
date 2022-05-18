package com.ayushman999.alarmmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Toast;

import com.ayushman999.alarmmanager.databinding.ActivityMainBinding;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MaterialTimePicker picker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
            }
        });

        binding.cancelAlarm.setOnClickListener(v->{
            cancelAlarm();
        });

        binding.setAlarm.setOnClickListener(v->{
            setAlarm();
        });
    }

    private void cancelAlarm() {
         Intent intent = new Intent(this, AlarmReceiver.class);
         pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

         if( alarmManager == null){
             alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
         }

         alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Cancelled", Toast.LENGTH_SHORT).show();
    }

    private void setAlarm() {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent= new Intent(this,AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,pendingIntent);
        Toast.makeText(this, "Alarm Set!", Toast.LENGTH_SHORT).show();

    }

    private void showTimePicker() {
        picker = new MaterialTimePicker.Builder()                   //instance
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Set Alarm Time")
                .build();
        picker.show(getSupportFragmentManager(), "amsandroid");

        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(picker.getHour()>12){
                    binding.textView.setText(
                            String.format("%02d",(picker.getHour()-12)+" : "+String.format("%02d",picker.getMinute())+" PM")
                    );
                }
                else {
                    binding.textView.setText(picker.getHour()+" : "+ picker.getMinute() + " AM");

                }

                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,picker.getHour());
                calendar.set(Calendar.MINUTE,picker.getMinute());
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);
            }
        });
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name ="AmsRemindingChannel";
            String description = "Channel For Alarm Manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("amsandroid",name,importance);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}