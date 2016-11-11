package br.com.true_promotion.views.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dalton on 29/10/2016.
 */

public class DynamicTabViewPageAdapter extends FragmentPagerAdapter {


    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    public DynamicTabViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments == null ? null : fragments.get(position);
    }

    @Override
    public int getCount() {

        return fragments == null ? 0 : fragments.size();
    }

    public void addFragment(Fragment fragment, String title){
        fragments.add(fragment);
        titles.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.titles.get(position);
    }
}
