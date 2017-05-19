package br.com.android.posologia.fragment;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import br.com.android.posologia.R;
import br.com.android.posologia.adapter.ArrayAdapterMedicamento;
import br.com.android.posologia.database.DataBase;
import br.com.android.posologia.dominio.RepMedicamento;
import br.com.android.posologia.dominio.entidades.Medicamento;
import br.com.android.posologia.util.FiltraDados;
import br.com.android.posologia.view.MedicamentoNewActivity;

/**
 * Created by kevyn on 08/05/2017.
 */

public class MedicamentoFragment extends Fragment {

    private ListView lvMedicamento;
    private ArrayAdapter adapter;
    private EditText edtFiltro;


    private RepMedicamento repMedicamento;
    private FiltraDados filtraDados;
    public static final String PARAM_MEDICAMENTO = "MEDICAMENTO";


    public MedicamentoFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_medicamento, container, false);
        lvMedicamento = (ListView) view.findViewById(R.id.lstMedicamentos);
        edtFiltro = (EditText) view.findViewById(R.id.edtFiltro);


        try {
            repMedicamento = new RepMedicamento(getActivity());
            adapter = repMedicamento.listarMedicamentos(getActivity());
            lvMedicamento.setAdapter(adapter);
            filtraDados = new FiltraDados(adapter);
            edtFiltro.addTextChangedListener(filtraDados);

        } catch (SQLException e) {
            Toast.makeText(getActivity(), "Error do SQL" + e, Toast.LENGTH_LONG).show();

        }
        clickLista();


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = repMedicamento.listarMedicamentos(getActivity());
        filtraDados.setArrayAdapter(adapter);
        lvMedicamento.setAdapter(adapter);


    }

    private void clickLista() {
        lvMedicamento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Medicamento medicamento = (Medicamento) adapter.getItem(position);

                Intent intent = new Intent(getActivity(), MedicamentoNewActivity.class);
                intent.putExtra(PARAM_MEDICAMENTO, medicamento);
                startActivity(intent);


            }
        });
    }


  /*  @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        adapter = repMedicamento.listarMedicamentos(getActivity());
        filtraDados.setArrayAdapter(adapter);
        lvMedicamento.setAdapter(adapter);

    }*/


}
