package com.softhex.sonic;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.StringDef;
import androidx.fragment.app.Fragment;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class sonicRotaAdapter2 extends RecyclerView.Adapter<sonicRotaAdapter2.ViewHolder> implements Filterable {

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
    private sonicDatabaseCRUD DBC;
    private sonicConstants myCons;
    private GradientDrawable shape;
    private SharedPreferences myPrefs;
    private Activity mActivity;
    private Fragment mFragment;
    private sonicDatabaseCRUD mDataBase;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNome;
        TextView tvEndereco;
        TextView tvStatus;
        TextView tvOptions;
        TextView tvObservacao;
        TextView tvAtendente;
        TextView tvDataHora;
        TextView tvSituacao;
        CircleImageView ivImagem;
        TextView tvLetra;
        LinearLayout linearItem;

        ViewHolder(View view) {
            super(view);
            linearItem = view.findViewById(R.id.linearItem);
            //timelineView = view.findViewById(R.id.tlRota);
            //tvOptions = view.findViewById(R.id.tvOptions);
            tvNome = view.findViewById(R.id.tvNome);
            tvLetra = view.findViewById(R.id.tvLetra);
            ivImagem = view.findViewById(R.id.ivImagem);
            tvAtendente = view.findViewById(R.id.tvAtendente);
            tvStatus = view.findViewById(R.id.tvStatus);
            tvSituacao = view.findViewById(R.id.tvSituacao);
            tvEndereco = view.findViewById(R.id.tvEndereco);
            tvDataHora = view.findViewById(R.id.tvDataHora);
            tvObservacao = view.findViewById(R.id.tvObservacao);

        }
    }

    sonicRotaAdapter2(List<sonicRotaHolder> rotas, sonicRotaClientes fragment) {
        this.myCtx = fragment.getActivity().getBaseContext();
        if(sonicConstants.ROTA_ALTERADA){
            this.rotas = alteredRotas;
        }else{
            this.rotas = rotas;
        }
        this.filteredRotas = rotas;
        this.mActivity = fragment.getActivity();
        this.mFragment = fragment;
        this.mDataBase = new sonicDatabaseCRUD(myCtx);
    }

    public void updateAdapter(List<sonicRotaHolder> rotas){
        this.alteredRotas = rotas;
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
        sonicUtils utils = new sonicUtils(myCtx);

        holder.tvNome.setText(rota.getRazaoSocial());
        holder.tvAtendente.setText("Responsável: "+rota.getAtendente());
        holder.tvEndereco.setText(rota.getEnderecoCompleto());
        holder.tvDataHora.setText("Data Prevista: "+utils.Data.dataFotmatadaBarra(rota.getDataAgendamento())+" Ás "+utils.Data.horaFotmatadaBR(rota.getHoraAgendamento()));
        holder.tvObservacao.setText("Observação: "+rota.getObservacao());
        /*holder.timelineView.setMarker(
                rota.getTipo()==Tipo.PADRAO ?
                        myCtx.getResources().getDrawable(R.mipmap.ic_map_grey600_36dp) :
                        rota.getTipo()==Tipo.AGENDAMENTO ?
                                myCtx.getResources().getDrawable(R.mipmap.ic_phone_grey600_36dp) : myCtx.getResources().getDrawable(R.mipmap.ic_phone_return_grey600_36dp));

        if(rota.getStatus()==2){
            sonicConstants.ROTA_EM_ATENDIMENTO = true;
        }*/

        File file = new File(Environment.getExternalStorageDirectory(), myCons.LOCAL_IMG_CLIENTES + rota.getCodigoCliente() + "_1.JPG");

        if(file.exists()){
            holder.ivImagem.setVisibility(View.VISIBLE);
            holder.tvLetra.setVisibility(View.GONE);
            Glide.with(myCtx)
                    .load(file)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                    .into(holder.ivImagem);


        }else{

            holder.tvLetra.setText("A");

        }

        String statusText = rota.getStatus()==Status.NAO_INICIADO ? StatusText.NAO_INICIADO :
                rota.getStatus()==Status.EM_ATENDIMENTO ? StatusText.EM_ATENDIMENTO :
                        rota.getStatus()==Status.CONCLUIDO ? StatusText.CONCLUIDO :
                                StatusText.CANCELADO;
        Drawable statusDrawable = rota.getStatus()==Status.NAO_INICIADO  ?  myCtx.getResources().getDrawable(R.drawable.status_nao_iniciado) :
                rota.getStatus()==Status.EM_ATENDIMENTO ? myCtx.getResources().getDrawable(R.drawable.status_em_atendimento) :
                        rota.getStatus()==Status.CONCLUIDO ? myCtx.getResources().getDrawable(R.drawable.status_concluido) :
                                myCtx.getResources().getDrawable(R.drawable.status_cancelado);
        int statusColor = rota.getStatus()==Status.NAO_INICIADO  ? myCtx.getResources().getColor(R.color.colorPrimaryBlue) :
                rota.getStatus()==Status.EM_ATENDIMENTO ? myCtx.getResources().getColor(R.color.colorPrimaryOrange) :
                        rota.getStatus()==Status.CONCLUIDO ? myCtx.getResources().getColor(R.color.colorPrimaryGreen) :
                                myCtx.getResources().getColor(R.color.colorPrimaryRed);

        int statusOptions = rota.getStatus()==Status.NAO_INICIADO  ? View.VISIBLE :
                rota.getStatus()==Status.EM_ATENDIMENTO ? View.VISIBLE :
                        rota.getStatus()==Status.CONCLUIDO ? View.GONE :
                                View.GONE;
        int statusSituacao = rota.getStatus()==Status.NAO_INICIADO  ? View.GONE :
                rota.getStatus()==Status.EM_ATENDIMENTO ? View.GONE :
                        rota.getStatus()==Status.CONCLUIDO ? View.VISIBLE :
                                View.GONE;

        holder.tvStatus.setText(statusText);
        holder.tvStatus.setBackground(statusDrawable);

        //holder.tvOptions.setVisibility(statusOptions);
        //holder.tvSituacao.setVisibility(statusSituacao);

        //holder.timelineView.setEndLineColor(statusColor, holder.getItemViewType());
        //holder.timelineView.setMarkerColor(statusColor);

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
