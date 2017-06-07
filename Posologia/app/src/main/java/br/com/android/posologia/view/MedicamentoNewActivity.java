package br.com.android.posologia.view;

import android.content.DialogInterface;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import br.com.android.posologia.R;
import br.com.android.posologia.dao.RepMedicamento;
import br.com.android.posologia.helper.MedicamentoHelper;
import br.com.android.posologia.model.Medicamento;
import br.com.android.posologia.fragment.MedicamentoFragment;


public class MedicamentoNewActivity extends AppCompatActivity {

    private Button btSalvarMedicamento;
    private Button btExcluirMedicamento;

    private Medicamento medicamento;
    private RepMedicamento repMedicamento;

    Medicamento medicamentoalter;
    MedicamentoHelper medHelper;

    private String caminhoArquivo;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento_new);

        medHelper = new MedicamentoHelper(this);

        btSalvarMedicamento = (Button) findViewById(R.id.btSalvarMedicamento);
        btExcluirMedicamento = (Button) findViewById(R.id.btExcluirMedicamento);


        //SPINNER TIPO DE MEDICAMENTO
        medHelper.spinnerTipoMedicamento(this);

        bundle = getIntent().getExtras();
        receberDados();


        // Deixa o objeto de consulta pronto.
        repMedicamento = new RepMedicamento(this);


        clickSalvar();
        clickExcluir();
    }

    private void receberDados() {
        if ((bundle != null) && (bundle.containsKey(MedicamentoFragment.PARAM_MEDICAMENTO))) {
            medicamentoalter = ((Medicamento) bundle.getSerializable(MedicamentoFragment.PARAM_MEDICAMENTO));
            medHelper.preencheForm(medicamentoalter);
            btSalvarMedicamento.setText(getString(R.string.alterar));
            btExcluirMedicamento.setVisibility(View.VISIBLE);
        } else {
            medicamento = new Medicamento();
        }
    }


    private void clickSalvar() {
        btSalvarMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    medicamento = medHelper.getMedicamento();
                    medHelper.salvaImagem(caminhoArquivo);
                    boolean validar = medHelper.validarCampos(MedicamentoNewActivity.this);

                    if (medicamento.getId() == 0 && validar == true) {

                        repMedicamento.inserirMedicamento(medicamento);
                        finish();
                    } else if (medicamento.getId() != 0 && validar == true) {
                        repMedicamento.alterarMedicamento(medicamento);
                        finish();
                    }
                } catch (Exception e) {
                    Toast.makeText(MedicamentoNewActivity.this, getString(R.string.lbl_erro_salvar), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void clickExcluir() {
        btExcluirMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    verificaExclusao();
                } catch (Exception e) {
                    Toast.makeText(MedicamentoNewActivity.this, getString(R.string.lbl_erro_excluir), Toast.LENGTH_LONG).show();

                }
            }
        });
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            medHelper.carregaImagem(caminhoArquivo);
        }
    }


    private void capturaFoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        caminhoArquivo = Environment.getExternalStorageDirectory().toString() + "/" + System.currentTimeMillis() + ".png";
        File arquivo = new File(caminhoArquivo);
// Uri.. explicando para o android onde fica o local da imagem
        Uri localImagem = Uri.fromFile(arquivo);
//salvar copia da imagem pelo Extra_output
        intent.putExtra(MediaStore.EXTRA_OUTPUT, localImagem);
        startActivityForResult(intent, 1);
    }

    public void verificaExclusao() {

        AlertDialog.Builder mensagem = new AlertDialog.Builder(this);
        mensagem.setMessage(R.string.verifica_exclusao);


        mensagem.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                repMedicamento.excluirMedicamento(medicamentoalter.getId());
                finish();

            }
        });
        mensagem.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        mensagem.create().show();
    }

}

