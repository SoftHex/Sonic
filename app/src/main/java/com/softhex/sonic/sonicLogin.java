package com.softhex.sonic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import zerobranch.androidremotedebugger.AndroidRemoteDebugger;

public class sonicLogin extends AppCompatActivity {

    private Button btEntrar;
    private EditText etSenha;
    private Activity mActivity;
    private ProgressDialog mProgress;
    private TextView tvUsuario, tvCargo, tvEmpresa;
    private ImageView myImage;
    private LinearLayout llAdmin;
    private Button btAcesse;
    private sonicPreferences mPrefs;
    private LinearLayout llSnackBar;
    private sonicDatabaseCRUD mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_login);

        sonicAppearence.layoutWhitTransparentStatusBar(this, getWindow());
        AndroidRemoteDebugger.init(this);

        mActivity = this;
        mData = new sonicDatabaseCRUD(this);
        mPrefs = new sonicPreferences(this);
        tvUsuario = findViewById(R.id.tvUsuario);
        tvCargo = findViewById(R.id.tvCargo);
        tvEmpresa = findViewById(R.id.tvEmpresa);
        myImage = findViewById(R.id.ciPerfil);
        sonicPreferences pref = new sonicPreferences(this);
        tvUsuario.setText(pref.Users.getUsuarioNome());
        tvCargo.setText("("+pref.Users.getUsuarioCargo()+")");
        tvEmpresa.setText(pref.Users.getEmpresaNome());
        btEntrar = findViewById(R.id.btEntrar);
        llAdmin = findViewById(R.id.llAdmin);
        btAcesse = findViewById(R.id.btAcesse);
        etSenha = findViewById(R.id.etSenha);
        llSnackBar = findViewById(R.id.llSnackBar);

        if(mPrefs.Users.getAdmin() && mPrefs.Users.getConfirmado()){
            llAdmin.setVisibility(View.VISIBLE);
            btAcesse.setOnClickListener((View v)->{
                exibirListaUsuarios();
            });
        }

        String imagem = pref.Users.getEmpresaId()+"_"+pref.Users.getUsuarioId()+".JPG";

        File file = new File(Environment.getExternalStorageDirectory(), sonicConstants.LOCAL_IMG_USUARIO +imagem);

        if(file.exists()){

            Glide.with(getApplicationContext())
                    .load(file)
                    .apply(new RequestOptions().override(600,600))
                    .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                    .into(myImage);

        }

        btEntrar.setOnClickListener((View v)-> {

            mProgress = new ProgressDialog(mActivity);
            mProgress.setCancelable(false);
            mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgress.setMessage("Autenticando...");
            mProgress.setIndeterminate(true);
            mProgress.show();

            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        new mAsyncTask().execute(etSenha.getText().toString());

                                    }
                                }
                    , 1000);

        });


    }

    private void exibirListaUsuarios() {

        List<sonicUsuariosHolder> users;

        users = mData.Usuario.listaUsuarios();

        List<String> l = new ArrayList<>();
        List<Integer> cod = new ArrayList<>();

        for(int i=0; i < users.size(); i++ ){
            l.add(users.get(i).getCodigo()+" - "+users.get(i).getNome());
            cod.add(users.get(i).getCodigo());
        }

        final CharSequence[] chars = l.toArray(new CharSequence[l.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(sonicLogin.this);
        builder.setItems(chars, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                mData.Usuario.setAtivo(cod.get(item));
                mData.Empresa.selecionarPrimeiraEmpresa();
                mData.Database.truncateAllTablesNonSite();
                mPrefs.Users.setAtivo(true);
                mPrefs.Users.setLogado(true);
                startActivity(new Intent(sonicLogin.this, sonicSplash.class));
                sonicLogin.this.finish();
            }
        }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();

    }


    public class mAsyncTask extends AsyncTask<String, Void, Boolean>{
        @Override
        protected Boolean doInBackground(String... strings) {

            return mData.Usuario.checkCredenciais(String.valueOf(mPrefs.Users.getUsuarioId()) , strings[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mProgress.dismiss();
            if(aBoolean){
                mPrefs.Users.setLogado(true);
                Intent i = new Intent(sonicLogin.this, sonicMain.class);
                startActivity(i);
                sonicLogin.this.finish();
            }else{
                Snackbar snackbar = Snackbar.make(llSnackBar, "SENHA INCORRETA", Snackbar.LENGTH_LONG);
                SnackbarHelper.configSnackbar(sonicLogin.this, snackbar);
                llSnackBar.addView(snackbar.getView());
                snackbar.show();
            }

        }
    }
}
