package diogosantos.moneymeans.domain.Activities;
import  diogosantos.moneymeans.domain.Adapters.*;
import diogosantos.moneymeans.domain.Services.UpdateServiceManager;
import  diogosantos.moneymeans.domain.classes.*;
import  diogosantos.moneymeans.domain.domain.dao.*;
import  diogosantos.moneymeans.domain.Fragments.*;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import diogosantos.moneymeans.domain.Fragments.ExpenseFragment;
import moneymeans.R;


public class MainActivity extends ActionBarActivity {

    ListView mDrawerList;
    ArrayAdapter<String> mAdapter;
    ViewPager mViewPager;
    DatabaseHandler dbHandler = new DatabaseHandler(this);
    ListView expensesListView;
    List<Fragment> listFragments = new ArrayList<Fragment>();
    ExpenseFragment fragment_expense;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, UpdateServiceManager.class);
        intent.putExtra(UpdateServiceManager.PARAM_IN_MSG, "teste");
        startService(intent);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        dbHandler = DatabaseHandler.getInstance(super.getApplicationContext());

        final ExpenseListFragment fragment_expenses_list = ExpenseListFragment.newInstance();
        fragment_expense = ExpenseFragment.newInstance();


        listFragments.add(fragment_expense);
        listFragments.add(fragment_expenses_list);


        ArrayList<Expense> expenses = fragment_expense.getExpensesList();

        ExpenseListAdapter arrayAdapter = new ExpenseListAdapter(this, expenses);
        arrayAdapter.notifyDataSetChanged();

        fragment_expenses_list.setAdapterForTheListView(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();


        fragment_expenses_list.setExpenseArray(expenses);
        arrayAdapter.notifyDataSetChanged();

        startService(new Intent(MainActivity.this, UpdateServiceManager.class));



        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(
                getSupportFragmentManager(), listFragments);
        mViewPager.setAdapter(myFragmentPagerAdapter);

        ViewPager.SimpleOnPageChangeListener mListener= new ViewPager.SimpleOnPageChangeListener(){


            @Override
            public void onPageSelected (int position) {
                if (position == 1) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(fragment_expenses_list.getView().getWindowToken(), 0);

                    fragment_expenses_list.listview.invalidate();
                    fragment_expenses_list.calculateBalance();
                    fragment_expenses_list.invalidadeBalance();
                    fragment_expenses_list.notifydatasetchanged();
                }
            }



        };
        mViewPager.setOnPageChangeListener(mListener);


    }


    private void addDrawerItems() {
        String[] useCasesArray = {"Registrador", "Relator", "Projetor"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, useCasesArray);
        mDrawerList.setAdapter(mAdapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    
}
