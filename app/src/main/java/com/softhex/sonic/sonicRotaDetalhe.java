package com.softhex.sonic;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
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
import android.widget.ProgressBar;
import android.widget.RadioButton;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sonic_rota_detalhe);

        mData = new sonicDatabaseCRUD(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mContex = this;
        mPrefs = new sonicPreferences(this);
        mUtils = new sonicUtils(mContex);
        mViewpager = findViewById(R.id.pagerSlide);
        mCollapsingToolbar = findViewById(R.id.mCollapsingToolbar);
        dotsLayout = findViewById(R.id.layoutDots);
        tvCount = findViewById(R.id.tvCount);
        fbNavegar = findViewById(R.id.fbNavegar);
        tvLogradrouro = findViewById(R.id.tvLogradouro);
        tvEndCompleto = findViewById(R.id.tvEnderecoCompleto);
        tvData = findViewById(R.id.tvData);
        tvNegCanc = findViewById(R.id.tvNegCanc);
        tvNegCancTitle = findViewById(R.id.tvNegCancTitle);
        tvCep = findViewById(R.id.tvCep);
        tvDuracao = findViewById(R.id.tvDuracao);
        btIniciar = findViewById(R.id.btIniciar);
        btCancelar = findViewById(R.id.btCancelar);
        llDetalhe = findViewById(R.id.llDetalhe);
        llBotoes = findViewById(R.id.llBotoes);
        llSituacao = findViewById(R.id.llSituacao);
        llTime = findViewById(R.id.llTime);
        llTime2 = findViewById(R.id.llTime2);
        tvObservacao = findViewById(R.id.tvObservacao);
        tvObservacaoTitle = findViewById(R.id.tvObservacaoTitle);
        tvPositivado = findViewById(R.id.tvPositivado);
        tvNegativado = findViewById(R.id.tvNegativado);
        tvCancelado = findViewById(R.id.tvCancelado);
        tvTextoInicio = findViewById(R.id.tvTextoInicio);
        tvTextoFim = findViewById(R.id.tvTextoFim);
        tvDuracaoMini = findViewById(R.id.tvDuracaoMini);
        pbDuracao = findViewById(R.id.pbDuracao);
        tvTempo = findViewById(R.id.tvTempo);

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
                    mCollapsingToolbar.setTitle(mPrefs.Clientes.getNome());
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
        mList = mData.Rota.selectRotaPorID(mPrefs.Rota.getCodigo());
        tvLogradrouro.setText(mPrefs.Clientes.getLogradouro());
        tvEndCompleto.setText(mPrefs.Clientes.getBairro()+", "+mPrefs.Clientes.getMunicipio()+" - "+mPrefs.Clientes.getUf());
        tvCep.setText("CEP: "+mPrefs.Clientes.getCep());
        switch (mList.get(0).getStatus()){
            case Status.NAO_INICIADO:
                llDetalhe.setVisibility(View.GONE);
                llBotoes.setVisibility(View.VISIBLE);
                btIniciar.setText("INICIAR ATENDIMENTO");
                btIniciar.setBackground(getResources().getDrawable(R.drawable.status_nao_iniciado));
                break;
            case Status.EM_ATENDIMENTO:
                continueClock();
                pbDuracao.setVisibility(View.VISIBLE);
                tvTempo.setVisibility(View.VISIBLE);
                tvTempo.setText(getString(R.string.rotaTime, "2 horas"));
                llBotoes.setVisibility(View.VISIBLE);
                llSituacao.setVisibility(View.GONE);
                llDetalhe.setVisibility(View.GONE);
                btIniciar.setText("FINALIZAR ATENDIMENTO");
                btIniciar.setBackground(getResources().getDrawable(R.drawable.status_em_atendimento));
                btCancelar.setText("IR PARA O CADASTRO DO CLIENTE");
                btCancelar.setBackground(getResources().getDrawable(R.drawable.botao_neutro));
                btCancelar.setTextColor(getResources().getColor(R.color.colorPrimary));
                mPrefs.Rota.setDataHora("Iniciado em "+mUtils.Data.dataFotmatadaBR(mList.get(0).getDataInicio())+" às "+mList.get(0).getHoraInicio());
                mPrefs.Rota.setEmAtendimentoCliente(mPrefs.Clientes.getClienteExibicao().equals("Nome Fantasia") ? mList.get(0).getNomeFantasia() : mList.get(0).getRazaoSocial());
                mPrefs.Rota.setEmAtendimentoEmpresa(mList.get(0).getEmpresa());
                break;
            case Status.CONCLUIDO:
                llTime.setVisibility(View.GONE);
                llTime2.setVisibility(View.VISIBLE);
                tvData.setText("Data: "+mUtils.Data.dataFotmatadaBR(mList.get(0).getDataInicio()));
                tvTextoInicio.setText("Iniciado às "+mList.get(0).getHoraInicio());
                tvTextoFim.setText("Concluído às "+mList.get(0).getHoraFim());
                tvDuracaoMini.setText(sonicUtils.getDifferenceTime(mList.get(0).getHoraInicio(), mList.get(0).getHoraFim()));
                llBotoes.setVisibility(View.GONE);
                llSituacao.setVisibility(View.VISIBLE);
                llDetalhe.setVisibility(View.VISIBLE);
                tvObservacaoTitle.setVisibility((mList.get(0).getObservacao().equals(null) || mList.get(0).getObservacao().equals("")) ? View.GONE : View.VISIBLE);
                tvObservacao.setVisibility((mList.get(0).getObservacao().equals(null) || mList.get(0).getObservacao().equals(""))  ? View.GONE : View.VISIBLE);
                tvObservacao.setText(mList.get(0).getObservacao());
                switch (mList.get(0).getSituacao()){
                    case 1: //POSITIVADO
                        tvNegCancTitle.setVisibility(View.GONE);
                        tvNegCanc.setVisibility(View.GONE);
                        tvPositivado.setTextColor(getResources().getColor(R.color.colorPrimaryWhite));
                        tvPositivado.setBackground(getResources().getDrawable(R.drawable.flat_selected_green_left));
                        break;
                    case 2: //NEGATIVADO
                        tvNegativado.setTextColor(getResources().getColor(R.color.colorPrimaryWhite));
                        tvNegativado.setBackground(getResources().getDrawable(R.drawable.flat_selected_orange_middle));
                        tvNegCancTitle.setText("Negativado por:");
                        tvNegCanc.setText(mList.get(0).getNegativacao());
                        break;
                }
                break;
            case Status.CANCELADO:
                llDetalhe.setVisibility(View.VISIBLE);
                llSituacao.setVisibility(View.VISIBLE);
                tvDuracao.setTextSize(14f);
                tvDuracao.setTypeface(tvDuracao.getTypeface(), Typeface.ITALIC);
                tvDuracao.setText("Cancelado em "+mUtils.Data.dataFotmatadaBR(mList.get(0).getDataInicio())+" às "+mList.get(0).getHoraInicio());
                tvCancelado.setTextColor(getResources().getColor(R.color.colorPrimaryWhite));
                tvCancelado.setBackground(getResources().getDrawable(R.drawable.flat_selected_red_right));
                tvNegCancTitle.setText("Cancelado por:");
                tvNegCanc.setText(mList.get(0).getCancelamento());
                tvObservacaoTitle.setVisibility(mList.get(0).getObservacao().equals("") ? View.GONE : View.VISIBLE);
                tvObservacao.setText(mList.get(0).getObservacao());
                llBotoes.setVisibility(View.GONE);
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

        // BOTÃO DE INICIAR/CONCLUIR ATENDIMENTO
        btIniciar.setOnClickListener((View v)->{
            myProgress.show();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    myProgress.dismiss();
                    if(mPrefs.Rota.getEmAtendimento()){
                        finalizarAtendimento();
                    }else {
                        btIniciar.setText("FINALIZAR ATENDIMENTO");
                        pbDuracao.setVisibility(View.VISIBLE);
                        pbDuracao.animate();
                        tvTempo.setVisibility(View.VISIBLE);
                        tvTempo.animate();
                        tvTempo.setText(getString(R.string.rotaTime, "2 horas"));
                        btIniciar.setBackground(getResources().getDrawable(R.drawable.status_em_atendimento));
                        btCancelar.animate()
                                .translationX(btCancelar.getWidth()+100)
                                .alpha(0.0f)
                                .setDuration(300)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        btCancelar.setText("IR PARA O CADASTRO DO CLIENTE");
                                        btCancelar.setBackground(getResources().getDrawable(R.drawable.botao_neutro));
                                        btCancelar.setTextColor(getResources().getColor(R.color.colorPrimary));
                                        btCancelar.animate()
                                                .translationX(0)
                                                .alpha(1f)
                                                .setDuration(300);

                                    }
                                });
                        new mAsyncTask().execute("INICIAR","","");
                    }
                }
            },500);

        });

        //BOTÃO DE CANCELAR ATENDIMENTO/IR PARA O CADASTRO DO CLIENTE
        btCancelar.setOnClickListener((View v)->{
            if(mPrefs.Rota.getEmAtendimento()){
                Intent i = new Intent(mContex, sonicClientesDetalhe.class);
                startActivityForResult(i, 1);
            }else{
                myProgress.show();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        myProgress.dismiss();
                        cancelarAtendimento();
                    }
                },1000);
            }
        });
    }

    private void finalizarAtendimento(){
        Rect displayRectangle = new Rect();
        Window window = this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.FullDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_rota_confirmada, viewGroup, false);
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
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mList);
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
                Toast.makeText(v.getContext(), "Escolha uma opção para negativação.", Toast.LENGTH_SHORT).show();
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
                mPrefs.Rota.setRefresh(true);
                if(!clockCounting && !mPrefs.Rota.getCancelada()){
                    startClock();
                }else{
                    stopClock();
                }
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
        clockCounting = true;
        customHandler.postDelayed(updateTimerThread, 0);

    }
    public void continueClock(){
        clockCounting = true;
        customHandler.postDelayed(updateTimerThread, 0);
    }
    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - mPrefs.Rota.getStartTime();
            pbDuracao.setMax(2*60);
            pbDuracao.setProgress((int)(timeInMilliseconds/(1000*60)));
            tvDuracao.setText(getDateFromMillis(timeInMilliseconds));
            customHandler.postDelayed(this, 1000);
        }
    };

    public static String getDateFromMillis(long d) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(d);
    }

    public void stopClock() {
        clockCounting = false;
        customHandler.removeCallbacks(updateTimerThread);
        loadDetails();

    }

    public void slideImages(){

        int count = 0;
        File file;
        String image = "";

        for(int i = 0; i < myImages.length ; i++){

            image = mPrefs.Clientes.getId()+(i==0? "" : "_"+i)+".JPG";
            file = new File(Environment.getExternalStorageDirectory(), sonicConstants.LOCAL_IMG_CLIENTES+image);

            if(file.exists()){
                count++;
            }
        }

        myImages = new String[count];

        for(int i = 0; i < count ; i++){

            image = mPrefs.Clientes.getId()+(i==0? "" : "_"+i)+".JPG";
            file = new File(Environment.getExternalStorageDirectory(), sonicConstants.LOCAL_IMG_CLIENTES+image);

            myImages[i] = file.toString();

        }


        if(count==0){
            myImages = new String[1];
            myImages[0] = sonicUtils.getURIForResource(R.drawable.nophoto);
        }

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
