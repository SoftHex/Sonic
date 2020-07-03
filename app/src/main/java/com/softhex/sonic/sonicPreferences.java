package com.softhex.sonic;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import java.io.File;

public class sonicPreferences{
    public static final String PREFERENCE_NAME = "com.softhex.sonic_preferences";
    private final SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    private Context mContex;
    private static final String KEEP_LOGGED = "usuarioLogado";
    private static final String USUARIO_ID = "usuarioId";
    private static final String USUARIO_CODIGO_SINC = "usuarioCodigoSinc";
    private static final String USUARIO_ARQUIVO_SINC = "usuarioArquivoSinc";
    private static final String USUARIO_NOME = "usuarioNome";
    private static final String USUARIO_CARGO = "usuarioCargo";
    private static final String USUARIO_IMEI = "usuarioImei";
    private static final String USUARIO_PATH = "usuarioPath";
    private static final String USUARIO_PICTURE = "usuarioPicture";
    private static final String USUARIO_ATIVO = "usuarioAtivo";
    private static final String USUARIO_ADMIN = "usuarioAdmin";
    private static final String USUARIO_ADMIN_ID = "usuarioAdminID";
    private static final String USUARIO_PRIMEIRO_ACESSO = "usuarioPrimeiroAcesso";
    private static final String USUARIO_CONFIRMADO = "usuarioConfirmado";
    private static final String USUARIO_ULTIMA_SINC_ID = "usuarioUltimaSincID";
    private static final String USUARIO_ULTIMA_SINC_NOME = "usuarioUltimaSincNome";
    private static final String FTP_DESCRICAO = "ftpDescricao";
    private static final String FTP_ENDERECO = "ftpEndereco";
    private static final String FTP_USUARIO = "ftpUsuario";
    private static final String FTP_SENHA = "ftpSenha";
    private static final String FTP_ENDERECO_ALTERNATIVO = "ftpEnderecoAlternativo";
    private static final String PRODUTO_ID = "produtoId";
    private static final String PRODUTO_NOME = "produtoNome";
    private static final String PRODUTO_GRUPO = "produtoGrupo";
    private static final String PRODUTO_NOVO = "produtoNovo";
    private static final String PRODUTO_DETALHE = "produtoDetalhe";
    private static final String GRUPO_PRODUTO_FILTRO = "grupoProdutoFiltro";
    private static final String PRODUTO_DATA_CADASTRO = "produtoDataCadastro";
    private static final String GRUPO_PRODUTO_FILTRO_LISTA = "grupoProdutoFiltroLista";
    private static final String GRUPO_PRODUTO_FILTRO_GRID = "grupoProdutoFiltroGrid";
    private static final String EMPRESA_ID = "empresaId";
    private static final String EMPRESA_NOME = "empresaNome";
    private static final String ENVIRONMENT = "pathEnvironment";
    private static final String CLIENTE_ID = "clienteId";
    private static final String CLIENTE_NOME = "clienteNome";
    private static final String CLIENTE_FANTASIA = "clienteFantasia";
    private static final String CLIENTE_RAZAO = "clienteRazao";
    private static final String CLIENTE_GRUPO = "clienteGrupo";
    private static final String CLIENTE_CNPJ_CPF = "clienteCnpjCpf";
    private static final String CLIENTE_TELEFONE = "clienteTelefone";
    private static final String CLIENTE_EMAIL = "clienteEmail";
    private static final String CLIENTE_WHATSAPP = "clienteWhatsApp";
    private static final String CLIENTE_BLOQUEADO = "clienteBloqueado";
    private static final String CLIENTE_LOGRADOURO = "clienteLogradouro";
    private static final String CLIENTE_BAIRRO = "clienteBairro";
    private static final String CLIENTE_MUNICIPIO = "clienteMunicipio";
    private static final String CLIENTE_UF = "clienteUf";
    private static final String CLIENTE_CEP = "clienteCep";
    private static final String CLIENTE_IE = "clienteInscEstadual";
    private static final String CLIENTE_OBS = "clienteObservacao";
    private static final String CLIENTE_ENDERECO_COMPLETO = "clienteEnderecoCompleto";
    private static final String CLIENTE_PATH = "clientePath";
    private static final String CLIENTE_NUNCA_COMPROU = "clienteNuncaComprou";
    private static final String CLIENTE_COMPRAS = "clienteCompras";
    private static final String CLIENTE_TITULOS = "clienteTitulos";
    private static final String GRUPO_CLIENTE_FILTRO_CPF = "grupoClienteFiltroCpf";
    private static final String GRUPO_CLIENTE_FILTRO_CNPJ = "grupoClienteFiltroCnpj";
    private static final String GRUPO_CLIENTE_FILTRO_PADRAO = "grupoClienteFiltroPadrao";
    private static final String SAUDACAO = "saudacao";
    private static final String MATRIZ_NOME = "matrizNome";
    private static final String MATRIZ_DESCRICAO = "matrizDescricao";
    private static final String MATRIZ_DATA_FUNDACAO = "matrizDataFundacao";
    private static final String MATRIZ_ENDERECO = "matrizEndereco";
    private static final String MATRIZ_BAIRRO = "matrizBairro";
    private static final String MATRIZ_MUNICIPIO = "matrizMunicipio";
    private static final String MATRIZ_UF = "matrizUF";
    private static final String MATRIZ_CEP = "matrizCep";
    private static final String MATRIZ_FONE = "matrizFone";
    private static final String MATRIZ_WHATS = "matrizWhatsapp";
    private static final String MATRIZ_EMAIL = "matrizEmail";
    private static final String MATRIZ_SITE = "matrizSite";
    private static final String MATRIZ_PICTURE = "matrizPicture";
    private static final String MATRIZ_PATH = "matrizPath";
    private static final String GERAL_LISTAGEM_COMPLETA = "listagemCompleta";
    private static final String GERAL_HOME_CHART_TYPE = "homeChartType";
    private static final String GERAL_TIPO_HORA = "tipoHora";
    private static final String GERAL_SITE = "site";
    private static final String GERAL_FIRST_SINC = "sincFirst";
    private static final String GERAL_ERROR = "geralError";
    private static final String GERAL_DATA_RANGE = "geralDataRange";
    private static final String GERAL_DATA_SEARCH = "geralDataSearch";
    private static final String GERAL_TERM_SEARCH = "geralTermSearch";
    private static final String SINC_REFRESH = "sincRefresh";
    private static final String SINC_DOWNLOAD_TYPE = "sincDownloadType";
    private static final String SINC_CALLED_ACTIVITY = "sincCalledActivity";
    private static final String SINC_FIRST = "sincFirst";
    private static final String SINC_DRAWER_REFRESH = "sincDrawerRefresh";
    private static final String SINC_HOME_REFRESH = "sincHomeRefresh";
    private static final String ROTA_ID = "rotaId";
    private static final String ROTA_ITEM_POSITION = "rotaItemPosition";
    private static final String ROTA_ADDRESS_MAP = "rotaAddressMap";
    private static final String ROTA_START_TIME = "rotaStartTime";
    private static final String ROTA_REFRESH = "rotaRefresh";
    private static final String ROTA_EM_ATENDIMENTO = "rotaEmAtendimento";
    private static final String ROTA_EM_ATENDIMENTO_CLIENTE = "rotaEmAtendimentoCliente";
    private static final String ROTA_EM_ATENDIMENTO_EMPRESA = "rotaEmAtendimentoEmpresa";
    private static final String ROTA_CANCELADA = "rotaCancelada";
    private static final String ROTA_STATUS = "rotaStatus";
    private static final String ROTA_SITUACAO = "rotaSituacao";
    private static final String ROTA_DATA_HORA = "rotaDataHora";
    private static final String ROTA_PESSOAL = "rotaPessoal";
    private static final String ROTA_PESSOAL_ID = "rotaPessoalId";
    private static final String ROTA_PESSOAL_REFRESH = "rotaPessoalRefresh";
    private static final String ROTA_PESSOAL_FROM_CLIENTE = "rotaPessoalFromCliente";
    private static final String ROTA_PESSOAL_ADDING = "rotaPessoalAdding";
    private static final String ROTA_CLIENTE_PICKED= "rotaClientePicked";
    private static final String ROTA_PESSOAL_DATA = "rotaPessoalData";
    private static final String ROTA_PESSOAL_HORA = "rotaPessoalHora";
    private static final String ROTA_TERM_SEARCH = "rotaTermSearch";


