package br.com.android.posologia.model;

import java.io.Serializable;

/**
 * Created by Kevyn on 03/05/2017.
 */

public class Medicamento implements Serializable {

    public static String TABELA = "Medicamento";

    public static String ID = "_id";
    public static String NOME = "Nome";
    public static String MILIGRAMA = "Miligrama";
    public static String OBSERVACAO = "Observacao";
    public static String TIPO = "Tipo";
    public static String FOTO = "Foto";

    private long id;
    private String nome;
    private String miligrama;
    private String observacao;
    private String tipo;
    private String foto;

    public Medicamento() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMiligrama() {
        return miligrama;
    }

    public void setMiligrama(String miligrama) {
        this.miligrama = miligrama;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }




    @Override
    public String toString() {
        return this.nome + " " + this.miligrama;
    }
}
