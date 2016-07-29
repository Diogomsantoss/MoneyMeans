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


public class FinancingListAdapter extends ArrayAdapter<Parcel> {

    public FinancingListAdapter(Context context, ArrayList<Parcel> parcels) {

        super(context, 0, parcels);

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Parcel parcel = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.listview_item_financing, parent, false);
        }

        //pega os objetos da view da lista

        TextView TextParcelValue = (TextView) view.findViewById(R.id.parcel_value);
        TextView TextParcelIndex = (TextView) view.findViewById(R.id.parcel_index_number);
        TextView TextParcelDate = (TextView) view.findViewById(R.id.parcel_notification_date);

        //seta os valores na view atraves do adapter

        TextParcelValue.setText(String.valueOf(parcel.getValue()));
        TextParcelIndex.setText(String.valueOf(parcel.getIndex() + 1));
        TextParcelDate.setText(String.valueOf( parcel.getDate().get(Calendar.DAY_OF_MONTH) + "-" + parcel.getDate().get(Calendar.MONTH) + "-" + parcel.getDate().get(Calendar.YEAR) ));

        return view;
    }
}

