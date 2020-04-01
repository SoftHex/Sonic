package com.softhex.sonic;

import android.app.Activity;
import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class sonicSincronizacaoDownloadAdapterT extends RecyclerView.Adapter {

    private Context myCtx;
    private Activity mAct;
    private List<sonicSincronizacaoDownloadHolder> myList;
    private sonicFtp myFtp;
    private sonicPreferences mPrefs;
    private sonicUtils mUtils;

    public class sincHolder extends RecyclerView.ViewHolder {

        CardView mCard;
        ImageView ivImagem;
        TextView tvTitulo;
        TextView tvDescricao;
        TextView tvDependencia;
        TextView tvSincronizacao;
        LinearLayout linearItem;

        public sincHolder(View itemView) {
            super(itemView);

            mCard = itemView.findViewById(R.id.mCard);
            ivImagem = itemView.findViewById(R.id.ivImagem);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvDescricao = itemView.findViewById(R.id.tvDescricao);
            //tvDependencia = itemView.findViewById(R.id.tvDependencia);
            tvSincronizacao = itemView.findViewById(R.id.tvSincronizacao);
            linearItem = itemView.findViewById(R.id.linearItem);

        }
    }


    public sonicSincronizacaoDownloadAdapterT(Activity act, List<sonicSincronizacaoDownloadHolder> mList) {
        this.myCtx = act;
        this.mAct = act;
        this.mUtils = new sonicUtils(act);
        this.mPrefs = new sonicPreferences(act);
        this.myList = mList;
        this.myFtp = new sonicFtp(act);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(myCtx);
        view = inflater.inflate(R.layout.sonic_layout_cards_sincronizacao_download, viewGroup, false);
        return new sincHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        sincHolder holder = (sincHolder)viewHolder;

        holder.tvTitulo.setText(myList.get(position).getTitulo());
        holder.tvDescricao.setText(myList.get(position).getDescricao());
        SpannableString stringEstilizada = new SpannableString("Ainda não houve sincronização.");
        stringEstilizada.setSpan(new StrikethroughSpan(),0,stringEstilizada.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        holder.tvSincronizacao.setText((myList.get(position).getData().equals("") || myList.get(position).getData()=="") ? stringEstilizada : "Sincronizado "+mUtils.Data.dataSocial(myList.get(position).getData())+" às "+myList.get(position).getHora());
        holder.linearItem.setId(position);

        holder.linearItem.setOnClickListener((View v)-> {

                onItemClicked(v);
        });

        Glide.with(myCtx)
                .load(myList.get(position).getImagem())
                .apply(new RequestOptions().override(300,300))
                .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                .into(holder.ivImagem);
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
        return myList.size();
    }

    public void onItemClicked(View v){

        String file;

        switch (v.getId()){
            case 0:
                mPrefs.Geral.setSincRefresh(true);
                mPrefs.Sincronizacao.setDownloadType("DADOS");
                file = String.format("%5s",mPrefs.Users.getUsuarioId()).replace(" ", "0")+".TXT";
                myFtp.downloadFile2(sonicConstants.FTP_USUARIOS+file, sonicConstants.LOCAL_TEMP+file);
                break;
            case 1:
                mPrefs.Sincronizacao.setDownloadType("CATALOGO");
                file = "CATALOGO.zip";
                myFtp.downloadFile2(sonicConstants.FTP_IMAGENS +file, sonicConstants.LOCAL_TEMP+file);
                break;
            case 2:
                mPrefs.Sincronizacao.setDownloadType("CLIENTES");
                file = "CLIENTES.zip";
                myFtp.downloadFile2(sonicConstants.FTP_IMAGENS +file, sonicConstants.LOCAL_TEMP+file);
                break;
            case 3:
                mPrefs.Sincronizacao.setDownloadType("ESTOQUE");
                file = "ESTOQUE.TXT";
                myFtp.downloadFile2(sonicConstants.FTP_ESTOQUE +file, sonicConstants.LOCAL_TEMP+file);
                break;
            case 4:
                sonicConstants.DOWNLOAD_TYPE = "LOCAL";
                file = "LOCAL.TXT";
                myFtp.downloadFile2(sonicConstants.FTP_ESTOQUE +file, sonicConstants.LOCAL_TEMP+file);
                break;
        }

    }

}
