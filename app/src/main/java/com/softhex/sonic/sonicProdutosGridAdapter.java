package com.softhex.sonic;

import android.app.Activity;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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

    private static final int VIEW_FILTER = 1;
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
    private GridLayoutManager mLayout;
    private sonicFtp mFtp;
    private ImageView ivDownload;

    public class prodHolder extends RecyclerView.ViewHolder {

        TextView tvNome;
        int codigo;
        String tvGrupo;
        ImageView mImage, ivNew;
        LinearLayout llDescription, linearNew;
        RelativeLayout rlCatalogo, linearItem;
        String status;
        String dataCadastro;
        String tvDetalhe;
        LinearLayout llGroupFilter;
        ImageButton ibDownloadImage;
        LinearLayout llDownload;

        public prodHolder(View view) {
            super(view);

            rlCatalogo = view.findViewById(R.id.rlCatalogo);
            linearItem = view.findViewById(R.id.linearItem);
            linearNew = view.findViewById(R.id.llNew);
            llDescription = view.findViewById(R.id.llDescricao);
            tvNome = view.findViewById(R.id.tvNome);
            //tvGrupo = view.findViewById(R.id.tvGrupo);
            mImage = view.findViewById(R.id.ivImagem);
            ivNew = view.findViewById(R.id.ivNew);
            llGroupFilter = view.findViewById(R.id.llGroupFilter);
            llDownload = view.findViewById(R.id.llDownload);
            ibDownloadImage = view.findViewById(R.id.ibDownloadImage);

        }
    }

    public sonicProdutosGridAdapter(List<sonicProdutosHolder> produto, Context context, RecyclerView recycler, GridLayoutManager layout) {

        this.myCons = new sonicConstants();
        this.mTotalList = produto;
        this.mFilteredList = produto;
        this.mContext = context;
        this.mUtil = new sonicUtils(context);
        this.mPrefs = new sonicPreferences(context);
        this.mRecycler = recycler;
        this.mData = new sonicDatabaseCRUD(context);
        this.mLayout = layout;
        this.mDataAtual = mDateFormat.format(new Date());
        this.mFtp = new sonicFtp((Activity)context);

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
            case VIEW_FILTER:
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_cards_filter, parent, false);
                break;
            case VIEW_ITEM:
                view = LayoutInflater.from(mContext).inflate(R.layout.sonic_layout_cards_grid, parent, false);
                break;
            case VIEW_PROGRESS:
                view = LayoutInflater.from(mContext).inflate(R.layout.sonic_layout_cards_grid_shimmer, parent, false);
                break;
        }
        return new prodHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        prodHolder holder = (prodHolder) viewHolder;
        sonicProdutosHolder prod = mTotalList.get(position);

        switch (holder.getItemViewType()){
            case VIEW_FILTER:
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
        return (position==0 && mTotalList.get(position)==null) ? VIEW_FILTER : mTotalList.get(position) == null ? VIEW_PROGRESS : VIEW_ITEM;
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

        holder.llGroupFilter.removeAllViews();
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.height = sonicUtils.convertDpToPixel(36, mContext);
        p2.height = sonicUtils.convertDpToPixel(36, mContext);
        p.setMargins(0,0, sonicUtils.convertDpToPixel(6, mContext), 0);
        p2.setMargins(sonicUtils.convertDpToPixel(12, mContext),0, sonicUtils.convertDpToPixel(6, mContext), 0);
        mGroupList = mData.GrupoProduto.selectGrupo();
        Button bt1 = new Button(mContext);
        bt1.setText(mPrefs.GrupoProduto.getFiltroGrid());
        bt1.setTextSize(12);
        bt1.setBackground(mContext.getResources().getDrawable(R.drawable.botao_selecionado));
        bt1.setLayoutParams(p2);
        bt1.setPadding(30,0,30,0);
        holder.llGroupFilter.addView(bt1);
        List<String> grupoAuxiliar;
        grupoAuxiliar = new ArrayList<>();
        grupoAuxiliar.add(mPrefs.GrupoProduto.getFiltroGrid());
        for(int i=0; i < (mGroupList.size()<TOTAL_ITENS_FILTRO ? mGroupList.size() : TOTAL_ITENS_FILTRO) ;i++){

            grupoAuxiliar.add(mGroupList.get(i).getDescricao());

            if(!mGroupList.get(i).getDescricao().contains(mPrefs.GrupoProduto.getFiltroGrid())){

                Button bt2 = new Button(mContext);

                bt2.setOnClickListener((View v)-> {

                    mPrefs.GrupoProduto.setFiltroGrid(bt2.getText().toString());

                    ((sonicProdutos)mContext).refreshFragments(1);

                });

                bt2.setText(mGroupList.get(i).getDescricao());
                bt2.setTextSize(12);
                bt2.setBackground(mContext.getResources().getDrawable(R.drawable.botao_neutro));
                bt2.setLayoutParams(p);

                bt2.setPadding(30,0,30,0);
                holder.llGroupFilter.addView(bt2);
            }

        }

        if(mGroupList.size()>TOTAL_ITENS_FILTRO){
            Button bt3 = new Button(mContext);

            bt3.setOnClickListener((View v)-> {
                exibirFiltroExtra(grupoAuxiliar);
            });
            bt3.setText("MAIS...");
            bt3.setTextSize(12);
            bt3.setBackground(mContext.getResources().getDrawable(R.drawable.botao_neutro));
            bt3.setLayoutParams(p);
            bt3.setPadding(30,0,30,0);
            holder.llGroupFilter.addView(bt3);
        }

    }

    private void exibirItemLista(prodHolder holder, sonicProdutosHolder prod, int position){

        holder.linearItem.setOnClickListener((View v)-> {

            mPrefs.Produtos.setProdutoId(prod.getCodigo());
            mPrefs.Produtos.setProdutoNome(prod.getNome());
            mPrefs.Produtos.setProdutoGrupo(prod.getGrupo() == null ? "GRUPO: --" : "GRUPO: "+prod.getGrupo());
            mPrefs.Produtos.setDetalhe("CÓD.: "+prod.getCodigo()+" / REFERÊNCIA: "+prod.getCodigoAlternativo());
            Intent i = new Intent(v.getContext(), sonicProdutosDetalhe.class);
            v.getContext().startActivity(i);

        });

        holder.codigo = prod.getCodigo();
        holder.tvNome.setText(prod.getNome());
        holder.tvGrupo = prod.getGrupo() == null ? "GRUPO: --" : "GRUPO: "+prod.getGrupo();
        holder.tvDetalhe = "CÓD.: "+prod.getCodigo()+" / REFERÊNCIA: "+prod.getCodigoAlternativo();

        String[] array = mContext.getResources().getStringArray(R.array.prefProdutoNovoOptions);
        diasDiff = mUtil.Data.dateDiffDay(prod.getDataCadastro(), mDataAtual);
        dias = mPrefs.Produtos.getDiasNovo().equals(array[0]) ? 30 :
                mPrefs.Produtos.getDiasNovo().equals(array[1]) ? 60 :
                        mPrefs.Produtos.getDiasNovo().equals(array[2]) ? 90 :
                                mPrefs.Produtos.getDiasNovo().equals(array[3]) ? 180 :
                                        mPrefs.Produtos.getDiasNovo().equals(array[4]) ? 360 : 90;


        String[] arrayColumn = mContext.getResources().getStringArray(R.array.prefProdutoCatalogoOptions);
        int colunas = mPrefs.Produtos.getCatalogoColunas().equals(arrayColumn[0]) ? 2 :
                mPrefs.Produtos.getCatalogoColunas().equals(arrayColumn[1]) ? 3 :
                        mPrefs.Produtos.getCatalogoColunas().equals(arrayColumn[2]) ? 4 : 3;

        holder.ivNew.getLayoutParams().height = sonicUtils.intToDps(mContext, 90/colunas);
        holder.ivNew.setVisibility(diasDiff<=dias ? View.VISIBLE : View.GONE);
        holder.linearNew.setVisibility(View.VISIBLE);
        holder.rlCatalogo.getLayoutParams().height = sonicUtils.intToDps(mContext,380/colunas);

        sonicGlide.glideImageView(mContext, holder.mImage, sonicUtils.checkImageJpgPng(sonicConstants.LOCAL_IMG_CATALOGO, String.valueOf(prod.getCodigo()), R.drawable.nophoto), 900/colunas, 900/colunas);
        if(!sonicUtils.checkImage(sonicConstants.LOCAL_IMG_CATALOGO, String.valueOf(prod.getCodigo())) && prod.getFoto()==1){
            holder.llDownload.setVisibility(View.VISIBLE);
            holder.ibDownloadImage.setOnClickListener((View v)->{
                downloadImagens(String.valueOf(prod.getCodigo()));
            });
        }else{
            holder.llDownload.setVisibility(View.GONE);
            ivDownload = holder.mImage;
        }

    }

    private void downloadImagens(String codigo){

        mFtp.downloadImages(sonicConstants.LOCAL_IMG_CATALOGO, codigo);
    }

    private void exibirFiltroExtra(List<String> mListAuxiliar) {

        List<sonicGrupoProdutosHolder> grupo;

        grupo = new sonicDatabaseCRUD(mContext).GrupoProduto.selectGrupo();

        List<String> l = new ArrayList<>();

        for(int i=0; i < grupo.size(); i++ ){
            if(!mListAuxiliar.contains(grupo.get(i).getDescricao())){
                l.add(grupo.get(i).getDescricao());
            }
        }

        final CharSequence[] chars = l.toArray(new CharSequence[l.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setItems(chars, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                dialog.dismiss();
                mPrefs.GrupoProduto.setFiltroGrid(chars[item].toString());
                ((sonicProdutos)mContext).refreshFragments(1);

            }
        }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton((mPrefs.GrupoProduto.getFiltroGrid().equals("TODOS") ? "" : "LIMPAR FILTRO"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mPrefs.GrupoProduto.setFiltroGrid("TODOS");
                ((sonicProdutos)mContext).refreshFragments(1);

            }
        }).show();

    }

    private void ajustarGrid(List<sonicProdutosHolder> filteredList){

        String[] array = mContext.getResources().getStringArray(R.array.prefProdutoCatalogoOptions);
        int colunas = mPrefs.Produtos.getCatalogoColunas().equals(array[0]) ? 2 :
                mPrefs.Produtos.getCatalogoColunas().equals(array[1]) ? 3 :
                        mPrefs.Produtos.getCatalogoColunas().equals(array[2]) ? 4 : 3;
        mLayout.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                return (position==0 && filteredList.get(0)==null) ? colunas : 1;
            }
        });
    }

    private class prodFilter extends Filter {

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

                ajustarGrid(filteredList);

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
