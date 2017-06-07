package br.com.android.posologia.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.service.notification.StatusBarNotification;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.android.posologia.R;
import br.com.android.posologia.dao.RepMedicamento;
import br.com.android.posologia.dao.RepPosologia;
import br.com.android.posologia.model.Medicamento;
import br.com.android.posologia.model.Posologia;

public class ServiceNotification extends Service{

    public Intent mIntent;
    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }

    @Override
    public void onCreate() {
        super.onCreate();

        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                //Toast.makeText(context, "Service is still running", Toast.LENGTH_LONG).show();


                    try {

                        RepMedicamento repMedicamento = new RepMedicamento(context);
                        ArrayList<Medicamento> lstMedicamentos = repMedicamento.listaNomeMedicamento();

                        RepPosologia repPosologia = new RepPosologia(context);
                        ArrayList<Posologia> listPosologia = repPosologia.listaArrayPosologia();

                        if(lstMedicamentos!= null & lstMedicamentos.size() > 0) {
                            for (int i = 0; i < lstMedicamentos.size(); i++) {
                                Medicamento medicamento = lstMedicamentos.get(i);

                                for (int x = 0; x < listPosologia.size(); x++) {
                                    Posologia posologia = listPosologia.get(x);
                                    if(medicamento.getId() == posologia.getMedicamento_ID() ){


                                        //Toast.makeText(context, "Medicamento: " + medicamento.getNome() + "Horario: "+ posologia.getHorario(), Toast.LENGTH_LONG).show();
                                        gerarNotificacao(context,
                                                         mIntent,
                                                         "Tomar Medicamento",
                                                         "Posologia", "Medicamento: " + medicamento.getNome() + "Horario: "+ posologia.getHorario(),
                                                         medicamento.getId());
                                    }

                                }
                            }
                        }
                        else
                        {
                            Toast.makeText(context, "Não existe medicamentos configurados", Toast.LENGTH_LONG).show();
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                handler.postDelayed(runnable, 10000);
            }
        };
        handler.postDelayed(runnable, 10000);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.mIntent = intent;
        return super.onStartCommand(intent, flags, startId);

    }

    //Metodo para Notificação
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void gerarNotificacao(Context context, Intent intent, CharSequence ticker,
                                 CharSequence titulo, CharSequence descricao, Long idNotification) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        StatusBarNotification[] notifications = nm.getActiveNotifications();
        for (StatusBarNotification notification : notifications) {
            if (notification.getId() == idNotification) {
                return;
            }
        }

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
        nm.notify(Integer.valueOf(idNotification.toString()), n);

        //Toque para Notificação
        try {
            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(context, som);
            toque.play();
        } catch (Exception e) {

        }
    }

}


