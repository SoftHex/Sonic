package com.softhex.sonic;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class sonicEmpresa extends sonicRuntimePermission {

    private static final int REQUEST_PERMISSION = 10;
    private Button myRegister;
    private EditText myCode;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_empresa);

        //sonicAppearence.layoutWhitNoLogicalMenu(this, getWindow());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        mContext = this;
        myRegister = findViewById(R.id.btRegistrar);
        myCode = findViewById(R.id.etCodigo);
        //myTest = findViewById(R.id.teste);

        /*myTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(new sonicUtils(sonicEmpresa.this).Feedback.statusNetwork()){

                    new sonicVerificarSite(sonicEmpresa.this).validar("EMPRESA_TESTE", true);

                }else{

                    new sonicDialog(sonicEmpresa.this).showSnackBar(view,"Verifique sua conexão com a internet...");

                }
            }
        });*/

        myRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hideKeyboard(view);

                if(new sonicUtils(sonicEmpresa.this).Feedback.statusNetwork()) {

                    if ((myCode.getText().toString().length() < 11 || myCode.getText().toString().length() > 11 ||  myCode.getText().toString().equals(""))) {

                        new sonicDialog(sonicEmpresa.this).showSnackBar(view,"Código inválido...");

                    } else {

                        new sonicVerificarSite(sonicEmpresa.this).validar(myCode.getText().toString(), false);

                    }

                }else{

                    new sonicDialog(sonicEmpresa.this).showSnackBar(view,"Verifique sua conexão com a internet...");

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

        String[] folders = {sonicConstants.LOCAL_IMG_CATALOGO,
                sonicConstants.LOCAL_IMG_CLIENTES,
                sonicConstants.LOCAL_IMG_USUARIO,
                sonicConstants.LOCAL_IMG_EMPRESA,
                sonicConstants.LOCAL_DATA_BACKUP,
                sonicConstants.LOCAL_TEMP};
        new sonicStorage(mContext).createFolder(folders);

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
