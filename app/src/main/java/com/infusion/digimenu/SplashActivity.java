package com.infusion.digimenu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.infusion.digimenu.datasource.MenuDataSource;
import com.infusion.digimenu.datasource.MenuDataSourceImpl;
import com.infusion.digimenu.datasource.MenuDataSourceObserver;
import com.infusion.digimenu.model.Location;
import com.infusion.digimenu.model.Menu;

import java.util.Observable;


public class SplashActivity extends Activity implements MenuDataSourceObserver {
    private final MenuDataSource mMenuDataSource;

    public SplashActivity() {
        mMenuDataSource = new MenuDataSourceImpl(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView titleTextView = (TextView) findViewById(R.id.titleTextView);
        applyTypeface(titleTextView);

        TextView loadingTextView = (TextView) findViewById(R.id.loadingTextView);
        applyTypeface(loadingTextView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Location currentLocation = new Location(0, 0);

        // retrieve the latest menu
        mMenuDataSource.addObserver(this);
        mMenuDataSource.getMenu(currentLocation);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mMenuDataSource.deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object data) {
        if (data == null) {
            // invalid result - guard
            return;
        }

        handleMenuRetrieved((Menu) data);
    }

    private void handleMenuRetrieved(Menu menu) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(MenuActivity.BUNDLE_MENU, menu);

        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
        finish();
    }

    private void applyTypeface(TextView textView) {
        textView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/lily_script_one_regular.ttf"));
    }
}
