package diogosantos.moneymeans.domain.Adapters;
import  diogosantos.moneymeans.domain.classes.*;
import  diogosantos.moneymeans.domain.domain.dao.*;
import  diogosantos.moneymeans.domain.Fragments.*;
import  diogosantos.moneymeans.domain.Activities.*;

import  diogosantos.moneymeans.domain.*;

import java.util.List;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;

/**
 * Created by moura_000 on 10/7/2015.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {


    List<Fragment> listFragments;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> listFragments) {
        super(fm);
        this.listFragments = listFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return listFragments.get(position);
    }

    @Override
    public int getCount() {
        return listFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Entrada de transação";
        }

        if (position == 1) {
            return "Listagem de transações";

        }
        return "tab";
    }

}
