package br.com.android.posologia.dao;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.android.posologia.model.Usuario;

/**
 * Created by Ikaro Sales on 18/05/2017.
 */

public class WebService extends AsyncTask<Void, Integer, Void> {
    private Usuario usuario;

    public WebService(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    protected void onPreExecute(){
        Log.i("AsyncTask", "Carregando... "+Thread.currentThread().getName());
    }

    @Override
    protected Void doInBackground(Void... params) {
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
        return null;
    }

    @Override
    protected void onPostExecute(Void params) {
        Log.i("AsyncTask", "Processo de Cadastro finalizado.");
    }
}