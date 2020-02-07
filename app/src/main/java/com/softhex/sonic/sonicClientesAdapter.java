package com.softhex.sonic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrador on 11/08/2017.
 */

public class sonicClientesAdapter extends RecyclerView.Adapter implements Filterable{

    private Context myCtx;
    private List<sonicClientesHolder> clientes;
    private List<sonicClientesHolder> filteredClientes;
    private UserFilter userFilter;
    private sonicDatabaseCRUD DBC;
    private sonicConstants myCons;
    private GradientDrawable shape;
    private SharedPreferences myPrefs;
    private String clienteTipo;

    public class cliHolder extends RecyclerView.ViewHolder {


        TextView cliente;
        TextView grupo;
        TextView endereco;
        TextView letra;
        TextView sit;
        TextView titulos;
        TextView pedidos;
        CardView card;
        String clienteStatus;
        CircleImageView imagem;
        RelativeLayout back;
        String codigo;
        String order;
        Boolean exibir_titulos;
        Boolean situacao;
        LinearLayout item;
        LinearLayout lineraNew;

        public cliHolder(View view) {
            super(view);

            card = view.findViewById(R.id.cardView);
            item = view.findViewById(R.id.linearItem);
            cliente = view.findViewById(R.id.tvNome);
            letra = view.findViewById(R.id.tvLetra);
            grupo = view.findViewById(R.id.tvGrupo);
            endereco = view.findViewById(R.id.tvDetalhe);
            //sit = view.findViewById(R.id.letterOne);
            //pedidos = view.findViewById(R.id.letterTwo);
            //titulos = view.findViewById(R.id.letterThree);
            imagem = view.findViewById(R.id.ivImagem);
            lineraNew = view.findViewById(R.id.linearNew);
            //back = view.findViewById(R.id.layout);
            order = myPrefs.getString("show_name", "0");
            exibir_titulos = myPrefs.getBoolean("exibir_titulos", true);
            situacao = myPrefs.getBoolean("situacao", true);


            DBC = new sonicDatabaseCRUD(myCtx);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent i = new Intent(v.getContext(), sonicClientesDetalhe.class);
                    i.putExtra("CLIENTE_CODIGO", codigo);
                    i.putExtra("CLIENTE_NOME", cliente.getText());
                    i.putExtra("CLIENTE_GRUPO", grupo.getText());
                    i.putExtra("CLIENTE_STATUS", clienteStatus);
                    v.getContext().startActivity(i);


                }
            });

        }
    }

    public sonicClientesAdapter(List<sonicClientesHolder> cliente, Context ctx, String tipo) {

        myPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        myCons = new sonicConstants();
        this.clientes = cliente;
        this.filteredClientes = cliente;
        this.myCtx = ctx;
        this.clienteTipo = tipo;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(myCtx).inflate(R.layout.sonic_layout_cards_list, parent, false);
        cliHolder clientes = new cliHolder(view);
        return clientes;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        cliHolder holder = (cliHolder) viewHolder;
        holder.setIsRecyclable(false);
        sonicClientesHolder cli = clientes.get(position);

        holder.codigo = String.valueOf(cli.getCodigo());
        holder.clienteStatus = cli.getStatus();
        holder.lineraNew.setVisibility(cli.getStatus().equals("NOVO") ? View.VISIBLE : View.GONE);

        String letra;
        String letra2;

        switch (holder.order){
            case "0":
                holder.cliente.setText(cli.getRazaoSocial());
                letra = String.valueOf(cli.getRazaoSocial().charAt(0)).toUpperCase();
                break;
            case "1":
                holder.cliente.setText(cli.getNomeFantasia());
                letra = String.valueOf(cli.getNomeFantasia().charAt(0)).toUpperCase();
                break;
                default:
                holder.cliente.setText(cli.getRazaoSocial());
                letra = String.valueOf(cli.getRazaoSocial().charAt(0)).toUpperCase();
                break;
        }

        //DESTACA O GRUPO/CATEGORIA DO CLIENTE
        if(clienteTipo.equals("J") && !myCons.GRUPO_CLIENTES_CNPJ.equals("TODOS")){
            shape = new GradientDrawable();
            shape.setShape(GradientDrawable.RECTANGLE);
            shape.setColor(myCtx.getResources().getColor(R.color.colorPrimaryGreenLightT));
            shape.setCornerRadius(80);
            holder.grupo.setPadding(4,0,10,0);
            holder.grupo.setBackground(shape);
            holder.grupo.setTextColor(myCtx.getResources().getColor(R.color.colorTextPrimary));
        }else if(clienteTipo.equals("F") && !myCons.GRUPO_CLIENTES_CPF.equals("TODOS")){
            shape = new GradientDrawable();
            shape.setShape(GradientDrawable.RECTANGLE);
            shape.setColor(myCtx.getResources().getColor(R.color.colorPrimaryGreenLightT));
            shape.setCornerRadius(80);
            holder.grupo.setPadding(4,0,10,0);
            holder.grupo.setBackground(shape);
            holder.grupo.setTextColor(myCtx.getResources().getColor(R.color.colorTextPrimary));
        }

        holder.grupo.setText(cli.getGrupo());
        holder.endereco.setText(cli.getEndereco()+", "+cli.getBairro()+", "+cli.getMunicipio()+" - "+cli.getUf());

        if(cli.getTitulos()>0){
            holder.cliente.setTextColor(myCtx.getResources().getColor(R.color.colorPrimaryOrange));
            holder.grupo.setTextColor(myCtx.getResources().getColor(R.color.colorPrimaryOrange));
            holder.endereco.setTextColor(myCtx.getResources().getColor(R.color.colorPrimaryOrange));
            holder.titulos.setVisibility(View.VISIBLE);
        }

        if(holder.situacao && cli.getSituacao()>1){
            holder.cliente.setTextColor(myCtx.getResources().getColor(R.color.colorPrimaryOrangeDark));
            holder.grupo.setTextColor(myCtx.getResources().getColor(R.color.colorPrimaryOrangeDark));
            holder.endereco.setTextColor(myCtx.getResources().getColor(R.color.colorPrimaryOrangeDark));
            holder.sit.setVisibility(View.VISIBLE);
        }

        File file = new File(Environment.getExternalStorageDirectory(), myCons.LOCAL_IMG_CLIENTES + cli.getCodigo() + "_1.jpg");

        if(file.exists()){

            holder.imagem.setVisibility(View.VISIBLE);
            holder.letra.setVisibility(View.GONE);
            Glide.with(myCtx)
                    .load(file)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                    .into(holder.imagem);

        }else{

            holder.imagem.setVisibility(View.GONE);
            holder.letra.setVisibility(View.VISIBLE);
            holder.letra.setText(letra);
            holder.letra.setTypeface(Typeface.DEFAULT_BOLD);

        }


    }

    @Override
    public Filter getFilter() {
        if(userFilter == null)
            userFilter = new UserFilter(this, clientes);
        return userFilter;

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }


    private static class UserFilter extends Filter {

        private final sonicClientesAdapter adapter;

        private final List<sonicClientesHolder> originalList;

        private final List<sonicClientesHolder> filteredList;

        private UserFilter(sonicClientesAdapter adapter, List<sonicClientesHolder> originalList) {
            super();
            this.adapter = adapter;
            this.originalList = new LinkedList<>(originalList);
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();



            if (constraint.length() == 0) {
                filteredList.addAll(originalList);

            } else {

                final String filterPattern = constraint.toString().toUpperCase().trim();

                for (final sonicClientesHolder cli : originalList) {
                    if (cli.getRazaoSocial().contains(filterPattern) || cli.getNomeFantasia().contains(filterPattern)|| String.valueOf(cli.getCodigo()).contains(filterPattern)) {
                        filteredList.add(cli);

                    }

                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.clientes.clear();
            adapter.filteredClientes.addAll((ArrayList<sonicClientesHolder>) results.values);
            adapter.notifyDataSetChanged();
        }
    }


}

