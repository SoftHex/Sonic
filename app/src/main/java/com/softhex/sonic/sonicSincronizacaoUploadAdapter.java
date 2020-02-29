package com.softhex.sonic;

import android.content.Context;
import android.graphics.Color;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrador on 11/08/2017.
 */

public class sonicSincronizacaoUploadAdapter extends RecyclerView.Adapter{

    private Context ctx;
    //private go_ftp_baixar down;
    //private go_first_access gravar;
    //private go_database_common_crud DBC;
    //private List<go_sincronizar_holder> sincs;
    private String[] nome, desc, last_sinc_data, last_sinc_hora;
    private Calendar c = Calendar.getInstance();
    private SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
    private String data_atual = data.format(c.getTime());

    public class sincHolder extends RecyclerView.ViewHolder {


        TextView nome;
        TextView descricao;
        TextView atualizacao;
        ImageView situacao;
        ProgressBar progress;
        View teste;

        public sincHolder(View view) {
            super(view);
            //nome = (TextView)view.findViewById(R.id.nome_upload);
            //tvNome = (TextView)view.findViewById(R.id.descricao_upload);
            //atualizacao = (TextView)view.findViewById(R.id.atualizacao_upload);
            //situacao = (ImageView)view.findViewById(R.id.situacao_upload);
            //progress = (ProgressBar)view.findViewById(R.id.progress_upload);

            /*view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getPosition();
                    final Context ctx = view.getContext();
                    down = new go_ftp_baixar();
                    gravar = new go_first_access();
                    String arquivo = removeAccents(nome.getText().toString());
                    progress.setVisibility(View.VISIBLE);
                    down.execute(arquivo);
                    DBC = new go_database_common_crud(view.getContext());
                    Handler handler = new Handler();


                    switch (arquivo){
                        case "SINCRONIZAR TUDO":
                            break;
                        case "CLIENTES":
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    String secao = "[CLIENTES]";
                                    String arquivo = "CLIENTES";
                                    Calendar c = Calendar.getInstance();
                                    SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
                                    String hora_atual = hora.format(c.getTime());
                                    if(gravar.gravarDados(ctx,secao,arquivo, "SINCRONIZACAO")){
                                        progress.setVisibility(View.GONE);
                                        situacao.setBackgroundColor(Color.GREEN);
                                        DBC.Sincronizacao.saveSincronizacao(arquivo,"DOWNLOAD", "SINCRONIZACAO", "setOnClickListener()");
                                        atualizacao.setText("ÚLTIMA SINCRONIZAÇÃO: HOJE ÀS "+hora_atual);
                                        Toasty.success(ctx, "Sincronizada tabela de CLIENTES.", Toast.LENGTH_LONG).show();
                                    }else{
                                        progress.setVisibility(View.GONE);
                                        Toasty.error(ctx, "Erro ao gravar na tabela CLIENTES.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }, 2000);
                            break;
                        case "PRODUTOS":
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    String secao = "[PRODUTOS]";
                                    String arquivo = "PRODUTOS";
                                    Calendar c = Calendar.getInstance();
                                    SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
                                    String hora_atual = hora.format(c.getTime());
                                    if(gravar.gravarDados(ctx,secao,arquivo, "SINCRONIZACAO")){
                                        progress.setVisibility(View.GONE);
                                        situacao.setBackgroundColor(Color.GREEN);
                                        DBC.Sincronizacao.saveSincronizacao(arquivo,"DOWNLOAD", "SINCRONIZACAO", "setOnClickListener()");
                                        atualizacao.setText("ÚLTIMA SINCRONIZAÇÃO: HOJE ÀS "+hora_atual);
                                        Toasty.success(ctx, "Sincronizada tabela de PRODUTOS.", Toast.LENGTH_LONG).show();
                                    }else{
                                        progress.setVisibility(View.GONE);
                                        Toasty.error(ctx, "Erro ao gravar na tabela PRODUTOS.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }, 2000);
                            break;
                        case "TITULOS":
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    String secao = "[TITULOS]";
                                    String arquivo = "TITULOS";
                                    Calendar c = Calendar.getInstance();
                                    SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
                                    String hora_atual = hora.format(c.getTime());
                                    if(gravar.gravarDados(ctx,secao,arquivo, "SINCRONIZACAO")){
                                        progress.setVisibility(View.GONE);
                                        situacao.setBackgroundColor(Color.GREEN);
                                        DBC.Sincronizacao.saveSincronizacao(arquivo,"DOWNLOAD", "SINCRONIZACAO", "setOnClickListener()");
                                        atualizacao.setText("ÚLTIMA SINCRONIZAÇÃO: HOJE ÀS "+hora_atual);
                                        Toasty.success(ctx, "Sincronizada tabela de TÍTULOS.", Toast.LENGTH_LONG).show();
                                    }else{
                                        progress.setVisibility(View.GONE);
                                        Toasty.error(ctx, "Erro ao gravar na tabela TÍTULOS.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }, 2000);
                            break;
                        case "PEDIDOS":
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    String secao = "[PEDIDOS]";
                                    String arquivo = "PEDIDOS";
                                    Calendar c = Calendar.getInstance();
                                    SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
                                    String hora_atual = hora.format(c.getTime());
                                    if(gravar.gravarDados(ctx,secao,arquivo, "SINCRONIZACAO")){
                                        progress.setVisibility(View.GONE);
                                        situacao.setBackgroundColor(Color.GREEN);
                                        DBC.Sincronizacao.saveSincronizacao(arquivo,"DOWNLOAD", "SINCRONIZACAO", "setOnClickListener()");
                                        atualizacao.setText("ÚLTIMA SINCRONIZAÇÃO: HOJE ÀS "+hora_atual);
                                        Toasty.success(ctx, "Sincronizada tabela de PEDIDOS.", Toast.LENGTH_LONG).show();
                                    }else{
                                        progress.setVisibility(View.GONE);
                                        Toasty.error(ctx, "Erro ao gravar na tabela PEDIDOS.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }, 2000);
                            break;
                        case "RETORNO":
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    String secao = "[RETORNO]";
                                    String arquivo = "RETORNO";
                                    Calendar c = Calendar.getInstance();
                                    SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
                                    String hora_atual = hora.format(c.getTime());
                                    if(gravar.gravarDados(ctx,secao,arquivo, "SINCRONIZACAO")){
                                        progress.setVisibility(View.GONE);
                                        situacao.setBackgroundColor(Color.GREEN);
                                        DBC.Sincronizacao.saveSincronizacao(arquivo,"DOWNLOAD", "SINCRONIZACAO", "setOnClickListener()");
                                        atualizacao.setText("ÚLTIMA SINCRONIZAÇÃO: HOJE ÀS "+hora_atual);
                                        Toasty.success(ctx, "Sincronizada tabela de RETORNO.", Toast.LENGTH_LONG).show();
                                    }else{
                                        progress.setVisibility(View.GONE);
                                        Toasty.error(ctx, "Erro ao gravar na tabela RETORNO.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }, 2000);
                            break;
                        case "ESTOQUE":
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    String secao = "[ESTOQUE]";
                                    String arquivo = "ESTOQUE";
                                    Calendar c = Calendar.getInstance();
                                    SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
                                    String hora_atual = hora.format(c.getTime());
                                    if(gravar.gravarDados(ctx,secao,arquivo, "SINCRONIZACAO")){
                                        progress.setVisibility(View.GONE);
                                        situacao.setBackgroundColor(Color.GREEN);
                                        DBC.Sincronizacao.saveSincronizacao(arquivo,"DOWNLOAD", "SINCRONIZACAO", "setOnClickListener()");
                                        atualizacao.setText("ÚLTIMA SINCRONIZAÇÃO: HOJE ÀS "+hora_atual);
                                        Toasty.success(ctx, "Sincronizada tabela de ESTOQUE.", Toast.LENGTH_LONG).show();
                                    }else{
                                        progress.setVisibility(View.GONE);
                                        Toasty.error(ctx, "Erro ao gravar na tabela ESTOQUE.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }, 2000);
                            break;
                        default:
                            break;


                    }


                }
            });*/

            //view.animate();

        }
    }

