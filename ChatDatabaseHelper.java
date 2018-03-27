package com.example.oliver.android_labs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;

import java.security.Key;

/**
 * Created by oliver on 2/25/2018.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

    /**
     * Created by Ki Beom Song on 10/13/2016.
     */
    public class ChatDatabaseHelper extends SQLiteOpenHelper {

        protected static final String ACTIVITY_NAME = "StartActivity";
        public static String DATABASE_NAME = "Lab5 Database";
        public static int VERSION_NUM = 3;
        final static String KEY_ID = "ID";
        final static String KEY_MESSAGE = "MESSAGE";

        public ChatDatabaseHelper(Context ctx) {
            super(ctx, DATABASE_NAME, null, VERSION_NUM);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE Lab5 (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MESSAGE + " text);");
            Log.i(ACTIVITY_NAME, "Calling onCreate");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
            db.execSQL("DROP TABLE IF EXISTS Lab5");
            onCreate(db);
            Log.i(ACTIVITY_NAME, "Calling onUpgrade, oldVersion=" + oldVer + " newVersion=" + newVer);
        }


    }
