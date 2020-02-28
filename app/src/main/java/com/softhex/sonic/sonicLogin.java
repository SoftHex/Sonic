package com.softhex.sonic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

public class sonicLogin extends AppCompatActivity {

    private Button btEntrar;
    private Activity mActivity;
    ProgressDialog mProgress;
    private TextView tvUsuario, tvCargo, tvEmpresa;
    private ImageView myImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_login);

        mActivity = this;
        tvUsuario = findViewById(R.id.tvUsuario);
        tvCargo = findViewById(R.id.tvCargo);
        tvEmpresa = findViewById(R.id.tvEmpresa);
        myImage = findViewById(R.id.ciPerfil);
        sonicPreferences pref = new sonicPreferences(this);
        tvUsuario.setText(pref.Users.getUsuarioNome());
        tvCargo.setText("("+pref.Users.getUsuarioCargo()+")");
        tvEmpresa.setText(pref.Users.getEmpresaNome());
        btEntrar = findViewById(R.id.btEntrar);

        String imagem = pref.Users.getEmpresaId()+"_"+pref.Users.getUsuarioId()+".JPG";

        File file = new File(Environment.getExternalStorageDirectory(), sonicConstants.LOCAL_IMG_USUARIO+imagem);
        Log.d("IMAGEM", file.toString());
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
            mProgress.setTitle("Users");
            mProgress.setMessage("Autenticando...");
            mProgress.setIndeterminate(true);
            mProgress.show();

            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        new mAsyncTask().execute("");

                                    }
                                }
                    , 1000);

        });


    }

    public class mAsyncTask extends AsyncTask<String, Void, Boolean>{
        @Override
        protected Boolean doInBackground(String... strings) {

            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mProgress.dismiss();
            Intent i = new Intent(mActivity, sonicMain.class);
            startActivity(i);
        }
    }
}
