package com.softhex.sonic;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

import static android.view.View.GONE;

public class sonicSplash extends AppCompatActivity {

    private Intent i;
    private sonicDatabaseCRUD DBC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_splash);

        DBC = new sonicDatabaseCRUD(sonicSplash.this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(()->logar() ,sonicUtils.Randomizer.generate(1000,4000));

    }

    public void logar(){

        if(DBC.Usuarios.usuarioAtivo()){
            List<sonicUsuariosHolder> listaUser;
            List<sonicEmpresasHolder> listaEmpresa;
            listaUser = DBC.Usuarios.selectUsuarioAtivo();
            listaEmpresa = DBC.Empresa.empresasUsuarios();
            i  = new Intent(sonicSplash.this,sonicMain.class);
            i.putExtra(sonicConstants.LOGED_USER_ID, listaUser.get(0).getCodigo());
            i.putExtra(sonicConstants.LOGED_USER_NAME, listaUser.get(0).getNome());
            i.putExtra(sonicConstants.LOGED_USER_NIVEL, listaUser.get(0).getNivelAcessoId());
            i.putExtra(sonicConstants.LOGED_USER_CARGO, listaUser.get(0).getCargo());
            i.putExtra(sonicConstants.EMPRESA_SELECIONADA_NOME, listaEmpresa.get(0).getNomeFantasia());
        }else{
            i  = new Intent(sonicSplash.this,sonicEmpresa.class);
        }

        startActivity(i);
        finish();
    }

}
