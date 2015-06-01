package com.infusion.digimenu.model;

import java.io.Serializable;

/**
 * Created by ali on 2015-05-30.
 */
public class Menu implements Serializable {
    public String region;
    public MenuCategory[] categories;

    public Menu(String region, MenuCategory[] categories) {
        this.region = region;
        this.categories = categories;
    }
}
