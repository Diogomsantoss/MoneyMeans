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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import moneymeans.R;


/**
 * Created by moura_000 on 11/18/2015.
 */
public class FinancingProposalsAdapter  extends ArrayAdapter<FinancingProposal> {

    public FinancingProposalsAdapter(Context context, ArrayList<FinancingProposal> proposals) {

        super(context, 0, proposals);

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        FinancingProposal proposal = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.listview_proposal_item, parent, false);
        }

        //pega os objetos da view da lista

        TextView TextValue = (TextView) view.findViewById(R.id.total_value);
        TextView TextInterest = (TextView) view.findViewById(R.id.financing_proposal_Interest);
        TextView TextParcelsNumber = (TextView) view.findViewById(R.id.financing_parcels_number);
        TextView TextFirstMonth = (TextView) view.findViewById(R.id.txt_first_month);
        TextView TextInterestType = (TextView) view.findViewById(R.id.txt_type_interest);

        //seta os valores na view atraves do adapter

        TextValue.setText(String.valueOf(proposal.getValue()));
        TextInterest.setText(String.valueOf(proposal.getInterest()));
        TextParcelsNumber.setText(String.valueOf(proposal.getParcelsNumber()));
        TextFirstMonth.setText(String.valueOf(proposal.getFirstMonth()));
        TextInterestType.setText(String.valueOf(proposal.getInterestType()));

        return view;
    }
}
