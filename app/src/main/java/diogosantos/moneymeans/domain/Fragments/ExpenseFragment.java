package diogosantos.moneymeans.domain.Fragments;
import  diogosantos.moneymeans.domain.Activities.*;
import  diogosantos.moneymeans.domain.Adapters.*;
import  diogosantos.moneymeans.domain.classes.*;
import  diogosantos.moneymeans.domain.domain.dao.*;

import  diogosantos.moneymeans.domain.*;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

import diogosantos.moneymeans.domain.Activities.FinancingActivity;
import moneymeans.R;

public class ExpenseFragment extends Fragment {


    LinearLayout llLayout;
    FragmentActivity faActivity;
    Expense expense;
    Balance balance;
    DatabaseHandler dbHandler;
    EditText nameTxt, valueTxt;
    ArrayList<Expense> expenses = new ArrayList<Expense>();
    ListView expensesList;
    Switch TypeSwitch;
    Spinner spinnerCategory;
    DatePicker dateEntry;
    String textSumUpDate;
    Context context;
    DatePickerFragment datePickerDialog;


    int mYear;
    int mMonth;
    int mDay;

    static final int DATE_DIALOG_ID = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.expense_entry, container, false);

        nameTxt = (EditText) v.findViewById(R.id.txtName);
        valueTxt = (EditText) v.findViewById(R.id.textValue);
        TypeSwitch = (Switch) v.findViewById(R.id.switch_transaction_type);
        //dateEntry = (DatePicker) v.findViewById(R.id.datePicker);

        datePickerDialog = new DatePickerFragment();

        String spinnerArray[] = {"Moradia", "Saude", "Vestuario", "Acessorios", "Educação", "Transporte", "Lazer", "Salario"};
        spinnerCategory = (Spinner) v.findViewById(R.id.spinner_category);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCategory = (Spinner) v.findViewById(R.id.spinner_category);
        spinnerCategory.setAdapter(spinnerArrayAdapter);

        dbHandler = DatabaseHandler.getInstance(super.getActivity().getApplicationContext());


        final Button addBtn = (Button) v.findViewById(R.id.btnAdd);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                textSumUpDate = datePickerDialog.getTheDate();

                String option = new String();

                if (TypeSwitch.isChecked()) {
                    option = String.valueOf(TypeSwitch.getTextOn());
                } else {
                    option = String.valueOf(TypeSwitch.getTextOff());
                }
                Expense expense = new Expense(dbHandler.getExpensesCount(), String.valueOf(nameTxt.getText()), String.valueOf(valueTxt.getText()), String.valueOf(option), spinnerCategory.getSelectedItem().toString(), textSumUpDate);

                dbHandler.createExpense(expense);
                expenses.add(expense);


                Toast.makeText(getActivity(), String.valueOf(nameTxt.getText()) + " Foi registrada uma transação!", Toast.LENGTH_SHORT).show();
                return;


            }
        });

        final AppCompatImageButton btnCallPizzaChart = (AppCompatImageButton) v.findViewById(R.id.btnCallPizzaChartActivity);
        btnCallPizzaChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), CategoryChartActivity.class);
                startActivity(intent);
                return;



            }
        });

        final AppCompatImageButton btnCallLineChart = (AppCompatImageButton) v.findViewById(R.id.btnCallBarsChartActivity);
        btnCallLineChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent intent = new Intent(getActivity(), TimeChartActivity.class);
               startActivity(intent);
                return;



            }
        });

        final AppCompatImageButton todayDaterBtn = (AppCompatImageButton) v.findViewById(R.id.today_button);
        todayDaterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datePickerDialog.show(getFragmentManager().beginTransaction(), "datePicker");

                textSumUpDate = datePickerDialog.getTheDate();

                return;


            }
        });


        final Button callProjectorBtn = (Button) v.findViewById(R.id.btnCallProjetor);
        callProjectorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FinancingActivity.class);
                getActivity().startActivity(intent);
                return;


            }
        });

        nameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                addBtn.setEnabled(String.valueOf(nameTxt.getText()).trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        if (dbHandler.getExpensesCount() != 0)
            expenses.addAll(dbHandler.getAllExpenses());

        //     populateList();

        return v;
    }

//    private void populateList() {
//        ArrayAdapter<Expense> adapter = new ExpenseListAdapter(super.getActivity().getApplicationContext(), expenses);
//       expensesList.setAdapter(adapter);
//    }


    public ArrayList<Expense> getExpensesList() {
        return this.expenses;
    }


    public ExpenseListAdapter getExpensesListAdapter() {
        ExpenseListAdapter adapter = new ExpenseListAdapter(getActivity().getApplicationContext(), expenses);
        return adapter;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
    }

    public void callFinancingView(View view) {
        Intent intent = new Intent(super.getActivity().getApplicationContext(), FinancingActivity.class);
        getActivity().startActivity(intent);
    }

    public static ExpenseFragment newInstance()
    {
        ExpenseFragment myFrag = new ExpenseFragment();
        return myFrag;
    }


}

