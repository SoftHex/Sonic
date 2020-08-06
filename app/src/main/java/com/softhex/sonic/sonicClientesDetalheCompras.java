package com.softhex.sonic;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrador on 21/07/2017.
 */

public class sonicClientesDetalheCompras extends Fragment {

    private View myView;
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
    private ProgressBar pbGroup;
    private ArrayList<Integer> groupClicked;
    private TabLayout mTabLayout;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.sonic_clientes_detalhe_compras, container, false);

        mContext = getContext();
        mPrefs = new sonicPreferences(mContext);
        mData = new sonicDatabaseCRUD(mContext);
        mTabLayout = getActivity().findViewById(R.id.mTabs);
        mExpandableList = myView.findViewById(R.id.mExpandableList);
        groupClicked = new ArrayList<>();

        new mAsyncLoadGroups().execute();

        mExpandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                pbGroup = mAdapter.getGroupView(mExpandableList, groupPosition).findViewById(R.id.pbGroup);
                if(mExpandableList.isGroupExpanded(groupPosition)){
                    mExpandableList.collapseGroup(groupPosition);
                }else{
                    if(!groupClicked.contains(groupPosition)){
                        pbGroup.setVisibility(View.VISIBLE);
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
                mPrefs.Produtos.setDetalhe("CÓD.: "+mItens.getCodigoProduto()+" / REF.: "+mItens.getReferencia());
                Intent i = new Intent(mContext, sonicProdutosDetalhe.class);
                mContext.startActivity(i);

                return false;
            }
        });

        return myView;

    }

    class mAsyncLoadGroups extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            headerData = new ArrayList<>();
            mList = mData.Cliente.selectComprasPorCliente(mPrefs.Clientes.getCodigo(), 100);
            mHash = new HashMap<>();

            for(int i=0; i < mList.size(); i++){
                headerData.add(String.valueOf(mList.get(i).getCodigo()));
                childData = new ArrayList<>();
                mHash.put(headerData.get(i),childData);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            checkResultGroup();
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

    public void checkResultGroup(){
        if(mList.size()>0){
            mAdapter = new sonicClientesDetalheComprasAdapter(mContext, headerData, mList, mHash);
            mExpandableList.setAdapter(mAdapter);

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
                    pbGroup.setVisibility(View.INVISIBLE);
                }
             },mListItens.size()*100);
        }else{
            mAdapter = new sonicClientesDetalheComprasAdapter(mContext, headerData, mList, mHash);
            mExpandableList.setAdapter(mAdapter);
            mExpandableList.expandGroup(result);
        }
        groupClicked.add(result);
    }

}
