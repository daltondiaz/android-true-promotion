package br.com.true_promotion;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import br.com.true_promotion.domain.Product;
import br.com.true_promotion.domain.Promotion;
import br.com.true_promotion.fragments.PromotionsFragment;
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

    public void addPromotion(View view){

        Promotion p = new Promotion();
        String label = new String();
        realm = Realm.getDefaultInstance();
        promotions = realm.where( Promotion.class ).findAll();

        Product product = realm.where(Product.class).findFirst();
        if(product == null || product.getId() == 0){
            product = new Product();
            product.setId(1l);
            product.setName("Arroz");
            product.setDescription("Arroz Camil 1 kg");
            product.setMeasure(1);
            product.setType_product("Alimento");
            try{
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(product);
                realm.commitTransaction();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

        if( p.getId() == 0 ){
            promotions.sort( "id", RealmResults.SORT_ORDER_DESCENDING );
            long id = promotions.size() == 0 ? 1 : promotions.get(0).getId() + 1;
            p.setId( id );
            label = "adicionada";
        }

        try{
            realm.beginTransaction();
            p.setPrice(2 * p.getId());
            p.setProduct(product);
            p.setDateCreate(new Date());
            realm.copyToRealmOrUpdate(p);
            realm.commitTransaction();

            Toast.makeText(PromotionsActivity.this, "Promotion "+label, Toast.LENGTH_SHORT).show();
            finish();
        }
        catch(Exception e){
            e.printStackTrace();
            Toast.makeText(PromotionsActivity.this, "Falhou!", Toast.LENGTH_SHORT).show();
        }

    }
}
