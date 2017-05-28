package br.com.android.posologia.dao;

import android.provider.BaseColumns;

/**
 * Created by kevyn on 27/05/2017.
 */

public interface MedicamentoTable extends BaseColumns {
    public static String TABELA = "Medicamento";

    public static String ID = "_id";
    public static String NOME = "Nome";
    public static String MILIGRAMA = "Miligrama";
    public static String OBSERVACAO = "Observacao";
    public static String TIPO = "Tipo";
    public static String FOTO = "Foto";
}
