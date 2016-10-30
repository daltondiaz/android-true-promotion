package br.com.true_promotion;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import br.com.true_promotion.adapter.DynamicTabViewPageAdapter;
import br.com.true_promotion.fragments.PromotionsFragment;

public class MainActivity extends AppCompatActivity {


    private DynamicTabViewPageAdapter viewPagerAdapter;
    protected Toolbar toolbar;
    protected TabLayout tabLayout;
    protected ViewPager mViewPager;
    private String[] titlesArray;

    private int[]tabIcons = {R.drawable.ic_view_list,R.drawable.ic_new_promotion_24dp};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        titlesArray = getResources().getStringArray(R.array.tab_titles);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        setupTabs(mViewPager);

    }

    /**
     * Setup Tabs
     * @param viewPager
     */
    public void setupTabs(final ViewPager viewPager){
        viewPagerAdapter = new DynamicTabViewPageAdapter(getSupportFragmentManager());
        if(viewPager != null){
            toolbar.setTitle(titlesArray[0]);

            //Set Fragments
            viewPagerAdapter.addFragment(new PromotionsFragment(),titlesArray[0]);

            viewPager.setAdapter(viewPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    tab.getCustomView().setAlpha(1.0f);
                    if(tab != null){
                        mViewPager.setCurrentItem(tab.getPosition());
                        toolbar.setTitle(titlesArray[tab.getPosition()]);
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    tab.getCustomView().setAlpha(0.5f);

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                    tab.getCustomView().setAlpha(1.0f);
                }
            });

        }
    }



    public void signUP(View view){
        startActivity(new Intent(this,PromotionsActivity.class));
    }
}
