package com.softhex.sonic;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by Administrador on 11/08/2017.
 */

public class sonicProdutosGridAdapter extends RecyclerView.Adapter implements Filterable{

    private Context ctx;
    private List<sonicProdutosHolder> produtos;
    private List<sonicProdutosHolder> produtos_filtered;
    private prodFilter prodFilter;
    private sonicDatabaseCRUD DBC;

    public class prodHolder extends RecyclerView.ViewHolder {

        TextView tvDescricao;
        String codigo;
        TextView grupo;
        ImageView ivImagem, ivNew;
        LinearLayout llDescription, linearNew;
        RelativeLayout rlCatalogo, linearItem;
        String status;

        public prodHolder(View view) {
            super(view);

            rlCatalogo = view.findViewById(R.id.relativeCatalogo);
            linearItem = view.findViewById(R.id.linearItem);
            linearNew = view.findViewById(R.id.linearNew);
            llDescription = view.findViewById(R.id.llDescricao);
            tvDescricao = view.findViewById(R.id.tvDescricao);
            grupo = view.findViewById(R.id.tvGrupo);
            ivImagem = view.findViewById(R.id.ivImagem);
            ivNew = view.findViewById(R.id.ivNew);

            DBC = new sonicDatabaseCRUD(ctx);

            linearItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(v.getContext(), sonicProdutosDetalhe.class);
                    i.putExtra("PRODUTO_CODIGO", codigo);
                    i.putExtra("PRODUTO_NOME", tvDescricao.getText());
                    i.putExtra("PRODUTO_GRUPO", grupo.getText());
                    i.putExtra("PRODUTO_STATUS", status);
                    v.getContext().startActivity(i);

                }


            });

        }
    }

    public sonicProdutosGridAdapter(List<sonicProdutosHolder> produto, Context ctx) {

        this.produtos = produto;
        this.produtos_filtered = produto;
        this.ctx = ctx;
    }

    public void updateList(List<sonicProdutosHolder> list){
        produtos = list;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        if(prodFilter == null)
            prodFilter = new prodFilter(this, produtos);
        return prodFilter;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(ctx).inflate(R.layout.sonic_layout_cards_grid, parent, false);
            prodHolder produtos = new prodHolder(view);
            return produtos;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        prodHolder holder = (prodHolder) viewHolder;
        sonicProdutosHolder prod = produtos.get(position);
        holder.codigo = String.valueOf(prod.getCodigo());
        holder.tvDescricao.setText(prod.getDescricao());
        holder.status = prod.getStatus();
        holder.linearNew.setVisibility((prod.getStatus().equals("NOVO")) ? View.VISIBLE : View.GONE);
        //holder.grupo.setText(prod.getGrupo());
        //TODO WITH PREFERENCES
        int qtd = 3;

        holder.ivNew.getLayoutParams().height = sonicUtils.intToDps(ctx, 120/qtd);
        holder.rlCatalogo.getLayoutParams().height = sonicUtils.intToDps(ctx,380/qtd);

        String fileJpg = String.valueOf(prod.getCodigo())+".JPG";

        sonicGlide.glideImageView(ctx, holder.ivImagem, sonicUtils.checkImageJpg(sonicConstants.LOCAL_IMG_CATALOGO, fileJpg ,R.drawable.nophoto));

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
        }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return produtos == null ? 0 : produtos.size();
    }

    private static class prodFilter extends Filter {
        private final sonicProdutosGridAdapter adapter;

        private final List<sonicProdutosHolder> originalList;

        private final List<sonicProdutosHolder> filteredList;

        private prodFilter(sonicProdutosGridAdapter adapter, List<sonicProdutosHolder> originalList) {
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



                for (final sonicProdutosHolder prod : originalList) {

                    String codigo = String.valueOf(prod.getCodigo());

                    if (prod.getDescricao().contains(filterPattern) || prod.getCodigoAlternativo().contains(filterPattern) ||codigo.contains(filterPattern)) {
                        filteredList.add(prod);

                    }

                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.produtos.clear();
            adapter.produtos_filtered.addAll((ArrayList<sonicProdutosHolder>) results.values);
            adapter.notifyDataSetChanged();
        }
    }

}
