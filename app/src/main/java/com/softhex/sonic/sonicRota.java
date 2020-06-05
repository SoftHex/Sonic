package com.softhex.sonic;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class sonicRota extends AppCompatActivity{

    private Context mContext;
    private  ViewPagerAdapter myAdapter;
    private sonicPreferences mPrefs;
    private FloatingActionMenu fmMenu;
    private FloatingActionButton fbAddVisita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_layout_rota);

        mContext = this;
        mPrefs = new sonicPreferences(mContext);
        fmMenu = findViewById(R.id.fmMenu);
        fbAddVisita = findViewById(R.id.fbAddVisita);
        createInterface();
        loadActions();

    }

    private void createInterface(){

        Toolbar myToolbar = findViewById(R.id.mToolbar);
        setSupportActionBar(myToolbar);
        ActionBar myActionBar = getSupportActionBar();
        myActionBar.setTitle("Rota");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setElevation(0);
        myActionBar.setDisplayHomeAsUpEnabled(true);
        myActionBar.setDisplayShowHomeEnabled(true);

        ViewPager myViewPager = findViewById(R.id.mViewPager);
        setUpViewPager(myViewPager);

        TabLayout myTabLayout = findViewById(R.id.mTabs);
        myTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimaryWhite));
        myTabLayout.setupWithViewPager(myViewPager);

        LayoutTransition transition = new LayoutTransition();
        transition.setDuration(100);

        AppBarLayout myAppBar = findViewById(R.id.appBar);
        myAppBar.setLayoutTransition(transition);

        myToolbar.setNavigationOnClickListener((View v)-> {
                onBackPressed();
        });

    }

    private void loadActions(){
        Intent i = new Intent(this, sonicRotaPessoalAdd.class);
        fbAddVisita.setOnClickListener((View v)-> {
            mPrefs.Rota.setAddFromCliente(false);
            mPrefs.Rota.setAdding(true);
            fmMenu.close(false);
            startActivity(i);
        });
    }

    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if(position==1){
                fmMenu.setVisibility(View.VISIBLE);
                //handlerFloatMenu();
            }else{
                fmMenu.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void handlerFloatMenu(){
        fmMenu.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fmMenu.open(true);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fmMenu.close(true);
                    }
                },2000);
            }
        },700);

    }

    public void setUpViewPager(ViewPager viewpager){
        myAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        myAdapter.addFragment(new sonicRotaAgenda(), "AGENDA");
        myAdapter.addFragment(new sonicRotaPessoal(), "PESSOAL");
        viewpager.addOnPageChangeListener(listener);
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

    public void refreshRotaFragment(){

        for(int i=0; i<myAdapter.getCount();i++){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.detach(myAdapter.getItem(i)).attach(myAdapter.getItem(i)).commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mPrefs.Rota.getRefresh())
        refreshRotaFragment();
    }
}
