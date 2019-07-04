package com.softhex.sonic;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrador on 11/08/2017.
 */

public class sonicSincronizacaoDownloadAdapter extends RecyclerView.Adapter{

    private Context ctx;
    //private go_ftp_baixar down;
    //private go_first_access gravar;
    //private go_read_write_all gravarAll;
   // private go_database_common_crud DBC;
    private sonicUtils util;
    //private List<go_sincronizar_holder> sincs;
    private String[] titulo, descricao, last_sinc_data, last_sinc_hora;
    private Calendar c = Calendar.getInstance();
    private SimpleDateFormat data = new SimpleDateFormat("yyyy/MM/dd");
    private SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
    private String data_atual = data.format(c.getTime());

    public class sincHolder extends RecyclerView.ViewHolder {


        TextView titulo;
        TextView descricao;
        TextView ultima_sincronizacao;
        //ActionProcessButton button;

        public sincHolder(View view) {
            super(view);
            //titulo = (TextView)view.findViewById(R.id.titulo);
            //descricao = (TextView)view.findViewById(R.id.descricao);
            //ultima_sincronizacao = (TextView)view.findViewById(R.id.ultima_sincronizacao);
            //button = (ActionProcessButton)view.findViewById(R.id.button);

            util = new sonicUtils(ctx);

            /*button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(util.Feedback.statusNetwork(ctx)){

                    int position = getPosition();
                    final Context ctx = view.getContext();
                    down = new go_ftp_baixar();
                    gravar = new go_first_access();
                    gravarAll = new go_read_write_all();
                    DBC = new go_database_common_crud(view.getContext());
                    String arquivo = removeAccents(titulo.getText().toString());
                    button.setMode(ActionProcessButton.Mode.ENDLESS);
                    button.setProgress(50);
                    String secao = "";

                    switch (arquivo){
                        case "RECEBER DADOS":
                            secao = "GERAL";
                            down.execute(secao, String.valueOf(DBC.vendedor.vendedorAtivoId()));
                            break;
                        case "LOCALIZACAO":
                            secao = "LOCALIZACAO";
                            down.execute(secao, String.valueOf(DBC.vendedor.vendedorAtivoId()));
                            break;
                        case "CATALOGO":
                            secao = "CATALOGO";
                            down.catalogo.execute();
                            break;

                    }

                    Handler handler = new Handler();

                    switch (secao){
                        case "GERAL":
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    String arquivo = "GERAL";
                                    if(gravarAll.gravarDados(ctx, arquivo, "SINCRONIZACAO")){
                                        DBC.sincronizacao.saveSincronizacao(arquivo,"DOWNLOAD", "SINCRONIZACAO", "setOnClickListener()");
                                        button.setProgress(100);
                                        button.setClickable(false);
                                        util.Arquivo.moveFile(arquivo);
                                        Calendar c = Calendar.getInstance();
                                        SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
                                        String hora_atual = hora.format(c.getTime());
                                        ultima_sincronizacao.setText("Sincronizado hoje às "+hora_atual);
                                        ultima_sincronizacao.setTextColor(Color.parseColor("#25D366"));
                                    }else{
                                        button.setProgress(-1);
                                        button.setText("SINCRONIZAR");
                                        Toasty.warning(ctx, "Tente novamente em alguns minutos...", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }, 2000);
                            break;
                            case "CATALOGO":
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        String arquivo = "CATALOGO";
                                        if(util.Arquivo.unzipFile(arquivo)){
                                            DBC.sincronizacao.saveSincronizacao(arquivo,"DOWNLOAD", "SINCRONIZACAO", "setOnClickListener()");
                                            button.setProgress(100);
                                            button.setClickable(false);
                                            Calendar c = Calendar.getInstance();
                                            SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
                                            String hora_atual = hora.format(c.getTime());
                                            ultima_sincronizacao.setText("Sincronizado hoje às "+hora_atual);
                                            ultima_sincronizacao.setTextColor(Color.parseColor("#25D366"));
                                        }else{
                                            button.setProgress(-1);
                                            button.setText("SINCRONIZAR");
                                            Toasty.warning(ctx, "Tente novamente em alguns minutos...", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }, 2000);
                                break;
                        case "LOCALIZACAO":
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    String arquivo = "LOCALIZACAO";
                                    if(gravarAll.gravarDados(ctx, arquivo, "SINCRONIZACAO")){
                                        DBC.sincronizacao.saveSincronizacao(arquivo,"DOWNLOAD", "SINCRONIZACAO", "setOnClickListener()");
                                        button.setProgress(100);
                                        button.setClickable(false);
                                        util.Arquivo.moveFile(arquivo);
                                        Calendar c = Calendar.getInstance();
                                        SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
                                        String hora_atual = hora.format(c.getTime());
                                        ultima_sincronizacao.setText("Sincronizado hoje às "+hora_atual);
                                        ultima_sincronizacao.setTextColor(Color.parseColor("#25D366"));
                                    }else{
                                        button.setProgress(-1);
                                        button.setText("SINCRONIZAR");
                                        Toasty.warning(ctx, "Tente novamente em alguns minutos...", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }, 2000);
                        default:
                            break;


                    }


                    }else{
                        Toasty.warning(ctx, "Verifique sua conexão com a internet.", Toast.LENGTH_LONG).show();
                    }

                }
            });*/

            //view.animate();

        }
    }

    public sonicSincronizacaoDownloadAdapter(String[] titulo, String[] descricao, String[] last_sinc_data, String[] last_sinc_hora, Context ctx){
        //this.sincs = sinc;
        this.ctx = ctx;
        this.titulo = titulo;
        this.descricao = descricao;
        this.last_sinc_data = last_sinc_data;
        this.last_sinc_hora = last_sinc_hora;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //View view = LayoutInflater.from(ctx).inflate(R.layout.layout_cards_sincronizacao_download, parent, false);
        //sincHolder sincs = new sincHolder(view);
        return null;//sincs;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        sincHolder holder = (sincHolder) viewHolder;

        int dias = util.Data.dateDiffDay(last_sinc_data[position], data_atual);

        holder.titulo.setText(titulo[position]);
        holder.descricao.setText(descricao[position]);
        //holder.button.setText("SINCRONIZAR");

        switch (dias){
            case 0:
                holder.ultima_sincronizacao.setText("Sincronizado hoje às "+last_sinc_hora[position]);
                holder.ultima_sincronizacao.setTextColor(Color.parseColor("#25D366"));
                break;
            case 1:
                holder.ultima_sincronizacao.setText("Sincronizado ontem às "+last_sinc_hora[position]);
                holder.ultima_sincronizacao.setTextColor(Color.parseColor("#FFD700"));
                break;
            case 2:
                holder.ultima_sincronizacao.setText("Sincronizado em "+last_sinc_data[position]+" às "+last_sinc_hora[position]);
                holder.ultima_sincronizacao.setTextColor(Color.parseColor("#FF0000"));
                break;
            default:
                holder.ultima_sincronizacao.setText("Ainda não houve sincronização.");
                break;
        }

    }

    @Override
    public int getItemCount() {
        return titulo.length;
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
