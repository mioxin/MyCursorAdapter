package com.example.mmm.mycursoradapter;

/**
 * Created by palchuk on 28.11.2014.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DB {
    final String LOG_TAG = "myLogs";
    private static final String DB_NAME = "mydb";
    private static final int DB_VERSION = 2;
    private static final String DB_TABLE = "mytab";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_IMG = "img";
    public static final String COLUMN_TXT = "txt";
    public static final String COLUMN_TXT1 = "txt1";
    private static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_IMG + " integer, " +
                    COLUMN_TXT + " text, " +
                    COLUMN_TXT1 + " text" +
                    ");";
    private final Context mCtx;
    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;
    public DB(Context ctx) {
        mCtx = ctx;
    }
    // открыть подключение
    public void open() {
        Log.d(LOG_TAG, "open");
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }
    // закрыть подключение
    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }
    // получить все данные из таблицы DB_TABLE
    public Cursor getAllData() {
        return mDB.query(DB_TABLE, null, null, null, null, null, null);
    }

    // добавить запись в DB_TABLE
    public void addRec(String txt, String txt1, int img) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TXT, txt);
        cv.put(COLUMN_TXT1, txt1);
        cv.put(COLUMN_IMG, img);
        mDB.insert(DB_TABLE, null, cv);
    }
    // удалить запись из DB_TABLE
    public void delRec(long id) {
        mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
    }
    // класс по созданию и управлению БД
    private class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context, String name, CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }
        // создаем и заполняем БД
        @Override
        public void onCreate(SQLiteDatabase db1) {
            //super.onCreate(db1);
            Log.d(LOG_TAG, "onCreate DB");
            db1.execSQL(DB_CREATE);
            ContentValues cv = new ContentValues();
            for (int i = 1; i < 5; i++) {
                cv.put(COLUMN_TXT, "Sometitle " + i);
                cv.put(COLUMN_TXT1, "Some small text. Aiuubnjk kk " + i);
                cv.put(COLUMN_IMG, R.drawable.ic_launcher);
                db1.insert(DB_TABLE, null, cv);
                Log.d(LOG_TAG, COLUMN_TXT + " " + COLUMN_TXT1 + " " + i);
            }
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
