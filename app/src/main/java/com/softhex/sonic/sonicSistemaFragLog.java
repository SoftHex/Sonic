package com.softhex.sonic;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.bumptech.glide.request.RequestOptions;
import com.facebook.shimmer.ShimmerFrameLayout;
import java.util.List;
import static android.content.Context.SEARCH_SERVICE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Administrador on 21/07/2017.
 */

public class sonicSistemaFragLog extends Fragment{

    private View myView;
    private View myHeader;
    private RecyclerView myRecycler;
    private RecyclerView.LayoutManager myLayout;
    private sonicSistemaLogAdapter myAdapter;
    private List<sonicSistemaLogHolder> myErros;
    private sonicDatabaseLogCRUD DBC;
    private MenuItem mySearch;
    private Toolbar myToolBar;
    private TabLayout myTabLayout;
    private CoordinatorLayout myCoordinatorLayout;
    private ShimmerFrameLayout myShimmer;
    private TextView myTextView;
    private sonicConstants myCons;
    private boolean allowSearch;
    private ProgressDialog myProgressDialog;
    private Context _this;
    private ImageView myImage;
    Intent i;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myHeader = inflater.inflate(R.layout.header, container, false);
        myView = inflater.inflate(R.layout.sonic_recycler_layout_log, container, false);

        _this = getActivity();

        loadFragment();

        return myView;

    }

    public void bindRecyclerView(){

        new myAsyncTask().execute();

    }

    public void loadFragment(){

        setHasOptionsMenu(true);

        myCons = new sonicConstants();

        DBC = new sonicDatabaseLogCRUD(getActivity());

        myToolBar = getActivity().findViewById(R.id.toolbar);

        myTabLayout = getActivity().findViewById(R.id.tabs);

        myTextView = myView.findViewById(R.id.text);

        myImage = myView.findViewById(R.id.image);

        myCoordinatorLayout = myView.findViewById(R.id.layout_main);

        myShimmer = myView.findViewById(R.id.shimmer);

        myRecycler =  myView.findViewById(R.id.recycler_list);

        myRecycler.setHasFixedSize(true);

        myLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        myRecycler.setLayoutManager(myLayout);

        ViewGroup.LayoutParams params = myCoordinatorLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;

        myShimmer.startShimmer();

        bindRecyclerView();


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.sonic_sistema_log, menu);
        mySearch = menu.findItem(R.id.search);

        final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) mySearch.getActionView();
        searchView.setQueryHint("Pesquisar...");
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (allowSearch) {
                    myAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });

        searchView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {
                myTabLayout.setVisibility(GONE);
                sonicAppearence.searchAppearence(getActivity(),searchView,myToolBar,2,true,true);
            }

            @Override
            public void onViewDetachedFromWindow(View view) {
                myTabLayout.setVisibility(VISIBLE);
                sonicAppearence.searchAppearence(getActivity(),searchView,myToolBar,2,false,false);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                return false;
            case R.id.delete:
                dialogDelete();
                return false;
            default:
                break;
        }

        return false;
    }

    class myAsyncTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {

            myErros =  new sonicDatabaseLogCRUD(_this).Log.selectLog();
            return myErros.size();

        }

        @Override
        protected void onPostExecute(final Integer result) {
            super.onPostExecute(result);

            AlphaAnimation fadeOut;
            fadeOut = new AlphaAnimation(1,0);
            fadeOut.setDuration(500);
            fadeOut.setFillAfter(true);
            myShimmer.setAnimation(fadeOut);


            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(() -> {

                        if(result>0){

                            showResult();

                        }else{

                            showNoResult();

                        }

                        myShimmer.stopShimmer();
                        myShimmer.setVisibility(GONE);

                    }
                    ,sonicUtils.Randomizer.generate(500,1500));

        }
    }

    private void showResult(){

        AlphaAnimation fadeIn;
        fadeIn = new AlphaAnimation(0,1);
        fadeIn.setDuration(500);
        fadeIn.setFillAfter(true);

        allowSearch = true;
        myAdapter = new sonicSistemaLogAdapter(myErros, _this);
        myRecycler.setVisibility(VISIBLE);
        myRecycler.setAdapter(myAdapter);
        myRecycler.startAnimation(fadeIn);
        ViewGroup.LayoutParams params = myCoordinatorLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;

    }

    private void showNoResult(){

        AlphaAnimation fadeIn;
        fadeIn = new AlphaAnimation(0,1);
        fadeIn.setDuration(500);
        fadeIn.setFillAfter(true);

        allowSearch = false;
        myImage.setVisibility(VISIBLE);
        myTextView.setVisibility(VISIBLE);
        myTextView.startAnimation(fadeIn);
        myTextView.setText(R.string.noLog);
        Glide.with(_this)
                .load(R.drawable.nolog)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                .into(myImage);

    }

    private void dialogDelete() {

        AlertDialog.Builder builder = new AlertDialog.Builder(_this);
        builder.setMessage("Deseja apagar todos os logs?")
            .setPositiveButton("APAGAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    apagarLogs();
                }
            })
            .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            })
            .show();

    }

    public void apagarLogs(){

        myProgressDialog = new ProgressDialog(_this);
        myProgressDialog.setCancelable(false);
        myProgressDialog.setMessage("Apagando, aguarde...");
        myProgressDialog.setMax(1);
        myProgressDialog.setProgressStyle(0);
        myProgressDialog.show();

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    new myAsyncTaskDialog().execute();

                                }
                            }
                , 500);


    }

    class myAsyncTaskDialog extends AsyncTask<Boolean, Boolean, Boolean>{
        @Override
        protected Boolean doInBackground(Boolean... booleans) {
            return new sonicDatabaseLogCRUD(_this).Log.cleanLog();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            myProgressDialog.cancel();

            if(aBoolean){

                myErros.clear();
                myAdapter.notifyDataSetChanged();
                showNoResult();

            }
        }
    }

    public void refreshFragment(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("RESUME","");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("PAUSE","");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
