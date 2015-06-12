package com.infusion.digimenu;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.infusion.digimenu.framework.DownloadImageTask;
import com.infusion.digimenu.model.MenuItem;

import java.text.NumberFormat;

public class MenuItemActivity extends ActionBarActivity {

    public static final String BUNDLE_MENU_ITEM = "com.infusion.digimenu.extra.BUNDLE_MENU_ITEM";

    private AsyncTask<String, Void, Bitmap> mDownloadImageTask;
    private ImageButton mLikeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            // bundle expected - guard
            return;
        }

        // retrieve the menu item from the bundle
        final MenuItem menuItem = (MenuItem) bundle.get(BUNDLE_MENU_ITEM);

        // start download the menu item image
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        mDownloadImageTask = new DownloadImageTask(imageView).execute(menuItem.imageUri);

        // setup the activity title
        setTitle(menuItem.name);

        // setup the menu item description
        TextView descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        descriptionTextView.setText(menuItem.description);

        // setup the menu item price
        TextView priceTextView = (TextView) findViewById(R.id.priceTextView);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(menuItem.price));

        // setup the menu item like button
        mLikeButton = (ImageButton) findViewById(R.id.likeButton);
        mLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLike(menuItem.id);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        // stop the menu item image from downloading (if it hasn't already completed)
        if (mDownloadImageTask != null && mDownloadImageTask.getStatus() != AsyncTask.Status.FINISHED) {
            mDownloadImageTask.cancel(true);
        }
    }

    private void sendLike(int menuItemId) {
        // =======================================
        // LAB 3
        // =======================================
        // 1. Create an explicit intent to start the LikeMenuItemService service
        // 2. Pass the menuItemId value as an extra
    }
}
