package com.softhex.sonic;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import static android.view.View.VISIBLE;

/**
 * Created by Administrador on 21/07/2017.
 */

public class sonicAvisosFragNaoLidos extends Fragment{

    private RecyclerView myRecycler;
    private sonicAvisosAdapter myAdapter;
    private RecyclerView.LayoutManager myLayout;
    private List<sonicAvisosHolder> myAvisos;
    private CoordinatorLayout myCoordinatorLayout;
    private Boolean allowRefresh = false;
    private TextView myTextViewText;
    private View myView;
    private ShimmerFrameLayout myShimmer;
    private Context _this;


    @Nullable

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.sonic_recycler_layout_list, container, false);

        _this = getContext();

        loadFragment();

        return myView;

    }

    public void bindRecyclerView(){

        new myAsyncTask().execute();

    }

    public void loadFragment(){

        setHasOptionsMenu(true);

        myTextViewText = myView.findViewById(R.id.text);

        myCoordinatorLayout = myView.findViewById(R.id.layout_main);

        myShimmer =  myView.findViewById(R.id.shimmer);
        myShimmer.startShimmer();

        myRecycler = myView.findViewById(R.id.recycler_list);

        myRecycler.setHasFixedSize(true);

        myLayout = new LinearLayoutManager(_this, LinearLayoutManager.VERTICAL, false);
        myRecycler.setLayoutManager(myLayout);

        ViewGroup.LayoutParams params = myCoordinatorLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;

        bindRecyclerView();

    }

    class myAsyncTask extends AsyncTask<Integer, Integer, Integer> {


        @Override
        protected Integer doInBackground(Integer... integers) {

            myAvisos =  new sonicDatabaseCRUD(_this).Avisos.selectAvisos();
            return myAvisos.size();

        }

        @Override
        protected void onPostExecute(final Integer result) {
            super.onPostExecute(result);

            final AlphaAnimation fadeOut;
            final AlphaAnimation fadeIn;
            fadeIn = new AlphaAnimation(0,1);
            fadeOut = new AlphaAnimation(1,0);
            fadeIn.setDuration(500);
            fadeOut.setDuration(500);
            fadeIn.setFillAfter(true);
            fadeOut.setFillAfter(true);
            myShimmer.setAnimation(fadeOut);

            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        if(result>0){
                                            myAdapter = new sonicAvisosAdapter(myAvisos, getActivity());
                                            myRecycler.setVisibility(VISIBLE);
                                            myRecycler.setAnimation(fadeIn);
                                            myRecycler.setAdapter(myAdapter);
                                            ViewGroup.LayoutParams params = myCoordinatorLayout.getLayoutParams();
                                            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                                        }else{
                                            myTextViewText.setText("No Messages");
                                            myTextViewText.setVisibility(View.VISIBLE);
                                            myTextViewText.startAnimation(fadeIn);
                                        }

                                        myShimmer.stopShimmer();
                                        myShimmer.setVisibility(View.GONE);


                                    }
                                }
                    , 500);


        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(allowRefresh){
            allowRefresh = false;
        }else{
            allowRefresh = true;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if(allowRefresh){
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if(ft !=null && !ft.isEmpty()){
                ft.detach(this).attach(this).commit();
            }

        }

    }

}
