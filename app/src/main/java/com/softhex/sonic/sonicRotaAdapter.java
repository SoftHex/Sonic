package com.softhex.sonic;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softhex.timelineview.TimelineView;

public class sonicRotaAdapter extends RecyclerView.Adapter<sonicRotaHolder> {
    @NonNull
    @Override
    public sonicRotaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.layout_main_vendedor, null);
        return new sonicRotaHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull sonicRotaHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

}
