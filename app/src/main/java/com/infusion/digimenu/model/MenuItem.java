package com.infusion.digimenu.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by ali on 2015-05-30.
 */
public class MenuItem implements Serializable {
    public int id;
    public String name;
    public String description;
    public boolean isSpicy;
    public String imageUri;
    public BigDecimal price;

    public MenuItem(int id, String name, String description, boolean isSpicy, String imageUri, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isSpicy = isSpicy;
        this.imageUri = imageUri;
        this.price = price;
    }
}
