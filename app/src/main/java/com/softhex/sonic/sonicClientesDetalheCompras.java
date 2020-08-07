package com.softhex.sonic;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Administrador on 21/07/2017.
 */

public class sonicClientesDetalheCompras extends Fragment {

    private View mView;
    private Context mContext;
    private sonicClientesDetalheComprasAdapter mAdapter;
    private ExpandableListView mExpandableList;
    private List<sonicClientesDetalheComprasHolder> mList;
    private List<sonicClientesDetalheComprasItensHolder> mListItens;
    private sonicDatabaseCRUD mData;
    private sonicPreferences mPrefs;
    private HashMap<String, List<sonicClientesDetalheComprasItensHolder>> mHash;
    private List<String> headerData;
    private List<sonicClientesDetalheComprasItensHolder> childData;
    private ProgressBar mProgressGroup;
    private ArrayList<Integer> groupClicked;
    //private TabLayout mTabLayout;
    private ShimmerFrameLayout mShimmer;
    private LinearLayout llMensagem;
    private TextView tvTitle;
    private TextView tvMensagem;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.sonic_clientes_detalhe_compras, container, false);

        // TO AVOID FRAGMENT REFRESH ON SWIPE TABS/PAGES
        //setRetainInstance(true);

        mContext = getContext();
        mPrefs = new sonicPreferences(mContext);
        mData = new sonicDatabaseCRUD(mContext);

        loadFragment();

        bindRecyclerView();

        return mView;

    }

    public void bindRecyclerView(){

        new mAsyncLoadGroups().execute();

    }

    private void loadFragment(){

        //mTabLayout = getActivity().findViewById(R.id.mTabs);
        llMensagem = mView.findViewById(R.id.llMensagem);
        tvTitle = mView.findViewById(R.id.tvTitle);
        tvMensagem = mView.findViewById(R.id.tvMensagem);
        mShimmer = mView.findViewById(R.id.mShimmerLayout);
        mExpandableList = mView.findViewById(R.id.mExpandableList);
        groupClicked = new ArrayList<>();

        mShimmer.startShimmer();

        mExpandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                mProgressGroup = mAdapter.getGroupView(mExpandableList, groupPosition).findViewById(R.id.pbGroup);
                if(mExpandableList.isGroupExpanded(groupPosition)){
                    mExpandableList.collapseGroup(groupPosition);

                }else{
                    if(!groupClicked.contains(groupPosition)){
                        mProgressGroup.setVisibility(View.VISIBLE);
                    }else{
                        mProgressGroup.setVisibility(GONE);
                    }
                    new mAsyncLoadItens().execute(groupPosition);
                }
                return true;
            }
        });
        mExpandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                sonicClientesDetalheComprasItensHolder mItens = (sonicClientesDetalheComprasItensHolder)mAdapter.getChild(groupPosition,childPosition);
                mPrefs.Produtos.setProdutoId(mItens.getCodigoProduto());
                mPrefs.Produtos.setProdutoNome(mItens.getProduto());
                mPrefs.Produtos.setProdutoGrupo(mItens.getGrupo());
                mPrefs.Produtos.setDetalhe("Cód.: "+mItens.getCodigoProduto()+" / Ref.: "+mItens.getReferencia());
                Intent i = new Intent(mContext, sonicProdutosDetalhe.class);
                mContext.startActivity(i);

                return false;
            }
        });

    }

    class mAsyncLoadGroups extends AsyncTask<Void, Void, Integer>{
        @Override
        protected Integer doInBackground(Void... voids) {
            headerData = new ArrayList<>();
            mList = mData.Cliente.selectComprasPorCliente(mPrefs.Clientes.getCodigo(), 100);
            mHash = new HashMap<>();

            for(int i=0; i < mList.size(); i++){
                headerData.add(String.valueOf(mList.get(i).getCodigo()));
                childData = new ArrayList<>();
                mHash.put(headerData.get(i),childData);
            }

            return mList.size();
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        if(result>0){

                                            showResult();

                                        }else{

                                            showNoResult();

                                        }

                                        mShimmer.stopShimmer();
                                        mShimmer.setVisibility(GONE);

                                    }
                                }
                    , mList.size()>1000 ? mList.size() : 500);

        }
    }

    class mAsyncLoadItens extends AsyncTask<Integer, Void, Integer>{
        @Override
        protected Integer doInBackground(Integer... integer) {

            mHash = new HashMap<>();
            childData = new ArrayList<>();
            mListItens = mData.Cliente.selectComprasPorClienteItens(mList.get(integer[0]).getCodigo());
            for(int x=0 ; x < mListItens.size(); x++){
                childData.add(mListItens.get(x));
            }
            mHash.put(headerData.get(integer[0]),childData);

            return integer[0];
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            checkResultItem(result);
        }
    }

    public void checkResultItem(Integer result){

        Handler handler = new Handler();
        if(!groupClicked.contains(result)){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mAdapter = new sonicClientesDetalheComprasAdapter(mContext, headerData, mList, mHash);
                    mExpandableList.setAdapter(mAdapter);
                    mExpandableList.expandGroup(result);
                    mProgressGroup.setVisibility(View.INVISIBLE);
                }
             },mListItens.size()*100);
        }else{
            mAdapter = new sonicClientesDetalheComprasAdapter(mContext, headerData, mList, mHash);
            mExpandableList.setAdapter(mAdapter);
            mExpandableList.expandGroup(result);
        }
        groupClicked.add(result);
    }

    private void showResult(){

        AlphaAnimation fadeIn;
        fadeIn = new AlphaAnimation(0,1);
        fadeIn.setDuration(500);
        fadeIn.setFillAfter(true);

        mAdapter = new sonicClientesDetalheComprasAdapter(mContext, headerData, mList, mHash);
        mExpandableList.setAdapter(mAdapter);
        mExpandableList.setVisibility(VISIBLE);
        mExpandableList.startAnimation(fadeIn);

    }

    private void showNoResult(){

        AlphaAnimation fadeIn;
        fadeIn = new AlphaAnimation(0,1);
        fadeIn.setDuration(500);
        fadeIn.setFillAfter(true);
        llMensagem.setVisibility(VISIBLE);
        llMensagem.startAnimation(fadeIn);
        tvTitle.setText("COMPRAS");
        tvMensagem.setText(R.string.nenhumaCompra);

    }

}
