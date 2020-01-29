package com.softhex.sonic;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.PorterDuff;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
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

public class sonicProdutosDetalhe extends AppCompatActivity {

    private Toolbar myToolbar;
    private ViewPager myViewpager;
    private TextView[] dots;
    private LinearLayout dotsLayout;
    private sonicDatabaseCRUD DBC;
    private CollapsingToolbarLayout myCollapsingToolbar;
    private AppBarLayout myAppBar;
    private String[] myImages = new String[sonicConstants.TOTAL_IMAGES_SLIDE];
    private Context ctx;
    private ImageView myImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonic_produtos_detalhe);

        ctx = this;
        DBC = new sonicDatabaseCRUD(this);
        //myViewpager = findViewById(R.id.pagerSlide);
        myAppBar = findViewById(R.id.appbar);
        myCollapsingToolbar = findViewById(R.id.collapsingToolbar);

        createInterface();
        slideImages();

        myAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if((myCollapsingToolbar.getHeight()+verticalOffset)<(2 * ViewCompat.getMinimumHeight(myCollapsingToolbar))){
                    myToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryWhite), PorterDuff.Mode.SRC_ATOP);
                }else{
                    myToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryBlack), PorterDuff.Mode.SRC_ATOP);
                }
            }
        });

    }


    private void createInterface() {

        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        ActionBar myActionBar = getSupportActionBar();
        myActionBar.setTitle("Produtos");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setElevation(0);
        myActionBar.setDisplayHomeAsUpEnabled(true);
        myActionBar.setDisplayShowHomeEnabled(true);

        LayoutTransition transition = new LayoutTransition();
        transition.setDuration(100);

        AppBarLayout myAppBar = findViewById(R.id.appbar);
        myAppBar.setLayoutTransition(transition);

        myCollapsingToolbar.setTitle(sonicConstants.PUT_EXTRA_PRODUTO_NOME);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    public void slideImages(){

        List<sonicProdutosHolder> myList;
        myList = DBC.Produto.selectProdutoselecionado();

        File file = new File(Environment.getExternalStorageDirectory(), sonicConstants.LOCAL_IMG_CATALOGO + myList.get(0).getCodigo() + ".JPG");
        if(file.exists()){
            myImages[0] = file.toString();
        }else{
            myImages[0] = sonicUtils.getURLForResource(R.drawable.nophoto);
        }

        //sonicSlideImageAdapter myAdapter = new sonicSlideImageAdapter(this, myImages);
        //dotsLayout = findViewById(R.id.layoutDots);
        //myViewpager.setAdapter(myAdapter);
        //myViewpager.addOnPageChangeListener(viewListener);
        //addBottomDots(0);
        myImage = findViewById(R.id.pagerSlide);
        sonicGlide.glideImageView(ctx,myImage,myImages[0]);

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
            }
        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

            addBottomDots(i);

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

}
