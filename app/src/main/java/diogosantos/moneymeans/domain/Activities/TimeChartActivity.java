package diogosantos.moneymeans.domain.Activities;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


import diogosantos.moneymeans.domain.classes.Expense;
import diogosantos.moneymeans.domain.domain.dao.DatabaseHandler;
import moneymeans.R;


public class TimeChartActivity extends ActionBarActivity {

    ArrayList<Expense> expenses;
    DatabaseHandler dbHandler;

    private LineChart mChart;
    private float [] yData = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private String[] xData = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.line_chart);

        yData = getTheMontlyCalculatedArray();

        mChart = new LineChart(this);
        setContentView(mChart);

        mChart.setNoDataTextDescription("Sem dados. Cadastre novas transações");
        mChart.setDescription("Grafico de linha para transações ao decorrer do mês");

        mChart.getYChartMax();
        mChart.getXChartMax();

        mChart.setHighlightPerTapEnabled(true);
        mChart.setTouchEnabled(true);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);

        mChart.setPinchZoom(true);

        mChart.setBackgroundColor(Color.parseColor("#cdf2bd"));

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Janeiro");
        labels.add("Fevereiro");
        labels.add("Março");
        labels.add("Abril");
        labels.add("Maio");
        labels.add("Junho");
        labels.add("Julho");
        labels.add("Agosto");
        labels.add("Setembro");
        labels.add("Outubro");
        labels.add("Dezembro");

        LineData data = new LineData(labels, addEntry() );
        mChart.setData(data);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                    for (int i = 0; i < expenses.size(); i++) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                           public void run() {
//                               addEntry();
//                            }
//                        });

//                        try {
//                           Thread.sleep(600);
//                        } catch (InterruptedException e)
//                                {

//                                   }
//                    }
//            }

//        }).start();

//    }

    private LineDataSet addEntry () {

        ArrayList<Entry> entries = new ArrayList<Entry>();
        entries.add(new Entry(yData[0], 0));
        entries.add(new Entry(yData[1], 1));
        entries.add(new Entry(yData[2], 2));
        entries.add(new Entry(yData[3], 3));
        entries.add(new Entry(yData[4], 4));
        entries.add(new Entry(yData[5], 5));
        entries.add(new Entry(yData[6], 6));
        entries.add(new Entry(yData[7], 7));
        entries.add(new Entry(yData[8], 8));
        entries.add(new Entry(yData[9], 9));

        LineDataSet dataset = new LineDataSet(entries, "# de transações");

        dataset.setCircleSize(10);
        dataset.setCircleColor(Color.parseColor("#bae9b6"));
        dataset.setValueTextSize(20);

        return dataset;

    }


    public float[] getTheMontlyCalculatedArray () {

        dbHandler = new DatabaseHandler(getApplicationContext());

        expenses = dbHandler.getAllExpenses();

        Expense expense;

        float[] yData = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};


        for (int i = 0; i < 12; i++) {

            for (int j = 0; j < expenses.size(); j++) {

                expense = expenses.get(j);

                String date = expense.getDate();
                String[] dateValues = date.split("-");
                String month = dateValues[1];
                month.trim();

              //  Toast.makeText(getApplicationContext(), month, Toast.LENGTH_SHORT).show();

                if (month.equals(String.valueOf(i))) {
                    yData[i] = yData[i] + (float) Integer.parseInt(expense.getValue());
                }

                // faz uma vez o loop da lista inteira uma vez pra cada mes
            }

         //   Toast.makeText(getApplicationContext(), String.valueOf(yData[i]), Toast.LENGTH_SHORT).show();

        }



        return yData;
    }
}