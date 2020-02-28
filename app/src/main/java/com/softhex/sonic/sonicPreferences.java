package com.softhex.sonic;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.ListPreference;

import java.io.File;

public class sonicPreferences{
    public static final String PREFERENCE_NAME = "com.softhex.sonic_preferences";
    private final SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    private Context mContex;
    private ListPreference mListPref;
    private static final String KEEP_LOGGED = "keepLogged";
    private static final String USUARIO_ID = "usuarioId";
    private static final String USUARIO_NOME = "usuarioNome";
    private static final String USUARIO_CARGO = "usuarioCargo";
    private static final String EMPRESA_ID = "empresaId";
    private static final String EMPRESA_NOME = "empresaNome";
    private static final String ENVIRONMENT = "pathEnvironment";
    private static final String PROFILE_PATH = "profilePath";
    private static final String SAUDACAO = "saudacao";

    Users Users = new Users();
    Path Path = new Path();
    Clientes Clientes = new Clientes();
    Produtos Produtos = new Produtos();
    Util Util = new Util();

    public sonicPreferences(Context context) {
        sharedpreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        //sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mContex = context;
    }

    public class Produtos{
        public String getCatalogoColunas(){
            return sharedpreferences.getString(mContex.getResources().getString(R.string.catalogoQtde), mContex.getResources().getString(R.string.catalogoQtde));
        }
        public void setCatalogoColunas(String value){
            editor = sharedpreferences.edit();
            editor.putString(mContex.getResources().getString(R.string.catalogoQtde), value);
            editor.apply();
        }
        public String getDiasNovo(){
            return sharedpreferences.getString(mContex.getResources().getString(R.string.produtoNovo), mContex.getResources().getString(R.string.prefProdutoNovoDefault));
        }
        public void setDiasNovo(String value){
            editor = sharedpreferences.edit();
            editor.putString(mContex.getResources().getString(R.string.produtoNovo), value);
            editor.apply();
        }
    }

    public class Clientes{
        public String getClienteExibicao(){
            return sharedpreferences.getString(mContex.getResources().getString(R.string.clienteTipo), mContex.getResources().getString(R.string.prefClienteTipoDefault));
        }
        public void  setClienteExibicao(String value){
            editor = sharedpreferences.edit();
            editor.putString(mContex.getResources().getString(R.string.clienteTipo), value);
            editor.apply();
        }
    }
    public class Users {

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
            return sharedpreferences.getString(USUARIO_NOME, "USUÁRIO");
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
            editor.putString(PROFILE_PATH, sonicConstants.LOCAL_IMG_USUARIO);
            editor.apply();
        }
        public String getProfilePath(){
            setProfilePath();
            return sharedpreferences.getString(PROFILE_PATH, sonicConstants.LOCAL_IMG_USUARIO);
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
