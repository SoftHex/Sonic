package com.softhex.sonic;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class sonicSplash extends AppCompatActivity {

    private Intent i;
    private sonicDatabaseCRUD DBC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_splash);

        DBC = new sonicDatabaseCRUD(sonicSplash.this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                logar();
            }
        }, 3000);

    }

    public void logar(){

        if(DBC.Vendedor.vendedorAtivo()){
            i  = new Intent(sonicSplash.this,sonicMain.class);
        }else{
            i  = new Intent(sonicSplash.this,sonicEmpresa.class);
        }

        startActivity(i);
        finish();
        //overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);

    }

}
