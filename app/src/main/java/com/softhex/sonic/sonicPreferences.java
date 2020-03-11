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
    private static final String KEEP_LOGGED = "keepLogged";
    private static final String USUARIO_ID = "usuarioId";
    private static final String USUARIO_NOME = "usuarioNome";
    private static final String USUARIO_CARGO = "usuarioCargo";
    private static final String USUARIO_IMEI = "usuarioImei";
    private static final String USUARIO_PATH = "userPath";
    private static final String PRODUTO_ID = "produtoId";
    private static final String PRODUTO_NOME = "produtoNome";
    private static final String PRODUTO_GRUPO = "produtoGrupo";
    private static final String PRODUTO_NOVO = "produtoNovo";
    private static final String PRODUTO_DETALHE = "produtoDetalhe";
    private static final String PRODUTO_DATA_CADASTRO = "produtoDataCadastro";
    private static final String EMPRESA_ID = "empresaId";
    private static final String EMPRESA_NOME = "empresaNome";
    private static final String ENVIRONMENT = "pathEnvironment";
    private static final String CLIENTE_ID = "clienteId";
    private static final String CLIENTE_NOME = "clienteNome";
    private static final String CLIENTE_GRUPO = "clienteGrupo";
    private static final String CLIENTE_PATH = "clientePath";
    private static final String SAUDACAO = "saudacao";
    private static final String GRUPO_EMPRESA_NOME = "grupoEmpresaNome";
    private static final String GRUPO_EMPRESA_DESCRICAO = "grupoEmpresaDescricao";
    private static final String GRUPO_EMPRESA_DATA_FUNDACAO = "grupoEmpresaDataFundacao";
    private static final String GRUPO_EMPRESA_ENDERECO = "grupoEmpresaEndereco";
    private static final String GRUPO_EMPRESA_BAIRRO = "grupoEmpresaBairro";
    private static final String GRUPO_EMPRESA_MUNICIPIO = "grupoEmpresaMunicipio";
    private static final String GRUPO_EMPRESA_UF = "grupoEmpresaUF";
    private static final String GRUPO_EMPRESA_CEP = "grupoEmpresaCep";
    private static final String GRUPO_EMPRESA_FONE = "grupoEmpresaFone";
    private static final String GRUPO_EMPRESA_WHATS = "grupoEmpresaWhatsapp";
    private static final String GRUPO_EMPRESA_EMAIL = "grupoEmpresaEmail";
    private static final String GRUPO_EMPRESA_SITE = "grupoEmpresaSite";
    private static final String GRUPO_EMPRESA_PICTURE = "grupoEmpresaPicture";
    private static final String GRUPO_EMPRESA_PATH = "grupoEmpresaPath";

    Users Users = new Users();
    Path Path = new Path();
    Clientes Clientes = new Clientes();
    GrupoEmpresas GrupoEmpresas = new GrupoEmpresas();
    Produtos Produtos = new Produtos();
    Util Util = new Util();

    public sonicPreferences(Context context) {
        sharedpreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        //sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mContex = context;
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

    public class GrupoEmpresas{

        public void setNome(String value){
            editor = sharedpreferences.edit();
            editor.putString(GRUPO_EMPRESA_NOME, value);
            editor.apply();
        }
        public String getNome(){
            return sharedpreferences.getString(GRUPO_EMPRESA_NOME, "");
        }
        public void setDescricao(String value){
            editor = sharedpreferences.edit();
            editor.putString(GRUPO_EMPRESA_DESCRICAO, value);
            editor.apply();
        }
        public String getDescricao(){
            return sharedpreferences.getString(GRUPO_EMPRESA_DESCRICAO, "");
        }
        public void setDataFundacao(String value){
            editor = sharedpreferences.edit();
            editor.putString(GRUPO_EMPRESA_DATA_FUNDACAO, value);
            editor.apply();
        }
        public String getDataFundacao(){
            return sharedpreferences.getString(GRUPO_EMPRESA_DATA_FUNDACAO, "");
        }
        public void setEndereco(String value){
            editor = sharedpreferences.edit();
            editor.putString(GRUPO_EMPRESA_ENDERECO, value);
            editor.apply();
        }
        public String getEndereco(){
            return sharedpreferences.getString(GRUPO_EMPRESA_ENDERECO, "");
        }
        public void setBairro(String value){
            editor = sharedpreferences.edit();
            editor.putString(GRUPO_EMPRESA_BAIRRO, value);
            editor.apply();
        }
        public String getBairro(){
            return sharedpreferences.getString(GRUPO_EMPRESA_BAIRRO, "");
        }
        public void setMunicipio(String value){
            editor = sharedpreferences.edit();
            editor.putString(GRUPO_EMPRESA_MUNICIPIO, value);
            editor.apply();
        }
        public String getMunicipio(){
            return sharedpreferences.getString(GRUPO_EMPRESA_MUNICIPIO, "");
        }
        public void setUF(String value){
            editor = sharedpreferences.edit();
            editor.putString(GRUPO_EMPRESA_UF, value);
            editor.apply();
        }
        public String getUF(){
            return sharedpreferences.getString(GRUPO_EMPRESA_UF, "");
        }
        public void setCep(String value){
            editor = sharedpreferences.edit();
                editor.putString(GRUPO_EMPRESA_CEP, value);
            editor.apply();
        }
        public String getCep(){
            return sharedpreferences.getString(GRUPO_EMPRESA_CEP, "");
        }
        public void setFone(String value){
            editor = sharedpreferences.edit();
            editor.putString(GRUPO_EMPRESA_FONE, value);
            editor.apply();
        }
        public String getFone(){
            return sharedpreferences.getString(GRUPO_EMPRESA_FONE, "");
        }

        public void setWhats(String value) {
            editor = sharedpreferences.edit();
            editor.putString(GRUPO_EMPRESA_WHATS, value);
            editor.apply();
        }
        public String getWhats(){
            return sharedpreferences.getString(GRUPO_EMPRESA_WHATS, "");
        }
        public void setEmail(String value){
            editor = sharedpreferences.edit();
            editor.putString(GRUPO_EMPRESA_EMAIL, value);
            editor.apply();
        }
        public String getEmail(){
            return sharedpreferences.getString(GRUPO_EMPRESA_EMAIL, "");
        }
        public void setSite(String value){
            editor = sharedpreferences.edit();
            editor.putString(GRUPO_EMPRESA_SITE, value);
            editor.apply();
        }
        public String getSite(){
            return sharedpreferences.getString(GRUPO_EMPRESA_SITE, "");
        }

        public String getPicture(){
            return sonicConstants.LOCAL_IMG_CATALOGO+"empresa.JPG";
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
            return sharedpreferences.getString(CLIENTE_NOME, "");
        }
        public void setGrupo(String value){
            editor = sharedpreferences.edit();
            editor.putString(CLIENTE_GRUPO, value);
            editor.apply();
        }
        public String getGrupo(){
            return sharedpreferences.getString(CLIENTE_GRUPO, "");
        }
        public String getClienteExibicao(){
            return sharedpreferences.getString(mContex.getResources().getString(R.string.clienteTipo), mContex.getResources().getString(R.string.prefClienteTipoDefault));
        }
        public void  setClienteExibicao(String value){
            editor = sharedpreferences.edit();
            editor.putString(mContex.getResources().getString(R.string.clienteTipo), value);
            editor.apply();
        }
        public void setClienteSemCompra(Boolean value){
            editor = sharedpreferences.edit();
            editor.putBoolean(mContex.getResources().getString(R.string.clienteSemCompra), value);
            editor.apply();
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
    }
    public class Users {

        public void setUsuarioImei(String value){
            editor = sharedpreferences.edit();
            editor.putString(USUARIO_IMEI, value);
            editor.apply();
        }
        public String getUsuarioImei(){
            return sharedpreferences.getString(USUARIO_IMEI, "");
        }
        public void setStatusLogin(Boolean login){
            editor = sharedpreferences.edit();
            editor.putBoolean(KEEP_LOGGED, login);
            editor.apply();
        }
        public boolean getStatusLogin(){
            return sharedpreferences.getBoolean(KEEP_LOGGED, false);
        }
        public void setUsuarioId(int id){
            editor = sharedpreferences.edit();
            editor.putInt(USUARIO_ID, id);
            editor.apply();
        }
        public int getUsuarioId(){
            return sharedpreferences.getInt(USUARIO_ID,0);
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
        public String getPicture(){
            return  sharedpreferences.getString(USUARIO_PATH,"")
                    +sharedpreferences.getInt(EMPRESA_ID,0) +"_"
                    +sharedpreferences.getInt(USUARIO_ID,0) +".JPG";
        }
        /*
        *
           @value = ID DA EMPRESA
         */
        public String getPicture(int value){
            return  sonicConstants.LOCAL_IMG_USUARIO
                    + value +"_"
                    + sharedpreferences.getInt(USUARIO_ID,0)
                    + ".JPG";
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
}
