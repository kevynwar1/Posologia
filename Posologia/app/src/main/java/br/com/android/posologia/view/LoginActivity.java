package br.com.android.posologia.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.android.posologia.R;
import br.com.android.posologia.dao.WebService;
import br.com.android.posologia.model.Usuario;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail;
    private EditText edtSenha;

    private Button btnEntrar;
    private Button btnCadastrar;

    WebService service;
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

                Intent it = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(it);
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
}