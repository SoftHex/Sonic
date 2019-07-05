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
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
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
    private RecyclerView myRecycler;
    private RecyclerView.LayoutManager myLayout;
    private sonicSistemaLogAdapter myAdapter;
    private List<sonicSistemaLogHolder> myErros;
    private sonicDatabaseLogCRUD DBC;
    private MenuItem mySearch;
    private MenuItem myDelete;
    private Toolbar myToolBar;
    private TabLayout myTabLayout;
    private CoordinatorLayout myCoordinatorLayout;
    private ShimmerFrameLayout myShimmer;
    private TextView myTextView;
    private sonicConstants myCons;
    private boolean allowSearch;
    private ProgressDialog myProgressDialog;
    private Context _this;
    SharedPreferences.OnSharedPreferenceChangeListener prefs;
    Intent i;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.sonic_recycler_layout_log, container, false);

        _this = getActivity();

        loadFragment();

        return myView;

    }

    public void bindRecyclerView(){

        new myAsyncTask(myRecycler).execute();

    }

    public void loadFragment(){

        setHasOptionsMenu(true);

        myCons = new sonicConstants();

        DBC = new sonicDatabaseLogCRUD(getActivity());

        myToolBar = getActivity().findViewById(R.id.toolbar);

        myTabLayout = getActivity().findViewById(R.id.tabs);

        myTextView = (TextView)myView.findViewById(R.id.text);

        myCoordinatorLayout = (CoordinatorLayout)myView.findViewById(R.id.layout_main);

        myShimmer = (ShimmerFrameLayout)myView.findViewById(R.id.shimmer);

        myRecycler = (RecyclerView) myView.findViewById(R.id.recycler_list);

        myRecycler.setHasFixedSize(true);

        myLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        myRecycler.setLayoutManager(myLayout);

        ViewGroup.LayoutParams params = myCoordinatorLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;

        myShimmer.startShimmer();

        bindRecyclerView();


    }

    // MÉTODO PARA CRIAR UM MENU DE OPÇÕES PELO ID DA VIEW/OBJETO. MENU DEFINIDO NO LAYOUT INFLADO
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.sonic_sistema_log, menu);
        mySearch = (MenuItem) menu.findItem(R.id.search);
        myDelete = (MenuItem) menu.findItem(R.id.delete);

        final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) mySearch.getActionView();
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(SEARCH_SERVICE);
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

        RecyclerView myRecyclerView;

        public  myAsyncTask(RecyclerView rview) {
            this.myRecyclerView = rview;
        }

        @Override
        protected Integer doInBackground(Integer... integers) {

            myErros =  new sonicDatabaseLogCRUD(_this).Log.selectLog();
            return myErros.size();

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

                                            allowSearch = true;
                                            myAdapter = new sonicSistemaLogAdapter(myErros, _this);
                                            myRecycler.setVisibility(VISIBLE);
                                            myRecycler.setAnimation(fadeIn);
                                            myRecycler.setAdapter(myAdapter);
                                            ViewGroup.LayoutParams params = myCoordinatorLayout.getLayoutParams();
                                            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                                        }else{
                                            allowSearch = false;
                                            myTextView.setText("No log");
                                            myTextView.setVisibility(VISIBLE);
                                            myTextView.startAnimation(fadeIn);

                                        }

                                        myShimmer.stopShimmer();
                                        myShimmer.setVisibility(GONE);

                                    }
                                }
                    , 1);


        }
    }

    private void dialogDelete() {

        AlertDialog.Builder builder = new AlertDialog.Builder(_this);
        builder.setMessage("Deseja apagar todos os logs?")
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    myProgress();
                }
            })
            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            })
            .show();

    }

    public void myProgress(){

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
                myAdapter.updateList(null);

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
