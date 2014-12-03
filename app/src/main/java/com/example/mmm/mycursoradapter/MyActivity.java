package com.example.mmm.mycursoradapter;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MyActivity extends Activity {

        private static final int CM_DELETE_ID = 1;
        ListView lvData;
        DB db;
        SimpleCursorAdapter scAdapter;
        Cursor cursor;
        /** Called when the activity is first created. */
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_my);
// открываем подключение к БД
            db = new DB(this);
            db.open();
// получаем курсор
            cursor = db.getAllData();
            startManagingCursor(cursor);
// формируем столбцы сопоставления
            String[] from = new String[] { DB.COLUMN_IMG, DB.COLUMN_TXT, DB.COLUMN_TXT1 };
            int[] to = new int[] { R.id.ivImg, R.id.tvText, R.id.tvSmall };
// создааем адаптер и настраиваем список
            scAdapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to,2);
            lvData = (ListView) findViewById(R.id.lvData);
            lvData.setAdapter(scAdapter);
// добавляем контекстное меню к списку
            registerForContextMenu(lvData);
        }
        // обработка нажатия кнопки
        public void onButtonClick(View view) {
// добавляем запись
            db.addRec("Sometitle " + (cursor.getCount() + 1),"Some small text. ohjeuhuwe weufh h " + (cursor.getCount() + 1), R.drawable.ic_launcher);
// обновляем курсор
            cursor.requery();
        }
        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu, v, menuInfo);
            menu.add(0, CM_DELETE_ID, 0, R.string.delete_record);
        }
        public boolean onContextItemSelected(MenuItem item) {
            if (item.getItemId() == CM_DELETE_ID) {
// получаем из пункта контекстного меню данные по пункту списка
                AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
// извлекаем id записи и удаляем соответствующую запись в БД
                db.delRec(acmi.id);
// обновляем курсор
                cursor.requery();
                return true;
            }
            return super.onContextItemSelected(item);
        }
        protected void onDestroy() {
            super.onDestroy();
// закрываем подключение при выходе
            db.close();
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
