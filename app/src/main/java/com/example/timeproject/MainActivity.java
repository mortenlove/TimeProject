package com.example.timeproject;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import android.widget.TextView;


import java.time.DayOfWeek;
import java.time.ZonedDateTime;

import java.util.Timer;
import java.util.TimerTask;



class Convert
{
    // КОНВЕРТАЦИЯ ВРЕМЕНИ
    public int converted;
    public int getConvert(int hour, int min)
    {
        converted = (hour * 60 + min);
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

class LessonsCalc
{

    final int[] nomondaystartlessonshours = new int[] {8,8,9,10,11,12,13,13,14,15,16,16,17,18};
    final int[] nomondayendlessonshours = new int[] {8,9,10,11,11,12,13,14,15,15,16,17,18,19};

    final int[] nomondaystartlessonsminutes = new int[] {0,50,35,20,15,0,0,45,30,15,5,50,35,20};
    final int[] nomondayendlessonsminutes = new int[] {40,30,15,0,55,40,40,25,10,55,45,30,15,0};

    Convert conv = new Convert();
    Terms terms = new Terms();
    public String lefttime;
    public String getNoMonday(int hour, int minutes, int seconds)
    {

        for (int i = 0; i < nomondaystartlessonshours.length; i++)
        {
            if (terms.getTerms(conv.getConvert(nomondaystartlessonshours[i], nomondaystartlessonsminutes[i]), conv.getConvert(nomondayendlessonshours[i], nomondayendlessonsminutes[i]), conv.getConvert(hour, minutes)))
            {
//                timetxt.setText("До конца урока: " + (conv.getConvert(endlessonshours[i], endlessonsminutes[i]) - conv.getConvert(hour, minutes)) + " минут.");
                lefttime = "До конца урока: " + (conv.getConvert(nomondayendlessonshours[i], nomondayendlessonsminutes[i]) - conv.getConvert(hour, minutes)) + " минут "  + (60 - seconds) + " секунд.";
            }
            else
            {
                if (i != 0)
                {
                    if (conv.getConvert(hour, minutes) <= conv.getConvert(nomondaystartlessonshours[i], nomondaystartlessonsminutes[i]) & conv.getConvert(hour, minutes) >= conv.getConvert(nomondayendlessonshours[i - 1], nomondayendlessonsminutes[i - 1]))
                    {
//                        timetxt.setText("До конца перемены: " + (conv.getConvert(startlessonshours[i] ,startlessonsminutes[i])-conv.getConvert(hour,minutes)) + " минут.");
                        lefttime = "До конца перемены: " + (conv.getConvert(nomondaystartlessonshours[i] ,nomondaystartlessonsminutes[i])-conv.getConvert(hour,minutes)) + " минут " + (60 - seconds) + " секунд.";
                    }
                }
            }
        }
        return lefttime;
    }

    public String getMonday(int hour, int minutes, int seconds)
    {
        final int[] mondaystartlessonshours = new int[] {7,8,9,9,10,11,12,13,13,14,15,15,16,17,18,18};
        final int[] mondaystartlessonsminutes = new int[] {45,15,0,45,30,25,10,10,40,25,10,55,40,25,10,55};

        final int[] mondayendlessonshours = new int[] {8,8,9,10,11,12,12,13,14,15,15,16,17,18,18,19};
        final int[] mondayendlessonsminutes = new int[] {10,55,40,25,10,5,50,35,20,5,50,35,20,5,50,35};

        for (int i = 0; i < mondaystartlessonshours.length; i++)
        {
            if (terms.getTerms(conv.getConvert(mondaystartlessonshours[i], mondaystartlessonsminutes[i]), conv.getConvert(mondayendlessonshours[i], mondayendlessonsminutes[i]), conv.getConvert(hour, minutes)))
            {
//                timetxt.setText("До конца урока: " + (conv.getConvert(endlessonshours[i], endlessonsminutes[i]) - conv.getConvert(hour, minutes)) + " минут.");
                lefttime = "До конца урока: " + (conv.getConvert(mondayendlessonshours[i], mondayendlessonsminutes[i]) - conv.getConvert(hour, minutes)) + " минут " + (60 - seconds) + " секунд.";
            }
            else
            {
                if (i != 0)
                {
                    if (conv.getConvert(hour, minutes) <= conv.getConvert(mondaystartlessonshours[i], mondaystartlessonsminutes[i]) & conv.getConvert(hour, minutes) >= conv.getConvert(mondayendlessonshours[i - 1], mondayendlessonsminutes[i - 1]))
                    {
//                        timetxt.setText("До конца перемены: " + (conv.getConvert(startlessonshours[i] ,startlessonsminutes[i])-conv.getConvert(hour,minutes)) + " минут.");
                        lefttime = "До конца перемены: " + (conv.getConvert(mondaystartlessonshours[i] ,mondaystartlessonsminutes[i])-conv.getConvert(hour,minutes)) + " минут " + (60 - seconds) + " секунд.";
                    }
                }
            }
        }
        return lefttime;
    }
}

public class MainActivity extends AppCompatActivity
{


    int lesson;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView timetxt = (TextView) findViewById(R.id.textView);
        final TextView outtime = (TextView) findViewById(R.id.textView2);
        timetxt.setFontFeatureSettings("Times New Roman");

        Timer timer = new Timer();
        LessonsCalc lessons = new LessonsCalc();

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
                        ZonedDateTime zone = ZonedDateTime.now();
                        int hour = zone.getHour();
                        int minutes = zone.getMinute();
                        int seconds = zone.getSecond();
                        DayOfWeek dayofweek = zone.getDayOfWeek();
                        System.out.println(dayofweek.toString());
                        outtime.setText(hour + ":" + minutes + ":" + seconds);
                        if (dayofweek.toString() == "MONDAY")
                        {
                            timetxt.setText(lessons.getMonday(hour,minutes,seconds));
                        }
                        else
                        {
                            timetxt.setText(lessons.getNoMonday(hour,minutes,seconds));
                        }



                    }
                });
            }

        },0,1000);

    }
}


//                                if(i%2==1)
//                                {
//                                    if (conv.getConvert(hour,minutes) <= conv.getConvert(startlessonshours[i] ,startlessonsminutes[i]) & conv.getConvert(hour,minutes) >= conv.getConvert(endlessonshours[i-1] ,endlessonsminutes[i-1]))
//                                    {
//                                        timetxt.setText("До конца перемены: " + (conv.getConvert(startlessonshours[i] ,startlessonsminutes[i])-conv.getConvert(hour,minutes)) + " минут.");
//                                    }
//                                }
//                                else if (i%2!=1 & i != 0)
//                                {
//                                    if (conv.getConvert(hour,minutes) <= conv.getConvert(startlessonshours[i] ,startlessonsminutes[i]) & conv.getConvert(hour,minutes) >= conv.getConvert(endlessonshours[i-1] ,endlessonsminutes[i-1]))
//                                    {
//                                        timetxt.setText("До конца перемены: " + (conv.getConvert(startlessonshours[i] ,startlessonsminutes[i])-conv.getConvert(hour,minutes)) + " минут.");
//                                    }
//
//                                }
