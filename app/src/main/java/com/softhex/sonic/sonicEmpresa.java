package com.softhex.sonic;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class sonicEmpresa extends sonicRuntimePermission {

    private static final int REQUEST_PERMISSION = 10;
    private Button myRegister;
    private EditText myCode;
    private TextView myTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_empresa);

        sonicAppearence.layoutWhitNoLogicalMenu(this, getWindow());

        myRegister = findViewById(R.id.registrar);
        myCode = findViewById(R.id.code);
        myTest = findViewById(R.id.teste);

        myTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(new sonicUtils(sonicEmpresa.this).Feedback.statusNetwork()){

                    new sonicVerificarSite(sonicEmpresa.this).validar("EMPRESA_TESTE", true);

                }else{

                    new sonicTM(sonicEmpresa.this).showSB(view,"Verifique sua conexão com a internet...");

                }
            }
        });

        myRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hideKeyboard(view);

                if(new sonicUtils(sonicEmpresa.this).Feedback.statusNetwork()) {

                    if ((myCode.getText().toString().length() < 11 || myCode.getText().toString().length() > 11 ||  myCode.getText().toString().equals(""))) {

                        new sonicTM(sonicEmpresa.this).showSB(view,"Código inválido...");

                    } else {

                        new sonicVerificarSite(sonicEmpresa.this).validar(myCode.getText().toString(), false);

                    }

                }else{

                    new sonicTM(sonicEmpresa.this).showSB(view,"Verifique sua conexão com a internet...");

                }

            }

        });

        requestAppPermissions(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE // LER O IMEI DO APARELHO
                /*
                Manifest.permission.CALL_PHONE,
                Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.VIBRATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_CONTACTS,

                * */

                },R.string.msgPerms,REQUEST_PERMISSION);

    }

    @Override
    public void onPermissionGaranted(int requestCode) {

        new sonicStorage(sonicEmpresa.this).createFolder(sonicConstants.LOCAL_IMG_CATALOGO);
        new sonicStorage(sonicEmpresa.this).createFolder(sonicConstants.LOCAL_IMG_CLIENTES);
        new sonicStorage(sonicEmpresa.this).createFolder(sonicConstants.LOCAL_IMG_USUARIO);
        new sonicStorage(sonicEmpresa.this).createFolder(sonicConstants.LOCAL_DATA_BACKUP);
        new sonicStorage(sonicEmpresa.this).createFolder(sonicConstants.LOCAL_TEMP);

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void showKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInputFromInputMethod(view.getWindowToken(), 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
    }
}
