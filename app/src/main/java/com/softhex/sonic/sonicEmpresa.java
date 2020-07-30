package com.softhex.sonic;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import zerobranch.androidremotedebugger.AndroidRemoteDebugger;

public class sonicEmpresa extends sonicRuntimePermission {

    private static final int REQUEST_PERMISSION = 10;
    private Button myRegister;
    private EditText myCode;
    private TextInputEditText mCodigo;
    private Context mContext;
    private ActionBar mActionBar;
    private Toolbar mToolbar;
    private LinearLayout llSuporte;
    private Button btSuporte;
    private LinearLayout llSnackBar;
    private sonicPreferences mPrefs;
    private Snackbar mSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_empresa);

        sonicAppearence.layoutWithStatusBarColorPrimary(this, getWindow());
        AndroidRemoteDebugger.init(this);

        mContext = this;
        mToolbar = findViewById(R.id.mToolbar);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setTitle("");
        myRegister = findViewById(R.id.btRegistrar);
        myCode = findViewById(R.id.etCodigo);
        mCodigo = findViewById(R.id.etCodigoEmpresa);
        myCode.requestFocus();
        llSuporte = findViewById(R.id.llSuporte);
        btSuporte = findViewById(R.id.btSuporte);
        llSnackBar = findViewById(R.id.llSnackBar);
        mPrefs = new sonicPreferences(this);

        myRegister.setOnClickListener((View v)-> {

                hideKeyboard(v);

                if(new sonicUtils(this).Feedback.statusNetwork()) {

                    if ((myCode.getText().toString().length() < 11 || myCode.getText().toString().length() > 11 ||  myCode.getText().toString().equals(""))) {

                        mensagemErro("CÓDIGO INVÁLIDO...");

                    } else {

                        new sonicVerificarSite(sonicEmpresa.this).validar(myCode.getText().toString());

                    }

                }else{

                    mensagemErro("VERIFIQUE SUA CONEXÃO...");

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
                Intent i = new Intent(sonicEmpresa.this, sonicServidor.class);
                startActivity(i);
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

    public void mensagemErroDetalhe(boolean support){

        llSnackBar.removeAllViews();
        mSnackbar = Snackbar
                .make(llSnackBar, "ERRO", Snackbar.LENGTH_INDEFINITE)
                .setAction("DETALHE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new android.app.AlertDialog.Builder(mContext)
                                //.setTitle("Atenção!\n\n")
                                .setMessage(mPrefs.Geral.getError())
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        if(support)
                                        ativarSuporte();
                                    }
                                }).show();
                    }
                });
        SnackbarHelper.configSnackbar(mContext, mSnackbar, SnackbarHelper.SNACKBAR_WARNING);
        llSnackBar.addView(mSnackbar.getView());
        mSnackbar.show();

    }

    public void mensagemErro(String erro){
        llSnackBar.removeAllViews();
        mSnackbar = Snackbar
                .make(llSnackBar, erro, Snackbar.LENGTH_LONG);
        SnackbarHelper.configSnackbar(mContext, mSnackbar, SnackbarHelper.SNACKBAR_WARNING);
        llSnackBar.addView(mSnackbar.getView());
        mSnackbar.show();
    }

    public void ativarSuporte(){
        llSuporte.setVisibility(View.VISIBLE);
        btSuporte.setOnClickListener((View v)->{
            senhaSuporte();
        });
    }

    public void senhaSuporte() {

        View v = getLayoutInflater().inflate(R.layout.dialog_suporte_senha, null);
        EditText senha = v.findViewById(R.id.etSuporteSenha);
        senha.setHint("Senha de suporte...");
        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setTitle("Login Suporte");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                hideKeyboard(v);
                if(!senha.getText().toString().equals("123456")){
                    senhaIncorreta();
                }else{
                    exibirListaUsuarios();
                }
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(v);
        alertDialog.show();

    }

    private void senhaIncorreta(){

        llSnackBar.removeAllViews();
        Snackbar snackbar = Snackbar.make(llSnackBar, "SENHA INCORRETA", Snackbar.LENGTH_LONG);
        SnackbarHelper.configSnackbar(mContext, snackbar, SnackbarHelper.SNACKBAR_WARNING);
        llSnackBar.addView(snackbar.getView());
        snackbar.show();

    }

    private void exibirListaUsuarios() {

        List<sonicUsuariosHolder> users;

        users = new sonicDatabaseCRUD(mContext).Usuario.listaUsuarios();

        List<String> l = new ArrayList<>();
        List<Integer> cod = new ArrayList<>();

        for(int i=0; i < users.size(); i++ ){
            l.add(users.get(i).getCodigo()+" - "+users.get(i).getNome());
            cod.add(users.get(i).getCodigo());
        }

        CharSequence[] chars = l.toArray(new CharSequence[l.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setItems(chars, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                new sonicDatabaseCRUD(mContext).Usuario.setAtivo(cod.get(item));
                mPrefs.Users.setAtivo(true);
                Intent i = new Intent(sonicEmpresa.this, sonicSplash.class);
                //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finishAffinity();
            }
        }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();

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
