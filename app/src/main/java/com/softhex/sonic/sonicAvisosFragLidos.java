package com.softhex.sonic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.SearchManager;
import android.content.Intent;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    private ImageView myImage;
    private Intent i;
    private View myView;
    private android.support.v7.widget.Toolbar myToolBar;
    private boolean allowSearch;


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

        setHasOptionsMenu(true);

        myToolBar = (android.support.v7.widget.Toolbar)getActivity().findViewById(R.id.avisos_toolbar);

        myTextViewText = (TextView)myView.findViewById(R.id.text);

        myImage = (ImageView)myView.findViewById(R.id.no_item_image);

        myCoordinatorLayout = (CoordinatorLayout)myView.findViewById(R.id.layout_main);

        myProgress = (ProgressBar) myView.findViewById(R.id.progress);

        myRecycler = (RecyclerView) myView.findViewById(R.id.recycler_list);

        myRecycler.setHasFixedSize(true);

        myLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        myRecycler.setLayoutManager(myLayout);

        ViewGroup.LayoutParams params = myCoordinatorLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        myProgress.setVisibility(View.VISIBLE);

        myProgress.setVisibility(View.VISIBLE);
        bindRecyclerView();

    }

    // MÉTODO PARA CRIAR UM MENU DE OPÇÕES PELO ID DA VIEW/OBJETO. MENU DEFINIDO NO LAYOUT INFLADO
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
                super.onCreateOptionsMenu(menu, inflater);
                inflater.inflate(R.menu.menu_sonic_avisos, menu);
                mySearch = (MenuItem) menu.findItem(R.id.avisos_search);
                // TORNA INVISIVEL CPARA CASO O ADAPTER NÃO RETORNAR NENHUM REGISTRO
                //mySearch.setVisible(false);

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
                //Toast.makeText(view.getContext(), "Teste", Toast.LENGTH_SHORT).show();
                //myToolBar.setBackgroundColor(view.getResources().getColor(R.color.colorPrimaryWhite));
                EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
                searchEditText.setTextColor(getResources().getColor(R.color.colorTextAccent));
                searchEditText.setHintTextColor(getResources().getColor(R.color.colorTextNoAccent));
                animateSearchToolbar(1, true, true);
            }

            @Override
            public void onViewDetachedFromWindow(View view) {
                //myToolBar.setBackgroundColor(view.getResources().getColor(R.color.colorPrimary));
                animateSearchToolbar(1, false, false);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.avisos_search:
                return false;
            default:
                break;
        }

        return false;
    }

    public void animateSearchToolbar(int numberOfMenuIcon, boolean containsOverflow, boolean show) {

        myToolBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryWhite));

        if (show) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int width = myToolBar.getWidth() -
                        (containsOverflow ? getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material) : 0) -
                        ((getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) * numberOfMenuIcon) / 2);
                Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(myToolBar,
                        false ? myToolBar.getWidth() - width : width, myToolBar.getHeight() / 2, 0.0f, (float) width);
                createCircularReveal.setDuration(250);
                createCircularReveal.start();
            } else {
                TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, (float) (-myToolBar.getHeight()), 0.0f);
                translateAnimation.setDuration(220);
                myToolBar.clearAnimation();
                myToolBar.startAnimation(translateAnimation);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int width = myToolBar.getWidth() -
                        (containsOverflow ? getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material) : 0) -
                        ((getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) * numberOfMenuIcon) / 2);
                Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(myToolBar,
                        false ? myToolBar.getWidth() - width : width, myToolBar.getHeight() / 2, (float) width, 0.0f);
                createCircularReveal.setDuration(250);
                createCircularReveal.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        myToolBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    }
                });
                createCircularReveal.start();
            } else {
                AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                Animation translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, (float) (-myToolBar.getHeight()));
                AnimationSet animationSet = new AnimationSet(true);
                animationSet.addAnimation(alphaAnimation);
                animationSet.addAnimation(translateAnimation);
                animationSet.setDuration(220);
                animationSet.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        //mToolbar.setBackgroundColor(getThemeColor(MainActivity.this, R.attr.colorPrimary));
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                myToolBar.startAnimation(animationSet);
            }
            //mDrawerLayout.setStatusBarBackgroundColor(getThemeColor(MainActivity.this, R.attr.colorPrimaryDark));
        }
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
            fadeIn.setDuration(result); //set duration in mill seconds
            fadeOut.setDuration(result); //set duration in mill seconds
            fadeIn.setFillAfter(true);
            fadeOut.setFillAfter(true);

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
                                            //myToolBar.getMenu().findItem(R.id.avisos_search).setVisible(false);
                                            //SpannableStringBuilder ssb = new SpannableStringBuilder("Sem Avisos para serem exibidos no momento. Vá até o menu no ícone   e faça uma sincronização.");
                                            //Bitmap smiley = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_sync_black_48dp);
                                            //ssb.setSpan(new ImageSpan(smiley), 66, 67, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                                            //myToolBar.getMenu().findItem(R.id.avisos_search).setVisible(true);
                                            //myToolBar.getMenu().clear();
                                            myTextViewText.setText("No messages");
                                            myTextViewText.setVisibility(View.VISIBLE);
                                            myTextViewText.startAnimation(fadeIn);
                                            //myTextViewTitle.setText("AVISOS");
                                            //myTextViewTitle.setVisibility(View.VISIBLE);
                                            //myTextViewTitle.startAnimation(fadeIn);
                                            myImage.setVisibility(View.VISIBLE);
                                            myImage.startAnimation(fadeIn);
                                            //myTextView.setVisibility(View.VISIBLE);
                                        }

                                        myProgress.startAnimation(fadeOut);
                                        myProgress.setVisibility(View.GONE);


                                    }
                                }
                    , result);


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
