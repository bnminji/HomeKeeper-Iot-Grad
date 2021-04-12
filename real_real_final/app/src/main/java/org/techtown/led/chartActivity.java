package org.techtown.led;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class chartActivity extends AppCompatActivity {
    private LineChart lineChart;
    private Thread thread;
    MainActivity mActivity;
    private static final String TAG = "chartActivity";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mq7_chart);
        ArrayList<Entry> entryChart = new ArrayList<>();
        lineChart = (LineChart) findViewById(R.id.lineChart);//layout의 id
        LineData chartData = new LineData();
        lineChart.getDescription().setEnabled(true);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setDrawGridBackground(true);
        chartData.setValueTextColor(Color.WHITE);
        lineChart.animateXY(1000,1000);
        lineChart.getDescription().setTextColor(Color.WHITE);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setTextColor(Color.WHITE);
        xAxis.setTextSize(10f);
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(Color.WHITE);
        yLAxis.setAxisMaximum(100f);
        yLAxis.setAxisMinimum(0f);
        yLAxis.setDrawGridLines(false);
        yLAxis.setGridColor(Color.WHITE);

        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setEnabled(false);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // 전체화면
        lineChart.setData(chartData);
        feedMultiple(); // 스레드 활용하여 실시간 데이터 활용 - 따로 작성

        Button chartButton =(Button) findViewById(R.id.chartButton);
        chartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mActivity.sendMsg("TURNOFF_WARNING", "Turn-off alarm");
            }
        });

    }

    private LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null, "CO data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(Color.GREEN);
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setFillColor(Color.GREEN);
        set.setDrawFilled(true); // 선 아래로 색상 표시
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(true);
        return set;
    }

    private void feedMultiple() {
        if(thread != null) {
            thread.interrupt();
        }
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                addEntry();
            }
        };
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    runOnUiThread(runnable);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }
    private void addEntry() {
        Intent intent = getIntent();
        LineData data = lineChart.getData();
        String[] idandname = intent.getStringArrayExtra("idandname");
        float[] CO_Value = intent.getFloatArrayExtra("values");
        int max = CO_Value.length;
        if (data != null) {
            ILineDataSet set = data.getDataSetByIndex(0);
            if (set == null) {
                set = createSet();
                data.addDataSet(set);
                int i = 0;
                while(i < max) {
                    float CO_data = CO_Value[i];
                    data.addEntry(new Entry(set.getEntryCount(), CO_data), 0);
                    i++;
                }
            }
            else { // intent = getIntent()로 데이터 가져오기!
                // 배열이 main에서 새로 받아지지 않았거나, 업데이트된 배열을 가져오지 않아서 마지막 값만 계속 나오는듯
                // 위 둘다인거 같아서 같이 수정해봐야할듯!
                // 그래도 한개값씩만 들어오도록 변경 성공해서 다행 ㅠㅠ
                data.addEntry(new Entry(set.getEntryCount(), CO_Value[max-1]), 0);
            }
            data.notifyDataChanged();
            lineChart.invalidate();

            // let the graph know it's data has changed
            lineChart.notifyDataSetChanged();
            // limit the number of visible entries
            lineChart.setVisibleXRangeMaximum(120);
            // move to the latest entry
            lineChart.moveViewToX(data.getEntryCount());
        }
    }
}