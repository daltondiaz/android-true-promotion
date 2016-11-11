package br.com.true_promotion.views.widgets;

import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Dalton on 11/11/2016.
 */

public class BadgeViewLayout {

    private RelativeLayout rootLayout;
    private ImageView badgeImage;

    public BadgeViewLayout(RelativeLayout relativeLayout){
        this.rootLayout = relativeLayout;
    }

    public void initUi(int idImage){

        if(rootLayout != null){
            this.badgeImage = (ImageView) rootLayout.findViewById(idImage);
        }

    }

    public ImageView getBadgeImage() {
        return badgeImage;
    }
}
