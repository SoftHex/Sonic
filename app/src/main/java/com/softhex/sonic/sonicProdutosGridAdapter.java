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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by Administrador on 11/08/2017.
 */

public class sonicProdutosGridAdapter extends RecyclerView.Adapter implements Filterable{

    private Context mContext;
    private List<sonicProdutosHolder> produtos;
    private List<sonicProdutosHolder> produtos_filtered;
    private prodFilter prodFilter;
    private sonicDatabaseCRUD DBC;
    private sonicUtils mUtil;
    private sonicPreferences mPrefs;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMdd");
    private String mDataAtual = mDateFormat.format(new Date());

    public class prodHolder extends RecyclerView.ViewHolder {

        TextView tvNome;
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
            tvNome = view.findViewById(R.id.tvNome);
            grupo = view.findViewById(R.id.tvGrupo);
            ivImagem = view.findViewById(R.id.ivImagem);
            ivNew = view.findViewById(R.id.ivNew);

            DBC = new sonicDatabaseCRUD(mContext);

            linearItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(v.getContext(), sonicProdutosDetalhe.class);
                    i.putExtra("PRODUTO_CODIGO", codigo);
                    i.putExtra("PRODUTO_NOME", tvNome.getText());
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
        this.mContext = ctx;
        this.mUtil = new sonicUtils(ctx);
        this.mPrefs = new sonicPreferences(ctx);
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

            View view = LayoutInflater.from(mContext).inflate(R.layout.sonic_layout_cards_grid, parent, false);
            prodHolder produtos = new prodHolder(view);
            return produtos;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        prodHolder holder = (prodHolder) viewHolder;
        sonicProdutosHolder prod = produtos.get(position);
        holder.codigo = String.valueOf(prod.getCodigo());
        holder.tvNome.setText(prod.getNome());

        String[] arrayNovo = mContext.getResources().getStringArray(R.array.prefProdutoNovoOptions);
        int diasDiff = mUtil.Data.dateDiffDay(prod.getDataCadastro(), mDataAtual);
        int dias = mPrefs.Produtos.getDiasNovo().equals(arrayNovo[0]) ? 30 :
                mPrefs.Produtos.getDiasNovo().equals(arrayNovo[1]) ? 60 :
                        mPrefs.Produtos.getDiasNovo().equals(arrayNovo[2]) ? 90 : 30;

        String[] arrayColumn = mContext.getResources().getStringArray(R.array.prefProdutoCatalogoOptions);
        int colunas = mPrefs.Produtos.getCatalogoColunas().equals(arrayColumn[0]) ? 2 :
                mPrefs.Produtos.getCatalogoColunas().equals(arrayColumn[1]) ? 3 :
                        mPrefs.Produtos.getCatalogoColunas().equals(arrayColumn[2]) ? 4 : 3;

        holder.ivNew.getLayoutParams().height = sonicUtils.intToDps(mContext, 120/colunas);
        holder.ivNew.setVisibility(diasDiff<=dias ? View.VISIBLE : View.GONE);
        holder.rlCatalogo.getLayoutParams().height = sonicUtils.intToDps(mContext,380/colunas);

        String fileJpg = prod.getCodigo()+".JPG";

        sonicGlide.glideImageView(mContext, holder.ivImagem, sonicUtils.checkImageJpg(sonicConstants.LOCAL_IMG_CATALOGO, fileJpg ,R.drawable.nophoto));

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

                    if (prod.getNome().contains(filterPattern) || prod.getCodigoAlternativo().contains(filterPattern) ||codigo.contains(filterPattern)) {
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
