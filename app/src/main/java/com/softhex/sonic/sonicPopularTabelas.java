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
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
    private sonicDatabaseLogCRUD DBCL;
    private sonicDatabaseCRUD DBC;
    private sonicSystem mySystem;
    private ProgressDialog myProgress;
    private String arquivo;

    sonicPopularTabelas(Context ctx){
        this.myCtx = ctx;
        this.DBCL = new sonicDatabaseLogCRUD(myCtx);
        this.mySystem = new sonicSystem(myCtx);
        this.DBC = new sonicDatabaseCRUD(myCtx);
    }

    public void gravarDados(String arquivo){

        File file = new File(Environment.getExternalStorageDirectory(),sonicConstants.LOCAL_TEMP+arquivo);

        if(file.exists()){

            new Handler(Looper.getMainLooper()).post(()-> {

                myProgress = new ProgressDialog(myCtx);
                myProgress.setCancelable(false);
                myProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                myProgress.setTitle("Gravando na tabela...");
                myProgress.setMessage("");
                myProgress.setProgress(0);
                myProgress.show();
                new myAsyncTaskGravar().execute(arquivo);

            });
        }else {
            new sonicTM(myCtx).showMI(R.string.headerWarning,R.string.fileNotFound, sonicTM.MSG_WARNING);
        }

    }

    protected static boolean contains(CharSequence sequence)
    {
        return String.valueOf(sequence).indexOf(sequence.toString()) > -1;
    }

    class myAsyncTaskGravar extends AsyncTask<String,String, Boolean>{
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

                    String line = reader.readLine();

                    while(line!=null) {

                        if (line != null && ("[SITE]".equals(line) || line.equals("[SITE]"))) {

                            Log.d("SITE", "ENTROU");

                            tabela = line;

                            DBC.Site.cleanSite();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress(tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.Site.saveSite(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[FTP]".equals(line) || line.equals("[FTP]"))) {

                            Log.d("FTP", "ENTROU");

                            tabela = line;

                            DBC.Ftp.cleanFtp();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress(tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.Ftp.saveFtp(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[EMPRESAS]".equals(line) || line.equals("[EMPRESAS]"))) {

                            Log.d("EMPRESAS", "ENTROU");

                            tabela = line;

                            DBC.Empresa.cleanEmpresa();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress(tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.Empresa.saveEmpresa(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[NIVEL_ACESSO]".equals(line) || line.equals("[NIVEL_ACESSO]"))) {

                            Log.d("NIVEL_ACESSO", "ENTROU");

                            tabela = line;

                            DBC.NivelAcesso.nivelAcesso();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress(tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.NivelAcesso.saveNivelAcesso(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[USUARIOS]".equals(line) || line.equals("[USUARIOS]"))) {

                            Log.d("USUARIOS", "ENTROU");

                            tabela = line;

                            DBC.Usuarios.cleanUsuarios();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress(tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.Usuarios.saveUsuario(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[EMPRESAS_USUARIOS]".equals(line) || line.equals("[EMPRESAS_USUARIOS]") || line.contains(("[EMPRESAS_USUARIOS]")))) {

                            Log.d("EMPRESAS_USUARIOS", "ENTROU");

                            tabela = line;

                            DBC.EmpresasUsuarios.cleanEmpresasUsuarios();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress(tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.EmpresasUsuarios.saveEmpresasUsuarios(data);
                                line = reader.readLine();


                            }

                        }

                        if (line != null && (line.contains("[CLIENTES]"))) {

                            Log.d("CLIENTES", "ENTROU");
                            tabela = line;

                            DBC.Clientes.cleanCliente();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress(tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.Clientes.saveCliente(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[GRUPO_CLIENTES]".equals(line) || line.equals("[GRUPO_CLIENTES]") || line.contains("[GRUPO_CLIENTES]"))) {

                            Log.d("GRUPO_CLIENTES", "ENTROU");
                            tabela = line;

                            DBC.GrupoCliente.cleanGrupoCliente();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress(tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.GrupoCliente.saveGrupoCliente(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[EMPRESAS_CLIENTES]".equals(line) || line.equals("[EMPRESAS_CLIENTES]") || line.contains("[EMPRESAS_CLIENTES]"))) {

                            Log.d("GRUPO_CLIENTES", "ENTROU");
                            tabela = line;

                            DBC.EmpresasClientes.cleanEmpresasClientes();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress(tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.EmpresasClientes.saveEmpresasClientes(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[PRODUTOS]".equals(line) || line.equals("[PRODUTOS]"))) {

                            Log.d("PRODUTOS", "ENTROU");
                            tabela = line;

                            DBC.Produto.cleanProduto();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress(tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";", -1));
                                DBC.Produto.saveProduto(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[GRUPO_PRODUTOS]".equals(line) || line.equals("[GRUPO_PRODUTOS]"))) {

                            Log.d("GRUPO_PRODUTOS", "ENTROU");

                            tabela = line;

                            DBC.GrupoProduto.cleanGrupoProduto();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress(tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.GrupoProduto.saveGrupoProduto(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[ROTA]".equals(line) || line.equals("[ROTA]"))) {

                            Log.d("ROTA", "ENTROU");

                            tabela = line;

                            DBC.Rota.cleanRota();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress(tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.Rota.saveRota(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[ESTOQUE_PRODUTOS]".equals(line) || line.equals("[ESTOQUE_PRODUTOS]") || line.contains("[ESTOQUE_PRODUTOS]"))) {

                            Log.d("ESTOQUE_PRODUTOS", "ENTROU");

                            tabela = line;

                            DBC.Estoque.cleanEstoque();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress(tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.Estoque.saveEstoque(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[TITULOS]".equals(line) || line.equals("[TITULOS]"))) {

                            Log.d("TITULOS", "ENTROU");

                            tabela = line;

                            DBC.Titulos.cleanTitulos();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress(tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.Titulos.saveTitulos(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[EMPRESAS_CLIENTES]".equals(line) || line.equals("[EMPRESAS_CLIENTES]") || line.contains("[EMPRESAS_CLIENTES]"))) {

                            Log.d("EMPRESAS_CLIENTES", "ENTROU");

                            tabela = line;

                            DBC.EmpresasClientes.cleanEmpresasClientes();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress(tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.EmpresasClientes.saveEmpresasClientes(data);
                                line = reader.readLine();

                            }

                        }


                        if (line != null && ("[FINANCEIRO]".equals(line) || line.equals("[FINANCEIRO]") || line.contains("[FINANCEIRO]"))) {

                            Log.d("FINANCEIRO", "ENTROU");

                            tabela = line;

                            DBC.Financeiro.cleanFinanceiro();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress(tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.Financeiro.saveFinanceiro(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[TIPO_PEDIDO]".equals(line) || line.equals("[TIPO_PEDIDO]") || line.contains("[TIPO_PEDIDO]"))) {

                            Log.d("TIPO_PEDIDO", "ENTROU");

                            tabela = line;

                            DBC.TipoPedido.cleanTipoPedido();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress(tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.TipoPedido.saveTipoPedido(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[TIPO_COBRANCA]".equals(line) || line.equals("[TIPO_COBRANCA]") || line.contains("[TIPO_COBRANCA]"))) {

                            Log.d("TIPO_COBRANCA", "ENTROU");

                            tabela = line;

                            DBC.TipoCobranca.cleanTipoCobranca();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress(tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.TipoCobranca.saveTipoCobranca(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[UNIDADE_MEDIDA]".equals(line) || line.equals("[UNIDADE_MEDIDA]") || line.contains("[UNIDADE_MEDIDA]"))) {

                            Log.d("UNIDADE_MEDIDA", "ENTROU");

                            tabela = line;

                            DBC.UnidadeMedida.cleanUnidadeMedida();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress(tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.UnidadeMedida.saveUnidadeMedida(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[PRAZO]".equals(line) || line.equals("[PRAZO]") || line.contains("[PRAZO]"))) {

                            Log.d("PRAZO", "ENTROU");

                            tabela = line;

                            DBC.Prazo.cleanPrazo();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress(tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.Prazo.savePrazo(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[AVISOS]".equals(line) || line.equals("[AVISOS]"))) {

                            Log.d("AVISOS", "ENTROU");

                            tabela = line;

                            DBC.Avisos.cleanAvisos();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress(tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.Avisos.saveAvisos(data);
                                line = reader.readLine();

                            }

                        }

                        //CONTINUA LENDO ATE ENTRAR NUMA DAS CONDIÇÕES
                        //reader = new BufferedReader(new InputStreamReader(f));
                        line = reader.readLine();
                        reader.mark(0);

                        reader.reset();
                        //Log.d("LINHA", line);

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
                switch (sonicConstants.DOWNLOAD_TYPE){
                    case "DADOS":
                        new sonicTM(myCtx).showMI(R.string.headerSuccess,R.string.dadosSincronizados, sonicTM.MSG_SUCCESS);
                        new sonicDatabaseCRUD(myCtx).Sincronizacao.saveSincronizacao("dados","geral");
                        break;
                    case "ESTOQUE":
                        new sonicTM(myCtx).showMI(R.string.headerSuccess,R.string.estoqueSincronizado, sonicTM.MSG_SUCCESS);
                        new sonicDatabaseCRUD(myCtx).Sincronizacao.saveSincronizacao("dados","estoque");
                        break;
                    case "SITE":
                        primeiroAcesso();
                        Log.d("TYPE", "PRIMEIRO ACESSO");
                        break;


                }
            }
        }
    }

    public void primeiroAcesso(){

        TelephonyManager myPhoneManager = (TelephonyManager)myCtx.getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(myCtx, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED){


            if(new sonicDatabaseCRUD(myCtx).Database.checkMinimumData()){

                String imei = "";

                if(android.os.Build.VERSION.SDK_INT >= 26) {
                    imei = myPhoneManager.getImei();

                }else{
                    imei = myPhoneManager.getDeviceId();
                }

                Log.d("IMEI", imei);

                if(sonicConstants.EMP_TESTE){
                    imei = "0000000000000000000";
                }

                sonicConstants.USER_IMEI = imei;

                List<sonicUsuariosHolder> lista;
                lista = DBC.Usuarios.selectUsuarioImei(imei);
                if(!lista.isEmpty()){

                    startActivity(lista.get(0).getEmpresa(), lista.get(0).getEmpresaId() , lista.get(0).getCodigo() ,lista.get(0).getNome(), lista.get(0).getCargo(), imei);

                }else{
                    new sonicTM(myCtx).showMS("::: Atenção :::" , "Seu aparelho com o IMEI: " + imei + " não está cadastrado para usar o sistema. Favor entrar em contato com o responsável na empresa pela administração do serviço.", sonicTM.MSG_WARNING);
                    sonicConstants.EMP_TESTE = false;
                }

            }else{

                new sonicTM(myCtx).showMS("::: Atenção :::", "Não foi possível gravar os dados nas tabelas. O arquivo solicitado parece não conter dados suficientes ou está mal formatado.", sonicTM.MSG_WARNING);
                sonicConstants.EMP_TESTE = false;
            }

        }else{
            ActivityCompat.requestPermissions(((Activity) myCtx),
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    1);

        }

    }

    public void startActivity(String empresa, int empresa_id, int id, String usuario, String cargo, String imei){

        Intent i = new Intent(myCtx, sonicFirstAccess.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("ID", id);
        i.putExtra("EMPRESA", empresa);
        i.putExtra("EMPRESA_ID", empresa_id);
        i.putExtra("USUARIO", usuario);
        i.putExtra("CARGO", cargo);
        i.putExtra("IMEI", imei);
        myCtx.startActivity(i);
        ((AppCompatActivity) myCtx).finish();

    }



}
