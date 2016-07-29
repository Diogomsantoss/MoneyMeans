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


public class ExpenseListAdapterFilterable extends ArrayAdapter<Expense> implements Filterable {

    private final ArrayList<Expense>  mItems;
    private       ArrayList<Expense>  mFilteredItems;
    private final Comparator<Object> mComparator;
    private String QueryString;

    public ExpenseListAdapterFilterable(Context context, ArrayList<Expense> expenses) {

        super(context, 0, expenses);

        mItems = new ArrayList<Expense>(expenses);
        mFilteredItems = new ArrayList<Expense>(expenses);
        mComparator = new Comparator<Object>()
        {
            @Override
            public int compare(Object o1, Object o2)
            {
                String s1 = ((Expense)o1).getCategory();
                String s2 = ((Expense)o2).getCategory();
                return s1.toLowerCase(Locale.getDefault()).compareTo(s2.toLowerCase());
            }
        };

         Collections.sort(mItems, mComparator);
         Collections.sort(mFilteredItems, mComparator);

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

    @Override
    public int getCount()
    {
        return mFilteredItems.size();
    }

    @Override
    public Expense getItem(int position)
    {
        return mFilteredItems.get(position);
    }

    @Override
    public int getPosition(Expense item)
    {
        return mFilteredItems.indexOf(item);
    }

    @Override
    public Filter getFilter()
    {
        return new Filter()
        {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results)
            {
                mFilteredItems = (ArrayList<Expense>)results.values;
                if (results.count > 0)
                {
                    notifyDataSetChanged();
                }
                else
                {
                    notifyDataSetInvalidated();
                }
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint)
            {



                ArrayList<Expense> filteredResults = new ArrayList<Expense>();
                for (Expense item : mItems)
                {
                    if (item.getCategory().toLowerCase(Locale.getDefault()).contains(constraint.toString().toLowerCase(Locale.getDefault())))
                    {
                        filteredResults.add(item);
                    }

                    if (constraint.toString().equals("Sem filtro") ) {
                        filteredResults.add(item);
                    }

                }

                Collections.sort(filteredResults, mComparator);
                FilterResults results = new FilterResults();
                results.values = filteredResults;
                results.count = filteredResults.size();

                return results;
            }
        };
    }
}

