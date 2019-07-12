package com.softhex.sonic;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.List;

public class sonicSincronizacaoDownloadAdapter extends RecyclerView.Adapter<sonicSincronizacaoDownloadAdapter.myViewHolder> {

    private Context myCtx;
    private List<sonicSincronizacaoDownloadHolder> myList;
    private List<sonicUsuariosHolder> myListUsers;

    public sonicSincronizacaoDownloadAdapter(Context myCtx, List<sonicSincronizacaoDownloadHolder> mList) {
        this.myCtx = myCtx;
        this.myList = mList;
        this.myListUsers = new sonicDatabaseCRUD(myCtx).Usuarios.selectUsuarioAtivo();
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(myCtx);
        view = inflater.inflate(R.layout.sonic_layout_cards_sincronizacao_download, viewGroup, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i) {

        myViewHolder.titulo.setText(myList.get(i).getTitulo());
        myViewHolder.descricao.setText(myList.get(i).getDescricao());
        //myViewHolder.imagem.setImageResource(myList.get(i).getImagem());

        Glide.with(myCtx)
                .load(myList.get(i).getImagem())
                .apply(new RequestOptions().override(600,600))
                .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                .into(myViewHolder.imagem);


        myViewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (i){
                    case 0:
                        String file = String.format("%5s",myListUsers.get(0).getCodigo()).replace(" ", "0")+".TXT";
                        new sonicFtp(myCtx).downloadFile2(sonicConstants.FTP_USUARIOS+file, sonicConstants.LOCAL_TMP+file);
                        break;
                    case 1:
                        file = "CATALOGO.zip";
                        //ftpFile = sonicConstants.FTP_IMAGENS+file;
                        //myFtp.downloadFile2(sonicConstants.FTP_IMAGENS +"CATALOGO.zip", sonicConstants.LOCAL_TMP+file);
                        //myUtil.Arquivo.unzipFile(String.format(file));
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{

        CardView card;
        ImageView imagem;
        TextView titulo;
        TextView descricao;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.card);
            imagem = itemView.findViewById(R.id.imagem);
            titulo = itemView.findViewById(R.id.titulo);
            descricao = itemView.findViewById(R.id.descricao);


        }
    }

}
