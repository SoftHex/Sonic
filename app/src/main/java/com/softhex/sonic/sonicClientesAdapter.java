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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.clans.fab.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrador on 11/08/2017.
 */

public class sonicClientesAdapter extends RecyclerView.Adapter implements Filterable{

    private static final int VIEW_HEADER = 1;
    private static final int VIEW_PROGRESS = 2;
    private static final int VIEW_ITEM = 3;
    private static final int TOTAL_ITENS_FILTRO = 6;
    private Context mContext;
    private List<sonicClientesHolder> mTotalList;
    private List<sonicClientesHolder> mFilteredList;
    private List<sonicClientesHolder> mPartialList;
    private List<sonicGrupoClientesHolder> mGroupList;
    private UserFilter userFilter;
    private sonicConstants myCons;
    private sonicPreferences mPrefs;
    private Boolean nFantasia;
    private Boolean cliSemCompra;
    private RecyclerView mRecycler;
    private boolean isLoading = false;
    private LinearLayoutManager linearLayoutManager;
    private boolean cnpj;
    private sonicDatabaseCRUD mData;
    private FloatingActionButton fbUp;

    public class cliHolder extends RecyclerView.ViewHolder {


        int codigo;
        TextView tvLinha1;
        TextView tvLinha2;
        TextView tvLinha3;
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
        LinearLayout llGroupDate;


        public cliHolder(View view) {
            super(view);

            cardView = view.findViewById(R.id.cardView);
            linearItem = view.findViewById(R.id.linearItem);
            tvLinha1 = view.findViewById(R.id.tvLinha1);
            letra = view.findViewById(R.id.tvLetra);
            tvLinha2 = view.findViewById(R.id.tvLinha2);
            tvLinha3 = view.findViewById(R.id.tvLinha3);
            mImage = view.findViewById(R.id.ivImagem);
            lineraNew = view.findViewById(R.id.linearNew);
            llExtra = view.findViewById(R.id.llExtra);
            tvSemCompra = view.findViewById(R.id.tvSemCompra);
            tvAtraso = view.findViewById(R.id.tvAtraso);
            llGroupDate = view.findViewById(R.id.llGroupDate);

        }

    }


