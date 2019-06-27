package com.softhex.sonic;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class sonicEmpresa extends sonicRuntimePermission {

    private static final int REQUEST_PERMISSION = 10;
    private Button myRegister;
    private EditText myCode;
    private sonicStorage myPath = new sonicStorage();
    private sonicUtils myUtil;
    private Snackbar mySnackBar;
    private Context myCtx;
    private TextView myTest;
    private sonicVerificarSite verSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sonic_empresa);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        myCtx = sonicEmpresa.this;
        verSite = new sonicVerificarSite(myCtx);

        myRegister = (Button)findViewById(R.id.registrar);
        myCode = (EditText)findViewById(R.id.code);
        myTest = (TextView)findViewById(R.id.teste);

        myTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCtx = sonicEmpresa.this;
                myUtil = new sonicUtils(view.getContext());
                if(myUtil.Feedback.statusNetwork(myCtx)){

                    verSite.validar("EMPRESA_TESTE", true);

                }else{

                    mySnackBar = Snackbar.make(view,"Verifique sua conexão com a internet...", Snackbar.LENGTH_SHORT);
                    View sbView = mySnackBar.getView();
                    sbView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkT));
                    mySnackBar.show();

                }
            }
        });

        myRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hideKeyboard(view);
                myUtil = new sonicUtils(myCtx);

                if(myUtil.Feedback.statusNetwork(myCtx)) {

                    if ((myCode.getText().toString().length() < 11 || myCode.getText().toString().equals(""))) {

                        mySnackBar = Snackbar.make(view,"Código inválido...", Snackbar.LENGTH_SHORT);
                        View sbView = mySnackBar.getView();
                        sbView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkT));
                        mySnackBar.show();

                    } else {

                        verSite.validar(myCode.getText().toString(), false);
                    }

                }else{

                    mySnackBar = Snackbar.make(view,"Verifique sua conexão com a internet...", Snackbar.LENGTH_SHORT);
                    View sbView = mySnackBar.getView();
                    sbView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkT));
                    mySnackBar.show();

                }

            }

        });

        requestAppPermissions(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.VIBRATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.READ_PHONE_STATE
                },R.string.msgPerms,REQUEST_PERMISSION);

    }

    @Override
    public void onPermissionGaranted(int requestCode) {

        // SE HOUVER PERMISSÕES DE ARMAZENAMENTO CRIA-SE OS DIRETÓRIOS PADRÕES DO APP
        myPath.createFolder(getApplication(), sonicConstants.LOCAL_IMG_CATALOGO);
        myPath.createFolder(getApplication(), sonicConstants.LOCAL_IMG_CLIENTES);
        myPath.createFolder(getApplication(), sonicConstants.LOCAL_IMG_PERFIL);
        myPath.createFolder(getApplication(), sonicConstants.LOCAL_DATA_BACKUP);
        myPath.createFolder(getApplication(), sonicConstants.LOCAL_TMP);

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void showKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInputFromInputMethod(view.getWindowToken(), 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
    }
}
