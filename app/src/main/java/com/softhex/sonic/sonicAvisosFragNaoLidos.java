package com.softhex.sonic;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import static android.view.View.VISIBLE;

/**
 * Created by Administrador on 21/07/2017.
 */

public class sonicAvisosFragNaoLidos extends Fragment {

    private RecyclerView myRecycler;
    private sonicAvisosAdapter myAdapter;
    private RecyclerView.LayoutManager myLayout;
    private List<sonicAvisosHolder> myAvisos;
    private CoordinatorLayout myCoordinatorLayout;
    private Boolean allowRefresh = false;
    private TextView myTextViewText;
    private View myView;
    private ImageView myImage;
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

        myImage = myView.findViewById(R.id.image);

        myCoordinatorLayout = myView.findViewById(R.id.layout_main);

        myShimmer =  myView.findViewById(R.id.shimmer);
        myShimmer.startShimmer();

        myRecycler = myView.findViewById(R.id.recycler_list);

        myRecycler.setHasFixedSize(true);

        myLayout = new LinearLayoutManager(_this, RecyclerView.VERTICAL, false);
        myRecycler.setLayoutManager(myLayout);

        ViewGroup.LayoutParams params = myCoordinatorLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;

        bindRecyclerView();

    }

    class myAsyncTask extends AsyncTask<Integer, Integer, Integer> {


        @Override
        protected Integer doInBackground(Integer... integers) {

            myAvisos =  new sonicDatabaseCRUD(_this).Aviso.selectAviso();
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

                                            myImage.setVisibility(VISIBLE);
                                            Glide.with(_this)
                                                    .load(R.drawable.nomessage)
                                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                                    .skipMemoryCache(true)
                                                    //.apply(new RequestOptions().override(300,300))
                                                    .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                                                    .into(myImage);
                                            myTextViewText.setText("Sem avisos por enquanto.");
                                            myTextViewText.setVisibility(View.VISIBLE);
                                            myTextViewText.startAnimation(fadeIn);
                                        }

                                        myShimmer.stopShimmer();
                                        myShimmer.setVisibility(View.GONE);


                                    }
                                }
                    , 700);


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
