package com.softhex.sonic;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.tv.TvContract;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.CopyStreamAdapter;
import org.apache.commons.net.io.CopyStreamEvent;
import org.apache.commons.net.io.CopyStreamListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Administrador on 26/07/2017.
 */

public class sonicFtp {

    private FTPClient ftpClient = new FTPClient();
    private Context myCtx;
    private ProgressDialog myProgress;
    private ProgressDialog myProgress2;
    private sonicDatabaseLogCRUD DBCL;
    private sonicDatabaseCRUD DBC;
    private sonicThrowMessage myMessage;
    private sonicSystem mySystem;
    private sonicPopularTabelas myPopTable;
    private String arquivo;

    public sonicFtp(Context ctx){
        this.myCtx = ctx;
        this.DBCL = new sonicDatabaseLogCRUD(myCtx);
        this.DBC = new sonicDatabaseCRUD(myCtx);
        this.myMessage = new sonicThrowMessage(myCtx);
        this.mySystem = new sonicSystem(myCtx);
        this.myPopTable = new sonicPopularTabelas(myCtx);
        this.myProgress2 = new ProgressDialog(myCtx);
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

            ftpClient.setDataTimeout(10*1000);
            ftpClient.connect(server, 21);

            try{

                FTPReply.isPositiveCompletion(ftpClient.getReplyCode());

                try{

                    result = ftpClient.login(user, pass);
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                    ftpClient.enterLocalActiveMode();

                }catch (IOException e){
                    e.printStackTrace();
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                }

            }catch (Exception e){
                e.printStackTrace();
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
            }

        } catch (IOException e) {
            e.printStackTrace();
            DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
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
            DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                    e.getMessage(),
                    mySystem.System.getActivityName(),
                    mySystem.System.getClassName(el),
                    mySystem.System.getMethodNames(el));
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

                try{
                    destino.createNewFile();
                    return true;
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                            e.getMessage(),
                            mySystem.System.getActivityName(),
                            mySystem.System.getClassName(el),
                            mySystem.System.getMethodNames(el));
                    return false;
                }


            }else{
                file.close();
                result =  false;
            }


        }catch (Exception e){
            e.printStackTrace();
            DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                    e.getMessage(),
                    mySystem.System.getActivityName(),
                    mySystem.System.getClassName(el),
                    mySystem.System.getMethodNames(el));
            return false;

        }

        return  result;
    }

    public void downloadFile2(String fileName, String localFile) {

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                myProgress = new ProgressDialog(myCtx);
                myProgress.setCancelable(false);
                myProgress.setMessage("Conectando...");
                myProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                myProgress.show();
                new myAsyncTaskDownload().execute(fileName, localFile);


            }
        });

    }

    public class myAsyncTaskDownload extends AsyncTask<String, String, Boolean>{
        @Override
        public Boolean doInBackground(String... strings) {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean res = false;

            if(ftpConnect()){

                myProgress.dismiss();

                new Handler(Looper.getMainLooper()).post(()-> {

                    myProgress2.setCancelable(false);
                    myProgress2.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    myProgress2.setMessage("Fazendo download...");
                    myProgress2.setProgress(0);
                    myProgress2.show();

                });

                int fileSize = sonicUtils.getFileSize(ftpClient,strings[0]);

                myProgress2.setMax(fileSize);

                CopyStreamAdapter sa = new CopyStreamAdapter(){

                    @Override
                    public void bytesTransferred(long l, int i, long l1) {

                            if((l%(1024*2))==0)
                            myProgress2.setProgressNumberFormat((sonicUtils.bytes2String(l)) + " / " + (sonicUtils.bytes2String(fileSize)));
                            publishProgress("Fazendo download...",String.valueOf(l));

                    }

                };

                ftpClient.setCopyStreamListener(sa);

                try{

                    File destino = new File(Environment.getExternalStorageDirectory()+strings[1]);
                    FileOutputStream file = new FileOutputStream(destino);

                    if(ftpClient.retrieveFile(strings[0], file)){

                        destino.createNewFile();

                        arquivo = strings[0].substring(strings[0].lastIndexOf("/")+1, strings[0].length());

                       res = true;
                    }else{
                        new sonicThrowMessage(myCtx).showMessage("Ops,","Arquivo não encontrado. Verifique com o administrador do sistema.", sonicThrowMessage.MSG_WARNING);
                    }

                    file.close();


                }catch (IOException e){
                    e.printStackTrace();
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                            e.getMessage(),
                            mySystem.System.getActivityName(),
                            mySystem.System.getClassName(el),
                            mySystem.System.getMethodNames(el));
                    return false;
                }

            } else {

                new sonicThrowMessage(myCtx).showMessage("Erro", "Não foi possível conectar ao servidor no momento.", myMessage.MSG_WRONG);
                this.cancel(true);
                return false;
            }

            ftpDisconnect();
            return res;

        }

        @Override
        protected void onProgressUpdate(String... strings) {
            super.onProgressUpdate(strings);
            myProgress2.setMessage(String.valueOf(strings[0]));
            myProgress2.setProgress(Integer.parseInt(strings[1]));
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            myProgress2.dismiss();

            if(aBoolean){

                switch (sonicConstants.DOWNLOAD_TYPE){
                    case "DADOS":
                        new sonicPopularTabelas(myCtx).gravarDados(arquivo);
                        Log.d("ARQUIVO", arquivo);
                        break;
                    case "CATALOGO":
                        new sonicUtils(myCtx).Arquivo.unzipFile(arquivo);
                        Log.d("ARQUIVO", arquivo);
                        new sonicDatabaseCRUD(myCtx).Sincronizacao.saveSincronizacao("imagens","catalogo");
                        break;
                    case "CLIENTES":
                        new sonicUtils(myCtx).Arquivo.unzipFile(arquivo);
                        Log.d("ARQUIVO", arquivo);
                        new sonicDatabaseCRUD(myCtx).Sincronizacao.saveSincronizacao("imagens","clientes");
                        break;

                }

            }

        }
    }

}
