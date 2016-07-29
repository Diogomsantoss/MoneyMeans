package diogosantos.moneymeans.domain.classes;
import android.widget.EditText;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;

import  diogosantos.moneymeans.domain.domain.dao.*;
import  diogosantos.moneymeans.domain.Activities.*;
import  diogosantos.moneymeans.domain.Adapters.*;
import  diogosantos.moneymeans.domain.Fragments.*;
import moneymeans.R;

public class FinancingProposal {

    private String _value, _interest, _parcelsnumber, _first_month, _interest_type;
    private int _id;

    public FinancingProposal(int id, String value, String interest, String parcelsnumber, String firstmonth, String interestType) {
        _id = id;
        _value = value;
        _interest = interest;
        _parcelsnumber = parcelsnumber;
        _first_month = firstmonth;
        _interest_type = interestType;
    }

    public int getId() { return _id; }

    public String getValue() {
        return _value;
    }

    public String getInterest() { return _interest; }

    public String getParcelsNumber() { return _parcelsnumber; }

    public String getFirstMonth() { return _first_month; }

    public String getInterestType() { return _interest_type; }


    public double getCalculatedParcelValue() {

        Double calculatedFinalValue;

        Double TotalValue = Double.parseDouble(this._value);
        Double intInterest = Double.parseDouble(this._interest);
        int ParcelsNumber = Integer.parseInt(this._parcelsnumber);
        int FirstMonth = Integer.parseInt(this._first_month);

        if (TotalValue == 0 || intInterest == 0 || ParcelsNumber == 0 ) {
            return 0;
        }

        intInterest = intInterest/100;
        calculatedFinalValue = TotalValue * (1 + (intInterest * ParcelsNumber));
        Double ParcelValue = calculatedFinalValue / ParcelsNumber;

        ParcelValue = round(ParcelValue, 2);

        return ParcelValue;

    }

    // para arredondar com 2 casas decimais (centavos) o valor da parcela
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
