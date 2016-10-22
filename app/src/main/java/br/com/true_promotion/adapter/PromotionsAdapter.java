package br.com.true_promotion.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.true_promotion.R;
import br.com.true_promotion.domain.Promotion;
import br.com.true_promotion.interfaces.RecyclerViewOnClickListenerHack;
import io.realm.Realm;

/**
 *
 *
 * Created by Dalton on 19/10/2016.
 */

public class PromotionsAdapter extends RecyclerView.Adapter<PromotionsAdapter.MyViewHolder> {

    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private LayoutInflater layoutInflater;
    private List<Promotion> promotions;
    private Context promotionContext;

    public PromotionsAdapter(Context context,List<Promotion> promotions) {
        this.promotionContext = context;
        this.promotions = promotions;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_promotion_card, parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.ivPromotion.setImageResource(R.drawable.example_product);
        holder.tvProduct.setText(promotions.get(position).getProduct().getName());
        holder.tvPrice.setText(String.valueOf(promotions.get(position).getPrice()));

    }

    @Override
    public int getItemCount() {
        return promotions.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }

    public void addPromotionItem(Promotion promotion, int position){
        //TODO insert in realm new  promotion
        promotions.add(promotion);
        notifyItemInserted(position);
    }

    public void removerPromotion(int position){
        promotions.remove(position);
        notifyItemRemoved(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView ivPromotion;
        public TextView tvProduct;
        public TextView tvPrice;

        public MyViewHolder(View itemView) {
            super(itemView);

            ivPromotion = (ImageView) itemView.findViewById(R.id.iv_promotion);
            tvProduct = (TextView) itemView.findViewById(R.id.tv_product);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mRecyclerViewOnClickListenerHack != null){
                mRecyclerViewOnClickListenerHack.onClickListener(v, getPosition());
            }
        }
    }
}
