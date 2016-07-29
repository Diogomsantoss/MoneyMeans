package diogosantos.moneymeans.domain.classes;
import  diogosantos.moneymeans.domain.domain.dao.*;
import  diogosantos.moneymeans.domain.Activities.*;
import  diogosantos.moneymeans.domain.Adapters.*;
import  diogosantos.moneymeans.domain.Fragments.*;



public class Balance {

    private String _value;
    private int _id;

    public Balance(int id, String value) {
        _id = id;
        _value = value;

    }

    public int getId() { return _id; }

    public String getValue() {
        return _value;
    }

}
