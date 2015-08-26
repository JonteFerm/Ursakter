package com.example.ursakter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Ferm on 2015-08-25.
 */
public class LibraryPagerAdapter extends FragmentStatePagerAdapter{
    private ArrayList<Excuse> excusesInCategory;

    public LibraryPagerAdapter(FragmentManager fm) {
        super(fm);
        excusesInCategory = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return new ExcuseFragment().newInstance(excusesInCategory.get(position));
    }

    @Override
    public int getCount() {
        return excusesInCategory.size();
    }

    public void setExcusesInCategory(ArrayList<Excuse> excusesInCategory) {
        this.excusesInCategory = excusesInCategory;
    }


}
