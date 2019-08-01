package com.softhex.sonic;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.hookedonplay.decoviewlib.DecoView;
import com.jackandphantom.circularprogressbar.CircleProgressbar;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.SEARCH_SERVICE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Administrador on 21/07/2017.
 */

public class sonicMainHome extends Fragment {

    private View myView;
    private RecyclerView myRecycler;
    private RecyclerView.LayoutManager myLayout;
    private sonicMainAdapter myAdapter;
    private List<sonicSistemaLogHolder> myErros;
    private CoordinatorLayout myCoordinatorLayout;
    private TextView pedidos;
    private Context _this;
    private CircleProgressbar myCirclePogress;
    private TextView empresa;


    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.layout_main_vendedor, container, false);

        _this = getActivity();

        loadFragment();

        return myView;

    }

    public void bindRecyclerView(){

        new myAsyncTask().execute();

    }

    public void loadFragment(){

        myRecycler =  myView.findViewById(R.id.recycler_list);
        myRecycler.setHasFixedSize(true);

        myCoordinatorLayout = myView.findViewById(R.id.layout_main);

        myLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        myRecycler.setLayoutManager(myLayout);

        ViewGroup.LayoutParams params = myCoordinatorLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;

        pedidos = myView.findViewById(R.id.pedidos);
       // myCirclePogress = myView.findViewById(R.id.progress);
        empresa = myView.findViewById(R.id.empresa);
        empresa.setText(sonicConstants.EMPRESA_SELECIONADA_NOME);
        //DecoView arcView = myView.findViewById(R.id.arcView);
        //String url = Environment.getExternalStorageDirectory().getPath()+sonicConstants.LOCAL_IMG_PERFIL+7+"_"+55+".jpg";
        //arcView.setBackground(sonicUtils.stringToDrawable(url));
        //myCirclePogress.setProgressWithAnimation(80);
        /*Glide.with(_this)
                .load(R.drawable.nopeople)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .transition(GenericTransitionOptions.with(R.anim.fade_in))
                .into((Drawable)myCirclePogress.getBackground());*/
        //String url = Environment.getExternalStorageDirectory().getPath()+sonicConstants.LOCAL_IMG_PERFIL+7+"_"+55+".jpg";
        //myCirclePogress.setBackground(sonicUtils.stringToDrawable(url));
        //myCirclePogress.
        pedidos.setText("0");


        bindRecyclerView();

    }

    class myAsyncTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {

          return null;
        }

        @Override
        protected void onPostExecute(final Integer result) {
            super.onPostExecute(result);


            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {



                                    }
                                }
                    , 500);


        }
    }

}