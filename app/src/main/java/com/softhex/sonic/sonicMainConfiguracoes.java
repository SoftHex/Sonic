package com.softhex.sonic;

import android.content.Intent;
import android.preference.SwitchPreference;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

public class sonicMainConfiguracoes extends AppCompatActivity implements View.OnClickListener{

    private Toolbar myToolbar;
    private LinearLayout myLayout;
    private LinearLayout seguranca, personalizacao, notificacoes, sobre, ajuda;
    private SwitchPreference myVibrateSwitch;
    private Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_main_configuracoes);

        myToolbar = (Toolbar) findViewById(R.id.go_main_configuracoes_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Configurações");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myLayout = (LinearLayout)this.findViewById(R.id.layout_configuracoes);

        seguranca = (LinearLayout)myLayout.findViewById(R.id.seguranca);
        personalizacao = (LinearLayout)myLayout.findViewById(R.id.personalizacao);
        notificacoes = (LinearLayout)myLayout.findViewById(R.id.notificacoes);
        ajuda = (LinearLayout)myLayout.findViewById(R.id.ajuda);
        sobre = (LinearLayout)myLayout.findViewById(R.id.sobre);
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
            case R.id.seguranca:
                //i = new Intent(this, go_main_pref_seguranca.class);
                //startActivity(i);
                break;
            case R.id.personalizacao:
                //i = new Intent(this, go_main_pref_personalizacao.class);
                //startActivity(i);
                break;
            case R.id.notificacoes:
                //i = new Intent(this, go_main_pref_notificacoes.class);
                //startActivity(i);
                break;
            case R.id.ajuda:
                break;
            case R.id.sobre:
                break;
        }
    }

}
