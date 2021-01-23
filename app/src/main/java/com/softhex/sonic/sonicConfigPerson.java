package com.softhex.sonic;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

public class sonicConfigPerson extends PreferenceActivity{

    private Context mContext;
    private Toolbar mToolbar;
    private sonicPreferences mPreferences;
    private ListPreference mPreferenceCliente;
    private ListPreference mPreferenceCatalogoQtde;
    private SwitchPreference mPreferenceProdutoNovo;
    private ListPreference mPreferenceHomeChartType;
    private ListPreference mPreferenceTipoHora;
    private PreferenceScreen mPreferenceClientLista;
    private CheckBoxPreference mPreferenceListagemCompleta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.sonic_personalizacao);
        LinearLayout root = (LinearLayout)findViewById(android.R.id.list).getParent().getParent().getParent();
        mToolbar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.sonic_preference_toolbar, root, false);

        mToolbar.setTitle("Personalização");
        root.addView(mToolbar, 0);


        mContext = this;

        mPreferences = new sonicPreferences(getBaseContext());

        mPreferenceCliente = (ListPreference)getPreferenceScreen().findPreference(getResources().getString(R.string.clienteTipo));
        mPreferenceCatalogoQtde = (ListPreference)getPreferenceScreen().findPreference(getResources().getString(R.string.catalogoQtde));
        mPreferenceProdutoNovo = (SwitchPreference) getPreferenceScreen().findPreference(getResources().getString(R.string.produtoNovo));
        mPreferenceHomeChartType = (ListPreference)getPreferenceScreen().findPreference(getResources().getString(R.string.homeChartaType));
        mPreferenceTipoHora = (ListPreference)getPreferenceScreen().findPreference(getResources().getString(R.string.tipoHora));
        mPreferenceClientLista = (PreferenceScreen)getPreferenceScreen().findPreference(getResources().getString(R.string.clienteLista));
        mPreferenceListagemCompleta = (CheckBoxPreference)getPreferenceScreen().findPreference(getResources().getString(R.string.listagemCompleta));

        mPreferenceCliente.setOnPreferenceChangeListener((preference, newValue) -> {
            mPreferences.Clientes.setClienteExibicao(newValue.toString());
            return true;
        });
        mPreferenceCatalogoQtde.setOnPreferenceChangeListener((preference, newValue) -> {
            mPreferences.Produtos.setCatalogoColunas(newValue.toString());
            return true;
        });
        mPreferenceProdutoNovo.setOnPreferenceChangeListener((preference, newValue) ->  {
            mPreferences.Produtos.setDestacarNovo((Boolean)newValue);
            if(newValue.equals(true)){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                alertDialog.setTitle("Destacar quando...");
                String[] items = mContext.getResources().getStringArray(R.array.prefProdutoNovoOptions);
                mPreferences.Produtos.setDiasNovo(items[0]);
                alertDialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPreferences.Produtos.setDiasNovo(items[which]);
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = alertDialog.create();
                alert.setCanceledOnTouchOutside(false);
                alert.show();
            }

            return true;
        });
        mPreferenceHomeChartType.setOnPreferenceChangeListener((preference, newValue) -> {
            mPreferences.Geral.setHomeChartType(newValue.toString());
            mPreferences.Sincronizacao.setHomeRefresh(!mPreferences.Sincronizacao.getHomeRefresh());
            return true;
        });
        mPreferenceTipoHora.setOnPreferenceChangeListener((preference, newValue) -> {
            mPreferences.Geral.setTipoHora(newValue.toString());
            return true;
        });
        mPreferenceClientLista.setOnPreferenceClickListener((Preference) -> {
            Intent i = new Intent(this, sonicConfigPersonClienteLista.class);
            startActivity(i);
            return true;
        });
        mPreferenceListagemCompleta.setOnPreferenceChangeListener((preference ,newValue)->{
            mPreferences.Geral.setListagemCompleta((Boolean)newValue);
            return true;
        });
        mToolbar.setNavigationOnClickListener((View v) -> {
                onBackPressed();
        });

    }

}
