package br.com.android.posologia.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.android.posologia.R;
import br.com.android.posologia.model.Medicamento;

/**
 * Created by Kevyn on 03/05/2017.
 */

public class ArrayAdapterMedicamento extends ArrayAdapter<Medicamento> {
    int resource;

    private LayoutInflater inflater;
    private Context context;
    private Bitmap foto;

    public ArrayAdapterMedicamento(Context context, int resource) {
        super(context, resource);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        this.context = context;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ViewHolder();

            view = inflater.inflate(resource, parent, false);

            viewHolder.ivItem = (ImageView) view.findViewById(R.id.ivItem);
            viewHolder.txtNome = (TextView) view.findViewById(R.id.txtNome);
            viewHolder.txtDosagem = (TextView) view.findViewById(R.id.txtDosagem);


            view.setTag(viewHolder);

            // convertView = view;
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

        Medicamento medicamento = getItem(position);

        viewHolder.txtNome.setText(medicamento.getNome());
        viewHolder.txtDosagem.setText(medicamento.getMiligrama() + "mg");
        if (medicamento.getFoto() != null) {
            foto = BitmapFactory.decodeFile(medicamento.getFoto());
            viewHolder.ivItem.setImageBitmap(foto);
        } else {
            viewHolder.ivItem.setImageResource(R.drawable.medical_pot_pills);
        }


        return view;
    }

    static class ViewHolder {
        ImageView ivItem;
        TextView txtNome;
        TextView txtDosagem;
    }
}
