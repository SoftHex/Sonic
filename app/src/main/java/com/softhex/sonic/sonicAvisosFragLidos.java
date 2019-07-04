package com.softhex.sonic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import static android.content.Context.SEARCH_SERVICE;

/**
 * Created by Administrador on 21/07/2017.
 */

public class sonicAvisosFragLidos extends Fragment{

    private RecyclerView myRecycler;
    private sonicAvisosAdapter myAdapter;
    private RecyclerView.LayoutManager myLayout;
    private MenuItem mySearch;
    private List<sonicAvisosHolder> avisos;
    private sonicDatabaseCRUD DBC;
    private CoordinatorLayout myCoordinatorLayout;
    private ProgressBar myProgress;
    private Boolean allowRefresh = false;
    private TextView myTextViewTitle, myTextViewText;
    private Intent i;
    private View myView;
    private ShimmerFrameLayout myShimmer;
    private Toolbar myToolBar;
    private Context _this;
    private boolean allowSearch;
    private ActionBar myActionBar;

    @Nullable

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        sonicAppearence.onFragmentCreateSetTheme(this);
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

        myActionBar = getActivity().getActionBar();

        myToolBar = (android.support.v7.widget.Toolbar)getActivity().findViewById(R.id.toolbar);

        myTextViewText = (TextView)myView.findViewById(R.id.text);

        myCoordinatorLayout = (CoordinatorLayout)myView.findViewById(R.id.layout_main);

        myShimmer =  myView.findViewById(R.id.shimmer);

        myRecycler = (RecyclerView) myView.findViewById(R.id.recycler_list);

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
                inflater.inflate(R.menu.sonic_avisos, menu);
                mySearch = (MenuItem) menu.findItem(R.id.search);

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
                    if(allowSearch){
                        myAdapter.getFilter().filter(newText);
                    }
                    return false;
                }

            });

        searchView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {

                sonicAppearence.searchAppearence(getActivity(),searchView,myToolBar,1,true,true);
            }

            @Override
            public void onViewDetachedFromWindow(View view) {

                sonicAppearence.searchAppearence(getActivity(),searchView,myToolBar,1,false,false);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                return false;
            default:
                break;
        }

        return false;
    }

    class myAsyncTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {

            avisos = new sonicDatabaseCRUD(_this).Avisos.selectAvisos();
            return avisos.size();

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
                                            myAdapter = new sonicAvisosAdapter(avisos, getActivity());
                                            myRecycler.setAdapter(myAdapter);
                                            ViewGroup.LayoutParams params = myCoordinatorLayout.getLayoutParams();
                                            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                                        }else{
                                            allowSearch = false;
                                            myTextViewText.setText("No messages");
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
            ft.detach(this).attach(this).commit();

        }

    }

}
