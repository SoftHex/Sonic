package com.softhex.sonic;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import java.io.File;
import java.util.List;

/**
 * Created by Administrador on 14/12/2017.
 */

public class sonicVerificarSite{

    private Context myCtx;
    private ProgressDialog myProgress;
    private sonicFtp myFtp;
    private sonicThrowMessage myMessage;
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
        this.myMessage = new sonicThrowMessage(myCtx);
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
            appTest = true;
        }

        String fileName = "SITES/"+ site +".TXT";
        String fileFull = sonicConstants.LOCAL_TMP + site +".TXT";

        myProgress = new ProgressDialog(myCtx);
        myProgress.setCancelable(false);
        myProgress.setMessage("Conectando...");
        myProgress.setMax(1);
        myProgress.setProgressStyle(0);
        myProgress.show();

        new myAsyncTaskVerificar().execute(site, fileName, fileFull);

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

                        File file = new File(Environment.getExternalStorageDirectory(), sonicConstants.LOCAL_TMP + strings[0] + ".TXT");

                        if (file.exists()) {

                            publishProgress("Gravando dados...");

                            if(myPopTable.gravarDados(strings[0]+".TXT")){

                                myUtil.Arquivo.deleteFile(sonicConstants.LOCAL_TMP + strings[0] + ".TXT");

                                res = true;

                            }
                            else
                            {
                                myMessage.showMessage("Atenção", "Não foi possível gravar os dados nas tabelas. Não há nenhuma seção obrigatória disponível.", myMessage.MSG_WARNING);
                                res = false;

                            }

                        }

                    }else{

                        File delete = new File(Environment.getExternalStorageDirectory(), strings[2]);
                        delete.delete();
                        sonicConstants.EMP_EXIST = "0";
                        myMessage.showMessage("Atenção", "Empresa não encontrada.", myMessage.MSG_WARNING);
                        res = false;

                    }

                }
                catch (Exception e){
                    e.printStackTrace();
                    DBCL.logerro.saveLogErro(e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                    res = false;
                }


            }else{

                myMessage.showMessage("Erro", "Não foi possível conectar ao servidor no momento.", myMessage.MSG_WRONG);
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
            if(aBoolean){

                if (ActivityCompat.checkSelfPermission(myCtx, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED){


                    if(DBC.Database.checkMinimumData()){


                        String imei="";

                        if (android.os.Build.VERSION.SDK_INT >= 26) {
                            imei = myPhonyManager.getImei();
                        }
                        else
                        {
                            imei = myPhonyManager.getDeviceId();
                        }

                        if(appTest){
                            imei = "123456789";
                            sonicConstants.EMP_TESTE = true;
                        }

                        sonicConstants.USER_IMEI = imei;

                        List<sonicUsuariosHolder> lista;
                        lista = DBC.Usuarios.selectUsuarioImei(imei);
                        if(!lista.isEmpty()){

                            startActivity(DBC.Empresa.empresaPadrao(), lista.get(0).getCodigo() ,lista.get(0).getNome(), lista.get(0).getCargo(), imei);

                        }else{
                            myMessage.showMessage("Atenção", "Seu aparelho não esta cadastrado para "+DBC.Empresa.empresaPadrao()+". Verifique se seu imei ("+imei+") foi cadastrado corretamente pelo administrador.", myMessage.MSG_WARNING);

                        }

                    }else{

                        myMessage.showMessage("Atenção", "Não foi possível gravar os dados nas tabelas. O arquivo solicitado parece não conter dados suficientes ou está mal formatado.", myMessage.MSG_WARNING);

                    }

                }else{
                    ActivityCompat.requestPermissions(((Activity) myCtx),
                            new String[]{Manifest.permission.READ_PHONE_STATE},
                            1);

                }

            }

        }
    }

    public void startActivity(String empresa, int id, String usuario, String cargo, String imei){

        Intent i = new Intent(myCtx, sonicFirstAccess.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("VENDEDOR_ID", id);
        i.putExtra("USUARIO", usuario);
        i.putExtra("CARGO", cargo);
        i.putExtra("EMPRESA", empresa);
        i.putExtra("IMEI", imei);
        myCtx.startActivity(i);
        ((Activity) myCtx).finish();
    }

}
