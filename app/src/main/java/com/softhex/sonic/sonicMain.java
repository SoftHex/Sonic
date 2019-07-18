package com.softhex.sonic;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;
import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionViewTarget;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import co.mobiwise.materialintro.MaterialIntroConfiguration;
import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.view.MaterialIntroView;
import eu.long1.spacetablayout.SpaceTabLayout;


public class sonicMain extends AppCompatActivity{

    public static final int IMAGE_GALLERY_REQUEST = 20;
    public static final int CAMERA_REQUEST_CODE = 228;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 4192;
    private ImageView imgPicture;
    private sonicDatabaseCRUD DBC;
    private sonicDatabaseLogCRUD DBCL;
    private Intent i;
    private Toolbar myToolbar;
    private ActionBar myActionBar;
    private ViewPager myViewPager;
    private ViewPagerAdapter myAdapter;
    private sonicConstants myCons;
    private BottomNavigationView myBottonNav;
    private SpaceTabLayout mySpaceTabLayout;
    private SharedPreferences pref;
    private AccountHeader myHeader;
    private Context myCtx;
    private sonicUtils myUtil;
    private sonicThrowMessage myMessage;
    private Drawer myDrawer;
    private boolean check;
    private String usuarioNome;
    private String usuarioCargo;
    private int usuarioId;
    private int usuarioNivelAcesso;
    private boolean empSelecioanada;
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
    private SecondaryDrawerItem myDrawerRankingClientes;
    private SecondaryDrawerItem myDrawerRankingProdutos;
    private SecondaryDrawerItem myDrawerClientesSemCompraa;
    private SecondaryDrawerItem myDrawerPremiacoes;
    List<sonicEmpresasHolder> listaEmpresa;
    MaterialIntroConfiguration config = new MaterialIntroConfiguration();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_main);

        myCtx = this;
        DBC = new sonicDatabaseCRUD(myCtx);
        DBCL = new sonicDatabaseLogCRUD(myCtx);

        myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        myActionBar = getSupportActionBar();
        myActionBar.setTitle(R.string.app_name);

        List<sonicUsuariosHolder> listaUser;
        listaUser = DBC.Usuarios.selectUsuarioAtivo();
        usuarioId = listaUser.get(0).getCodigo();
        usuarioNome = listaUser.get(0).getNome();
        usuarioCargo = listaUser.get(0).getCargo();
        usuarioNivelAcesso = listaUser.get(0).getNivelAcessoId();


        // ATUALIZA A BADGE DE AVISOS
        new myAssyncTask().execute(2);
        // ATUALIZA A BADGE DE CLIENTES
        new myAssyncTask().execute(3);
        // ATUALIZA A BADGE DE PRODUTOS
        new myAssyncTask().execute(4);

        //mySpaceTabLayout = (SpaceTabLayout)findViewById(R.id.spaceTabLayout);

        myViewPager = findViewById(R.id.pager);
        setUpViewPager(myViewPager);

        List<Fragment> fragmentList = new ArrayList<>();
        //fragmentList.add(new sonicMainHome());
        //fragmentList.add(new sonicAvisosFragNaoLidos());
        //fragmentList.add(new sonicAvisosFragNaoLidos());

        //mySpaceTabLayout.initialize(myViewPager,getSupportFragmentManager(),fragmentList,savedInstanceState);

        //mySpaceTabLayout.setTabOneOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View view) {

                //Intent i = new Intent(sonicMain.this, sonicShimmer.class);
                //startActivity(i);

            //}
        //});

        myViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //myBottonNav.getMenu().getItem(position).setChecked(true);

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        createDrawerMenu();

    }

    public void createDrawerMenu(){

        listaEmpresa = DBC.Empresa.empresasUsuarios();

        myHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.company)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {

                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {

                        if(!currentProfile) {
                            setEmpresa(profile.getIdentifier());
                            new myAssyncTask().execute(3);
                            new myAssyncTask().execute(4);
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

            File file = new File(Environment.getExternalStorageDirectory(), myCons.LOCAL_IMG_PERFIL +listaEmpresa.get(i).getCodigo()+"_"+usuarioId+".jpg");

            //String image = file.exists() ? file.toString() : getResources().getDrawable(R.drawable.no_profile).toString();

            if(i<3){

                if(file.exists()){
                    myHeader.addProfiles(
                            new ProfileDrawerItem()
                                    .withName(usuarioNome +" ("+ usuarioCargo +")")
                                    .withEmail(listaEmpresa.get(i).getNomeFantasia())
                                    .withIcon(file.toString())
                                    .withIdentifier(listaEmpresa.get(i).getCodigo())
                    );
                }else{

                    myHeader.addProfiles(
                            new ProfileDrawerItem()
                                    .withName(usuarioNome +" ("+ usuarioCargo +")")
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
                .withBadge("").withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700).withCornersDp(20).withPadding(2))
                //.withBadge("100")//.withBadgeStyle(new BadgeStyle().withColor(sonicMain.this.getResources().getColor( R.color.chart_red)).withCornersDp(15).withPadding(2))
                .withIcon(getResources().getDrawable(R.mipmap.ic_message_text_grey600_24dp));

        myDrawerClientes = new PrimaryDrawerItem()
                .withIdentifier(3)
                .withName(R.string.clientesTitulo)
                .withDescription(R.string.clientesSubTitulo)
                .withSelectable(false)
                .withBadge("").withBadgeStyle(new BadgeStyle().withTextColor(getResources().getColor(R.color.colorPrimaryBlue)))
                .withIcon(getResources().getDrawable(R.mipmap.ic_account_multiple_grey600_24dp));

        myDrawerProdutos = new PrimaryDrawerItem()
                .withIdentifier(4)
                .withName(R.string.produtosTitulo)
                .withDescription(R.string.produtosSubTitulo)
                .withSelectable(false)
                .withBadge("").withBadgeStyle(new BadgeStyle().withTextColor(getResources().getColor(R.color.colorPrimaryBlue)))
                .withIcon(getResources().getDrawable(R.mipmap.ic_cube_grey600_24dp));

        myDrawerPedidos = new PrimaryDrawerItem()
                .withIdentifier(5)
                .withName(R.string.pedidosTitulo)
                .withDescription(R.string.pedidosSubTitulo)
                .withSelectable(false)
                .withBadge("").withBadgeStyle(new BadgeStyle().withPaddingTopBottomDp(10))
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
                .withIcon(getResources().getDrawable(R.mipmap.ic_power_grey600_24dp));

        myDrawerExportar = new PrimaryDrawerItem()
                .withIdentifier(18)
                .withName("Exportar")
                .withSelectable(false)
                .withIcon(getResources().getDrawable(R.mipmap.ic_database_export_grey600_24dp));

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
                                //i = new Intent(sonicMain.this, sonicSincronizacao.class);
                                //startActivity(i);
                                new myAsyncStartActivity().execute(sonicSincronizacao.class);
                                //i = new Intent(sonicMain.this, sonicSincronizacao.class);
                                //startActivity(i);
                                break;
                            case 2:
                                i = new Intent(sonicMain.this, sonicAvisos.class);
                                startActivity(i);
                                break;
                            case 3:
                                new myAsyncStartActivity().execute(sonicClientes.class);
                                break;
                            case 4:
                                i = new Intent(sonicMain.this, sonicClientes.class);
                                startActivity(i);
                                //i = new Intent(sonicMain.this, sonicProdutos.class);
                                //startActivity(i);
                                break;
                            case 5:
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
                            case 9:
                                //i = new Intent(sonicMain.this, sonicVendedores.class);
                                //startActivity(i);
                                //normalListDialogNoTitle("MENU");
                                break;
                            case 11:
                                new myAsyncStartActivity().execute(sonicSistema.class);
                                //i = new Intent(sonicMain.this, sonicSistema.class);
                                //startActivity(i);
                                //border_bottom();
                                break;
                            case 12:
                                mensagem();
                                //normalListDialogNoTitle("MENU");
                                break;
                            case 13:
                                SharedPreferences.Editor check = pref.edit();
                                check.putBoolean("KEEP_PASS", false);
                                check.apply();
                                finish();
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
                                exportDataBase();
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //mySpaceTabLayout.saveState(outState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //mySpaceTabLayout.saveState(savedInstanceState);
    }

    public void setUpViewPager(ViewPager viewpager){
        myAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        myAdapter.addFragment(new sonicMainHome(), "");
        //myAdapter.addFragment(new sonicAvisosFragNaoLidos(), "");
        //myAdapter.addFragment(new sonicMainHome(), "");
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

    public void updateBadge( int id, String badge){

        //myDrawer.updateBadge(id, new StringHolder(badge));

    }

    public class myAssyncTask extends AsyncTask<Integer, Void, Integer[]>{
        @Override
        protected Integer[] doInBackground(Integer... integers) {

            Integer[] result = new Integer[2];

            switch (integers[0]){
                case 2:
                    result[0] = integers[0];
                    result[1] = DBC.Avisos.countNaoLidos();
                    break;
                case 3:
                    result[0] = integers[0];
                    result[1] = (int)DBC.Clientes.count();
                    break;
                case 4:
                    result[0] = integers[0];
                    result[1] = DBC.Produto.countPorEmpresa();
                    break;
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer[] integers) {
            super.onPostExecute(integers);

            if(integers[1]>0){
                //updateBadge(integers[0],integers[1]+"");
            }else {
                //updateBadge(integers[0], null);
            }

        }
    }

    protected void exportDataBase() {
        OutputStream output=null;
        try {
            File dbFile = new File(Environment.getExternalStorageDirectory().getPath()+sonicConstants.LOCAL_DATA+"sonic.db");
            FileInputStream fis = new FileInputStream(dbFile);

            String outFileName = sonicConstants.LOCAL_DATA + "sonic.db";
            File bk = new File(outFileName);

            // Open the empty db as the output stream
            output = new FileOutputStream(outFileName);

            // Transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            // Close the streams
            fis.close();
            output.flush();
            output.close();


        } catch (IOException e) {
            e.printStackTrace();
            Log.e("dbBackup:", e.getMessage());
            //output.flush();
            //output.close();
        }
    }

    public void setEmpresa(long empresa){

        allowHomeUpdate = true;

        if(empresa==0){
            DBC.Empresa.marcarEmpresas();
        }else{
            DBC.Empresa.desmarcarEmpresas();
            DBC.Empresa.setSelecionado((int)empresa);
        }


        //refreshHomeFragment();

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

        int id = item.getItemId();

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                item.setEnabled(true);
                return false;
            }
        });

        if (id == R.id.action_settings) {
            i = new Intent(getBaseContext(), sonicMainConfiguracoes.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }


    public void mensagem(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setIcon(@)
        builder.setMessage(R.string.go_sure).setPositiveButton("REDEFINIR", dialogClickListener).setNegativeButton("CANCELAR", dialogClickListener).show();
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    sonicDatabaseCRUD DBC = new sonicDatabaseCRUD(getApplicationContext());
                    DBC.Database.truncateAllTables();
                    new sonicStorage(sonicMain.this).deleteFiles(sonicConstants.LOCAL_TMP);
                    clearPreferences();
                    deleteCache(getApplicationContext());
                    Intent mStartActivity = new Intent(sonicMain.this, MainActivity.class);
                    int mPendingIntentId = 123456;
                    PendingIntent mPendingIntent = PendingIntent.getActivity(sonicMain.this, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager mgr = (AlarmManager) sonicMain.this.getSystemService(Context.ALARM_SERVICE);
                    mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                    System.exit(0);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };


    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    private void clearPreferences() {

        getApplication().getSharedPreferences("GO_LOGIN", 0).edit().clear().apply();

        try {

            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear com.softhex.sonic");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void normalListDialogNoTitle(final String content) {
        final ArrayList<DialogMenuItem> testItems = new ArrayList<>();
//        testItems.add(new DialogMenuItem("??", R.mipmap.ic_winstyle_copy));
//        testItems.add(new DialogMenuItem("??", R.mipmap.ic_winstyle_delete));
        testItems.add(new DialogMenuItem("ITEM", R.mipmap.ic_add_shopping_cart_black_24dp));
        final NormalListDialog dialog = new NormalListDialog(sonicMain.this, testItems);
        dialog.title("TITULO")//
                .isTitleShow(true)//
                .itemPressColor(Color.parseColor("#85D3EF"))//
                .itemTextColor(Color.parseColor("#303030"))//
                .itemTextSize(15)//
                .cornerRadius(2)//
                .widthScale(0.75f)
                .show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://??
                        Toast.makeText(sonicMain.this,"ITEM", Toast.LENGTH_SHORT).show();
                        break;
                }
                dialog.dismiss();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode != RESULT_CANCELED && data != null) {

            if (requestCode == IMAGE_GALLERY_REQUEST) {

                Uri imageUri = data.getData();

                myUtil = new sonicUtils(myCtx);

                long empresaId = myHeader.getActiveProfile().getIdentifier();

                myUtil.Arquivo.saveUriFile(imageUri, sonicConstants.LOCAL_IMG_PERFIL, empresaId, usuarioId);

                String url = Environment.getExternalStorageDirectory().getPath()+sonicConstants.LOCAL_IMG_PERFIL+empresaId+"_"+usuarioId+".jpg";

                myHeader.getActiveProfile().withIcon(url);

                myHeader.updateProfile(myHeader.getActiveProfile());


            }

        }
    }


}


