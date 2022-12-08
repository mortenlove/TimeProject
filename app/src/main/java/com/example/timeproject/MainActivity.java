package com.example.timeproject;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import android.widget.TextView;


import java.time.ZonedDateTime;

import java.util.Timer;
import java.util.TimerTask;



public class MainActivity extends AppCompatActivity {


    public int[] lessonshours = new int[] {8,8,8,9,9,10,10,11,11,11,12,12,13,13,13,14,14,15,15,15,16,16,16,17,17,18,18,19};
    public int[] lessonsminutes = new int[] {0,40,50,30,35,15,20,0,15,55,0,40,0,40,45,25,30,10,15,55,5,45,50,30,35,15,20,0};
    int lesson;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView timetxt = (TextView) findViewById(R.id.textView);
        final TextView outtime = (TextView) findViewById(R.id.textView2);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ZonedDateTime zone = ZonedDateTime.now();
                        int hour = zone.getHour();
                        int minutes = zone.getMinute();
                        int seconds = zone.getSecond();
//                        timetxt.setText(hour + ":" + minutes + ":" + seconds);
                        for (int i = 0; i < lessonshours.length; i += 2) {
                            System.out.println(lessonshours[i] + ":" + lessonsminutes[i] + " - " + lessonshours[i+1]+ ":" + lessonsminutes[i+1]);
                            if ((hour == lessonshours[i] && minutes >= lessonsminutes[i]) || (hour == lessonshours[i+1] && minutes <= lessonsminutes[i+1])) {
                                int left = lessonsminutes[i+1]-minutes;
                                timetxt.setText("Осталось до конца - " + left);
                                System.out.println("True " + lessonshours[i] + ":" + lessonsminutes[i]);
                            }
                            else {
                                System.out.println("False ");
                            }
                        }
                    }
                });


            }


        },0,1000);




    }
}