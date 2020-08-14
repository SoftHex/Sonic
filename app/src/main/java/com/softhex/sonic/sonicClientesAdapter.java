package com.softhex.sonic;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.GenericTransitionOptions;
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

    private static final int VIEW_ITEM = 1;
    private static final int VIEW_PROGRESS = 2;
    private static final int TOTAL_ITENS_FILTRO = 6;
    private Context mContext;
    private Activity mAct;
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
    private String cliNomeExibicao;
    private int cliCodigo;

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
        LinearLayout llGrouFilter;
        LinearLayout llRootView;
        LinearLayout llParentView;
        RelativeLayout rlChildView;

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
            llGrouFilter = view.findViewById(R.id.llGroupFilter);
            llRootView = view.findViewById(R.id.llRootView);
            llParentView = view.findViewById(R.id.llParentView);
            rlChildView = view.findViewById(R.id.rlChildView);

            /*linearItem.setOnClickListener((View v)->{
                new mAsyncTaskRecentAdd().execute(cliCodigo);
                mPrefs.Clientes.setCodigo(cliCodigo);
                mPrefs.Clientes.setNome(cliNomeExibicao);
                mPrefs.Geral.setCallActivity("sonicClientes"+(cnpj ? "CNPJ" : "CPF"));
                if(mPrefs.RotaPessoal.getAdding()){
                    mPrefs.RotaPessoal.setClientePicked(true);
                    ((Activity)mContext).finish();
                }else{
                    Intent i = new Intent(v.getContext(), sonicClientesDetalhe.class);
                    ((Activity)mContext).startActivityForResult(i,1);
                }
            });*/

        }

    }

    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
    }

    public sonicClientesAdapter(List<sonicClientesHolder> fullList, Context context, RecyclerView recycler, View rootView, Activity act, boolean cnpj) {

        this.myCons = new sonicConstants();
        this.mTotalList = fullList;
        this.mFilteredList = fullList;
        this.mContext = context;
        this.mPrefs = new sonicPreferences(mContext);
        this.mRecycler = recycler;
        this.cnpj = cnpj;
        this.nFantasia =  mPrefs.Clientes.getClienteExibicao().equals("Nome Fantasia") ? true : false;
        this.cliSemCompra = mPrefs.Clientes.getClienteSemCompra();
        this.mData = new sonicDatabaseCRUD(mContext);
        this.mAct = act;
        this.mRecycler.setHasFixedSize(true);

        mPartialList = new ArrayList();

        if(mPrefs.Geral.getListagemCompleta()){

            for(int i=0;i<fullList.size();i++){
                mPartialList.add(fullList.get(i));
            }

        }else{

            if(fullList.size()< sonicConstants.TOTAL_ITENS_LOAD){
                for(int i = 0; i < fullList.size(); i++){
                    mPartialList.add(fullList.get(i));
                }
            }else{
                for(int i = 0; i<sonicConstants.TOTAL_ITENS_LOAD-1; i++){
                    mPartialList.add(fullList.get(i));
                }
            }

            linearLayoutManager = (LinearLayoutManager) mRecycler.getLayoutManager();

            RecyclerView.OnScrollListener mListener = new RecyclerView.OnScrollListener() {
                int y;
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    //super.onScrolled(recyclerView, dx, dy);
                    y = dy;
                    if(!isLoading){
                        if(linearLayoutManager !=null && linearLayoutManager.findLastVisibleItemPosition()==mPartialList.size()-1){
                            if(mPartialList.size()>=sonicConstants.TOTAL_ITENS_LOAD-1){
                                loadMore();
                            }
                        }
                    }
                }
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    View v = rootView.findViewById(R.id.llRootView);
                    if(newState==mRecycler.SCROLL_STATE_SETTLING){
                        if(y<=0){
                            if(v.getVisibility()==View.GONE)
                            expand(v);
                        }else{
                            y=0;
                            collapse(v);
                        }
                    }
                }
            };

            mRecycler.addOnScrollListener(mListener);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=null;
        switch (viewType){
            case VIEW_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sonic_layout_cards_itens, parent, false);
                break;
            case VIEW_PROGRESS:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sonic_layout_cards_itens_shimmer, parent, false);
                break;
        }

        return new cliHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

            cliHolder holder = (cliHolder) viewHolder;
            sonicClientesHolder cli = mTotalList.get(position);

            switch (holder.getItemViewType()){
                case VIEW_ITEM:
                    exibirItemLista(holder, cli, position);
                    break;
            }

    }

    @Override
    public boolean onFailedToRecycleView(@NonNull RecyclerView.ViewHolder holder) {
        //return super.onFailedToRecycleView(holder);
        holder.itemView.clearAnimation();
        return true;
    }

    @Override
    public Filter getFilter() {
        if(userFilter == null)
            userFilter = new UserFilter(this, mTotalList);
        return userFilter;

    }

    @Override
    public int getItemViewType(int position) {
        return mTotalList.get(position) == null ? VIEW_PROGRESS : VIEW_ITEM;
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

    private void exibirItemLista(cliHolder holder, sonicClientesHolder cli, int position){

        cliNomeExibicao = nFantasia ? cli.getNomeFantasia() : cli.getRazaoSocial();
        cliCodigo = cli.getCodigo();

        holder.linearItem.setOnClickListener((View v)-> {

            new mAsyncTaskRecentAdd().execute(cli.getCodigo());
            mPrefs.Clientes.setCodigo(cli.getCodigo());
            mPrefs.Clientes.setNome(cliNomeExibicao);
            mPrefs.Geral.setCallActivity("sonicClientes"+(cnpj ? "CNPJ" : "CPF"));
            if(mPrefs.RotaPessoal.getAdding()){
                mPrefs.RotaPessoal.setClientePicked(true);
                ((Activity)mContext).finish();
            }else{
                Intent i = new Intent(v.getContext(), sonicClientesDetalhe.class);
                ((Activity)mContext).startActivityForResult(i,1);
            }
        });

        holder.codigo = cli.getCodigo();
        holder.clienteStatus = cli.getStatus();
        holder.tvLinha1.setText(cliNomeExibicao);
        holder.tvSemCompra.setVisibility((cliSemCompra && cli.getCliSemCompra()>0) ? View.VISIBLE : View.GONE);
        holder.tvAtraso.setVisibility(cli.getTitulosEmAtraso()>0 ? View.VISIBLE : View.GONE);
        holder.tvLinha2.setText(sonicUtils.stringToCnpjCpf(cli.getCpfCnpj()) + " / " + cli.getGrupo());
        holder.tvLinha3.setText(cli.getEnderecoCompleto());

        File f = sonicFile.searchImage(myCons.LOCAL_IMG_CLIENTES, cli.getCodigo());

        if(f.exists()){

            holder.mImage.setVisibility(View.VISIBLE);
            holder.letra.setVisibility(View.GONE);
            carregarImagemCliente(holder.mImage, f);

        }else{

            holder.mImage.setVisibility(View.GONE);
            holder.letra.setVisibility(View.VISIBLE);
            holder.letra.setText(String.valueOf(cliNomeExibicao.charAt(0)).toUpperCase());

        }

    }

    private void carregarImagemCliente(ImageView image, File file){
        new Thread(new Runnable() {
            @Override
            public void run() {

                mAct.runOnUiThread(new Runnable() {
                    public void run() {
                        Glide.with(mContext).clear(image);
                        Glide.with(mContext)
                                .load(file)
                                .circleCrop()
                                .transition(GenericTransitionOptions.with(R.anim.fade_in))
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .dontAnimate()
                                .into(image);

                    }
                });
            }
        }).start();
    }

    private void exibirFiltroExtra(List<String> mListAuxiliar) {

        List<sonicGrupoClientesHolder> grupo;

        grupo = new sonicDatabaseCRUD(mContext).GrupoCliente.selectGrupoCliente(cnpj ? "J" : "F");

        List<String> l = new ArrayList<>();

        for(int i=0; i < grupo.size(); i++ ){
            if(!mListAuxiliar.contains(grupo.get(i).getNome())){
                l.add(grupo.get(i).getNome());
            }
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

    class mAsyncTaskRecentAdd extends AsyncTask<Integer, Void, Void>{

        @Override
        protected Void doInBackground(Integer... integers) {
            new sonicDatabaseCRUD(mContext).Cliente.addAcesso(integers[0]);
            return null;
        }
    }

    public static void expand(final View v) {
        v.measure(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? WindowManager.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
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

