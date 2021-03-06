package com.softhex.sonic;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.softhex.materialdialog.PromptDialog;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Administrador on 04/08/2017.
 */

public class sonicPopularTabelas {

    private Context mContext;
    private Activity mAct;
    private sonicPreferences mPref;
    private sonicDatabaseLogCRUD DBCL;
    private sonicDatabaseCRUD DBC;
    private sonicSystem mySystem;
    private ProgressDialog myProgress;
    private String arquivo;
    private List<String> mTablesSinc = new ArrayList<>();
    private String[][] mTables = {

            { "SITE", sonicConstants.TB_SITE, "Site", "save"},
            { "FTP", sonicConstants.TB_FTP, "Ftp" ,"save" },
            { "EMPRESAS", sonicConstants.TB_EMPRESAS, "Empresas", "save" },
            { "NIVEL_ACESSO", sonicConstants.TB_NIVEL_ACESSO, "Nível de Acesso", "save" },
            { "USUARIOS", sonicConstants.TB_USUARIOS, "Usuários" ,"save" },
            { "EMPRESAS_USUARIOS", sonicConstants.TB_EMPRESAS_USUARIOS, "Usuários por Empresa", "save" },
            { "MATRIZ", sonicConstants.TB_GRUPO_EMPRESAS, "Grupo Empresas", "save"},
            { "CLIENTES", sonicConstants.TB_CLIENTES, "Clientes", "save" },
            { "GRUPO_CLIENTES", sonicConstants.TB_GRUPO_CLIENTES, "Grupo de Clientes", "save" },
            { "EMPRESAS_CLIENTES", sonicConstants.TB_EMPRESAS_CLIENTES, "Clientes por Empresa", "save" },
            { "PRODUTOS", sonicConstants.TB_PRODUTOS, "Produtos", "save" },
            { "GRUPO_PRODUTOS", sonicConstants.TB_GRUPO_PRODUTOS, "Grupo de Produtos", "save" },
            { "ESTOQUE_PRODUTOS", sonicConstants.TB_ESTOQUE_PRODUTOS, "Estoque de Produtos", "save" },
            { "BLOQUEIO_PRODUTOS", sonicConstants.TB_BLOQUEIO_PRODUTOS, "Bloqueio de Produtos", "save" },
            { "TABELA_PRECO", sonicConstants.TB_TABELA_PRECO, "Tabela de Preços", "save" },
            { "TABELA_PRECO_EMPRESA", sonicConstants.TB_TABELA_PRECO_EMPRESA, "Tabela de Preço por Empresa", "save" },
            { "TABELA_PRECO_PRODUTO", sonicConstants.TB_TABELA_PRECO_PRODUTO, "Tabela de Preço por Produto", "save" },
            { "TIPO_COBRANCA", sonicConstants.TB_TIPO_COBRANCA, "Tipo de Cobrança", "save" },
            { "TIPO_PEDIDO", sonicConstants.TB_TIPO_PEDIDO, "Tipo de Pedido", "save" },
            { "AGENTE_COBRADOR", sonicConstants.TB_AGENTE_COBRADOR, "Agente Cobrador", "save" },
            { "UNIDADE_MEDIDA", sonicConstants.TB_UNIDADE_MEDIDA, "Unidade de Medida", "save" },
            { "ROTA", sonicConstants.TB_ROTA, "Agenda de Visitas", "save" },
            { "CLIENTES_SEM_COMPRA", sonicConstants.TB_CLIENTES_SEM_COMPRA, "Clientes sem Compra", "save" },
            { "TITULOS", sonicConstants.TB_TITULOS, "Títulos", "save" },
            { "PRAZO", sonicConstants.TB_PRAZO, "Prazo", "save" },
            { "ULTIMAS_COMPRAS", sonicConstants.TB_ULTIMAS_COMPRAS, "Últimas Compras", "save" },
            { "ULTIMAS_COMPRAS_ITENS", sonicConstants.TB_ULTIMAS_COMPRAS_ITENS, "Últimas Compras - Itens", "save" },
            { "VENDAS", sonicConstants.TB_VENDAS, "Vendas", "save" },
            { "", sonicConstants.TB_VENDAS, "", "replace" }

    };

    sonicPopularTabelas(Activity act){
        this.mContext = act;
        this.mAct = act;
        this.DBCL = new sonicDatabaseLogCRUD(mContext);
        this.mySystem = new sonicSystem(mContext);
        this.DBC = new sonicDatabaseCRUD(mContext);
        this.mPref = new sonicPreferences(mContext);
    }


