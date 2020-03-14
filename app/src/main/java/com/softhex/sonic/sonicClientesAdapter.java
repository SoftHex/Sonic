package com.softhex.sonic;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrador on 11/08/2017.
 */

public class sonicClientesAdapter extends RecyclerView.Adapter implements Filterable{

    private final int VIEW_ITEM = 123456789;
    private final int VIEW_PROG = 987654321;
    private Context mContext;
    private List<sonicClientesHolder> clientes;
    private List<sonicClientesHolder> filteredClientes;
    private List<sonicClientesHolder> mPartialList;
    private UserFilter userFilter;
    private sonicConstants myCons;
    private sonicPreferences mPrefs;
    private Boolean nFantasia;
    private Boolean cliSemCompra;
    private RecyclerView mRecycler;
    private boolean isLoading = false;
    private LinearLayoutManager linearLayoutManager;

    public class cliHolder extends RecyclerView.ViewHolder {


        int codigo;
        TextView tvNome;
        TextView tvGrupo;
        TextView tvDetalhe;
        TextView letra;
        TextView sit;
        TextView titulos;
        CardView cardView;
        String clienteStatus;
        ImageView mImage;
        Boolean situacao;
        LinearLayout linearItem;
        LinearLayout lineraNew;
        LinearLayout llExtra;
        TextView tvSemCompra, tvAtraso;


        public cliHolder(View view) {
            super(view);

            cardView = view.findViewById(R.id.cardView);
            linearItem = view.findViewById(R.id.linearItem);
            tvNome = view.findViewById(R.id.tvNome);
            letra = view.findViewById(R.id.tvLetra);
            tvGrupo = view.findViewById(R.id.tvGrupo);
            tvDetalhe = view.findViewById(R.id.tvDetalhe);
            mImage = view.findViewById(R.id.ivImagem);
            lineraNew = view.findViewById(R.id.linearNew);
            llExtra = view.findViewById(R.id.llExtra);
            tvSemCompra = view.findViewById(R.id.tvSemCompra);
            tvAtraso = view.findViewById(R.id.tvAtraso);


        }

    }


    public sonicClientesAdapter(Context mContex, List<sonicClientesHolder> cliente, RecyclerView mRecycler) {

        this.myCons = new sonicConstants();
        this.clientes = cliente;
        this.filteredClientes = cliente;
        this.mContext = mContex;
        this.mPrefs = new sonicPreferences(mContext);
        this.mRecycler = mRecycler;
        this.nFantasia =  mPrefs.Clientes.getClienteExibicao().equals("Nome Fantasia") ? true : false;
        this.cliSemCompra = mPrefs.Clientes.getClienteSemCompra();

        mPartialList = new ArrayList();

        if(cliente.size()< sonicConstants.TOTAL_ITENS_LOAD){
            for(int i = 0; i < cliente.size(); i++){
                mPartialList.add(cliente.get(i));
            }
        }else{
            for(int i = 0; i<sonicConstants.TOTAL_ITENS_LOAD-1; i++){
                mPartialList.add(cliente.get(i));
            }
        }

        linearLayoutManager = (LinearLayoutManager) mRecycler.getLayoutManager();

        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        if(viewType==VIEW_ITEM){
            view = LayoutInflater.from(mContext).inflate(R.layout.sonic_layout_cards_list, parent, false);
        }else{
            view = LayoutInflater.from(mContext).inflate(R.layout.sonic_layout_cards_list_shimmer, parent, false);
        }
        return new cliHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

            cliHolder holder = (cliHolder) viewHolder;
            holder.setIsRecyclable(false);
            sonicClientesHolder cli = mPartialList.get(position);

            if(getItemViewType(position)==VIEW_ITEM){

                String cliNomeExibicao = nFantasia ? cli.getNomeFantasia() : cli.getRazaoSocial();

                holder.linearItem.setOnClickListener((View v)-> {

                    mPrefs.Clientes.setId(cli.getCodigo());
                    mPrefs.Clientes.setNome(cliNomeExibicao);
                    mPrefs.Clientes.setGrupo(cli.getGrupo());
                    Intent i = new Intent(v.getContext(), sonicClientesDetalhe.class);
                    mContext.startActivity(i);

                });

                holder.codigo = cli.getCodigo();
                holder.clienteStatus = cli.getStatus();
                holder.tvNome.setText(cliNomeExibicao);
                holder.tvSemCompra.setVisibility((cliSemCompra && cli.getCliSemCompra()>0) ? View.VISIBLE : View.GONE);
                holder.tvAtraso.setVisibility(cli.getTitulosEmAtraso()>0 ? View.VISIBLE : View.GONE);
                holder.tvGrupo.setText("CÓD.: "+cli.getCodigo()+" / GRUPO: "+cli.getGrupo());
                holder.tvDetalhe.setText(cli.getEndereco()+", "+cli.getBairro()+", "+cli.getMunicipio()+" - "+cli.getUf());

                File file = new File(Environment.getExternalStorageDirectory(), myCons.LOCAL_IMG_CLIENTES + cli.getCodigo() + ".JPG");

                if(file.exists()){

                    holder.mImage.setVisibility(View.VISIBLE);
                    holder.letra.setVisibility(View.GONE);
                    Glide.with(mContext)
                            .load(file)
                            .circleCrop()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(holder.mImage);

                }else{

                    holder.mImage.setVisibility(View.GONE);
                    holder.letra.setVisibility(View.VISIBLE);
                    holder.letra.setText(String.valueOf(cliNomeExibicao.charAt(0)).toUpperCase());

                }

            }

    }


    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
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
                        if(currentSize<clientes.size()){
                            mPartialList.add(clientes.get(i));
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
        if(userFilter == null)
            userFilter = new UserFilter(this, clientes);
        return userFilter;

    }

    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
        return mPartialList.get(position) == null ? VIEW_PROG : VIEW_ITEM;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return mPartialList.size();
    }


    private class UserFilter extends Filter {

        private final sonicClientesAdapter adapter;

        private final List<sonicClientesHolder> originalList;

        private final List<sonicClientesHolder> filteredList;

        private UserFilter(sonicClientesAdapter adapter, List<sonicClientesHolder> originalList) {
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

                for (final sonicClientesHolder cli : originalList) {
                    if(mPrefs.Clientes.getClienteExibicao().contains("Nome Fantasia")){
                        if (cli.getNomeFantasia().contains(filterPattern)|| String.valueOf(cli.getCodigo()).contains(filterPattern)) {
                            filteredList.add(cli);
                        }
                    }else {
                        if (cli.getRazaoSocial().contains(filterPattern)|| String.valueOf(cli.getCodigo()).contains(filterPattern)) {
                            filteredList.add(cli);
                        }
                    }

                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.clientes.clear();
            adapter.filteredClientes.addAll((ArrayList<sonicClientesHolder>) results.values);
            adapter.notifyDataSetChanged();
        }
    }


}

