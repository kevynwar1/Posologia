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
import br.com.android.posologia.dominio.entidades.Medicamento;
import br.com.android.posologia.dominio.entidades.Posologia;

/**
 * Created by Kevyn on 03/05/2017.
 */

public class ArrayAdapterPosologia extends ArrayAdapter<Posologia> {

    private int resource;
    private LayoutInflater inflater;
    private Context context;
    private Bitmap foto;

    public ArrayAdapterPosologia(Context context, int resource) {
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

            viewHolder.ivPosologia = (ImageView) view.findViewById(R.id.ivPosologia);
            viewHolder.txtDiasTratamento = (TextView) view.findViewById(R.id.txtDiasTratamento);
            viewHolder.txtVezesDia = (TextView) view.findViewById(R.id.txtVezesDia);
            viewHolder.txtHorario = (TextView) view.findViewById(R.id.txtHorario);


            view.setTag(viewHolder);

            // convertView = view;
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

        Posologia posologia = getItem(position);

        viewHolder.txtDiasTratamento.setText("Tomar Durante :" + " " + posologia.getDiasTratamento() + " " + posologia.getTempo());
        viewHolder.txtVezesDia.setText(posologia.getVezesDia() + " " + " Vezes ao Dia");
        viewHolder.txtHorario.setText("De: " + posologia.getHorario());
        if (posologia.getFotoPosologia() != null) {
            foto = BitmapFactory.decodeFile(posologia.getFotoPosologia());
            viewHolder.ivPosologia.setImageBitmap(foto);
        } else {
            viewHolder.ivPosologia.setImageResource(R.drawable.picture_no_image);
        }


        return view;
    }

    static class ViewHolder {
        ImageView ivPosologia;
        TextView txtDiasTratamento;
        TextView txtVezesDia;
        TextView txtHorario;
    }
}
