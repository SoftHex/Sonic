package com.softhex.sonic;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class sonicProdutosDetalhe extends AppCompatActivity {

    private Toolbar myToolbar;
    private ViewPager myViewpager;
    private TextView[] dots;
    private LinearLayout dotsLayout;
    private sonicDatabaseCRUD DBC;
    private CollapsingToolbarLayout myCollapsingToolbar;
    private AppBarLayout myAppBar;
    private String[] myImages = new String[1];
    private Context mContext;
    private TextView tvNome, tvGrupo, tvDescricao, tvPreco, tvUnidadeMedida, tvCodigoAlternativo;
    private TextView tvDataCad, tvNcm, tvPesoBruto, tvPesoLiq, tvEstoqueMin, tvMultip, tvCodEan, tvCodEanTrib;
    private ActionBar myActionBar;
    private LinearLayout linearNew;
    private sonicUtils mUtil;
    private sonicPreferences mPrefs;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMdd");
    private String mDataAtual = mDateFormat.format(new Date());
    private int dias, diasDiff;
    private ProgressBar mProgress;
    private List<sonicProdutosHolder> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonic_produtos_detalhe);

        mContext = this;
        mUtil = new sonicUtils(mContext);
        mPrefs = new sonicPreferences(mContext);
        DBC = new sonicDatabaseCRUD(this);
        myAppBar = findViewById(R.id.appbar);
        myCollapsingToolbar = findViewById(R.id.collapsingToolbar);
        tvUnidadeMedida = findViewById(R.id.tvUnidade);
        tvDataCad = findViewById(R.id.tvDataCadastro);
        tvNcm = findViewById(R.id.tvNcm);
        tvPesoBruto = findViewById(R.id.tvPesoBruto);
        tvPesoLiq = findViewById(R.id.tvPesoLiquido);
        tvEstoqueMin = findViewById(R.id.tvEstoqueMin);
        tvMultip = findViewById(R.id.tvMultiplicidade);
        tvCodEan = findViewById(R.id.tvCodEan);
        tvCodEanTrib = findViewById(R.id.tvCodEanTrib);

        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        myActionBar = getSupportActionBar();
        myActionBar.setTitle("");
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

        myAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if((myCollapsingToolbar.getHeight()+verticalOffset)<(2 * ViewCompat.getMinimumHeight(myCollapsingToolbar))){
                    myToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryWhite), PorterDuff.Mode.SRC_ATOP);
                    myCollapsingToolbar.setTitle(mPrefs.Produtos.getProdutoNome());
                }else {
                    myToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryBlack), PorterDuff.Mode.SRC_ATOP);
                    myCollapsingToolbar.setTitle("");
                }
            }
        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadImage();
            }
        });

        new mAsyncTask().execute(mPrefs.Produtos.getProdutoId());

    }

    class mAsyncTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... integers) {
            mList = DBC.Produto.selectProdutoID(integers[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //mProgress.setVisibility(View.GONE);
            loadDetail(mList);

        }
    }

    public void loadImage(){

        String fileJpg = mPrefs.Produtos.getProdutoId() + ".JPG";

        myViewpager = findViewById(R.id.pagerSlide);

        myImages[0] = sonicUtils.checkImageJpg(sonicConstants.LOCAL_IMG_CATALOGO, fileJpg, R.drawable.nophoto);
        sonicSlideImageAdapter myAdapter = new sonicSlideImageAdapter(this, myImages, false);
        myViewpager.setAdapter(myAdapter);
        linearNew = findViewById(R.id.linearNew);
        String[] array = mContext.getResources().getStringArray(R.array.prefProdutoNovoOptions);
        diasDiff = mUtil.Data.dateDiffDay(mPrefs.Produtos.getProdutoDataCadastro(), mDataAtual);
        dias = mPrefs.Produtos.getDiasNovo().equals(array[0]) ? 30 :
                mPrefs.Produtos.getDiasNovo().equals(array[1]) ? 60 :
                        mPrefs.Produtos.getDiasNovo().equals(array[2]) ? 90 : 30;

        linearNew.setVisibility(diasDiff<=dias ? View.VISIBLE : View.INVISIBLE);


    }

    public void loadDetail(List<sonicProdutosHolder> list){


            tvUnidadeMedida.setText(list.get(0).getUnidadeMedida());
            tvDataCad.setText(list.get(0).getDataCadastro());
            tvNcm.setText(list.get(0).getNcm());
            tvPesoBruto.setText(list.get(0).getPesoBruto());
            tvPesoLiq.setText(list.get(0).getPesoLiquido());
            tvEstoqueMin.setText(String.valueOf(list.get(0).getEstoqueMinimo()));
            tvMultip.setText(String.valueOf(list.get(0).getMultiplicidade()));
            tvCodEan.setText(list.get(0).getCodigoEan());
            tvCodEanTrib.setText(list.get(0).getCodigoEanTributavel());


    }

}
