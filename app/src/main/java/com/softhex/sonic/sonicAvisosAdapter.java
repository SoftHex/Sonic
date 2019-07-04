package com.softhex.sonic;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrador on 11/08/2017.
 */

public class sonicAvisosAdapter extends RecyclerView.Adapter implements Filterable{

    private Context ctx;
    private List<sonicAvisosHolder> avisos;
    private List<sonicAvisosHolder> filtered_avisos;
    private AvisoFilter avsFilter;
    private int lastPosition = -1;
    private sonicUtils myUtil;
    private sonicDatabaseCRUD DBC;

    public class avsHolder extends RecyclerView.ViewHolder {

        int codigo;
        int status;
        TextView star_one;
        TextView star_two;
        TextView star_three;
        TextView letra;
        TextView autor;
        TextView titulo;
        String hora;
        TextView data;
        TextView mensagem;
        TextView close;
        CardView card;

        public avsHolder(View view) {
            super(view);

            letra = (TextView)view.findViewById(R.id.letra);
            autor = (TextView)view.findViewById(R.id.nome);
            titulo = (TextView)view.findViewById(R.id.titulo);
            data = (TextView)view.findViewById(R.id.data);
            mensagem = (TextView)view.findViewById(R.id.mensagem);
            card = (CardView)view.findViewById(R.id.cardView);

            myUtil = new sonicUtils(view.getContext());

            DBC = new sonicDatabaseCRUD(view.getContext());

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(status==0){
                        DBC.Avisos.saveAvisosLidos(codigo);
                    }

                    TextView dados[] = {autor, titulo, mensagem, data};
                    exibirItemInfo(dados, hora);

                    //selectTaskListItem(getPosition());
                    //view.setBackgroundColor(Color.GRAY);

                }
            });



            //setAnimation(view, 0);

        }
    }


    public sonicAvisosAdapter(List<sonicAvisosHolder> avisos, Context ctx) {

        this.avisos = avisos;
        this.filtered_avisos = avisos;
        this.ctx = ctx;
    }


    public void updateList(List<sonicAvisosHolder> list){
        avisos = list;
        list.clear();
        notifyDataSetChanged();
    }

    public void clearList(){
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        if(avsFilter == null)
            avsFilter = new AvisoFilter(this, avisos);
        return avsFilter;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(ctx).inflate(R.layout.sonic_layout_cards_list, parent, false);
        avsHolder avs = new avsHolder(view);

        return avs;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        avsHolder holder = (avsHolder) viewHolder;
        holder.setIsRecyclable(false);
        sonicAvisosHolder avs = avisos.get(position);


        holder.codigo = avs.getCodigo();
        holder.status = avs.getStatus();
        holder.hora = avs.getHora();
        holder.autor.setText(avs.getAutor());
        //holder.data.setText(myUtil.Data.dataPorExtenso(avs.getData()));
        holder.titulo.setText(avs.getTitulo());
        holder.mensagem.setText(avs.getMensagem());

        holder.letra.setText(String.valueOf(avs.getAutor().charAt(0)).toUpperCase());
        holder.letra.setBackground((ctx).getResources().getDrawable(R.drawable.circle_textview));

        //Drawable drawable = myCtx.getResources().getDrawable(R.mipmap.ic_star_outline_grey600_18dp);
        //drawable = DrawableCompat.wrap(drawable);
        //DrawableCompat.setTint(drawable, ContextCompat.getColor(myCtx,R.color.chart_yellow));

        if(avs.getStatus()==1){
            holder.autor.setTextColor(ctx.getResources().getColor(R.color.colorTextNoAccent));
            holder.autor.setTypeface(Typeface.DEFAULT);
            holder.titulo.setTextColor(ctx.getResources().getColor(R.color.colorTextNoAccent));
            holder.titulo.setTypeface(Typeface.DEFAULT);
            holder.data.setTextColor(ctx.getResources().getColor(R.color.colorTextNoAccent));
            holder.data.setTypeface(Typeface.DEFAULT);
            //holder.star_one.setTextColor(ContextCompat.getColor(myCtx,R.color.chart_yellow));
            //holder.star_one.setCompoundDrawables(drawable,drawable,drawable,drawable);

        }

        switch (avs.getPrioridade()){
            case 2:
                holder.star_two.setVisibility(View.VISIBLE);
                break;
            case 3:
                holder.star_two.setVisibility(View.VISIBLE);
                holder.star_three.setVisibility(View.VISIBLE);
                break;
                default:
        }
        //holder.mensagem.setText(myUtil.Data.dataFotmatadaBarra(tit.getDataEmissao())+" "+Html.fromHtml("&#187;")+" "+myUtil.Data.dataFotmatadaBarra(tit.getDataVencimento())+" "+Html.fromHtml("&#187;")+" "+tit.getDiasAtraso());
        //holder.valor.setText("R$ "+ String.format("%,.2f", Float.valueOf( tit.getValor().replace(',','.') )));
        //holder.valor.setTextColor(Color.parseColor(tit.getSituacaoCor()));

        setAnimation(holder.itemView, position);

    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.fade_in);
            animation.setDuration(1000);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public void exibirItemInfo(TextView dados[], String _hora){

        final TextView _autor = dados[0];
        final TextView _titulo = dados[1];
        final TextView _data = dados[3];

        /*View view = LayoutInflater.from(ctx).inflate(R.layout.dialog_avisos, null);
        final AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
        TextView autor = (TextView)view.findViewById(R.id.autor);
        TextView titulo = (TextView)view.findViewById(R.id.titulo);
        TextView mensagem = (TextView)view.findViewById(R.id.mensagem);
        TextView hora = (TextView)view.findViewById(R.id.hora);
        autor.setText("De: "+dados[0].getText().toString());
        titulo.setText("Assunto: "+dados[1].getText().toString());
        mensagem.setText(dados[2].getText().toString());
        hora.setText(_hora);

        dialog.setView(view);
        final AlertDialog ad = dialog.show();

        TextView close = (TextView)view.findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.dismiss();
                //dialog.dismiss();
            }
        });

        ad.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                _autor.setTextColor(ctx.getResources().getColor(R.color.colorTextNoAccent));
                _autor.setTypeface(Typeface.DEFAULT);
                _titulo.setTextColor(ctx.getResources().getColor(R.color.colorTextNoAccent2));
                _titulo.setTypeface(Typeface.DEFAULT);
                _data.setTextColor(ctx.getResources().getColor(R.color.colorTextNoAccent2));
                _data.setTypeface(Typeface.DEFAULT);


            }
        });*/



        //dialog.show();

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
        return avisos.size();
    }


private static class AvisoFilter extends Filter {

        private final sonicAvisosAdapter adapter;

        private final List<sonicAvisosHolder> originalList;

        private final List<sonicAvisosHolder> filteredList;

        private AvisoFilter(sonicAvisosAdapter adapter, List<sonicAvisosHolder> originalList) {
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

                for (final sonicAvisosHolder avs : originalList) {
                    if (avs.getTitulo().toUpperCase().trim().contains(filterPattern) || avs.getMensagem().toUpperCase().trim().contains(filterPattern) || avs.getAutor().toUpperCase().trim().contains(filterPattern)) {
                        filteredList.add(avs);

                    }

                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.avisos.clear();
            adapter.filtered_avisos.addAll((ArrayList<sonicAvisosHolder>) results.values);
            adapter.notifyDataSetChanged();
        }
    }

}

