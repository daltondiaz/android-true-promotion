package br.com.true_promotion.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import br.com.true_promotion.R;
import br.com.true_promotion.adapter.PromotionsAdapter;
import br.com.true_promotion.domain.Promotion;
import br.com.true_promotion.interfaces.RecyclerViewOnClickListenerHack;
import io.realm.Realm;

/**
 * Created by Dalton on 19/10/2016.
 */

public class PromotionsFragment extends Fragment implements RecyclerViewOnClickListenerHack {

    private RecyclerView promotionRV;
    private List<Promotion> promotions;
    private Realm realm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_promotion, container, false);

        promotionRV = (RecyclerView) view.findViewById(R.id.rv_list);
        promotionRV.setHasFixedSize(true);
        promotionRV.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager =
                        (LinearLayoutManager) promotionRV.getLayoutManager();

                PromotionsAdapter adapter = (PromotionsAdapter) promotionRV.getAdapter();
                if(promotions.size() == layoutManager.findLastCompletelyVisibleItemPosition() + 1){
                    //GetMoreTen
                }
            }


        });

        promotionRV.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(),promotionRV,this));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        promotionRV.setLayoutManager(layoutManager);

        //TODO get 10 item of realm
        try{
            realm = Realm.getDefaultInstance();
            promotions = realm.where(Promotion.class).findAll();

        }catch (Exception e){
            e.printStackTrace();
        }

        PromotionsAdapter adapter = new PromotionsAdapter(getActivity(),promotions);
        promotionRV.setAdapter(adapter);


        return view;
    }

    @Override
    public void onDestroy() {
        realm.removeAllChangeListeners();
        realm.close();
        super.onDestroy();
    }

    @Override
    public void onClickListener(View view, int position) {
        Toast.makeText(getActivity(), "onClickListener(): "+position, Toast.LENGTH_SHORT).show();
    }

    public static class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener{

        private Context context;
        private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;
        private GestureDetector gestureDetector;


        public RecyclerViewTouchListener(Context context, final RecyclerView rv, RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack) {
            this.context = context;
            this.recyclerViewOnClickListenerHack = recyclerViewOnClickListenerHack;

            this.gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){

            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            gestureDetector.onTouchEvent(e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
