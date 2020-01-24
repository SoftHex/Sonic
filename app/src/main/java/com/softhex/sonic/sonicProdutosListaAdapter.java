package com.softhex.sonic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

public class sonicProdutosListaAdapter extends RecyclerView.Adapter implements Filterable{

    private Context ctx;
    private List<sonicProdutosHolder> produtos;
    private List<sonicProdutosHolder> produtos_filtered;
    private prodFilter prodFilter;
    private AlertDialog.Builder dialogBuilder;
    private int lastPosition = -1;
    private sonicDatabaseCRUD DBC;
    private Bitmap bitmap;
    private sonicConstants myCons;
    private GradientDrawable shape;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public class prodHolder extends RecyclerView.ViewHolder {

        TextView descricao;
        int codigo;
        TextView codigo_full;
        TextView grupo;
        TextView situacao;
        CircleImageView imagem;
        TextView letra;
        CardView card;
        LinearLayout item;


        public prodHolder(View view) {
            super(view);

            card = (CardView)view.findViewById(R.id.cardView);
            item = view.findViewById(R.id.item);
            descricao = (TextView)view.findViewById(R.id.nome);
            grupo = (TextView)view.findViewById(R.id.grupo);
            codigo_full = (TextView)view.findViewById(R.id.detalhe);
            //situacao = (TextView)view.findViewById(R.id.situacao);
            letra = (TextView)view.findViewById(R.id.letra);
            imagem = (CircleImageView) view.findViewById(R.id.imagem);

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

            //notifyDataSetChanged();

            /*dialogBuilder = new AlertDialog.Builder(view.getContext());
            final LayoutInflater inflate = LayoutInflater.from(view.getContext());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    View v = inflate.inflate(R.layout.dialog_produto_catalogo, null);

                    Arquivo file = new Arquivo(Environment.getExternalStorageDirectory(), "/GO2/media/images/catalogo/"+codigo+".jpg");

                    ImageView img = (ImageView)v.findViewById(R.id.Produto);

                    Log.d("IMAGEM", file.toString());

                    if(file.exists()){

                        Log.d("IMAGEM", file.toString());

                        String path = Environment.getExternalStorageDirectory() + "/GO2/media/images/catalogo/"+codigo+".jpg";
                        Bitmap bitmap = BitmapFactory.decodeFile(path);

                        img.setImageBitmap(bitmap);

                    }else{

                        String path = Environment.getExternalStorageDirectory() + "/GO2/media/images/catalogo/no_image.png";
                        Bitmap bitmap = BitmapFactory.decodeFile(path);
                        img.setImageBitmap(bitmap);
                        //img.setBackgroundResource(R.mipmap.ic_no_image_available);

                    }

                    dialogBuilder.setMessage(nome_full.getText());

                    TextView estoque_diag = (TextView)v.findViewById(R.id.Estoque);
                    TextView estoque_minimo_diag = (TextView)v.findViewById(R.id.estoque_minimo);
                    TextView codigo_diag = (TextView)v.findViewById(R.id.codigo);
                    codigo_diag.setText("CÓDIGO: "+codigo);
                    estoque_diag.setText("ESTOQUE: "+Estoque.getText());
                    estoque_minimo_diag.setText("ESTOQUE MÍN: "+estoque_minimo.getText());

                    AlertDialog alertDialog = dialogBuilder.create();

                    if(v.getParent() == null){
                        alertDialog.setView(v);
                    }else{
                        v = null;
                        alertDialog.setView(v);
                    }

                    alertDialog.show();

                }
            });*/

        }
    }

    public sonicProdutosListaAdapter(List<sonicProdutosHolder> produto, Context ctx) {

        myCons = new sonicConstants();
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
        prodHolder produtos;
        View view;
        if(viewType == VIEW_TYPE_ITEM){
            view = LayoutInflater.from(ctx).inflate(R.layout.sonic_layout_cards_list, parent, false);
            return new prodHolder(view);
        }else{
            view = LayoutInflater.from(ctx).inflate(R.layout.sonic_layout_cards_list_shimmer, parent, false);
            return new prodHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        prodHolder holder = (prodHolder) viewHolder;
        sonicProdutosHolder prod = produtos.get(position);

        holder.descricao.setText(prod.getDescricao());
        //holder.descricao.setTextColor(Color.parseColor(prod.getSituacaoCor()));
        holder.codigo_full.setText("Cód.: "+prod.getCodigo()+" "+Html.fromHtml("&#187;")+" Referência: "+prod.getCodigoAlternativo());
        holder.codigo = prod.getCodigo();
        holder.grupo.setText(prod.getGrupo());

        if(!myCons.GRUPO_PRODUTOS.equals("TODOS")){
            shape = new GradientDrawable();
            shape.setShape(GradientDrawable.RECTANGLE);
            shape.setColor(ctx.getResources().getColor(R.color.colorPrimaryLightT));
            shape.setCornerRadius(80);
            holder.grupo.setPadding(10,0,10,0);
            holder.grupo.setBackground(shape);
        }

        File fileJpg = new File(Environment.getExternalStorageDirectory(), myCons.LOCAL_IMG_CATALOGO +String.valueOf(prod.getCodigo())+".JPG");

        if(fileJpg.exists()){

            sonicGlide.glideFile(ctx, holder.imagem, fileJpg);
            holder.imagem.setVisibility(View.VISIBLE);
            holder.letra.setVisibility(View.GONE);


        }else {
            holder.imagem.setVisibility(View.GONE);
            holder.letra.setVisibility(View.VISIBLE);
            holder.letra.setText(String.valueOf(prod.getDescricao().charAt(0)));
            //holder.letra.setBackground((ctx).getResources().getDrawable(R.drawable.circle_textview));
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

        private final sonicProdutosListaAdapter adapter;

        private final List<sonicProdutosHolder> originalList;

        private final List<sonicProdutosHolder> filteredList;

        private prodFilter(sonicProdutosListaAdapter adapter, List<sonicProdutosHolder> originalList) {
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
