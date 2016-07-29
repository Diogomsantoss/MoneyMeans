package diogosantos.moneymeans.domain.Activities;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;


import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import diogosantos.moneymeans.domain.Adapters.ExpenseListAdapter;
import diogosantos.moneymeans.domain.Adapters.ExpenseListAdapterFilterable;
import diogosantos.moneymeans.domain.classes.Expense;
import diogosantos.moneymeans.domain.domain.dao.DatabaseHandler;
import moneymeans.R;


public class CategoryChartActivity extends ActionBarActivity {





    public class GraphSlice {

        public String SliceCategory;
        public float  SliceValue = 0 ;



    }

    ArrayList<Expense> expenses;
    DatabaseHandler dbHandler;


    GraphSlice slice;

    GraphSlice[] slices = new GraphSlice[7];

    String[] sortedSlices = new String[7];


    private PieChart mChart;
    private float [] yData = {0, 0, 0, 0, 0, 0, 0};
    private String[] xData = {"Moradia", "Saude", "Vestuario", "Acessorios", "Educação", "Transporte", "Lazer"};

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.pizza_chart);

        yData = getTheCategoryCalculatedArray ();

        mChart = new PieChart(this);
        setContentView(mChart);

        mChart.setUsePercentValues(true);
        mChart.setDescription("Grafico de pizza de categorias de gastos");
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(55);
        mChart.setTransparentCircleRadius(15);

        mChart.setNoDataTextDescription("Sem dados. Cadastre novas transações");



        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);


        final ExpenseListAdapterFilterable arrayAdapter = new ExpenseListAdapterFilterable(this , expenses);

        ListView expensesList = new ListView(this);
        expensesList.setAdapter(arrayAdapter);

        final AlertDialog.Builder ListFilteredByCategory = new AlertDialog.Builder(this);
        ListFilteredByCategory.setTitle("Categoria");
        ListFilteredByCategory.setCancelable(true);
        ListFilteredByCategory.setView(expensesList);
        final Dialog ListFilteredDialog = ListFilteredByCategory.create();


        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (e == null)
                    return;

                Toast.makeText(CategoryChartActivity.this,"Total de gastos Categoria : " +  xData[e.getXIndex()] + " = R$" + e.getVal(), Toast.LENGTH_LONG).show();

                arrayAdapter.getFilter().filter(xData[e.getXIndex()]);
                ListFilteredDialog.show();

            }

            @Override
            public void onNothingSelected() {

            }
        });

        addData ();

        Legend legend = mChart.getLegend();
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        legend.setXEntrySpace(7);
        legend.setYEntrySpace(5);

        for (int i = 0; i < yData.length; i++){

            slice = new GraphSlice();

            slice.SliceValue = yData[i];
            slice.SliceCategory = xData[i];

            slices[i] = slice;

        }



        Arrays.sort(slices, new Comparator<GraphSlice>() {
            @Override
            public int compare(GraphSlice slice1, GraphSlice slice2) {
                if(slice1.SliceValue < slice2.SliceValue) return 1;
                else if(slice1.SliceValue > slice2.SliceValue) return -1;
                else return 0;
            }
        });


        for (int i = 0; i < yData.length; i++){

            String SliceString = new String();

            SliceString = slices[i].SliceCategory;

            sortedSlices[i] = SliceString;


        }




        Spinner SortedListadeCategorias = (Spinner) new Spinner(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, sortedSlices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SortedListadeCategorias.setAdapter(adapter);


        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Lista de gategorias Valor Decrescente, do topo até o rodapé. ");
        b.setCancelable(true);

        b.setItems(sortedSlices, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                switch(which){
                    case 0:
                        arrayAdapter.getFilter().filter(sortedSlices[0]);
                        ListFilteredDialog.show();
                        break;
                    case 1:
                        arrayAdapter.getFilter().filter(sortedSlices[1]);
                        ListFilteredDialog.show();
                        break;
                    case 2:
                        arrayAdapter.getFilter().filter(sortedSlices[2]);
                        ListFilteredDialog.show();
                        break;
                    case 3:
                        arrayAdapter.getFilter().filter(sortedSlices[3]);
                        ListFilteredDialog.show();
                        break;
                    case 4:
                        arrayAdapter.getFilter().filter(sortedSlices[4]);
                        ListFilteredDialog.show();
                        break;
                    case 5:
                        arrayAdapter.getFilter().filter(sortedSlices[5]);
                        ListFilteredDialog.show();
                        break;
                    case 6:
                        arrayAdapter.getFilter().filter(sortedSlices[6]);
                        ListFilteredDialog.show();
                        break;
                }
            }

        });

        b.show();



    }

    private void addData () {
        ArrayList<Entry> yValsl = new ArrayList<Entry>();

        for (int i = 0; i < yData.length; i++)
            yValsl.add(new Entry(yData[i],i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xData.length; i++)
            xVals.add(xData[i]);

        PieDataSet dataset = new PieDataSet(yValsl, "Categorias");
        dataset.setSliceSpace(3);
        dataset.setSelectionShift(5);

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataset.setColors(colors);

        PieData data = new PieData(xVals, dataset);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.parseColor("#111111"));



        mChart.setData(data);


        mChart.highlightValue(null);

        mChart.invalidate();
    }


    public float[] getTheCategoryCalculatedArray ()
    {

        dbHandler = new DatabaseHandler(getApplicationContext());

        expenses = dbHandler.getAllExpenses();

        Expense expense;

        float [] yData = {0, 0, 0, 0, 0, 0, 0};

        for (int i = 0; i < expenses.size(); i++) {

            expense = expenses.get(i);

            String category = expense.getCategory();

            if ( category.equals("Moradia"))
                yData[0] = yData[0] + Integer.parseInt(expense.getValue());

            if ( category.equals( "Saude"))
                yData[1] = yData[1] + Integer.parseInt(expense.getValue());

            if ( category.equals( "Vestuario"))
                yData[2] = yData[2] + Integer.parseInt(expense.getValue());

            if ( category.equals( "Acessorios"))
                yData[3] = yData[3] + Integer.parseInt(expense.getValue());

            if ( category.equals( "Educação"))
                yData[4] = yData[4] + Integer.parseInt(expense.getValue());

            if ( category.equals( "Transporte"))
                yData[5] = yData[5] + Integer.parseInt(expense.getValue());

            if ( category.equals( "Lazer"))
                yData[6] = yData[6] + Integer.parseInt(expense.getValue());


        }

        return yData;

    }

    public float ReturnSortedCategoryValues() {

        float yData[] = getTheCategoryCalculatedArray();

        Arrays.sort(yData);

        float max = yData[yData.length - 1];

        return max;

    }



}