package com.softhex.sonic;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class sonicConfigPersonClienteLista extends AppCompatActivity {

    private Switch sCliSemCompra;
    private Switch sTituloEmAtraso;
    private sonicPreferences mPrefs;
    private LinearLayout llCliSemCompra;
    private LinearLayout llTituloEmAtraso;
    private TextView tvSemCompra, tvAtraso;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference_cliente_list);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar myActionBar = getSupportActionBar();
        myActionBar.setTitle("Lista de Clientes");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setElevation(0);
        myActionBar.setDisplayHomeAsUpEnabled(true);
        myActionBar.setDisplayShowHomeEnabled(true);
        mPrefs = new sonicPreferences(this);
        sCliSemCompra = findViewById(R.id.sClienteSemCompra);
        sTituloEmAtraso = findViewById(R.id.sTituloEmAtraso);
        tvSemCompra = findViewById(R.id.tvSemCompra);
        tvAtraso = findViewById(R.id.tvAtraso);
        //llCliSemCompra = findViewById(R.id.llCliSemCompra);
        llTituloEmAtraso = findViewById(R.id.llTituloEmAtraso);

        sCliSemCompra.setChecked(mPrefs.Clientes.getClienteSemCompra());
        sTituloEmAtraso.setChecked(mPrefs.Clientes.getTituloEmAtraso());
        tvSemCompra.setVisibility(sCliSemCompra.isChecked() ? View.VISIBLE : View.GONE);
        //llCliSemCompra.setVisibility(sCliSemCompra.isChecked() ? View.VISIBLE : View.GONE);
        //llTituloEmAtraso.setVisibility(sTituloEmAtraso.isChecked() ? View.VISIBLE : View.GONE);
        tvAtraso.setVisibility(sTituloEmAtraso.isChecked() ? View.VISIBLE : View.GONE);

        sCliSemCompra.setOnCheckedChangeListener((CompoundButton, isChecked)-> {

            mPrefs.Clientes.setClienteSemCompra(isChecked);
            //llCliSemCompra.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            tvSemCompra.setVisibility(isChecked ? View.VISIBLE : View.GONE);

        });

        sTituloEmAtraso.setOnCheckedChangeListener((CompoundButton, isChecked)-> {

            mPrefs.Clientes.setTituloEmTraso(isChecked);
            //llTituloEmAtraso.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            tvAtraso.setVisibility(isChecked ? View.VISIBLE : View.GONE);

        });

        mToolbar.setNavigationOnClickListener((View v)-> {
                onBackPressed();
        });


    }
}
