package br.com.android.posologia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

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

        values.put(PosologiaTable.FOTOPOSOLOGIA, posologia.getFotoPosologia());
        values.put(PosologiaTable.DIASTRATAMENTO, posologia.getDiasTratamento());
        values.put(PosologiaTable.VEZESDIA, posologia.getVezesDia());
        values.put(PosologiaTable.HORARIO, posologia.getHorario());
        values.put(PosologiaTable.DOSAGEM, posologia.getDosagem());
        values.put(PosologiaTable.TEMPO, posologia.getTempo());
        values.put(PosologiaTable.TIPO, posologia.getTipo());

        values.put(PosologiaTable.MEDICAMENTOID, posologia.getMedicamentoID().getId());


        return values;
    }

    public void inserirPosologia(Posologia posologia) {
        conn = dataBase.getWritableDatabase();
        ContentValues values = preencheContentValues(posologia);
        conn.insertOrThrow(PosologiaTable.TABELA, null, values);

    }

    public void alterarPosologia(Posologia posologia) {
        conn = dataBase.getWritableDatabase();
        ContentValues values = preencheContentValues(posologia);
        conn.update(PosologiaTable.TABELA, values, "_idPosologia = ?", new String[]{String.valueOf(posologia.getIdPosologia())});
    }

    public void excluirPosologia(long id) {
        conn = dataBase.getWritableDatabase();
        conn.delete(PosologiaTable.TABELA, "_idPosologia = ?", new String[]{String.valueOf(id)});
    }

    public ArrayAdapterPosologia listarPosologias(Context context) {
        conn = dataBase.getReadableDatabase();
        ArrayAdapterPosologia adpPosologia = new ArrayAdapterPosologia(context, R.layout.item_posologia);

        //Cursor cursor = conn.query(PosologiaTable.TABELA, null, null, null, null, null, null);
          Cursor cursor = conn.rawQuery("SELECT _idPosologia, MedicamentoID, Nome, FotoPosologia, DiasTratamento, VezesDia, Dosagem, Horario," +
          "Tempo, Posologia.Tipo FROM Posologia INNER JOIN Medicamento ON (MedicamentoID = _id)", null);


        if (cursor.getCount() > 0) {

            cursor.moveToFirst();

            do {
                Posologia posologia = new Posologia();

                posologia.setIdPosologia(cursor.getInt(cursor.getColumnIndex(PosologiaTable.IDPOSOLOGIA)));
                posologia.getMedicamentoID().setId(cursor.getLong(cursor.getColumnIndex(PosologiaTable.MEDICAMENTOID)));
                posologia.getMedicamentoID().setNome(cursor.getString(cursor.getColumnIndex(MedicamentoTable.NOME)));
                posologia.setFotoPosologia(cursor.getString(cursor.getColumnIndex(PosologiaTable.FOTOPOSOLOGIA)));
                posologia.setDiasTratamento(cursor.getString(cursor.getColumnIndex(PosologiaTable.DIASTRATAMENTO)));
                posologia.setVezesDia(cursor.getString(cursor.getColumnIndex(PosologiaTable.VEZESDIA)));
                posologia.setDosagem(cursor.getString(cursor.getColumnIndex(PosologiaTable.DOSAGEM)));
                posologia.setHorario(cursor.getString(cursor.getColumnIndex(PosologiaTable.HORARIO)));
                posologia.setTempo(cursor.getString(cursor.getColumnIndex(PosologiaTable.TEMPO)));
                posologia.setTipo(cursor.getString(cursor.getColumnIndex(PosologiaTable.TIPO)));

                adpPosologia.add(posologia);
            } while (cursor.moveToNext());
        }

        return adpPosologia;
    }

    public ArrayList<Posologia> listaArrayPosologia() {
        conn = dataBase.getReadableDatabase();
        ArrayList<Posologia> lista = new ArrayList<>();

        // Cursor cursor = conn.query(BPosologia.TABELA, null, null, null, null, null, null);
        Cursor cursor = conn.rawQuery("SELECT Horario, MedicamentoID FROM Posologia", null);

        if (cursor != null & cursor.getCount() > 0) {

            cursor.moveToFirst();

            do {
                Posologia posologia = new Posologia();
                posologia.setHorario(cursor.getString(cursor.getColumnIndex(PosologiaTable.HORARIO)));
                posologia.setMedicamento_ID(cursor.getInt(cursor.getColumnIndex(PosologiaTable.MEDICAMENTOID)));
                lista.add(posologia);
            } while (cursor.moveToNext());
        }

        return lista;

    }
}
