package com.softhex.sonic;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class sonicClientes extends AppCompatActivity {

    private ViewPagerAdapter mAdapter;
    private sonicPreferences mPrefs;
    private LinearLayout llSnackBar;
    private Snackbar mSnackBar;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_layout_padrao);

        mPrefs = new sonicPreferences(this);
        mContext = this;
        createInterface();

    }

    private void createInterface(){

        Toolbar mToolbar = findViewById(R.id.mToolbar);
        setSupportActionBar(mToolbar);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setTitle("Clientes");
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

        mToolbar.setNavigationOnClickListener((View v)-> {
                onBackPressed();
        });

        llSnackBar = findViewById(R.id.llSnackBar);

    }

    public void setUpViewPager(ViewPager viewpager){
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mAdapter.addFragment(new sonicClientesCNPJ(), "CNPJ");
        mAdapter.addFragment(new sonicClientesCPF(), "CPF");
        viewpager.addOnPageChangeListener(listener);
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

    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

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

    public void mensagemErro(){

        llSnackBar.removeAllViews();
        mSnackBar = Snackbar
                .make(llSnackBar, "ERRO", Snackbar.LENGTH_INDEFINITE)
                .setAction("DETALHE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new android.app.AlertDialog.Builder(mContext)
                                //.setTitle("Atenção!\n\n")
                                .setMessage(mPrefs.Geral.getError())
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
                });
        SnackbarHelper.configSnackbar(mContext, mSnackBar, SnackbarHelper.SNACKBAR_WARNING);
        llSnackBar.addView(mSnackBar.getView());
        mSnackBar.show();

    }

    public void mensagemOK(String msg, boolean refresh){

        llSnackBar.removeAllViews();
        mSnackBar = Snackbar
                .make(llSnackBar, msg, Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(refresh)
                            refreshFragments();
                    }
                });
        SnackbarHelper.configSnackbar(mContext, mSnackBar, SnackbarHelper.SNACKBAR_SUCCESS);
        llSnackBar.addView(mSnackBar.getView());
        mSnackBar.show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPrefs.GrupoCliente.setFiltroCpf("TODOS");
        mPrefs.GrupoCliente.setFiltroCnpj("TODOS");
    }
}
