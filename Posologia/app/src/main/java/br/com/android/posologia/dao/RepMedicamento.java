package br.com.android.posologia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.android.posologia.R;
import br.com.android.posologia.model.Medicamento;
import br.com.android.posologia.adapter.ArrayAdapterMedicamento;


/**
 * Created by Kevyn on 03/05/2017.
 */

public class RepMedicamento {

    private SQLiteDatabase conn;
    private DataBase dataBase;

    public RepMedicamento(Context ctx) {
        dataBase = new DataBase(ctx);
    }

    public RepMedicamento() {
    }

    private ContentValues preencheContentValues(Medicamento medicamento) {
        ContentValues values = new ContentValues();

        values.put(MedicamentoTable.NOME, medicamento.getNome());
        values.put(MedicamentoTable.MILIGRAMA, medicamento.getMiligrama());
        values.put(MedicamentoTable.OBSERVACAO, medicamento.getObservacao());
        values.put(MedicamentoTable.TIPO, medicamento.getTipo());
        values.put(MedicamentoTable.FOTO, medicamento.getFoto());

        return values;
    }

    public void inserirMedicamento(Medicamento medicamento) {
        conn = dataBase.getWritableDatabase();
        ContentValues values = preencheContentValues(medicamento);
        conn.insertOrThrow(MedicamentoTable.TABELA, null, values);
    }

    public void alterarMedicamento(Medicamento medicamento) {
        conn = dataBase.getWritableDatabase();
        ContentValues values = preencheContentValues(medicamento);
        conn.update(MedicamentoTable.TABELA, values, "_id = ?", new String[]{String.valueOf(medicamento.getId())});
    }

    public void excluirMedicamento(long id) {
        conn = dataBase.getWritableDatabase();
        conn.delete(MedicamentoTable.TABELA, "_id = ?", new String[]{String.valueOf(id)});
    }

    public ArrayAdapterMedicamento listarMedicamentos(Context context) {
        conn = dataBase.getReadableDatabase();
        ArrayAdapterMedicamento adpMedicamentos = new ArrayAdapterMedicamento(context, R.layout.item_medicamento);

        Cursor cursor = conn.query(MedicamentoTable.TABELA, null, null, null, null, null, null);

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();

            do {
                Medicamento medicamento = new Medicamento();

                medicamento.setId(cursor.getLong(cursor.getColumnIndex(MedicamentoTable.ID)));
                medicamento.setNome(cursor.getString(cursor.getColumnIndex(MedicamentoTable.NOME)));
                medicamento.setMiligrama(cursor.getString(cursor.getColumnIndex(MedicamentoTable.MILIGRAMA)));
                medicamento.setObservacao(cursor.getString(cursor.getColumnIndex(MedicamentoTable.OBSERVACAO)));
                medicamento.setTipo(cursor.getString(cursor.getColumnIndex(MedicamentoTable.TIPO)));
                medicamento.setFoto(cursor.getString(cursor.getColumnIndex(MedicamentoTable.FOTO)));

                adpMedicamentos.add(medicamento);
            } while (cursor.moveToNext());
        }

        return adpMedicamentos;
    }

    public ArrayList<Medicamento> listaNomeMedicamento() {
        conn = dataBase.getReadableDatabase();
        ArrayList<Medicamento> lista = new ArrayList<>();

        // Cursor cursor = conn.query(BPosologia.TABELA, null, null, null, null, null, null);
        Cursor cursor = conn.rawQuery("SELECT _id, Nome FROM Medicamento ORDER BY _id", null);

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();

            do {
                Medicamento medicamento = new Medicamento();
                medicamento.setId(cursor.getLong(cursor.getColumnIndex(MedicamentoTable.ID)));
                medicamento.setNome(cursor.getString(cursor.getColumnIndex(MedicamentoTable.NOME)));
                lista.add(medicamento);
            } while (cursor.moveToNext());
        }

        return lista;

    }


}
