package com.softhex.sonic;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private static final int VIEW_HEADER = 1;
    private static final int VIEW_PROGRESS = 2;
    private static final int VIEW_ITEM = 3;
    private static final int TOTAL_ITENS_FILTRO = 6;
    private Context mContext;
    private List<sonicProdutosHolder> mTotalList;
    private List<sonicProdutosHolder> mFilteredList;
    private List<sonicProdutosHolder> mPartialList;
    private List<sonicGrupoProdutosHolder> mGroupList;
    private prodFilter prodFilter;
    private sonicDatabaseCRUD mData;
    private sonicUtils mUtil;
    private sonicPreferences mPrefs;
    private sonicConstants myCons;
    private RecyclerView mRecycler;
    private boolean isLoading = false;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMdd");
    private String mDataAtual;
    private LinearLayoutManager linearLayoutManager;
    private int dias, diasDiff;

    public class prodHolder extends RecyclerView.ViewHolder {

        int codigo;
        TextView tvLinha1;
        TextView tvLinha2;
        TextView tvLinha3;
        ImageView mImage;
        TextView tvLetra;
        CardView card;
        LinearLayout linearItem, linearNew;
        LinearLayout llGroupDate;

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
            llGroupDate = view.findViewById(R.id.llGroupDate);
            linearItem = view.findViewById(R.id.linearItem);

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
        this.mData = new sonicDatabaseCRUD(context);
        this.mDataAtual = mDateFormat.format(new Date());

        mPartialList = new ArrayList();
        mPartialList.add(0,null);
        mTotalList.add(0,null);

        if(mPrefs.Geral.getListagemCompleta()){

            for(int i=0; i<produto.size() ;i++){
                mPartialList.add(produto.get(i));
            }

        }else{

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
        View view=null;
        switch (viewType){
            case VIEW_HEADER:
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_cards_header, parent, false);
                break;
            case VIEW_ITEM:
                view = LayoutInflater.from(mContext).inflate(R.layout.sonic_layout_cards_itens, parent, false);
                break;
            case VIEW_PROGRESS:
                view = LayoutInflater.from(mContext).inflate(R.layout.sonic_layout_cards_itens_shimmer, parent, false);
                break;
        }
        return new prodHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        prodHolder holder = (prodHolder) viewHolder;
        sonicProdutosHolder prod = mTotalList.get(position);

        switch (holder.getItemViewType()){
            case VIEW_HEADER:
                criarFiltroBusca(holder);
                break;
            case VIEW_ITEM:
                exibirItemLista(holder, prod, position);
                break;
        }

    }

    @Override
    public Filter getFilter() {
        if(prodFilter == null)
            prodFilter = new prodFilter(this, mTotalList);
        return prodFilter;
    }

    @Override
    public int getItemViewType(int position) {
        return (position==0 && mTotalList.get(position)==null) ? VIEW_HEADER : mTotalList.get(position) == null ? VIEW_PROGRESS : VIEW_ITEM;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return mTotalList.size();
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


    private void criarFiltroBusca(prodHolder holder){

        holder.llGroupDate.removeAllViews();
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.height = sonicUtils.convertDpToPixel(36, mContext);
        p2.height = sonicUtils.convertDpToPixel(36, mContext);
        p.setMargins(0,0, sonicUtils.convertDpToPixel(6, mContext), 0);
        p2.setMargins(sonicUtils.convertDpToPixel(12, mContext),0, sonicUtils.convertDpToPixel(6, mContext), 0);
        mGroupList = mData.GrupoProduto.selectGrupo();
        Button bt1 = new Button(mContext);
        bt1.setText(mPrefs.GrupoProduto.getFiltroLista());
        bt1.setTextSize(12);
        bt1.setBackground(mContext.getResources().getDrawable(R.drawable.botao_selecionado));
        bt1.setLayoutParams(p2);
        bt1.setPadding(30,0,30,0);
        holder.llGroupDate.addView(bt1);
        List<sonicGrupoProdutosHolder> grupoAuxiliar;
        grupoAuxiliar = new ArrayList<>();
        for(int i=0; i < (mGroupList.size()<TOTAL_ITENS_FILTRO ? mGroupList.size() : TOTAL_ITENS_FILTRO) ;i++){

            grupoAuxiliar.add(mGroupList.get(i));

            if(!mGroupList.get(i).getDescricao().contains(mPrefs.GrupoProduto.getFiltroLista())){

                Button bt2 = new Button(mContext);

                bt2.setOnClickListener((View v)-> {

                    mPrefs.GrupoProduto.setFiltroLista(bt2.getText().toString());
                    ((sonicProdutos)mContext).refreshFragments(0);

                });

                bt2.setText(mGroupList.get(i).getDescricao());
                bt2.setTextSize(12);
                bt2.setBackground(mContext.getResources().getDrawable(R.drawable.botao_neutro));
                bt2.setLayoutParams(p);

                bt2.setPadding(30,0,30,0);
                holder.llGroupDate.addView(bt2);
            }

        }

        Button bt3 = new Button(mContext);

        bt3.setOnClickListener((View v)-> {
            exibirFiltroExtra(grupoAuxiliar);
        });

        bt3.setText("MAIS...");
        bt3.setTextSize(12);
        bt3.setBackground(mContext.getResources().getDrawable(R.drawable.botao_neutro));
        bt3.setLayoutParams(p);
        bt3.setPadding(30,0,30,0);
        holder.llGroupDate.addView(bt3);

    }

    private void exibirItemLista(prodHolder holder, sonicProdutosHolder prod, int position){


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
        String[] array = mContext.getResources().getStringArray(R.array.prefProdutoNovoOptions);
        diasDiff = mUtil.Data.dateDiffDay(prod.getDataCadastro(), mDataAtual);
        dias = mPrefs.Produtos.getDiasNovo().equals(array[0]) ? 30 :
                mPrefs.Produtos.getDiasNovo().equals(array[1]) ? 60 :
                        mPrefs.Produtos.getDiasNovo().equals(array[2]) ? 90 :
                                mPrefs.Produtos.getDiasNovo().equals(array[3]) ? 180 :
                                        mPrefs.Produtos.getDiasNovo().equals(array[4]) ? 360 : 90;

        holder.linearNew.setVisibility(diasDiff<=dias ? View.VISIBLE : View.GONE);

        File f = sonicFile.searchImage(myCons.LOCAL_IMG_CATALOGO, prod.getCodigo());

        if(f.exists()){

            Glide.with(mContext)
                    .load(f)
                    .circleCrop()
                    .apply(new RequestOptions().override(100,100))
                    //.transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                    .into(holder.mImage);

            holder.mImage.setVisibility(View.VISIBLE);
            holder.tvLetra.setVisibility(View.GONE);


        }else {
            holder.mImage.setVisibility(View.GONE);
            holder.tvLetra.setVisibility(View.VISIBLE);
            holder.tvLetra.setText(String.valueOf(prod.getNome().charAt(0)));
        }

        //TextView tv = ((sonicProdutos)mContext).findViewById(R.id.tvHeader);
        //tv.setText(String.valueOf(prod.getNome().charAt(0)));

    }

    private void exibirFiltroExtra(List<sonicGrupoProdutosHolder> mListAuxiliar) {

        List<sonicGrupoProdutosHolder> grupo;

        grupo = new sonicDatabaseCRUD(mContext).GrupoProduto.selectGrupo();

        List<String> l = new ArrayList<>();

        for(int i=0; i < grupo.size(); i++ ){
            l.add(grupo.get(i).getDescricao());
        }

        final CharSequence[] chars = l.toArray(new CharSequence[l.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setItems(chars, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                dialog.dismiss();
                mPrefs.GrupoProduto.setFiltroLista(chars[item].toString());
                ((sonicProdutos)mContext).refreshFragments(0);

            }
        }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton((mPrefs.GrupoProduto.getFiltroLista().equals("TODOS") ? "" : "LIMPAR FILTRO"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mPrefs.GrupoProduto.setFiltroLista("TODOS");
                ((sonicProdutos)mContext).refreshFragments(0);
            }
        }).show();

    }

    private class prodFilter extends Filter {

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
                    if(prod!=null)
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