    public sonicClientesAdapter(List<sonicClientesHolder> cliente, Context context, RecyclerView recycler, FloatingActionButton fbup, boolean cnpj) {

        this.myCons = new sonicConstants();
        this.mTotalList = cliente;
        this.mFilteredList = cliente;
        this.mContext = context;
        this.mPrefs = new sonicPreferences(mContext);
        this.mRecycler = recycler;
        this.cnpj = cnpj;
        this.nFantasia =  mPrefs.Clientes.getClienteExibicao().equals("Nome Fantasia") ? true : false;
        this.cliSemCompra = mPrefs.Clientes.getClienteSemCompra();
        this.mData = new sonicDatabaseCRUD(mContext);
        this.fbUp = fbup;

        mPartialList = new ArrayList();
        mPartialList.add(0,null);
        mTotalList.add(0,null);

        if(mPrefs.Geral.getListagemCompleta()){

            for(int i=0;i<cliente.size();i++){
                mPartialList.add(cliente.get(i));
            }

        }else{

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
                    fbUp.setVisibility(linearLayoutManager.findLastCompletelyVisibleItemPosition()>sonicConstants.TOTAL_ITENS_LOAD ? View.VISIBLE : View.GONE);
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

        fbUp.setOnClickListener((View v)->{
            mRecycler.scrollToPosition(0);
        });

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
        return new cliHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

            cliHolder holder = (cliHolder) viewHolder;
            sonicClientesHolder cli = mTotalList.get(position);

            switch (holder.getItemViewType()){
                case VIEW_HEADER:
                    criarFiltroBusca(holder);
                    break;
                case VIEW_ITEM:
                    exibirItemLista(holder, cli, position);
                    break;
            }

    }

    @Override
    public Filter getFilter() {
        if(userFilter == null)
            userFilter = new UserFilter(this, mTotalList);
        return userFilter;

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

    private void criarFiltroBusca(cliHolder holder){

        holder.llGroupDate.removeAllViews();
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.height = sonicUtils.convertDpToPixel(36, mContext);
        p2.height = sonicUtils.convertDpToPixel(36, mContext);
        p.setMargins(0,0, sonicUtils.convertDpToPixel(6, mContext), 0);
        p2.setMargins(sonicUtils.convertDpToPixel(12, mContext),0, sonicUtils.convertDpToPixel(6, mContext), 0);
        mGroupList = mData.GrupoCliente.selectGrupoCliente(cnpj ? "J" : "F");
        Button bt1 = new Button(mContext);
        bt1.setText(cnpj ? mPrefs.GrupoCliente.getFiltroCnpj() : mPrefs.GrupoCliente.getFiltroCpf());
        bt1.setTextSize(12);
        bt1.setBackground(mContext.getResources().getDrawable(R.drawable.botao_selecionado));
        bt1.setLayoutParams(p2);
        bt1.setPadding(30,0,30,0);
        holder.llGroupDate.addView(bt1);
        List<sonicGrupoClientesHolder> grupoAuxiliar;
        grupoAuxiliar = new ArrayList<>();
        for(int i=0; i < (mGroupList.size()<TOTAL_ITENS_FILTRO ? mGroupList.size() : TOTAL_ITENS_FILTRO) ;i++){

            grupoAuxiliar.add(mGroupList.get(i));

            if(!mGroupList.get(i).getNome().contains(cnpj ? mPrefs.GrupoCliente.getFiltroCnpj() : mPrefs.GrupoCliente.getFiltroCpf())){

                Button bt2 = new Button(mContext);

                bt2.setOnClickListener((View v)-> {

                    if(cnpj){
                        mPrefs.GrupoCliente.setFiltroCnpj(bt2.getText().toString());
                    }else{
                        mPrefs.GrupoCliente.setFiltroCpf(bt2.getText().toString());
                    }

                ((sonicClientes)mContext).refreshFragments(cnpj ? 0 : 1);

                });

                bt2.setOnLongClickListener((View v)->{
                    //marcarFiltroPadrao(bt2.getText().toString());
                    return false;
                });

                bt2.setText(mGroupList.get(i).getNome());
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

    private void exibirItemLista(cliHolder holder, sonicClientesHolder cli, int position){

        String cliNomeExibicao = nFantasia ? cli.getNomeFantasia() : cli.getRazaoSocial();

        holder.linearItem.setOnClickListener((View v)-> {

            mPrefs.Clientes.setId(cli.getCodigo());
            mPrefs.Clientes.setNome(cliNomeExibicao);
            mPrefs.Clientes.setClienteNuncaComprou(cli.getCliSemCompra() > 0 ? true : false);
            if(mPrefs.RotaPessoal.getAdding()){
                mPrefs.RotaPessoal.setClientePicked(true);
                ((Activity)mContext).finish();
            }else{
                Intent i = new Intent(v.getContext(), sonicClientesDetalhe.class);
                mContext.startActivity(i);
            }
        });

        holder.codigo = cli.getCodigo();
        holder.clienteStatus = cli.getStatus();
        holder.tvLinha1.setText(cliNomeExibicao);
        holder.tvSemCompra.setVisibility((cliSemCompra && cli.getCliSemCompra()>0) ? View.VISIBLE : View.GONE);
        holder.tvAtraso.setVisibility(cli.getTitulosEmAtraso()>0 ? View.VISIBLE : View.GONE);
        holder.tvLinha2.setText(cli.getGrupo());
        holder.tvLinha3.setText(cli.getEnderecoCompleto());

        File f = sonicFile.searchImage(myCons.LOCAL_IMG_CLIENTES, cli.getCodigo());

        if(f.exists()){

            holder.mImage.setVisibility(View.VISIBLE);
            holder.letra.setVisibility(View.GONE);
            Glide.with(mContext)
                    .load(f)
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

    private void exibirFiltroExtra(List<sonicGrupoClientesHolder> mListAuxiliar) {

        List<sonicGrupoClientesHolder> grupo;

        grupo = new sonicDatabaseCRUD(mContext).GrupoCliente.selectGrupoCliente(cnpj ? "J" : "F");

        List<String> l = new ArrayList<>();

        for(int i=0; i < grupo.size(); i++ ){
            l.add(grupo.get(i).getNome());
        }

        final CharSequence[] chars = l.toArray(new CharSequence[l.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setItems(chars, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                dialog.dismiss();
                if(cnpj){
                    mPrefs.GrupoCliente.setFiltroCnpj(chars[item].toString());
                    ((sonicClientes)mContext).refreshFragments(0);
                }else{
                    mPrefs.GrupoCliente.setFiltroCpf(chars[item].toString());
                    ((sonicClientes)mContext).refreshFragments(1);
                }
            }
        }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton(cnpj ? (mPrefs.GrupoCliente.getFiltroCnpj().equals("TODOS") ? "" : "LIMPAR FILTRO") : (mPrefs.GrupoCliente.getFiltroCpf().equals("TODOS") ? "" : "LIMPAR"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(cnpj){
                    mPrefs.GrupoCliente.setFiltroCnpj("TODOS");
                    ((sonicClientes)mContext).refreshFragments(0);
                }else{
                    mPrefs.GrupoCliente.setFiltroCpf("TODOS");
                    ((sonicClientes)mContext).refreshFragments(1);
                }
            }
        }).show();

    }

    public void marcarFiltroPadrao(String filtro) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle("Filtro Padrão");

        alertDialogBuilder
                .setMessage("Deseja marcar esse filtro como padrão? Será apenas para a tela de clientes.")
                .setCancelable(false)
                .setPositiveButton("SIM",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        mPrefs.GrupoCliente.setFiltroPadrao(filtro);
                    }
                })
                .setNegativeButton("CANCELAR",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


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
                    if(cli!=null)
                        if (mPrefs.Clientes.getClienteExibicao().equals("Nome Fantasia")
                                ? cli.getNomeFantasia().contains(filterPattern)
                                : cli.getRazaoSocial().contains(filterPattern) || String.valueOf(cli.getCodigo()).contains(filterPattern) || cli.getCpfCnpj().contains(filterPattern)) {
                            filteredList.add(cli);
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
            adapter.mFilteredList.addAll((ArrayList<sonicClientesHolder>) results.values);
            adapter.notifyDataSetChanged();
        }
    }


}

