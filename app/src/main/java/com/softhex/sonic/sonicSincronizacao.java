package com.softhex.sonic;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class sonicSincronizacao extends AppCompatActivity{

    private Toolbar myToolbar;
    private ViewPagerAdapter myAdapter;
    private ActionBar myActionBar;
    private ViewPager myViewPager;
    private TabLayout myTabLayout;
    private SharedPreferences prefs;
    private Activity mAct;
    private sonicPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_sincronizacao);
        mPrefs = new sonicPreferences(this);
        mAct = this;
        createInterface();

    }

    private void createInterface(){

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        myToolbar = findViewById(R.id.mToolbar);
        setSupportActionBar(myToolbar);
        myActionBar = getSupportActionBar();
        myActionBar.setTitle("Sincronização");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        myActionBar.setDisplayHomeAsUpEnabled(true);
        myActionBar.setDisplayShowHomeEnabled(true);

        myViewPager = findViewById(R.id.mViewPager);
        setUpViewPager(myViewPager);

        myTabLayout = findViewById(R.id.mTabs);
        myTabLayout.setupWithViewPager(myViewPager);

        myToolbar.setNavigationOnClickListener((View view)-> {

                onBackPressed();

        });

    }

    public void setUpViewPager(ViewPager viewpager){
        myAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        myAdapter.addFragment(new sonicSincronizacaoDownload(), "RECEBER");
        myAdapter.addFragment(new sonicSincronizacaoDownload(), "ENVIAR");
        viewpager.setAdapter(myAdapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        private ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        private void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    public void refreshSincFragment(){

        for(int i=0; i<myAdapter.getCount();i++){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.detach(myAdapter.getItem(i)).attach(myAdapter.getItem(i)).commit();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}
