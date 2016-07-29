package diogosantos.moneymeans.domain.Services;

import  diogosantos.moneymeans.domain.Activities.*;
import  diogosantos.moneymeans.domain.Adapters.*;
import  diogosantos.moneymeans.domain.classes.*;
import  diogosantos.moneymeans.domain.domain.dao.*;
import  diogosantos.moneymeans.domain.Fragments.*;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.IntentService;
import android.support.v4.app.NotificationCompat;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import moneymeans.R;


/**
 * Created by moura_000 on 11/22/2015.
 */
public class UpdateServiceManager extends IntentService {

    public static final String PARAM_IN_MSG = "imsg";
    public static final String PARAM_OUT_MSG = "omsg";

    private final int UPDATE_INTERVAL = 60 * 1000;
    private Timer timer = new Timer();
    private static final int NOTIFICATION_EX = 1;
    private NotificationManager notificationManager;
    MainActivity not;
    DatabaseHandler dbHandler;
    ArrayList<FinancingProposal> financingProposals = new ArrayList<FinancingProposal>();


    boolean AvaliableToCalclulate = false;

    public UpdateServiceManager() {
        super("UpdateServiceManager");
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }


    protected void onHandleIntent(Intent intent) {



    }

    @Override
    public void onCreate() {
        // code to execute when the service is first created
        super.onCreate();

    }


    @Override
    public IBinder onBind(Intent intent) {
        // I guess that you don't want to bind to the service
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
      //  Toast.makeText(this, "Service is running", Toast.LENGTH_SHORT).show();

        Log.i("MyService", "Service Started.");

        getTotalObligations();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service is going down", Toast.LENGTH_SHORT).show();
    }

    public void createNotification(View view) {

                NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Saldo menor que obrigações")
                        .setContentText("Seu saldo é menor que as obrigações que você tem pra pagar");
        int NOTIFICATION_ID = 12345;

        Intent targetIntent = new Intent(this, CategoryChartActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(NOTIFICATION_ID, builder.build());

    }

    public double getTotalObligations() {
        int i;

        dbHandler = DatabaseHandler.getInstance(getApplicationContext());

        ArrayList<FinancingProposal> ProposalsOfThisMonth = new ArrayList<FinancingProposal>();

        Calendar c = Calendar.getInstance();

        final ExpenseListFragment fragment_expenses_list = ExpenseListFragment.newInstance();

        double TotalObligationsValue = 0;

        financingProposals = dbHandler.getAllFinancingProposals();

        for (i = 0; i < dbHandler.getFinancingsCount() ; i++ ) {
            if (( c.get(Calendar.MONTH) + 1) == Integer.parseInt(financingProposals.get(i).getFirstMonth()))
            {
                ProposalsOfThisMonth.add(financingProposals.get(i));
            }
        }

        for (i = 0; i < ProposalsOfThisMonth.size() ; i++ ) {

            TotalObligationsValue = TotalObligationsValue + ProposalsOfThisMonth.get(i).getCalculatedParcelValue();
       //     Toast.makeText(this, String.valueOf(TotalObligationsValue), Toast.LENGTH_SHORT).show();

            if (i == (ProposalsOfThisMonth.size() - 1 )) {
                AvaliableToCalclulate = true;
            }

        }



        if (fragment_expenses_list.getTheBalance() < TotalObligationsValue && AvaliableToCalclulate == true)
        {
     //       Toast.makeText(this, String.valueOf(fragment_expenses_list.getTheBalance()), Toast.LENGTH_SHORT).show();
            createNotification(null);
        }


        return TotalObligationsValue;

    }

}