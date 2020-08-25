package com.softhex.sonic;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Administrador on 07/07/2017.
 */

public class sonicSincronizacaoDownload extends Fragment {

    private View myView;
    private ShimmerFrameLayout myShimmer;
    private LinearLayout myLinearLayout;
    private List<sonicSincronizacaoDownloadHolder> myList;
    private sonicSincronizacaoDownloadAdapterT myAdapter;
    private RecyclerView myRecycler;
    private sonicDatabaseCRUD mData;
    private String[][] mSincs = {
            {"DADOS GERAIS","Clientes, Produtos, Estoque.", sonicConstants.TB_TODAS, "dados"},
            {"CATÁLOGO","Imagem dos Produtos.", sonicConstants.TB_PRODUTOS, "imagens"},
            {"IMAGENS","Imagem das Lojas dos Clientes.", sonicConstants.TB_CLIENTES ,"imagens"},
            {"ESTOQUE","Estoque dos Produtos.", sonicConstants.TB_ESTOQUE_PRODUTOS,  "dados"}
    };
    private Integer[] mImages = {R.mipmap.dados, R.mipmap.catalogo, R.mipmap.lojas, R.mipmap.estoque};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.sonic_sincronizacao_download, container, false);

        mData = new sonicDatabaseCRUD(getContext());
        loadFragment(myView);

        return myView;

    }

    public void bindRecyclerView(){

        new myAsyncTask().execute();

    }

    public void loadFragment(View view){

        myRecycler = view.findViewById(R.id.recycler);
        myShimmer = view.findViewById(R.id.shimmer);
        myShimmer.startShimmer();

        bindRecyclerView();

    }

    class myAsyncTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {

            List<String[]> list = Arrays.asList(mSincs);
            List<sonicSincronizacaoDownloadHolder> lDtHora = new ArrayList<>();
            myList = new ArrayList<>();
            for(int i=0; i<list.size(); i++){
                lDtHora = mData.Sincronizacao.selectLastSinc(mSincs[i][2],mSincs[i][3]);
                myList.add(new sonicSincronizacaoDownloadHolder(
                        mSincs[i][0],mSincs[i][1],
                        lDtHora.get(0).getData(),
                        lDtHora.get(0).getHora(),
                        mImages[i]));
            }

            return myList.size();

        }

        @Override
        protected void onPostExecute(final Integer result) {
            super.onPostExecute(result);

            AlphaAnimation fadeOut;
            fadeOut = new AlphaAnimation(1,0);
            fadeOut.setDuration(1000);
            fadeOut.setFillAfter(true);
            myShimmer.setAnimation(fadeOut);

            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(() -> {

                        if(result>0){

                            showResult();

                        }

                        myShimmer.stopShimmer();
                        myShimmer.setVisibility(GONE);

                    }
                    ,sonicUtils.Randomizer.generate(500,1000));


        }
    }

    public void showResult(){

        AlphaAnimation fadeIn;
        fadeIn = new AlphaAnimation(0,1);
        fadeIn.setDuration(1000);
        fadeIn.setFillAfter(true);

        myAdapter = new sonicSincronizacaoDownloadAdapterT(getActivity(), myList);
        myRecycler.setVisibility(VISIBLE);
        myRecycler.setAnimation(fadeIn);
        myRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        myRecycler.setAdapter(myAdapter);

    }

}


