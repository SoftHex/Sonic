package com.softhex.sonic;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

/**
 * Created by Administrador on 11/08/2017.
 */

public class sonicClientesAdapter extends RecyclerView.Adapter implements Filterable{

    private Context mContext;
    private List<sonicClientesHolder> clientes;
    private List<sonicClientesHolder> filteredClientes;
    private UserFilter userFilter;
    private sonicDatabaseCRUD DBC;
    private sonicConstants myCons;
    private GradientDrawable shape;
    private sonicPreferences mPrefs;
    private String clienteTipo;
    private Boolean nFantasia;
    private Boolean cliSemCompra;

    public class cliHolder extends RecyclerView.ViewHolder {


        int codigo;
        TextView tvNome;
        TextView tvGrupo;
        TextView tvDetalhe;
        TextView letra;
        TextView sit;
        TextView titulos;
        CardView card;
        String clienteStatus;
        ImageView mImage;
        Boolean situacao;
        LinearLayout item;
        LinearLayout lineraNew;
        LinearLayout llExtra;
        TextView tvSemCompra, tvAtraso;

        public cliHolder(View view) {
            super(view);

            card = view.findViewById(R.id.cardView);
            item = view.findViewById(R.id.linearItem);
            tvNome = view.findViewById(R.id.tvNome);
            letra = view.findViewById(R.id.tvLetra);
            tvGrupo = view.findViewById(R.id.tvGrupo);
            tvDetalhe = view.findViewById(R.id.tvDetalhe);
            mImage = view.findViewById(R.id.ivImagem);
            lineraNew = view.findViewById(R.id.linearNew);
            llExtra = view.findViewById(R.id.llExtra);
            tvSemCompra = view.findViewById(R.id.tvSemCompra);
            tvAtraso = view.findViewById(R.id.tvAtraso);

            DBC = new sonicDatabaseCRUD(mContext);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mPrefs.Clientes.setId(codigo);
                    mPrefs.Clientes.setNome(tvNome.getText().toString());
                    mPrefs.Clientes.setGrupo(tvGrupo.getText().toString());

                    Intent i = new Intent(v.getContext(), sonicClientesDetalhe.class);
                    v.getContext().startActivity(i);

                    /*ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            (Activity)mContext
                            ,Pair.create(mImage, ViewCompat.getTransitionName(mImage))
                            ,Pair.create(tvNome, ViewCompat.getTransitionName(tvNome))
                            ,Pair.create(tvGrupo, ViewCompat.getTransitionName(tvGrupo)));

                    v.getContext().startActivity(i, options.toBundle());*/


                }
            });

        }
    }

    public sonicClientesAdapter(List<sonicClientesHolder> cliente, Context ctx, String tipo) {

        this.myCons = new sonicConstants();
        this.clientes = cliente;
        this.filteredClientes = cliente;
        this.mContext = ctx;
        this.clienteTipo = tipo;
        this.mPrefs = new sonicPreferences(ctx);
        this.nFantasia =  mPrefs.Clientes.getClienteExibicao().equals("Nome Fantasia") ? true : false;
        this.cliSemCompra = mPrefs.Clientes.getClienteSemCompra();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.sonic_layout_cards_list, parent, false);
        cliHolder clientes = new cliHolder(view);
        return clientes;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        cliHolder holder = (cliHolder) viewHolder;
        holder.setIsRecyclable(false);
        sonicClientesHolder cli = clientes.get(position);
        String cliNome = nFantasia ? cli.getNomeFantasia() : cli.getRazaoSocial();

        holder.codigo = cli.getCodigo();
        holder.clienteStatus = cli.getStatus();
        holder.tvNome.setText(cliNome);

        holder.tvSemCompra.setVisibility((cliSemCompra && cli.getCliSemCompra()>0) ? View.VISIBLE : View.GONE);
        //holder.llExtra.setVisibility(((cliSemCompra && cli.getCliSemCompra()!=0) || cli.getTitulosEmAtraso()>0 ) ? View.VISIBLE : View.GONE);
        //holder.llExtra.setBackground(cli.getTitulosEmAtraso()>0 ? mContext.getResources().getDrawable(R.drawable.rounded_box_right_orange) : mContext.getResources().getDrawable(R.drawable.rounded_box_right_green));
        holder.tvAtraso.setVisibility(cli.getTitulosEmAtraso()>0 ? View.VISIBLE : View.GONE);

        holder.tvGrupo.setText("CÓD.: "+cli.getCodigo()+" / GRUPO: "+cli.getGrupo());
        holder.tvDetalhe.setText(cli.getEndereco()+", "+cli.getBairro()+", "+cli.getMunicipio()+" - "+cli.getUf());

        File file = new File(Environment.getExternalStorageDirectory(), myCons.LOCAL_IMG_CLIENTES + cli.getCodigo() + ".JPG");

        if(file.exists()){

            holder.mImage.setVisibility(View.VISIBLE);
            holder.letra.setVisibility(View.GONE);
            Glide.with(mContext)
                    .load(file)
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                    .into(holder.mImage);

        }else{

            holder.mImage.setVisibility(View.GONE);
            holder.letra.setVisibility(View.VISIBLE);
            holder.letra.setText(String.valueOf(cliNome.charAt(0)).toUpperCase());

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


    private class UserFilter extends Filter {

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
                    if(mPrefs.Clientes.getClienteExibicao().contains("Nome Fantasia")){
                        if (cli.getNomeFantasia().contains(filterPattern)|| String.valueOf(cli.getCodigo()).contains(filterPattern)) {
                            filteredList.add(cli);
                        }
                    }else {
                        if (cli.getRazaoSocial().contains(filterPattern)|| String.valueOf(cli.getCodigo()).contains(filterPattern)) {
                            filteredList.add(cli);
                        }
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

