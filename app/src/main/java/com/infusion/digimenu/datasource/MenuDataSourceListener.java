package com.infusion.digimenu.datasource;

import com.infusion.digimenu.model.Menu;

/**
 * Created by ali on 2015-05-31.
 */
public interface MenuDataSourceListener {
    void onMenuRetrieved(Menu menu);
}
