package com.example.ursakter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Ferm on 2015-09-01.
 */
public class ExcusePagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Excuse> excuses;

    public ExcusePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new ExcuseFragment().newInstance(excuses.get(position));
    }

    @Override
    public int getCount() {
        return excuses.size();
    }

    public void setExcuses(ArrayList<Excuse> excuses) {
        this.excuses = excuses;
    }
}
