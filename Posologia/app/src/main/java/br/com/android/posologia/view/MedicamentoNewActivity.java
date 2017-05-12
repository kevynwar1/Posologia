package br.com.android.posologia.view;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import br.com.android.posologia.R;
import br.com.android.posologia.app.MessageBox;
import br.com.android.posologia.database.DataBase;
import br.com.android.posologia.dominio.RepMedicamento;
import br.com.android.posologia.dominio.entidades.Medicamento;
import br.com.android.posologia.fragment.MedicamentoFragment;

public class MedicamentoNewActivity extends AppCompatActivity {

    private EditText edtNome;
    private EditText edtDosagem;
    private EditText edtObservacoes;
    private Spinner spMedicamento;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private RepMedicamento repMedicamento;
    private Medicamento medicamento;
    ArrayAdapter<String> adapter;

    // private String[] Tipos = new String[]{"Antibióticos, AntiInflamatorio, Analgésico, Outros"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento_new);

        edtNome = (EditText) findViewById(R.id.edtNomeMedicamento);
        edtDosagem = (EditText) findViewById(R.id.edtDosagem);
        edtObservacoes = (EditText) findViewById(R.id.edtObservacoes);
        spMedicamento = (Spinner) findViewById(R.id.spTipo);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMedicamento.setAdapter(adapter);
        adapter.add("Antibioticos");
        adapter.add("AntiInflamatorio");
        adapter.add("Analgésico");
        adapter.add("Outros");


        Bundle bundle = getIntent().getExtras();

        // Se recebeu parametros da lista, modo edição.
        if ((bundle != null) && (bundle.containsKey(MedicamentoFragment.PARAM_MEDICAMENTO))) {
            medicamento = ((Medicamento) bundle.getSerializable(MedicamentoFragment.PARAM_MEDICAMENTO));
            preencheDados();
        } else {
            medicamento = new Medicamento();
        }

        try {
            // Deixa o objeto de consulta pronto.
            repMedicamento = new RepMedicamento(this);
        } catch (SQLException e) {
            MessageBox.showAlert(this, getResources().getString(R.string.lbl_erro), getResources().getString(R.string.lbl_erro_conexao) + ": " + e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (conn != null) {
            conn.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_medicamento_new, menu);

        // Se estiver editando apresenta opção Excluir.
        if (medicamento.getId() != 0) {
            menu.getItem(1).setVisible(true);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mn_salvar:
                if (salvar()) {
                    finish();
                }
                break;
            case R.id.mn_excluir:
                if (excluir()) {
                    finish();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void preencheDados() {
        edtNome.setText(medicamento.getNome());
        edtDosagem.setText(medicamento.getDosagem());
        edtObservacoes.setText(medicamento.getObservacao());
        ArrayAdapter adapter2 = (ArrayAdapter) spMedicamento.getAdapter();
        spMedicamento.setSelection(adapter2.getPosition(medicamento.getTipo()));
    }

    private boolean salvar() {
        try {
            medicamento.setNome(edtNome.getText().toString());
            medicamento.setDosagem(edtDosagem.getText().toString());
            medicamento.setObservacao(edtObservacoes.getText().toString());
            medicamento.setTipo(spMedicamento.getSelectedItem().toString());

            if (medicamento.getNome().isEmpty()) {
                MessageBox.showInfo(this, getResources().getString(R.string.lbl_atencao), getResources().getString(R.string.lbl_nome_requerido));
                return false;
            } else {
                if (medicamento.getId() == 0) {
                    repMedicamento.inserirMedicamento(medicamento);
                } else {
                    repMedicamento.alterarMedicamento(medicamento);
                }

                return true;
            }
        } catch (Exception e) {
            MessageBox.showAlert(this, getResources().getString(R.string.lbl_erro), getResources().getString(R.string.lbl_erro_salvar) + ": " + e.getMessage());
            return false;
        }
    }

    private boolean excluir() {
        try {
            repMedicamento.excluirMedicamento(medicamento.getId());
            return true;
        } catch (Exception e) {
            MessageBox.showAlert(this, getResources().getString(R.string.lbl_erro), getResources().getString(R.string.lbl_erro_excluir) + ": " + e.getMessage());
            return false;
        }
    }


}

