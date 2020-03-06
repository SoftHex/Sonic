package com.softhex.sonic;

import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class sonicEmpresaDetalhe extends AppCompatActivity{

    private Toolbar mToolbar;
    private TextView mTitle;
    private sonicPreferences mPref;
    private TextView mDesc;
    private TextView mEndereco;
    private TextView mBairro;
    private TextView mMunicipio;
    private TextView mCep;
    private TextView mFone;
    private TextView mEmail;
    private TextView mSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_empresa_detalhe);

        mPref= new sonicPreferences(this);
        mToolbar = findViewById(R.id.tbConfig);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Perfil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            View decor = getWindow().getDecorView();
            fade.excludeTarget(R.id.appBar, true);
            fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);
            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(fade);
            getWindow().setSharedElementsUseOverlay(false);
        }

        mTitle = findViewById(R.id.tvTitle);
        mDesc = findViewById(R.id.tvDescricao);
        mEndereco = findViewById(R.id.tvEndereco);
        mBairro = findViewById(R.id.tvBairro);
        mMunicipio = findViewById(R.id.tvMunicipio);
        mCep = findViewById(R.id.tvCep);
        mFone = findViewById(R.id.tvFone);
        mEmail = findViewById(R.id.tvEmail);
        mSite = findViewById(R.id.tvSite);

        mTitle.setText(mPref.GrupoEmpresas.getNome());
        mDesc.setText(mPref.GrupoEmpresas.getDescricao());
        mEndereco.setText(mPref.GrupoEmpresas.getEndereco());
        mBairro.setText(mPref.GrupoEmpresas.getBairro());
        mMunicipio.setText(mPref.GrupoEmpresas.getMunicipio()+" - "+mPref.GrupoEmpresas.getUF());
        mCep.setText("Cep: "+mPref.GrupoEmpresas.getCep());
        mFone.setText(mPref.GrupoEmpresas.getFone());
        mEmail.setText(mPref.GrupoEmpresas.getEmail());
        mSite.setText(mPref.GrupoEmpresas.getSite());

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}
