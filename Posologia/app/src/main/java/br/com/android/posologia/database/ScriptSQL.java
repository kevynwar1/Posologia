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
        sqlBuilder.append("_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("Nome VARCHAR(150) NOT NULL UNIQUE, ");
        sqlBuilder.append("Dosagem VARCHAR(150) NOT NULL, ");
        sqlBuilder.append("Observacao VARCHAR(255), ");
        sqlBuilder.append("Tipo VARCHAR (1) NOT NULL");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    // Table: Posologia (DropTable)
    public static String getDropTablePessoaMedicamento() {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("DROP TABLE IF EXISTS Posologia;");

        return sqlBuilder.toString();
    }

    // Table: Posologia (CreateTable)
    public static String getCreateTablePessoaMedicamento() {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE Posologia ( ");
        sqlBuilder.append("_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("PessoaId INTEGER REFERENCES Pessoa(_id) NOT NULL, ");
        sqlBuilder.append("MedicamentoId INTEGER REFERENCES Medicamento(_id) NOT NULL, ");
        sqlBuilder.append("Horario DATETIME NOT NULL, ");
        sqlBuilder.append("Observacao VARCHAR(255), ");
        sqlBuilder.append("DataRegistro DATETIME NOT NULL DEFAULT(getDate()) ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }
}
