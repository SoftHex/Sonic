package com.softhex.sonic;

import android.content.Intent;
import android.os.Bundle;
import android.preference.SwitchPreference;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class sonicConfig extends AppCompatActivity implements View.OnClickListener{

    private Toolbar myToolbar;
    private LinearLayout myLayout;
    private LinearLayout seguranca, personalizacao, notificacoes, sobre, ajuda;
    private SwitchPreference myVibrateSwitch;
    private Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_main_configuracoes);

        myToolbar = findViewById(R.id.tbConfig);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Configurações");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        myLayout = findViewById(R.id.llConfig);

        seguranca = findViewById(R.id.llSeguranca);
        personalizacao = findViewById(R.id.llPersonalizacao);
        notificacoes = findViewById(R.id.llNotificacoes);
        ajuda = findViewById(R.id.llAjuda);
        sobre = findViewById(R.id.llSobre);
        seguranca.setOnClickListener(this);
        personalizacao.setOnClickListener(this);
        notificacoes.setOnClickListener(this);
        ajuda.setOnClickListener(this);
        sobre.setOnClickListener(this);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.llSeguranca:
                i = new Intent(this, sonicConfigSeguranca.class);
                startActivity(i);
                break;
            case R.id.llPersonalizacao:
                i = new Intent(this, sonicConfigPerson.class);
                startActivity(i);
                break;
            case R.id.llNotificacoes:
                //i = new Intent(this, go_main_pref_notificacoes.class);
                //startActivity(i);
                break;
            case R.id.llAjuda:
                break;
            case R.id.llSobre:
                break;
        }
    }

}
