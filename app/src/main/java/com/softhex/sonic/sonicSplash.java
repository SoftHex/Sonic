package com.softhex.sonic;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

public class sonicSplash extends AppCompatActivity {

    private Intent i;
    private sonicDatabaseCRUD DBC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_splash);

        DBC = new sonicDatabaseCRUD(sonicSplash.this);

        sonicAppearence.checkLayoutLimit(this, getWindow());

        new myAsyncTaskLogar().execute();

    }

    class myAsyncTaskLogar extends AsyncTask<Integer, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Integer... integers) {

            Boolean res = DBC.Usuarios.usuarioAtivo();
            List<sonicUsuariosHolder> listaUser;
            List<sonicEmpresasHolder> listaEmpresa;
            listaUser = DBC.Usuarios.selectUsuarioAtivo();
            listaEmpresa = DBC.Empresa.empresasUsuarios();

            if(res){

                i  = new Intent(sonicSplash.this,sonicMain.class);
                sonicConstants.USUARIO_ATIVO_ID = listaUser.get(0).getCodigo();
                sonicConstants.USUARIO_ATIVO_NOME = listaUser.get(0).getNome();
                sonicConstants.USUARIO_ATIVO_NIVEL = listaUser.get(0).getNivelAcessoId();
                sonicConstants.USUARIO_ATIVO_CARGO = listaUser.get(0).getCargo();
                sonicConstants.USUARIO_ATIVO_META_VENDA = listaUser.get(0).getMetaVenda();
                sonicConstants.USUARIO_ATIVO_META_VISITA = listaUser.get(0).getMetaVisita();
                sonicConstants.EMPRESA_SELECIONADA_NOME = listaEmpresa.get(0).getNomeFantasia();
                sonicConstants.EMPRESA_SELECIONADA_ID = listaEmpresa.get(0).getCodigo();
                //Log.d("EMPRESA", listaEmpresa.get(0).getNomeFantasia());

            }else{
                res = false;
            }

            return res;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if(result){

                i  = new Intent(sonicSplash.this,sonicMain.class);

            }else{

                i  = new Intent(sonicSplash.this,sonicEmpresa.class);
            }

           logar(i);

        }
    }

    private void logar(Intent i){


        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    startActivity(i);
                                    finish();

                                }
                            }
                , sonicUtils.Randomizer.generate(1000, 4000));


    }

}
