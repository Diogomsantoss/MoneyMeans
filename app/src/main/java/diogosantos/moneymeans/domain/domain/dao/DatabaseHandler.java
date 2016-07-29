package diogosantos.moneymeans.domain.domain.dao;
import diogosantos.moneymeans.domain.Adapters.*;
import diogosantos.moneymeans.domain.Activities.*;
import diogosantos.moneymeans.domain.classes.*;
import diogosantos.moneymeans.domain.Fragments.*;

import  diogosantos.moneymeans.domain.*;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {


    private static DatabaseHandler sInstance;

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "expenseManager",
    TABLE_EXPENSES = "expenses",
    KEY_ID = "id",
    KEY_NAME = "name",
    KEY_VALUE_EXPENSE = "expensevalue",
    KEY_EXPENSE_TYPE = "type",
    KEY_EXPENSE_CATEGORY = "category",
    KEY_EXPENSE_DATE = "date";

    private static final String DATABASE_FINANCING = "financingManager",
    TABLE_FINANCING = "financesproposals",
    KEY_FINANCING_ID = "financingid",
    KEY_FINANCING_VALUE = "financingvalue",
    KEY_INTEREST_PERCENTAGE = "financingpercentage",
    KEY_PROPOSAL_PARCELS_NUMBER = "financingparcelsnumber",
    KEY_PROPOSAL_FIRST_MONTH = "firstmonthoftheparcel",
    KEY_INTEREST_TYPE = "interesttype";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHandler getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_EXPENSES + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_VALUE_EXPENSE + " TEXT," + KEY_EXPENSE_TYPE + " TEXT," + KEY_EXPENSE_CATEGORY + " TEXT," + KEY_EXPENSE_DATE + " TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_FINANCING + "(" + KEY_FINANCING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_FINANCING_VALUE + " TEXT," + KEY_INTEREST_PERCENTAGE + " TEXT," + KEY_PROPOSAL_PARCELS_NUMBER + " TEXT," + KEY_PROPOSAL_FIRST_MONTH + " TEXT," + KEY_INTEREST_TYPE + " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FINANCING);

        onCreate(db);
    }

    public void createExpense(Expense expense) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, expense.getName());
        values.put(KEY_VALUE_EXPENSE, expense.getValue());
        values.put(KEY_EXPENSE_TYPE, expense.getType());
        values.put(KEY_EXPENSE_CATEGORY, expense.getCategory());
        values.put(KEY_EXPENSE_DATE, expense.getDate());

        db.insert(TABLE_EXPENSES, null, values);

    }


    public void createFinancingProposal(FinancingProposal finance) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_FINANCING_VALUE, finance.getValue());
        values.put(KEY_INTEREST_PERCENTAGE, finance.getInterest());
        values.put(KEY_PROPOSAL_PARCELS_NUMBER, finance.getParcelsNumber());
        values.put(KEY_PROPOSAL_FIRST_MONTH, finance.getFirstMonth());
        values.put(KEY_INTEREST_TYPE, finance.getInterestType());

        db.insert(TABLE_FINANCING, null, values);

    }



    public Expense getExpense(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_EXPENSES, new String[] { KEY_ID, KEY_NAME, KEY_VALUE_EXPENSE, KEY_EXPENSE_TYPE}, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null );

        if (cursor != null)
            cursor.moveToFirst();

        Expense expense = new Expense(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));

        cursor.close();
        return expense;
    }

    public FinancingProposal getFinancingProposal(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_EXPENSES, new String[] { KEY_FINANCING_ID, KEY_FINANCING_VALUE, KEY_INTEREST_PERCENTAGE, KEY_PROPOSAL_PARCELS_NUMBER, KEY_PROPOSAL_FIRST_MONTH, KEY_INTEREST_TYPE}, KEY_FINANCING_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null );

        if (cursor != null)
            cursor.moveToFirst();

        FinancingProposal finance = new FinancingProposal(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));

        cursor.close();
        return finance;
    }




    public void deleteExpense(Expense expense) {
        SQLiteDatabase db = getWritableDatabase();

        if (expense.getId() > 1 || expense.getId() < getExpensesCount()){

            db.delete(TABLE_EXPENSES, KEY_ID + "=?", new String[] { String.valueOf(expense.getId()) });
        } else {

            db.delete(TABLE_EXPENSES, KEY_ID + "=?", new String[] { String.valueOf(expense.getId()) });
            //db.execSQL("delete from "+ TABLE_EXPENSES);
            //db.execSQL("TRUNCATE table" + TABLE_EXPENSES);

        }

        if (expense.getId() == 0 ) {

        }


    }


    public void deleteFinanceProposal(FinancingProposal finance) {
        SQLiteDatabase db = getWritableDatabase();

        if (finance.getId() > 1 || finance.getId() < getFinancingsCount()){

            db.delete(TABLE_FINANCING, KEY_FINANCING_ID + "=?", new String[] { String.valueOf(finance.getId()) });
        } else {

            db.delete(TABLE_FINANCING, KEY_FINANCING_ID + "=?", new String[] { String.valueOf(finance.getId()) });
            //db.execSQL("delete from "+ TABLE_EXPENSES);
            //db.execSQL("TRUNCATE table" + TABLE_EXPENSES);

        }

        if (finance.getId() == 0 ) {

        }


    }

    public int getExpensesCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EXPENSES, null);
        int count = 0;
        try {
            if (cursor.moveToFirst()) {
                count = cursor.getCount();
            }
            return count;
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }



    public int getFinancingsCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FINANCING, null);
        int count = 0;
        try {
            if (cursor.moveToFirst()) {
                count = cursor.getCount();
            }
            return count;
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public int updateExpense(Expense expense) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, expense.getName());
        values.put(KEY_VALUE_EXPENSE, expense.getValue());
        values.put(KEY_EXPENSE_TYPE, expense.getType());
        values.put(KEY_EXPENSE_CATEGORY, expense.getCategory());
        values.put(KEY_EXPENSE_DATE, expense.getDate());

        int rowsAffected = db.update(TABLE_EXPENSES, values, KEY_ID + "=?", new String[] { String.valueOf(expense.getId()) });
        db.close();

        return rowsAffected;
    }



    public ArrayList<Expense> getAllExpenses() {
        ArrayList<Expense> expenses = new ArrayList<Expense>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EXPENSES, null);

        if (cursor.moveToFirst()) {
            do {
                expenses.add(new Expense(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return expenses;
    }


    public ArrayList<FinancingProposal> getAllFinancingProposals() {
        ArrayList<FinancingProposal> financeProposals = new ArrayList<FinancingProposal>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FINANCING, null);

        if (cursor.moveToFirst()) {
            do {
                financeProposals.add(new FinancingProposal(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return financeProposals;
    }


}
