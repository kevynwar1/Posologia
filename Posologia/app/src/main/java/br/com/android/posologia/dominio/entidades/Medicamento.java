package br.com.android.posologia.dominio.entidades;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kevyn on 03/05/2017.
 */

public class Medicamento implements Serializable {

    public static String TABELA = "Medicamento";

    public static String ID = "_id";
    public static String NOME = "Nome";
    public static String DESCRICAO = "Descricao";
    public static String DATAREGISTRO = "DataRegistro";

    private long Id;
    private String Nome;
    private String Descricao;
    private Date DataRegistro;

    public Medicamento() {
        this.DataRegistro = new Date();
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public Date getDataRegistro() {
        return DataRegistro;
    }

    public void setDataRegistro(Date dataRegistro) {
        DataRegistro = dataRegistro;
    }

    @Override
    public String toString() {
        return this.Nome + " " + this.Descricao;
    }
}
