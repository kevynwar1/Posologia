package br.com.android.posologia.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.content.AsyncTaskLoader;
import java.util.List;
import java.util.Locale;


/**
 * Created by Karlinhos on 12/06/2017.
 * Realiza a busca por locais invocando metodos da classe Geocoder
 *
 */

public class BuscarLocalTask extends AsyncTaskLoader<List<Address>> {

    Context contexto;
    String local;
    List<Address> enderecos;

    public BuscarLocalTask(Context activity, String local){

        super(activity);
        contexto = activity;
        this.local = local;

    }
    //trata a lista de endereços encontrados
    @Override
    protected void onStartLoading(){
        if (enderecos == null){

            forceLoad();

        }else {
            deliverResult(enderecos);

        }


    }
    /*preenche a lsita de endereços atraves do geocoder
      buscando o nome do local e sua quantidade maxima
     */
    @Override
    public List<Address> loadInBackground() {
        Geocoder geocoder = new Geocoder(contexto, Locale.getDefault());
        try {
            enderecos = geocoder.getFromLocationName(local, 10);
        } catch (Exception e){

            e.printStackTrace();
        }
        return enderecos;
    }
}
