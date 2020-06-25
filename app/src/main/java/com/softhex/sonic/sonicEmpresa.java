package com.softhex.sonic;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

public class sonicEmpresa extends sonicRuntimePermission {

    private static final int REQUEST_PERMISSION = 10;
    private Button myRegister;
    private EditText myCode;
    private TextInputEditText mCodigo;
    private Context mContext;
    private ActionBar mActionBar;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_empresa);

        sonicAppearence.layoutWithStatusBarColorPrimary(this, getWindow());

        mContext = this;
        mToolbar = findViewById(R.id.mToolbar);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setTitle("");
        myRegister = findViewById(R.id.btRegistrar);
        myCode = findViewById(R.id.etCodigo);
        mCodigo = findViewById(R.id.etCodigoEmpresa);
        myCode.requestFocus();

        myRegister.setOnClickListener((View v)-> {

                hideKeyboard(v);

                if(new sonicUtils(this).Feedback.statusNetwork()) {

                    if ((myCode.getText().toString().length() < 11 || myCode.getText().toString().length() > 11 ||  myCode.getText().toString().equals(""))) {

                        new sonicDialog(sonicEmpresa.this).showSnackBar(getWindow().getDecorView().getRootView(),"Código inválido...");

                    } else {

                        new sonicVerificarSite(sonicEmpresa.this).validar(myCode.getText().toString());

                    }

                }else{

                    new sonicDialog(sonicEmpresa.this).showSnackBar(v,"Verifique sua conexão com a internet...");

                }

        });

        requestAppPermissions(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE, // LER O IMEI DO APARELHO
                Manifest.permission.CALL_PHONE,
                Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.VIBRATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_CONTACTS
                },R.string.msgPerms,REQUEST_PERMISSION);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.sonic_empresa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itServer:
                startActivity(new Intent(this, sonicServidor.class));
                break;
        }
        return false;
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
