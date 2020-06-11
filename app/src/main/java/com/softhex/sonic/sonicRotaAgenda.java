package com.softhex.sonic;

import android.content.Context;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Administrador on 21/07/2017.
 */

public class sonicRotaAgenda extends Fragment {

    private View mView;
    private RecyclerView mRecycler;
    private RecyclerView.LayoutManager mLayout;
    private sonicRotaAdapter mAdapter;
    private List<sonicRotaHolder> mList;
    private MenuItem mSearch;
    private Toolbar mToolBar;
    private TabLayout mTabLayout;
    private CoordinatorLayout mCoordinatorLayout;
    private ShimmerFrameLayout mShimmer;
    private TextView tvTexto, tvTitle, tvSearch;
    private sonicConstants myCons;
    private boolean allowSearch;
    private Context mContext;
    private ImageView mImage;
    private sonicPreferences mPrefs;
    private LinearLayout llNoResult;
    private RelativeLayout rlDesert;
    private Button btSinc;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.sonic_recycler_layout_rota, container, false);

        mContext = getActivity();
        mPrefs = new sonicPreferences(getActivity());
        loadFragment();

        return mView;

    }

    public void loadFragment(){

        setHasOptionsMenu(true);

        myCons = new sonicConstants();

        mToolBar = getActivity().findViewById(R.id.mToolbar);

        mTabLayout = getActivity().findViewById(R.id.mTabs);

        llNoResult = mView.findViewById(R.id.llNoResult);

        rlDesert = mView.findViewById(R.id.rlDesert);

        btSinc = mView.findViewById(R.id.btSinc);

        tvTexto = mView.findViewById(R.id.tvText);

        tvTitle = mView.findViewById(R.id.tvTitle);

        tvSearch = mView.findViewById(R.id.tvSearch);

        mImage = mView.findViewById(R.id.ivImage);

        mCoordinatorLayout = mView.findViewById(R.id.layoutMain);

        mShimmer = mView.findViewById(R.id.mShimmerLayout);

        mRecycler =  mView.findViewById(R.id.recyclerList);

        mRecycler.setHasFixedSize(true);

        mRecycler.getRecycledViewPool().setMaxRecycledViews(1,50);

        mLayout = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        mLayout.scrollToPosition(mPrefs.Rota.getItemPosition());
        //myLayout.smoothScrollToPosition(myRecycler, new RecyclerView.State(), mPrefs.Rota.getItemPosition());

        mRecycler.setLayoutManager(mLayout);

        ViewGroup.LayoutParams params = mCoordinatorLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;

        mShimmer.startShimmer();

        bindRecyclerView();

    }

    public void bindRecyclerView(){

        new myAsyncTask().execute();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.sonic_rota, menu);
        mSearch = menu.findItem(R.id.itSearch);

        final androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) MenuItemCompat.getActionView(mSearch);
        searchView.setQueryHint("Pesquisar");
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
                                if (mAdapter.getItemCount()==0) {
                                    tvSearch.setVisibility(VISIBLE);
                                    tvSearch.setText("Nenhum resultado para '"+newText+"'");
                                } else {
                                    tvSearch.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                    };
                    mAdapter.getFilter().filter(newText, listener);
                }
                return false;
            }
        });

        searchView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {
                mTabLayout.setVisibility(GONE);
                sonicAppearence.searchAppearence(getActivity(),searchView, mToolBar,2,true,true);
            }

            @Override
            public void onViewDetachedFromWindow(View view) {
                mTabLayout.setVisibility(VISIBLE);
                sonicAppearence.searchAppearence(getActivity(),searchView, mToolBar,2,false,false);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itSearch:
                return false;
            case R.id.itFilter:
                exibirFiltro();
                return false;
            default:
                break;
        }

        return false;
    }

    class myAsyncTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {

            mList =  new sonicDatabaseCRUD(mContext).Rota.selectRota(false);
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

                        mShimmer.stopShimmer();
                        mShimmer.setVisibility(GONE);

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
        mAdapter = new sonicRotaAdapter(mList, mContext, mRecycler, getActivity(), false);
        mRecycler.setVisibility(VISIBLE);
        mRecycler.setAdapter(mAdapter);
        mRecycler.startAnimation(fadeIn);
        ViewGroup.LayoutParams params = mCoordinatorLayout.getLayoutParams();
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
        mImage.setVisibility(GONE);
        tvTitle.setText("Ops, nenhuma rota por enquanto...");
        tvTexto.setText("Se você ainda não sincronizou, pode fazê-lo clicando no botão abaixo.");
        btSinc.setOnClickListener((View v)->{
            mPrefs.Sincronizacao.setSincRefresh(true);
            mPrefs.Sincronizacao.setDownloadType("DADOS");
            mPrefs.Sincronizacao.setCalledActivity(getActivity().getClass().getSimpleName());
            sonicFtp myFtp = new sonicFtp(getActivity());
            String file = mPrefs.Users.getArquivoSinc();
            myFtp.downloadFile2(sonicConstants.FTP_USUARIOS+file, sonicConstants.LOCAL_TEMP+file);
        });

    }

    private void exibirFiltro() {


        List<String> l = new ArrayList<String>();
        l.add("NÃO INICIADO");
        l.add("EM ATENDIMENTO");
        l.add("CONCLUIDO");
        l.add("CANCELADO");

        final CharSequence[] chars = l.toArray(new CharSequence[l.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //builder.setTitle("Selecione um status...");
        builder.setItems(chars, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                sonicConstants.GRUPO_ROTA_STATUS_INT = item+1;
                sonicConstants.GRUPO_ROTA_STATUS = chars[item].toString();
                sonicConstants.GRUPO_ROTA_STATUS_LIMPAR = "LIMPAR FILTRO";
                dialog.dismiss();
                refreshFragment();

            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton(sonicConstants.GRUPO_ROTA_STATUS_LIMPAR, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sonicConstants.GRUPO_ROTA_STATUS = "TODOS";
                sonicConstants.GRUPO_ROTA_STATUS_LIMPAR = "";
                refreshFragment();
            }
        }).show();

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
