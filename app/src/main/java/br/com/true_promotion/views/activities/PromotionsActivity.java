package br.com.true_promotion.views.activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.com.true_promotion.R;
import br.com.true_promotion.domain.Promotion;
import br.com.true_promotion.views.fragments.PromotionsFragment;
import io.realm.Realm;
import io.realm.RealmResults;

public class PromotionsActivity extends AppCompatActivity {

    private Realm realm;
    private RealmResults<Promotion> promotions;
    private Promotion promotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotions);

        // FRAGMENT
        PromotionsFragment frag = (PromotionsFragment) getSupportFragmentManager().findFragmentByTag("mainFrag");
        if(frag == null) {
            frag = new PromotionsFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container, frag, "mainFrag");
            ft.commit();
        }
    }

    public void newPromotion(View view){

        startActivity(new Intent(this,AddUpdatePromotionActivity.class));


    }
}
