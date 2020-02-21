package com.softhex.sonic;

import android.content.Context;
import android.content.SharedPreferences;

public class sonicPreferences {
    public static final String PREFERENCE_NAME = "PREFERENCE_DATA";
    private final SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    private static final String KEEP_LOGGED = "KEEP_LOGGED";
    private static final String USUARIO_ID = "USUARIO_ID";
    private static final String USUARIO_NOME = "USUARIO_NOME";
    private static final String USUARIO_CARGO = "USUARIO_CARGO";
    private static final String EMPRESA_ID = "EMPRESA_ID";
    private static final String EMPRESA_NOME = "EMPRESA_NOME";


    Login Login = new Login();

    public sonicPreferences(Context context) {
        sharedpreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }
    public class Login{

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
    }
}
