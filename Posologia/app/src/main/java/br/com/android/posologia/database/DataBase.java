package br.com.android.posologia.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kevyn on 03/05/2017.
 */

public class DataBase extends SQLiteOpenHelper {

    public DataBase(Context context) {
        super(context, "Posologia", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptSQL.getCreateTableMedicamento());
        db.execSQL(ScriptSQL.getCreateTablePosologia());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
