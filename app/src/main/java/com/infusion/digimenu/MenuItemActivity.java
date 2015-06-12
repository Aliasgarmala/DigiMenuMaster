package com.infusion.digimenu;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
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
                animateLikeButtonOut();
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        animateLikeButtonInViewXml();
    }

    private void sendLike(int menuItemId) {
        // =======================================
        // LAB 3
        // =======================================
        // 1. Create an explicit intent to start the LikeMenuItemService service
        // 2. Pass the menuItemId value as an extra
    }

    private void animateLikeButtonInViewPropertyAnim() {
        float y = mLikeButton.getY();
        mLikeButton.setY(-mLikeButton.getHeight());
        mLikeButton.animate().setInterpolator(new BounceInterpolator()).setDuration(1000).y(y);
        Log.d(this.getClass().getName(), "onWindowFocusChanged");
    }

    private void animateLikeButtonInViewCode() {
        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, //From X
                Animation.RELATIVE_TO_SELF, 0f, //To X
                Animation.RELATIVE_TO_PARENT, -1f, //From Y
                Animation.RELATIVE_TO_SELF, 0f);  //To Y
        animation.setFillBefore(true);
        animation.setInterpolator(new BounceInterpolator());
        animation.setDuration(1000);
        mLikeButton.setAnimation(animation);
        animation.start();
    }

    private void animateLikeButtonInViewXml() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.like_button_in);
        mLikeButton.setAnimation(animation);
        animation.start();
    }

    private void animateLikeButtonInPropertyAnim() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mLikeButton, "y", -mLikeButton.getHeight(), mLikeButton.getY());
        objectAnimator.setDuration(1000);
        objectAnimator.setInterpolator(new BounceInterpolator());
        objectAnimator.start();
    }

    private void animateLikeButtonOut() {
        mLikeButton.animate().scaleY(0f).scaleX(0f).setInterpolator(new AnticipateInterpolator()).alpha(0).setDuration(500);
    }
}
