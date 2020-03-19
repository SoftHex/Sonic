package com.softhex.sonic;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
    private int previousGroup = -1;
    private TextView tvTotalPedidos;
    private HashMap<String, List<sonicClientesDetalheComprasItensHolder>> mHash;
    private List<String> headerData;
    private List<sonicClientesDetalheComprasItensHolder> childData;
    private LinearLayout linearPedidos;
    private LinearLayout llSemResultado;
    private TextView tvSemResultado;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.sonic_clientes_detalhe_compras, container, false);

        mContext = getContext();
        mPrefs = new sonicPreferences(mContext);
        mData = new sonicDatabaseCRUD(mContext);
        linearPedidos = myView.findViewById(R.id.linearPedidos);
        tvTotalPedidos = myView.findViewById(R.id.tvTotalPedidos);
        mExpandableList = myView.findViewById(R.id.mExpandableList);
        llSemResultado = myView.findViewById(R.id.llSemResultado);
        tvSemResultado = myView.findViewById(R.id.tvSeResultado);

        //loadDetails();
        new mAsyncTask().execute();

        return myView;

    }

    public void loadDetails(){

        if(mList.size()==0){
            linearPedidos.setVisibility(View.GONE);
            llSemResultado.setVisibility(View.VISIBLE);
            tvSemResultado.setText(mPrefs.Clientes.getClienteSemCompra() ? "Cliente ainda não possui compra com a empresa..." : "Cliente sem compra nos últimos seis meses...");
        }else{
            tvTotalPedidos.setText(mList.size()==1 ? "Total de "+mList.size()+" pedido nos últimos seis mêses..." : "Total de "+mList.size()+" pedidos nos últimos seis meses...");
            mAdapter = new sonicClientesDetalheComprasAdapter(mContext, headerData, mList, mHash);
            mExpandableList.setAdapter(mAdapter);
            mExpandableList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {
                    if(groupPosition != previousGroup)
                        mExpandableList.collapseGroup(previousGroup);
                    previousGroup = groupPosition;
                }
            });
            mExpandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    sonicClientesDetalheComprasItensHolder mItens = (sonicClientesDetalheComprasItensHolder)mAdapter.getChild(groupPosition,childPosition);

                        mPrefs.Produtos.setProdutoId(mItens.getCodigoProduto());
                        mPrefs.Produtos.setProdutoNome(mItens.getProduto());
                        mPrefs.Produtos.setProdutoGrupo(mItens.getGrupo());
                        mPrefs.Produtos.setDetalhe("CÓD.: "+mItens.getCodigoProduto()+" / REFERÊNCIA: "+mItens.getReferencia());
                        Intent i = new Intent(mContext, sonicProdutosDetalhe.class);
                        mContext.startActivity(i);

                    return false;
                }
            });
            //DisplayMetrics metrics = new DisplayMetrics();
            //int width = metrics.widthPixels;
            //mExpandableList.setIndicatorBounds(width- GetPixelFromDips(50), width - GetPixelFromDips(10));
        }
    }

    class mAsyncTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            headerData = new ArrayList<>();
            mList = mData.Cliente.selectComprasPorCliente(mPrefs.Clientes.getId(), 100);
            mHash = new HashMap<>();

            for(int i=0; i < mList.size(); i++){
                headerData.add(String.valueOf(mList.get(i).getCodigo()));
                childData = new ArrayList<>();
                mListItens = mData.Cliente.selectComprasPorClienteItens(mList.get(i).getCodigo());
                for(int x=0 ; x < mListItens.size(); x++){
                    childData.add(mListItens.get(x));
                }
                mHash.put(headerData.get(i),childData);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loadDetails();
        }
    }

    public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

}
