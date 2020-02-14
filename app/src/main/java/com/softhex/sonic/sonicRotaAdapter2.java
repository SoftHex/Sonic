package com.softhex.sonic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IntDef;
import androidx.annotation.StringDef;
import androidx.recyclerview.widget.RecyclerView;

import com.softhex.timelineview.TimelineView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class sonicRotaAdapter2 extends RecyclerView.Adapter<sonicRotaAdapter2.ViewHolder> implements Filterable {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({Tipo.PADRAO, Tipo.AGENDAMENTO, Tipo.REAGENDAMENTO})
    public @interface Tipo {
        int PADRAO = 1;
        int AGENDAMENTO = 2;
        int REAGENDAMENTO = 3;
    }
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({Status.NAO_INICIADO, Status.EM_ANDAMENTO, Status.CONCLUIDA, Status.CANCELADA})
    public @interface Status{
        int NAO_INICIADO = 1;
        int EM_ANDAMENTO = 2;
        int CONCLUIDA = 3;
        int CANCELADA = 4;
    }
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({StatusText.NAO_INICIADO, StatusText.EM_ANDAMENTO, StatusText.CONCLUIDA, StatusText.CANCELADA})
    public @interface StatusText{
        String NAO_INICIADO = "Não Iniciado";
        String EM_ANDAMENTO = "Em Andamento";
        String CONCLUIDA = "Concluído";
        String CANCELADA = "Cancelado";
    }

    private Context myCtx;
    private List<sonicRotaHolder> rotas;
    private List<sonicRotaHolder> filteredRotas;
    private RotaFilter rotaFilter;
    private sonicDatabaseCRUD DBC;
    private sonicConstants myCons;
    private GradientDrawable shape;
    private SharedPreferences myPrefs;
    private Activity mActivity;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TimelineView timelineView;
        TextView tvNome;
        TextView tvEndereco;
        TextView tvStatus;
        TextView tvOptions;
        TextView tvObservacao;
        TextView tvAtendente;
        TextView tvDataHora;
        TextView tvSituacao;
        LinearLayout linearItem;

        ViewHolder(View view) {
            super(view);
            linearItem = view.findViewById(R.id.linearItem);
            timelineView = view.findViewById(R.id.tlRota);
            tvOptions = view.findViewById(R.id.tvOptions);
            tvNome = view.findViewById(R.id.tvNome);
            tvAtendente = view.findViewById(R.id.tvAtendente);
            tvStatus = view.findViewById(R.id.tvStatus);
            tvSituacao = view.findViewById(R.id.tvSituacao);
            tvEndereco = view.findViewById(R.id.tvEndereco);
            tvDataHora = view.findViewById(R.id.tvDataHora);
            tvObservacao = view.findViewById(R.id.tvObservacao);

        }
    }

    sonicRotaAdapter2(Activity activity, List<sonicRotaHolder> rotas) {
        this.myCtx = activity.getApplicationContext();
        this.rotas = rotas;
        this.filteredRotas = rotas;
        this.mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cards_rota, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        sonicRotaHolder rota = rotas.get(position);
        holder.setIsRecyclable(false);
        sonicUtils utils = new sonicUtils(myCtx);

        holder.tvNome.setText(rota.getRazaoSocial());
        holder.tvAtendente.setText("Responsável: "+rota.getAtendente());
        holder.tvEndereco.setText(rota.getEnderecoCompleto());
        holder.tvDataHora.setText("Data Prevista: "+utils.Data.dataFotmatadaBarra(rota.getDataAgendamento())+" Ás "+utils.Data.horaFotmatadaBR(rota.getHoraAgendamento()));
        holder.tvObservacao.setText(rota.getObservacao());
        holder.timelineView.setMarker(
                rota.getTipo()==Tipo.PADRAO ?
                        myCtx.getResources().getDrawable(R.mipmap.ic_map_grey600_36dp) :
                        rota.getTipo()==Tipo.AGENDAMENTO ?
                                myCtx.getResources().getDrawable(R.mipmap.ic_phone_grey600_36dp) : myCtx.getResources().getDrawable(R.mipmap.ic_phone_return_grey600_36dp));

        switch (rota.getStatus()){
            case Status.NAO_INICIADO:
                holder.tvStatus.setBackground(myCtx.getResources().getDrawable(R.drawable.status_nao_iniciada));
                holder.tvStatus.setText(StatusText.NAO_INICIADO);
                holder.tvNome.setTextColor(myCtx.getResources().getColor(R.color.colorPrimaryBlue));
                holder.tvEndereco.setTextColor(myCtx.getResources().getColor(R.color.colorPrimaryBlue));
                holder.timelineView.setEndLineColor(myCtx.getResources().getColor(R.color.colorPrimaryBlue), holder.getItemViewType());
                holder.timelineView.setMarkerColor(myCtx.getResources().getColor(R.color.colorPrimaryBlue));
                break;
            case Status.EM_ANDAMENTO:
                holder.tvStatus.setBackground(myCtx.getResources().getDrawable(R.drawable.status_iniciada));
                holder.tvStatus.setText(StatusText.EM_ANDAMENTO);
                holder.tvNome.setTextColor(myCtx.getResources().getColor(R.color.colorPrimaryOrange));
                holder.tvEndereco.setTextColor(myCtx.getResources().getColor(R.color.colorPrimaryOrange));
                holder.timelineView.setEndLineColor(myCtx.getResources().getColor(R.color.colorPrimaryOrange), holder.getItemViewType());
                holder.timelineView.setMarkerColor(myCtx.getResources().getColor(R.color.colorPrimaryOrange));
                break;
            case Status.CONCLUIDA:
                holder.tvStatus.setBackground(myCtx.getResources().getDrawable(R.drawable.status_concluida));
                holder.tvStatus.setText(StatusText.CONCLUIDA);
                holder.tvNome.setTextColor(myCtx.getResources().getColor(R.color.colorPrimaryGreen));
                holder.tvEndereco.setTextColor(myCtx.getResources().getColor(R.color.colorPrimaryGreen));
                holder.timelineView.setEndLineColor(myCtx.getResources().getColor(R.color.colorPrimaryGreen), holder.getItemViewType());
                holder.timelineView.setMarkerColor(myCtx.getResources().getColor(R.color.colorPrimaryGreen));
                holder.tvOptions.setVisibility(View.GONE);
                holder.tvSituacao.setVisibility(View.VISIBLE);
                break;
            case Status.CANCELADA:
                holder.tvStatus.setBackground(myCtx.getResources().getDrawable(R.drawable.status_cancelada));
                holder.tvStatus.setText(StatusText.CANCELADA);
                holder.tvNome.setTextColor(myCtx.getResources().getColor(R.color.colorPrimaryRed));
                holder.tvEndereco.setTextColor(myCtx.getResources().getColor(R.color.colorPrimaryRed));
                holder.timelineView.setEndLineColor(myCtx.getResources().getColor(R.color.colorPrimaryRed), holder.getItemViewType());
                holder.timelineView.setMarkerColor(myCtx.getResources().getColor(R.color.colorPrimaryRed));
                holder.tvOptions.setVisibility(View.GONE);
                break;
        }
        holder.linearItem.setOnClickListener((View v)->{

            dialogRota(v, rota);

            });
        holder.tvOptions.setOnClickListener((View v) -> {

            PopupWindow popup = popupDisplay(v, rota.getCodigo(), rota.getStatus());
            popup.showAsDropDown(v, 0, -26);

        });

    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.layout_cards_rota;

    }

    @Override
    public int getItemCount() {
        return rotas.size();
    }

    @Override
    public Filter getFilter() {
        if(rotaFilter == null)
            rotaFilter = new RotaFilter(this, rotas);
        return rotaFilter;
    }

    private static class RotaFilter extends Filter {

        private final sonicRotaAdapter2 adapter;

        private final List<sonicRotaHolder> originalList;

        private final List<sonicRotaHolder> filteredList;

        private RotaFilter(sonicRotaAdapter2 adapter, List<sonicRotaHolder> originalList) {
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

                for (final sonicRotaHolder rota : originalList) {
                    if (String.valueOf(rota.getCodigoCliente()).contains(filterPattern) || rota.getRazaoSocial().contains(filterPattern) || rota.getNomeFantasia().contains(filterPattern)|| rota.getAtendente().contains(filterPattern)) {
                        filteredList.add(rota);

                    }

                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.rotas.clear();
            adapter.filteredRotas.addAll((ArrayList<sonicRotaHolder>) results.values);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     *
     * @param v View
     * @param cod Código da rota
     * @param status Status atual da rota
     * @return PopupWindow
     */

    public PopupWindow popupDisplay(View v, int cod, int status)
    {

        final PopupWindow popupWindow = new PopupWindow(v);

        // inflate your layout or dynamically add view
        LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.menu_rota_options, null);

        TextView tvIniciar = view.findViewById(R.id.tvIniciar);
        TextView tvCancelar = view.findViewById(R.id.tvCancelar);
        TextView tvFinalizar = view.findViewById(R.id.tvFinalizar);

        switch (status){
            case Status.EM_ANDAMENTO:
                tvIniciar.setVisibility(View.GONE);
                tvCancelar.setVisibility(View.GONE);
                tvFinalizar.setVisibility(View.VISIBLE);
        }

        tvIniciar.setOnClickListener((View x) -> {

            Toast.makeText(x.getContext(), "Iniciar", Toast.LENGTH_SHORT).show();

        });

        tvCancelar.setOnClickListener((View x) -> {

            Toast.makeText(x.getContext(), "Cancelar", Toast.LENGTH_SHORT).show();

        });

        tvFinalizar.setOnClickListener((View x) -> {

            Toast.makeText(x.getContext(), "Finalizar", Toast.LENGTH_SHORT).show();

        });

        popupWindow.setFocusable(true);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);

        return popupWindow;
    }

    public void dialogRota(View v, sonicRotaHolder holder){

            v.setOnClickListener((View x)-> {

                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mActivity);
                    Rect displayRectangle = new Rect();
                    Window window = mActivity.getWindow();
                    window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                    ViewGroup viewGroup = x.findViewById(android.R.id.content);
                    View view = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_rota, viewGroup, false);
                    view.setMinimumWidth(displayRectangle.width());
                    view.setMinimumHeight(displayRectangle.height());
                    dialogBuilder.setView(view);
                    AlertDialog dialog = dialogBuilder.create();
                    TextView tvHeader = view.findViewById(R.id.tvHeader);
                    TextView tvEndereco = view.findViewById(R.id.tvEndereco);
                    TextView tvClose = view.findViewById(R.id.tvClose);
                    tvClose.setOnClickListener((View Vview)->{
                        dialog.dismiss();
                    });
                    ProgressBar pbProgress = view.findViewById(R.id.pbProgress);

                        Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {

                                                  pbProgress.setVisibility(View.GONE);
                                                  tvHeader.setText(holder.getRazaoSocial());
                                                  tvEndereco.setText(holder.getEnderecoCompleto());
                                                }
                                            }
                                , 500);


                    dialog.show();


            });

    }

}
