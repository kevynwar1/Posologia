package br.com.android.posologia.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;

import br.com.android.posologia.dominio.entidades.Medicamento;

/**
 * Created by kevyn on 04/05/2017.
 */

public class FiltraDados implements TextWatcher {
    private ArrayAdapter<String> arrayAdapter;

    public FiltraDados(ArrayAdapter<String> arrayAdapter) {
        this.arrayAdapter = arrayAdapter;
    }

    public void setArrayAdapter(ArrayAdapter<String> arrayAdapter) {
        this.arrayAdapter = arrayAdapter;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        arrayAdapter.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
