package com.softhex.sonic;

import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class sonicAvisos extends AppCompatActivity {

    private TabLayout myTabLayout;
    Toolbar myToolbar;
    ActionBar myActionBar;
    private ViewPagerAdapter myAdapter;
    private ViewPager myViewPager;
    private sonicDatabaseCRUD DBC;
    Button filtro;
    List<sonicAvisosHolder> lista = new ArrayList<sonicAvisosHolder>();
    LinearLayout container;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_avisos);

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        DBC = new sonicDatabaseCRUD(getApplicationContext());


        myToolbar = (Toolbar) findViewById(R.id.avisos_toolbar);
        setSupportActionBar(myToolbar);
        myActionBar = getSupportActionBar();
        myActionBar.setTitle(R.string.avisosTitulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Drawable backArrow = getResources().getDrawable(R.mipmap.ic_trending_up_black_24dp);
        //backArrow.setColorFilter(getResources().getColor(R.color.md_grey_900), PorterDuff.Mode.SRC_ATOP);
        //getSupportActionBar().setHomeAsUpIndicator(backArrow);

        myViewPager = (ViewPager) findViewById(R.id.avisos_pager);
        setUpViewPager(myViewPager);

        /*myTabLayout = (TabLayout) findViewById(R.id.sonic_avisos_tabs);
        myTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.tab_selected));
        myTabLayout.setupWithViewPager(myViewPager);

        if(prefs.getBoolean("personalizacao_switch_icones", false)){
            setUpTabIcons();
        }else{
            setUpTabText();
        }*/

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    public void setUpViewPager(ViewPager viewpager){
        myAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        myAdapter.addFragment(new sonicAvisosFragNaoLidos(), "");
        //myAdapter.addFragment(new sonicAvisosFragNaoLidos(), "");
        viewpager.setAdapter(myAdapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
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

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    public void listarGrupos(){

        new MyAsyncTask().execute();

    }

    class MyAsyncTask extends AsyncTask<Integer, Integer, Integer>{

        @Override
        protected Integer doInBackground(Integer... integers) {
            //lista = DBC.GrupoProduto.selectGrupoProduto();
            return lista.size();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if(integer>0){
                //preencherFiltros();
            }

        }
    }


}
