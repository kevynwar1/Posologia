package br.com.android.posologia.view;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import br.com.android.posologia.R;
import br.com.android.posologia.app.MessageBox;
import br.com.android.posologia.database.DataBase;

public class CadastroActivity extends AppCompatActivity {

   /*

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtNome = (EditText) findViewById(R.id.edtNome);

        Bundle bundle = getIntent().getExtras();

        // Se recebeu parametros da lista, modo edição.
        if ((bundle != null) && (bundle.containsKey(PessoaFragment.PARAM_PESSOA))) {
            pessoa = ((Pessoa) bundle.getSerializable(PessoaFragment.PARAM_PESSOA));
            preencheDados();
        } else {
            pessoa = new Pessoa();
        }

        try {
            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();

            // Deixa o objeto de consulta pronto.
            repPessoa = new RepPessoa(conn);
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
        inflater.inflate(R.menu.menu_posologia_new, menu);

        // Se estiver editando apresenta opção Excluir.
        if (pessoa.getId() != 0) {
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
        edtNome.setText(pessoa.getNome());
    }

    private boolean salvar() {
        try {
            pessoa.setNome(edtNome.getText().toString());

            if (pessoa.getNome().isEmpty()) {
                MessageBox.showInfo(this, getResources().getString(R.string.lbl_atencao), getResources().getString(R.string.lbl_nome_requerido));
                return false;
            } else {
                if (pessoa.getId() == 0) {
                    repPessoa.inserirPessoa(pessoa);
                } else {
                    repPessoa.alterarPessoa(pessoa);
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
            repPessoa.excluirPessoa(pessoa.getId());
            return true;
        } catch (Exception e) {
            MessageBox.showAlert(this, getResources().getString(R.string.lbl_erro), getResources().getString(R.string.lbl_erro_excluir) + ": " + e.getMessage());
            return false;
        }
    }*/
}
