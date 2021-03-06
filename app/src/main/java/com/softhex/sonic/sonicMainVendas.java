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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
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
    private BarChart mBarChart;
    private LineChart mLineChart;;
    private Context mContex;
    private Locale meuLocal = new Locale( "pt", "BR" );
    private NumberFormat nfVal = NumberFormat.getCurrencyInstance( meuLocal );
    private TextView tvMaxValue, tvTitulo, tvMensagem;
    private sonicPreferences mPrefs;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.sonic_recycler_layout_chart, container, false);

        mContex = getContext();
        mPrefs = new sonicPreferences(mContex);
        pbChart = myView.findViewById(R.id.pbChart);
        mData = new sonicDatabaseCRUD(getContext());
        mBarChart = myView.findViewById(R.id.mBarChart);
        mLineChart = myView.findViewById(R.id.mLineChart);
        tvMaxValue = myView.findViewById(R.id.tvMaxValue);
        tvTitulo = myView.findViewById(R.id.tvTitulo);
        tvMensagem = myView.findViewById(R.id.tvMensagem);
        tvMensagem.setVisibility(mPrefs.Geral.getFirstSinc() ? View.VISIBLE : View.INVISIBLE);
        mList = mData.Venda.selectVendas();
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                pbChart.setVisibility(View.GONE);
                tvTitulo.setText("");
                switch (mPrefs.Geral.getHomeChartType()){
                    case "Linhas":
                        mLineChart.setVisibility(View.VISIBLE);
                        loadLineChart();
                        break;
                    case "Barras":
                        mBarChart.setVisibility(View.VISIBLE);
                        loadBarChart();
                        break;
                }
            }
        },1000);

        return myView;

    }

    private void loadBarChart(){

        Float maxValue = 0f;
        String max = "";
        String mes;
        String ano;
        ArrayList<String> xAxisLabel = new ArrayList<>();
        ArrayList<BarEntry> valueSet = new ArrayList<>();
        ArrayList<IBarDataSet> dataSets = null;
        dataSets = new ArrayList<>();
        for(int i=0 ; i<mList.size() ; i++){
            ano = mList.get(i).getAno().length()<4 ? mList.get(i).getAno() : mList.get(i).getAno().substring(2);
            switch (mList.get(i).getMes()){
                case "01":
                    mes = "Jan/";
                    xAxisLabel.add(mes+ano);
                    break;
                case "02":
                    mes = "Fev/";
                    xAxisLabel.add(mes+ano);
                    break;
                case "03":
                    mes = "Mar/";
                    xAxisLabel.add(mes+ano);
                    break;
                case "04":
                    mes = "Abr/";
                    xAxisLabel.add(mes+ano);
                    break;
                case "05":
                    mes = "Mai/";
                    xAxisLabel.add(mes+ano);
                    break;
                case "06":
                    mes = "Jun/";
                    xAxisLabel.add(mes+ano);
                    break;
                case "07":
                    mes = "Jul/";
                    xAxisLabel.add(mes+ano);
                    break;
                case "08":
                    mes = "Ago/";
                    xAxisLabel.add(mes+ano);
                    break;
                case "09":
                    mes = "Set/";
                    xAxisLabel.add(mes+ano);
                    break;
                case "10":
                    mes = "Out/";
                    xAxisLabel.add(mes+ano);
                    break;
                case "11":
                    mes = "Nov/";
                    xAxisLabel.add(mes+ano);
                    break;
                case "12":
                    mes = "Dez/";
                    xAxisLabel.add(mes+ano);
                    break;
                default:
                    mes = "--";
                    xAxisLabel.add("--");
                    break;
            }
            if(Float.valueOf(mList.get(i).getValor())> maxValue){
                maxValue = Float.valueOf(mList.get(i).getValor());
                nfVal.setMaximumFractionDigits(2);
                double v = (double)maxValue/100;
                max = nfVal.format(v).replace("R$", "R$ ")+" - "+mes+mList.get(i).getAno();// "R$ "+mList.get(i).getValor()+" - "+mes+mList.get(i).getAno();
            }
            valueSet.add(new BarEntry(i, Float.valueOf(mList.get(i).getValor())));
        }
        tvMaxValue.setText(maxValue==0f ? "R$ 0,00" : max);
        BarDataSet barDataSet = new BarDataSet(valueSet, "");
        barDataSet.setColor(getResources().getColor(R.color.colorTypeInfo));
        barDataSet.setValueTextColor(Color.WHITE);
        barDataSet.setValueTextSize(10f);

        barDataSet.setValueFormatter(new mXAxisBarValueFormatter(valueSet));
        dataSets.add(barDataSet);

        BarData data = new BarData(dataSets);
        data.setBarWidth(0.7f);
        mBarChart.setData(data);
        mBarChart.animateXY(1000, 1000);
        mBarChart.invalidate();
        Description d = new Description();
        d.setText("");
        mBarChart.setDescription(d);
        mBarChart.getAxisLeft().setDrawAxisLine(false);
        mBarChart.getAxisLeft().setDrawGridLines(false);
        mBarChart.getAxisRight().setDrawAxisLine(false);
        mBarChart.getAxisRight().setDrawGridLines(false);
        mBarChart.setDoubleTapToZoomEnabled(false);
        mBarChart.setPinchZoom(false);
        mBarChart.setScaleEnabled(false);
        mBarChart.getXAxis().setDrawGridLines(false);
        mBarChart.getXAxis().setEnabled(true);
        mBarChart.getXAxis().setTextColor(Color.WHITE);
        mBarChart.getAxisLeft().setTextColor(Color.WHITE);
        mBarChart.getAxisRight().setTextColor(Color.WHITE);
        mBarChart.getLegend().setTextColor(Color.WHITE);
        mBarChart.getLegend().setEnabled(false);
        mBarChart.setViewPortOffsets(0,0,0,56);
        YAxis yAxisRight = mBarChart.getAxisRight();
        yAxisRight.setEnabled(false);
        yAxisRight = mBarChart.getAxisLeft();
        yAxisRight.setEnabled(false);
        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setGranularity(0.5f);
        xAxis.setGranularityEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        LabelFormatter formatter = new LabelFormatter(xAxisLabel);
        xAxis.setValueFormatter(formatter);

    }

    private void loadLineChart(){

        Float maxValue = 0f;
        String max= "";
        String ano;
        String mes;
        ArrayList<String> xAxisLabel = new ArrayList<>();
        ArrayList<Entry> valueSet = new ArrayList<>();
        ArrayList<ILineDataSet> dataSets = null;
        dataSets = new ArrayList<>();
        // TO OFFSET FIRST AND LAST VALUES
        valueSet.add(new Entry(0, 0));
        xAxisLabel.add("");
        for(int i=1 ; i<=mList.size() ; i++){
            ano = mList.get(i-1).getAno().length()<4 ? mList.get(i-1).getAno() : mList.get(i-1).getAno().substring(2);
            switch (mList.get(i-1).getMes()){
                case "01":
                    mes = "Jan/";
                    xAxisLabel.add(mes+ano);
                    break;
                case "02":
                    mes = "Fev/";
                    xAxisLabel.add(mes+ano);
                    break;
                case "03":
                    mes = "Mar/";
                    xAxisLabel.add(mes+ano);
                    break;
                case "04":
                    mes = "Abr/";
                    xAxisLabel.add(mes+ano);
                    break;
                case "05":
                    mes = "Mai/";
                    xAxisLabel.add(mes+ano);
                    break;
                case "06":
                    mes = "Jun/";
                    xAxisLabel.add(mes+ano);
                    break;
                case "07":
                    mes = "Jul/";
                    xAxisLabel.add(mes+ano);
                    break;
                case "08":
                    mes = "Ago/";
                    xAxisLabel.add(mes+ano);
                    break;
                case "09":
                    mes = "Set/";
                    xAxisLabel.add(mes+ano);
                    break;
                case "10":
                    mes = "Out/";
                    xAxisLabel.add(mes+ano);
                    break;
                case "11":
                    mes = "Nov/";
                    xAxisLabel.add(mes+ano);
                    break;
                case "12":
                    mes = "Dez/";
                    xAxisLabel.add(mes+ano);
                    break;
                default:
                    mes = "--";
                    xAxisLabel.add("--");
                    break;
            }
            if(Float.valueOf(mList.get(i-1).getValor())> maxValue){
                maxValue = Float.valueOf(mList.get(i-1).getValor());
                nfVal.setMaximumFractionDigits(2);
                double v = (double)maxValue/100;
                max = nfVal.format(v).replace("R$", "R$ ")+" - "+mes+mList.get(i-1).getAno();
            }
            valueSet.add(new Entry(i, Float.valueOf(mList.get(i-1).getValor())));
        }
        tvMaxValue.setText(maxValue==0f ? "R$ 0,00" : max);
        // TO OFFSET FIRST AND LAST VALUES
        valueSet.add(new Entry(7, 0f));
        xAxisLabel.add("");

        LineDataSet lineDataSet = new LineDataSet(valueSet, "");
        lineDataSet.setValues(valueSet);
        lineDataSet.setValueTextColor(Color.WHITE);
        lineDataSet.setValueTextSize(12f);
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleRadius(8f);
        lineDataSet.setCircleHoleRadius(4f);
        lineDataSet.setFillDrawable(getResources().getDrawable(R.drawable.chart_color));
        lineDataSet.setFillAlpha(100);
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        // HIGHLIGHT - WHEN LINE IS SELECTED
        lineDataSet.setHighlightEnabled(false);
        lineDataSet.setValueFormatter(new sonicMainVendas.mXAxisLineValueFormatter(valueSet));
        dataSets.add(lineDataSet);

        LineData data = new LineData(dataSets);
        mLineChart.setData(data);
        mLineChart.animateXY(1000, 1000);

        Description d = new Description();
        d.setText("");
        mLineChart.setDescription(d);
        mLineChart.getAxisLeft().setDrawAxisLine(false);
        mLineChart.getAxisLeft().setDrawGridLines(false);
        mLineChart.getAxisRight().setDrawAxisLine(false);
        mLineChart.getAxisRight().setDrawGridLines(false);
        mLineChart.setDoubleTapToZoomEnabled(false);
        mLineChart.setPinchZoom(false);
        mLineChart.setScaleEnabled(false);
        mLineChart.getXAxis().setTextColor(Color.WHITE);
        mLineChart.getAxisLeft().setTextColor(Color.WHITE);
        mLineChart.getAxisRight().setTextColor(Color.WHITE);
        mLineChart.getLegend().setTextColor(Color.WHITE);
        mLineChart.getLegend().setEnabled(false);
        // YAXIX - VERTICAL
        YAxis yAxisRight = mLineChart.getAxisRight();
        yAxisRight.setEnabled(false);
        yAxisRight = mLineChart.getAxisLeft();
        yAxisRight.setEnabled(false);
        // XAXIS - BOTTOM
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // REMOVE BOTTOM LINE FROM LABEL AND CHART
        xAxis.setDrawAxisLine(false);
        mLineChart.invalidate();
        LabelFormatter formatter = new LabelFormatter(xAxisLabel);
        xAxis.setValueFormatter(formatter);
    }

    public class mXAxisBarValueFormatter implements IValueFormatter {
        private ArrayList <BarEntry> mValues;

        public mXAxisBarValueFormatter(ArrayList<BarEntry> values)
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

    public class mXAxisLineValueFormatter implements IValueFormatter {
        private ArrayList <Entry> mValues;
        public mXAxisLineValueFormatter(ArrayList<Entry> values)
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
