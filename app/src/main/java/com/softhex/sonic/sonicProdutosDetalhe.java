package com.softhex.sonic;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
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
    private Context ctx;
    private TextView tvDescricao, tvGrupo, tvDetalhe, tvPreco;
    private ActionBar myActionBar;
    private String produtoCod;
    private String produtoNome;
    private String produtoStatus;
    private LinearLayout linearNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonic_produtos_detalhe);

        ctx = this;
        DBC = new sonicDatabaseCRUD(this);
        //myViewpager = findViewById(R.id.pagerSlide);
        myAppBar = findViewById(R.id.appbar);
        myCollapsingToolbar = findViewById(R.id.collapsingToolbar);
        //tvDescricao = findViewById(R.id.tvDescricao);
        //tvGrupo = findViewById(R.id.tvGrupo);
        //tvDetalhe = findViewById(R.id.tvDetalhe);
        //tvPreco = findViewById(R.id.tvPreco);

        Bundle extras = getIntent().getExtras();
        if(savedInstanceState==null){
            if(extras!=null){
                produtoNome = extras.getString("PRODUTO_NOME");
                produtoCod = extras.getString("PRODUTO_CODIGO");
                produtoStatus = extras.getString("PRODUTO_STATUS");
            }
        }else{
            produtoNome = extras.getString("PRODUTO_NOME");
            produtoCod = extras.getString("PRODUTO_CODIGO");
            produtoStatus = extras.getString("PRODUTO_STATUS");
        }

        createInterface();
        loadImage();

        myAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if((myCollapsingToolbar.getHeight()+verticalOffset)<(2 * ViewCompat.getMinimumHeight(myCollapsingToolbar))){
                    myToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryWhite), PorterDuff.Mode.SRC_ATOP);
                    //myCollapsingToolbar.setTitle(produtoCod);
                }else {
                    myToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryBlack), PorterDuff.Mode.SRC_ATOP);
                    //myCollapsingToolbar.setTitle("");
                }
            }
        });
    }

    private void createInterface() {

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

        myCollapsingToolbar.setTitle(produtoNome);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    public void loadImage(){

        List<sonicProdutosHolder> myList;
        myList = DBC.Produto.selectProdutoID(50675);

        String fileJpg = produtoCod + ".JPG";

        myViewpager = findViewById(R.id.pagerSlide);

        myImages[0] = sonicUtils.checkImageJpg(sonicConstants.LOCAL_IMG_CATALOGO, fileJpg, R.drawable.nophoto);
        sonicSlideImageAdapter myAdapter = new sonicSlideImageAdapter(this, myImages, false);
        //dotsLayout = findViewById(R.id.layoutDots);
        myViewpager.setAdapter(myAdapter);
        linearNew = findViewById(R.id.linearNew);
        linearNew.setVisibility(produtoStatus.equals("NOVO") ? View.VISIBLE : View.INVISIBLE);
        //myViewpager.setOn
        //addBottomDots(0);

    }

}
