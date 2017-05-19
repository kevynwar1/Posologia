package br.com.android.posologia.database;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.android.posologia.dominio.entidades.Usuario;

/**
 * Created by Ikaro Sales on 18/05/2017.
 */

public class WebService {
    public void cadastrar(final Usuario usuario) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
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
                            toastThread(null, sb.toString());
                        }
                    } catch (Exception e) {}
                    os.flush();
                    os.close();
                    conn.disconnect();
                } catch (Exception e) {}
            }
        });
        thread.start();
    }

    public static void toastThread(final Context context, final String msg) {
        if (context != null && msg != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
