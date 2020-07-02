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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.softhex.materialdialog.PromptDialog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrador on 04/08/2017.
 */

public class sonicPopularTabelas {

    private Context myCtx;
    private Activity mAct;
    private sonicPreferences mPref;
    private sonicDatabaseLogCRUD DBCL;
    private sonicDatabaseCRUD DBC;
    private sonicSystem mySystem;
    private ProgressDialog myProgress;
    private String arquivo;
    private String[][] mTables = {

            { "[SITE]", sonicConstants.TB_SITE, "Site", "save"},
            { "[FTP]", sonicConstants.TB_FTP, "Ftp" ,"save" },
            { "[EMPRESAS]", sonicConstants.TB_EMPRESA, "Empresas", "save" },
            { "[NIVEL_ACESSO]", sonicConstants.TB_NIVEL_ACESSO, "Nível de Acesso", "save" },
            { "[USUARIOS]", sonicConstants.TB_USUARIO, "Usuários" ,"save" },
            { "[EMPRESAS_USUARIOS]", sonicConstants.TB_EMPRESA_USUARIO, "Usuários por Empresa", "save" },
            { "[MATRIZ]", sonicConstants.TB_GRUPO_EMPRESAS, "Grupo Empresas", "save"},
            { "[CLIENTES]", sonicConstants.TB_CLIENTE, "Clientes", "save" },
            { "[GRUPO_CLIENTES]", sonicConstants.TB_GRUPO_CLIENTE, "Grupo de Clientes", "save" },
            { "[EMPRESAS_CLIENTES]", sonicConstants.TB_EMPRESA_CLIENTE, "Clientes por Empresa", "save" },
            { "[PRODUTOS]", sonicConstants.TB_PRODUTO, "Produtos", "save" },
            { "[GRUPO_PRODUTOS]", sonicConstants.TB_GRUPO_PRODUTO, "Grupo de Produtos", "save" },
            { "[ESTOQUE_PRODUTOS]", sonicConstants.TB_ESTOQUE_PRODUTO, "Estoque de Produtos", "save" },
            { "[BLOQUEIO_PRODUTOS]", sonicConstants.TB_BLOQUEIO_PRODUTO, "Bloqueio de Produtos", "save" },
            { "[TABELA_PRECO]", sonicConstants.TB_TABELA_PRECO, "Tabela de Preços", "save" },
            { "[TABELA_PRECO_EMPRESA]", sonicConstants.TB_TABELA_PRECO_EMPRESA, "Tabela de Preço por Empresa", "save" },
            { "[TABELA_PRECO_PRODUTO]", sonicConstants.TB_TABELA_PRECO_PRODUTO, "Tabela de Preço por Produto", "save" },
            { "[TIPO_COBRANCA]", sonicConstants.TB_TIPO_COBRANCA, "Tipo de Cobrança", "save" },
            { "[TIPO_PEDIDO]", sonicConstants.TB_TIPO_PEDIDO, "Tipo de Pedido", "save" },
            { "[AGENTE_COBRADOR]", sonicConstants.TB_AGENTE_COBRADOR, "Agente Cobrador", "save" },
            { "[UNIDADE_MEDIDA]", sonicConstants.TB_UNIDADE_MEDIDA, "Unidade de Medida", "save" },
            { "[ROTA]", sonicConstants.TB_ROTA, "Agenda de Visitas", "replace" },
            { "[CLIENTES_SEM_COMPRA]", sonicConstants.TB_CLIENTE_SEM_COMPRA, "Clientes sem Compra", "save" },
            { "[TITULOS]", sonicConstants.TB_TITULO, "Títulos", "save" },
            { "[PRAZO]", sonicConstants.TB_PRAZO, "Prazo", "save" },
            { "[ULTIMAS_COMPRAS]", sonicConstants.TB_ULTIMAS_COMPRAS, "Últimas Compras", "save" },
            { "[ULTIMAS_COMPRAS_ITENS]", sonicConstants.TB_ULTIMAS_COMPRAS_ITENS, "Últimas Compras - Itens", "save" },
            { "[VENDAS]", sonicConstants.TB_VENDA, "Vendas", "save" },
            { "[]", sonicConstants.TB_VENDA, "", "replace" }

    };

    sonicPopularTabelas(Activity act){
        this.myCtx = act;
        this.mAct = act;
        this.DBCL = new sonicDatabaseLogCRUD(myCtx);
        this.mySystem = new sonicSystem(myCtx);
        this.DBC = new sonicDatabaseCRUD(myCtx);
        this.mPref = new sonicPreferences(myCtx);
    }


