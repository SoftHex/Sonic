package com.softhex.sonic;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
    private int lastPosition = -1;
    private sonicDatabaseCRUD DBC;
    private sonicConstants myCons;
    private GradientDrawable shape;

    public class erroHolder extends RecyclerView.ViewHolder {


        TextView manufacturer;
        TextView model;
        TextView version;
        TextView activity;
        TextView classe;
        TextView codigo;
        TextView log;
        TextView data_hora;
        TextView hora;
        CardView card;
        View layout;
        RelativeLayout back;


        public erroHolder(View view) {
            super(view);

            card = (CardView)view.findViewById(R.id.card_view);
            //manufacturer = (TextView)view.findViewById(R.id.manufacturer);
            //model = (TextView)view.findViewById(R.id.model);
            //version = (TextView)view.findViewById(R.id.version);
            //activity = (TextView)view.findViewById(R.id.activity);
            //classe = (TextView)view.findViewById(R.id.classe);
            //log = (TextView)view.findViewById(R.id.erro);
            //codigo = (TextView)view.findViewById(R.id.codigo);
           // data_hora = (TextView)view.findViewById(R.id.data_hora);
            DBC = new sonicDatabaseCRUD(view.getContext());

        }
    }

    public sonicSistemaLogAdapter(List<sonicSistemaLogHolder> erros, Context ctx) {

        this.erros = erros;
        this.filtered_erros = erros;
        this.ctx = ctx;
    }

    public void updateList(List<sonicSistemaLogHolder> list){
        erros = list;
        list.clear();
        notifyDataSetChanged();

    }

    @Override
    public Filter getFilter() {
        if(userFilter == null)
            userFilter = new UserFilter(this, erros);
        return userFilter;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(ctx).inflate(R.layout.layout_cards_log_lista, parent, false);
        erroHolder log = new erroHolder(view);
        return log;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        erroHolder holder = (erroHolder) viewHolder;
        holder.setIsRecyclable(false);
        sonicSistemaLogHolder log = erros.get(position);

        //String newWord = erro.getManufaturer().replace(String.valueOf(erro.getManufaturer().charAt(0)), String.valueOf(erro.getManufaturer().charAt(0)).toUpperCase());

        /*holder.codigo.setText("#"+erro.getCodigo());
        holder.manufacturer.setText("Fabricante: " +newWord);
        holder.model.setText("Modelo: "+erro.getModel());
        holder.version.setText("Versão/SDK: "+erro.getName()+" (SDK "+erro.getSdk()+")");
        holder.activity.setText("Activity: "+erro.getActivity());
        holder.classe.setText("Classe/Método: "+erro.getClasse());
        holder.data_hora.setText(erro.getData()+"/"+erro.getHora());
        holder.log.setText("Detalhe: "+erro.getLog());*/


    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.fade_in);
            animation.setDuration(1000);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
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
                    if (erro.getLog().contains(filterPattern) || erro.getData().contains(filterPattern)) {
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

