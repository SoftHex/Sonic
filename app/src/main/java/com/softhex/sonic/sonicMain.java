package com.softhex.sonic;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.tabs.TabLayout;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.softhex.view.ProgressProfileView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import zerobranch.androidremotedebugger.AndroidRemoteDebugger;

public class sonicMain extends AppCompatActivity{

    static final String STATE_SCORE = "playerScore";
    static final String STATE_LEVEL = "playerLevel";
    static final int IMAGE_GALLERY_REQUEST = 20;
    private sonicDatabaseCRUD DBC;
    private Intent i;
    private TabLayout myTabLayout;
    private Toolbar myToolbar;
    private ActionBar myActionBar;
    private ViewPager myViewPager;
    private ViewPagerAdapter myAdapter;
    private AccountHeader myHeader;
    private Context mActivity;
    private sonicUtils myUtil;
    private Drawer myDrawer;
    private String usuarioMeta;
    private Boolean allowHomeUpdate = false;
    private PrimaryDrawerItem myDrawerSincronizar;
    private PrimaryDrawerItem myDrawerAvisos;
    private PrimaryDrawerItem myDrawerClientes;
    private PrimaryDrawerItem myDrawerProdutos;
    private PrimaryDrawerItem myDrawerPedidos;
    private PrimaryDrawerItem myDrawerRetorno;
    private PrimaryDrawerItem myDrawerTitulos;
    private PrimaryDrawerItem myDrawerRota;
    private PrimaryDrawerItem myDrawerVendedores;
    private ExpandableDrawerItem myDrawerRelatorios;
    private PrimaryDrawerItem myDrawerSistema;
    private PrimaryDrawerItem myDrawerRedefinir;
    private PrimaryDrawerItem myDrawerExportar;
    private SwitchDrawerItem myDrawerLock;
    private SecondaryDrawerItem myDrawerRankingClientes;
    private SecondaryDrawerItem myDrawerRankingProdutos;
    private SecondaryDrawerItem myDrawerClientesSemCompraa;
    private SecondaryDrawerItem myDrawerPremiacoes;
    private LinearLayout llDetail;
    private TextView tvEmpresa, tvSaudacao, tvUsuario, tvPedidos, tvDesemprenho, tvVendido, tvMeta;
    private ProgressProfileView myProgressProfile;
    private ProgressBar pbEmpresa, pbSaudacaoUsuario, pbPedidos, pbDesempenho, pbVendido, pbMeta;
    private String  vendido;
    private LinearLayout llStar, llChecked;
    private Float percentualProgress, percentualTotal;
    private sonicFtp myFtp;
    private int back = sonicUtils.Randomizer.generate(0,4);
    private sonicPreferences prefs;
    List<sonicUsuariosHolder> listaUser;
    List<sonicEmpresasHolder> listaEmpresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_main);

        AndroidRemoteDebugger.init(this);

        mActivity = this;
        DBC = new sonicDatabaseCRUD(mActivity);
        prefs = new sonicPreferences(mActivity);
        sonicConstants.BACK = back;

        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        myActionBar = getSupportActionBar();
        myActionBar.setTitle(R.string.appName);

        // INSTANCIANDO OS OBJETOS
        llDetail = findViewById(R.id.llDetail);
        usuarioMeta = sonicConstants.USUARIO_ATIVO_META_VENDA;
        tvEmpresa = findViewById(R.id.tvEmpresa);
        tvSaudacao = findViewById(R.id.tvSaudacao);
        tvUsuario = findViewById(R.id.tvUsuario);
        tvPedidos = findViewById(R.id.tvPedidos);
        tvDesemprenho = findViewById(R.id.tvDesemprenho);
        tvVendido = findViewById(R.id.tvVendido);
        tvMeta = findViewById(R.id.tvMeta);
        pbEmpresa = findViewById(R.id.pbEmpresa);
        pbSaudacaoUsuario = findViewById(R.id.pbSaudacaoUsuario);
        pbPedidos = findViewById(R.id.pbPedidos);
        pbDesempenho = findViewById(R.id.pbDesempenho);
        pbVendido = findViewById(R.id.pbVendido);
        pbMeta = findViewById(R.id.pbMeta);
        llStar = findViewById(R.id.llStar);
        llChecked = findViewById(R.id.llChecked);
        myProgressProfile = findViewById(R.id.myProgressProfile);

        // DISPLAY DRAWABLE FILE IN LINEAR LAYOUT
        Glide.with(getBaseContext())
                .load(getResources().getDrawable(sonicConstants.BACKS[back]))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                .fitCenter()
                .into(new CustomTarget<Drawable>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        llDetail.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

        // CARREGAR FOTO DO PERFIL
        File file = new File(prefs.Path.getEnvironmente()+prefs.Path.getProfilePath()+prefs.Users.getEmpresaId()+prefs.Users.getUsuarioId()+".JPG");
        if(file.exists()){
            Log.d("File", "EXISTE");
            Glide.get(getBaseContext()).clearMemory();
            Glide.with(getBaseContext())
                    .load(file)
                    .placeholder(R.drawable.no_profile)
                    .apply(new RequestOptions().override(300,300))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                    .into(myProgressProfile);
        }

        myViewPager = findViewById(R.id.pagerSlide);
        setUpViewPager(myViewPager);

        myTabLayout = findViewById(R.id.tab);
        myTabLayout.setupWithViewPager(myViewPager);

        myTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getIcon()!=null){
                    myTabLayout.getTabAt(tab.getPosition()).getIcon().setColorFilter(getResources().getColor(R.color.iconTabSelected), PorterDuff.Mode.SRC_ATOP);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if(tab.getIcon()!=null){
                    myTabLayout.getTabAt(tab.getPosition()).getIcon().setColorFilter(getResources().getColor(R.color.iconTabUnselected), PorterDuff.Mode.SRC_ATOP);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        createDrawerMenu();
        calcularPercentual("2200000",usuarioMeta);
        lerDadosUsuario();
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //calcularPercentual("1234500",usuarioMeta);
                                    //new lerDadosUsuarioAsyncTask().execute();
                                    //lerDadosUsuario();


                                }
                            }
                , sonicUtils.Randomizer.generate(1500, 3000));


        // ATUALIZA A BADGE DE AVISOS
        //new myAssyncTask().execute(2);
        // ATUALIZA A BADGE DE CLIENTES
        new myAssyncTask().execute(3);
        // ATUALIZA A BADGE DE PRODUTOS
        new myAssyncTask().execute(4);

        tvEmpresa.setText(prefs.Users.getEmpresaNome());
        tvUsuario.setText(prefs.Users.getUsuarioNome());
        tvSaudacao.setText(sonicUtils.saudacao());

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SCORE, 1);
        outState.putInt(STATE_LEVEL, 2);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            //mCurrentScore = savedInstanceState.getInt(STATE_SCORE);
            //mCurrentLevel = savedInstanceState.getInt(STATE_LEVEL);
        } else {
            // Probably initialize members with default values for a new instance
        }
    }

    public void calcularPercentual(String vend, String uMeta){
        // FAZER CALCULO DO PERCENTUAL DE ATUAÇÃO
        vendido = vend;
        //percentualProgress = (Float.valueOf(vend)/Float.valueOf(uMeta))*100;
        //percentualTotal = percentualProgress;
        int [] progress2 = null;
        llStar.setVisibility(View.INVISIBLE);
        llChecked.setVisibility(View.INVISIBLE);
        /*tvDesemprenho.setTextColor(getResources().getColor(R.color.colorPrimaryWhite));
        tvMeta.setTextColor(getResources().getColor(R.color.colorPrimaryWhite));
        if(percentualProgress <=25.0){
            progress2 = mActivity.getResources().getIntArray(R.array.progressProfile0t025);
        }else if(percentualProgress <=50.0){
            progress2 = mActivity.getResources().getIntArray(R.array.progressProfile25t050);
        }else if(percentualProgress <=75.0){
            progress2 = mActivity.getResources().getIntArray(R.array.progressProfile50to75);
        }else if(percentualProgress <100.0){
            progress2 = mActivity.getResources().getIntArray(R.array.progressProfile75to100);
        }else{
            progress2 = mActivity.getResources().getIntArray(R.array.progressProfile100);
            percentualProgress = 100f;
            llChecked.setVisibility(View.VISIBLE);
            llStar.setVisibility(View.VISIBLE);
            tvDesemprenho.setTextColor(getResources().getColor(R.color.colorPrimaryGreen));
            tvMeta.setTextColor(getResources().getColor(R.color.colorPrimaryGreen));
        }*/
        //progress = mActivity.getResources().getIntArray(R.array.progressProfile75to100);
        myProgressProfile.setProgressGradient(mActivity.getResources().getIntArray(R.array.progressProfile100));
        myProgressProfile.setProgress(10f);
        myProgressProfile.startAnimation();
    }

    public void lerDadosUsuario(){

        // ESCONDE O PROGRESS E EXIBE OS VALORES
        pbPedidos.setVisibility(View.GONE);
        pbDesempenho.setVisibility(View.GONE);
        pbVendido.setVisibility(View.GONE);
        pbMeta.setVisibility(View.GONE);
        tvPedidos.setVisibility(View.VISIBLE);
        tvPedidos.setText("12");
        tvDesemprenho.setVisibility(View.VISIBLE);
        //tvDesemprenho.setText(percentualProgress==100.0 ? (String.format("%.0f", percentualTotal)+"%") : (String.format("%.1f", percentualProgress)+"%"));
        tvVendido.setVisibility(View.VISIBLE);
        //tvVendido.setText(new sonicUtils(mActivity).Number.stringToMoeda2(vendido));
        tvMeta.setVisibility(View.VISIBLE);
        //tvMeta.setText(new sonicUtils(mActivity).Number.stringToMoeda2(usuarioMeta));

    }

    public void createDrawerMenu(){

        listaEmpresa = DBC.Empresa.empresaUsuario();
        listaUser = DBC.Usuario.selectUsuarioAtivo();

        myHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(sonicConstants.BACKS[back])
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {

                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {

                        if(!currentProfile) {
                            setTvEmpresa(profile.getIdentifier());
                            new myAssyncTask().execute(3);
                            new myAssyncTask().execute(4);
                        }

                        sonicConstants.EMPRESA_SELECIONADA_NOME = profile.getEmail().toString();
                        sonicConstants.EMPRESA_SELECIONADA_ID = (int)profile.getIdentifier();
                        usuarioMeta = listaUser.get(0).getMetaVenda();
                        tvEmpresa.setText(profile.getEmail().toString());
                        tvMeta.setText(new sonicUtils(getBaseContext()).Number.stringToMoeda2(usuarioMeta));
                        File file = new File(prefs.Path.getEnvironmente()+prefs.Path.getProfilePath()+prefs.Users.getEmpresaId()+prefs.Users.getUsuarioId()+".JPG");
                        String picture = file.exists() ? file.toString() : sonicUtils.getURIForResource(R.drawable.no_profile);
                        sonicGlide.glideImageView(mActivity,myProgressProfile,picture);
                        calcularPercentual("2200000", usuarioMeta);
                        lerDadosUsuario();
                        refreshHomeFragment();

                        return true;
                    }

                })
                .withOnAccountHeaderProfileImageListener(new AccountHeader.OnAccountHeaderProfileImageListener() {
                    @Override
                    public boolean onProfileImageClick(View view, IProfile profile, boolean current) {
                        return false;
                    }

                    @Override
                    public boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {

                        if(current){
                            Intent i = new Intent(Intent.ACTION_PICK);
                            File picture = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                            String path = picture.getPath();

                            i.setDataAndType(Uri.parse(path), "image/*");

                            startActivityForResult(i, IMAGE_GALLERY_REQUEST);
                        }

                        return false;
                    }
                })
                .build();

        for (int i = 0; i< listaEmpresa.size(); i++)

        {

            File file = new File(prefs.Path.getEnvironmente()+prefs.Path.getProfilePath()+prefs.Users.getEmpresaId()+prefs.Users.getUsuarioId()+".JPG");

            if(i<3){

                if(file.exists()){
                    myHeader.addProfiles(
                            new ProfileDrawerItem()
                                    .withName(prefs.Users.getUsuarioNome() +" ("+ prefs.Users.getUsuarioCargo() +")")
                                    .withEmail(listaEmpresa.get(i).getNomeFantasia())
                                    .withIcon(sonicUtils.centerAndCropBitmap(BitmapFactory.decodeFile(file.toString())))
                                    .withIdentifier(listaEmpresa.get(i).getCodigo())
                    );
                }else{

                    myHeader.addProfiles(
                            new ProfileDrawerItem()
                                    .withName(prefs.Users.getUsuarioNome() +" ("+ prefs.Users.getUsuarioCargo() +")")
                                    .withEmail(listaEmpresa.get(i).getNomeFantasia())
                                    .withIcon(getResources().getDrawable(R.drawable.no_profile))
                                    .withIdentifier(listaEmpresa.get(i).getCodigo())
                    );

                }

            }
        }

        myDrawerSincronizar = new PrimaryDrawerItem()
                .withIdentifier(1)
                .withName(R.string.sincronizarTitulo)
                .withDescription(R.string.sincronizarSubTitulo)
                .withSelectable(false)
                .withIcon(getResources().getDrawable(R.mipmap.ic_sync_grey600_24dp));

        myDrawerAvisos = new PrimaryDrawerItem()
                .withIdentifier(2)
                .withName(R.string.avisosTitulo)
                .withDescription(R.string.avisosSubTitulo)
                .withSelectable(false)
                .withBadge("0").withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700))//.withCornersDp(20).withPadding(2))
                //.withBadge("100")//.withBadgeStyle(new BadgeStyle().withColor(sonicMain.this.getResources().getColor( R.color.chart_red)).withCornersDp(15).withPadding(2))
                .withIcon(getResources().getDrawable(R.mipmap.ic_message_text_grey600_24dp));

        myDrawerClientes = new PrimaryDrawerItem()
                .withIdentifier(3)
                .withName(R.string.clientesTitulo)
                .withDescription(R.string.clientesSubTitulo)
                .withSelectable(false)
                .withBadge("0").withBadgeStyle(new BadgeStyle().withTextColor(getResources().getColor(R.color.colorPrimaryWhite)).withColor(getResources().getColor(R.color.colorSpotLight2)))
                .withIcon(getResources().getDrawable(R.mipmap.ic_account_multiple_grey600_24dp));

        myDrawerProdutos = new PrimaryDrawerItem()
                .withIdentifier(4)
                .withName(R.string.produtosTitulo)
                .withDescription(R.string.produtosSubTitulo)
                .withSelectable(false)
                .withBadge("0").withBadgeStyle(new BadgeStyle().withTextColor(getResources().getColor(R.color.colorPrimaryWhite)).withColor(getResources().getColor(R.color.colorSpotLight2)))
                .withIcon(getResources().getDrawable(R.mipmap.ic_cube_grey600_24dp));

        myDrawerPedidos = new PrimaryDrawerItem()
                .withIdentifier(5)
                .withName(R.string.pedidosTitulo)
                .withDescription(R.string.pedidosSubTitulo)
                .withSelectable(false)
                .withBadge("0").withBadgeStyle(new BadgeStyle().withTextColor(getResources().getColor(R.color.colorPrimaryWhite)).withColor(getResources().getColor(R.color.colorSpotLight2)))
                .withIcon(getResources().getDrawable(R.mipmap.ic_cart_grey600_24dp));

        myDrawerRetorno = new PrimaryDrawerItem()
                .withIdentifier(6)
                .withName(R.string.retornoTitulo)
                .withDescription(R.string.relatoriosSubTitulo)
                .withSelectable(false)
                .withIcon(getResources().getDrawable(R.mipmap.ic_rotate_3d_grey600_24dp));

        myDrawerTitulos = new PrimaryDrawerItem()
                .withIdentifier(7)
                .withName(R.string.titulosTitulo)
                .withDescription(R.string.titulosSubTitulo)
                .withSelectable(false)
                .withIcon(getResources().getDrawable( R.mipmap.ic_coin_grey600_24dp));

        myDrawerRota = new PrimaryDrawerItem()
                .withIdentifier(8)
                .withName(R.string.rotaTitulo)
                .withDescription(R.string.rotaSubTitulo)
                .withSelectable(false)
                .withIcon(getResources().getDrawable(R.mipmap.ic_map_marker_radius_grey600_24dp));

        myDrawerVendedores = new PrimaryDrawerItem()
                .withIdentifier(9)
                .withName(R.string.vendedoresTitulo)
                .withDescription(R.string.vendedoresSubTitulo)
                .withSelectable(false)
                .withIcon(getResources().getDrawable(R.mipmap.ic_account_tie_grey600_24dp));

        myDrawerRelatorios = new ExpandableDrawerItem()
                .withIdentifier(10)
                .withName(R.string.relatoriosTitulo)
                .withDescription(R.string.relatoriosSubTitulo)
                .withIcon(getResources().getDrawable(R.mipmap.ic_finance_grey600_24dp))
                .withIsExpanded(false)
                .withSelectable(false)
                .withSubItems(
                        myDrawerRankingClientes =  new SecondaryDrawerItem()
                                .withIdentifier(14)
                                .withSelectable(false)
                                .withName(R.string.rankingClientes).withLevel(2)
                                .withIcon(R.mipmap.ic_chart_line_variant_grey600_24dp),
                        myDrawerRankingProdutos = new SecondaryDrawerItem()
                                .withIdentifier(15)
                                .withSelectable(false)
                                .withName(R.string.rankingProdutos).withLevel(2)
                                .withIcon(R.mipmap.ic_chart_line_variant_grey600_24dp),
                        myDrawerClientesSemCompraa = new SecondaryDrawerItem()
                                .withIdentifier(16)
                                .withSelectable(false)
                                .withName(R.string.clientesSemCompra).withLevel(2)
                                .withIcon(R.mipmap.ic_chart_line_variant_grey600_24dp),
                        myDrawerPremiacoes = new SecondaryDrawerItem()
                                .withIdentifier(17)
                                .withSelectable(false)
                                .withName(R.string.premiacoes).withLevel(2)
                                .withIcon(R.mipmap.ic_chart_line_variant_grey600_24dp)
                        /*.withIcon(getResources().getDrawable(R.mipmap.ic_settings_black_24dp))*/
                );

        myDrawerSistema = new PrimaryDrawerItem()
                .withIdentifier(11)
                .withName(R.string.sistemaTitulo)
                .withSelectable(false)
                .withIcon(getResources().getDrawable(R.mipmap.settings_24));

        myDrawerRedefinir = new PrimaryDrawerItem()
                .withIdentifier(12)
                .withName(R.string.redefinirTitulo)
                .withSelectable(false)
                .withIcon(getResources().getDrawable(R.mipmap.ic_wrench_grey600_24dp));

        PrimaryDrawerItem item13 = new PrimaryDrawerItem()
                .withIdentifier(13)
                .withName("Sair")
                .withSelectable(false)
                .withIcon(getResources().getDrawable(R.mipmap.ic_power_settings_new_white_24dp));

        myDrawerExportar = new PrimaryDrawerItem()
                .withIdentifier(18)
                .withName("Exportar")
                .withSelectable(false)
                .withIcon(getResources().getDrawable(R.mipmap.ic_database_export_grey600_24dp));

        myDrawerLock = new SwitchDrawerItem()
                .withIdentifier(19)
                .withChecked(new sonicPreferences(mActivity).Users.getStatusLogin())
                .withName("Sistema")
                .withDescription("Solicitar senha ao entrar")
                .withIcon(getResources().getDrawable(R.mipmap.ic_cellphone_lock_grey600_24dp))
                .withOnCheckedChangeListener(new OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {

                        new sonicPreferences(mActivity).Users.setStatusLogin(isChecked);

                    }
        });

        myDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(myToolbar)
                .withAccountHeader(myHeader)
                .withSelectedItem(-1)
                .withShowDrawerUntilDraggedOpened(false)
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {

                        // AVISOS
                        //new myAssyncTask().execute(2,null,2);
                        // PRODUTOS
                        //new myAssyncTask().execute(4,null,4);

                        /*int result = DBC.Avisos.countNaoLidos();
                        if(result != avisoNaoLido) {

                            if(result>0){
                                updateBadge(result + "", 2);
                            }else{
                                updateBadge(null, 2);
                            }

                        }

                        avisoNaoLido = result;*/

                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {

                    }

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {

                    }

                })
                //.addStickyDrawerItems(
                        //myDrawerAvisos
                //)
                .addDrawerItems(
                        myDrawerSincronizar,
                        myDrawerAvisos,
                        new SectionDrawerItem().withName(R.string.inicioTitulo),
                        myDrawerClientes,
                        myDrawerProdutos,
                        myDrawerPedidos,
                        myDrawerRetorno,
                        myDrawerTitulos,
                        myDrawerRota,
                        myDrawerVendedores,
                        new SectionDrawerItem().withName(R.string.relatoriosTitulo),
                        myDrawerRelatorios,
                        new SectionDrawerItem().withName("Segurança"),
                        myDrawerLock,
                        new SectionDrawerItem().withName(R.string.extraTitulo),
                        myDrawerSistema,
                        myDrawerRedefinir,
                        myDrawerExportar
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        switch (view.getId()){
                            case 1:
                                new myAsyncStartActivity().execute(sonicSincronizacao.class);
                                break;
                            case 2:
                                new myAsyncStartActivity().execute(sonicAvisos.class);
                                break;
                            case 3:
                                new myAsyncStartActivity().execute(sonicClientes.class);
                                break;
                            case 4:
                                new myAsyncStartActivity().execute(sonicProdutos.class);
                                break;
                            case 5:
                                new myAsyncStartActivity().execute(sonicLogin.class);
                                //i = new Intent(sonicMain.this, go_pedido.class);
                                //startActivity(i);
                                break;
                            case 6:
                                //i = new Intent(sonicMain.this, sonicRetorno.class);
                                //startActivity(i);
                                break;
                            case 7:
                                //i = new Intent(sonicMain.this, sonicTitulos.class);
                                //startActivity(i);
                                break;
                            case 8:
                                i = new Intent(sonicMain.this, sonicRota.class);
                                startActivity(i);
                                break;
                            case 9:
                                //i = new Intent(sonicMain.this, sonicVendedores.class);
                                //startActivity(i);
                                //normalListDialogNoTitle("MENU");
                                //mActivity.getSharedPreferences("PREFERENCE_DATA",0).edit().clear().apply();
                                prefs.Util.deleteCache();
                                prefs.Util.clearPreferences();
                                break;
                            case 11:
                                new myAsyncStartActivity().execute(sonicSistema.class);
                                //i = new Intent(sonicMain.this, sonicSistema.class);
                                //startActivity(i);
                                //border_bottom();
                                break;
                            case 12:
                                dialogRedefinir();
                                //normalListDialogNoTitle("MENU");
                                break;
                            case 13:
                                break;
                            case 14:
                                //i = new Intent(sonicMain.this, sonicRankingClientes.class);
                                //startActivity(i);
                                break;
                            case 15:
                                //i = new Intent(sonicMain.this, sonicRankingProdutos.class);
                                //startActivity(i);
                                break;
                            case 16:
                                //i = new Intent(sonicMain.this, sonicClientesSemCompra.class);
                                //startActivity(i);
                                break;
                            case 18:
                                dialogBackup();
                                break;

                            default:
                                break;
                        }
                        return false;
                    }

                })
                .build();

    }

    class myAsyncStartActivity extends AsyncTask<Class, Void, Void>{


        @Override
        protected Void doInBackground(Class... classes) {
            Intent i = new Intent(sonicMain.this, classes[0]);
            startActivity(i);
            return null;
        }
    }


    class lerDadosUsuarioAsyncTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            listaEmpresa = DBC.Empresa.empresaUsuario();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    lerDadosUsuario();

                }
            });
        }
    }

    class myUserAsyncTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    public void setUpViewPager(ViewPager viewpager){
        myAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        myAdapter.addFragment(new sonicMainHome1(), "", R.mipmap.ic_chart_line_variant_white_24dp);
        myAdapter.addFragment(new sonicMainHome1(), "Perform");
        myAdapter.addFragment(new sonicMainHome1(), "Atuação");

        viewpager.setAdapter(myAdapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private final List<Integer> mIcon = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            if(mIcon.get(position)!=null){
                myTabLayout.getTabAt(position).setIcon(mIcon.get(position));
                if(myTabLayout.getTabAt(position).isSelected()){
                    myTabLayout.getTabAt(position).getIcon().setColorFilter(getResources().getColor(R.color.iconTabSelected), PorterDuff.Mode.SRC_ATOP);
                }
            }
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
            mIcon.add(null);
        }

        public void addFragment(Fragment fragment, String title, Integer icon) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
            mIcon.add(icon);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return super.getItemPosition(object);
        }
    }

    public void updateBadge( int id, String badge){

        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                myDrawer.updateBadge(id, new StringHolder(badge));

            }
        });

    }

    public class myAssyncTask extends AsyncTask<Integer, Void, Integer[]>{
        @Override
        protected Integer[] doInBackground(Integer... integers) {

            Integer[] result = new Integer[2];

            switch (integers[0]){
                case 2:
                    result[0] = integers[0];
                    result[1] = DBC.Aviso.countNaoLido();
                    break;
                case 3:
                    result[0] = integers[0];
                    result[1] = DBC.Cliente.countPorEmpresa();
                    //updateBadge(3, result[1]+"");
                    break;
                case 4:
                    result[0] = integers[0];
                    result[1] = DBC.Produto.countPorEmpresa();
                    //updateBadge(4, result[1]+"");
                    break;
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer[] integers) {
            super.onPostExecute(integers);

            if(integers[1]>0){
                updateBadge(integers[0],integers[1]+"");
            }else {
                updateBadge(integers[0], "0");
            }

        }
    }

    private void dialogBackup() {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(mActivity);
        builder.setMessage("O backup será salvo no diretório: "+ sonicConstants.LOCAL_DATA_BACKUP)
                .setPositiveButton("FAZER BACKUP", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        salvarBackup();
                    }
                })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();

    }

    public void salvarBackup(){

        ProgressDialog myProgressDialog = new ProgressDialog(mActivity);
        myProgressDialog.setCancelable(false);
        myProgressDialog.setMessage("Salvando, aguarde...");
        myProgressDialog.setMax(1);
        myProgressDialog.setProgressStyle(0);
        myProgressDialog.show();

        String result = new sonicDatabase(mActivity).exportDatabase();

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    myProgressDialog.dismiss();

                                    new sonicDialog(mActivity).showMS("Backup",result, sonicDialog.MSG_INFO);

                                }
                            }
                , 1000);



    }

    public void setTvEmpresa(long tvEmpresa){

        allowHomeUpdate = true;

        if(tvEmpresa ==0){
            DBC.Empresa.marcarEmpresa();
        }else{
            DBC.Empresa.desmarcarEmpresa();
            DBC.Empresa.setSelecionada((int) tvEmpresa);
        }


        refreshHomeFragment();

    }

    public void refreshHomeFragment(){

        if(allowHomeUpdate){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.detach(myAdapter.getItem(0)).attach(myAdapter.getItem(0)).commit();
            allowHomeUpdate = false;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sonic_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.itSettings:
                i = new Intent(getBaseContext(), sonicMainConfiguracoes.class);
                startActivity(i);
                break;
            case R.id.itNotificacao:
                Toast.makeText(getApplicationContext(), "Notificação", Toast.LENGTH_SHORT).show();
                return false;
            case R.id.itSincronizar:
                myFtp = new sonicFtp(mActivity);
                sonicConstants.DOWNLOAD_TYPE = "DADOS";
                String file = String.format("%5s",listaUser.get(0).getCodigo()).replace(" ", "0")+".TXT";
                Log.d("FILE", file);
                myFtp.downloadFile2(sonicConstants.FTP_USUARIOS+file, sonicConstants.LOCAL_TEMP+file);
                //Toast.makeText(getApplicationContext(), "Sincronizar", Toast.LENGTH_SHORT).show();
                return false;

        }

       return false;
    }


    public void dialogRedefinir(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.go_sure).setPositiveButton("REDEFINIR", dialogClickListener).setNegativeButton("CANCELAR", dialogClickListener).show();
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    sonicDatabaseCRUD DBC = new sonicDatabaseCRUD(mActivity);
                    DBC.Database.truncateAllTables();
                    new sonicStorage(sonicMain.this).deleteFiles(sonicConstants.LOCAL_TEMP);
                    prefs.Util.clearPreferences();
                    prefs.Util.deleteCache();
                    Intent mStartActivity = new Intent(sonicMain.this, MainActivity.class);
                    int mPendingIntentId = 123456;
                    PendingIntent mPendingIntent = PendingIntent.getActivity(mActivity, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager mgr = (AlarmManager) mActivity.getSystemService(Context.ALARM_SERVICE);
                    mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                    System.exit(0);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_CANCELED && data != null) {

            if (requestCode == IMAGE_GALLERY_REQUEST) {

                Uri imageUri = data.getData();

                myUtil = new sonicUtils(mActivity);

                long empresaId = myHeader.getActiveProfile().getIdentifier();

                myUtil.Arquivo.saveUriFile(imageUri, prefs.Path.getProfilePath(), prefs.Users.getEmpresaId(), prefs.Users.getUsuarioId());

                String url = Environment.getExternalStorageDirectory().getPath() + prefs.Path.getProfilePath() + prefs.Users.getEmpresaId() + "_" + prefs.Users.getUsuarioId() + ".JPG";

                myHeader.getActiveProfile().withIcon(sonicUtils.centerAndCropBitmap(BitmapFactory.decodeFile(url)));

                myHeader.updateProfile(myHeader.getActiveProfile());

                Glide.with(getBaseContext())
                        .load(url)
                        .placeholder(R.drawable.no_profile)
                        .apply(new RequestOptions().override(300,300))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                        .into(myProgressProfile);


            }

        }
    }


}


