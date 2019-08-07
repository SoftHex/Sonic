package com.softhex.sonic;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
    private ImageView myImage;
    private Context _this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_first_access);

        _this = this;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        myEmpresa = findViewById(R.id.empresa);
        myUsuario = findViewById(R.id.usuario);
        myCargo = findViewById(R.id.cargo);
        myButton = findViewById(R.id.confirmar);
        myImage = findViewById(R.id.perfil);

        myEmpresa.setText(getIntent().getStringExtra("EMPRESA"));
        myUsuario.setText(getIntent().getStringExtra("USUARIO"));
        myCargo.setText("("+getIntent().getStringExtra("CARGO")+")");

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myProgress(view.getContext());
            }
        });

        String imagem = getIntent().getIntExtra("EMPRESA_ID",0)+"_"+getIntent().getIntExtra("ID",0)+".jpg";

        File file = new File(Environment.getExternalStorageDirectory(), sonicConstants.LOCAL_IMG_PERFIL+imagem);

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
            }, 1000);

    }

    class  myAsyncTask  extends AsyncTask<ProgressDialog, ProgressDialog, ProgressDialog>{


        @Override
        protected ProgressDialog doInBackground(ProgressDialog... progressDialogs) {
            new sonicDatabaseCRUD(_this).Usuarios.setAtivo(getIntent().getIntExtra("ID",0));
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
