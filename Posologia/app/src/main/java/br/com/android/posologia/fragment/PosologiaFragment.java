package br.com.android.posologia.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.android.posologia.R;

/**
 * Created by kevyn on 04/05/2017.
 */

public class PosologiaFragment extends Fragment {
    public PosologiaFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posologia, container, false);
    }
}
