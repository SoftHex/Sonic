package com.softhex.sonic;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.softhex.view.ProgressProfileView;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;


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
    private TextView myPedidos, myEmpresa, myMetaVenda, myVendaRealizada, myTotalPercent, myUser, mySaudacao;
    private int myMetaVisita;
    private sonicDatabaseCRUD DBC;
    private Context _this;
    private ProgressProfileView myProgressProfile;


    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.layout_main_vendedor, container, false);

        _this = getActivity();
        DBC = new sonicDatabaseCRUD(_this);

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
        if(params !=null){
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        }else{
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }


        myPedidos = myView.findViewById(R.id.pedidos);
        myProgressProfile = myView.findViewById(R.id.myProgressProfile);
        myEmpresa = myView.findViewById(R.id.empresa);
        myMetaVenda = myView.findViewById(R.id.metaVenda);
        myVendaRealizada = myView.findViewById(R.id.vendaRealizada);
        myTotalPercent = myView.findViewById(R.id.totalPercent);
        mySaudacao = myView.findViewById(R.id.saudacao);
        myUser = myView.findViewById(R.id.usuario);

        List<sonicUsuariosHolder> listaUser;
        listaUser = DBC.Usuarios.selectUsuarioAtivo();
        myPedidos.setText("0");
        myEmpresa.setText(sonicConstants.EMPRESA_SELECIONADA_NOME);
        myUser.setText(sonicConstants.USUARIO_ATIVO_NOME);
        mySaudacao.setText(sonicUtils.salutation());
        ///myMetaVenda.setText(new sonicUtils(_this).Number.stringToMoeda(listaUser.get(0).getMetaVenda()));
        Log.d("META", listaUser.get(0).getMetaVenda()+"");
        myMetaVenda.setText(new sonicUtils(_this).Number.stringToMoeda2(listaUser.get(0).getMetaVenda()+""));
        myVendaRealizada.setText(new sonicUtils(_this).Number.stringToMoeda2("0"));
        myTotalPercent.setText("0,00%");
        myProgressProfile.setProgress(70.7f);

        File file =  new File(Environment.getExternalStorageDirectory().getPath()+sonicConstants.LOCAL_IMG_PERFIL+sonicConstants.EMPRESA_SELECIONADA_ID+"_"+sonicConstants.USUARIO_ATIVO_ID+".jpg");

        if(file.exists()){
            Glide.with(_this)
                    .load(file)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .transition(GenericTransitionOptions.with(R.anim.fade_in))
                    .into(myProgressProfile);
        }else{
            Glide.with(_this)
                    .load(R.drawable.no_profile)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .transition(GenericTransitionOptions.with(R.anim.fade_in))
                    .into(myProgressProfile);
        }



        myProgressProfile.startAnimation();

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