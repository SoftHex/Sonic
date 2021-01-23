package com.softhex.sonic;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Html;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.holder.StringHolder;
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
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import zerobranch.androidremotedebugger.AndroidRemoteDebugger;

public class sonicMain extends AppCompatActivity{

    static final String STATE_SCORE = "playerScore";
    static final String STATE_LEVEL = "playerLevel";
    static final int IMAGE_GALLERY_REQUEST = 20;
    private sonicDatabaseCRUD mData;
    private Intent i;
    private TabLayout mTabLayout;
    private Toolbar myToolbar;
    private ActionBar mActionBar;
    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;
    private AccountHeader mHeader;
    private Activity mActivity;
    private sonicUtils mUtil;
    private Drawer mDrawer;
    private String usuarioMeta;
    private PrimaryDrawerItem mDrawerSincronizar;
    private PrimaryDrawerItem mDrawerAvisos;
    private PrimaryDrawerItem mDrawerClientes;
    private PrimaryDrawerItem mDrawerProdutos;
    private PrimaryDrawerItem mDrawerPedidos;
    private PrimaryDrawerItem mDrawerRetorno;
    private PrimaryDrawerItem mDrawerTitulos;
    private PrimaryDrawerItem mDrawerRota;
    private PrimaryDrawerItem mDrawerVendedores;
    private ExpandableDrawerItem mDrawerRelatorios;
    private ExpandableDrawerItem mDrawerSistema;
    private SecondaryDrawerItem myDrawerRedefinir;
    private SecondaryDrawerItem myDrawerExportar;
    private SwitchDrawerItem myDrawerLock;
    private SecondaryDrawerItem myDrawerRankingClientes;
    private SecondaryDrawerItem myDrawerRankingProdutos;
    private SecondaryDrawerItem myDrawerClientesSemCompraa;
    private SecondaryDrawerItem myDrawerPremiacoes;
    private PrimaryDrawerItem mDrawerItemSair;
    private LinearLayout llDetail;
    private TextView tvEmpresa, tvSaudacao, tvUsuario, tvPedidos, tvDesemprenho, tvVendas, tvMeta;
    private ProgressProfileView mProgressProfile;
    private ProgressBar pbEmpresa, pbSaudacaoUsuario, pbPedidos, pbDesempenho, pbVendido, pbMeta;
    private String  vendido;
    private sonicFtp mFtp;
    private int back = sonicUtils.Randomizer.generate(0,1);
    private sonicPreferences mPrefs;
    private Context mContext;
    private TextView[] dots;
    private Locale mLocal = new Locale( "pt", "BR" );
    private NumberFormat nfVal = NumberFormat.getCurrencyInstance(mLocal);
    private Calendar mCalendar;
    private SimpleDateFormat data = new SimpleDateFormat("yyyyMMdd");
    private LinearLayout dotsLayout;
    private List<sonicUsuariosHolder> listaUser;
    private List<sonicEmpresasHolder> listaEmpresa;
    private LinearLayout llGroupVendas;
    private LinearLayout llSnackBar;
    private Snackbar mSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_main);

        AndroidRemoteDebugger.init(this);
        sonicAppearence.transparentStatusAndNavigation(this);

        mActivity = this;
        mContext = getApplicationContext();
        mData = new sonicDatabaseCRUD(mActivity);
        mPrefs = new sonicPreferences(mActivity);

        myToolbar = findViewById(R.id.mToolbar);
        setSupportActionBar(myToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setTitle(R.string.appName);

        // INSTANCIANDO OS OBJETOS
        llSnackBar = findViewById(R.id.llSnackBar);
        llDetail = findViewById(R.id.llDetail);
        usuarioMeta = sonicConstants.USUARIO_ATIVO_META_VENDA;
        tvEmpresa = findViewById(R.id.tvEmpresa);
        tvSaudacao = findViewById(R.id.tvSaudacao);
        tvUsuario = findViewById(R.id.tvUsuario);
        tvPedidos = findViewById(R.id.tvPedidos);
        tvDesemprenho = findViewById(R.id.tvDesempenho);
        tvVendas = findViewById(R.id.tvVendas);
        llGroupVendas = findViewById(R.id.llGroupVendas);
        dotsLayout = findViewById(R.id.layoutDots);
        //tvMeta = findViewById(R.id.tvMeta);
        pbEmpresa = findViewById(R.id.pbEmpresa);
        pbSaudacaoUsuario = findViewById(R.id.pbSaudacaoUsuario);
        //pbPedidos = findViewById(R.id.pbPedidos);
        //pbDesempenho = findViewById(R.id.pbDesempenho);
        //pbVendido = findViewById(R.id.pbVendido);
        //pbMeta = findViewById(R.id.pbMeta);
        //llStar = findViewById(R.id.llStar);
        //llChecked = findViewById(R.id.llChecked);
        mProgressProfile = findViewById(R.id.myProgressProfile);

        // CARREGAR FOTO DO PERFIL
        File file = new File(Environment.getExternalStorageDirectory(),sonicConstants.LOCAL_IMG_USUARIO+mPrefs.Users.getPicture(mPrefs.Users.getEmpresaId()));

        if(file.exists()){
            Glide.get(getBaseContext()).clearMemory();
            Glide.with(getBaseContext())
                    .load(file)
                    .placeholder(R.drawable.no_profile)
                    .apply(new RequestOptions().override(300,300))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                    .into(mProgressProfile);
        }

        mViewPager = findViewById(R.id.mViewPager);
        setUpViewPager(mViewPager);

        mTabLayout = findViewById(R.id.mTabs);

        addBottomDots(0);

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

        tvEmpresa.setText(mPrefs.Users.getEmpresaNome());
        tvUsuario.setText(mPrefs.Users.getUsuarioNome());
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
        //llStar.setVisibility(View.INVISIBLE);
        //llChecked.setVisibility(View.INVISIBLE);
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
        mProgressProfile.setProgressGradient(mActivity.getResources().getIntArray(R.array.progressProfile25t050));
        mProgressProfile.setProgress(70f);
        mProgressProfile.startAnimation();
    }

    public void lerDadosUsuario(){

        List<sonicVendasValorHolder> mVendas = new ArrayList<>();
        List<sonicVendasPedidosHolder> mPedidos = new ArrayList<>();
        mVendas = mData.Venda.selectVendas();
        mPedidos = mData.Venda.selectPedidos();
        Float value = Float.valueOf(mVendas.get(5).getValor());
        nfVal.setMaximumFractionDigits(2);
        double v = (double)value/100;
        tvVendas.setVisibility(View.VISIBLE);
        tvVendas.setText(nfVal.format(v).replace("R$", "R$ "));
        tvPedidos.setVisibility(View.VISIBLE);
        tvPedidos.setText(String.valueOf(mPedidos.get(5).getTotal()));
        // ESCONDE O PROGRESS E EXIBE OS VALORES
        //pbPedidos.setVisibility(View.GONE);
        //pbDesempenho.setVisibility(View.GONE);
        //pbVendido.setVisibility(View.GONE);
        //pbMeta.setVisibility(View.GONE);
        tvDesemprenho.setVisibility(View.VISIBLE);
        tvDesemprenho.setText("0%");
        //tvDesemprenho.setText(percentualProgress==100.0 ? (String.format("%.0f", percentualTotal)+"%") : (String.format("%.1f", percentualProgress)+"%"));

        //tvVendas.setText(new sonicUtils(mActivity).Number.stringToMoeda2(vendido));
        //tvMeta.setVisibility(View.VISIBLE);
        //tvMeta.setText(new sonicUtils(mActivity).Number.stringToMoeda2(usuarioMeta));

    }

    public void createDrawerMenu(){

        listaEmpresa = mData.Empresa.empresaUsuario();
        listaUser = mData.Usuario.selectUsuarioAtivo();

        mHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withAlternativeProfileHeaderSwitching(true)
                .withDividerBelowHeader(true)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.backhome)
                .withTextColor(getResources().getColor(R.color.colorPrimaryWhite))
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {

                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {

                        if(!currentProfile) {

                            lerDadosUsuario();
                            setTvEmpresa(profile.getIdentifier());
                            new myAssyncTask().execute(3);
                            new myAssyncTask().execute(4);
                            mPrefs.Users.setEmpresaNome(profile.getEmail().toString());
                            mPrefs.Users.setEmpresaId((int)profile.getIdentifier());
                            mData.Empresa.setSelecionada((int)profile.getIdentifier());
                            usuarioMeta = listaUser.get(0).getMetaVenda();
                            tvEmpresa.setText(profile.getEmail().toString());
                            //tvMeta.setText(new sonicUtils(getBaseContext()).Number.stringToMoeda2(usuarioMeta));
                            File file = new File(Environment.getExternalStorageDirectory(), sonicConstants.LOCAL_IMG_USUARIO + mPrefs.Users.getPicture((int)profile.getIdentifier()));
                            String picture = file.exists() ? file.toString() : sonicUtils.getURIForResource(R.drawable.no_profile);
                            sonicGlide.glideImageView(mActivity, mProgressProfile, picture, 100,100);
                            calcularPercentual("2200000", usuarioMeta);
                            lerDadosUsuario();
                            refreshFragments();

                        }

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
                            i.setDataAndType(Uri.parse(picture.getPath()), "image/*");

                            startActivityForResult(i, IMAGE_GALLERY_REQUEST);
                        }

                        return false;
                    }
                })
                .build();

        for (int x = 0; x < listaEmpresa.size() && x < 3 ; x++)

        {

            File file = new File(Environment.getExternalStorageDirectory(),sonicConstants.LOCAL_IMG_USUARIO + mPrefs.Users.getPicture(listaEmpresa.get(x).getCodigo()));

            if(file.exists()){
                mHeader.addProfiles(
                        new ProfileDrawerItem()
                                 //.withTextColor(getResources().getColor(R.color.colorPrimaryWhite))
                                .withName(mPrefs.Users.getUsuarioNome() +" ("+ mPrefs.Users.getUsuarioCargo() +")")
                                .withEmail(listaEmpresa.get(x).getNomeFantasia())
                                .withIcon(sonicUtils.centerAndCropBitmap(BitmapFactory.decodeFile(file.toString())))
                                .withIdentifier(listaEmpresa.get(x).getCodigo())
                );
            }else{

                mHeader.addProfiles(
                        new ProfileDrawerItem()
                                 //.withTextColor(getResources().getColor(R.color.colorPrimaryWhite))
                                .withName(mPrefs.Users.getUsuarioNome() +" ("+ mPrefs.Users.getUsuarioCargo() +")")
                                .withEmail(listaEmpresa.get(x).getNomeFantasia())
                                .withIcon(getResources().getDrawable(R.drawable.no_profile))
                                .withIdentifier(listaEmpresa.get(x).getCodigo())
                );

            }

        }
        mDrawerSincronizar = new PrimaryDrawerItem()
                .withIdentifier(1)
                .withName(R.string.sincronizarTitulo)
                .withDescription(R.string.sincronizarSubTitulo)
                .withSelectable(false)
                .withIcon(getResources().getDrawable(R.mipmap.ic_sync_grey600_24dp));

        mDrawerAvisos = new PrimaryDrawerItem()
                .withIdentifier(2)
                .withName(R.string.avisosTitulo)
                .withDescription(R.string.avisosSubTitulo)
                .withSelectable(false)
                .withBadge("0").withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700))//.withCornersDp(20).withPadding(2))
                //.withBadge("100")//.withBadgeStyle(new BadgeStyle().withColor(sonicMain.this.getResources().getColor( R.color.chart_red)).withCornersDp(15).withPadding(2))
                .withIcon(getResources().getDrawable(R.mipmap.ic_message_text_grey600_24dp));

        mDrawerClientes = new PrimaryDrawerItem()
                .withIdentifier(3)
                .withName(R.string.clientesTitulo)
                .withDescription(R.string.clientesSubTitulo)
                .withSelectable(false)
                .withBadge("0").withBadgeStyle(new BadgeStyle().withTextColor(getResources().getColor(R.color.colorPrimary)))
                .withIcon(getResources().getDrawable(R.mipmap.ic_account_multiple_grey600_24dp));

        mDrawerProdutos = new PrimaryDrawerItem()
                .withIdentifier(4)
                .withName(R.string.produtosTitulo)
                .withDescription(R.string.produtosSubTitulo)
                .withSelectable(false)
                .withBadge("0").withBadgeStyle(new BadgeStyle().withTextColor(getResources().getColor(R.color.colorPrimary)))
                .withIcon(getResources().getDrawable(R.mipmap.ic_cube_grey600_24dp));

        mDrawerPedidos = new PrimaryDrawerItem()
                .withIdentifier(5)
                .withName(R.string.pedidosTitulo)
                .withDescription(R.string.pedidosSubTitulo)
                .withSelectable(false)
                .withBadge("0").withBadgeStyle(new BadgeStyle().withTextColor(getResources().getColor(R.color.colorPrimary)))
                .withIcon(getResources().getDrawable(R.mipmap.ic_cart_grey600_24dp));

        mDrawerRetorno = new PrimaryDrawerItem()
                .withIdentifier(6)
                .withName(R.string.retornoTitulo)
                .withDescription(R.string.relatoriosSubTitulo)
                .withSelectable(false)
                .withIcon(getResources().getDrawable(R.mipmap.ic_rotate_3d_grey600_24dp));

        mDrawerTitulos = new PrimaryDrawerItem()
                .withIdentifier(7)
                .withName(R.string.titulosTitulo)
                .withDescription(R.string.titulosSubTitulo)
                .withSelectable(false)
                .withIcon(getResources().getDrawable( R.mipmap.ic_coin_grey600_24dp));

        mDrawerRota = new PrimaryDrawerItem()
                .withIdentifier(8)
                .withName(R.string.rotaTitulo)
                .withDescription(R.string.rotaSubTitulo)
                .withSelectable(false)
                .withIcon(getResources().getDrawable(R.mipmap.ic_map_marker_radius_grey600_24dp));

        mDrawerVendedores = new PrimaryDrawerItem()
                .withIdentifier(9)
                .withName(R.string.vendedoresTitulo)
                .withDescription(R.string.vendedoresSubTitulo)
                .withSelectable(false)
                .withIcon(getResources().getDrawable(R.mipmap.ic_account_tie_grey600_24dp));

        mDrawerRelatorios = new ExpandableDrawerItem()
                .withIdentifier(10)
                .withName(R.string.relatoriosTitulo)
                //.withDescription(R.string.relatoriosSubTitulo)
                .withIcon(getResources().getDrawable(R.mipmap.ic_finance_grey600_24dp))
                .withIsExpanded(false)
                .withSelectable(false)
                .withSubItems(
                        myDrawerRankingClientes =  new SecondaryDrawerItem()
                                .withIdentifier(11)
                                .withSelectable(false)
                                .withName(R.string.rankingClientes).withLevel(2)
                                .withIcon(R.mipmap.ic_chart_line_variant_grey600_24dp),
                        myDrawerRankingProdutos = new SecondaryDrawerItem()
                                .withIdentifier(12)
                                .withSelectable(false)
                                .withName(R.string.rankingProdutos).withLevel(2)
                                .withIcon(R.mipmap.ic_chart_line_variant_grey600_24dp),
                        myDrawerClientesSemCompraa = new SecondaryDrawerItem()
                                .withIdentifier(13)
                                .withSelectable(false)
                                .withName(R.string.clientesSemCompra).withLevel(2)
                                .withIcon(R.mipmap.ic_chart_line_variant_grey600_24dp),
                        myDrawerPremiacoes = new SecondaryDrawerItem()
                                .withIdentifier(14)
                                .withSelectable(false)
                                .withName(R.string.premiacoes).withLevel(2)
                                .withIcon(R.mipmap.ic_chart_line_variant_grey600_24dp)
                        /*.withIcon(getResources().getDrawable(R.mipmap.ic_settings_black_24dp))*/
                );
        DrawerBuilder drawerBuilder = new DrawerBuilder();

        mDrawerSistema = new ExpandableDrawerItem()
                .withIdentifier(20)
                .withName(R.string.sistemaTitulo)
                //.withDescription(R.string.sistemaSubTitulo)
                .withIcon(getResources().getDrawable(R.mipmap.ic_cogs))
                .withIsExpanded(false)
                .withSelectable(false)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        //mDrawer.getRecyclerView().smoothScrollToPosition(mDrawer.getRecyclerView().getAdapter().getItemCount()-1);
                        return false;
                    }
                })
                .withSubItems(
                        myDrawerRedefinir = new SecondaryDrawerItem()
                                .withIdentifier(21)
                                .withName("Logs").withLevel(2)
                                .withSelectable(false)
                                .withIcon(R.mipmap.ic_wrench_grey600_24dp),
                        myDrawerRedefinir = new SecondaryDrawerItem()
                                .withIdentifier(22)
                                .withName(R.string.redefinirTitulo).withLevel(2)
                                .withSelectable(false)
                                .withIcon(R.mipmap.ic_wrench_grey600_24dp),
                        myDrawerExportar = new SecondaryDrawerItem()
                                .withIdentifier(23)
                                .withName("Exportar").withLevel(2)
                                .withSelectable(false)
                                .withIcon(R.mipmap.ic_database_export_grey600_24dp)

                                );

        mDrawerItemSair = new PrimaryDrawerItem()
                .withIdentifier(30)
                .withName("Sair")
                .withSelectable(false)
                .withIcon(getResources().getDrawable(R.mipmap.ic_logout_grey600_24dp));

        /*myDrawerLock = new SwitchDrawerItem()
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
        });*/


        //drawerBuilder.withDrawerGravity(Gravity.BOTTOM);
        //drawerBuilder.withKeepStickyItemsVisible(true);
        drawerBuilder.withShowDrawerOnFirstLaunch(false);
        drawerBuilder.withActivity(this);
        drawerBuilder.withToolbar(myToolbar);
        drawerBuilder.withAccountHeader(mHeader);
        drawerBuilder.withSelectedItem(-1);
        drawerBuilder.withHeaderDivider(true);
        drawerBuilder.withCloseOnClick(false);
        drawerBuilder.withShowDrawerUntilDraggedOpened(false);
        //drawerBuilder.withShowDrawerUntilDraggedOpened(false);
        drawerBuilder.withOnDrawerListener(new Drawer.OnDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {

                if (mPrefs.Sincronizacao.getDrawerRefresh()) {
                    mPrefs.Sincronizacao.setDrawerRefresh(false);
                    // CLIENTES
                    new myAssyncTask().execute(3);
                    // PRODUTOS
                    new myAssyncTask().execute(4);
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }



        });
        drawerBuilder.addStickyDrawerItems(
                mDrawerItemSair
        );
        drawerBuilder.addDrawerItems(
                mDrawerSincronizar,
                mDrawerAvisos,
                new SectionDrawerItem().withName(R.string.inicioTitulo),
                mDrawerClientes,
                mDrawerProdutos,
                mDrawerPedidos,
                mDrawerRetorno,
                mDrawerTitulos,
                mDrawerRota,
                mDrawerVendedores,
                new SectionDrawerItem().withName("Extras"),
                mDrawerRelatorios,
                //new SectionDrawerItem().withName("Segurança"),
                myDrawerLock,
                //new SectionDrawerItem(),
                mDrawerSistema
                //myDrawerRedefinir,
                //myDrawerExportar
        );
        drawerBuilder.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                switch (view.getId()) {
                    case 0:
                        break;
                    case 1:
                        i = new Intent(sonicMain.this, sonicSincronizacao.class);
                        startActivityForResult(i, 1);
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
                        mPrefs.Util.deleteCache();
                        mPrefs.Util.clearPreferences();
                        break;
                    case 21:
                        new myAsyncStartActivity().execute(sonicSistema.class);
                        //i = new Intent(sonicMain.this, sonicSistema.class);
                        //startActivity(i);
                        //border_bottom();
                        break;
                    case 22:
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
                    case 23:
                        dialogBackup();
                        break;
                    case 30:
                        logout();
                        break;
                    default:
                        break;
                }
                return false;
            }

        });
        mDrawer = drawerBuilder.build();

    }

    private void logout(){
        ProgressDialog mProgress = new ProgressDialog(sonicMain.this);
        mProgress.setCancelable(false);
        mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgress.setIndeterminate(true);
        mProgress.setMessage("Saindo...");
        mProgress.show();
        mPrefs.Users.setLogado(false);
        mData.Usuario.setAtivo(mPrefs.Users.getPrimeiroAcessoID());
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgress.dismiss();
                startActivity(new Intent(sonicMain.this, MainActivity.class));
                sonicMain.this.finish();
            }
        },1000);
    }

    private void exibirListaUsuarios() {

        List<sonicUsuariosHolder> users;

        users = new sonicDatabaseCRUD(sonicMain.this).Usuario.listaUsuarios();

        List<String> l = new ArrayList<>();
        List<Integer> cod = new ArrayList<>();

        for(int i=0; i < users.size(); i++ ){
            l.add(users.get(i).getCodigo()+" - "+users.get(i).getNome());
            cod.add(users.get(i).getCodigo());
        }

        final CharSequence[] chars = l.toArray(new CharSequence[l.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(sonicMain.this);
        builder.setItems(chars, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                mData.Usuario.setAtivo(cod.get(item));
                mData.Empresa.selecionarPrimeiraEmpresa();
                mData.Database.truncateAllTablesNonNecessary();
                mPrefs.Users.setAtivo(true);
                startActivity(new Intent(sonicMain.this, MainActivity.class));
                sonicMain.this.finish();
            }
        }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();

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
            listaEmpresa = mData.Empresa.empresaUsuario();
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
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mAdapter.addFragment(new sonicMainVendas(), "Vendas");
        mAdapter.addFragment(new sonicMainPedidos(), "Pedidos");
        //myAdapter.addFragment(new sonicMainDesempenhoDiario(), "Desempenho");
        //myAdapter.addFragment(new sonicMainVendas(), "Visitas");
        viewpager.addOnPageChangeListener(listener);
        viewpager.setAdapter(mAdapter);

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
                mTabLayout.getTabAt(position).setIcon(mIcon.get(position));
                if(mTabLayout.getTabAt(position).isSelected()){
                    mTabLayout.getTabAt(position).getIcon().setColorFilter(getResources().getColor(R.color.iconTabSelected), PorterDuff.Mode.SRC_ATOP);
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

    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            switch (position){
                case 0:
                    for(int i=0;i<llGroupVendas.getChildCount();i++){
                        View view = llGroupVendas.getChildAt(i);
                        if(view instanceof TextView){
                            //((TextView)view).setTextColor(Color.RED);
                        }
                    }
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void updateBadge( int id, String badge){

        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                mDrawer.updateBadge(id, new StringHolder(badge));

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
                    //result[1] = mData.Aviso.countNaoLido();
                    break;
                case 3:
                    result[0] = integers[0];
                    result[1] = mData.Cliente.countPorEmpresa();
                    //updateBadge(3, result[1]+"");
                    break;
                case 4:
                    result[0] = integers[0];
                    result[1] = mData.Produto.countPorEmpresa();
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

        mCalendar = Calendar.getInstance();
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(mActivity);
        builder.setMessage("O backup será salvo em "+ sonicConstants.LOCAL_DATA_BACKUP+"Sonic-bkp-"+(new SimpleDateFormat("dd-MM-yyy-HHmm").format(mCalendar.getTime())))
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

        boolean result = new sonicDatabase(mActivity).exportDatabase();

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    myProgressDialog.dismiss();
                                    if(result){
                                        Snackbar snackbar = Snackbar.make(llSnackBar, "BACUKP SALVO", Snackbar.LENGTH_LONG);
                                        SnackbarHelper.configSnackbar(sonicMain.this, snackbar, SnackbarHelper.SNACKBAR_SUCCESS);
                                        llSnackBar.addView(snackbar.getView());
                                        snackbar.show();
                                    }else{
                                        Snackbar snackbar = Snackbar.make(llSnackBar, "ERRO", Snackbar.LENGTH_INDEFINITE)
                                                .setAction("DETALHE", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        new android.app.AlertDialog.Builder(sonicMain.this)
                                                                .setMessage(mPrefs.Geral.getError())
                                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                        dialog.dismiss();
                                                                        llSnackBar.removeAllViews();
                                                                    }
                                                                }).show();
                                                    }
                                                });
                                        SnackbarHelper.configSnackbar(sonicMain.this, snackbar, SnackbarHelper.SNACKBAR_WARNING);
                                        llSnackBar.addView(snackbar.getView());
                                        snackbar.show();
                                    }

                                }
                            }
                , 1000);

    }

    public void mensagemErro(){

        llSnackBar.removeAllViews();
        mSnackbar = Snackbar
                .make(llSnackBar, "ERRO", Snackbar.LENGTH_INDEFINITE)
                .setAction("DETALHE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new android.app.AlertDialog.Builder(mActivity)
                                //.setTitle("Atenção!\n\n")
                                .setMessage(mPrefs.Geral.getError())
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
                });
        SnackbarHelper.configSnackbar(mActivity, mSnackbar, SnackbarHelper.SNACKBAR_WARNING);
        llSnackBar.addView(mSnackbar.getView());
        mSnackbar.show();

    }

    public void mensagemOK(String msg, boolean refresh){

        llSnackBar.removeAllViews();
        mSnackbar = Snackbar
                .make(llSnackBar, msg, Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(refresh)
                            refreshFragments();
                    }
                });
        SnackbarHelper.configSnackbar(mActivity, mSnackbar, SnackbarHelper.SNACKBAR_SUCCESS);
        llSnackBar.addView(mSnackbar.getView());
        mSnackbar.show();

    }

    public void setTvEmpresa(long tvEmpresa){

        //allowHomeUpdate = true;

        if(tvEmpresa ==0){
            mData.Empresa.marcarEmpresa();
        }else{
            mData.Empresa.selecionarPrimeiraEmpresa();
            mData.Empresa.setSelecionada((int) tvEmpresa);
        }


        refreshFragments();

    }

    private void addBottomDots(int position){

        dots = new TextView[mAdapter.getCount()];
        dotsLayout.removeAllViews();
        if(mAdapter.getCount()>1){
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
                //dots[position].setTextSize(40);
            }
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
                i = new Intent(getBaseContext(), sonicConfig.class);
                startActivityForResult(i, 2);
                break;
            case R.id.itNotificacao:
                Toast.makeText(getApplicationContext(), "Notificação", Toast.LENGTH_SHORT).show();
                return false;
            case R.id.itSincronizar:
                mPrefs.Sincronizacao.setCalledActivity(this.getClass().getSimpleName());
                mPrefs.Sincronizacao.setDownloadType("DADOS");
                mPrefs.Sincronizacao.setDrawerRefresh(true);
                mFtp = new sonicFtp(mActivity);
                mFtp.downloadFile2(sonicConstants.FTP_USUARIOS + mPrefs.Users.getArquivoSinc() , sonicConstants.LOCAL_TEMP+mPrefs.Users.getArquivoSinc());
                return false;

        }

        return false;
    }

    public void dialogRedefinir(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.redefinir).setPositiveButton("REDEFINIR", dialogClickListener).setNegativeButton("CANCELAR", dialogClickListener).show();
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    sonicDatabaseCRUD DBC = new sonicDatabaseCRUD(mActivity);
                    DBC.Database.truncateAllTables();
                    new sonicStorage(sonicMain.this).deleteFiles(sonicConstants.LOCAL_TEMP);
                    mPrefs.Util.clearPreferences();
                    mPrefs.Util.deleteCache();
                    Intent mStartActivity = new Intent(sonicMain.this, MainActivity.class);
                    int mPendingIntentId = 987654321;
                    PendingIntent mPendingIntent = PendingIntent.getActivity(sonicMain.this, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager mgr = (AlarmManager) mActivity.getSystemService(Context.ALARM_SERVICE);
                    mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                    System.exit(0);
                    finishAffinity();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(mPrefs.Sincronizacao.getHomeRefresh()){
            refreshFragments();
            mPrefs.Sincronizacao.setHomeRefresh(false);
        }

        if (resultCode != RESULT_CANCELED && data != null) {

            if (requestCode == IMAGE_GALLERY_REQUEST) {

                Uri imageUri = data.getData();

                mUtil = new sonicUtils(mActivity);

                mUtil.Arquivo.saveUriFile(imageUri, mPrefs.Path.getProfilePath(), mPrefs.Users.getEmpresaId(), mPrefs.Users.getUsuarioId());

                String url = Environment.getExternalStorageDirectory().getPath() + mPrefs.Path.getProfilePath() + mPrefs.Users.getEmpresaId() + "_" + mPrefs.Users.getUsuarioId() + ".JPG";

                mHeader.getActiveProfile().withIcon(sonicUtils.centerAndCropBitmap(BitmapFactory.decodeFile(url)));

                mHeader.updateProfile(mHeader.getActiveProfile());

                Glide.with(getBaseContext())
                        .load(url)
                        .placeholder(R.drawable.no_profile)
                        .apply(new RequestOptions().override(100,100))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                        .into(mProgressProfile);


            }

        }
    }

    public class SpeedyLinearLayoutManager extends LinearLayoutManager {

        private static final float MILLISECONDS_PER_INCH = 100f; //default is 25f (bigger = slower)

        public SpeedyLinearLayoutManager(Context context) {
            super(context);
        }

        public SpeedyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public SpeedyLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {

            final LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {

                @Override
                public PointF computeScrollVectorForPosition(int targetPosition) {
                    return super.computeScrollVectorForPosition(targetPosition);
                }

                @Override
                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
                }
            };

            linearSmoothScroller.setTargetPosition(position);
            startSmoothScroll(linearSmoothScroller);
        }
    }

    public void refreshFragments(){
        for(int i = 0; i< mAdapter.getCount(); i++){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.detach(mAdapter.getItem(i)).attach(mAdapter.getItem(i)).commit();
        }
    }

    public void refreshHomeFragments(int position){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.detach(mAdapter.getItem(position)).attach(mAdapter.getItem(position)).commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

}


