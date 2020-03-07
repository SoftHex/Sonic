package com.softhex.sonic;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.List;

public class sonicFirstAccess extends AppCompatActivity {

    private TextView myEmpresa, myUsuario, myCargo;
    private Button mConfirm;
    private ImageView myImage;
    private sonicDatabaseCRUD DBC;
    private sonicPreferences mPref;
    List<sonicUsuariosHolder> mListUser;
    List<sonicGrupoEmpresasHolder> mListEmpresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_first_access);

        sonicAppearence.layoutWhitNoLogicalMenu(this, getWindow());

        DBC = new sonicDatabaseCRUD(this);
        mPref = new sonicPreferences(this);

        myEmpresa = findViewById(R.id.tvEmpresa);
        myUsuario = findViewById(R.id.usuario);
        myCargo = findViewById(R.id.cargo);
        mConfirm = findViewById(R.id.confirmar);
        myImage = findViewById(R.id.perfil);

        myEmpresa.setText(mPref.Users.getEmpresaNome());
        myUsuario.setText(mPref.Users.getUsuarioNome());
        myCargo.setText("("+mPref.Users.getUsuarioCargo()+")");

        mConfirm.setOnClickListener((View v)-> {

                DBC.Usuario.setAtivo(mPref.Users.getUsuarioId());
                myProgress(v.getContext());

        });

        File file = new File(Environment.getExternalStorageDirectory(), mPref.Users.getPicture());

        if(file.exists()){

            Glide.with(getApplicationContext())
                    .load(file)
                    .apply(new RequestOptions().override(600,600))
                    .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                    .into(myImage);

        }

    }

    public void myProgress(Context ctx){

        final ProgressDialog myProgress;
        myProgress = new ProgressDialog(ctx);
        myProgress.setCancelable(false);
        myProgress.setMessage("Gravando informações...");
        myProgress.setMax(1);
        myProgress.setProgressStyle(0);
        myProgress.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                new myAsyncTask().execute(myProgress);
                }
            }, sonicUtils.Randomizer.generate(500, 3000));

    }

    class  myAsyncTask  extends AsyncTask<ProgressDialog, ProgressDialog, ProgressDialog>{


        @Override
        protected ProgressDialog doInBackground(ProgressDialog... progressDialogs) {

            mListUser = DBC.Usuario.selectUsuarioAtivo();
            mListEmpresa = DBC.GrupoEmpresas.selectGrupoEmpresas();
            mPref.GrupoEmpresas.setNome(mListEmpresa.get(0).getNome());
            mPref.GrupoEmpresas.setDescricao(mListEmpresa.get(0).getDescricao());
            mPref.GrupoEmpresas.setDataFundacao(mListEmpresa.get(0).getDataFundacao());
            mPref.GrupoEmpresas.setEndereco(mListEmpresa.get(0).getEndereco());
            mPref.GrupoEmpresas.setBairro(mListEmpresa.get(0).getBairro());
            mPref.GrupoEmpresas.setMunicipio(mListEmpresa.get(0).getMunicipio());
            mPref.GrupoEmpresas.setUF(mListEmpresa.get(0).getUf());
            mPref.GrupoEmpresas.setCep(mListEmpresa.get(0).getCep());
            mPref.GrupoEmpresas.setFone(mListEmpresa.get(0).getFone());
            mPref.GrupoEmpresas.setEmail(mListEmpresa.get(0).getEmail());
            mPref.GrupoEmpresas.setSite(mListEmpresa.get(0).getSite());
            mPref.GrupoEmpresas.setWhats(mListEmpresa.get(0).getWhatsapp());
            sonicConstants.USUARIO_ATIVO_NIVEL = mListUser.get(0).getNivelAcessoId();
            sonicConstants.USUARIO_ATIVO_CARGO = mListUser.get(0).getCargo();
            sonicConstants.USUARIO_ATIVO_META_VENDA = mListUser.get(0).getMetaVenda();
            sonicConstants.USUARIO_ATIVO_META_VISITA = mListUser.get(0).getMetaVisita();
            sonicConstants.EMPRESA_SELECIONADA_ID = mListUser.get(0).getEmpresaId();
            sonicConstants.EMPRESA_SELECIONADA_NOME = mListUser.get(0).getEmpresa(); //NOME FANTASIA
            return progressDialogs[0];
        }

        @Override
        protected void onPostExecute(ProgressDialog progs) {
            super.onPostExecute(progs);
            progs.dismiss();
                startActivity();

        }
    }

    public void startActivity(){
        Intent i = new Intent(sonicFirstAccess.this, sonicMain.class);
        startActivity(i);
        finish();
    }

}
