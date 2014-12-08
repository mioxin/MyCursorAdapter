package com.example.mmm.mycursoradapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MyActivity extends Activity {
    final String LOG_TAG = "myLogs";
    int DIALOG_DATE = 1;
    int DIALOG_TIME = 2;
    int myYear = 2011;
    int myMonth = 02;
    int myDay = 03;
    int myHour = 14;
    int myMinute = 35;
    ListView lvMain;
    
    View header1;
    View header2;
    View footer1;
    View footer2;
    ArrayList<Article> articles = new ArrayList<Article>();
    BoxAdapter adapter;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

// создаем адаптер
        fillData();
        adapter = new BoxAdapter(this, articles);
// настраиваем список
        lvMain = (ListView) findViewById(R.id.lvData);
        //lvMain.setAdapter(adapter);
        // создаем Header и Footer
        header1 = createHeader("First header 1");
        header2 = createHeader("Second header 2");
        footer1 = createFooter("First footer 1");
        footer2 = createFooter("Second footer 2");
        fillList();

    }


    // генерируем данные для адаптера
    void fillData() {
        Date d = new Date(System.currentTimeMillis());

        for (int i = 1; i <= 5; i++) {
            articles.add(new Article("Article " + i, "Article fdgegvr fgbgr v egewg ewgrwgewge erwgwerg wege wer ",
                    d,
                    R.drawable.ic_launcher, false));
        }
    }


    // формирование списка
    void fillList() {
        try {
            lvMain.addHeaderView(header1);
            lvMain.addHeaderView(header2, "some text header2.", false);
            lvMain.addFooterView(footer1);
            lvMain.addFooterView(footer2, "some text footer2.", false);
            lvMain.setAdapter(adapter);
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage());
        }
    }

    // нажатие кнопки
    public void onclick(View v) {
        showDialog(DIALOG_DATE);
    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_DATE) {
            DatePickerDialog tpd = new DatePickerDialog(this, myCallBack, myYear, myMonth, myDay);
            return tpd;
        }
        if (id == DIALOG_TIME) {
            TimePickerDialog tpd = new TimePickerDialog(this, myCallBack1, myHour, myMinute, true);
            return tpd;
        }
        return super.onCreateDialog(id);

    }

    DatePickerDialog.OnDateSetListener myCallBack = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear,
                              int dayOfMonth) {
            myYear = year;
            myMonth = monthOfYear;
            myDay = dayOfMonth;
            TextView tvt = (TextView)header1.findViewById(R.id.tvText);
            tvt.setText("Today is " + myDay + "/" + myMonth + "/" + myYear);
        }
    };

    // создание Header
    View createHeader(String text) {
        View v = getLayoutInflater().inflate(R.layout.header, null);
        ((TextView)v.findViewById(R.id.tvText)).setText(text);
        return v;
    }
    // создание Footer
    View createFooter(String text) {
        View v = getLayoutInflater().inflate(R.layout.footer, null);
        ((TextView)v.findViewById(R.id.tvText)).setText(text);
        return v;
    }

    public void onclick1(View view) {
        showDialog(DIALOG_TIME);
    }

    TimePickerDialog.OnTimeSetListener myCallBack1 = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHour = hourOfDay;
            myMinute = minute;
            TextView tvt = (TextView)header2.findViewById(R.id.tvText);
            tvt.setText("Time is " + myHour + " hours " + myMinute + " minutes");
        }
    };
}