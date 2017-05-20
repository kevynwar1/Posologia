package br.com.android.posologia.database;

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
        sqlBuilder.append("Nome VARCHAR(150) NOT NULL UNIQUE, ");
        sqlBuilder.append("Miligrama VARCHAR(150) NOT NULL, ");
        sqlBuilder.append("Observacao VARCHAR(255), ");
        sqlBuilder.append("Tipo VARCHAR(1) NOT NULL,");
        sqlBuilder.append("Foto VARCHAR(150) ");
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
        sqlBuilder.append("FotoPosologia VARCHAR(150), ");
        sqlBuilder.append("DiasTratamento VARCHAR(3) NOT NULL, ");
        sqlBuilder.append("VezesDia VARCHAR(3) NOT NULL, ");
        sqlBuilder.append("Horario VARCHAR(1) NOT NULL, ");
        sqlBuilder.append("Dosagem VARCHAR(10) NOT NULL, ");
        sqlBuilder.append("Tempo VARCHAR(1), ");
        sqlBuilder.append("Tipo VARCHAR(1), ");
        sqlBuilder.append("MedicamentoID INTEGER, ");
        sqlBuilder.append("FOREIGN KEY (MedicamentoID) REFERENCES Medicamento (_id) ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }
}
