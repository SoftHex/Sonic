package com.softhex.sonic;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

public class sonicConfigPerfil extends AppCompatActivity{

    private Toolbar mToolbar;
    private TextView mTitle;
    private sonicPreferences mPref;
    private TextView mDesc;
    private TextView mEndereco;
    private TextView mBairro;
    private TextView mMunicipio;
    private TextView mCep;
    private TextView mFone;
    private TextView mWhats;
    private TextView mEmail;
    private TextView mSite;
    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_config_perfil);

        mPref= new sonicPreferences(this);
        mToolbar = findViewById(R.id.mToolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Perfil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sonicAppearence.removeFlashingTransition(getWindow());

        mImage = findViewById(R.id.ivImagem);
        mTitle = findViewById(R.id.tvTitle);
        mDesc = findViewById(R.id.tvDescricao);
        mEndereco = findViewById(R.id.tvEndereco);
        mBairro = findViewById(R.id.tvBairro);
        mMunicipio = findViewById(R.id.tvMunicipio);
        mCep = findViewById(R.id.tvCep);
        mFone = findViewById(R.id.tvFone);
        mWhats = findViewById(R.id.tvWhats);
        mEmail = findViewById(R.id.tvEmail);
        mSite = findViewById(R.id.tvSite);

        mTitle.setText(mPref.GrupoEmpresas.getNome());
        mDesc.setText(mPref.GrupoEmpresas.getDescricao());
        mEndereco.setText(mPref.GrupoEmpresas.getEndereco());
        mBairro.setText(mPref.GrupoEmpresas.getBairro());
        mMunicipio.setText(mPref.GrupoEmpresas.getMunicipio()+" - "+mPref.GrupoEmpresas.getUF());
        mCep.setText("Cep: "+mPref.GrupoEmpresas.getCep());
        mFone.setText(mPref.GrupoEmpresas.getFone());
        mWhats.setText(mPref.GrupoEmpresas.getWhats());
        mEmail.setText(mPref.GrupoEmpresas.getEmail());
        mSite.setText(mPref.GrupoEmpresas.getSite());

        File file = new File(Environment.getExternalStorageDirectory(), mPref.GrupoEmpresas.getPicture());
        if(file.exists()){

            Glide.with(getApplicationContext())
                    .load(file)
                    .apply(new RequestOptions().override(600,300))
                    .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                    .into(mImage);

        }

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}
