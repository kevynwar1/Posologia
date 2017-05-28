package br.com.android.posologia.dao;

import android.provider.BaseColumns;

/**
 * Created by kevyn on 27/05/2017.
 */

public interface PosologiaTable extends BaseColumns {

    public static String TABELA = "Posologia";

    public static String IDPOSOLOGIA = "_idPosologia";
    public static String FOTOPOSOLOGIA = "FotoPosologia";
    public static String DIASTRATAMENTO = "DiasTratamento";
    public static String VEZESDIA = "VezesDia";
    public static String HORARIO = "Horario";
    public static String DOSAGEM = "Dosagem";
    public static String TEMPO = "Tempo";
    public static String TIPO = "Tipo";
    public static String MEDICAMENTOID = "MedicamentoID";

}