    Ftp Ftp = new Ftp();
    Users Users = new Users();
    Path Path = new Path();
    Clientes Clientes = new Clientes();
    GrupoCliente GrupoCliente = new GrupoCliente();
    Matriz Matriz = new Matriz();
    Produtos Produtos = new Produtos();
    GrupoProduto GrupoProduto = new GrupoProduto();
    Util Util = new Util();
    Geral Geral = new Geral();
    Sincronizacao Sincronizacao = new Sincronizacao();
    Rota Rota = new Rota();
    RotaPessoal RotaPessoal = new RotaPessoal();

    public sonicPreferences(Context context) {
        sharedpreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        mContex = context;
    }

    public class Ftp{
        public void setDescricao(String value){
            editor = sharedpreferences.edit();
            editor.putString(FTP_DESCRICAO, value);
            editor.apply();
        }
        public String getDescricao(){
            return sharedpreferences.getString(FTP_DESCRICAO, "");
        }
        public void setEndereco(String value){
            editor = sharedpreferences.edit();
            editor.putString(FTP_ENDERECO, value);
            editor.apply();
        }
        public String getEndereco(){
            return sharedpreferences.getString(FTP_ENDERECO, "");
        }

        public void setEnderecoAlternativo(boolean value){
            editor = sharedpreferences.edit();
            editor.putBoolean(FTP_ENDERECO_ALTERNATIVO, value);
            editor.apply();
        }
        public boolean getEnderecoAlternativo(){
            return sharedpreferences.getBoolean(FTP_ENDERECO_ALTERNATIVO, false);
        }

        public void setUsuario(String value){
            editor = sharedpreferences.edit();
            editor.putString(FTP_USUARIO, value);
            editor.apply();
        }
        public String getUsuario(){
            return sharedpreferences.getString(FTP_USUARIO, "");
        }
        public void setSenha(String value){
            editor = sharedpreferences.edit();
            editor.putString(FTP_SENHA, value);
            editor.apply();
        }
            public String getSenha(){
            return sharedpreferences.getString(FTP_SENHA, "");
        }
    }
    public class Produtos{

        public void setProdutoId(int value){
            editor = sharedpreferences.edit();
            editor.putInt(PRODUTO_ID, value);
            editor.apply();
        }
        public int getProdutoId(){
            return sharedpreferences.getInt(PRODUTO_ID, 0);
        }

