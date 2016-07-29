package diogosantos.moneymeans.domain.Activities;
import  diogosantos.moneymeans.domain.Adapters.*;
import  diogosantos.moneymeans.domain.classes.*;
import  diogosantos.moneymeans.domain.domain.dao.*;
import  diogosantos.moneymeans.domain.Fragments.*;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import diogosantos.moneymeans.domain.Adapters.FinancingListAdapter;
import moneymeans.R;

public class FinancingListActivity extends ActionBarActivity {

    ListView financingProposalsListView;
    ArrayList<FinancingProposal> financingProposals = new ArrayList<FinancingProposal>();
    FinancingProposalsAdapter arrayAdapterProposals;
    FinancingProposal financeproposal;
    DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.financing_proposals_list);

        final Calendar currentdate = Calendar.getInstance();
        final Calendar c = Calendar.getInstance();
        financingProposalsListView = (ListView) findViewById(R.id.proposals_list_view);

        dbHandler = DatabaseHandler.getInstance(this);

        currentdate.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH));
        currentdate.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
        currentdate.set(Calendar.YEAR, c.get(Calendar.YEAR) - 1);

      //  Toast.makeText(getApplicationContext(), currentdate.get(Calendar.DAY_OF_MONTH) + "-" + currentdate.get(Calendar.MONTH) + "-" + currentdate.get(Calendar.YEAR) , Toast.LENGTH_SHORT).show();

        financingProposals = dbHandler.getAllFinancingProposals();

        final FinancingProposalsAdapter arrayAdapterProposals = new FinancingProposalsAdapter(getApplicationContext(), financingProposals);
        arrayAdapterProposals.notifyDataSetChanged();
        financingProposalsListView.setAdapter(arrayAdapterProposals);
        arrayAdapterProposals.notifyDataSetChanged();
        financingProposalsListView.invalidate();


        financingProposalsListView.setLongClickable(true);
        financingProposalsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View v, final int position, long id) {
                //Do your tasks here

                AlertDialog.Builder alert = new AlertDialog.Builder(
                        FinancingListActivity.this);
                alert.setTitle("Alerta");
                alert.setMessage("Você tem certeza que deseja deletar essa proposta ?");
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dbHandler.deleteFinanceProposal(financingProposals.get(position));

                        Toast.makeText(getApplicationContext(),"proposta de financiamento de indice " + position + " foi removida!", Toast.LENGTH_SHORT).show();
                        financingProposals.remove(position);
                        arrayAdapterProposals.notifyDataSetChanged();
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


        final Button btnProjectorActivity = (Button) findViewById(R.id.button_call_financing_activity);
        btnProjectorActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), FinancingActivity.class);
                startActivity(intent);
                return;



            }
        });

    }



}
