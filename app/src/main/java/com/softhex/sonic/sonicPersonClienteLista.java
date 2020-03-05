package com.softhex.sonic;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class sonicPersonClienteLista extends AppCompatActivity {

    private Switch sCliSemCompra;
    private Switch sTituloEmAtraso;
    private sonicPreferences mPrefs;
    private LinearLayout llCliSemCompra;
    private LinearLayout llTituloEmAtraso;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference_cliente_list);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar myActionBar = getSupportActionBar();
        myActionBar.setTitle(getResources().getString(R.string.clienteLista));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setElevation(0);
        myActionBar.setDisplayHomeAsUpEnabled(true);
        myActionBar.setDisplayShowHomeEnabled(true);
        mPrefs = new sonicPreferences(this);
        sCliSemCompra = findViewById(R.id.sClienteSemCompra);
        sTituloEmAtraso = findViewById(R.id.sTituloEmAtraso);
        llCliSemCompra = findViewById(R.id.llCliSemCompra);
        llTituloEmAtraso = findViewById(R.id.llTituloEmAtraso);

        sCliSemCompra.setChecked(mPrefs.Clientes.getClienteSemCompra());
        sTituloEmAtraso.setChecked(mPrefs.Clientes.getTituloEmAtraso());
        llCliSemCompra.setVisibility(sCliSemCompra.isChecked() ? View.VISIBLE : View.GONE);
        llTituloEmAtraso.setVisibility(sTituloEmAtraso.isChecked() ? View.VISIBLE : View.GONE);

        sCliSemCompra.setOnCheckedChangeListener((CompoundButton, isChecked)-> {

            mPrefs.Clientes.setClienteSemCompra(isChecked);
            llCliSemCompra.setVisibility(isChecked ? View.VISIBLE : View.GONE);

        });

        sTituloEmAtraso.setOnCheckedChangeListener((CompoundButton, isChecked)-> {

            mPrefs.Clientes.setTituloEmTraso(isChecked);
            llTituloEmAtraso.setVisibility(isChecked ? View.VISIBLE : View.GONE);

        });

        mToolbar.setNavigationOnClickListener((View v)-> {
                onBackPressed();
        });


    }
}
