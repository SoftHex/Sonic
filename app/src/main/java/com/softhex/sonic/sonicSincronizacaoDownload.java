package com.softhex.sonic;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.sonic_sincronizacao_download, container, false);

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
        myLinearLayout = myView.findViewById(R.id.sync);

        bindRecyclerView();

    }

    class myAsyncTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {

            myList = new ArrayList<>();
            myList.add(new sonicSincronizacaoDownloadHolder("Dados Gerais", "Clientes, Produtos, retorno...", R.mipmap.dados));
            myList.add(new sonicSincronizacaoDownloadHolder("Catálogo", "Foto dos produtos.", R.mipmap.catalogo));
            myList.add(new sonicSincronizacaoDownloadHolder("Imagens", "Foto da loja dos clientes.", R.mipmap.lojas));
            myList.add(new sonicSincronizacaoDownloadHolder("Estoque", "Atualiza estoque dos produtos.", R.mipmap.estoque));
            myList.add(new sonicSincronizacaoDownloadHolder("Localização", "Localização dos vendedores", R.mipmap.lugar));

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
                    ,sonicUtils.Randomizer.generate(500,1500));


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
        myRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        myRecycler.setAdapter(myAdapter);
        myLinearLayout.setVisibility(VISIBLE);
        myLinearLayout.setAnimation(fadeIn);

    }

}


