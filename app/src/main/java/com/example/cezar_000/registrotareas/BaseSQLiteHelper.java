package com.example.cezar_000.registrotareas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cezar_000 on 10/03/2018.
 */
public class BaseSQLiteHelper extends SQLiteOpenHelper {

    String Tabla = "CREATE TABLE TAREAS(ID INTEGER PRIMARY KEY AUTOINCREMENT, TITULO TEXT, DESCRIPCION TEXT," +
            " FECHA TEXT, HORA TEXT)";

    public BaseSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Tabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE TAREAS");
        db.execSQL(Tabla);

    }
}