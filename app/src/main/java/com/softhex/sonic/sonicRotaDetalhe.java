package com.softhex.sonic;

import android.Manifest;
import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class sonicRotaDetalhe extends AppCompatActivity {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({Status.NAO_INICIADO, Status.EM_ATENDIMENTO, Status.CONCLUIDO, Status.CANCELADO})
    public @interface Status{
        int NAO_INICIADO = 1;
        int EM_ATENDIMENTO = 2;
        int CONCLUIDO = 3;
        int CANCELADO = 4;
    }

    @IntDef({Situacao.POSITIVADO, Situacao.NEGATIVADO})
    public @interface Situacao{
        int POSITIVADO = 1;
        int NEGATIVADO = 2;
    }

    private static final int REQUEST_PERMISSION = 10;
    private CoordinatorLayout layoutMain;
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
    private TextView tvLogradrouro;
    private TextView tvEndCompleto;
    private TextView tvCep;
    private TextView tvData;
    private TextView tvDuracao;
    private TextView tvTextoInicio;
    private TextView tvTextoFim;
    private TextView tvDuracaoMini;
    private TextView tvNegCanc;
    private TextView tvNegCancTitle;
    private TextView tvObservacao;
    private TextView tvObservacaoTitle;
    private TextView tvPositivado;
    private TextView tvNegativado;
    private TextView tvCancelado;
    private LinearLayout llDetalhe;
    private LinearLayout llTime;
    private LinearLayout llTime2;
    private LinearLayout llSituacao;
    private LinearLayout llBotoes;
    private Button btIniciar;
    private Button btCancelar;
    private FloatingActionButton fbNavegar;
    private FusedLocationProviderClient mFusedLocationClient;
    private long timeInMilliseconds = 0;
    private Handler customHandler = new Handler();
    private boolean clockCounting = false;
    private ProgressDialog myProgress;
    private sonicUtils mUtils;
    private List<sonicRotaHolder> mList;
    private sonicPreferences mPrefs;
    private ProgressBar pbDuracao;
    private TextView tvTempo;
    private LinearLayout llProgress;
    private ProgressBar pbProgress;
    private int totalImagens;
    private AppBarLayout mAppBar;
    private ScrollView svRootView;
    private LinearLayout llCabecalho;
    private LinearLayout llDots;
    private LinearLayout llContador;
    private TextView tvContador;
    private LinearLayout llMensagem;
    private TextView tvMensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_visita_detalhe);

        mData = new sonicDatabaseCRUD(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mContex = this;
        mPrefs = new sonicPreferences(this);
        mUtils = new sonicUtils(mContex);
        layoutMain = findViewById(R.id.layoutMain);
        mViewpager = findViewById(R.id.pagerSlide);
        mCollapsingToolbar = findViewById(R.id.mCollapsingToolbar);
        dotsLayout = findViewById(R.id.layoutDots);
        tvCount = findViewById(R.id.tvCount);
        fbNavegar = findViewById(R.id.fbNavegar);
        tvLogradrouro = findViewById(R.id.tvLogradouro);
        tvEndCompleto = findViewById(R.id.tvEnderecoCompleto);
        //tvData = findViewById(R.id.tvData);
        //tvNegCanc = findViewById(R.id.tvNegCanc);
        //tvNegCancTitle = findViewById(R.id.tvNegCancTitle);
        tvCep = findViewById(R.id.tvCep);
        //tvDuracao = findViewById(R.id.tvDuracao);
        btIniciar = findViewById(R.id.btIniciar);
        btCancelar = findViewById(R.id.btCancelar);
        //llDetalhe = findViewById(R.id.llDetalhe);
        //llBotoes = findViewById(R.id.llBotoes);
        //llSituacao = findViewById(R.id.llSituacao);
        //llTime = findViewById(R.id.llTime);
        //llTime2 = findViewById(R.id.llTime2);
        tvObservacao = findViewById(R.id.tvObservacao);
       // tvObservacaoTitle = findViewById(R.id.tvObservacaoTitle);
        //tvPositivado = findViewById(R.id.tvPositivado);
        //tvNegativado = findViewById(R.id.tvNegativado);
        //tvCancelado = findViewById(R.id.tvCancelado);
        //tvTextoInicio = findViewById(R.id.tvTextoInicio);
        //tvTextoFim = findViewById(R.id.tvTextoFim);
        //tvDuracaoMini = findViewById(R.id.tvDuracaoMini);
        //pbDuracao = findViewById(R.id.pbDuracao);
        //tvTempo = findViewById(R.id.tvTempo);
        llProgress = findViewById(R.id.llProgress);
        pbProgress = findViewById(R.id.pbProgress);
        svRootView = findViewById(R.id.svRootView);
        llDots = findViewById(R.id.llDots);
        llCabecalho = findViewById(R.id.llCabecalho);
        llContador = findViewById(R.id.llContador);
        tvContador = findViewById(R.id.tvContador);
        llMensagem = findViewById(R.id.llMensagem);
        tvMensagem = findViewById(R.id.tvMensagem);

        slideImages();
        createInterface();
        loadAddress();
        loadItens();
        //loadActions();

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

        mAppBar = findViewById(R.id.appBar);
        mAppBar.setLayoutTransition(transition);

        mCollapsingToolbar.setTitle(mPrefs.Clientes.getNome()+"\nCÓD.: #"+mPrefs.Rota.getCodigo());

        mCollapsingToolbar.setExpandedTitleTextAppearance(totalImagens==0 ? R.style.ExpandedTitlePrimary : R.style.ExpandedTitleWhite);

        //mAppBar.setExpanded(totalImagens==0 ? false : true);

        mToolbar.setNavigationOnClickListener((View v)-> {
            onBackPressed();
        });

        mAppBar.addOnOffsetChangedListener(appBarListener);

        fbNavegar.setOnClickListener((View v)->{
            mList = mData.Rota.selectRotaPorID(mPrefs.Rota.getCodigo());
            addItem(Status.EM_ATENDIMENTO, mList);
        });

    }

    private AppBarLayout.OnOffsetChangedListener appBarListener = new AppBarLayout.OnOffsetChangedListener() {
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            if((mCollapsingToolbar.getHeight()+verticalOffset)<(2 * ViewCompat.getMinimumHeight(mCollapsingToolbar))){
                mToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryWhite), PorterDuff.Mode.SRC_ATOP);
                mCollapsingToolbar.setTitle("#" + mPrefs.Rota.getCodigo() +" - " + mPrefs.Clientes.getNome());
            }else {
                mToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryBlack), PorterDuff.Mode.SRC_ATOP);
                mCollapsingToolbar.setTitle(mPrefs.Clientes.getNome()+"\nCÓD.: #"+mPrefs.Rota.getCodigo());
            }
        }
    };

    private void loadAddress(){

        mList = mData.Rota.selectRotaPorID(mPrefs.Rota.getCodigo());
        tvLogradrouro.setText(mList.get(0).getLogradrouro());
        tvEndCompleto.setText(mList.get(0).getBairro()+", "+mList.get(0).getMunicipio()+" - "+mList.get(0).getUf());
        tvCep.setText("CEP: "+sonicUtils.stringToCep(mList.get(0).getCep()));
        mPrefs.Clientes.setEnderecoCompleto(mList.get(0).getLogradrouro()+", "+mList.get(0).getBairro()+", "+mList.get(0).getMunicipio()+" - "+mList.get(0).getUf());
    }

    private void loadItens(){
        mList = mData.Rota.selectRotaPorID(mPrefs.Rota.getCodigo());
        LinearLayout llParentView = (LinearLayout)svRootView.getChildAt(0);
        View view = LayoutInflater.from(mContex).inflate(R.layout.layout_cards_rota_detalhe_itens, null, false);
        View dots = view.findViewById(R.id.dotStatus);
        TextView tvData = view.findViewById(R.id.tvData);
        TextView tvDataLimite = view.findViewById(R.id.tvDataLimite);
        LinearLayout llBotoesAcao = view.findViewById(R.id.llBotoesAcao);
        Button btIniciar = view.findViewById(R.id.btIniciar);
        Button btFinalizar = view.findViewById(R.id.btFinalizar);
        Button btCancelar = view.findViewById(R.id.btCancelar);
        llParentView.removeAllViews();

        switch (mList.get(0).getStatus()){
            case Status.NAO_INICIADO:
                dots.setBackground(mContex.getResources().getDrawable(R.drawable.dots_nao_iniciado));
                tvData.setText("Agendado para "+mUtils.Data.dataFotmatadaBR(mList.get(0).getDataAgendamento()));
                llBotoesAcao.setVisibility(View.VISIBLE);
                btIniciar.setVisibility(View.VISIBLE);
                btIniciar.setOnClickListener((View v)->{
                    Handler h = new Handler();
                    myProgress = new ProgressDialog(mContex);
                    myProgress.setCancelable(false);
                    myProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    myProgress.setIndeterminate(true);
                    myProgress.setTitle("Processando...");
                    myProgress.show();
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startClock();
                            myProgress.dismiss();
                            llBotoesAcao.setVisibility(View.GONE);
                            new mAsyncTask().execute("INICIAR","","");
                        }
                    },500);

                });
                btCancelar.setVisibility(View.VISIBLE);
                llParentView.addView(view);
                break;
            case Status.EM_ATENDIMENTO:
                continueClock();
                Animation a = AnimationUtils.loadAnimation(mContex, R.anim.slide_in_left);
                a.setStartOffset(0);
                llContador.startAnimation(a);
                llContador.setVisibility(View.VISIBLE);
                llMensagem.startAnimation(a);
                llMensagem.setVisibility(View.VISIBLE);
                tvMensagem.setText(getString(R.string.rotaTime, "2 horas"));
                llCabecalho.findViewById(R.id.vSeparador).setVisibility(View.VISIBLE);
                addItem(Status.NAO_INICIADO, mList);
                dots.setBackground(mContex.getResources().getDrawable(R.drawable.dots_em_atendimento));
                tvData.setText("Iniciado em "+ mUtils.Data.dataFotmatadaBR(mList.get(0).getDataInicio())+" às "+ mUtils.Data.horaFotmatadaBR(mList.get(0).getHoraInicio()));
                tvData.setTextColor(getResources().getColor(R.color.colorPrimaryBlue));
                llBotoesAcao.setVisibility(View.VISIBLE);
                btFinalizar.setVisibility(View.VISIBLE);
                btFinalizar.setOnClickListener((View v)->{
                    Handler h = new Handler();
                    myProgress = new ProgressDialog(mContex);
                    myProgress.setCancelable(false);
                    myProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    myProgress.setIndeterminate(true);
                    myProgress.setTitle("Processando...");
                    myProgress.show();
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            stopClock();
                            myProgress.dismiss();
                            finalizarAtendimento();
                        }
                    },500);

                });
                llParentView.addView(view);
                break;
            case Status.CONCLUIDO:
                llMensagem.setVisibility(View.GONE);
                llCabecalho.findViewById(R.id.vSeparador).setVisibility(View.VISIBLE);
                addItem(Status.NAO_INICIADO, mList);
                addItem(Status.EM_ATENDIMENTO, mList);
                addItem(Status.CONCLUIDO, mList);
                Animation b = AnimationUtils.loadAnimation(mContex, R.anim.fade_in);
                b.setStartOffset(0);
                llContador.startAnimation(b);
                llContador.setVisibility(View.VISIBLE);
                tvContador.setText(sonicUtils.getDifferenceTime(mList.get(0).getHoraInicio(), mList.get(0).getHoraFim()));

                break;
            case Status.CANCELADO:
                llMensagem.setVisibility(View.GONE);
                llCabecalho.findViewById(R.id.vSeparador).setVisibility(View.VISIBLE);
                addItem(Status.NAO_INICIADO, mList);
                addItem(Status.CANCELADO, mList);
                break;
        }

        llParentView.post(new Runnable() {
            @Override
            public void run() {

                Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_in_left);
                animation.setStartOffset(0);
                view.startAnimation(animation);

            }
        });

    }

    private void addItem(int tipo, List<sonicRotaHolder> mList){
        LinearLayout llParentView = (LinearLayout)svRootView.getChildAt(0);
        View view = LayoutInflater.from(mContex).inflate(R.layout.layout_cards_rota_detalhe_itens, null, false);
        View dots = view.findViewById(R.id.dotStatus);
        TextView tvData = view.findViewById(R.id.tvData);
        TextView tvMotivoNegativacao = view.findViewById(R.id.tvMotivoNegativacao);
        TextView tvMotivoCancelamento = view.findViewById(R.id.tvMotivoCancelamento);
        TextView tvObservacao = view.findViewById(R.id.tvObservacao);

        switch (tipo){
            case Status.NAO_INICIADO:
                dots.setBackground(mContex.getResources().getDrawable(R.drawable.dots_nao_iniciado));
                tvData.setText("Agendado para "+mUtils.Data.dataFotmatadaBR(mList.get(0).getDataAgendamento()));
                llParentView.addView(view);
                break;
            case Status.EM_ATENDIMENTO:
                dots.setBackground(mContex.getResources().getDrawable(R.drawable.dots_em_atendimento));
                tvData.setText("Iniciado em " + mUtils.Data.dataFotmatadaBR(mList.get(0).getDataInicio())+" às "+ mUtils.Data.horaFotmatadaBR(mList.get(0).getHoraInicio()));
                tvData.setTextColor(getResources().getColor(R.color.colorPrimaryBlue));
                llParentView.addView(view);
                break;
            case Status.CONCLUIDO:
                switch (mList.get(0).getSituacao()){
                    case Situacao.POSITIVADO:
                        dots.setBackground(mContex.getResources().getDrawable(R.drawable.dots_concluido_positivado));
                        tvData.setText("Positivado às "+ mUtils.Data.horaFotmatadaBR(mList.get(0).getHoraFim()));
                        tvData.setTextColor(getResources().getColor(R.color.colorPrimaryGreen));
                        tvObservacao.setVisibility(View.VISIBLE);
                        tvObservacao.setText("Observação: " + mList.get(0).getObservacao());
                        llParentView.addView(view);
                        break;
                    case Situacao.NEGATIVADO:
                        dots.setBackground(mContex.getResources().getDrawable(R.drawable.dots_concluido_negativado));
                        tvData.setText("Negativado às "+ mUtils.Data.horaFotmatadaBR(mList.get(0).getHoraFim()));
                        tvData.setTextColor(getResources().getColor(R.color.colorPrimaryOrange));
                        tvMotivoNegativacao.setVisibility(View.VISIBLE);
                        tvMotivoNegativacao.setText("Motivo: " + mList.get(0).getNegativacao());
                        tvObservacao.setVisibility(View.VISIBLE);
                        tvObservacao.setText("Observação: " + mList.get(0).getObservacao());
                        llParentView.addView(view);
                        break;
                }
                break;
            case Status.CANCELADO:
                dots.setBackground(mContex.getResources().getDrawable(R.drawable.dots_cancelado));
                tvData.setText("Cancelado em "+ mUtils.Data.dataFotmatadaBR(mList.get(0).getDataInicio())+" às "+ mUtils.Data.horaFotmatadaBR(mList.get(0).getHoraInicio()));
                tvData.setTextColor(getResources().getColor(R.color.colorPrimaryRed));
                tvMotivoCancelamento.setVisibility(View.VISIBLE);
                tvMotivoCancelamento.setText(mList.get(0).getCancelamento());
                tvObservacao.setVisibility(View.VISIBLE);
                tvObservacao.setText("Observação: " + mList.get(0).getObservacao());
                llParentView.addView(view);
                break;
        }

        llParentView.post(new Runnable() {
            @Override
            public void run() {

                Animation animation = AnimationUtils.loadAnimation(mContex, R.anim.slide_in_left);
                animation.setStartOffset(0);
                view.startAnimation(animation);

            }
        });


    }

    private void finalizarAtendimento(){

        Window window = this.getWindow();

        Rect displayRectangle = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContex, R.style.FullDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(mContex).inflate(R.layout.dialog_rota_confirmada, viewGroup, false);
        dialogView.setMinimumWidth((int)(displayRectangle.width() * 1f));
        dialogView.setMinimumHeight((int)(displayRectangle.height() * 1f));
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();

        TextView tvTitulo = dialogView.findViewById(R.id.tvTitulo);
        tvTitulo.setText("Atendimento #" + mPrefs.Rota.getCodigo());
        TextView tvDescricao = dialogView.findViewById(R.id.tvDescricao);
        tvDescricao.setText(mPrefs.Clientes.getNome()+" - "+mPrefs.Clientes.getEnderecoCompleto());
        TextView tvDataHora = dialogView.findViewById(R.id.tvDataHora);
        tvDataHora.setText(mPrefs.Rota.getDataHora());
        RadioButton rbPositivar = dialogView.findViewById(R.id.rbPositivar);
        RadioButton rbNegativar = dialogView.findViewById(R.id.rbNegativar);
        EditText etObservacao = dialogView.findViewById(R.id.etObservacao);
        Spinner spMotivo = dialogView.findViewById(R.id.spMotivo);
        LinearLayout llSpinner = dialogView.findViewById(R.id.llSpinner);
        View vsSpinner = dialogView.findViewById(R.id.vsSpinner);
        Button btConfirmar = dialogView.findViewById(R.id.btConfirmar);
        TextView tvFechar = dialogView.findViewById(R.id.tvFechar);

        List<String> mList = new ArrayList<>();
        String[] s = getResources().getStringArray(R.array.rotaNegativadaOptions);
        for (String item: s) {
            mList.add(item);
        }

        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(mContex, android.R.layout.simple_spinner_item, mList);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMotivo.setAdapter(mAdapter);

        btConfirmar.setOnClickListener((View v) -> {
            myProgress = new ProgressDialog(mContex);
            myProgress.setCancelable(false);
            myProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            myProgress.setIndeterminate(true);
            myProgress.setTitle("Salvando...");
            Handler h = new Handler();
            if(rbNegativar.isChecked() && spMotivo.getSelectedItem().toString().equals("ESCOLHA UMA OPÇÃO...")){
                new android.app.AlertDialog.Builder(mContex)
                        .setMessage("Escolha uma opção para negativação...")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                        spMotivo.requestFocus();
            }else if(rbNegativar.isChecked() && !spMotivo.getSelectedItem().toString().equals("ESCOLHA UMA OPÇÃO...")){
                myProgress.show();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        alertDialog.dismiss();
                        new mAsyncTask().execute("NEGATIVAR", spMotivo.getSelectedItem().toString(), etObservacao.getText().toString());
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
                btConfirmar.setBackground(isChecked ? getResources().getDrawable(R.drawable.botao_positivar) : getResources().getDrawable(R.drawable.botao_negativar));
            }
        });

        alertDialog.show();

    }

    private void cancelarAtendimento(){

            Rect displayRectangle = new Rect();
            Window window = this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.FullDialog);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_rota_cancelada, viewGroup, false);
            dialogView.setMinimumWidth((int)(displayRectangle.width() * 1f));
            dialogView.setMinimumHeight((int)(displayRectangle.height() * 1f));
            dialogBuilder.setView(dialogView);
            final AlertDialog alertDialog = dialogBuilder.create();

            TextView tvTitulo = dialogView.findViewById(R.id.tvTitulo);
            tvTitulo.setText("Atendimento #" + mPrefs.Rota.getCodigo());
            TextView tvDescricao = dialogView.findViewById(R.id.tvDescricao);
            tvDescricao.setText(mPrefs.Clientes.getNome()+" - "+mPrefs.Clientes.getEnderecoCompleto());
            TextView tvInfo = dialogView.findViewById(R.id.tvInfo);
            tvInfo.setText(R.string.rotaCancelada);
            EditText etObservacao = dialogView.findViewById(R.id.etObservacao);
            Spinner spMotivo = dialogView.findViewById(R.id.spMotivo);
            Button btConfirmar = dialogView.findViewById(R.id.btConfirmar);
            TextView tvFechar = dialogView.findViewById(R.id.tvFechar);

            List<String> mList = new ArrayList<>();
            String[] s = getResources().getStringArray(R.array.rotaCanceladaOptions);
            for (String item: s) {
                mList.add(item);
            }

            ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mList);
            mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spMotivo.setAdapter(mAdapter);

            btConfirmar.setOnClickListener((View v) -> {
                myProgress = new ProgressDialog(mContex);
                myProgress.setCancelable(false);
                myProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                myProgress.setIndeterminate(true);
                myProgress.setTitle("Cancelando...");
                Handler h = new Handler();
                if(spMotivo.getSelectedItem().toString().equals("ESCOLHA UMA OPÇÃO...")){
                    Toast.makeText(v.getContext(), "Escolha uma opção para cancelamento.", Toast.LENGTH_SHORT).show();
                    spMotivo.requestFocus();
                }else{
                    myProgress.show();
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            alertDialog.dismiss();
                            new mAsyncTask().execute("CANCELAR", spMotivo.getSelectedItem().toString(), etObservacao.getText().toString());
                        }
                    }, 1000);
                }

            });

            tvFechar.setOnClickListener((View v)-> {
                alertDialog.dismiss();
            });

            alertDialog.show();

    }

    class mAsyncTask extends AsyncTask<String, Void, Boolean>{


        @Override
        protected Boolean doInBackground(String... strings) {
            String codigo = String.valueOf(mPrefs.Rota.getCodigo());
            Boolean result = false;
            switch (strings[0]){
                case "INICIAR":
                    startClock();
                    result = mData.Rota.iniciarRota(codigo);
                    mPrefs.Rota.setEmAtendimento(true);
                    mPrefs.Rota.setCancelada(false);
                    break;
                case "POSITIVAR":
                    result = mData.Rota.positivarRota(codigo, strings[1]);
                    mPrefs.Rota.setEmAtendimento(false);
                    break;
                case "NEGATIVAR":
                    result = mData.Rota.negativarRota(codigo, strings[1], strings[2]);
                    mPrefs.Rota.setEmAtendimento(false);
                    break;
                case "CANCELAR":
                    result = mData.Rota.cancelarRota(codigo, strings[1], strings[2]);
                    mPrefs.Rota.setEmAtendimento(false);
                    mPrefs.Rota.setCancelada(true);
                    break;
            }

            return result;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(myProgress != null && myProgress.isShowing()){
                myProgress.dismiss();
            }
            if(aBoolean){
                loadItens();
            }else{
                Snackbar snackbar = Snackbar
                        .make(getWindow().getDecorView().getRootView(), "ERRO", Snackbar.LENGTH_INDEFINITE)
                        .setAction("DETALHE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                new android.app.AlertDialog.Builder(mContex)
                                        .setMessage(mPrefs.Geral.getError())
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).show();
                            }
                        });

                View sbView = snackbar.getView();
                TextView textView = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                sbView.setBackgroundColor(mContex.getResources().getColor(R.color.colorPrimary));
                textView.setTextColor(mContex.getResources().getColor(R.color.colorPrimaryWhite));
                snackbar.show();
                mPrefs.Rota.setEmAtendimento(false);
            }

        }
    }

    public void startClock(){
        mPrefs.Rota.setStartTime(SystemClock.uptimeMillis());
        customHandler.postDelayed(updateTimerThread, 0);

    }
    public void continueClock(){
        customHandler.postDelayed(updateTimerThread, 0);
    }
    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - mPrefs.Rota.getStartTime();
            //pbDuracao.setMax(2*60);
            //pbProgress.setMax(2*60);
            //pbDuracao.setProgress((int)(timeInMilliseconds/(1000*60)));
            //pbProgress.setProgress((int)(timeInMilliseconds/(1000*60)));
            tvContador.setText(getDateFromMillis(timeInMilliseconds));
            customHandler.postDelayed(this, 1000);
        }
    };

    public static String getDateFromMillis(long d) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(d);
    }

    public void stopClock() {
        customHandler.removeCallbacks(updateTimerThread);
    }

    public void slideImages(){

        totalImagens = 0;
        File file;
        String image = "";

        for(int i = 0; i < myImages.length ; i++){

            image = mPrefs.Clientes.getCodigo()+(i==0? "" : "_"+i)+".JPG";
            file = new File(Environment.getExternalStorageDirectory(), sonicConstants.LOCAL_IMG_CLIENTES+image);

            if(file.exists()){
                totalImagens++;
            }
        }

        myImages = new String[totalImagens];

        for(int i = 0; i < totalImagens ; i++){

            image = mPrefs.Clientes.getCodigo()+(i==0? "" : "_"+i)+".JPG";
            file = new File(Environment.getExternalStorageDirectory(), sonicConstants.LOCAL_IMG_CLIENTES+image);

            myImages[i] = file.toString();

        }


        if(totalImagens==0){
            myImages = new String[1];
            myImages[0] = sonicUtils.getURIForResource(R.drawable.nophoto);
        }

        sonicSlideImageAdapter myAdapter = new sonicSlideImageAdapter(this, myImages, totalImagens==0 ? false : true);
        mViewpager.setAdapter(myAdapter);
        mViewpager.addOnPageChangeListener(viewListener);
        //addBottomDots(0);
        addCount(1);

    }

    private void addCount(int position){
        if(myImages.length>1){
            llDots.setVisibility(View.VISIBLE);
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
                                    String uri = "geo:"+location.getLongitude()+","+location.getLongitude()+"?q="+mPrefs.Rota.getAddressMap();
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
