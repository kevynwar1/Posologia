package br.com.android.posologia.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.android.posologia.R;
import br.com.android.posologia.model.Posologia;
import de.hdodenhof.circleimageview.CircleImageView;

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

            viewHolder.ivPosologia = (CircleImageView) view.findViewById(R.id.ivPosologia);
            viewHolder.txtVezesDia = (TextView) view.findViewById(R.id.txtVezesDia);
            viewHolder.txtHorario = (TextView) view.findViewById(R.id.txtHorario);
            viewHolder.txtNomeMedicamento = (TextView) view.findViewById(R.id.txtNomeMedicamento);

            view.setTag(viewHolder);

            // convertView = view;
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

        Posologia posologia = getItem(position);

        viewHolder.txtNomeMedicamento.setText(posologia.getMedicamentoID().getNome());
        viewHolder.txtVezesDia.setText(posologia.getVezesDia() + " " + context.getString(R.string.lbl_vezes_dia));
        viewHolder.txtHorario.setText(context.getString(R.string.lbl_de) + posologia.getHorario());

        String caminhoFoto = posologia.getFotoPosologia();

        if (caminhoFoto != null) {
            foto = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap imagemreduzida = Bitmap.createScaledBitmap(foto, 100, 100, true);
            viewHolder.ivPosologia.setImageBitmap(imagemreduzida);
        } else {
            viewHolder.ivPosologia.setImageResource(R.drawable.medical_pot_pills);
        }


        return view;
    }


    static class ViewHolder {
        CircleImageView ivPosologia;
        TextView txtVezesDia;
        TextView txtHorario;
        TextView txtNomeMedicamento;
    }
}
