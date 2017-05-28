package br.com.android.posologia.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import br.com.android.posologia.R;
import br.com.android.posologia.model.Medicamento;
import br.com.android.posologia.view.MedicamentoNewActivity;

/**
 * Created by kevyn on 26/05/2017.
 */

public class MedicamentoHelper {

    private EditText edtNome;
    private EditText edtMiligrama;
    private EditText edtObservacoes;
    private Spinner spMedicamento;
    private ImageView ivMedicamento;

    private Medicamento medicamento;
    ArrayAdapter<String> adapter;

    public MedicamentoHelper(MedicamentoNewActivity medicamentoNew) {
        edtNome = (EditText) medicamentoNew.findViewById(R.id.edtNomeMedicamento);
        edtMiligrama = (EditText) medicamentoNew.findViewById(R.id.edtMiligrama);
        edtObservacoes = (EditText) medicamentoNew.findViewById(R.id.edtObservacoes);

        spMedicamento = (Spinner) medicamentoNew.findViewById(R.id.spTipo);
        ivMedicamento = (ImageView) medicamentoNew.findViewById(R.id.ivMedicamento);
        medicamento = new Medicamento();


    }

    public Medicamento getMedicamento() {

        medicamento.setNome(edtNome.getText().toString());
        medicamento.setMiligrama(edtMiligrama.getText().toString());
        medicamento.setObservacao(edtObservacoes.getText().toString());
        medicamento.setTipo(spMedicamento.getSelectedItem().toString());


        return medicamento;


    }

    public void spinnerTipoMedicamento(MedicamentoNewActivity medicamentoNew) {

        adapter = new ArrayAdapter<String>(medicamentoNew, android.R.layout.simple_spinner_dropdown_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMedicamento.setAdapter(adapter);
        adapter.add("Antibioticos");
        adapter.add("AntiInflamatorio");
        adapter.add("Analgésico");
        adapter.add("Outros");
    }

    public void preencheForm(Medicamento medicamentoalter) {
        medicamento = medicamentoalter;
        edtNome.setText(medicamento.getNome());
        edtMiligrama.setText(medicamento.getMiligrama());
        edtObservacoes.setText(medicamento.getObservacao());
        spMedicamento.setSelection(adapter.getPosition(medicamento.getTipo()));
        carregaImagem(medicamento.getFoto());

    }

    public void carregaImagem(String caminhoArquivo) {
        if (caminhoArquivo != null) {
            Bitmap imagem = BitmapFactory.decodeFile(caminhoArquivo);
            // Bitmap imagemreduzida = Bitmap.createScaledBitmap(imagem, 100, 100, true);
            ivMedicamento.setImageBitmap(imagem);
        } else {
            ivMedicamento.setImageResource(android.R.drawable.ic_menu_camera);
        }

    }

    public void salvaImagem(String caminho) {
        if (caminho != null) {
            medicamento.setFoto(caminho);

            Bitmap imagem = BitmapFactory.decodeFile(caminho);
            ivMedicamento.setImageBitmap(imagem);
        } else {
            ivMedicamento.setImageResource(android.R.drawable.ic_menu_camera);
        }

    }

    public boolean validarCampos() {


        if (edtNome.getText().toString().isEmpty()) {
            edtNome.setError("Informe o Nome do Medicamento");
            edtNome.setFocusable(true);
            edtNome.requestFocus();
            return false;
        } else if (edtNome.getText().toString().length() < 5) {
            edtNome.setError("Informe um Nome maior que 5 letras");
            edtNome.setFocusable(true);
            edtNome.requestFocus();
            return false;
        } else if (edtMiligrama.getText().toString().isEmpty()) {
            edtMiligrama.setError("Informe o Miligrama");
            edtMiligrama.setFocusable(true);
            edtMiligrama.requestFocus();
            return false;
        } else if (edtMiligrama.getText().toString().length() < 2) {
            edtMiligrama.setError("Informe o Miligrama com 2 Numeros ou mais");
            edtMiligrama.setFocusable(true);
            edtMiligrama.requestFocus();
            return false;
        }

        return true;
    }
}





