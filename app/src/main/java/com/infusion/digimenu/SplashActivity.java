package com.infusion.digimenu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.infusion.digimenu.datasource.MenuDataSource;
import com.infusion.digimenu.datasource.MenuDataSourceImpl;
import com.infusion.digimenu.service.MenuService;
import com.infusion.digimenu.service.MenuServiceImpl;
import com.infusion.digimenu.service.MenuServiceListener;


public class SplashActivity extends Activity implements MenuServiceListener {
    private final MenuService mMenuService;

    public SplashActivity() {
        MenuDataSource menuDataSource = new MenuDataSourceImpl(this);
        mMenuService = new MenuServiceImpl(menuDataSource);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView titleTextView = (TextView) findViewById(R.id.titleTextView);
        applyTypeface(titleTextView);

        TextView loadingTextView = (TextView) findViewById(R.id.loadingTextView);
        applyTypeface(loadingTextView);

        // retrieve the latest menu
        mMenuService.getMenuAsync(this);
    }

    @Override
    public void onMenuRetrieved(com.infusion.digimenu.model.Menu menu) {
        // create the adapter to map from model object to tab - apply it
        // determine the item clicked - navigate to detail page
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
