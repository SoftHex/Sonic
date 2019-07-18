package com.softhex.sonic;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

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
            i  = new Intent(sonicSplash.this,sonicMain.class);
        }else{
            i  = new Intent(sonicSplash.this,sonicEmpresa.class);
        }

        startActivity(i);
        finish();
    }

}
