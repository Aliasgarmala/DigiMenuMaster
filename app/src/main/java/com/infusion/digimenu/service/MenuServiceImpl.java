package com.infusion.digimenu.service;

import com.infusion.digimenu.datasource.MenuDataSource;
import com.infusion.digimenu.datasource.MenuDataSourceListener;
import com.infusion.digimenu.model.Menu;
import com.infusion.digimenu.model.Location;

/**
 * Created by ali on 2015-05-31.
 */
public class MenuServiceImpl implements MenuService {
    private MenuDataSource mDataSource;
    private Menu mCache;

    public MenuServiceImpl(MenuDataSource dataSource) {
        this.mDataSource = dataSource;
    }

    @Override
    public void getMenuAsync(final MenuServiceListener callback) {
        if (mCache != null) {
            // menu already cached - use it
            callback.onMenuRetrieved(mCache);
            return;
        }

        final Location position = new Location(0, 0);
        final MenuDataSourceListener dataSourceCallback = new MenuDataSourceListener() {
            @Override
            public void onMenuRetrieved(Menu menu) {
                // menu retrieved - cache and notify
                mCache = menu;
                callback.onMenuRetrieved(menu);
            }
        };

        mDataSource.getMenuAsync(position, dataSourceCallback);
    }
}
