package com.example.timeproject;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class LessonService extends Service {

    final int[] nomondaystartlessonshours = new int[] {8,8,9,10,11,12,13,13,14,15,16,16,17,18};
    final int[] nomondayendlessonshours = new int[] {8,9,10,11,11,12,13,14,15,15,16,17,18,19};

    final int[] nomondaystartlessonsminutes = new int[] {0,50,35,20,15,0,0,45,30,15,5,50,35,20};
    final int[] nomondayendlessonsminutes = new int[] {40,30,15,0,55,40,40,25,10,55,45,30,15,0};

    final int[] nomondaynumberlessons = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14};

    final int[] mondaystartlessonshours = new int[] {7,8,9,9,10,11,12,13,13,14,15,15,16,17,18,18};
    final int[] mondaystartlessonsminutes = new int[] {45,15,0,45,30,25,10,10,40,25,10,55,40,25,10,55};

    final int[] mondayendlessonshours = new int[] {8,8,9,10,11,12,12,13,14,15,15,16,17,18,18,19};
    final int[] mondayendlessonsminutes = new int[] {10,55,40,25,10,5,50,35,20,5,50,35,20,5,50,35};

    final int[] mondaynumberlessons = new int[] {0,1,2,3,4,5,6,0,7,8,9,10,11,12,13,14};

    private static final int NOTIFY_ID = 15605;

    private static final String CHANNEL_ID = "Polytechrasp";

    private NotificationManager mNM;

    Timer timer = new Timer();
    LessonsCalc lessons = new LessonsCalc();

    @Override
    public void onCreate() {
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        MainCalculate();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        timer.cancel();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.cancel(NOTIFY_ID);
    }

    private  void MainCalculate()
    {
        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {

                ZonedDateTime zone = ZonedDateTime.now();

                int hour = zone.getHour();
                int minutes = zone.getMinute();
                int seconds = zone.getSecond();
                DayOfWeek dayofweek = zone.getDayOfWeek();
                //String dayofweek = "MONDAY";
                if (dayofweek.toString() == "MONDAY")
                {
                    lessons.getLefttime(hour,minutes,seconds,mondaystartlessonshours,mondayendlessonshours,mondaystartlessonsminutes,mondayendlessonsminutes,mondaynumberlessons);
                    NotificationLessons(lessons.title, lessons.mainText);
                }
                else if (dayofweek.toString() == "SATURDAY" || dayofweek.toString() == "SUNDAY" || dayofweek.toString() == "FRIDAY" && hour >= 19)
                {
                    NotificationLessons("ВЫХОДНОЙ", "");
                }
                else
                {
                    lessons.getLefttime(hour,minutes,seconds,nomondaystartlessonshours,nomondayendlessonshours,nomondaystartlessonsminutes,nomondayendlessonsminutes,nomondaynumberlessons);
                    NotificationLessons(lessons.title, lessons.mainText);
                }
            }
        },0,1000);
    }

    private void NotificationLessons(String title, String mainText) {

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, LessonService.class), PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_popup_reminder)
                .setContentTitle(title)
                .setContentText(mainText)
                .setContentIntent(contentIntent)
                .setOngoing(true);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(LessonService.this);
        createChannelIfNeeded(notificationManager);

        mNM.notify(NOTIFY_ID ,builder.build());
    }

    private static void createChannelIfNeeded(NotificationManagerCompat manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
    }
}
