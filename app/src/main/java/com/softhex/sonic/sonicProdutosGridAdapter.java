package com.softhex.sonic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Environment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Administrador on 11/08/2017.
 */

public class sonicProdutosGridAdapter extends RecyclerView.Adapter implements Filterable{

    private Context ctx;
    private List<sonicProdutosHolder> produtos;
    private List<sonicProdutosHolder> produtos_filtered;
    private prodFilter prodFilter;
    private sonicDatabaseCRUD DBC;

    public class prodHolder extends RecyclerView.ViewHolder {

        TextView descricao;
        int codigo;
        TextView grupo;
        ImageView imagem;
        LinearLayout item;


        public prodHolder(View view) {
            super(view);

            item = view.findViewById(R.id.item);
            descricao = view.findViewById(R.id.descricao);
            grupo = view.findViewById(R.id.grupo);
            imagem = view.findViewById(R.id.imagem);

            DBC = new sonicDatabaseCRUD(ctx);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DBC.Produto.setNaoSelecionado();
                    DBC.Produto.setSelecionado(codigo);

                    Intent i = new Intent(v.getContext(), sonicProdutosDetalhe.class);
                    sonicConstants.PUT_EXTRA_PRODUTO_NOME = descricao.getText().toString();
                    sonicConstants.PUT_EXTRA_PRODUTO_ID = codigo;
                    v.getContext().startActivity(i);

                }


            });

        }
    }

    public sonicProdutosGridAdapter(List<sonicProdutosHolder> produto, Context ctx) {

        this.produtos = produto;
        this.produtos_filtered = produto;
        this.ctx = ctx;
    }

    public void updateList(List<sonicProdutosHolder> list){
        produtos = list;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        if(prodFilter == null)
            prodFilter = new prodFilter(this, produtos);
        return prodFilter;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(ctx).inflate(R.layout.sonic_layout_cards_grid, parent, false);
            prodHolder produtos = new prodHolder(view);
            return produtos;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        prodHolder holder = (prodHolder) viewHolder;
        sonicProdutosHolder prod = produtos.get(position);
        holder.codigo = prod.getCodigo();
        holder.descricao.setText(prod.getDescricao());
        holder.grupo.setText(prod.getGrupo());

        if(!sonicConstants.GRUPO_PRODUTOS_GRID.equals("TODOS")){
            GradientDrawable shape;
            shape = new GradientDrawable();
            shape.setShape(GradientDrawable.RECTANGLE);
            shape.setColor(ctx.getResources().getColor(R.color.colorPrimaryGreenLightT));
            shape.setCornerRadius(80);
            holder.grupo.setPadding(10,0,10,0);
            holder.grupo.setBackground(shape);
            holder.grupo.setTextColor(ctx.getResources().getColor(R.color.colorPrimary));
            holder.grupo.setTypeface(null, Typeface.ITALIC);
        }

        File fileJpg = new File(Environment.getExternalStorageDirectory(), sonicConstants.LOCAL_IMG_CATALOGO +String.valueOf(prod.getCodigo())+".JPG");

        if(fileJpg.exists()){

            //sonicGlide.glideFile(ctx, holder.imagem, fileJpg);
            Glide.with(ctx).clear(holder.imagem);
            Glide.with(ctx)
                    .load(fileJpg)
                    .dontAnimate()
                    //.apply(new RequestOptions().override(105, 105))
                    .override(200,180)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(false)
                    .transition(GenericTransitionOptions.with(R.anim.fade_in))
                    .into(holder.imagem);



        }else {

            Glide.with(ctx).clear(holder.imagem);
            Glide.with(ctx)
                    .load(R.drawable.nophoto)
                    .dontAnimate()
                    //.apply(new RequestOptions().override(105, 105))
                    .override(200,180)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(false)
                    .transition(GenericTransitionOptions.with(R.anim.fade_in))
                    .into(holder.imagem);


        }


    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
        }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return produtos == null ? 0 : produtos.size();
    }

    private static class prodFilter extends Filter {

        private final sonicProdutosGridAdapter adapter;

        private final List<sonicProdutosHolder> originalList;

        private final List<sonicProdutosHolder> filteredList;

        private prodFilter(sonicProdutosGridAdapter adapter, List<sonicProdutosHolder> originalList) {
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



                for (final sonicProdutosHolder prod : originalList) {

                    String codigo = String.valueOf(prod.getCodigo());

                    if (prod.getDescricao().contains(filterPattern) || prod.getCodigoAlternativo().contains(filterPattern) ||codigo.contains(filterPattern)) {
                        filteredList.add(prod);

                    }

                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.produtos.clear();
            adapter.produtos_filtered.addAll((ArrayList<sonicProdutosHolder>) results.values);
            adapter.notifyDataSetChanged();
        }
    }

}
