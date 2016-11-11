package br.com.true_promotion.views.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import br.com.true_promotion.R;
import br.com.true_promotion.domain.Product;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by Dalton on 20/10/2016.
 */

public class ProductSpinnerAdapter extends RealmBaseAdapter<Product> implements ListAdapter {


    public ProductSpinnerAdapter(Context context, RealmResults<Product> realmResults) {
        super(context, realmResults, false);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CustomViewHolder holder;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_product,parent,false);
            holder = new CustomViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_item_product);
            convertView.setTag(holder);
        }else{
            holder = (CustomViewHolder) convertView.getTag();
        }

        final Product p = realmResults.get(position);
        holder.tvName.setText(p.getName());

        return convertView;
    }

    public static class CustomViewHolder{
        TextView tvName;
    }
}
