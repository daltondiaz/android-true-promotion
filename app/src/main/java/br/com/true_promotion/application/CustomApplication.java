package br.com.true_promotion.application;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Dalton on 19/10/2016.
 */

public class CustomApplication extends Application {

    @Override
    public void onCreate() {

        super.onCreate();

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name("true_promotion")
                .deleteRealmIfMigrationNeeded() // TODO After Delete this
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
