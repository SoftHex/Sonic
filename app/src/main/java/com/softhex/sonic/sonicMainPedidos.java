package com.softhex.sonic;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

public class sonicMainPedidos extends Fragment {

    private View myView;
    private ProgressBar pbChart;
    private sonicDatabaseCRUD mData;
    private List<sonicVendasPedidosHolder> mList;
    private LineChart mChart;
    private Context mContex;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.sonic_recycler_layout_chart, container, false);

        mContex = getContext();
        pbChart = myView.findViewById(R.id.pbChart);
        mData = new sonicDatabaseCRUD(getContext());
        mChart = myView.findViewById(R.id.mLineChart);
        mList = mData.Venda.selectPedidos();
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

        ArrayList<String> xAxisLabel = new ArrayList<>();
        ArrayList<Entry> valueSet = new ArrayList<>();
        ArrayList<ILineDataSet> dataSets = null;
        dataSets = new ArrayList<>();
        Log.d("SIZE",mList.size()+"");
        for(int i=0 ; i<mList.size() ; i++){
            //String ano = mList.get(i).getAno().length()<4 ? mList.get(i).getAno() : mList.get(i).getAno().substring(2);
            switch (mList.get(i).getMes()){
                case "01":
                    xAxisLabel.add("Jan");
                    break;
                case "02":
                    xAxisLabel.add("Fev");
                    break;
                case "03":
                    xAxisLabel.add("Mar");
                    break;
                case "04":
                    xAxisLabel.add("Abr");
                    break;
                case "05":
                    xAxisLabel.add("Mai");
                    break;
                case "06":
                    xAxisLabel.add("Jun");
                    break;
                case "07":
                    xAxisLabel.add("Jul");
                    break;
                case "08":
                    xAxisLabel.add("Ago");
                    break;
                case "09":
                    xAxisLabel.add("Set");
                    break;
                case "10":
                    xAxisLabel.add("Out");
                    break;
                case "11":
                    xAxisLabel.add("Nov");
                    break;
                case "12":
                    xAxisLabel.add("Dez");
                    break;
                default:
                    xAxisLabel.add("--");
                    break;
            }
            valueSet.add(new Entry(i, mList.get(i).getTotal()));
            Log.d("TOTAL", mList.get(i).getTotal()+"");
        }
        LineDataSet lineDataSet = new LineDataSet(valueSet, "");
        lineDataSet.setValues(valueSet);
        lineDataSet.setColor(getResources().getColor(R.color.colorTypeInfo));
        lineDataSet.setValueTextColor(Color.WHITE);
        lineDataSet.setValueTextSize(12f);
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleHoleRadius(10);

        lineDataSet.setValueFormatter(new sonicMainPedidos.MyXAxisValueFormatter(valueSet));
        dataSets.add(lineDataSet);

        LineData data = new LineData(dataSets);
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
        mChart.setViewPortOffsets(26,0,26,56);
        YAxis yAxisRight = mChart.getAxisRight();
        yAxisRight.setEnabled(false);
        yAxisRight = mChart.getAxisLeft();
        yAxisRight.setEnabled(false);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        sonicMainPedidos.LabelFormatter formatter = new sonicMainPedidos.LabelFormatter(xAxisLabel);
        xAxis.setValueFormatter(formatter);
    }

    public class MyXAxisValueFormatter implements IValueFormatter {
        private ArrayList <Entry> mValues;
        public MyXAxisValueFormatter(ArrayList<Entry> values)
        {
            this.mValues=values;
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return (int)value+"";
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
