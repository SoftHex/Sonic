package com.softhex.sonic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.CopyStreamAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrador on 26/07/2017.
 */

public class sonicFtp {

    private FTPClient ftpClient = new FTPClient();
    private Activity mAct;
    private Context myCtx;
    private ProgressDialog myProgress;
    private ProgressDialog myProgress2;
    private sonicDatabaseLogCRUD DBCL;
    private sonicDatabaseCRUD DBC;
    private sonicDialog myMessage;
    private sonicSystem mySystem;
    private sonicPopularTabelas myPopTable;
    private String arquivo;
    private sonicPreferences mPrefs;
    private String mPath;

    public sonicFtp(Activity act){
        this.myCtx = act;
        this.mAct = act;
        this.mPrefs = new sonicPreferences(act);
        this.DBCL = new sonicDatabaseLogCRUD(myCtx);
        this.DBC = new sonicDatabaseCRUD(myCtx);
        this.myMessage = new sonicDialog(myCtx);
        this.mySystem = new sonicSystem(myCtx);
        this.myPopTable = new sonicPopularTabelas(act);
        this.myProgress2 = new ProgressDialog(myCtx);
    }

    public Boolean ftpConnect(){

        StackTraceElement el = Thread.currentThread().getStackTrace()[2];
        Boolean result = false;

        try{

            ftpClient.setDataTimeout(10*1000);
            ftpClient.connect((mPrefs.Users.getAtivo() || mPrefs.Ftp.getEnderecoAlternativo()) ? mPrefs.Ftp.getEndereco() : sonicConstants.FTP_SERVER, 21);

            try{

                FTPReply.isPositiveCompletion(ftpClient.getReplyCode());

                try{

                    result = mPrefs.Users.getAtivo() ? ftpClient.login(mPrefs.Ftp.getUsuario(), mPrefs.Ftp.getSenha()) : ftpClient.login(sonicConstants.FTP_USER, sonicConstants.FTP_PASS);
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                    ftpClient.enterLocalActiveMode();

                }catch (IOException e){
                    mPrefs.Geral.setError(e.getMessage());
                    e.printStackTrace();
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                }

            }catch (Exception e){
                mPrefs.Geral.setError(e.getMessage());
                e.printStackTrace();
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
            }

        } catch (ConnectException | NoRouteToHostException e) {
            mPrefs.Geral.setError(e.getMessage());
            e.printStackTrace();
            DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
        }catch (IOException e){
            mPrefs.Geral.setError(e.getMessage());
            e.printStackTrace();
            DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
        }

        return result;
    }

    public Boolean ftpConnect(String endereco){

        StackTraceElement el = Thread.currentThread().getStackTrace()[2];
        Boolean result = false;

        try{

            ftpClient.setDataTimeout(10*500);
            ftpClient.connect(endereco, 21);

            try{

                FTPReply.isPositiveCompletion(ftpClient.getReplyCode());

                try{

                    result = ftpClient.login(sonicConstants.FTP_USER, sonicConstants.FTP_PASS);
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                    ftpClient.enterLocalActiveMode();

                }catch (IOException e){
                    mPrefs.Geral.setError(e.getMessage());
                    e.printStackTrace();
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                }

            }catch (Exception e){
                mPrefs.Geral.setError(e.getMessage());
                e.printStackTrace();
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
            }

        } catch (ConnectException e) {
            mPrefs.Geral.setError(e.getMessage());
            e.printStackTrace();
            DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
        }catch (IOException e){
            mPrefs.Geral.setError(e.getMessage());
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

        }catch (IOException e){
            mPrefs.Geral.setError(e.getMessage());
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
        Boolean result;

        try {

            File destino = new File(Environment.getExternalStorageDirectory()+localFile);
            FileOutputStream file = new FileOutputStream(destino);

            if(ftpClient.retrieveFile(fileName, file)){

                try{
                    destino.createNewFile();
                    return true;
                }catch (FileNotFoundException e){
                    mPrefs.Geral.setError(e.getMessage());
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
            mPrefs.Geral.setError(e.getMessage());
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

    public String downloadFile2(String fileName, String localFile) {

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                myProgress = new ProgressDialog(myCtx, R.style.ProgressTheme);
                myProgress.setCancelable(false);
                myProgress.setMessage("Conectando...");
                myProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                myProgress.show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new myAsyncTaskDownload().execute(fileName, localFile);
                        }
                    }, sonicUtils.Randomizer.generate(500,1000));

            }
        });

