package com.softhex.sonic;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.telephony.TelephonyManager;

import java.io.File;

/**
 * Created by Administrador on 14/12/2017.
 */

public class sonicVerificarSite{

    private Context myCtx;
    private ProgressDialog myProgress;
    private sonicFtp myFtp;
    private sonicDialog myMessage;
    private sonicDatabaseLogCRUD DBCL;
    private sonicDatabaseCRUD DBC;
    private sonicSystem mySystem;
    private sonicPopularTabelas myPopTable;
    private sonicUtils myUtil;
    private TelephonyManager myPhonyManager;
    private Boolean appTest;

    public sonicVerificarSite(Context ctx){
        this.myCtx = ctx;
        this.myProgress = new ProgressDialog(myCtx);
        this.myFtp = new sonicFtp(myCtx);
        this.myMessage = new sonicDialog(myCtx);
        this.DBCL = new sonicDatabaseLogCRUD(myCtx);
        this.DBC = new sonicDatabaseCRUD(myCtx);
        this.mySystem = new sonicSystem(myCtx);
        this.myPopTable = new sonicPopularTabelas(myCtx);
        this.myUtil = new sonicUtils(myCtx);
        this.myPhonyManager = (TelephonyManager)myCtx.getSystemService(Context.TELEPHONY_SERVICE);
        this.appTest = false;
    }

    public Boolean validar(String site, Boolean test){

        if(test){
            sonicConstants.EMP_TESTE = true;
        }

        String fileName = "sites/"+ site +".TXT";
        String fileFull = sonicConstants.LOCAL_TEMP + site +".TXT";

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

                        sonicConstants.EMP_SITE = strings[0];
                        sonicConstants.EMP_EXIST = "1";
                        sonicConstants.DOWNLOAD_TYPE = "SITE";

                        File file = new File(Environment.getExternalStorageDirectory(), sonicConstants.LOCAL_TEMP + strings[0] + ".TXT");

                        if (file.exists()) {

                            publishProgress("Gravando dados...");

                            sonicConstants.DOWNLOAD_TYPE = "SITE";
                            new sonicPopularTabelas(myCtx).gravarDados(strings[0]+".TXT");

                            res = true;

                        }

                    }else{

                        File delete = new File(Environment.getExternalStorageDirectory(), strings[2]);
                        delete.delete();
                        sonicConstants.EMP_EXIST = "0";
                        myMessage.showMS("Atenção", "Empresa não encontrada.", myMessage.MSG_WARNING);
                        res = false;

                    }

                }
                catch (Exception e){
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                            e.getMessage(),
                            mySystem.System.getActivityName(),
                            mySystem.System.getClassName(el),
                            mySystem.System.getMethodNames(el));
                    e.printStackTrace();
                }


            }else{

                myMessage.showMS("Erro...", "Não foi possível conectar ao servidor no momento. Tente novmente em alguns minutos.", myMessage.MSG_WRONG);
                myProgress.dismiss();
                res = false;

            }

            myFtp.ftpDisconnect();

            return res;
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
