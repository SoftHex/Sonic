package com.softhex.sonic;

import android.Manifest;
import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import java.io.File;

public class sonicRotaDetalhe extends sonicRuntimePermission{

    private static final int REQUEST_PERMISSION = 10;
    private Context mContex;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewpager;
    private TextView[] dots;
    private TextView tvCount;
    private LinearLayout dotsLayout;
    private sonicDatabaseCRUD DBC;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private String[] myImages = new String[sonicConstants.TOTAL_IMAGES_SLIDE];
    private LinearLayout linearNew;
    private sonicPreferences mPref;
    private TextView tvLogradrouro;
    private TextView tvEndCompleto;
    private TextView tvCep;
    private Button btNavegar;
    private Button btIniciar;
    private Button btCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_rota_detalhe);

        mPref = new sonicPreferences(this);
        DBC = new sonicDatabaseCRUD(this);
        mViewpager = findViewById(R.id.pagerSlide);
        mCollapsingToolbar = findViewById(R.id.mCollapsingToolbar);
        dotsLayout = findViewById(R.id.layoutDots);
        tvCount = findViewById(R.id.tvCount);
        tvLogradrouro = findViewById(R.id.tvLogradouro);
        tvEndCompleto = findViewById(R.id.tvEnderecoCompleto);
        tvCep = findViewById(R.id.tvCep);
        btNavegar = findViewById(R.id.btNavegar);
        btIniciar = findViewById(R.id.btIniciar);
        btCancelar = findViewById(R.id.btCancelar);

        createInterface();
        slideImages();
        loadDetails();
        loadActions();

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

        LayoutTransition transition = new LayoutTransition();
        transition.setDuration(100);

        AppBarLayout mAppBar = findViewById(R.id.appBar);
        mAppBar.setLayoutTransition(transition);

        mCollapsingToolbar.setTitle("");

        mToolbar.setNavigationOnClickListener((View v)-> {
                onBackPressed();
        });

        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if((mCollapsingToolbar.getHeight()+verticalOffset)<(2 * ViewCompat.getMinimumHeight(mCollapsingToolbar))){
                    mToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryWhite), PorterDuff.Mode.SRC_ATOP);
                    dotsLayout.setVisibility(View.INVISIBLE);
                    mCollapsingToolbar.setTitle(mPref.Clientes.getNome());
                }else {
                    mToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryBlack), PorterDuff.Mode.SRC_ATOP);
                    dotsLayout.setVisibility(View.VISIBLE);
                    mCollapsingToolbar.setTitle("");
                }
            }
        });

    }

    public void slideImages(){

        int count = 0;
        File file;
        String image = "";

        for(int i = 0; i < myImages.length ; i++){

            image = mPref.Clientes.getId()+(i==0? "" : "_"+i)+".JPG";
            file = new File(Environment.getExternalStorageDirectory(), sonicConstants.LOCAL_IMG_CLIENTES+image);

            if(file.exists()){
                count++;
            }
        }

        myImages = new String[count];

        for(int i = 0; i < count ; i++){

            image = mPref.Clientes.getId()+(i==0? "" : "_"+i)+".JPG";
            file = new File(Environment.getExternalStorageDirectory(), sonicConstants.LOCAL_IMG_CLIENTES+image);

            myImages[i] = file.toString();

        }


        if(count==0){
            myImages = new String[1];
            myImages[0] = sonicUtils.getURIForResource(R.drawable.nophoto);
        }

        linearNew = findViewById(R.id.linearNew);
        //linearNew.setVisibility(clienteStatus.equals("NOVO") ? View.VISIBLE : View.INVISIBLE);

        sonicSlideImageAdapter myAdapter = new sonicSlideImageAdapter(this, myImages, count==0 ? false : true);
        mViewpager.setAdapter(myAdapter);
        mViewpager.addOnPageChangeListener(viewListener);
        //addBottomDots(0);
        addCount(1);

    }

    private void addCount(int position){
        if(myImages.length>1){
            tvCount.setVisibility(View.VISIBLE);
            tvCount.setText(position+"/"+myImages.length);
        }
    }

    private void addBottomDots(int position){

        dots = new TextView[myImages.length];
        dotsLayout.removeAllViews();
        if(myImages.length>1){
            for(int i=0; i < dots.length; i++)
            {
                dots[i] = new TextView(this);
                dots[i].setText(Html.fromHtml("&#8226;"));
                dots[i].setTextSize(30);
                dots[i].setTextColor(getResources().getColor(R.color.dotSlideInactive));
                dotsLayout.addView(dots[i]);
            }
            if(dots.length>1){
                dots[position].setTextColor(getResources().getColor(R.color.dotSlideActive));
                dots[position].setTextSize(45);
            }
        }

    }

    public void loadDetails(){
        tvLogradrouro.setText(mPref.Clientes.getLogradouro());
        tvEndCompleto.setText(mPref.Clientes.getBairro()+", "+mPref.Clientes.getMunicipio()+" - "+mPref.Clientes.getUf());
        tvCep.setText("CEP: "+mPref.Clientes.getCep());
    }

    public void loadActions(){
        btNavegar.setOnClickListener((View v)->{
            requestAppPermissions(new String[]{

                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION

            },R.string.msgPerms,REQUEST_PERMISSION);

        });
    }

    @Override
    public void onPermissionGaranted(int requestCode) {
        //String uri = "geo: latitude,longtitude ?q= latitude,longtitude";
        //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
        Toast.makeText(mContex, "LIBERADO", Toast.LENGTH_LONG).show();
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

            //addBottomDots(i);
            addCount(i+1);

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

}
