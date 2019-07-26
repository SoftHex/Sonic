package com.softhex.sonic;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class sonicSincronizacao extends AppCompatActivity{

    private Toolbar myToolbar;
    private ViewPagerAdapter myAdapter;
    private ActionBar myActionBar;
    private ViewPager myViewPager;
    private TabLayout myTabLayout;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_sincronizacao);

        createInterface();

    }

    private void createInterface(){

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        myActionBar = getSupportActionBar();
        myActionBar.setTitle("Sincronizar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        myActionBar.setDisplayHomeAsUpEnabled(true);
        myActionBar.setDisplayShowHomeEnabled(true);

        myViewPager = findViewById(R.id.pagerSlide);
        setUpViewPager(myViewPager);

        myTabLayout = findViewById(R.id.tabs);
        myTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimaryWhite));
        myTabLayout.setupWithViewPager(myViewPager);

        setUpTabText();

        myToolbar.setNavigationOnClickListener((View view)-> {

                onBackPressed();

        });

    }

    public void setUpViewPager(ViewPager viewpager){
        myAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        myAdapter.addFragment(new sonicSincronizacaoDownload(), "");
        myAdapter.addFragment(new sonicSincronizacaoDownload(), "");
        viewpager.setAdapter(myAdapter);

    }

    public void setUpTabText(){

        myTabLayout.getTabAt(0).setText(R.string.tabSinronizarReceber);
        myTabLayout.getTabAt(1).setText(R.string.tabSinronizarEnviar);


    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
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

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
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
