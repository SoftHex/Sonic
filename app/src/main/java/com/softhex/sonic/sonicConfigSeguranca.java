package com.softhex.sonic;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.appcompat.widget.Toolbar;

public class sonicConfigSeguranca extends PreferenceActivity{

    private Toolbar mToolbar;
    private sonicPreferences mPref;
    private SwitchPreference mPreferenceLogin;
    private Switch mSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_seguranca);
        LinearLayout root = (LinearLayout)findViewById(android.R.id.list).getParent().getParent().getParent();
        mToolbar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.sonic_preference_toolbar, root, false);

        mToolbar.setTitle("Segurança");
        root.addView(mToolbar, 0);

        mPref = new sonicPreferences(getBaseContext());

        mPreferenceLogin = (SwitchPreference) getPreferenceScreen().findPreference(getResources().getString(R.string.usuarioLogin));

        mPreferenceLogin.setChecked(mPref.Users.getStatusLogin());

        mPreferenceLogin.setOnPreferenceChangeListener((preference, newValue) -> {
            mPref.Users.setStatusLogin((Boolean)newValue);
            return true;
        });

        mToolbar.setNavigationOnClickListener((View v) -> {
                onBackPressed();
        });

    }

}
