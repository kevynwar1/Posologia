package br.com.android.posologia.view;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.lang.reflect.Array;

import br.com.android.posologia.R;
import br.com.android.posologia.app.MessageBox;
import br.com.android.posologia.dominio.RepPosologia;
import br.com.android.posologia.dominio.entidades.Posologia;
import br.com.android.posologia.fragment.PosologiaFragment;

public class PosologiaNewActivity extends AppCompatActivity {
    private ImageView ivPosologia;
    private EditText edtDiasMedicamento;
    private EditText edtVezesDia;
    private EditText edtDosagem;

    private Spinner spHora;
    private Spinner spTempo;
    private Spinner spTipo;

    private Button btSalvarPosologa;
    private Button btExcluirPosologia;

    private Posologia posologia;
    private RepPosologia repPosologia;

    ArrayAdapter<String> adapterHorario;
    ArrayAdapter<String> adapterTempo;
    ArrayAdapter<String> adapterTipo;
    private String pathImg;
    Bitmap imagem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posologia_new);

        ivPosologia = (ImageView) findViewById(R.id.ivPosologia);

        edtDiasMedicamento = (EditText) findViewById(R.id.edtDiasMedicamento);
        edtVezesDia = (EditText) findViewById(R.id.edtVezesDia);
        edtDosagem = (EditText) findViewById(R.id.edtDosagem);

        spHora = (Spinner) findViewById(R.id.spHora);
        spTempo = (Spinner) findViewById(R.id.spTempo);
        spTipo = (Spinner) findViewById(R.id.spDosagem);

        btSalvarPosologa = (Button) findViewById(R.id.btSalvarPosologia);
        btExcluirPosologia = (Button) findViewById(R.id.btExcluirPosologia);

        repPosologia = new RepPosologia(this);

        //SPINNER DO HORARIO
        adapterHorario = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        spHora.setAdapter(adapterHorario);
        adapterHorario.add("02 em 02 Horas");
        adapterHorario.add("04 em 04 Horas");
        adapterHorario.add("06 em 06 Horas");
        adapterHorario.add("08 em 08 Horas");
        adapterHorario.add("12 em 12 Horas");

        //SPINNER DO TEMPO
        adapterTempo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        spTempo.setAdapter(adapterTempo);
        adapterTempo.add("Dias");
        adapterTempo.add("Meses");
        adapterTempo.add("Anos");

        //SPINNER TIPO
        adapterTipo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        spTipo.setAdapter(adapterTipo);
        adapterTipo.add("Gotas");
        adapterTipo.add("Comprimidos");


        Bundle bundle = getIntent().getExtras();

        // Se recebeu parametros da lista, modo edição.
        if ((bundle != null) && (bundle.containsKey(PosologiaFragment.PARAM_POSOLOGIA))) {
            posologia = (Posologia) bundle.getSerializable(PosologiaFragment.PARAM_POSOLOGIA);
            preencheDados();
            btSalvarPosologa.setText("Alterar");
            btExcluirPosologia.setVisibility(View.VISIBLE);
        } else {
            posologia = new Posologia();
        }


        clickSalvaPosologia();
        clickExcluirPosologia();
        clickImagem();
    }

    private void preencheDados() {

        edtDiasMedicamento.setText(posologia.getDiasTratamento());
        edtVezesDia.setText(posologia.getVezesDia());
        edtDosagem.setText(posologia.getDosagem());

        ArrayAdapter adapterPosologia2 = (ArrayAdapter) spHora.getAdapter();
        spHora.setSelection(adapterPosologia2.getPosition(posologia.getHorario()));

        ArrayAdapter adapterTempo2 = (ArrayAdapter) spTempo.getAdapter();
        spTempo.setSelection(adapterTempo2.getPosition(posologia.getTempo()));

        ArrayAdapter adapterTipo2 = (ArrayAdapter) spTipo.getAdapter();
        spTipo.setSelection(adapterTipo2.getPosition(posologia.getTipo()));
        if (posologia.getFotoPosologia() != null) {
            carregaImagem(posologia.getFotoPosologia());
        } else {
            ivPosologia.setImageResource(R.drawable.picture_no_image);
        }

    }

    private void clickSalvaPosologia() {
        btSalvarPosologa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    posologia.setDiasTratamento(edtDiasMedicamento.getText().toString());
                    posologia.setVezesDia(edtVezesDia.getText().toString());
                    posologia.setDosagem(edtDosagem.getText().toString());
                    posologia.setHorario(spHora.getSelectedItem().toString());
                    posologia.setTempo(spTempo.getSelectedItem().toString());
                    posologia.setTipo(spTipo.getSelectedItem().toString());
                    posologia.setFotoPosologia(pathImg);

                    if (posologia.getIdPosologia() == 0) {
                        repPosologia.inserirPosologia(posologia);
                        finish();
                    } else {
                        repPosologia.alterarPosologia(posologia);
                        finish();
                    }
                } catch (Exception e) {
                    MessageBox.showAlert(PosologiaNewActivity.this, getResources().getString(R.string.lbl_erro), getResources().getString(R.string.lbl_erro_salvar) + ": " + e.getMessage());
                }
            }
        });
    }

    private void clickExcluirPosologia() {
        btExcluirPosologia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    repPosologia.excluirPosologia(posologia.getIdPosologia());
                    finish();

                } catch (Exception e) {
                    MessageBox.showAlert(PosologiaNewActivity.this, getResources().getString(R.string.lbl_erro), getResources().getString(R.string.lbl_erro_excluir) + ": " + e.getMessage());

                }
            }
        });
    }

    private void clickImagem() {
        ivPosologia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri imageSelecionada = data.getData();

            String[] colunas = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(imageSelecionada, colunas, null, null, null);
            cursor.moveToFirst();
            int indexColuna = cursor.getColumnIndex(colunas[0]);
            pathImg = cursor.getString(indexColuna);
            imagem = BitmapFactory.decodeFile(pathImg);
            ivPosologia.setImageBitmap(imagem);

            cursor.close();
        }else{
            ivPosologia.setImageResource(android.R.drawable.ic_menu_camera);
        }

    }

    public Bitmap carregaImagem(String pathImg) {
        posologia.setFotoPosologia(pathImg);
        imagem = BitmapFactory.decodeFile(pathImg);
        ivPosologia.setImageBitmap(imagem);
        return imagem;


    }
}
