package com.softhex.sonic;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
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

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class sonicRotaAdapter2 extends RecyclerView.Adapter<sonicRotaAdapter2.ViewHolder> implements Filterable {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({Tipo.PADRAO, Tipo.AGENDAMENTO, Tipo.REAGENDAMENTO})
    public @interface Tipo {
        int PADRAO = 1;
        int AGENDAMENTO = 2;
        int REAGENDAMENTO = 3;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({Status.NAO_INICIADO, Status.EM_ATENDIMENTO, Status.CONCLUIDO})
    public @interface Status{
        int NAO_INICIADO = 1;
        int EM_ATENDIMENTO = 2;
        int CONCLUIDO = 3;
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({StatusText.NAO_INICIADO, StatusText.EM_ATENDIMENTO, StatusText.CONCLUIDO})
    public @interface StatusText{
        String NAO_INICIADO = "Não Iniciado";
        String EM_ATENDIMENTO = "Em Atendimento";
        String CONCLUIDO = "Concluído";
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({Situacao.POSITIVADO, Situacao.NEGATIVADO, Situacao.CANCELADO})
    public @interface Situacao{
        int POSITIVADO = 1;
        int NEGATIVADO = 2;
        int CANCELADO = 3;
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({SituacaoText.POSITIVADO, SituacaoText.NEGATIVADO, SituacaoText.CANCELADO})
    public @interface SituacaoText{
        String POSITIVADO = "Positivado";
        String NEGATIVADO = "Negativado";
        String CANCELADO = "Cancelado";
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ItemText.INICIAR, ItemText.CONCLUIR, ItemText.CANCELAR})
    public @interface ItemText{
        String INICIAR = "Iniciar Atendimento";
        String CONCLUIR = "Concluir Atendimento";
        String CANCELAR = "Cancelar Atendimento";
    }

    private Context myCtx;
    private List<sonicRotaHolder> rotas;
    private List<sonicRotaHolder> filteredRotas;
    private List<sonicRotaHolder> alteredRotas;
    private RotaFilter rotaFilter;
    private Boolean nFantasia;
    private sonicPreferences mPref;
    private sonicUtils mUtils;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNome;
        TextView tvEndereco;
        TextView tvStatus;
        TextView tvObservacao;
        TextView tvAtendente;
        TextView tvDataHora;
        ImageView ivImagem;
        TextView tvPositivado;
        TextView tvNegativado;
        TextView tvCancelado;
        TextView tvLetra;
        LinearLayout linearItem;

        ViewHolder(View view) {
            super(view);
            linearItem = view.findViewById(R.id.linearItem);
            tvNome = view.findViewById(R.id.tvNome);
            tvLetra = view.findViewById(R.id.tvLetra);
            ivImagem = view.findViewById(R.id.ivImagem);
            tvAtendente = view.findViewById(R.id.tvAtendente);
            tvStatus = view.findViewById(R.id.tvStatus);
            tvPositivado = view.findViewById(R.id.tvPositivado);
            tvNegativado = view.findViewById(R.id.tvNegativado);
            tvCancelado = view.findViewById(R.id.tvCancelado);
            tvEndereco = view.findViewById(R.id.tvEndereco);
            tvDataHora = view.findViewById(R.id.tvDataHora);
            tvObservacao = view.findViewById(R.id.tvObservacao);

        }
    }

    sonicRotaAdapter2(List<sonicRotaHolder> rotas, Activity mActivity) {
        this.myCtx = mActivity;
        this.filteredRotas = rotas;
        this.rotas = rotas;
        mUtils = new sonicUtils(myCtx);
        this.mPref = new sonicPreferences(myCtx);
        this.nFantasia =  mPref.Clientes.getClienteExibicao().equals("Nome Fantasia") ? true : false;
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
        String cliNome = nFantasia ? rota.getNomeFantasia() : rota.getRazaoSocial();

        holder.tvNome.setText(cliNome);
        holder.tvAtendente.setText("Responsável: "+rota.getAtendente());
        holder.tvEndereco.setText(rota.getEnderecoCompleto());
        holder.tvDataHora.setText("Data Prevista: "+mUtils.Data.dataFotmatadaBarra(rota.getDataAgendamento())+" Ás "+mUtils.Data.horaFotmatadaBR(rota.getHoraAgendamento()));
        holder.tvObservacao.setText("Observação: "+rota.getObservacao());

        File file = new File(Environment.getExternalStorageDirectory(), sonicConstants.LOCAL_IMG_CLIENTES + rota.getCodigoCliente() + ".JPG");

        if(file.exists()){
            holder.ivImagem.setVisibility(View.VISIBLE);
            holder.tvLetra.setVisibility(View.GONE);
            Glide.with(myCtx)
                    .load(file)
                    .circleCrop()
                    .override(100,100)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                    .into(holder.ivImagem);


        }else{

            holder.tvLetra.setText(String.valueOf(cliNome.charAt(0)).toUpperCase());

        }
        holder.tvStatus.setBackground(rota.getStatus()==Status.NAO_INICIADO ?
                myCtx.getResources().getDrawable(R.drawable.status_nao_iniciado) :
                    rota.getStatus()==Status.EM_ATENDIMENTO ? myCtx.getResources().getDrawable(R.drawable.status_em_atendimento) :
                        myCtx.getResources().getDrawable(R.drawable.status_concluido));

        holder.tvStatus.setText(rota.getStatus()==Status.NAO_INICIADO ? StatusText.NAO_INICIADO :
                rota.getStatus()==Status.EM_ATENDIMENTO ? StatusText.EM_ATENDIMENTO :
                        StatusText.CONCLUIDO);
        switch (rota.getSituacao()){
            case Situacao.POSITIVADO:
                holder.tvNegativado.setVisibility(View.GONE);
                holder.tvCancelado.setVisibility(View.GONE);
                break;
            case Situacao.NEGATIVADO:
                holder.tvPositivado.setVisibility(View.GONE);
                holder.tvCancelado.setVisibility(View.GONE);
                break;
            case Situacao.CANCELADO:
                holder.tvPositivado.setVisibility(View.GONE);
                holder.tvNegativado.setVisibility(View.GONE);
                    break;
             default:
                 holder.tvPositivado.setVisibility(View.GONE);
                 holder.tvNegativado.setVisibility(View.GONE);
                 holder.tvCancelado.setVisibility(View.GONE);
        }

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

        private final sonicRotaAdapter2 adapter;

        private final List<sonicRotaHolder> originalList;

        private final List<sonicRotaHolder> filteredList;

        private RotaFilter(sonicRotaAdapter2 adapter, List<sonicRotaHolder> originalList) {
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
