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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

    private View myView;
    private RecyclerView myRecycler;
    private RecyclerView.LayoutManager myLayout;
    private sonicClientesDetalheTitulosAdapter myAdapter;
    private List<sonicTitulosHolder> myList;
    private CoordinatorLayout myCoordinatorLayout;
    private ShimmerFrameLayout myShimmer;
    private TextView tvTexto, tvTitle, tvSearch;
    private Context mContext;
    private ImageView myImage;
    private sonicDatabaseCRUD DBC;
    private Intent i;
    private sonicPreferences mPrefs;
    private LinearLayout llNoResult;
    private RelativeLayout rlDesert;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.sonic_recycler_layout_detalhe, container, false);

        mContext = getActivity();

        DBC = new sonicDatabaseCRUD(mContext);

        mPrefs = new sonicPreferences(getActivity());

        loadFragment();

        return myView;

    }

    public void bindRecyclerView(){

        new myAsyncTask().execute();

    }

    public void loadFragment(){


        //myCoordinatorLayout = myView.findViewById(R.id.layoutMain);

        myShimmer = myView.findViewById(R.id.mShimmerLayout);

        myRecycler =  myView.findViewById(R.id.recyclerList);

        myRecycler.setHasFixedSize(true);

        myLayout = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        myRecycler.setLayoutManager(myLayout);

        //rlDesert = myView.findViewById(R.id.rlDesert);

        myShimmer.startShimmer();

        bindRecyclerView();


    }

    class myAsyncTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {

            myList =  new sonicDatabaseCRUD(mContext).Titulo.selectTitulosPorCliente(mPrefs.Clientes.getCodigo());
            return myList.size();

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
                    , 700);


        }
    }

    private void showResult(){

        AlphaAnimation fadeIn;
        fadeIn = new AlphaAnimation(0,1);
        fadeIn.setDuration(500);
        fadeIn.setFillAfter(true);

        myAdapter = new sonicClientesDetalheTitulosAdapter(myList, mContext, myRecycler);
        if(!myAdapter.hasObservers()){
            myAdapter.setHasStableIds(true);
        }
        myRecycler.setVisibility(VISIBLE);
        myRecycler.setAdapter(myAdapter);
        myRecycler.startAnimation(fadeIn);
        //ViewGroup.LayoutParams params = myCoordinatorLayout.getLayoutParams();
        //params.height = ViewGroup.LayoutParams.WRAP_CONTENT;

    }

    private void showNoResult(){

        //ViewGroup.LayoutParams params = myCoordinatorLayout.getLayoutParams();
        //params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        //rlDesert.setVisibility(VISIBLE);

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
