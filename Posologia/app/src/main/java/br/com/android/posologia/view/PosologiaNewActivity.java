package br.com.android.posologia.view;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.Toast;


import java.util.ArrayList;

import br.com.android.posologia.R;

import br.com.android.posologia.dao.RepMedicamento;
import br.com.android.posologia.dao.RepPosologia;
import br.com.android.posologia.helper.PosologiaHelper;
import br.com.android.posologia.model.Medicamento;
import br.com.android.posologia.model.Posologia;
import br.com.android.posologia.fragment.PosologiaFragment;

import static br.com.android.posologia.R.id.ivPosologia;

public class PosologiaNewActivity extends AppCompatActivity {

    private Button btSalvarPosologa;
    private Button btExcluirPosologia;

    private Posologia posologia;
    private RepPosologia repPosologia;
    private RepMedicamento repMedicamento;
    ArrayList<Medicamento> list;
    Bundle bundle;
    PosologiaHelper posHelper;
    String pathImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posologia_new);

        posHelper = new PosologiaHelper(this);

        btSalvarPosologa = (Button) findViewById(R.id.btSalvarPosologia);
        btExcluirPosologia = (Button) findViewById(R.id.btExcluirPosologia);

        posHelper.spinnerDosagem(this);
        posHelper.spinnerHorario(this);
        posHelper.spinnerTempo(this);

        bundle = getIntent().getExtras();
        receberDados();

        repPosologia = new RepPosologia(this);
        repMedicamento = new RepMedicamento(this);

        clickSalvaPosologia();
        clickExcluirPosologia();
        listMedicamento();
    }

    private void receberDados() {
        if ((bundle != null) && (bundle.containsKey(PosologiaFragment.PARAM_POSOLOGIA))) {
            Posologia posologiaalter = (Posologia) bundle.getSerializable(PosologiaFragment.PARAM_POSOLOGIA);
            posHelper.preencheForm(posologiaalter);
            btSalvarPosologa.setText("Alterar");
            btExcluirPosologia.setVisibility(View.VISIBLE);
        } else {
            posologia = new Posologia();
        }
    }

      private void clickSalvaPosologia() {
        btSalvarPosologa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                posologia = posHelper.getPosologia();
                posHelper.salvarImagem(pathImg);

                if (posologia.getIdPosologia() == 0) {
                    repPosologia.inserirPosologia(posologia);
                    finish();
                } else {
                    repPosologia.alterarPosologia(posologia);
                    finish();
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
                    Toast.makeText(PosologiaNewActivity.this, "Error ao Excluir", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void listMedicamento() {
        list = repMedicamento.listaNomeMedicamento();
        ArrayList<String> listaa = new ArrayList<>();
        for (Medicamento pos : list) {
            listaa.add(pos.getNome());
        }
        posHelper.spinnerNomeMedicamento(this, listaa);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_medicamento, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_foto:
                capturaFoto();
        }

        return super.onOptionsItemSelected(item);
    }

    public void capturaFoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri imageSelecionada = data.getData();

            String[] colunas = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(imageSelecionada, colunas, null, null, null);
            cursor.moveToFirst();
            int indexColuna = cursor.getColumnIndex(colunas[0]);

            pathImg = cursor.getString(indexColuna);
            posHelper.carregaImagem(pathImg);
            cursor.close();
        }

    }


}

