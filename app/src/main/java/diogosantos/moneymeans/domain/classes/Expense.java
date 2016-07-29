package diogosantos.moneymeans.domain.classes;
import  diogosantos.moneymeans.domain.domain.dao.*;
import  diogosantos.moneymeans.domain.Activities.*;
import  diogosantos.moneymeans.domain.Adapters.*;
import  diogosantos.moneymeans.domain.Fragments.*;

public class Expense {

    private String _name, _value, _type, _category, _date;
    private int _id;

    public Expense(int id, String name, String value, String type,  String category, String date) {
        _id = id;
        _name = name;
        _value = value;
        _type = type;
        _category = category;
        _date = date;


    }

    public int getId() { return _id; }

    public String getName() {
       return _name;
    }

    public String getValue() { return _value; }

    public String getType() { return _type; }

    public String getCategory() { return _category; }

    public String getDate() { return _date; }


}
