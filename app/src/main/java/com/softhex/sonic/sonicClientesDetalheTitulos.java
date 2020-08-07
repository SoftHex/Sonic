package com.softhex.sonic;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Administrador on 21/07/2017.
 */

public class sonicClientesDetalheTitulos extends Fragment {

    private View mView;
    private RecyclerView myRecycler;
    private RecyclerView.LayoutManager myLayout;
    private sonicClientesDetalheTitulosAdapter myAdapter;
    private List<sonicTitulosHolder> mList;
    private CoordinatorLayout myCoordinatorLayout;
    private ShimmerFrameLayout myShimmer;
    private Context mContext;
    private sonicPreferences mPrefs;
    private LinearLayout llMensagem;
    private TextView tvTitle;
    private TextView tvMensagem;


    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.sonic_recycler_layout_detalhe, container, false);

        mContext = getActivity();

        mPrefs = new sonicPreferences(getActivity());

        loadFragment();

        return mView;

    }

    public void bindRecyclerView(){

        new myAsyncTask().execute();

    }

    public void loadFragment(){

        //myCoordinatorLayout = mView.findViewById(R.id.layoutMain);

        llMensagem = mView.findViewById(R.id.llMensagem);

        tvTitle = mView.findViewById(R.id.tvTitle);

        tvMensagem = mView.findViewById(R.id.tvMensagem);

        myShimmer = mView.findViewById(R.id.mShimmerLayout);

        myRecycler =  mView.findViewById(R.id.recyclerList);

        myRecycler.setHasFixedSize(true);

        myLayout = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        myRecycler.setLayoutManager(myLayout);

        myShimmer.startShimmer();

        bindRecyclerView();


    }

    class myAsyncTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {

            mList =  new sonicDatabaseCRUD(mContext).Titulo.selectTitulosPorCliente(mPrefs.Clientes.getCodigo());
            return mList.size();

        }

        @Override
        protected void onPostExecute(final Integer result) {
            super.onPostExecute(result);

            AlphaAnimation fadeOut;
            fadeOut = new AlphaAnimation(1,0);
            fadeOut.setDuration(500);
            fadeOut.setFillAfter(true);
            myShimmer.setAnimation(fadeOut);

            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        if(result>0){

                                           showResult();

                                        }else{

                                           showNoResult();

                                        }

                                        myShimmer.stopShimmer();
                                        myShimmer.setVisibility(GONE);

                                    }
                                }
                    , mList.size()>1000 ? mList.size() : 500);


        }
    }

    private void showResult(){

        AlphaAnimation fadeIn;
        fadeIn = new AlphaAnimation(0,1);
        fadeIn.setDuration(500);
        fadeIn.setFillAfter(true);

        myAdapter = new sonicClientesDetalheTitulosAdapter(mList, mContext, myRecycler);
        if(!myAdapter.hasObservers()){
            myAdapter.setHasStableIds(true);
        }
        myRecycler.setVisibility(VISIBLE);
        myRecycler.setAdapter(myAdapter);
        myRecycler.startAnimation(fadeIn);

    }

    private void showNoResult(){

        AlphaAnimation fadeIn;
        fadeIn = new AlphaAnimation(0,1);
        fadeIn.setDuration(500);
        fadeIn.setFillAfter(true);
        llMensagem.setVisibility(VISIBLE);
        llMensagem.startAnimation(fadeIn);
        tvTitle.setText("TITULOS");
        tvMensagem.setText(R.string.nenhumTitulo);

    }

    public void refreshFragment(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
