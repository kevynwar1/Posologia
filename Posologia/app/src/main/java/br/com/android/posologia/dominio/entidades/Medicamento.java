package br.com.android.posologia.dominio.entidades;

import java.io.Serializable;

/**
 * Created by Kevyn on 03/05/2017.
 */

public class Medicamento implements Serializable {

    public static String TABELA = "Medicamento";

    public static String ID = "_id";
    public static String NOME = "Nome";
    public static String DOSAGEM = "Dosagem";
    public static String OBSERVACAO = "Observacao";
    public static String TIPO = "Tipo";

    private long Id;
    private String Nome;
    private String Dosagem;
    private String Observacao;
    private String Tipo;

    public Medicamento() {

    }

    public String getDosagem() {
        return Dosagem;
    }

    public void setDosagem(String dosagem) {
        Dosagem = dosagem;
    }

    public String getObservacao() {
        return Observacao;
    }

    public void setObservacao(String observacao) {
        Observacao = observacao;
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

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }


    /*@Override
    public String toString() {
        return this.Nome + " " + this.Dosagem ;
    }*/
}
