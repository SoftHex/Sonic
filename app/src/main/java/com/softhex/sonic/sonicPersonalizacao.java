package com.softhex.sonic;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;

public class sonicPersonalizacao extends PreferenceActivity{

    private Toolbar myToolbar;
    private sonicPreferences mPreferences;
    private ListPreference mPreferenceCliente;
    private ListPreference mPreferenceCatalogoQtde;
    private EditTextPreference mPreferenceUsuarioNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.sonic_personalizacao);

        LinearLayout root = (LinearLayout)findViewById(android.R.id.list).getParent().getParent().getParent();
        myToolbar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.sonic_preference_toolbar, root, false);

        root.addView(myToolbar, 0);

        mPreferences = new sonicPreferences(getBaseContext());


        mPreferenceCliente = (ListPreference)getPreferenceScreen().findPreference("clienteTipo");
        mPreferenceCatalogoQtde = (ListPreference)getPreferenceScreen().findPreference("catalogoQtde");
        mPreferenceUsuarioNome = (EditTextPreference)getPreferenceScreen().findPreference("usuarioNome");
        mPreferenceUsuarioNome.setText(mPreferences.Users.getUsuarioNome());
        mPreferenceCliente.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Log.d("VALUE", newValue.toString());
                mPreferences.Clientes.setClienteExibicao(newValue.toString());
                return true;
            }
        });
        mPreferenceCatalogoQtde.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                int value;
                switch (newValue.toString()){
                    case "Duas Colunas":
                        value = 2;
                        break;
                    case "Três Colunas":
                        value = 3;
                        break;
                    case "Quatro Colunas":
                        value = 4;
                        break;
                    default:
                        value = 3;
                        break;
                }
                mPreferences.Produtos.setCatalogoColunas(value);
                return true;
            }
        });

        mPreferenceUsuarioNome.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                mPreferences.Users.setUsuarioNome(newValue.toString());
                return false;
            }
        });

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

}
