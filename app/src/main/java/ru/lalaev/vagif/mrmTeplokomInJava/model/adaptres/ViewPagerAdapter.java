package ru.lalaev.vagif.mrmTeplokomInJava.model.adaptres;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList;
    private final List<String> mFragmentTitleList;
    private final List<View> mFragmentsViewsList;



    @Override
    public Fragment getItem(int position) {
        return this.mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return this.mFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return (CharSequence) this.mFragmentTitleList.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    public void addFragmentView(View fragmentView) {
        mFragmentsViewsList.add(fragmentView);
    }

    public List<View> getmFragmentsViewsList() {
        return mFragmentsViewsList;
    }

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        this.mFragmentList = new ArrayList<>();
        this.mFragmentTitleList = new ArrayList<>();
        this.mFragmentsViewsList = new ArrayList<>();
    }
}
