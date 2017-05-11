package br.com.android.posologia.dominio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.android.posologia.R;
import br.com.android.posologia.dominio.entidades.Medicamento;
import br.com.android.posologia.adapter.ArrayAdapterMedicamento;
import br.com.android.posologia.dominio.entidades.Medicamento;

/**
 * Created by Kevyn on 03/05/2017.
 */

public class RepMedicamento {

    private SQLiteDatabase conn;

    public RepMedicamento(SQLiteDatabase conn) {
        this.conn = conn;
    }

    private ContentValues preencheContentValues(Medicamento medicamento) {
        ContentValues values = new ContentValues();

        values.put(Medicamento.NOME, medicamento.getNome());
        values.put(Medicamento.DOSAGEM, medicamento.getDosagem());
        values.put(Medicamento.OBSERVACAO, medicamento.getObservacao());
        values.put(Medicamento.TIPO, medicamento.getTipo());

        return values;
    }

    public void inserirMedicamento(Medicamento medicamento) {
        ContentValues values = preencheContentValues(medicamento);
        conn.insertOrThrow(Medicamento.TABELA, null, values);
    }

    public void alterarMedicamento(Medicamento medicamento) {
        ContentValues values = preencheContentValues(medicamento);
        conn.update(Medicamento.TABELA, values, "_id = ?", new String[]{String.valueOf(medicamento.getId())});
    }

    public void excluirMedicamento(long id) {
        conn.delete(Medicamento.TABELA, "_id = ?", new String[]{String.valueOf(id)});
    }

    public ArrayAdapterMedicamento listarMedicamentos(Context context) {
        ArrayAdapterMedicamento adpMedicamentos = new ArrayAdapterMedicamento(context, R.layout.item_medicamento);

        Cursor cursor = conn.query(Medicamento.TABELA, null, null, null, null, null, null);

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();

            do {
                Medicamento medicamento = new Medicamento();

                medicamento.setId(cursor.getLong(cursor.getColumnIndex(Medicamento.ID)));
                medicamento.setNome(cursor.getString(cursor.getColumnIndex(Medicamento.NOME)));
                medicamento.setDosagem(cursor.getString(cursor.getColumnIndex(Medicamento.DOSAGEM)));
                medicamento.setObservacao(cursor.getString(cursor.getColumnIndex(Medicamento.OBSERVACAO)));
                medicamento.setTipo(cursor.getString(cursor.getColumnIndex(Medicamento.TIPO)));

                adpMedicamentos.add(medicamento);
            } while (cursor.moveToNext());
        }

        return adpMedicamentos;
    }

}
