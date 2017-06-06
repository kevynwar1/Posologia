package br.com.android.posologia.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.android.posologia.R;
import br.com.android.posologia.dao.RepMedicamento;
import br.com.android.posologia.model.Medicamento;
import br.com.android.posologia.model.Posologia;
import br.com.android.posologia.view.PosologiaNewActivity;

/**
 * Created by kevyn on 26/05/2017.
 */

public class PosologiaHelper {
    private ImageView ivPosologia;

    private EditText edtDiasMedicamento;
    private EditText edtVezesDia;
    private EditText edtDosagem;

    private Spinner spHora;
    private Spinner spTempo;
    private Spinner spTipo;
    private Spinner spNomeMedicamento;

    private Posologia posologia;
    ArrayList<Medicamento> mede;

    ArrayAdapter<String> adapterHorario;
    ArrayAdapter<String> adapterTempo;
    ArrayAdapter<String> adapterTipo;
    ArrayAdapter adapterNome;


    public PosologiaHelper(PosologiaNewActivity posologiaNew) {
        ivPosologia = (ImageView) posologiaNew.findViewById(R.id.ivPosologia);

        edtDiasMedicamento = (EditText) posologiaNew.findViewById(R.id.edtDiasMedicamento);
        edtVezesDia = (EditText) posologiaNew.findViewById(R.id.edtVezesDia);
        edtDosagem = (EditText) posologiaNew.findViewById(R.id.edtDosagem);

        spHora = (Spinner) posologiaNew.findViewById(R.id.spHora);
        spTempo = (Spinner) posologiaNew.findViewById(R.id.spTempo);
        spTipo = (Spinner) posologiaNew.findViewById(R.id.spDosagem);
        spNomeMedicamento = (Spinner) posologiaNew.findViewById(R.id.spNomeMedicamento);
        posologia = new Posologia();
        RepMedicamento rep = new RepMedicamento(posologiaNew);
        mede = rep.listaNomeMedicamento();


    }


    public void spinnerHorario(PosologiaNewActivity posologiaNew) {
        adapterHorario = new ArrayAdapter<String>(posologiaNew, android.R.layout.simple_spinner_dropdown_item);
        spHora.setAdapter(adapterHorario);
        adapterHorario.add("02 em 02 Horas");
        adapterHorario.add("04 em 04 Horas");
        adapterHorario.add("06 em 06 Horas");
        adapterHorario.add("08 em 08 Horas");
        adapterHorario.add("12 em 12 Horas");
    }

    public void spinnerTempo(PosologiaNewActivity posologiaNew) {
        adapterTempo = new ArrayAdapter<String>(posologiaNew, android.R.layout.simple_spinner_dropdown_item);
        spTempo.setAdapter(adapterTempo);
        adapterTempo.add("Dias");
        adapterTempo.add("Meses");
        adapterTempo.add("Anos");
    }

    public void spinnerDosagem(PosologiaNewActivity posologiaNew) {
        adapterTipo = new ArrayAdapter<String>(posologiaNew, android.R.layout.simple_spinner_dropdown_item);
        spTipo.setAdapter(adapterTipo);
        adapterTipo.add("Gotas");
        adapterTipo.add("Comprimidos");
        adapterTipo.add("ML");
    }

    public void spinnerNomeMedicamento(PosologiaNewActivity posologiaNew, ArrayList<String> list) {

        adapterNome = new ArrayAdapter(posologiaNew, android.R.layout.simple_spinner_dropdown_item, list);
        spNomeMedicamento.setAdapter(adapterNome);

    }

    public void preencheForm(Posologia posologiaalter) {
        posologia = posologiaalter;

        edtDiasMedicamento.setText(posologia.getDiasTratamento());
        edtVezesDia.setText(posologia.getVezesDia());
        edtDosagem.setText(posologia.getDosagem());

        carregaImagem(posologiaalter.getFotoPosologia());

        spHora.setSelection(adapterHorario.getPosition(posologia.getHorario()));

        spTempo.setSelection(adapterTempo.getPosition(posologia.getTempo()));

        spTipo.setSelection(adapterTipo.getPosition(posologia.getTipo()));

        spNomeMedicamento.setSelection(adapterNome.getPosition(posologia.getMedicamentoID().getNome()));

    }

    public Posologia getPosologia() {

        posologia.setDiasTratamento(edtDiasMedicamento.getText().toString());
        posologia.setVezesDia(edtVezesDia.getText().toString());
        posologia.setDosagem(edtDosagem.getText().toString());
        posologia.setHorario(spHora.getSelectedItem().toString());
        posologia.setTempo(spTempo.getSelectedItem().toString());
        posologia.setTipo(spTipo.getSelectedItem().toString());
        listMedicamento2(posologia);
//        posologia.getMedicamentoID().setId(spNomeMedicamento.getSelectedItemId());
        return posologia;
    }

    public void carregaImagem(String caminhoArquivo) {
        if (caminhoArquivo != null) {
            Bitmap imagem = BitmapFactory.decodeFile(caminhoArquivo);
            // Bitmap imagemreduzida = Bitmap.createScaledBitmap(imagem, 100, 100, true);
            ivPosologia.setImageBitmap(imagem);
        } else {
            ivPosologia.setImageResource(R.drawable.medical_pot_pills);
        }

    }

    public void salvarImagem(String caminho) {
        if (caminho != null) {
            posologia.setFotoPosologia(caminho);

            Bitmap imagem = BitmapFactory.decodeFile(caminho);
            ivPosologia.setImageBitmap(imagem);
        } else {
            ivPosologia.setImageResource(R.drawable.medical_pot_pills);
        }

    }

    public boolean validarCampos(PosologiaNewActivity pos) {


        if (edtDiasMedicamento.getText().toString().isEmpty()) {
            edtDiasMedicamento.setError("Informe o Tempo de Medicação");
            edtDiasMedicamento.setFocusable(true);
            edtDiasMedicamento.requestFocus();
            return false;
        } else if (edtDosagem.getText().toString().isEmpty()) {
            edtDosagem.setError("Informe a Dosagem do Medicamento");
            edtDosagem.setFocusable(true);
            edtDosagem.requestFocus();
            return false;
        } else if (edtVezesDia.getText().toString().isEmpty()) {
            edtVezesDia.setError("Informe Quantidade vezes ao dia");
            edtVezesDia.setFocusable(true);
            edtVezesDia.requestFocus();
            return false;
        }
        return true;
    }

    public void listMedicamento2(Posologia pos) {
        for (int i = 0; i < mede.size(); i++) {
            int poss = spNomeMedicamento.getSelectedItemPosition();
            Medicamento medec = mede.get(poss);
            try {
                pos.getMedicamentoID().setId(medec.getId());
            } catch (Exception e) {

            }
        }
    }


}
