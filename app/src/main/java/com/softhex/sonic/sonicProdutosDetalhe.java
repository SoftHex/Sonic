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
    private TextView tvDescricao, tvGrupo, tvDetalhe, tvPreco;
    private ImageView myImage;
    private ActionBar myActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonic_produtos_detalhe);

        ctx = this;
        DBC = new sonicDatabaseCRUD(this);
        //myViewpager = findViewById(R.id.pagerSlide);
        myAppBar = findViewById(R.id.appbar);
        myCollapsingToolbar = findViewById(R.id.collapsingToolbar);

        tvDescricao = findViewById(R.id.tvDescricao);
        tvGrupo = findViewById(R.id.tvGrupo);
        tvDetalhe = findViewById(R.id.tvDetalhe);
        tvPreco = findViewById(R.id.tvPreco);
        tvDescricao.setText(sonicConstants.PUT_EXTRA_PRODUTO_NOME);
        tvGrupo.setText(sonicConstants.PUT_EXTRA_PRODUTO_GRUPO);

        createInterface();
        loadImage();

        myAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if((myCollapsingToolbar.getHeight()+verticalOffset)<(2 * ViewCompat.getMinimumHeight(myCollapsingToolbar))){
                    myToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryWhite), PorterDuff.Mode.SRC_ATOP);
                    //myActionBar.setTitle("Produtos");
                }else {
                    myToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryBlack), PorterDuff.Mode.SRC_ATOP);
                    //myActionBar.setTitle("");
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

        //myCollapsingToolbar.setTitle(sonicConstants.PUT_EXTRA_PRODUTO_NOME);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    public void loadImage(){

        List<sonicProdutosHolder> myList;
        myList = DBC.Produto.selectProdutoselecionado();

        File file = new File(Environment.getExternalStorageDirectory(), sonicConstants.LOCAL_IMG_CATALOGO + myList.get(0).getCodigo() + ".JPG");

        myImages[0] = file.exists() ? file.toString() : sonicUtils.getURLForResource(R.drawable.nophoto);

        myImage = findViewById(R.id.pagerSlide);
        sonicGlide.glideImageView(ctx,myImage,myImages[0]);

    }

}
