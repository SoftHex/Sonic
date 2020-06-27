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
    private sonicDatabaseCRUD mData;
    private List<sonicClientesHolder> mList;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.sonic_clientes_detalhe_geral, container, false);

        mContext = getContext();
        mData = new sonicDatabaseCRUD(getContext());
        mPref = new sonicPreferences(getContext());

        loadDetails();

        return myView;

    }

    private void loadDetails(){

        mList = mData.Cliente.selectClienteByID(mPref.Clientes.getId());
        tvNome = myView.findViewById(R.id.tvNome);
        tvNome.setText(mList.get(0).getNome());
        tvNome.setText(mPref.Clientes.getClienteExibicao().equals("Nome Fantasia") ? mList.get(0).getNomeFantasia() : mList.get(0).getRazaoSocial());
        tvGrupo = myView.findViewById(R.id.tvGrupo);
        tvGrupo.setText("CÓD.: "+mList.get(0).getCodigo()+" / GRUPO: " +mList.get(0).getGrupo());
        tvFantRazao = myView.findViewById(R.id.tvFantRazao);
        tvFantRazao.setText(mPref.Clientes.getClienteExibicao().equals("Nome Fantasia") ? mList.get(0).getRazaoSocial() : mList.get(0).getNomeFantasia());
        tvCnpjCpf = myView.findViewById(R.id.tvCnpjCpf);
        tvCnpjCpf.setText("CNPJ/CPF: "+sonicUtils.stringToCnpjCpf(mList.get(0).getCpfCnpj()));
        tvEndereco = myView.findViewById(R.id.tvEndereco);
        tvEndereco.setText(mList.get(0).getEndereco());
        tvBairro = myView.findViewById(R.id.tvBairro);
        tvBairro.setText(mList.get(0).getBairro());
        tvMunicipio = myView.findViewById(R.id.tvMunicipio);
        tvMunicipio.setText(mList.get(0).getMunicipio());
        tvCep = myView.findViewById(R.id.tvCep);
        tvCep.setText("CEP: "+sonicUtils.stringToCep(mList.get(0).getCep()));
        tvFone = myView.findViewById(R.id.tvFone);
        tvFone.setText(mList.get(0).getFone());
        tvWhats = myView.findViewById(R.id.tvWhats);
        tvWhats.setText(mList.get(0).getWhats());
        tvEmail = myView.findViewById(R.id.tvEmail);
        tvEmail.setText(mList.get(0).getEmail());
        tvCgf = myView.findViewById(R.id.tvInscEstadual);
        tvCgf.setText("IE: "+mList.get(0).getInscEstadual());
        tvObservacao = myView.findViewById(R.id.tvObservacao);
        tvObservacao.setText("Observações: "+mList.get(0).getObservacao());

    }

}
