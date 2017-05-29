package br.com.android.posologia.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.util.Date;

import br.com.android.posologia.R;
import br.com.android.posologia.dao.DataBase;
import br.com.android.posologia.dao.MedicamentoTable;
import br.com.android.posologia.dao.PosologiaTable;
import br.com.android.posologia.dao.RepPosologia;
import br.com.android.posologia.model.Posologia;
import br.com.android.posologia.view.MainActivity;

public class BroadcastReceiverAlarme extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context context, Intent intent) {

        DataBase dataBase;
        dataBase = new DataBase(context);
        SQLiteDatabase conn = dataBase.getReadableDatabase();
        Cursor cursor = conn.rawQuery("SELECT _idPosologia, MedicamentoID, Nome, FotoPosologia, DiasTratamento, VezesDia, Dosagem, Horario," +
                "Tempo, Posologia.Tipo FROM Posologia INNER JOIN Medicamento  ON (_id = MedicamentoID)", null);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String currentDateandTime = sdf.format(new Date());
        Toast.makeText(context, "Horario: " + currentDateandTime, Toast.LENGTH_SHORT).show();


        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Posologia posologia = new Posologia();

                posologia.setIdPosologia(cursor.getInt(cursor.getColumnIndex(PosologiaTable.IDPOSOLOGIA)));
                posologia.getMedicamentoID().setId(cursor.getLong(cursor.getColumnIndex(PosologiaTable.MEDICAMENTOID)));
                posologia.getMedicamentoID().setNome(cursor.getString(cursor.getColumnIndex(MedicamentoTable.NOME)));
                posologia.setFotoPosologia(cursor.getString(cursor.getColumnIndex(PosologiaTable.FOTOPOSOLOGIA)));
                posologia.setDiasTratamento(cursor.getString(cursor.getColumnIndex(PosologiaTable.DIASTRATAMENTO)));
                posologia.setVezesDia(cursor.getString(cursor.getColumnIndex(PosologiaTable.VEZESDIA)));
                posologia.setDosagem(cursor.getString(cursor.getColumnIndex(PosologiaTable.DOSAGEM)));
                posologia.setHorario(cursor.getString(cursor.getColumnIndex(PosologiaTable.HORARIO)));
                posologia.setTempo(cursor.getString(cursor.getColumnIndex(PosologiaTable.TEMPO)));
                posologia.setTipo(cursor.getString(cursor.getColumnIndex(PosologiaTable.TIPO)));

                //SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
                //String currentDateandTime = sdf.format(new Date());

                Toast.makeText(context, "Horario: " + currentDateandTime, Toast.LENGTH_SHORT).show();
                if (posologia.getHorario().equals(currentDateandTime)){

                    gerarNotificacao(context, new Intent(context, MainActivity.class), "Nova Mensagem", "Titulo",
                            "Descricao Msg");

                }


            } while (cursor.moveToNext());
        }

        cursor.close();
        dataBase.close();




    }

    //Metodo para Notificação
    public void gerarNotificacao(Context context, Intent intent, CharSequence ticker,
                                 CharSequence titulo, CharSequence descricao){
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent p = PendingIntent.getActivity(context, 0, intent, 0);

        //Dados da Notificação
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setTicker(ticker);
        builder.setContentTitle(titulo);
        builder.setContentText(descricao);
        builder.setSmallIcon(R.drawable.medicamentos);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.medicamentos));
        builder.setContentIntent(p);

        /*Descrição da Notificação
        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
        String[] descricao = new String[]{"Descricao1", "Descricao2"};
        for (int i = 0; i < descricao.length; i++){
            style.addLine((descricao[i]));
        }
        builder.setStyle(style);*/

        //Vibração para Notificação
        Notification n = builder.build();
        n.vibrate = new long[]{150, 300, 150, 600};
        n.flags = Notification.FLAG_AUTO_CANCEL;
        nm.notify(R.drawable.medicamentos, n);

        //Toque para Notificação
        try{
            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(context, som);
            toque.play();
        }catch(Exception e) {

        }
    }
}
