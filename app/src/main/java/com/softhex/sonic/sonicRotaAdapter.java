package com.softhex.sonic;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class sonicRotaAdapter extends RecyclerView.Adapter implements Filterable {


    private Context myCtx;
    private List<sonicRotaHolder> rotas;
    private List<sonicRotaHolder> filteredRotas;
    private RotaFilter rotaFilter;
    private sonicDatabaseCRUD DBC;
    private sonicConstants myCons;
    private GradientDrawable shape;
    private SharedPreferences myPrefs;

    public class rotaHolder extends RecyclerView.ViewHolder{

        TextView cliente;

        public rotaHolder(@NonNull View itemView) {
            super(itemView);

            cliente = itemView.findViewById(R.id.nome);

        }
    }

    public sonicRotaAdapter(List<sonicRotaHolder> rotas, Context myCtx) {
        myPrefs = PreferenceManager.getDefaultSharedPreferences(myCtx);
        myCons = new sonicConstants();
        this.rotas = rotas;
        this.filteredRotas = rotas;
        this.myCtx = myCtx;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(myCtx,R.layout.layout_cards_rota,null);
        rotaHolder rotas = new rotaHolder(view);
        return rotas;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        rotaHolder holder = (rotaHolder) viewHolder;
        holder.setIsRecyclable(false);
        sonicRotaHolder rota = rotas.get(position);

        holder.cliente.setText(rota.getData());

    }

    @Override
    public Filter getFilter() {
        if(rotaFilter == null)
            rotaFilter = new RotaFilter(this, rotas);
        return rotaFilter;

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
        return rotas.size();
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
                    if (rota.getRazaoSocial().contains(filterPattern) || rota.getNomeFantasia().contains(filterPattern)|| String.valueOf(rota.getOrdem()).contains(filterPattern)) {
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
