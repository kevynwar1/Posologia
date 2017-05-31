package br.com.android.posologia.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kevyn on 03/05/2017.
 */

public class DataBase extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "Posologia";
    private static final int VERSAO = 1;


    public DataBase(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptSQL.getCreateTableMedicamento());
        db.execSQL(ScriptSQL.getCreateTablePosologia());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ScriptSQL.getDropTableMedicamento());
        db.execSQL(ScriptSQL.getDropTablePosologia());

        this.onCreate(db);
    }
}
