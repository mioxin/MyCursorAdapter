package com.example.mmm.mycursoradapter;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

public class MyActivity extends Activity {
    final String LOG_TAG = "myLogs";
    final int DIALOG_DATE = 4;
    final int DIALOG_TIME = 5;
    int myYear = 2011;
    int myMonth = 02;
    int myDay = 03;
    int myHour = 14;
    int myMinute = 35;
    ListView lvData;
    
    View header1;
    View header2;
    View footer1;
    View footer2;
    ArrayList<Article> articles = new ArrayList<Article>();
    BoxAdapter adapter;
    final int DIALOG_ITEMS = 1;
    final int DIALOG_ADAPTER = 2;
    final int DIALOG_CURSOR = 3;
    int cnt = 0;
    com.example.mmm.mycursoradapter.db db;
    Cursor cursor;

    String data[] = { "one", "two", "three", "four" };
    boolean chkd[] = { false, true, true, false };

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

// создаем адаптер
        fillData();
        adapter = new BoxAdapter(this, articles);
// настраиваем список
        lvData = (ListView) findViewById(R.id.lvData);
        //lvData.setAdapter(adapter);
        // создаем Header и Footer
//        header1 = createHeader("First header 1");
//        header2 = createHeader("Second header 2");
//        footer1 = createFooter("First footer 1");
//        footer2 = createFooter("Second footer 2");
        fillList();
        // открываем подключение к БД
        db = new com.example.mmm.mycursoradapter.db(this);
        db.open();
        cursor = db.getAllData();
        startManagingCursor(cursor);
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
//            lvData.addHeaderView(header1);
//            lvData.addHeaderView(header2, "some text header2.", false);
//            lvData.addFooterView(footer1);
//            lvData.addFooterView(footer2, "some text footer2.", false);
            lvData.setAdapter(adapter);
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage());
        }
    }

//    нажатие кнопки
    public void onclick(View v) {
        changeCount();
        switch (v.getId()) {
            case R.id.btnItems:
                showDialog(DIALOG_ITEMS);
                break;
            case R.id.btnAdapter:
                showDialog(DIALOG_ADAPTER);
                break;
            case R.id.btnCursor:
                showDialog(DIALOG_CURSOR);
                break;
            default:
                break;
        }
    }

    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        switch (id) {
// массив
            case DIALOG_ITEMS:
                adb.setTitle(R.string.items);
                adb.setMultiChoiceItems(data, chkd, myItemsMultiClickListener);
                //adb.setItems(data, myClickListener);
                break;
// адаптер
            case DIALOG_ADAPTER:
                adb.setTitle(R.string.adapter);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_selectable_list_item, data);
                adb.setSingleChoiceItems(adapter, -1, myClickListener);
                //adb.setAdapter(adapter, myClickListener);
                break;
// курсор
            case DIALOG_CURSOR:
                adb.setTitle(R.string.cursor);
                adb.setMultiChoiceItems(cursor, com.example.mmm.mycursoradapter.db.COLUMN_CHK, com.example.mmm.mycursoradapter.db.COLUMN_TXT, myCursorMultiClickListener);
                //adb.setCursor(cursor, myClickListener, DB.COLUMN_TXT);
                break;

            case DIALOG_DATE:
                DatePickerDialog dpd = new DatePickerDialog(this, myCallBack, myYear, myMonth, myDay);
                return (Dialog) dpd;

            case DIALOG_TIME:
                TimePickerDialog tpd = new TimePickerDialog(this, myCallBack1, myHour, myMinute, true);
                Log.d(LOG_TAG, "onCreate TimeDialog ");
                return (Dialog) tpd;

            default:
                return super.onCreateDialog(id);
        }
        adb.setPositiveButton("<OK>", myClickListener);
    return adb.create();
    }

    protected void onPrepareDialog(int id, Dialog dialog) {
// получаем доступ к адаптеру списка диалога
        AlertDialog aDialog = (AlertDialog) dialog;
        ListAdapter lAdapter = aDialog.getListView().getAdapter();
        Log.d(LOG_TAG, "onPrepare Dialog ");
        switch (id) {
            case DIALOG_ITEMS:
            case DIALOG_ADAPTER:
// проверка возможности преобразования
                if (lAdapter instanceof BaseAdapter) {
// преобразование и вызов метода-уведомления о новых данных
                    BaseAdapter bAdapter = (BaseAdapter) lAdapter;
                    bAdapter.notifyDataSetChanged();
                }
                break;
            case DIALOG_CURSOR:
                break;
            default:
                break;
        }
    };

    // обработчик нажатия на пункт списка диалога
    DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            // выводим в лог позицию нажатого элемента
            ListView lv = ((AlertDialog) dialog).getListView();
            ListAdapter lAdapter = lv.getAdapter();
            TextView tvt = (TextView)header2.findViewById(R.id.tvText);

            if (which == Dialog.BUTTON_POSITIVE)
                // выводим в лог позицию выбранного элемента
                Log.d(LOG_TAG, "pos = " + lv.getCheckedItemPosition());
            else {
                // выводим в лог позицию нажатого элемента
                Log.d(LOG_TAG, "which = " + which);
                tvt.setText("Нажали пункт №" + which + " " + lAdapter.getItem(which).toString());
            }
        }
    };
    // обработчик для списка массива
    DialogInterface.OnMultiChoiceClickListener myItemsMultiClickListener = new DialogInterface.OnMultiChoiceClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
            Log.d(LOG_TAG, "which = " + which + ", isChecked = " + isChecked);
        }
    };
    // обработчик для списка курсора
    DialogInterface.OnMultiChoiceClickListener myCursorMultiClickListener = new DialogInterface.OnMultiChoiceClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
            ListView lv = ((AlertDialog) dialog).getListView();
            Log.d(LOG_TAG, "which = " + which + ", isChecked = " + isChecked);
            db.changeRec(which, isChecked);
            cursor.requery();
        }
    };
    // меняем значение счетчика
    void changeCount() {
        cnt++;
// обновляем массив
        data[3] = String.valueOf(cnt);
// обновляем БД
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
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

    public void onclick1(View view) {
        Log.d(LOG_TAG, "onclick1 ");
        showDialog(DIALOG_TIME);
    }

    TimePickerDialog.OnTimeSetListener myCallBack1 = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHour = hourOfDay;
            myMinute = minute;
            Log.d(LOG_TAG, "onTimeSet - ");
            TextView tvt = (TextView)header2.findViewById(R.id.tvText);
            tvt.setText("Time is " + myHour + " hours " + myMinute + " minutes");
            Log.d(LOG_TAG, "onTimeSet --- ");
        }
    };

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


}