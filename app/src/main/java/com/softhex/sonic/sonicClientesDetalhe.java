package com.softhex.sonic;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class sonicClientesDetalhe extends AppCompatActivity{

    private Context mContex;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewpager;
    private TextView[] dots;
    private TextView tvCount;

    private sonicDatabaseCRUD mData;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private String[] myImages = new String[sonicConstants.TOTAL_IMAGES_SLIDE];
    private LinearLayout linearNew;
    private sonicPreferences mPref;
    private FloatingActionMenu fbMenu;
    private FloatingActionButton fbTelefone;
    private FloatingActionButton fbEmail;
    private FloatingActionButton fbWhatsApp;
    private FloatingActionButton fbAddPedido;
    private FloatingActionButton fbAddVisita;
    private List<sonicClientesHolder> mList;
    private AppBarLayout mAppBar;
    private int totalImagens;
    private LinearLayout llDots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_clientes_detalhe);

        mPref = new sonicPreferences(this);
        mData = new sonicDatabaseCRUD(this);
        mContex = this;
        mList = mData.Cliente.selectClienteByID(mPref.Clientes.getCodigo());
        mViewpager = findViewById(R.id.pagerSlide);
        mCollapsingToolbar = findViewById(R.id.mCollapsingToolbar);
        tvCount = findViewById(R.id.tvCount);
        fbMenu = findViewById(R.id.fbMenu);
        fbTelefone = findViewById(R.id.fbTelefone);
        fbEmail = findViewById(R.id.fbEmail);
        fbWhatsApp = findViewById(R.id.fbWhatsApp);
        fbAddPedido = findViewById(R.id.fbAddPedido);
        fbAddVisita = findViewById(R.id.fbAddVisita);
        llDots = findViewById(R.id.llDots);

        slideImages();
        createInterface();
        handlerFloatMenu(false);
        loadAction();

    }

    private void handlerFloatMenu(boolean anim){

        fbTelefone.setVisibility((mList.get(0).getFone()==null || mList.get(0).getFone().equals("")) ? View.GONE : View.VISIBLE);
        fbEmail.setVisibility((mList.get(0).getEmail()==null || mList.get(0).getEmail().equals("")) ? View.GONE : View.VISIBLE);
        fbWhatsApp.setVisibility((mList.get(0).getWhats()==null || mList.get(0).getWhats().equals("")) ? View.GONE : View.VISIBLE);

        if(anim) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fbMenu.open(true);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fbMenu.close(true);
                        }
                    }, 2000);
                }
            }, 700);
        }

    }

    private void loadAction(){
        Intent i = new Intent(this, sonicRotaPessoalAdd.class);
        fbAddVisita.setOnClickListener((View v)-> {
            fbMenu.close(false);
            mPref.RotaPessoal.setClientePicked(false);
            mPref.RotaPessoal.setAddFromCliente(true);
            mPref.RotaPessoal.setAdding(true);
            startActivity(i);
        });
    }

    public void setUpViewPager(ViewPager viewpager){
        ViewPagerAdapter mAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        mAdapter.addFragment(new sonicClientesDetalheGeral(), "GERAL");
        mAdapter.addFragment(new sonicClientesDetalheGeral(), "FINANCEIRO");
        mAdapter.addFragment(new sonicClientesDetalheCompras(), (mList.get(0).getCompras()>0 ? "COMPRAS("+ mList.get(0).getCompras()+")" : "COMPRAS"));
        mAdapter.addFragment(new sonicClientesDetalheTitulos(), (mList.get(0).getTitulos()>0 ? "TÍTULOS("+ mList.get(0).getTitulos()+")" : "TÍTULOS"));
        mAdapter.addFragment(new sonicClientesDetalheVisitas(), (mList.get(0).getVisitas() > 0 ? "VISITAS(" + mList.get(0).getVisitas() + ")" : "VISITAS"));

        viewpager.addOnPageChangeListener(viewListenerFragment);
        viewpager.setAdapter(mAdapter);
        viewpager.setOffscreenPageLimit((mAdapter.getCount() > 1 ? mAdapter.getCount() - 1 : 1));

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private final List<Integer> mIcon = new ArrayList<>();
        private CustomOnScrollChangeListener mListener;

        public void setCustomScrollChangeListener(CustomOnScrollChangeListener listener){
            mListener = listener;
        }

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            if(mIcon.get(position)!=null){
                mTabLayout.getTabAt(position).setIcon(mIcon.get(position));
                if(mTabLayout.getTabAt(position).isSelected()){
                    mTabLayout.getTabAt(position).getIcon().setColorFilter(getResources().getColor(R.color.iconTabSelected), PorterDuff.Mode.SRC_ATOP);
                }
            }
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
            mIcon.add(null);
        }

        public void addFragment(Fragment fragment, String title, Integer icon) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
            mIcon.add(icon);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return super.getItemPosition(object);
        }

    }
    private void createInterface() {

        mToolbar = findViewById(R.id.mToolbar);
        setSupportActionBar(mToolbar);
        ActionBar myActionBar = getSupportActionBar();
        myActionBar.setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setElevation(0);
        myActionBar.setDisplayHomeAsUpEnabled(true);
        myActionBar.setDisplayShowHomeEnabled(true);

        ViewPager myViewPager = findViewById(R.id.mViewPager);
        setUpViewPager(myViewPager);

        mTabLayout = findViewById(R.id.mTabs);
        mTabLayout.setupWithViewPager(myViewPager);

        LayoutTransition transition = new LayoutTransition();
        transition.setDuration(100);

        mAppBar = findViewById(R.id.appBar);
        mAppBar.setLayoutTransition(transition);

        mAppBar.setExpanded(totalImagens==0 ? false : true);

        mCollapsingToolbar.setTitle(mPref.Clientes.getNome());

        mCollapsingToolbar.setExpandedTitleTextAppearance(totalImagens==0 ? R.style.ExpandedTitlePrimaryLarge : R.style.ExpandedTitleWhiteLarge);


        mToolbar.setNavigationOnClickListener((View v)-> {
                onBackPressed();
        });

        mAppBar.addOnOffsetChangedListener(appBarListener);

    }

    private AppBarLayout.OnOffsetChangedListener appBarListener = new AppBarLayout.OnOffsetChangedListener() {
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            if((mCollapsingToolbar.getHeight()+verticalOffset)<(2 * ViewCompat.getMinimumHeight(mCollapsingToolbar))){
                mToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryWhite), PorterDuff.Mode.SRC_ATOP);
                //llDots.setVisibility(View.INVISIBLE);
            }else {
                mToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryBlack), PorterDuff.Mode.SRC_ATOP);
                //llDots.setVisibility(View.VISIBLE);
            }
        }
    };

    public void slideImages(){

        totalImagens = 0;
        File file;
        String image = "";

        for(int i = 0; i < myImages.length ; i++){

            image = mPref.Clientes.getCodigo()+(i==0? "" : "_"+i)+".JPG";
            file = new File(Environment.getExternalStorageDirectory(), sonicConstants.LOCAL_IMG_CLIENTES+image);

            if(file.exists()){
                totalImagens++;
            }
        }

        myImages = new String[totalImagens];

        for(int i = 0; i < totalImagens ; i++){

            image = mPref.Clientes.getCodigo()+(i==0? "" : "_"+i)+".JPG";
            file = new File(Environment.getExternalStorageDirectory(), sonicConstants.LOCAL_IMG_CLIENTES+image);

            myImages[i] = file.toString();

        }


        if(totalImagens==0){
            myImages = new String[1];
            myImages[0] = sonicUtils.getURIForResource(R.drawable.nophoto);
        }

        linearNew = findViewById(R.id.linearNew);
        //linearNew.setVisibility(clienteStatus.equals("NOVO") ? View.VISIBLE : View.INVISIBLE);

        sonicSlideImageAdapter myAdapter = new sonicSlideImageAdapter(this, myImages, totalImagens==0 ? false : true);
        mViewpager.setAdapter(myAdapter);
        mViewpager.addOnPageChangeListener(viewListenerImage);
        //addBottomDots(0);
        addCount(1);

    }

    private void addCount(int position){
        if(totalImagens>1){
            llDots.setVisibility(View.VISIBLE);
            tvCount.setText(position+"/"+myImages.length);
        }
    }

    private void addBottomDots(int position){

        dots = new TextView[myImages.length];
        llDots.removeAllViews();
        if(myImages.length>1){
            for(int i=0; i < dots.length; i++)
            {
                dots[i] = new TextView(this);
                dots[i].setText(Html.fromHtml("&#8226;"));
                dots[i].setTextSize(30);
                dots[i].setTextColor(getResources().getColor(R.color.dotSlideInactive));
                llDots.addView(dots[i]);
            }
            if(dots.length>1){
                dots[position].setTextColor(getResources().getColor(R.color.dotSlideActive));
                dots[position].setTextSize(45);
            }
        }

    }

    ViewPager.OnPageChangeListener viewListenerFragment = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

            fbMenu.setVisibility(i!=0 ? View.GONE : View.VISIBLE);

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    ViewPager.OnPageChangeListener viewListenerImage = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

            addCount(i+1);

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
