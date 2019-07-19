package com.softhex.sonic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class sonicSincronizacaoDownloadAdapter extends RecyclerView.Adapter<sonicSincronizacaoDownloadAdapter.myViewHolder> {

    private Context myCtx;
    private List<sonicSincronizacaoDownloadHolder> myList;
    private List<sonicUsuariosHolder> myListUsers;
    private sonicFtp myFtp;

    public sonicSincronizacaoDownloadAdapter(Context myCtx, List<sonicSincronizacaoDownloadHolder> mList) {
        this.myCtx = myCtx;
        this.myList = mList;
        this.myFtp = new sonicFtp(myCtx);
        this.myListUsers = new sonicDatabaseCRUD(myCtx).Usuarios.selectUsuarioAtivo();
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(myCtx);
        view = inflater.inflate(R.layout.sonic_layout_cards_sincronizacao_download, viewGroup, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(myViewHolder myViewHolder, int i) {

        myViewHolder.titulo.setText(myList.get(i).getTitulo());
        myViewHolder.descricao.setText(myList.get(i).getDescricao());
        myViewHolder.card.setId(i);

        Glide.with(myCtx)
                .load(myList.get(i).getImagem())
                .apply(new RequestOptions().override(600,600))
                .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                .into(myViewHolder.imagem);


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

    public static class myViewHolder extends RecyclerView.ViewHolder{

        CardView card;
        ImageView imagem;
        TextView titulo;
        TextView descricao;

        public myViewHolder(View itemView) {
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
                            Log.d("ID",v.getId()+"");
                            sonicConstants.DOWNLOAD_TYPE = "DADOS";
                            //file = String.format("%5s",myListUsers.get(0).getCodigo()).replace(" ", "0")+".TXT";
                            //myFtp.downloadFile2(sonicConstants.FTP_USUARIOS+file, sonicConstants.LOCAL_TMP+file);
                            //Toast.makeText(myCtx,"ID="+i,Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            Log.d("ID",v.getId()+"");
                            sonicConstants.DOWNLOAD_TYPE = "CATALOGO";
                            //file = "CATALOGO.zip";
                            //myFtp.downloadFile2(sonicConstants.FTP_IMAGENS +file, sonicConstants.LOCAL_TMP+file);
                            //Toast.makeText(myCtx,"ID="+i,Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            Log.d("ID",v.getId()+"");
                            sonicConstants.DOWNLOAD_TYPE = "CLIENTES";
                            //file = "CLIENTES.zip";
                            //myFtp.downloadFile2(sonicConstants.FTP_IMAGENS +file, sonicConstants.LOCAL_TMP+file);
                            //Toast.makeText(myCtx,"ID="+i,Toast.LENGTH_SHORT).show();
                        case 3:
                            Log.d("ID",v.getId()+"");
                            sonicConstants.DOWNLOAD_TYPE = "ESTOQUE";
                            //file = "ESTOQUE.TXT";
                            //myFtp.downloadFile2(sonicConstants.FTP_ESTOQUE +file, sonicConstants.LOCAL_TMP+file);
                            //Toast.makeText(myCtx,"ID="+i,Toast.LENGTH_SHORT).show();
                        case 4:
                            Log.d("ID",v.getId()+"");
                            sonicConstants.DOWNLOAD_TYPE = "LOCAL";
                            //file = "ESTOQUE.TXT";
                            //myFtp.downloadFile2(sonicConstants.FTP_ESTOQUE +file, sonicConstants.LOCAL_TMP+file);
                            //Toast.makeText(myCtx,"ID="+i,Toast.LENGTH_SHORT).show();
                    }


                }
            });

        }
    }

}
