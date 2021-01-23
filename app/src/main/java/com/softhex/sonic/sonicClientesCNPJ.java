package com.softhex.sonic;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Administrador on 21/07/2017.
 */

public class sonicClientesCNPJ extends Fragment {

    private static final int TOTAL_ITENS_FILTRO = 6;
    private View mView;
    private RecyclerView mRecycler;
    private RecyclerView.LayoutManager mLayout;
    private sonicClientesAdapter mAdapter;
    private List<sonicClientesHolder> mList;
    private List<sonicClientesHolder> mRecentList;
    private List<sonicGrupoClientesHolder> mGroupList;
    private sonicDatabaseCRUD mData;
    private MenuItem mSearch;
    private Toolbar mToolBar;
    private TabLayout mTabLayout;
    private CoordinatorLayout mCoordinatorLayout;
    private ShimmerFrameLayout mShimmer;
    private TextView tvTexto, tvTitle, tvSearch;
    private sonicConstants myCons;
    private boolean allowSearch;
    private Context mContext;
    private ImageView myImage;
    private sonicPreferences mPrefs;
    private LinearLayout llNoResult;
    private RelativeLayout rlDesert;
    private Button btSinc;
    private Activity mAct;
    private LinearLayout llFilterRootView;
    private LinearLayout llGroupFilter;
    private LinearLayout llRecenteRootView;
    private LinearLayout llParentView;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.sonic_recycler_layout_list, container, false);

        mContext = getActivity();
        mAct = getActivity();
        mPrefs = new sonicPreferences(getActivity());
        mData = new sonicDatabaseCRUD(getContext());
        mView.setTag("sonicClientesCNPJ");
        loadFragment();

        return mView;

    }

    public void loadFragment(){

        setHasOptionsMenu(true);

        myCons = new sonicConstants();

        mToolBar = getActivity().findViewById(R.id.mToolbar);

        mTabLayout = getActivity().findViewById(R.id.mTabs);

        llFilterRootView = mView.findViewById(R.id.llFilterRootView);

        llGroupFilter = mView.findViewById(R.id.llGroupFilter);

        llRecenteRootView = mView.findViewById(R.id.llRecenteRootView);

        llParentView = mView.findViewById(R.id.llParentView);

        llNoResult = mView.findViewById(R.id.llNoResult);

        rlDesert = mView.findViewById(R.id.rlDesert);

        btSinc = mView.findViewById(R.id.btSinc);

        tvTexto = mView.findViewById(R.id.tvText);

        tvTitle = mView.findViewById(R.id.tvTitle);

        tvSearch = mView.findViewById(R.id.tvSearch);

        myImage = mView.findViewById(R.id.ivImage);

        mCoordinatorLayout = mView.findViewById(R.id.layoutMain);

        mShimmer = mView.findViewById(R.id.mShimmerLayout);

        mRecycler =  mView.findViewById(R.id.recyclerList);

        mRecycler.setHasFixedSize(true);
        mRecycler.setItemViewCacheSize(20);
        mRecycler.setDrawingCacheEnabled(true);
        mRecycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        mLayout = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        mRecycler.setLayoutManager(mLayout);

        ViewGroup.LayoutParams params = mCoordinatorLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;

        mShimmer.startShimmer();

        bindRecyclerView();

    }

    public void bindRecyclerView(){

        new myAsyncTask().execute();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.sonic_clientes, menu);
        mSearch = menu.findItem(R.id.itemSearch);

        final androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) MenuItemCompat.getActionView(mSearch);
        searchView.setQueryHint("Pesquisar...");
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(allowSearch){
                    Filter.FilterListener listener = new Filter.FilterListener() {
                        @Override
                        public void onFilterComplete(int count) {
                            if (allowSearch) {
                                if (mAdapter.getItemCount()==0) {
                                    tvSearch.setVisibility(VISIBLE);
                                    tvSearch.setText("Nenhum resultado para '"+newText+"'");
                                } else {
                                    tvSearch.setVisibility(GONE);
                                }
                            }
                        }
                    };
                    mAdapter.getFilter().filter(newText, listener);
                }
                return false;
            }
        });

        searchView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {
                mTabLayout.setVisibility(GONE);
                sonicAppearence.searchAppearence(getActivity(),searchView, mToolBar,5,true,true);
            }

            @Override
            public void onViewDetachedFromWindow(View view) {
                mTabLayout.setVisibility(VISIBLE);
                if(!mPrefs.RotaPessoal.getAdding()){
                    sonicAppearence.searchAppearence(getActivity(),searchView, mToolBar,5,false,false);
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemSearch:
                return false;
            default:
                break;
        }
        return false;
    }

    class myAsyncTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {

            mRecentList = mData.Cliente.selectAccessos(true);
            mList =  mData.Cliente.selectClienteTipo(true);
            return mList.size();

        }

        @Override
        protected void onPostExecute(final Integer result) {
            super.onPostExecute(result);

            Handler handler = new Handler();
            handler.postDelayed(() -> {

                        if(result>0){

                            exibirFiltroBusca();
                            exibirItensRecentes();
                            exibirLista();

                        }else{

                            exibirListaVazia();

                        }

                        mShimmer.stopShimmer();
                        mShimmer.setVisibility(GONE);

                    }
                    ,mList.size()>1000 ? mList.size() : 500);


        }
    }

    private void exibirLista(){

        AlphaAnimation fadeIn;
        fadeIn = new AlphaAnimation(0,1);
        fadeIn.setDuration(500);
        fadeIn.setFillAfter(true);
        llNoResult.setVisibility(GONE);
        rlDesert.setVisibility(GONE);
        allowSearch = true;
        mAdapter = new sonicClientesAdapter(mList, mContext, mRecycler, mView, mAct,true);
        mRecycler.setVisibility(VISIBLE);
        mRecycler.setAdapter(mAdapter);
        mRecycler.startAnimation(fadeIn);
        ViewGroup.LayoutParams params = mCoordinatorLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;

    }

    private void exibirListaVazia(){

        AlphaAnimation fadeIn;
        fadeIn = new AlphaAnimation(0,1);
        fadeIn.setDuration(500);
        fadeIn.setFillAfter(true);
        llNoResult.setVisibility(VISIBLE);
        rlDesert.setVisibility(VISIBLE);
        llNoResult.startAnimation(fadeIn);
        rlDesert.startAnimation(fadeIn);
        allowSearch = false;
        myImage.setVisibility(GONE);
        String text = "** Nenhum cliente CNPJ **";
        SpannableString sp = new SpannableString(text);
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryGreen)),18,22, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvTitle.setText(sp);
        tvTexto.setText(R.string.noSincText);
        btSinc.setOnClickListener((View v)->{
            mPrefs.Sincronizacao.setHomeRefresh(true);
            mPrefs.Sincronizacao.setDrawerRefresh(true);
            mPrefs.Sincronizacao.setDownloadType("DADOS");
            mPrefs.Sincronizacao.setCalledActivity(getActivity().getClass().getSimpleName());
            sonicFtp myFtp = new sonicFtp(getActivity());
            String file = mPrefs.Users.getArquivoSinc();
            myFtp.downloadFile2(sonicConstants.FTP_USUARIOS+file, sonicConstants.LOCAL_TEMP+file);
        });

    }

    private void exibirFiltroBusca(){

        llFilterRootView.setVisibility(VISIBLE);
        llGroupFilter.removeAllViews();
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.height = sonicUtils.convertDpToPixel(36, mContext);
        p2.height = sonicUtils.convertDpToPixel(36, mContext);
        p.setMargins(0,0, sonicUtils.convertDpToPixel(6, mContext), 0);
        p2.setMargins(sonicUtils.convertDpToPixel(12, mContext),0, sonicUtils.convertDpToPixel(6, mContext), 0);
        mGroupList = mData.GrupoCliente.selectGrupoCliente("J");
        Button bt1 = new Button(mContext);
        bt1.setText(mPrefs.GrupoCliente.getFiltroCnpj());
        bt1.setTextSize(12);
        bt1.setBackground(mContext.getResources().getDrawable(R.drawable.botao_selecionado));
        bt1.setLayoutParams(p2);
        bt1.setPadding(30,0,30,0);
        llGroupFilter.addView(bt1);
        List<String> grupoAuxiliar;
        grupoAuxiliar = new ArrayList<>();
        grupoAuxiliar.add(mPrefs.GrupoCliente.getFiltroCnpj());
        for(int i=0; i < (mGroupList.size()<TOTAL_ITENS_FILTRO ? mGroupList.size() : TOTAL_ITENS_FILTRO) ;i++){

            grupoAuxiliar.add(mGroupList.get(i).getNome());

            if(!mGroupList.get(i).getNome().contains(mPrefs.GrupoCliente.getFiltroCnpj())){

                Button bt2 = new Button(mContext);

                bt2.setOnClickListener((View v)-> {

                    mPrefs.GrupoCliente.setFiltroCnpj(bt2.getText().toString());

                    ((sonicClientes)mContext).refreshFragments(0);

                });

                bt2.setOnLongClickListener((View v)->{
                    //marcarFiltroPadrao(bt2.getText().toString());
                    return false;
                });

                bt2.setText(mGroupList.get(i).getNome());
                bt2.setTextSize(12);
                bt2.setBackground(mContext.getResources().getDrawable(R.drawable.botao_branco_round));
                bt2.setLayoutParams(p);

                bt2.setPadding(30,0,30,0);
                llGroupFilter.addView(bt2);
            }

        }

        if(mGroupList.size()>TOTAL_ITENS_FILTRO){
            Button bt3 = new Button(mContext);

            bt3.setOnClickListener((View v)-> {
                exibirFiltroExtra(grupoAuxiliar);
            });
            bt3.setText("MAIS...");
            bt3.setTextSize(12);
            bt3.setBackground(mContext.getResources().getDrawable(R.drawable.botao_branco_round));
            bt3.setLayoutParams(p);
            bt3.setPadding(30,0,30,0);
            llGroupFilter.addView(bt3);
        }

    }

    private void exibirItensRecentes(){

        if(mRecentList.size()>0){
            if(llRecenteRootView.getVisibility()!=VISIBLE){
                llRecenteRootView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_in));
                llRecenteRootView.setVisibility(VISIBLE);
            }
            llParentView.setVisibility(VISIBLE);
            llParentView.removeAllViews();
            llRecenteRootView.findViewById(R.id.vSeparador).setVisibility(VISIBLE);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            p.setMargins(sonicUtils.convertDpToPixel(4, mContext),0, 0, 0);
            for(int x=0; x < mRecentList.size(); x++){
                View child = LayoutInflater.from(mContext).inflate(R.layout.chips_atividade_recente_item, null, false);
                child.setLayoutParams(p);
                TextView letra = child.findViewById(R.id.tvLetra);
                ImageView imagem = child.findViewById(R.id.ivImagem);
                TextView descricao = child.findViewById(R.id.tvDescricao);
                TextView grupo = child.findViewById(R.id.tvGrupo);
                String nome = mPrefs.Clientes.getClienteExibicao().equals("Nome Fantasia") ? mRecentList.get(x).getNomeFantasia() : mRecentList.get(x).getRazaoSocial();

                File f = sonicFile.searchImage(myCons.LOCAL_IMG_CLIENTES, mRecentList.get(x).getCodigo());

                if(f.exists()){

                    imagem.setVisibility(View.VISIBLE);
                    letra.setVisibility(View.GONE);
                    Glide.with(mContext)
                            .load(f)
                            .circleCrop()
                            .transition(GenericTransitionOptions.with(R.anim.fade_in))
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(imagem);

                }else{

                    imagem.setVisibility(View.GONE);
                    letra.setVisibility(View.VISIBLE);
                    letra.setText(String.valueOf(nome.charAt(0)).toUpperCase());

                }

                descricao.setText(nome);
                grupo.setText(mRecentList.get(x).getGrupo());

                child.setOnClickListener((View v)->{
                    int index = llParentView.indexOfChild(v);
                    new mAsyncTaskRecentAdd().execute(mRecentList.get(index).getCodigo());
                    mPrefs.Clientes.setCodigo(mRecentList.get(index).getCodigo());
                    mPrefs.Clientes.setNome(nome);
                    mPrefs.Geral.setCallActivity("sonicClientesCNPJ");
                    if(mPrefs.RotaPessoal.getAdding()){
                        mPrefs.RotaPessoal.setClientePicked(true);
                        ((Activity)mContext).finish();
                    }else{
                        Intent i = new Intent(v.getContext(), sonicClientesDetalhe.class);
                        ((Activity)mContext).startActivityForResult(i,1);
                    }

                });
                //@SuppressLint("ResourceType") Transition t = TransitionInflater.from(mContext).inflateTransition(R.transition.teste);
                //TransitionManager.beginDelayedTransition(llParentView, t);

                Animation a = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_left);
                llParentView.addView(child);
                if(llRecenteRootView.getVisibility()==VISIBLE){
                    child.startAnimation(a);
                }

            }
        }
    }

    public void atualizarItensRecentes(){
        new mAsyncTaskRecentList().execute();
    }

    private void exibirFiltroExtra(List<String> mListAuxiliar) {

        List<sonicGrupoClientesHolder> grupo;

        grupo = new sonicDatabaseCRUD(mContext).GrupoCliente.selectGrupoCliente("J");

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
                mPrefs.GrupoCliente.setFiltroCnpj(chars[item].toString());
                ((sonicClientes)mContext).refreshFragments(0);

            }
        }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton(mPrefs.GrupoCliente.getFiltroCnpj().equals("TODOS") ? "" : "LIMPAR FILTRO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mPrefs.GrupoCliente.setFiltroCnpj("TODOS");
                ((sonicClientes)mContext).refreshFragments(0);

            }
        }).show();

    }

    class mAsyncTaskRecentAdd extends AsyncTask<Integer, Void, Void>{

        @Override
        protected Void doInBackground(Integer... integers) {
            mData.Cliente.addAcesso(integers[0]);
            return null;
        }

    }

    class mAsyncTaskRecentList extends AsyncTask<Integer, Integer, Integer>{

        @Override
        protected Integer doInBackground(Integer... integers) {
            mRecentList = mData.Cliente.selectAccessos(true);
            return mRecentList.size();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if(integer>0){
                exibirItensRecentes();
            }
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d("RESUME","");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("PAUSE","");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
