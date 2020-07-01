package com.softhex.sonic;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class sonicSplash extends AppCompatActivity {

    private Activity mActivity;
    private sonicPreferences mPrefs;
    private Long timeInMilliseconds;
    private sonicDatabaseCRUD mData;
    private List<sonicUsuariosHolder> mListUser;
    private List<sonicGrupoEmpresasHolder> mListEmpresa;
    private List<sonicFtpHolder> mListFtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_splash);

        mActivity = this;
        mData = new sonicDatabaseCRUD(this);
        mPrefs = new sonicPreferences(this);

        sonicAppearence.layoutWhitNoLogicalMenu(this, getWindow());

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    verificarUsuario(mData.Usuario.usuarioAtivo());

                                }
                            }
                ,sonicUtils.Randomizer.generate(1500, 3000));


    }

    class mAsyncTaskCarregarDados extends AsyncTask<Integer, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Integer... integers) {
            carregarDados();
            limparDados();
            finalizarRota();
            preencherTabelasFixas();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            Intent i = new Intent(sonicSplash.this, sonicMain.class);
            Intent i2 = new Intent(sonicSplash.this ,sonicLogin.class);
            //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            //i2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mPrefs.Users.getLogado() ? i : i2);
            finishAffinity();

        }
    }

    public void verificarUsuario(boolean logar){

        if(logar){
            new mAsyncTaskCarregarDados().execute();
        }else {
            Intent i  = new Intent(sonicSplash.this, sonicEmpresa.class);
            //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finishAffinity();
        }
    }

    private void carregarDados(){
        mListUser = mData.Usuario.selectUsuarioAtivo();
        mListEmpresa = mData.GrupoEmpresas.selectGrupoEmpresas();
        mListFtp = mData.Ftp.selectFtpAtivo();
        mPrefs.Ftp.setDescricao(mListFtp.get(0).getDescricao());
        mPrefs.Ftp.setEndereco(mListFtp.get(0).getEndereco());
        mPrefs.Ftp.setUsuario(mListFtp.get(0).getUsuario());
        mPrefs.Ftp.setSenha(mListFtp.get(0).getSenha());
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
        mPrefs.Users.setAdmin(mListUser.get(0).getAdmin() == 0 ? false : true);
        mPrefs.Users.setUsuarioId(mListUser.get(0).getCodigo());
        mPrefs.Users.setUsuarioNome(mListUser.get(0).getNome());
        mPrefs.Users.setUsuarioCargo(mListUser.get(0).getCargo());
        mPrefs.Users.setEmpresaId(mListUser.get(0).getEmpresaId());
        mPrefs.Users.setEmpresaNome(mListUser.get(0).getEmpresa());
        mPrefs.Users.setCodigoSinc(mListUser.get(0).getCodigo());
        mPrefs.Users.setArquivoSinc(mListUser.get(0).getCodigo());
    }

    private void limparDados(){
        mPrefs.GrupoCliente.setFiltroCnpj("TODOS");
        mPrefs.GrupoCliente.setFiltroCpf("TODOS");
        mPrefs.GrupoProduto.setFiltroGrid("TODOS");
        mPrefs.GrupoProduto.setFiltroLista("TODOS");
    }

    private void preencherTabelasFixas(){

        new sonicDatabaseCRUD(this).Database.cleanData(sonicConstants.TB_BANCOS);
        String[] codigoBancos = getResources().getStringArray(R.array.codigoBancos);
        String[] nomeBancos = getResources().getStringArray(R.array.nomeBancos);
        String[] nomeBancosFull = getResources().getStringArray(R.array.nomeBancosFull);
        for(int x=0; x<codigoBancos.length; x++){
            List<String> mTotalList = new ArrayList<>();
            mTotalList.add(codigoBancos[x]);
            mTotalList.add(nomeBancos[x]);
            mTotalList.add(nomeBancosFull[x]);
            new sonicDatabaseCRUD(this).Database.saveData(sonicConstants.TB_BANCOS, mTotalList, sonicDatabaseCRUD.DB_MODE_SAVE);
        }

    }

    private void finalizarRota(){
        timeInMilliseconds = SystemClock.uptimeMillis() - mPrefs.Rota.getStartTime();
        if(timeInMilliseconds>2*60*60*1000){
            if(mData.Rota.negativarRota(String.valueOf(mPrefs.Rota.getCodigo()), "TEMPO MÁXIMO ATINGIDO","")){
                mPrefs.Rota.setEmAtendimento(false);
            }
        }
    }
}
