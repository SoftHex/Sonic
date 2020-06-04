package com.softhex.sonic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.StringDef;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.softhex.materialdialog.PromptDialog;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class sonicRotaAdapter extends RecyclerView.Adapter<sonicRotaAdapter.ViewHolder> implements Filterable {

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

    private Context myCtx;
    private Activity mActivity;
    private List<sonicRotaHolder> rotas;
    private List<sonicRotaHolder> filteredRotas;
    private List<sonicRotaHolder> alteredRotas;
    private RotaFilter rotaFilter;
    private Boolean nFantasia;
    private sonicPreferences mPrefs;
    private sonicUtils mUtils;
    private RecyclerView mRecycler;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCodigo;
        TextView tvNome;
        TextView tvEndereco;
        TextView tvStatus;
        TextView tvObservacao;
        TextView tvAtendente;
        TextView tvDataHora;
        ImageView ivImagem;
        TextView tvSituacao;
        TextView tvLetra;
        TextView tvDuracao;
        LinearLayout linearItem;

        ViewHolder(View view) {
            super(view);
            linearItem = view.findViewById(R.id.linearItem);
            tvCodigo = view.findViewById(R.id.tvCodigo);
            tvNome = view.findViewById(R.id.tvNome);
            tvLetra = view.findViewById(R.id.tvLetra);
            ivImagem = view.findViewById(R.id.ivImagem);
            tvAtendente = view.findViewById(R.id.tvAtendente);
            tvStatus = view.findViewById(R.id.tvStatus);
            tvSituacao = view.findViewById(R.id.tvSituacao);
            tvEndereco = view.findViewById(R.id.tvEndereco);
            tvDataHora = view.findViewById(R.id.tvDataHora);
            tvDuracao = view.findViewById(R.id.tvDuracao);
            tvObservacao = view.findViewById(R.id.tvObservacao);

        }
    }

    sonicRotaAdapter(List<sonicRotaHolder> rotas, Context context, Activity activity, RecyclerView mRecycler) {
        this.myCtx = context;
        this.filteredRotas = rotas;
        this.mActivity = activity;
        this.rotas = rotas;
        this.mUtils = new sonicUtils(myCtx);
        this.mPrefs = new sonicPreferences(myCtx);
        this.mRecycler = mRecycler;
        this.nFantasia =  mPrefs.Clientes.getClienteExibicao().equals("Nome Fantasia") ? true : false;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cards_rota_itens, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        sonicRotaHolder rota = rotas.get(position);
        holder.setIsRecyclable(false);
        String cliNomeExibicao = nFantasia ? rota.getNomeFantasia() : rota.getRazaoSocial();

        holder.tvCodigo.setText("#"+rota.getCodigo());
        holder.tvNome.setText(cliNomeExibicao);
        holder.tvAtendente.setText("Responsável: "+rota.getAtendente());
        holder.tvEndereco.setText(rota.getEnderecoCompleto());
        holder.tvDataHora.setText("Data Prevista: "+mUtils.Data.dataFotmatadaBR(rota.getDataAgendamento())+" às "+mUtils.Data.horaFotmatadaBR(rota.getHoraAgendamento()));
        holder.tvObservacao.setText("Observação: "+rota.getObservacao());
        holder.tvDuracao.setText(rota.getHoraFim().equals("") ? "Duração:" : "Duração: "+sonicUtils.getDifferenceTime(rota.getHoraInicio(), rota.getHoraFim()));

        File f = sonicFile.searchFile(sonicConstants.LOCAL_IMG_CLIENTES, rota.getCodigoCliente());

        if(f.exists()){
            holder.ivImagem.setVisibility(View.VISIBLE);
            holder.tvLetra.setVisibility(View.GONE);
            Glide.with(myCtx)
                    .load(f)
                    .circleCrop()
                    .override(100,100)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                    .into(holder.ivImagem);


        }else{

            holder.tvLetra.setText(String.valueOf(cliNomeExibicao.charAt(0)).toUpperCase());

        }
        holder.tvStatus.setBackground(
                rota.getStatus()==Status.NAO_INICIADO ?
                    myCtx.getResources().getDrawable(R.drawable.status_nao_iniciado) :
                        rota.getStatus()==Status.EM_ATENDIMENTO ?
                            myCtx.getResources().getDrawable(R.drawable.status_em_atendimento) :
                                rota.getStatus()==Status.CONCLUIDO ?
                                    myCtx.getResources().getDrawable(R.drawable.status_concluido) :
                                        myCtx.getResources().getDrawable(R.drawable.status_cancelado));

        holder.tvStatus.setText(
                rota.getStatus()==Status.NAO_INICIADO ? StatusText.NAO_INICIADO :
                rota.getStatus()==Status.EM_ATENDIMENTO ? StatusText.EM_ATENDIMENTO :
                        rota.getStatus()==Status.CONCLUIDO ? StatusText.CONCLUIDO :
                        StatusText.CANCELADO);

        if(rota.getStatus()==Status.CONCLUIDO){
            holder.tvSituacao.setVisibility(View.VISIBLE);
            switch (rota.getSituacao()){
                case Situacao.POSITIVADO:
                    holder.tvSituacao.setText(SituacaoText.POSITIVADO);
                    holder.tvSituacao.setBackground(myCtx.getResources().getDrawable(R.drawable.situacao_positivado));
                    break;
                case Situacao.NEGATIVADO:
                    holder.tvSituacao.setText(SituacaoText.NEGATIVADO);
                    holder.tvSituacao.setBackground(myCtx.getResources().getDrawable(R.drawable.situacao_negativado));
                    break;
            }

        }

        holder.linearItem.setOnClickListener((View v)-> {
            if(mPrefs.Rota.getEmAtendimento() && rota.getStatus()==Status.NAO_INICIADO){

                new PromptDialog(myCtx)
                        .setDialogType(PromptDialog.DIALOG_TYPE_INFO)
                        .setAnimationEnable(true)
                        .setTitleText(R.string.msgAtencao)
                        .setContentText(R.string.rotaEmAtendimento)
                        .setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                            @Override
                            public void onClick(PromptDialog dialog){
                                dialog.dismiss();
                                mRecycler.smoothScrollToPosition(mPrefs.Rota.getItemPosition());
                            }
                        }).show();
                /*new AlertDialog.Builder(myCtx)
                        .setTitle("Atenção")
                        .setMessage("Existe uma rota em atendimento. \nFinalize a rota para iniciar a próxima.")
                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                mRecycler.smoothScrollToPosition(mPrefs.Rota.getItemPosition());
                                Handler h = new Handler();
                                h.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //View x = mRecycler.getLayoutManager().findViewByPosition(mPrefs.Rota.getItemPosition());
                                        //Animation myBlinkAnimation = AnimationUtils.loadAnimation(myCtx, R.anim.blink);
                                        //x.startAnimation(myBlinkAnimation);
                                    }
                                },500);

                                //Animation blinkanimation;
                                //blinkanimation= new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
                                //blinkanimation.setDuration(300); // duration
                                //blinkanimation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
                                //blinkanimation.setRepeatCount(3); // Repeat animation infinitely
                                //blinkanimation.setRepeatMode(Animation.REVERSE);
                            }
                        })
                        // A null listener allows the button to dismiss the dialog and take no further action.
                        //.setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();*/


            }else{

                mPrefs.Clientes.setId(rota.getCodigoCliente());
                mPrefs.Clientes.setNome(cliNomeExibicao);
                mPrefs.Clientes.setLogradouro(rota.getLogradrouro());
                mPrefs.Clientes.setBairro(rota.getBairro());
                mPrefs.Clientes.setMunicipio(rota.getMunicipio());
                mPrefs.Clientes.setUf(rota.getUf());
                mPrefs.Clientes.setCep(sonicUtils.stringToCep(rota.getCep()));
                mPrefs.Clientes.setEnderecoCompleto(rota.getLogradrouro()+", "+rota.getBairro()+" - "+rota.getMunicipio()+"/"+rota.getUf());
                mPrefs.Rota.setAddressMap(sonicUtils.addressToMapSearch(rota.getLogradrouro()+"+"+rota.getCep()+"+"+rota.getMunicipio()));
                mPrefs.Rota.setCodigo(rota.getCodigo());
                mPrefs.Rota.setItemPosition(position);
                mPrefs.Rota.setRefresh(false);
                Intent i = new Intent(mActivity, sonicRotaDetalhe.class);
                mActivity.startActivityForResult(i, 1);
            }

        });

    }

    private Void scrollToPosition(){
        mRecycler.smoothScrollToPosition(mPrefs.Rota.getItemPosition());
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.layout_cards_rota_itens;

    }

    @Override
    public int getItemCount() {
        return rotas.size();
    }

    @Override
    public Filter getFilter() {
        if(rotaFilter == null)
            rotaFilter = new RotaFilter(this, rotas);
        return rotaFilter;
    }

    private static class RotaFilter extends Filter {

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
                    if (String.valueOf(rota.getCodigoCliente()).contains(filterPattern) || rota.getRazaoSocial().contains(filterPattern) || rota.getNomeFantasia().contains(filterPattern)|| rota.getAtendente().contains(filterPattern)) {
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
            adapter.rotas.clear();
            adapter.filteredRotas.addAll((ArrayList<sonicRotaHolder>) results.values);
            adapter.notifyDataSetChanged();
        }
    }


}