        public void setProdutoNome(String value){
            editor = sharedpreferences.edit();
            editor.putString(PRODUTO_NOME, value);
            editor.apply();
        }

        public String getProdutoNome(){
            return sharedpreferences.getString(PRODUTO_NOME, "");
        }

        public void setProdutoGrupo(String value){
            editor = sharedpreferences.edit();
            editor.putString(PRODUTO_GRUPO, value);
            editor.apply();
        }
        public String getProdutoGrupo(){
            return sharedpreferences.getString(PRODUTO_GRUPO, "");
        }
        public void setProdutoDataCadastro(String value){
            editor = sharedpreferences.edit();
            editor.putString(PRODUTO_DATA_CADASTRO, value);
            editor.apply();
        }
        public void setDetalhe(String value){
            editor = sharedpreferences.edit();
            editor.putString(PRODUTO_DETALHE, value);
            editor.apply();
        }
        public String getDetalhe(){
            return sharedpreferences.getString(PRODUTO_DETALHE, "");
        }
        public String getProdutoDataCadastro(){
            return sharedpreferences.getString(PRODUTO_DATA_CADASTRO, "");
        }
        public void setProdutoNovo(Boolean value){
            editor = sharedpreferences.edit();
            editor.putBoolean(PRODUTO_NOVO, value);
            editor.apply();
        }
        public Boolean getProdutoNovo(){
            return sharedpreferences.getBoolean(PRODUTO_NOVO , false);
        }
        public String getCatalogoColunas(){
            return sharedpreferences.getString(mContex.getResources().getString(R.string.catalogoQtde), mContex.getResources().getString(R.string.catalogoQtde));
        }
        public void setCatalogoColunas(String value){
            editor = sharedpreferences.edit();
            editor.putString(mContex.getResources().getString(R.string.catalogoQtde), value);
            editor.apply();
        }
        public String getDiasNovo(){
            return sharedpreferences.getString(mContex.getResources().getString(R.string.produtoNovoDias), mContex.getResources().getString(R.string.prefProdutoNovoDefault));
        }
        public void setDiasNovo(String value){
            editor = sharedpreferences.edit();
            editor.putString(mContex.getResources().getString(R.string.produtoNovoDias), value);
            editor.apply();
        }
    }
    public class GrupoProduto{
        public void setFiltroLista(String value){
            editor = sharedpreferences.edit();
            editor.putString(GRUPO_PRODUTO_FILTRO_LISTA, value);
            editor.apply();
        }
        public String getFiltroLista(){
            return sharedpreferences.getString(GRUPO_PRODUTO_FILTRO_LISTA, "");
        }
        public void setFiltroGrid(String value){
            editor = sharedpreferences.edit();
            editor.putString(GRUPO_PRODUTO_FILTRO_GRID, value);
            editor.apply();
        }
        public String getFiltroGrid(){
            return sharedpreferences.getString(GRUPO_PRODUTO_FILTRO_GRID, "TODOS");
        }
    }
    public class Matriz {

        public void setNome(String value){
            editor = sharedpreferences.edit();
            editor.putString(MATRIZ_NOME, value);
            editor.apply();
        }
        public String getNome(){
            return sharedpreferences.getString(MATRIZ_NOME, "");
        }
        public void setDescricao(String value){
            editor = sharedpreferences.edit();
            editor.putString(MATRIZ_DESCRICAO, value);
            editor.apply();
        }
        public String getDescricao(){
            return sharedpreferences.getString(MATRIZ_DESCRICAO, "");
        }
        public void setDataFundacao(String value){
            editor = sharedpreferences.edit();
            editor.putString(MATRIZ_DATA_FUNDACAO, value);
            editor.apply();
        }
        public String getDataFundacao(){
            return sharedpreferences.getString(MATRIZ_DATA_FUNDACAO, "");
        }
        public void setEndereco(String value){
            editor = sharedpreferences.edit();
            editor.putString(MATRIZ_ENDERECO, value);
            editor.apply();
        }
        public String getEndereco(){
            return sharedpreferences.getString(MATRIZ_ENDERECO, "");
        }
        public void setBairro(String value){
            editor = sharedpreferences.edit();
            editor.putString(MATRIZ_BAIRRO, value);
            editor.apply();
        }
        public String getBairro(){
            return sharedpreferences.getString(MATRIZ_BAIRRO, "");
        }
        public void setMunicipio(String value){
            editor = sharedpreferences.edit();
            editor.putString(MATRIZ_MUNICIPIO, value);
            editor.apply();
        }
        public String getMunicipio(){
            return sharedpreferences.getString(MATRIZ_MUNICIPIO, "");
        }
        public void setUF(String value){
            editor = sharedpreferences.edit();
            editor.putString(MATRIZ_UF, value);
            editor.apply();
        }
        public String getUF(){
            return sharedpreferences.getString(MATRIZ_UF, "");
        }
        public void setCep(String value){
            editor = sharedpreferences.edit();
                editor.putString(MATRIZ_CEP, value);
            editor.apply();
        }
        public String getCep(){
            return sharedpreferences.getString(MATRIZ_CEP, "");
        }
        public void setFone(String value){
            editor = sharedpreferences.edit();
            editor.putString(MATRIZ_FONE, value);
            editor.apply();
        }
        public String getFone(){
            return sharedpreferences.getString(MATRIZ_FONE, "");
        }

