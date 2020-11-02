package com.example.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MYopener extends SQLiteOpenHelper {




        protected final static String DATABASE_NAME = "ContactsDB";
        protected final static int VERSION_NUM = 4;
        public final static String TABLE_NAME = "Message";
        public final static String COL_Message = "Text";
        public final static String COL_ISSEND = "t";
        public final static String COL_ID = "_id";

        public MYopener(Context ctx)
        {
            super(ctx, DATABASE_NAME, null, VERSION_NUM);
        }


        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_Message + " text,"
                    + COL_ISSEND + " boolean);");  // add or remove columns
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {   //Drop the old table:
            db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
            //Create the new table:
            onCreate(db);
        }


        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)

        {   //Drop the old table:
            db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
            //Create the new table:
            onCreate(db);
        }

    }

