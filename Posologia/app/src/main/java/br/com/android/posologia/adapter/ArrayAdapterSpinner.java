package br.com.android.posologia.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.android.posologia.R;
import br.com.android.posologia.dominio.entidades.Posologia;

/**
 * Created by kevyn on 19/05/2017.
 */

public class ArrayAdapterSpinner extends ArrayAdapter<Posologia> {
    private int resource;
    private LayoutInflater inflater;
    private Context context;


    public ArrayAdapterSpinner(Context context, int resource) {
        super(context, resource);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ArrayAdapterSpinner.ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ArrayAdapterSpinner.ViewHolder();

            view = inflater.inflate(resource, parent, false);

            viewHolder.txtSpinner = (TextView) view.findViewById(R.id.txtSpinner);


            view.setTag(viewHolder);

            // convertView = view;
        } else {
            viewHolder = (ArrayAdapterSpinner.ViewHolder) convertView.getTag();
            view = convertView;
        }

        Posologia posologia = getItem(position);

        viewHolder.txtSpinner.setText(posologia.getMedicamentoID().getNome());


        return view;
    }


    static class ViewHolder {

        TextView txtSpinner;
    }
}
