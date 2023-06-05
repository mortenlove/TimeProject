package com.example.timeproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.CompoundButton;
import android.widget.TextView;


import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.util.Timer;
import java.util.TimerTask;


class Convert
{
    // КОНВЕРТАЦИЯ ВРЕМЕНИ
    private int converted;
    public int getConvert(int hour, int min)
    {
        converted = (hour * 60 + min);
        return converted;
    }
}



class Terms
{
    // УРОК
    private boolean isaterms;
    public boolean getTerms(int convtime,int convtime1, int currtime)
    {
        isaterms = currtime >= convtime & currtime <= convtime1;
        return isaterms;
    }
    // ПЕРЕМЕНА
    private boolean isaterms1;

    public boolean getTerms1(int currtime, int convtime, int convtime1)
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
    public String lefttime2;
    public String lefttime3;
    public String title;
    public String mainText;

    public void getLefttime(int hour, int minutes, int seconds, int[] startlessonhours,int[] endlessonhour, int[] startlessonminutes, int[] endlessonminutes, int[] numberlesson)
    {
        for (int i = 0; i < startlessonhours.length; i++)
        {
            if (terms.getTerms(conv.getConvert(startlessonhours[i], startlessonminutes[i]), conv.getConvert(endlessonhour[i], endlessonminutes[i]), conv.getConvert(hour, minutes)))
            {
                //lefttime = "До конца " + numberlesson[i] + " урока: " + (conv.getConvert(endlessonhour[i], endlessonminutes[i]) - 1 - conv.getConvert(hour, minutes)) + " минут " + (60 - seconds) + " секунд.";
                lefttime = "До конца";
                lefttime2 = (conv.getConvert(endlessonhour[i], endlessonminutes[i]) - 1 - conv.getConvert(hour, minutes)) + " минут " + (60 - seconds) + " секунд.";
                lefttime3 = numberlesson[i] + " урока";
                title = "Урок " + numberlesson[i];
                mainText = "До конца: " + (conv.getConvert(endlessonhour[i], endlessonminutes[i]) - 1 - conv.getConvert(hour, minutes)) + " минут " + (60 - seconds) + " секунд.";
            }
            else if (i != 0)
            {
                if (terms.getTerms1(conv.getConvert(hour,minutes), conv.getConvert(startlessonhours[i], startlessonminutes[i]), conv.getConvert(endlessonhour[i-1], endlessonminutes[i-1])))
                {
                    //lefttime = "До конца перемены между " + numberlesson[i-1] + " и " + numberlesson[i]  + " уроками: " + (conv.getConvert(startlessonhours[i] ,startlessonminutes[i]) - conv.getConvert(hour,minutes) - 1) + " минут " + (60 - seconds) + " секунд.";
                    lefttime = "До конца перемены между";
                    lefttime2 = (conv.getConvert(startlessonhours[i] ,startlessonminutes[i]) - conv.getConvert(hour,minutes) - 1) + " минут " + (60 - seconds) + " секунд.";

                    lefttime3 = numberlesson[i-1] + " и " + numberlesson[i];
                    title = "Следующий урок " + numberlesson[i];
                    mainText = "До начала: " + (conv.getConvert(startlessonhours[i] ,startlessonminutes[i]) - conv.getConvert(hour,minutes) - 1) + " минут " + (60 - seconds) + " секунд.";
                }
            }
            else //if (conv.getConvert(hour,minutes) > conv.getConvert(endlessonhour[endlessonhour.length-1],endlessonminutes[endlessonminutes.length-1]) & conv.getConvert(hour,minutes) < conv.getConvert(startlessonhours[0],startlessonminutes[0]))
            {
                // ВРЕМЯ ДО НАЧАЛА ПАР
                // ДОБАВИТЬ ПРОВЕРКУ ЗАВТРАШНЕГО ДНЯ ЧЕРЕЗ КЛАСС
                if (conv.getConvert(hour,minutes) > conv.getConvert(startlessonhours[0],startlessonminutes[0]))
                {
                    lefttime = (conv.getConvert(24,0) - conv.getConvert(hour,minutes) + conv.getConvert(startlessonhours[0],startlessonminutes[0])) / 60 + ":" + (conv.getConvert(24,0) - conv.getConvert(hour,minutes) + conv.getConvert(startlessonhours[0],startlessonminutes[0])-1) % 60 + ":" + (60 - seconds);
//                    System.out.println(lefttime);
//                    System.out.println("1");
                }
                else
                {
                    lefttime = (conv.getConvert(startlessonhours[0],startlessonminutes[0]) - conv.getConvert(hour,minutes)) / 60 + ":" + (conv.getConvert(startlessonhours[0],startlessonminutes[0]) - conv.getConvert(hour,minutes)-1) % 60 + ":" + (60 - seconds);
//                    System.out.println(lefttime);
//                    System.out.println("2");
                }
            }
        }
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
    public static final String APP_PREFERENCES = "settings";
    final String KEY_SWITCH_STATE = "SAVED_SWITCH_STATE";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView timetxt = findViewById(R.id.textView);
        TextView timetxt2 = findViewById(R.id.textView2);
        TextView timetxt3 = findViewById(R.id.textView3);

//        TextView outtime = findViewById(R.id.textView2);

        Timer timer = new Timer();
        LessonsCalc lessons = new LessonsCalc();

        LoadPreferences();

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
                        //String dayofweek = "MONDAY";
                        //System.out.println(dayofweek.toString());
//                        outtime.setText(hour + ":" + minutes + ":" + seconds);
                        if (dayofweek.toString() == "MONDAY")
                        {
                            lessons.getLefttime(hour,minutes,seconds,mondaystartlessonshours,mondayendlessonshours,mondaystartlessonsminutes,mondayendlessonsminutes,mondaynumberlessons);
                            timetxt.setText(lessons.lefttime);
                            timetxt2.setText(lessons.lefttime2);
                            timetxt3.setText(lessons.lefttime3);
                            LaunchService();
                        }
                        else if (dayofweek.toString() == "SATURDAY" || dayofweek.toString() == "SUNDAY" || dayofweek.toString() == "FRIDAY" && hour >= 19)
                        {
                            timetxt.setText("ВЫХОДНОЙ");
                        }
                        else
                        {
                            lessons.getLefttime(hour,minutes,seconds,nomondaystartlessonshours,nomondayendlessonshours,nomondaystartlessonsminutes,nomondayendlessonsminutes,nomondaynumberlessons);
                            timetxt.setText(lessons.lefttime);
                            timetxt2.setText(lessons.lefttime2);
                            timetxt3.setText(lessons.lefttime3);

                            LaunchService();
                        }
                    }
                });
            }
        },0,1000);
    }

    private void LaunchService()
    {
        SwitchCompat switchbutton = findViewById(R.id.switch1);
        Intent intent = new Intent(this, LessonService.class);
        switchbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean state) {
                if(state)
                {
                    startService(intent);
                }
                else
                {
                    stopService(intent);
                }
                SavePreferences(KEY_SWITCH_STATE, state);
            }
        });
    }

    private void SavePreferences(String key, boolean value) {
        SharedPreferences sharedPreferences = getSharedPreferences(
                APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private void LoadPreferences() {
        SwitchCompat switchbutton = findViewById(R.id.switch1);
        SharedPreferences sharedPreferences = getSharedPreferences(
                APP_PREFERENCES, MODE_PRIVATE);
        boolean savedSwitchState = sharedPreferences.getBoolean(
                KEY_SWITCH_STATE, false);
        switchbutton.setChecked(savedSwitchState);
    }
    //вынести функции связанные со свитч кнопкой в отдельный клас? (крашит весь проект)
}
