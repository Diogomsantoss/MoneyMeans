package diogosantos.moneymeans.domain.Adapters;
import  diogosantos.moneymeans.domain.classes.*;
import  diogosantos.moneymeans.domain.domain.dao.*;
import  diogosantos.moneymeans.domain.Fragments.*;
import  diogosantos.moneymeans.domain.Activities.*;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import diogosantos.moneymeans.domain.classes.Expense;
import moneymeans.R;


public class ExpenseListAdapter extends ArrayAdapter<Expense> {


    public ExpenseListAdapter(Context context, ArrayList<Expense> expenses) {

        super(context, 0, expenses);
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Expense exp = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.listview_item, parent, false);
        }

        //pega os objetos da view da lista

        TextView TextExpenseName = (TextView) view.findViewById(R.id.expenseName);
        TextView TextExpenseValue = (TextView) view.findViewById(R.id.valueNumber);
        TextView TextExpenseType = (TextView) view.findViewById(R.id.TipoTexto);
        TextView TextExpenseCategory = (TextView) view.findViewById(R.id.txt_categoria);
        TextView TextExpenseDate = (TextView) view.findViewById(R.id.txt_date);

        //seta os valores na view atraves do adapter

        TextExpenseName.setText(exp.getName());
        TextExpenseValue.setText(exp.getValue());
        TextExpenseType.setText(exp.getType());
        TextExpenseCategory.setText(exp.getCategory());
        TextExpenseDate.setText(exp.getDate());

        return view;
    }

}

