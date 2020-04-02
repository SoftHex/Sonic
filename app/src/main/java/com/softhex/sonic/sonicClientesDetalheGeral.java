package com.softhex.sonic;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

        mPref = new sonicPreferences(getContext());

        loadDetails();

        return myView;

    }

    private void loadDetails(){

        tvNome = myView.findViewById(R.id.tvNome);
        tvNome.setText(mPref.Clientes.getNome());
        tvGrupo = myView.findViewById(R.id.tvGrupo);
        tvGrupo.setText("CÓD.: "+mPref.Clientes.getId()+" / " +mPref.Clientes.getGrupo());
        tvFantRazao = myView.findViewById(R.id.tvFantRazao);
        tvFantRazao.setText(mPref.Clientes.getClienteExibicao().equals("Nome Fantasia") ? mPref.Clientes.getRazao() : mPref.Clientes.getNome());
        tvCnpjCpf = myView.findViewById(R.id.tvCnpjCpf);
        tvCnpjCpf.setText(mPref.Clientes.getCnpjCpf());
        tvEndereco = myView.findViewById(R.id.tvEndereco);
        tvEndereco.setText(mPref.Clientes.getLogradouro());
        tvBairro = myView.findViewById(R.id.tvBairro);
        tvBairro.setText(mPref.Clientes.getBairro());
        tvMunicipio = myView.findViewById(R.id.tvMunicipio);
        tvMunicipio.setText(mPref.Clientes.getMunicipio());
        tvCep = myView.findViewById(R.id.tvCep);
        tvCep.setText("CEP: "+mPref.Clientes.getCep());
        tvFone = myView.findViewById(R.id.tvFone);
        tvFone.setText(mPref.Clientes.getTelefone().equals("") ? "--" : mPref.Clientes.getTelefone());
        tvWhats = myView.findViewById(R.id.tvWhats);
        tvWhats.setText(mPref.Clientes.getWhatsApp());
        tvWhats.setText("--");
        tvEmail = myView.findViewById(R.id.tvEmail);
        tvEmail.setText(mPref.Clientes.getEmail().equals("") ? "--" : mPref.Clientes.getEmail());
        tvCgf = myView.findViewById(R.id.tvInscEstadual);
        tvCgf.setText("IE: "+mPref.Clientes.getIe());
        tvObservacao = myView.findViewById(R.id.tvObservacao);
        tvObservacao.setText("Observações: "+mPref.Clientes.getObs());

    }

}
