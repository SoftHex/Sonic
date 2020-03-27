package com.softhex.sonic;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private List<sonicUsuariosHolder> myListUsers;
    private sonicFtp myFtp;
    private sonicPreferences mPrefs;

    public class sincHolder extends RecyclerView.ViewHolder {

        CardView card;
        ImageView imagem;
        TextView titulo;
        TextView descricao;

        public sincHolder(View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.card);
            imagem = itemView.findViewById(R.id.imagem);
            titulo = itemView.findViewById(R.id.titulo);
            descricao = itemView.findViewById(R.id.descricao);

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String file = "";

                    switch (v.getId()){
                        case 0:
                            mPrefs.Geral.setSincRefresh(true);
                            sonicConstants.DOWNLOAD_TYPE = "DADOS";
                            file = String.format("%5s",myListUsers.get(0).getCodigo()).replace(" ", "0")+".TXT";
                            myFtp.downloadFile2(sonicConstants.FTP_USUARIOS+file, sonicConstants.LOCAL_TEMP+file);
                            break;
                        case 1:
                            sonicConstants.DOWNLOAD_TYPE = "CATALOGO";
                            file = "CATALOGO.zip";
                            myFtp.downloadFile2(sonicConstants.FTP_IMAGENS +file, sonicConstants.LOCAL_TEMP+file);
                            break;
                        case 2:
                            sonicConstants.DOWNLOAD_TYPE = "CLIENTES";
                            file = "CLIENTES.zip";
                            myFtp.downloadFile2(sonicConstants.FTP_IMAGENS +file, sonicConstants.LOCAL_TEMP+file);
                            break;
                        case 3:
                            sonicConstants.DOWNLOAD_TYPE = "ESTOQUE";
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
            });

        }
    }


    public sonicSincronizacaoDownloadAdapterT(Activity act, List<sonicSincronizacaoDownloadHolder> mList) {
        this.myCtx = act;
        this.mAct = act;
        this.mPrefs = new sonicPreferences(act);
        this.myList = mList;
        this.myFtp = new sonicFtp(act);
        this.myListUsers = new sonicDatabaseCRUD(myCtx).Usuario.selectUsuarioAtivo();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(myCtx);
        view = inflater.inflate(R.layout.sonic_layout_cards_sincronizacao_download, viewGroup, false);
        return new sincHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        sincHolder holder = (sincHolder)viewHolder;

        holder.titulo.setText(myList.get(i).getTitulo());
        holder.descricao.setText(myList.get(i).getDescricao());
        holder.card.setId(i);

        Glide.with(myCtx)
                .load(myList.get(i).getImagem())
                .apply(new RequestOptions().override(600,600))
                .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                .into(holder.imagem);
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

}
