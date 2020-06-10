package com.softhex.sonic;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
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

    private static final int VIEW_PROG = 123456789;
    private static final int VIEW_ITEM = 987654321;
    private Context mContext;
    private List<sonicProdutosHolder> mTotalList;
    private List<sonicProdutosHolder> mFilteredList;
    private List<sonicProdutosHolder> mPartialList;
    private prodFilter prodFilter;
    private sonicDatabaseCRUD mData;
    private sonicUtils mUtil;
    private sonicPreferences mPrefs;
    private sonicConstants myCons;
    private RecyclerView mRecycler;
    private boolean isLoading = false;
    private final int VIEW_TYPE_ITEM = 0;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMdd");
    private String mDataAtual = mDateFormat.format(new Date());
    private LinearLayoutManager linearLayoutManager;
    private int dias, diasDiff;

    public class prodHolder extends RecyclerView.ViewHolder {

        int codigo;
        TextView tvLinha1;
        TextView tvLinha2;
        TextView tvLinha3;
        String status;
        ImageView mImage;
        TextView tvLetra;
        CardView card;
        String dataCadastro;
        LinearLayout linearItem, linearNew;


        public prodHolder(View view) {
            super(view);

            card = view.findViewById(R.id.cardView);
            linearItem = view.findViewById(R.id.linearItem);
            linearNew = view.findViewById(R.id.linearNew);
            tvLinha1 = view.findViewById(R.id.tvLinha1);
            tvLinha2 = view.findViewById(R.id.tvLinha2);
            tvLinha3 = view.findViewById(R.id.tvLinha3);
            tvLetra = view.findViewById(R.id.tvLetra);
            mImage = view.findViewById(R.id.ivImagem);
            mData = new sonicDatabaseCRUD(mContext);

        }
    }

    public sonicProdutosListaAdapter(List<sonicProdutosHolder> produto, Context context, RecyclerView recycler) {

        this.myCons = new sonicConstants();
        this.mTotalList = produto;
        this.mFilteredList = produto;
        this.mContext = context;
        this.mUtil = new sonicUtils(context);
        this.mPrefs = new sonicPreferences(context);
        this.mRecycler = recycler;

        if(mPrefs.Geral.getListagemCompleta()){

            mPartialList = mTotalList;

        }else{

            mPartialList = new ArrayList();

            if(produto.size()< sonicConstants.TOTAL_ITENS_LOAD){
                for(int i = 0; i < produto.size(); i++){
                    mPartialList.add(produto.get(i));
                }
            }else{
                for(int i = 0; i<sonicConstants.TOTAL_ITENS_LOAD-1; i++){
                    mPartialList.add(produto.get(i));
                }
            }

            linearLayoutManager = (LinearLayoutManager) mRecycler.getLayoutManager();

            mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if(!isLoading){
                        if(linearLayoutManager !=null && linearLayoutManager.findLastVisibleItemPosition()==mPartialList.size()-1){
                            if(mPartialList.size()>=sonicConstants.TOTAL_ITENS_LOAD-1){
                                loadMore();
                            }
                        }
                    }
                }
            });
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType==VIEW_ITEM){
            view = LayoutInflater.from(mContext).inflate(R.layout.sonic_layout_cards_list, parent, false);
        }else{
            view = LayoutInflater.from(mContext).inflate(R.layout.sonic_layout_cards_list_shimmer, parent, false);
        }
        return new prodHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        prodHolder holder = (prodHolder) viewHolder;
        sonicProdutosHolder prod = mTotalList.get(position);

        if(holder.getItemViewType()==VIEW_ITEM){

            holder.linearItem.setOnClickListener((View v) -> {

                mPrefs.Produtos.setProdutoId(prod.getCodigo());
                mPrefs.Produtos.setProdutoNome(prod.getNome());
                mPrefs.Produtos.setProdutoGrupo(prod.getGrupo());
                mPrefs.Produtos.setProdutoDataCadastro(prod.getDataCadastro());
                mPrefs.Produtos.setDetalhe("CÓD.: "+prod.getCodigo()+" / REFERÊNCIA: "+prod.getCodigoAlternativo());
                Intent i = new Intent(v.getContext(), sonicProdutosDetalhe.class);
                v.getContext().startActivity(i);

        });

            holder.tvLinha1.setText(prod.getNome());
            //holder.tvNome.setTextColor(Color.parseColor(prod.getSituacaoCor()));
            holder.tvLinha3.setText("CÓD.: "+prod.getCodigo()+" / REFERÊNCIA: "+prod.getCodigoAlternativo());
            holder.codigo = prod.getCodigo();
            holder.tvLinha2.setText(prod.getGrupo() == null ? "GRUPO: --" : "GRUPO: "+prod.getGrupo());
            holder.dataCadastro = prod.getDataCadastro();
            String[] array = mContext.getResources().getStringArray(R.array.prefProdutoNovoOptions);
            diasDiff = mUtil.Data.dateDiffDay(holder.dataCadastro, mDataAtual);
            dias = mPrefs.Produtos.getDiasNovo().equals(array[0]) ? 30 :
                    mPrefs.Produtos.getDiasNovo().equals(array[1]) ? 60 :
                        mPrefs.Produtos.getDiasNovo().equals(array[2]) ? 90 : 30;

            holder.linearNew.setVisibility(diasDiff<=dias ? View.VISIBLE : View.GONE);

            File f = sonicFile.searchFile(myCons.LOCAL_IMG_CATALOGO, prod.getCodigo());

            if(f.exists()){

                Glide.with(mContext)
                        .load(f)
                        .circleCrop()
                        .apply(new RequestOptions().override(100,100))
                        .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                        .into(holder.mImage);

                holder.mImage.setVisibility(View.VISIBLE);
                holder.tvLetra.setVisibility(View.GONE);


            }else {
                holder.mImage.setVisibility(View.GONE);
                holder.tvLetra.setVisibility(View.VISIBLE);
                holder.tvLetra.setText(String.valueOf(prod.getNome().charAt(0)));
            }

        }

    }

    private void loadMore(){

        isLoading = true;
        mPartialList.add(null);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        },0);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                mPartialList.remove(mPartialList.size()-1);
                notifyItemRemoved(mPartialList.size());

                if(!mRecycler.isComputingLayout()){
                    notifyDataSetChanged();
                    int scrollPosition = mPartialList.size();
                    int currentSize = scrollPosition;
                    int nextLimit = currentSize + sonicConstants.TOTAL_ITENS_LOAD;
                    for(int i = currentSize; i < nextLimit; i++) {
                        if(currentSize< mTotalList.size()){
                            mPartialList.add(mTotalList.get(i));
                            currentSize++;
                        }
                    }
                }
                isLoading = false;
            }
        }, 1000);

    }

    @Override
    public Filter getFilter() {
        if(prodFilter == null)
            prodFilter = new prodFilter(this, mTotalList);
        return prodFilter;

    }

    @Override
    public int getItemViewType(int position) {
        return mPartialList.get(position > mPartialList.size()-1 ? mPartialList.size()-1 : position ) == null ? VIEW_PROG : VIEW_ITEM;
        }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return mTotalList.size();
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

            } else {

                final String filterPattern = constraint.toString().toUpperCase().trim();

                for (final sonicProdutosHolder prod : originalList) {

                    if (prod.getNome().contains(filterPattern) || prod.getCodigoAlternativo().contains(filterPattern) || String.valueOf(prod.getCodigo()).contains(filterPattern)) {
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
                adapter.mTotalList.clear();
                adapter.mFilteredList.addAll((ArrayList<sonicProdutosHolder>) results.values);
                adapter.notifyDataSetChanged();
        }
    }

}
