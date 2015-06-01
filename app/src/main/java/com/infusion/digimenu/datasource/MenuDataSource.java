package com.infusion.digimenu.datasource;

import com.infusion.digimenu.model.Location;

/**
 * Created by ali on 2015-05-30.
 */
public interface MenuDataSource {
    void getMenuAsync(Location position, MenuDataSourceListener listener);
}
