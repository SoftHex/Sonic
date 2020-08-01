package com.softhex.sonic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.StringDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.softhex.materialdialog.PromptDialog;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class sonicRotaAdapter extends RecyclerView.Adapter<sonicRotaAdapter.rotaHolder> implements Filterable {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({Tipo.PADRAO, Tipo.AGENDAMENTO, Tipo.REAGENDAMENTO})
    public @interface Tipo {
        int PADRAO = 1;
        int AGENDAMENTO = 2;
        int REAGENDAMENTO = 3;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({Status.NAO_INICIADO, Status.EM_ATENDIMENTO, Status.CONCLUIDO, Status.CANCELADO})
    public @interface Status{
        int NAO_INICIADO = 1;
        int EM_ATENDIMENTO = 2;
        int CONCLUIDO = 3;
        int CANCELADO = 4;
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({StatusText.NAO_INICIADO, StatusText.EM_ATENDIMENTO, StatusText.CONCLUIDO, StatusText.CANCELADO})
    public @interface StatusText{
        String NAO_INICIADO = "Não Iniciado";
        String EM_ATENDIMENTO = "Em Atendimento";
        String CONCLUIDO = "Concluído";
        String CANCELADO = "Cancelado";
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({Situacao.POSITIVADO, Situacao.NEGATIVADO})
    public @interface Situacao{
        int POSITIVADO = 1;
        int NEGATIVADO = 2;
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({SituacaoText.POSITIVADO, SituacaoText.NEGATIVADO})
    public @interface SituacaoText{
        String POSITIVADO = "Positivado";
        String NEGATIVADO = "Negativado";
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ItemText.INICIAR, ItemText.CONCLUIR, ItemText.CANCELAR})
    public @interface ItemText{
        String INICIAR = "Iniciar Atendimento";
        String CONCLUIR = "Concluir Atendimento";
        String CANCELAR = "Cancelar Atendimento";
    }

    private static final int VIEW_HEADER= 1;
    private static final int VIEW_PROGRESS = 2;
    private static final int VIEW_ITEM = 3;
    private Context mContext;
    private Activity mActivity;
    private List<sonicRotaHolder> mTotalList;
    private List<sonicRotaHolder> mPartialList;
    private List<sonicRotaHolder> mFilteredList;
    private RotaFilter rotaFilter;
    private Boolean nFantasia;
    private sonicPreferences mPrefs;
    private sonicUtils mUtils;
    private RecyclerView mRecycler;
    private boolean rotaPessoal;
    private boolean isLoading = false;
    private LinearLayoutManager linearLayoutManager;
    private Long timeInMilliseconds;
    private Handler customHandler = new Handler();
    private Calendar mCalendarStart, mCalendarEnd;
    private SimpleDateFormat dataSearch = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat dataShow = new SimpleDateFormat("dd/MM/yyyy");

    public class rotaHolder extends RecyclerView.ViewHolder {

        TextView tvCodigo;
        TextView tvNome;
        TextView tvEndereco;
        TextView tvStatus;
        TextView tvAtendente;
        TextView tvDataHora;
        TextView tvTempo;
        ImageView ivImagem;
        TextView tvSituacao;
        TextView tvLetra;
        LinearLayout linearItem;
        ProgressBar pbDuracao;
        LinearLayout llGroupDate;
        LinearLayout llProgress;

        rotaHolder(View view) {
            super(view);
            linearItem = view.findViewById(R.id.linearItem);
            tvCodigo = view.findViewById(R.id.tvCodigo);
            tvNome = view.findViewById(R.id.tvNome);
            tvLetra = view.findViewById(R.id.tvLetra);
            ivImagem = view.findViewById(R.id.ivImagem);
            tvAtendente = view.findViewById(R.id.tvAtendente);
            tvStatus = view.findViewById(R.id.tvStatus);
            //tvTempo = view.findViewById(R.id.tvTempo);
            tvSituacao = view.findViewById(R.id.tvSituacao);
            tvEndereco = view.findViewById(R.id.tvEndereco);
            tvDataHora = view.findViewById(R.id.tvDataHora);
            pbDuracao = view.findViewById(R.id.pbDuracao);
            llGroupDate = view.findViewById(R.id.llGroupDate);
            llProgress = view.findViewById(R.id.llProgress);

        }
    }

    sonicRotaAdapter(List<sonicRotaHolder> rotas, Context context, RecyclerView mRecycler, Activity act, boolean pessoal) {
        this.mContext = context;
        this.mTotalList = rotas;
        this.mFilteredList = rotas;
        this.mActivity = act;
        this.rotaPessoal = pessoal;
        this.mUtils = new sonicUtils(mContext);
        this.mPrefs = new sonicPreferences(mContext);
        this.mRecycler = mRecycler;
        this.nFantasia =  mPrefs.Clientes.getClienteExibicao().equals("Nome Fantasia") ? true : false;

        mPartialList = new ArrayList();
        mPartialList.add(0,null);
        mTotalList.add(0,null);

        if(mPrefs.Geral.getListagemCompleta()){

            for(int i=0;i<rotas.size();i++){
                mPartialList.add(rotas.get(i));
            }

        }else{

            if(rotas.size()< sonicConstants.TOTAL_ITENS_LOAD){
                for(int i = 0; i < rotas.size(); i++){
                    mPartialList.add(rotas.get(i));
                }
            }else{
                for(int i = 0; i<sonicConstants.TOTAL_ITENS_LOAD-1; i++){
                    mPartialList.add(rotas.get(i));
                }
            }

            linearLayoutManager = (LinearLayoutManager) mRecycler.getLayoutManager();

            mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if(!isLoading){
                        if(linearLayoutManager !=null && linearLayoutManager.findLastVisibleItemPosition()==mPartialList.size()-1){
                            if(mPartialList.size()>=sonicConstants.TOTAL_ITENS_LOAD-1){
                                loadMore();
                            }
                        }
                    }
                }
            });
        }

    }

    @Override
    public rotaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=null;
        switch (viewType){
            case VIEW_HEADER:
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_cards_header, parent, false);
                break;
            case VIEW_ITEM:
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_cards_rota_itens, parent, false);
                break;
            case VIEW_PROGRESS:
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_cards_rota_itens_shimmer, parent, false);
                break;
        }
        return new rotaHolder(view);
    }

    @Override
    public void onBindViewHolder(rotaHolder holder, int position) {

        sonicRotaHolder rota = mTotalList.get(position);
        holder.setIsRecyclable(false);

        switch (holder.getItemViewType()){
            case VIEW_HEADER:
                criarFiltroBusca(holder);
                break;
            case VIEW_ITEM:
                exibirItemLista(holder, rota, position);
                break;
        }

    }

    @Override
    public Filter getFilter() {
        if(rotaFilter == null)
            rotaFilter = new RotaFilter(this, mTotalList);
        return rotaFilter;
    }

    @Override
    public int getItemViewType(int position) {
        return (position==0 && mTotalList.get(position)==null) ? VIEW_HEADER : mTotalList.get(position) == null ? VIEW_PROGRESS : VIEW_ITEM;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return mTotalList.size();
    }

    private void loadMore(){

        isLoading = true;
        mPartialList.add(null);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        },0);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                mPartialList.remove(mPartialList.size()-1);
                notifyItemRemoved(mPartialList.size());

                if(!mRecycler.isComputingLayout()){
                    notifyDataSetChanged();
                    int scrollPosition = mPartialList.size();
                    int currentSize = scrollPosition;
                    int nextLimit = currentSize + sonicConstants.TOTAL_ITENS_LOAD;
                    for(int i = currentSize; i < nextLimit; i++) {
                        if(currentSize< mTotalList.size()){
                            mPartialList.add(mTotalList.get(i));
                            currentSize++;
                        }
                    }
                }
                isLoading = false;
            }
        }, 1000);

    }
    private void criarFiltroBusca(rotaHolder holder){

        holder.llGroupDate.removeAllViews();
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.height = sonicUtils.convertDpToPixel(36, mContext);
        p2.height = sonicUtils.convertDpToPixel(36, mContext);
        p.setMargins(0,0, sonicUtils.convertDpToPixel(6, mContext), 0);
        p2.setMargins(sonicUtils.convertDpToPixel(12, mContext),0, sonicUtils.convertDpToPixel(6, mContext), 0);
        String[] values = mContext.getResources().getStringArray(R.array.datePickerValues);
        Button bt1 = new Button(mContext);
        bt1.setText(mPrefs.Rota.getTermSearch());
        bt1.setTextSize(12);
        bt1.setBackground(mContext.getResources().getDrawable(R.drawable.botao_selecionado));
        bt1.setLayoutParams(p2);
        bt1.setPadding(30,0,30,0);
        holder.llGroupDate.addView(bt1);
        for (String s: values) {
            if(!s.contains(mPrefs.Rota.getTermSearch())){
                Button bt2 = new Button(mContext);

                bt2.setOnClickListener((View v)-> {

                    switch (bt2.getText().toString()){
                        case "FAIXA ESPECÍFICA...":
                            exibirDateOptionsRange();
                            break;
                        default:
                            mPrefs.Rota.setTermSearch(bt2.getText().toString());
                            ((sonicRota)mContext).refreshFragments(rotaPessoal ? 1 : 0);
                            break;
                    }

                });
                bt2.setText(s);
                bt2.setTextSize(12);
                bt2.setBackground(mContext.getResources().getDrawable(R.drawable.botao_neutro));
                bt2.setLayoutParams(p);

                bt2.setPadding(30,0,30,0);
                holder.llGroupDate.addView(bt2);
            }
        }

    }

    private void exibirItemLista(rotaHolder holder, sonicRotaHolder rota, int position){

        String cliNomeExibicao = nFantasia ? rota.getNomeFantasia() : rota.getRazaoSocial();

        holder.tvCodigo.setText("#" + (!rotaPessoal ? rota.getCodigo() : rota.getId()));
        holder.tvNome.setText(cliNomeExibicao);
        holder.tvAtendente.setText(rota.getAtendente());
        holder.tvEndereco.setText(rota.getEnderecoCompleto());
        holder.tvDataHora.setText(mUtils.Data.dataFotmatadaBR(rota.getDataAgendamento())+" às "+mUtils.Data.horaFotmatadaSemSegundoBR(rota.getHoraAgendamento()));

        File f = sonicFile.searchImage(sonicConstants.LOCAL_IMG_CLIENTES, rota.getCodigoCliente());

        if (f.exists()) {
            holder.ivImagem.setVisibility(View.VISIBLE);
            holder.tvLetra.setVisibility(View.GONE);
            Glide.get(mContext).clearMemory();
            Glide.with(mContext)
                    .load(f)
                    .circleCrop()
                    .override(100, 100)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    //.transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                    .into(holder.ivImagem);


        } else {

            holder.tvLetra.setText(String.valueOf(cliNomeExibicao.charAt(0)).toUpperCase());

        }
        switch (rota.getStatus()) {
            case Status.NAO_INICIADO:
                holder.tvStatus.setBackground(mContext.getResources().getDrawable(R.drawable.status_nao_iniciado));
                holder.tvStatus.setText(StatusText.NAO_INICIADO);
                break;
            case Status.EM_ATENDIMENTO:
                //holder.tvTempo.setVisibility(View.VISIBLE);
                //holder.pbDuracao.setVisibility(View.VISIBLE);
                holder.llProgress.setVisibility(View.VISIBLE);
                holder.tvStatus.setBackground(mContext.getResources().getDrawable(R.drawable.status_em_atendimento));
                holder.tvStatus.setText(StatusText.EM_ATENDIMENTO);
                timeInMilliseconds = SystemClock.uptimeMillis() - mPrefs.Rota.getStartTime();
                holder.pbDuracao.setMax(2 * 60);
                holder.pbDuracao.setProgress((int) (timeInMilliseconds / (1000 * 60)));
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        timeInMilliseconds = SystemClock.uptimeMillis() - mPrefs.Rota.getStartTime();
                        holder.pbDuracao.setMax(2 * 60);
                        holder.pbDuracao.setProgress((int) (timeInMilliseconds / (1000 * 60)));
                        customHandler.postDelayed(this, 1000);
                    }
                };
                customHandler.postDelayed(r, 0);
                break;
            case Status.CONCLUIDO:
                holder.tvStatus.setBackground(mContext.getResources().getDrawable(R.drawable.status_concluido));
                holder.tvStatus.setText(StatusText.CONCLUIDO);
                holder.tvSituacao.setVisibility(View.VISIBLE);
                switch (rota.getSituacao()) {
                    case Situacao.POSITIVADO:
                        holder.tvSituacao.setText(SituacaoText.POSITIVADO);
                        holder.tvSituacao.setBackground(mContext.getResources().getDrawable(R.drawable.situacao_positivado));
                        break;
                    case Situacao.NEGATIVADO:
                        holder.tvSituacao.setText(SituacaoText.NEGATIVADO);
                        holder.tvSituacao.setBackground(mContext.getResources().getDrawable(R.drawable.situacao_negativado));
                        break;
                }
                break;
            case Status.CANCELADO:
                holder.tvStatus.setBackground(mContext.getResources().getDrawable(R.drawable.status_cancelado));
                holder.tvStatus.setText(StatusText.CANCELADO);
                break;
        }

        holder.linearItem.setOnClickListener((View v) -> {
            if (mPrefs.Rota.getEmAtendimento() && rota.getStatus() == Status.NAO_INICIADO) {

                new PromptDialog(mContext)
                        .setDialogType(PromptDialog.DIALOG_TYPE_INFO)
                        .setAnimationEnable(true)
                        .setTitleText(R.string.msgAtencao)
                        .setContentText("Existe um rota em atendimento para o cliente " + mPrefs.Rota.getEmAtendimentoCliente() + ". Finalize-a para iniciar a próxima.")
                        .setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                            @Override
                            public void onClick(PromptDialog dialog) {
                                dialog.dismiss();
                                //mRecycler.smoothScrollToPosition(mPrefs.Rota.getItemPosition());
                            }
                        }).show();

            } else {

                mPrefs.Clientes.setId(rota.getCodigoCliente());
                mPrefs.Clientes.setNome(cliNomeExibicao);
                mPrefs.Clientes.setLogradouro(rota.getLogradrouro());
                mPrefs.Clientes.setBairro(rota.getBairro());
                mPrefs.Clientes.setMunicipio(rota.getMunicipio());
                mPrefs.Clientes.setUf(rota.getUf());
                mPrefs.Clientes.setCep(sonicUtils.stringToCep(rota.getCep()));
                mPrefs.Clientes.setEnderecoCompleto(rota.getLogradrouro() + ", " + rota.getBairro() + " - " + rota.getMunicipio() + "/" + rota.getUf());
                mPrefs.Rota.setAddressMap(sonicUtils.addressToMapSearch(rota.getLogradrouro() + "+" + rota.getCep() + "+" + rota.getMunicipio()));
                mPrefs.Rota.setCodigo(!rotaPessoal ? rota.getCodigo() : rota.getId());
                mPrefs.Rota.setPessoal(rotaPessoal);
                mPrefs.Rota.setItemPosition(position);
                mPrefs.Rota.setRefresh(false);
                Intent i = new Intent(mContext, sonicRotaDetalhe.class);
                mActivity.startActivityForResult(i, 0);

            }

        });


    }

    public void exibirDateOptionsRange(){

        DateRangePickerFragment d = new DateRangePickerFragment();
        d.setOnDateRangeSelectedListener(new DateRangePickerFragment.OnDateRangeSelectedListener() {
            @Override
            public void onDateRangeSelected(int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear, boolean isCanceled) {
                if(isCanceled){
                    if(mPrefs.Geral.getDataSearch()){
                        ((sonicRota)mContext).refreshFragments(0);
                        mPrefs.Geral.setDataSearch(false);
                    }

                }else{
                    mPrefs.Geral.setDataSearch(true);
                    mCalendarStart = Calendar.getInstance();
                    mCalendarStart.set(Calendar.YEAR, startYear);
                    mCalendarStart.set(Calendar.MONTH, startMonth);
                    mCalendarStart.set(Calendar.DAY_OF_MONTH, startDay);
                    mCalendarEnd = Calendar.getInstance();
                    mCalendarEnd.set(Calendar.YEAR, endYear);
                    mCalendarEnd.set(Calendar.MONTH, endMonth);
                    mCalendarEnd.set(Calendar.DAY_OF_MONTH, endDay);
                    mPrefs.Geral.setDataRange("BETWEEN "+ dataSearch.format(mCalendarStart.getTime())+" AND "+dataSearch.format(mCalendarEnd.getTime()));
                    mPrefs.Rota.setTermSearch(dataShow.format(mCalendarStart.getTime())+" à "+dataShow.format(mCalendarEnd.getTime()));
                    ((sonicRota)mContext).refreshFragments(0);
                }

            }
        });
        d.show(((AppCompatActivity)mActivity).getSupportFragmentManager(), "start");

    }

    private class RotaFilter extends Filter {

        private final sonicRotaAdapter adapter;

        private final List<sonicRotaHolder> originalList;

        private final List<sonicRotaHolder> filteredList;

        private RotaFilter(sonicRotaAdapter adapter, List<sonicRotaHolder> originalList) {
            super();
            this.adapter = adapter;
            this.originalList = new LinkedList<>(originalList);
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();

            if (constraint.length() == 0) {
                filteredList.addAll(originalList);

            } else {

                final String filterPattern = constraint.toString().toUpperCase().trim();

                for (final sonicRotaHolder rota : originalList) {
                    if(rota!=null)
                        if (mPrefs.Clientes.getClienteExibicao().equals("Nome Fantasia")
                                ? rota.getNomeFantasia().contains(filterPattern)
                                : rota.getRazaoSocial().contains(filterPattern) || String.valueOf(rota.getCodigo()).contains(constraint.toString())) {
                            filteredList.add(rota);
                        }
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.mTotalList.clear();
            adapter.mFilteredList.addAll((ArrayList<sonicRotaHolder>) results.values);
            adapter.notifyDataSetChanged();
        }
    }


}
