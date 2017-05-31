package br.com.android.posologia.util;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;




/**
 * Created by Karlinhos on 31/05/2017.
 */

public class ObterGPS extends Service implements LocationListener {

    private final Context contexto;
    boolean gpsAtivo = false;
    private boolean localizacao = false;
    boolean internet = false;
    Location location;
    private double latitude;
    private double longitude;

    private static final long DISTANCIA_MINIMA_ATUALIZACAO = 10;

    private static final long MINIMO_DE_ATT_POR_MILISEGUNDOS = 1000 * 60 * 1;

    protected LocationManager locationManager;

    public ObterGPS(Context contexto) {
        this.contexto = contexto;


        getLocalizacao();

    }


    public Location getLocalizacao() {
        try {
            locationManager = (LocationManager) contexto.getSystemService(LOCATION_SERVICE);
            gpsAtivo = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            internet = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


            if (!gpsAtivo && !internet) {


            } else {

                this.localizacao = true;

                if (internet) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ActivityCompat.checkSelfPermission(contexto, android.Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED){ // && ActivityCompat.checkSelfPermission(contexto, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


                            return null;
                        }
                    }
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MINIMO_DE_ATT_POR_MILISEGUNDOS,
                            DISTANCIA_MINIMA_ATUALIZACAO,this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            Log.i("LOG", "lat" + getLatitude());
                            Log.i("LOG", "long" + getLongitude());
                        }
                    }

                }
                // if GPS Enabled get lat/long using GPS Services
                if (gpsAtivo) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MINIMO_DE_ATT_POR_MILISEGUNDOS,
                                DISTANCIA_MINIMA_ATUALIZACAO,this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public boolean isLocalizacao() {
        return this.localizacao;
    }


    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        return latitude;
    }


    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        return  longitude;

    }






}