    public void gravarDados(String arquivo){

        File file = new File(Environment.getExternalStorageDirectory(),sonicConstants.LOCAL_TEMP+arquivo);

        if(file.exists()){

            new Handler(Looper.getMainLooper()).post(()-> {

                myProgress = new ProgressDialog(mContext);
                myProgress.setCancelable(false);
                myProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                myProgress.setMessage("");
                myProgress.setProgress(0);
                myProgress.show();
                //new myAsyncTaskGravar().execute(arquivo);
                new myAsyncTaskGravarXml().execute(arquivo);
            });

        }else {
            mPref.Geral.setError(mContext.getResources().getString(R.string.fileNotFound));
            enviarErro();
        }

    }

    private void enviarErro(){
        switch (mPref.Sincronizacao.getCalledActivity()){
            case "sonicMain":
                ((sonicMain)mContext).mensagemErro();
                break;
            case "sonicSincronizacao":
                break;
            case "sonicProdutos":
                break;
            case "sonicClientes":
                break;

        }
    }

    protected static boolean contains(CharSequence sequence)
    {
        return String.valueOf(sequence).indexOf(sequence.toString()) > -1;
    }

    public class myAsyncTaskGravar extends AsyncTask<String, String, Boolean>{
        @Override
        protected Boolean doInBackground(String... strings) {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            FileInputStream f;
            BufferedReader reader;
            arquivo = strings[0];

            File file = new File(Environment.getExternalStorageDirectory(), sonicConstants.LOCAL_TEMP + arquivo);

            if(file.exists()){

                try {

                    f = new FileInputStream(file);
                    reader = new BufferedReader(new InputStreamReader(f, StandardCharsets.ISO_8859_1));
                    int count=0;
                    myProgress.setMax(sonicUtils.countFileLines(file));

                    String line = reader.readLine();

                    List<String[]> list = Arrays.asList(mTables);

                    while (line!=null){

                        for(String[] arr: list){

                            if(line!=null && (line.contains(arr[0]) || arr[0].contains(line))){

                                mTablesSinc.add(arr[1]);

                                if(arr[3].equals("save")){
                                    DBC.Database.cleanData(arr[1]);
                                }

                                line = reader.readLine();

                                while (line != null && line.indexOf("->") != 0) {

                                    if(line.indexOf("<")==0){

                                        line = reader.readLine();

                                    }else{
                                        count += 1;
                                        publishProgress("Gravando "+ Html.fromHtml("<b>"+arr[2]+"</b>"), String.valueOf(count));

                                        String str = line;
                                        int pos = str.indexOf("=") + 1;
                                        int len = str.length();
                                        String str2 = str.substring(pos, len);
                                        List<String> data = Arrays.asList(str2.split(";", -1));
                                        if(!DBC.Database.saveData(arr[1], data, arr[3].equals("save") ? ModeSave.DELETE_AND_INSERT : ModeSave.INSERT)){
                                            Log.d("ERRO", mPref.Geral.getError());
                                        }
                                        line = reader.readLine();
                                    }

                                }
                            }
                        }

                        line = reader.readLine();
                        reader.mark(0);
                        reader.reset();

                    }

                }catch (Exception e){
                    e.printStackTrace();
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                    return false;
                }

                return true;

            }

            return true;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            myProgress.setMessage(values[0]);
            myProgress.setProgress(Integer.parseInt(values[1]));
        }

        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            myProgress.dismiss();
            new sonicUtils(mContext).Arquivo.deleteFile(sonicConstants.LOCAL_TEMP+arquivo);
            if(aBoolean){
                sonicDatabaseCRUD mData = new sonicDatabaseCRUD(mContext);
                switch (mPref.Sincronizacao.getDownloadType()){
                    case "DADOS":
                        mPref.Geral.setFirstSinc(false);
                        mPref.Users.setUltimaSincID(mPref.Users.getUsuarioId());
                        mPref.Users.setUltimaSincNome(mPref.Users.getUsuarioNome());
                        enviarMensagemSincronizacao();
                        mData.Sincronizacao.saveSincronizacao(sonicConstants.TB_TODAS, sonicConstants.TIPO_SINC_DADOS);
                        for(String arr: mTablesSinc){
                            mData.Sincronizacao.saveSincronizacao(arr, sonicConstants.TIPO_SINC_DADOS);
                        }
                        break;
                    case "ESTOQUE":
                        new PromptDialog(mContext)
                                .setDialogType(sonicDialog.MSG_SUCCESS)
                                .setAnimationEnable(true)
                                .setTitleText(R.string.headerSuccess)
                                .setContentText(R.string.estoqueSincronizado)
                                .setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                                    @Override
                                    public void onClick(PromptDialog dialog) {
                                        dialog.dismiss();
                                        ((sonicSincronizacao)mAct).refreshSincFragment();
                                    }
                                }).show();
                        mData.Sincronizacao.saveSincronizacao(sonicConstants.TB_ESTOQUE_PRODUTOS, sonicConstants.TIPO_SINC_DADOS);
                        break;
                    case "SITE":
                        mData.Sincronizacao.saveSincronizacao(sonicConstants.TB_SITE, sonicConstants.TIPO_SINC_DADOS);
                        primeiroAcesso();
                        break;
                }
            }
        }
    }

    public class myAsyncTaskGravarXml extends AsyncTask<String, String, Boolean>{
        @Override
        protected Boolean doInBackground(String... strings) {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            arquivo = strings[0];

            File file = new File(Environment.getExternalStorageDirectory(), sonicConstants.LOCAL_TEMP + arquivo);

            if(file.exists()){

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder;

                try {

                    int count=0;
                    myProgress.setMax(sonicUtils.countFileLines(file));
                    List<String[]> list = Arrays.asList(mTables);
                    dBuilder = dbFactory.newDocumentBuilder();
                    dbFactory.setIgnoringElementContentWhitespace(true);
                    Document doc = dBuilder.parse(file);
                    doc.getDocumentElement().normalize();
                    NodeList nodeList = doc.getElementsByTagName("secao");
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        NodeList childList = nodeList.item(i).getChildNodes();
                        Element element = (Element)nodeList.item(i);
                        for(String[] arr: list){
                            if(arr[0].equals(element.getAttribute("nome")) || element.getAttribute("nome").equals(arr[0])){
                                if(arr[3].equals("save")){
                                    DBC.Database.cleanData(arr[1]);
                                }
                                mTablesSinc.add(arr[0]);
                                for (int j = 0; j < childList.getLength(); j++) {
                                    Node childNode = childList.item(j);
                                    if ("valores".equals(childNode.getNodeName())) {
                                        count +=1;
                                        publishProgress("Gravando "+ arr[2], String.valueOf(count));
                                        List<String> data = Arrays.asList(childNode.getTextContent().split("\\|", -1));
                                        if(!DBC.Database.saveData(arr[1], data, arr[3].equals("save") ? ModeSave.DELETE_AND_INSERT : ModeSave.INSERT)){
                                            return false;
                                        }
                                    }
                                }
                            }
                        }
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                    return false;
                }

                return true;

            }

            return true;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            myProgress.setMessage(values[0]);
            myProgress.setProgress(Integer.parseInt(values[1]));
        }

        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            myProgress.dismiss();
            new sonicUtils(mContext).Arquivo.deleteFile(sonicConstants.LOCAL_TEMP+arquivo);
            if(aBoolean){
                sonicDatabaseCRUD mData = new sonicDatabaseCRUD(mContext);
                switch (mPref.Sincronizacao.getDownloadType()){
                    case "DADOS":
                        mPref.Geral.setFirstSinc(false);
                        mPref.Users.setUltimaSincID(mPref.Users.getUsuarioId());
                        mPref.Users.setUltimaSincNome(mPref.Users.getUsuarioNome());
                        enviarMensagemSincronizacao();
                        mData.Sincronizacao.saveSincronizacao(sonicConstants.TB_TODAS, sonicConstants.TIPO_SINC_DADOS);
                        for(String arr: mTablesSinc){
                            mData.Sincronizacao.saveSincronizacao(arr, sonicConstants.TIPO_SINC_DADOS);
                        }
                        break;
                    case "ESTOQUE":
                        new PromptDialog(mContext)
                                .setDialogType(sonicDialog.MSG_SUCCESS)
                                .setAnimationEnable(true)
                                .setTitleText(R.string.headerSuccess)
                                .setContentText(R.string.estoqueSincronizado)
                                .setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                                    @Override
                                    public void onClick(PromptDialog dialog) {
                                        dialog.dismiss();
                                        ((sonicSincronizacao)mAct).refreshSincFragment();
                                    }
                                }).show();
                        mData.Sincronizacao.saveSincronizacao(sonicConstants.TB_ESTOQUE_PRODUTOS, sonicConstants.TIPO_SINC_DADOS);
                        break;
                    case "SITE":
                        mData.Sincronizacao.saveSincronizacao(sonicConstants.TB_SITE, sonicConstants.TIPO_SINC_DADOS);
                        primeiroAcesso();
                        break;
                }
            }else{
                enviarMensagemErro();
            }
        }
    }

    private void enviarMensagemErro(){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                switch (mPref.Sincronizacao.getCalledActivity()){
                    case "sonicEmpresa":
                        ((sonicEmpresa)mAct).mensagemErroDetalhe(false);
                        break;
                    case "sonicMain":
                        ((sonicMain)mAct).mensagemErro();
                        break;
                }

            }
        });
    }

    public void enviarMensagemSincronizacao(){

        mAct.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (mPref.Sincronizacao.getCalledActivity()){
                    case "sonicClientes":
                        ((sonicClientes)mAct).refreshFragments();
                        break;
                    case "sonicProdutos":
                        ((sonicProdutos)mAct).refreshFragments();
                        break;
                    case "sonicMain":
                        ((sonicMain)mAct).mensagemOK("SINCRONIZADO.", true);
                        break;
                    case "sonicRota":
                        ((sonicRota)mAct).refreshFragments();
                        break;
                }
            }
        });

    }

    public void primeiroAcesso(){

        TelephonyManager myPhoneManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED){


            if(new sonicDatabaseCRUD(mContext).Database.checkMinimumData()){

                mPref.Users.setUsuarioImei(android.os.Build.VERSION.SDK_INT >= 26 ? myPhoneManager.getImei() : myPhoneManager.getDeviceId());

                new mAsyncTask().execute();

            }else{

                //new sonicDialog(myCtx).showMS("::: Atenção :::", "Não foi possível gravar os dados nas tabelas. O arquivo solicitado parece não conter dados suficientes ou está mal formatado.", sonicDialog.MSG_WARNING);
                mPref.Geral.setError("Não foi possível gravar os dados nas tabelas. O arquivo solicitado parece não conter dados suficientes ou está mal formatado.");
                ((sonicEmpresa) mContext).mensagemErroDetalhe(false);
            }

        }else{
            ActivityCompat.requestPermissions(((Activity) mContext),
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    1);

        }

    }


    private class mAsyncTask extends AsyncTask<String, String, List<sonicUsuariosHolder>>{
        @Override
        protected List<sonicUsuariosHolder> doInBackground(String... strings) {
            return DBC.Usuario.selectUsuarioImei(mPref.Users.getUsuarioImei());
        }

        @Override
        protected void onPostExecute(List<sonicUsuariosHolder> mList) {
            super.onPostExecute(mList);
            if(!mList.isEmpty()){
                startActivity(mList);
            }
            else{
                mPref.Geral.setError("Seu IMEI: " + mPref.Users.getUsuarioImei() + " não está cadastrado para usar o aplicativo.\n\nFavor entrar em contato com o responsável na empresa pela administração do serviço.\n\nSe você for usuário de suporte, pode fazer login agora.");
                ((sonicEmpresa) mContext).mensagemErroDetalhe(true);
                //new sonicDialog(myCtx).showMS("::: Atenção :::" , "Seu aparelho com o IMEI: " + mPref.Users.getUsuarioImei() + " não está cadastrado para usar o sistema. Favor entrar em contato com o responsável na empresa pela administração do serviço.", sonicDialog.MSG_WARNING);
            }
        }
    }

    public void startActivity(List<sonicUsuariosHolder> mList){

        Intent i = new Intent(mContext, sonicPrimeiroAcesso.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mPref.Users.setEmpresaId(mList.get(0).getEmpresaId());
        mPref.Users.setEmpresaNome(mList.get(0).getEmpresa());
        mPref.Users.setUsuarioId(mList.get(0).getCodigo());
        mPref.Users.setUsuarioNome(mList.get(0).getNome());
        mPref.Users.setUsuarioCargo(mList.get(0).getCargo());
        mContext.startActivity(i);
        ((AppCompatActivity) mContext).finish();

    }
}
