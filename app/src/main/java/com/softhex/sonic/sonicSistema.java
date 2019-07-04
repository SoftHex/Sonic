package com.softhex.sonic;

import android.animation.LayoutTransition;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.Settings;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class sonicSistema extends AppCompatActivity {

    private Toolbar myToolbar;
    private PendingIntent p;
    private ActionBar myActionBar;
    private ViewPager myViewPager;
    private TabLayout myTabLayout;
    private AppBarLayout myAppBar;
    private ViewPagerAdapter myAdapter;
    private sonicDatabaseCRUD DBC;
    private Context myCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_sistema);

        DBC = new sonicDatabaseCRUD(getApplication());

        myCtx = this;

        DBC = new sonicDatabaseCRUD(getApplication());

        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Sistema");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        //ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        setUpViewPager(viewPager);

        //adapter.addFragment(new sonicSistemaFragLog(), "SOBRE");
        //adapter.addFragment(new sonicSistemaFragLog(), "AJUDA");

        //viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimaryWhite));
        tabLayout.setupWithViewPager(viewPager);

        LayoutTransition transition = new LayoutTransition();
        transition.setDuration(100);

        myAppBar = (AppBarLayout)findViewById(R.id.appbar);
        myAppBar.setLayoutTransition(transition);
        //tabLayout.getTabAt(0).setIcon(R.mipmap.ic_download_white_48dp);
        //tabLayout.getTabAt(1).setIcon(R.mipmap.ic_upload_white_48dp);

        //setUpTabText();

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    public void setUpViewPager(ViewPager viewpager){
        myAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        myAdapter.addFragment(new sonicAvisosFragLidos(), "Sobre");
        myAdapter.addFragment(new sonicSistemaFragLog(), "Ajuda");
        myAdapter.addFragment(new sonicSistemaFragLog(), "Log");
        viewpager.setAdapter(myAdapter);

    }


    public void retorno(View v){
        //DBC.Retorno.selectRetornoPedido(2);

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

}
