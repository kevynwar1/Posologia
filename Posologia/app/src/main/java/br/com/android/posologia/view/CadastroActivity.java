package br.com.android.posologia.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.android.posologia.R;
import br.com.android.posologia.model.Usuario;

public class CadastroActivity extends AppCompatActivity {
    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnCadastrar;
    private Button btnConta;

    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnConta = (Button) findViewById(R.id.btnConta);
        edtNome.requestFocus();

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connection()) {
                    if (validarCampos() == true) {
                        usuario = new Usuario();

                        usuario.setNome(edtNome.getText().toString());
                        usuario.setEmail(edtEmail.getText().toString());
                        usuario.setSenha(edtSenha.getText().toString());

                        new AsyncTask<Object, Object, Usuario>() {
                            public ProgressDialog dialog;

                            protected void onPreExecute() {
                                dialog = ProgressDialog.show(CadastroActivity.this, getString(R.string.aguarde), getString(R.string.cadastro_usuario), true, true);
                            }

                            @Override
                            protected Usuario doInBackground(Object... params) {
                                try {
                                    URL url = new URL("http://coopera.pe.hu/WebService/public/api/usuario/add");
                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                    conn.setRequestMethod("POST");
                                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                                    conn.setRequestProperty("Accept", "application/json");
                                    conn.setDoOutput(true);
                                    conn.setDoInput(true);

                                    JSONObject jsonParam = new JSONObject();
                                    jsonParam.put("nome", usuario.getNome());
                                    jsonParam.put("email", usuario.getEmail());
                                    jsonParam.put("senha", usuario.getSenha());

                                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                                    os.writeBytes(jsonParam.toString());

                                    try {
                                        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                                        StringBuilder sb = new StringBuilder();
                                        String linha;

                                        while ((linha = br.readLine()) != null) {
                                            sb.append(linha);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    os.flush();
                                    os.close();
                                    conn.disconnect();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return usuario;
                            }

                            @Override
                            protected void onPostExecute(Usuario params) {
                                if(dialog != null) {
                                    dialog.dismiss();
                                }
                                Toast.makeText(CadastroActivity.this, usuario.getNome() + getString(R.string.cadastro_sucesso), Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }.execute();
                    }
                } else {
                    Toast.makeText(CadastroActivity.this, R.string.verifica_conexao, Toast.LENGTH_LONG).show();
                }
            }
        });


        btnConta.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(CadastroActivity.this, LoginActivity.class);
                startActivity(it);

            }
        });

    }

    public boolean validarCampos() {
        if (edtNome.getText().toString().isEmpty()) {
            edtNome.setError(getString(R.string.lbl_nome_requerido));
            edtNome.setFocusable(true);
            edtNome.requestFocus();
            return false;
        } else if (edtNome.getText().toString().length() < 3) {
            edtNome.setError(getString(R.string.nome_usuario_maior));
            edtNome.setFocusable(true);
            edtNome.requestFocus();
            return false;
        } else if (edtSenha.getText().toString().isEmpty()) {
            edtSenha.setError(getString(R.string.senha_usuario));
            edtSenha.setFocusable(true);
            edtSenha.requestFocus();
            return false;
        } else if (edtSenha.getText().toString().length() <= 3) {
            edtSenha.setError(getString(R.string.senha_usuario_maior));
            edtSenha.setFocusable(true);
            edtSenha.requestFocus();
            return false;
        } else if (edtEmail.getText().toString().isEmpty()) {
            edtEmail.setError(getString(R.string.email_usuario));
            edtEmail.setFocusable(true);
            edtEmail.requestFocus();
            return false;
        } else if (!isValidEmail(edtEmail.getText().toString())) {
            edtEmail.setError(getString(R.string.email_usuario_invalido));
            edtEmail.setFocusable(true);
            edtEmail.requestFocus();
            return false;
        }

        return true;
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public boolean connection() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }
}