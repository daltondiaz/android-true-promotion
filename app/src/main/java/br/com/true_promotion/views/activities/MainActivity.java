package br.com.true_promotion.views.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import br.com.true_promotion.R;
import br.com.true_promotion.views.adapters.DynamicTabViewPageAdapter;
import br.com.true_promotion.views.fragments.NewProductFragment;
import br.com.true_promotion.views.fragments.NewPromotionFragment;
import br.com.true_promotion.views.fragments.PromotionsFragment;
import br.com.true_promotion.views.widgets.BadgeViewLayout;

public class MainActivity extends AppCompatActivity {


    private DynamicTabViewPageAdapter viewPagerAdapter;
    protected Toolbar toolbar;
    protected TabLayout tabLayout;
    protected ViewPager mViewPager;
    private String[] titlesArray;
    protected BadgeViewLayout notificationBadge;

    public static final int[]tabIcons = {
            R.drawable.ic_list_promotion_24dp,
            R.drawable.ic_add_tag_24dp,
            R.drawable.ic_add_product_24dp
    };

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

            try{
                toolbar.setTitle(titlesArray[0]);

                //Set Fragments
                viewPagerAdapter.addFragment(new PromotionsFragment(),titlesArray[0]);
                viewPagerAdapter.addFragment(new NewPromotionFragment(),titlesArray[1]);
                viewPagerAdapter.addFragment(new NewProductFragment(),titlesArray[2]);

                viewPager.setAdapter(viewPagerAdapter);
                tabLayout.setupWithViewPager(viewPager);
                tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
        //                tab.getCustomView().setAlpha(1.0f);
                        if(tab != null){
                            tab.getCustomView().setAlpha(1.0f);
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

                for (int i=0; i<tabIcons.length; i++){
                    RelativeLayout mTabLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.tabbadge,null,false);
                    BadgeViewLayout badgeViewLayout = new BadgeViewLayout(mTabLayout);
                    badgeViewLayout.initUi(R.id.badgeImage);
                    Picasso.with(this).load(tabIcons[i]).into(badgeViewLayout.getBadgeImage());

                    tabLayout.getTabAt(i).setCustomView(mTabLayout);

                    if(i != 0){
                        tabLayout.getTabAt(i).getCustomView().setAlpha(0.5f);
                    }

                }

            }catch (Exception e){
                Log.e("DEBUG = ", e.getMessage());
            }


        }
    }

    public void signUP(View view){
        startActivity(new Intent(this,PromotionsActivity.class));
    }
}