        public void setWhats(String value) {
            editor = sharedpreferences.edit();
            editor.putString(MATRIZ_WHATS, value);
            editor.apply();
        }
        public String getWhats(){
            return sharedpreferences.getString(MATRIZ_WHATS, "");
        }
        public void setEmail(String value){
            editor = sharedpreferences.edit();
            editor.putString(MATRIZ_EMAIL, value);
            editor.apply();
        }
        public String getEmail(){
            return sharedpreferences.getString(MATRIZ_EMAIL, "");
        }
        public void setSite(String value){
            editor = sharedpreferences.edit();
            editor.putString(MATRIZ_SITE, value);
            editor.apply();
        }
        public String getSite(){
            return sharedpreferences.getString(MATRIZ_SITE, "");
        }

        public String getPicture(){
            return sonicConstants.LOCAL_IMG_CATALOGO+"matriz.JPG";
        }
    }
    public class Clientes{
        public void setId(int value){
            editor = sharedpreferences.edit();
            editor.putInt(CLIENTE_ID, value);
            editor.apply();

        }
        public int getId(){
            return sharedpreferences.getInt(CLIENTE_ID, 0);
        }
        public void setNome(String value){
            editor = sharedpreferences.edit();
            editor.putString(CLIENTE_NOME, value);
            editor.apply();
        }
        public String getNome(){
            return sharedpreferences.getString(CLIENTE_NOME, null);
        }
        public void setRazao(String value){
            editor = sharedpreferences.edit();
            editor.putString(CLIENTE_RAZAO, value);
            editor.apply();
        }
        public String getRazao(){
            return sharedpreferences.getString(CLIENTE_RAZAO, null);
        }
        public void setFantasia(String value){
            editor = sharedpreferences.edit();
            editor.putString(CLIENTE_FANTASIA, value);
            editor.apply();
        }
        public String getFantasia(){
            return sharedpreferences.getString(CLIENTE_FANTASIA, null);
        }
        public void setGrupo(String value){
            editor = sharedpreferences.edit();
            editor.putString(CLIENTE_GRUPO, value);
            editor.apply();
        }
        public String getGrupo(){
            return sharedpreferences.getString(CLIENTE_GRUPO, null);
        }
        public String getClienteExibicao(){
            return sharedpreferences.getString(mContex.getResources().getString(R.string.clienteTipo), mContex.getResources().getString(R.string.prefClienteTipoDefault));
        }

        public void setLogradouro(String value){
            editor = sharedpreferences.edit();
            editor.putString(CLIENTE_LOGRADOURO, value);
            editor.apply();
        }
        public String getLogradouro(){
            return sharedpreferences.getString(CLIENTE_LOGRADOURO, "");
        }
        public void setBairro(String value){
            editor = sharedpreferences.edit();
            editor.putString(CLIENTE_BAIRRO, value);
            editor.apply();
        }
        public String getBairro(){
            return sharedpreferences.getString(CLIENTE_BAIRRO, "");
        }
        public void setMunicipio(String value){
            editor = sharedpreferences.edit();
            editor.putString(CLIENTE_MUNICIPIO, value);
            editor.apply();
        }
        public String getMunicipio(){
            return sharedpreferences.getString(CLIENTE_MUNICIPIO, "");
        }
        public void setUf(String value){
            editor = sharedpreferences.edit();
            editor.putString(CLIENTE_UF, value);
            editor.apply();
        }
        public String getUf(){
            return sharedpreferences.getString(CLIENTE_UF, "");
        }
        public void setCep(String value){
            editor = sharedpreferences.edit();
            editor.putString(CLIENTE_CEP, value);
            editor.apply();
        }
        public String getCep(){
            return sharedpreferences.getString(CLIENTE_CEP, "");
        }
        public void setIe(String value){
            editor = sharedpreferences.edit();
            editor.putString(CLIENTE_IE, value);
            editor.apply();
        }
        public String getIe(){
            return sharedpreferences.getString(CLIENTE_IE, "");
        }
        public void setEnderecoCompleto(String value){
            editor = sharedpreferences.edit();
            editor.putString(CLIENTE_ENDERECO_COMPLETO, value);
            editor.apply();
        }
        public String getEnderecoCompleto(){
            return sharedpreferences.getString(CLIENTE_ENDERECO_COMPLETO, null);
        }
        public void setCnpjCpf(String value){
            editor = sharedpreferences.edit();
            editor.putString(CLIENTE_CNPJ_CPF, value);
            editor.apply();
        }
        public String getCnpjCpf(){
            return sharedpreferences.getString(CLIENTE_CNPJ_CPF, null);
        }
        public void  setClienteExibicao(String value){
            editor = sharedpreferences.edit();
            editor.putString(mContex.getResources().getString(R.string.clienteTipo), value);
            editor.apply();
        }
        public void setTelefone(String value){
            editor = sharedpreferences.edit();
            editor.putString(CLIENTE_TELEFONE, value);
            editor.apply();
        }
        public String getTelefone(){
            return sharedpreferences.getString(CLIENTE_TELEFONE, null);
        }
        public void setEmail(String value){
            editor = sharedpreferences.edit();
            editor.putString(CLIENTE_EMAIL, value);
            editor.apply();
        }
        public String getEmail(){
            return sharedpreferences.getString(CLIENTE_EMAIL, null);
        }
        public void setObs(String value){
            editor = sharedpreferences.edit();
            editor.putString(CLIENTE_OBS, value);
            editor.apply();
        }
        public String getObs(){
            return sharedpreferences.getString(CLIENTE_OBS, null);
        }
        public void setWhatsApp(String value){
            editor = sharedpreferences.edit();
            editor.putString(CLIENTE_WHATSAPP, value);
            editor.apply();
        }
        public String getWhatsApp(){
            return sharedpreferences.getString(CLIENTE_WHATSAPP, null);
        }
        public void setClienteSemCompra(Boolean value){
            editor = sharedpreferences.edit();
            editor.putBoolean(mContex.getResources().getString(R.string.clienteSemCompra), value);
            editor.apply();
        }
        public void getBloqueado(boolean value){
            editor = sharedpreferences.edit();
            editor.putBoolean(CLIENTE_BLOQUEADO, value);
            editor.apply();
        }
        public Boolean getBloquado(){
            return sharedpreferences.getBoolean(CLIENTE_BLOQUEADO,false);
        }
        public void setClienteNuncaComprou(Boolean value){
            editor = sharedpreferences.edit();
            editor.putBoolean(CLIENTE_NUNCA_COMPROU, value);
            editor.apply();
        }
        public  Boolean getClienteNuncaComprou(){
            return sharedpreferences.getBoolean(CLIENTE_NUNCA_COMPROU, false);
        }
        public Boolean getClienteSemCompra(){
            return sharedpreferences.getBoolean(mContex.getResources().getString(R.string.clienteSemCompra),true);
        }
        public void setTituloEmTraso(Boolean value){
            editor = sharedpreferences.edit();
            editor.putBoolean(mContex.getResources().getString(R.string.tituloEmAtraso), value);
            editor.apply();
        }
        public Boolean getTituloEmAtraso(){
            return sharedpreferences.getBoolean(mContex.getResources().getString(R.string.tituloEmAtraso), true);
        }
        public void setCompras(int value){
            editor = sharedpreferences.edit();
            editor.putInt(CLIENTE_COMPRAS, value);
            editor.apply();

        }
        public int getCompras(){
            return sharedpreferences.getInt(CLIENTE_COMPRAS, 0);
        }

