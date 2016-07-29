package diogosantos.moneymeans.domain.Activities;
import  diogosantos.moneymeans.domain.Adapters.*;
import diogosantos.moneymeans.domain.Services.UpdateServiceManager;
import  diogosantos.moneymeans.domain.classes.*;
import  diogosantos.moneymeans.domain.domain.dao.*;
import  diogosantos.moneymeans.domain.Fragments.*;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import diogosantos.moneymeans.domain.Fragments.ExpenseFragment;
import moneymeans.R;

public class splashscreen extends Activity {

    //Set waktu lama splashscreen
    private static int splashInterval = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splashscreen);

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent i = new Intent(splashscreen.this, MainActivity.class);
                startActivity(i);


                //jeda selesai Splashscreen
                this.finish();
            }

            private void finish() {
                // TODO Auto-generated method stub

            }
        }, splashInterval);

    };

}

