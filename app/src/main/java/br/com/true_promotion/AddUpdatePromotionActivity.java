package br.com.true_promotion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import br.com.true_promotion.adapter.ProductSpinnerAdapter;
import br.com.true_promotion.domain.Product;
import br.com.true_promotion.domain.Promotion;
import io.realm.Realm;
import io.realm.RealmResults;

public class AddUpdatePromotionActivity extends AppCompatActivity {

    private Realm realm;
    private RealmResults<Product> products;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_promotion);

        realm = realm.getDefaultInstance();
        products = realm.where(Product.class).findAll();

        Spinner spinner = (Spinner) findViewById(R.id.sp_products);
        spinner.setAdapter(new ProductSpinnerAdapter(this,products));

    }

    @Override
    protected void onDestroy() {
        realm.removeAllChangeListeners();
        realm.close();
        super.onDestroy();
    }

    public void createProductTemporary(View view){
        try{
            RealmResults<Product> productsAux = realm.where(Product.class).findAllSorted("id",false);
            long id = 1;

            if(productsAux != null && productsAux.size()> 0){
                id = productsAux.get(0).getId() + 1;
            }
            Product  product = new Product();
            product.setId(id);
            product.setName("Arroz");
            product.setDescription("Arroz Camil 1 kg");
            product.setMeasure(1);
            product.setTypeProduct("Alimento");

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(product);
            realm.commitTransaction();

            Toast.makeText(AddUpdatePromotionActivity.this, "Produto cadastrado! ", Toast.LENGTH_SHORT).show();
            finish();

        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(AddUpdatePromotionActivity.this, "Falhou ao cadastrar produto!", Toast.LENGTH_SHORT).show();
        }
    }

    public void savePromotion(View view){

        RealmResults<Promotion> promotions;
        Promotion promotion = new Promotion();
        String label = new String();
        realm = Realm.getDefaultInstance();
        promotions = realm.where( Promotion.class ).findAll();

        if( promotion.getId() == 0 ){
            promotions.sort( "id", RealmResults.SORT_ORDER_DESCENDING );
            long id = promotions.size() == 0 ? 1 : promotions.get(0).getId() + 1;
            promotion.setId( id );
            label = "adicionada";
        }

        try{

            EditText etPrice = (EditText) findViewById(R.id.et_price);
            float price = 0;
            if(etPrice != null &&  !etPrice.toString().equals("")){
                Log.d("NEW PROMOTION ",etPrice.getText().toString());
                price =  Float.parseFloat(etPrice.getText().toString());
            }

            realm.beginTransaction();
            promotion.setPrice(price);
            promotion.setDateCreate(new Date());
            realm.copyToRealmOrUpdate(promotion);
            realm.commitTransaction();

            promotion = realm.where(Promotion.class).equalTo("id",promotion.getId()).findFirst();

            realm.beginTransaction();
            Product product = realm.copyToRealmOrUpdate(getProduct(view,products));
            promotion.setProduct(product);
            realm.commitTransaction();
            Toast.makeText(AddUpdatePromotionActivity.this, "Promoção cadastrada! "+label, Toast.LENGTH_SHORT).show();
            finish();
        }
        catch(Exception e){
            e.printStackTrace();
            Toast.makeText(AddUpdatePromotionActivity.this, "Falhou ao cadastrar promoção!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Get a Product from item of Spinner who was selected.
     * @param view
     * @param products List of Products which created a Spinner itens
     * @return Product selected
     */
    public Product getProduct(View view, RealmResults<Product> products){
        RelativeLayout rlParent = (RelativeLayout) view.getParent().getParent();
        for(int i = 0; i<rlParent.getChildCount(); i++){

            if(rlParent.getChildAt(i) instanceof LinearLayout){
                LinearLayout llChield = (LinearLayout) rlParent.getChildAt(i);
                if(llChield.getChildAt(0) instanceof Spinner){
                    Spinner spProduct = (Spinner) llChield.getChildAt(0).findViewById(R.id.sp_products);
                    Product product = new Product();
                    product.setId(products.get(spProduct.getSelectedItemPosition()).getId());
                    product.setName(products.get(spProduct.getSelectedItemPosition()).getName());
                    product.setTypeProduct(products.get(spProduct.getSelectedItemPosition()).getTypeProduct());
                    product.setMeasure(products.get(spProduct.getSelectedItemPosition()).getMeasure());
                    product.setDescription(products.get(spProduct.getSelectedItemPosition()).getDescription());
                    return product;
                }

            }

        }
        return null;
    }
}
