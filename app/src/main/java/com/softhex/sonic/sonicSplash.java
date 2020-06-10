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
    private sonicDatabaseCRUD mData;
    private Activity mActivity;
    private sonicPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_splash);

        mActivity = this;
        mData = new sonicDatabaseCRUD(this);
        mPrefs = new sonicPreferences(this);

        sonicAppearence.layoutWhitNoLogicalMenu(this, getWindow());

        new myAsyncTaskLogar().execute();

    }

    class myAsyncTaskLogar extends AsyncTask<Integer, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Integer... integers) {

            return mData.Usuario.usuarioAtivo();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if(result){

                List<sonicUsuariosHolder> mListUser;
                List<sonicGrupoEmpresasHolder> mListEmpresa;
                mListUser = mData.Usuario.selectUsuarioAtivo();
                mListEmpresa = mData.GrupoEmpresas.selectGrupoEmpresas();
                mPrefs.Matriz.setNome(mListEmpresa.get(0).getNome());
                mPrefs.Matriz.setDescricao(mListEmpresa.get(0).getDescricao());
                mPrefs.Matriz.setDataFundacao(mListEmpresa.get(0).getDataFundacao());
                mPrefs.Matriz.setEndereco(mListEmpresa.get(0).getEndereco());
                mPrefs.Matriz.setBairro(mListEmpresa.get(0).getBairro());
                mPrefs.Matriz.setMunicipio(mListEmpresa.get(0).getMunicipio());
                mPrefs.Matriz.setUF(mListEmpresa.get(0).getUf());
                mPrefs.Matriz.setCep(mListEmpresa.get(0).getCep());
                mPrefs.Matriz.setFone(mListEmpresa.get(0).getFone());
                mPrefs.Matriz.setEmail(mListEmpresa.get(0).getEmail());
                mPrefs.Matriz.setSite(mListEmpresa.get(0).getSite());
                mPrefs.Matriz.setWhats(mListEmpresa.get(0).getWhatsapp());
                mPrefs.Users.setUsuarioId(mListUser.get(0).getCodigo());
                mPrefs.Users.setUsuarioNome(mListUser.get(0).getNome());
                mPrefs.Users.setUsuarioCargo(mListUser.get(0).getCargo());
                mPrefs.Users.setEmpresaId(mListUser.get(0).getEmpresaId());
                mPrefs.Users.setEmpresaNome(mListUser.get(0).getEmpresa());
                mPrefs.Users.setCodigoSinc(mListUser.get(0).getCodigo());
                mPrefs.Users.setArquivoSinc(mListUser.get(0).getCodigo());

                // VERIFICA SE O USUARIO ESCOLHEU EXIGIR SENHA AO ENTRAR
                if(mPrefs.Users.getStatusLogin()){
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
                ,sonicUtils.Randomizer.generate(1500, 3000));

    }

}
