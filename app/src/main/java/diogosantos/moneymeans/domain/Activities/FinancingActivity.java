package diogosantos.moneymeans.domain.Activities;
import  diogosantos.moneymeans.domain.Adapters.*;
import  diogosantos.moneymeans.domain.classes.*;
import  diogosantos.moneymeans.domain.domain.dao.*;
import  diogosantos.moneymeans.domain.Fragments.*;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;


import diogosantos.moneymeans.domain.Adapters.FinancingListAdapter;
import moneymeans.R;

public class FinancingActivity extends ActionBarActivity {

    ListView financingListView;
    ArrayList<Parcel> parcelsList = new ArrayList<Parcel>();
    EditText txtTotalValue, txtInterestValue, txtParcelsNumber;
    ArrayList<FinancingProposal> financingProposals = new ArrayList<FinancingProposal>();
    FinancingListAdapter arrayAdapterParcel;
    Parcel parcel;
    Switch InterestTypeSwitch;
    FinancingProposal financeproposal;
    DatabaseHandler dbHandler;


    final Calendar c = Calendar.getInstance();


    int TotalValue,
            intInterest,
            ParcelsNumber,
            ParcelValue,
            calculatedFinalValue,
            i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.financing_projector);

        financingListView = (ListView) findViewById(R.id.list_financiamento);

        InterestTypeSwitch = (Switch) findViewById(R.id.switch_interest_type);

        dbHandler = DatabaseHandler.getInstance(super.getApplicationContext());

        final FinancingListAdapter arrayAdapterParcel = new FinancingListAdapter(getApplicationContext(), parcelsList);
        financingListView.setAdapter(arrayAdapterParcel);


        final Button ListParcelsBtn = (Button) findViewById(R.id.btnListParcelas);
        ListParcelsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txtTotalValue = (EditText) findViewById(R.id.txt_total_value_financing);
                txtInterestValue = (EditText) findViewById(R.id.txt_value_interest);
                txtParcelsNumber = (EditText) findViewById(R.id.parcels_number);

                Double calculatedFinalValue;

                Double TotalValue = Double.parseDouble(String.valueOf(txtTotalValue.getText()));
                Double intInterest = Double.parseDouble(String.valueOf(txtInterestValue.getText()));
                int ParcelsNumber = Integer.parseInt(String.valueOf(txtParcelsNumber.getText()));

                intInterest = intInterest/100;
                calculatedFinalValue = 0.0;

                if (InterestTypeSwitch.isChecked()) {
                    calculatedFinalValue = TotalValue * Math.pow((1 + intInterest), ParcelsNumber);
                } else {
                    calculatedFinalValue = TotalValue * (1 + (intInterest * ParcelsNumber));
                }

                Double ParcelValue = calculatedFinalValue / ParcelsNumber;

                // arredonda o resultado

                ParcelValue =  round(ParcelValue, 2);

                parcelsList.clear(); // limpa a lista assim que o usuario aperta o botao

                //cria a lista de parcelas
                for (i = 0; i < ParcelsNumber; i++) {

                    c.add(Calendar.MONTH, 1);
                    Parcel parcel = new Parcel(i, String.valueOf(ParcelValue), c );

                    parcelsList.add(parcel);


                }


                arrayAdapterParcel.notifyDataSetChanged();
                financingListView.invalidate();


            }
        });


        final Button btnProposalsList = (Button) findViewById(R.id.btnProposalsList);
        btnProposalsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), FinancingListActivity.class);
                startActivity(intent);
                return;



            }
        });



        final Button btnMainTransactionEntryActivity = (Button) findViewById(R.id.btn_main_transaction_entry_activity);
        btnMainTransactionEntryActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return;



            }
        });

        // botao que chama o construtor de finacing proposal, passando o que o usuario digitou,
        final Button BtnCreateFinancingProposal = (Button) findViewById(R.id.button_create_financing_proposal);
        BtnCreateFinancingProposal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String option = new String();

                if (InterestTypeSwitch.isChecked()) {
                    option  = String.valueOf(InterestTypeSwitch.getTextOn());
                } else {
                    option = String.valueOf(InterestTypeSwitch.getTextOff());
                }


                financeproposal =  new FinancingProposal(dbHandler.getFinancingsCount(), String.valueOf(txtTotalValue.getText()), String.valueOf(txtInterestValue.getText()), String.valueOf(txtParcelsNumber.getText()), String.valueOf(c.get(Calendar.MONTH) + 1), String.valueOf(option));
                dbHandler.createFinancingProposal(financeproposal);
                financingProposals.add(financeproposal);

                Toast.makeText(getApplicationContext(), "Proposta de financiamento criada com sucesso ", Toast.LENGTH_SHORT).show();

            }
        });

         // listeners das caixas de texto para poder hablitar o botao
        txtTotalValue = (EditText) findViewById(R.id.txt_total_value_financing);
        txtInterestValue = (EditText) findViewById(R.id.txt_value_interest);
        txtParcelsNumber = (EditText) findViewById(R.id.parcels_number);
        txtParcelsNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                ListParcelsBtn.setEnabled(String.valueOf(txtParcelsNumber.getText()).trim().length() > 0 &&
                        String.valueOf(txtInterestValue.getText()).trim().length() > 0 &&
                        String.valueOf(txtTotalValue.getText()).trim().length() > 0);


                BtnCreateFinancingProposal.setEnabled(String.valueOf(txtParcelsNumber.getText()).trim().length() > 0 &&
                        String.valueOf(txtInterestValue.getText()).trim().length() > 0 &&
                        String.valueOf(txtTotalValue.getText()).trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    // funcao para arredondar resultado
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
