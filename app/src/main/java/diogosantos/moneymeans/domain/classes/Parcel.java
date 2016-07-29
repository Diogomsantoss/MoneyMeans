package diogosantos.moneymeans.domain.classes;
import  diogosantos.moneymeans.domain.domain.dao.*;
import  diogosantos.moneymeans.domain.Activities.*;
import  diogosantos.moneymeans.domain.Adapters.*;
import  diogosantos.moneymeans.domain.Fragments.*;

import  diogosantos.moneymeans.domain.*;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by moura_000 on 11/6/2015.
 */
public class Parcel {

    private String value;
    private Calendar date;
    private int index;

    public Parcel(int index, String value, Calendar date) {
        this.index = index;
        this.value = value;
        this.date = date;

    }

    public int getIndex() { return index; }

    public String getValue() { return value; }

    public Calendar getDate() { return date;}

    public void setIndex( int index) { this.index = index; }

    public void setValue( String value) { this.value = value; }

    public void setDate( Calendar date) { this.date = date; }

}
