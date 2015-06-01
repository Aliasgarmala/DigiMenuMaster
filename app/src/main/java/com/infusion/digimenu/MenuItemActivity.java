package com.infusion.digimenu;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.infusion.digimenu.framework.DownloadImageTask;
import com.infusion.digimenu.model.MenuItem;

import java.text.NumberFormat;

public class MenuItemActivity extends ActionBarActivity {
    public static final String BUNDLE_MENU_ITEM = "MenuItem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);

        // retrieve the menu item from the bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            // invalid argument - no menu item specified
            return;
        }

        MenuItem menuItem = (MenuItem) bundle.get(BUNDLE_MENU_ITEM);
        setTitle(menuItem.name);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        new DownloadImageTask(imageView).execute(menuItem.imageUri);

        TextView descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        descriptionTextView.setText(menuItem.description);

        TextView priceTextView = (TextView) findViewById(R.id.priceTextView);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(menuItem.price));
    }
}
