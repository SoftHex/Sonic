package com.softhex.sonic;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class sonicSplash extends AppCompatActivity {

    private Intent i;
    private sonicDatabaseCRUD DBC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_splash);

        DBC = new sonicDatabaseCRUD(sonicSplash.this);

        sonicAppearence.layoutWhitNoLogicalMenu(this, getWindow());


        new myAsyncTaskLogar().execute();

    }

    class myAsyncTaskLogar extends AsyncTask<Integer, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Integer... integers) {

            Boolean res = DBC.Usuario.usuarioAtivo();
            List<sonicUsuariosHolder> listaUser;
            listaUser = DBC.Usuario.selectUsuarioAtivo();

            if(res){

                i  = new Intent(sonicSplash.this,sonicMain.class);
                sonicConstants.USUARIO_ATIVO_ID = listaUser.get(0).getCodigo();
                sonicConstants.USUARIO_ATIVO_NOME = listaUser.get(0).getNome();
                sonicConstants.USUARIO_ATIVO_NIVEL = listaUser.get(0).getNivelAcessoId();
                sonicConstants.USUARIO_ATIVO_CARGO = listaUser.get(0).getCargo();
                sonicConstants.USUARIO_ATIVO_META_VENDA = listaUser.get(0).getMetaVenda();
                sonicConstants.USUARIO_ATIVO_META_VISITA = listaUser.get(0).getMetaVisita();
                sonicConstants.EMPRESA_SELECIONADA_NOME = listaUser.get(0).getEmpresa();
                sonicConstants.EMPRESA_SELECIONADA_ID = listaUser.get(0).getEmpresaId();

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
                , 3000);


    }

}
