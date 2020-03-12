package com.softhex.sonic;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class sonicSistema extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_sistema);

        createInterface();

    }

    private void createInterface(){

        Toolbar mToolbar = findViewById(R.id.mToolbar);
        setSupportActionBar(mToolbar);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setTitle("Sistema");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setElevation(0);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);

        ViewPager mViewPager = findViewById(R.id.mViewPager);
        setUpViewPager(mViewPager);

        TabLayout mTabLayout = findViewById(R.id.mTabs);
        mTabLayout.setupWithViewPager(mViewPager);

        LayoutTransition transition = new LayoutTransition();
        transition.setDuration(100);

        AppBarLayout myAppBar = findViewById(R.id.appBar);
        myAppBar.setLayoutTransition(transition);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    public void setUpViewPager(ViewPager viewpager){
        ViewPagerAdapter myAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        myAdapter.addFragment(new sonicSistemaFragLog(), "Sobre");
        myAdapter.addFragment(new sonicSistemaFragLog(), "Ajuda");
        myAdapter.addFragment(new sonicSistemaFragLog(), "Log");
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

}
