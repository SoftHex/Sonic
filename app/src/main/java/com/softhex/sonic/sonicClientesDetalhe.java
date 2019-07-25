package com.softhex.sonic;

import android.animation.LayoutTransition;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.transition.Slide;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class sonicClientesDetalhe extends AppCompatActivity {

    private Toolbar myToolbar;
    private ViewPager myViewpager;
    private TextView[] dots;
    private LinearLayout dotsLayout;
    private String[] myImages = new String[]{
            "13480.jpg",
            "4176.jpg",
            "13484.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonic_clientes_detalhe);

        myViewpager = findViewById(R.id.pager);

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

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    public void slideImages(){

        sonicSlideImageAdapter adapter = new sonicSlideImageAdapter(this, myImages);
        dotsLayout = findViewById(R.id.layoutDots);
        myViewpager.setAdapter(adapter);
        myViewpager.addOnPageChangeListener(viewListener);
        addBottomDots(0);

    }

    private void addBottomDots(int position){

        dots = new TextView[3];
        dotsLayout.removeAllViews();
        for(int i=0; i<dots.length; i++)
        {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(30);
            dots[i].setTextColor(getResources().getColor(R.color.colorTextDark));
            dotsLayout.addView(dots[i]);
        }
        if(dots.length>0){
            dots[position].setTextColor(getResources().getColor(R.color.colorPrimaryWhite));
        }
    }

    private int getItem(int i){
        return myViewpager.getCurrentItem()+i;
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
