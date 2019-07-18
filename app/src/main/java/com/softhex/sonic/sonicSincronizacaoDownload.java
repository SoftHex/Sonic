package com.softhex.sonic;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

/**
 * Created by Administrador on 07/07/2017.
 */

public class sonicSincronizacaoDownload extends Fragment{

    private View myView;
    private ProgressDialog myProgress;
    private sonicFtp myFtp;
    private sonicDatabaseCRUD DBC;
    private sonicDatabaseLogCRUD DBCL;
    private String file, ftpFile, tipo, msg;
    private sonicPopularTabelas popularTabelas;
    private sonicUtils myUtil;
    private sonicConstants myCons;
    private GridView myGridView;
    private Context _this;
    private Boolean myError = false;
    List<sonicUsuariosHolder> list;

    int[] gridViewString = {
            //R.string.dados, R.string.catalogo, R.string.lojas, R.string.estoque, R.string.lugar
    } ;
    int[] gridViewImageId = {
            //R.drawable.dados, R.drawable.catalogo, R.drawable.lojas, R.drawable.estoque, R.drawable.lugar
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.sonic_recycler_layout_list, container, false);

        _this = getActivity();
        DBC = new sonicDatabaseCRUD(_this);
        myUtil = new sonicUtils(_this);
        popularTabelas = new sonicPopularTabelas(_this);
        myCons = new sonicConstants();
        list = DBC.Usuarios.selectUsuarioAtivo();


        //sonicSincronizacaoUploadAdapter adapterViewAndroid = new sonicSincronizacaoUploadAdapter(getContext(), gridViewString, gridViewImageId);
        //myGridView = (GridView)myView.findViewById(R.id.gridview);
        //myGridView.setAdapter(adapterViewAndroid);

        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i){
                    case 0:
                        String file = String.format("%5s",list.get(0).getCodigo()).replace(" ", "0")+".TXT";
                        myFtp.downloadFile(sonicConstants.FTP_USUARIOS+file, sonicConstants.LOCAL_TMP+file);
                        break;
                    case 1:
                        file = "CATALOGO.zip";
                        ftpFile = sonicConstants.FTP_IMAGENS+file;
                        myFtp.downloadFile(sonicConstants.FTP_IMAGENS +"CATALOGO.zip", sonicConstants.LOCAL_TMP+file);
                        myUtil.Arquivo.unzipFile(String.format(file));
                }

            }
        });

        return myView;

    }

    public void myProgress(int id){

        if(myUtil.Feedback.statusNetwork()){

            myProgress = new ProgressDialog(getContext());
            myProgress.setCancelable(false);
            myProgress.setMessage("Conectando...");
            myProgress.setMax(1);
            myProgress.setProgressStyle(0);
            myProgress.show();

            new myAsyncTask().execute(0,id);

        }else{

               new sonicThrowMessage(_this).showSnackBar(myView,"Verifique a conexao com a internet." );

        }
    }

    public class myAsyncTask extends AsyncTask<Integer, String, String>{

        @Override
        protected String doInBackground(Integer... integers) {

            String result = "";

            if(new sonicFtp(_this).ftpConnect()){

                this.publishProgress("Baixando arquivos...",String.valueOf(integers[1]));
                //Boolean res = myFtp.downloadFile2(ftpFile, sonicConstants.LOCAL_TMP+file);

                switch (integers[1]){
                    case 0:
                        file = String.format("%5s",list.get(0).getCodigo()).replace(" ", "0")+".TXT";
                        ftpFile = sonicConstants.FTP_USUARIOS+file;

                        Boolean d = true;//myFtp.downloadFile2(ftpFile, sonicConstants.LOCAL_TMP+file);
                        Boolean g = true;//popularTabelas.gravarDados(file);
                        //File f = new File(Environment.getExternalStorageDirectory(),sonicConstants.LOCAL_TMP+file);
                        if(d){
                            this.publishProgress("Gravando dados...",String.valueOf(integers[1]));
                            if(g){
                                result = "Dados gravados com sucesso!";
                                //myCons.REFRESH_HOME = true;
                            }else{
                                myError = true;
                                this.cancel(true);
                                result = "Não foi possível gravar dados nas tabelas.";
                            }
                        }else{
                            myError = true;
                            this.cancel(true);
                            result = "Arquivo não encontrado.";
                        }

                        break;

                    case 1:
                        file = "CATALOGO.zip";
                        ftpFile = sonicConstants.FTP_IMAGENS+file;

                        Boolean d1 = true;//myFtp.downloadFile2(ftpFile, sonicConstants.LOCAL_TMP+file);
                        Boolean u = true;//myUtil.Arquivo.unzipFile(String.format(file));

                        if(d1){
                            this.publishProgress("Salvando imagens...",String.valueOf(integers[1]));
                            if(u){
                                result = "Imagens salvas com sucesso!";
                                //myCons.REFRESH_HOME = true;
                            }else{
                                myError = true;
                                this.cancel(true);
                                result = "Não foi possível salvar as imagens.";
                            }
                        }else{
                            myError = true;
                            this.cancel(true);
                            result = "Não foi possível fazer o download das imagens.";
                        }
                        break;

                    case 2:
                        file = String.format("%5s",list.get(0).getCodigo()).replace(" ", "0")+".zip";
                        ftpFile = sonicConstants.FTP_IMAGENS+file;
                        Boolean d2 = true;//myFtp.downloadFile2(ftpFile, sonicConstants.LOCAL_TMP+file);
                        Boolean u2 = true;//myUtil.Arquivo.unzipFile(String.format(sonicConstants.LOCAL_IMG_CATALOGO+file).replace(".zip",""));

                        if(d2){
                            this.publishProgress("Salvando imagens...",String.valueOf(integers[1]));
                            if(u2){
                                result = "Imagens salvas com sucesso!";
                                //myCons.REFRESH_HOME = true;
                            }else{
                                myError = true;
                                this.cancel(true);
                                result = "Não foi possível salvar as imagens.";
                            }
                        }else{
                            myError = true;
                            this.cancel(true);
                            result = "Não foi possível fazer o download das imagens.";
                        }
                        break;

                    }

                }

            else {
                myError = true;
                this.cancel(true);
                result = "Não foi se conectar ao servidor";

            }

            myFtp.ftpDisconnect();
            return result;

        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String status = values[0];
            myProgress.setMessage(status);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            myProgress.dismiss();
            if(myError){
                new sonicThrowMessage(_this).showMessage("Erro",s,sonicThrowMessage.MSG_WRONG);
            }else{
                DBC.Sincronizacao.saveSincronizacao("","",_this.getClass().getSimpleName(),"");
                new sonicThrowMessage(_this).showMessage("Sincronizado",s,sonicThrowMessage.MSG_SUCCESS);
            }
        }
    };

}