        public void setTitulos(int value){
            editor = sharedpreferences.edit();
            editor.putInt(CLIENTE_TITULOS, value);
            editor.apply();
        }
        public int getTitulos(){
            return sharedpreferences.getInt(CLIENTE_TITULOS, 0);
        }
    }
    public class GrupoCliente{
        public void setFiltroCpf(String value){
            editor = sharedpreferences.edit();
            editor.putString(GRUPO_CLIENTE_FILTRO_CPF, value);
            editor.apply();
        }
        public String getFiltroCpf(){
            return sharedpreferences.getString(GRUPO_CLIENTE_FILTRO_CPF, "TODOS");
        }
        public void setFiltroCnpj(String value){
            editor = sharedpreferences.edit();
            editor.putString(GRUPO_CLIENTE_FILTRO_CNPJ, value);
            editor.apply();
        }
        public String getFiltroCnpj(){
            return sharedpreferences.getString(GRUPO_CLIENTE_FILTRO_CNPJ, "TODOS");
        }
        public void setFiltroPadrao(String value){
            editor = sharedpreferences.edit();
            editor.putString(GRUPO_CLIENTE_FILTRO_PADRAO, value);
            editor.apply();
        }
        public String getFiltroPadrao(){
            return sharedpreferences.getString(GRUPO_CLIENTE_FILTRO_PADRAO, "TODOS");
        }
    }
    public class Users {

        public void setConfirmado(Boolean value){
            editor = sharedpreferences.edit();
            editor.putBoolean(USUARIO_CONFIRMADO, value);
            editor.apply();
        }
        public boolean getConfirmado(){
            return sharedpreferences.getBoolean(USUARIO_CONFIRMADO, false);
        }

        public void setAdmin(Boolean value){
            editor = sharedpreferences.edit();
            editor.putBoolean(USUARIO_ADMIN, value);
            editor.apply();
        }
        public boolean getAdmin(){
            return sharedpreferences.getBoolean(USUARIO_ADMIN, false);
        }

        public void setPrimeiroAcessoID(int value){
            editor = sharedpreferences.edit();
            editor.putInt(USUARIO_PRIMEIRO_ACESSO, value);
            editor.apply();
        }
        public int getPrimeiroAcessoID(){
            return sharedpreferences.getInt(USUARIO_PRIMEIRO_ACESSO, 0);
        }

        public void setAdminID(int value){
            editor = sharedpreferences.edit();
            editor.putInt(USUARIO_ADMIN_ID, value);
            editor.apply();
        }
        public int getAdminID(){
            return sharedpreferences.getInt(USUARIO_ADMIN_ID, 0);
        }

        public void setAtivo(Boolean value){
            editor = sharedpreferences.edit();
            editor.putBoolean(USUARIO_ATIVO, value);
            editor.apply();
        }
        public boolean getAtivo(){
            return sharedpreferences.getBoolean(USUARIO_ATIVO, false);
        }

        public void setUsuarioImei(String value){
            editor = sharedpreferences.edit();
            editor.putString(USUARIO_IMEI, value);
            editor.apply();
        }
        public String getUsuarioImei(){
            return sharedpreferences.getString(USUARIO_IMEI, "");
        }
        public void setLogado(Boolean value){
            editor = sharedpreferences.edit();
            editor.putBoolean(KEEP_LOGGED, value);
            editor.apply();
        }
        public boolean getLogado(){
            return sharedpreferences.getBoolean(KEEP_LOGGED, true);
        }
        public void setUsuarioId(int id){
            editor = sharedpreferences.edit();
            editor.putInt(USUARIO_ID, id);
            editor.apply();
        }
        public int getUsuarioId(){
            return sharedpreferences.getInt(USUARIO_ID,0);
        }