    /*public sonicSincronizacaoUploadAdapter(List<go_sincronizar_holder> sinc, String[] nome, String[] desc, String[] last_sinc_data, String[] last_sinc_hora, Context ctx){
        this.sincs = sinc;
        this.ctx = ctx;
        this.nome = nome;
        this.desc = desc;
        this.last_sinc_data = last_sinc_data;
        this.last_sinc_hora = last_sinc_hora;
    }*/

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //View view = LayoutInflater.from(ctx).inflate(R.layout.activity_go_sincronizar_frag_upload, parent, false);
        //sincHolder sincs = new sincHolder(view);
        return null;//sincs;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        sincHolder holder = (sincHolder) viewHolder;
        //go_sincronizar_holder sinc = sincs.get(position);

        holder.nome.setText(nome[position]);
        holder.descricao.setText(desc[position]);
        //holder.tvNome.setText(sinc.getHora_sinc());
        //holder.updTxt.setText("ÚLTIMA SINCRONIZAÇÃO: "+last_sinc_data[position]);



        if(last_sinc_data[position].equals("")){
            holder.situacao.setBackgroundColor(Color.RED);
            holder.atualizacao.setText("ÚLTIMA SINCRONIZAÇÃO: "+last_sinc_hora[position]);
        }else if(last_sinc_data[position].equals(data_atual)){
            holder.situacao.setBackgroundColor(Color.GREEN);
            holder.atualizacao.setText("ÚLTIMA SINCRONIZAÇÃO: HOJE ÀS "+last_sinc_hora[position]);
        }else{
            holder.situacao.setBackgroundColor(Color.YELLOW);
            holder.atualizacao.setText("ÚLTIMA SINCRONIZAÇÃO: ONTEM ÀS "+last_sinc_hora[position]);
        }


    }

    @Override
    public int getItemCount() {
        return nome.length;
    }

    private static Map<Character, Character> MAP_NORM;
    public static String removeAccents(String value)
    {
        if (MAP_NORM == null || MAP_NORM.size() == 0)
        {
            MAP_NORM = new HashMap<Character, Character>();
            MAP_NORM.put('À', 'A');
            MAP_NORM.put('Á', 'A');
            MAP_NORM.put('Â', 'A');
            MAP_NORM.put('Ã', 'A');
            MAP_NORM.put('Ä', 'A');
            MAP_NORM.put('È', 'E');
            MAP_NORM.put('É', 'E');
            MAP_NORM.put('Ê', 'E');
            MAP_NORM.put('Ë', 'E');
            MAP_NORM.put('Í', 'I');
            MAP_NORM.put('Ì', 'I');
            MAP_NORM.put('Î', 'I');
            MAP_NORM.put('Ï', 'I');
            MAP_NORM.put('Ù', 'U');
            MAP_NORM.put('Ú', 'U');
            MAP_NORM.put('Û', 'U');
            MAP_NORM.put('Ü', 'U');
            MAP_NORM.put('Ò', 'O');
            MAP_NORM.put('Ó', 'O');
            MAP_NORM.put('Ô', 'O');
            MAP_NORM.put('Õ', 'O');
            MAP_NORM.put('Ö', 'O');
            MAP_NORM.put('Ñ', 'N');
            MAP_NORM.put('Ç', 'C');
            MAP_NORM.put('ª', 'A');
            MAP_NORM.put('º', 'O');
            MAP_NORM.put('§', 'S');
            MAP_NORM.put('³', '3');
            MAP_NORM.put('²', '2');
            MAP_NORM.put('¹', '1');
            MAP_NORM.put('à', 'a');
            MAP_NORM.put('á', 'a');
            MAP_NORM.put('â', 'a');
            MAP_NORM.put('ã', 'a');
            MAP_NORM.put('ä', 'a');
            MAP_NORM.put('è', 'e');
            MAP_NORM.put('é', 'e');
            MAP_NORM.put('ê', 'e');
            MAP_NORM.put('ë', 'e');
            MAP_NORM.put('í', 'i');
            MAP_NORM.put('ì', 'i');
            MAP_NORM.put('î', 'i');
            MAP_NORM.put('ï', 'i');
            MAP_NORM.put('ù', 'u');
            MAP_NORM.put('ú', 'u');
            MAP_NORM.put('û', 'u');
            MAP_NORM.put('ü', 'u');
            MAP_NORM.put('ò', 'o');
            MAP_NORM.put('ó', 'o');
            MAP_NORM.put('ô', 'o');
            MAP_NORM.put('õ', 'o');
            MAP_NORM.put('ö', 'o');
            MAP_NORM.put('ñ', 'n');
            MAP_NORM.put('ç', 'c');
        }

        if (value == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder(value);

        for(int i = 0; i < value.length(); i++) {
            Character c = MAP_NORM.get(sb.charAt(i));
            if(c != null) {
                sb.setCharAt(i, c.charValue());
            }
        }

        return sb.toString();

    }

}
