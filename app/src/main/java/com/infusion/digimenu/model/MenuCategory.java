package com.infusion.digimenu.model;

import java.io.Serializable;

/**
 * Created by ali on 2015-05-30.
 */
public class MenuCategory implements Serializable {
    public String name;
    public MenuItem[] items;

    public MenuCategory(String name, MenuItem[] items) {
        this.name = name;
        this.items = items;
    }
}
