package com.softhex.sonic;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.softhex.timelineview.LineType;
import com.softhex.timelineview.TimelineView;

import java.io.File;
import java.util.List;

public class sonicRotaAdapter2 extends RecyclerView.Adapter<sonicRotaAdapter2.ViewHolder> {

    private Context myCtx;
    private List<sonicRotaHolder> rotas;
    private List<sonicRotaHolder> filteredRotas;
    //private sonicRotaAdapter.RotaFilter rotaFilter;
    private sonicDatabaseCRUD DBC;
    private sonicConstants myCons;
    private GradientDrawable shape;
    private SharedPreferences myPrefs;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TimelineView timelineView;
        TextView tvName;
        TextView tvAddress;

        ViewHolder(View view) {
            super(view);
            timelineView = (TimelineView) view.findViewById(R.id.tlRota);
            //tvName = (TextView) view.findViewById(R.id.tv_name);
            //tvAddress = (TextView) view.findViewById(R.id.tv_address);
        }
    }

    sonicRotaAdapter2(Context myCtx, List<sonicRotaHolder> rotas) {
        this.myCtx = myCtx;
        this.rotas = rotas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        sonicRotaHolder rota = rotas.get(position);

        //holder.tvName.setText(items.get(position).getName());
        //holder.tvAddress.setText(items.get(position).getAddress());
        holder.timelineView.setLineType(getLineType(position));
        holder.timelineView.setNumber(position);

        // Make first and last markers stroked, others filled
        if (position == 0 || position + 1 == getItemCount()) {
            holder.timelineView.setFillMarker(false);
        } else {
            holder.timelineView.setFillMarker(true);
        }

        if (position == 4) {
            //holder.timelineView.setDrawable(AppCompatResources
            //.getDrawable(holder.timelineView.getContext(),
            //R.mipmap.ic_check_all_white_24dp));

        } else {
            holder.timelineView.setDrawable(null);
        }

        // Set every third item active
        holder.timelineView.setActive(position % 3 == 2);

        String file = Environment.getExternalStorageDirectory()+myCons.LOCAL_IMG_CLIENTES + rota.getCodigoCliente()+"_1.jpg";
        File f = new File(file);

        if(f.exists()) {

            //holder.timelineView.setDrawable(AppCompatResources.getDrawable(holder.timelineView.getContext(), myCtx.getResources().getDrawable(sonicUtils.stringToDrawable(f))));
        }

    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.layout_cards_rota;

    }

    @Override
    public int getItemCount() {
        return rotas.size();
    }

    private LineType getLineType(int position) {
        if (getItemCount() == 1) {
            return LineType.ONLYONE;

        } else {
            if (position == 0) {
                return LineType.BEGIN;

            } else if (position == getItemCount() - 1) {
                return LineType.END;

            } else {
                return LineType.NORMAL;
            }
        }
    }

}
