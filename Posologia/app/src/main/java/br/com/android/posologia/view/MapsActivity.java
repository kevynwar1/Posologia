package br.com.android.posologia.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.android.posologia.R;
import br.com.android.posologia.util.ObterGPS;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private boolean res;
    ObterGPS gps;
    private LatLng origem;
    private boolean permissao = false;
    boolean gpsLigado = false;
    Handler mHandler;
    int tentativas;

    private static final int LOADER_ENDERECO = 1;
    EditText editLocal;
    TextView TxtProgresso;
    LinearLayout LayoutProgresso;
    LoaderManager loaderManager;
    private LatLng destino;
    ImageButton botaoBuscar;
    ImageButton botaoLocalizar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mHandler = new Handler();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    public void verificaGPS() {

        LocationManager local = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        gpsLigado = local.isProviderEnabled( LocationManager.GPS_PROVIDER);

        if( gpsLigado == false){

            exibirMensagem();

        }


    }






    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        verificaGPS();

        if(gpsLigado == true){

            pegarLocalAtual();
        }
    }


    public void pegarLocalAtual() {
        gps = new ObterGPS(this);



        if(pegarLocalizacao(this) &&  gps.getLatitude() != 0.0 || gps.getLongitude() != 0.0){

            atualizaMapa();
            tentativas = 0;

        }
        else if (tentativas < 10){

            tentativas++;

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pegarLocalAtual();
                }
            }, 2000);


        }
        else{

            Toast.makeText(this, "Localização não encontrada, por favor tente novamente!",Toast.LENGTH_SHORT).show();


        }


    }

    public void atualizaMapa(){

        BitmapDescriptor icone = BitmapDescriptorFactory.fromResource(R.drawable.icone_maps2);

        mMap.clear();
        origem = new LatLng(gps.getLatitude(), gps.getLongitude());
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.addMarker(new MarkerOptions().position(origem).icon(icone).title("Seu local"));
        CameraPosition atualizaLoc = new CameraPosition.Builder().target(origem).zoom(15).bearing(0).tilt(45).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(atualizaLoc));
        Toast.makeText(MapsActivity.this, "lat: " + gps.getLatitude() + " log: " + gps.getLongitude(), Toast.LENGTH_LONG).show();




    }


    @Override
    protected void onResume() {
        super.onResume();

        if(permissao == true){

            pegarLocalAtual();
        }

    }





    public boolean pegarLocalizacao(Context context){
        int REQUEST_PERMISSION_LOCALIZATION = 221;
        this.res = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)   != PackageManager.PERMISSION_GRANTED) { //&& ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                this.res = false;
                ActivityCompat.requestPermissions((Activity) context, new String[]{
                                android.Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_PERMISSION_LOCALIZATION);

            }
        }


        return this.res;
    }




    public void exibirMensagem(){
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(MapsActivity.this);

        // Titulo do dialogo
        alertDialog.setTitle("GPS");

        // Mensagem do dialogo
        alertDialog.setMessage("GPS não está habilitado. Deseja configurar?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Configurar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                MapsActivity.this.startActivity(intent);
                permissao = true;
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // visualizacao do dialogo
        alertDialog.show();
    }


}
