package com.infusion.digimenu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.infusion.digimenu.datasource.MenuDataSource;
import com.infusion.digimenu.datasource.MenuDataSourceHttpImpl;
import com.infusion.digimenu.location.CountryLocator;
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
        //TODO: (5) Instantiate mCountryLocator with a new CountryLocatorImpl object
    }

    @Override
    protected void onResume() {
        super.onResume();

        //TODO: (6) Remove following line. We are going to find the country by location
        String country = getCountry();

        //TODO: (7) Subscribe to Country Locator and request country name

        //TODO: (8) Comment the following code. We can't attempt to get location based manu before finding the contry name
        //We will do this when we receive the country name
        // retrieve the latest menu
        mMenuDataSource.addObserver(this);
        mMenuDataSource.getMenuAsync(country);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //TODO: (9) Unsubscribe from Country Locator
        mMenuDataSource.deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object data) {
        if (data == null) {
            // invalid result - guard
            return;
        }

        //  We have subscribed to two Observables (CountryLocator and MenuDataSource) and receive notifications from both
        //  of them here. Use reflection on the observable to determine the source of the notification
        if( observable instanceof  MenuDataSource) {
            handleMenuRetrieved((Menu) data);
        }
        //TODO: (10) Listen to CountryLocator notifications here by adding an 'else' clause here
        //  When we receive the country name, subscribe to the MenuDataSource and request menu
        //  (move commented code from onResume() here)

    }

    private void handleMenuRetrieved(Menu menu) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(MenuActivity.BUNDLE_MENU, menu);

        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
        finish();
    }

    //TODO: (11) Remove this method. We are not using hard-coded country name any more
    private String getCountry() {
        return "Canada";
    }

    private void applyTypeface(TextView textView) {
        textView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/lily_script_one_regular.ttf"));
    }
}
