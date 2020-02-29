package com.softhex.sonic;

import android.animation.LayoutTransition;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.io.File;
import java.util.List;

public class sonicClientesDetalhe extends AppCompatActivity {

    private Toolbar myToolbar;
    private ViewPager myViewpager;
    private TextView[] dots;
    private TextView tvCount;
    private LinearLayout dotsLayout;
    private sonicDatabaseCRUD DBC;
    private CollapsingToolbarLayout myCollapsingToolbar;
    private String[] myImages = new String[sonicConstants.TOTAL_IMAGES_SLIDE];
    private String clienteNome;
    private String clienteCod;
    private String clienteStatus;
    private LinearLayout linearNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonic_clientes_detalhe);

        //sonicAppearence.layoutFullScreenMode(getWindow());

        DBC = new sonicDatabaseCRUD(this);
        myViewpager = findViewById(R.id.pagerSlide);
        myCollapsingToolbar = findViewById(R.id.collapsingToolbar);
        dotsLayout = findViewById(R.id.layoutDots);
        tvCount = findViewById(R.id.tvCount);

        Bundle extras = getIntent().getExtras();
        if(savedInstanceState==null){
            if(extras!=null){
                clienteNome = extras.getString("CLIENTE_NOME");
                clienteCod = extras.getString("CLIENTE_CODIGO");
                clienteStatus = extras.getString("CLIENTE_STATUS");
            }
        }else{
            clienteNome = extras.getString("CLIENTE_NOME");
            clienteCod = extras.getString("CLIENTE_CODIGO");
            clienteStatus = extras.getString("CLIENTE_STATUS");
        }

        createInterface();
        slideImages();

    }

    private void createInterface() {

        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        ActionBar myActionBar = getSupportActionBar();
        myActionBar.setTitle("Clientes");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setElevation(0);
        myActionBar.setDisplayHomeAsUpEnabled(true);
        myActionBar.setDisplayShowHomeEnabled(true);

        LayoutTransition transition = new LayoutTransition();
        transition.setDuration(100);

        AppBarLayout myAppBar = findViewById(R.id.appbar);
        myAppBar.setLayoutTransition(transition);

        //myCollapsingToolbar.setTitle(clienteNome);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        myAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if((myCollapsingToolbar.getHeight()+verticalOffset)<(2 * ViewCompat.getMinimumHeight(myCollapsingToolbar))){
                    myToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryWhite), PorterDuff.Mode.SRC_ATOP);
                    dotsLayout.setVisibility(View.INVISIBLE);
                    myCollapsingToolbar.setTitle(clienteNome);
                }else {
                    myToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryBlack), PorterDuff.Mode.SRC_ATOP);
                    dotsLayout.setVisibility(View.VISIBLE);
                    myCollapsingToolbar.setTitle("");
                }
            }
        });

    }

    public void slideImages(){

        List<sonicClientesHolder> myList;
        int count = 0;

        myList = DBC.Cliente.selectClienteID(12345);
        File file;
        String image = "";

        for(int i = 0; i < myImages.length ; i++){

            image = clienteCod+"_"+(i+1)+".jpg";
            file = new File(Environment.getExternalStorageDirectory(), sonicConstants.LOCAL_IMG_CLIENTES+image);

            if(file.exists()){
                count++;
            }
        }

        myImages = new String[count];

        for(int i = 0; i < myImages.length ; i++){

            image = clienteCod+"_"+(i+1)+".jpg";
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
        myViewpager.setAdapter(myAdapter);
        myViewpager.addOnPageChangeListener(viewListener);
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
