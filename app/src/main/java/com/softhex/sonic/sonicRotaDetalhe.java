package com.softhex.sonic;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.Rect;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
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
import java.util.ArrayList;
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
    private TextView tvSituacao;
    private TextView tvNegativacao;
    private TextView tvObservacao;
    private LinearLayout llDetalhe;
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
        tvSituacao = findViewById(R.id.tvSituacao);
        tvNegativacao = findViewById(R.id.tvNegativacao);
        tvCep = findViewById(R.id.tvCep);
        tvDuracao = findViewById(R.id.tvDuracao);
        btIniciar = findViewById(R.id.btIniciar);
        btCancelar = findViewById(R.id.btCancelar);
        llDetalhe = findViewById(R.id.llDetalhe);
        tvNegativacao = findViewById(R.id.tvNegativacao);
        tvObservacao = findViewById(R.id.tvObservacao);

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
        mList = mData.Rota.selectRotaId(mPref.Rota.getCodigo());
        tvLogradrouro.setText(mPref.Clientes.getLogradouro());
        tvEndCompleto.setText(mPref.Clientes.getBairro()+", "+mPref.Clientes.getMunicipio()+" - "+mPref.Clientes.getUf());
        tvCep.setText("CEP: "+mPref.Clientes.getCep());
        tvDataInicio.setText(mList.get(0).getDataInicio());
        tvHoraInicio.setText(mList.get(0).getHoraInicio());
        tvHoraFim.setText(mList.get(0).getHoraFim());
        tvDuracao.setText(mPref.Rota.getDuracao());
        switch (mList.get(0).getStatus()){
            case 1:
                btIniciar.setText("INICIAR ATENDIMENTO");
                btIniciar.setBackground(getResources().getDrawable(R.drawable.status_nao_iniciado));
                break;
            case 2:
                btIniciar.setText("FINALIZAR ATENDIMENTO");
                btIniciar.setBackground(getResources().getDrawable(R.drawable.status_em_atendimento));
                btCancelar.setVisibility(View.GONE);
                break;
            case 3:
                btIniciar.setVisibility(View.GONE);
                btCancelar.setVisibility(View.GONE);
                llDetalhe.setVisibility(View.VISIBLE);
                switch (mList.get(0).getSituacao()){
                    case 1:
                        tvSituacao.setText("Positivado");
                        tvSituacao.setTextColor(getResources().getColor(R.color.colorPrimaryGreen));
                        tvNegativacao.setVisibility(View.GONE);
                        tvObservacao.setText(mList.get(0).getObservacao());
                        break;
                    case 2:
                        tvSituacao.setText("Negativado");
                        tvSituacao.setTextColor(getResources().getColor(R.color.colorPrimaryOrange));
                        tvNegativacao.setVisibility(View.VISIBLE);
                        tvNegativacao.setText(mList.get(0).getNegativacao());
                        tvObservacao.setText(mList.get(0).getObservacao());
                        break;
                }
                break;
            case 4:
                btIniciar.setVisibility(View.GONE);
                btCancelar.setVisibility(View.GONE);
                llDetalhe.setVisibility(View.VISIBLE);
                tvSituacao.setText("Cancelado");
                tvSituacao.setTextColor(getResources().getColor(R.color.colorPrimaryGreen));
                tvSituacao.setVisibility(View.GONE);
                tvNegativacao.setVisibility(View.GONE);
                break;
        }
    }

    public void loadActions(){

        Handler h = new Handler();
        myProgress = new ProgressDialog(mContex);
        myProgress.setCancelable(false);
        myProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        myProgress.setIndeterminate(true);
        myProgress.setTitle("Processando...");

        // NAVEGAR VIA GOOGLE GPS
        fbNavegar.setOnClickListener((View v)->{
            startNavigation();
        });

        // BOTÃO DE INICIAR/CONCLUIR ROTA
        btIniciar.setOnClickListener((View v)->{
            myProgress.show();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    myProgress.dismiss();
                    if(mPref.Rota.getEmAtendimento()){
                        finalizarAtendimento();
                    }else {
                        //btCancelar.setVisibility(View.GONE);
                        btCancelar.animate()
                                .translationX(btCancelar.getWidth()+100)
                                .alpha(0.0f)
                                .setDuration(300)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        btCancelar.setVisibility(View.GONE);
                                    }
                                });
                        new mAsyncTask().execute("INICIAR","","");
                    }
                }
            },500);

        });

        //BOTÃO DE CANCELAR ROTA
        btCancelar.setOnClickListener((View v)->{

            cancelarAtendimento();

        });
    }

    private void finalizarAtendimento(){
        Rect displayRectangle = new Rect();
        Window window = this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.FullDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_rota3, viewGroup, false);
        dialogView.setMinimumWidth((int)(displayRectangle.width() * 1f));
        dialogView.setMinimumHeight((int)(displayRectangle.height() * 1f));
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();

        TextView tvTitulo = dialogView.findViewById(R.id.tvTitulo);
        tvTitulo.setText("Atendimento - " + mPref.Rota.getCodigo());
        TextView tvDescricao = dialogView.findViewById(R.id.tvDescricao);
        tvDescricao.setText(mPref.Clientes.getNome()+" - "+mPref.Clientes.getEnderecoCompleto());
        TextView tvObservacao = dialogView.findViewById(R.id.tvObservacao);
        RadioGroup rgMotivo = dialogView.findViewById(R.id.rgMotivo);
        RadioButton rbPositivar = dialogView.findViewById(R.id.rbPositivar);
        RadioButton rbNegativar = dialogView.findViewById(R.id.rbNegativar);
        EditText etObservacao = dialogView.findViewById(R.id.etObservacao);
        Spinner spMotivo = dialogView.findViewById(R.id.spMotivo);
        LinearLayout llSpinner = dialogView.findViewById(R.id.llSpinner);
        View vsSpinner = dialogView.findViewById(R.id.vsSpinner);
        Button btSalvar = dialogView.findViewById(R.id.btSalvar);
        TextView tvFechar = dialogView.findViewById(R.id.tvFechar);

        List<String> mList = new ArrayList<>();
        mList.add("ESCOLHA UMA OPÇÃO...");
        mList.add("ESTABELECIMENTO FECHADO");
        mList.add("ESTOQUE ABASTECIDO");
        mList.add("CLIENTE INSATISFEITO");
        mList.add("CLIENTE SEM TEMPO");
        mList.add("COMPROU DE OUTRO FORNECEDOR");
        mList.add("OUTROS");
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mList);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMotivo.setAdapter(mAdapter);
        //spMotivo.setEnabled(false);
        spMotivo.setActivated(false);

        btSalvar.setOnClickListener((View v) -> {
            myProgress = new ProgressDialog(mContex);
            myProgress.setCancelable(false);
            myProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            myProgress.setIndeterminate(true);
            myProgress.setTitle("Salvando...");
            Handler h = new Handler();
            if(rbNegativar.isChecked() && spMotivo.getSelectedItem().toString().equals("ESCOLHA UMA OPÇÃO...")){
                Toast.makeText(v.getContext(), "Escolha uma opção para negativação.", Toast.LENGTH_SHORT).show();
                spMotivo.requestFocus();
            }else if(rbNegativar.isChecked() && !spMotivo.getSelectedItem().toString().equals("ESCOLHA UMA OPÇÃO...")){
                alertDialog.dismiss();
                myProgress.show();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new mAsyncTask().execute("NEGATIVAR", etObservacao.getText().toString(), spMotivo.getSelectedItem().toString());
                    }
                }, 1000);
            }else{
                alertDialog.dismiss();
                myProgress.show();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new mAsyncTask().execute("POSITIVAR", etObservacao.getText().toString(), "");
                    }
                }, 1000);
            }

        });

        tvFechar.setOnClickListener((View v)-> {
            alertDialog.dismiss();
        });

        rbPositivar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                llSpinner.setVisibility(isChecked ? View.GONE : View.VISIBLE);
                vsSpinner.setVisibility(isChecked ? View.GONE : View.VISIBLE);
            }
        });

        alertDialog.show();

    }

    private void cancelarAtendimento(){

    }

    class mAsyncTask extends AsyncTask<String, Void, Boolean>{


        @Override
        protected Boolean doInBackground(String... strings) {
            mCalendar = Calendar.getInstance();
            String codigo = String.valueOf(mPref.Rota.getCodigo());
            Boolean result = false;
            switch (strings[0]){
                case "INICIAR":
                    result = mData.Rota.iniciarRota(codigo, data.format(mCalendar.getTime()), hora.format(mCalendar.getTime()));
                    mPref.Rota.setEmAtendimento(true);
                    break;
                case "POSITIVAR":
                    result = mData.Rota.positivarRota(codigo, strings[1]);
                    mPref.Rota.setEmAtendimento(false);
                    break;
                case "NEGATIVAR":
                    result = mData.Rota.negativarRota(codigo, strings[1], strings[2]);
                    mPref.Rota.setEmAtendimento(false);
                    break;
                case "CANCELAR":
                    result = mData.Rota.cancelarRota(codigo, strings[1], strings[2]);
                    mPref.Rota.setEmAtendimento(false);
                    break;
            }

            return result;
            /*return (mData.Rota.updateRota(String.valueOf(mPref.Rota.getCodigo()),"status", Integer.valueOf(strings[0]))
                    && mData.Rota.updateRota(String.valueOf(mPref.Rota.getCodigo()),"situacao", Integer.valueOf(strings[1]))
                    && mData.Rota.updateRota(String.valueOf(mPref.Rota.getCodigo()),"observacao", Integer.valueOf(strings[2]))
                    && mData.Rota.updateRota(String.valueOf(mPref.Rota.getCodigo()), !timeStart ? "data_inicio" : "data_fim" ,data.format(mCalendar.getTime()))
                    && mData.Rota.updateRota(String.valueOf(mPref.Rota.getCodigo()),!timeStart ? "hora_inicio" : "hora_fim",hora.format(mCalendar.getTime())));*/
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(myProgress != null && myProgress.isShowing()){
                myProgress.dismiss();
            }
            if(aBoolean){
                if(!timeStart){
                    //myProgress.dismiss();
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

    public static String getSecondFromMillis(long d) {
        SimpleDateFormat df = new SimpleDateFormat("ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(d);
    }

    public void stopCounter() {
        mCalendar = Calendar.getInstance();
        tvHoraFim.setText(hora.format(mCalendar.getTime()));
        mPref.Rota.setEndDate(mUtils.Data.dataFotmatadaBR(data.format(mCalendar.getTime())));
        mPref.Rota.setEndHora(hora.format(mCalendar.getTime()));
        //btIniciar.setText("CONCLUIDO");
        //btIniciar.setEnabled(false);
        //btIniciar.setBackground(getResources().getDrawable(R.drawable.status_concluido));
        customHandler.removeCallbacks(updateTimerThread);
        loadDetails();

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
