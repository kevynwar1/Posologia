package br.com.android.posologia.model;

/**
 * Created by kevyn on 08/05/2017.
 */

public class Usuario {
    private String nome;
    private String email;
    private String senha;

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }

    public void setSenha(String senha) { this.senha = senha; }
}
