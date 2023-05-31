

import android.app.AlarmManager;

import android.app.Notification;

import android.app.NotificationChannel;

import android.app.NotificationManager;

import android.content.BroadcastReceiver;

import android.content.Context;

import android.content.Intent;

import android.content.IntentFilter;

import android.os.Build;

import android.os.Bundle;

import android.view.View;

import android.widget.Button;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.NotificationCompat;

import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "water_reminder_channel";

    private static final int NOTIFICATION_ID = 1;

    private static final int INTERVAL_MINUTES = 60; // Interval between reminders in minutes

    private static final int TOTAL_REMINDERS = 8; // Total number of reminders to be shown

    private Button startButton;

    private Button stopButton;

    private BroadcastReceiver receiver;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        createNotificationChannel();

        startButton = findViewById(R.id.start_button);

        stopButton = findViewById(R.id.stop_button);

        startButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                startWaterReminder();

            }

        });

        stopButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                stopWaterReminder();

            }

        });

        // Register a broadcast receiver to listen for the reminder notifications

        receiver = new BroadcastReceiver() {

            @Override

            public void onReceive(Context context, Intent intent) {

                showNotification();

            }

        };

        registerReceiver(receiver, new IntentFilter("WATER_REMINDER"));

    }

    @Override

    protected void onDestroy() {

        super.onDestroy();

        unregisterReceiver(receiver);

    }

    private void startWaterReminder() {

        // Set up the alarm manager to schedule reminders

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent("WATER_REMINDER");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        // Calculate the initial reminder time

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MINUTE, INTERVAL_MINUTES);

        long startTime = calendar.getTimeInMillis();

        // Schedule the reminders

        for (int i = 0; i < TOTAL_REMINDERS; i++) {

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, startTime, pendingIntent);

            startTime += INTERVAL_MINUTES * 60 * 1000; // Increment the reminder time

        }

        Toast.makeText(this, "Water reminders started", Toast.LENGTH_SHORT).show();

    }

    private void stopWaterReminder() {

        // Cancel the alarms and remove any pending intents

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent("WATER_REMINDER");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        alarmManager.cancel(pendingIntent);

        pendingIntent.cancel();

        Toast.makeText(this, "Water reminders stopped", Toast.LENGTH_SHORT).show();

    }

    private void showNotification() {

        // Build the notification

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)

                .setSmallIcon

ActivityMain.java 👆👆👆

<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"

    xmlns:tools="http://schemas.android.com/tools"

    android:layout_width="match_parent"

    android:layout_height="match_parent"

    android:paddingBottom="16dp"

    android:paddingLeft="16dp"

    android:paddingRight="16dp"

    android:paddingTop="16dp"

    tools:context=".MainActivity">

    <Button

        android:id="@+id/start_button"

        android:layout_width="match_parent"

        android:layout_height="wrap_content"

        android:layout_marginBottom="16dp"

        android:text="Start Water Reminder" />

    <Button

        android:id="@+id/stop_button"

        android:layout_width="match_parent"

        android:layout_height="wrap_content"

        android:layout_below="@id/start_button"

        android:text="Stop Water Reminder" />

</RelativeLayout>

activity_main.xml👆👆
