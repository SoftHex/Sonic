package com.softhex.sonic;

import android.content.Context;
import android.content.res.Resources;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrador on 11/08/2017.
 */

public class sonicMainAdapter extends RecyclerView.Adapter implements Filterable{

    private Context ctx;
    private List<sonicSistemaLogHolder> erros;
    private List<sonicSistemaLogHolder> filtered_erros;
    private UserFilter userFilter;

    public class erroHolder extends RecyclerView.ViewHolder {



        public erroHolder(View view) {
            super(view);


        }

    }

    public sonicMainAdapter(List<sonicSistemaLogHolder> erros, Context ctx) {

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

        View view = LayoutInflater.from(ctx).inflate(R.layout.sonic_layout_cards_ultimos_pedidos, parent, false);
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

        private final sonicMainAdapter adapter;

        private final List<sonicSistemaLogHolder> originalList;

        private final List<sonicSistemaLogHolder> filteredList;

        private UserFilter(sonicMainAdapter adapter, List<sonicSistemaLogHolder> originalList) {
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

