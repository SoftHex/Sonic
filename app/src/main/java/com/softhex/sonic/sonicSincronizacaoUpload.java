package com.softhex.sonic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/**
 * Created by Administrador on 07/07/2017.
 */

public class sonicSincronizacaoUpload extends Fragment {

    private View myView;
    private ProgressDialog myProgress;
    private sonicFtp ftp;
    private sonicDatabaseCRUD DBC;
    private String file, ftpFile, tipo, msg;
    private sonicPopularTabelas popularTabelas;
    private sonicUtils util;
    private sonicConstants myCons;
    private GridView myGridView;
    private Context mContext;
    private Activity mAct;
    List<sonicUsuariosHolder> list;

    int[] gridViewString = {
            //R.string.pedidos, R.string.reenvio, R.string.clientes
    } ;
    int[] gridViewImageId = {
            //R.drawable.pedidos, R.drawable.reenvio, R.drawable.clientes
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.sonic_recycler_layout_list, container, false);

        mContext = getActivity();
        //ftp = new sonicFtp(mContext);
       // mAct =
        DBC = new sonicDatabaseCRUD(mContext);
        util = new sonicUtils(mContext);
        //popularTabelas = new sonicPopularTabelas(mContext);
        myCons = new sonicConstants();
        list = DBC.Usuario.selectUsuarioAtivo();

        //sonicSincronizacaoFragUploadAdapter adapterViewAndroid = new sonicSincronizacaoFragUploadAdapter(getContext(), gridViewString, gridViewImageId);
        //myGridView = (GridView)myView.findViewById(R.id.gridview);
        //myGridView.setAdapter(adapterViewAndroid);

        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                myProgress(i);
            }
        });

        return myView;

    }

    public void myProgress(int id){

        if(new sonicUtils(mContext).Feedback.statusNetwork()){

            myProgress = new ProgressDialog(getContext());
            myProgress.setCancelable(false);
            //apagarLogs.setTitle("Sincronização");
            myProgress.setMessage("Conectando...");
            myProgress.setMax(1);
            myProgress.setProgressStyle(0);
            myProgress.show();

            myAsyncTask m = new myAsyncTask();
            m.execute(0, id);

        }else{

            Snackbar.make(myView,"Verifique a conexao com a internet.",Snackbar.LENGTH_LONG).show();
        }
    }

    public class myAsyncTask extends AsyncTask<Integer, String, String>{

        @Override
        protected String doInBackground(Integer... integers) {

            switch (integers[1]){
                case 0:
                    tipo = "Gravando dados...";
                    file = String.format("%5s",list.get(0).getCodigo()).replace(" ", "0")+".TXT";
                    ftpFile = sonicConstants.FTP_USUARIOS+file;
                    msg = "Dados gravados com sucesso!";
                    break;
                case 1:
                    tipo = "Salvando imagens...";
                    file = "CATALOGO.zip";
                    ftpFile = sonicConstants.FTP_IMAGENS+file;
                    msg = "Imagens salvas!";
                    break;
                case 2:
                    tipo = "Salvando imagens...";
                    file = "LOJAS.zip";
                    ftpFile = sonicConstants.FTP_IMAGENS+file;
                    msg = "Imagens salvas!";
                    break;
                case 3:
                    tipo = "Atualizando Estoque...";
                    ftpFile = sonicConstants.FTP_ESTOQUE;
                    msg = "Estoque atualizado!";
                    break;
                case 4:
                    tipo = "Atualizando localizações...";
                    ftpFile = sonicConstants.FTP_LOCALIZACAO;
                    msg = "Localizações salvas!";
                    break;

            }

            try{

                ftp.ftpConnect();

                try{

                    this.publishProgress("Baixando arquivos...",String.valueOf(integers[1]));
                    // FROM FTP TO LOCAL TEMP
                    ftp.downloadFile(ftpFile, sonicConstants.LOCAL_TEMP+file);

                    if(integers[1]==1 || integers[1]==2){
                        util.Arquivo.unzipFile(String.format(file).replace(".zip",""));
                    }

                }catch (Exception e){
                    this.cancel(true);
                    mensagem(e.getMessage());
                }

                try{

                    this.publishProgress(tipo,String.valueOf(integers[1]));
                    popularTabelas.gravarDados(file);
                    myCons.REFRESH_HOME = true;

                }catch (Exception e){
                    this.cancel(true);
                    mensagem(e.getMessage());
                }

            }catch (Exception e){

                e.printStackTrace();
                myProgress.cancel();
                mensagem(e.getMessage());

            }

            ftp.ftpDisconnect();
            //util.Arquivo.moveFile(sonicConstants.LOCAL_TMP+file,sonicConstants.LOCAL_DATA+file);
            this.publishProgress("1", msg);

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            String message = values[0];

            myProgress.setMessage(message);
            if(message.equals("1")){
                myProgress.cancel();
                mensagem(values[1]);

            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    };

    public void mensagem(String msg){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }

}


