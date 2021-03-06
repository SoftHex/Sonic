package com.softhex.sonic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;

import java.io.File;

/**
 * Created by Administrador on 14/12/2017.
 */

public class sonicVerificarSite{

    private Context myCtx;
    private Activity mAct;
    private ProgressDialog myProgress;
    private sonicFtp myFtp;
    private sonicDialog myMessage;
    private sonicDatabaseLogCRUD DBCL;
    private sonicSystem mySystem;
    private sonicPreferences mPrefs;


    public sonicVerificarSite(Activity act){
        this.mAct = act;
        this.myCtx = act;
        this.mPrefs = new sonicPreferences(myCtx);
        this.myProgress = new ProgressDialog(myCtx);
        this.myFtp = new sonicFtp(act);
        this.myMessage = new sonicDialog(myCtx);
        this.DBCL = new sonicDatabaseLogCRUD(myCtx);
        this.mySystem = new sonicSystem(myCtx);
    }

    public Boolean validar(String site){

        String fileName = sonicConstants.FTP_SITES+ site +".xml";
        String fileFull = sonicConstants.LOCAL_TEMP + site +".xml";

        myProgress = new ProgressDialog(myCtx);
        myProgress.setCancelable(false);
        myProgress.setMessage("Conectando...");
        myProgress.setMax(1);
        myProgress.setProgressStyle(0);
        myProgress.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    new myAsyncTaskVerificar().execute(site, fileName, fileFull);

                                }
                            }
                , sonicUtils.Randomizer.generate(500, 3000));


        Boolean result = false;

        return result;
    }

    class myAsyncTaskVerificar extends AsyncTask<String, String, Boolean>{
        @Override
        protected Boolean doInBackground(String... strings) {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean res = false;

            if(myFtp.ftpConnect()){

                publishProgress("Verificando...");

                try{

                    if(myFtp.downloadFile(strings[1], strings[2])){

                        if (sonicFile.checkXmlFile(sonicConstants.LOCAL_TEMP, strings[0])) {

                            publishProgress("Salvando configurações...");

                            mPrefs.Geral.setSite(strings[0]);
                            mPrefs.Sincronizacao.setDownloadType("SITE");
                            //new sonicPopularTabelas(mAct).gravarDados(strings[0]+".TXT");
                            new sonicPopularTabelas(mAct).gravarDados(strings[0]+".xml");

                            res = true;

                        }else{
                            enviarErro("ARQUIVO NÃO ENCONTRADO...");
                        }

                    }else{

                        File delete = new File(Environment.getExternalStorageDirectory(), strings[2]);
                        delete.delete();

                        enviarErro("EMPRESA NÃO ENCONTRADA...");

                        res = false;

                    }

                }
                catch (Exception e){
                    mPrefs.Geral.setError(e.getMessage());
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                            e.getMessage(),
                            mySystem.System.getActivityName(),
                            mySystem.System.getClassName(el),
                            mySystem.System.getMethodNames(el));
                    e.printStackTrace();
                }

            }else{

                myProgress.dismiss();
                mPrefs.Geral.setError("Não foi possível conectar ao servidor no momento. Tente novamente em alguns minutos.");
                enviarErroDetalhe();

                res = false;

            }

            myFtp.ftpDisconnect();

            return res;
        }

        private void enviarErroDetalhe(){

            mAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((sonicEmpresa)myCtx).mensagemErroDetalhe(false);
                }
            });
        }

        private void enviarErro(String erro){

            mAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((sonicEmpresa)myCtx).mensagemErro(erro);
                }
            });
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            myProgress.setMessage(values[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            myProgress.dismiss();
        }

    }

}
