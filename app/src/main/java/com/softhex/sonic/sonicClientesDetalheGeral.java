package com.softhex.sonic;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

/**
 * Created by Administrador on 21/07/2017.
 */

public class sonicClientesDetalheGeral extends Fragment {

    private View myView;
    private Context mContext;
    private sonicDatabaseCRUD DBC;
    private TextView tvNome;
    private TextView tvFantRazao;
    private TextView tvGrupo;
    private TextView tvCnpjCpf;
    private TextView tvEndereco;
    private TextView tvBairro;
    private TextView tvMunicipio;
    private TextView tvCep;
    private TextView tvFone;
    private TextView tvWhats;
    private TextView tvEmail;
    private TextView tvCgf;
    private TextView tvObservacao;
    private sonicPreferences mPref;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.sonic_clientes_detalhe_geral, container, false);

        mContext = getContext();

        DBC = new sonicDatabaseCRUD(mContext);
        mPref = new sonicPreferences(getContext());
        tvNome = myView.findViewById(R.id.tvNome);
        tvNome.setText(mPref.Clientes.getNome());
        tvGrupo = myView.findViewById(R.id.tvGrupo);
        tvGrupo.setText("CÓD.: "+mPref.Clientes.getId()+" / " +mPref.Clientes.getGrupo());
        tvFantRazao = myView.findViewById(R.id.tvFantRazao);
        tvCnpjCpf = myView.findViewById(R.id.tvCnpjCpf);
        tvCnpjCpf.setText(mPref.Clientes.getCnpjCpf());
        tvEndereco = myView.findViewById(R.id.tvEndereco);
        tvBairro = myView.findViewById(R.id.tvBairro);
        tvMunicipio = myView.findViewById(R.id.tvMunicipio);
        tvCep = myView.findViewById(R.id.tvCep);
        tvFone = myView.findViewById(R.id.tvFone);
        tvWhats = myView.findViewById(R.id.tvWhats);
        tvEmail = myView.findViewById(R.id.tvEmail);
        tvCgf = myView.findViewById(R.id.tvInscEstadual);
        tvObservacao = myView.findViewById(R.id.tvObservacao);

        loadDetails();

        return myView;

    }

    private void loadDetails(){

        List<sonicClientesHolder> mList;
        mList = DBC.Cliente.selectClienteID(mPref.Clientes.getId());
        tvFantRazao.setText(mPref.Clientes.getClienteExibicao().equals("Nome Fantasia") ? mList.get(0).getRazaoSocial() : mList.get(0).getNomeFantasia());
        tvEndereco.setText(mList.get(0).getEndereco());
        tvBairro.setText(mList.get(0).getBairro());
        tvMunicipio.setText(mList.get(0).getMunicipio()+" - "+mList.get(0).getUf());
        tvCep.setText("Cep: "+mList.get(0).getCep());
        tvCgf.setText("INSC. ESTADUAL: "+mList.get(0).getInscEstadual());
        tvFone.setText(mList.get(0).getFone().equals("") ? "--" : mList.get(0).getFone()+(mList.get(0).getContato().equals("") ? "" : " / "+mList.get(0).getContato()));
        tvEmail.setText(mList.get(0).getEmail().equals("") ? "--" : mList.get(0).getEmail());
        tvWhats.setText("--");
        tvObservacao.setText("Observação: "+mList.get(0).getObservacao());

    }

}
