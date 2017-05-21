package br.com.android.posologia.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import br.com.android.posologia.R;
import br.com.android.posologia.dominio.RepPosologia;
import br.com.android.posologia.dominio.entidades.Posologia;
import br.com.android.posologia.util.FiltraDados;
import br.com.android.posologia.view.PosologiaNewActivity;

/**
 * Created by kevyn on 04/05/2017.
 */

public class PosologiaFragment extends Fragment {

    private ListView lvPosologia;
    private EditText edtFiltroPosologia;
    private ArrayAdapter adapterPosologia;

    private RepPosologia repPosologia;
    private FiltraDados filtraDadosPosologia;
    public static final String PARAM_POSOLOGIA = "POSOLOGIA";

    public PosologiaFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_posologia, container, false);
        lvPosologia = (ListView) view.findViewById(R.id.lvPosologia);
        edtFiltroPosologia = (EditText) view.findViewById(R.id.edtFiltroPosologia);

        try {
            repPosologia = new RepPosologia(getActivity());
            adapterPosologia = repPosologia.listarPosologias(getActivity());
            lvPosologia.setAdapter(adapterPosologia);
            filtraDadosPosologia = new FiltraDados(adapterPosologia);
            edtFiltroPosologia.addTextChangedListener(filtraDadosPosologia);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error do SQL" + e, Toast.LENGTH_LONG).show();
        }
        clickListaPosologia();


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapterPosologia = repPosologia.listarPosologias(getActivity());
        filtraDadosPosologia.setArrayAdapter(adapterPosologia);
        lvPosologia.setAdapter(adapterPosologia);

    }

    private void clickListaPosologia() {
        lvPosologia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Posologia posologia = (Posologia) adapterPosologia.getItem(position);

                Intent intent = new Intent(getActivity(), PosologiaNewActivity.class);
                intent.putExtra(PARAM_POSOLOGIA, posologia);
                startActivity(intent);
            }
        });

    }
}
