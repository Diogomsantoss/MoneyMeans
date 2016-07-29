package diogosantos.moneymeans.domain.Fragments;
import  diogosantos.moneymeans.domain.Activities.*;
import  diogosantos.moneymeans.domain.Adapters.*;
import  diogosantos.moneymeans.domain.classes.*;
import  diogosantos.moneymeans.domain.domain.dao.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import moneymeans.R;


public class ExpenseListFragment extends Fragment {

    EditText edit;
    String text;
    public ListView listview;
    DatabaseHandler dbHandler;
    ArrayAdapter<Expense> arrayAdapter;
    ArrayList<Expense> expenses;
    ArrayList<FinancingProposal> financingProposals = new ArrayList<FinancingProposal>();

    TextView textviewSaldo;
    int intBalance = 0;
    int i; //indice generico

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.expenses_list, container, false);
        setExpenseArray(this.expenses);
        listview = (ListView) v.findViewById(R.id.listView);
        listview.setAdapter(this.arrayAdapter);
        textviewSaldo = (TextView) v.findViewById(R.id.textview_saldo);

        textviewSaldo.setText(String.valueOf(intBalance));
        textviewSaldo.invalidate();

        listview.invalidate();

        listview.setLongClickable(true);
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View v, final int position, long id) {
                //Do your tasks here

                AlertDialog.Builder alert = new AlertDialog.Builder(
                        getActivity());
                alert.setTitle("Alerta");
                alert.setMessage("Você tem certeza que deseja deletar essa transação ?");
                        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                               dbHandler.deleteExpense(expenses.get(position));

                                Toast.makeText(getActivity().getApplicationContext(),"despesa de indice " + position + " foi removida!", Toast.LENGTH_SHORT).show();
                                expenses.remove(position);
                                arrayAdapter.notifyDataSetChanged();
                                calculateBalance();
                                textviewSaldo.setText(String.valueOf(intBalance));
                                textviewSaldo.invalidate();
                                dialog.dismiss();

                            }
                        });
                alert.setNegativeButton("Não", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                alert.show();

                return true;
            }
        });

        this.arrayAdapter.notifyDataSetChanged();
        return v;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        dbHandler = new DatabaseHandler(super.getActivity());
    }


    public void calculateBalance() {
        this.intBalance = 0;

        for (i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getType().equals("ENTRADA")) {
                intBalance = intBalance + Integer.parseInt(expenses.get(i).getValue());
            } else {
                intBalance = intBalance - Integer.parseInt(expenses.get(i).getValue());

            }

        }

    }

    public int getCurrentBalance () {
        return this.intBalance;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
    }

    public static ExpenseListFragment newInstance()
    {
        ExpenseListFragment myFrag = new ExpenseListFragment();
        return myFrag;
    }

    public ListView getTheListView() {
        return this.listview;
    }

    public void setAdapterForTheListView(ArrayAdapter<Expense> arrayAdapter) {


       this.arrayAdapter = arrayAdapter;
       this.arrayAdapter.notifyDataSetChanged();

    }





    public void setExpenseArray(ArrayList<Expense> expenses) {
       this.expenses = expenses;
    }

    public ArrayList<Expense> getExpenseArray() {
       return this.expenses;
    }


    public void notifydatasetchanged() {
        arrayAdapter.notifyDataSetChanged();
    }

    public void invalidadeBalance() {
        textviewSaldo.setText(String.valueOf(intBalance));
        textviewSaldo.invalidate();
    }

    public int getTheBalance () {
       return this.intBalance;
    }


    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        calculateBalance();
        textviewSaldo.setText(String.valueOf(intBalance));
        textviewSaldo.invalidate();
        getView().invalidate();

    }

    public void createNotification(View view) {
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(getActivity().getApplicationContext(), FinancingActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(getActivity(), 0, intent, 0);

        // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(getActivity().getApplicationContext())
                .setContentTitle("Notification Title")
                .setContentText("Click here to read").setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pIntent)
                .build();
    }

    public int calculatedCurrentObligations() {

            return 0;
    }


    public static class ActivityFinancingProjector extends ActionBarActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.financing_projector);
        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.activity_financing_projector, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();
            if (id == R.id.action_settings) {
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }
}
