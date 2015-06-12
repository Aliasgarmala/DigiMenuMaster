package com.infusion.digimenu.datasource;

import java.util.Observable;

/**
 * Created by ali on 2015-05-30.
 */
public abstract class MenuDataSource extends Observable {
    public abstract Thread getMenuAsync(String country);
}
