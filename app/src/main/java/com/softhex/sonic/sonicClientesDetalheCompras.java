package com.softhex.sonic;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

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
    HashMap<List<sonicClientesDetalheComprasHolder>,List<sonicClientesDetalheComprasItensHolder>> childList = new HashMap<>();
    private int previousGroup = -1;


    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.sonic_clientes_detalhe_compras, container, false);

        mContext = getContext();
        mPrefs = new sonicPreferences(mContext);
        mData = new sonicDatabaseCRUD(mContext);
        mExpandableList = myView.findViewById(R.id.mExpandableList);
        mExpandableList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousGroup)
                    mExpandableList.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });
        DisplayMetrics metrics = new DisplayMetrics();
        int width = metrics.widthPixels;
        mExpandableList.setIndicatorBounds(width- GetPixelFromDips(50), width - GetPixelFromDips(10));
        loadDetails();

        return myView;

    }

    private void loadDetails(){

        HashMap<List<sonicClientesDetalheComprasHolder>, List<sonicClientesDetalheComprasItensHolder>> hashMap = new HashMap<>();
        List<sonicClientesDetalheComprasHolder> header = new ArrayList<>();
        List<sonicClientesDetalheComprasItensHolder> child = new ArrayList<>();

        mList = mData.Cliente.selectComprasPorCliente(mPrefs.Clientes.getId(), 5);
        mListItens  = mData.Cliente.selectComprasPorClienteItens(mPrefs.Clientes.getId());

        for(int i=0; i< mList.size();i++){
            header.add(mList.get(i));
        }
        for(int i=0; i< mListItens.size();i++){
            child.add(mListItens.get(i));

        }

        //for(int i =0 ; i< header.size(); i++){
            hashMap.put(header, child);
        //}

        mAdapter = new sonicClientesDetalheComprasAdapter(mContext, header, hashMap);
        mExpandableList.setAdapter(mAdapter);

    }

    public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

}
