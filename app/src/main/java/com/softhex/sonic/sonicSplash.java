package com.softhex.sonic;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class sonicSplash extends AppCompatActivity {

    private Intent i;
    private sonicDatabaseCRUD DBC;
    private Activity mActivity;
    private sonicPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_splash);

        mActivity = this;
        DBC = new sonicDatabaseCRUD(sonicSplash.this);
        mPref = new sonicPreferences(this);

        sonicAppearence.layoutWhitNoLogicalMenu(this, getWindow());


        new myAsyncTaskLogar().execute();

    }

    class myAsyncTaskLogar extends AsyncTask<Integer, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Integer... integers) {

            Boolean res = DBC.Usuario.usuarioAtivo();
            List<sonicUsuariosHolder> mListUser;
            List<sonicGrupoEmpresasHolder> mListEmpresa;
            mListUser = DBC.Usuario.selectUsuarioAtivo();
            mListEmpresa = DBC.GrupoEmpresas.selectGrupoEmpresas();

            if(res){

                mPref.Matriz.setNome(mListEmpresa.get(0).getNome());
                mPref.Matriz.setDescricao(mListEmpresa.get(0).getDescricao());
                mPref.Matriz.setDataFundacao(mListEmpresa.get(0).getDataFundacao());
                mPref.Matriz.setEndereco(mListEmpresa.get(0).getEndereco());
                mPref.Matriz.setBairro(mListEmpresa.get(0).getBairro());
                mPref.Matriz.setMunicipio(mListEmpresa.get(0).getMunicipio());
                mPref.Matriz.setUF(mListEmpresa.get(0).getUf());
                mPref.Matriz.setCep(mListEmpresa.get(0).getCep());
                mPref.Matriz.setFone(mListEmpresa.get(0).getFone());
                mPref.Matriz.setEmail(mListEmpresa.get(0).getEmail());
                mPref.Matriz.setSite(mListEmpresa.get(0).getSite());
                mPref.Matriz.setWhats(mListEmpresa.get(0).getWhatsapp());
                mPref.Users.setUsuarioId(mListUser.get(0).getCodigo());
                mPref.Users.setUsuarioNome(mListUser.get(0).getNome());
                mPref.Users.setUsuarioCargo(mListUser.get(0).getCargo());
                mPref.Users.setEmpresaId(mListUser.get(0).getEmpresaId());
                mPref.Users.setEmpresaNome(mListUser.get(0).getEmpresa());


            }else{
                res = false;
            }

            return res;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if(result){

                // VERIFICA SE O USUARIO ESCOLHEU EXIGIR SENHA AO ENTRAR
                if(new sonicPreferences(mActivity).Users.getStatusLogin()){
                    i  = new Intent(mActivity,sonicLogin.class);
                }else {
                    i = new Intent(mActivity, sonicMain.class);
                }

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
