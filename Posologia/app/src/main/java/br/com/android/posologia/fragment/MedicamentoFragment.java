package br.com.android.posologia.fragment;

import android.content.Intent;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import br.com.android.posologia.R;
import br.com.android.posologia.dao.RepMedicamento;
import br.com.android.posologia.model.Medicamento;
import br.com.android.posologia.model.Posologia;
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
            Toast.makeText(getActivity(), getString(R.string.lbl_sql) + e, Toast.LENGTH_LONG).show();

        }
        clickLista();

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        adapter = repMedicamento.listarMedicamentos(getActivity());
        filtraDados.setAdapter(adapter);
        lvMedicamento.setAdapter(adapter);


    }

    private void clickLista() {
        lvMedicamento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Medicamento medicamento = (Medicamento) adapter.getItem(position);

                    Intent intent = new Intent(getActivity(), MedicamentoNewActivity.class);
                    intent.putExtra(PARAM_MEDICAMENTO, medicamento);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), R.string.erro_lista_medicamento, Toast.LENGTH_LONG).show();
                }

            }
        });
    }

}
