package com.softhex.sonic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class sonicConfig extends AppCompatActivity implements View.OnClickListener{

    private Toolbar mToolbar;
    private LinearLayout seguranca, personalizacao, notificacoes, sobre, ajuda;
    private Intent i;
    private LinearLayout llContent;
    private CircleImageView mImagem;
    private TextView mTitle, mDesc;
    private sonicPreferences mPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_config);

        mPref = new sonicPreferences(this);
        mToolbar = findViewById(R.id.mToolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Configurações");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sonicAppearence.removeFlashingTransition(getWindow());

        llContent = findViewById(R.id.llContent);
        mImagem = findViewById(R.id.ivImagem);
        mTitle = findViewById(R.id.tvTitle);
        mTitle.setText(mPref.GrupoEmpresas.getNome());
        mDesc = findViewById(R.id.tvDescricao);
        mDesc.setText(mPref.GrupoEmpresas.getDescricao());
        seguranca = findViewById(R.id.llSeguranca);
        personalizacao = findViewById(R.id.llPersonalizacao);
        notificacoes = findViewById(R.id.llNotificacoes);
        ajuda = findViewById(R.id.llAjuda);
        sobre = findViewById(R.id.llSobre);
        llContent.setOnClickListener(this);
        seguranca.setOnClickListener(this);
        personalizacao.setOnClickListener(this);
        notificacoes.setOnClickListener(this);
        ajuda.setOnClickListener(this);
        sobre.setOnClickListener(this);

        File file = new File(Environment.getExternalStorageDirectory(), sonicConstants.LOCAL_IMG_CATALOGO +"empresa.JPG");
        if(file.exists()){

            Glide.with(getApplicationContext())
                    .load(file)
                    .apply(new RequestOptions().override(100,100))
                    .into(mImagem);

        }
        mToolbar.setNavigationOnClickListener((View) -> {
                onBackPressed();
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.llContent:
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this
                        ,Pair.create(mImagem, ViewCompat.getTransitionName(mImagem))
                        ,Pair.create(mTitle, ViewCompat.getTransitionName(mTitle))
                        ,Pair.create(mDesc, ViewCompat.getTransitionName(mDesc))
                );
                i = new Intent(this, sonicConfigPerfil.class);
                startActivity(i,activityOptionsCompat.toBundle());
                break;
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
