package br.com.android.posologia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.android.posologia.R;
import br.com.android.posologia.adapter.ArrayAdapterPosologia;
import br.com.android.posologia.model.Medicamento;
import br.com.android.posologia.model.Posologia;

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
        values.put(Posologia.MEDICAMENTOID, posologia.getMedicamentoID().getId());


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

        //  Cursor cursor = conn.query(BPosologia.TABELA, null, null, null, null, null, null);
        Cursor cursor = conn.rawQuery("SELECT _id, Nome, _idPosologia, FotoPosologia, DiasTratamento, VezesDia, Dosagem, Horario," +
                "Tempo, Posologia.Tipo,MedicamentoID FROM Posologia INNER JOIN Medicamento  ON (_id = MedicamentoID)", null);

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();

            do {
                Posologia posologia = new Posologia();

                posologia.setIdPosologia(cursor.getInt(cursor.getColumnIndex(Posologia.IDPOSOLOGIA)));
                posologia.setFotoPosologia(cursor.getString(cursor.getColumnIndex(Posologia.FOTOPOSOLOGIA)));
                posologia.setDiasTratamento(cursor.getString(cursor.getColumnIndex(Posologia.DIASTRATAMENTO)));
                posologia.setVezesDia(cursor.getString(cursor.getColumnIndex(Posologia.VEZESDIA)));
                posologia.setDosagem(cursor.getString(cursor.getColumnIndex(Posologia.DOSAGEM)));
                posologia.setHorario(cursor.getString(cursor.getColumnIndex(Posologia.HORARIO)));
                posologia.setTempo(cursor.getString(cursor.getColumnIndex(Posologia.TEMPO)));
                posologia.setTipo(cursor.getString(cursor.getColumnIndex(Posologia.TIPO)));

                posologia.getMedicamentoID().setId(cursor.getLong(cursor.getColumnIndex(Posologia.MEDICAMENTOID)));
                posologia.getMedicamentoID().setNome(cursor.getString(cursor.getColumnIndex(Medicamento.NOME)));

                adpPosologia.add(posologia);
            } while (cursor.moveToNext());
        }

        return adpPosologia;
    }
}
