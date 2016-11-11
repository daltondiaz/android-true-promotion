package br.com.true_promotion.views.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import br.com.true_promotion.domain.Product;
import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by Dalton on 19/10/2016.
 */

public class ProductAdapter extends RealmBaseAdapter<Product> implements ListAdapter {

    private Realm realm;

    public ProductAdapter(Context context, Realm realm,RealmResults<Product> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
        this.realm = realm;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
