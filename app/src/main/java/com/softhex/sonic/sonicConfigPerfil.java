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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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

        mTitle.setText(mPref.Matriz.getNome());
        mDesc.setText(mPref.Matriz.getDescricao());
        mEndereco.setText(mPref.Matriz.getEndereco());
        mBairro.setText(mPref.Matriz.getBairro());
        mMunicipio.setText(mPref.Matriz.getMunicipio()+" - "+mPref.Matriz.getUF());
        mCep.setText("Cep: "+mPref.Matriz.getCep());
        mFone.setText(mPref.Matriz.getFone());
        mWhats.setText(mPref.Matriz.getWhats());
        mEmail.setText(mPref.Matriz.getEmail());
        mSite.setText(mPref.Matriz.getSite());

        File file = new File(Environment.getExternalStorageDirectory(), mPref.Matriz.getPicture());
        if(file.exists()){

            Glide.with(this).clear(mImage);
            Glide.get(this).clearMemory();
            Glide.with(getApplicationContext())
                    .load(file)
                    .apply(new RequestOptions().override(600,300)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true))
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
