package com.softhex.sonic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrador on 11/08/2017.
 */

public class sonicProdutosListaAdapter extends RecyclerView.Adapter implements Filterable{

    private Context mContext;
    private Activity mActivity;
    private AppCompatActivity mCompatActitvity;
    private List<sonicProdutosHolder> produtos;
    private List<sonicProdutosHolder> produtos_filtered;
    private prodFilter prodFilter;
    private sonicDatabaseCRUD DBC;
    private sonicUtils mUtil;
    private sonicPreferences mPrefs;
    private sonicConstants myCons;
    private final int VIEW_TYPE_ITEM = 0;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMdd");
    private String mDataAtual = mDateFormat.format(new Date());
    private int dias, diasDiff;

    public class prodHolder extends RecyclerView.ViewHolder {

        TextView tvNome;
        int codigo;
        TextView codigoFull;
        TextView tvGrupo;
        String status;
        ImageView mImage;
        TextView tvLetra;
        CardView card;
        String dataCadastro;
        LinearLayout llItem, linearNew;


        public prodHolder(View view) {
            super(view);

            card = view.findViewById(R.id.cardView);
            llItem = view.findViewById(R.id.linearItem);
            linearNew = view.findViewById(R.id.linearNew);
            tvNome = view.findViewById(R.id.tvNome);
            tvGrupo = view.findViewById(R.id.tvGrupo);
            codigoFull = view.findViewById(R.id.tvDetalhe);
            tvLetra = view.findViewById(R.id.tvLetra);
            mImage = view.findViewById(R.id.ivImagem);

            DBC = new sonicDatabaseCRUD(mContext);

            llItem.setOnClickListener((View v) -> {

                mPrefs.Produtos.setProdutoId(codigo);
                mPrefs.Produtos.setProdutoNome(tvNome.getText().toString());
                mPrefs.Produtos.setProdutoGrupo(tvGrupo.getText().toString());
                mPrefs.Produtos.setProdutoDataCadastro(dataCadastro);
                Intent i = new Intent(v.getContext(), sonicProdutosDetalhe.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        (Activity)mContext, mImage, "imagemTransition");
                v.getContext().startActivity(i, options.toBundle());

            });

        }
    }

    public sonicProdutosListaAdapter(List<sonicProdutosHolder> produto, Context mContext) {

        myCons = new sonicConstants();
        this.produtos = produto;
        this.produtos_filtered = produto;
        this.mContext = mContext;
        this.mUtil = new sonicUtils(mContext);
        this.mPrefs = new sonicPreferences(mContext);
    }

    public void updateList(){
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
        View view;
        if(viewType == VIEW_TYPE_ITEM){
            view = LayoutInflater.from(mContext).inflate(R.layout.sonic_layout_cards_list, parent, false);
            return new prodHolder(view);
        }else{
            view = LayoutInflater.from(mContext).inflate(R.layout.sonic_layout_cards_list_shimmer, parent, false);
            return new prodHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        prodHolder holder = (prodHolder) viewHolder;
        sonicProdutosHolder prod = produtos.get(position);

        holder.tvNome.setText(prod.getNome());
        //holder.tvNome.setTextColor(Color.parseColor(prod.getSituacaoCor()));
        holder.codigoFull.setText("CÓD.: "+prod.getCodigo()+" / REFERÊNCIA: "+prod.getCodigoAlternativo());
        holder.codigo = prod.getCodigo();
        holder.tvGrupo.setText(prod.getGrupo());
        holder.dataCadastro = prod.getDataCadastro();
        String[] array = mContext.getResources().getStringArray(R.array.prefProdutoNovoOptions);
        diasDiff = mUtil.Data.dateDiffDay(holder.dataCadastro, mDataAtual);
        dias = mPrefs.Produtos.getDiasNovo().equals(array[0]) ? 30 :
                mPrefs.Produtos.getDiasNovo().equals(array[1]) ? 60 :
        mPrefs.Produtos.getDiasNovo().equals(array[2]) ? 90 : 30;

        holder.linearNew.setVisibility(diasDiff<=dias ? View.VISIBLE : View.GONE);

        File fileJpg = new File(Environment.getExternalStorageDirectory(), myCons.LOCAL_IMG_CATALOGO +String.valueOf(prod.getCodigo())+".JPG");

        if(fileJpg.exists()){

            Glide.with(mContext)
                    .load(fileJpg)
                    .circleCrop()
                    .apply(new RequestOptions().override(100,100))
                    .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                    .into(holder.mImage);

            //sonicGlide.glideFile(mContext, holder.mImage, fileJpg);
            //holder.mImage.setVisibility(View.VISIBLE);
            holder.tvLetra.setVisibility(View.GONE);


        }else {
            //holder.mImage.setVisibility(View.GONE);
            holder.tvLetra.setVisibility(View.VISIBLE);
            holder.tvLetra.setText(String.valueOf(prod.getNome().charAt(0)));
            //holder.tvLetra.setTypeface(Typeface.DEFAULT_BOLD);
        }

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
        return produtos.size();
    }

    private static class prodFilter extends Filter {

        private final sonicProdutosListaAdapter adapter;

        private final List<sonicProdutosHolder> originalList;

        private final List<sonicProdutosHolder> filteredList;

        private prodFilter(sonicProdutosListaAdapter adapter, List<sonicProdutosHolder> originalList) {
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
                sonicConstants.SEARCH_COUNT = originalList.size();
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