        public void setCodigoSinc(int codigo){
            editor = sharedpreferences.edit();
            editor.putString(USUARIO_CODIGO_SINC, String.format("%5s",codigo).replace(" ", "0"));
            editor.apply();
        }
        public String getCodigoSinc(){
            return sharedpreferences.getString(USUARIO_CODIGO_SINC,"");
        }
        public void setArquivoSinc(int codigo){
            editor = sharedpreferences.edit();
            editor.putString(USUARIO_ARQUIVO_SINC, String.format("%5s",codigo).replace(" ", "0")+".TXT");
            editor.apply();
        }
        public String getArquivoSinc(){
            return sharedpreferences.getString(USUARIO_ARQUIVO_SINC,"");
        }
        public void setUsuarioNome(String nome){
            editor = sharedpreferences.edit();
            editor.putString(USUARIO_NOME, nome);
            editor.apply();
        }
        public String getUsuarioNome(){
            return sharedpreferences.getString(USUARIO_NOME, "");
        }
        public void setUsuarioCargo(String cargo){
            editor = sharedpreferences.edit();
            editor.putString(USUARIO_CARGO, cargo);
            editor.apply();
        }
        public String getUsuarioCargo(){
            return sharedpreferences.getString(USUARIO_CARGO, "");
        }
        public void setEmpresaId(int id){
            editor = sharedpreferences.edit();
            editor.putInt(EMPRESA_ID, id);
            editor.apply();
        }
        public int getEmpresaId(){
            return sharedpreferences.getInt(EMPRESA_ID, 0);
        }
        public void setEmpresaNome(String nome){
            editor = sharedpreferences.edit();
            editor.putString(EMPRESA_NOME, nome);
            editor.apply();
        }
        public String getEmpresaNome(){
            return sharedpreferences.getString(EMPRESA_NOME, "");
        }
        public void setPicture(){
            editor = sharedpreferences.edit();
            editor.putString(USUARIO_PICTURE, sharedpreferences.getInt(EMPRESA_ID,0)+"_"+sharedpreferences.getInt(USUARIO_ID,0)+".JPG");
            editor.apply();
        }
        public String getPicture(int empresa){
            return empresa+"_"+sharedpreferences.getInt(USUARIO_ID,0)+".JPG";
        }

        public void setUltimaSincID(int value){
            editor = sharedpreferences.edit();
            editor.putInt(USUARIO_ULTIMA_SINC_ID, value);
            editor.apply();
        }

        public int getUltimaSincID(){
            return sharedpreferences.getInt(USUARIO_ULTIMA_SINC_ID, 0);
        }

        public void setUltimaSincNome(String value){
            editor = sharedpreferences.edit();
            editor.putString(USUARIO_ULTIMA_SINC_NOME, value);
            editor.apply();
        }

        public String getUltimaSincNome(){
            return sharedpreferences.getString(USUARIO_ULTIMA_SINC_NOME, "");
        }
    }
    public class Path{
        private void setEnvironment(){
            editor = sharedpreferences.edit();
            editor.putString(ENVIRONMENT, Environment.getExternalStorageDirectory().getPath());
            editor.apply();
        }
        public String getEnvironmente(){
            setEnvironment();
            return sharedpreferences.getString(ENVIRONMENT, Environment.getExternalStorageDirectory().getPath());
        }
        private void setProfilePath(){
            editor = sharedpreferences.edit();
            editor.putString(USUARIO_PATH, sonicConstants.LOCAL_IMG_USUARIO);
            editor.apply();
        }
        public String getProfilePath(){
            setProfilePath();
            return sharedpreferences.getString(USUARIO_PATH, sonicConstants.LOCAL_IMG_USUARIO);
        }
        private void setClientePath(){
            editor = sharedpreferences.edit();
            editor.putString(CLIENTE_PATH, sonicConstants.LOCAL_IMG_CLIENTES);
            editor.apply();
        }
        public String getClientePath(){
            setProfilePath();
            return sharedpreferences.getString(CLIENTE_PATH, sonicConstants.LOCAL_IMG_CLIENTES);
        }
    }
    public class Util{
        private void setSaudacao(){
            editor = sharedpreferences.edit();
            editor.putString(SAUDACAO, sonicUtils.saudacao());
            editor.apply();
        }
        public String getSaudacao(){
            setSaudacao();
        return sharedpreferences.getString(SAUDACAO, "Olá, ");
        }
        public void clearPreferences() {

            File sharedPreferenceFile = new File("/data/data/"+ mContex.getPackageName()+ "/shared_prefs/");
            File[] listFiles = sharedPreferenceFile.listFiles();
            for (File file : listFiles) {
                file.delete();
            }
            SharedPreferences.Editor p = mContex.getSharedPreferences(PREFERENCE_NAME, 0).edit();
            p.clear();
            p.apply();

        }
        public void deleteCache() {
            try {
                File dir = mContex.getCacheDir();
                deleteDir(dir);
            } catch (Exception e) {}
        }
        private boolean deleteDir(File dir) {
            if (dir != null && dir.isDirectory()) {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++) {
                    boolean success = deleteDir(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
                return dir.delete();
            } else if(dir!= null && dir.isFile()) {
                return dir.delete();
            } else {
                return false;
            }
        }
    }
    public class Geral{
        public void setListagemCompleta(boolean value){
            editor = sharedpreferences.edit();
            editor.putBoolean(GERAL_LISTAGEM_COMPLETA, value);
            editor.apply();
        }
        public boolean getListagemCompleta(){
            return sharedpreferences.getBoolean(GERAL_LISTAGEM_COMPLETA, false);
        }
        public void setDataRange(String value){
            editor = sharedpreferences.edit();
            editor.putString(GERAL_DATA_RANGE, value);
            editor.apply();
        }
        public String getDataRange(){
            return sharedpreferences.getString(GERAL_DATA_RANGE, "");
        }
        public void setDataSearch(boolean value){
            editor = sharedpreferences.edit();
            editor.putBoolean(GERAL_DATA_SEARCH, value);
            editor.apply();
        }
        public boolean getDataSearch(){
            return sharedpreferences.getBoolean(GERAL_DATA_SEARCH, false);
        }
        public void setTermSearch(String value){
            editor = sharedpreferences.edit();
            editor.putString(GERAL_TERM_SEARCH, value);
            editor.apply();
        }
        public String getTermSearch(){
            return sharedpreferences.getString(GERAL_TERM_SEARCH, "TUDO");
        }

