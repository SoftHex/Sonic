package com.softhex.sonic;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Administrador on 21/07/2017.
 */

public class sonicAvisosFragNaoLidos extends Fragment{

    private RecyclerView myRecycler;
    private sonicAvisosAdapter myAdapter;
    private RecyclerView.LayoutManager myLayout;
    private List<sonicAvisosHolder> avisos;
    private sonicDatabaseCRUD DBC;
    private CoordinatorLayout myCoordinatorLayout;
    private ProgressBar myProgress;
    private Boolean allowRefresh = false;
    private TextView myTextViewText;
    private View myView;


    @Nullable

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.sonic_recycler_layout_list, container, false);

        loadFragment();

        return myView;

    }

    public void bindRecyclerView(){

        new myAsyncTask().execute();

    }

    public void loadFragment(){

        myTextViewText = (TextView)myView.findViewById(R.id.text);

        myCoordinatorLayout = (CoordinatorLayout)myView.findViewById(R.id.layout_main);

        myRecycler = (RecyclerView) myView.findViewById(R.id.recycler_list);

        myRecycler.setHasFixedSize(true);

        myLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        myRecycler.setLayoutManager(myLayout);

        ViewGroup.LayoutParams params = myCoordinatorLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        bindRecyclerView();

    }

    class myAsyncTask extends AsyncTask<Integer, Integer, Integer> {


        @Override
        protected Integer doInBackground(Integer... integers) {

            DBC = new sonicDatabaseCRUD(getActivity());
            avisos = DBC.Avisos.selectAvisos();
            return avisos.size();
        }

        @Override
        protected void onPostExecute(final Integer result) {
            super.onPostExecute(result);

            final AlphaAnimation fadeOut;
            final AlphaAnimation fadeIn;
            fadeIn = new AlphaAnimation(0,1); //fade in animation from 0 (transparent) to 1 (fully visible)
            fadeOut = new AlphaAnimation(1,0); //fade out animation from 1 (fully visible) to 0 (transparent)
            fadeIn.setDuration(500); //set duration in mill seconds
            fadeOut.setDuration(500); //set duration in mill seconds
            fadeIn.setFillAfter(true);
            fadeOut.setFillAfter(true);

            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        if(result>0){
                                            myAdapter = new sonicAvisosAdapter(avisos, getActivity());
                                            myRecycler.setAdapter(myAdapter);
                                            ViewGroup.LayoutParams params = myCoordinatorLayout.getLayoutParams();
                                            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                                        }else{
                                            myTextViewText.setText("No Messages");
                                            myTextViewText.setVisibility(View.VISIBLE);
                                            myTextViewText.startAnimation(fadeIn);
                                        }

                                        //myProgress.startAnimation(fadeOut);
                                        //myProgress.setVisibility(View.GONE);


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
