package com.softhex.sonic;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class sonicMainHome1 extends Fragment {

    private View myView;
    private ProgressBar pbChart;
    private sonicDatabaseCRUD mData;
    private List<sonicVendasHolder> mList;
    private BarChart mChart;
    private Context mContex;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.sonic_recycler_layout_chart, container, false);

        mContex = getContext();
        pbChart = myView.findViewById(R.id.pbChart);
        mData = new sonicDatabaseCRUD(getContext());
        mChart = myView.findViewById(R.id.mChart);

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                //pbChart.setVisibility(View.GONE);
            }
        },2000);


        mList = mData.Venda.selectVendas();
        Long a = Long.parseLong("999999999");
        ArrayList<String> xAxisLabel = new ArrayList<>();
        ArrayList<BarEntry> valueSet = new ArrayList<>();
        ArrayList<IBarDataSet> dataSets = null;
        dataSets = new ArrayList<>();
        for(int i=0 ; i<mList.size() ; i++){
            switch (mList.get(i).getMes()){
                case "01":
                    xAxisLabel.add("Jan/"+mList.get(i).getAno());
                    break;
                case "02":
                    xAxisLabel.add("Fev/"+mList.get(i).getAno());
                    break;
                case "03":
                    xAxisLabel.add("Mar/"+mList.get(i).getAno());
                    break;
                case "04":
                    xAxisLabel.add("Abr/"+mList.get(i).getAno());
                    break;
                case "05":
                    xAxisLabel.add("Mai/"+mList.get(i).getAno());
                    break;
                case "06":
                    xAxisLabel.add("Jun/"+mList.get(i).getAno());
                    break;
                case "07":
                    xAxisLabel.add("Jul/"+mList.get(i).getAno());
                    break;
                case "08":
                    xAxisLabel.add("Ago/"+mList.get(i).getAno());
                    break;
                case "09":
                    xAxisLabel.add("Sep/"+mList.get(i).getAno());
                    break;
                case "10":
                    xAxisLabel.add("Out/"+mList.get(i).getAno());
                    break;
                case "11":
                    xAxisLabel.add("Nov/"+mList.get(i).getAno());
                    break;
                case "12":
                    xAxisLabel.add("Dez/"+mList.get(i).getAno());
                    break;
                    default:
                        xAxisLabel.add("--");
            }
            valueSet.add(new BarEntry(i, Float.valueOf(mList.get(i).getValor())));
        }
        BarDataSet barDataSet = new BarDataSet(valueSet, "");
        barDataSet.setColor(getResources().getColor(R.color.colorTypeInfo));
        barDataSet.setValueTextColor(Color.WHITE);
        barDataSet.setValueTextSize(10f);

        barDataSet.setValueFormatter(new MyXAxisValueFormatter(valueSet));
        dataSets.add(barDataSet);

        BarData data = new BarData(dataSets);
        data.setBarWidth(0.7f);
        mChart.setData(data);
        mChart.animateXY(1000, 1000);
        mChart.invalidate();
        Description d = new Description();
        d.setText("");
        mChart.setDescription(d);
        mChart.getAxisLeft().setDrawAxisLine(false);
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getAxisRight().setDrawAxisLine(false);
        mChart.getAxisRight().setDrawGridLines(false);
        //mChart.getXAxis().setDrawLabels(false);
        mChart.getXAxis().setDrawGridLines(false);
        mChart.getXAxis().setEnabled(true);
        mChart.getXAxis().setTextColor(Color.WHITE);
        mChart.getAxisLeft().setTextColor(Color.WHITE);
        mChart.getAxisRight().setTextColor(Color.WHITE);
        mChart.getLegend().setTextColor(Color.WHITE);
        mChart.getLegend().setEnabled(false);
        mChart.setViewPortOffsets(6,0,6,56);
        YAxis yAxisRight = mChart.getAxisRight();
        yAxisRight.setEnabled(false);
        yAxisRight = mChart.getAxisLeft();
        yAxisRight.setEnabled(false);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        LabelFormatter formatter = new LabelFormatter(xAxisLabel);
        xAxis.setValueFormatter(formatter);

        return myView;

    }

    public class MyXAxisValueFormatter implements IValueFormatter {
        private ArrayList <BarEntry> mValues;
        public MyXAxisValueFormatter(ArrayList<BarEntry> values)
        {
            this.mValues=values;
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            Locale meuLocal = new Locale( "pt", "BR" );
            NumberFormat nfVal = NumberFormat.getCurrencyInstance( meuLocal );
            nfVal.setMaximumFractionDigits(2);
            double v = (double)value/100;
            return nfVal.format(v).replace("R$", "");
        }
    }

    public class LabelFormatter implements IAxisValueFormatter {
        private String[] mLabels;

        public LabelFormatter(ArrayList<String> labels) {
            mLabels = labels.toArray(new String[labels.size()]);
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mLabels[(int) value];
        }
    }

}