        public void setHomeChartType(String value){
            editor = sharedpreferences.edit();
            editor.putString(GERAL_HOME_CHART_TYPE, value);
            editor.apply();
        }
        public String getHomeChartType(){
            return sharedpreferences.getString(GERAL_HOME_CHART_TYPE, "Linhas");
        }
        public void setError(String value){
            editor = sharedpreferences.edit();
            editor.putString(GERAL_ERROR, value);
            editor.apply();
        }
        public String getError(){
            return sharedpreferences.getString(GERAL_ERROR, "Linhas");
        }
        public void setTipoHora(String value){
            editor = sharedpreferences.edit();
            editor.putString(GERAL_TIPO_HORA, value);
            editor.apply();
        }
        public String getTipoHora(){
            return sharedpreferences.getString(GERAL_TIPO_HORA, "24 Horas");
        }
        public void setSite(String value){
            editor = sharedpreferences.edit();
            editor.putString(GERAL_SITE, value);
            editor.apply();
        }
        public String getSite(){
            return sharedpreferences.getString(GERAL_SITE, "");
        }
        public void setFirstSinc(boolean value){
            editor = sharedpreferences.edit();
            editor.putBoolean(GERAL_FIRST_SINC, value);
            editor.apply();
        }
        public boolean getFirstSinc(){
            return sharedpreferences.getBoolean(GERAL_FIRST_SINC, true);
        }
    }
    public class Sincronizacao{
        public void setSincRefresh(boolean value){
            editor = sharedpreferences.edit();
            editor.putBoolean(SINC_REFRESH, value);
            editor.apply();
        }
        public boolean getSincRefresh(){
            return sharedpreferences.getBoolean(SINC_REFRESH, false);
        }
        public void setDownloadType(String value){
            editor = sharedpreferences.edit();
            editor.putString(SINC_DOWNLOAD_TYPE,    value);
            editor.apply();
        }
        public String getDownloadType(){
            return sharedpreferences.getString(SINC_DOWNLOAD_TYPE, "DADOS");
        }
        public void setCalledActivity(String value){
            editor = sharedpreferences.edit();
            editor.putString(SINC_CALLED_ACTIVITY, value);
            editor.apply();
        }
        public String getCalledActivity(){
            return sharedpreferences.getString(SINC_CALLED_ACTIVITY, "");
        }
        public void setDrawerRefresh(boolean value){
            editor = sharedpreferences.edit();
            editor.putBoolean(SINC_DRAWER_REFRESH, value);
            editor.apply();
        }
        public boolean getDrawerRefresh(){
            return sharedpreferences.getBoolean(SINC_DRAWER_REFRESH, false);
        }
        public void setHomeRefresh(boolean value){
            editor = sharedpreferences.edit();
            editor.putBoolean(SINC_HOME_REFRESH, value);
            editor.apply();
        }
        public boolean getHomeRefresh(){
            return sharedpreferences.getBoolean(SINC_HOME_REFRESH ,false);
        }
    }
    public class Rota{
        public void setPessoal(boolean value){
            editor = sharedpreferences.edit();
            editor.putBoolean(ROTA_PESSOAL, value);
            editor.apply();
        }
        public boolean getPessoal(){
            return sharedpreferences.getBoolean(ROTA_PESSOAL, false);
        }
        public void setStartTime(long value){
            editor = sharedpreferences.edit();
            editor.putLong(ROTA_START_TIME, value);
            editor.apply();
        }
        public long getStartTime(){
            return sharedpreferences.getLong(ROTA_START_TIME, 0);
        }
        public void setCodigo(int value){
            editor = sharedpreferences.edit();
            editor.putInt(ROTA_ID, value);
            editor.apply();
        }
        public int getCodigo(){
            return sharedpreferences.getInt(ROTA_ID, 0);
        }
        public void setAddressMap(String value){
            editor = sharedpreferences.edit();
            editor.putString(ROTA_ADDRESS_MAP, value);
            editor.apply();
        }
        public String getAddressMap(){
            return sharedpreferences.getString(ROTA_ADDRESS_MAP, "");
        }

        public void setItemPosition(int value){
            editor = sharedpreferences.edit();
            editor.putInt(ROTA_ITEM_POSITION, value);
            editor.apply();
        }

