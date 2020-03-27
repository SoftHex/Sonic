package com.softhex.sonic;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

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

public class sonicMainVendas extends Fragment {

    private View myView;
    private ProgressBar pbChart;
    private sonicDatabaseCRUD mData;
    private List<sonicVendasValorHolder> mList;
    private BarChart mChart;
    private Context mContex;
    private Locale meuLocal = new Locale( "pt", "BR" );
    private NumberFormat nfVal = NumberFormat.getCurrencyInstance( meuLocal );
    private TextView tvMaxValue, tvMinValue;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.sonic_recycler_layout_chart, container, false);

        mContex = getContext();
        pbChart = myView.findViewById(R.id.pbChart);
        mData = new sonicDatabaseCRUD(getContext());
        mChart = myView.findViewById(R.id.mBarChart);
        tvMaxValue = myView.findViewById(R.id.tvMaxValue);
        tvMinValue = myView.findViewById(R.id.tvMinValue);
        mList = mData.Venda.selectVendas();
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                pbChart.setVisibility(View.GONE);
                mChart.setVisibility(View.VISIBLE);
                loadChart();
            }
        },1000);

        return myView;

    }

    private void loadChart(){

        Float maxValue = 0f;
        Float minValue = 99999999999f;
        String max = "";
        String min = "";
        String mes;
        int[] barColors = new int[mList.size()];
        List<Integer> textColors = new ArrayList<>();
        ArrayList<String> xAxisLabel = new ArrayList<>();
        ArrayList<BarEntry> valueSet = new ArrayList<>();
        ArrayList<IBarDataSet> dataSets = null;
        dataSets = new ArrayList<>();
        for(int i=0 ; i<mList.size() ; i++){
            switch (mList.get(i).getMes()){
                case "01":
                    mes = "Jan/";
                    xAxisLabel.add(mes+mList.get(i).getAno());
                    break;
                case "02":
                    mes = "Fev/";
                    xAxisLabel.add(mes+mList.get(i).getAno());
                    break;
                case "03":
                    mes = "Mar/";
                    xAxisLabel.add(mes+mList.get(i).getAno());
                    break;
                case "04":
                    mes = "Abr/";
                    xAxisLabel.add(mes+mList.get(i).getAno());
                    break;
                case "05":
                    mes = "Mai/";
                    xAxisLabel.add(mes+mList.get(i).getAno());
                    break;
                case "06":
                    mes = "Jun/";
                    xAxisLabel.add(mes+mList.get(i).getAno());
                    break;
                case "07":
                    mes = "Jul/";
                    xAxisLabel.add(mes+mList.get(i).getAno());
                    break;
                case "08":
                    mes = "Ago/";
                    xAxisLabel.add(mes+mList.get(i).getAno());
                    break;
                case "09":
                    mes = "Set/";
                    xAxisLabel.add(mes+mList.get(i).getAno());
                    break;
                case "10":
                    mes = "Out/";
                    xAxisLabel.add(mes+mList.get(i).getAno());
                    break;
                case "11":
                    mes = "Nov/";
                    xAxisLabel.add(mes+mList.get(i).getAno());
                    break;
                case "12":
                    mes = "Dez/";
                    xAxisLabel.add(mes+mList.get(i).getAno());
                    break;
                default:
                    mes = "--";
                    xAxisLabel.add("--");
            }
            if(i==5){
                barColors[i] = getResources().getColor(R.color.colorPrimaryRed);
                textColors.add(getResources().getColor(R.color.colorPrimaryRed));
            }else{
                barColors[i] = getResources().getColor(R.color.colorTypeInfo);
                textColors.add(getResources().getColor(R.color.colorTextWhite));
            }
            if(Float.valueOf(mList.get(i).getValor())> maxValue){
                maxValue = Float.valueOf(mList.get(i).getValor());
                nfVal.setMaximumFractionDigits(2);
                double v = (double)maxValue/100;
                max = nfVal.format(v).replace("R$", "R$ ")+" - "+mes+mList.get(i).getAno();// "R$ "+mList.get(i).getValor()+" - "+mes+mList.get(i).getAno();
            }
            if(Float.valueOf(mList.get(i).getValor())< minValue){
                minValue = Float.valueOf(mList.get(i).getValor());
                nfVal.setMaximumFractionDigits(2);
                double v = (double)minValue/100;
                min = nfVal.format(v).replace("R$", "R$ ")+" - "+mes+mList.get(i).getAno();// "R$ "+mList.get(i).getValor()+" - "+mes+mList.get(i).getAno();
            }
            valueSet.add(new BarEntry(i, Float.valueOf(mList.get(i).getValor())));
        }
        tvMaxValue.setText(max);
        tvMinValue.setText(min);
        BarDataSet barDataSet = new BarDataSet(valueSet, "");
        //barDataSet.setColor(getResources().getColor(R.color.colorTypeInfo));
        barDataSet.setColors(barColors);
        //barDataSet.setValueTextColor(Color.WHITE);
        barDataSet.setValueTextColors(textColors);
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
        mChart.setViewPortOffsets(0,0,0,56);
        YAxis yAxisRight = mChart.getAxisRight();
        yAxisRight.setEnabled(false);
        yAxisRight = mChart.getAxisLeft();
        yAxisRight.setEnabled(false);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setGranularity(0.5f);
        xAxis.setGranularityEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        LabelFormatter formatter = new LabelFormatter(xAxisLabel);
        xAxis.setValueFormatter(formatter);

    }

    public class MyXAxisValueFormatter implements IValueFormatter {
        private ArrayList <BarEntry> mValues;
        public MyXAxisValueFormatter(ArrayList<BarEntry> values)
        {
            this.mValues=values;
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
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
