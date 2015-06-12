package com.infusion.digimenu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.infusion.digimenu.datasource.MenuDataSource;
import com.infusion.digimenu.datasource.MenuDataSourceHttpImpl;
import com.infusion.digimenu.location.CountryLocator;
import com.infusion.digimenu.location.CountryLocatorImpl;
import com.infusion.digimenu.model.Menu;

import java.util.Observable;
import java.util.Observer;


public class SplashActivity extends Activity implements Observer {
    private MenuDataSource mMenuDataSource;
    private CountryLocator mCountryLocator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // apply custom font family for text controls
        applyTypeface((TextView) findViewById(R.id.titleTextView));
        applyTypeface((TextView) findViewById(R.id.loadingTextView));

        mMenuDataSource = new MenuDataSourceHttpImpl();
        mCountryLocator = new CountryLocatorImpl(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mCountryLocator.addObserver(this);
        mCountryLocator.locateCountry();

//        String country = getCountry();
//
//        // retrieve the latest menu
//        mMenuDataSource.addObserver(this);
//        mMenuDataSource.getMenuAsync(country);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mCountryLocator.deleteObserver(this);
        mMenuDataSource.deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object data) {
        if (data == null) {
            // invalid result - guard
            return;
        }

        if (observable instanceof CountryLocator) {
            String country = (String) data;
            // retrieve the latest menu
            mMenuDataSource.addObserver(this);
            mMenuDataSource.getMenuAsync(country);
        } else if (observable instanceof MenuDataSource) {
            handleMenuRetrieved((Menu) data);
        }
    }

    private void handleMenuRetrieved(Menu menu) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(MenuActivity.BUNDLE_MENU, menu);

        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
        finish();
    }

    private String getCountry() {
        return "Canada";
    }

    private void applyTypeface(TextView textView) {
        textView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/lily_script_one_regular.ttf"));
    }
}
