package com.example.timeproject;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import android.provider.Settings;
import android.widget.TextView;


import java.time.ZonedDateTime;

import java.util.Timer;
import java.util.TimerTask;

class Convert
{
    // КОНВЕРТАЦИЯ ВРЕМЕНИ
    public int converted;
    public int getConvert(int hour, int min)
    {
        converted = hour * 60 + min;
        return converted;
    }
}

class Terms
{
    // УРОК
    public boolean isaterms;
    public boolean getTerms(int convtime,int convtime1, int currtime)
    {
        isaterms = currtime >= convtime & currtime <= convtime1;
        return isaterms;
    }
    // ПЕЕМЕНА
    public boolean isaterms1;

    public boolean getTerms1(int convtime, int convtime1, int currtime)
    {
        isaterms1 = currtime > convtime & currtime < convtime1;
        return isaterms1;
    }
}

//class Calculate
//{
//    // ПОДСЧЕТ ОСТАВШЕГОСЯ ВРЕМЕНИ
//    public int mins;
//    public int getMins(int currmins, int calcmins)
//    {
//        mins = calcmins - currmins;
//        return mins;
//    }
//}

//class Output
//{
//    public boolean isoutput;
//    public boolean setOutput(S)
//}

public class MainActivity extends AppCompatActivity
{


    final int[] startlessonshours = new int[] {8,8,9,10,11,12,13,13,14,15,16,16,17,18};
    final int[] startlessonsminutes = new int[] {0,50,35,20,15,0,0,45,30,15,5,50,35,20};
    final int[] endlessonshours = new int[] {8,9,10,11,11,12,13,14,15,15,16,17,18,19};
    final int[] endlessonsminutes = new int[] {40,30,15,0,55,40,40,25,10,55,45,30,15,0};
    int lesson;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView timetxt = (TextView) findViewById(R.id.textView);
        final TextView outtime = (TextView) findViewById(R.id.textView2);

        Convert conv = new Convert();
        Terms terms = new Terms();

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        System.out.println("HELLO WORLD");
                        ZonedDateTime zone = ZonedDateTime.now();
                        int hour = zone.getHour();
                        int minutes = zone.getMinute();
                        int seconds = zone.getSecond();
                        outtime.setText(hour + ":" + minutes + ":" + seconds);
                        for (int i = 0; i < startlessonshours.length; i++)
                        {
//                            for (int j = 1; j < startlessonsminutes.length; j++)
//                            {
                                if (terms.getTerms(conv.getConvert(startlessonshours[i], startlessonsminutes[i]), conv.getConvert(endlessonshours[i], endlessonsminutes[i]), conv.getConvert(hour, minutes)))
                                {
                                    timetxt.setText("" + (conv.getConvert(endlessonshours[i], endlessonsminutes[i]) - conv.getConvert(hour, minutes)));
                                }
                                else
                                {
//                                    if (terms.getTerms1(conv.getConvert(endlessonshours[i], endlessonsminutes[i]), conv.getConvert(startlessonshours[j], startlessonsminutes[j]), conv.getConvert(hour, minutes)))
//                                    {
//                                        timetxt.setText("" + (conv.getConvert(startlessonshours[j], startlessonsminutes[j]) - conv.getConvert(hour, minutes)));
//                                    }
                                }
//                            }
                        }
                    }
                });
            }

        },0,1000);

    }
}