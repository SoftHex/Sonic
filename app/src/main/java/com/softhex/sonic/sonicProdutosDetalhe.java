package com.softhex.sonic;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
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
    private String title="";

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

        createInterface();
        loadImage();

        Bundle extras = getIntent().getExtras();
        if(savedInstanceState==null){
            if(extras!=null){
                tvDescricao.setText(extras.getString("PRODUTO_NOME"));
                tvGrupo.setText(extras.getString("PRODUTO_GRUPO"));
                title = extras.getString("PRODUTO_NOME");
            }
        }else{
            tvDescricao.setText(extras.getString("PRODUTO_NOME"));
            tvGrupo.setText(extras.getString("PRODUTO_GRUPO"));
            title = extras.getString("PRODUTO_NOME");
        }

        myAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if((myCollapsingToolbar.getHeight()+verticalOffset)<(2 * ViewCompat.getMinimumHeight(myCollapsingToolbar))){
                    myToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryWhite), PorterDuff.Mode.SRC_ATOP);
                    myCollapsingToolbar.setTitle(title);
                }else {
                    myToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryBlack), PorterDuff.Mode.SRC_ATOP);
                    myCollapsingToolbar.setTitle("");
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

        myCollapsingToolbar.setTitle(title);

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

        String fileJpg = myList.get(0).getCodigo() + ".JPG";

        myImage = findViewById(R.id.pagerSlide);
        sonicGlide.glideImageView(ctx, myImage, sonicUtils.checkImageJpg(sonicConstants.LOCAL_IMG_CATALOGO, fileJpg, R.drawable.nophoto));

    }

}
