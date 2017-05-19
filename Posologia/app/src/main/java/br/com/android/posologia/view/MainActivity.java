package br.com.android.posologia.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import br.com.android.posologia.R;
import br.com.android.posologia.adapter.ViewPagerAdapter;
import br.com.android.posologia.dominio.entidades.Medicamento;
import br.com.android.posologia.fragment.MedicamentoFragment;
import br.com.android.posologia.fragment.PosologiaFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar3);
        toolbar.setTitle("Posologia");
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

			//EXCLUIR DEPOIS
       /*   try {
            dataBase = new DataBase(this);
          conn = dataBase.getReadableDatabase();

            repPessoa = new RepPessoa(conn);
            repMedicamento = new RepMedicamento(conn);
            repPosologia = new RepPosologia();


            //  adapter = repPessoa.listarPessoas(this);

            //lvPessoa.setAdapter(adapter);

            filtraDados = new FiltraDados(adapter);
        } catch (SQLException e) {
            MessageBox.showAlert(this, getResources().getString(R.string.lbl_erro), getResources().getString(R.string.lbl_erro_criar_base) + ": " + e.getMessage());
        }*/
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
                Posologia();
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

    private void Posologia() {
        Intent intent = new Intent(this, PosologiaNewActivity.class);
        startActivity(intent);
    }

}

