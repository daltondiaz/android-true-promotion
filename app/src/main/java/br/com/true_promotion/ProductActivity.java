package br.com.true_promotion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.true_promotion.domain.Product;
import io.realm.Realm;
import io.realm.RealmResults;

import static android.widget.Toast.makeText;

public class ProductActivity extends AppCompatActivity {

    private Realm realm;
    private RealmResults<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_product);

        realm = Realm.getDefaultInstance();


    }

    public void saveProduct(View view){
        try{
            RealmResults<Product> productsAux = realm.where(Product.class).findAllSorted("id",false);
            long id = 1;

            if(productsAux != null && productsAux.size()> 0){
                id = productsAux.get(0).getId() + 1;
            }

            Product  product = new Product();
            product.setId(id);
            EditText etNome = (EditText) findViewById(R.id.et_product_name);
            product.setName(etNome.getText().toString());
            EditText etDescription = (EditText) findViewById(R.id.et_product_description);
            product.setDescription(etDescription.getText().toString());
            EditText etMeasure = (EditText) findViewById(R.id.et_product_measure);
            product.setMeasure(Float.parseFloat(etMeasure.getText().toString()));
            EditText etTypeProduct = (EditText) findViewById(R.id.et_product_type);
            product.setTypeProduct(etTypeProduct.getText().toString());

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(product);
            realm.commitTransaction();

            makeText(ProductActivity.this, "Produto cadastrado! ", Toast.LENGTH_SHORT).show();
            finish();

        }catch(Exception e){
            e.printStackTrace();
            makeText(ProductActivity.this, "Falhou ao cadastrar produto!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        realm.removeAllChangeListeners();
        realm.close();
        super.onDestroy();
    }
}
