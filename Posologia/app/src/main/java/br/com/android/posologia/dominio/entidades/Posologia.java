package br.com.android.posologia.dominio.entidades;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kevyn on 03/05/2017.
 */

public class Posologia implements Serializable {


    public static String TABELA = "Posologia";

    public static String IDPOSOLOGIA = "_idPosologia";
    public static String FOTOPOSOLOGIA = "FotoPosologia";
    public static String DIASTRATAMENTO = "DiasTratamento";
    public static String VEZESDIA = "VezesDia";
    public static String HORARIO = "Horario";
    public static String DOSAGEM = "Dosagem";
    public static String TEMPO = "Tempo";
    public static String TIPO = "Tipo";


    private long idPosologia;
    private String fotoPosologia;
    private String diasTratamento;
    private String vezesDia;
    private String horario;
    private String dosagem;
    private String tempo;
    private String Tipo;


    public long getIdPosologia() {
        return idPosologia;
    }

    public void setIdPosologia(long idPosologia) {
        this.idPosologia = idPosologia;
    }

    public String getFotoPosologia() {
        return fotoPosologia;
    }

    public void setFotoPosologia(String fotoPosologia) {
        this.fotoPosologia = fotoPosologia;
    }

    public String getDiasTratamento() {
        return diasTratamento;
    }

    public void setDiasTratamento(String diasTratamento) {
        this.diasTratamento = diasTratamento;
    }

    public String getVezesDia() {
        return vezesDia;
    }

    public void setVezesDia(String vezesDia) {
        this.vezesDia = vezesDia;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    @Override
    public String toString() {
        return this.diasTratamento + " " + this.vezesDia + " " + this.horario;
    }
}
