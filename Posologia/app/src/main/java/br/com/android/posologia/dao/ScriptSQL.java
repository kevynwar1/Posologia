package br.com.android.posologia.dao;

/**
 * Created by Kevyn on 03/05/2017.
 */

public class ScriptSQL {


    // Table: Medicamento (DropTable)
    public static String getDropTableMedicamento() {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("DROP TABLE IF EXISTS Medicamento;");

        return sqlBuilder.toString();
    }

    // Table: Medicamento (CreateTable)
    public static String getCreateTableMedicamento() {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE Medicamento ( ");
        sqlBuilder.append("_id INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("Nome TEXT NOT NULL UNIQUE, ");
        sqlBuilder.append("Miligrama TEXT NOT NULL, ");
        sqlBuilder.append("Observacao TEXT, ");
        sqlBuilder.append("Tipo TEXT NOT NULL,");
        sqlBuilder.append("Foto TEXT ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    // Table: Posologia (DropTable)
    public static String getDropTablePosologia() {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("DROP TABLE IF EXISTS Posologia;");

        return sqlBuilder.toString();
    }

    // Table: Posologia (CreateTable)
    public static String getCreateTablePosologia() {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE Posologia ( ");
        sqlBuilder.append("_idPosologia INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("FotoPosologia TEXT, ");
        sqlBuilder.append("DiasTratamento TEXT NOT NULL, ");
        sqlBuilder.append("VezesDia TEXT NOT NULL, ");
        sqlBuilder.append("Horario TEXT NOT NULL, ");
        sqlBuilder.append("Dosagem TEXT NOT NULL, ");
        sqlBuilder.append("Tempo TEXT NOT NULL, ");
        sqlBuilder.append("Tipo TEXT NOT NULL, ");
        sqlBuilder.append("MedicamentoID INTEGER, ");
        sqlBuilder.append("FOREIGN KEY (MedicamentoID) REFERENCES Medicamento (_id) ");
        sqlBuilder.append(");");
        sqlBuilder.append("PRAGMA foreign_keys = ON; ");

        return sqlBuilder.toString();
    }
}
