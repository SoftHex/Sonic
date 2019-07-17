package com.softhex.sonic;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
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

    sonicPopularTabelas(Context myCtx){
        this.myCtx = myCtx;
        this.DBCL = new sonicDatabaseLogCRUD(myCtx);
        this.mySystem = new sonicSystem(myCtx);
        this.DBC = new sonicDatabaseCRUD(myCtx);
    }

    public Boolean gravarDados(String arquivo){

        StackTraceElement el = Thread.currentThread().getStackTrace()[2];

        Boolean result = false;

        File file = new File(Environment.getExternalStorageDirectory(), sonicConstants.LOCAL_TMP + arquivo);

        if(file.exists()){

            FileInputStream f;
            BufferedReader reader;

            try {

                f = new FileInputStream(file);
                reader = new BufferedReader(new InputStreamReader(f));

                String line = reader.readLine();

                        while(line!=null) {

                            if ("[SITE]".equals(line)) {

                                Log.d("SITE", "ENTROU");
                                DBC.Site.cleanSite();

                                line = reader.readLine();

                                while (line != null && line.indexOf("[") != 0) {

                                    String str = line;
                                    int pos = str.indexOf("=") + 1;
                                    int len = str.length();
                                    String str2 = str.substring(pos, len);
                                    List<String> data = Arrays.asList(str2.split(";"));
                                    result = DBC.Site.saveSite(data);
                                    line = reader.readLine();

                                }

                            }

                            if ("[FTP]".equals(line)) {

                                Log.d("FTP", "ENTROU");
                                DBC.Ftp.cleanFtp();

                                line = reader.readLine();

                                while (line != null && line.indexOf("[") != 0) {

                                    String str = line;
                                    int pos = str.indexOf("=") + 1;
                                    int len = str.length();
                                    String str2 = str.substring(pos, len);
                                    List<String> data = Arrays.asList(str2.split(";"));
                                    result = DBC.Ftp.saveFtp(data);
                                    line = reader.readLine();

                                }

                            }

                            if ("[EMPRESAS]".equals(line)) {

                                Log.d("EMPRESAS", "ENTROU");
                                DBC.Empresa.cleanEmpresa();

                                line = reader.readLine();

                                while (line != null && line.indexOf("[") != 0) {

                                    String str = line;
                                    int pos = str.indexOf("=") + 1;
                                    int len = str.length();
                                    String str2 = str.substring(pos, len);
                                    List<String> data = Arrays.asList(str2.split(";"));
                                    result = DBC.Empresa.saveEmpresa(data);
                                    line = reader.readLine();

                                }

                            }

                            if ("[NIVEL_ACESSO]".equals(line)) {

                                Log.d("NIVEL_ACESSO", "ENTROU");
                                DBC.NivelAcesso.nivelAcesso();

                                line = reader.readLine();

                                while (line != null && line.indexOf("[") != 0) {

                                    String str = line;
                                    int pos = str.indexOf("=") + 1;
                                    int len = str.length();
                                    String str2 = str.substring(pos, len);
                                    List<String> data = Arrays.asList(str2.split(";"));
                                    result = DBC.NivelAcesso.saveNivelAcesso(data);
                                    line = reader.readLine();

                                }

                            }

                            if ("[USUARIOS]".equals(line)) {

                                Log.d("USUARIOS", "ENTROU");
                                DBC.Usuarios.cleanUsuarios();

                                line = reader.readLine();

                                while (line != null && line.indexOf("[") != 0) {

                                    String str = line;
                                    int pos = str.indexOf("=") + 1;
                                    int len = str.length();
                                    String str2 = str.substring(pos, len);
                                    List<String> data = Arrays.asList(str2.split(";"));
                                    result = DBC.Usuarios.saveUsuario(data);
                                    line = reader.readLine();

                                }

                            }

                            if ("[EMPRESAS_USUARIOS]".equals(line)) {

                                Log.d("EMPRESAS_USUARIOS", "ENTROU");
                                DBC.EmpresasUsuarios.cleanEmpresasUsuarios();

                                line = reader.readLine();

                                 while (line != null && line.indexOf("[") != 0) {

                                    String str = line;
                                    int pos = str.indexOf("=") + 1;
                                    int len = str.length();
                                    String str2 = str.substring(pos, len);
                                    List<String> data = Arrays.asList(str2.split(";"));
                                    result = DBC.EmpresasUsuarios.saveEmpresasUsuarios(data);
                                    line = reader.readLine();


                                }

                            }

                            if ("[CLIENTES]".equals(line)) {

                                Log.d("CLIENTES", "ENTROU");

                                DBC.Clientes.cleanCliente();

                                line = reader.readLine();

                                while (line != null && line.indexOf("[") != 0) {

                                    String str = line;
                                    int pos = str.indexOf("=") + 1;
                                    int len = str.length();
                                    String str2 = str.substring(pos, len);
                                    List<String> data = Arrays.asList(str2.split(";"));
                                    result = DBC.Clientes.saveCliente(data);
                                    line = reader.readLine();

                                }

                            }

                            if ("[GRUPO_CLIENTES]".equals(line) || line.equals("[GRUPO_CLIENTES]")) {

                                Log.d("GRUPO_CLIENTES", "ENTROU");

                                DBC.GrupoCliente.cleanGrupoCliente();

                                line = reader.readLine();

                                while (line != null && line.indexOf("[") != 0) {

                                    String str = line;
                                    int pos = str.indexOf("=") + 1;
                                    int len = str.length();
                                    String str2 = str.substring(pos, len);
                                    List<String> data = Arrays.asList(str2.split(";"));
                                    result = DBC.GrupoCliente.saveGrupoCliente(data);
                                    line = reader.readLine();

                                }

                            }

                            if ("[RANKING_CLIENTES]".equals(line)) {

                                Log.d("RANKING_CLIENTES", "ENTROU");

                                DBC.RankingClientes.cleanRankingCliente();

                                line = reader.readLine();

                                while (line != null && line.indexOf("[") != 0) {

                                    String str = line;
                                    int pos = str.indexOf("=") + 1;
                                    int len = str.length();
                                    String str2 = str.substring(pos, len);
                                    List<String> data = Arrays.asList(str2.split(";"));
                                    result = DBC.RankingClientes.saveRankingCliente(data);
                                    line = reader.readLine();

                                }

                            }

                            if ("[VENDAS]".equals(line)) {

                                Log.d("VENDAS", "ENTROU");

                                DBC.Vendas.cleanVendas();

                                line = reader.readLine();

                                while (line != null && line.indexOf("[") != 0) {

                                    String str = line;
                                    int pos = str.indexOf("=") + 1;
                                    int len = str.length();
                                    String str2 = str.substring(pos, len);
                                    List<String> data = Arrays.asList(str2.split(";"));
                                    result = DBC.Vendas.saveVendas(data);
                                    line = reader.readLine();

                                }

                            }

                            if ("[VENDAS_ITENS]".equals(line)) {

                                Log.d("VENDAS_ITENS", "ENTROU");

                                DBC.VendasItens.cleanVendasItens();

                                line = reader.readLine();

                                while (line != null && line.indexOf("[") != 0) {

                                    String str = line;
                                    int pos = str.indexOf("=") + 1;
                                    int len = str.length();
                                    String str2 = str.substring(pos, len);
                                    List<String> data = Arrays.asList(str2.split(";"));
                                    result = DBC.VendasItens.saveVendasItens(data);
                                    line = reader.readLine();

                                }

                            }

                            if ("[CLIENTES_SEM_CROMPRA]".equals(line)) {

                                Log.d("CLIENTES_SEM_COMPRA", "ENTROU");

                                DBC.ClienteSemCompra.cleanClienteSemCompra();

                                line = reader.readLine();

                                while (line != null && line.indexOf("[") != 0) {

                                    String str = line;
                                    int pos = str.indexOf("=") + 1;
                                    int len = str.length();
                                    String str2 = str.substring(pos, len);
                                    List<String> data = Arrays.asList(str2.split(";"));
                                    result = DBC.ClienteSemCompra.saveClienteSemCompra(data);
                                    line = reader.readLine();

                                }

                            }

                            if ("[PRODUTOS]".equals(line)) {

                                Log.d("PRODUTOS", "ENTROU");

                                DBC.Produto.cleanProduto();

                                line = reader.readLine();

                                while (line != null && line.indexOf("[") != 0) {

                                    String str = line;
                                    int pos = str.indexOf("=") + 1;
                                    int len = str.length();
                                    String str2 = str.substring(pos, len);
                                    List<String> data = Arrays.asList(str2.split(";"));
                                    DBC.Produto.saveProduto(data);
                                    line = reader.readLine();

                                }

                            }

                            if ("[GRUPO_PRODUTOS]".equals(line)) {

                                Log.d("GRUPO_PRODUTOS", "ENTROU");

                                DBC.GrupoProduto.cleanGrupoProduto();

                                line = reader.readLine();

                                while (line != null && line.indexOf("[") != 0) {

                                    String str = line;
                                    int pos = str.indexOf("=") + 1;
                                    int len = str.length();
                                    String str2 = str.substring(pos, len);
                                    List<String> data = Arrays.asList(str2.split(";"));
                                    result = DBC.GrupoProduto.saveGrupoProduto(data);
                                    line = reader.readLine();

                                }

                            }

                            if ("[ESTOQUE_PRODUTOS]".equals(line)) {

                                Log.d("ESTOQUE_PRODUTOS", "ENTROU");

                                DBC.Estoque.cleanEstoque();

                                line = reader.readLine();

                                while (line != null && line.indexOf("[") != 0) {

                                    String str = line;
                                    int pos = str.indexOf("=") + 1;
                                    int len = str.length();
                                    String str2 = str.substring(pos, len);
                                    List<String> data = Arrays.asList(str2.split(";"));
                                    result = DBC.Estoque.saveEstoque(data);
                                    line = reader.readLine();

                                }

                            }

                            if ("[RANKING_PRODUTOS]".equals(line)) {

                                Log.d("RANKING_PRODUTOS", "ENTROU");

                                DBC.Produto.cleanRankingProduto();

                                line = reader.readLine();

                                while (line != null && line.indexOf("[") != 0) {

                                    String str = line;
                                    int pos = str.indexOf("=") + 1;
                                    int len = str.length();
                                    String str2 = str.substring(pos, len);
                                    List<String> data = Arrays.asList(str2.split(";"));
                                    result = DBC.Produto.saveRankingProduto(data);
                                    line = reader.readLine();

                                }

                            }

							if ("[FINANCEIRO]".equals(line)) {

                                Log.d("FINANCEIRO", "ENTROU");

                                DBC.Financeiro.cleanFinanceiro();

                                line = reader.readLine();

                                while (line != null && line.indexOf("[") != 0) {

                                    String str = line;
                                    int pos = str.indexOf("=") + 1;
                                    int len = str.length();
                                    String str2 = str.substring(pos, len);
                                    List<String> data = Arrays.asList(str2.split(";"));
                                    result = DBC.Financeiro.saveFinanceiro(data);
                                    line = reader.readLine();

                                }

                            }
							
							if ("[TITULOS]".equals(line)) {

                                Log.d("TITULOS", "ENTROU");

                                DBC.Titulos.cleanTitulos();

                                line = reader.readLine();

                                while (line != null && line.indexOf("[") != 0) {

                                    String str = line;
                                    int pos = str.indexOf("=") + 1;
                                    int len = str.length();
                                    String str2 = str.substring(pos, len);
                                    List<String> data = Arrays.asList(str2.split(";"));
                                    result = DBC.Titulos.saveTitulos(data);
                                    line = reader.readLine();

                                }

                            }

                            if ("[RETORNO_PEDIDO]".equals(line)) {

                                Log.d("RETORNO_PEDIDO", "ENTROU");

                                DBC.Retorno.cleanRetornoPedido();

                                line = reader.readLine();

                                while (line != null && line.indexOf("[") != 0) {

                                    String str = line;
                                    int pos = str.indexOf("=") + 1;
                                    int len = str.length();
                                    String str2 = str.substring(pos, len);
                                    List<String> data = Arrays.asList(str2.split(";"));
                                    result = DBC.Retorno.saveRetornoPedido(data);
                                    line = reader.readLine();

                                }

                            }
							
							if ("[RETORNO_PEDIDO_ITENS]".equals(line)) {

                                Log.d("RETORNO_PEDIDO_ITENS", "ENTROU");

                                DBC.Retorno.cleanRetornoPedidoItens();

                                line = reader.readLine();

                                while (line != null && line.indexOf("[") != 0) {

                                    String str = line;
                                    int pos = str.indexOf("=") + 1;
                                    int len = str.length();
                                    String str2 = str.substring(pos, len);
                                    List<String> data = Arrays.asList(str2.split(";"));
                                    //TODO //result = DBC.Retorno.saveRetornoPedidoItens(data);
                                    line = reader.readLine();

                                }

                            }

                            if ("[HISTORICO_VENDEDOR]".equals(line)) {

                                Log.d("HOSTORICO_VENDEDOR", "ENTROU");

                                DBC.HistoricoUsuario.cleanHistoricoUsuario();

                                line = reader.readLine();

                                while (line != null && line.indexOf("[") != 0) {

                                    String str = line;
                                    int pos = str.indexOf("=") + 1;
                                    int len = str.length();
                                    String str2 = str.substring(pos, len);
                                    List<String> data = Arrays.asList(str2.split(";"));
                                    result = DBC.HistoricoUsuario.saveHistoricoUsuario(data);
                                    line = reader.readLine();

                                }

                            }

                            if ("[TIPO_PEDIDO]".equals(line)) {

                                Log.d("TIPO_PEDIDO", "ENTROU");

                                DBC.TipoPedido.cleanTipoPedido();

                                line = reader.readLine();

                                while (line != null && line.indexOf("[") != 0) {

                                    String str = line;
                                    int pos = str.indexOf("=") + 1;
                                    int len = str.length();
                                    String str2 = str.substring(pos, len);
                                    List<String> data = Arrays.asList(str2.split(";"));
                                    result = DBC.TipoPedido.saveTipoPedido(data);
                                    line = reader.readLine();

                                }

                            }

                            if ("[UNIDADE_MEDIDA]".equals(line)) {

                                Log.d("UNIDADE_MEDIDA", "ENTROU");

                                DBC.UnidadeMedida.cleanUnidadeMedida();

                                line = reader.readLine();

                                while (line != null && line.indexOf("[") != 0) {

                                    String str = line;
                                    int pos = str.indexOf("=") + 1;
                                    int len = str.length();
                                    String str2 = str.substring(pos, len);
                                    List<String> data = Arrays.asList(str2.split(";"));
                                    result = DBC.UnidadeMedida.saveUnidadeMedida(data);
                                    line = reader.readLine();

                                }

                            }

                            if ("[PRAZO]".equals(line)) {

                                Log.d("PRAZO", "ENTROU");

                                DBC.Prazo.cleanPrazo();

                                line = reader.readLine();

                                while (line != null && line.indexOf("[") != 0) {

                                    String str = line;
                                    int pos = str.indexOf("=") + 1;
                                    int len = str.length();
                                    String str2 = str.substring(pos, len);
                                    List<String> data = Arrays.asList(str2.split(";"));
                                    result = DBC.Prazo.savePrazo(data);
                                    line = reader.readLine();

                                }

                            }

                            if ("[TABELA_PRECO]".equals(line)) {

                                Log.d("TABELA_PRECO", "ENTROU");

                                DBC.TabelaPreco.cleanTabelaPreco();

                                line = reader.readLine();

                                while (line != null && line.indexOf("[") != 0) {

                                    String str = line;
                                    int pos = str.indexOf("=") + 1;
                                    int len = str.length();
                                    String str2 = str.substring(pos, len);
                                    List<String> data = Arrays.asList(str2.split(";"));
                                    result = DBC.TabelaPreco.saveTabelaPreco(data);
                                    line = reader.readLine();

                                }

                            }

                            if ("[LOCALIZACAO]".equals(line)) {

                                line = reader.readLine();

                                while (line != null && line.indexOf("[") != 0) {

                                    String str = line;
                                    int pos = str.indexOf("=") + 1;
                                    int len = str.length();
                                    String str2 = str.substring(pos, len);
                                    List<String> data = Arrays.asList(str2.split(";"));
                                    result = DBC.Localizacao.saveLocalizacao(data);
                                    line = reader.readLine();

                                }

                            }

                            if ("[AVISOS]".equals(line)) {

                                Log.d("AVISOS", "ENTROU");

                                DBC.Avisos.cleanAvisos();

                                line = reader.readLine();

                                while (line != null && line.indexOf("[") != 0) {

                                    String str = line;
                                    int pos = str.indexOf("=") + 1;
                                    int len = str.length();
                                    String str2 = str.substring(pos, len);
                                    List<String> data = Arrays.asList(str2.split(";"));
                                    result = DBC.Avisos.saveAvisos(data);
                                    line = reader.readLine();

                                }

                            }

                            //CONTINUA LENDO ATE ENTRAR NUMA DAS CONDIÇÕES
                            line = reader.readLine();

                        }


                    }catch (Exception e){

                    e.printStackTrace();
                    DBCL.Log.saveLog(e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));

                }


            }

        return result;
    }


}
