package com.softhex.sonic;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

public class sonicFirstAccess extends AppCompatActivity {


    private TextView myEmpresa, myUsuario, myCargo;
    private Button myButton;
    private ProgressDialog myProgress;
    private sonicDatabaseCRUD DBC;
    private ImageView myImage;
    private sonicConstants myCons;
    private Context myCtx;
    SharedPreferences pref;
    SharedPreferences.Editor pref_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_first_access);

        myCtx = getApplicationContext();
        myCons = new sonicConstants();
        DBC = new sonicDatabaseCRUD(myCtx);

        myEmpresa = (TextView)findViewById(R.id.empresa);
        myUsuario = (TextView)findViewById(R.id.usuario);
        myCargo = (TextView)findViewById(R.id.cargo);
        myButton = (Button)findViewById(R.id.confirmar);
        myImage = (ImageView)findViewById(R.id.perfil);

        myEmpresa.setText(getIntent().getStringExtra("EMPRESA"));
        myUsuario.setText(getIntent().getStringExtra("USUARIO"));
        myCargo.setText(getIntent().getStringExtra("CARGO"));

        if(sonicConstants.EMP_TESTE){
            TextView adm = (TextView)findViewById(R.id.adm);
            adm.setVisibility(View.GONE);
        }

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myProgress(view.getContext());
            }
        });


        File file = new File(Environment.getExternalStorageDirectory(), myCons.LOCAL_IMG_PERFIL+DBC.Empresa.empresaPadraoId()+"_"+getIntent().getIntExtra("VENDEDOR_ID",0)+".jpg");
        if(file.exists()){

            Glide.with(getApplicationContext())
                    .load(file)
                    .apply(new RequestOptions().override(600,600))
                    .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                    .into(myImage);

        }

        /*String url = "https://www.urbanarts.com.br/imagens/produtos/113487/0/Ampliada/mulher-de-perfil-preto-e-branco.jpg";
            Glide.with(getApplicationContext())
                    .load(url)
                    .apply(new RequestOptions().override(300,300))
                    .transition(GenericTransitionOptions.with(R.anim.activity_fade_in))
                    .into(myImage);*/


    }

    public void myProgress(Context ctx){

        myProgress = new ProgressDialog(ctx);
        myProgress.setCancelable(false);
        myProgress.setMessage("Gravando informações...");
        myProgress.setMax(1);
        myProgress.setProgressStyle(0);
        myProgress.show();

        new myAsyncTask().execute();

    }

    class myAsyncTask  extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            DBC.Usuarios.setAtivo(getIntent().getStringExtra("IMEI"));
            pref = getSharedPreferences("GO_LOGIN", Context.MODE_PRIVATE);
            pref_login  = pref.edit();
            pref_login.putInt("VENDEDOR_ID", DBC.Usuarios.usuarioAtivoId());
            pref_login.putString("VENDEDOR_NOME", DBC.Usuarios.usuarioAtivoNome());
            pref_login.putString("VENDEDOR_CARGO", DBC.Usuarios.usuarioAtivoCargo());
            pref_login.putString("VENDEDOR_EMPRESA", DBC.Usuarios.usuarioEmpresa());
            pref_login.putInt("VENDEDOR_NIVEL_ACESSO", DBC.Usuarios.usuarioNivelAcesso());
            pref_login.putInt("SISTEMA_MENU_ROTA", 1);
            pref_login.apply();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    myProgress.dismiss();
                    startActivity();
                }
            }, 1000);

        }
    }

    public void startActivity(){
        Intent i = new Intent(sonicFirstAccess.this, sonicMain.class);
        startActivity(i);
        finish();
    }

}
