package br.com.android.posologia.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.android.posologia.R;
import br.com.android.posologia.model.Usuario;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail;
    private EditText edtSenha;

    private Button btnEntrar;
    private Button btnCadastrar;

    Usuario user;
    InputStream is = null;
    String result;

    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = (EditText) findViewById(R.id.edtLEmail);
        edtSenha = (EditText) findViewById(R.id.edtLSenha);

        btnEntrar = (Button) findViewById(R.id.btnEntrarLogin);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();

                usuario.setEmail(edtEmail.getText().toString());
                usuario.setSenha(edtSenha.getText().toString());

                new AsyncTask<Object, Object, Usuario>() {
                    public ProgressDialog dialog;

                    @Override
                    protected void onPreExecute() {
                        dialog = ProgressDialog.show(LoginActivity.this, "Aguarde...", "Verificando conta.", true, true);
                    }

                    @Override
                    protected Usuario doInBackground(Object... params) {
                        try {
                            URL url = new URL("http://coopera.pe.hu/WebService/public/api/usuario/entrar");
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setReadTimeout(10000);
                            conn.setConnectTimeout(15000);
                            conn.setRequestMethod("POST");
                            conn.setDoInput(true);
                            conn.connect();

                            JSONObject jsonParam = new JSONObject();
                            jsonParam.put("email", usuario.getEmail());
                            jsonParam.put("senha", usuario.getSenha());

                            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                            os.writeBytes(jsonParam.toString());

                            int response = conn.getResponseCode();
                            is = conn.getInputStream();

                            result = readStream(is, 500);
                            String[] split = result.split(",");

                            user = new Usuario();
                            user.setId(Integer.parseInt(split[0]));
                            user.setNome(split[1]);
                            user.setEmail(split[2]);
                            user.setSenha(split[3]);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        return user;
                    }

                    @Override
                    protected void onPostExecute(Usuario usuario) {
                        dialog.dismiss();
                        if (usuario.getNome() != null) {
                            Toast.makeText(LoginActivity.this, "Olá, " + usuario.getNome() + ".", Toast.LENGTH_LONG).show();
                            Intent it = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(it);
                            edtEmail.setText("");
                            edtSenha.setText("");
                        } else {
                            Toast.makeText(LoginActivity.this, "E-mail e/ou Senha incorreta.", Toast.LENGTH_LONG).show();
                        }
                    }
                }.execute();
            }
        });

        btnCadastrar = (Button) findViewById(R.id.btnCadastrarLogin);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(it);


            }
        });
    }

    private String readStream(InputStream stream, int maxLength) throws IOException {
        String result = null;
        InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[maxLength];
        int numChars = 0;
        int readSize = 0;
        while (numChars < maxLength && readSize != -1) {
            numChars += readSize;
            int pct = (100 * numChars) / maxLength;
            readSize = reader.read(buffer, numChars, buffer.length - numChars);
        }
        if (numChars != -1) {
            numChars = Math.min(numChars, maxLength);
            result = new String(buffer, 0, numChars);
        }
        return result;
    }


}