    public void gravarDados(String arquivo){

        File file = new File(Environment.getExternalStorageDirectory(),sonicConstants.LOCAL_TEMP+arquivo);

        if(file.exists()){

            new Handler(Looper.getMainLooper()).post(()-> {

                myProgress = new ProgressDialog(myCtx);
                myProgress.setCancelable(false);
                myProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                //myProgress.setTitle("Gravando");
                myProgress.setMessage("");
                myProgress.setProgress(0);
                myProgress.show();
                new myAsyncTaskGravar().execute(arquivo);
            });

        }else {
            new sonicDialog(myCtx).showMI(R.string.headerWarning,R.string.fileNotFound, sonicDialog.MSG_WARNING);
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
                    String tabela = "";
                    String tabela2 = "";

                    String line = reader.readLine();

                    List<String[]> list = Arrays.asList(mTables);

                    while (line!=null){
                        for(String[] arr: list){
                            //Log.d(Arrays.asList(arr).get(0), Arrays.asList(arr).get(1));

                            if(line!=null && (line.contains(arr[0]) || arr[0].contains(line))){

                                if(arr[3].equals("save")){
                                    DBC.Database.cleanData(arr[1]);
                                }

                                tabela = line;
                                tabela2 = arr[2];
                                line = reader.readLine();

                                while (line != null && line.indexOf("[") != 0) {

                                    if(line.indexOf("###")==0){

                                        line = reader.readLine();

                                    }else{
                                        count += 1;
                                        publishProgress("Gravando "+ Html.fromHtml("<b>"+tabela2+"</b>"), String.valueOf(count));

                                        String str = line;
                                        int pos = str.indexOf("=") + 1;
                                        int len = str.length();
                                        String str2 = str.substring(pos, len);
                                        List<String> data = Arrays.asList(str2.split(";", -1));
                                        DBC.Database.saveData(arr[1], data, arr[3].equals("save") ? sonicDatabaseCRUD.DB_MODE_SAVE : sonicDatabaseCRUD.DB_MODE_SAVE_UNIQUE);
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
            new sonicUtils(myCtx).Arquivo.deleteFile(sonicConstants.LOCAL_TEMP+arquivo);
            if(aBoolean){
                sonicDatabaseCRUD mData = new sonicDatabaseCRUD(myCtx);
                switch (mPref.Sincronizacao.getDownloadType()){
                    case "DADOS":
                        new PromptDialog(myCtx)
                                .setDialogType(sonicDialog.MSG_SUCCESS)
                                .setAnimationEnable(true)
                                .setTitleText(R.string.headerSuccess)
                                .setContentText(R.string.dadosSincronizados)
                                .setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                                    @Override
                                    public void onClick(PromptDialog dialog) {
                                        dialog.dismiss();
                                        if(mPref.Sincronizacao.getSincRefresh()){
                                            switch (mPref.Sincronizacao.getCalledActivity()){
                                                case "sonicClientes":
                                                    ((sonicClientes)mAct).refreshFragments();
                                                    break;
                                                case "sonicProdutos":
                                                    ((sonicProdutos)mAct).refreshFragments();
                                                    break;
                                                case "sonicMain":
                                                    ((sonicMain)mAct).refreshFragments();
                                                    break;
                                                case "sonicRota":
                                                    ((sonicRota)mAct).refreshFragments();
                                                    break;
                                            }

                                        }
                                    }
                                }).show();
                        mData.Sincronizacao.saveSincronizacao(sonicConstants.TB_TODAS, sonicConstants.TIPO_SINC_DADOS);
                        List<String[]> list = Arrays.asList(mTables);
                        for(String[] arr: list){
                            mData.Sincronizacao.saveSincronizacao(arr[1], sonicConstants.TIPO_SINC_DADOS);
                        }
                        break;
                    case "ESTOQUE":
                        new PromptDialog(myCtx)
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
                        mData.Sincronizacao.saveSincronizacao(sonicConstants.TB_ESTOQUE_PRODUTO, sonicConstants.TIPO_SINC_DADOS);
                        break;
                    case "SITE":
                        mData.Sincronizacao.saveSincronizacao(sonicConstants.TB_SITE, sonicConstants.TIPO_SINC_DADOS);
                        primeiroAcesso();
                        break;
                }
            }
        }
    }

    public void primeiroAcesso(){

        TelephonyManager myPhoneManager = (TelephonyManager)myCtx.getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(myCtx, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED){


            if(new sonicDatabaseCRUD(myCtx).Database.checkMinimumData()){

                mPref.Users.setUsuarioImei(android.os.Build.VERSION.SDK_INT >= 26 ? myPhoneManager.getImei() : myPhoneManager.getDeviceId());

                new mAsyncTask().execute();

            }else{

                //new sonicDialog(myCtx).showMS("::: Atenção :::", "Não foi possível gravar os dados nas tabelas. O arquivo solicitado parece não conter dados suficientes ou está mal formatado.", sonicDialog.MSG_WARNING);
                mPref.Geral.setError("Não foi possível gravar os dados nas tabelas. O arquivo solicitado parece não conter dados suficientes ou está mal formatado.");
                ((sonicEmpresa)myCtx).mensagemErro();
            }

        }else{
            ActivityCompat.requestPermissions(((Activity) myCtx),
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
                ((sonicEmpresa)myCtx).mensagemErro();
                //new sonicDialog(myCtx).showMS("::: Atenção :::" , "Seu aparelho com o IMEI: " + mPref.Users.getUsuarioImei() + " não está cadastrado para usar o sistema. Favor entrar em contato com o responsável na empresa pela administração do serviço.", sonicDialog.MSG_WARNING);
            }
        }
    }

    public void startActivity(List<sonicUsuariosHolder> mList){

        Intent i = new Intent(myCtx, sonicPrimeiroAcesso.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mPref.Users.setEmpresaId(mList.get(0).getEmpresaId());
        mPref.Users.setEmpresaNome(mList.get(0).getEmpresa());
        mPref.Users.setUsuarioId(mList.get(0).getCodigo());
        mPref.Users.setUsuarioNome(mList.get(0).getNome());
        mPref.Users.setUsuarioCargo(mList.get(0).getCargo());
        myCtx.startActivity(i);
        ((AppCompatActivity) myCtx).finish();

    }
}
