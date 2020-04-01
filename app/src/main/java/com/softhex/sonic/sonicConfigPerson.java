package com.softhex.sonic;

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;

public class sonicConfigPerson extends PreferenceActivity{

    private Toolbar mToolbar;
    private sonicPreferences mPreferences;
    private ListPreference mPreferenceCliente;
    private ListPreference mPreferenceCatalogoQtde;
    private ListPreference mPreferenceProdutoNovo;
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

        mPreferences = new sonicPreferences(getBaseContext());

        mPreferenceCliente = (ListPreference)getPreferenceScreen().findPreference(getResources().getString(R.string.clienteTipo));
        mPreferenceCatalogoQtde = (ListPreference)getPreferenceScreen().findPreference(getResources().getString(R.string.catalogoQtde));
        mPreferenceProdutoNovo = (ListPreference)getPreferenceScreen().findPreference(getResources().getString(R.string.produtoNovoDias));
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
            mPreferences.Produtos.setDiasNovo(newValue.toString());
            return true;
        });
        mPreferenceHomeChartType.setOnPreferenceChangeListener((preference, newValue) -> {
            mPreferences.Geral.setHomeChartType(newValue.toString());
            mPreferences.Geral.setHomeRefresh(!mPreferences.Geral.getHomeRefresh());
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
