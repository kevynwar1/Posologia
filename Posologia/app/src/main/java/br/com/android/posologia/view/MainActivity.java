package br.com.android.posologia.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Calendar;

import br.com.android.posologia.R;
import br.com.android.posologia.adapter.ViewPagerAdapter;
import br.com.android.posologia.fragment.MedicamentoFragment;
import br.com.android.posologia.fragment.PosologiaFragment;
import br.com.android.posologia.util.ServiceNotification;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    int orientation;
    boolean isTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        isTablet = getResources().getBoolean(R.bool.isTablet);

        Intent it = new Intent(this, ServiceNotification.class);
        startService(it);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            //code for portrait mode
            toolbar.setTitle("Posologia");
            if (isTablet) {
                toolbar.setTitle("Posologia - TABLET");
            }
        } else {
            //code for landscape mode
            toolbar.setTitle("Posologia - Controle seus Medicamentos");
            if (isTablet) {
                toolbar.setTitle("Posologia - Controle seus Medicamentos - TABLET");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_exit:
                finish();
                break;
            case R.id.action_medicamento:
                NewMedicamentos();
                break;
            case R.id.action_posologia:
                NewPosologia();
                break;
            case R.id.action_mapa:
                NewMapa();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());


        adapter.addFrag(new MedicamentoFragment(), "MEDICAMENTOS");
        adapter.addFrag(new PosologiaFragment(), "HORÁRIOS");

        viewPager.setAdapter(adapter);
    }

    private void NewMedicamentos() {
        Intent intent = new Intent(this, MedicamentoNewActivity.class);
        startActivity(intent);
    }

    private void NewPosologia() {
        Intent intent = new Intent(this, PosologiaNewActivity.class);
        startActivity(intent);
    }

    private void NewMapa() {
        Intent it = new Intent(this, MapsActivity.class);
        startActivity(it);
    }
}

