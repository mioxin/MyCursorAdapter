package com.example.mmm.mycursoradapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
public class MyActivity extends Activity {
    ArrayList<Article> articles = new ArrayList<Article>();
    BoxAdapter boxAdapter;
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
// создаем адаптер
        fillData();
        boxAdapter = new BoxAdapter(this, articles);
// настраиваем список
        ListView lvMain = (ListView) findViewById(R.id.lvData);
        lvMain.setAdapter(boxAdapter);
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


    // выводим информацию о корзине
    public void showResult(View v) {
        String result = "Товары в корзине:";
        for (Article p : boxAdapter.getBox()) {
            if (p.box)
                result += "\n" + p.title;
        }
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }
}