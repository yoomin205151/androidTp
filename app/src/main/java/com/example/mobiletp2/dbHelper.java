package com.example.mobiletp2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbHelper extends SQLiteOpenHelper {
    public dbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuario (\n" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "nombre TEXT, \n" +
                "apellido TEXT, \n" +
                "dni TEXT, \n" +
                "email TEXT, \n" +
                "nombre_usuario TEXT, \n" +
                "contrasenia TEXT \n" +
                ")");

        db.execSQL("CREATE TABLE empleado (\n" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "nombre TEXT, \n" +
                "apellido TEXT, \n" +
                "dni TEXT, \n" +
                "email TEXT, \n" +
                "direccion TEXT, \n" +
                "codigo_postal TEXT \n" +
                ")");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
