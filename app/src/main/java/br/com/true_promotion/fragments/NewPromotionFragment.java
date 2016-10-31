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

import br.com.true_promotion.AddUpdatePromotionActivity;
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

public class NewPromotionFragment extends Fragment {

    private Realm realm;
    private RealmResults<Product> products;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_promotion, container, false);

        realm = realm.getDefaultInstance();
        products = realm.where(Product.class).findAll();

        Spinner spinner = (Spinner) view.findViewById(R.id.sp_products);
        spinner.setAdapter(new ProductSpinnerAdapter(getActivity(), products));

        ImageView ivCamera = (ImageView) view.findViewById(R.id.iv_Camera);
        ivCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File image = ApplicationUtilities.getOutputMediaFile(
                        getActivity(), false);
                Uri fileUri = Uri.fromFile(image);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            }
        });

        Button btnSave = (Button) view.findViewById(R.id.btn_add_promotion);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewPromotionFragment.this.savePromotion(v);
            }
        });

        return view;
    }

    /**
     * Save a new Promotion
     * @param v
     */
    private void savePromotion(View v) {
        RealmResults<Promotion> promotions;
        Promotion promotion = new Promotion();
        String label = new String();
        realm = Realm.getDefaultInstance();
        promotions = realm.where(Promotion.class).findAll();

        if (promotion.getId() == 0) {
            promotions.sort("id", RealmResults.SORT_ORDER_DESCENDING);
            long id = promotions.size() == 0 ? 1 : promotions.get(0).getId() + 1;
            promotion.setId(id);
            label = "adicionada";
        }

        try {

            EditText etPrice = (EditText) v.findViewById(R.id.et_price);
            float price = 0;
            if (etPrice != null && !etPrice.toString().equals("")) {
                Log.d("NEW PROMOTION ", etPrice.getText().toString());
                price = Float.parseFloat(etPrice.getText().toString());
            }

            realm.beginTransaction();
            promotion.setPrice(price);
            promotion.setDateCreate(new Date());
            realm.copyToRealmOrUpdate(promotion);
            realm.commitTransaction();

            promotion = realm.where(Promotion.class).equalTo("id", promotion.getId()).findFirst();

            realm.beginTransaction();
            Product product = realm.copyToRealmOrUpdate(getProduct(v, products));
            promotion.setProduct(product);
            realm.commitTransaction();
            makeText(getActivity(), "Promoção cadastrada! " + label, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            makeText(getActivity(), "Falhou ao cadastrar promoção!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        realm.removeAllChangeListeners();
        realm.close();
        super.onDestroy();
    }

    /**
     * Get a Product from item of Spinner who was selected.
     *
     * @param view
     * @param products List of Products which created a Spinner itens
     * @return Product selected
     */
    public Product getProduct(View view, RealmResults<Product> products) {
        RelativeLayout rlParent = (RelativeLayout) view.getParent().getParent();
        for (int i = 0; i < rlParent.getChildCount(); i++) {

            if (rlParent.getChildAt(i) instanceof RelativeLayout) {
                RelativeLayout rlChield = (RelativeLayout) rlParent.getChildAt(i);

                for (int j = 0; j < rlChield.getChildCount(); j++) {
                    if (rlChield.getChildAt(j) instanceof Spinner) {
                        Spinner spProduct = (Spinner) rlChield.getChildAt(j).findViewById(R.id.sp_products);
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

        }
        return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1888) {
            File mImage = ApplicationUtilities.getOutputMediaFile(getActivity(), false);
            File mImageCropped = ApplicationUtilities.getOutputMediaFile(getActivity(), true);
            callCrop(Uri.fromFile(mImage), Uri.fromFile(mImageCropped));
        } else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }


    public void callCrop(Uri sourceUri, Uri destinationUri) {
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        UCrop.of(sourceUri, destinationUri)
                .withAspectRatio(4, 3)
                .withOptions(options)
                .start(getActivity());
    }
}