        return fileName.substring(fileName.lastIndexOf("/")+1, fileName.length());
    }

    public class myAsyncTaskDownload extends AsyncTask<String, String, Boolean>{
        @Override
        public Boolean doInBackground(String... strings) {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            boolean res = true;

                if(sonicUtils.internetConnectionAvailable(3000)){

                    if(ftpConnect()){

                        myProgress.dismiss();

                        new Handler(Looper.getMainLooper()).post(()-> {

                            myProgress2.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            myProgress2.setMessage("Fazendo download...");
                            myProgress2.setProgress(0);
                            myProgress2.setCancelable(false);
                            myProgress2.show();

                        });

                        long fileSize = sonicUtils.getFileSize(ftpClient,strings[0]);

                        myProgress2.setMax((int)fileSize);

                        myProgress2.setProgressNumberFormat("");


                        CopyStreamAdapter sa = new CopyStreamAdapter(){

                            @Override
                            public void bytesTransferred(long l, int i, long l1) {

                                myProgress2.setProgressNumberFormat((sonicUtils.bytes2String(fileSize)));
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

                                return true;

                            //}else{
                            //    new sonicDialog(myCtx).showMI(R.string.headerWarning, R.string.fileNotFound, sonicDialog.MSG_WARNING);
                            //    res =  false;
                            }

                            file.close();


                        }catch (IOException e){
                            mPrefs.Geral.setError(e.getMessage());
                            e.printStackTrace();
                            DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                                    e.getMessage(),
                                    mySystem.System.getActivityName(),
                                    mySystem.System.getClassName(el),
                                    mySystem.System.getMethodNames(el));
                            res = false;
                        }

                    } else {

                        mPrefs.Geral.setError(myCtx.getResources().getString(R.string.failToConnect));
                        return false;
                    }

                }else{
                    mPrefs.Geral.setError(myCtx.getResources().getString(R.string.networkError));
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
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            myProgress2.dismiss();

            if(aBoolean){

                switch (mPrefs.Sincronizacao.getDownloadType()){
                    case "DADOS":
                        new sonicPopularTabelas(mAct).gravarDados(arquivo);
                        break;
                    case "CATALOGO":
                        new sonicUtils(mAct).Arquivo.unzipFile(arquivo);
                        new sonicDatabaseCRUD(myCtx).Sincronizacao.saveSincronizacao(sonicConstants.TB_PRODUTO, sonicConstants.TIPO_SINC_IMAGENS);
                        break;
                    case "CLIENTES":
                        new sonicUtils(mAct).Arquivo.unzipFile(arquivo);
                        new sonicDatabaseCRUD(myCtx).Sincronizacao.saveSincronizacao(sonicConstants.TB_CLIENTE, sonicConstants.TIPO_SINC_IMAGENS);
                        break;
                    case "ESTOQUE":
                        new sonicPopularTabelas(mAct).gravarDados(arquivo);
                        break;

                }

            }else{
                exibirMensagemErro();
            }

        }

    }

    public void downloadImages(String path, String file) {

        mPath = path;

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                myProgress = new ProgressDialog(myCtx);
                myProgress.setCancelable(false);
                myProgress.setMessage("Conectando...");
                myProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                myProgress.show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new mAsyncTaskDownloadImages().execute(file);
                    }
                }, sonicUtils.Randomizer.generate(500,1000));

            }
        });
    }

    class mAsyncTaskDownloadImages extends AsyncTask<String, String, Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            boolean res = false;

            if(sonicUtils.internetConnectionAvailable(3000)){

                if(ftpConnect()){

                    myProgress.dismiss();

                    new Handler(Looper.getMainLooper()).post(()-> {

                        myProgress2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        myProgress2.setIndeterminate(true);
                        myProgress2.setMessage("Fazendo download...");
                        myProgress2.setCancelable(false);
                        myProgress2.show();

                    });

                    try{

                        List<String> l = new ArrayList<>();
                        l.add(strings[0]+".jpg");
                        l.add(strings[0]+".png");
                        for(int i=1; i<sonicConstants.TOTAL_IMAGES_SLIDE; i++){
                            l.add(strings[0]+"_"+i+".jpg");
                            l.add(strings[0]+"_"+i+".png");
                        }

                        for (int i=0; i < l.size(); i++){
                            InputStream inputStream = ftpClient.retrieveFileStream(sonicConstants.FTP_IMAGENS + l.get(i));
                            int returnCod = ftpClient.getReplyCode();
                            if(!(inputStream == null || returnCod == 550)){
                                ftpClient.completePendingCommand();
                                byte[] buffer = new byte[inputStream.available()];
                                inputStream.read(buffer);
                                File destino = new File(Environment.getExternalStorageDirectory()+mPath+l.get(i));
                                OutputStream out = new FileOutputStream(destino);
                                out.write(buffer);
                                out.close();
                                res = true;
                            }
                        }

                    }catch (Exception e){
                        mPrefs.Geral.setError(e.getMessage());
                        e.printStackTrace();
                        DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                                e.getMessage(),
                                mySystem.System.getActivityName(),
                                mySystem.System.getClassName(el),
                                mySystem.System.getMethodNames(el));
                        res = false;
                    }

                } else {

                    mPrefs.Geral.setError(myCtx.getResources().getString(R.string.failToConnect));
                    return false;
                }

            }else{
                mPrefs.Geral.setError(myCtx.getResources().getString(R.string.networkError));
                return false;
            }

            ftpDisconnect();
            return res;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            myProgress2.dismiss();
            if(aBoolean){
                exibirMensagemOK();
            }else{
                exibirMensagemErro();
            }
        }
    }


    private void exibirMensagemErro(){

        myProgress.dismiss();

        mAct.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (mPrefs.Sincronizacao.getCalledActivity()){
                    case "sonicClientes":
                        ((sonicClientes)mAct).mensagemErro();
                        break;
                    case "sonicProdutos":
                        ((sonicProdutos)mAct).mensagemErro();
                        break;
                    case "sonicMain":
                        ((sonicMain)mAct).mensagemErro();
                        break;
                    case "sonicRota":
                        ((sonicRota)mAct).refreshFragments();
                        break;
                }
            }
        });
    }

    private void exibirMensagemOK(){
        switch (mPrefs.Sincronizacao.getCalledActivity()){
            case "sonicMain":
                ((sonicMain)mAct).mensagemErro();
                break;
            case "sonicClientes":
                ((sonicClientes)mAct).mensagemErro();
                break;
        }
    }

}
