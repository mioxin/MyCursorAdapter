package com.example.mmm.mycursoradapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends Activity {
    final String LOG_TAG = "myLogs";
    String[] data = {"one", "two", "three", "four", "five"};
    ListView lvMain;
    
    View header1;
    View header2;
    View footer1;
    View footer2
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
        lvMain.setAdapter(adapter);
        // создаем Header и Footer
        header1 = createHeader("header 1");
        header2 = createHeader("header 2");
        footer1 = createFooter("footer 1");
        footer2 = createFooter("footer 2");
        fillList();
    }


    // генерируем данные для адаптера
    void fillData() {
        Date d = new Date(System.currentTimeMillis());

        for (int i = 1; i <= 20; i++) {
            articles.add(new Article("Article " + i, "Article fdgegvr fgbgr v egewg ewgrwgewge erwgwerg wege wer ",
                    d,
                    R.drawable.ic_launcher, false));
        }
    }


    // формирование списка
    void fillList() {
        try {
            lvMain.setAdapter(adapter);
            lvMain.addHeaderView(header1);
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage());
        }
    }

    // нажатие кнопки
    public void onclick(View v) {
    }

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
}