        public void setTermSearch(String value){
            editor = sharedpreferences.edit();
            editor.putString(ROTA_TERM_SEARCH, value);
            editor.apply();
        }
        public String getTermSearch(){
            return sharedpreferences.getString(ROTA_TERM_SEARCH, "TUDO");
        }

        public int getItemPosition(){
            return sharedpreferences.getInt(ROTA_ITEM_POSITION, 0);
        }


        public void setRefresh(boolean value){
            editor = sharedpreferences.edit();
            editor.putBoolean(ROTA_REFRESH, value);
            editor.apply();
        }
        public boolean getRefresh(){
            return sharedpreferences.getBoolean(ROTA_REFRESH, false);
        }

        public void setEmAtendimento(boolean value){
            editor = sharedpreferences.edit();
            editor.putBoolean(ROTA_EM_ATENDIMENTO, value);
            editor.apply();
        }
        public boolean getEmAtendimento(){
            return sharedpreferences.getBoolean(ROTA_EM_ATENDIMENTO, false);
        }

        public void setEmAtendimentoCliente(String value){
            editor = sharedpreferences.edit();
            editor.putString(ROTA_EM_ATENDIMENTO_CLIENTE, value);
            editor.apply();
        }
        public String getEmAtendimentoCliente(){
            return sharedpreferences.getString(ROTA_EM_ATENDIMENTO_CLIENTE, "");
        }

        public void setEmAtendimentoEmpresa(String value){
            editor = sharedpreferences.edit();
            editor.putString(ROTA_EM_ATENDIMENTO_EMPRESA, value);
            editor.apply();
        }
        public String getEmAtendimentoEmpresa(){
            return sharedpreferences.getString(ROTA_EM_ATENDIMENTO_EMPRESA, "");
        }

        public void setCancelada(boolean value){
            editor = sharedpreferences.edit();
            editor.putBoolean(ROTA_CANCELADA, value);
            editor.apply();
        }
        public boolean getCancelada(){
            return sharedpreferences.getBoolean(ROTA_CANCELADA, false);
        }

        public void setStatus(int value){
            editor = sharedpreferences.edit();
            editor.putInt(ROTA_STATUS, value);
            editor.apply();
        }
        public int getStatus(){
            return sharedpreferences.getInt(ROTA_STATUS, 0);
        }

        public void setSituacao(int value){
            editor = sharedpreferences.edit();
            editor.putInt(ROTA_SITUACAO, value);
            editor.apply();
        }
        public int getSituacao(){
            return sharedpreferences.getInt(ROTA_SITUACAO, 0);
        }


        public void setDataHora(String value){
            editor = sharedpreferences.edit();
            editor.putString(ROTA_DATA_HORA, value);
            editor.apply();
        }
        public String getDataHora(){
            return sharedpreferences.getString(ROTA_DATA_HORA, "");
        }

    }

    public class RotaPessoal{

        public void setCodigo(int value){
            editor = sharedpreferences.edit();
            editor.putInt(ROTA_PESSOAL_ID, value);
            editor.apply();
        }
        public int getCodigo(){
            return sharedpreferences.getInt(ROTA_PESSOAL_ID, 0);
        }
        public void setAddressMap(String value){
            editor = sharedpreferences.edit();
            editor.putString(ROTA_PESSOAL_ID, value);
            editor.apply();
        }

        public void setRefresh(boolean value){
            editor = sharedpreferences.edit();
            editor.putBoolean(ROTA_PESSOAL_REFRESH, value);
            editor.apply();
        }
        public boolean getRefresh(){
            return sharedpreferences.getBoolean(ROTA_PESSOAL_REFRESH, false);
        }

        public void setAddFromCliente(boolean value){
            editor = sharedpreferences.edit();
            editor.putBoolean(ROTA_PESSOAL_FROM_CLIENTE, value);
            editor.apply();
        }

        public boolean getAddFromCliente(){
            return sharedpreferences.getBoolean(ROTA_PESSOAL_FROM_CLIENTE, false);
        }

        public void setAdding(boolean value){
            editor = sharedpreferences.edit();
            editor.putBoolean(ROTA_PESSOAL_ADDING, value);
            editor.apply();
        }

        public boolean getAdding(){
            return sharedpreferences.getBoolean(ROTA_PESSOAL_ADDING, false);
        }

        public void setClientePicked(boolean value){
            editor = sharedpreferences.edit();
            editor.putBoolean(ROTA_CLIENTE_PICKED, value);
            editor.apply();
        }

        public boolean getClientePicked(){
            return sharedpreferences.getBoolean(ROTA_CLIENTE_PICKED, false);
        }

        public void setAddData(String value){
            editor = sharedpreferences.edit();
            editor.putString(ROTA_PESSOAL_DATA, value);
            editor.apply();
        }
        public String getAddData(){
            return sharedpreferences.getString(ROTA_PESSOAL_DATA, "");
        }

        public void setAddHora(String value){
            editor = sharedpreferences.edit();
            editor.putString(ROTA_PESSOAL_HORA, value);
            editor.apply();
        }
        public String getAddHora(){
            return sharedpreferences.getString(ROTA_PESSOAL_HORA, "");
        }

        public void setAdd(boolean value){
            editor = sharedpreferences.edit();
            editor.putBoolean(ROTA_PESSOAL, value);
            editor.apply();
        }

        public boolean getAdd(){
            return sharedpreferences.getBoolean(ROTA_PESSOAL, false);
        }

    }
}
