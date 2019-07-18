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
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
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

    sonicPopularTabelas(Context ctx){
        this.myCtx = ctx;
        this.DBCL = new sonicDatabaseLogCRUD(myCtx);
        this.mySystem = new sonicSystem(myCtx);
        this.DBC = new sonicDatabaseCRUD(myCtx);
    }

    class myAsyncTaskGravar extends AsyncTask<String,String, Boolean>{
        @Override
        protected Boolean doInBackground(String... strings) {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            FileInputStream f;
            BufferedReader reader;
            String arquivo = strings[0];

            File file = new File(Environment.getExternalStorageDirectory(), sonicConstants.LOCAL_TMP + arquivo);

            if(file.exists()){

                try {

                    f = new FileInputStream(file);
                    reader = new BufferedReader(new InputStreamReader(f));
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
                                publishProgress("Gravando na seção...\n\n"+tabela, String.valueOf(count));

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
                                publishProgress("Gravando na seção...\n\n"+tabela, String.valueOf(count));

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
                                publishProgress("Gravando na seção...\n\n"+tabela, String.valueOf(count));

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
                                publishProgress("Gravando na seção...\n\n"+tabela, String.valueOf(count));

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
                                publishProgress("Gravando na seção...\n\n"+tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.Usuarios.saveUsuario(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[EMPRESAS_USUARIOS]".equals(line) || line.equals("[EMPRESAS_USUARIOS]"))) {

                            Log.d("EMPRESAS_USUARIOS", "ENTROU");

                            tabela = line;

                            DBC.EmpresasUsuarios.cleanEmpresasUsuarios();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress("Gravando na seção...\n\n"+tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.EmpresasUsuarios.saveEmpresasUsuarios(data);
                                line = reader.readLine();


                            }

                        }

                        if (line != null && ("[CLIENTES]".equals(line) || line.equals("[CLIENTES]"))) {

                            Log.d("CLIENTES", "ENTROU");
                            tabela = line;

                            DBC.Clientes.cleanCliente();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress("Gravando na seção...\n\n"+tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.Clientes.saveCliente(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[GRUPO_CLIENTES]".equals(line) || line.equals("[GRUPO_CLIENTES]"))) {

                            Log.d("GRUPO_CLIENTES", "ENTROU");
                            tabela = line;

                            DBC.GrupoCliente.cleanGrupoCliente();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress("Gravando na seção...\n\n"+tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.GrupoCliente.saveGrupoCliente(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[RANKING_CLIENTES]".equals(line) || line.equals("[RANKING_CLIENTES]"))) {

                            Log.d("RANKING_CLIENTES", "ENTROU");
                            tabela = line;

                            DBC.RankingClientes.cleanRankingCliente();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {
                                
                                count+=1;
                                publishProgress("Gravando na seção...\n\n"+tabela, String.valueOf(count));
                                
                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.RankingClientes.saveRankingCliente(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[VENDAS]".equals(line) || line.equals("[VENDAS]"))) {

                            Log.d("VENDAS", "ENTROU");
                            tabela = line;
                            
                            DBC.Vendas.cleanVendas();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress("Gravando na seção...\n\n"+tabela, String.valueOf(count));
                                
                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.Vendas.saveVendas(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[VENDAS_ITENS]".equals(line) || line.equals("[VENDAS_ITENS]"))) {

                            Log.d("VENDAS_ITENS", "ENTROU");
                            tabela = line;
                            DBC.VendasItens.cleanVendasItens();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress("Gravando na seção...\n\n"+tabela, String.valueOf(count));
                                
                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.VendasItens.saveVendasItens(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[CLIENTES_SEM_CROMPRA]".equals(line) || line.equals("[CLIENTES_SEM_COMPRA]"))) {

                            Log.d("CLIENTES_SEM_COMPRA", "ENTROU");
                            tabela = line;
                            
                            DBC.ClienteSemCompra.cleanClienteSemCompra();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress("Gravando na seção...\n\n"+tabela, String.valueOf(count));
                                
                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.ClienteSemCompra.saveClienteSemCompra(data);
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
                                publishProgress("Gravando na seção...\n\n"+tabela, String.valueOf(count));
                                
                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
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
                                publishProgress("Gravando na seção...\n\n"+tabela, String.valueOf(count));
                                
                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.GrupoProduto.saveGrupoProduto(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[ESTOQUE_PRODUTOS]".equals(line) || line.equals("[ESTOQUE_PRODUTOS]"))) {

                            Log.d("ESTOQUE_PRODUTOS", "ENTROU");

                            tabela = line;

                            DBC.Estoque.cleanEstoque();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress("Gravando na seção...\n\n"+tabela, String.valueOf(count));
                                
                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.Estoque.saveEstoque(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[RANKING_PRODUTOS]".equals(line) || line.equals("[RANKING_PRODUTOS]"))) {

                            Log.d("RANKING_PRODUTOS", "ENTROU");

                            tabela = line;

                            DBC.Produto.cleanRankingProduto();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress("Gravando na seção...\n\n"+tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.Produto.saveRankingProduto(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[FINANCEIRO]".equals(line) || line.equals("[FINANCEIRO]"))) {

                            Log.d("FINANCEIRO", "ENTROU");

                            tabela = line;

                            DBC.Financeiro.cleanFinanceiro();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress("Gravando na seção...\n\n"+tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.Financeiro.saveFinanceiro(data);
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
                                publishProgress("Gravando na seção...\n\n"+tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.Titulos.saveTitulos(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[RETORNO_PEDIDO]".equals(line) || line.equals("[RETORNO_PEDIDO]"))) {

                            Log.d("RETORNO_PEDIDO", "ENTROU");

                            tabela = line;

                            DBC.Retorno.cleanRetornoPedido();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress("Gravando na seção...\n\n"+tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.Retorno.saveRetornoPedido(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[RETORNO_PEDIDO_ITENS]".equals(line) || line.equals("[RETORNO_PEDIDO_ITENS]"))) {

                            Log.d("RETORNO_PEDIDO_ITENS", "ENTROU");

                            tabela = line;

                            DBC.Retorno.cleanRetornoPedidoItens();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress("Gravando na seção...\n\n"+tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                //TODO //result = DBC.Retorno.saveRetornoPedidoItens(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[HISTORICO_VENDEDOR]".equals(line) || line.equals("[HISTORICO_VENDEDOR]"))) {

                            Log.d("HOSTORICO_VENDEDOR", "ENTROU");

                            tabela = line;

                            DBC.HistoricoUsuario.cleanHistoricoUsuario();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress("Gravando na seção...\n\n"+tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.HistoricoUsuario.saveHistoricoUsuario(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[TIPO_PEDIDO]".equals(line) || line.equals("[TIPO_PEDIDO]"))) {

                            Log.d("TIPO_PEDIDO", "ENTROU");

                            tabela = line;

                            DBC.TipoPedido.cleanTipoPedido();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress("Gravando na seção...\n\n"+tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.TipoPedido.saveTipoPedido(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[UNIDADE_MEDIDA]".equals(line) || line.equals("[UNIDADE_MEDIDA]"))) {

                            Log.d("UNIDADE_MEDIDA", "ENTROU");

                            tabela = line;

                            DBC.UnidadeMedida.cleanUnidadeMedida();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress("Gravando na seção...\n\n"+tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.UnidadeMedida.saveUnidadeMedida(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[PRAZO]".equals(line) || line.equals("[PRAZO]"))) {

                            Log.d("PRAZO", "ENTROU");

                            tabela = line;

                            DBC.Prazo.cleanPrazo();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress("Gravando na seção...\n\n"+tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.Prazo.savePrazo(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[TABELA_PRECO]".equals(line) || line.equals("[TABELA_PRECO]"))) {

                            Log.d("TABELA_PRECO", "ENTROU");

                            tabela = line;

                            DBC.TabelaPreco.cleanTabelaPreco();

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress("Gravando na seção...\n\n"+tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.TabelaPreco.saveTabelaPreco(data);
                                line = reader.readLine();

                            }

                        }

                        if (line != null && ("[LOCALIZACAO]".equals(line))) {

                            line = reader.readLine();

                            while (line != null && line.indexOf("[") != 0) {

                                count+=1;
                                publishProgress("Gravando na seção...\n\n"+tabela, String.valueOf(count));

                                String str = line;
                                int pos = str.indexOf("=") + 1;
                                int len = str.length();
                                String str2 = str.substring(pos, len);
                                List<String> data = Arrays.asList(str2.split(";"));
                                DBC.Localizacao.saveLocalizacao(data);
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
                                publishProgress("Gravando na seção...\n\n"+tabela, String.valueOf(count));

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
                        line = reader.readLine();

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

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            myProgress.dismiss();
            if(aBoolean){
                switch (sonicConstants.DOWNLOAD_TYPE){
                    case "DADOS":
                        new sonicThrowMessage(myCtx).showMessage("Tudo certo.","Dados sincronizados com sucesso!", sonicThrowMessage.MSG_SUCCESS);
                        new sonicDatabaseCRUD(myCtx).Sincronizacao.saveSincronizacao("dados","dados");
                        break;
                    case "ESTOQUE":
                        new sonicThrowMessage(myCtx).showMessage("Tudo certo.","Estoque sincronizado com sucesso!", sonicThrowMessage.MSG_SUCCESS);
                        break;
                    case "SITE":

                        TelephonyManager myPhoneManager = (TelephonyManager)myCtx.getSystemService(Context.TELEPHONY_SERVICE);

                        if (ActivityCompat.checkSelfPermission(myCtx, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED){


                            if(new sonicDatabaseCRUD(myCtx).Database.checkMinimumData()){


                                String imei="";

                                if (android.os.Build.VERSION.SDK_INT >= 26) {
                                    imei = myPhoneManager.getImei();
                                }
                                else
                                {
                                    imei = myPhoneManager.getDeviceId();
                                }

                                if(sonicConstants.EMP_TESTE){
                                    imei = "123456789";
                                }

                                sonicConstants.USER_IMEI = imei;

                                List<sonicUsuariosHolder> lista;
                                lista = DBC.Usuarios.selectUsuarioImei(imei);
                                if(!lista.isEmpty()){

                                    startActivity(lista.get(0).getEmpresa(), lista.get(0).getEmpresaId() , lista.get(0).getCodigo() ,lista.get(0).getNome(), lista.get(0).getCargo(), imei);

                                }else{
                                    new sonicThrowMessage(myCtx).showMessage("Atenção", "Seu aparelho não esta cadastrado para "+DBC.Empresa.empresaPadrao()+". Verifique se seu imei ("+imei+") foi cadastrado corretamente pelo administrador.", sonicThrowMessage.MSG_WARNING);

                                }

                            }else{

                                new sonicThrowMessage(myCtx).showMessage("Atenção", "Não foi possível gravar os dados nas tabelas. O arquivo solicitado parece não conter dados suficientes ou está mal formatado.", sonicThrowMessage.MSG_WARNING);

                            }

                        }else{
                            ActivityCompat.requestPermissions(((Activity) myCtx),
                                    new String[]{Manifest.permission.READ_PHONE_STATE},
                                    1);

                        }
                        break;


                }
            }
        }
    }

    public void gravarDados(String arquivo){

        new Handler(Looper.getMainLooper()).post(()-> {

            myProgress = new ProgressDialog(myCtx);
            myProgress.setCancelable(false);
            myProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            myProgress.setMessage("Gravando na seção...\n\n");
            myProgress.setProgress(0);
            myProgress.show();
            new myAsyncTaskGravar().execute(arquivo);

        });



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
        ((Activity) myCtx).finish();
    }



}
