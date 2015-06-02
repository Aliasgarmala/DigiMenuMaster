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

    private ViewPager mViewPager;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // setup the actionbar
        mActionBar = getSupportActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mViewPager = (ViewPager) findViewById(R.id.pager);

        // retrieve the menu from the bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            // invalid argument - no menu specified
            return;
        }

        loadMenu((Menu) bundle.get(BUNDLE_MENU));
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    private void loadMenu(com.infusion.digimenu.model.Menu menu) {
        // create the adapter to map from model object to tab - apply it
        FragmentPagerAdapter menuPagerAdapter = new MenuPagerAdapter(getSupportFragmentManager(), menu);
        mViewPager.setAdapter(menuPagerAdapter);

        // synchronize the actionbar with changes to the view pager
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mActionBar.setSelectedNavigationItem(position);
            }
        });

        // create a tab for each menu category
        for (int i = 0; i < menuPagerAdapter.getCount(); i++) {
            mActionBar.addTab(mActionBar.newTab()
                    .setText(menuPagerAdapter.getPageTitle(i)).setTabListener(this));
        }
    }

    public class MenuPagerAdapter extends FragmentPagerAdapter {

        private final Menu mMenu;

        public MenuPagerAdapter(FragmentManager fm, Menu menu) {
            super(fm);

            mMenu = menu;
        }

        @Override
        public Fragment getItem(int position) {
            MenuCategory menuCategory = mMenu.categories[position];
            return MenuPageFragment.newInstance(menuCategory);
        }

        @Override
        public int getCount() {
            return mMenu.categories.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mMenu.categories[position].name;
        }
    }
}
