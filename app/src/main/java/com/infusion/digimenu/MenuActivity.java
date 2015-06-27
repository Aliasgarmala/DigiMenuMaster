package com.infusion.digimenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.infusion.digimenu.model.Menu;
import com.infusion.digimenu.model.MenuCategory;


public class MenuActivity extends ActionBarActivity implements ActionBar.TabListener {

    public static final String BUNDLE_MENU = "com.infusion.digimenu.extra.BUNDLE_MENU";

    private ActionBarr mActionBar;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            // bundle expected - guard
            return;
        }

        // setup the action bar with tab layout
        mActionBar = getSupportActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mViewPager = (ViewPager) findViewById(R.id.pager);

        loadMenu((Menu) bundle.get(BUNDLE_MENU));
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // user pressed an actionbar tab - update the tab page shown
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    private void loadMenu(com.infusion.digimenu.model.Menu menu) {
        MenuCategory[] menuCategories = menu.categories;

        // create an actionbar tab for each category of the menu
        for (int i = 0; i < menuCategories.length; i++) {
            ActionBar.Tab menuCategoryTab = mActionBar.newTab();
            menuCategoryTab.setText(menuCategories[i].name);
            menuCategoryTab.setTabListener(this);

            mActionBar.addTab(menuCategoryTab);
        }

        // create an adapter to map from menu categories (i.e. starters, entrees, etc.) to fragments of the tab view
        MenuCategoryPagerAdapter menuCategoryPageAdapter = new MenuCategoryPagerAdapter(getSupportFragmentManager(), menuCategories);
        mViewPager.setAdapter(menuCategoryPageAdapter);

        // user swiped between tab pages - update the selected actionbar tab
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mActionBar.setSelectedNavigationItem(position);
            }
        });
    }

    public class MenuCategoryPagerAdapter extends FragmentPagerAdapter {
        private final MenuCategory[] mMenuCategories;

        public MenuCategoryPagerAdapter(FragmentManager fm, MenuCategory[] menuCategories) {
            super(fm);

            mMenuCategories = menuCategories;
        }

        @Override
        public Fragment getItem(int position) {
            return MenuCategoryFragment.createNewInstance(mMenuCategories[position]);
        }

        @Override
        public int getCount() {
            return mMenuCategories.length;
        }
    }
}