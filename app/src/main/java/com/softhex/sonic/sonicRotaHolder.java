package com.softhex.sonic;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softhex.timelineview.TimelineView;

public class sonicRotaHolder extends RecyclerView.ViewHolder {

    private TimelineView myTimeLine;

    public sonicRotaHolder(@NonNull View itemView, int viewType) {
        super(itemView);
        myTimeLine = itemView.findViewById(R.id.timeLineView);
        myTimeLine.initLine(viewType);
    }



}
