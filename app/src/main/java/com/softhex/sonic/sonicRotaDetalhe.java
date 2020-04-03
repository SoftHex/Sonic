package com.softhex.sonic;

import android.Manifest;
import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class sonicRotaDetalhe extends AppCompatActivity {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({Status.NAO_INICIADO, Status.EM_ATENDIMENTO, Status.CONCLUIDO})
    public @interface Status{
        int NAO_INICIADO = 1;
        int EM_ATENDIMENTO = 2;
        int CONCLUIDO = 3;
    }

    private static final int REQUEST_PERMISSION = 10;
    private Context mContex;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewpager;
    private TextView[] dots;
    private TextView tvCount;
    private LinearLayout dotsLayout;
    private sonicDatabaseCRUD mData;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private String[] myImages = new String[sonicConstants.TOTAL_IMAGES_SLIDE];
    private LinearLayout linearNew;
    private sonicPreferences mPref;
    private TextView tvLogradrouro;
    private TextView tvEndCompleto;
    private TextView tvCep;
    private TextView tvDataInicio;
    private TextView tvHoraInicio;
    private TextView tvHoraFim;
    private TextView tvDuracao;
    private Button btNavegar;
    private Button btIniciar;
    private Button btCancelar;
    private FloatingActionButton fbNavegar;
    private FusedLocationProviderClient mFusedLocationClient;
    private long startTime, timeInMilliseconds = 0;
    private Handler customHandler = new Handler();
    private boolean timeStart = false;
    private ProgressDialog myProgress;
    private Calendar mCalendar;
    private SimpleDateFormat data = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");
    private sonicUtils mUtils;
    private Activity mActivity;
    private List<sonicRotaHolder> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_rota_detalhe);

        mPref = new sonicPreferences(this);
        mData = new sonicDatabaseCRUD(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mContex = this;
        mActivity = getParent();
        mUtils = new sonicUtils(mContex);
        mViewpager = findViewById(R.id.pagerSlide);
        mCollapsingToolbar = findViewById(R.id.mCollapsingToolbar);
        dotsLayout = findViewById(R.id.layoutDots);
        tvCount = findViewById(R.id.tvCount);
        fbNavegar = findViewById(R.id.fbNavegar);
        tvLogradrouro = findViewById(R.id.tvLogradouro);
        tvEndCompleto = findViewById(R.id.tvEnderecoCompleto);
        tvDataInicio = findViewById(R.id.tvDataInicio);
        tvHoraInicio = findViewById(R.id.tvHoraInicio);
        tvHoraFim = findViewById(R.id.tvHoraFim);
        tvCep = findViewById(R.id.tvCep);
        tvDuracao = findViewById(R.id.tvDuracao);
        btIniciar = findViewById(R.id.btIniciar);
        btCancelar = findViewById(R.id.btCancelar);

        createInterface();
        slideImages();
        loadDetails();
        loadActions();

    }

    private void createInterface() {

        mToolbar = findViewById(R.id.mToolbar);
        setSupportActionBar(mToolbar);
        ActionBar myActionBar = getSupportActionBar();
        myActionBar.setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setElevation(0);
        myActionBar.setDisplayHomeAsUpEnabled(true);
        myActionBar.setDisplayShowHomeEnabled(true);

        LayoutTransition transition = new LayoutTransition();
        transition.setDuration(100);

        AppBarLayout mAppBar = findViewById(R.id.appBar);
        mAppBar.setLayoutTransition(transition);

        mCollapsingToolbar.setTitle("");

        mToolbar.setNavigationOnClickListener((View v)-> {
            onBackPressed();
        });

        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if((mCollapsingToolbar.getHeight()+verticalOffset)<(2 * ViewCompat.getMinimumHeight(mCollapsingToolbar))){
                    mToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryWhite), PorterDuff.Mode.SRC_ATOP);
                    dotsLayout.setVisibility(View.INVISIBLE);
                    //fbNavegar.setVisibility(View.INVISIBLE);
                    mCollapsingToolbar.setTitle(mPref.Clientes.getNome());
                }else {
                    mToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryBlack), PorterDuff.Mode.SRC_ATOP);
                    dotsLayout.setVisibility(View.VISIBLE);
                    //fbNavegar.setVisibility(View.VISIBLE);
                    mCollapsingToolbar.setTitle("");
                }
            }
        });

    }

    public void loadDetails(){
        tvLogradrouro.setText(mPref.Clientes.getLogradouro());
        tvEndCompleto.setText(mPref.Clientes.getBairro()+", "+mPref.Clientes.getMunicipio()+" - "+mPref.Clientes.getUf());
        tvCep.setText("CEP: "+mPref.Clientes.getCep());
        tvDataInicio.setText(mPref.Rota.getStartDate());
        tvHoraInicio.setText(mPref.Rota.getStartHora());
        tvHoraFim.setText(mPref.Rota.getEndHora());
        tvDuracao.setText(mPref.Rota.getDuracao());
        if(mPref.Rota.getEmAtendimento()){
            btIniciar.setText("FINALIZAR ATENDIMENTO");
            btIniciar.setBackground(getResources().getDrawable(R.drawable.status_em_atendimento));
            btCancelar.setEnabled(false);
            btCancelar.setBackground(getResources().getDrawable(R.drawable.status_nao_iniciado));
            startCounter();
        }else if(mPref.Rota.getFinalizada()) {
            btIniciar.setText("CONCLUÍDO");
            btIniciar.setBackground(getResources().getDrawable(R.drawable.status_concluido));
            btCancelar.setEnabled(false);
            btCancelar.setBackground(getResources().getDrawable(R.drawable.status_nao_iniciado));
        }else{
            btIniciar.setText("INICIAR ATENDIMENTO");
            btIniciar.setBackground(getResources().getDrawable(R.drawable.status_nao_iniciado));
            btCancelar.setBackground(getResources().getDrawable(R.drawable.status_cancelado));
        }

    }

    public void loadActions(){
        fbNavegar.setOnClickListener((View v)->{
            startNavigation();
        });
        btIniciar.setOnClickListener((View v)->{

            if(mPref.Rota.getEmAtendimento()){

            }

            if(!timeStart){
                new mAsyncTask().execute(Status.EM_ATENDIMENTO);
                mPref.Rota.setEmAtendimento(true);
            }else{
                myProgress = new ProgressDialog(mContex);
                myProgress.setCancelable(false);
                myProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                myProgress.setIndeterminate(true);
                myProgress.setTitle("Iniciando...");
                myProgress.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mPref.Rota.setRefresh(true);
                                            new mAsyncTask().execute(Status.CONCLUIDO);
                                        }
                                    }
                        , 0);
            }

        });
        btCancelar.setOnClickListener((View v)->{

        });
    }

    class mAsyncTask extends AsyncTask<Integer, Void, Boolean>{


        @Override
        protected Boolean doInBackground(Integer... integers) {
            mCalendar = Calendar.getInstance();
            return (mData.Rota.updateRota(String.valueOf(mPref.Rota.getCodigo()),"status",integers[0])
                    && mData.Rota.updateRota(String.valueOf(mPref.Rota.getCodigo()), !timeStart ? "data_inicio" : "data_fim" ,data.format(mCalendar.getTime()))
                    && mData.Rota.updateRota(String.valueOf(mPref.Rota.getCodigo()),!timeStart ? "hora_inicio" : "hora_fim",hora.format(mCalendar.getTime())));
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(aBoolean){
                if(!timeStart){
                    myProgress.dismiss();
                    startCounter();
                    timeStart = true;
                }else{
                    stopCounter();
                }
            }else{
                Toast.makeText(mContex, "Não foi possivel iniciar o atendimento...", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void startCounter(){
        customHandler.postDelayed(updateTimerThread, 0);
        btIniciar.setText("FINALIZAR ATENDIMENTO");
        btIniciar.setBackground(getResources().getDrawable(R.drawable.status_em_atendimento));
        btCancelar.setEnabled(false);
        btCancelar.setBackground(getResources().getDrawable(R.drawable.status_nao_iniciado));
        mCalendar = Calendar.getInstance();
        tvDataInicio.setText(mUtils.Data.dataFotmatadaBR(data.format(mCalendar.getTime())));
        mPref.Rota.setStartDate(mUtils.Data.dataFotmatadaBR(data.format(mCalendar.getTime())));
        tvHoraInicio.setText(hora.format(mCalendar.getTime()));
        mPref.Rota.setStartHora(hora.format(mCalendar.getTime()));
        startTime = SystemClock.uptimeMillis();

    }
    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            tvDuracao.setText(getDateFromMillis(timeInMilliseconds));
            customHandler.postDelayed(this, 1000);
        }
    };

    public static String getDateFromMillis(long d) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(d);
    }

    public void stopCounter() {
        mCalendar = Calendar.getInstance();
        tvHoraFim.setText(hora.format(mCalendar.getTime()));
        mPref.Rota.setEndDate(mUtils.Data.dataFotmatadaBR(data.format(mCalendar.getTime())));
        mPref.Rota.setEndHora(hora.format(mCalendar.getTime()));
        btIniciar.setText("CONCLUIDO");
        btIniciar.setEnabled(false);
        btIniciar.setBackground(getResources().getDrawable(R.drawable.status_concluido));
        customHandler.removeCallbacks(updateTimerThread);

    }

    public void slideImages(){

        int count = 0;
        File file;
        String image = "";

        for(int i = 0; i < myImages.length ; i++){

            image = mPref.Clientes.getId()+(i==0? "" : "_"+i)+".JPG";
            file = new File(Environment.getExternalStorageDirectory(), sonicConstants.LOCAL_IMG_CLIENTES+image);

            if(file.exists()){
                count++;
            }
        }

        myImages = new String[count];

        for(int i = 0; i < count ; i++){

            image = mPref.Clientes.getId()+(i==0? "" : "_"+i)+".JPG";
            file = new File(Environment.getExternalStorageDirectory(), sonicConstants.LOCAL_IMG_CLIENTES+image);

            myImages[i] = file.toString();

        }


        if(count==0){
            myImages = new String[1];
            myImages[0] = sonicUtils.getURIForResource(R.drawable.nophoto);
        }

        linearNew = findViewById(R.id.linearNew);
        //linearNew.setVisibility(clienteStatus.equals("NOVO") ? View.VISIBLE : View.INVISIBLE);

        sonicSlideImageAdapter myAdapter = new sonicSlideImageAdapter(this, myImages, count==0 ? false : true);
        mViewpager.setAdapter(myAdapter);
        mViewpager.addOnPageChangeListener(viewListener);
        //addBottomDots(0);
        addCount(1);

    }

    private void addCount(int position){
        if(myImages.length>1){
            tvCount.setVisibility(View.VISIBLE);
            tvCount.setText(position+"/"+myImages.length);
        }
    }

    public boolean checkPermissions(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        return false;
    }
    public void requestPermissions(){
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSION
        );
    }

    private boolean isLocationEnabled(){
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }
    @SuppressLint("MissingPermission")
    public void startNavigation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    String uri = "geo:"+location.getLongitude()+","+location.getLongitude()+"?q="+mPref.Rota.getAddressMap();
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Localização desligada...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }

    }
    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }
    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
        }
    };

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addCount(i+1);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

}
