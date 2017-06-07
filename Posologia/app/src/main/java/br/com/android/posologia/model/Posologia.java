package br.com.android.posologia.model;

import java.io.Serializable;

/**
 * Created by Kevyn on 03/05/2017.
 */

public class Posologia implements Serializable {




    private long idPosologia;
    private String fotoPosologia;
    private String diasTratamento;
    private String vezesDia;
    private String horario;
    private String dosagem;
    private String tempo;
    private String tipo;



    private Integer medicamento_ID;
    private Medicamento medicamentoID;

    public Posologia() {
        medicamentoID = new Medicamento();
    }


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
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Medicamento getMedicamentoID() {
        return medicamentoID;
    }

    public void setMedicamentoID(Medicamento medicamentoID) {
        this.medicamentoID = medicamentoID;
    }

    public Integer getMedicamento_ID() {
        return medicamento_ID;
    }

    public void setMedicamento_ID(Integer medicamento_ID) {
        this.medicamento_ID = medicamento_ID;
    }

    @Override
    public String toString() {
        return this.diasTratamento + " " + this.vezesDia + " " + this.horario + " " + this.medicamentoID;
    }
}
