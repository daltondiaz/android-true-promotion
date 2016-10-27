package br.com.true_promotion.utils;

import android.app.Activity;
import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dalton on 27/10/2016.
 */

public class ApplicationUtilities {


    /**
     * Method responsible for create File of type image jpg on disk
     * @param activity
     * @param isCropped
     * @return File
     */
    public static File getOutputMediaFile(Activity activity, boolean isCropped){
        File mediaFileStore = new File(Environment.getExternalStorageDirectory()
                +"/Android/data/"
                +activity.getApplicationContext().getPackageName()
                +"/Images");

        if(!mediaFileStore.exists()){
            if(!mediaFileStore.mkdirs()){
                return null;
            }
        }

        String timestamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());

        File mediaFile ;
        String imageName = "img_"+(isCropped ? "small_":"")+timestamp+".jpg";
        mediaFile = new File(mediaFileStore.getPath()+File.separator+imageName);
        return mediaFile;
    }
}
