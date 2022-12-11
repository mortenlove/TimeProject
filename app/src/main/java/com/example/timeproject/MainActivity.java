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
    // ПЕРЕМЕНА
    public boolean isaterms1;

    public boolean getTerms1(int convtime, int convtime1, int currtime)
    {
        isaterms1 = currtime <= convtime & currtime >= convtime1;
        return isaterms1;
    }
}

class LessonsCalc
{


    Convert conv = new Convert();
    Terms terms = new Terms();

    public String lefttime;

    public String getLefttime(int hour, int minutes, int seconds, int[] startlessonhours,int[] endlessonhour, int[] startlessonminutes, int[] endlessonminutes, int[] numberlesson)
    {
        for (int i = 0; i < startlessonhours.length; i++)
        {
            if (terms.getTerms(conv.getConvert(startlessonhours[i], startlessonminutes[i]), conv.getConvert(endlessonhour[i], endlessonminutes[i]), conv.getConvert(hour, minutes)))
            {
                lefttime = "До конца " + numberlesson[i] + " урока: " + (conv.getConvert(endlessonhour[i], endlessonminutes[i]) - conv.getConvert(hour, minutes)) + " минут " + (60 - seconds) + " секунд.";
            }
            else
            {
                if (i != 0)
                {
                    if (terms.getTerms1(conv.getConvert(hour,minutes), conv.getConvert(startlessonhours[i], startlessonminutes[i]), conv.getConvert(endlessonhour[i-1], endlessonminutes[i-1])))
                    {
                        lefttime = "До конца перемены между " + numberlesson[i-1] + " и " + numberlesson[i]  + "уроками: " + (conv.getConvert(startlessonhours[i] ,startlessonminutes[i])-conv.getConvert(hour,minutes)) + " минут " + (60 - seconds) + " секунд.";
                    }
                }
            }
        }
        return lefttime;
    }

}

public class MainActivity extends AppCompatActivity
{

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

    int lesson;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView timetxt = (TextView) findViewById(R.id.textView);
        final TextView outtime = (TextView) findViewById(R.id.textView2);

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
//                      String dayofweek = "TUESDAY";
                        System.out.println(dayofweek.toString());
                        outtime.setText(hour + ":" + minutes + ":" + seconds);
                        if (dayofweek.toString() == "MONDAY")
                        {
                            timetxt.setText(lessons.getLefttime(hour,minutes,seconds,mondaystartlessonshours,mondayendlessonshours,mondaystartlessonsminutes,mondayendlessonsminutes,mondaynumberlessons));
                        }
                        else if (dayofweek.toString() == "SATURDAY" || dayofweek.toString() == "SUNDAY")
                        {
                            timetxt.setText("ВЫХОДНОЙ");
                        }
                        else
                        {
                            timetxt.setText(lessons.getLefttime(hour,minutes,seconds,nomondaystartlessonshours,nomondayendlessonshours,nomondaystartlessonsminutes,nomondayendlessonsminutes,nomondaynumberlessons));
                        }



                    }
                });
            }

        },0,1000);

    }
}
