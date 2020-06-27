package com.softhex.sonic;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Administrador on 21/07/2017.
 */

public class sonicClientesCPF extends Fragment {

    private View myView;
    private RecyclerView myRecycler;
    private RecyclerView.LayoutManager myLayout;
    private sonicClientesAdapter myAdapter;
    private List<sonicClientesHolder> mList;
    private MenuItem mySearch;
    private Toolbar myToolBar;
    private TabLayout myTabLayout;
    private CoordinatorLayout myCoordinatorLayout;
    private ShimmerFrameLayout myShimmer;
    private TextView tvTexto, tvTitle, tvSearch;
    private sonicConstants myCons;
    private boolean allowSearch;
    private Context mContext;
    private ImageView myImage;
    private sonicDatabaseCRUD DBC;
    private Intent i;
    private sonicPreferences mPrefs;
    private LinearLayout llNoResult;
    private RelativeLayout rlDesert;
    private Button btSinc;
    private FloatingActionButton fbUp;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.sonic_recycler_layout_list, container, false);

        mContext = getActivity();
        mPrefs = new sonicPreferences(getActivity());
        DBC = new sonicDatabaseCRUD(mContext);

        loadFragment();

        return myView;

    }

    public void loadFragment(){

        setHasOptionsMenu(true);

        myCons = new sonicConstants();

        myToolBar = getActivity().findViewById(R.id.mToolbar);

        myTabLayout = getActivity().findViewById(R.id.mTabs);

        llNoResult = myView.findViewById(R.id.llNoResult);

        rlDesert = myView.findViewById(R.id.rlDesert);

        btSinc = myView.findViewById(R.id.btSinc);

        tvTexto = myView.findViewById(R.id.tvText);

        tvTitle = myView.findViewById(R.id.tvTitle);

        tvSearch = myView.findViewById(R.id.tvSearch);

        myImage = myView.findViewById(R.id.ivImage);

        myCoordinatorLayout = myView.findViewById(R.id.layoutMain);

        myShimmer = myView.findViewById(R.id.mShimmerLayout);

        myRecycler =  myView.findViewById(R.id.recyclerList);

        myRecycler.setHasFixedSize(true);

        myLayout = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        myRecycler.setLayoutManager(myLayout);

        fbUp = myView.findViewById(R.id.fbUp);

        ViewGroup.LayoutParams params = myCoordinatorLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;

        myShimmer.startShimmer();

        bindRecyclerView();

    }

    public void bindRecyclerView(){

        new myAsyncTask().execute();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.sonic_clientes, menu);
        mySearch = menu.findItem(R.id.itemSearch);

        final androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) MenuItemCompat.getActionView(mySearch);
        searchView.setQueryHint("Pesquisar...");
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(allowSearch){
                    Filter.FilterListener listener = new Filter.FilterListener() {
                        @Override
                        public void onFilterComplete(int count) {
                            if (allowSearch) {
                                if (myAdapter.getItemCount()==0) {
                                    tvSearch.setVisibility(VISIBLE);
                                    tvSearch.setText("Nenhum resultado para '"+newText+"'");
                                } else {
                                    tvSearch.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                    };
                    myAdapter.getFilter().filter(newText, listener);
                }
                return false;
            }
        });

        searchView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {
                myTabLayout.setVisibility(GONE);
                sonicAppearence.searchAppearence(getActivity(),searchView,myToolBar,5,true,true);
            }

            @Override
            public void onViewDetachedFromWindow(View view) {
                myTabLayout.setVisibility(VISIBLE);
                if(!mPrefs.RotaPessoal.getAdding()){
                    sonicAppearence.searchAppearence(getActivity(),searchView,myToolBar,5,false,false);
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemSearch:
                return false;
            default:
                break;
        }

        return false;
    }

    class myAsyncTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {

            mList =  new sonicDatabaseCRUD(mContext).Cliente.selectClienteTipo(false);
            return mList.size();

        }

        @Override
        protected void onPostExecute(final Integer result) {
            super.onPostExecute(result);

            Handler handler = new Handler();
            handler.postDelayed(() -> {

                        if(result>0){

                            showResult();

                        }else{

                            showNoResult();

                        }

                        myShimmer.stopShimmer();
                        myShimmer.setVisibility(GONE);

                    }
                    ,mList.size()>1000 ? mList.size() : 500);

        }
    }

    private void showResult(){

        AlphaAnimation fadeIn;
        fadeIn = new AlphaAnimation(0,1);
        fadeIn.setDuration(500);
        fadeIn.setFillAfter(true);
        llNoResult.setVisibility(GONE);
        rlDesert.setVisibility(GONE);
        allowSearch = true;
        myAdapter = new sonicClientesAdapter(mList, mContext, myRecycler, fbUp, false);
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
        llNoResult.setVisibility(VISIBLE);
        rlDesert.setVisibility(VISIBLE);
        llNoResult.startAnimation(fadeIn);
        rlDesert.startAnimation(fadeIn);
        allowSearch = false;
        myImage.setVisibility(GONE);
        tvTitle.setText("Ops, nenhum cliente por enquanto...");
        tvTexto.setText("Se você ainda não sincronizou, pode fazê-lo clicando no botão abaixo.");
        btSinc.setOnClickListener((View v)->{
            mPrefs.Geral.setHomeRefresh(true);
            mPrefs.Geral.setDrawerRefresh(true);
            mPrefs.Sincronizacao.setDownloadType("DADOS");
            mPrefs.Sincronizacao.setCalledActivity(getActivity().getClass().getSimpleName());
            sonicFtp myFtp = new sonicFtp(getActivity());
            String file = mPrefs.Users.getArquivoSinc();
            myFtp.downloadFile2(sonicConstants.FTP_USUARIOS+file, sonicConstants.LOCAL_TEMP+file);
        });

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
