package br.com.true_promotion.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.Date;

import br.com.true_promotion.ProductActivity;
import br.com.true_promotion.R;
import br.com.true_promotion.adapter.ProductSpinnerAdapter;
import br.com.true_promotion.domain.Product;
import br.com.true_promotion.domain.Promotion;
import br.com.true_promotion.utils.ApplicationUtilities;
import io.realm.Realm;
import io.realm.RealmResults;

import static android.app.Activity.RESULT_OK;
import static android.widget.Toast.makeText;

/**
 * Created by Dalton on 31/10/2016.
 */

public class NewProductFragment extends Fragment {

    private Realm realm;
    private RealmResults<Product> products;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_product, container, false);

        realm = Realm.getDefaultInstance();

        Button btnSave = (Button) view.findViewById(R.id.btn_save_product);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewProductFragment.this.saveProduct(v);
            }
        });

        return view;
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
            EditText etNome = (EditText) getActivity().findViewById(R.id.et_product_name);
            product.setName(etNome.getText().toString());
            EditText etDescription = (EditText) getActivity().findViewById(R.id.et_product_description);
            product.setDescription(etDescription.getText().toString());
            EditText etMeasure = (EditText) getActivity().findViewById(R.id.et_product_measure);
            product.setMeasure(Float.parseFloat(etMeasure.getText().toString()));
            EditText etTypeProduct = (EditText) getActivity().findViewById(R.id.et_product_type);
            product.setTypeProduct(etTypeProduct.getText().toString());

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(product);
            realm.commitTransaction();

            makeText(getActivity(), "Produto cadastrado! ", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            e.printStackTrace();
            makeText(getActivity(), "Falhou ao cadastrar produto!", Toast.LENGTH_SHORT).show();
        }
    }

}
