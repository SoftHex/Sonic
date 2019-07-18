package com.softhex.sonic;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrador on 11/08/2017.
 */

public class sonicSistemaLogAdapter extends RecyclerView.Adapter implements Filterable{

    private Context ctx;
    private List<sonicSistemaLogHolder> erros;
    private List<sonicSistemaLogHolder> filtered_erros;
    private UserFilter userFilter;

    public class erroHolder extends RecyclerView.ViewHolder {

        TextView fabricante;
        TextView modelo;
        TextView versao;
        TextView atividade;
        TextView classe;
        TextView linha;
        TextView codigo;
        TextView log;
        TextView data;
        TextView hora;

        public erroHolder(View view) {
            super(view);

            fabricante = view.findViewById(R.id.fabricante);
            modelo = view.findViewById(R.id.modelo);
            versao = view.findViewById(R.id.versao);
            atividade = view.findViewById(R.id.atividade);
            classe = view.findViewById(R.id.classe);
            linha = view.findViewById(R.id.linha);
            log = view.findViewById(R.id.log);
            codigo = view.findViewById(R.id.codigo);
            data = view.findViewById(R.id.data);
            hora = view.findViewById(R.id.hora);

        }

    }

    public sonicSistemaLogAdapter(List<sonicSistemaLogHolder> erros, Context ctx) {

        this.erros = erros;
        this.filtered_erros = erros;
        this.ctx = ctx;
    }

    @Override
    public Filter getFilter() {
        if(userFilter == null)
            userFilter = new UserFilter(this, erros);
        return userFilter;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(ctx).inflate(R.layout.sonic_layout_cards_log, parent, false);
        erroHolder log = new erroHolder(view);
        return log;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        erroHolder holder = (erroHolder) viewHolder;
        holder.setIsRecyclable(false);
        sonicSistemaLogHolder log = erros.get(position);

        String newWord = log.getManufaturer().replace(String.valueOf(log.getManufaturer().charAt(0)), String.valueOf(log.getManufaturer().charAt(0)).toUpperCase());

        Resources res = ctx.getResources();

        holder.codigo.setText(res.getString(R.string.placeholderHashtag)+log.getCodigo());
        holder.fabricante.setText(res.getString(R.string.placeholderFabricante)+newWord);
        holder.modelo.setText(res.getString(R.string.placeholderModelo)+log.getModel());
        holder.versao.setText(res.getString(R.string.placeholderVersao)+log.getName()+" (SDK "+log.getSdk()+")");
        holder.atividade.setText(res.getString(R.string.placeholderAtividade)+log.getActivity());
        holder.classe.setText(res.getString(R.string.placeholderClasse)+log.getClasse());
        holder.linha.setText(res.getString(R.string.placeholderLinha)+log.getLine());
        holder.data.setText(log.getData());
        holder.hora.setText(log.getHora());
        holder.log.setText(res.getString(R.string.placeholderDetalhe)+log.getLog());

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return erros.size();
    }

    private static class UserFilter extends Filter {

        private final sonicSistemaLogAdapter adapter;

        private final List<sonicSistemaLogHolder> originalList;

        private final List<sonicSistemaLogHolder> filteredList;

        private UserFilter(sonicSistemaLogAdapter adapter, List<sonicSistemaLogHolder> originalList) {
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

                for (final sonicSistemaLogHolder erro : originalList) {
                    if (String.valueOf(erro.getCodigo()).contains(filterPattern) || erro.getLog().contains(filterPattern) || erro.getData().contains(filterPattern)) {
                        filteredList.add(erro);

                    }

                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.erros.clear();
            adapter.filtered_erros.addAll((ArrayList<sonicSistemaLogHolder>) results.values);
            adapter.notifyDataSetChanged();
        }
    }


}

