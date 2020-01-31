package com.softhex.sonic;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class sonicMainHome1 extends Fragment {

    private View myView;
    private ProgressBar pbChart;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.sonic_recycler_layout_chart, container, false);

        pbChart = myView.findViewById(R.id.pbChart);

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                pbChart.setVisibility(View.GONE);
            }
        },2000);

        return myView;

    }
}
