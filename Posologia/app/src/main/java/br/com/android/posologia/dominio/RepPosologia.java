package br.com.android.posologia.dominio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.android.posologia.R;
import br.com.android.posologia.adapter.ArrayAdapterPosologia;
import br.com.android.posologia.database.DataBase;
import br.com.android.posologia.dominio.entidades.Posologia;

/**
 * Created by Kevyn on 03/05/2017.
 */

public class RepPosologia {
    private SQLiteDatabase conn;
    private DataBase dataBase;

    public RepPosologia(Context ctx) {
        dataBase = new DataBase(ctx);
    }

    private ContentValues preencheContentValues(Posologia posologia) {
        ContentValues values = new ContentValues();

        values.put(Posologia.FOTOPOSOLOGIA, posologia.getFotoPosologia());
        values.put(Posologia.DIASTRATAMENTO, posologia.getDiasTratamento());
        values.put(Posologia.VEZESDIA, posologia.getVezesDia());
        values.put(Posologia.HORARIO, posologia.getHorario());
        values.put(Posologia.DOSAGEM, posologia.getDosagem());
        values.put(Posologia.TEMPO, posologia.getTempo());
        values.put(Posologia.TIPO, posologia.getTipo());


        return values;
    }

    public void inserirPosologia(Posologia posologia) {
        conn = dataBase.getWritableDatabase();
        ContentValues values = preencheContentValues(posologia);
        conn.insertOrThrow(Posologia.TABELA, null, values);

    }

    public void alterarPosologia(Posologia posologia) {
        conn = dataBase.getWritableDatabase();
        ContentValues values = preencheContentValues(posologia);
        conn.update(Posologia.TABELA, values, "_idPosologia = ?", new String[]{String.valueOf(posologia.getIdPosologia())});
    }

    public void excluirPosologia(long id) {
        conn = dataBase.getWritableDatabase();
        conn.delete(Posologia.TABELA, "_idPosologia = ?", new String[]{String.valueOf(id)});
    }

    public ArrayAdapterPosologia listarPosologias(Context context) {
        conn = dataBase.getReadableDatabase();
        ArrayAdapterPosologia adpPosologia = new ArrayAdapterPosologia(context, R.layout.item_posologia);

        Cursor cursor = conn.query(Posologia.TABELA, null, null, null, null, null, null);

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();

            do {
                Posologia posologia = new Posologia();

                posologia.setIdPosologia(cursor.getLong(cursor.getColumnIndex(Posologia.IDPOSOLOGIA)));
                posologia.setFotoPosologia(cursor.getString(cursor.getColumnIndex(Posologia.FOTOPOSOLOGIA)));
                posologia.setDiasTratamento(cursor.getString(cursor.getColumnIndex(Posologia.DIASTRATAMENTO)));
                posologia.setVezesDia(cursor.getString(cursor.getColumnIndex(Posologia.VEZESDIA)));
                posologia.setDosagem(cursor.getString(cursor.getColumnIndex(Posologia.DOSAGEM)));
                posologia.setHorario(cursor.getString(cursor.getColumnIndex(Posologia.HORARIO)));
                posologia.setTempo(cursor.getString(cursor.getColumnIndex(Posologia.TEMPO)));
                posologia.setTipo(cursor.getString(cursor.getColumnIndex(Posologia.TIPO)));

                adpPosologia.add(posologia);
            } while (cursor.moveToNext());
        }

        return adpPosologia;
    }


}
