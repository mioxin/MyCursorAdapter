package com.example.mmm.mycursoradapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.HeaderViewListAdapter;
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
        Object obj;
        HeaderViewListAdapter hvlAdapter = (HeaderViewListAdapter) lvMain.getAdapter();
        obj = hvlAdapter.getItem(1);
        Log.d(LOG_TAG, "hvlAdapter.getItem(1) = " + obj.toString());
        obj = hvlAdapter.getItem(4);
        Log.d(LOG_TAG, "hvlAdapter.getItem(4) = " + obj.toString());
        ArrayAdapter<String> alAdapter = (ArrayAdapter<String>) hvlAdapter.getWrappedAdapter();
        obj = alAdapter.getItem(1);
        Log.d(LOG_TAG, "alAdapter.getItem(1) = " + obj.toString());
        obj = alAdapter.getItem(4);
        Log.d(LOG_TAG, "alAdapter.getItem(4) = " + obj.toString());
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