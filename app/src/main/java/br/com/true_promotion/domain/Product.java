package br.com.true_promotion.domain;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Dalton on 19/10/2016.
 */

public class Product extends RealmObject{


    public static final String ID = "br.com.true_promotion.domain.Product.ID";

    @PrimaryKey
    private long id;
    private String name;
    private String description;
    private float measure;
    private String type_product;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getMeasure() {
        return measure;
    }

    public void setMeasure(float measure) {
        this.measure = measure;
    }

    public String getType_product() {
        return type_product;
    }

    public void setType_product(String type_product) {
        this.type_product = type_product;
    }
}
