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
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class sonicProdutos extends AppCompatActivity {

    private ViewPagerAdapter mAdapter;
    private sonicPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_layout_padrao);

        sonicAppearence.removeFlashingTransition(getWindow());
        mPrefs = new sonicPreferences(this);
        createInterface();

    }

    private void createInterface(){

        Toolbar myToolbar = findViewById(R.id.mToolbar);
        setSupportActionBar(myToolbar);
        ActionBar myActionBar = getSupportActionBar();
        myActionBar.setTitle(R.string.produtosTitulo);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setElevation(0);
        myActionBar.setDisplayHomeAsUpEnabled(true);
        myActionBar.setDisplayShowHomeEnabled(true);

        ViewPager myViewPager = findViewById(R.id.mViewPager);
        setUpViewPager(myViewPager);

        TabLayout myTabLayout = findViewById(R.id.mTabs);
        //myTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimaryWhite));
        myTabLayout.setupWithViewPager(myViewPager);

        LayoutTransition transition = new LayoutTransition();
        transition.setDuration(100);

        AppBarLayout myAppBar = findViewById(R.id.appBar);
        myAppBar.setLayoutTransition(transition);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    public void setUpViewPager(ViewPager viewpager){
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mAdapter.addFragment(new sonicProdutosLista(), "LISTA");
        mAdapter.addFragment(new sonicProdutosGrid(), "CATÁLOGO");
        viewpager.setAdapter(mAdapter);

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

    public void refreshFragments(){

        for(int i=0; i<mAdapter.getCount();i++){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.detach(mAdapter.getItem(i)).attach(mAdapter.getItem(i)).commit();
        }
    }

    public void refreshFragments(int position){

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(mAdapter.getItem(position)).attach(mAdapter.getItem(position)).commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPrefs.GrupoProduto.setFiltroGrid("TODOS");
        mPrefs.GrupoProduto.setFiltroLista("TODOS");
    }
}
