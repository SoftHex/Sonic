package com.softhex.sonic;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.CopyStreamAdapter;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;

/**
 * Created by Administrador on 26/07/2017.
 */

public class sonicFtp {

    private FTPClient ftpClient = new FTPClient();
    private Context myCtx;
    private ProgressDialog myProgress;
    private sonicDatabaseLogCRUD DBCL;
    private sonicDatabaseCRUD DBC;
    private sonicThrowMessage myMessage;
    private sonicSystem mySystem;
    private sonicPopularTabelas myPopTable;

    public sonicFtp(Context ctx){
        this.myCtx = ctx;
        this.myProgress = new ProgressDialog(myCtx);
        this.DBCL = new sonicDatabaseLogCRUD(myCtx);
        this.DBC = new sonicDatabaseCRUD(myCtx);
        this.myMessage = new sonicThrowMessage(myCtx);
        this.mySystem = new sonicSystem(myCtx);
        this.myPopTable = new sonicPopularTabelas(myCtx);
    }

    public Boolean ftpConnect(){

        StackTraceElement el = Thread.currentThread().getStackTrace()[2];
        Boolean result = false;
        String server, user ,pass;

        if(DBC.Usuarios.usuarioAtivo()){
            server = DBC.Ftp.ftp();
            user = DBC.Ftp.user();
            pass = DBC.Ftp.pass();

        }else{
            server = sonicConstants.FTP_SERVER;
            user = sonicConstants.FTP_USER;
            pass = sonicConstants.FTP_PASS;
        }

        try{

            ftpClient.setConnectTimeout(10*1000);
            ftpClient.connect(server, 21);

            try{

                FTPReply.isPositiveCompletion(ftpClient.getReplyCode());

                try{

                    result = ftpClient.login(user, pass);
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                    ftpClient.enterLocalActiveMode();

                }catch (IOException e){
                    e.printStackTrace();
                    DBCL.logerro.saveLogErro(e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                }

            }catch (Exception e){
                e.printStackTrace();
                DBCL.logerro.saveLogErro(e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
            }

        } catch (IOException e) {
            e.printStackTrace();
            DBCL.logerro.saveLogErro(e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
        }

        return result;
    }

    public boolean ftpDisconnect(){

        StackTraceElement el = Thread.currentThread().getStackTrace()[2];

        try{

            ftpClient.logout();
            ftpClient.disconnect();

        }catch (Exception e){
            e.printStackTrace();
            DBCL.logerro.saveLogErro(e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
        }

        return false;
    }

    public Boolean downloadFile(final String fileName, String localFile) {

        StackTraceElement el = Thread.currentThread().getStackTrace()[2];
        Boolean result = false;

        try {

            File destino = new File(Environment.getExternalStorageDirectory()+localFile);
            FileOutputStream file = new FileOutputStream(destino);

            if(ftpClient.retrieveFile(fileName, file)){
                destino.createNewFile();
                CopyStreamAdapter streamListener = new CopyStreamAdapter() {

                    @Override
                    public void bytesTransferred(long totalBytesTransferred, int bytesTransferred, long streamSize) {
                        //this method will be called everytime some bytes are transferred

                        //int percent = (int)(totalBytesTransferred*100);
                        //Log.d("FILE", totalBytesTransferred+"");
                        //Log.d("FILE_TOTAL", files2[0].getSize()+"");
                    }

                };
                ftpClient.setCopyStreamListener(streamListener);
                result = true;
            }

            file.close();

        }catch (Exception e){
            e.printStackTrace();
            DBCL.logerro.saveLogErro(e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
            return false;

        }

        return  result;
    }

    public void downloadFile2(String fileName, String localFile) {

        myProgress = new ProgressDialog(myCtx);
        myProgress.setCancelable(false);
        myProgress.setMessage("Conectando...");
        myProgress.setProgressStyle(0);
        myProgress.setMax(1);
        myProgress.show();
        new myAsyncTaskDownload().execute(fileName, localFile);

    }

    public class myAsyncTaskDownload extends AsyncTask<String, String, Boolean>{
        @Override
        protected Boolean doInBackground(String... strings) {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean res = false;

            if(ftpConnect()){

                publishProgress("Baixando arquivos...");


                try{

                    FTPFile f = ftpClient.mlistFile(strings[0]);
                    Long size = f.getSize();
                    final Long percent = size/1024;
                    String sufix;

                    /*CopyStreamAdapter streamListener = new CopyStreamAdapter() {

                        int total;

                        @Override
                        public void bytesTransferred(long totalBytesTransferred, int bytesTransferred, long streamSize) {


                           total+=bytesTransferred;

                                Log.d("TOTAL", (float)(total/1024)+" MB");


                            //publishProgress((totalBytesTransferred/1024)+" MB");
                            //Log.d("TOTAL", (totalBytesTransferred/1024)+" MB");

                        }

                    };*/

                    File destino = new File(Environment.getExternalStorageDirectory()+strings[1]);
                    FileOutputStream file = new FileOutputStream(destino);

                    //ftpClient.setCopyStreamListener(streamListener);
                    if(ftpClient.retrieveFile(strings[0], file)){

                        destino.createNewFile();

                        publishProgress("Gravando dados...");

                        String arquivo = strings[0].substring(strings[0].lastIndexOf("/")+1, strings[0].length());

                        myProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        myProgress.setIndeterminate(true);
                        myProgress.setMax(100);
                        myPopTable.gravarDados(arquivo);

                       res = true;
                    }

                    file.close();


                }catch (IOException e){
                    e.printStackTrace();
                    DBCL.logerro.saveLogErro(e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                    return false;
                }

            } else {

                myMessage.showMessage("Erro", "Não foi possível conectar ao servidor no momento.", myMessage.MSG_WRONG);
                this.cancel(true);
                myProgress.dismiss();
                return false;
            }

            ftpDisconnect();
            return res;

        }

        @Override
        protected void onProgressUpdate(String... strings) {
            super.onProgressUpdate(strings);
            myProgress.setMessage(String.valueOf(strings[0]));
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            myProgress.dismiss();

            if(aBoolean){

            }

        }
    }

}
