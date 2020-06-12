package com.softhex.sonic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private Button btProximo, btPular;
    private TextView[] dots;
    private LinearLayout dotsLayout;
    private int[] layouts;
    private sonicDatabaseCRUD DBC;
    private Intent i;
    private int current;
    private sonicPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPrefs = new sonicPreferences(this);
        File f = sonicFile.searchImage(sonicConstants.LOCAL_IMG_USUARIO, mPrefs.Users.getEmpresaId());

        Log.d("IMAGEM", f.toString());

        DBC = new sonicDatabaseCRUD(this);

        if (DBC.Database.checkMinimumData()){

            sonicAppearence.layoutWhitNoLogicalMenu(this, getWindow());
            i  = new Intent(MainActivity.this,sonicSplash.class);
            startActivity(i);
            finish();

        }else {
            sonicAppearence.layoutWhitLogicalMenu(this, getWindow());
        }

        viewPager = findViewById(R.id.view_pager);
        dotsLayout = findViewById(R.id.layoutDots);
        btPular = findViewById(R.id.btPular);
        btProximo = findViewById(R.id.btProximo);

        layouts = new int[]{R.layout.sonic_slide_one,R.layout.sonic_slide_two,R.layout.sonic_slide_tree,R.layout.sonic_slide_four};
        addBottomDots(0);
        viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewListener);

        btPular.setOnClickListener((View v)-> {
                //Intent i  = new Intent(MainActivity.this,sonicEmpresa.class);
                //startActivity(i);
                //finish();
                if(current==0){

                    Intent i  = new Intent(MainActivity.this,sonicEmpresa.class);
                    startActivity(i);
                    finish();
                }
                else {
                    current = getItem(-1);
                    viewPager.setCurrentItem(current);
                }
        });

        btProximo.setOnClickListener((View v)->{

                current = getItem(+1);

                if(current<layouts.length){
                    viewPager.setCurrentItem(current);
                }
                else {
                        Intent i  = new Intent(MainActivity.this,sonicEmpresa.class);
                        startActivity(i);
                        finish();
                    }

        });
    }

    private void addBottomDots(int position){

        dots = new TextView[layouts.length];
        dotsLayout.removeAllViews();
        for(int i=0; i<dots.length; i++)
        {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(30);
            dots[i].setTextColor(getResources().getColor(R.color.dotInactive));
            dotsLayout.addView(dots[i]);
        }
        if(dots.length>0){
            dots[position].setTextColor(getResources().getColor(R.color.dotActive));
        }
    }

    private int getItem(int i){
        return viewPager.getCurrentItem()+i;
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener(){
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addBottomDots(position);

            if(position==layouts.length-1){
                btProximo.setText(R.string.btIniciar);
            }
            else{
                btProximo.setText(R.string.btProximo);
                btPular.setText(R.string.btVoltar);
                if(position==0){
                    btPular.setText(R.string.btPular);
                }
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public class ViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(layouts[position],container,false);
            container.addView(v);
            return v;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v =(View)object;
            container.removeView(v);
        }
    }
}
