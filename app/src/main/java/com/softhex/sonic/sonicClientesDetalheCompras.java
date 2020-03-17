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
    List<String> main_title = new ArrayList<>();
    List<String> android_list = new ArrayList<>();
    List<String> java_list = new ArrayList<>();
    List<String> php_list = new ArrayList<>();
    HashMap<String,List<String>> child_list = new HashMap<String,List<String>>();
    private int previousGroup = -1;


    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.sonic_clientes_detalhe_compras, container, false);

        mContext = getContext();

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

        String[] cource_title = getResources().getStringArray(R.array.cource_title);
        String[] android_items = getResources().getStringArray(R.array.android_item);
        String[] java_items = getResources().getStringArray(R.array.java_item);
        String[] php_items = getResources().getStringArray(R.array.php_item);

        main_title.clear();
        android_list.clear();
        java_list.clear();
        php_list.clear();
        for(String heading : cource_title)
        {
            main_title.add(heading);
        }
        for (String android : android_items)
        {
            android_list.add(android);
        }
        for (String java : java_items)
        {
            java_list.add(java);
        }
        for (String php : php_items)
        {
            php_list.add(php);
        }

        child_list.put(main_title.get(0),android_list);
        child_list.put(main_title.get(1),java_list);
        child_list.put(main_title.get(2),php_list);

        mAdapter = new sonicClientesDetalheComprasAdapter(mContext, main_title, child_list);
        mExpandableList.setAdapter(mAdapter);

    }

    public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

}
