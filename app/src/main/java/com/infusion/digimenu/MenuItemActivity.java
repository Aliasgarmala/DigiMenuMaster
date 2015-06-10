package com.infusion.digimenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.infusion.digimenu.framework.DownloadImageTask;
import com.infusion.digimenu.model.MenuItem;

import java.text.NumberFormat;

public class MenuItemActivity extends ActionBarActivity {
    public static final String BUNDLE_MENU_ITEM = "com.infusion.digimenu.extra.BUNDLE_MENU_ITEM";

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

        final MenuItem menuItem = (MenuItem) bundle.get(BUNDLE_MENU_ITEM);
        setTitle(menuItem.name);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        new DownloadImageTask(imageView).execute(menuItem.imageUri);

        TextView descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        descriptionTextView.setText(menuItem.description);

        TextView priceTextView = (TextView) findViewById(R.id.priceTextView);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(menuItem.price));

        final ImageButton likeButton = (ImageButton)findViewById(R.id.likeButton);

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeButton.animate().scaleY(0f).scaleX(0f).setInterpolator(new AnticipateInterpolator()).alpha(0).setDuration(500);
                sendLikeAsync(menuItem.id);
            }
        });
    }


    private void sendLikeAsync(int menuItemId) {
        // perform service to notify of like
        Intent intent = new Intent(this, LikeMenuItemService.class);
        intent.putExtra(LikeMenuItemService.EXTRA_MENU_ITEM_ID, menuItemId);

        startService(intent);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        final ImageButton likeButton = (ImageButton)findViewById(R.id.likeButton);
        float y = likeButton.getY();
        likeButton.setY(0);
        likeButton.setAlpha(0f);
        likeButton.animate().setInterpolator(new BounceInterpolator()).setDuration(1000).y(y).alpha(1);
        Log.d(this.getClass().getName(), "onWindowFocusChanged");
    }
}
