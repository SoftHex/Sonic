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
    private Context _this;
    private sonicDatabaseCRUD DBC;
    private TextView tvNome, tvFantRazao, tvGrupo, tvCnpjCpf, tvEndereco, tvBairro, tvMunicipio, tvCep, tvFone, tvWhats, tvEmail, tvCgf, tvObservacao;
    private sonicPreferences mPref;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.sonic_clientes_detalhe_geral, container, false);

        _this = getContext();

        DBC = new sonicDatabaseCRUD(_this);
        mPref = new sonicPreferences(getContext());
        tvNome = myView.findViewById(R.id.tvNome);
        tvNome.setText(mPref.Clientes.getNome());
        tvGrupo = myView.findViewById(R.id.tvGrupo);
        tvGrupo.setText(mPref.Clientes.getGrupo());
        tvFantRazao = myView.findViewById(R.id.tvFantRazao);
        tvCnpjCpf = myView.findViewById(R.id.tvCnpjCpf);
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
        tvCnpjCpf.setText("CPF/CNPJ: "+mList.get(0).getCpfCnpj());
        tvFantRazao.setText(mPref.Clientes.getClienteExibicao().equals("Nome Fantasia") ? mList.get(0).getRazaoSocial() : mList.get(0).getNomeFantasia());
        tvEndereco.setText(mList.get(0).getEndereco());
        tvBairro.setText(mList.get(0).getBairro());
        tvMunicipio.setText(mList.get(0).getMunicipio()+" - "+mList.get(0).getUf());
        tvCep.setText("Cep: "+mList.get(0).getCep());
        tvCgf.setText("IE: "+mList.get(0).getInscEstadual());
        tvFone.setText(mList.get(0).getFone().equals("") ? "--" : mList.get(0).getFone()+(mList.get(0).getContato().equals("") ? "" : " / "+mList.get(0).getContato()));
        tvEmail.setText(mList.get(0).getEmail().equals("") ? "--" : mList.get(0).getEmail());
        tvWhats.setText("--");
        tvObservacao.setText("Observação: "+mList.get(0).getObservacao());

    }

}
